package net.mcreator.xillysorespawn.item;

import net.mcreator.xillysorespawn.XillysOrespawnModElements;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.Rarity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.registries.ObjectHolder;

/** OreSpawn 20.3 Miner's Dream: an 11x5x64 passage that preserves ores. */
@XillysOrespawnModElements.ModElement.Tag
public class MinersDreamItem extends XillysOrespawnModElements.ModElement {
    @ObjectHolder("xillys_orespawn:miners_dream") public static final Item block = null;
    public MinersDreamItem(XillysOrespawnModElements instance) { super(instance, 132); }
    @Override public void initElements() { elements.items.add(CustomItem::new); }

    public static class CustomItem extends Item {
        public CustomItem() {
            super(new Item.Properties().group(ItemGroup.MISC).maxStackSize(16).rarity(Rarity.COMMON));
            setRegistryName("miners_dream");
        }

        @Override public ActionResultType onItemUseFirst(ItemStack stack, ItemUseContext context) {
            PlayerEntity player = context.getPlayer();
            if (player == null) return ActionResultType.PASS;
            World world = context.getWorld();
            if (world.isRemote) return ActionResultType.SUCCESS;
            Direction forward = player.getHorizontalFacing();
            Direction side = forward.rotateY();
            BlockPos origin = new BlockPos(context.getPos().getX(), (int) player.getPosY(), context.getPos().getZ());
            world.playSound(null, player.getPosX(), player.getPosY(), player.getPosZ(),
                SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.PLAYERS, 1.0F, 1.5F);
            for (int y = 0; y < 5; y++) for (int depth = 0; depth < 64; depth++) {
                int solidCeiling = 0;
                for (int width = -5; width <= 5; width++) {
                    BlockPos at = origin.offset(forward, depth).offset(side, width).up(y);
                    Block current = world.getBlockState(at).getBlock();
                    if (canExcavate(current)) world.setBlockState(at, Blocks.AIR.getDefaultState(), 2);
                    if (y == 4) {
                        BlockPos roof = at.up();
                        Block above = world.getBlockState(roof).getBlock();
                        if (above != Blocks.AIR) solidCeiling++;
                        if (above == Blocks.AIR || above == Blocks.GRAVEL || above == Blocks.SAND
                                || above == Blocks.WATER || above == Blocks.LAVA)
                            world.setBlockState(roof, Blocks.COBBLESTONE.getDefaultState(), 2);
                    }
                }
                if (y == 4 && solidCeiling == 0) {
                    for (int width = -5; width <= 5; width++)
                        world.setBlockState(origin.offset(forward, depth).offset(side, width).up(5),
                            Blocks.AIR.getDefaultState(), 2);
                }
            }
            for (int depth = 0; depth < 64; depth += 5) {
                BlockPos at = origin.offset(forward, depth);
                if (world.isAirBlock(at) && world.getBlockState(at.down()).isSolid())
                    world.setBlockState(at, Blocks.TORCH.getDefaultState(), 2);
            }
            if (!player.abilities.isCreativeMode) stack.shrink(1);
            return ActionResultType.SUCCESS;
        }

        private static boolean canExcavate(Block block) {
            return block == Blocks.STONE || block == Blocks.DIRT || block == Blocks.GRAVEL
                || block == Blocks.WATER || block == Blocks.LAVA || block == Blocks.NETHERRACK
                || block == Blocks.END_STONE
                || (block.getRegistryName() != null
                    && "xillys_orespawn:crystal_stone".equals(block.getRegistryName().toString()));
        }
    }
}
