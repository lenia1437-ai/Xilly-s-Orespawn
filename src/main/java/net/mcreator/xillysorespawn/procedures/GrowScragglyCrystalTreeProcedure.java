package net.mcreator.xillysorespawn.procedures;

import net.minecraft.world.IWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.block.BlockState;

import net.mcreator.xillysorespawn.block.CrystalTreeLogBlock;
import net.mcreator.xillysorespawn.block.CrystalTreeLeaves2Block;
import net.mcreator.xillysorespawn.block.CrystalSapling2Block;
import net.mcreator.xillysorespawn.XillysOrespawnMod;

import java.util.Map;

public class GrowScragglyCrystalTreeProcedure {

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("world") == null) {
			if (!dependencies.containsKey("world"))
				XillysOrespawnMod.LOGGER.warn("Failed to load dependency world for procedure GrowScragglyCrystalTree!");
			return;
		}
		if (dependencies.get("x") == null) {
			if (!dependencies.containsKey("x"))
				XillysOrespawnMod.LOGGER.warn("Failed to load dependency x for procedure GrowScragglyCrystalTree!");
			return;
		}
		if (dependencies.get("y") == null) {
			if (!dependencies.containsKey("y"))
				XillysOrespawnMod.LOGGER.warn("Failed to load dependency y for procedure GrowScragglyCrystalTree!");
			return;
		}
		if (dependencies.get("z") == null) {
			if (!dependencies.containsKey("z"))
				XillysOrespawnMod.LOGGER.warn("Failed to load dependency z for procedure GrowScragglyCrystalTree!");
			return;
		}
		IWorld world = (IWorld) dependencies.get("world");
		double x = dependencies.get("x") instanceof Integer ? (int) dependencies.get("x") : (double) dependencies.get("x");
		double y = dependencies.get("y") instanceof Integer ? (int) dependencies.get("y") : (double) dependencies.get("y");
		double z = dependencies.get("z") instanceof Integer ? (int) dependencies.get("z") : (double) dependencies.get("z");
		if ((world.getBlockState(new BlockPos(x, y, z))).getBlock() == CrystalSapling2Block.block) {
			{
				BlockPos _bp = new BlockPos(x - 2, y + 3, z - 1);
				BlockState _bs = CrystalTreeLeaves2Block.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x - 2, y + 3, z);
				BlockState _bs = CrystalTreeLeaves2Block.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x - 2, y + 3, z + 1);
				BlockState _bs = CrystalTreeLeaves2Block.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x - 1, y + 3, z - 2);
				BlockState _bs = CrystalTreeLeaves2Block.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x - 1, y + 3, z - 1);
				BlockState _bs = CrystalTreeLeaves2Block.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x - 1, y + 3, z);
				BlockState _bs = CrystalTreeLeaves2Block.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x - 1, y + 3, z + 1);
				BlockState _bs = CrystalTreeLeaves2Block.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x - 1, y + 3, z + 2);
				BlockState _bs = CrystalTreeLeaves2Block.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x, y + 3, z - 2);
				BlockState _bs = CrystalTreeLeaves2Block.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x, y + 3, z - 1);
				BlockState _bs = CrystalTreeLeaves2Block.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x, y + 3, z);
				BlockState _bs = CrystalTreeLeaves2Block.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x, y + 3, z + 1);
				BlockState _bs = CrystalTreeLeaves2Block.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x, y + 3, z + 2);
				BlockState _bs = CrystalTreeLeaves2Block.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x + 1, y + 3, z - 2);
				BlockState _bs = CrystalTreeLeaves2Block.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x + 1, y + 3, z - 1);
				BlockState _bs = CrystalTreeLeaves2Block.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x + 1, y + 3, z);
				BlockState _bs = CrystalTreeLeaves2Block.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x + 1, y + 3, z + 1);
				BlockState _bs = CrystalTreeLeaves2Block.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x + 1, y + 3, z + 2);
				BlockState _bs = CrystalTreeLeaves2Block.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x + 2, y + 3, z - 1);
				BlockState _bs = CrystalTreeLeaves2Block.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x + 2, y + 3, z);
				BlockState _bs = CrystalTreeLeaves2Block.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x + 2, y + 3, z + 1);
				BlockState _bs = CrystalTreeLeaves2Block.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x - 2, y + 5, z - 1);
				BlockState _bs = CrystalTreeLeaves2Block.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x - 2, y + 5, z);
				BlockState _bs = CrystalTreeLeaves2Block.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x - 2, y + 5, z + 1);
				BlockState _bs = CrystalTreeLeaves2Block.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x - 1, y + 5, z - 2);
				BlockState _bs = CrystalTreeLeaves2Block.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x - 1, y + 5, z - 1);
				BlockState _bs = CrystalTreeLeaves2Block.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x - 1, y + 5, z);
				BlockState _bs = CrystalTreeLeaves2Block.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x - 1, y + 5, z + 1);
				BlockState _bs = CrystalTreeLeaves2Block.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x - 1, y + 5, z + 2);
				BlockState _bs = CrystalTreeLeaves2Block.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x, y + 5, z - 2);
				BlockState _bs = CrystalTreeLeaves2Block.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x, y + 5, z - 1);
				BlockState _bs = CrystalTreeLeaves2Block.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x, y + 5, z);
				BlockState _bs = CrystalTreeLeaves2Block.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x, y + 5, z + 1);
				BlockState _bs = CrystalTreeLeaves2Block.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x, y + 5, z + 2);
				BlockState _bs = CrystalTreeLeaves2Block.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x + 1, y + 5, z - 2);
				BlockState _bs = CrystalTreeLeaves2Block.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x + 1, y + 5, z - 1);
				BlockState _bs = CrystalTreeLeaves2Block.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x + 1, y + 5, z);
				BlockState _bs = CrystalTreeLeaves2Block.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x + 1, y + 5, z + 1);
				BlockState _bs = CrystalTreeLeaves2Block.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x + 1, y + 5, z + 2);
				BlockState _bs = CrystalTreeLeaves2Block.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x + 2, y + 5, z - 1);
				BlockState _bs = CrystalTreeLeaves2Block.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x + 2, y + 5, z);
				BlockState _bs = CrystalTreeLeaves2Block.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x + 2, y + 5, z + 1);
				BlockState _bs = CrystalTreeLeaves2Block.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x - 2, y + 7, z - 1);
				BlockState _bs = CrystalTreeLeaves2Block.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x - 2, y + 7, z);
				BlockState _bs = CrystalTreeLeaves2Block.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x - 2, y + 7, z + 1);
				BlockState _bs = CrystalTreeLeaves2Block.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x - 1, y + 7, z - 2);
				BlockState _bs = CrystalTreeLeaves2Block.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x - 1, y + 7, z - 1);
				BlockState _bs = CrystalTreeLeaves2Block.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x - 1, y + 7, z);
				BlockState _bs = CrystalTreeLeaves2Block.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x - 1, y + 7, z + 1);
				BlockState _bs = CrystalTreeLeaves2Block.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x - 1, y + 7, z + 2);
				BlockState _bs = CrystalTreeLeaves2Block.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x, y + 7, z - 2);
				BlockState _bs = CrystalTreeLeaves2Block.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x, y + 7, z - 1);
				BlockState _bs = CrystalTreeLeaves2Block.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x, y + 7, z);
				BlockState _bs = CrystalTreeLeaves2Block.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x, y + 7, z + 1);
				BlockState _bs = CrystalTreeLeaves2Block.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x, y + 7, z + 2);
				BlockState _bs = CrystalTreeLeaves2Block.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x + 1, y + 7, z - 2);
				BlockState _bs = CrystalTreeLeaves2Block.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x + 1, y + 7, z - 1);
				BlockState _bs = CrystalTreeLeaves2Block.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x + 1, y + 7, z);
				BlockState _bs = CrystalTreeLeaves2Block.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x + 1, y + 7, z + 1);
				BlockState _bs = CrystalTreeLeaves2Block.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x + 1, y + 7, z + 2);
				BlockState _bs = CrystalTreeLeaves2Block.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x + 2, y + 7, z - 1);
				BlockState _bs = CrystalTreeLeaves2Block.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x + 2, y + 7, z);
				BlockState _bs = CrystalTreeLeaves2Block.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x + 2, y + 7, z + 1);
				BlockState _bs = CrystalTreeLeaves2Block.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x - 1, y + 8, z - 1);
				BlockState _bs = CrystalTreeLeaves2Block.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x - 1, y + 8, z);
				BlockState _bs = CrystalTreeLeaves2Block.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x - 1, y + 8, z + 1);
				BlockState _bs = CrystalTreeLeaves2Block.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x, y + 8, z - 1);
				BlockState _bs = CrystalTreeLeaves2Block.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x, y + 8, z);
				BlockState _bs = CrystalTreeLeaves2Block.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x, y + 8, z + 1);
				BlockState _bs = CrystalTreeLeaves2Block.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x + 1, y + 8, z - 1);
				BlockState _bs = CrystalTreeLeaves2Block.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x + 1, y + 8, z);
				BlockState _bs = CrystalTreeLeaves2Block.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x + 1, y + 8, z + 1);
				BlockState _bs = CrystalTreeLeaves2Block.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x + 1, y + 3, z);
				BlockState _bs = CrystalTreeLogBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x - 1, y + 3, z);
				BlockState _bs = CrystalTreeLogBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x, y + 3, z + 1);
				BlockState _bs = CrystalTreeLogBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x, y + 3, z - 1);
				BlockState _bs = CrystalTreeLogBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x + 2, y + 3, z);
				BlockState _bs = CrystalTreeLogBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x - 2, y + 3, z);
				BlockState _bs = CrystalTreeLogBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x, y + 3, z + 2);
				BlockState _bs = CrystalTreeLogBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x, y + 3, z - 2);
				BlockState _bs = CrystalTreeLogBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x, y, z);
				BlockState _bs = CrystalTreeLogBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x, y + 1, z);
				BlockState _bs = CrystalTreeLogBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x, y + 2, z);
				BlockState _bs = CrystalTreeLogBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x, y + 3, z);
				BlockState _bs = CrystalTreeLogBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x, y + 4, z);
				BlockState _bs = CrystalTreeLogBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x, y + 5, z);
				BlockState _bs = CrystalTreeLogBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x, y + 6, z);
				BlockState _bs = CrystalTreeLogBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x, y + 7, z);
				BlockState _bs = CrystalTreeLogBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
		}
	}
}
