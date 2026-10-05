package net.mcreator.xillysorespawn.world.dimension;

import net.mcreator.xillysorespawn.XillysOrespawnModElements;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.HashSet;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/** Delayed deterministic population pass for OreSpawn's Dimension-Islands. */
@XillysOrespawnModElements.ModElement.Tag
public class IslandsDimensionWorldGenerator extends XillysOrespawnModElements.ModElement {
    private static final long SALT = 0x49534C414E445334L;
    private static final int ANT_NESTS = 1;
    private static final int ISLANDS = 2;
    private static final int ROCKS = 4;
    private static final int CLOUD_SHARK = 8;

    private final Queue<PendingChunk> pending = new ConcurrentLinkedQueue<>();
    private final Set<Long> pendingKeys = ConcurrentHashMap.newKeySet();
    private boolean generating;
    private IslandsLegacyCastles.Plan castlePlan;
    private long castleChunk;

    public IslandsDimensionWorldGenerator(XillysOrespawnModElements instance) {
        super(instance, 1);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onWorldUnload(net.minecraftforge.event.world.WorldEvent.Unload event) {
        if (event.getWorld() instanceof ServerWorld && IslandsDimension.ISLANDS_WORLD.equals(
                ((ServerWorld) event.getWorld()).getDimensionKey())) {
            pending.clear();
            pendingKeys.clear();
            castlePlan = null;
        }
    }

    @SubscribeEvent
    public void onChunkLoad(ChunkEvent.Load event) {
        if (generating || !(event.getWorld() instanceof ServerWorld)) return;
        ServerWorld world = (ServerWorld) event.getWorld();
        if (!IslandsDimension.ISLANDS_WORLD.equals(world.getDimensionKey())) return;
        ChunkPos pos = event.getChunk().getPos();
        long key = pos.asLong();
        GeneratedData data = world.getSavedData().getOrCreate(GeneratedData::new, GeneratedData.NAME);
        if (data.contains(key) || !pendingKeys.add(key)) return;

        long seed = mix(world.getSeed(), pos.x, pos.z);
        Random random = new Random(seed);
        int mask = 0;
        if (random.nextInt(30) == 0) mask |= ANT_NESTS;
        if (random.nextInt(40) == 0) mask |= ISLANDS;
        if (random.nextInt(7) == 0) mask |= ROCKS;
        if (random.nextInt(300) == 0) mask |= CLOUD_SHARK;
        int major = random.nextInt(100) == 0 ? random.nextInt(19) : -1;
        int trees = random.nextInt(3) == 0 ? 1 + random.nextInt(3) : 0;
        pending.add(new PendingChunk(pos.x, pos.z, seed, mask, major, trees, world.getGameTime() + 5L));
    }

    @SubscribeEvent
    public void onWorldTick(TickEvent.WorldTickEvent event) {
        if (event.phase != TickEvent.Phase.END || event.world.isRemote
            || !(event.world instanceof ServerWorld)
            || !IslandsDimension.ISLANDS_WORLD.equals(event.world.getDimensionKey()) || generating) return;

        ServerWorld world = (ServerWorld) event.world;
        if (castlePlan != null) {
            if (castlePlan.apply(world, 1500)) {
                world.getSavedData().getOrCreate(GeneratedData::new, GeneratedData.NAME).mark(castleChunk);
                pendingKeys.remove(castleChunk);
                castlePlan = null;
            }
            return;
        }
        int allowance = 2;
        while (allowance-- > 0) {
            PendingChunk task = pending.peek();
            if (task == null || task.readyAt > world.getGameTime()) return;
            pending.poll();
            long key = ChunkPos.asLong(task.chunkX, task.chunkZ);
            pendingKeys.remove(key);
            GeneratedData data = world.getSavedData().getOrCreate(GeneratedData::new, GeneratedData.NAME);
            if (data.contains(key)) continue;

            BlockPos center = new BlockPos((task.chunkX << 4) + 8, 5, (task.chunkZ << 4) + 8);
            int radius = task.major >= 0 && task.major < 3 ? 64 : task.major >= 0 ? 32 : (task.mask & ISLANDS) != 0 ? 20 : 8;
            if (!world.isAreaLoaded(center, radius)) {
                if (pendingKeys.add(key)) pending.add(task.later(world.getGameTime() + 60L));
                continue;
            }

            // Do not recursively enqueue chunks while placing a landmark.
            generating = true;
            try {
                build(world, task);
                if (task.major >= 0 && task.major < 3 || task.major == 7) {
                    // A local seed keeps the original random choice reproducible after a restart.
                    castlePlan = IslandsLegacyCastles.create(new Random(task.seed ^ 0x434153544C45L),
                        center.down(), task.major == 7);
                    castleChunk = key;
                    pendingKeys.add(key);
                } else data.mark(key);
            } finally {
                generating = false;
            }
            if (task.major >= 0) return; // only one expensive build in a tick
        }
    }

    private static void build(ServerWorld world, PendingChunk task) {
        Random random = new Random(task.seed);
        int chunkX = task.chunkX << 4, chunkZ = task.chunkZ << 4;

        // Reduced to an average of 2/3 tree per chunk, requested for the Islands forest.
        for (int i = 0; i < task.trees; i++) {
            int x = chunkX + 2 + random.nextInt(12);
            int z = chunkZ + 2 + random.nextInt(12);
            IslandsDimensionStructureBuilder.buildScragglyTree(world, random, new BlockPos(x, 4, z));
        }

        int x = chunkX + 4 + random.nextInt(8);
        int z = chunkZ + 4 + random.nextInt(8);
        BlockPos ground = new BlockPos(x, 4, z);
        if ((task.mask & ANT_NESTS) != 0) IslandsDimensionStructureBuilder.buildAntNests(world, random, ground);
        if ((task.mask & ROCKS) != 0) IslandsDimensionStructureBuilder.buildRockField(world, random, ground);
        if ((task.mask & ISLANDS) != 0) {
            int count = 1;
            for (int i = 0; i < count; i++) {
                int ix = chunkX + 3 + random.nextInt(10) + (i - 1) * 6;
                int iz = chunkZ + 3 + random.nextInt(10) - (i - 1) * 6;
                int iy = 20 + random.nextInt(64);
                IslandsDimensionStructureBuilder.buildFloatingIsland(world, random, new BlockPos(ix, iy, iz));
            }
        }
        if (task.major >= 3 && task.major != 7) IslandsDimensionStructureBuilder.buildMajor(world, random, ground, task.major);
        if ((task.mask & CLOUD_SHARK) != 0) {
            IslandsDimensionStructureBuilder.buildCloudSharkDungeon(world, random,
                new BlockPos(x, 150 + random.nextInt(40), z));
        }
    }

    private static long mix(long seed, int chunkX, int chunkZ) {
        long value = seed ^ SALT ^ (long) chunkX * 341873128712L ^ (long) chunkZ * 132897987541L;
        value ^= value >>> 33;
        value *= 0xff51afd7ed558ccdl;
        value ^= value >>> 33;
        value *= 0xc4ceb9fe1a85ec53l;
        return value ^ value >>> 33;
    }

    private static final class PendingChunk {
        final int chunkX, chunkZ, mask, major, trees;
        final long seed, readyAt;

        PendingChunk(int chunkX, int chunkZ, long seed, int mask, int major, int trees, long readyAt) {
            this.chunkX = chunkX;
            this.chunkZ = chunkZ;
            this.seed = seed;
            this.mask = mask;
            this.major = major;
            this.trees = trees;
            this.readyAt = readyAt;
        }

        PendingChunk later(long time) {
            return new PendingChunk(chunkX, chunkZ, seed, mask, major, trees, time);
        }
    }

    public static final class GeneratedData extends WorldSavedData {
        static final String NAME = "xillys_orespawn_islands_dimension_structures";
        private final Set<Long> generated = new HashSet<>();

        public GeneratedData() { super(NAME); }
        boolean contains(long key) { return generated.contains(key); }
        void mark(long key) { if (generated.add(key)) markDirty(); }

        @Override public void read(CompoundNBT nbt) {
            generated.clear();
            for (long value : nbt.getLongArray("GeneratedChunks")) generated.add(value);
        }

        @Override public CompoundNBT write(CompoundNBT nbt) {
            long[] values = new long[generated.size()];
            int i = 0;
            for (Long value : generated) values[i++] = value;
            nbt.putLongArray("GeneratedChunks", values);
            return nbt;
        }
    }
}
