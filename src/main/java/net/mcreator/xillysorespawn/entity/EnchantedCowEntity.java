package net.mcreator.xillysorespawn.entity;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraft.entity.*;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.item.*;
import net.minecraft.network.IPacket;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.*;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.server.ServerWorld;
import net.mcreator.xillysorespawn.XillysOrespawnModElements;
import net.mcreator.xillysorespawn.entity.renderer.EnchantedCowRenderer;

/** OreSpawn Enchanted Golden Apple Cow. */
@XillysOrespawnModElements.ModElement.Tag
public class EnchantedCowEntity extends XillysOrespawnModElements.ModElement {
    public static final EntityType entity = EntityType.Builder
        .<CustomEntity>create(CustomEntity::new, EntityClassification.CREATURE)
        .setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3)
        .setCustomClientFactory(CustomEntity::new).size(0.9F, 1.4F)
        .build("enchanted_cow").setRegistryName("enchanted_cow");
    public EnchantedCowEntity(XillysOrespawnModElements instance) {
        super(instance, 1);
        FMLJavaModLoadingContext.get().getModEventBus().register(new EnchantedCowRenderer.ModelRegisterHandler());
        FMLJavaModLoadingContext.get().getModEventBus().register(new AttributesHandler());
        MinecraftForge.EVENT_BUS.register(this);
    }
    @Override public void initElements() {
        elements.entities.add(() -> entity);
        elements.items.add(() -> new SpawnEggItem(entity, 0xE6BD36, 0xC75AFF,
            new Item.Properties().group(ItemGroup.MISC)).setRegistryName("enchanted_cow_spawn_egg"));
    }
    @SubscribeEvent public void addFeatureToBiomes(BiomeLoadingEvent event) {
        if (!OreSpawnLogic.allowNaturalSpawn(event, "enchanted_cow")) return;
        ResourceLocation id = event.getName();
        if (id == null) return;
        String p = id.getPath();
        int weight = p.equals("mushroom_fields") ? 15 : p.equals("giant_tree_taiga") ? 5 : 3;
        int min = p.equals("mushroom_fields") ? 3 : 2;
        int max = p.equals("mushroom_fields") ? 6 : p.equals("giant_tree_taiga") ? 5 : 4;
        event.getSpawns().getSpawner(EntityClassification.CREATURE)
            .add(new MobSpawnInfo.Spawners(entity, weight, min, max));
    }
    @Override public void init(FMLCommonSetupEvent event) {
        EntitySpawnPlacementRegistry.register(entity, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND,
            Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::canAnimalSpawn);
    }
    public static class AttributesHandler {
        @SubscribeEvent public void register(EntityAttributeCreationEvent event) {
            event.put(entity, CowEntity.func_234188_eI_().create());
        }
    }
    public static class CustomEntity extends CowEntity {
        public CustomEntity(FMLPlayMessages.SpawnEntity packet, World world) { this(entity, world); }
        public CustomEntity(EntityType<? extends CustomEntity> type, World world) { super(type, world); }
        @Override protected void dropSpecialItems(DamageSource source, int looting, boolean recentlyHit) {
            int first = this.rand.nextInt(4) + this.rand.nextInt(1 + Math.max(0, looting));
            int inherited = this.rand.nextInt(3) + this.rand.nextInt(1 + Math.max(0, looting));
            if (first > 0) this.entityDropItem(new ItemStack(Items.APPLE, first));
            this.entityDropItem(new ItemStack(Items.GOLDEN_APPLE, 2));
            this.entityDropItem(Items.ENCHANTED_GOLDEN_APPLE);
            if (inherited > 0) this.entityDropItem(new ItemStack(Items.APPLE, inherited));
            super.dropSpecialItems(source, looting, recentlyHit);
        }
        @Override public void livingTick() { if (!world.isRemote && rand.nextInt(200) == 1) setAttackTarget(null); super.livingTick(); }
        @Override public CowEntity func_241840_a(ServerWorld world, AgeableEntity mate) { return (CowEntity) entity.create(world); }
        @Override public boolean canDespawn(double distanceToClosestPlayer) { return false; }
        @Override public IPacket<?> createSpawnPacket() { return NetworkHooks.getEntitySpawningPacket(this); }
    }
}
