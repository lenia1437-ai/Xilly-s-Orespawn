package net.mcreator.xillysorespawn.world.dimension;

import net.mcreator.xillysorespawn.XillysOrespawnModElements;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeAmbience;
import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.biome.MoodSoundAmbience;
import net.minecraft.world.gen.feature.structure.StructureFeatures;
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

/** OreSpawn 1.7.10 Dimension-VillageMania, reached through a rainbow ant. */
@XillysOrespawnModElements.ModElement.Tag
public class VillageDimension extends XillysOrespawnModElements.ModElement {
    public static final String MODID = "xillys_orespawn";
    public static final ResourceLocation VILLAGE_ID = new ResourceLocation(MODID, "village");
    public static final net.minecraft.util.RegistryKey<World> VILLAGE_WORLD =
        net.minecraft.util.RegistryKey.getOrCreateKey(Registry.WORLD_KEY, VILLAGE_ID);

    public VillageDimension(XillysOrespawnModElements instance) {
        super(instance, 1);
        FMLJavaModLoadingContext.get().getModEventBus().register(new BiomeRegistryHandler());
        MinecraftForge.EVENT_BUS.register(this);
    }

    private static final class BiomeRegistryHandler {
        @SubscribeEvent
        public void registerBiomes(RegistryEvent.Register<Biome> event) {
            Biome biome = createVillageBiome();
            biome.setRegistryName(VILLAGE_ID);
            event.getRegistry().register(biome);
        }
    }

    private static Biome createVillageBiome() {
        BiomeGenerationSettings.Builder generation = new BiomeGenerationSettings.Builder()
            .withSurfaceBuilder(ConfiguredSurfaceBuilders.field_244178_j)
            .withStructure(StructureFeatures.VILLAGE_PLAINS);
        DefaultBiomeFeatures.withStrongholdAndMineshaft(generation);
        DefaultBiomeFeatures.withCavesAndCanyons(generation);
        DefaultBiomeFeatures.withLavaAndWaterLakes(generation);
        DefaultBiomeFeatures.withMonsterRoom(generation);
        DefaultBiomeFeatures.withCommonOverworldBlocks(generation);
        DefaultBiomeFeatures.withOverworldOres(generation);
        DefaultBiomeFeatures.withDisks(generation);
        DefaultBiomeFeatures.withPlainGrassVegetation(generation);
        DefaultBiomeFeatures.withDefaultFlowers(generation);
        DefaultBiomeFeatures.withNormalMushroomGeneration(generation);
        DefaultBiomeFeatures.withSugarCaneAndPumpkins(generation);
        DefaultBiomeFeatures.withLavaAndWaterSprings(generation);

        MobSpawnInfo.Builder spawns = new MobSpawnInfo.Builder().isValidSpawnBiomeForPlayer();
        DefaultBiomeFeatures.withPassiveMobs(spawns);
        DefaultBiomeFeatures.withBatsAndHostiles(spawns);

        float temperature = 0.7F;
        float adjusted = MathHelper.clamp(temperature / 3.0F, -1.0F, 1.0F);
        int sky = MathHelper.hsvToRGB(0.62222224F - adjusted * 0.05F,
            0.5F + adjusted * 0.1F, 1.0F);
        return new Biome.Builder()
            .precipitation(Biome.RainType.RAIN)
            .category(Biome.Category.PLAINS)
            .depth(0.125F).scale(0.05F)
            .temperature(temperature).downfall(0.5F)
            .setEffects(new BiomeAmbience.Builder()
                .setWaterColor(4159204).setWaterFogColor(329011)
                .setFogColor(12638463).withSkyColor(sky)
                .setMoodSound(MoodSoundAmbience.DEFAULT_CAVE).build())
            .withMobSpawnSettings(spawns.copy())
            .withGenerationSettings(generation.build()).build();
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void configureVillageSpawns(BiomeLoadingEvent event) {
        if (!VILLAGE_ID.equals(event.getName())) return;
        Arrays.stream(EntityClassification.values())
            .forEach(c -> event.getSpawns().getSpawner(c).clear());

        addVanilla(event, EntityClassification.CREATURE, EntityType.SHEEP, 12, 4, 4);
        addVanilla(event, EntityClassification.CREATURE, EntityType.PIG, 10, 4, 4);
        addVanilla(event, EntityClassification.CREATURE, EntityType.CHICKEN, 10, 4, 4);
        addVanilla(event, EntityClassification.CREATURE, EntityType.COW, 8, 4, 4);
        addVanilla(event, EntityClassification.AMBIENT, EntityType.BAT, 10, 8, 8);
        addVanilla(event, EntityClassification.MONSTER, EntityType.SPIDER, 100, 4, 4);
        addVanilla(event, EntityClassification.MONSTER, EntityType.ZOMBIE, 95, 4, 4);
        addVanilla(event, EntityClassification.MONSTER, EntityType.SKELETON, 100, 4, 4);
        addVanilla(event, EntityClassification.MONSTER, EntityType.CREEPER, 100, 4, 4);
        addVanilla(event, EntityClassification.MONSTER, EntityType.ENDERMAN, 10, 1, 4);

        add(event, EntityClassification.CREATURE, "gazelle", 10, 2, 4);
        add(event, EntityClassification.CREATURE, "girlfriend", 6, 2, 3);
        add(event, EntityClassification.CREATURE, "boyfriend", 6, 2, 3);
        add(event, EntityClassification.CREATURE, "red_cow", 18, 4, 8);
        add(event, EntityClassification.CREATURE, "gold_cow", 14, 2, 6);
        add(event, EntityClassification.CREATURE, "enchanted_cow", 9, 2, 4);

        add(event, EntityClassification.AMBIENT, "firefly", 25, 3, 6);
        add(event, EntityClassification.AMBIENT, "butterfly", 45, 3, 6);
        add(event, EntityClassification.AMBIENT, "moth", 30, 1, 5);
        add(event, EntityClassification.AMBIENT, "chipmunk", 8, 1, 2);
        add(event, EntityClassification.AMBIENT, "cockateil", 25, 2, 4);
        add(event, EntityClassification.AMBIENT, "gold_fish", 1, 1, 1);
        add(event, EntityClassification.AMBIENT, "coin", 4, 1, 1);
        add(event, EntityClassification.AMBIENT, "cricket", 5, 4, 6);
        add(event, EntityClassification.AMBIENT, "tshirt", 2, 1, 1);
        add(event, EntityClassification.AMBIENT, "bandp", 15, 1, 2);
        add(event, EntityClassification.WATER_CREATURE, "whale", 1, 1, 1);
        add(event, EntityClassification.WATER_CREATURE, "flounder", 2, 2, 4);
        add(event, EntityClassification.WATER_CREATURE, "frog", 5, 4, 6);

        add(event, EntityClassification.MONSTER, "robot_1", 25, 4, 8);
        add(event, EntityClassification.MONSTER, "robot_2", 16, 2, 8);
        add(event, EntityClassification.MONSTER, "robot_3", 12, 2, 4);
        add(event, EntityClassification.MONSTER, "robot_4", 8, 1, 2);
        add(event, EntityClassification.MONSTER, "robot_5", 20, 4, 8);
        add(event, EntityClassification.MONSTER, "giant_robot", 8, 1, 2);
        add(event, EntityClassification.MONSTER, "spider_robot", 20, 3, 5);
        add(event, EntityClassification.MONSTER, "godzilla", 2, 1, 1);

        add(event, EntityClassification.CREATURE, "rainbow_ant", 12, 2, 4);
        add(event, EntityClassification.CREATURE, "red_ant", 4, 1, 3);
        add(event, EntityClassification.CREATURE, "brown_ant", 4, 1, 3);
        add(event, EntityClassification.CREATURE, "unstable_ant", 4, 1, 3);
        add(event, EntityClassification.CREATURE, "termite", 4, 1, 3);
    }

    private static void add(BiomeLoadingEvent event, EntityClassification classification,
                            String id, int weight, int min, int max) {
        EntityType<?> type = ForgeRegistries.ENTITIES.getValue(new ResourceLocation(MODID, id));
        if (type != null) addVanilla(event, classification, type, weight, min, max);
    }

    private static void addVanilla(BiomeLoadingEvent event, EntityClassification classification,
                                   EntityType<?> type, int weight, int min, int max) {
        event.getSpawns().getSpawner(classification)
            .add(new MobSpawnInfo.Spawners(type, weight, min, max));
    }

    /** WorldProviderOreSpawn3 skipped the second half of each day. */
    @SubscribeEvent
    public void skipVillageNight(TickEvent.WorldTickEvent event) {
        if (event.phase != TickEvent.Phase.END || event.world.isRemote
            || !(event.world instanceof ServerWorld)
            || !VILLAGE_WORLD.equals(event.world.getDimensionKey())) return;
        ServerWorld world = (ServerWorld) event.world;
        long time = world.getDayTime();
        long dayPart = Math.floorMod(time, 24000L);
        if (dayPart > 12000L && world.getGameRules().getBoolean(GameRules.DO_DAYLIGHT_CYCLE)) {
            world.setDayTime(time + 24000L - dayPart);
        }
    }
}
