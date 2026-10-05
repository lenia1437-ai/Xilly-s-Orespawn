package net.mcreator.xillysorespawn.entity;

import java.util.EnumSet;
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
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.PanicGoal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.server.ServerWorld;

import net.mcreator.xillysorespawn.XillysOrespawnModElements;
import net.mcreator.xillysorespawn.entity.renderer.CricketRenderer;

@XillysOrespawnModElements.ModElement.Tag
public class CricketEntity extends XillysOrespawnModElements.ModElement {
    public static EntityType entity = (EntityType.Builder.<CustomEntity>create(CustomEntity::new, EntityClassification.CREATURE)
        .setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3)
        .setCustomClientFactory(CustomEntity::new).size(0.1F, 0.1F))
        .build("cricket").setRegistryName("cricket");

    public CricketEntity(XillysOrespawnModElements instance) {
        super(instance, 15);
        FMLJavaModLoadingContext.get().getModEventBus().register(new CricketRenderer.ModelRegisterHandler());
        FMLJavaModLoadingContext.get().getModEventBus().register(new EntityAttributesRegisterHandler());
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public void initElements() {
        elements.entities.add(() -> entity);
        elements.items.add(() -> new net.minecraft.item.SpawnEggItem(entity, -1, -1,
            new net.minecraft.item.Item.Properties().group(net.minecraft.item.ItemGroup.MISC))
            .setRegistryName("cricket_spawn_egg"));
    }

    @SubscribeEvent
    public void addFeatureToBiomes(BiomeLoadingEvent event) {
        if (!OreSpawnLogic.allowNaturalSpawn(event, "cricket")) return;
        event.getSpawns().getSpawner(EntityClassification.CREATURE)
            .add(new MobSpawnInfo.Spawners(entity, 8, 1, 4));
    }

    @Override
    public void init(FMLCommonSetupEvent event) {
        EntitySpawnPlacementRegistry.register(entity, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND,
            Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, CricketEntity::canSpawn);
    }

    public static boolean canSpawn(EntityType<? extends AnimalEntity> type, IServerWorld world,
            SpawnReason reason, BlockPos pos, Random random) {
        if (reason == SpawnReason.SPAWNER || reason == SpawnReason.SPAWN_EGG || reason == SpawnReason.COMMAND) {
            return true;
        }
        if (pos.getY() < 30) {
            return false;
        }
        AxisAlignedBB area = new AxisAlignedBB(pos).grow(20.0D, 10.0D, 20.0D);
        return world.getEntitiesWithinAABB(CustomEntity.class, area, e -> true).size() <= 5;
    }

    public static class EntityAttributesRegisterHandler {
        @SubscribeEvent
        public void onEntityAttributeCreation(EntityAttributeCreationEvent event) {
            event.put(entity, MobEntity.func_233666_p_()
                .createMutableAttribute(Attributes.MAX_HEALTH, 3.0D)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.15D)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 0.0D)
                .createMutableAttribute(Attributes.FOLLOW_RANGE, 16.0D).create());
        }
    }

    public static class CustomEntity extends AnimalEntity {
        private static final DataParameter<Byte> SINGING = EntityDataManager.createKey(CustomEntity.class, DataSerializers.BYTE);
        private int singingTicks;
        private int jumpCooldown;

        public CustomEntity(FMLPlayMessages.SpawnEntity packet, World world) {
            this(entity, world);
        }

        public CustomEntity(EntityType<? extends CustomEntity> type, World world) {
            super(type, world);
            this.experienceValue = 1;
        }

        @Override
        public IPacket<?> createSpawnPacket() {
            return NetworkHooks.getEntitySpawningPacket(this);
        }

        @Override
        protected void registerData() {
            super.registerData();
            this.dataManager.register(SINGING, (byte) 0);
        }

        public int getSinging() {
            return this.dataManager.get(SINGING);
        }

        private void setSinging(int ticks) {
            this.dataManager.set(SINGING, (byte) ticks);
        }

        @Override
        protected void registerGoals() {
            super.registerGoals();
            this.goalSelector.addGoal(0, new PanicGoal(this, 1.4D));
            this.goalSelector.addGoal(1, new WanderALotGoal(this));
        }

        private void jumpAround() {
            Vector3d motion = this.getMotion();
            float force = 0.3F + Math.abs(this.rand.nextFloat() * 0.25F);
            float angle = (float) (this.rand.nextFloat() * Math.PI * 2.0D);
            this.setPosition(this.getPosX(), this.getPosY() + 0.25D, this.getPosZ());
            this.setMotion(motion.x + force * Math.sin(angle),
                motion.y + 0.55F + Math.abs(this.rand.nextFloat() * 0.35F),
                motion.z + force * Math.cos(angle));
            this.velocityChanged = true;
        }

        @Override
        public void livingTick() {
            super.livingTick();
            if (!this.world.isRemote) {
                if (this.singingTicks > 0 && --this.singingTicks <= 0) {
                    this.setSinging(0);
                }
                if (this.jumpCooldown > 0) {
                    --this.jumpCooldown;
                }
                if (this.jumpCooldown == 0 && this.rand.nextInt(50) == 1) {
                    this.jumpAround();
                    this.jumpCooldown = 50;
                }
            }
        }

        @Override
        protected SoundEvent getAmbientSound() {
            if (!this.world.isRemote) {
                if (this.rand.nextInt(2) == 0) {
                    return null;
                }
                this.singingTicks = 40;
                this.setSinging(40);
            }
            return OreSpawnLogic.soundOrNull("cricket");
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
            return 0.7F;
        }

        @Override
        public boolean onLivingFall(float distance, float damageMultiplier) {
            return false;
        }

        @Override
        public AgeableEntity func_241840_a(ServerWorld world, AgeableEntity mate) {
            return null;
        }

        private static final class WanderALotGoal extends Goal {
            private final CustomEntity cricket;
            private Vector3d destination;

            private WanderALotGoal(CustomEntity cricket) {
                this.cricket = cricket;
                this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE));
            }

            @Override
            public boolean shouldExecute() {
                if (this.cricket.rand.nextInt(30) != 0) {
                    return false;
                }
                this.destination = RandomPositionGenerator.findRandomTarget(this.cricket, 8, 7);
                return this.destination != null;
            }

            @Override
            public boolean shouldContinueExecuting() {
                return !this.cricket.getNavigator().noPath();
            }

            @Override
            public void startExecuting() {
                this.cricket.getNavigator().tryMoveToXYZ(this.destination.x, this.destination.y,
                    this.destination.z, 1.0D);
            }
        }
    }
}
