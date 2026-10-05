package net.mcreator.xillysorespawn.world.dimension;

import net.mcreator.xillysorespawn.XillysOrespawnModElements;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.gen.Heightmap;
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

/** Budgeted replacement for ChunkProviderOreSpawn5 and its OreSpawnWorld population pass. */
@XillysOrespawnModElements.ModElement.Tag
public class CrystalDimensionWorldGenerator extends XillysOrespawnModElements.ModElement {
    private static final long SALT = 0x4352595354414C35L;
    private final Queue<PendingChunk> pending = new ConcurrentLinkedQueue<>();
    private final Set<Long> pendingKeys = ConcurrentHashMap.newKeySet();
    private boolean generating;

    public CrystalDimensionWorldGenerator(XillysOrespawnModElements instance) {
        super(instance, 1);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onWorldUnload(net.minecraftforge.event.world.WorldEvent.Unload event) {
        if (event.getWorld() instanceof ServerWorld && CrystalDimension.CRYSTAL_WORLD.equals(
                ((ServerWorld) event.getWorld()).getDimensionKey())) {
            pending.clear();
            pendingKeys.clear();
        }
    }

    @SubscribeEvent
    public void onChunkLoad(ChunkEvent.Load event) {
        if (generating || !(event.getWorld() instanceof ServerWorld)) return;
        ServerWorld world = (ServerWorld) event.getWorld();
        if (!CrystalDimension.CRYSTAL_WORLD.equals(world.getDimensionKey())) return;
        ChunkPos pos = event.getChunk().getPos();
        long key = pos.asLong();
        GeneratedData data = world.getSavedData().getOrCreate(GeneratedData::new, GeneratedData.NAME);
        if (data.contains(key) || !pendingKeys.add(key)) return;

        long seed = mix(world.getSeed(), pos.x, pos.z);
        Random random = new Random(seed ^ 0x535452554354L);
        boolean fairyTree = random.nextInt(5) == 0;
        boolean fairyCastle = fairyTree && random.nextInt(5) == 1;
        int major = -1;
        if (!fairyTree) {
            if (random.nextInt(150) == 0) major = 0;
            else if (random.nextInt(180) == 0) major = 1;
            else if (random.nextInt(230) == 0) major = 2;
            else if (random.nextInt(150) == 0) major = 3;
            else if (random.nextInt(280) == 0) major = 4;
        }
        boolean irukandji = !fairyTree && random.nextInt(80) == 0;
        pending.add(new PendingChunk(pos.x, pos.z, seed, fairyTree, fairyCastle,
            major, irukandji, world.getGameTime() + 5L));
    }

    @SubscribeEvent
    public void onWorldTick(TickEvent.WorldTickEvent event) {
        if (event.phase != TickEvent.Phase.END || event.world.isRemote
            || !(event.world instanceof ServerWorld)
            || !CrystalDimension.CRYSTAL_WORLD.equals(event.world.getDimensionKey()) || generating) return;
        ServerWorld world = (ServerWorld) event.world;

        PendingChunk task = pending.peek();
        if (task == null || task.readyAt > world.getGameTime()) return;
        pending.poll();
        long key = ChunkPos.asLong(task.chunkX, task.chunkZ);
        pendingKeys.remove(key);
        GeneratedData data = world.getSavedData().getOrCreate(GeneratedData::new, GeneratedData.NAME);
        if (data.contains(key)) return;

        BlockPos center = new BlockPos((task.chunkX << 4) + 8, 64, (task.chunkZ << 4) + 8);
        int radius = task.fairyTree || task.major >= 0 ? 24 : 8;
        if (!world.isAreaLoaded(center, radius)) {
            if (pendingKeys.add(key)) pending.add(task.later(world.getGameTime() + 60L));
            return;
        }

        generating = true;
        try {
            CrystalDimensionStructureBuilder.buildMaze(world, new Random(task.seed ^ 0x4D415A45L), task.chunkX, task.chunkZ);
            CrystalDimensionStructureBuilder.buildSurfaceDecorations(world,
                new Random(task.seed ^ 0x4445434F52L), task.chunkX, task.chunkZ);

            int x = (task.chunkX << 4) + 8, z = (task.chunkZ << 4) + 8;
            BlockPos surface = world.getHeight(Heightmap.Type.WORLD_SURFACE, new BlockPos(x, 0, z));
            if (task.fairyTree && surface.getY() > 40)
                CrystalDimensionStructureBuilder.buildFairyTree(world,
                    new Random(task.seed ^ 0x4641495259L), surface, task.fairyCastle);
            else if (task.major >= 0 && surface.getY() > 40)
                CrystalDimensionStructureBuilder.buildMajor(world,
                    new Random(task.seed ^ 0x4D414A4F52L), surface, task.major);
            if (task.irukandji) {
                Random waterRandom = new Random(task.seed ^ 0x4952554B41L);
                int wx = (task.chunkX << 4) + waterRandom.nextInt(16);
                int wz = (task.chunkZ << 4) + waterRandom.nextInt(16);
                BlockPos water = world.getHeight(Heightmap.Type.OCEAN_FLOOR, new BlockPos(wx, 0, wz)).up();
                CrystalDimensionStructureBuilder.buildIrukandjiSpawner(world, water);
            }
            data.mark(key);
        } finally {
            generating = false;
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
        final int chunkX, chunkZ, major;
        final long seed, readyAt;
        final boolean fairyTree, fairyCastle, irukandji;
        PendingChunk(int chunkX, int chunkZ, long seed, boolean fairyTree, boolean fairyCastle,
                     int major, boolean irukandji, long readyAt) {
            this.chunkX = chunkX; this.chunkZ = chunkZ; this.seed = seed;
            this.fairyTree = fairyTree; this.fairyCastle = fairyCastle;
            this.major = major; this.irukandji = irukandji; this.readyAt = readyAt;
        }
        PendingChunk later(long time) {
            return new PendingChunk(chunkX, chunkZ, seed, fairyTree, fairyCastle, major, irukandji, time);
        }
    }

    public static final class GeneratedData extends WorldSavedData {
        static final String NAME = "xillys_orespawn_crystal_dimension_structures";
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
