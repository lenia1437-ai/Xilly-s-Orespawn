package net.mcreator.xillysorespawn.procedures;

import net.minecraftforge.items.ItemHandlerHelper;

import net.minecraft.item.ItemStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.Entity;

import net.mcreator.xillysorespawn.item.SpiderRobotKitItem;
import net.mcreator.xillysorespawn.item.AntRobotKitItem;
import net.mcreator.xillysorespawn.entity.SpiderRobotEntity;
import net.mcreator.xillysorespawn.entity.AntRobotEntity;
import net.mcreator.xillysorespawn.XillysOrespawnMod;

import java.util.Map;

public class DismantleRobotWithWrenchProcedure {

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				XillysOrespawnMod.LOGGER.warn("Failed to load dependency entity for procedure DismantleRobotWithWrench!");
			return;
		}
		if (dependencies.get("sourceentity") == null) {
			if (!dependencies.containsKey("sourceentity"))
				XillysOrespawnMod.LOGGER.warn("Failed to load dependency sourceentity for procedure DismantleRobotWithWrench!");
			return;
		}
		Entity entity = (Entity) dependencies.get("entity");
		Entity sourceentity = (Entity) dependencies.get("sourceentity");
		if (entity instanceof SpiderRobotEntity.CustomEntity) {
			if (sourceentity instanceof PlayerEntity) {
				ItemStack _setstack = new ItemStack(SpiderRobotKitItem.block);
				_setstack.setCount((int) 1);
				ItemHandlerHelper.giveItemToPlayer(((PlayerEntity) sourceentity), _setstack);
			}
			if (!entity.world.isRemote())
				entity.remove();
		}
		if (entity instanceof AntRobotEntity.CustomEntity) {
			if (sourceentity instanceof PlayerEntity) {
				ItemStack _setstack = new ItemStack(AntRobotKitItem.block);
				_setstack.setCount((int) 1);
				ItemHandlerHelper.giveItemToPlayer(((PlayerEntity) sourceentity), _setstack);
			}
			if (!entity.world.isRemote())
				entity.remove();
		}
	}
}
