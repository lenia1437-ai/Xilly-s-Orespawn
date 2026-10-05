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
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.network.IPacket;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.server.ServerWorld;

import net.mcreator.xillysorespawn.XillysOrespawnModElements;
import net.mcreator.xillysorespawn.entity.renderer.CoinRenderer;

@XillysOrespawnModElements.ModElement.Tag
public class CoinEntity extends XillysOrespawnModElements.ModElement {
    public static EntityType entity = (EntityType.Builder.<CustomEntity>create(CustomEntity::new, EntityClassification.CREATURE)
        .setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3)
        .setCustomClientFactory(CustomEntity::new).size(1.5F, 1.5F))
        .build("coin").setRegistryName("coin");

    public CoinEntity(XillysOrespawnModElements instance) {
        super(instance, 11);
        FMLJavaModLoadingContext.get().getModEventBus().register(new CoinRenderer.ModelRegisterHandler());
        FMLJavaModLoadingContext.get().getModEventBus().register(new EntityAttributesRegisterHandler());
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public void initElements() {
        elements.entities.add(() -> entity);
        elements.items.add(() -> new net.minecraft.item.SpawnEggItem(entity, -1, -1,
            new net.minecraft.item.Item.Properties().group(net.minecraft.item.ItemGroup.MISC))
            .setRegistryName("coin_spawn_egg"));
    }

    @SubscribeEvent
    public void addFeatureToBiomes(BiomeLoadingEvent event) {
        if (!OreSpawnLogic.allowNaturalSpawn(event, "coin")) return;
        event.getSpawns().getSpawner(EntityClassification.CREATURE)
            .add(new MobSpawnInfo.Spawners(entity, 2, 1, 1));
    }

    @Override
    public void init(FMLCommonSetupEvent event) {
        EntitySpawnPlacementRegistry.register(entity, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND,
            Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, CoinEntity::canSpawn);
    }

    public static boolean canSpawn(EntityType<? extends AnimalEntity> type, IServerWorld world,
            SpawnReason reason, BlockPos pos, Random random) {
        if (reason == SpawnReason.SPAWNER || reason == SpawnReason.SPAWN_EGG || reason == SpawnReason.COMMAND) {
            return true;
        }
        if (!world.getWorld().isDaytime() || pos.getY() < 50) {
            return false;
        }
        AxisAlignedBB area = new AxisAlignedBB(pos).grow(20.0D, 8.0D, 20.0D);
        return world.getEntitiesWithinAABB(CustomEntity.class, area, e -> true).isEmpty();
    }

    public static class EntityAttributesRegisterHandler {
        @SubscribeEvent
        public void onEntityAttributeCreation(EntityAttributeCreationEvent event) {
            event.put(entity, MobEntity.func_233666_p_()
                .createMutableAttribute(Attributes.MAX_HEALTH, 1.0D)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.0D)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 0.0D)
                .createMutableAttribute(Attributes.FOLLOW_RANGE, 16.0D).create());
        }
    }

    public static class CustomEntity extends AnimalEntity {
        public CustomEntity(FMLPlayMessages.SpawnEntity packet, World world) {
            this(entity, world);
        }

        public CustomEntity(EntityType<? extends CustomEntity> type, World world) {
            super(type, world);
            this.experienceValue = 10;
        }

        @Override
        public IPacket<?> createSpawnPacket() {
            return NetworkHooks.getEntitySpawningPacket(this);
        }

        @Override
        protected void registerGoals() {
            super.registerGoals();
            this.goalSelector.addGoal(0, new LookRandomlyGoal(this));
        }

        @Override
        public ActionResultType func_230254_b_(PlayerEntity player, Hand hand) {
            return ActionResultType.PASS;
        }

        @Override
        protected void dropSpecialItems(DamageSource source, int looting, boolean recentlyHit) {
            super.dropSpecialItems(source, looting, recentlyHit);
            int roll = this.rand.nextInt(10);
            Item drop;
            switch (roll) {
            case 0:
                drop = Items.DIAMOND;
                break;
            case 1:
                drop = OreSpawnLogic.item("uranium_nugget");
                break;
            case 2:
                drop = OreSpawnLogic.item("titanium_nugget");
                break;
            case 3:
                drop = Items.EMERALD;
                break;
            case 4:
                drop = OreSpawnLogic.item("emerald_axe");
                break;
            case 5:
                drop = OreSpawnLogic.item("emerald_shovel");
                break;
            case 6:
                drop = OreSpawnLogic.item("emerald_pickaxe");
                break;
            case 7:
                drop = OreSpawnLogic.item("emerald_hoe");
                break;
            case 8:
                drop = OreSpawnLogic.item("coin_spawn_egg", "coin_egg");
                break;
            default:
                drop = OreSpawnLogic.item("emerald_sword");
                break;
            }
            OreSpawnLogic.drop(this.world, this, drop, 1, 2);
        }

        @Override
        protected SoundEvent getAmbientSound() {
            return null;
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
        public AgeableEntity func_241840_a(ServerWorld world, AgeableEntity mate) {
            return null;
        }
    }
}
