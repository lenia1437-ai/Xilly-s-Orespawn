package net.mcreator.xillysorespawn.world.dimension;

import net.mcreator.xillysorespawn.XillysOrespawnModElements;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
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
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.biome.MoodSoundAmbience;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;
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

/** OreSpawn 1.7.10 Dimension-Crystal, reached by right-clicking a Termite. */
@XillysOrespawnModElements.ModElement.Tag
public class CrystalDimension extends XillysOrespawnModElements.ModElement {
    public static final String MODID = "xillys_orespawn";
    public static final ResourceLocation CRYSTAL_ID = new ResourceLocation(MODID, "crystal");
    public static final net.minecraft.util.RegistryKey<World> CRYSTAL_WORLD =
        net.minecraft.util.RegistryKey.getOrCreateKey(Registry.WORLD_KEY, CRYSTAL_ID);

    public CrystalDimension(XillysOrespawnModElements instance) {
        super(instance, 1);
        FMLJavaModLoadingContext.get().getModEventBus().register(new BiomeRegistryHandler());
        MinecraftForge.EVENT_BUS.register(this);
    }

    private static final class BiomeRegistryHandler {
        @SubscribeEvent
        public void registerBiomes(RegistryEvent.Register<Biome> event) {
            Biome biome = createCrystalBiome();
            biome.setRegistryName(CRYSTAL_ID);
            event.getRegistry().register(biome);
        }
    }

    private static Block block(String id, Block fallback) {
        Block value = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(MODID, id));
        return value == null || value == Blocks.AIR ? fallback : value;
    }

    private static Biome createCrystalBiome() {
        Block stone = block("crystal_stone", Blocks.STONE);
        Block grass = block("crystal_grass", Blocks.GRASS_BLOCK);
        BiomeGenerationSettings generation = new BiomeGenerationSettings.Builder()
            .withSurfaceBuilder(SurfaceBuilder.DEFAULT.func_242929_a(
                new SurfaceBuilderConfig(grass.getDefaultState(), stone.getDefaultState(), stone.getDefaultState())))
            .build();
        MobSpawnInfo spawns = new MobSpawnInfo.Builder().isValidSpawnBiomeForPlayer().copy();
        float adjusted = MathHelper.clamp(0.8F / 3.0F, -1.0F, 1.0F);
        int sky = MathHelper.hsvToRGB(0.72F - adjusted * 0.04F, 0.48F, 1.0F);
        return new Biome.Builder()
            .precipitation(Biome.RainType.RAIN)
            .category(Biome.Category.PLAINS)
            .depth(0.1F).scale(0.5F)
            .temperature(0.8F).downfall(0.01F)
            .setEffects(new BiomeAmbience.Builder()
                .setWaterColor(0x6A42D8).setWaterFogColor(0x29125C)
                .setFogColor(0xC9A9FF).withSkyColor(sky)
                .withGrassColor(0x56E88A).withFoliageColor(0x8B63FF)
                .setMoodSound(MoodSoundAmbience.DEFAULT_CAVE).build())
            .withMobSpawnSettings(spawns)
            .withGenerationSettings(generation).build();
    }

    /** Exact setCrystalCreatures weights from BiomeGenUtopianPlains. */
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void configureCrystalSpawns(BiomeLoadingEvent event) {
        if (!CRYSTAL_ID.equals(event.getName())) return;
        Arrays.stream(EntityClassification.values()).forEach(c -> event.getSpawns().getSpawner(c).clear());

        add(event, EntityClassification.CREATURE, "crystal_cow", 1, 1, 4);
        add(event, EntityClassification.AMBIENT, "fairy", 10, 4, 8);
        add(event, EntityClassification.AMBIENT, "peacock", 5, 4, 8);
        add(event, EntityClassification.AMBIENT, "mantis", 1, 1, 1);
        add(event, EntityClassification.MONSTER, "rotator", 4, 1, 2);
        add(event, EntityClassification.MONSTER, "vortex", 3, 1, 2);
        add(event, EntityClassification.MONSTER, "urchin", 15, 2, 4);
        add(event, EntityClassification.MONSTER, "dungeon_beast", 30, 4, 6);
        add(event, EntityClassification.MONSTER, "rat", 40, 4, 6);
        add(event, EntityClassification.AMBIENT, "butterfly", 10, 2, 4);
        add(event, EntityClassification.AMBIENT, "cockateil", 4, 1, 2);
        add(event, EntityClassification.AMBIENT, "moth", 4, 1, 2);
        add(event, EntityClassification.WATER_CREATURE, "whale", 1, 1, 2);
        add(event, EntityClassification.WATER_CREATURE, "crab", 1, 1, 2);
        add(event, EntityClassification.WATER_CREATURE, "flounder", 5, 6, 8);
        add(event, EntityClassification.WATER_CREATURE, "irukandji", 4, 2, 3);
        add(event, EntityClassification.WATER_CREATURE, "skate", 2, 3, 6);
        add(event, EntityClassification.WATER_CREATURE, "frog", 1, 3, 5);
    }

    private static void add(BiomeLoadingEvent event, EntityClassification classification,
                            String id, int weight, int min, int max) {
        EntityType<?> type = ForgeRegistries.ENTITIES.getValue(new ResourceLocation(MODID, id));
        if (type != null) event.getSpawns().getSpawner(classification)
            .add(new MobSpawnInfo.Spawners(type, weight, min, max));
    }

    /** WorldProviderOreSpawn5 skipped the night half of each day. */
    @SubscribeEvent
    public void skipCrystalNight(TickEvent.WorldTickEvent event) {
        if (event.phase != TickEvent.Phase.END || event.world.isRemote
            || !(event.world instanceof ServerWorld)
            || !CRYSTAL_WORLD.equals(event.world.getDimensionKey())) return;
        ServerWorld world = (ServerWorld) event.world;
        long time = world.getDayTime();
        long dayPart = Math.floorMod(time, 24000L);
        if (dayPart > 12000L && world.getGameRules().getBoolean(GameRules.DO_DAYLIGHT_CYCLE))
            world.setDayTime(time + 24000L - dayPart);
    }
}
