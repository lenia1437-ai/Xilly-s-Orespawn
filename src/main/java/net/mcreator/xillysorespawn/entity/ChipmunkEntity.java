package net.mcreator.xillysorespawn.entity;

import java.util.Random;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.fml.network.NetworkHooks;

import net.minecraft.block.Blocks;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.entity.ai.goal.BreedGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.MoveThroughVillageAtNightGoal;
import net.minecraft.entity.ai.goal.PanicGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Difficulty;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.server.ServerWorld;

import net.mcreator.xillysorespawn.XillysOrespawnModElements;
import net.mcreator.xillysorespawn.entity.renderer.ChipmunkRenderer;

@XillysOrespawnModElements.ModElement.Tag
public class ChipmunkEntity extends XillysOrespawnModElements.ModElement {
    public static EntityType entity = (EntityType.Builder.<CustomEntity>create(CustomEntity::new, EntityClassification.CREATURE)
        .setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3)
        .setCustomClientFactory(CustomEntity::new).size(0.35F, 0.35F))
        .build("chipmunk").setRegistryName("chipmunk");

    public ChipmunkEntity(XillysOrespawnModElements instance) {
        super(instance, 7);
        FMLJavaModLoadingContext.get().getModEventBus().register(new ChipmunkRenderer.ModelRegisterHandler());
        FMLJavaModLoadingContext.get().getModEventBus().register(new EntityAttributesRegisterHandler());
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public void initElements() {
        elements.entities.add(() -> entity);
        elements.items.add(() -> new net.minecraft.item.SpawnEggItem(entity, -1, -1,
            new net.minecraft.item.Item.Properties().group(net.minecraft.item.ItemGroup.MISC))
            .setRegistryName("chipmunk_spawn_egg"));
    }

    @SubscribeEvent
    public void addFeatureToBiomes(BiomeLoadingEvent event) {
        if (!OreSpawnLogic.allowNaturalSpawn(event, "chipmunk")) return;
        event.getSpawns().getSpawner(EntityClassification.CREATURE)
            .add(new MobSpawnInfo.Spawners(entity, 10, 1, 3));
    }

    @Override
    public void init(FMLCommonSetupEvent event) {
        EntitySpawnPlacementRegistry.register(entity, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND,
            Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, ChipmunkEntity::canSpawn);
    }

    public static boolean canSpawn(EntityType<? extends TameableEntity> type, IServerWorld world,
            SpawnReason reason, BlockPos pos, Random random) {
        if (reason == SpawnReason.SPAWNER || reason == SpawnReason.SPAWN_EGG || reason == SpawnReason.COMMAND) {
            return true;
        }
        if (pos.getY() < 50) {
            return false;
        }
        AxisAlignedBB area = new AxisAlignedBB(pos).grow(20.0D, 10.0D, 20.0D);
        return world.getEntitiesWithinAABB(CustomEntity.class, area, e -> true).size() <= 2;
    }

    public static class EntityAttributesRegisterHandler {
        @SubscribeEvent
        public void onEntityAttributeCreation(EntityAttributeCreationEvent event) {
            event.put(entity, MobEntity.func_233666_p_()
                .createMutableAttribute(Attributes.MAX_HEALTH, 5.0D)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.38D)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 1.0D)
                .createMutableAttribute(Attributes.ARMOR, 0.0D)
                .createMutableAttribute(Attributes.FOLLOW_RANGE, 16.0D).create());
        }
    }

    public static class CustomEntity extends TameableEntity {
        private static final DataParameter<Integer> ACTIVATED = EntityDataManager.createKey(CustomEntity.class, DataSerializers.VARINT);
        private static final DataParameter<Integer> HAT_COLOR = EntityDataManager.createKey(CustomEntity.class, DataSerializers.VARINT);
        private String nameOne;
        private String nameTwo;
        private BlockPos patrolPos = BlockPos.ZERO;

        public CustomEntity(FMLPlayMessages.SpawnEntity packet, World world) {
            this(entity, world);
        }

        public CustomEntity(EntityType<? extends CustomEntity> type, World world) {
            super(type, world);
            this.experienceValue = 5;
            this.func_233687_w_(false);
        }

        @Override
        public IPacket<?> createSpawnPacket() {
            return NetworkHooks.getEntitySpawningPacket(this);
        }

        @Override
        protected void registerData() {
            super.registerData();
            this.dataManager.register(ACTIVATED, 0);
            this.dataManager.register(HAT_COLOR, 0);
        }

        public int get_is_activated() {
            return this.dataManager.get(ACTIVATED);
        }

        private void setActivated(int value) {
            this.dataManager.set(ACTIVATED, value);
        }

        public int getHatColor() {
            return this.dataManager.get(HAT_COLOR);
        }

        private void setHatColor(int value) {
            this.dataManager.set(HAT_COLOR, value);
        }

        public boolean isChildModel() {
            return this.isChild();
        }

        @Override
        protected void registerGoals() {
            super.registerGoals();
            this.goalSelector.addGoal(0, new SwimGoal(this));
            this.goalSelector.addGoal(1, new BreedGoal(this, 1.0D));
            this.goalSelector.addGoal(2, new OreSpawnFollowOwnerGoal(this, 2.0D, 10.0F, 2.0F));
            this.goalSelector.addGoal(3, new AvoidEntityGoal<MonsterEntity>(this,
                    MonsterEntity.class, 8.0F, 1.0D, 1.6D) {
                @Override
                public boolean shouldExecute() {
                    return CustomEntity.this.get_is_activated() == 0 && super.shouldExecute();
                }
            });
            this.goalSelector.addGoal(4, new TemptGoal(this, 1.2D, Ingredient.fromItems(Items.APPLE), false));
            this.goalSelector.addGoal(5, new PanicGoal(this, 1.5D));
            this.goalSelector.addGoal(6, new AvoidEntityGoal<>(this, PlayerEntity.class, 8.0F, 1.0D, 1.4D));
            this.goalSelector.addGoal(7, new LookAtGoal(this, PlayerEntity.class, 6.0F));
            this.goalSelector.addGoal(8, new LookAtGoal(this, LivingEntity.class, 5.0F));
            this.goalSelector.addGoal(9, new OreSpawnWanderGoal(this, 10, 30, 1.0D, false));
            this.goalSelector.addGoal(10, new LookRandomlyGoal(this));
            this.goalSelector.addGoal(11, new MoveThroughVillageAtNightGoal(this, 50));
        }

        private boolean isCannonFodder(LivingEntity entity) {
            return OreSpawnLogic.isNamed(entity, "chipmunk", "ostrich", "lizard", "velocity_raptor");
        }

        private int otherHatColor(LivingEntity entity) {
            return entity instanceof CustomEntity ? ((CustomEntity) entity).getHatColor()
                : entity.serializeNBT().getInt("HatColor");
        }

        private boolean isOriginalTarget(LivingEntity target) {
            if (this.world.getDifficulty() == Difficulty.PEACEFUL || !this.getEntitySenses().canSee(target)) {
                return false;
            }
            if (this.isSitting() && target.getDistanceSq(this.patrolPos.getX(), this.patrolPos.getY(), this.patrolPos.getZ()) > 144.0D) {
                return false;
            }
            if (target instanceof MonsterEntity) {
                return true;
            }
            if (this.isCannonFodder(target)) {
                int color = this.otherHatColor(target);
                return color != 0 && color != this.getHatColor();
            }
            if (target instanceof PlayerEntity) {
                PlayerEntity player = (PlayerEntity) target;
                String id = player.getUniqueID().toString();
                return !player.abilities.isCreativeMode && !id.equals(this.nameOne) && !id.equals(this.nameTwo);
            }
            return false;
        }

        private void tickActivatedCombat() {
            if (this.get_is_activated() != 2) {
                return;
            }
            if (this.world.getDifficulty() != Difficulty.PEACEFUL && this.rand.nextInt(5) == 1) {
                LivingEntity target = OreSpawnLogic.nearestTarget(this, this.getBoundingBox().grow(10.0D, 4.0D, 10.0D),
                    this::isOriginalTarget);
                if (target != null) {
                    this.getNavigator().tryMoveToEntityLiving(target, 1.25D);
                    if (this.getDistanceSq(target) < 9.0D
                            && (this.rand.nextInt(7) == 0 || this.rand.nextInt(6) == 1)) {
                        target.attackEntityFrom(DamageSource.causeMobDamage(this), 3.0F);
                    }
                } else if (this.isSitting()) {
                    this.getNavigator().tryMoveToXYZ(this.patrolPos.getX(), this.patrolPos.getY(),
                        this.patrolPos.getZ(), 0.65D);
                }
            }
            if (this.rand.nextInt(250) == 1) {
                this.heal(1.0F);
            }
        }

        @Override
        public void livingTick() {
            super.livingTick();
            if (!this.world.isRemote) {
                this.getAttribute(Attributes.ARMOR).setBaseValue(this.get_is_activated() == 2 ? 3.0D : 0.0D);
                if (this.rand.nextInt(200) == 1) {
                    this.setAttackTarget(null);
                }
                if (this.rand.nextInt(250) == 0) {
                    this.heal(1.0F);
                }
                if (this.rand.nextInt(600) == 1) {
                    BlockPos below = this.getPosition().down();
                    if ((this.world.getBlockState(below).isIn(Blocks.DIRT)
                            || this.world.getBlockState(below).isIn(Blocks.FARMLAND))
                            && ForgeEventFactory.getMobGriefingEvent(this.world, this)) {
                        this.world.destroyBlock(below, false, this);
                    }
                }
                this.tickActivatedCombat();
            }
        }

        private boolean consumeActivationFood(PlayerEntity player, ItemStack stack, Item item, int color) {
            if (stack.getItem() != item || this.getDistanceSq(player) >= 16.0D) {
                return false;
            }
            this.setHatColor(color);
            String playerId = player.getUniqueID().toString();
            if (this.nameOne == null) this.nameOne = playerId;
            if (this.get_is_activated() == 0) this.setActivated(1);
            this.setTamedBy(player);
            this.func_233687_w_(false);
            this.heal(this.getMaxHealth() - this.getHealth());
            this.enablePersistence();
            if (!player.abilities.isCreativeMode) stack.shrink(1);
            return true;
        }

        private void updateTwoOwnerActivation(PlayerEntity player) {
            if (this.nameOne == null || !this.isTamed()) return;
            String playerId = player.getUniqueID().toString();
            if (this.nameOne.equals(playerId) && this.nameTwo == null) {
                this.nameTwo = this.nameOne;
                this.nameOne = playerId;
                this.setOwnerId(player.getUniqueID());
                this.setActivated(2);
            } else if (!this.nameOne.equals(playerId) && this.nameTwo == null) {
                this.nameTwo = this.nameOne;
                this.nameOne = playerId;
                this.setOwnerId(player.getUniqueID());
                this.setActivated(2);
            } else if (playerId.equals(this.nameTwo)) {
                this.nameTwo = this.nameOne;
                this.nameOne = playerId;
                this.setOwnerId(player.getUniqueID());
                this.setActivated(2);
            }
        }

        @Override
        public ActionResultType func_230254_b_(PlayerEntity player, Hand hand) {
            ItemStack stack = player.getHeldItem(hand);
            this.updateTwoOwnerActivation(player);

            if (this.consumeActivationFood(player, stack, Items.CARROT, 1)
                    || this.consumeActivationFood(player, stack, Items.POTATO, 3)
                    || this.consumeActivationFood(player, stack, OreSpawnLogic.item("quinoa"), 2)) {
                return ActionResultType.func_233537_a_(this.world.isRemote);
            }

            if (this.get_is_activated() == 2 && stack.getItem() == OreSpawnLogic.item("corn_cob", "corn")
                    && this.getDistanceSq(player) < 16.0D) {
                Entity childEntity = OreSpawnLogic.spawn(this.world, "chipmunk", this.getPosX() + this.rand.nextFloat(),
                    this.getPosY() + 0.01D, this.getPosZ() + this.rand.nextFloat());
                if (childEntity instanceof CustomEntity) {
                    CustomEntity child = (CustomEntity) childEntity;
                    child.nameOne = this.nameOne;
                    child.nameTwo = this.nameTwo;
                    child.setHatColor(this.getHatColor());
                    child.setActivated(this.get_is_activated());
                    child.setOwnerId(this.getOwnerId());
                    child.setTamed(true);
                    child.enablePersistence();
                }
                this.world.setEntityState(this, (byte) 7);
                this.playSound(SoundEvents.ENTITY_GENERIC_EXPLODE, 0.75F, 2.0F);
                if (!player.abilities.isCreativeMode) stack.shrink(1);
                return ActionResultType.func_233537_a_(this.world.isRemote);
            }

            if (this.get_is_activated() == 2 && this.getDistanceSq(player) < 16.0D) {
                this.func_233687_w_(!this.isSitting());
                if (this.isSitting()) this.patrolPos = this.getPosition();
                return ActionResultType.func_233537_a_(this.world.isRemote);
            }

            if (stack.getItem() == Items.APPLE && this.getDistanceSq(player) < 16.0D) {
                if (!this.isTamed()) {
                    if (!this.world.isRemote) {
                        if (this.rand.nextInt(2) == 0) {
                            this.setTamedBy(player);
                            this.nameOne = player.getUniqueID().toString();
                            this.func_233687_w_(false);
                            this.enablePersistence();
                            this.heal(this.getMaxHealth() - this.getHealth());
                            this.world.setEntityState(this, (byte) 7);
                        } else {
                            this.world.setEntityState(this, (byte) 6);
                        }
                    }
                } else if (this.isOwner(player)) {
                    this.heal(this.getMaxHealth() - this.getHealth());
                }
                if (!player.abilities.isCreativeMode) stack.shrink(1);
                return ActionResultType.func_233537_a_(this.world.isRemote);
            }

            if (this.isTamed() && this.isOwner(player) && stack.getItem() == Blocks.DEAD_BUSH.asItem()
                    && this.getDistanceSq(player) < 16.0D) {
                this.setTamed(false);
                this.setOwnerId(null);
                this.nameOne = null;
                this.nameTwo = null;
                this.setActivated(0);
                this.setHatColor(0);
                if (!player.abilities.isCreativeMode) stack.shrink(1);
                return ActionResultType.func_233537_a_(this.world.isRemote);
            }

            ActionResultType parent = super.func_230254_b_(player, hand);
            if (parent.isSuccessOrConsume()) return parent;
            if (this.isTamed() && this.isOwner(player) && this.getDistanceSq(player) < 16.0D) {
                this.func_233687_w_(!this.isSitting());
                return ActionResultType.func_233537_a_(this.world.isRemote);
            }
            return ActionResultType.PASS;
        }

        @Override
        public boolean isBreedingItem(ItemStack stack) {
            return !stack.isEmpty() && stack.getItem() == OreSpawnLogic.item("crystal_apple", "my_crystal_apple");
        }

        @Override
        public AgeableEntity func_241840_a(ServerWorld world, AgeableEntity mate) {
            CustomEntity child = (CustomEntity) entity.create(world);
            if (child != null && this.isTamed()) {
                child.setOwnerId(this.getOwnerId());
                child.setTamed(true);
            }
            return child;
        }

        @Override
        public boolean onLivingFall(float distance, float damageMultiplier) {
            float damage = (float) Math.ceil(distance - 3.0F);
            if (damage <= 0.0F) return false;
            this.attackEntityFrom(DamageSource.FALL, Math.min(2.0F, damage));
            return true;
        }

        @Override
        protected SoundEvent getAmbientSound() {
            return null;
        }

        @Override
        protected SoundEvent getHurtSound(DamageSource source) {
            return OreSpawnLogic.sound("scorpion_hit", SoundEvents.ENTITY_RABBIT_HURT);
        }

        @Override
        protected SoundEvent getDeathSound() {
            return OreSpawnLogic.sound("cryo_death", SoundEvents.ENTITY_RABBIT_DEATH);
        }

        @Override
        protected float getSoundVolume() {
            return 0.4F;
        }

        @Override
        protected float getSoundPitch() {
            return this.isChild() ? (this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F + 1.5F
                : (this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F + 1.0F;
        }

        @Override
        protected void dropSpecialItems(DamageSource source, int looting, boolean recentlyHit) {
            super.dropSpecialItems(source, looting, recentlyHit);
            if (this.isTamed()) {
                int count = this.rand.nextInt(5) + 2;
                for (int i = 0; i < count; i++) this.entityDropItem(Blocks.POPPY.asItem());
            } else {
                int count = this.rand.nextInt(3);
                if (looting > 0) count += this.rand.nextInt(looting + 1);
                for (int i = 0; i < count; i++) this.entityDropItem(Items.WHEAT);
            }
        }

        @Override
        public void writeAdditional(CompoundNBT nbt) {
            super.writeAdditional(nbt);
            nbt.putString("NameOne", this.nameOne == null ? "" : this.nameOne);
            nbt.putString("NameTwo", this.nameTwo == null ? "" : this.nameTwo);
            nbt.putInt("IsActivated", this.get_is_activated());
            nbt.putInt("HatColor", this.getHatColor());
            nbt.putInt("PatrolX", this.patrolPos.getX());
            nbt.putInt("PatrolY", this.patrolPos.getY());
            nbt.putInt("PatrolZ", this.patrolPos.getZ());
        }

        @Override
        public void readAdditional(CompoundNBT nbt) {
            super.readAdditional(nbt);
            this.nameOne = nbt.getString("NameOne");
            if (this.nameOne.isEmpty()) this.nameOne = null;
            this.nameTwo = nbt.getString("NameTwo");
            if (this.nameTwo.isEmpty()) this.nameTwo = null;
            this.setActivated(nbt.getInt("IsActivated"));
            this.setHatColor(nbt.getInt("HatColor"));
            this.patrolPos = new BlockPos(nbt.getInt("PatrolX"), nbt.getInt("PatrolY"), nbt.getInt("PatrolZ"));
            if (this.nameOne != null) {
                this.setTamed(true);
                this.enablePersistence();
            }
        }
    }
}
