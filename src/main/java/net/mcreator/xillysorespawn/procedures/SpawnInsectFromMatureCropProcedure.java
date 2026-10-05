package net.mcreator.xillysorespawn.procedures;

import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.IWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.Entity;

import net.mcreator.xillysorespawn.entity.MothEntity;
import net.mcreator.xillysorespawn.entity.FireflyEntity;
import net.mcreator.xillysorespawn.entity.ButterflyEntity;
import net.mcreator.xillysorespawn.block.Moth3Block;
import net.mcreator.xillysorespawn.block.Firefly3Block;
import net.mcreator.xillysorespawn.block.Butterfly3Block;
import net.mcreator.xillysorespawn.XillysOrespawnMod;

import java.util.Map;

public class SpawnInsectFromMatureCropProcedure {

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("world") == null) {
			if (!dependencies.containsKey("world"))
				XillysOrespawnMod.LOGGER.warn("Failed to load dependency world for procedure SpawnInsectFromMatureCrop!");
			return;
		}
		if (dependencies.get("x") == null) {
			if (!dependencies.containsKey("x"))
				XillysOrespawnMod.LOGGER.warn("Failed to load dependency x for procedure SpawnInsectFromMatureCrop!");
			return;
		}
		if (dependencies.get("y") == null) {
			if (!dependencies.containsKey("y"))
				XillysOrespawnMod.LOGGER.warn("Failed to load dependency y for procedure SpawnInsectFromMatureCrop!");
			return;
		}
		if (dependencies.get("z") == null) {
			if (!dependencies.containsKey("z"))
				XillysOrespawnMod.LOGGER.warn("Failed to load dependency z for procedure SpawnInsectFromMatureCrop!");
			return;
		}
		IWorld world = (IWorld) dependencies.get("world");
		double x = dependencies.get("x") instanceof Integer ? (int) dependencies.get("x") : (double) dependencies.get("x");
		double y = dependencies.get("y") instanceof Integer ? (int) dependencies.get("y") : (double) dependencies.get("y");
		double z = dependencies.get("z") instanceof Integer ? (int) dependencies.get("z") : (double) dependencies.get("z");
		if ((world.getBlockState(new BlockPos(x, y, z))).getBlock() == Butterfly3Block.block) {
			if (world instanceof ServerWorld) {
				Entity entityToSpawn = new ButterflyEntity.CustomEntity(ButterflyEntity.entity, (World) world);
				entityToSpawn.setLocationAndAngles(x, (y + 1), z, world.getRandom().nextFloat() * 360F, 0);
				if (entityToSpawn instanceof MobEntity)
					((MobEntity) entityToSpawn).onInitialSpawn((ServerWorld) world, world.getDifficultyForLocation(entityToSpawn.getPosition()),
							SpawnReason.MOB_SUMMONED, (ILivingEntityData) null, (CompoundNBT) null);
				world.addEntity(entityToSpawn);
			}
			world.getPendingBlockTicks().scheduleTick(new BlockPos(x, y, z), world.getBlockState(new BlockPos(x, y, z)).getBlock(), (int) 1200);
		} else {
			if ((world.getBlockState(new BlockPos(x, y, z))).getBlock() == Firefly3Block.block) {
				if (world instanceof ServerWorld) {
					Entity entityToSpawn = new FireflyEntity.CustomEntity(FireflyEntity.entity, (World) world);
					entityToSpawn.setLocationAndAngles(x, (y + 1), z, world.getRandom().nextFloat() * 360F, 0);
					if (entityToSpawn instanceof MobEntity)
						((MobEntity) entityToSpawn).onInitialSpawn((ServerWorld) world, world.getDifficultyForLocation(entityToSpawn.getPosition()),
								SpawnReason.MOB_SUMMONED, (ILivingEntityData) null, (CompoundNBT) null);
					world.addEntity(entityToSpawn);
				}
				world.getPendingBlockTicks().scheduleTick(new BlockPos(x, y, z), world.getBlockState(new BlockPos(x, y, z)).getBlock(), (int) 1200);
			} else {
				if ((world.getBlockState(new BlockPos(x, y, z))).getBlock() == Moth3Block.block) {
					if (world instanceof ServerWorld) {
						Entity entityToSpawn = new MothEntity.CustomEntity(MothEntity.entity, (World) world);
						entityToSpawn.setLocationAndAngles(x, (y + 1), z, world.getRandom().nextFloat() * 360F, 0);
						if (entityToSpawn instanceof MobEntity)
							((MobEntity) entityToSpawn).onInitialSpawn((ServerWorld) world,
									world.getDifficultyForLocation(entityToSpawn.getPosition()), SpawnReason.MOB_SUMMONED, (ILivingEntityData) null,
									(CompoundNBT) null);
						world.addEntity(entityToSpawn);
					}
					world.getPendingBlockTicks().scheduleTick(new BlockPos(x, y, z), world.getBlockState(new BlockPos(x, y, z)).getBlock(),
							(int) 1200);
				}
			}
		}
	}
}
