
package net.mcreator.xillysorespawn.item;

import net.minecraftforge.registries.ObjectHolder;

import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Item;
import net.minecraft.item.IItemTier;

import net.mcreator.xillysorespawn.XillysOrespawnModElements;

@XillysOrespawnModElements.ModElement.Tag
public class PoisonSwordItem extends XillysOrespawnModElements.ModElement {
	@ObjectHolder("xillys_orespawn:poison_sword")
	public static final Item block = null;

	public PoisonSwordItem(XillysOrespawnModElements instance) {
		super(instance, 148);
	}

	@Override
	public void initElements() {
		elements.items.add(() -> new SwordItem(new IItemTier() {
			public int getMaxUses() {
				return 1300;
			}

			public float getEfficiency() {
				return 10f;
			}

			public float getAttackDamage() {
				return 8f;
			}

			public int getHarvestLevel() {
				return 3;
			}

			public int getEnchantability() {
				return 75;
			}

			public Ingredient getRepairMaterial() {
				return Ingredient.EMPTY;
			}
		}, 3, -2.4f, new Item.Properties().group(ItemGroup.TOOLS)) {
		}.setRegistryName("poison_sword"));
	}
}
