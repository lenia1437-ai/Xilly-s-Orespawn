package net.mcreator.xillysorespawn.block;

import net.mcreator.xillysorespawn.XillysOrespawnModElements;
import net.mcreator.xillysorespawn.world.dimension.ExtremeDimensionStructureBuilder;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.loot.LootContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.registries.ObjectHolder;

import java.util.Collections;
import java.util.List;
import java.util.Random;

/** Delayed OreSpawn random-dungeon seed block. */
@XillysOrespawnModElements.ModElement.Tag
public class DungeonSpawnerBlock extends XillysOrespawnModElements.ModElement {
    @ObjectHolder("xillys_orespawn:dungeon_spawner")
    public static final Block block = null;

    public DungeonSpawnerBlock(XillysOrespawnModElements instance) {
        super(instance, 277);
    }

    @Override
    public void initElements() {
        elements.blocks.add(CustomBlock::new);
        elements.items.add(() -> new BlockItem(block,
            new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)).setRegistryName(block.getRegistryName()));
    }

    public static class CustomBlock extends Block {
        public CustomBlock() {
            super(AbstractBlock.Properties.create(Material.PLANTS).doesNotBlockMovement()
                .zeroHardnessAndResistance().sound(SoundType.PLANT).notSolid());
            setRegistryName("dungeon_spawner");
        }

        @Override public int getOpacity(BlockState state, IBlockReader world, BlockPos pos) { return 0; }

        @Override
        public List<net.minecraft.item.ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
            List<net.minecraft.item.ItemStack> drops = super.getDrops(state, builder);
            return drops.isEmpty() ? Collections.singletonList(new net.minecraft.item.ItemStack(this)) : drops;
        }

        @Override
        public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean moving) {
            super.onBlockAdded(state, world, pos, oldState, moving);
            if (!world.isRemote && oldState.getBlock() != this)
                world.getPendingBlockTicks().scheduleTick(pos, this, 80 + world.rand.nextInt(81));
        }

        @Override
        public void tick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
            if (world.getBlockState(pos).getBlock() != this) return;
            world.removeBlock(pos, false);
            BlockPos ground = pos.down();
            switch (random.nextInt(7)) {
                case 0:
                    ExtremeDimensionStructureBuilder.buildGenericDungeon(world, random, pos.add(0, -8, 0));
                    break;
                case 1: ExtremeDimensionStructureBuilder.buildBasiliskMaze(world, random, ground); break;
                case 2: ExtremeDimensionStructureBuilder.buildBeeHive(world, random, ground); break;
                case 3: ExtremeDimensionStructureBuilder.buildShadowDungeon(world, random, ground); break;
                case 4: ExtremeDimensionStructureBuilder.buildAlienLab(world, random, ground); break;
                case 5: ExtremeDimensionStructureBuilder.buildEnderKnightDungeon(world, random, ground); break;
                default: ExtremeDimensionStructureBuilder.buildLeonNest(world, random, ground); break;
            }
        }
    }
}
