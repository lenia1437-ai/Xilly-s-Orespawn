package net.mcreator.xillysorespawn.entity;

import javax.annotation.Nullable;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.RandomWalkingGoal;
import net.minecraft.entity.ai.goal.SitGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.OwnerHurtByTargetGoal;
import net.minecraft.entity.ai.goal.OwnerHurtTargetGoal;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.BowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.TieredItem;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

/** Taming, equipment, skins, sitting and owner combat shared by both companions. */
abstract class OreSpawnCompanionBase extends TameableEntity {
    private static final DataParameter<Integer> SKIN = EntityDataManager.createKey(OreSpawnCompanionBase.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> WET_SKIN = EntityDataManager.createKey(OreSpawnCompanionBase.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> VOICE = EntityDataManager.createKey(OreSpawnCompanionBase.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> SPECIAL = EntityDataManager.createKey(OreSpawnCompanionBase.class, DataSerializers.VARINT);
    private int healTimer = 200;

    protected OreSpawnCompanionBase(EntityType<? extends OreSpawnCompanionBase> type, World world) {
        super(type, world);
        this.experienceValue = 0;
        this.setTamed(false);
    }
    protected abstract int drySkinCount();
    protected int wetSkinCount() { return 18; }
    protected abstract Item tamingItem();

    @Override protected void registerData() {
        super.registerData();
        this.dataManager.register(SKIN, 0);
        this.dataManager.register(WET_SKIN, 0);
        this.dataManager.register(VOICE, 0);
        this.dataManager.register(SPECIAL, 0);
    }
    public int getSkin() { return this.dataManager.get(SKIN); }
    public int getWetSkin() { return this.dataManager.get(WET_SKIN); }
    public int getVoice() { return this.dataManager.get(VOICE); }
    public int getSpecialSkin() { return this.dataManager.get(SPECIAL); }
    public void setSpecialSkin(int value) { this.dataManager.set(SPECIAL, value); }

    @Override public ILivingEntityData onInitialSpawn(IServerWorld world, net.minecraft.world.DifficultyInstance difficulty,
            SpawnReason reason, @Nullable ILivingEntityData data, @Nullable CompoundNBT nbt) {
        this.dataManager.set(SKIN, this.rand.nextInt(this.drySkinCount()));
        this.dataManager.set(WET_SKIN, this.rand.nextInt(this.wetSkinCount()));
        this.dataManager.set(VOICE, this.rand.nextInt(10));
        return super.onInitialSpawn(world, difficulty, reason, data, nbt);
    }

    @Override protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new SitGoal(this));
        this.goalSelector.addGoal(2, new FollowOwnerGoal(this, 1.4D, 12.0F, 1.5F, false));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.25D, Ingredient.fromItems(this.tamingItem()), false));
        this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1.25D, false));
        this.goalSelector.addGoal(7, new LookAtGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(8, new RandomWalkingGoal(this, 0.75D));
        this.goalSelector.addGoal(9, new LookRandomlyGoal(this));
        this.targetSelector.addGoal(0, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(1, new OwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
        if (OreSpawnLogic.playNicely == 0) this.targetSelector.addGoal(3,
            new NearestAttackableTargetGoal<>(this, MonsterEntity.class, 15, true, false, e -> true));
    }

    private void hearts(boolean success) {
        for (int i = 0; i < 7; i++) this.world.addParticle(success ? ParticleTypes.HEART : ParticleTypes.SMOKE,
            this.getPosX() + (this.rand.nextDouble() - 0.5D) * this.getWidth(), this.getPosY() + 0.5D + this.rand.nextDouble(),
            this.getPosZ() + (this.rand.nextDouble() - 0.5D) * this.getWidth(), 0.0D, 0.0D, 0.0D);
    }

    private boolean equipFrom(PlayerEntity player, ItemStack held) {
        EquipmentSlotType slot = null;
        if (held.getItem() instanceof ArmorItem) slot = ((ArmorItem)held.getItem()).getEquipmentSlot();
        else if (held.getItem() instanceof TieredItem || held.getItem() instanceof BowItem) slot = EquipmentSlotType.MAINHAND;
        if (slot == null) return false;
        ItemStack old = this.getItemStackFromSlot(slot);
        if (!old.isEmpty()) this.entityDropItem(old.copy());
        ItemStack equipped = held.copy();
        equipped.setCount(1);
        this.setItemStackToSlot(slot, equipped);
        if (!player.abilities.isCreativeMode) held.shrink(1);
        this.setDropChance(slot, 1.0F);
        return true;
    }

    @Override public ActionResultType func_230254_b_(PlayerEntity player, Hand hand) {
        ItemStack held = player.getHeldItem(hand);
        if (!this.isTamed() && held.getItem() == this.tamingItem()) {
            if (!player.abilities.isCreativeMode) held.shrink(1);
            if (!this.world.isRemote) {
                boolean success = this.rand.nextInt(3) == 0;
                if (success) { this.setTamedBy(player); this.setAttackTarget(null); this.func_233687_w_(false); }
                this.hearts(success);
            }
            return ActionResultType.func_233537_a_(this.world.isRemote);
        }
        if (this.isTamed() && this.isOwner(player)) {
            if (held.getItem() == Items.COOKED_BEEF && this.getHealth() < this.getMaxHealth()) {
                if (!player.abilities.isCreativeMode) held.shrink(1);
                this.heal(8.0F);
                return ActionResultType.func_233537_a_(this.world.isRemote);
            }
            if (!held.isEmpty() && this.equipFrom(player, held)) return ActionResultType.func_233537_a_(this.world.isRemote);
            if (held.isEmpty()) {
                this.func_233687_w_(!this.isSitting());
                this.getNavigator().clearPath();
                return ActionResultType.func_233537_a_(this.world.isRemote);
            }
        }
        return super.func_230254_b_(player, hand);
    }

    @Override public void livingTick() {
        super.livingTick();
        if (!this.world.isRemote && --this.healTimer <= 0) {
            if (this.getHealth() < this.getMaxHealth()) this.heal(1.0F);
            this.healTimer = 200;
        }
    }

    @Override public void writeAdditional(CompoundNBT nbt) {
        super.writeAdditional(nbt);
        nbt.putInt("CompanionSkin", this.getSkin());
        nbt.putInt("CompanionWetSkin", this.getWetSkin());
        nbt.putInt("CompanionVoice", this.getVoice());
        nbt.putInt("CompanionSpecial", this.getSpecialSkin());
    }
    @Override public void readAdditional(CompoundNBT nbt) {
        super.readAdditional(nbt);
        this.dataManager.set(SKIN, Math.floorMod(nbt.getInt("CompanionSkin"), this.drySkinCount()));
        this.dataManager.set(WET_SKIN, Math.floorMod(nbt.getInt("CompanionWetSkin"), this.wetSkinCount()));
        this.dataManager.set(VOICE, nbt.getInt("CompanionVoice"));
        this.dataManager.set(SPECIAL, nbt.getInt("CompanionSpecial"));
    }
    @Override public AgeableEntity func_241840_a(ServerWorld world, AgeableEntity mate) { return null; }
}
