package net.mcreator.xillysorespawn.procedures;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

import net.minecraft.world.IWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Entity;
import net.minecraft.block.Blocks;
import net.minecraft.block.BlockState;

import net.mcreator.xillysorespawn.item.AppleTreeSeedItem;
import net.mcreator.xillysorespawn.block.LeavesAppleBlock;
import net.mcreator.xillysorespawn.XillysOrespawnMod;

import java.util.Map;
import java.util.HashMap;

public class GrowAppleTreeFromSeedProcedure {
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
				XillysOrespawnMod.LOGGER.warn("Failed to load dependency world for procedure GrowAppleTreeFromSeed!");
			return;
		}
		if (dependencies.get("x") == null) {
			if (!dependencies.containsKey("x"))
				XillysOrespawnMod.LOGGER.warn("Failed to load dependency x for procedure GrowAppleTreeFromSeed!");
			return;
		}
		if (dependencies.get("y") == null) {
			if (!dependencies.containsKey("y"))
				XillysOrespawnMod.LOGGER.warn("Failed to load dependency y for procedure GrowAppleTreeFromSeed!");
			return;
		}
		if (dependencies.get("z") == null) {
			if (!dependencies.containsKey("z"))
				XillysOrespawnMod.LOGGER.warn("Failed to load dependency z for procedure GrowAppleTreeFromSeed!");
			return;
		}
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				XillysOrespawnMod.LOGGER.warn("Failed to load dependency entity for procedure GrowAppleTreeFromSeed!");
			return;
		}
		IWorld world = (IWorld) dependencies.get("world");
		double x = dependencies.get("x") instanceof Integer ? (int) dependencies.get("x") : (double) dependencies.get("x");
		double y = dependencies.get("y") instanceof Integer ? (int) dependencies.get("y") : (double) dependencies.get("y");
		double z = dependencies.get("z") instanceof Integer ? (int) dependencies.get("z") : (double) dependencies.get("z");
		Entity entity = (Entity) dependencies.get("entity");
		if (((entity instanceof LivingEntity) ? ((LivingEntity) entity).getHeldItemMainhand() : ItemStack.EMPTY).getItem() == AppleTreeSeedItem.block
				&& ((world.getBlockState(new BlockPos(x, y, z))).getBlock() == Blocks.GRASS_BLOCK
						|| (world.getBlockState(new BlockPos(x, y, z))).getBlock() == Blocks.DIRT
						|| (world.getBlockState(new BlockPos(x, y, z))).getBlock() == Blocks.MYCELIUM)) {
			if (entity instanceof PlayerEntity) {
				ItemStack _stktoremove = new ItemStack(AppleTreeSeedItem.block);
				((PlayerEntity) entity).inventory.func_234564_a_(p -> _stktoremove.getItem() == p.getItem(), (int) 1,
						((PlayerEntity) entity).container.func_234641_j_());
			}
			{
				BlockPos _bp = new BlockPos(x - 4, y + 7, z - 1);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x - 4, y + 7, z);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x - 4, y + 7, z + 1);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x - 3, y + 7, z - 2);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x - 3, y + 7, z - 1);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x - 3, y + 7, z);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x - 3, y + 7, z + 1);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x - 3, y + 7, z + 2);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x - 2, y + 7, z - 3);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x - 2, y + 7, z - 2);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x - 2, y + 7, z - 1);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x - 2, y + 7, z);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x - 2, y + 7, z + 1);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x - 2, y + 7, z + 2);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x - 2, y + 7, z + 3);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x - 1, y + 7, z - 4);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x - 1, y + 7, z - 3);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x - 1, y + 7, z - 2);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x - 1, y + 7, z - 1);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x - 1, y + 7, z);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x - 1, y + 7, z + 1);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x - 1, y + 7, z + 2);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x - 1, y + 7, z + 3);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x - 1, y + 7, z + 4);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x, y + 7, z - 4);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x, y + 7, z - 3);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x, y + 7, z - 2);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x, y + 7, z - 1);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x, y + 7, z);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x, y + 7, z + 1);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x, y + 7, z + 2);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x, y + 7, z + 3);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x, y + 7, z + 4);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x + 1, y + 7, z - 4);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x + 1, y + 7, z - 3);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x + 1, y + 7, z - 2);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x + 1, y + 7, z - 1);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x + 1, y + 7, z);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x + 1, y + 7, z + 1);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x + 1, y + 7, z + 2);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x + 1, y + 7, z + 3);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x + 1, y + 7, z + 4);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x + 2, y + 7, z - 3);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x + 2, y + 7, z - 2);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x + 2, y + 7, z - 1);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x + 2, y + 7, z);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x + 2, y + 7, z + 1);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x + 2, y + 7, z + 2);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x + 2, y + 7, z + 3);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x + 3, y + 7, z - 2);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x + 3, y + 7, z - 1);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x + 3, y + 7, z);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x + 3, y + 7, z + 1);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x + 3, y + 7, z + 2);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x + 4, y + 7, z - 1);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x + 4, y + 7, z);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x + 4, y + 7, z + 1);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x - 4, y + 8, z - 1);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x - 4, y + 8, z);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x - 4, y + 8, z + 1);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x - 3, y + 8, z - 2);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x - 3, y + 8, z - 1);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x - 3, y + 8, z);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x - 3, y + 8, z + 1);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x - 3, y + 8, z + 2);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x - 2, y + 8, z - 3);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x - 2, y + 8, z - 2);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x - 2, y + 8, z - 1);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x - 2, y + 8, z);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x - 2, y + 8, z + 1);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x - 2, y + 8, z + 2);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x - 2, y + 8, z + 3);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x - 1, y + 8, z - 4);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x - 1, y + 8, z - 3);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x - 1, y + 8, z - 2);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x - 1, y + 8, z - 1);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x - 1, y + 8, z);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x - 1, y + 8, z + 1);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x - 1, y + 8, z + 2);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x - 1, y + 8, z + 3);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x - 1, y + 8, z + 4);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x, y + 8, z - 4);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x, y + 8, z - 3);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x, y + 8, z - 2);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x, y + 8, z - 1);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x, y + 8, z);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x, y + 8, z + 1);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x, y + 8, z + 2);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x, y + 8, z + 3);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x, y + 8, z + 4);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x + 1, y + 8, z - 4);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x + 1, y + 8, z - 3);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x + 1, y + 8, z - 2);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x + 1, y + 8, z - 1);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x + 1, y + 8, z);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x + 1, y + 8, z + 1);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x + 1, y + 8, z + 2);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x + 1, y + 8, z + 3);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x + 1, y + 8, z + 4);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x + 2, y + 8, z - 3);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x + 2, y + 8, z - 2);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x + 2, y + 8, z - 1);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x + 2, y + 8, z);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x + 2, y + 8, z + 1);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x + 2, y + 8, z + 2);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x + 2, y + 8, z + 3);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x + 3, y + 8, z - 2);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x + 3, y + 8, z - 1);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x + 3, y + 8, z);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x + 3, y + 8, z + 1);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x + 3, y + 8, z + 2);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x + 4, y + 8, z - 1);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x + 4, y + 8, z);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x + 4, y + 8, z + 1);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x - 3, y + 9, z - 1);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x - 3, y + 9, z);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x - 3, y + 9, z + 1);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x - 2, y + 9, z - 2);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x - 2, y + 9, z - 1);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x - 2, y + 9, z);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x - 2, y + 9, z + 1);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x - 2, y + 9, z + 2);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x - 1, y + 9, z - 3);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x - 1, y + 9, z - 2);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x - 1, y + 9, z - 1);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x - 1, y + 9, z);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x - 1, y + 9, z + 1);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x - 1, y + 9, z + 2);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x - 1, y + 9, z + 3);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x, y + 9, z - 3);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x, y + 9, z - 2);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x, y + 9, z - 1);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x, y + 9, z);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x, y + 9, z + 1);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x, y + 9, z + 2);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x, y + 9, z + 3);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x + 1, y + 9, z - 3);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x + 1, y + 9, z - 2);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x + 1, y + 9, z - 1);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x + 1, y + 9, z);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x + 1, y + 9, z + 1);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x + 1, y + 9, z + 2);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x + 1, y + 9, z + 3);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x + 2, y + 9, z - 2);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x + 2, y + 9, z - 1);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x + 2, y + 9, z);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x + 2, y + 9, z + 1);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x + 2, y + 9, z + 2);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x + 3, y + 9, z - 1);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x + 3, y + 9, z);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x + 3, y + 9, z + 1);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x - 3, y + 10, z - 1);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x - 3, y + 10, z);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x - 3, y + 10, z + 1);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x - 2, y + 10, z - 2);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x - 2, y + 10, z - 1);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x - 2, y + 10, z);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x - 2, y + 10, z + 1);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x - 2, y + 10, z + 2);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x - 1, y + 10, z - 3);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x - 1, y + 10, z - 2);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x - 1, y + 10, z - 1);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x - 1, y + 10, z);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x - 1, y + 10, z + 1);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x - 1, y + 10, z + 2);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x - 1, y + 10, z + 3);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x, y + 10, z - 3);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x, y + 10, z - 2);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x, y + 10, z - 1);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x, y + 10, z);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x, y + 10, z + 1);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x, y + 10, z + 2);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x, y + 10, z + 3);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x + 1, y + 10, z - 3);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x + 1, y + 10, z - 2);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x + 1, y + 10, z - 1);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x + 1, y + 10, z);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x + 1, y + 10, z + 1);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x + 1, y + 10, z + 2);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x + 1, y + 10, z + 3);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x + 2, y + 10, z - 2);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x + 2, y + 10, z - 1);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x + 2, y + 10, z);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x + 2, y + 10, z + 1);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x + 2, y + 10, z + 2);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x + 3, y + 10, z - 1);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x + 3, y + 10, z);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x + 3, y + 10, z + 1);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x - 2, y + 11, z - 1);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x - 2, y + 11, z);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x - 2, y + 11, z + 1);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x - 1, y + 11, z - 2);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x - 1, y + 11, z - 1);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x - 1, y + 11, z);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x - 1, y + 11, z + 1);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x - 1, y + 11, z + 2);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x, y + 11, z - 2);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x, y + 11, z - 1);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x, y + 11, z);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x, y + 11, z + 1);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x, y + 11, z + 2);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x + 1, y + 11, z - 2);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x + 1, y + 11, z - 1);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x + 1, y + 11, z);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x + 1, y + 11, z + 1);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x + 1, y + 11, z + 2);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x + 2, y + 11, z - 1);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x + 2, y + 11, z);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x + 2, y + 11, z + 1);
				BlockState _bs = LeavesAppleBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x + 1, y + 7, z);
				BlockState _bs = Blocks.OAK_LOG.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x - 1, y + 7, z);
				BlockState _bs = Blocks.OAK_LOG.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x, y + 7, z + 1);
				BlockState _bs = Blocks.OAK_LOG.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x, y + 7, z - 1);
				BlockState _bs = Blocks.OAK_LOG.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x + 2, y + 7, z);
				BlockState _bs = Blocks.OAK_LOG.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x - 2, y + 7, z);
				BlockState _bs = Blocks.OAK_LOG.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x, y + 7, z + 2);
				BlockState _bs = Blocks.OAK_LOG.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x, y + 7, z - 2);
				BlockState _bs = Blocks.OAK_LOG.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x + 3, y + 7, z);
				BlockState _bs = Blocks.OAK_LOG.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x - 3, y + 7, z);
				BlockState _bs = Blocks.OAK_LOG.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x, y + 7, z + 3);
				BlockState _bs = Blocks.OAK_LOG.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x, y + 7, z - 3);
				BlockState _bs = Blocks.OAK_LOG.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x, y + 1, z);
				BlockState _bs = Blocks.OAK_LOG.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x, y + 2, z);
				BlockState _bs = Blocks.OAK_LOG.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x, y + 3, z);
				BlockState _bs = Blocks.OAK_LOG.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x, y + 4, z);
				BlockState _bs = Blocks.OAK_LOG.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x, y + 5, z);
				BlockState _bs = Blocks.OAK_LOG.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x, y + 6, z);
				BlockState _bs = Blocks.OAK_LOG.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x, y + 7, z);
				BlockState _bs = Blocks.OAK_LOG.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x, y + 8, z);
				BlockState _bs = Blocks.OAK_LOG.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x, y + 9, z);
				BlockState _bs = Blocks.OAK_LOG.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x, y + 10, z);
				BlockState _bs = Blocks.OAK_LOG.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			{
				BlockPos _bp = new BlockPos(x, y + 11, z);
				BlockState _bs = Blocks.OAK_LOG.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
		}
	}
}
