package net.mcreator.xillysorespawn.world.dimension;

import net.mcreator.xillysorespawn.XillysOrespawnModElements;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeAmbience;
import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.biome.MoodSoundAmbience;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilders;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Arrays;

/** Floating Chaos biome with no vanilla structures and OreSpawn's hard mobs. */
@XillysOrespawnModElements.ModElement.Tag
public class ChaosDimension extends XillysOrespawnModElements.ModElement {
    public static final ResourceLocation CHAOS_ID = new ResourceLocation("xillys_orespawn", "chaos");

    public ChaosDimension(XillysOrespawnModElements instance) {
        super(instance, 1070);
        FMLJavaModLoadingContext.get().getModEventBus().register(new RegistryHandler());
        MinecraftForge.EVENT_BUS.register(this);
    }

    private static class RegistryHandler {
        @SubscribeEvent
        public void register(RegistryEvent.Register<Biome> event) {
            BiomeGenerationSettings generation = new BiomeGenerationSettings.Builder()
                .withSurfaceBuilder(ConfiguredSurfaceBuilders.field_244178_j).build();
            float t = 0.7F;
            int sky = MathHelper.hsvToRGB(0.62222224F - MathHelper.clamp(t / 3F, -1F, 1F) * 0.05F,
                0.5F + MathHelper.clamp(t / 3F, -1F, 1F) * 0.1F, 1F);
            Biome biome = new Biome.Builder().precipitation(Biome.RainType.NONE)
                .category(Biome.Category.NONE).depth(0.1F).scale(0.2F).temperature(t).downfall(0F)
                .setEffects(new BiomeAmbience.Builder().setWaterColor(4159204).setWaterFogColor(329011)
                    .setFogColor(10518688).withSkyColor(sky).setMoodSound(MoodSoundAmbience.DEFAULT_CAVE).build())
                .withMobSpawnSettings(new MobSpawnInfo.Builder().isValidSpawnBiomeForPlayer().copy())
                .withGenerationSettings(generation).build();
            biome.setRegistryName(CHAOS_ID);
            event.getRegistry().register(biome);
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void spawns(BiomeLoadingEvent event) {
        if (!CHAOS_ID.equals(event.getName())) return;
        Arrays.stream(EntityClassification.values()).forEach(c -> event.getSpawns().getSpawner(c).clear());
        add(event, "emperor_scorpion", 9, 1, 2);
        add(event, "mantis", 10, 1, 2);
        add(event, "hercules_beetle", 8, 1, 2);
        add(event, "brutalfly", 8, 1, 2);
        add(event, "mothra", 8, 1, 2);
        add(event, "vortex", 7, 1, 2);
        add(event, "dungeon_beast", 7, 1, 2);
        add(event, "cephadrome", 6, 1, 1);
        add(event, "dragon", 6, 1, 1);
        add(event, "kraken", 4, 1, 1);
        add(event, "godzilla", 3, 1, 1);
        add(event, "mobzilla", 1, 1, 1);
        add(event, "the_king", 1, 1, 1);
        add(event, "the_queen", 1, 1, 1);
    }

    private static void add(BiomeLoadingEvent event, String id, int weight, int min, int max) {
        EntityType<?> type = ForgeRegistries.ENTITIES.getValue(new ResourceLocation("xillys_orespawn", id));
        if (type != null) event.getSpawns().getSpawner(EntityClassification.MONSTER)
            .add(new MobSpawnInfo.Spawners(type, weight, min, max));
    }
}
