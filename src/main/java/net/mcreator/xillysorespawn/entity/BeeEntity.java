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

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.block.Blocks;
import net.minecraft.item.Items;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.Heightmap;

import net.mcreator.xillysorespawn.XillysOrespawnModElements;
import net.mcreator.xillysorespawn.entity.renderer.BeeRenderer;

@XillysOrespawnModElements.ModElement.Tag
public class BeeEntity extends XillysOrespawnModElements.ModElement {
    public static EntityType entity = (EntityType.Builder.<CustomEntity>create(CustomEntity::new, EntityClassification.MONSTER)
        .setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3)
        .setCustomClientFactory(CustomEntity::new).size(1.5F, 2.5F))
        .build("bee").setRegistryName("bee");

    public BeeEntity(XillysOrespawnModElements instance) {
        super(instance, 1);
        FMLJavaModLoadingContext.get().getModEventBus().register(new BeeRenderer.ModelRegisterHandler());
        FMLJavaModLoadingContext.get().getModEventBus().register(new EntityAttributesRegisterHandler());
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override public void initElements() {
        elements.entities.add(() -> entity);
        elements.items.add(() -> new net.minecraft.item.SpawnEggItem(entity, -1, -1,
            new net.minecraft.item.Item.Properties().group(net.minecraft.item.ItemGroup.MISC))
            .setRegistryName("bee_spawn_egg"));
    }

    @SubscribeEvent public void addFeatureToBiomes(BiomeLoadingEvent event) {
        if (!OreSpawnLogic.allowNaturalSpawn(event, "bee")) return;
        event.getSpawns().getSpawner(EntityClassification.MONSTER)
            .add(new MobSpawnInfo.Spawners(entity, 5, 1, 2));
    }

    @Override public void init(FMLCommonSetupEvent event) {
        EntitySpawnPlacementRegistry.register(entity, EntitySpawnPlacementRegistry.PlacementType.NO_RESTRICTIONS,
            Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, BeeEntity::canSpawn);
    }

    public static boolean canSpawn(EntityType<? extends MonsterEntity> type, IServerWorld world,
            SpawnReason reason, BlockPos pos, Random random) {
        if (reason == SpawnReason.SPAWNER || reason == SpawnReason.SPAWN_EGG || reason == SpawnReason.COMMAND) return true;
        if (OreSpawnLogic.isIslandsDimension(world.getWorld())) return true;
        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                for (int y = 1; y < 5; y++) {
                    if (!world.getWorld().isAirBlock(pos.add(x, y, z))) return false;
                }
            }
        }
        return pos.getY() >= 50 && world.getWorld().isDaytime();
    }

    public static class EntityAttributesRegisterHandler {
        @SubscribeEvent public void onEntityAttributeCreation(EntityAttributeCreationEvent event) {
            event.put(entity, MobEntity.func_233666_p_()
                .createMutableAttribute(Attributes.MAX_HEALTH, 80.0D)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.32D)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 12.0D)
                .createMutableAttribute(Attributes.ARMOR, 5.0D)
                .createMutableAttribute(Attributes.FOLLOW_RANGE, 24.0D).create());
        }
    }

    public static class CustomEntity extends MonsterEntity {
        private static final DataParameter<Byte> ATTACKING = EntityDataManager.createKey(CustomEntity.class, DataSerializers.BYTE);
        private BlockPos flightTarget;
        private int stuckTicks;
        private int lastX;
        private int lastZ;
        private Entity revengeFlightTarget;

        public CustomEntity(FMLPlayMessages.SpawnEntity packet, World world) { this(entity, world); }
        public CustomEntity(EntityType<? extends CustomEntity> type, World world) {
            super(type, world);
            this.experienceValue = 25;
            this.setNoGravity(true);
        }

        @Override public IPacket<?> createSpawnPacket() { return NetworkHooks.getEntitySpawningPacket(this); }

        @Override protected void registerData() {
            super.registerData();
            this.dataManager.register(ATTACKING, (byte) 0);
        }

        public boolean isAttacking() { return this.dataManager.get(ATTACKING) != 0; }
        private void setAttacking(boolean value) { this.dataManager.set(ATTACKING, value ? (byte) 1 : (byte) 0); }
        @Override protected void registerGoals() { super.registerGoals(); }

        private boolean isOriginalTarget(LivingEntity target) {
            if (!this.getEntitySenses().canSee(target) || target.isInWater()) return false;
            if (target instanceof PlayerEntity) return !((PlayerEntity) target).abilities.isCreativeMode;
            return target instanceof VillagerEntity || OreSpawnLogic.isNamed(target, "girlfriend", "boyfriend");
        }

        private void chooseFlightTarget() {
            BlockPos origin = this.getPosition();
            for (int tries = 50; tries > 0; --tries) {
                int x = this.rand.nextInt(9) + 4;
                int z = this.rand.nextInt(9) + 4;
                if (this.rand.nextBoolean()) x = -x;
                if (this.rand.nextBoolean()) z = -z;
                BlockPos candidate = origin.add(x, this.rand.nextInt(6) - 3, z);
                if (OreSpawnLogic.isAirTarget(this.world, candidate)
                        && OreSpawnLogic.hasClearPath(this, candidate.getX(), candidate.getY(), candidate.getZ())) {
                    this.flightTarget = candidate;
                    return;
                }
            }
        }

        private void tickOriginalFlight() {
            int x = MathHelper.floor(this.getPosX());
            int z = MathHelper.floor(this.getPosZ());
            if (x == this.lastX && z == this.lastZ) this.stuckTicks++;
            else {
                this.stuckTicks = 0;
                this.lastX = x;
                this.lastZ = z;
            }
            if (this.flightTarget == null) this.flightTarget = this.getPosition();
            double distance = this.getDistanceSq(Vector3d.copyCentered(this.flightTarget));
            if (this.stuckTicks > 50 || this.rand.nextInt(300) == 0 || distance < 2.1D) {
                this.stuckTicks = 0;
                this.chooseFlightTarget();
            } else if (this.rand.nextInt(15) == 0) {
                LivingEntity target = this.revengeFlightTarget instanceof LivingEntity && this.revengeFlightTarget.isAlive()
                    ? (LivingEntity) this.revengeFlightTarget : null;
                if (target == null && OreSpawnLogic.playNicely == 0) {
                    target = OreSpawnLogic.nearestTarget(this, this.getBoundingBox().grow(10.0D, 6.0D, 10.0D),
                        this::isOriginalTarget);
                }
                if (target != null) {
                    this.setAttacking(true);
                    this.flightTarget = target.getPosition().up();
                    if (this.getDistanceSq(target) < 16.0D) this.attackEntityAsMob(target);
                } else {
                    this.setAttacking(false);
                }
            }
            if (this.flightTarget == null) return;
            double dx = this.flightTarget.getX() + 0.5D - this.getPosX();
            double dy = this.flightTarget.getY() + 0.1D - this.getPosY();
            double dz = this.flightTarget.getZ() + 0.5D - this.getPosZ();
            Vector3d motion = this.getMotion();
            this.setMotion(motion.x + (Math.signum(dx) * 0.5D - motion.x) * 0.30000000149011613D,
                motion.y + (Math.signum(dy) * 0.7D - motion.y) * 0.20000000149011612D,
                motion.z + (Math.signum(dz) * 0.5D - motion.z) * 0.30000000149011613D);
            float yaw = (float) (Math.atan2(this.getMotion().z, this.getMotion().x) * 180.0D / Math.PI) - 90.0F;
            this.rotationYaw += MathHelper.wrapDegrees(yaw - this.rotationYaw) / 4.0F;
            this.renderYawOffset = this.rotationYaw;
        }

        @Override public void livingTick() {
            super.livingTick();
            this.setNoGravity(true);
            Vector3d motion = this.getMotion();
            this.setMotion(motion.x, motion.y * 0.6D, motion.z);
            if (!this.world.isRemote) {
                if (this.isInWater() && this.rand.nextInt(4) == 1) this.attackEntityAsMob(this);
                this.tickOriginalFlight();
            }
        }

        @Override public boolean attackEntityAsMob(Entity target) {
            boolean hit = target.attackEntityFrom(DamageSource.causeMobDamage(this), 12.0F);
            if (this.rand.nextInt(3) == 1 && target instanceof LivingEntity) {
                ((LivingEntity) target).addPotionEffect(new EffectInstance(Effects.POISON, 50, 0));
            }
            return hit;
        }

        @Override public boolean attackEntityFrom(DamageSource source, float amount) {
            boolean hit = super.attackEntityFrom(source, amount);
            Entity attacker = source.getTrueSource();
            if (attacker instanceof LivingEntity) {
                this.revengeFlightTarget = attacker;
                this.flightTarget = attacker.getPosition();
            }
            return hit;
        }

        @Override public boolean onLivingFall(float distance, float multiplier) { return false; }

        @Override protected SoundEvent getAmbientSound() {
            return OreSpawnLogic.sound("beebuzz", SoundEvents.ENTITY_BEE_LOOP);
        }
        @Override protected SoundEvent getHurtSound(DamageSource source) {
            return OreSpawnLogic.sound("dragonfly_hurt", SoundEvents.ENTITY_BEE_HURT);
        }
        @Override protected SoundEvent getDeathSound() {
            return OreSpawnLogic.sound("alo_death", SoundEvents.ENTITY_BEE_DEATH);
        }
        @Override protected float getSoundVolume() { return 0.25F; }

        @Override protected void dropSpecialItems(DamageSource source, int looting, boolean recentlyHit) {
            super.dropSpecialItems(source, looting, recentlyHit);
            int count = 2 + this.rand.nextInt(10);
            for (int i = 0; i < count; i++) OreSpawnLogic.drop(this.world, this, Items.GOLD_NUGGET, 1, 4);
            count = 2 + this.rand.nextInt(10);
            for (int i = 0; i < count; i++) OreSpawnLogic.drop(this.world, this,
                OreSpawnLogic.item("butter_candy"), 1, 4);
            count = 2 + this.rand.nextInt(10);
            for (int i = 0; i < count; i++) OreSpawnLogic.drop(this.world, this, Blocks.DANDELION.asItem(), 1, 4);
            count = 2 + this.rand.nextInt(10);
            for (int i = 0; i < count; i++) OreSpawnLogic.drop(this.world, this, Items.SUGAR, 1, 4);
        }
    }
}
