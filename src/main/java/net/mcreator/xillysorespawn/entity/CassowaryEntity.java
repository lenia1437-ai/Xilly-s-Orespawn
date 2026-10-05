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
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.entity.ai.goal.BreedGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.PanicGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.IPacket;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.server.ServerWorld;

import net.mcreator.xillysorespawn.XillysOrespawnModElements;
import net.mcreator.xillysorespawn.entity.renderer.CassowaryRenderer;

@XillysOrespawnModElements.ModElement.Tag
public class CassowaryEntity extends XillysOrespawnModElements.ModElement {
    public static EntityType entity = (EntityType.Builder.<CustomEntity>create(CustomEntity::new, EntityClassification.CREATURE)
        .setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3)
        .setCustomClientFactory(CustomEntity::new).size(0.5F, 1.2F))
        .build("cassowary").setRegistryName("cassowary");

    public CassowaryEntity(XillysOrespawnModElements instance) {
        super(instance, 4);
        FMLJavaModLoadingContext.get().getModEventBus().register(new CassowaryRenderer.ModelRegisterHandler());
        FMLJavaModLoadingContext.get().getModEventBus().register(new EntityAttributesRegisterHandler());
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public void initElements() {
        elements.entities.add(() -> entity);
        elements.items.add(() -> new net.minecraft.item.SpawnEggItem(entity, -1, -1,
            new net.minecraft.item.Item.Properties().group(net.minecraft.item.ItemGroup.MISC))
            .setRegistryName("cassowary_spawn_egg"));
    }

    @SubscribeEvent
    public void addFeatureToBiomes(BiomeLoadingEvent event) {
        if (!OreSpawnLogic.allowNaturalSpawn(event, "cassowary")) return;
        event.getSpawns().getSpawner(EntityClassification.CREATURE)
            .add(new MobSpawnInfo.Spawners(entity, 8, 1, 3));
    }

    @Override
    public void init(FMLCommonSetupEvent event) {
        EntitySpawnPlacementRegistry.register(entity, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND,
            Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, CassowaryEntity::canSpawn);
    }

    public static boolean canSpawn(EntityType<? extends AnimalEntity> type, IServerWorld world,
            SpawnReason reason, BlockPos pos, Random random) {
        if (reason == SpawnReason.SPAWNER || reason == SpawnReason.SPAWN_EGG
                || reason == SpawnReason.COMMAND || reason == SpawnReason.STRUCTURE) {
            return true;
        }
        return world.getWorld().isDaytime() && AnimalEntity.canAnimalSpawn(type, world, reason, pos, random);
    }

    public static class EntityAttributesRegisterHandler {
        @SubscribeEvent
        public void onEntityAttributeCreation(EntityAttributeCreationEvent event) {
            AttributeModifierMap.MutableAttribute attributes = MobEntity.func_233666_p_()
                .createMutableAttribute(Attributes.MAX_HEALTH, 10.0D)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.25D)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 8.0D)
                .createMutableAttribute(Attributes.FOLLOW_RANGE, 16.0D);
            event.put(entity, attributes.create());
        }
    }

    public static class CustomEntity extends AnimalEntity {
        public CustomEntity(FMLPlayMessages.SpawnEntity packet, World world) {
            this(entity, world);
        }

        public CustomEntity(EntityType<? extends CustomEntity> type, World world) {
            super(type, world);
            this.experienceValue = 5;
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
            this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, MonsterEntity.class, 8.0F, 1.0D, 1.4D));
            this.goalSelector.addGoal(3, new AvoidEntityGoal<>(this, PlayerEntity.class, 8.0F, 1.0D, 1.4D));
            this.goalSelector.addGoal(4, new PanicGoal(this, 1.5D));
            this.goalSelector.addGoal(5, new LookAtGoal(this, LivingEntity.class, 12.0F));
            this.goalSelector.addGoal(6, new OreSpawnWanderGoal(this, 10, 90, 1.0D, false));
            this.goalSelector.addGoal(7, new LookRandomlyGoal(this));
        }

        @Override
        public void livingTick() {
            if (!this.world.isRemote && this.rand.nextInt(200) == 1) {
                this.setAttackTarget(null);
            }
            super.livingTick();
        }

        @Override
        public AgeableEntity func_241840_a(ServerWorld world, AgeableEntity mate) {
            return (AgeableEntity) entity.create(world);
        }

        @Override
        public boolean isBreedingItem(ItemStack stack) {
            return !stack.isEmpty() && stack.getItem() == OreSpawnLogic.item("crystal_apple", "my_crystal_apple");
        }

        public boolean isWheat(ItemStack stack) {
            return !stack.isEmpty() && stack.getItem() == Items.APPLE;
        }

        @Override
        protected SoundEvent getAmbientSound() {
            return null;
        }

        @Override
        protected SoundEvent getHurtSound(DamageSource source) {
            return OreSpawnLogic.sound("duck_hurt", SoundEvents.ENTITY_CHICKEN_HURT);
        }

        @Override
        protected SoundEvent getDeathSound() {
            return OreSpawnLogic.sound("duck_hurt", SoundEvents.ENTITY_CHICKEN_DEATH);
        }

        @Override
        protected float getSoundVolume() {
            return 0.4F;
        }

        @Override
        protected void dropSpecialItems(DamageSource source, int looting, boolean recentlyHit) {
            super.dropSpecialItems(source, looting, recentlyHit);
            int count = this.rand.nextInt(3) + 2;
            for (int i = 0; i < count; i++) {
                this.entityDropItem(Items.CHICKEN);
            }
        }
    }
}
