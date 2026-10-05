package net.mcreator.xillysorespawn.entity;

import java.util.EnumSet;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.util.math.vector.Vector3d;

/** Exact 1.16.5 equivalent of OreSpawn's MyEntityAIWander variants. */
public final class OreSpawnWanderGoal extends Goal {
    private final CreatureEntity entity;
    private final int horizontalRange;
    private final int frequency;
    private final double speed;
    private final boolean stopAtOwner;
    private Vector3d destination;
    private boolean busy;

    public OreSpawnWanderGoal(CreatureEntity entity, int horizontalRange, int frequency,
            double speed, boolean stopAtOwner) {
        this.entity = entity;
        this.horizontalRange = horizontalRange;
        this.frequency = frequency;
        this.speed = speed;
        this.stopAtOwner = stopAtOwner;
        this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    public void setBusy(boolean busy) {
        this.busy = busy;
    }

    @Override
    public boolean shouldExecute() {
        if (this.busy || this.entity.getRNG().nextInt(this.frequency) != 0) {
            return false;
        }
        if (this.entity instanceof TameableEntity && ((TameableEntity) this.entity).isSitting()) {
            return false;
        }
        this.destination = RandomPositionGenerator.findRandomTarget(this.entity, this.horizontalRange, 7);
        return this.destination != null;
    }

    @Override
    public boolean shouldContinueExecuting() {
        if (this.stopAtOwner && this.entity instanceof TameableEntity) {
            LivingEntity owner = ((TameableEntity) this.entity).getOwner();
            if (owner != null && (int) this.entity.getPosX() == (int) owner.getPosX()
                    && (int) this.entity.getPosZ() == (int) owner.getPosZ()
                    && (int) this.entity.getPosY() < (int) owner.getPosY() + 2
                    && (int) this.entity.getPosY() > (int) owner.getPosY() - 2) {
                return false;
            }
        }
        return !this.entity.getNavigator().noPath();
    }

    @Override
    public void startExecuting() {
        this.entity.getNavigator().tryMoveToXYZ(this.destination.x, this.destination.y,
            this.destination.z, this.speed);
    }
}
