package net.mcreator.xillysorespawn.block;

import net.mcreator.xillysorespawn.XillysOrespawnModElements;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
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
public class DuplicatorTreeLogBlock extends XillysOrespawnModElements.ModElement {
    @ObjectHolder("xillys_orespawn:duplicator_tree_log") public static final Block block = null;
    public DuplicatorTreeLogBlock(XillysOrespawnModElements instance) { super(instance, 278); }
    @Override public void initElements() {
        elements.blocks.add(CustomBlock::new);
        elements.items.add(() -> new BlockItem(block, new Item.Properties().group(ItemGroup.BUILDING_BLOCKS))
            .setRegistryName(block.getRegistryName()));
    }

    public static class CustomBlock extends Block {
        public CustomBlock() {
            super(Block.Properties.create(Material.WOOD).sound(SoundType.WOOD)
                .hardnessAndResistance(1.0F, 10.0F).tickRandomly());
            setRegistryName("duplicator_tree_log");
        }

        @Override public void onBlockAdded(BlockState state, net.minecraft.world.World world,
                                            BlockPos pos, BlockState oldState, boolean isMoving) {
            super.onBlockAdded(state, world, pos, oldState, isMoving);
            Block below = world.getBlockState(pos.down()).getBlock();
            if (!world.isRemote && state.getBlock() != oldState.getBlock()
                    && (below == Blocks.GRASS_BLOCK || below == Blocks.DIRT || below == Blocks.FARMLAND))
                world.getPendingBlockTicks().scheduleTick(pos, this, 1);
        }

        @Override public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
            growAndDuplicate(world, pos, random);
        }

        @Override public void tick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
            growAndDuplicate(world, pos, random);
            world.getPendingBlockTicks().scheduleTick(pos, this, 20);
        }

        private void growAndDuplicate(ServerWorld world, BlockPos pos, Random random) {
            Block below = world.getBlockState(pos.down()).getBlock();
            if (below != Blocks.GRASS_BLOCK && below != Blocks.DIRT && below != Blocks.FARMLAND) return;
            for (int y = 1; y <= 3; y++) {
                BlockPos at = pos.up(y);
                if (world.getBlockState(at).getBlock() != this) {
                    if (world.isAirBlock(at)) world.setBlockState(at, getDefaultState(), 2);
                    return;
                }
            }
            BlockPos top = pos.up(4);
            if (world.getBlockState(top).getBlock() != LeavesAppleBlock.block) {
                if (world.isAirBlock(top)) world.setBlockState(top, LeavesAppleBlock.block.getDefaultState(), 2);
                return;
            }
            for (int x = -1; x <= 1; x++) for (int z = -1; z <= 1; z++) {
                if (x == 0 && z == 0) continue;
                BlockPos at = pos.add(x, 3, z);
                if (world.isAirBlock(at)) {
                    world.setBlockState(at, LeavesAppleBlock.block.getDefaultState(), 2);
                    return;
                }
            }
            for (int tries = 0; tries < 20; tries++) {
                BlockPos source = pos.add(random.nextInt(5) - 2, 0, random.nextInt(5) - 2);
                BlockState copy = world.getBlockState(source);
                if (copy.isAir() || copy.getBlock() == this || copy.getBlock() == Blocks.BEDROCK) continue;
                for (int targetTry = 0; targetTry < 20; targetTry++) {
                    BlockPos target = pos.add(random.nextInt(5) - 2, 0, random.nextInt(5) - 2);
                    if (world.isAirBlock(target)) {
                        world.setBlockState(target, copy, 2);
                        return;
                    }
                }
            }
        }
    }
}
