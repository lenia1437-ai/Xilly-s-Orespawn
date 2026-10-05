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
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.MoveThroughVillageGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.Heightmap;

import net.mcreator.xillysorespawn.XillysOrespawnModElements;
import net.mcreator.xillysorespawn.entity.renderer.CaterKillerRenderer;

/** Behavioural port of OreSpawn 1.7.10 CaterKiller. */
@XillysOrespawnModElements.ModElement.Tag
public class CaterKillerEntity extends XillysOrespawnModElements.ModElement {
    public static EntityType entity = EntityType.Builder.<CustomEntity>create(CustomEntity::new, EntityClassification.MONSTER)
        .setShouldReceiveVelocityUpdates(true).setTrackingRange(100).setUpdateInterval(3)
        .setCustomClientFactory(CustomEntity::new).size(2.9F, 4.6F)
        .build("cater_killer").setRegistryName("cater_killer");

    public CaterKillerEntity(XillysOrespawnModElements instance) {
        super(instance, 1);
        FMLJavaModLoadingContext.get().getModEventBus().register(new CaterKillerRenderer.ModelRegisterHandler());
        FMLJavaModLoadingContext.get().getModEventBus().register(new EntityAttributesRegisterHandler());
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override public void initElements() {
        elements.entities.add(() -> entity);
        elements.items.add(() -> new SpawnEggItem(entity, -1, -1,
            new Item.Properties().group(ItemGroup.MISC)).setRegistryName("cater_killer_spawn_egg"));
    }

    @SubscribeEvent public void addFeatureToBiomes(BiomeLoadingEvent event) {
        if (!OreSpawnLogic.allowNaturalSpawn(event, "cater_killer")) return;
        event.getSpawns().getSpawner(EntityClassification.MONSTER)
            .add(new MobSpawnInfo.Spawners(entity, 5, 1, 2));
    }

    @Override public void init(FMLCommonSetupEvent event) {
        EntitySpawnPlacementRegistry.register(entity, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND,
            Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, CaterKillerEntity::canSpawn);
    }

    public static boolean canSpawn(EntityType<? extends MonsterEntity> type, IServerWorld world,
            SpawnReason reason, BlockPos pos, Random random) {
        if (reason == SpawnReason.SPAWNER || reason == SpawnReason.SPAWN_EGG
                || reason == SpawnReason.COMMAND) return true;
        if (pos.getY() < 50 || random.nextInt(10) != 0 || !world.getWorld().isDaytime()) return false;
        for (int x = -1; x < 2; x++) for (int z = -1; z < 2; z++) for (int y = 1; y < 5; y++) {
            BlockState state = world.getBlockState(pos.add(x, y, z));
            if (!state.isAir() && !state.isIn(BlockTags.LEAVES) && !state.isIn(BlockTags.LOGS)) return false;
        }
        return world.getEntitiesWithinAABB(CustomEntity.class,
            new AxisAlignedBB(pos).grow(48.0D, 16.0D, 48.0D), e -> true).isEmpty();
    }

    public static class EntityAttributesRegisterHandler {
        @SubscribeEvent public void onEntityAttributeCreation(EntityAttributeCreationEvent event) {
            event.put(entity, CustomEntity.createAttributes().create());
        }
    }

    public static class CustomEntity extends MonsterEntity {
        private static final DataParameter<Byte> ATTACKING = EntityDataManager.createKey(CustomEntity.class, DataSerializers.BYTE);
        private static final DataParameter<Integer> PLAY_NICELY = EntityDataManager.createKey(CustomEntity.class, DataSerializers.VARINT);
        private int damagedTicker;
        private boolean foundMob;

        public CustomEntity(FMLPlayMessages.SpawnEntity packet, World world) { this(entity, world); }
        public CustomEntity(EntityType<? extends CustomEntity> type, World world) {
            super(type, world);
            this.experienceValue = 200;
            this.setNoAI(false);
        }

        public static AttributeModifierMap.MutableAttribute createAttributes() {
            return MonsterEntity.func_233666_p_()
                .createMutableAttribute(Attributes.MAX_HEALTH, 450.0D)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.35D)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 32.0D)
                .createMutableAttribute(Attributes.ARMOR, 19.0D)
                .createMutableAttribute(Attributes.FOLLOW_RANGE, 48.0D);
        }

        @Override protected void registerData() {
            super.registerData();
            this.dataManager.register(ATTACKING, (byte)0);
            this.dataManager.register(PLAY_NICELY, OreSpawnLogic.playNicely);
        }

        @Override protected void registerGoals() {
            super.registerGoals();
            this.goalSelector.addGoal(0, new SwimGoal(this));
            this.goalSelector.addGoal(1, new MoveThroughVillageGoal(this, 1.0D, false, 4, () -> true));
            this.goalSelector.addGoal(2, new OreSpawnWanderGoal(this, 16, 30, 1.0D, false));
            this.goalSelector.addGoal(3, new LookAtGoal(this, PlayerEntity.class, 8.0F));
            this.goalSelector.addGoal(4, new LookRandomlyGoal(this));
            this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        }

        @Override public IPacket<?> createSpawnPacket() { return NetworkHooks.getEntitySpawningPacket(this); }
        public int getPlayNicely() { return this.dataManager.get(PLAY_NICELY); }
        public int getAttacking() { return this.dataManager.get(ATTACKING); }
        public void setAttacking(int value) { this.dataManager.set(ATTACKING, (byte)value); }
        public boolean isChildModel() { return false; }
        public Random getModelRandom() { return this.rand; }

        @Override public boolean attackEntityFrom(DamageSource source, float amount) {
            boolean result = super.attackEntityFrom(source, amount);
            if (source.getTrueSource() instanceof LivingEntity)
                this.setAttackTarget((LivingEntity)source.getTrueSource());
            return result;
        }

        @Override public void livingTick() {
            super.livingTick();
            if (this.world.isRemote) return;
            this.dataManager.set(PLAY_NICELY, OreSpawnLogic.playNicely);

            if (this.getHealth() + 1.0F < this.getMaxHealth() && ++this.damagedTicker > 2400) {
                OreSpawnLogic.spawn(this.world, "brutalfly", this.getPosX(), this.getPosY() + 4.0D, this.getPosZ());
                this.playSound(SoundEvents.ENTITY_GENERIC_EXPLODE, 1.0F, this.rand.nextFloat() * 0.2F + 0.9F);
                for (int i = 0; i < 10; i++) OreSpawnLogic.spawn(this.world, "butterfly", this.getPosX(),
                    this.getPosY() + 1.0D + this.rand.nextInt(4), this.getPosZ());
                this.remove();
                return;
            }

            if (this.collidedHorizontally) clearNearbyWebs();
            if (this.rand.nextInt(4) == 0) updateCombat();
            if (((this.rand.nextInt(8) == 0 && this.getHealth() < this.getMaxHealth())
                    || this.rand.nextInt(30) == 0) && OreSpawnLogic.playNicely == 0) eatTreePart();
        }

        private void clearNearbyWebs() {
            for (int x = -2; x <= 2; x++) for (int y = -1; y < 5; y++) for (int z = -2; z <= 2; z++) {
                BlockPos p = this.getPosition().add(x, y, z);
                if (this.world.getBlockState(p).getBlock() == Blocks.COBWEB) this.world.setBlockState(p, Blocks.AIR.getDefaultState(), 3);
            }
        }

        private void updateCombat() {
            LivingEntity target = this.getAttackTarget();
            if (target != null && !target.isAlive()) { this.setAttackTarget(null); target = null; }
            if (this.rand.nextInt(200) == 0) { this.setAttackTarget(null); target = null; }
            if (target == null) target = findSomethingToAttack();
            if (target == null) { this.foundMob = false; setAttacking(0); return; }
            this.foundMob = true;
            this.getLookController().setLookPositionWithEntity(target, 10.0F, 10.0F);
            double reach = 5.0F + target.getWidth() / 2.0F;
            if (this.getDistanceSq(target) < reach * reach) {
                setAttacking(1);
                if (this.rand.nextInt(3) == 0 || this.rand.nextInt(4) == 1) this.attackEntityAsMob(target);
            } else {
                setAttacking(0);
                this.getNavigator().tryMoveToEntityLiving(target, 1.25D);
                if (this.rand.nextInt(4) == 0 && this.world.getGameRules().getBoolean(net.minecraft.world.GameRules.MOB_GRIEFING)) {
                    int x = MathHelper.floor(target.getPosX() + (this.rand.nextFloat() - this.rand.nextFloat()) * 2.0D);
                    int z = MathHelper.floor(target.getPosZ() + (this.rand.nextFloat() - this.rand.nextFloat()) * 2.0D);
                    for (int y = 2; y > -2; y--) {
                        BlockPos p = new BlockPos(x, MathHelper.floor(target.getPosY()) + y + 1, z);
                        if (this.world.isAirBlock(p) && !this.world.isAirBlock(p.down())) {
                            this.world.setBlockState(p, Blocks.COBWEB.getDefaultState(), 3); break;
                        }
                    }
                }
            }
        }

        private LivingEntity findSomethingToAttack() {
            if (OreSpawnLogic.playNicely != 0) return null;
            LivingEntity found = OreSpawnLogic.nearestTarget(this,
                this.getBoundingBox().grow(20.0D, 8.0D, 20.0D), e -> {
                    if (e instanceof CustomEntity || !myCanSee(e)) return false;
                    if (e instanceof PlayerEntity) return !((PlayerEntity)e).abilities.isCreativeMode;
                    return OreSpawnLogic.isAttackableNonMob(e);
                });
            this.setAttackTarget(found);
            return found;
        }

        private boolean myCanSee(LivingEntity target) {
            double xzoff = 2.5D;
            double x = this.getPosX() - xzoff * Math.sin(Math.toRadians(this.rotationYaw));
            double y = this.getPosY() + 3.0D;
            double z = this.getPosZ() + xzoff * Math.cos(Math.toRadians(this.rotationYaw));
            double dx = target.getPosX() - x;
            double dy = target.getPosY() + target.getHeight() / 2.0D - y;
            double dz = target.getPosZ() - z;
            int steps = Math.max(10, (int)Math.ceil(Math.max(Math.abs(dx), Math.max(Math.abs(dy), Math.abs(dz)))) * 10);
            for (int i = 1; i <= steps; i++) {
                BlockState state = this.world.getBlockState(new BlockPos(
                    x + dx * i / steps, y + dy * i / steps, z + dz * i / steps));
                if (!state.isAir() && state.getBlock() != Blocks.COBWEB && state.getBlock() != Blocks.GRASS
                        && !state.isIn(BlockTags.LEAVES)) return false;
            }
            return true;
        }

        private boolean isEdibleTree(BlockState state) {
            if (state.isIn(BlockTags.LEAVES) || state.isIn(BlockTags.LOGS)
                    || state.getBlock() == Blocks.VINE) return true;
            net.minecraft.block.Block block = state.getBlock();
            return block == OreSpawnLogic.blockOrNull("duplicator_tree_log", "duplicator_tree")
                || block == OreSpawnLogic.blockOrNull("apple_leaves")
                || block == OreSpawnLogic.blockOrNull("experience_leaves")
                || block == OreSpawnLogic.blockOrNull("scary_leaves")
                || block == OreSpawnLogic.blockOrNull("peach_leaves")
                || block == OreSpawnLogic.blockOrNull("cherry_leaves");
        }

        private void eatTreePart() {
            BlockPos found = OreLoot.scanShell(this.world, this.getPosition().up(), 12, 9, 12,
                (w, p) -> isEdibleTree(w.getBlockState(p)));
            if (found == null) return;
            if (!this.foundMob) this.getNavigator().tryMoveToXYZ(found.getX(), found.getY(), found.getZ(), 1.0D);
            if (this.getDistanceSq(found.getX() + 0.5D, found.getY() + 0.5D, found.getZ() + 0.5D) < 81.0D) {
                if (this.world.getGameRules().getBoolean(net.minecraft.world.GameRules.MOB_GRIEFING)
                        && ForgeHooks.canEntityDestroy(this.world, found, this)) this.world.destroyBlock(found, false, this);
                this.heal(2.0F);
                if (this.rand.nextInt(20) == 1) this.playSound(SoundEvents.ENTITY_PLAYER_BURP, 1.0F,
                    this.rand.nextFloat() * 0.2F + 0.9F);
            }
        }

        @Override public boolean attackEntityAsMob(Entity target) {
            if (!super.attackEntityAsMob(target)) return false;
            if (target instanceof LivingEntity) {
                double strength = 1.2D;
                double lift = target instanceof PlayerEntity ? 0.2D : 0.1D;
                float angle = (float)Math.atan2(target.getPosZ() - this.getPosZ(), target.getPosX() - this.getPosX());
                target.addVelocity(Math.cos(angle) * strength, lift, Math.sin(angle) * strength);
            }
            return true;
        }

        @Override protected void dropSpecialItems(DamageSource source, int looting, boolean recentlyHit) {
            super.dropSpecialItems(source, looting, recentlyHit);
            OreSpawnLogic.drop(this.world, this, OreSpawnLogic.item("caterkiller_jaw", "cater_killer_jaw"), 1, 5);
            OreSpawnLogic.drop(this.world, this, Items.ITEM_FRAME, 1, 5);
            for (int i = 0; i < 10; i++) OreSpawnLogic.drop(this.world, this, Items.LEATHER, 1, 5);
            for (int i = 0; i < 6; i++) OreSpawnLogic.drop(this.world, this, Items.BEEF, 1, 5);
            for (int i = 0, rolls = 1 + this.rand.nextInt(5); i < rolls; i++) dropTreasure(this.rand.nextInt(20));
            for (int i = 0; i < 25; i++) OreSpawnLogic.spawn(this.world, "butterfly",
                this.getPosX(), this.getPosY() + 1.0D, this.getPosZ());
        }

        private ItemStack dropNamed(String... names) {
            return OreSpawnLogic.drop(this.world, this, OreSpawnLogic.item(names), 1, 5);
        }

        private void dropTreasure(int roll) {
            ItemStack stack;
            switch (roll) {
                case 0: dropNamed("ultimate_sword"); break;
                case 1: dropNamed("ruby"); break;
                case 2: OreSpawnLogic.drop(this.world, this, Blocks.DIAMOND_BLOCK.asItem(), 1, 5); break;
                case 3: stack = dropNamed("ruby_sword"); if (!stack.isEmpty()) OreLoot.sword(this.rand, stack); break;
                case 4: stack = dropNamed("ruby_shovel"); if (!stack.isEmpty()) OreLoot.shovelOrHoeOrAxe(this.rand, stack); break;
                case 5: stack = dropNamed("ruby_pickaxe"); if (!stack.isEmpty()) OreLoot.pickaxe(this.rand, stack); break;
                case 6: stack = dropNamed("ruby_axe"); if (!stack.isEmpty()) OreLoot.shovelOrHoeOrAxe(this.rand, stack); break;
                case 7: stack = dropNamed("ruby_hoe"); if (!stack.isEmpty()) OreLoot.shovelOrHoeOrAxe(this.rand, stack); break;
                case 8: stack = dropNamed("ruby_helmet"); if (!stack.isEmpty()) OreLoot.helmet(this.rand, stack); break;
                case 9: stack = dropNamed("ruby_chestplate", "ruby_body"); if (!stack.isEmpty()) OreLoot.chestOrLegs(this.rand, stack); break;
                case 10: stack = dropNamed("ruby_leggings", "ruby_legs"); if (!stack.isEmpty()) OreLoot.chestOrLegs(this.rand, stack); break;
                case 11: stack = dropNamed("ruby_boots"); if (!stack.isEmpty()) OreLoot.boots(this.rand, stack); break;
                case 12: dropNamed("ultimate_bow"); break;
                default: break;
            }
        }

        @Override protected SoundEvent getAmbientSound() {
            return this.rand.nextInt(3) == 0
                ? OreSpawnLogic.sound("caterkiller_living", SoundEvents.ENTITY_SILVERFISH_AMBIENT) : null;
        }
        @Override protected SoundEvent getHurtSound(DamageSource source) {
            return OreSpawnLogic.sound("caterkiller_hit", SoundEvents.ENTITY_SILVERFISH_HURT);
        }
        @Override protected SoundEvent getDeathSound() {
            return OreSpawnLogic.sound("caterkiller_death", SoundEvents.ENTITY_SILVERFISH_DEATH);
        }
        @Override protected float getSoundVolume() { return 1.5F; }
    }
}
