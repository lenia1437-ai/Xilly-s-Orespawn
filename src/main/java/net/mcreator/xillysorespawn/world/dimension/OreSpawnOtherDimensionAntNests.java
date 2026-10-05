package net.mcreator.xillysorespawn.world.dimension;

import net.mcreator.xillysorespawn.XillysOrespawnModElements;
import net.mcreator.xillysorespawn.block.AntNestBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
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

/** OreSpawnWorld ant-nest pass for the custom dimensions that lacked one. */
@XillysOrespawnModElements.ModElement.Tag
public class OreSpawnOtherDimensionAntNests extends XillysOrespawnModElements.ModElement {
    private final Queue<Task> pending = new ConcurrentLinkedQueue<>();
    private final Set<String> queued = ConcurrentHashMap.newKeySet();
    private boolean placing;

    public OreSpawnOtherDimensionAntNests(XillysOrespawnModElements instance) {
        super(instance, 1080);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent public void onWorldUnload(WorldEvent.Unload event) {
        if (!(event.getWorld() instanceof ServerWorld)) return;
        String dim = ((ServerWorld) event.getWorld()).getDimensionKey().getLocation().getPath();
        pending.removeIf(task -> task.dim.equals(dim));
        queued.removeIf(key -> key.startsWith(dim + ':'));
    }

    @SubscribeEvent public void onChunkLoad(ChunkEvent.Load event) {
        if (placing || !(event.getWorld() instanceof ServerWorld)) return;
        ServerWorld world = (ServerWorld) event.getWorld();
        String dim = world.getDimensionKey().getLocation().getPath();
        if (!"extreme".equals(dim) && !"village".equals(dim) && !"utopia".equals(dim)
                && !"chaos".equals(dim)) return;
        ChunkPos chunk = event.getChunk().getPos();
        String key = dim + ':' + chunk.asLong();
        String dataName = "xillys_orespawn_ant_nests_" + dim;
        NestData data = world.getSavedData().getOrCreate(() -> new NestData(dataName), dataName);
        if (data.seen(chunk.asLong()) || !queued.add(key)) return;
        long seed = world.getSeed() ^ 0x414E544E4553544CL
            ^ (long) chunk.x * 341873128712L ^ (long) chunk.z * 132897987541L;
        Random random = new Random(seed);
        boolean selected = random.nextInt(30) == 0;
        if ("extreme".equals(dim)) selected |= random.nextInt(30) == 0;
        if (!selected) {
            data.mark(chunk.asLong());
            queued.remove(key);
            return;
        }
        pending.add(new Task(dim, chunk.x, chunk.z, seed, world.getGameTime() + 40));
    }

    @SubscribeEvent public void onTick(TickEvent.WorldTickEvent event) {
        if (placing || event.phase != TickEvent.Phase.END || !(event.world instanceof ServerWorld)) return;
        ServerWorld world = (ServerWorld) event.world;
        String dim = world.getDimensionKey().getLocation().getPath();
        for (int budget = 2, checked = pending.size(); budget > 0 && checked > 0; checked--) {
            Task task = pending.poll();
            if (task == null) return;
            if (!task.dim.equals(dim) || task.readyAt > world.getGameTime()) {
                pending.add(task);
                continue;
            }
            String key = dim + ':' + ChunkPos.asLong(task.x, task.z);
            BlockPos center = new BlockPos((task.x << 4) + 8, 64, (task.z << 4) + 8);
            if (!world.isAreaLoaded(center, 1)) {
                pending.add(task.later(world.getGameTime() + 100));
                continue;
            }
            placing = true;
            try {
                place(world, task);
                String dataName = "xillys_orespawn_ant_nests_" + dim;
                world.getSavedData().getOrCreate(() -> new NestData(dataName), dataName)
                    .mark(ChunkPos.asLong(task.x, task.z));
                queued.remove(key);
            } finally {
                placing = false;
            }
            budget--;
        }
    }

    private static void place(ServerWorld world, Task task) {
        Random random = new Random(task.seed ^ 0x4C4F434154494F4EL);
        for (int i = 0; i < 4; i++) {
            int x = (task.x << 4) + random.nextInt(16);
            int z = (task.z << 4) + random.nextInt(16);
            int y = world.getHeight(Heightmap.Type.WORLD_SURFACE, x, z) - 1;
            if (y < 4 || y > 200) continue;
            BlockPos at = new BlockPos(x, y, z);
            Block block = world.getBlockState(at).getBlock();
            if (block != Blocks.GRASS_BLOCK) continue;
            if (!world.isAirBlock(at.up())) continue;
            int type = random.nextInt("extreme".equals(task.dim) || "chaos".equals(task.dim) ? 2 : 4) == 0
                ? 1 + random.nextInt(4) : 0;
            world.setBlockState(at, AntNestBlock.nest(type), 2);
        }
    }

    private static final class Task {
        final String dim;
        final int x, z;
        final long seed, readyAt;
        Task(String dim, int x, int z, long seed, long readyAt) {
            this.dim = dim; this.x = x; this.z = z; this.seed = seed; this.readyAt = readyAt;
        }
        Task later(long time) { return new Task(dim, x, z, seed, time); }
    }

    public static final class NestData extends WorldSavedData {
        private final Set<Long> chunks = new HashSet<>();
        public NestData(String name) { super(name); }
        boolean seen(long value) { return chunks.contains(value); }
        void mark(long value) { if (chunks.add(value)) markDirty(); }
        @Override public void read(CompoundNBT nbt) {
            chunks.clear();
            for (long value : nbt.getLongArray("Chunks")) chunks.add(value);
        }
        @Override public CompoundNBT write(CompoundNBT nbt) {
            long[] values = new long[chunks.size()];
            int index = 0;
            for (long value : chunks) values[index++] = value;
            nbt.putLongArray("Chunks", values);
            return nbt;
        }
    }
}
