
package net.mcreator.xillysorespawn.item;

import net.minecraftforge.registries.ObjectHolder;

import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Item;
import net.minecraft.item.IItemTier;

import net.mcreator.xillysorespawn.XillysOrespawnModElements;

@XillysOrespawnModElements.ModElement.Tag
public class RubySwordItem extends XillysOrespawnModElements.ModElement {
	@ObjectHolder("xillys_orespawn:ruby_sword")
	public static final Item block = null;

	public RubySwordItem(XillysOrespawnModElements instance) {
		super(instance, 186);
	}

	@Override
	public void initElements() {
		elements.items.add(() -> new SwordItem(new IItemTier() {
			public int getMaxUses() {
				return 1500;
			}

			public float getEfficiency() {
				return 11f;
			}

			public float getAttackDamage() {
				return 16f;
			}

			public int getHarvestLevel() {
				return 3;
			}

			public int getEnchantability() {
				return 85;
			}

			public Ingredient getRepairMaterial() {
				return Ingredient.EMPTY;
			}
		}, 3, -2.4f, new Item.Properties().group(ItemGroup.TOOLS)) {
		}.setRegistryName("ruby_sword"));
	}
}
