
package net.mcreator.xillysorespawn.item;

import net.minecraftforge.registries.ObjectHolder;

import net.minecraft.item.Rarity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Item;
import net.minecraft.block.BlockState;

import net.mcreator.xillysorespawn.XillysOrespawnModElements;

@XillysOrespawnModElements.ModElement.Tag
public class BigBerthaHandleItem extends XillysOrespawnModElements.ModElement {
	@ObjectHolder("xillys_orespawn:big_bertha_handle")
	public static final Item block = null;

	public BigBerthaHandleItem(XillysOrespawnModElements instance) {
		super(instance, 49);
	}

	@Override
	public void initElements() {
		elements.items.add(() -> new ItemCustom());
	}

	public static class ItemCustom extends Item {
		public ItemCustom() {
			super(new Item.Properties().group(ItemGroup.MISC).maxStackSize(64).rarity(Rarity.COMMON));
			setRegistryName("big_bertha_handle");
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
