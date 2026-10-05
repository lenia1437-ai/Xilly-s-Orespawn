package net.mcreator.xillysorespawn.world.dimension;

import net.mcreator.xillysorespawn.XillysOrespawnModElements;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeAmbience;
import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.biome.MoodSoundAmbience;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilders;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Arrays;

/** OreSpawn 1.7.10 Dimension-Islands, reached through the Unstable Ant. */
@XillysOrespawnModElements.ModElement.Tag
public class IslandsDimension extends XillysOrespawnModElements.ModElement {
    public static final String MODID = "xillys_orespawn";
    public static final ResourceLocation ISLANDS_ID = new ResourceLocation(MODID, "islands");
    public static final net.minecraft.util.RegistryKey<World> ISLANDS_WORLD =
        net.minecraft.util.RegistryKey.getOrCreateKey(Registry.WORLD_KEY, ISLANDS_ID);

    public IslandsDimension(XillysOrespawnModElements instance) {
        super(instance, 1);
        FMLJavaModLoadingContext.get().getModEventBus().register(new BiomeRegistryHandler());
        MinecraftForge.EVENT_BUS.register(this);
    }

    private static final class BiomeRegistryHandler {
        @SubscribeEvent
        public void registerBiomes(RegistryEvent.Register<Biome> event) {
            Biome biome = createIslandsBiome();
            biome.setRegistryName(ISLANDS_ID);
            event.getRegistry().register(biome);
        }
    }

    private static Biome createIslandsBiome() {
        BiomeGenerationSettings generation = new BiomeGenerationSettings.Builder()
            .withSurfaceBuilder(ConfiguredSurfaceBuilders.field_244178_j).build();
        MobSpawnInfo spawns = new MobSpawnInfo.Builder().isValidSpawnBiomeForPlayer().copy();
        float temperature = 0.8F;
        return new Biome.Builder()
            .precipitation(Biome.RainType.RAIN)
            .category(Biome.Category.PLAINS)
            .depth(0.0F).scale(0.0F)
            .temperature(temperature).downfall(0.01F)
            .setEffects(new BiomeAmbience.Builder()
                .setWaterColor(1515305).setWaterFogColor(131844)
                .setFogColor(1973790).withSkyColor(657930)
                .setMoodSound(MoodSoundAmbience.DEFAULT_CAVE).build())
            .withMobSpawnSettings(spawns)
            .withGenerationSettings(generation).build();
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void configureIslandSpawns(BiomeLoadingEvent event) {
        if (!ISLANDS_ID.equals(event.getName())) return;
        Arrays.stream(EntityClassification.values())
            .forEach(c -> event.getSpawns().getSpawner(c).clear());

        add(event, EntityClassification.AMBIENT, "butterfly", 5, 2, 6);
        add(event, EntityClassification.AMBIENT, "cockateil", 4, 1, 2);
        add(event, EntityClassification.AMBIENT, "moth", 5, 2, 4);
        add(event, EntityClassification.AMBIENT, "firefly", 10, 4, 8);
        add(event, EntityClassification.AMBIENT, "dragon", 1, 1, 2);
        add(event, EntityClassification.AMBIENT, "stinky", 2, 1, 2);
        add(event, EntityClassification.AMBIENT, "cliff_racer", 20, 3, 6);
        add(event, EntityClassification.AMBIENT, "cloud_shark", 1, 1, 1);
        add(event, EntityClassification.AMBIENT, "gold_fish", 1, 1, 1);

        add(event, EntityClassification.MONSTER, "creeping_horror", 60, 4, 8);
        add(event, EntityClassification.MONSTER, "terrible_terror", 25, 3, 6);
        add(event, EntityClassification.MONSTER, "lurking_terror", 1, 1, 1);
        add(event, EntityClassification.MONSTER, "pitch_black", 15, 3, 6);
        add(event, EntityClassification.MONSTER, "leaf_monster", 35, 2, 4);
        add(event, EntityClassification.MONSTER, "ender_reaper", 25, 2, 4);
        add(event, EntityClassification.MONSTER, "hercules_beetle", 5, 1, 2);
        add(event, EntityClassification.CREATURE, "unstable_ant", 5, 1, 3);
    }

    private static void add(BiomeLoadingEvent event, EntityClassification classification,
                            String id, int weight, int min, int max) {
        EntityType<?> type = ForgeRegistries.ENTITIES.getValue(new ResourceLocation(MODID, id));
        if (type != null) event.getSpawns().getSpawner(classification)
            .add(new MobSpawnInfo.Spawners(type, weight, min, max));
    }

    /** WorldProviderOreSpawn4 skipped the second half of every day. */
    @SubscribeEvent
    public void skipIslandsNight(TickEvent.WorldTickEvent event) {
        if (event.phase != TickEvent.Phase.END || event.world.isRemote
            || !(event.world instanceof ServerWorld)
            || !ISLANDS_WORLD.equals(event.world.getDimensionKey())) return;
        ServerWorld world = (ServerWorld) event.world;
        long time = world.getDayTime();
        long dayPart = Math.floorMod(time, 24000L);
        if (dayPart > 12000L && world.getGameRules().getBoolean(GameRules.DO_DAYLIGHT_CYCLE)) {
            world.setDayTime(time + 24000L - dayPart);
        }
    }
}
