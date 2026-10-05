package net.mcreator.xillysorespawn.world.dimension;

import net.mcreator.xillysorespawn.XillysOrespawnModElements;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeAmbience;
import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.biome.MoodSoundAmbience;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilders;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Arrays;

/**
 * OreSpawn's Utopia (Dimension-Utopia) adapted to Forge 1.16.5.
 *
 * The terrain/dimension type live in the accompanying data JSON files.  This
 * class supplies the Utopia biome, restores the original spawn table and
 * reproduces the old provider's "skip the night" behaviour.
 */
@XillysOrespawnModElements.ModElement.Tag
public class UtopiaDimension extends XillysOrespawnModElements.ModElement {
    public static final String MODID = "xillys_orespawn";
    public static final ResourceLocation UTOPIA_ID = new ResourceLocation(MODID, "utopia");
    public static final net.minecraft.util.RegistryKey<World> UTOPIA_WORLD =
        net.minecraft.util.RegistryKey.getOrCreateKey(Registry.WORLD_KEY, UTOPIA_ID);

    private static Biome utopiaBiome;

    public UtopiaDimension(XillysOrespawnModElements instance) {
        super(instance, 1);
        FMLJavaModLoadingContext.get().getModEventBus().register(new BiomeRegistryHandler());
        MinecraftForge.EVENT_BUS.register(this);
    }

    private static final class BiomeRegistryHandler {
        @SubscribeEvent
        public void registerBiomes(RegistryEvent.Register<Biome> event) {
            utopiaBiome = createUtopiaBiome();
            utopiaBiome.setRegistryName(UTOPIA_ID);
            event.getRegistry().register(utopiaBiome);
        }
    }

    private static Biome createUtopiaBiome() {
        BiomeGenerationSettings.Builder generation = new BiomeGenerationSettings.Builder()
            .withSurfaceBuilder(ConfiguredSurfaceBuilders.field_244178_j);

        // Same broad terrain ingredients as the old overworld-like chunk provider.
        // Deliberately no ordinary trees: OreSpawn Utopia populated its own huge,
        // apple, wind and sky trees instead of the vanilla tree decorator.
        DefaultBiomeFeatures.withCavesAndCanyons(generation);
        DefaultBiomeFeatures.withLavaAndWaterLakes(generation);
        DefaultBiomeFeatures.withMonsterRoom(generation);
        DefaultBiomeFeatures.withCommonOverworldBlocks(generation);
        DefaultBiomeFeatures.withOverworldOres(generation);
        DefaultBiomeFeatures.withDisks(generation);
        generation.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.FLOWER_PLAIN_DECORATED);
        generation.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.PATCH_GRASS_PLAIN);
        DefaultBiomeFeatures.withNormalMushroomGeneration(generation);
        DefaultBiomeFeatures.withSugarCaneAndPumpkins(generation);
        DefaultBiomeFeatures.withLavaAndWaterSprings(generation);

        float temperature = 0.7F;
        float adjusted = MathHelper.clamp(temperature / 3.0F, -1.0F, 1.0F);
        int skyColor = MathHelper.hsvToRGB(0.62222224F - adjusted * 0.05F,
            0.5F + adjusted * 0.1F, 1.0F);

        return new Biome.Builder()
            // Utopia is a permanently bright paradise. Leaving overworld rain
            // enabled here allowed a weather cycle (or a Kraken) to make the
            // fixed-biome dimension look as if it rained forever.
            .precipitation(Biome.RainType.NONE)
            .category(Biome.Category.PLAINS)
            .depth(0.125F)
            .scale(0.05F)
            .temperature(temperature)
            .downfall(0.0F)
            .setEffects(new BiomeAmbience.Builder()
                .setWaterColor(4159204)
                .setWaterFogColor(329011)
                .setFogColor(12638463)
                .withSkyColor(skyColor)
                .setMoodSound(MoodSoundAmbience.DEFAULT_CAVE)
                .build())
            .withMobSpawnSettings(new MobSpawnInfo.Builder().isValidSpawnBiomeForPlayer().copy())
            .withGenerationSettings(generation.build())
            .build();
    }

    /**
     * Every generated entity class in this workspace adds its spawn to every
     * biome. Run last for Utopia, wipe that global list and restore the exact
     * 1.7.10 UtopianPlains table. Missing not-yet-ported entity IDs are skipped.
     */
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void configureUtopiaSpawns(BiomeLoadingEvent event) {
        if (!UTOPIA_ID.equals(event.getName())) {
            return;
        }

        Arrays.stream(EntityClassification.values())
            .forEach(type -> event.getSpawns().getSpawner(type).clear());

        addSpawn(event, EntityClassification.CREATURE, "gazelle", 10, 2, 4);
        addSpawn(event, EntityClassification.AMBIENT, "firefly", 15, 3, 6);
        addSpawn(event, EntityClassification.CREATURE, "girlfriend", 5, 2, 3);
        addSpawn(event, EntityClassification.CREATURE, "boyfriend", 5, 2, 3);
        addSpawn(event, EntityClassification.CREATURE, "red_cow", 10, 4, 8);
        addSpawn(event, EntityClassification.CREATURE, "gold_cow", 8, 2, 6);
        addSpawn(event, EntityClassification.CREATURE, "enchanted_cow", 5, 2, 4);
        addSpawn(event, EntityClassification.AMBIENT, "butterfly", 20, 3, 6);
        addSpawn(event, EntityClassification.AMBIENT, "moth", 10, 1, 5);
        addSpawn(event, EntityClassification.AMBIENT, "chipmunk", 3, 1, 2);
        addSpawn(event, EntityClassification.AMBIENT, "cockateil", 10, 2, 4);
        addSpawn(event, EntityClassification.AMBIENT, "gold_fish", 1, 1, 1);
        addSpawn(event, EntityClassification.WATER_CREATURE, "whale", 1, 1, 1);
        addSpawn(event, EntityClassification.WATER_CREATURE, "flounder", 2, 2, 4);
        addSpawn(event, EntityClassification.AMBIENT, "coin", 2, 1, 1);
        addSpawn(event, EntityClassification.AMBIENT, "cricket", 5, 4, 6);
        addSpawn(event, EntityClassification.WATER_CREATURE, "frog", 5, 4, 6);
    }

    private static void addSpawn(BiomeLoadingEvent event, EntityClassification classification,
                                 String entityName, int weight, int min, int max) {
        EntityType<?> type = ForgeRegistries.ENTITIES.getValue(new ResourceLocation(MODID, entityName));
        if (type != null) {
            event.getSpawns().getSpawner(classification)
                .add(new MobSpawnInfo.Spawners(type, weight, min, max));
        }
    }

    /** Reproduces WorldProviderOreSpawn.setWorldTime: Utopia jumps over night. */
    @SubscribeEvent
    public void keepUtopiaInDaylight(TickEvent.WorldTickEvent event) {
        if (event.phase != TickEvent.Phase.END || event.world.isRemote
            || !(event.world instanceof ServerWorld)
            || !UTOPIA_WORLD.equals(event.world.getDimensionKey())) {
            return;
        }

        ServerWorld world = (ServerWorld) event.world;
        if (world.isRaining() || world.isThundering()) {
            world.func_241113_a_(12000, 0, false, false);
        }
        long time = world.getDayTime();
        long dayPart = Math.floorMod(time, 24000L);
        if (dayPart > 12000L && world.getGameRules().getBoolean(net.minecraft.world.GameRules.DO_DAYLIGHT_CYCLE)) {
            world.setDayTime(time + 24000L - dayPart);
        }
    }
}
