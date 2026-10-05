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

/** Delayed, deterministic population pass for ExtremeDimension structures. */
@XillysOrespawnModElements.ModElement.Tag
public class ExtremeDimensionWorldGenerator extends XillysOrespawnModElements.ModElement {
    private static final long SALT = 0x524544414E543231L;
    private final Queue<PendingChunk> pending = new ConcurrentLinkedQueue<>();
    private final Set<Long> pendingKeys = ConcurrentHashMap.newKeySet();
    private boolean generating;
    private int cooldown;

    public ExtremeDimensionWorldGenerator(XillysOrespawnModElements instance) {
        super(instance, 1);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onChunkLoad(ChunkEvent.Load event) {
        if (generating || !(event.getWorld() instanceof ServerWorld)) return;
        ServerWorld world = (ServerWorld) event.getWorld();
        if (!ExtremeDimension.EXTREME_WORLD.equals(world.getDimensionKey())) return;
        ChunkPos pos = event.getChunk().getPos();
        long key = pos.asLong();
        GeneratedData data = world.getSavedData().getOrCreate(GeneratedData::new, GeneratedData.NAME);
        if (data.contains(key) || !pendingKeys.add(key)) return;

        long seed = mix(world.getSeed(), pos.x, pos.z);
        Random random = new Random(seed);
        boolean major = random.nextInt(95) == 1;
        boolean generic = !major && random.nextInt(16) == 0;
        if (!major && !generic) {
            data.mark(key); // This chunk has been deterministically considered.
            pendingKeys.remove(key);
            return;
        }
        pending.add(new PendingChunk(pos.x, pos.z, seed, major, world.getGameTime() + 40L));
    }

    @SubscribeEvent
    public void onWorldTick(TickEvent.WorldTickEvent event) {
        if (event.phase != TickEvent.Phase.END || event.world.isRemote
            || !(event.world instanceof ServerWorld)
            || !ExtremeDimension.EXTREME_WORLD.equals(event.world.getDimensionKey())) return;
        if (generating || cooldown-- > 0) return;

        ServerWorld world = (ServerWorld) event.world;
        PendingChunk task = pending.peek();
        if (task == null || task.readyAt > world.getGameTime()) return;
        pending.poll();
        long key = ChunkPos.asLong(task.chunkX, task.chunkZ);
        pendingKeys.remove(key);
        GeneratedData data = world.getSavedData().getOrCreate(GeneratedData::new, GeneratedData.NAME);
        if (data.contains(key)) return;

        BlockPos center = new BlockPos((task.chunkX << 4) + 8, 64, (task.chunkZ << 4) + 8);
        if (!world.isAreaLoaded(center, task.major ? 2 : 1)) {
            if (pendingKeys.add(key)) pending.add(task.later(world.getGameTime() + 80L));
            return;
        }

        // Mark first. Large structures touch neighbouring chunks and can cause nested load events.
        data.mark(key);
        generating = true;
        try {
            build(world, task);
        } finally {
            generating = false;
            cooldown = task.major ? 20 : 4;
        }
    }

    private static void build(ServerWorld world, PendingChunk task) {
        Random random = new Random(task.seed);
        int x = (task.chunkX << 4) + 4 + random.nextInt(8);
        int z = (task.chunkZ << 4) + 4 + random.nextInt(8);
        if (!task.major) {
            int y = 8 + random.nextInt(33);
            ExtremeDimensionStructureBuilder.buildGenericDungeon(world, random, new BlockPos(x, y, z));
            return;
        }

        int surfaceY = world.getHeight(Heightmap.Type.WORLD_SURFACE, x, z) - 1;
        if (surfaceY < 40 || surfaceY > world.getHeight() - 35) return;
        BlockPos ground = new BlockPos(x, surfaceY, z);
        switch (random.nextInt(7)) {
            case 0: ExtremeDimensionStructureBuilder.buildBasiliskMaze(world, random, ground); break;
            case 1: ExtremeDimensionStructureBuilder.buildKyuubiDungeon(world, random, ground); break;
            case 2: ExtremeDimensionStructureBuilder.buildBeeHive(world, random, ground); break;
            case 3: ExtremeDimensionStructureBuilder.buildShadowDungeon(world, random, ground); break;
            case 4: ExtremeDimensionStructureBuilder.buildAlienLab(world, random, ground); break;
            case 5: ExtremeDimensionStructureBuilder.buildEnderKnightDungeon(world, random, ground); break;
            default: ExtremeDimensionStructureBuilder.buildLeonNest(world, random, ground); break;
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
        final int chunkX, chunkZ;
        final long seed, readyAt;
        final boolean major;

        PendingChunk(int chunkX, int chunkZ, long seed, boolean major, long readyAt) {
            this.chunkX = chunkX;
            this.chunkZ = chunkZ;
            this.seed = seed;
            this.major = major;
            this.readyAt = readyAt;
        }

        PendingChunk later(long time) { return new PendingChunk(chunkX, chunkZ, seed, major, time); }
    }

    public static final class GeneratedData extends WorldSavedData {
        static final String NAME = "xillys_orespawn_extremedimension_structures";
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
