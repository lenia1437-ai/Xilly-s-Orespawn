
package net.mcreator.xillysorespawn.item;

import net.minecraftforge.registries.ObjectHolder;

import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Item;
import net.minecraft.item.IItemTier;

import net.mcreator.xillysorespawn.XillysOrespawnModElements;

@XillysOrespawnModElements.ModElement.Tag
public class AmethystShovelItem extends XillysOrespawnModElements.ModElement {
	@ObjectHolder("xillys_orespawn:amethyst_shovel")
	public static final Item block = null;

	public AmethystShovelItem(XillysOrespawnModElements instance) {
		super(instance, 40);
	}

	@Override
	public void initElements() {
		elements.items.add(() -> new ShovelItem(new IItemTier() {
			public int getMaxUses() {
				return 2000;
			}

			public float getEfficiency() {
				return 11f;
			}

			public float getAttackDamage() {
				return 8f;
			}

			public int getHarvestLevel() {
				return 11;
			}

			public int getEnchantability() {
				return 70;
			}

			public Ingredient getRepairMaterial() {
				return Ingredient.EMPTY;
			}
		}, 1, -3f, new Item.Properties().group(ItemGroup.TOOLS)) {
		}.setRegistryName("amethyst_shovel"));
	}
}
