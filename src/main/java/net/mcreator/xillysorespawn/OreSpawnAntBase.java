package net.mcreator.xillysorespawn.entity;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.PanicGoal;
import net.minecraft.entity.ai.goal.RandomWalkingGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;

import java.util.Arrays;

/** Shared 1.16.5 behaviour of OreSpawn's five tiny dimension insects. */
abstract class OreSpawnAntBase extends CreatureEntity {
    protected OreSpawnAntBase(EntityType<? extends OreSpawnAntBase> type, World world) {
        super(type, world);
        this.experienceValue = 0;
        this.stepHeight = 1.0F;
    }

    protected abstract String[] destinationDimensions();
    protected boolean needsEmptyInventory() { return false; }
    protected boolean randomlyBitesPlayers() { return false; }

    @Override protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.4D));
        this.goalSelector.addGoal(2, new RandomWalkingGoal(this, 1.0D, 9));
    }

    @Override public void livingTick() {
        super.livingTick();
        if (!this.world.isRemote && this.rand.nextInt(200) == 1) this.setAttackTarget(null);
        if (!this.world.isRemote && this.randomlyBitesPlayers()
                && this.world.getDifficulty() != Difficulty.PEACEFUL && this.ticksExisted % 20 == 0) {
            PlayerEntity player = this.world.getClosestPlayer(this, 1.5D);
            if (player != null && !player.abilities.isCreativeMode) this.attackEntityAsMob(player);
        }
    }

    @Override public boolean attackEntityAsMob(Entity target) {
        if (!this.randomlyBitesPlayers() || this.world.getDifficulty() == Difficulty.PEACEFUL
                || this.rand.nextInt(15) != 0) return false;
        return target.attackEntityFrom(DamageSource.causeMobDamage(this), 1.0F);
    }

    private boolean inventoryIsEmpty(PlayerEntity player) {
        for (ItemStack stack : player.inventory.mainInventory) if (!stack.isEmpty()) return false;
        for (ItemStack stack : player.inventory.armorInventory) if (!stack.isEmpty()) return false;
        for (ItemStack stack : player.inventory.offHandInventory) if (!stack.isEmpty()) return false;
        return true;
    }

    @Override public ActionResultType func_230254_b_(PlayerEntity player, Hand hand) {
        if (hand != Hand.MAIN_HAND) return super.func_230254_b_(player, hand);
        // OreSpawn 1.7.10 deliberately required an empty selected slot. Keep
        // that rule, but do not fail silently: otherwise a valid ant looks as
        // if it has no interaction at all.
        if (!player.getHeldItem(hand).isEmpty()) {
            if (!this.world.isRemote) {
                player.sendStatusMessage(new net.minecraft.util.text.StringTextComponent(
                    "Для телепортации нажмите на муравья пустой основной рукой."), true);
            }
            return ActionResultType.FAIL;
        }
        if (!(player instanceof ServerPlayerEntity)) return ActionResultType.SUCCESS;
        boolean alreadyInDestination = Arrays.stream(this.destinationDimensions()).anyMatch(path ->
            player.world.getDimensionKey().getLocation().equals(
                new net.minecraft.util.ResourceLocation(OreSpawnLogic.MODID, path)));
        // The original Termite checked an empty inventory only while entering
        // Dimension-Crystal. Leaving it for the Overworld never trapped the player.
        if (!alreadyInDestination && this.needsEmptyInventory() && !this.inventoryIsEmpty(player)) {
            player.sendStatusMessage(new net.minecraft.util.text.StringTextComponent(
                "Empty your inventory and take off your armor!"), true);
            return ActionResultType.FAIL;
        }
        if (OreSpawnLogic.toggleDimension((ServerPlayerEntity) player, this.destinationDimensions())) {
            return ActionResultType.SUCCESS;
        }
        player.sendStatusMessage(new net.minecraft.util.text.StringTextComponent(
            "Измерение муравья не загружено. Проверьте JSON в data/xillys_orespawn/dimension и dimension_type."),
            false);
        return ActionResultType.FAIL;
    }

    @Override public boolean onLivingFall(float distance, float multiplier) { return false; }
    @Override protected float getSoundVolume() { return 0.0F; }
}
