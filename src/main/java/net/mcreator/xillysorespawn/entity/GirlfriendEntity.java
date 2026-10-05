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
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.network.IPacket;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.Heightmap;
import net.mcreator.xillysorespawn.XillysOrespawnModElements;
import net.mcreator.xillysorespawn.entity.renderer.GirlfriendRenderer;

@XillysOrespawnModElements.ModElement.Tag
public class GirlfriendEntity extends XillysOrespawnModElements.ModElement {
    public static final EntityType entity = EntityType.Builder
        .<CustomEntity>create(CustomEntity::new, EntityClassification.CREATURE)
        .setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3)
        .setCustomClientFactory(CustomEntity::new).size(0.5F, 1.6F)
        .build("girlfriend").setRegistryName("girlfriend");
    public GirlfriendEntity(XillysOrespawnModElements instance) { super(instance, 1);
        FMLJavaModLoadingContext.get().getModEventBus().register(new GirlfriendRenderer.ModelRegisterHandler());
        FMLJavaModLoadingContext.get().getModEventBus().register(new AttributesHandler()); MinecraftForge.EVENT_BUS.register(this); }
    @Override public void initElements() { elements.entities.add(() -> entity); elements.items.add(() -> new SpawnEggItem(entity, 0xE784AD, 0xF1C6A6,
        new Item.Properties().group(ItemGroup.MISC)).setRegistryName("girlfriend_spawn_egg")); }
    @SubscribeEvent public void addFeatureToBiomes(BiomeLoadingEvent event) {
        if (!OreSpawnLogic.allowNaturalSpawn(event, "girlfriend")) return;
        event.getSpawns().getSpawner(EntityClassification.CREATURE)
            .add(new MobSpawnInfo.Spawners(entity, 10, 2, 6));
    }
    @Override public void init(FMLCommonSetupEvent event) { EntitySpawnPlacementRegistry.register(entity,
        EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, GirlfriendEntity::canSpawn); }
    public static boolean canSpawn(EntityType<CustomEntity> type, IServerWorld world, SpawnReason reason, BlockPos pos, Random random) {
        return reason == SpawnReason.SPAWN_EGG || reason == SpawnReason.SPAWNER || reason == SpawnReason.COMMAND
            || AnimalEntity.canAnimalSpawn(type, world, reason, pos, random);
    }
    public static class AttributesHandler { @SubscribeEvent public void register(EntityAttributeCreationEvent event) { event.put(entity, CustomEntity.attributes().create()); } }
    public static class CustomEntity extends OreSpawnCompanionBase {
        public CustomEntity(FMLPlayMessages.SpawnEntity packet, World world) { this(entity, world); }
        public CustomEntity(EntityType<? extends CustomEntity> type, World world) { super(type, world); }
        static AttributeModifierMap.MutableAttribute attributes() { return AnimalEntity.func_233666_p_()
            .createMutableAttribute(Attributes.MAX_HEALTH, 80.0D).createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.3D)
            .createMutableAttribute(Attributes.ATTACK_DAMAGE, 8.0D).createMutableAttribute(Attributes.ARMOR, 8.0D)
            .createMutableAttribute(Attributes.FOLLOW_RANGE, 24.0D); }
        @Override protected int drySkinCount() { return 41; }
        @Override protected Item tamingItem() { return Blocks.POPPY.asItem(); }
        @Override public IPacket<?> createSpawnPacket() { return NetworkHooks.getEntitySpawningPacket(this); }
        @Override protected SoundEvent getAmbientSound() { return null; }
        @Override protected SoundEvent getHurtSound(DamageSource source) { return OreSpawnLogic.sound("o_hurt", SoundEvents.ENTITY_PLAYER_HURT); }
        @Override protected SoundEvent getDeathSound() { return OreSpawnLogic.sound(this.isTamed() ? "o_death_girlfriend" : "o_death_single", SoundEvents.ENTITY_PLAYER_DEATH); }
    }
}
