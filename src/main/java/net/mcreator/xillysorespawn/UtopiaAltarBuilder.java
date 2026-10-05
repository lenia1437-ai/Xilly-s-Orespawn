package net.mcreator.xillysorespawn.world.dimension;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ChestBlock;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.registries.ForgeRegistries;

/** Direct 1.16.5 adaptation of GenericDungeon's King and Queen altars. */
public final class UtopiaAltarBuilder {
    private static final int WIDTH = 51;
    private static final int HEIGHT = 48;
    private static final int[] PORTRAIT = new int[]{
        -1, -1, 24, 3, -1, 24, 5, -1, 17, 12, -1, 16, 15, -1,
        15, 14, -1, 15, 6, 3, 5, -1, 14, 6, 4, 3, -1, 14, 5, -1,
        14, 5, -1, 12, 9, -1, 11, 11, -1, 8, 17, -1, 5, 23, -1,
        3, 27, -1, 2, 29, -1, 1, 31, -1, 0, 33, -1, 13, 6, -1,
        12, 9, -1, 11, 3, 1, 2, 1, 4, -1, 10, 3, 2, 2, 3, 2, -1,
        10, 2, 4, 2, 3, 2, -1, 9, 2, 5, 2, 4, 6, -1, 9, 2, 5, 2,
        6, 4, -1, 8, 2, 6, 1, -1, 8, 2, 5, 2, -1, 8, 2, 5, 2, -1,
        8, 2, 5, 2, -1, 15, 2, -1, -1, -1
    };

    private UtopiaAltarBuilder() {
    }

    public static boolean hasSpace(ServerWorld world, BlockPos origin) {
        int checkY = origin.getY() + 8;
        if (origin.getY() < 1 || origin.getY() + HEIGHT + 10 >= world.getHeight()) {
            return false;
        }
        for (int x = -5; x < 55; x++) {
            for (int z = -5; z < 55; z++) {
                if (!world.isAirBlock(new BlockPos(origin.getX() + x, checkY, origin.getZ() + z))) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void build(ServerWorld world, BlockPos origin, boolean king) {
        BlockState roof = king ? Blocks.QUARTZ_BLOCK.getDefaultState() : Blocks.OBSIDIAN.getDefaultState();

        // The old structure explicitly cleared a 61x61x59 volume.
        for (int y = 0; y <= HEIGHT + 10; y++) {
            for (int x = -5; x < WIDTH + 5; x++) {
                for (int z = -5; z < WIDTH + 5; z++) {
                    BlockPos pos = origin.add(x, y, z);
                    if (!world.isAirBlock(pos)) {
                        set(world, pos, Blocks.AIR.getDefaultState());
                    }
                }
            }
        }

        // Grass floor and up to nine blocks of support under holes/water.
        for (int x = 0; x < WIDTH; x++) {
            for (int z = 0; z < WIDTH; z++) {
                BlockPos floor = origin.add(x, 0, z);
                set(world, floor, Blocks.GRASS_BLOCK.getDefaultState());
                for (int down = 1; down < 10; down++) {
                    BlockPos support = floor.down(down);
                    BlockState current = world.getBlockState(support);
                    if (!(current.isAir(world, support) || current.getMaterial().isReplaceable()
                        || current.getBlock() == Blocks.WATER)) {
                        continue;
                    }
                    set(world, support, Blocks.DIRT.getDefaultState());
                }
            }
        }

        buildColumn(world, origin.add(1, 1, 1), king);
        buildColumn(world, origin.add(WIDTH - 8, 1, WIDTH - 8), king);
        buildColumn(world, origin.add(1, 1, WIDTH - 8), king);
        buildColumn(world, origin.add(WIDTH - 8, 1, 1), king);

        fill(world, origin.add(0, HEIGHT - 1, 0), origin.add(WIDTH - 1, HEIGHT - 1, WIDTH - 1), roof);
        fill(world, origin.add(-1, HEIGHT, -1), origin.add(WIDTH, HEIGHT, WIDTH), roof);
        buildPortrait(world, origin.add(4, 10, 9), king);
        buildCenter(world, origin.add(WIDTH / 2, 0, WIDTH / 2), king);
    }

    private static void buildColumn(ServerWorld world, BlockPos start, boolean king) {
        BlockState main = king ? Blocks.QUARTZ_PILLAR.getDefaultState() : Blocks.OBSIDIAN.getDefaultState();
        BlockState stripe = king ? Blocks.GOLD_BLOCK.getDefaultState() : Blocks.REDSTONE_BLOCK.getDefaultState();
        BlockState jewel = king ? Blocks.EMERALD_BLOCK.getDefaultState() : modBlock("amethyst_block", Blocks.PURPUR_BLOCK);
        int height = 44;

        fill(world, start, start.add(6, 0, 6), main);
        fill(world, start.add(0, height + 1, 0), start.add(6, height + 1, 6), main);
        BlockPos inner = start.add(1, 1, 1);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < 5; x++) {
                for (int z = 0; z < 5; z++) {
                    BlockState state = Blocks.AIR.getDefaultState();
                    if (x == 0 || z == 0 || x == 4 || z == 4) {
                        state = main;
                        int phase = y & 3;
                        if ((phase == 0 && (x == 2 || z == 2))
                            || ((phase == 1 || phase == 3) && (x == 1 || z == 1 || x == 3 || z == 3))) {
                            state = stripe;
                        } else if (phase == 2) {
                            if (x == 2 || z == 2) state = jewel;
                            else if (x == 1 || z == 1 || x == 3 || z == 3) state = stripe;
                        }
                    }
                    set(world, inner.add(x, y, z), state);
                }
            }
        }
    }

    private static void buildPortrait(ServerWorld world, BlockPos start, boolean king) {
        BlockState background = Blocks.STONE.getDefaultState();
        BlockState color = king ? Blocks.QUARTZ_BLOCK.getDefaultState() : modBlock("ruby_block", Blocks.REDSTONE_BLOCK);
        int cursorZ = 0;
        int cursorY = 0;
        BlockState current = background;
        for (int value : PORTRAIT) {
            if (value < 0) {
                while (cursorZ < 33) set(world, start.add(0, cursorY, cursorZ++), current);
                cursorY++;
                cursorZ = 0;
                current = background;
            } else {
                for (int n = 0; n < value; n++) set(world, start.add(0, cursorY, cursorZ++), current);
                current = current == background ? color : background;
            }
        }

        BlockState frame = king ? Blocks.GOLD_BLOCK.getDefaultState() : Blocks.DIAMOND_BLOCK.getDefaultState();
        for (int i = 0; i < 33; i++) {
            set(world, start.add(0, -1, i), frame);
            set(world, start.add(0, 33, i), frame);
        }
        for (int y = -1; y <= 33; y++) {
            set(world, start.add(0, y, -1), frame);
            set(world, start.add(0, y, 33), frame);
        }
        BlockState torchBase = Blocks.DIAMOND_BLOCK.getDefaultState();
        BlockState torch = modBlock("crystal_torch", Blocks.TORCH);
        BlockPos[] corners = new BlockPos[]{start.add(0, -2, -2), start.add(0, 34, 34),
            start.add(0, -2, 34), start.add(0, 34, -2)};
        for (BlockPos corner : corners) {
            set(world, corner, torchBase);
            set(world, corner.up(), torch);
        }
    }

    private static void buildCenter(ServerWorld world, BlockPos center, boolean king) {
        BlockState main = king ? Blocks.QUARTZ_BLOCK.getDefaultState() : Blocks.OBSIDIAN.getDefaultState();
        BlockState jewel = king ? Blocks.LAPIS_BLOCK.getDefaultState() : modBlock("amethyst_block", Blocks.PURPUR_BLOCK);
        BlockState torch = modBlock("crystal_torch", Blocks.TORCH);

        fillCross(world, center, 0, 10, 10, 6, 20, main);
        fillCross(world, center, 1, 8, 8, 4, 18, main);
        setFourCrossCorners(world, center.up(), 4, 18, jewel);
        fillCross(world, center, 2, 7, 7, 3, 17, main);
        setFourCorners(world, center.add(0, 3, 0), 7, torch);
        fillCross(world, center, 3, 6, 6, 2, 16, main);
        fill(world, center.add(-2, 4, -2), center.add(2, 4, 2), main);
        setFourCorners(world, center.add(0, 5, 0), 2, torch);

        BlockPos chestPos = center.add(0, 4, 0);
        set(world, chestPos, Blocks.CHEST.getDefaultState().with(ChestBlock.FACING, Direction.NORTH));
        TileEntity tile = world.getTileEntity(chestPos);
        if (tile instanceof ChestTileEntity) {
            Item egg = ForgeRegistries.ITEMS.getValue(new ResourceLocation(UtopiaDimension.MODID,
                king ? "the_king_spawn_egg" : "the_queen_spawn_egg"));
            if (egg != null) ((ChestTileEntity) tile).setInventorySlotContents(13, new ItemStack(egg));
        }
    }

    private static void fillCross(ServerWorld world, BlockPos center, int y,
                                  int squareX, int squareZ, int armX, int armZ, BlockState state) {
        fill(world, center.add(-squareX, y, -squareZ), center.add(squareX, y, squareZ), state);
        fill(world, center.add(-armX, y, -armZ), center.add(armX, y, armZ), state);
        fill(world, center.add(-armZ, y, -armX), center.add(armZ, y, armX), state);
    }

    private static void setFourCrossCorners(ServerWorld world, BlockPos center, int shortArm, int longArm, BlockState state) {
        set(world, center.add(shortArm, 0, longArm), state);
        set(world, center.add(shortArm, 0, -longArm), state);
        set(world, center.add(-shortArm, 0, longArm), state);
        set(world, center.add(-shortArm, 0, -longArm), state);
        set(world, center.add(longArm, 0, shortArm), state);
        set(world, center.add(longArm, 0, -shortArm), state);
        set(world, center.add(-longArm, 0, shortArm), state);
        set(world, center.add(-longArm, 0, -shortArm), state);
    }

    private static void setFourCorners(ServerWorld world, BlockPos center, int radius, BlockState state) {
        set(world, center.add(radius, 0, radius), state);
        set(world, center.add(radius, 0, -radius), state);
        set(world, center.add(-radius, 0, radius), state);
        set(world, center.add(-radius, 0, -radius), state);
    }

    private static void fill(ServerWorld world, BlockPos from, BlockPos to, BlockState state) {
        int minX = Math.min(from.getX(), to.getX());
        int minY = Math.min(from.getY(), to.getY());
        int minZ = Math.min(from.getZ(), to.getZ());
        int maxX = Math.max(from.getX(), to.getX());
        int maxY = Math.max(from.getY(), to.getY());
        int maxZ = Math.max(from.getZ(), to.getZ());
        for (int x = minX; x <= maxX; x++)
            for (int y = minY; y <= maxY; y++)
                for (int z = minZ; z <= maxZ; z++)
                    set(world, new BlockPos(x, y, z), state);
    }

    private static BlockState modBlock(String name, Block fallback) {
        Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(UtopiaDimension.MODID, name));
        return (block == null ? fallback : block).getDefaultState();
    }

    private static void set(ServerWorld world, BlockPos pos, BlockState state) {
        if (pos.getY() >= 0 && pos.getY() < world.getHeight()) world.setBlockState(pos, state, 2);
    }
}
