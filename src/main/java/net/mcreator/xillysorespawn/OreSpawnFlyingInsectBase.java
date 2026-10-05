package net.mcreator.xillysorespawn.entity;

import net.minecraft.block.Blocks;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.horse.AbstractHorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;

/** Shared free-flight logic for Butterfly and Luna Moth. */
abstract class OreSpawnFlyingInsectBase extends CreatureEntity {
    private static final DataParameter<Integer> VARIANT = EntityDataManager.createKey(OreSpawnFlyingInsectBase.class, DataSerializers.VARINT);
    protected BlockPos flightTarget;

    protected OreSpawnFlyingInsectBase(EntityType<? extends OreSpawnFlyingInsectBase> type, World world) {
        super(type, world);
        this.setNoGravity(true);
    }
    protected abstract int variantCount();
    protected abstract int horizontalRange();
    protected abstract String[] destinationDimensions();
    protected boolean seeksTorchesAtNight() { return false; }
    protected boolean attacksInDangerDimension() { return false; }

    @Override protected void registerData() { super.registerData(); this.dataManager.register(VARIANT, 0); }
    public int getVariant() { return this.dataManager.get(VARIANT); }
    public void setVariant(int value) { this.dataManager.set(VARIANT, Math.floorMod(value, Math.max(1, this.variantCount()))); }

    @Override public net.minecraft.entity.ILivingEntityData onInitialSpawn(net.minecraft.world.IServerWorld world,
            net.minecraft.world.DifficultyInstance difficulty, net.minecraft.entity.SpawnReason reason,
            net.minecraft.entity.ILivingEntityData data, CompoundNBT nbt) {
        this.setVariant(this.rand.nextInt(Math.max(1, this.variantCount())));
        return super.onInitialSpawn(world, difficulty, reason, data, nbt);
    }

    @Override protected void registerGoals() { super.registerGoals(); }

    protected BlockPos findTorch() {
        BlockPos origin = this.getPosition();
        BlockPos best = null;
        double bestDistance = Double.MAX_VALUE;
        for (int radius = 2; radius < 15; radius += radius < 6 ? 1 : 2) {
            for (int x = -radius; x <= radius; x++) for (int y = -radius; y <= radius; y++)
                for (int z = -radius; z <= radius; z++) {
                    if (Math.abs(x) != radius && Math.abs(y) != radius && Math.abs(z) != radius) continue;
                    BlockPos p = origin.add(x, y, z);
                    if (this.world.getBlockState(p).getBlock() != Blocks.TORCH) continue;
                    double d = origin.distanceSq(p);
                    if (d < bestDistance) { bestDistance = d; best = p.up(); }
                }
            if (best != null) return best;
        }
        return null;
    }

    private void chooseTarget() {
        if (this.seeksTorchesAtNight() && !this.world.isDaytime() && this.rand.nextInt(10) == 0) {
            BlockPos torch = this.findTorch();
            if (torch != null) { this.flightTarget = torch; return; }
        }
        BlockPos origin = this.getPosition();
        for (int tries = 0; tries < 25; tries++) {
            int range = this.horizontalRange();
            BlockPos p = origin.add(this.rand.nextInt(range) - this.rand.nextInt(range),
                this.rand.nextInt(6) - 2, this.rand.nextInt(range) - this.rand.nextInt(range));
            if (this.world.isAirBlock(p) && OreSpawnLogic.hasClearPath(this, p.getX(), p.getY(), p.getZ())) {
                this.flightTarget = p;
                return;
            }
        }
        this.flightTarget = origin.up();
    }

    private LivingEntity dangerTarget() {
        if (!this.attacksInDangerDimension() || this.getVariant() != 1
                || !OreSpawnLogic.isDimension(this.world, "dimension_danger", "danger", "unstable_ant_dimension")) return null;
        return OreSpawnLogic.nearestTarget(this, this.getBoundingBox().grow(8.0D, 5.0D, 8.0D), e ->
            (e instanceof PlayerEntity && !((PlayerEntity)e).abilities.isCreativeMode) || e instanceof AbstractHorseEntity);
    }

    @Override public void livingTick() {
        super.livingTick();
        this.setNoGravity(true);
        this.setMotion(this.getMotion().mul(1.0D, 0.6D, 1.0D));
        if (this.world.isRemote) return;
        LivingEntity target = this.dangerTarget();
        if (target != null) {
            this.flightTarget = target.getPosition().up();
            if (this.getDistanceSq(target) < 6.0D && this.rand.nextBoolean()) this.attackEntityAsMob(target);
        }
        if (this.flightTarget == null || this.rand.nextInt(100) == 0
                || this.getDistanceSq(this.flightTarget.getX() + 0.5D, this.flightTarget.getY() + 0.5D,
                    this.flightTarget.getZ() + 0.5D) < 4.0D) this.chooseTarget();
        OreSpawnEntityBase.steerFlight(this, this.flightTarget, 0.5D, this.seeksTorchesAtNight() ? 0.68D : 0.7D,
            0.1D, 0.1D, 1.0F);
    }

    @Override public boolean attackEntityAsMob(Entity target) {
        if (this.world.getDifficulty() == Difficulty.PEACEFUL || this.rand.nextBoolean()) return false;
        return target.attackEntityFrom(DamageSource.causeMobDamage(this), 1.0F);
    }

    @Override public ActionResultType func_230254_b_(PlayerEntity player, Hand hand) {
        if (hand != Hand.MAIN_HAND || !player.getHeldItem(hand).isEmpty()) return super.func_230254_b_(player, hand);
        if (!(player instanceof ServerPlayerEntity)) return ActionResultType.SUCCESS;
        return OreSpawnLogic.toggleDimension((ServerPlayerEntity)player, this.destinationDimensions())
            ? ActionResultType.SUCCESS : super.func_230254_b_(player, hand);
    }

    @Override public boolean onLivingFall(float distance, float multiplier) { return false; }
    @Override public boolean isPushedByWater() { return false; }
    @Override public boolean canBeCollidedWith() { return true; }
    @Override public void writeAdditional(CompoundNBT nbt) { super.writeAdditional(nbt); nbt.putInt("InsectVariant", this.getVariant()); }
    @Override public void readAdditional(CompoundNBT nbt) { super.readAdditional(nbt); this.setVariant(nbt.getInt("InsectVariant")); }
}
