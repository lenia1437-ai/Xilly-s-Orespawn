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
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.feature.OreFeatureConfig;
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

/** OreSpawn 1.7.10 ExtremeDimension, reached through a red ant. */
@XillysOrespawnModElements.ModElement.Tag
public class ExtremeDimension extends XillysOrespawnModElements.ModElement {
    public static final String MODID = "xillys_orespawn";
    /** The MCreator dimension element is named Extreme, so its registry path is extreme. */
    public static final ResourceLocation EXTREME_ID = new ResourceLocation(MODID, "extreme");
    public static final net.minecraft.util.RegistryKey<World> EXTREME_WORLD =
        net.minecraft.util.RegistryKey.getOrCreateKey(Registry.WORLD_KEY, EXTREME_ID);

    public ExtremeDimension(XillysOrespawnModElements instance) {
        super(instance, 1);
        FMLJavaModLoadingContext.get().getModEventBus().register(new BiomeRegistryHandler());
        MinecraftForge.EVENT_BUS.register(this);
    }

    private static final class BiomeRegistryHandler {
        @SubscribeEvent
        public void registerBiomes(RegistryEvent.Register<Biome> event) {
            Biome biome = createExtremeBiome();
            biome.setRegistryName(EXTREME_ID);
            event.getRegistry().register(biome);
        }
    }

    private static Biome createExtremeBiome() {
        BiomeGenerationSettings.Builder generation = new BiomeGenerationSettings.Builder()
            .withSurfaceBuilder(ConfiguredSurfaceBuilders.field_244178_j);
        DefaultBiomeFeatures.withCavesAndCanyons(generation);
        DefaultBiomeFeatures.withLavaAndWaterLakes(generation);
        DefaultBiomeFeatures.withMonsterRoom(generation);
        DefaultBiomeFeatures.withCommonOverworldBlocks(generation);
        DefaultBiomeFeatures.withOverworldOres(generation);
        // Exact DimensionID2 additions when LessOre == 0: 45 veins of size 7
        // and 25 veins of size 4, both restricted below y=50.
        generation.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES,
            Feature.ORE.withConfiguration(new OreFeatureConfig(
                OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD,
                net.minecraft.block.Blocks.REDSTONE_ORE.getDefaultState(), 7))
                .range(50).square().func_242731_b(45));
        generation.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES,
            Feature.ORE.withConfiguration(new OreFeatureConfig(
                OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD,
                net.minecraft.block.Blocks.REDSTONE_ORE.getDefaultState(), 4))
                .range(50).square().func_242731_b(25));
        DefaultBiomeFeatures.withDisks(generation);
        generation.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.FLOWER_PLAIN_DECORATED);
        generation.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.PATCH_GRASS_PLAIN);
        DefaultBiomeFeatures.withNormalMushroomGeneration(generation);
        DefaultBiomeFeatures.withSugarCaneAndPumpkins(generation);
        DefaultBiomeFeatures.withLavaAndWaterSprings(generation);

        MobSpawnInfo.Builder spawns = new MobSpawnInfo.Builder().isValidSpawnBiomeForPlayer();
        DefaultBiomeFeatures.withPassiveMobs(spawns);
        DefaultBiomeFeatures.withBatsAndHostiles(spawns);

        float temperature = 0.8F;
        float adjusted = MathHelper.clamp(temperature / 3.0F, -1.0F, 1.0F);
        int sky = MathHelper.hsvToRGB(0.62222224F - adjusted * 0.05F,
            0.5F + adjusted * 0.1F, 1.0F);
        return new Biome.Builder()
            .precipitation(Biome.RainType.RAIN)
            .category(Biome.Category.EXTREME_HILLS)
            .depth(1.0F).scale(0.5F)
            .temperature(temperature).downfall(0.01F)
            .setEffects(new BiomeAmbience.Builder()
                .setWaterColor(4159204).setWaterFogColor(329011)
                .setFogColor(12638463).withSkyColor(sky)
                .setMoodSound(MoodSoundAmbience.DEFAULT_CAVE).build())
            .withMobSpawnSettings(spawns.copy())
            .withGenerationSettings(generation.build()).build();
    }

    /** Restores ChunkProviderOreSpawn2's added monster and ambient lists. */
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void configureExtremeSpawns(BiomeLoadingEvent event) {
        if (!EXTREME_ID.equals(event.getName())) return;

        // Entity classes generated by MCreator add themselves globally. Clear those additions,
        // then recreate the vanilla mountain list and append the original Extreme creatures.
        Arrays.stream(EntityClassification.values())
            .forEach(c -> event.getSpawns().getSpawner(c).clear());
        addVanilla(event, EntityClassification.CREATURE, EntityType.SHEEP, 12, 4, 4);
        addVanilla(event, EntityClassification.CREATURE, EntityType.PIG, 10, 4, 4);
        addVanilla(event, EntityClassification.CREATURE, EntityType.CHICKEN, 10, 4, 4);
        addVanilla(event, EntityClassification.CREATURE, EntityType.COW, 8, 4, 4);
        addVanilla(event, EntityClassification.CREATURE, EntityType.LLAMA, 5, 4, 6);
        addVanilla(event, EntityClassification.AMBIENT, EntityType.BAT, 10, 8, 8);
        addVanilla(event, EntityClassification.MONSTER, EntityType.SPIDER, 100, 4, 4);
        addVanilla(event, EntityClassification.MONSTER, EntityType.ZOMBIE, 95, 4, 4);
        addVanilla(event, EntityClassification.MONSTER, EntityType.ZOMBIE_VILLAGER, 5, 1, 1);
        addVanilla(event, EntityClassification.MONSTER, EntityType.SKELETON, 100, 4, 4);
        addVanilla(event, EntityClassification.MONSTER, EntityType.CREEPER, 100, 4, 4);
        addVanilla(event, EntityClassification.MONSTER, EntityType.SLIME, 100, 4, 4);
        addVanilla(event, EntityClassification.MONSTER, EntityType.ENDERMAN, 10, 1, 4);
        addVanilla(event, EntityClassification.MONSTER, EntityType.WITCH, 5, 1, 1);

        add(event, EntityClassification.MONSTER, "alosaurus", 8, 1, 2);
        add(event, EntityClassification.MONSTER, "t_rex", 6, 1, 2);
        add(event, EntityClassification.MONSTER, "nastysaurus", 6, 1, 2);
        add(event, EntityClassification.MONSTER, "pointysaurus", 10, 4, 8);
        add(event, EntityClassification.MONSTER, "gamma_metroid", 35, 4, 7);
        add(event, EntityClassification.MONSTER, "alien", 35, 2, 3);
        add(event, EntityClassification.MONSTER, "cave_fisher", 35, 4, 8);
        add(event, EntityClassification.MONSTER, "cryolophosaurus", 26, 4, 7);
        add(event, EntityClassification.MONSTER, "spyro", 5, 1, 2);
        add(event, EntityClassification.AMBIENT, "velocity_raptor", 1, 2, 4);
        add(event, EntityClassification.AMBIENT, "dragonfly", 2, 1, 3);
        add(event, EntityClassification.AMBIENT, "camarasaurus", 1, 2, 4);
        add(event, EntityClassification.AMBIENT, "baryonyx", 2, 4, 8);
        // OreSpawnWorld called addAnts twice per DimensionID2 chunk. In 1.16
        // the nest blocks are replaced by a low-cost natural ant population.
        add(event, EntityClassification.CREATURE, "brown_ant", 12, 2, 4);
        add(event, EntityClassification.CREATURE, "red_ant", 4, 1, 3);
        add(event, EntityClassification.CREATURE, "rainbow_ant", 4, 1, 3);
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

    /** WorldProviderOreSpawn2 skipped the second half of every day. */
    @SubscribeEvent
    public void skipExtremeNight(TickEvent.WorldTickEvent event) {
        if (event.phase != TickEvent.Phase.END || event.world.isRemote
            || !(event.world instanceof ServerWorld)
            || !EXTREME_WORLD.equals(event.world.getDimensionKey())) return;
        ServerWorld world = (ServerWorld) event.world;
        long time = world.getDayTime();
        long dayPart = Math.floorMod(time, 24000L);
        if (dayPart > 12000L && world.getGameRules().getBoolean(GameRules.DO_DAYLIGHT_CYCLE)) {
            world.setDayTime(time + 24000L - dayPart);
        }
    }
}
