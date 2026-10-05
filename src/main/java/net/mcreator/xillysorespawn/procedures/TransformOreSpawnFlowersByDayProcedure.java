package net.mcreator.xillysorespawn.procedures;

import net.minecraft.world.World;
import net.minecraft.world.IWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.state.Property;
import net.minecraft.block.BlockState;

import net.mcreator.xillysorespawn.block.FlowerScaryBlock;
import net.mcreator.xillysorespawn.block.FlowerPinkBlock;
import net.mcreator.xillysorespawn.block.FlowerBlueBlock;
import net.mcreator.xillysorespawn.block.FlowerBlackBlock;
import net.mcreator.xillysorespawn.XillysOrespawnMod;

import java.util.Map;

public class TransformOreSpawnFlowersByDayProcedure {

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("world") == null) {
			if (!dependencies.containsKey("world"))
				XillysOrespawnMod.LOGGER.warn("Failed to load dependency world for procedure TransformOreSpawnFlowersByDay!");
			return;
		}
		if (dependencies.get("x") == null) {
			if (!dependencies.containsKey("x"))
				XillysOrespawnMod.LOGGER.warn("Failed to load dependency x for procedure TransformOreSpawnFlowersByDay!");
			return;
		}
		if (dependencies.get("y") == null) {
			if (!dependencies.containsKey("y"))
				XillysOrespawnMod.LOGGER.warn("Failed to load dependency y for procedure TransformOreSpawnFlowersByDay!");
			return;
		}
		if (dependencies.get("z") == null) {
			if (!dependencies.containsKey("z"))
				XillysOrespawnMod.LOGGER.warn("Failed to load dependency z for procedure TransformOreSpawnFlowersByDay!");
			return;
		}
		IWorld world = (IWorld) dependencies.get("world");
		double x = dependencies.get("x") instanceof Integer ? (int) dependencies.get("x") : (double) dependencies.get("x");
		double y = dependencies.get("y") instanceof Integer ? (int) dependencies.get("y") : (double) dependencies.get("y");
		double z = dependencies.get("z") instanceof Integer ? (int) dependencies.get("z") : (double) dependencies.get("z");
		if (((world instanceof World) ? ((World) world).isDaytime() : false)
				&& (world.getBlockState(new BlockPos(x, y, z))).getBlock() == FlowerBlackBlock.block) {
			{
				BlockPos _bp = new BlockPos(x, y, z);
				BlockState _bs = FlowerPinkBlock.block.getDefaultState();
				BlockState _bso = world.getBlockState(_bp);
				for (Map.Entry<Property<?>, Comparable<?>> entry : _bso.getValues().entrySet()) {
					Property _property = _bs.getBlock().getStateContainer().getProperty(entry.getKey().getName());
					if (_property != null && _bs.get(_property) != null)
						try {
							_bs = _bs.with(_property, (Comparable) entry.getValue());
						} catch (Exception e) {
						}
				}
				world.setBlockState(_bp, _bs, 3);
			}
		}
		if (((world instanceof World) ? ((World) world).isDaytime() : false)
				&& (world.getBlockState(new BlockPos(x, y, z))).getBlock() == FlowerScaryBlock.block) {
			{
				BlockPos _bp = new BlockPos(x, y, z);
				BlockState _bs = FlowerBlueBlock.block.getDefaultState();
				BlockState _bso = world.getBlockState(_bp);
				for (Map.Entry<Property<?>, Comparable<?>> entry : _bso.getValues().entrySet()) {
					Property _property = _bs.getBlock().getStateContainer().getProperty(entry.getKey().getName());
					if (_property != null && _bs.get(_property) != null)
						try {
							_bs = _bs.with(_property, (Comparable) entry.getValue());
						} catch (Exception e) {
						}
				}
				world.setBlockState(_bp, _bs, 3);
			}
		}
		if (!((world instanceof World) ? ((World) world).isDaytime() : false)
				&& (world.getBlockState(new BlockPos(x, y, z))).getBlock() == FlowerPinkBlock.block) {
			{
				BlockPos _bp = new BlockPos(x, y, z);
				BlockState _bs = FlowerBlackBlock.block.getDefaultState();
				BlockState _bso = world.getBlockState(_bp);
				for (Map.Entry<Property<?>, Comparable<?>> entry : _bso.getValues().entrySet()) {
					Property _property = _bs.getBlock().getStateContainer().getProperty(entry.getKey().getName());
					if (_property != null && _bs.get(_property) != null)
						try {
							_bs = _bs.with(_property, (Comparable) entry.getValue());
						} catch (Exception e) {
						}
				}
				world.setBlockState(_bp, _bs, 3);
			}
		}
		if (!((world instanceof World) ? ((World) world).isDaytime() : false)
				&& (world.getBlockState(new BlockPos(x, y, z))).getBlock() == FlowerBlueBlock.block) {
			{
				BlockPos _bp = new BlockPos(x, y, z);
				BlockState _bs = FlowerScaryBlock.block.getDefaultState();
				BlockState _bso = world.getBlockState(_bp);
				for (Map.Entry<Property<?>, Comparable<?>> entry : _bso.getValues().entrySet()) {
					Property _property = _bs.getBlock().getStateContainer().getProperty(entry.getKey().getName());
					if (_property != null && _bs.get(_property) != null)
						try {
							_bs = _bs.with(_property, (Comparable) entry.getValue());
						} catch (Exception e) {
						}
				}
				world.setBlockState(_bp, _bs, 3);
			}
		}
		world.getPendingBlockTicks().scheduleTick(new BlockPos(x, y, z), world.getBlockState(new BlockPos(x, y, z)).getBlock(), (int) 100);
	}
}
