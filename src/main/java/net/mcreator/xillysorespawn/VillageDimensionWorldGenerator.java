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

/** Delayed deterministic population pass for Dimension-VillageMania. */
@XillysOrespawnModElements.ModElement.Tag
public class VillageDimensionWorldGenerator extends XillysOrespawnModElements.ModElement {
    private static final long SALT = 0x5241494E424F5733L;
    private static final int DUNGEON = 2;
    private static final int FRUIT_TREES = 4;
    private static final int DAMSEL = 8;
    private static final int SPIDER_HANGOUT = 16;
    private static final int RED_ANT_HANGOUT = 32;

    private final Queue<PendingChunk> pending = new ConcurrentLinkedQueue<>();
    private final Set<Long> pendingKeys = ConcurrentHashMap.newKeySet();
    private boolean generating;
    private int cooldown;

    public VillageDimensionWorldGenerator(XillysOrespawnModElements instance) {
        super(instance, 1);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onChunkLoad(ChunkEvent.Load event) {
        if (generating || !(event.getWorld() instanceof ServerWorld)) return;
        ServerWorld world = (ServerWorld) event.getWorld();
        if (!VillageDimension.VILLAGE_WORLD.equals(world.getDimensionKey())) return;
        ChunkPos pos = event.getChunk().getPos();
        long key = pos.asLong();
        GeneratedData data = world.getSavedData().getOrCreate(GeneratedData::new, GeneratedData.NAME);
        if (data.contains(key) || !pendingKeys.add(key)) return;

        long seed = mix(world.getSeed(), pos.x, pos.z);
        Random random = new Random(seed);
        int mask = 0;
        if (random.nextInt(16) == 0) mask |= DUNGEON;
        if (random.nextInt(3) == 0) mask |= FRUIT_TREES;

        // Original probabilities from ChunkProviderOreSpawn3. Only one landmark is
        // selected, reproducing the old recently_placed separation behaviour.
        if (random.nextInt(250) == 1) mask |= DAMSEL;
        else if (random.nextInt(350) == 1) mask |= SPIDER_HANGOUT;
        else if (random.nextInt(250) == 1) mask |= RED_ANT_HANGOUT;

        if (mask == 0) {
            data.mark(key);
            pendingKeys.remove(key);
            return;
        }
        pending.add(new PendingChunk(pos.x, pos.z, seed, mask, world.getGameTime() + 10L));
    }

    @SubscribeEvent
    public void onWorldTick(TickEvent.WorldTickEvent event) {
        if (event.phase != TickEvent.Phase.END || event.world.isRemote
            || !(event.world instanceof ServerWorld)
            || !VillageDimension.VILLAGE_WORLD.equals(event.world.getDimensionKey())) return;
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
        int radius = (task.mask & (DAMSEL | SPIDER_HANGOUT | RED_ANT_HANGOUT)) != 0 ? 20 : 8;
        if (!world.isAreaLoaded(center, radius)) {
            if (pendingKeys.add(key)) pending.add(task.later(world.getGameTime() + 80L));
            return;
        }

        // Mark first: structures touch neighbouring chunks and can fire nested load events.
        data.mark(key);
        generating = true;
        try {
            build(world, task);
        } finally {
            generating = false;
            cooldown = 1;
        }
    }

    private static void build(ServerWorld world, PendingChunk task) {
        Random random = new Random(task.seed);
        int x = (task.chunkX << 4) + 4 + random.nextInt(8);
        int z = (task.chunkZ << 4) + 4 + random.nextInt(8);
        int surfaceY = findGround(world, x, z);
        if (surfaceY < 40 || surfaceY > world.getHeight() - 16) return;
        BlockPos ground = new BlockPos(x, surfaceY, z);

        if ((task.mask & DUNGEON) != 0) {
            int y = 8 + random.nextInt(Math.max(1, Math.min(35, surfaceY - 14)));
            VillageDimensionStructureBuilder.buildGenericDungeon(world, random, new BlockPos(x, y, z));
        }
        if ((task.mask & DAMSEL) != 0) {
            VillageDimensionStructureBuilder.buildDamselInDistress(world, random, ground);
        } else if ((task.mask & SPIDER_HANGOUT) != 0) {
            VillageDimensionStructureBuilder.buildSpiderHangout(world, random, ground);
        } else if ((task.mask & RED_ANT_HANGOUT) != 0) {
            VillageDimensionStructureBuilder.buildRedAntHangout(world, random, ground);
        }
        if ((task.mask & FRUIT_TREES) != 0) {
            int count = 1 + random.nextInt(3);
            for (int i = 0; i < count; i++) {
                int tx = (task.chunkX << 4) + 2 + random.nextInt(12);
                int tz = (task.chunkZ << 4) + 2 + random.nextInt(12);
                int ty = findGround(world, tx, tz);
                if (ty >= 40) VillageDimensionStructureBuilder.buildFruitTree(world, random,
                    new BlockPos(tx, ty, tz));
            }
        }
    }

    private static int findGround(ServerWorld world, int x, int z) {
        int y = world.getHeight(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, x, z) - 1;
        while (y >= 40) {
            net.minecraft.block.Block block = world.getBlockState(new BlockPos(x, y, z)).getBlock();
            if (block == net.minecraft.block.Blocks.GRASS_BLOCK
                || block == net.minecraft.block.Blocks.DIRT
                || block == net.minecraft.block.Blocks.COARSE_DIRT
                || block == net.minecraft.block.Blocks.PODZOL) return y;
            y--;
        }
        return -1;
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
        final int chunkX, chunkZ, mask;
        final long seed, readyAt;

        PendingChunk(int chunkX, int chunkZ, long seed, int mask, long readyAt) {
            this.chunkX = chunkX;
            this.chunkZ = chunkZ;
            this.seed = seed;
            this.mask = mask;
            this.readyAt = readyAt;
        }

        PendingChunk later(long time) { return new PendingChunk(chunkX, chunkZ, seed, mask, time); }
    }

    public static final class GeneratedData extends WorldSavedData {
        static final String NAME = "xillys_orespawn_village_dimension_structures";
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
