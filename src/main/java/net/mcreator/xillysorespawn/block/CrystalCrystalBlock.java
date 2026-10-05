
package net.mcreator.xillysorespawn.block;

import net.minecraftforge.registries.ObjectHolder;

import net.minecraft.world.IBlockReader;
import net.minecraft.util.math.BlockPos;
import net.minecraft.loot.LootContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Item;
import net.minecraft.item.BlockItem;
import net.minecraft.block.material.Material;
import net.minecraft.block.SoundType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Block;

import net.mcreator.xillysorespawn.XillysOrespawnModElements;

import java.util.List;
import java.util.Collections;

@XillysOrespawnModElements.ModElement.Tag
public class CrystalCrystalBlock extends XillysOrespawnModElements.ModElement {
	@ObjectHolder("xillys_orespawn:crystal_crystal")
	public static final Block block = null;

	public CrystalCrystalBlock(XillysOrespawnModElements instance) {
		super(instance, 256);
	}

	@Override
	public void initElements() {
		elements.blocks.add(() -> new CustomBlock());
		elements.items
				.add(() -> new BlockItem(block, new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)).setRegistryName(block.getRegistryName()));
	}

	    @Override
    @net.minecraftforge.api.distmarker.OnlyIn(net.minecraftforge.api.distmarker.Dist.CLIENT)
    public void clientLoad(net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent event) {
        net.minecraft.client.renderer.RenderTypeLookup.setRenderLayer(block,
            net.minecraft.client.renderer.RenderType.getTranslucent());
    }
public static class CustomBlock extends Block {
		public CustomBlock() {
			super(Block.Properties.create(Material.GLASS).sound(SoundType.STONE).hardnessAndResistance(1f, 10f).setLightLevel(s -> 0).notSolid().setOpaque((bs, br, bp) -> false));
			setRegistryName("crystal_crystal");
		}

		@Override
		public int getOpacity(BlockState state, IBlockReader worldIn, BlockPos pos) {
			return 0;
		}

		@Override
		public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
			List<ItemStack> dropsOriginal = super.getDrops(state, builder);
			if (!dropsOriginal.isEmpty())
				return dropsOriginal;
			return Collections.singletonList(new ItemStack(this, 1));
		}
	}
}
