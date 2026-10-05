package net.mcreator.xillysorespawn.entity;

import java.util.Random;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.fml.network.NetworkHooks;

import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.Heightmap;

import net.mcreator.xillysorespawn.XillysOrespawnModElements;
import net.mcreator.xillysorespawn.entity.renderer.DungeonBeastRenderer;
import net.mcreator.xillysorespawn.entity.renderer.RenderInfo;

@XillysOrespawnModElements.ModElement.Tag
public class DungeonBeastEntity extends XillysOrespawnModElements.ModElement {
    public static EntityType entity = (EntityType.Builder.<CustomEntity>create(CustomEntity::new, EntityClassification.MONSTER)
        .setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3)
        .setCustomClientFactory(CustomEntity::new).size(1.15F, 1.1F))
        .build("dungeon_beast").setRegistryName("dungeon_beast");

    public DungeonBeastEntity(XillysOrespawnModElements instance) {
        super(instance, 18);
        FMLJavaModLoadingContext.get().getModEventBus().register(new DungeonBeastRenderer.ModelRegisterHandler());
        FMLJavaModLoadingContext.get().getModEventBus().register(new EntityAttributesRegisterHandler());
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public void initElements() {
        elements.entities.add(() -> entity);
        elements.items.add(() -> new net.minecraft.item.SpawnEggItem(entity, -1, -1,
            new net.minecraft.item.Item.Properties().group(net.minecraft.item.ItemGroup.MISC))
            .setRegistryName("dungeon_beast_spawn_egg"));
    }

    @SubscribeEvent
    public void addFeatureToBiomes(BiomeLoadingEvent event) {
        if (!OreSpawnLogic.allowNaturalSpawn(event, "dungeon_beast")) return;
        event.getSpawns().getSpawner(EntityClassification.MONSTER)
            .add(new MobSpawnInfo.Spawners(entity, 4, 1, 2));
    }

    @Override
    public void init(FMLCommonSetupEvent event) {
        EntitySpawnPlacementRegistry.register(entity, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND,
            Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DungeonBeastEntity::canSpawn);
    }

    public static boolean canSpawn(EntityType<? extends MonsterEntity> type, IServerWorld world,
            SpawnReason reason, BlockPos pos, Random random) {
        if (reason == SpawnReason.SPAWNER || reason == SpawnReason.SPAWN_EGG || reason == SpawnReason.COMMAND) {
            return true;
        }
        if (!MonsterEntity.canMonsterSpawnInLight(type, world, reason, pos, random)) {
            return false;
        }
        if (!OreSpawnLogic.isCrystalDimension(world.getWorld())) {
            return true;
        }
        if (pos.getY() < 25 || pos.getY() > 28) {
            return false;
        }
        int air = 0;
        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                if (world.getWorld().isAirBlock(pos.add(x, 1, z))) air++;
            }
        }
        return air >= 6;
    }

    public static class EntityAttributesRegisterHandler {
        @SubscribeEvent
        public void onEntityAttributeCreation(EntityAttributeCreationEvent event) {
            event.put(entity, MobEntity.func_233666_p_()
                .createMutableAttribute(Attributes.MAX_HEALTH, 65.0D)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.29D)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 12.0D)
                .createMutableAttribute(Attributes.ARMOR, 6.0D)
                .createMutableAttribute(Attributes.FOLLOW_RANGE, 24.0D).create());
        }
    }

    public static class CustomEntity extends MonsterEntity {
        private static final DataParameter<Byte> ATTACKING = EntityDataManager.createKey(CustomEntity.class, DataSerializers.BYTE);
        private final Random modelRandom = new Random();
        private final RenderInfo renderInfo = new RenderInfo();

        public CustomEntity(FMLPlayMessages.SpawnEntity packet, World world) {
            this(entity, world);
        }

        public CustomEntity(EntityType<? extends CustomEntity> type, World world) {
            super(type, world);
            this.experienceValue = 60;
        }

        @Override
        public IPacket<?> createSpawnPacket() {
            return NetworkHooks.getEntitySpawningPacket(this);
        }

        @Override
        protected void registerData() {
            super.registerData();
            this.dataManager.register(ATTACKING, (byte) 0);
        }

        public int getAttacking() {
            return this.dataManager.get(ATTACKING);
        }

        private void setAttacking(int value) {
            this.dataManager.set(ATTACKING, (byte) value);
        }

        public RenderInfo getRenderInfo() {
            return this.renderInfo;
        }

        public void setRenderInfo(RenderInfo state) {
            this.renderInfo.rf1 = state.rf1;
            this.renderInfo.rf2 = state.rf2;
            this.renderInfo.rf3 = state.rf3;
            this.renderInfo.rf4 = state.rf4;
            this.renderInfo.ri1 = state.ri1;
            this.renderInfo.ri2 = state.ri2;
            this.renderInfo.ri3 = state.ri3;
            this.renderInfo.ri4 = state.ri4;
        }

        public Random getModelRandom() {
            return this.modelRandom;
        }

        public boolean isChildModel() {
            return false;
        }

        @Override
        protected void registerGoals() {
            super.registerGoals();
            this.goalSelector.addGoal(0, new SwimGoal(this));
            this.goalSelector.addGoal(1, new OreSpawnWanderGoal(this, 14, 30, 1.0D, false));
            this.goalSelector.addGoal(2, new LookAtGoal(this, PlayerEntity.class, 8.0F));
            this.goalSelector.addGoal(3, new LookRandomlyGoal(this));
            this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        }

        private boolean isOriginalTarget(LivingEntity target) {
            if (OreSpawnLogic.isIgnoreable(target) || !this.getEntitySenses().canSee(target)) return false;
            if (OreSpawnLogic.isNamed(target, "rat", "dungeon_beast", "rotator", "peacock", "irukandji",
                    "skate", "whale", "flounder")) return false;
            return !(target instanceof PlayerEntity) || !((PlayerEntity) target).abilities.isCreativeMode;
        }

        private void tickOriginalCombat() {
            if (this.rand.nextInt(8) != 0 || OreSpawnLogic.playNicely != 0) return;
            LivingEntity target = OreSpawnLogic.nearestTarget(this, this.getBoundingBox().grow(16.0D, 3.0D, 16.0D),
                this::isOriginalTarget);
            if (target == null) {
                this.setAttacking(0);
            } else if (this.getDistanceSq(target) < 8.0D) {
                this.setAttacking(1);
                if (this.rand.nextInt(7) == 0 || this.rand.nextInt(8) == 1) this.attackEntityAsMob(target);
            } else {
                this.getNavigator().tryMoveToEntityLiving(target, 1.2D);
            }
        }

        @Override
        public void livingTick() {
            super.livingTick();
            if (!this.world.isRemote) this.tickOriginalCombat();
        }

        @Override
        public boolean attackEntityFrom(DamageSource source, float amount) {
            String type = source.getDamageType();
            return source == DamageSource.CACTUS || "cactus".equals(type) || "inWall".equals(type)
                ? false : super.attackEntityFrom(source, amount);
        }

        @Override
        public ActionResultType func_230254_b_(PlayerEntity player, Hand hand) {
            return ActionResultType.PASS;
        }

        @Override
        protected SoundEvent getAmbientSound() {
            return null;
        }

        @Override
        protected SoundEvent getHurtSound(DamageSource source) {
            return OreSpawnLogic.sound("dbhit", SoundEvents.ENTITY_RAVAGER_HURT);
        }

        @Override
        protected SoundEvent getDeathSound() {
            return OreSpawnLogic.sound("dbdead", SoundEvents.ENTITY_RAVAGER_DEATH);
        }

        @Override
        protected float getSoundVolume() {
            return 0.8F;
        }

        @Override
        protected void dropSpecialItems(DamageSource source, int looting, boolean recentlyHit) {
            super.dropSpecialItems(source, looting, recentlyHit);
            int count = this.rand.nextInt(3);
            if (looting > 0) count += this.rand.nextInt(looting + 1);
            for (int i = 0; i < count; i++) {
                int roll = this.rand.nextInt(4);
                Item drop = roll == 1 ? OreSpawnLogic.item("crystal_pink_ingot", "pink_tourmaline_ingot")
                    : roll == 2 ? OreSpawnLogic.item("crystal_apple", "my_crystal_apple")
                    : roll == 3 ? Blocks.OAK_LOG.asItem() : Items.AIR;
                OreSpawnLogic.drop(this.world, this, drop, 1, 0);
            }
        }
    }
}
