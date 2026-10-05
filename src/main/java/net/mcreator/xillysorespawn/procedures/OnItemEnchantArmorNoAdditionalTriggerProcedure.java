package net.mcreator.xillysorespawn.procedures;

import net.minecraft.item.ItemStack;
import net.minecraft.enchantment.Enchantments;

import net.mcreator.xillysorespawn.item.UltimateArmorItem;
import net.mcreator.xillysorespawn.item.RoyalArmorItem;
import net.mcreator.xillysorespawn.item.QueenArmorItem;
import net.mcreator.xillysorespawn.item.PeacockArmorItem;
import net.mcreator.xillysorespawn.item.MothScaleArmorItem;
import net.mcreator.xillysorespawn.item.MobzillaArmorItem;
import net.mcreator.xillysorespawn.item.LavaElArmorItem;
import net.mcreator.xillysorespawn.item.LapisArmorItem;
import net.mcreator.xillysorespawn.item.ExperienceArmorItem;
import net.mcreator.xillysorespawn.XillysOrespawnMod;

import java.util.Map;

public class OnItemEnchantArmorNoAdditionalTriggerProcedure {

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("itemstack") == null) {
			if (!dependencies.containsKey("itemstack"))
				XillysOrespawnMod.LOGGER.warn("Failed to load dependency itemstack for procedure OnItemEnchantArmorNoAdditionalTrigger!");
			return;
		}
		ItemStack itemstack = (ItemStack) dependencies.get("itemstack");
		if (!(itemstack).isEnchanted()) {
			if (itemstack.getItem() == ExperienceArmorItem.helmet) {
				(itemstack).addEnchantment(Enchantments.PROTECTION, (int) 2);
				(itemstack).addEnchantment(Enchantments.FEATHER_FALLING, (int) 1);
			}
			if (itemstack.getItem() == ExperienceArmorItem.body) {
				(itemstack).addEnchantment(Enchantments.PROTECTION, (int) 2);
				(itemstack).addEnchantment(Enchantments.FEATHER_FALLING, (int) 1);
			}
			if (itemstack.getItem() == ExperienceArmorItem.legs) {
				(itemstack).addEnchantment(Enchantments.PROTECTION, (int) 2);
				(itemstack).addEnchantment(Enchantments.FEATHER_FALLING, (int) 1);
			}
			if (itemstack.getItem() == ExperienceArmorItem.boots) {
				(itemstack).addEnchantment(Enchantments.PROTECTION, (int) 2);
				(itemstack).addEnchantment(Enchantments.FEATHER_FALLING, (int) 1);
			}
			if (itemstack.getItem() == LapisArmorItem.helmet) {
				(itemstack).addEnchantment(Enchantments.RESPIRATION, (int) 1);
				(itemstack).addEnchantment(Enchantments.AQUA_AFFINITY, (int) 1);
				(itemstack).addEnchantment(Enchantments.PROTECTION, (int) 1);
				(itemstack).addEnchantment(Enchantments.PROJECTILE_PROTECTION, (int) 1);
			}
			if (itemstack.getItem() == LapisArmorItem.body) {
				(itemstack).addEnchantment(Enchantments.RESPIRATION, (int) 1);
				(itemstack).addEnchantment(Enchantments.AQUA_AFFINITY, (int) 1);
				(itemstack).addEnchantment(Enchantments.PROTECTION, (int) 1);
				(itemstack).addEnchantment(Enchantments.PROJECTILE_PROTECTION, (int) 1);
			}
			if (itemstack.getItem() == LapisArmorItem.legs) {
				(itemstack).addEnchantment(Enchantments.RESPIRATION, (int) 1);
				(itemstack).addEnchantment(Enchantments.AQUA_AFFINITY, (int) 1);
				(itemstack).addEnchantment(Enchantments.PROTECTION, (int) 1);
				(itemstack).addEnchantment(Enchantments.PROJECTILE_PROTECTION, (int) 1);
			}
			if (itemstack.getItem() == LapisArmorItem.boots) {
				(itemstack).addEnchantment(Enchantments.RESPIRATION, (int) 1);
				(itemstack).addEnchantment(Enchantments.AQUA_AFFINITY, (int) 1);
				(itemstack).addEnchantment(Enchantments.PROTECTION, (int) 1);
				(itemstack).addEnchantment(Enchantments.PROJECTILE_PROTECTION, (int) 1);
			}
			if (itemstack.getItem() == LavaElArmorItem.helmet) {
				(itemstack).addEnchantment(Enchantments.RESPIRATION, (int) 1);
				(itemstack).addEnchantment(Enchantments.AQUA_AFFINITY, (int) 2);
				(itemstack).addEnchantment(Enchantments.PROTECTION, (int) 3);
				(itemstack).addEnchantment(Enchantments.FIRE_PROTECTION, (int) 2);
				(itemstack).addEnchantment(Enchantments.BLAST_PROTECTION, (int) 10);
				(itemstack).addEnchantment(Enchantments.FEATHER_FALLING, (int) 2);
			}
			if (itemstack.getItem() == LavaElArmorItem.body) {
				(itemstack).addEnchantment(Enchantments.RESPIRATION, (int) 1);
				(itemstack).addEnchantment(Enchantments.AQUA_AFFINITY, (int) 2);
				(itemstack).addEnchantment(Enchantments.PROTECTION, (int) 3);
				(itemstack).addEnchantment(Enchantments.FIRE_PROTECTION, (int) 2);
				(itemstack).addEnchantment(Enchantments.BLAST_PROTECTION, (int) 10);
				(itemstack).addEnchantment(Enchantments.FEATHER_FALLING, (int) 2);
			}
			if (itemstack.getItem() == LavaElArmorItem.legs) {
				(itemstack).addEnchantment(Enchantments.RESPIRATION, (int) 1);
				(itemstack).addEnchantment(Enchantments.AQUA_AFFINITY, (int) 2);
				(itemstack).addEnchantment(Enchantments.PROTECTION, (int) 3);
				(itemstack).addEnchantment(Enchantments.FIRE_PROTECTION, (int) 2);
				(itemstack).addEnchantment(Enchantments.BLAST_PROTECTION, (int) 10);
				(itemstack).addEnchantment(Enchantments.FEATHER_FALLING, (int) 2);
			}
			if (itemstack.getItem() == LavaElArmorItem.boots) {
				(itemstack).addEnchantment(Enchantments.RESPIRATION, (int) 1);
				(itemstack).addEnchantment(Enchantments.AQUA_AFFINITY, (int) 2);
				(itemstack).addEnchantment(Enchantments.PROTECTION, (int) 3);
				(itemstack).addEnchantment(Enchantments.FIRE_PROTECTION, (int) 2);
				(itemstack).addEnchantment(Enchantments.BLAST_PROTECTION, (int) 10);
				(itemstack).addEnchantment(Enchantments.FEATHER_FALLING, (int) 2);
			}
			if (itemstack.getItem() == MobzillaArmorItem.helmet) {
				(itemstack).addEnchantment(Enchantments.PROTECTION, (int) 10);
				(itemstack).addEnchantment(Enchantments.FIRE_PROTECTION, (int) 10);
				(itemstack).addEnchantment(Enchantments.BLAST_PROTECTION, (int) 10);
				(itemstack).addEnchantment(Enchantments.PROJECTILE_PROTECTION, (int) 10);
				(itemstack).addEnchantment(Enchantments.UNBREAKING, (int) 5);
				(itemstack).addEnchantment(Enchantments.FEATHER_FALLING, (int) 10);
			}
			if (itemstack.getItem() == MobzillaArmorItem.body) {
				(itemstack).addEnchantment(Enchantments.PROTECTION, (int) 10);
				(itemstack).addEnchantment(Enchantments.FIRE_PROTECTION, (int) 10);
				(itemstack).addEnchantment(Enchantments.BLAST_PROTECTION, (int) 10);
				(itemstack).addEnchantment(Enchantments.PROJECTILE_PROTECTION, (int) 10);
				(itemstack).addEnchantment(Enchantments.UNBREAKING, (int) 5);
				(itemstack).addEnchantment(Enchantments.FEATHER_FALLING, (int) 10);
			}
			if (itemstack.getItem() == MobzillaArmorItem.legs) {
				(itemstack).addEnchantment(Enchantments.PROTECTION, (int) 10);
				(itemstack).addEnchantment(Enchantments.FIRE_PROTECTION, (int) 10);
				(itemstack).addEnchantment(Enchantments.BLAST_PROTECTION, (int) 10);
				(itemstack).addEnchantment(Enchantments.PROJECTILE_PROTECTION, (int) 10);
				(itemstack).addEnchantment(Enchantments.UNBREAKING, (int) 5);
				(itemstack).addEnchantment(Enchantments.FEATHER_FALLING, (int) 10);
			}
			if (itemstack.getItem() == MobzillaArmorItem.boots) {
				(itemstack).addEnchantment(Enchantments.PROTECTION, (int) 10);
				(itemstack).addEnchantment(Enchantments.FIRE_PROTECTION, (int) 10);
				(itemstack).addEnchantment(Enchantments.BLAST_PROTECTION, (int) 10);
				(itemstack).addEnchantment(Enchantments.PROJECTILE_PROTECTION, (int) 10);
				(itemstack).addEnchantment(Enchantments.UNBREAKING, (int) 5);
				(itemstack).addEnchantment(Enchantments.FEATHER_FALLING, (int) 10);
			}
			if (itemstack.getItem() == MothScaleArmorItem.helmet) {
				(itemstack).addEnchantment(Enchantments.PROTECTION, (int) 3);
				(itemstack).addEnchantment(Enchantments.FIRE_PROTECTION, (int) 3);
				(itemstack).addEnchantment(Enchantments.BLAST_PROTECTION, (int) 3);
				(itemstack).addEnchantment(Enchantments.FEATHER_FALLING, (int) 5);
			}
			if (itemstack.getItem() == MothScaleArmorItem.body) {
				(itemstack).addEnchantment(Enchantments.PROTECTION, (int) 3);
				(itemstack).addEnchantment(Enchantments.FIRE_PROTECTION, (int) 3);
				(itemstack).addEnchantment(Enchantments.BLAST_PROTECTION, (int) 3);
				(itemstack).addEnchantment(Enchantments.FEATHER_FALLING, (int) 5);
			}
			if (itemstack.getItem() == MothScaleArmorItem.legs) {
				(itemstack).addEnchantment(Enchantments.PROTECTION, (int) 3);
				(itemstack).addEnchantment(Enchantments.FIRE_PROTECTION, (int) 3);
				(itemstack).addEnchantment(Enchantments.BLAST_PROTECTION, (int) 3);
				(itemstack).addEnchantment(Enchantments.FEATHER_FALLING, (int) 5);
			}
			if (itemstack.getItem() == MothScaleArmorItem.boots) {
				(itemstack).addEnchantment(Enchantments.PROTECTION, (int) 3);
				(itemstack).addEnchantment(Enchantments.FIRE_PROTECTION, (int) 3);
				(itemstack).addEnchantment(Enchantments.BLAST_PROTECTION, (int) 3);
				(itemstack).addEnchantment(Enchantments.FEATHER_FALLING, (int) 5);
			}
			if (itemstack.getItem() == PeacockArmorItem.helmet) {
				(itemstack).addEnchantment(Enchantments.FEATHER_FALLING, (int) 10);
			}
			if (itemstack.getItem() == PeacockArmorItem.body) {
				(itemstack).addEnchantment(Enchantments.FEATHER_FALLING, (int) 10);
			}
			if (itemstack.getItem() == PeacockArmorItem.legs) {
				(itemstack).addEnchantment(Enchantments.FEATHER_FALLING, (int) 10);
			}
			if (itemstack.getItem() == PeacockArmorItem.boots) {
				(itemstack).addEnchantment(Enchantments.FEATHER_FALLING, (int) 10);
			}
			if (itemstack.getItem() == QueenArmorItem.helmet) {
				(itemstack).addEnchantment(Enchantments.PROTECTION, (int) 10);
				(itemstack).addEnchantment(Enchantments.FIRE_PROTECTION, (int) 10);
				(itemstack).addEnchantment(Enchantments.BLAST_PROTECTION, (int) 10);
				(itemstack).addEnchantment(Enchantments.PROJECTILE_PROTECTION, (int) 10);
				(itemstack).addEnchantment(Enchantments.UNBREAKING, (int) 5);
				(itemstack).addEnchantment(Enchantments.FEATHER_FALLING, (int) 5);
			}
			if (itemstack.getItem() == QueenArmorItem.body) {
				(itemstack).addEnchantment(Enchantments.PROTECTION, (int) 10);
				(itemstack).addEnchantment(Enchantments.FIRE_PROTECTION, (int) 10);
				(itemstack).addEnchantment(Enchantments.BLAST_PROTECTION, (int) 10);
				(itemstack).addEnchantment(Enchantments.PROJECTILE_PROTECTION, (int) 10);
				(itemstack).addEnchantment(Enchantments.UNBREAKING, (int) 5);
				(itemstack).addEnchantment(Enchantments.FEATHER_FALLING, (int) 5);
			}
			if (itemstack.getItem() == QueenArmorItem.legs) {
				(itemstack).addEnchantment(Enchantments.PROTECTION, (int) 10);
				(itemstack).addEnchantment(Enchantments.FIRE_PROTECTION, (int) 10);
				(itemstack).addEnchantment(Enchantments.BLAST_PROTECTION, (int) 10);
				(itemstack).addEnchantment(Enchantments.PROJECTILE_PROTECTION, (int) 10);
				(itemstack).addEnchantment(Enchantments.UNBREAKING, (int) 5);
				(itemstack).addEnchantment(Enchantments.FEATHER_FALLING, (int) 5);
			}
			if (itemstack.getItem() == QueenArmorItem.boots) {
				(itemstack).addEnchantment(Enchantments.PROTECTION, (int) 10);
				(itemstack).addEnchantment(Enchantments.FIRE_PROTECTION, (int) 10);
				(itemstack).addEnchantment(Enchantments.BLAST_PROTECTION, (int) 10);
				(itemstack).addEnchantment(Enchantments.PROJECTILE_PROTECTION, (int) 10);
				(itemstack).addEnchantment(Enchantments.UNBREAKING, (int) 5);
				(itemstack).addEnchantment(Enchantments.FEATHER_FALLING, (int) 5);
			}
			if (itemstack.getItem() == RoyalArmorItem.helmet) {
				(itemstack).addEnchantment(Enchantments.PROTECTION, (int) 10);
				(itemstack).addEnchantment(Enchantments.FIRE_PROTECTION, (int) 10);
				(itemstack).addEnchantment(Enchantments.BLAST_PROTECTION, (int) 10);
				(itemstack).addEnchantment(Enchantments.PROJECTILE_PROTECTION, (int) 10);
				(itemstack).addEnchantment(Enchantments.UNBREAKING, (int) 5);
				(itemstack).addEnchantment(Enchantments.FEATHER_FALLING, (int) 10);
			}
			if (itemstack.getItem() == RoyalArmorItem.body) {
				(itemstack).addEnchantment(Enchantments.PROTECTION, (int) 10);
				(itemstack).addEnchantment(Enchantments.FIRE_PROTECTION, (int) 10);
				(itemstack).addEnchantment(Enchantments.BLAST_PROTECTION, (int) 10);
				(itemstack).addEnchantment(Enchantments.PROJECTILE_PROTECTION, (int) 10);
				(itemstack).addEnchantment(Enchantments.UNBREAKING, (int) 5);
				(itemstack).addEnchantment(Enchantments.FEATHER_FALLING, (int) 10);
			}
			if (itemstack.getItem() == RoyalArmorItem.legs) {
				(itemstack).addEnchantment(Enchantments.PROTECTION, (int) 10);
				(itemstack).addEnchantment(Enchantments.FIRE_PROTECTION, (int) 10);
				(itemstack).addEnchantment(Enchantments.BLAST_PROTECTION, (int) 10);
				(itemstack).addEnchantment(Enchantments.PROJECTILE_PROTECTION, (int) 10);
				(itemstack).addEnchantment(Enchantments.UNBREAKING, (int) 5);
				(itemstack).addEnchantment(Enchantments.FEATHER_FALLING, (int) 10);
			}
			if (itemstack.getItem() == RoyalArmorItem.boots) {
				(itemstack).addEnchantment(Enchantments.PROTECTION, (int) 10);
				(itemstack).addEnchantment(Enchantments.FIRE_PROTECTION, (int) 10);
				(itemstack).addEnchantment(Enchantments.BLAST_PROTECTION, (int) 10);
				(itemstack).addEnchantment(Enchantments.PROJECTILE_PROTECTION, (int) 10);
				(itemstack).addEnchantment(Enchantments.UNBREAKING, (int) 5);
				(itemstack).addEnchantment(Enchantments.FEATHER_FALLING, (int) 10);
			}
			if (itemstack.getItem() == UltimateArmorItem.helmet) {
				(itemstack).addEnchantment(Enchantments.PROTECTION, (int) 3);
				(itemstack).addEnchantment(Enchantments.FIRE_PROTECTION, (int) 3);
				(itemstack).addEnchantment(Enchantments.BLAST_PROTECTION, (int) 3);
				(itemstack).addEnchantment(Enchantments.UNBREAKING, (int) 1);
				(itemstack).addEnchantment(Enchantments.PROJECTILE_PROTECTION, (int) 3);
			}
			if (itemstack.getItem() == UltimateArmorItem.body) {
				(itemstack).addEnchantment(Enchantments.PROTECTION, (int) 3);
				(itemstack).addEnchantment(Enchantments.FIRE_PROTECTION, (int) 3);
				(itemstack).addEnchantment(Enchantments.BLAST_PROTECTION, (int) 3);
				(itemstack).addEnchantment(Enchantments.UNBREAKING, (int) 1);
				(itemstack).addEnchantment(Enchantments.PROJECTILE_PROTECTION, (int) 3);
			}
			if (itemstack.getItem() == UltimateArmorItem.legs) {
				(itemstack).addEnchantment(Enchantments.PROTECTION, (int) 3);
				(itemstack).addEnchantment(Enchantments.FIRE_PROTECTION, (int) 3);
				(itemstack).addEnchantment(Enchantments.BLAST_PROTECTION, (int) 3);
				(itemstack).addEnchantment(Enchantments.UNBREAKING, (int) 1);
				(itemstack).addEnchantment(Enchantments.PROJECTILE_PROTECTION, (int) 3);
			}
			if (itemstack.getItem() == UltimateArmorItem.boots) {
				(itemstack).addEnchantment(Enchantments.PROTECTION, (int) 3);
				(itemstack).addEnchantment(Enchantments.FIRE_PROTECTION, (int) 3);
				(itemstack).addEnchantment(Enchantments.BLAST_PROTECTION, (int) 3);
				(itemstack).addEnchantment(Enchantments.UNBREAKING, (int) 1);
				(itemstack).addEnchantment(Enchantments.PROJECTILE_PROTECTION, (int) 3);
			}
		}
	}
}
