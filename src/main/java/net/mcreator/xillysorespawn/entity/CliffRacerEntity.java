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

import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.passive.AnimalEntity;
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
import net.minecraft.world.server.ServerWorld;

import net.mcreator.xillysorespawn.XillysOrespawnModElements;
import net.mcreator.xillysorespawn.entity.renderer.CliffRacerRenderer;

@XillysOrespawnModElements.ModElement.Tag
public class CliffRacerEntity extends XillysOrespawnModElements.ModElement {
    public static EntityType entity = (EntityType.Builder.<CustomEntity>create(CustomEntity::new, EntityClassification.CREATURE)
        .setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3)
        .setCustomClientFactory(CustomEntity::new).size(0.75F, 0.5F))
        .build("cliff_racer").setRegistryName("cliff_racer");

    public CliffRacerEntity(XillysOrespawnModElements instance) {
        super(instance, 8);
        FMLJavaModLoadingContext.get().getModEventBus().register(new CliffRacerRenderer.ModelRegisterHandler());
        FMLJavaModLoadingContext.get().getModEventBus().register(new EntityAttributesRegisterHandler());
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public void initElements() {
        elements.entities.add(() -> entity);
        elements.items.add(() -> new net.minecraft.item.SpawnEggItem(entity, -1, -1,
            new net.minecraft.item.Item.Properties().group(net.minecraft.item.ItemGroup.MISC))
            .setRegistryName("cliff_racer_spawn_egg"));
    }

    @SubscribeEvent
    public void addFeatureToBiomes(BiomeLoadingEvent event) {
        if (!OreSpawnLogic.allowNaturalSpawn(event, "cliff_racer")) return;
        event.getSpawns().getSpawner(EntityClassification.CREATURE)
            .add(new MobSpawnInfo.Spawners(entity, 8, 1, 3));
    }

    @Override
    public void init(FMLCommonSetupEvent event) {
        EntitySpawnPlacementRegistry.register(entity, EntitySpawnPlacementRegistry.PlacementType.NO_RESTRICTIONS,
            Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, CliffRacerEntity::canSpawn);
    }

    public static boolean canSpawn(EntityType<? extends AnimalEntity> type, IServerWorld world,
            SpawnReason reason, BlockPos pos, Random random) {
        return reason == SpawnReason.SPAWNER || reason == SpawnReason.SPAWN_EGG
            || reason == SpawnReason.COMMAND || pos.getY() >= 50;
    }

    public static class EntityAttributesRegisterHandler {
        @SubscribeEvent
        public void onEntityAttributeCreation(EntityAttributeCreationEvent event) {
            event.put(entity, MobEntity.func_233666_p_()
                .createMutableAttribute(Attributes.MAX_HEALTH, 5.0D)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.33D)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 1.0D)
                .createMutableAttribute(Attributes.FOLLOW_RANGE, 16.0D).create());
        }
    }

    public static class CustomEntity extends AnimalEntity {
        private BlockPos flightTarget;

        public CustomEntity(FMLPlayMessages.SpawnEntity packet, World world) {
            this(entity, world);
        }

        public CustomEntity(EntityType<? extends CustomEntity> type, World world) {
            super(type, world);
            this.experienceValue = 5;
            this.setNoGravity(true);
        }

        @Override
        public IPacket<?> createSpawnPacket() {
            return NetworkHooks.getEntitySpawningPacket(this);
        }

        @Override
        protected void registerGoals() {
            super.registerGoals();
        }

        private void chooseFlightTarget() {
            BlockPos origin = this.getPosition();
            for (int tries = 50; tries > 0; --tries) {
                int x = this.rand.nextInt(10) + 5;
                int z = this.rand.nextInt(10) + 5;
                if (this.rand.nextBoolean()) x = -x;
                if (this.rand.nextBoolean()) z = -z;
                BlockPos candidate = origin.add(x, this.rand.nextInt(11) - 5, z);
                if (OreSpawnLogic.isAirTarget(this.world, candidate)
                        && OreSpawnLogic.hasClearPath(this, candidate.getX(), candidate.getY(), candidate.getZ())) {
                    this.flightTarget = candidate;
                    return;
                }
            }
        }

        private void tickOriginalFlight() {
            if (this.flightTarget == null) {
                this.flightTarget = this.getPosition();
            }
            if (this.rand.nextInt(300) == 0 || this.getDistanceSq(Vector3d.copyCentered(this.flightTarget)) < 2.1D) {
                this.chooseFlightTarget();
            }
            if (this.flightTarget == null) {
                return;
            }
            double dx = this.flightTarget.getX() + 0.4D - this.getPosX();
            double dy = this.flightTarget.getY() + 0.1D - this.getPosY();
            double dz = this.flightTarget.getZ() + 0.4D - this.getPosZ();
            Vector3d motion = this.getMotion();
            this.setMotion(motion.x + (Math.signum(dx) * 0.4D - motion.x) * 0.3D,
                motion.y + (Math.signum(dy) * 0.7D - motion.y) * 0.2D,
                motion.z + (Math.signum(dz) * 0.4D - motion.z) * 0.3D);
            float wantedYaw = (float) (Math.atan2(this.getMotion().z, this.getMotion().x) * 180.0D / Math.PI) - 90.0F;
            this.rotationYaw += MathHelper.wrapDegrees(wantedYaw - this.rotationYaw) / 6.0F;
            this.renderYawOffset = this.rotationYaw;
        }

        @Override
        public void livingTick() {
            super.livingTick();
            this.setNoGravity(true);
            Vector3d motion = this.getMotion();
            this.setMotion(motion.x, motion.y * 0.6D, motion.z);
            if (!this.world.isRemote) {
                this.tickOriginalFlight();
            }
        }

        @Override
        public boolean onLivingFall(float distance, float damageMultiplier) {
            return false;
        }

        @Override
        protected SoundEvent getAmbientSound() {
            return OreSpawnLogic.sound("cliffracer", SoundEvents.ENTITY_PARROT_AMBIENT);
        }

        @Override
        protected SoundEvent getHurtSound(DamageSource source) {
            return null;
        }

        @Override
        protected SoundEvent getDeathSound() {
            return null;
        }

        @Override
        protected float getSoundVolume() {
            return 0.45F;
        }

        @Override
        protected void dropSpecialItems(DamageSource source, int looting, boolean recentlyHit) {
            super.dropSpecialItems(source, looting, recentlyHit);
            int count = this.rand.nextInt(3);
            if (looting > 0) {
                count += this.rand.nextInt(looting + 1);
            }
            for (int i = 0; i < count; i++) {
                int roll = this.rand.nextInt(8);
                Item drop = roll == 0 ? Items.FEATHER
                    : roll == 1 ? OreSpawnLogic.item("uranium_nugget")
                    : roll == 2 ? OreSpawnLogic.item("titanium_nugget") : Items.AIR;
                OreSpawnLogic.drop(this.world, this, drop, 1, 0);
            }
        }

        @Override
        public AgeableEntity func_241840_a(ServerWorld world, AgeableEntity mate) {
            return null;
        }
    }
}
