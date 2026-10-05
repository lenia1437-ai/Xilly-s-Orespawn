package net.mcreator.xillysorespawn.entity;

import java.util.Random;

import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.fml.network.NetworkHooks;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
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
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.IPacket;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.item.crafting.Ingredient;

import net.mcreator.xillysorespawn.XillysOrespawnModElements;
import net.mcreator.xillysorespawn.entity.renderer.CamarasaurusRenderer;

@XillysOrespawnModElements.ModElement.Tag
public class CamarasaurusEntity extends XillysOrespawnModElements.ModElement {
    public static EntityType entity = (EntityType.Builder.<CustomEntity>create(CustomEntity::new, EntityClassification.CREATURE)
        .setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3)
        .setCustomClientFactory(CustomEntity::new).size(0.5F, 1.2F))
        .build("camarasaurus").setRegistryName("camarasaurus");

    public CamarasaurusEntity(XillysOrespawnModElements instance) {
        super(instance, 3);
        FMLJavaModLoadingContext.get().getModEventBus().register(new CamarasaurusRenderer.ModelRegisterHandler());
        FMLJavaModLoadingContext.get().getModEventBus().register(new EntityAttributesRegisterHandler());
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public void initElements() {
        elements.entities.add(() -> entity);
        elements.items.add(() -> new net.minecraft.item.SpawnEggItem(entity, -1, -1,
            new net.minecraft.item.Item.Properties().group(net.minecraft.item.ItemGroup.MISC))
            .setRegistryName("camarasaurus_spawn_egg"));
    }

    @SubscribeEvent
    public void addFeatureToBiomes(BiomeLoadingEvent event) {
        if (!OreSpawnLogic.allowNaturalSpawn(event, "camarasaurus")) return;
        event.getSpawns().getSpawner(EntityClassification.CREATURE)
            .add(new MobSpawnInfo.Spawners(entity, 5, 1, 3));
    }

    @Override
    public void init(FMLCommonSetupEvent event) {
        EntitySpawnPlacementRegistry.register(entity, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND,
            Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, CamarasaurusEntity::canSpawn);
    }

    public static boolean canSpawn(EntityType<? extends TameableEntity> type, IServerWorld world,
            SpawnReason reason, BlockPos pos, Random random) {
        return reason == SpawnReason.SPAWNER || reason == SpawnReason.SPAWN_EGG || reason == SpawnReason.COMMAND
            || pos.getY() >= 50 && world.getWorld().isDaytime();
    }

    public static class EntityAttributesRegisterHandler {
        @SubscribeEvent
        public void onEntityAttributeCreation(EntityAttributeCreationEvent event) {
            event.put(entity, MobEntity.func_233666_p_()
                .createMutableAttribute(Attributes.MAX_HEALTH, 20.0D)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.2D)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 1.0D)
                .createMutableAttribute(Attributes.FOLLOW_RANGE, 16.0D).create());
        }
    }

    public static class CustomEntity extends TameableEntity {
        public CustomEntity(FMLPlayMessages.SpawnEntity packet, World world) {
            this(entity, world);
        }

        public CustomEntity(EntityType<? extends CustomEntity> type, World world) {
            super(type, world);
            this.experienceValue = 5;
            this.func_233687_w_(false);
        }

        public boolean isChildModel() {
            return this.isChild();
        }

        public int getCamarasaurusHealth() {
            return MathHelper.ceil(this.getHealth());
        }

        @Override
        public IPacket<?> createSpawnPacket() {
            return NetworkHooks.getEntitySpawningPacket(this);
        }

        @Override
        protected void registerGoals() {
            super.registerGoals();
            this.goalSelector.addGoal(0, new SwimGoal(this));
            this.goalSelector.addGoal(1, new BreedGoal(this, 1.0D));
            this.goalSelector.addGoal(2, new OreSpawnFollowOwnerGoal(this, 2.0D, 10.0F, 2.0F));
            this.goalSelector.addGoal(3, new AvoidEntityGoal<>(this, MonsterEntity.class, 8.0F, 1.0D, 1.4D));
            this.goalSelector.addGoal(4, new TemptGoal(this, 1.2D, Ingredient.fromItems(Items.APPLE), false));
            this.goalSelector.addGoal(5, new PanicGoal(this, 1.5D));
            this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 6.0F));
            this.goalSelector.addGoal(7, new OreSpawnWanderGoal(this, 10, 90, 1.0D, true));
            this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
            this.goalSelector.addGoal(9, new MoveThroughVillageAtNightGoal(this, 50));
        }

        private boolean ediblePlant(BlockState state) {
            return state.isIn(BlockTags.LEAVES) || state.isIn(Blocks.VINE) || state.isIn(Blocks.GRASS)
                || state.isIn(Blocks.TALL_GRASS) || state.isIn(Blocks.FERN) || state.isIn(Blocks.LARGE_FERN);
        }

        private void tryEatPlant() {
            if (this.isSitting() || OreSpawnLogic.playNicely != 0) {
                return;
            }
            boolean hungryRoll = this.rand.nextInt(20) == 0 && this.getHealth() < this.getMaxHealth();
            if (!hungryRoll && this.rand.nextInt(250) != 0) {
                return;
            }
            BlockPos target = null;
            BlockPos origin = this.getPosition().up();
            for (int radius = 1; radius < 11 && target == null; radius++) {
                int vertical = Math.min(radius, 2);
                target = OreLoot.scanShell(this.world, origin, radius, vertical, radius,
                    (w, p) -> this.ediblePlant(w.getBlockState(p)));
                if (radius >= 6) {
                    radius++;
                }
            }
            if (target == null) {
                return;
            }
            this.getNavigator().tryMoveToXYZ(target.getX(), target.getY(), target.getZ(), 1.0D);
            if (origin.distanceSq(target) < 12.0D) {
                if (ForgeHooks.canEntityDestroy(this.world, target, this)
                        && net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.world, this)) {
                    this.world.destroyBlock(target, false, this);
                }
                this.heal(1.0F);
                this.playSound(SoundEvents.ENTITY_PLAYER_BURP, 1.0F, this.rand.nextFloat() * 0.2F + 0.9F);
            }
        }

        @Override
        public void livingTick() {
            super.livingTick();
            if (!this.world.isRemote) {
                if (this.rand.nextInt(200) == 1) {
                    this.setAttackTarget(null);
                }
                this.tryEatPlant();
            }
        }

        @Override
        public ActionResultType func_230254_b_(PlayerEntity player, Hand hand) {
            ItemStack stack = player.getHeldItem(hand);
            if (stack.getItem() == Items.APPLE && this.getDistanceSq(player) < 16.0D) {
                if (!this.isTamed()) {
                    if (!this.world.isRemote) {
                        if (this.rand.nextInt(2) == 0) {
                            this.setTamedBy(player);
                            this.func_233687_w_(false);
                            this.enablePersistence();
                            this.heal(this.getMaxHealth() - this.getHealth());
                            this.world.setEntityState(this, (byte) 7);
                        } else {
                            this.world.setEntityState(this, (byte) 6);
                        }
                    }
                } else if (this.isOwner(player) && this.getHealth() < this.getMaxHealth()) {
                    this.heal(this.getMaxHealth() - this.getHealth());
                    this.world.setEntityState(this, (byte) 7);
                }
                if (!player.abilities.isCreativeMode) {
                    stack.shrink(1);
                }
                return ActionResultType.func_233537_a_(this.world.isRemote);
            }
            ActionResultType parent = super.func_230254_b_(player, hand);
            if (parent.isSuccessOrConsume()) {
                return parent;
            }
            if (this.isTamed() && this.isOwner(player) && this.getDistanceSq(player) < 16.0D) {
                this.func_233687_w_(!this.isSitting());
                this.getNavigator().clearPath();
                return ActionResultType.func_233537_a_(this.world.isRemote);
            }
            return ActionResultType.PASS;
        }

        @Override
        public boolean isBreedingItem(ItemStack stack) {
            return !stack.isEmpty() && stack.getItem() == OreSpawnLogic.item("crystal_apple", "my_crystal_apple");
        }

        public boolean isWheat(ItemStack stack) {
            return !stack.isEmpty() && stack.getItem() == Items.APPLE;
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
            if (damage <= 0.0F) {
                return false;
            }
            this.attackEntityFrom(DamageSource.FALL, Math.min(2.0F, damage));
            return true;
        }

        @Override
        protected SoundEvent getAmbientSound() {
            return null;
        }

        @Override
        protected SoundEvent getHurtSound(DamageSource source) {
            return OreSpawnLogic.sound("cryo_hurt", SoundEvents.ENTITY_COW_HURT);
        }

        @Override
        protected SoundEvent getDeathSound() {
            return OreSpawnLogic.sound("cryo_death", SoundEvents.ENTITY_COW_DEATH);
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
                for (int i = 0; i < count; i++) {
                    this.entityDropItem(Blocks.POPPY.asItem());
                }
            }
        }
    }
}
