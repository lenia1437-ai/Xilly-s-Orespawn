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
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
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
import net.mcreator.xillysorespawn.entity.renderer.CockateilRenderer;

@XillysOrespawnModElements.ModElement.Tag
public class CockateilEntity extends XillysOrespawnModElements.ModElement {
    public static EntityType entity = (EntityType.Builder.<CustomEntity>create(CustomEntity::new, EntityClassification.CREATURE)
        .setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3)
        .setCustomClientFactory(CustomEntity::new).size(0.5F, 0.5F))
        .build("cockateil").setRegistryName("cockateil");

    public CockateilEntity(XillysOrespawnModElements instance) {
        super(instance, 10);
        FMLJavaModLoadingContext.get().getModEventBus().register(new CockateilRenderer.ModelRegisterHandler());
        FMLJavaModLoadingContext.get().getModEventBus().register(new EntityAttributesRegisterHandler());
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public void initElements() {
        elements.entities.add(() -> entity);
        elements.items.add(() -> new net.minecraft.item.SpawnEggItem(entity, -1, -1,
            new net.minecraft.item.Item.Properties().group(net.minecraft.item.ItemGroup.MISC))
            .setRegistryName("cockateil_spawn_egg"));
    }

    @SubscribeEvent
    public void addFeatureToBiomes(BiomeLoadingEvent event) {
        if (!OreSpawnLogic.allowNaturalSpawn(event, "cockateil")) return;
        event.getSpawns().getSpawner(EntityClassification.CREATURE)
            .add(new MobSpawnInfo.Spawners(entity, 10, 2, 5));
    }

    @Override
    public void init(FMLCommonSetupEvent event) {
        EntitySpawnPlacementRegistry.register(entity, EntitySpawnPlacementRegistry.PlacementType.NO_RESTRICTIONS,
            Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, CockateilEntity::canSpawn);
    }

    public static boolean canSpawn(EntityType<? extends AnimalEntity> type, IServerWorld world,
            SpawnReason reason, BlockPos pos, Random random) {
        if (reason == SpawnReason.SPAWNER || reason == SpawnReason.SPAWN_EGG || reason == SpawnReason.COMMAND) {
            return true;
        }
        if (!world.getWorld().isDaytime()) {
            return false;
        }
        return OreSpawnLogic.isIslandsDimension(world.getWorld()) || pos.getY() >= 50;
    }

    public static class EntityAttributesRegisterHandler {
        @SubscribeEvent
        public void onEntityAttributeCreation(EntityAttributeCreationEvent event) {
            event.put(entity, MobEntity.func_233666_p_()
                .createMutableAttribute(Attributes.MAX_HEALTH, 2.0D)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.33D)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 1.0D)
                .createMutableAttribute(Attributes.FOLLOW_RANGE, 16.0D).create());
        }
    }

    public static class CustomEntity extends AnimalEntity {
        private static final DataParameter<Integer> BIRD_TYPE = EntityDataManager.createKey(CustomEntity.class, DataSerializers.VARINT);
        private BlockPos flightTarget;
        private int stuckTicks;
        private int lastX;
        private int lastZ;
        private int flyUp;
        private boolean killedByPlayer;

        public CustomEntity(FMLPlayMessages.SpawnEntity packet, World world) {
            this(entity, world);
        }

        public CustomEntity(EntityType<? extends CustomEntity> type, World world) {
            super(type, world);
            this.experienceValue = 2;
            this.setNoGravity(true);
        }

        @Override
        public IPacket<?> createSpawnPacket() {
            return NetworkHooks.getEntitySpawningPacket(this);
        }

        @Override
        protected void registerData() {
            super.registerData();
            this.dataManager.register(BIRD_TYPE, this.rand.nextInt(6));
        }

        public int getBirdType() {
            return this.dataManager.get(BIRD_TYPE);
        }

        public void setBirdType(int type) {
            this.dataManager.set(BIRD_TYPE, MathHelper.clamp(type, 0, 5));
        }

        public void setFlyUp() {
            this.flyUp = 2;
        }

        @Override
        protected void registerGoals() {
            super.registerGoals();
        }

        private void chooseFlightTarget() {
            BlockPos origin = this.getPosition();
            int stayUp = OreSpawnLogic.isIslandsDimension(this.world) ? 2 : 0;
            for (int tries = 35; tries > 0; --tries) {
                int x = this.rand.nextInt(8) + 5 - this.flyUp * 2;
                int z = this.rand.nextInt(8) + 5 - this.flyUp * 2;
                if (this.rand.nextBoolean()) x = -x;
                if (this.rand.nextBoolean()) z = -z;
                BlockPos candidate = origin.add(x, this.rand.nextInt(9 + stayUp) - 5 + this.flyUp, z);
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
            if (x == this.lastX && z == this.lastZ) {
                ++this.stuckTicks;
            } else {
                this.stuckTicks = 0;
                this.lastX = x;
                this.lastZ = z;
            }
            if (this.flightTarget == null) {
                this.flightTarget = this.getPosition();
            }
            double distance = this.flightTarget == null ? 0.0D
                : this.getDistanceSq(Vector3d.copyCentered(this.flightTarget));
            if (this.stuckTicks > 40 || this.rand.nextInt(250) == 0 || distance < 4.1D) {
                this.stuckTicks = 0;
                this.chooseFlightTarget();
            }
            if (this.flightTarget == null) {
                return;
            }
            double dx = this.flightTarget.getX() + 0.3D - this.getPosX();
            double dy = this.flightTarget.getY() + 0.1D - this.getPosY();
            double dz = this.flightTarget.getZ() + 0.3D - this.getPosZ();
            Vector3d motion = this.getMotion();
            this.setMotion(motion.x + (Math.signum(dx) * 0.3D - motion.x) * 0.25D,
                motion.y + (Math.signum(dy) * 0.699999D - motion.y) * 0.200000001D,
                motion.z + (Math.signum(dz) * 0.3D - motion.z) * 0.25D);
            float wantedYaw = (float) (Math.atan2(this.getMotion().z, this.getMotion().x) * 180.0D / Math.PI) - 90.0F;
            this.rotationYaw += MathHelper.wrapDegrees(wantedYaw - this.rotationYaw) / 3.0F;
            this.renderYawOffset = this.rotationYaw;
        }

        @Override
        public void livingTick() {
            super.livingTick();
            this.setNoGravity(true);
            Vector3d motion = this.getMotion();
            double vertical = this.flightTarget != null && this.getPosY() < this.flightTarget.getY()
                ? motion.y * 0.7D : motion.y * 0.5D;
            this.setMotion(motion.x, vertical, motion.z);
            if (!this.world.isRemote) {
                this.tickOriginalFlight();
            }
        }

        @Override
        public boolean attackEntityFrom(DamageSource source, float amount) {
            Entity attacker = source.getTrueSource();
            if (attacker instanceof PlayerEntity) {
                this.killedByPlayer = true;
            }
            return super.attackEntityFrom(source, amount);
        }

        @Override
        public boolean onLivingFall(float distance, float damageMultiplier) {
            return false;
        }

        @Override
        protected SoundEvent getAmbientSound() {
            return this.world.isDaytime() && !this.world.isRaining()
                ? OreSpawnLogic.soundOrNull("birds") : null;
        }

        @Override
        protected SoundEvent getHurtSound(DamageSource source) {
            return OreSpawnLogic.sound("duck_hurt", SoundEvents.ENTITY_PARROT_HURT);
        }

        @Override
        protected SoundEvent getDeathSound() {
            return OreSpawnLogic.sound("duck_hurt", SoundEvents.ENTITY_PARROT_DEATH);
        }

        @Override
        protected float getSoundVolume() {
            return 0.55F;
        }

        @Override
        protected void dropSpecialItems(DamageSource source, int looting, boolean recentlyHit) {
            super.dropSpecialItems(source, looting, recentlyHit);
            int count = this.rand.nextInt(3);
            if (looting > 0) {
                count += this.rand.nextInt(looting + 1);
            }
            for (int i = 0; i < count; i++) {
                Item drop = getBirdType() == 5 && this.killedByPlayer && this.rand.nextInt(3) == 1
                    ? OreSpawnLogic.item("ruby") : Items.FEATHER;
                OreSpawnLogic.drop(this.world, this, drop, 1, 0);
            }
        }

        @Override
        public void writeAdditional(CompoundNBT nbt) {
            super.writeAdditional(nbt);
            nbt.putInt("BirdType", this.getBirdType());
        }

        @Override
        public void readAdditional(CompoundNBT nbt) {
            super.readAdditional(nbt);
            this.setBirdType(nbt.getInt("BirdType"));
        }

        @Override
        public AgeableEntity func_241840_a(ServerWorld world, AgeableEntity mate) {
            return null;
        }
    }
}
