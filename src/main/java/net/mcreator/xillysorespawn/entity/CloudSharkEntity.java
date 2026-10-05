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
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.network.IPacket;
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
import net.mcreator.xillysorespawn.entity.renderer.CloudSharkRenderer;

@XillysOrespawnModElements.ModElement.Tag
public class CloudSharkEntity extends XillysOrespawnModElements.ModElement {
    public static EntityType entity = (EntityType.Builder.<CustomEntity>create(CustomEntity::new, EntityClassification.MONSTER)
        .setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3)
        .setCustomClientFactory(CustomEntity::new).size(1.0F, 0.75F))
        .build("cloud_shark").setRegistryName("cloud_shark");

    public CloudSharkEntity(XillysOrespawnModElements instance) {
        super(instance, 9);
        FMLJavaModLoadingContext.get().getModEventBus().register(new CloudSharkRenderer.ModelRegisterHandler());
        FMLJavaModLoadingContext.get().getModEventBus().register(new EntityAttributesRegisterHandler());
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override public void initElements() {
        elements.entities.add(() -> entity);
        elements.items.add(() -> new net.minecraft.item.SpawnEggItem(entity, -1, -1,
            new net.minecraft.item.Item.Properties().group(net.minecraft.item.ItemGroup.MISC))
            .setRegistryName("cloud_shark_spawn_egg"));
    }

    @SubscribeEvent public void addFeatureToBiomes(BiomeLoadingEvent event) {
        if (!OreSpawnLogic.allowNaturalSpawn(event, "cloud_shark")) return;
        event.getSpawns().getSpawner(EntityClassification.MONSTER)
            .add(new MobSpawnInfo.Spawners(entity, 5, 1, 2));
    }

    @Override public void init(FMLCommonSetupEvent event) {
        EntitySpawnPlacementRegistry.register(entity, EntitySpawnPlacementRegistry.PlacementType.NO_RESTRICTIONS,
            Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, CloudSharkEntity::canSpawn);
    }

    public static boolean canSpawn(EntityType<? extends MonsterEntity> type, IServerWorld world,
            SpawnReason reason, BlockPos pos, Random random) {
        return true;
    }

    public static class EntityAttributesRegisterHandler {
        @SubscribeEvent public void onEntityAttributeCreation(EntityAttributeCreationEvent event) {
            event.put(entity, MobEntity.func_233666_p_()
                .createMutableAttribute(Attributes.MAX_HEALTH, 15.0D)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.3D)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 6.0D)
                .createMutableAttribute(Attributes.ARMOR, 5.0D)
                .createMutableAttribute(Attributes.FOLLOW_RANGE, 24.0D).create());
        }
    }

    public static class CustomEntity extends MonsterEntity {
        private BlockPos flightTarget;

        public CustomEntity(FMLPlayMessages.SpawnEntity packet, World world) { this(entity, world); }
        public CustomEntity(EntityType<? extends CustomEntity> type, World world) {
            super(type, world);
            this.experienceValue = 5;
            this.setNoGravity(true);
        }

        @Override public IPacket<?> createSpawnPacket() { return NetworkHooks.getEntitySpawningPacket(this); }
        public boolean isChildModel() { return false; }
        @Override protected void registerGoals() { super.registerGoals(); }

        private boolean isOriginalTarget(LivingEntity target) {
            if (!this.getEntitySenses().canSee(target) || OreSpawnLogic.isNamed(target, "rock", "rock_base", "ant", "entity_ant")) {
                return false;
            }
            if (target instanceof PlayerEntity) return !((PlayerEntity) target).abilities.isCreativeMode;
            return OreSpawnLogic.isNamed(target, "butterfly", "cockateil", "mosquito", "entity_mosquito",
                "firefly", "gold_fish", "goldfish", "cliff_racer");
        }

        private void chooseFlightTarget() {
            BlockPos origin = this.getPosition();
            int upDown = this.getPosY() < 120.0D ? 2 : this.getPosY() > 140.0D ? -2 : 0;
            for (int tries = 50; tries > 0; --tries) {
                int x = this.rand.nextInt(10) + 8;
                int z = this.rand.nextInt(10) + 8;
                if (this.rand.nextBoolean()) x = -x;
                if (this.rand.nextBoolean()) z = -z;
                BlockPos candidate = origin.add(x, this.rand.nextInt(5) - 2 + upDown, z);
                if (OreSpawnLogic.isAirTarget(this.world, candidate)
                        && OreSpawnLogic.hasClearPath(this, candidate.getX(), candidate.getY(), candidate.getZ())) {
                    this.flightTarget = candidate;
                    return;
                }
            }
        }

        private void tickOriginalFlight() {
            if (this.flightTarget == null) this.flightTarget = this.getPosition();
            if (this.rand.nextInt(300) == 0 || this.getDistanceSq(Vector3d.copyCentered(this.flightTarget)) < 2.1D) {
                this.chooseFlightTarget();
            }
            if (this.rand.nextInt(9) == 2 && OreSpawnLogic.playNicely == 0) {
                LivingEntity target = OreSpawnLogic.nearestTarget(this, this.getBoundingBox().grow(12.0D, 10.0D, 12.0D),
                    this::isOriginalTarget);
                if (target != null) {
                    this.flightTarget = target.getPosition();
                    if (this.getDistanceSq(target) < 9.0D) this.attackEntityAsMob(target);
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
            if (!this.world.isRemote) this.tickOriginalFlight();
        }

        @Override public boolean attackEntityFrom(DamageSource source, float amount) {
            boolean damaged = super.attackEntityFrom(source, amount);
            Entity attacker = source.getTrueSource();
            if (damaged && attacker != null) this.flightTarget = attacker.getPosition();
            return damaged;
        }

        @Override public boolean onLivingFall(float distance, float multiplier) { return false; }

        @Override protected SoundEvent getAmbientSound() { return SoundEvents.ENTITY_GENERIC_SPLASH; }
        @Override protected SoundEvent getHurtSound(DamageSource source) {
            return OreSpawnLogic.sound("little_splat", SoundEvents.ENTITY_SLIME_HURT);
        }
        @Override protected SoundEvent getDeathSound() {
            return OreSpawnLogic.sound("big_splat", SoundEvents.ENTITY_SLIME_DEATH);
        }
        @Override protected float getSoundVolume() { return 0.25F; }

        @Override protected void dropSpecialItems(DamageSource source, int looting, boolean recentlyHit) {
            super.dropSpecialItems(source, looting, recentlyHit);
            int count = this.rand.nextInt(3);
            if (looting > 0) count += this.rand.nextInt(looting + 1);
            for (int i = 0; i < count; i++) {
                int roll = this.rand.nextInt(3);
                Item drop = roll == 0 ? Items.PAPER : roll == 1 ? Items.STRING : Items.BONE;
                this.entityDropItem(drop);
            }
        }
    }
}
