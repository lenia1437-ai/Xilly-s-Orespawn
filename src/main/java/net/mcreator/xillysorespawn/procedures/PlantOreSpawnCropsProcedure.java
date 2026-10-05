package net.mcreator.xillysorespawn.procedures;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

import net.minecraft.world.IWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.state.Property;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Entity;
import net.minecraft.block.Blocks;
import net.minecraft.block.BlockState;

import net.mcreator.xillysorespawn.item.TomatoSeedItem;
import net.mcreator.xillysorespawn.item.StrawberrySeedItem;
import net.mcreator.xillysorespawn.item.RiceItem;
import net.mcreator.xillysorespawn.item.RadishItem;
import net.mcreator.xillysorespawn.item.QuinoaItem;
import net.mcreator.xillysorespawn.item.MothSeedItem;
import net.mcreator.xillysorespawn.item.MosquitoSeedItem;
import net.mcreator.xillysorespawn.item.LettuceSeedItem;
import net.mcreator.xillysorespawn.item.FireflySeedItem;
import net.mcreator.xillysorespawn.item.CornSeedItem;
import net.mcreator.xillysorespawn.item.ButterflySeedItem;
import net.mcreator.xillysorespawn.block.Tomato0Block;
import net.mcreator.xillysorespawn.block.Strawberry0Block;
import net.mcreator.xillysorespawn.block.Rice0Block;
import net.mcreator.xillysorespawn.block.Radish0Block;
import net.mcreator.xillysorespawn.block.Quinoa0Block;
import net.mcreator.xillysorespawn.block.Moth0Block;
import net.mcreator.xillysorespawn.block.Mosquito0Block;
import net.mcreator.xillysorespawn.block.Lettuce0Block;
import net.mcreator.xillysorespawn.block.Firefly0Block;
import net.mcreator.xillysorespawn.block.CrystalGrassBlock;
import net.mcreator.xillysorespawn.block.Corn0Block;
import net.mcreator.xillysorespawn.block.Butterfly0Block;
import net.mcreator.xillysorespawn.XillysOrespawnMod;

import java.util.Map;
import java.util.HashMap;

public class PlantOreSpawnCropsProcedure {
	@Mod.EventBusSubscriber
	private static class GlobalTrigger {
		@SubscribeEvent
		public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
			PlayerEntity entity = event.getPlayer();
			if (event.getHand() != entity.getActiveHand()) {
				return;
			}
			double i = event.getPos().getX();
			double j = event.getPos().getY();
			double k = event.getPos().getZ();
			IWorld world = event.getWorld();
			BlockState state = world.getBlockState(event.getPos());
			Map<String, Object> dependencies = new HashMap<>();
			dependencies.put("x", i);
			dependencies.put("y", j);
			dependencies.put("z", k);
			dependencies.put("world", world);
			dependencies.put("entity", entity);
			dependencies.put("direction", event.getFace());
			dependencies.put("blockstate", state);
			dependencies.put("event", event);
			executeProcedure(dependencies);
		}
	}

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("world") == null) {
			if (!dependencies.containsKey("world"))
				XillysOrespawnMod.LOGGER.warn("Failed to load dependency world for procedure PlantOreSpawnCrops!");
			return;
		}
		if (dependencies.get("x") == null) {
			if (!dependencies.containsKey("x"))
				XillysOrespawnMod.LOGGER.warn("Failed to load dependency x for procedure PlantOreSpawnCrops!");
			return;
		}
		if (dependencies.get("y") == null) {
			if (!dependencies.containsKey("y"))
				XillysOrespawnMod.LOGGER.warn("Failed to load dependency y for procedure PlantOreSpawnCrops!");
			return;
		}
		if (dependencies.get("z") == null) {
			if (!dependencies.containsKey("z"))
				XillysOrespawnMod.LOGGER.warn("Failed to load dependency z for procedure PlantOreSpawnCrops!");
			return;
		}
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				XillysOrespawnMod.LOGGER.warn("Failed to load dependency entity for procedure PlantOreSpawnCrops!");
			return;
		}
		IWorld world = (IWorld) dependencies.get("world");
		double x = dependencies.get("x") instanceof Integer ? (int) dependencies.get("x") : (double) dependencies.get("x");
		double y = dependencies.get("y") instanceof Integer ? (int) dependencies.get("y") : (double) dependencies.get("y");
		double z = dependencies.get("z") instanceof Integer ? (int) dependencies.get("z") : (double) dependencies.get("z");
		Entity entity = (Entity) dependencies.get("entity");
		if (((entity instanceof LivingEntity) ? ((LivingEntity) entity).getHeldItemMainhand() : ItemStack.EMPTY).getItem() == CornSeedItem.block
				&& ((world.getBlockState(new BlockPos(x, y, z))).getBlock() == Blocks.FARMLAND
						|| (world.getBlockState(new BlockPos(x, y, z))).getBlock() == Blocks.GRASS_BLOCK
						|| (world.getBlockState(new BlockPos(x, y, z))).getBlock() == CrystalGrassBlock.block)
				&& (world.getBlockState(new BlockPos(x, y + 1, z))).getBlock() == Blocks.AIR) {
			if (entity instanceof PlayerEntity) {
				ItemStack _stktoremove = new ItemStack(CornSeedItem.block);
				((PlayerEntity) entity).inventory.func_234564_a_(p -> _stktoremove.getItem() == p.getItem(), (int) 1,
						((PlayerEntity) entity).container.func_234641_j_());
			}
			{
				BlockPos _bp = new BlockPos(x, y + 1, z);
				BlockState _bs = Corn0Block.block.getDefaultState();
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
		if (((entity instanceof LivingEntity) ? ((LivingEntity) entity).getHeldItemMainhand() : ItemStack.EMPTY).getItem() == LettuceSeedItem.block
				&& ((world.getBlockState(new BlockPos(x, y, z))).getBlock() == Blocks.FARMLAND
						|| (world.getBlockState(new BlockPos(x, y, z))).getBlock() == Blocks.GRASS_BLOCK
						|| (world.getBlockState(new BlockPos(x, y, z))).getBlock() == CrystalGrassBlock.block)
				&& (world.getBlockState(new BlockPos(x, y + 1, z))).getBlock() == Blocks.AIR) {
			if (entity instanceof PlayerEntity) {
				ItemStack _stktoremove = new ItemStack(LettuceSeedItem.block);
				((PlayerEntity) entity).inventory.func_234564_a_(p -> _stktoremove.getItem() == p.getItem(), (int) 1,
						((PlayerEntity) entity).container.func_234641_j_());
			}
			{
				BlockPos _bp = new BlockPos(x, y + 1, z);
				BlockState _bs = Lettuce0Block.block.getDefaultState();
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
		if (((entity instanceof LivingEntity) ? ((LivingEntity) entity).getHeldItemMainhand() : ItemStack.EMPTY).getItem() == TomatoSeedItem.block
				&& ((world.getBlockState(new BlockPos(x, y, z))).getBlock() == Blocks.FARMLAND
						|| (world.getBlockState(new BlockPos(x, y, z))).getBlock() == Blocks.GRASS_BLOCK
						|| (world.getBlockState(new BlockPos(x, y, z))).getBlock() == CrystalGrassBlock.block)
				&& (world.getBlockState(new BlockPos(x, y + 1, z))).getBlock() == Blocks.AIR) {
			if (entity instanceof PlayerEntity) {
				ItemStack _stktoremove = new ItemStack(TomatoSeedItem.block);
				((PlayerEntity) entity).inventory.func_234564_a_(p -> _stktoremove.getItem() == p.getItem(), (int) 1,
						((PlayerEntity) entity).container.func_234641_j_());
			}
			{
				BlockPos _bp = new BlockPos(x, y + 1, z);
				BlockState _bs = Tomato0Block.block.getDefaultState();
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
		if (((entity instanceof LivingEntity) ? ((LivingEntity) entity).getHeldItemMainhand() : ItemStack.EMPTY).getItem() == QuinoaItem.block
				&& ((world.getBlockState(new BlockPos(x, y, z))).getBlock() == Blocks.FARMLAND
						|| (world.getBlockState(new BlockPos(x, y, z))).getBlock() == Blocks.GRASS_BLOCK
						|| (world.getBlockState(new BlockPos(x, y, z))).getBlock() == CrystalGrassBlock.block)
				&& (world.getBlockState(new BlockPos(x, y + 1, z))).getBlock() == Blocks.AIR) {
			if (entity instanceof PlayerEntity) {
				ItemStack _stktoremove = new ItemStack(QuinoaItem.block);
				((PlayerEntity) entity).inventory.func_234564_a_(p -> _stktoremove.getItem() == p.getItem(), (int) 1,
						((PlayerEntity) entity).container.func_234641_j_());
			}
			{
				BlockPos _bp = new BlockPos(x, y + 1, z);
				BlockState _bs = Quinoa0Block.block.getDefaultState();
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
		if (((entity instanceof LivingEntity) ? ((LivingEntity) entity).getHeldItemMainhand() : ItemStack.EMPTY).getItem() == RadishItem.block
				&& ((world.getBlockState(new BlockPos(x, y, z))).getBlock() == Blocks.FARMLAND
						|| (world.getBlockState(new BlockPos(x, y, z))).getBlock() == Blocks.GRASS_BLOCK
						|| (world.getBlockState(new BlockPos(x, y, z))).getBlock() == CrystalGrassBlock.block)
				&& (world.getBlockState(new BlockPos(x, y + 1, z))).getBlock() == Blocks.AIR) {
			if (entity instanceof PlayerEntity) {
				ItemStack _stktoremove = new ItemStack(RadishItem.block);
				((PlayerEntity) entity).inventory.func_234564_a_(p -> _stktoremove.getItem() == p.getItem(), (int) 1,
						((PlayerEntity) entity).container.func_234641_j_());
			}
			{
				BlockPos _bp = new BlockPos(x, y + 1, z);
				BlockState _bs = Radish0Block.block.getDefaultState();
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
		if (((entity instanceof LivingEntity) ? ((LivingEntity) entity).getHeldItemMainhand() : ItemStack.EMPTY).getItem() == RiceItem.block
				&& ((world.getBlockState(new BlockPos(x, y, z))).getBlock() == Blocks.FARMLAND
						|| (world.getBlockState(new BlockPos(x, y, z))).getBlock() == Blocks.GRASS_BLOCK
						|| (world.getBlockState(new BlockPos(x, y, z))).getBlock() == CrystalGrassBlock.block)
				&& (world.getBlockState(new BlockPos(x, y + 1, z))).getBlock() == Blocks.AIR) {
			if (entity instanceof PlayerEntity) {
				ItemStack _stktoremove = new ItemStack(RiceItem.block);
				((PlayerEntity) entity).inventory.func_234564_a_(p -> _stktoremove.getItem() == p.getItem(), (int) 1,
						((PlayerEntity) entity).container.func_234641_j_());
			}
			{
				BlockPos _bp = new BlockPos(x, y + 1, z);
				BlockState _bs = Rice0Block.block.getDefaultState();
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
		if (((entity instanceof LivingEntity) ? ((LivingEntity) entity).getHeldItemMainhand() : ItemStack.EMPTY).getItem() == StrawberrySeedItem.block
				&& ((world.getBlockState(new BlockPos(x, y, z))).getBlock() == Blocks.FARMLAND
						|| (world.getBlockState(new BlockPos(x, y, z))).getBlock() == Blocks.GRASS_BLOCK
						|| (world.getBlockState(new BlockPos(x, y, z))).getBlock() == CrystalGrassBlock.block)
				&& (world.getBlockState(new BlockPos(x, y + 1, z))).getBlock() == Blocks.AIR) {
			if (entity instanceof PlayerEntity) {
				ItemStack _stktoremove = new ItemStack(StrawberrySeedItem.block);
				((PlayerEntity) entity).inventory.func_234564_a_(p -> _stktoremove.getItem() == p.getItem(), (int) 1,
						((PlayerEntity) entity).container.func_234641_j_());
			}
			{
				BlockPos _bp = new BlockPos(x, y + 1, z);
				BlockState _bs = Strawberry0Block.block.getDefaultState();
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
		if (((entity instanceof LivingEntity) ? ((LivingEntity) entity).getHeldItemMainhand() : ItemStack.EMPTY).getItem() == ButterflySeedItem.block
				&& ((world.getBlockState(new BlockPos(x, y, z))).getBlock() == Blocks.FARMLAND
						|| (world.getBlockState(new BlockPos(x, y, z))).getBlock() == Blocks.GRASS_BLOCK
						|| (world.getBlockState(new BlockPos(x, y, z))).getBlock() == CrystalGrassBlock.block)
				&& (world.getBlockState(new BlockPos(x, y + 1, z))).getBlock() == Blocks.AIR) {
			if (entity instanceof PlayerEntity) {
				ItemStack _stktoremove = new ItemStack(ButterflySeedItem.block);
				((PlayerEntity) entity).inventory.func_234564_a_(p -> _stktoremove.getItem() == p.getItem(), (int) 1,
						((PlayerEntity) entity).container.func_234641_j_());
			}
			{
				BlockPos _bp = new BlockPos(x, y + 1, z);
				BlockState _bs = Butterfly0Block.block.getDefaultState();
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
		if (((entity instanceof LivingEntity) ? ((LivingEntity) entity).getHeldItemMainhand() : ItemStack.EMPTY).getItem() == FireflySeedItem.block
				&& ((world.getBlockState(new BlockPos(x, y, z))).getBlock() == Blocks.FARMLAND
						|| (world.getBlockState(new BlockPos(x, y, z))).getBlock() == Blocks.GRASS_BLOCK
						|| (world.getBlockState(new BlockPos(x, y, z))).getBlock() == CrystalGrassBlock.block)
				&& (world.getBlockState(new BlockPos(x, y + 1, z))).getBlock() == Blocks.AIR) {
			if (entity instanceof PlayerEntity) {
				ItemStack _stktoremove = new ItemStack(FireflySeedItem.block);
				((PlayerEntity) entity).inventory.func_234564_a_(p -> _stktoremove.getItem() == p.getItem(), (int) 1,
						((PlayerEntity) entity).container.func_234641_j_());
			}
			{
				BlockPos _bp = new BlockPos(x, y + 1, z);
				BlockState _bs = Firefly0Block.block.getDefaultState();
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
		if (((entity instanceof LivingEntity) ? ((LivingEntity) entity).getHeldItemMainhand() : ItemStack.EMPTY).getItem() == MothSeedItem.block
				&& ((world.getBlockState(new BlockPos(x, y, z))).getBlock() == Blocks.FARMLAND
						|| (world.getBlockState(new BlockPos(x, y, z))).getBlock() == Blocks.GRASS_BLOCK
						|| (world.getBlockState(new BlockPos(x, y, z))).getBlock() == CrystalGrassBlock.block)
				&& (world.getBlockState(new BlockPos(x, y + 1, z))).getBlock() == Blocks.AIR) {
			if (entity instanceof PlayerEntity) {
				ItemStack _stktoremove = new ItemStack(MothSeedItem.block);
				((PlayerEntity) entity).inventory.func_234564_a_(p -> _stktoremove.getItem() == p.getItem(), (int) 1,
						((PlayerEntity) entity).container.func_234641_j_());
			}
			{
				BlockPos _bp = new BlockPos(x, y + 1, z);
				BlockState _bs = Moth0Block.block.getDefaultState();
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
		if (((entity instanceof LivingEntity) ? ((LivingEntity) entity).getHeldItemMainhand() : ItemStack.EMPTY).getItem() == MosquitoSeedItem.block
				&& ((world.getBlockState(new BlockPos(x, y, z))).getBlock() == Blocks.FARMLAND
						|| (world.getBlockState(new BlockPos(x, y, z))).getBlock() == Blocks.GRASS_BLOCK
						|| (world.getBlockState(new BlockPos(x, y, z))).getBlock() == CrystalGrassBlock.block)
				&& (world.getBlockState(new BlockPos(x, y + 1, z))).getBlock() == Blocks.AIR) {
			if (entity instanceof PlayerEntity) {
				ItemStack _stktoremove = new ItemStack(MosquitoSeedItem.block);
				((PlayerEntity) entity).inventory.func_234564_a_(p -> _stktoremove.getItem() == p.getItem(), (int) 1,
						((PlayerEntity) entity).container.func_234641_j_());
			}
			{
				BlockPos _bp = new BlockPos(x, y + 1, z);
				BlockState _bs = Mosquito0Block.block.getDefaultState();
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
	}
}
