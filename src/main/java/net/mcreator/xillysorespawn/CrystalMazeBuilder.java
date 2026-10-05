package net.mcreator.xillysorespawn.world.dimension;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/** The 4x4-cell, one-chunk maze layer generated at y=25 in every original Crystal chunk. */
final class CrystalMazeBuilder {
    private static final int TOP = 1, RIGHT = 2, BOTTOM = 4, LEFT = 8;
    private CrystalMazeBuilder() {}

    static void build(ServerWorld world, Random random, int chunkX, int chunkZ, BlockState crystalStone) {
        int ox = chunkX << 4, oz = chunkZ << 4, y = 25;
        for (int x = 0; x < 16; x++) for (int z = 0; z < 16; z++)
            for (int dy = 0; dy < 3; dy++) set(world, ox + x, y + dy, oz + z, Blocks.AIR.getDefaultState());

        int[][] walls = makeMaze(random);
        for (int cx = 0; cx < 4; cx++) for (int cz = 0; cz < 4; cz++) {
            int value = walls[cx][cz];
            if ((value & TOP) != 0) line(world, ox + cx * 4, y, oz + cz * 4, ox + (cx + 1) * 4, oz + cz * 4);
            if ((value & RIGHT) != 0) line(world, ox + (cx + 1) * 4 - 1, y, oz + cz * 4,
                ox + (cx + 1) * 4 - 1, oz + (cz + 1) * 4);
            if ((value & BOTTOM) != 0) line(world, ox + cx * 4, y, oz + (cz + 1) * 4 - 1,
                ox + (cx + 1) * 4, oz + (cz + 1) * 4 - 1);
            if ((value & LEFT) != 0) line(world, ox + cx * 4, y, oz + cz * 4, ox + cx * 4, oz + (cz + 1) * 4);
        }

        // Original openCrystalMaze removed all outside walls and sealed the layer
        // between two bedrock sheets. Five crystal-stone plugs are its entrances.
        for (int i = 0; i < 16; i++) for (int dy = 0; dy < 3; dy++) {
            set(world, ox, y + dy, oz + i, Blocks.AIR.getDefaultState());
            set(world, ox + i, y + dy, oz, Blocks.AIR.getDefaultState());
            set(world, ox + 15, y + dy, oz + i, Blocks.AIR.getDefaultState());
            set(world, ox + i, y + dy, oz + 15, Blocks.AIR.getDefaultState());
        }
        for (int x = 0; x < 16; x++) for (int z = 0; z < 16; z++) {
            set(world, ox + x, y - 1, oz + z, Blocks.BEDROCK.getDefaultState());
            set(world, ox + x, y + 3, oz + z, Blocks.BEDROCK.getDefaultState());
        }
        for (int i = 0; i < 4; i++)
            set(world, ox + random.nextInt(16), y + 3, oz + random.nextInt(16), crystalStone);
        set(world, ox + random.nextInt(16), y - 1, oz + random.nextInt(16), crystalStone);
    }

    private static int[][] makeMaze(Random random) {
        int[][] walls = new int[4][4];
        boolean[][] inside = new boolean[4][4];
        boolean[][] queued = new boolean[4][4];
        for (int x = 0; x < 4; x++) for (int z = 0; z < 4; z++) walls[x][z] = 15;
        List<Integer> frontier = new ArrayList<>();
        int sx = random.nextInt(4), sz = random.nextInt(4);
        inside[sx][sz] = true;
        addFrontier(sx, sz, inside, queued, frontier);
        while (!frontier.isEmpty()) {
            int index = random.nextInt(frontier.size());
            int packed = frontier.remove(index);
            int x = packed & 15, z = packed >>> 4;
            queued[x][z] = false;
            List<Integer> neighbours = new ArrayList<>(4);
            if (z > 0 && inside[x][z - 1]) neighbours.add(TOP);
            if (x < 3 && inside[x + 1][z]) neighbours.add(RIGHT);
            if (z < 3 && inside[x][z + 1]) neighbours.add(BOTTOM);
            if (x > 0 && inside[x - 1][z]) neighbours.add(LEFT);
            int direction = neighbours.get(random.nextInt(neighbours.size()));
            walls[x][z] ^= direction;
            if (direction == TOP) walls[x][z - 1] ^= BOTTOM;
            else if (direction == RIGHT) walls[x + 1][z] ^= LEFT;
            else if (direction == BOTTOM) walls[x][z + 1] ^= TOP;
            else walls[x - 1][z] ^= RIGHT;
            inside[x][z] = true;
            addFrontier(x, z, inside, queued, frontier);
        }
        return walls;
    }

    private static void addFrontier(int x, int z, boolean[][] inside, boolean[][] queued, List<Integer> out) {
        add(x, z - 1, inside, queued, out);
        add(x + 1, z, inside, queued, out);
        add(x, z + 1, inside, queued, out);
        add(x - 1, z, inside, queued, out);
    }

    private static void add(int x, int z, boolean[][] inside, boolean[][] queued, List<Integer> out) {
        if (x < 0 || x > 3 || z < 0 || z > 3 || inside[x][z] || queued[x][z]) return;
        queued[x][z] = true;
        out.add(x | z << 4);
    }

    private static void line(ServerWorld world, int x1, int y, int z1, int x2, int z2) {
        int chunkX = Math.floorDiv(x1, 16), chunkZ = Math.floorDiv(z1, 16);
        if (x1 == x2) for (int z = Math.min(z1, z2); z <= Math.max(z1, z2); z++) {
            if (Math.floorDiv(z, 16) == chunkZ) wall(world, x1, y, z);
        } else for (int x = Math.min(x1, x2); x <= Math.max(x1, x2); x++) {
            if (Math.floorDiv(x, 16) == chunkX) wall(world, x, y, z1);
        }
    }

    private static void wall(ServerWorld world, int x, int y, int z) {
        for (int dy = 0; dy < 3; dy++) set(world, x, y + dy, z, Blocks.BEDROCK.getDefaultState());
    }

    private static void set(ServerWorld world, int x, int y, int z, BlockState state) {
        world.setBlockState(new BlockPos(x, y, z), state, 2);
    }
}
