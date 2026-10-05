package net.mcreator.xillysorespawn.world.dimension;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Random;

/** Procedural huge, wind and sky trees used by the old Utopia generator. */
public final class UtopiaTreeBuilder {
    private UtopiaTreeBuilder() {
    }

    public static boolean buildHugeTree(ServerWorld world, Random random, BlockPos ground) {
        BlockState groundState = world.getBlockState(ground);
        if (groundState.getBlock() != Blocks.GRASS_BLOCK && groundState.getBlock() != Blocks.DIRT) return false;

        int treeType = random.nextInt(4);
        // The original Magic Apple trees were landmarks, not enlarged vanilla
        // trees: a normal trunk was roughly 17-21 blocks across before its
        // sprawling branches were added.
        int radius = 8 + random.nextInt(3);
        int shape = random.nextInt(100);
        boolean royalTree = random.nextInt(10) == 0;
        BlockState trunk = logFor(treeType);
        BlockState leaves = leavesFor(treeType);
        BlockState steps = Blocks.MOSSY_COBBLESTONE.getDefaultState();
        String boss = null;

        if (royalTree) {
            radius = 10;
            if (random.nextBoolean()) {
                trunk = Blocks.GOLD_BLOCK.getDefaultState();
                leaves = Blocks.EMERALD_BLOCK.getDefaultState();
                steps = Blocks.DIAMOND_BLOCK.getDefaultState();
                boss = "the_king";
            } else {
                trunk = Blocks.OBSIDIAN.getDefaultState();
                leaves = modBlock("ruby_block", Blocks.REDSTONE_BLOCK);
                steps = modBlock("amethyst_block", Blocks.PURPUR_BLOCK);
                boss = "the_queen";
            }
        } else if (shape > 75 && random.nextInt(20) == 0) {
            leaves = modBlock("leaves_apple", leaves.getBlock());
        }

        BlockPos base = ground.up();
        int wantedHeight = radius * 9 + random.nextInt(radius * 3 + 1);
        int height = Math.min(wantedHeight, world.getHeight() - base.getY() - 12);
        if (height < 64) return false;

        buildRoots(world, base, radius, trunk);
        if (shape > 75 || royalTree) buildSquare(world, random, base, radius, height, trunk, leaves, royalTree);
        else if (shape > 15) buildRound(world, random, base, radius, height, trunk, leaves, true, false);
        else buildRound(world, random, base, radius, height, trunk, leaves, false, false);

        buildSpiralStaircase(world, base, radius, height, steps);

        // Close the long taper with foliage before the single jewel cap.
        leafPlatform(world, random, base.up(height - 4), 5, leaves, royalTree);
        leafPlatform(world, random, base.up(height - 2), 3, leaves, royalTree);
        leafPlatform(world, random, base.up(height), 1, leaves, royalTree);
        BlockState crownGem = (random.nextBoolean() ? Blocks.DIAMOND_BLOCK : Blocks.EMERALD_BLOCK)
            .getDefaultState();
        set(world, base.up(height + 1), crownGem);
        if (boss != null) spawnBoss(world, boss, base.up(height + 4));
        return true;
    }

    public static boolean buildWindTree(ServerWorld world, Random random, BlockPos ground, int direction) {
        if (!isSoil(world.getBlockState(ground))) return false;
        int dx = direction == 0 ? 1 : direction == 1 ? -1 : 0;
        int dz = direction == 2 ? 1 : direction == 3 ? -1 : 0;
        int height = random.nextInt(8) + 40;
        int width = random.nextInt(4) + 8;
        BlockPos base = ground.up();
        for (int y = 0; y < height; y++) {
            set(world, base.up(y), Blocks.OAK_LOG.getDefaultState());
            if (y > height / 5) setIfAir(world, base.add(dx, y, dz), persistent(Blocks.OAK_LEAVES.getDefaultState()));
            if (y > height / 4 && y % 4 == 0) windBranch(world, base.up(y), height - y, dx, dz);
        }
        setIfAir(world, base.up(height), persistent(Blocks.OAK_LEAVES.getDefaultState()));
        return true;
    }

    public static boolean buildSkyTree(ServerWorld world, Random random, BlockPos ground) {
        if (!isSoil(world.getBlockState(ground))) return false;
        BlockPos base = ground.up();
        int top = 190 + random.nextInt(15);
        if (top - base.getY() < 20 || top + 2 >= world.getHeight()) return false;
        int width = 25 + random.nextInt(10);
        BlockState skyLog = modBlock("sky_tree_log", Blocks.OAK_LOG);
        for (int y = base.getY(); y <= top; y++) set(world, new BlockPos(base.getX(), y, base.getZ()), skyLog);
        BlockPos crown = new BlockPos(base.getX(), top, base.getZ());
        setIfAir(world, crown.up(), persistent(Blocks.OAK_LEAVES.getDefaultState()));
        for (Direction direction : Direction.Plane.HORIZONTAL) skyBranch(world, crown, width, direction, skyLog);
        crown = crown.down(5 + random.nextInt(4));
        for (Direction direction : Direction.Plane.HORIZONTAL) skyBranch(world, crown, width / 3, direction, skyLog);
        return true;
    }

    private static void buildSquare(ServerWorld world, Random random, BlockPos base, int radius, int height,
                                    BlockState trunk, BlockState leaves, boolean gemTree) {
        for (int y = 0; y <= height; y++) {
            int r = trunkRadius(radius, height, y);
            if (r == 0) {
                set(world, base.up(y), trunk);
                continue;
            }
            if (r <= 2) {
                for (int x = -r; x <= r; x++)
                    for (int z = -r; z <= r; z++)
                        set(world, base.add(x, y, z), trunk);
                continue;
            }
            for (int x = -r; x <= r; x++) {
                set(world, base.add(x, y, -r), trunk);
                set(world, base.add(x, y, r), trunk);
                set(world, base.add(-r, y, x), trunk);
                set(world, base.add(r, y, x), trunk);
            }
        }
        addBranchTiers(world, random, base, radius, height, trunk, leaves, gemTree);
        pointedCrown(world, random, base, radius, height, leaves, gemTree);
    }

    private static void buildRound(ServerWorld world, Random random, BlockPos base, int radius, int height,
                                   BlockState trunk, BlockState leaves, boolean circular, boolean gemTree) {
        for (int y = 0; y <= height; y++) {
            int r = trunkRadius(radius, height, y);
            if (r == 0) {
                set(world, base.up(y), trunk);
                continue;
            }
            for (int x = -r; x <= r; x++) {
                for (int z = -r; z <= r; z++) {
                    double d = Math.sqrt(x * x + z * z);
                    double shell = circular ? 1.35D : 1.8D;
                    if ((r <= 2 && d <= r + 0.25D)
                            || (r > 2 && d >= r - shell && d <= r + 0.55D)) {
                        set(world, base.add(x, y, z), trunk);
                    }
                }
            }
        }
        addBranchTiers(world, random, base, radius, height, trunk, leaves, gemTree);
        pointedCrown(world, random, base, radius, height, leaves, gemTree);
    }

    private static int trunkRadius(int baseRadius, int height, int y) {
        int taperHeight = spireStart(baseRadius, height);
        if (y >= taperHeight) return 0;
        int taper = (int) ((long) y * (baseRadius - 1) / Math.max(1, taperHeight));
        return Math.max(1, baseRadius - taper);
    }

    private static int spireStart(int baseRadius, int height) {
        // The last width=0 pass in OreSpawn's MakeBigSquareTree continued for
        // another long section before placing the emerald cap.
        return Math.max(height / 2, height - Math.max(16, baseRadius * 2));
    }

    private static void addBranchTiers(ServerWorld world, Random random, BlockPos base, int radius, int height,
                                       BlockState trunk, BlockState leaves, boolean gemTree) {
        int branchHeight = spireStart(radius, height);
        int[] levels = new int[]{branchHeight * 54 / 100, branchHeight * 72 / 100,
            branchHeight * 88 / 100};
        for (int tier = 0; tier < levels.length; tier++) {
            int y = levels[tier] + random.nextInt(5) - 2;
            int trunkAtLevel = trunkRadius(radius, height, y);
            for (Direction direction : Direction.Plane.HORIZONTAL) {
                // OreSpawn's make_branch used fixed cardinal directions. The
                // main branch is straight and its sub-branches turn exactly
                // ninety degrees while remaining on the same Y level.
                double dx = direction.getXOffset();
                double dz = direction.getZOffset();
                int length = radius * 2 + 8 + random.nextInt(7) - tier * 2;
                BlockPos start = base.add(direction.getXOffset() * (trunkAtLevel - 1), y,
                    direction.getZOffset() * (trunkAtLevel - 1));
                BlockPos end = growLimb(world, start, dx, dz, length, Math.max(2, 4 - tier), trunk);

                int forkLength = Math.max(9, length / 2);
                int forkDistance = length * 2 / 3;
                BlockPos forkOrigin = start.add((int) Math.round(dx * forkDistance), 0,
                    (int) Math.round(dz * forkDistance));
                BlockPos forkA = growLimb(world, forkOrigin, -dz, dx, forkLength, 2, trunk);
                BlockPos forkB = growLimb(world, forkOrigin, dz, -dx, forkLength, 2, trunk);
                // OreSpawn's leaves followed the branch forward in broad flat strips.
                // A round/ellipsoid clump at the tip made the port look like a vanilla tree
                // and did not cast the heavy shadow of the original giant canopy.
                leafBough(world, random, end, dx, dz, radius * 3 + 12 + random.nextInt(9),
                    7 + random.nextInt(3), leaves, gemTree);
                leafBough(world, random, forkA, -dz, dx, radius * 2 + 10 + random.nextInt(8),
                    6 + random.nextInt(3), leaves, gemTree);
                leafBough(world, random, forkB, dz, -dx, radius * 2 + 10 + random.nextInt(8),
                    6 + random.nextInt(3), leaves, gemTree);
                if (random.nextInt(5) == 0) placeBranchChest(world, random, end, dx, dz);
            }
        }
    }

    private static BlockPos growLimb(ServerWorld world, BlockPos start, double dx, double dz, int length,
                                     int startingWidth, BlockState trunk) {
        double lengthScale = Math.sqrt(dx * dx + dz * dz);
        dx /= lengthScale;
        dz /= lengthScale;
        BlockPos last = start;
        for (int i = 0; i <= length; i++) {
            double progress = i / (double) Math.max(1, length);
            int x = (int) Math.round(start.getX() + dx * i);
            // Original make_branch never changed Y: even very wide limbs were
            // walkable one-block-thick wooden platforms.
            int y = start.getY();
            int z = (int) Math.round(start.getZ() + dz * i);
            last = new BlockPos(x, y, z);
            int width = Math.max(1, startingWidth - (int) Math.floor(progress * startingWidth));
            placeBranchSection(world, last, width, Math.abs(dx) >= Math.abs(dz), trunk);
        }
        return last;
    }

    private static void placeBranchSection(ServerWorld world, BlockPos center, int radius,
                                           boolean alongX, BlockState trunk) {
        Direction.Axis axis = alongX ? Direction.Axis.X : Direction.Axis.Z;
        for (int side = -radius; side <= radius; side++) {
            // The 1.7 make_branch routine spread logs sideways on one Y level.
            // Branches are broad walkable platforms, never round pipes.
            BlockPos p = alongX ? center.add(0, 0, side) : center.add(side, 0, 0);
            set(world, p, axis(trunk, axis));
        }
    }

    private static void placeBranchChest(ServerWorld world, Random random, BlockPos branch,
                                         double outwardX, double outwardZ) {
        BlockPos chest = branch.up();
        set(world, chest, Blocks.CHEST.getDefaultState().with(ChestBlock.FACING, Direction.NORTH));
        LockableLootTileEntity.setLootTable(world, random, chest,
            new ResourceLocation("minecraft", "chests/simple_dungeon"));
        set(world, chest.up(), Blocks.AIR.getDefaultState());

        // Open a short approach through the leaves on the trunk-facing side.
        for (int i = 1; i <= 4; i++) {
            BlockPos path = chest.add((int) Math.round(-outwardX * i), 0,
                (int) Math.round(-outwardZ * i));
            set(world, path, Blocks.AIR.getDefaultState());
            set(world, path.up(), Blocks.AIR.getDefaultState());
        }
    }

    private static void buildRoots(ServerWorld world, BlockPos base, int radius, BlockState trunk) {
        // Four broad buttress roots make the base read as part of a giant tree
        // and anchor it on uneven Utopia terrain.
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            boolean alongX = direction.getAxis() == Direction.Axis.X;
            for (int i = 0; i <= radius + 7; i++) {
                int width = Math.max(1, 4 - i / 4);
                int drop = Math.min(4, i / 4);
                BlockPos center = base.offset(direction, Math.max(0, radius - 2) + i).down(drop);
                placeBranchSection(world, center, width, alongX, trunk);
            }
        }

        for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
                if (Math.max(Math.abs(x), Math.abs(z)) < radius - 1) continue;
                BlockPos p = base.add(x, -1, z);
                for (int depth = 0; depth < 12 && world.isAirBlock(p); depth++, p = p.down()) {
                    set(world, p, trunk);
                }
            }
        }
    }

    /** A continuous spiral which hugs every narrowing tier of the trunk. */
    private static void buildSpiralStaircase(ServerWorld world, BlockPos base,
                                             int baseRadius, int height, BlockState steps) {
        double phase = 0.0D;
        for (int y = 0; y <= height + 1; y++) {
            int radius = Math.max(1, trunkRadius(baseRadius, height, Math.min(y, height)) + 1);
            int perimeter = radius * 8;
            for (int advance = 0; advance < 2; advance++) {
                BlockPos offset = perimeterPoint(phase, radius);
                BlockPos stair = base.add(offset.getX(), y, offset.getZ());
                set(world, stair, steps);
                clearForPlayer(world, stair.up());

                int outsideX = Math.abs(offset.getX()) == radius ? Integer.signum(offset.getX()) : 0;
                int outsideZ = Math.abs(offset.getZ()) == radius ? Integer.signum(offset.getZ()) : 0;
                BlockPos outside = stair.add(outsideX, 0, outsideZ);
                set(world, outside, steps);
                clearForPlayer(world, outside.up());
                phase += 1.0D / perimeter;
            }
        }
    }

    private static BlockPos perimeterPoint(double phase, int radius) {
        int sideLength = radius * 2;
        int perimeter = sideLength * 4;
        double wrapped = phase - Math.floor(phase);
        int index = Math.min(perimeter - 1, (int) Math.floor(wrapped * perimeter));
        if (index < sideLength) return new BlockPos(-radius + index, 0, -radius);
        index -= sideLength;
        if (index < sideLength) return new BlockPos(radius, 0, -radius + index);
        index -= sideLength;
        if (index < sideLength) return new BlockPos(radius - index, 0, radius);
        index -= sideLength;
        return new BlockPos(-radius, 0, radius - index);
    }

    private static void clearForPlayer(ServerWorld world, BlockPos lowerAir) {
        set(world, lowerAir, Blocks.AIR.getDefaultState());
        set(world, lowerAir.up(), Blocks.AIR.getDefaultState());
    }

    /** Broad lower leaf decks narrowing to a single block directly below the jewel. */
    private static void pointedCrown(ServerWorld world, Random random, BlockPos base, int radius, int height,
                                     BlockState leaves, boolean gemTree) {
        int crownTop = spireStart(radius, height);
        int crownHeight = Math.min(20, height / 3);
        int widest = radius + 7;
        for (int rise = 0; rise <= crownHeight; rise += 4) {
            double remaining = 1.0D - rise / (double) crownHeight;
            int layerRadius = Math.max(1, (int) Math.round(widest * remaining));
            BlockPos layer = base.up(crownTop - crownHeight + rise);
            leafPlatform(world, random, layer, layerRadius, leaves, gemTree);
            if (layerRadius > 2) leafPlatform(world, random, layer.up(), layerRadius - 2, leaves, gemTree);
        }
        leafPlatform(world, random, base.up(crownTop), 1, leaves, gemTree);
    }

    private static void leafPlatform(ServerWorld world, Random random, BlockPos center, int radius,
                                     BlockState leaves, boolean gemTree) {
        for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
                if (x * x + z * z <= radius * radius) {
                    setIfAir(world, center.add(x, 0, z), leafOrRareGem(random, leaves, gemTree));
                }
            }
        }
    }

    /** Dense, mostly flat leaf branch extending away from the trunk. */
    private static void leafBough(ServerWorld world, Random random, BlockPos start, double dx, double dz,
                                  int length, int widest, BlockState leaves, boolean gemTree) {
        double scale = Math.sqrt(dx * dx + dz * dz);
        dx /= scale;
        dz /= scale;
        double sideX = -dz;
        double sideZ = dx;
        for (int forward = -8; forward <= length; forward++) {
            double progress = Math.max(0.0D, forward) / Math.max(1.0D, length);
            int halfWidth = Math.max(2, (int) Math.round(widest * (1.0D - progress * 0.65D)));
            for (int side = -halfWidth; side <= halfWidth; side++) {
                int x = (int) Math.round(start.getX() + dx * forward + sideX * side);
                int z = (int) Math.round(start.getZ() + dz * forward + sideZ * side);
                for (int y = 0; y <= (forward % 5 == 0 ? 2 : 1); y++) {
                    setIfAir(world, new BlockPos(x, start.getY() + y, z),
                        leafOrRareGem(random, leaves, gemTree));
                }
            }
        }
    }

    /** Exact royal-tree mix: 1/20 leaf positions, mostly diamond, otherwise U/T/ruby/amethyst. */
    private static BlockState leafOrRareGem(Random random, BlockState leaves, boolean gemTree) {
        if (!gemTree || random.nextInt(20) != 1) return persistent(leaves);
        if (random.nextInt(3) != 0) return Blocks.DIAMOND_BLOCK.getDefaultState();
        switch (random.nextInt(4)) {
            case 0: return modBlock("uranium_block", Blocks.EMERALD_BLOCK);
            case 1: return modBlock("titanium_block", Blocks.IRON_BLOCK);
            case 2: return modBlock("ruby_block", Blocks.REDSTONE_BLOCK);
            default: return modBlock("amethyst_block", Blocks.PURPUR_BLOCK);
        }
    }

    private static void windBranch(ServerWorld world, BlockPos start, int length, int dx, int dz) {
        BlockState leaf = persistent(Blocks.OAK_LEAVES.getDefaultState());
        Direction.Axis axis = dx == 0 ? Direction.Axis.Z : Direction.Axis.X;
        for (int i = 1; i <= length; i++) {
            BlockPos p = start.add(i * dx, 0, i * dz);
            set(world, p, axis(Blocks.OAK_LOG.getDefaultState(), axis));
            setIfAir(world, p.up(), leaf);
            if (i < length / 3) setIfAir(world, p.up(2), leaf);
            if (i > length / 3) {
                setIfAir(world, p.add(dz, 0, dx), leaf);
                setIfAir(world, p.add(-dz, 0, -dx), leaf);
            }
        }
        setIfAir(world, start.add((length + 1) * dx, 0, (length + 1) * dz), leaf);
        setIfAir(world, start.add((length + 2) * dx, 0, (length + 2) * dz), leaf);
    }

    private static void skyBranch(ServerWorld world, BlockPos start, int length, Direction direction, BlockState log) {
        BlockState leaf = persistent(Blocks.OAK_LEAVES.getDefaultState());
        for (int i = 1; i < length; i++) {
            BlockPos p = start.offset(direction, i);
            set(world, p, axis(log, direction.getAxis()));
            setIfAir(world, p.up(), leaf);
            Direction side = direction.rotateY();
            setIfAir(world, p.offset(side), leaf);
            setIfAir(world, p.offset(side.getOpposite()), leaf);
        }
        setIfAir(world, start.offset(direction, length), leaf);
    }

    private static void spawnBoss(ServerWorld world, String id, BlockPos pos) {
        EntityType<?> type = ForgeRegistries.ENTITIES.getValue(new ResourceLocation(UtopiaDimension.MODID, id));
        if (type == null) return;
        Entity boss = type.create(world);
        if (boss != null) {
            boss.setPosition(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D);
            boss.rotationYaw = world.rand.nextFloat() * 360.0F;
            world.addEntity(boss);
        }
    }

    private static boolean isSoil(BlockState state) {
        return state.getBlock() == Blocks.GRASS_BLOCK || state.getBlock() == Blocks.DIRT;
    }

    private static BlockState logFor(int type) {
        if (type == 1) return Blocks.SPRUCE_LOG.getDefaultState();
        if (type == 2) return Blocks.BIRCH_LOG.getDefaultState();
        if (type == 3) return Blocks.JUNGLE_LOG.getDefaultState();
        return Blocks.OAK_LOG.getDefaultState();
    }

    private static BlockState leavesFor(int type) {
        if (type == 1) return persistent(Blocks.SPRUCE_LEAVES.getDefaultState());
        if (type == 2) return persistent(Blocks.BIRCH_LEAVES.getDefaultState());
        if (type == 3) return persistent(Blocks.JUNGLE_LEAVES.getDefaultState());
        return persistent(Blocks.OAK_LEAVES.getDefaultState());
    }

    private static BlockState persistent(BlockState state) {
        return state.hasProperty(LeavesBlock.PERSISTENT) ? state.with(LeavesBlock.PERSISTENT, true) : state;
    }

    private static BlockState axis(BlockState state, Direction.Axis axis) {
        return state.hasProperty(RotatedPillarBlock.AXIS) ? state.with(RotatedPillarBlock.AXIS, axis) : state;
    }

    private static BlockState modBlock(String name, Block fallback) {
        Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(UtopiaDimension.MODID, name));
        return (block == null ? fallback : block).getDefaultState();
    }

    private static void setIfAir(ServerWorld world, BlockPos pos, BlockState state) {
        if (world.isAirBlock(pos)) set(world, pos, state);
    }

    private static void set(ServerWorld world, BlockPos pos, BlockState state) {
        if (pos.getY() >= 0 && pos.getY() < world.getHeight()) world.setBlockState(pos, state, 2);
    }
}
