package net.mcreator.xillysorespawn.entity;

import java.util.EnumSet;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

/** 1.16.5 equivalent of OreSpawn 1.7.10's MyEntityAIFollowOwner. */
public final class OreSpawnFollowOwnerGoal extends Goal {
    private final TameableEntity pet;
    private final PathNavigator navigator;
    private final double speed;
    private final float maxDistance;
    private final float minDistance;
    private LivingEntity owner;
    private int pathTimer;
    private float oldWaterCost;

    /**
     * Uses the original OreSpawn argument order: speed, maximum start distance,
     * minimum stop distance.
     */
    public OreSpawnFollowOwnerGoal(TameableEntity pet, double speed,
            float maxDistance, float minDistance) {
        this.pet = pet;
        this.navigator = pet.getNavigator();
        this.speed = speed;
        this.maxDistance = maxDistance;
        this.minDistance = minDistance;
        this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean shouldExecute() {
        LivingEntity candidate = this.pet.getOwner();
        if (candidate == null || this.pet.isSitting()) {
            return false;
        }
        this.owner = candidate;
        double distance = this.pet.getDistanceSq(candidate);
        if ((this.pet.getPosY() < 60.0D || !this.pet.world.isDaytime())
                && distance > square(this.maxDistance / 2.0F)) {
            return true;
        }
        return distance >= square(this.maxDistance);
    }

    @Override
    public boolean shouldContinueExecuting() {
        if (this.pet.isSitting() || this.navigator.noPath() || this.owner == null) {
            return false;
        }
        if ((int) this.pet.getPosX() == (int) this.owner.getPosX()
                && (int) this.pet.getPosZ() == (int) this.owner.getPosZ()
                && (int) this.pet.getPosY() < (int) this.owner.getPosY() + 2
                && (int) this.pet.getPosY() > (int) this.owner.getPosY() - 2) {
            return false;
        }
        return this.pet.getDistanceSq(this.owner) > square(this.minDistance);
    }

    @Override
    public void startExecuting() {
        this.pathTimer = 0;
        this.oldWaterCost = this.pet.getPathPriority(PathNodeType.WATER);
        this.pet.setPathPriority(PathNodeType.WATER, 0.0F);
    }

    @Override
    public void resetTask() {
        this.owner = null;
        this.navigator.clearPath();
        this.pet.setPathPriority(PathNodeType.WATER, this.oldWaterCost);
    }

    @Override
    public void tick() {
        if (this.owner == null) {
            return;
        }
        this.pet.getLookController().setLookPositionWithEntity(
            this.owner, 10.0F, (float) this.pet.getVerticalFaceSpeed());
        if (--this.pathTimer > 0 || this.pet.isSitting()) {
            return;
        }
        this.pathTimer = 10;
        if (!this.navigator.tryMoveToEntityLiving(this.owner, this.speed)
                && this.pet.getDistanceSq(this.owner) >= 144.0D) {
            teleportNearOwner();
        }
    }

    private void teleportNearOwner() {
        int startX = MathHelper.floor(this.owner.getPosX()) - 2;
        int startZ = MathHelper.floor(this.owner.getPosZ()) - 2;
        int y = MathHelper.floor(this.owner.getBoundingBox().minY);
        for (int xOffset = 0; xOffset <= 4; ++xOffset) {
            for (int zOffset = 0; zOffset <= 4; ++zOffset) {
                if (xOffset >= 1 && zOffset >= 1 && xOffset <= 3 && zOffset <= 3) {
                    continue;
                }
                BlockPos position = new BlockPos(startX + xOffset, y, startZ + zOffset);
                BlockPos floor = position.down();
                if (this.pet.world.getBlockState(floor).isSolidSide(this.pet.world, floor, Direction.UP)
                        && !this.pet.world.getBlockState(position).getMaterial().isSolid()
                        && !this.pet.world.getBlockState(position.up()).getMaterial().isSolid()) {
                    this.pet.setLocationAndAngles(position.getX() + 0.5D, position.getY(),
                        position.getZ() + 0.5D, this.pet.rotationYaw, this.pet.rotationPitch);
                    this.navigator.clearPath();
                    return;
                }
            }
        }
    }

    private static double square(float value) {
        return (double) value * (double) value;
    }
}
