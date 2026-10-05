
package net.mcreator.xillysorespawn.item;

import net.minecraftforge.registries.ObjectHolder;

import net.minecraft.item.Rarity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Item;
import net.minecraft.block.BlockState;

import net.mcreator.xillysorespawn.XillysOrespawnModElements;

@XillysOrespawnModElements.ModElement.Tag
public class CreeperlauncherItem extends XillysOrespawnModElements.ModElement {
	@ObjectHolder("xillys_orespawn:creeperlauncher")
	public static final Item block = null;

	public CreeperlauncherItem(XillysOrespawnModElements instance) {
		super(instance, 72);
	}

	@Override
	public void initElements() {
		elements.items.add(() -> new ItemCustom());
	}

	public static class ItemCustom extends Item {
		public ItemCustom() {
			super(new Item.Properties().group(ItemGroup.MISC).maxStackSize(64).rarity(Rarity.COMMON));
			setRegistryName("creeperlauncher");
		}

		@Override
		public int getItemEnchantability() {
			return 0;
		}

		@Override
		public float getDestroySpeed(ItemStack par1ItemStack, BlockState par2Block) {
			return 1F;
		}
	}
}
