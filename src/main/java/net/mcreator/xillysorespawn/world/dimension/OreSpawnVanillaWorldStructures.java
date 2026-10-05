package net.mcreator.xillysorespawn.world.dimension;

import net.mcreator.xillysorespawn.XillysOrespawnModElements;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.block.Blocks;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.HashSet;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Overworld structure selection follows OreSpawnWorld.generateSurface() from
 * OreSpawn 1.7.10. Extreme Dimension dungeons never belong in the Overworld.
 */
@XillysOrespawnModElements.ModElement.Tag
public class OreSpawnVanillaWorldStructures extends XillysOrespawnModElements.ModElement {
    private static final long SALT = 0x4F5245535041574EL;
    private final Queue<PendingChunk> pending = new ConcurrentLinkedQueue<>();
    private final Set<String> pendingKeys = ConcurrentHashMap.newKeySet();
    private boolean generating;
    private int cooldown;

    public OreSpawnVanillaWorldStructures(XillysOrespawnModElements instance) {
        super(instance, 1);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onWorldUnload(WorldEvent.Unload event) {
        if (!(event.getWorld() instanceof ServerWorld)) return;
        ServerWorld world = (ServerWorld) event.getWorld();
        boolean end = World.THE_END.equals(world.getDimensionKey());
        if (!end && !World.OVERWORLD.equals(world.getDimensionKey())) return;
        pending.removeIf(task -> task.end == end);
        pendingKeys.removeIf(key -> key.startsWith(end ? "E:" : "O:"));
        cooldown = 0;
    }

    @SubscribeEvent
    public void onChunkLoad(ChunkEvent.Load event) {
        if (generating || !(event.getWorld() instanceof ServerWorld)) return;
        ServerWorld world = (ServerWorld) event.getWorld();
        boolean overworld = World.OVERWORLD.equals(world.getDimensionKey());
        boolean end = World.THE_END.equals(world.getDimensionKey());
        if (!overworld && !end) return;

        ChunkPos pos = event.getChunk().getPos();
        long chunkKey = pos.asLong();
        String dataName = end ? GeneratedData.END_NAME : GeneratedData.OVERWORLD_NAME;
        GeneratedData data = world.getSavedData().getOrCreate(() -> new GeneratedData(dataName), dataName);
        String pendingKey = (end ? "E:" : "O:") + chunkKey;
        if (data.contains(chunkKey) || !pendingKeys.add(pendingKey)) return;
        if (overworld) data.advance();

        long seed = mix(world.getSeed(), pos.x, pos.z, end);
        Random random = new Random(seed);
        int kind = -1;
        if (end) {
            // Original End methods tried one of four structures and each had
            // its own 1/25 (castle 1/50) gate.  One dungeon per 25 chunks is
            // the same practical density without four nested random streams.
            if (random.nextInt(25) == 0) kind = 100;
        } else if (data.recentlyPlaced == 0) {
            Random siteRandom = new Random(seed);
            int siteX = (pos.x << 4) + 4 + siteRandom.nextInt(8);
            int siteZ = (pos.z << 4) + 4 + siteRandom.nextInt(8);
            int siteY = world.getHeight(Heightmap.Type.WORLD_SURFACE, siteX, siteZ) - 1;
            BlockPos site = new BlockPos(siteX, siteY, siteZ);
            // Original: choose exactly one of six ponds/islands, then attempt
            // the land structures in this order until one is placed.
            int waterChoice = random.nextInt(6);
            int[] waterOdds = {350, 350, 350, 300, 300, 350};
            if (random.nextInt(waterOdds[waterChoice]) == 0 && isOriginalSite(world, waterChoice + 1, site))
                kind = waterChoice + 1;
            if (kind < 0 && random.nextInt(230) == 0 && isOriginalSite(world, 7, site)) kind = 7;
            if (kind < 0 && random.nextInt(285) == 0 && isOriginalSite(world, 9, site)) kind = 9;
            if (kind < 0 && random.nextInt(275) == 0 && isOriginalSite(world, 10, site)) kind = 10;
            if (kind < 0 && random.nextInt(190) == 0 && isOriginalSite(world, 11, site)) kind = 11;
            if (kind < 0 && random.nextInt(220) == 0 && isOriginalSite(world, 12, site)) kind = 12;
            if (kind < 0 && random.nextInt(230) == 0 && isOriginalSite(world, 13, site)) kind = 13;
            if (kind < 0 && random.nextInt(275) == 0 && isOriginalSite(world, 14, site)) kind = 14;
            if (kind == 7 && random.nextBoolean()) kind = 8;
        }

        // Every new Overworld chunk also receives OreSpawn's surface flora and
        // ant-nest pass, even when none of the rare landmarks was selected.
        if (kind < 0 && end) {
            data.mark(chunkKey);
            pendingKeys.remove(pendingKey);
            return;
        }
        pending.add(new PendingChunk(pos.x, pos.z, seed, kind, end,
            world.getGameTime() + 40L));
    }

    @SubscribeEvent
    public void onWorldTick(TickEvent.WorldTickEvent event) {
        if (event.phase != TickEvent.Phase.END || event.world.isRemote
                || !(event.world instanceof ServerWorld) || generating || cooldown-- > 0) return;
        ServerWorld world = (ServerWorld) event.world;
        boolean end = World.THE_END.equals(world.getDimensionKey());
        if (!end && !World.OVERWORLD.equals(world.getDimensionKey())) return;

        // Ordinary plants are cheap: drain several chunks per tick so a player
        // travelling quickly does not outrun the pending landmark queue.
        for (int budget = 8; budget > 0; budget--) {
            PendingChunk task = nextFor(end, world.getGameTime());
            if (task == null) return;
            long chunkKey = ChunkPos.asLong(task.chunkX, task.chunkZ);
            String pendingKey = (end ? "E:" : "O:") + chunkKey;
            pendingKeys.remove(pendingKey);
            String dataName = end ? GeneratedData.END_NAME : GeneratedData.OVERWORLD_NAME;
            GeneratedData data = world.getSavedData().getOrCreate(() -> new GeneratedData(dataName), dataName);
            if (data.contains(chunkKey)) continue;

            BlockPos center = new BlockPos((task.chunkX << 4) + 8, 64, (task.chunkZ << 4) + 8);
            if (!world.isAreaLoaded(center, 2)) {
                if (pendingKeys.add(pendingKey)) pending.add(task.later(world.getGameTime() + 100L));
                continue;
            }

            generating = true;
            try {
                boolean placed = build(world, task);
                data.mark(chunkKey);
                if (!end && placed) data.setRecentlyPlaced(50);
            } finally {
                generating = false;
                cooldown = task.kind < 0 ? 0 : 5;
            }
            if (task.kind >= 0) return;
        }
    }

    private PendingChunk nextFor(boolean end, long time) {
        int count = pending.size();
        while (count-- > 0) {
            PendingChunk task = pending.poll();
            if (task == null) return null;
            if (task.end == end && task.readyAt <= time) return task;
            pending.add(task);
        }
        return null;
    }

    private static boolean build(ServerWorld world, PendingChunk task) {
        Random random = new Random(task.seed);
        int x = (task.chunkX << 4) + 4 + random.nextInt(8);
        int z = (task.chunkZ << 4) + 4 + random.nextInt(8);
        int surfaceY = world.getHeight(Heightmap.Type.WORLD_SURFACE, x, z) - 1;
        BlockPos ground = new BlockPos(x, surfaceY, z);
        boolean originalSite = task.end || task.kind < 0 || isOriginalSite(world, task.kind, ground);
        if (!task.end) OreSpawnOverworldFlora.populate(world, task.chunkX, task.chunkZ, task.seed);
        if (task.kind < 0 || surfaceY < 40 || surfaceY > 100 || !originalSite) return false;

        if (task.end) {
            ExtremeDimensionStructureBuilder.buildEnderKnightDungeon(world, random,
                new BlockPos(x, surfaceY, z));
            return true;
        }
        OreSpawnOverworldStructureBuilder.build(world, random, ground, task.kind);
        return true;
    }

    private static boolean isOriginalSite(ServerWorld world, int kind, BlockPos ground) {
        Biome biome = world.getBiome(ground);
        if (biome.getRegistryName() == null) return false;
        String name = biome.getRegistryName().getPath();
        BlockState block = world.getBlockState(ground);
        if (kind <= 5) return name.equals("ocean") && block.isIn(Blocks.WATER);
        if (kind == 6 || kind == 10 || kind == 14)
            return name.equals("plains") && block.isIn(Blocks.GRASS_BLOCK);
        if (kind == 7 || kind == 8)
            return (name.equals("forest") || name.equals("wooded_hills")
                || name.equals("jungle") || name.equals("jungle_hills")
                || name.equals("birch_forest") || name.equals("birch_forest_hills"))
                && block.isIn(Blocks.GRASS_BLOCK);
        if (kind == 9)
            return (name.equals("plains") || name.equals("taiga") || name.equals("swamp"))
                && block.isIn(Blocks.GRASS_BLOCK);
        if (kind == 11) return name.equals("swamp") && block.isIn(Blocks.GRASS_BLOCK);
        if (kind == 12) return name.equals("snowy_tundra") && block.isIn(Blocks.SNOW_BLOCK);
        return kind == 13 && name.equals("desert") && block.isIn(Blocks.SAND);
    }

    private static long mix(long seed, int chunkX, int chunkZ, boolean end) {
        long value = seed ^ SALT ^ (end ? 0x454E4444494D4CL : 0L)
            ^ (long) chunkX * 341873128712L ^ (long) chunkZ * 132897987541L;
        value ^= value >>> 33;
        value *= 0xff51afd7ed558ccdl;
        value ^= value >>> 33;
        value *= 0xc4ceb9fe1a85ec53l;
        return value ^ value >>> 33;
    }

    private static final class PendingChunk {
        final int chunkX, chunkZ, kind;
        final long seed, readyAt;
        final boolean end;

        PendingChunk(int chunkX, int chunkZ, long seed, int kind, boolean end, long readyAt) {
            this.chunkX = chunkX;
            this.chunkZ = chunkZ;
            this.seed = seed;
            this.kind = kind;
            this.end = end;
            this.readyAt = readyAt;
        }

        PendingChunk later(long time) {
            return new PendingChunk(chunkX, chunkZ, seed, kind, end, time);
        }
    }

    public static final class GeneratedData extends WorldSavedData {
        static final String OVERWORLD_NAME = "xillys_orespawn_overworld_structures";
        static final String END_NAME = "xillys_orespawn_end_structures";
        private final Set<Long> generated = new HashSet<>();
        private int recentlyPlaced = 50;

        public GeneratedData(String name) { super(name); }
        boolean contains(long key) { return generated.contains(key); }
        void mark(long key) { if (generated.add(key)) markDirty(); }
        void setRecentlyPlaced(int chunks) { recentlyPlaced = chunks; markDirty(); }
        void advance() { if (recentlyPlaced > 0) { recentlyPlaced--; markDirty(); } }

        @Override public void read(CompoundNBT nbt) {
            generated.clear();
            recentlyPlaced = nbt.contains("RecentlyPlaced") ? nbt.getInt("RecentlyPlaced") : 50;
            for (long value : nbt.getLongArray("GeneratedChunks")) generated.add(value);
        }

        @Override public CompoundNBT write(CompoundNBT nbt) {
            long[] values = new long[generated.size()];
            int index = 0;
            for (Long value : generated) values[index++] = value;
            nbt.putLongArray("GeneratedChunks", values);
            nbt.putInt("RecentlyPlaced", recentlyPlaced);
            return nbt;
        }
    }
}
