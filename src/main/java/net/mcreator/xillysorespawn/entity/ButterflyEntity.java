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
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.network.IPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.Heightmap;
import net.mcreator.xillysorespawn.XillysOrespawnModElements;
import net.mcreator.xillysorespawn.entity.renderer.ButterflyRenderer;

@XillysOrespawnModElements.ModElement.Tag
public class ButterflyEntity extends XillysOrespawnModElements.ModElement {
    public static final EntityType entity = EntityType.Builder
        .<CustomEntity>create(CustomEntity::new, EntityClassification.AMBIENT)
        .setShouldReceiveVelocityUpdates(true).setTrackingRange(32).setUpdateInterval(2)
        .setCustomClientFactory(CustomEntity::new).size(0.4F, 0.4F)
        .build("butterfly").setRegistryName("butterfly");
    public ButterflyEntity(XillysOrespawnModElements instance) { super(instance, 1);
        FMLJavaModLoadingContext.get().getModEventBus().register(new ButterflyRenderer.ModelRegisterHandler());
        FMLJavaModLoadingContext.get().getModEventBus().register(new AttributesHandler());
        MinecraftForge.EVENT_BUS.register(this); }
    @Override public void initElements() { elements.entities.add(() -> entity); elements.items.add(() -> new SpawnEggItem(entity, 0xF08BC4, 0x34255A,
        new Item.Properties().group(ItemGroup.MISC)).setRegistryName("butterfly_spawn_egg")); }
    @SubscribeEvent public void addFeatureToBiomes(BiomeLoadingEvent event) {
        if (!OreSpawnLogic.allowNaturalSpawn(event, "butterfly")) return;
        event.getSpawns().getSpawner(EntityClassification.AMBIENT)
        .add(new MobSpawnInfo.Spawners(entity, 8, 1, 4)); }
    @Override public void init(FMLCommonSetupEvent event) { EntitySpawnPlacementRegistry.register(entity,
        EntitySpawnPlacementRegistry.PlacementType.NO_RESTRICTIONS, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, ButterflyEntity::canSpawn); }
    public static boolean canSpawn(EntityType<CustomEntity> type, IServerWorld world, SpawnReason reason, BlockPos pos, Random random) {
        if (reason == SpawnReason.SPAWN_EGG || reason == SpawnReason.SPAWNER || reason == SpawnReason.COMMAND) return true;
        return pos.getY() >= 50 && world.getWorld().isDaytime() && world.getWorld().isAirBlock(pos);
    }
    public static class AttributesHandler { @SubscribeEvent public void register(EntityAttributeCreationEvent event) { event.put(entity, CustomEntity.attributes().create()); } }
    public static class CustomEntity extends OreSpawnFlyingInsectBase {
        public CustomEntity(FMLPlayMessages.SpawnEntity packet, World world) { this(entity, world); }
        public CustomEntity(EntityType<? extends CustomEntity> type, World world) { super(type, world); this.experienceValue = 0; }
        static AttributeModifierMap.MutableAttribute attributes() { return AnimalEntity.func_233666_p_()
            .createMutableAttribute(Attributes.MAX_HEALTH, 2.0D).createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.1D)
            .createMutableAttribute(Attributes.ATTACK_DAMAGE, 1.0D).createMutableAttribute(Attributes.FOLLOW_RANGE, 16.0D); }
        @Override protected int variantCount() { return 4; }
        @Override protected int horizontalRange() { return 7; }
        @Override protected boolean attacksInDangerDimension() { return true; }
        @Override protected String[] destinationDimensions() { return new String[]{"dimension_chaos", "chaos", "butterfly_dimension"}; }
        @Override public IPacket<?> createSpawnPacket() { return NetworkHooks.getEntitySpawningPacket(this); }
    }
}
