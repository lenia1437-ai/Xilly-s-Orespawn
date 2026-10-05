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

/**
 * Rare Utopia population pass matching OreSpawnWorld.addHugeTree,
 * addOtherTrees and addKingAltar. It is deterministic per world/chunk and its
 * completion markers are saved, so structures are never rebuilt on chunk load.
 */
@XillysOrespawnModElements.ModElement.Tag
public class UtopiaStructureGenerator extends XillysOrespawnModElements.ModElement {
    private static final long SALT = 0x4F7265537061776EL;
    private final Queue<PendingChunk> pending = new ConcurrentLinkedQueue<>();
    private final Set<Long> pendingKeys = ConcurrentHashMap.newKeySet();
    private boolean generating;
    private int generationCooldown;

    public UtopiaStructureGenerator(XillysOrespawnModElements instance) {
        super(instance, 1);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onChunkLoad(ChunkEvent.Load event) {
        if (generating || !(event.getWorld() instanceof ServerWorld)) return;
        ServerWorld world = (ServerWorld) event.getWorld();
        if (!UtopiaDimension.UTOPIA_WORLD.equals(world.getDimensionKey())) return;

        ChunkPos chunk = event.getChunk().getPos();
        long chunkKey = chunk.asLong();
        GeneratedData data = world.getSavedData().getOrCreate(GeneratedData::new, GeneratedData.NAME);
        if (data.contains(chunkKey)) return;

        long mixedSeed = mix(world.getSeed(), chunk.x, chunk.z);
        if (!isStructureCandidate(mixedSeed) || !pendingKeys.add(chunkKey)) return;

        // Never place blocks from ChunkEvent.Load. Big trees and 51x51 altars
        // touch neighbouring chunks; doing that here recursively fires more
        // ChunkEvent.Load events and can deadlock the teleport/world load.
        pending.add(new PendingChunk(chunk.x, chunk.z, mixedSeed, world.getGameTime() + 40L));
    }

    @SubscribeEvent
    public void onWorldTick(TickEvent.WorldTickEvent event) {
        if (event.phase != TickEvent.Phase.END || event.world.isRemote
            || !(event.world instanceof ServerWorld)
            || !UtopiaDimension.UTOPIA_WORLD.equals(event.world.getDimensionKey())) return;
        if (generating || generationCooldown-- > 0) return;

        ServerWorld world = (ServerWorld) event.world;
        PendingChunk task = pending.peek();
        if (task == null || task.readyAt > world.getGameTime()) return;
        pending.poll();
        pendingKeys.remove(ChunkPos.asLong(task.chunkX, task.chunkZ));

        BlockPos chunkCenter = new BlockPos((task.chunkX << 4) + 8, 64, (task.chunkZ << 4) + 8);
        if (!world.isAreaLoaded(chunkCenter, 0)) return;
        GeneratedData data = world.getSavedData().getOrCreate(GeneratedData::new, GeneratedData.NAME);
        long chunkKey = ChunkPos.asLong(task.chunkX, task.chunkZ);
        if (data.contains(chunkKey)) return;

        generating = true;
        try {
            generateForChunk(world, task, data, chunkKey);
        } finally {
            generating = false;
            // One large structure at most per second. This prevents a newly
            // entered dimension from constructing every spawn-area candidate
            // in the same server tick.
            generationCooldown = 20;
        }
    }

    private static boolean isStructureCandidate(long seed) {
        Random random = new Random(seed);
        if (random.nextInt(50) == 0) return true;
        if (random.nextInt(30) == 0) return true;
        return random.nextInt(2000) == 1;
    }

    private static void generateForChunk(ServerWorld world, PendingChunk task, GeneratedData data, long chunkKey) {
        Random random = new Random(task.seed);
        int baseX = task.chunkX << 4;
        int baseZ = task.chunkZ << 4;

        // Original addHugeTree: 1/50 chunks, one large tree attempt.
        if (random.nextInt(50) == 0) {
            int x = baseX + 4 + random.nextInt(8);
            int z = baseZ + 4 + random.nextInt(8);
            BlockPos ground = surfaceGround(world, x, z);
            if (ground != null && UtopiaTreeBuilder.buildHugeTree(world, random, ground)) data.mark(chunkKey);
            return;
        }

        // Original addOtherTrees: a rare group of wind or sky trees.
        if (random.nextInt(30) == 0) {
            boolean sky = random.nextBoolean();
            boolean made = false;
            int attempts = sky ? 3 : 5;
            for (int i = 0; i < attempts; i++) {
                int x = baseX + 3 + random.nextInt(10);
                int z = baseZ + 3 + random.nextInt(10);
                BlockPos ground = surfaceGround(world, x, z);
                if (ground == null) continue;
                made |= sky
                    ? UtopiaTreeBuilder.buildSkyTree(world, random, ground)
                    : UtopiaTreeBuilder.buildWindTree(world, random, ground, random.nextInt(4));
                if (sky && made) break;
            }
            if (made) data.mark(chunkKey);
            return;
        }

        // Original addKingAltar: 1/2000 and then an even King/Queen choice.
        if (random.nextInt(2000) == 1) {
            int x = baseX + 3 + random.nextInt(10);
            int z = baseZ + 3 + random.nextInt(10);
            BlockPos ground = surfaceGround(world, x, z);
            if (ground != null && ground.getY() > 50 && ground.getY() <= 100
                && UtopiaAltarBuilder.hasSpace(world, ground)) {
                // Mark before touching neighbouring chunks, preventing recursive generation.
                data.mark(chunkKey);
                UtopiaAltarBuilder.build(world, ground, random.nextBoolean());
            }
        }
    }

    private static BlockPos surfaceGround(ServerWorld world, int x, int z) {
        int y = world.getHeight(Heightmap.Type.WORLD_SURFACE, x, z) - 1;
        if (y < 1 || y >= world.getHeight() - 2) return null;
        BlockPos ground = new BlockPos(x, y, z);
        if (world.getBlockState(ground).getBlock() != net.minecraft.block.Blocks.GRASS_BLOCK) return null;
        return ground;
    }

    private static long mix(long seed, int chunkX, int chunkZ) {
        long value = seed ^ SALT;
        value ^= (long) chunkX * 341873128712L;
        value ^= (long) chunkZ * 132897987541L;
        value ^= value >>> 33;
        value *= 0xff51afd7ed558ccdl;
        value ^= value >>> 33;
        value *= 0xc4ceb9fe1a85ec53l;
        return value ^ value >>> 33;
    }

    private static final class PendingChunk {
        final int chunkX;
        final int chunkZ;
        final long seed;
        final long readyAt;

        PendingChunk(int chunkX, int chunkZ, long seed, long readyAt) {
            this.chunkX = chunkX;
            this.chunkZ = chunkZ;
            this.seed = seed;
            this.readyAt = readyAt;
        }
    }

    public static final class GeneratedData extends WorldSavedData {
        static final String NAME = "xillys_orespawn_utopia_structures";
        private final Set<Long> generatedChunks = new HashSet<>();

        public GeneratedData() {
            super(NAME);
        }

        boolean contains(long chunk) {
            return generatedChunks.contains(chunk);
        }

        void mark(long chunk) {
            if (generatedChunks.add(chunk)) markDirty();
        }

        @Override
        public void read(CompoundNBT nbt) {
            generatedChunks.clear();
            for (long value : nbt.getLongArray("GeneratedChunks")) generatedChunks.add(value);
        }

        @Override
        public CompoundNBT write(CompoundNBT nbt) {
            long[] values = new long[generatedChunks.size()];
            int index = 0;
            for (Long value : generatedChunks) values[index++] = value;
            nbt.putLongArray("GeneratedChunks", values);
            return nbt;
        }
    }
}
