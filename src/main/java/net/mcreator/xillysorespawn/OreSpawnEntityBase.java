package net.mcreator.xillysorespawn.entity;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;

import javax.annotation.Nullable;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.block.Blocks;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import net.mcreator.xillysorespawn.entity.renderer.RenderInfo;
import net.mcreator.xillysorespawn.entity.renderer.RenderSpiderRobotInfo;

/** Shared state and movement primitives used by the faithful 1.7.10 ports. */
final class OreSpawnEntityBase {
    private OreSpawnEntityBase() {
    }

    static LivingEntity nearest(MobEntity seeker, double xz, double y,
            Predicate<LivingEntity> predicate) {
        return OreSpawnLogic.nearestTarget(seeker, seeker.getBoundingBox().grow(xz, y, xz), predicate);
    }

    static boolean validHostileTarget(MobEntity seeker, LivingEntity target) {
        if (target == null || target == seeker || !target.isAlive()
                || !seeker.getEntitySenses().canSee(target) || OreSpawnLogic.isIgnoreable(target)) {
            return false;
        }
        return !(target instanceof PlayerEntity) || !((PlayerEntity) target).abilities.isCreativeMode;
    }

    static boolean royalTarget(MobEntity seeker, LivingEntity target) {
        return target!=null && target!=seeker && target.isAlive() && !target.isSpectator()
            && (!(target instanceof PlayerEntity) || !((PlayerEntity)target).abilities.isCreativeMode)
            && seeker.getEntitySenses().canSee(target);
    }

    @Nullable
    static BlockPos randomAirTarget(MobEntity entity, int horizontal, int vertical,
            int tries, int verticalBias) {
        BlockPos origin = entity.getPosition();
        for (int i = 0; i < tries; i++) {
            BlockPos candidate = origin.add(entity.getRNG().nextInt(horizontal * 2 + 1) - horizontal,
                entity.getRNG().nextInt(vertical * 2 + 1) - vertical + verticalBias,
                entity.getRNG().nextInt(horizontal * 2 + 1) - horizontal);
            if (entity.world.isAirBlock(candidate)
                    && OreSpawnLogic.hasClearPath(entity, candidate.getX() + 0.5D,
                        candidate.getY() + 0.5D, candidate.getZ() + 0.5D)) {
                return candidate;
            }
        }
        return null;
    }

    static void steerFlight(MobEntity entity, BlockPos target, double horizontalSpeed,
            double verticalSpeed, double horizontalBlend, double verticalBlend, float yawDivisor) {
        if (target == null) return;
        double dx = target.getX() + 0.5D - entity.getPosX();
        double dy = target.getY() + 0.1D - entity.getPosY();
        double dz = target.getZ() + 0.5D - entity.getPosZ();
        Vector3d motion = entity.getMotion();
        // Normalize horizontal steering and brake near the destination instead of
        // flipping full axis velocities every time a block coordinate is crossed.
        double horizontalDistance = Math.max(2.0D, Math.sqrt(dx * dx + dz * dz));
        entity.setMotion(motion.x + (dx / horizontalDistance * horizontalSpeed - motion.x) * horizontalBlend,
            motion.y + (MathHelper.clamp(dy / 3.0D, -1.0D, 1.0D) * verticalSpeed - motion.y) * verticalBlend,
            motion.z + (dz / horizontalDistance * horizontalSpeed - motion.z) * horizontalBlend);
        float desired = (float) (Math.atan2(entity.getMotion().z, entity.getMotion().x)
            * 180.0D / Math.PI) - 90.0F;
        entity.rotationYaw += MathHelper.wrapDegrees(desired - entity.rotationYaw) / yawDivisor;
        entity.renderYawOffset = entity.rotationYaw;
    }

    static BlockPos royalFlight(MobEntity entity, @Nullable BlockPos destination, double speed,
            Predicate<LivingEntity> prey) {
        LivingEntity target=entity.getAttackTarget();
        if (target!=null && (!target.isAlive() || !prey.test(target)
                || entity.getDistanceSq(target)>16384.0D)) target=null;
        if (OreSpawnLogic.playNicely!=0) target=null;
        else if (target==null && entity.ticksExisted%10==0) target=nearest(entity,72,48,prey);
        entity.setAttackTarget(target);
        if (destination==null || entity.ticksExisted%24==0
                || entity.getDistanceSq(Vector3d.copyCentered(destination))<9.0D) {
            BlockPos origin=target==null?entity.getPosition():target.getPosition();
            int radius=target==null?32:8;
            destination=origin.add(entity.getRNG().nextInt(radius*2+1)-radius,
                target==null?entity.getRNG().nextInt(19)-6:entity.getRNG().nextInt(9)+2,
                entity.getRNG().nextInt(radius*2+1)-radius);
            destination=new BlockPos(destination.getX(),MathHelper.clamp(destination.getY(),8,240),destination.getZ());
        }
        if (target!=null) {
            entity.getLookController().setLookPositionWithEntity(target,15,15);
            double reach=entity.getWidth()/2.0D+target.getWidth()/2.0D+4.0D;
            if (entity.ticksExisted%10==0 && entity.getDistanceSq(target)<reach*reach) entity.attackEntityAsMob(target);
        }
        steerFlight(entity,destination,speed,0.7D,0.16D,0.12D,6.0F);
        return destination;
    }

    static void knockAway(MobEntity attacker, Entity target, double horizontal, double vertical) {
        double angle = Math.atan2(target.getPosZ() - attacker.getPosZ(),
            target.getPosX() - attacker.getPosX());
        target.setMotion(target.getMotion().add(Math.cos(angle) * horizontal,
            target.isOnGround() || target instanceof PlayerEntity ? vertical * 2.0D : vertical,
            Math.sin(angle) * horizontal));
        target.velocityChanged = true;
    }
}

abstract class OreSpawnMonsterBase extends MonsterEntity {
    private static final DataParameter<Integer> ATTACKING = EntityDataManager.createKey(
        OreSpawnMonsterBase.class, DataSerializers.VARINT);
    private static final DataParameter<Boolean> SCREAMING = EntityDataManager.createKey(
        OreSpawnMonsterBase.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Integer> ACTIVITY = EntityDataManager.createKey(
        OreSpawnMonsterBase.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> SHIELDING = EntityDataManager.createKey(
        OreSpawnMonsterBase.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> ACTIVATED = EntityDataManager.createKey(
        OreSpawnMonsterBase.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> HAT_COLOR = EntityDataManager.createKey(
        OreSpawnMonsterBase.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> KILL_COUNT = EntityDataManager.createKey(
        OreSpawnMonsterBase.class, DataSerializers.VARINT);
    private static final DataParameter<Float> MODEL_SCALE = EntityDataManager.createKey(
        OreSpawnMonsterBase.class, DataSerializers.FLOAT);
    protected final RenderInfo renderInfo = new RenderInfo();
    protected final RenderSpiderRobotInfo spiderRobotInfo = new RenderSpiderRobotInfo();
    protected int attackAnimationTicks;
    protected UUID ownerId;

    protected OreSpawnMonsterBase(EntityType<? extends OreSpawnMonsterBase> type, World world) {
        super(type, world);
    }

    @Override protected void registerData() {
        super.registerData();
        this.dataManager.register(ATTACKING, 0);
        this.dataManager.register(SCREAMING, false);
        this.dataManager.register(ACTIVITY, 0);
        this.dataManager.register(SHIELDING, 0);
        this.dataManager.register(ACTIVATED, 0);
        this.dataManager.register(HAT_COLOR, 0);
        this.dataManager.register(KILL_COUNT, 0);
        this.dataManager.register(MODEL_SCALE, 1.0F);
    }

    public int getAttacking() { return this.dataManager.get(ATTACKING); }
    public void setAttacking(int value) { this.dataManager.set(ATTACKING, value); }
    public boolean isScreaming() { return this.dataManager.get(SCREAMING); }
    public void setScreaming(boolean value) { this.dataManager.set(SCREAMING, value); }
    public int getActivity() { return this.dataManager.get(ACTIVITY); }
    public void setActivity(int value) { this.dataManager.set(ACTIVITY, value); }
    public int getShielding() { return this.dataManager.get(SHIELDING); }
    public void setShielding(int value) { this.dataManager.set(SHIELDING, value); }
    public int get_is_activated() { return this.dataManager.get(ACTIVATED); }
    public void set_is_activated(int value) { this.dataManager.set(ACTIVATED, value); }
    public int getHatColor() { return this.dataManager.get(HAT_COLOR); }
    public void setHatColor(int value) { this.dataManager.set(HAT_COLOR, value); }
    public int getKillCount() { return this.dataManager.get(KILL_COUNT); }
    public void setKillCount(int value) { this.dataManager.set(KILL_COUNT, value); }
    public float getPitchBlackScale() { return this.dataManager.get(MODEL_SCALE); }
    public void setPitchBlackScale(float value) { this.dataManager.set(MODEL_SCALE, value); }
    public int getBeingRidden() { return this.isBeingRidden() ? 1 : 0; }
    @Nullable public LivingEntity getOwner() {
        return this.ownerId == null ? null : this.world.getPlayerByUuid(this.ownerId);
    }
    public boolean isOwner(PlayerEntity player) {
        return player != null && player.getUniqueID().equals(this.ownerId);
    }
    public void setOwner(PlayerEntity player) {
        this.ownerId = player == null ? null : player.getUniqueID();
    }
    public RenderInfo getRenderInfo() { return this.renderInfo; }
    public void setRenderInfo(RenderInfo value) {
        this.renderInfo.ri1 = value.ri1; this.renderInfo.ri2 = value.ri2;
        this.renderInfo.ri3 = value.ri3; this.renderInfo.ri4 = value.ri4;
        this.renderInfo.rf1 = value.rf1; this.renderInfo.rf2 = value.rf2;
        this.renderInfo.rf3 = value.rf3; this.renderInfo.rf4 = value.rf4;
    }
    public int getPlayNicely() { return OreSpawnLogic.playNicely; }
    public boolean isChildModel() { return this.isChild(); }
    public java.util.Random getModelRandom() { return this.rand; }
    public int getHydroHealth() { return MathHelper.ceil(this.getHealth()); }
    public int getSeaMonsterHealth() { return MathHelper.ceil(this.getHealth()); }
    public int getSeaViperHealth() { return MathHelper.ceil(this.getHealth()); }
    public int getTriffidHealth() { return MathHelper.ceil(this.getHealth()); }
    public int getOpenClosed() { return this.getShielding(); }
    public void setOpenClosed(int value) { this.setShielding(value); }
    public int getPower() { return this.getKillCount(); }
    public void setPower(int value) { this.setKillCount(value); }
    public int getIsHappy() { return this.getActivity(); }
    public boolean isHappy() { return this.getActivity() != 0; }
    public RenderSpiderRobotInfo getRenderSpiderRobotInfo() { return this.spiderRobotInfo; }

    @Override public boolean attackEntityAsMob(Entity target) {
        boolean hit = super.attackEntityAsMob(target);
        if (hit) {
            this.setAttacking(1);
            this.attackAnimationTicks = 10;
        }
        return hit;
    }

    @Override public void livingTick() {
        super.livingTick();
        if (!this.world.isRemote && this.attackAnimationTicks > 0
                && --this.attackAnimationTicks == 0) this.setAttacking(0);
    }

    @Override public void writeAdditional(CompoundNBT nbt) {
        super.writeAdditional(nbt);
        nbt.putBoolean("Screaming", this.isScreaming());
        nbt.putInt("OreSpawnActivity", this.getActivity());
        nbt.putInt("OreSpawnShielding", this.getShielding());
        nbt.putInt("OreSpawnActivated", this.get_is_activated());
        nbt.putInt("OreSpawnHatColor", this.getHatColor());
        nbt.putInt("OreSpawnKillCount", this.getKillCount());
        nbt.putFloat("OreSpawnModelScale", this.getPitchBlackScale());
        if (this.ownerId != null) nbt.putUniqueId("OreSpawnOwner", this.ownerId);
    }

    @Override public void readAdditional(CompoundNBT nbt) {
        super.readAdditional(nbt);
        this.setScreaming(nbt.getBoolean("Screaming"));
        this.setActivity(nbt.getInt("OreSpawnActivity"));
        this.setShielding(nbt.getInt("OreSpawnShielding"));
        this.set_is_activated(nbt.getInt("OreSpawnActivated"));
        this.setHatColor(nbt.getInt("OreSpawnHatColor"));
        this.setKillCount(nbt.getInt("OreSpawnKillCount"));
        if (nbt.contains("OreSpawnModelScale")) this.setPitchBlackScale(nbt.getFloat("OreSpawnModelScale"));
        this.ownerId = nbt.hasUniqueId("OreSpawnOwner") ? nbt.getUniqueId("OreSpawnOwner") : null;
    }
}

abstract class OreSpawnCreatureBase extends CreatureEntity {
    private static final DataParameter<Integer> ATTACKING = EntityDataManager.createKey(
        OreSpawnCreatureBase.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> VARIANT = EntityDataManager.createKey(
        OreSpawnCreatureBase.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> SINGING = EntityDataManager.createKey(
        OreSpawnCreatureBase.class, DataSerializers.VARINT);
    private static final DataParameter<Boolean> SITTING = EntityDataManager.createKey(
        OreSpawnCreatureBase.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Integer> ACTIVITY = EntityDataManager.createKey(
        OreSpawnCreatureBase.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> ACTIVATED = EntityDataManager.createKey(
        OreSpawnCreatureBase.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> HAT_COLOR = EntityDataManager.createKey(
        OreSpawnCreatureBase.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> KILL_COUNT = EntityDataManager.createKey(
        OreSpawnCreatureBase.class, DataSerializers.VARINT);
    protected final RenderInfo renderInfo = new RenderInfo();
    protected final RenderSpiderRobotInfo spiderRobotInfo = new RenderSpiderRobotInfo();
    protected int attackAnimationTicks;
    protected int blinkLength;
    protected int blinkTicks;
    protected BlockPos flightTarget;
    protected UUID ownerId;

    protected OreSpawnCreatureBase(EntityType<? extends OreSpawnCreatureBase> type, World world) {
        super(type, world);
        this.blinkLength = 20 + this.rand.nextInt(20);
    }

    @Override protected void registerData() {
        super.registerData();
        this.dataManager.register(ATTACKING, 0);
        this.dataManager.register(VARIANT, 0);
        this.dataManager.register(SINGING, 0);
        this.dataManager.register(SITTING, false);
        this.dataManager.register(ACTIVITY, 0);
        this.dataManager.register(ACTIVATED, 0);
        this.dataManager.register(HAT_COLOR, 0);
        this.dataManager.register(KILL_COUNT, 0);
    }

    public int getAttacking() { return this.dataManager.get(ATTACKING); }
    public void setAttacking(int value) { this.dataManager.set(ATTACKING, value); }
    public int getVariant() { return this.dataManager.get(VARIANT); }
    public void setVariant(int value) { this.dataManager.set(VARIANT, value); }
    public int getSinging() { return this.dataManager.get(SINGING); }
    public void setSinging(int value) { this.dataManager.set(SINGING, value); }
    public boolean isSitting() { return this.dataManager.get(SITTING); }
    public void setSitting(boolean value) { this.dataManager.set(SITTING, value); }
    public int getActivity() { return this.dataManager.get(ACTIVITY); }
    public void setActivity(int value) { this.dataManager.set(ACTIVITY, value); }
    public int get_is_activated() { return this.dataManager.get(ACTIVATED); }
    public void set_is_activated(int value) { this.dataManager.set(ACTIVATED, value); }
    public int getHatColor() { return this.dataManager.get(HAT_COLOR); }
    public void setHatColor(int value) { this.dataManager.set(HAT_COLOR, value); }
    public int getKillCount() { return this.dataManager.get(KILL_COUNT); }
    public void setKillCount(int value) { this.dataManager.set(KILL_COUNT, value); }
    public int getBeingRidden() { return this.isBeingRidden() ? 1 : 0; }
    public float getBlink() { return this.blinkTicks < this.blinkLength / 2 ? 240.0F : 0.0F; }
    public RenderInfo getRenderInfo() { return this.renderInfo; }
    public void setRenderInfo(RenderInfo value) {
        this.renderInfo.ri1 = value.ri1; this.renderInfo.ri2 = value.ri2;
        this.renderInfo.ri3 = value.ri3; this.renderInfo.ri4 = value.ri4;
        this.renderInfo.rf1 = value.rf1; this.renderInfo.rf2 = value.rf2;
        this.renderInfo.rf3 = value.rf3; this.renderInfo.rf4 = value.rf4;
    }
    public int getPlayNicely() { return OreSpawnLogic.playNicely; }
    public boolean isChildModel() { return this.isChild(); }
    public java.util.Random getModelRandom() { return this.rand; }
    public int getHydroHealth() { return MathHelper.ceil(this.getHealth()); }
    public int getSkin() { return this.getVariant(); }
    public void setSkin(int value) { this.setVariant(value); }
    public int getSpyroFire() { return this.get_is_activated(); }
    public void setSpyroFire(int value) { this.set_is_activated(value); }
    public int getThePrinceAdultFire() { return this.get_is_activated(); }
    public void setThePrinceAdultFire(int value) { this.set_is_activated(value); }
    public int getThePrinceTeenFire() { return this.get_is_activated(); }
    public void setThePrinceTeenFire(int value) { this.set_is_activated(value); }
    public int getPower() { return this.getKillCount(); }
    public void setPower(int value) { this.setKillCount(value); }
    public int getHead1Ext() { return this.renderInfo.ri1; }
    public int getHead2Ext() { return this.renderInfo.ri2; }
    public int getHead3Ext() { return this.renderInfo.ri3; }
    public void setHead1Ext(int value) { this.renderInfo.ri1 = value; }
    public void setHead2Ext(int value) { this.renderInfo.ri2 = value; }
    public void setHead3Ext(int value) { this.renderInfo.ri3 = value; }
    public int getThePrinceAdultHealth() { return MathHelper.ceil(this.getHealth()); }
    public int getThePrinceTeenHealth() { return MathHelper.ceil(this.getHealth()); }
    public RenderSpiderRobotInfo getRenderSpiderRobotInfo() { return this.spiderRobotInfo; }

    @Nullable public LivingEntity getOwner() {
        if (this.ownerId == null) return null;
        PlayerEntity player = this.world.getPlayerByUuid(this.ownerId);
        return player;
    }
    public boolean isOwner(PlayerEntity player) { return player != null && player.getUniqueID().equals(this.ownerId); }
    public void setOwner(PlayerEntity player) { this.ownerId = player == null ? null : player.getUniqueID(); }

    @Override public boolean attackEntityAsMob(Entity target) {
        boolean hit = super.attackEntityAsMob(target);
        if (hit) {
            this.setAttacking(1);
            this.attackAnimationTicks = 10;
        }
        return hit;
    }

    @Override public void livingTick() {
        super.livingTick();
        if (++this.blinkTicks > this.blinkLength) this.blinkTicks = 0;
        if (!this.world.isRemote && this.attackAnimationTicks > 0
                && --this.attackAnimationTicks == 0) this.setAttacking(0);
        int singing = this.getSinging();
        if (!this.world.isRemote && singing > 0) this.setSinging(singing - 1);
    }

    @Override public void writeAdditional(CompoundNBT nbt) {
        super.writeAdditional(nbt);
        nbt.putInt("OreSpawnVariant", this.getVariant());
        nbt.putBoolean("OreSpawnSitting", this.isSitting());
        nbt.putInt("OreSpawnActivity", this.getActivity());
        nbt.putInt("OreSpawnActivated", this.get_is_activated());
        nbt.putInt("OreSpawnHatColor", this.getHatColor());
        nbt.putInt("OreSpawnKillCount", this.getKillCount());
        if (this.ownerId != null) nbt.putUniqueId("OreSpawnOwner", this.ownerId);
    }

    @Override public void readAdditional(CompoundNBT nbt) {
        super.readAdditional(nbt);
        this.setVariant(nbt.getInt("OreSpawnVariant"));
        this.setSitting(nbt.getBoolean("OreSpawnSitting"));
        this.setActivity(nbt.getInt("OreSpawnActivity"));
        this.set_is_activated(nbt.getInt("OreSpawnActivated"));
        this.setHatColor(nbt.getInt("OreSpawnHatColor"));
        this.setKillCount(nbt.getInt("OreSpawnKillCount"));
        this.ownerId = nbt.hasUniqueId("OreSpawnOwner") ? nbt.getUniqueId("OreSpawnOwner") : null;
    }

    @Override public boolean onLivingFall(float distance, float multiplier) { return false; }
}

abstract class OreSpawnBossBase extends OreSpawnMonsterBase {
    protected OreSpawnBossBase(EntityType<? extends OreSpawnBossBase> type, World world) {
        super(type, world);
    }

    protected int wanderRange() { return 14; }
    protected double combatSpeed() { return 1.0D; }
    protected double searchRange() { return 16.0D; }
    protected double verticalSearchRange() { return 8.0D; }
    protected double meleeDistanceSq() {
        double reach = this.getWidth() * 0.5D + 2.0D;
        return reach * reach;
    }
    protected int targetInterval() { return 10; }
    protected boolean originalTarget(LivingEntity target) {
        return OreSpawnEntityBase.validHostileTarget(this, target)
            && (target instanceof PlayerEntity || OreSpawnLogic.isAttackableNonMob(target));
    }

    @Override protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, this.combatSpeed(), false));
        this.goalSelector.addGoal(5, new OreSpawnWanderGoal(this, this.wanderRange(), 120,
            this.combatSpeed(), false));
        this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 16.0F));
        this.goalSelector.addGoal(7, new LookRandomlyGoal(this));
        this.targetSelector.addGoal(0, new HurtByTargetGoal(this));
    }

    protected void originalCombatTick() {
        if (OreSpawnLogic.playNicely != 0 || this.world.getDifficulty().getId() == 0) {
            this.setAttackTarget(null);
            return;
        }
        LivingEntity target = this.getAttackTarget();
        if ((target == null || !this.originalTarget(target)) && this.rand.nextInt(this.targetInterval()) == 0) {
            target = OreSpawnEntityBase.nearest(this, this.searchRange(), this.verticalSearchRange(),
                this::originalTarget);
            this.setAttackTarget(target);
        }
        if (target == null) return;
        this.getLookController().setLookPositionWithEntity(target, 10.0F, 10.0F);
        if (this.getDistanceSq(target) <= this.meleeDistanceSq()) this.attackEntityAsMob(target);
        else this.getNavigator().tryMoveToEntityLiving(target, this.combatSpeed());
    }

    @Override public void livingTick() {
        super.livingTick();
        if (!this.world.isRemote) this.originalCombatTick();
    }
}

/** Ground equivalent of the old hand-written target scan used by most OreSpawn mobs. */
abstract class OreSpawnGroundMonsterBase extends OreSpawnMonsterBase {
    protected OreSpawnGroundMonsterBase(EntityType<? extends OreSpawnGroundMonsterBase> type, World world) {
        super(type, world);
    }

    protected double searchRange() { return 12.0D; }
    protected double verticalSearchRange() { return 4.0D; }
    protected double combatSpeed() { return 1.0D; }
    protected double meleeDistanceSq() {
        double reach = 2.0D + this.getWidth() * 0.5D;
        return reach * reach;
    }
    protected int targetInterval() { return 6; }
    protected boolean originalTarget(LivingEntity target) {
        return OreSpawnEntityBase.validHostileTarget(this, target)
            && !(target instanceof MonsterEntity);
    }

    @Override protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, this.combatSpeed(), false));
        this.goalSelector.addGoal(5, new OreSpawnWanderGoal(this, 14, 120,
            this.combatSpeed(), false));
        this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 12.0F));
        this.goalSelector.addGoal(7, new LookRandomlyGoal(this));
        this.targetSelector.addGoal(0, new HurtByTargetGoal(this));
    }

    protected void originalCombatTick() {
        if (OreSpawnLogic.playNicely != 0 || this.world.getDifficulty().getId() == 0) {
            this.setAttackTarget(null);
            this.setAttacking(0);
            return;
        }
        LivingEntity target = this.getAttackTarget();
        if ((target == null || !this.originalTarget(target)) && this.rand.nextInt(this.targetInterval()) == 0) {
            target = OreSpawnEntityBase.nearest(this, this.searchRange(),
                this.verticalSearchRange(), this::originalTarget);
            this.setAttackTarget(target);
        }
        if (target == null) { this.setAttacking(0); return; }
        this.getLookController().setLookPositionWithEntity(target, 10.0F, 10.0F);
        if (this.getDistanceSq(target) <= this.meleeDistanceSq()) this.attackEntityAsMob(target);
        else this.getNavigator().tryMoveToEntityLiving(target, this.combatSpeed());
    }

    @Override public void livingTick() {
        super.livingTick();
        if (!this.world.isRemote) this.originalCombatTick();
    }
}

abstract class OreSpawnEnderBase extends OreSpawnMonsterBase {
    private int teleportDelay;

    protected OreSpawnEnderBase(EntityType<? extends OreSpawnEnderBase> type, World world) {
        super(type, world);
        this.stepHeight = 1.0F;
    }

    @Override protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0D, false));
        this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 12.0F));
        this.goalSelector.addGoal(7, new LookRandomlyGoal(this));
        this.targetSelector.addGoal(0, new HurtByTargetGoal(this));
    }

    private boolean playerIsStaring(PlayerEntity player) {
        if (player.getItemStackFromSlot(net.minecraft.inventory.EquipmentSlotType.HEAD).getItem()
                == Blocks.CARVED_PUMPKIN.asItem()) return false;
        Vector3d look = player.getLook(1.0F).normalize();
        Vector3d toMob = new Vector3d(this.getPosX() - player.getPosX(),
            this.getPosYEye() - player.getPosYEye(), this.getPosZ() - player.getPosZ());
        double distance = toMob.length();
        toMob = toMob.normalize();
        return look.dotProduct(toMob) > 1.0D - 0.025D / distance
            && player.canEntityBeSeen(this);
    }

    protected boolean teleportRandomly() {
        return this.attemptTeleport(this.getPosX() + (this.rand.nextDouble() - 0.5D) * 64.0D,
            this.getPosY() + this.rand.nextInt(64) - 32,
            this.getPosZ() + (this.rand.nextDouble() - 0.5D) * 64.0D, true);
    }

    protected boolean teleportAwayFrom(Entity target) {
        Vector3d away = new Vector3d(this.getPosX() - target.getPosX(),
            this.getPosYHeight(0.5D) - target.getPosYEye(), this.getPosZ() - target.getPosZ()).normalize();
        return this.attemptTeleport(this.getPosX() + (this.rand.nextDouble() - 0.5D) * 8.0D + away.x * 16.0D,
            this.getPosY() + this.rand.nextInt(16) - 8,
            this.getPosZ() + (this.rand.nextDouble() - 0.5D) * 8.0D + away.z * 16.0D, true);
    }

    @Override public void livingTick() {
        super.livingTick();
        if (this.world.isRemote) {
            for (int i = 0; i < 2; i++) this.world.addParticle(ParticleTypes.PORTAL,
                this.getPosXRandom(0.5D), this.getPosYRandom(), this.getPosZRandom(0.5D),
                (this.rand.nextDouble() - 0.5D) * 2.0D, -this.rand.nextDouble(),
                (this.rand.nextDouble() - 0.5D) * 2.0D);
            return;
        }
        if (this.isWet() || this.isBurning()) {
            this.setScreaming(false);
            this.teleportRandomly();
        } else if (this.world.isDaytime() && this.world.canSeeSky(this.getPosition())
                && this.rand.nextFloat() * 30.0F < 1.2F) {
            this.setAttackTarget(null);
            this.setScreaming(false);
            this.teleportRandomly();
        }
        PlayerEntity staring = this.world.getClosestPlayer(this, 64.0D);
        LivingEntity target = this.getAttackTarget();
        if (target == null && staring != null && !staring.abilities.isCreativeMode && this.playerIsStaring(staring)) {
            this.setAttackTarget(staring);
            this.setScreaming(true);
            target = staring;
        }
        if (target == null || !target.isAlive()) {
            this.setScreaming(false);
            this.teleportDelay = 0;
            return;
        }
        double distanceSq = this.getDistanceSq(target);
        if (target instanceof PlayerEntity && this.playerIsStaring((PlayerEntity) target)) {
            if (distanceSq < 16.0D) this.teleportRandomly();
            this.teleportDelay = 0;
        } else if (distanceSq > 256.0D && ++this.teleportDelay >= 30 && this.teleportAwayFrom(target)) {
            this.teleportDelay = 0;
        }
    }

    @Override public boolean attackEntityFrom(DamageSource source, float amount) {
        this.setScreaming(true);
        if (source.isProjectile()) {
            for (int i = 0; i < 16; i++) if (this.teleportRandomly()) return true;
            return false;
        }
        boolean hit = super.attackEntityFrom(source, amount);
        if (hit && source.getTrueSource() != null && this.rand.nextInt(10) != 0)
            this.teleportAwayFrom(source.getTrueSource());
        return hit;
    }
}

abstract class OreSpawnFlyingBase extends OreSpawnCreatureBase {
    protected OreSpawnFlyingBase(EntityType<? extends OreSpawnFlyingBase> type, World world) {
        super(type, world);
        this.setNoGravity(true);
    }

    protected int retargetFrequency() { return 40; }
    protected int horizontalRange() { return 8; }
    protected int verticalRange() { return 2; }
    protected double horizontalSpeed() { return 0.2D; }
    protected double verticalSpeed() { return 0.7D; }
    protected double horizontalBlend() { return 0.1D; }
    protected double verticalBlend() { return 0.1D; }
    protected float yawDivisor() { return 4.0F; }

    protected void chooseFlightTarget() {
        BlockPos target = OreSpawnEntityBase.randomAirTarget(this, this.horizontalRange(),
            this.verticalRange(), 25, 0);
        if (target != null) this.flightTarget = target;
    }

    protected void originalFlightTick() {
        if (this.flightTarget == null) this.flightTarget = this.getPosition();
        if (this.rand.nextInt(this.retargetFrequency()) == 0
                || this.getDistanceSq(Vector3d.copyCentered(this.flightTarget)) < 4.0D) this.chooseFlightTarget();
        OreSpawnEntityBase.steerFlight(this, this.flightTarget, this.horizontalSpeed(),
            this.verticalSpeed(), this.horizontalBlend(), this.verticalBlend(), this.yawDivisor());
    }

    @Override public void livingTick() {
        super.livingTick();
        this.setNoGravity(!this.isSitting());
        if (!this.world.isRemote) {
            if (!this.isSitting()) this.originalFlightTick();
            else {this.getNavigator().clearPath();this.setMotion(0,this.getMotion().y,0);}
        }
    }

    @Override public boolean canBreatheUnderwater() { return true; }
}

abstract class OreSpawnAquaticBase extends OreSpawnCreatureBase {
    protected int dryTicks;

    protected OreSpawnAquaticBase(EntityType<? extends OreSpawnAquaticBase> type, World world) {
        super(type, world);
    }

    protected int waterSearchRange() { return 10; }

    @Nullable protected BlockPos findWater() {
        BlockPos origin = this.getPosition();
        BlockPos best = null;
        double bestDistance = Double.MAX_VALUE;
        int range = this.waterSearchRange();
        for (int x = -range; x <= range; x++) for (int y = -4; y <= 4; y++)
            for (int z = -range; z <= range; z++) {
                BlockPos pos = origin.add(x, y, z);
                if (this.world.getFluidState(pos).isTagged(FluidTags.WATER)) {
                    double distance = pos.distanceSq(origin);
                    if (distance < bestDistance) { best = pos; bestDistance = distance; }
                }
            }
        return best;
    }

    @Override public void livingTick() {
        super.livingTick();
        if (this.world.isRemote) return;
        if (this.isInWater()) {
            this.dryTicks = 0;
            if (this.rand.nextInt(50) == 0 && this.getHealth() < this.getMaxHealth()) this.heal(1.0F);
        } else if (++this.dryTicks % 10 == 0) {
            BlockPos water = this.findWater();
            if (water != null) this.getNavigator().tryMoveToXYZ(water.getX(), water.getY(), water.getZ(), 1.0D);
            else if (this.rand.nextInt(25) == 1) this.attackEntityFrom(DamageSource.DRYOUT, 1.0F);
        }
    }

    @Override public boolean canBreatheUnderwater() { return true; }
}

abstract class OreSpawnFlyingMonsterBase extends OreSpawnMonsterBase {
    protected BlockPos flightTarget;

    protected OreSpawnFlyingMonsterBase(EntityType<? extends OreSpawnFlyingMonsterBase> type, World world) {
        super(type, world);
        this.setNoGravity(true);
    }

    protected int retargetFrequency() { return 40; }
    protected int horizontalRange() { return 8; }
    protected int verticalRange() { return 2; }
    protected double horizontalSpeed() { return 0.2D; }
    protected double verticalSpeed() { return 0.7D; }
    protected double horizontalBlend() { return 0.1D; }
    protected double verticalBlend() { return 0.1D; }
    protected float yawDivisor() { return 4.0F; }
    protected double searchRange() { return 12.0D; }
    protected double verticalSearchRange() { return 8.0D; }
    protected double meleeDistanceSq() { return 6.0D; }
    protected int targetInterval() { return 9; }
    protected boolean originalTarget(LivingEntity target) {
        return OreSpawnEntityBase.validHostileTarget(this, target)
            && !(target instanceof MonsterEntity);
    }

    protected void chooseFlightTarget() {
        BlockPos target = OreSpawnEntityBase.randomAirTarget(this, this.horizontalRange(),
            this.verticalRange(), 25, 0);
        if (target != null) this.flightTarget = target;
    }

    protected void originalFlightTick() {
        if (OreSpawnLogic.playNicely == 0 && this.world.getDifficulty().getId() != 0) {
            LivingEntity target = this.getAttackTarget();
            if ((target == null || !this.originalTarget(target)) && this.rand.nextInt(this.targetInterval()) == 0) {
                target = OreSpawnEntityBase.nearest(this, this.searchRange(),
                    this.verticalSearchRange(), this::originalTarget);
                this.setAttackTarget(target);
            }
            if (target != null) {
                this.flightTarget = target.getPosition().up();
                this.getLookController().setLookPositionWithEntity(target, 10.0F, 10.0F);
                if (this.getDistanceSq(target) <= this.meleeDistanceSq()) this.attackEntityAsMob(target);
            }
        } else {
            this.setAttackTarget(null);
            this.setAttacking(0);
        }
        if (this.flightTarget == null) this.flightTarget = this.getPosition();
        if (this.rand.nextInt(this.retargetFrequency()) == 0
                || this.getDistanceSq(Vector3d.copyCentered(this.flightTarget)) < 4.0D) this.chooseFlightTarget();
        OreSpawnEntityBase.steerFlight(this, this.flightTarget, this.horizontalSpeed(),
            this.verticalSpeed(), this.horizontalBlend(), this.verticalBlend(), this.yawDivisor());
    }

    @Override public void livingTick() {
        super.livingTick();
        this.setNoGravity(true);
        if (!this.world.isRemote) this.originalFlightTick();
    }

    @Override public boolean onLivingFall(float distance, float multiplier) { return false; }
    @Override public boolean canBreatheUnderwater() { return true; }
}

abstract class OreSpawnAquaticMonsterBase extends OreSpawnMonsterBase {
    protected int dryTicks;

    protected OreSpawnAquaticMonsterBase(EntityType<? extends OreSpawnAquaticMonsterBase> type, World world) {
        super(type, world);
    }

    protected int waterSearchRange() { return 10; }

    @Nullable protected BlockPos findWater() {
        BlockPos origin = this.getPosition();
        BlockPos best = null;
        double bestDistance = Double.MAX_VALUE;
        int range = this.waterSearchRange();
        for (int x = -range; x <= range; x++) for (int y = -4; y <= 4; y++)
            for (int z = -range; z <= range; z++) {
                BlockPos pos = origin.add(x, y, z);
                if (this.world.getFluidState(pos).isTagged(FluidTags.WATER)) {
                    double distance = pos.distanceSq(origin);
                    if (distance < bestDistance) { best = pos; bestDistance = distance; }
                }
            }
        return best;
    }

    @Override public void livingTick() {
        super.livingTick();
        if (this.world.isRemote) return;
        if (this.isInWater()) this.dryTicks = 0;
        else if (++this.dryTicks % 10 == 0) {
            BlockPos water = this.findWater();
            if (water != null) this.getNavigator().tryMoveToXYZ(water.getX(), water.getY(), water.getZ(), 1.0D);
            else if (this.rand.nextInt(25) == 1) this.attackEntityFrom(DamageSource.DRYOUT, 1.0F);
        }
    }

    @Override public boolean canBreatheUnderwater() { return true; }
}

/** Shared 1.16 implementation of the three original burrowing worm cycles. */
abstract class OreSpawnWormBase extends OreSpawnMonsterBase {
    protected int upCount;
    protected int downCount;
    protected boolean wormsSpawned;

    protected OreSpawnWormBase(EntityType<? extends OreSpawnWormBase> type, World world) {
        super(type, world);
        this.noClip = true;
    }

    protected abstract int initialUpCount();
    protected abstract int nextUpCount();
    protected abstract int downDuration();
    protected abstract double noticeRange();
    protected abstract double attackRange();
    protected abstract int attackChance();
    protected abstract double hiddenDamping();
    protected abstract int wormTier();
    protected String blockingWorm() { return wormTier() == 3 ? "worm_medium" : wormTier() == 2 ? "worm_small" : ""; }

    protected boolean hasBlockingWorm() {
        String name = blockingWorm();
        if (name.isEmpty()) return false;
        return !this.world.getEntitiesWithinAABB(LivingEntity.class,
            this.getBoundingBox().grow(8.0D), e -> e != this && OreSpawnLogic.isNamed(e, name)).isEmpty();
    }

    @Nullable protected PlayerEntity nearbyPlayer(double range) {
        PlayerEntity player = this.world.getClosestPlayer(this, range);
        return player != null && !player.abilities.isCreativeMode && !player.isSpectator() ? player : null;
    }

    protected boolean replaceableBurrowBlock(BlockPos pos) {
        return this.world.isAirBlock(pos) || this.world.getBlockState(pos).getBlock() == Blocks.FIRE;
    }

    protected void pointAt(LivingEntity target) {
        double dx = target.getPosX() - this.getPosX();
        double dz = target.getPosZ() - this.getPosZ();
        this.rotationYaw = this.renderYawOffset = (float)(Math.atan2(dz, dx) * 180.0D / Math.PI) - 90.0F;
    }

    protected void damageAndThrowArmor(PlayerEntity player) {
        EquipmentSlotType[] order = wormTier() == 3
            ? new EquipmentSlotType[]{EquipmentSlotType.HEAD, EquipmentSlotType.CHEST, EquipmentSlotType.FEET}
            : wormTier() == 2
                ? new EquipmentSlotType[]{EquipmentSlotType.FEET, EquipmentSlotType.LEGS}
                : new EquipmentSlotType[]{EquipmentSlotType.FEET};
        for (EquipmentSlotType slot : order) {
            ItemStack stack = player.getItemStackFromSlot(slot);
            if (stack.isEmpty()) continue;
            player.setItemStackToSlot(slot, ItemStack.EMPTY);
            int divisor = wormTier() == 3 ? 10 : wormTier() == 2 ? 15 : 20;
            int damage = Math.max(1, (stack.getMaxDamage() - stack.getDamage()) / divisor);
            stack.setDamage(Math.min(stack.getMaxDamage(), stack.getDamage() + damage));
            this.entityDropItem(stack, 1.5F);
            break;
        }
    }

    protected void spawnChildrenOnce() {
        if (wormTier() != 3 || wormsSpawned || this.world.isRemote) return;
        wormsSpawned = true;
        for (int i = 0; i < 20; i++) {
            OreSpawnLogic.spawn(this.world, "worm_small", this.getPosX() + this.rand.nextInt(6) - this.rand.nextInt(6),
                this.getPosY(), this.getPosZ() + this.rand.nextInt(6) - this.rand.nextInt(6));
            OreSpawnLogic.spawn(this.world, "worm_medium", this.getPosX() + this.rand.nextInt(5) - this.rand.nextInt(5),
                this.getPosY(), this.getPosZ() + this.rand.nextInt(5) - this.rand.nextInt(5));
        }
    }

    @Override public void livingTick() {
        super.livingTick();
        if (this.world.isRemote) return;
        if (this.upCount == 0 && this.downCount == 0) this.upCount = initialUpCount();
        PlayerEntity target = hasBlockingWorm() ? null : nearbyPlayer(noticeRange());
        boolean active = target != null || OreSpawnLogic.playNicely != 0;
        if (active) {
            if (target != null) pointAt(target);
            if (wormTier() == 3) {
                BlockPos body = this.getPosition();
                if (!replaceableBurrowBlock(body)) this.setMotion(this.getMotion().x, this.getMotion().y + 0.25D, this.getMotion().z);
                else this.noClip = false;
            } else if (this.upCount > 0) {
                if (--this.upCount == 0) this.downCount = downDuration();
                BlockPos body = this.getPosition();
                if (!replaceableBurrowBlock(body)) this.setMotion(this.getMotion().x,
                    this.getMotion().y + (wormTier() == 2 ? 0.20D : 0.15D), this.getMotion().z);
            } else if (this.downCount > 0) {
                --this.downCount;
            } else {
                this.upCount = nextUpCount();
            }
        } else {
            this.upCount = this.rand.nextInt(50);
            this.downCount = 0;
            this.noClip = true;
        }
        if (this.noClip) {
            Vector3d motion = this.getMotion();
            this.setMotion(0.0D, motion.y * hiddenDamping() - 0.01D, 0.0D);
        }
        if (target != null && OreSpawnLogic.playNicely == 0) {
            pointAt(target);
            if (wormTier() == 3 && !this.noClip) this.getNavigator().tryMoveToEntityLiving(target, 1.0D);
            if ((wormTier() == 3 || this.upCount > 0) && this.getDistanceSq(target) < attackRange() * attackRange()
                    && this.rand.nextInt(attackChance()) == 1 && this.attackEntityAsMob(target)) {
                int armorChance = wormTier() == 3 ? 4 : 6;
                if (this.rand.nextInt(armorChance) == 1) damageAndThrowArmor(target);
            }
        }
        spawnChildrenOnce();
    }

    @Override public boolean attackEntityFrom(DamageSource source, float amount) {
        return source == DamageSource.IN_WALL ? false : super.attackEntityFrom(source, amount);
    }
    @Override public boolean onLivingFall(float distance, float multiplier) { return false; }
    @Override public boolean canDespawn(double distanceToClosestPlayer) { return false; }
}
