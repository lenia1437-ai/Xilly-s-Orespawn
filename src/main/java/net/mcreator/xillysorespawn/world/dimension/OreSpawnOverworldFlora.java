package net.mcreator.xillysorespawn.world.dimension;

import net.mcreator.xillysorespawn.block.AntNestBlock;
import net.mcreator.xillysorespawn.block.Butterfly0Block;
import net.mcreator.xillysorespawn.block.Corn0Block;
import net.mcreator.xillysorespawn.block.DuplicatorTreeLogBlock;
import net.mcreator.xillysorespawn.block.Firefly0Block;
import net.mcreator.xillysorespawn.block.Lettuce0Block;
import net.mcreator.xillysorespawn.block.Moth0Block;
import net.mcreator.xillysorespawn.block.Radish0Block;
import net.mcreator.xillysorespawn.block.Strawberry0Block;
import net.mcreator.xillysorespawn.block.Tomato0Block;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

/** Surface population rates and biome restrictions from OreSpawnWorld 1.7.10. */
final class OreSpawnOverworldFlora {
    private OreSpawnOverworldFlora() { }

    static void populate(ServerWorld world, int chunkX, int chunkZ, long seed) {
        Random random = new Random(seed ^ 0x58494C4C59534F52L);
        BlockPos center = new BlockPos((chunkX << 4) + 8, 64, (chunkZ << 4) + 8);
        if (world.getBiome(center).getRegistryName() == null) return;
        String biome = world.getBiome(center).getRegistryName().getPath();
        if (forest(biome) && random.nextInt(20) == 0)
            plant(world, random, chunkX, chunkZ, 5, Strawberry0Block.block);
        if (biome.equals("plains")) {
            if (random.nextInt(35) == 1) plant(world, random, chunkX, chunkZ, 6, Corn0Block.block);
            if (random.nextInt(70) == 1) plant(world, random, chunkX, chunkZ, 5, Tomato0Block.block);
        }
        if (butterflyBiome(biome) && random.nextInt(10) == 0) {
            for (int i = 0; i < 4; i++) {
                Block block = random.nextInt(3) == 0 ? Butterfly0Block.block
                    : (random.nextBoolean() ? Moth0Block.block : Firefly0Block.block);
                plant(world, random, chunkX, chunkZ, 1, block);
            }
        }
        if ((biome.equals("river") || biome.equals("swamp")) && random.nextInt(15) == 0) {
            for (int i = 0; i < 8; i++) {
                int what = random.nextInt(6);
                if (what == 2) plant(world, random, chunkX, chunkZ, 1, Radish0Block.block);
                else if (what == 3) plant(world, random, chunkX, chunkZ, 1, Lettuce0Block.block);
                else if (what == 5 && random.nextInt(50) == 1)
                    plant(world, random, chunkX, chunkZ, 1, DuplicatorTreeLogBlock.block);
            }
        }
        if (random.nextInt(30) == 0) antNests(world, random, chunkX, chunkZ);
    }

    private static void antNests(ServerWorld world, Random random, int chunkX, int chunkZ) {
        for (int i = 0; i < 4; i++) {
            BlockPos top = grassSite(world, random, chunkX, chunkZ);
            if (top != null) {
                int type = random.nextInt(4) == 0 ? 1 + random.nextInt(4) : 0;
                world.setBlockState(top.down(), AntNestBlock.nest(type), 2);
            }
        }
    }

    private static void plant(ServerWorld world, Random random, int chunkX, int chunkZ, int attempts, Block block) {
        if (block == null) return;
        for (int i = 0; i < attempts; i++) {
            BlockPos top = grassSite(world, random, chunkX, chunkZ);
            if (top != null && block.getDefaultState().isValidPosition(world, top))
                world.setBlockState(top, block.getDefaultState(), 2);
        }
    }

    private static BlockPos grassSite(ServerWorld world, Random random, int chunkX, int chunkZ) {
        int x = (chunkX << 4) + random.nextInt(16);
        int z = (chunkZ << 4) + random.nextInt(16);
        for (int y = 100; y > 40; y--) {
            BlockPos top = new BlockPos(x, y, z);
            if (world.isAirBlock(top) && world.getBlockState(top.down()).isIn(Blocks.GRASS_BLOCK)) return top;
        }
        return null;
    }

    private static boolean forest(String biome) {
        return biome.equals("forest") || biome.equals("wooded_hills")
            || biome.equals("birch_forest") || biome.equals("birch_forest_hills");
    }

    private static boolean butterflyBiome(String biome) {
        return forest(biome) || biome.equals("river") || biome.equals("jungle")
            || biome.equals("jungle_hills") || biome.equals("swamp") || biome.equals("dark_forest");
    }
}
