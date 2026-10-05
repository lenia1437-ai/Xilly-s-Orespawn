package net.mcreator.xillysorespawn.procedures;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;

import net.mcreator.xillysorespawn.block.Corn0Block;
import net.mcreator.xillysorespawn.block.Corn1Block;
import net.mcreator.xillysorespawn.block.Corn2Block;
import net.mcreator.xillysorespawn.block.Corn3Block;
import net.mcreator.xillysorespawn.block.Quinoa0Block;
import net.mcreator.xillysorespawn.block.Quinoa1Block;
import net.mcreator.xillysorespawn.block.Quinoa2Block;
import net.mcreator.xillysorespawn.block.Quinoa3Block;
import net.mcreator.xillysorespawn.block.Tomato0Block;
import net.mcreator.xillysorespawn.block.Tomato1Block;
import net.mcreator.xillysorespawn.block.Tomato2Block;
import net.mcreator.xillysorespawn.block.Tomato3Block;
import net.mcreator.xillysorespawn.XillysOrespawnMod;

import java.util.Map;

/** Original OreSpawn reed-style growth for corn, tomato and quinoa. */
public final class GrowOreSpawnCropStageProcedure {
    private GrowOreSpawnCropStageProcedure() {
    }

    public static void executeProcedure(Map<String, Object> dependencies) {
        Object worldValue = dependencies.get("world");
        if (!(worldValue instanceof IWorld) || dependencies.get("x") == null
                || dependencies.get("y") == null || dependencies.get("z") == null) {
            XillysOrespawnMod.LOGGER.warn("Missing dependency for GrowOreSpawnCropStage");
            return;
        }
        IWorld world = (IWorld) worldValue;
        if (world.isRemote()) return;
        BlockPos pos = new BlockPos(number(dependencies.get("x")), number(dependencies.get("y")), number(dependencies.get("z")));
        Block block = world.getBlockState(pos).getBlock();

        if (belongs(block, Corn0Block.block, Corn1Block.block, Corn2Block.block, Corn3Block.block)) {
            growColumn(world, pos, Corn0Block.block, Corn1Block.block, Corn2Block.block, Corn3Block.block, 4, 7);
        } else if (belongs(block, Tomato0Block.block, Tomato1Block.block, Tomato2Block.block, Tomato3Block.block)) {
            growColumn(world, pos, Tomato0Block.block, Tomato1Block.block, Tomato2Block.block, Tomato3Block.block, 3, 5);
        } else if (belongs(block, Quinoa0Block.block, Quinoa1Block.block, Quinoa2Block.block, Quinoa3Block.block)) {
            growColumn(world, pos, Quinoa0Block.block, Quinoa1Block.block, Quinoa2Block.block, Quinoa3Block.block, 3, 5);
        }
    }

    private static int number(Object value) {
        return ((Number) value).intValue();
    }

    private static boolean belongs(Block block, Block... family) {
        for (Block member : family) if (block == member) return true;
        return false;
    }

    private static void growColumn(IWorld world, BlockPos pos, Block top, Block stem, Block ripeStem, Block matureStem,
                                   int minimumHeight, int maximumHeight) {
        Block current = world.getBlockState(pos).getBlock();
        if (current == ripeStem) {
            world.setBlockState(pos, matureStem.getDefaultState(), 3);
            return;
        }
        // Only the _0 block is the growing tip. _1 is the stem underneath it.
        if (current != top || !world.isAirBlock(pos.up())) return;

        int height = 1;
        while (height < 12 && belongs(world.getBlockState(pos.down(height)).getBlock(), top, stem, ripeStem, matureStem)) {
            height++;
        }
        int spread = maximumHeight - minimumHeight + 1;
        int targetHeight = minimumHeight + Math.floorMod(pos.getX() * 73428767 ^ pos.getZ() * 912931 ^ pos.getY(), spread);
        if (height < targetHeight) {
            world.setBlockState(pos, stem.getDefaultState(), 3);
            world.setBlockState(pos.up(), top.getDefaultState(), 3);
            return;
        }

        // The top remains _0, matching My*Plant1 in OreSpawn 1.7.10.
        for (int depth = 1; depth < height; depth++) {
            BlockPos stemPos = pos.down(depth);
            if (world.getBlockState(stemPos).getBlock() == stem) {
                world.setBlockState(stemPos, ripeStem.getDefaultState(), 3);
            }
        }
    }
}
