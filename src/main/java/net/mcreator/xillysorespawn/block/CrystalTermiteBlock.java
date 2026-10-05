package net.mcreator.xillysorespawn.block;

import net.mcreator.xillysorespawn.XillysOrespawnModElements;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.registries.ObjectHolder;

import java.util.Random;

@XillysOrespawnModElements.ModElement.Tag
public class CrystalTermiteBlock extends XillysOrespawnModElements.ModElement {
    @ObjectHolder("xillys_orespawn:crystal_termite") public static final Block block = null;
    public CrystalTermiteBlock(XillysOrespawnModElements instance) { super(instance, 268); }
    @Override public void initElements() {
        elements.blocks.add(CustomBlock::new);
        elements.items.add(() -> new BlockItem(block, new Item.Properties().group(ItemGroup.BUILDING_BLOCKS))
            .setRegistryName(block.getRegistryName()));
    }
    public static class CustomBlock extends Block {
        public CustomBlock() {
            super(Block.Properties.create(Material.ROCK).sound(SoundType.STONE)
                .hardnessAndResistance(1.0F, 10.0F).tickRandomly());
            setRegistryName("crystal_termite");
        }
        @Override public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
            AntNestBlock.spawnFromNest(world, pos, random, 4);
        }
    }
}
