package net.mcreator.xillysorespawn.world.dimension;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.LadderBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.tileentity.MobSpawnerTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Random;

/** Procedural 1.16.5 versions of the seven ExtremeDimension landmarks. */
public final class ExtremeDimensionStructureBuilder {
    private static final String MODID = ExtremeDimension.MODID;

    private ExtremeDimensionStructureBuilder() {}

    public static void buildGenericDungeon(ServerWorld world, Random random, BlockPos origin) {
        BlockPos from = origin.add(-6, 0, -6);
        hollowRoom(world, from, 12, 6, 12, Blocks.COBBLESTONE.getDefaultState(),
            Blocks.MOSSY_COBBLESTONE.getDefaultState());
        placeSpawner(world, origin.add(0, 1, 0), random, "scorpion", "alien", "cryolophosaurus",
            "gamma_metroid", "kyuubi", "bee", "cloud_shark", "lurking_terror",
            "terrible_terror", "rotator", "rat", "dungeon_beast");
        placeChest(world, random, origin.add(0, 1, -5), "generic_dungeon");
    }

    /** Original 10x10, thirty-block-deep hive: no floors, many wall chests. */
    public static void buildBeeHive(ServerWorld world, Random random, BlockPos ground) {
        BlockPos top = ground.up().add(-5, 0, -5);
        for (int y = 0; y <= 30; y++) {
            for (int x = 0; x < 10; x++) for (int z = 0; z < 10; z++) {
                BlockPos p = top.add(x, -y, z);
                boolean shell = x == 0 || z == 0 || x == 9 || z == 9;
                BlockState material = y == 30 || (y & 1) == 0
                    ? Blocks.COAL_ORE.getDefaultState() : Blocks.GOLD_ORE.getDefaultState();
                set(world, p, y == 0 || (y < 30 && !shell)
                    ? Blocks.AIR.getDefaultState() : material);
            }
        }

        // The original has four Bee spawners suspended in the open shaft.
        for (int floor = 0; floor < 4; floor++) {
            int down = 2 + floor * 7;
            BlockPos center = top.add(5, -down, 5);
            placeSpawner(world, center, random, "bee");
        }

        // Four chests every other block down: 56 chests, matching fill_beehive_chests.
        for (int down = 2; down < 29; down += 2) {
            placeChest(world, random, top.add(1, -down, 5), "bee_hive");
            placeChest(world, random, top.add(8, -down, 5), "bee_hive");
            placeChest(world, random, top.add(5, -down, 1), "bee_hive");
            placeChest(world, random, top.add(5, -down, 8), "bee_hive");
        }
        fill(world, top.add(1, 1, 1), top.add(8, 4, 8), Blocks.AIR.getDefaultState());
    }

    public static void buildKyuubiDungeon(ServerWorld world, Random random, BlockPos ground) {
        int depth = Math.min(20, Math.max(12, ground.getY() - 10));
        BlockPos entry = ground.up().add(-2, 0, -2);

        // Netherrack entrance and a usable ladder down to the buried chamber.
        for (int down = 0; down <= depth; down++) {
            for (int x = 0; x < 5; x++) for (int z = 0; z < 5; z++) {
                boolean wall = x == 0 || z == 0 || x == 4 || z == 4;
                set(world, entry.add(x, -down, z), wall
                    ? Blocks.NETHERRACK.getDefaultState() : Blocks.AIR.getDefaultState());
            }
            set(world, entry.add(1, -down, 2), Blocks.LADDER.getDefaultState()
                .with(LadderBlock.FACING, Direction.EAST));
        }

        BlockPos corridor = entry.add(4, -depth, 0);
        hollowRoom(world, corridor, 13, 5, 5, Blocks.NETHERRACK.getDefaultState(),
            Blocks.OBSIDIAN.getDefaultState());
        BlockPos room = entry.add(16, -depth, -12);
        hollowRoom(world, room, 20, 18, 30, Blocks.NETHERRACK.getDefaultState(),
            Blocks.OBSIDIAN.getDefaultState());
        carveDoor(world, corridor.add(0, 1, 1), false);
        carveDoor(world, corridor.add(12, 1, 1), false);
        carveDoor(world, room.add(0, 1, 13), false);

        // Lava pools scattered through the original underground room.
        int[][] pools = new int[][]{{3,3},{6,8},{14,5},{7,20},{16,23},{3,25}};
        for (int[] p : pools) lavaPool(world, room.add(p[0], 1, p[1]));

        // Obsidian/lava Kyuubi altar with three stacked spawners and its reward.
        BlockPos altar = room.add(6, 1, 21);
        for (int x = -4; x <= 4; x++) for (int z = -4; z <= 4; z++) {
            int edge = Math.max(Math.abs(x), Math.abs(z));
            set(world, altar.add(x, 0, z), edge == 4
                ? Blocks.OBSIDIAN.getDefaultState()
                : edge == 3 ? Blocks.LAVA.getDefaultState() : Blocks.NETHERRACK.getDefaultState());
        }
        for (int i = 0; i < 3; i++) placeSpawner(world, altar.up(1 + i), random, "kyuubi");
        placeChest(world, random, altar.up(5), "kyuubi_dungeon");

        BlockPos blaze = room.add(15, 1, 7);
        fill(world, blaze.add(-3, 0, -3), blaze.add(3, 0, 3), Blocks.OBSIDIAN.getDefaultState());
        placeSpawner(world, blaze.up(), random, "minecraft:blaze");
        placeSpawner(world, blaze.add(1, 1, 0), random, "minecraft:blaze");
        placeSpawner(world, blaze.add(-1, 1, 0), random, "minecraft:blaze");
        placeChest(world, random, blaze.add(0, 1, 3), "kyuubi_dungeon");
        placeChest(world, random, blaze.add(3, 1, 0), "kyuubi_dungeon");
    }

    public static void buildShadowDungeon(ServerWorld world, Random random, BlockPos ground) {
        BlockPos base = ground.up().add(-9, 0, -9);
        // The old Shadow Dungeon is two tapering shells joined at their
        // nineteen-block-wide rim, visually forming the characteristic cone.
        for (int half = 0; half < 2; half++) {
            int sign = half == 0 ? -1 : 1;
            for (int inset = 0; inset <= 9; inset++) {
                int size = 19 - inset * 2;
                BlockPos layer = base.add(inset, sign * inset, inset);
                for (int x = 0; x < size; x++) for (int z = 0; z < size; z++) {
                    boolean edge = x == 0 || z == 0 || x == size - 1 || z == size - 1;
                    BlockState shell = (inset & 1) == 0
                        ? Blocks.OBSIDIAN.getDefaultState() : Blocks.CRYING_OBSIDIAN.getDefaultState();
                    if (edge && (x >= size / 2 - 1 && x <= size / 2 + 1
                            || z >= size / 2 - 1 && z <= size / 2 + 1)) {
                        shell = Blocks.BLACKSTONE.getDefaultState();
                    }
                    set(world, layer.add(x, 0, z), edge ? shell : Blocks.AIR.getDefaultState());
                }

                if (half == 0 && size <= 15 && size >= 9) {
                    String mob = (inset & 1) == 0 ? "pitch_black" : "ender_reaper";
                    placeSpawner(world, layer.add(1, 0, 1), random, mob);
                    placeSpawner(world, layer.add(size - 2, 0, 1), random, mob);
                    placeSpawner(world, layer.add(1, 0, size - 2), random, mob);
                    placeSpawner(world, layer.add(size - 2, 0, size - 2), random, mob);
                    if ((inset & 1) != 0) {
                        placeChest(world, random, layer.add(1, 0, size / 2), "shadow_dungeon");
                        placeChest(world, random, layer.add(size - 2, 0, size / 2), "shadow_dungeon");
                        placeChest(world, random, layer.add(size / 2, 0, 1), "shadow_dungeon");
                        placeChest(world, random, layer.add(size / 2, 0, size - 2), "shadow_dungeon");
                    }
                }
            }
        }
    }

    /** Four connected rooms, matching makeAlienWTFDungeon's rising difficulty. */
    public static void buildAlienLab(ServerWorld world, Random random, BlockPos ground) {
        int depth = Math.min(20, Math.max(12, ground.getY() - 10));
        BlockPos center = new BlockPos(ground.getX(), ground.getY() - depth, ground.getZ());

        // Surface hatch and enclosed ladder shaft down into the lab.
        for (int y = center.getY(); y <= ground.getY() + 1; y++) {
            BlockPos shaft = new BlockPos(center.getX() - 2, y, center.getZ() - 2);
            for (int x = 0; x < 5; x++) for (int z = 0; z < 5; z++) {
                boolean wall = x == 0 || z == 0 || x == 4 || z == 4;
                set(world, shaft.add(x, 0, z), wall
                    ? Blocks.IRON_BLOCK.getDefaultState() : Blocks.AIR.getDefaultState());
            }
            set(world, shaft.add(1, 0, 2), Blocks.LADDER.getDefaultState()
                .with(LadderBlock.FACING, Direction.EAST));
        }

        hollowRoom(world, center.add(-4, 0, -4), 9, 6, 9,
            Blocks.IRON_BLOCK.getDefaultState(), Blocks.QUARTZ_BLOCK.getDefaultState());

        BlockPos north = center.add(-4, 0, -21);
        BlockPos east = center.add(11, 0, -5);
        BlockPos south = center.add(-6, 0, 11);
        BlockPos west = center.add(-25, 0, -7);
        buildAlienRoom(world, random, north, 9, 1);
        buildAlienRoom(world, random, east, 11, 2);
        buildAlienRoom(world, random, south, 13, 3);
        buildAlienRoom(world, random, west, 15, 4);
        buildCorridorZ(world, center.getX(), center.getY(), center.getZ() - 17, center.getZ() - 4);
        buildCorridorX(world, center.getY(), center.getZ(), center.getX() + 4, center.getX() + 11);
        buildCorridorZ(world, center.getX(), center.getY(), center.getZ() + 4, center.getZ() + 11);
        buildCorridorX(world, center.getY(), center.getZ(), center.getX() - 11, center.getX() - 4);
    }

    public static void buildEnderKnightDungeon(ServerWorld world, Random random, BlockPos ground) {
        BlockPos base = ground.up().add(-6, 0, -14);
        hollowRoom(world, base, 13, 7, 29, Blocks.OBSIDIAN.getDefaultState(),
            Blocks.END_STONE_BRICKS.getDefaultState());
        for (int z = 2; z < 27; z += 4) {
            for (int y = 1; y <= 4; y++) {
                set(world, base.add(1, y, z), Blocks.BOOKSHELF.getDefaultState());
                set(world, base.add(11, y, z), Blocks.BOOKSHELF.getDefaultState());
            }
            if (random.nextInt(4) == 0) placeChest(world, random, base.add(2, 1, z), "ender_knight_dungeon");
        }
        placeSpawner(world, base.add(6, 2, 14), random, "ender_knight");
        placeSpawner(world, base.add(6, 3, 14), random, "ender_knight");
        carveDoor(world, base.add(5, 1, 0), true);
    }

    /** A hollow ten-block-deep bowl made from the mixed blocks of the original nest. */
    public static void buildLeonNest(ServerWorld world, Random random, BlockPos ground) {
        int radius = 10;
        Block[] shell = new Block[]{Blocks.OAK_LEAVES, Blocks.OAK_LOG, Blocks.OAK_PLANKS,
            Blocks.DIRT, Blocks.STONE, Blocks.MOSSY_COBBLESTONE};
        for (int down = 0; down <= radius; down++) {
            for (int x = -radius; x <= radius; x++) for (int z = -radius; z <= radius; z++) {
                double distance = Math.sqrt(x * x + z * z + down * down);
                BlockPos p = ground.add(x, 1 - down, z);
                if (distance <= radius - 2) set(world, p, Blocks.AIR.getDefaultState());
                else if (distance <= radius) set(world, p, shell[random.nextInt(shell.length)].getDefaultState());
            }
        }
        for (int y = 2; y <= 6; y++) fill(world, ground.add(-10, y, -10), ground.add(10, y, 10), Blocks.AIR.getDefaultState());
        placeSpawner(world, ground.down(5), random, "leon");
    }

    /** 10x10 cells, cell size 3, same dimensions and perfect-maze algorithm as BasiliskMaze. */
    public static void buildBasiliskMaze(ServerWorld world, Random random, BlockPos ground) {
        int depth = 20 + random.nextInt(10);
        int y = Math.max(5, ground.getY() - depth);
        BlockPos base = new BlockPos(ground.getX() - 15, y, ground.getZ() - 15);
        final int n = 10;
        int[][] walls = new int[n][n];
        boolean[][] seen = new boolean[n][n];
        for (int x = 0; x < n; x++) for (int z = 0; z < n; z++) walls[x][z] = 15;
        Deque<int[]> stack = new ArrayDeque<>();
        stack.push(new int[]{0, 0});
        seen[0][0] = true;
        int[] dx = new int[]{0, 1, 0, -1};
        int[] dz = new int[]{-1, 0, 1, 0};
        int[] bit = new int[]{1, 2, 4, 8};
        while (!stack.isEmpty()) {
            int[] c = stack.peek();
            int[] choices = new int[4];
            int count = 0;
            for (int d = 0; d < 4; d++) {
                int nx = c[0] + dx[d], nz = c[1] + dz[d];
                if (nx >= 0 && nz >= 0 && nx < n && nz < n && !seen[nx][nz]) choices[count++] = d;
            }
            if (count == 0) { stack.pop(); continue; }
            int d = choices[random.nextInt(count)];
            int nx = c[0] + dx[d], nz = c[1] + dz[d];
            walls[c[0]][c[1]] &= ~bit[d];
            walls[nx][nz] &= ~bit[(d + 2) & 3];
            seen[nx][nz] = true;
            stack.push(new int[]{nx, nz});
        }

        fill(world, base, base.add(30, 0, 30), Blocks.OBSIDIAN.getDefaultState());
        fill(world, base.up(4), base.add(30, 4, 30), Blocks.OBSIDIAN.getDefaultState());
        fill(world, base.add(1, 1, 1), base.add(29, 3, 29), Blocks.AIR.getDefaultState());
        for (int cx = 0; cx < n; cx++) for (int cz = 0; cz < n; cz++) {
            int ox = cx * 3, oz = cz * 3;
            if ((walls[cx][cz] & 1) != 0) wallLine(world, base.add(ox, 1, oz), 1, 0);
            if ((walls[cx][cz] & 8) != 0) wallLine(world, base.add(ox, 1, oz), 0, 1);
            if (cx == n - 1 && (walls[cx][cz] & 2) != 0) wallLine(world, base.add(30, 1, oz), 0, 1);
            if (cz == n - 1 && (walls[cx][cz] & 4) != 0) wallLine(world, base.add(ox, 1, 30), 1, 0);
        }
        // The maze opens into the original large Basilisk reward room.
        BlockPos bossRoom = base.add(30, 0, 0);
        hollowRoom(world, bossRoom, 31, 7, 31, Blocks.OBSIDIAN.getDefaultState(),
            Blocks.OBSIDIAN.getDefaultState());
        for (int x = 28; x <= 32; x++) for (int yy = 1; yy <= 3; yy++) {
            set(world, base.add(x, yy, 14), Blocks.AIR.getDefaultState());
            set(world, base.add(x, yy, 15), Blocks.AIR.getDefaultState());
            set(world, base.add(x, yy, 16), Blocks.AIR.getDefaultState());
        }

        BlockState randomTeleport = registeredBlock("teleport_block", Blocks.CRYING_OBSIDIAN);
        for (int i = 0; i < 20; i++) {
            int x = 2 + random.nextInt(27);
            int z = 2 + random.nextInt(27);
            set(world, bossRoom.add(x, 0, z), randomTeleport);
        }
        BlockPos boss = bossRoom.add(23, 1, 15);
        placeSpawner(world, boss.add(-1, 0, 0), random, "basilisk");
        placeSpawner(world, boss, random, "basilisk");
        placeSpawner(world, boss.add(1, 0, 0), random, "basilisk");
        for (int z = 4; z <= 10; z += 2) {
            placeChest(world, random, bossRoom.add(28, 1, z), "basilisk_maze");
        }

        // Vertical entrance at the first cell, exposed on the surface.
        for (int yy = y + 1; yy <= ground.getY() + 1; yy++) {
            BlockPos shaft = new BlockPos(base.getX() + 1, yy, base.getZ() + 1);
            set(world, shaft, Blocks.AIR.getDefaultState());
            set(world, shaft.east(), Blocks.LADDER.getDefaultState().with(LadderBlock.FACING, Direction.EAST));
        }
    }

    private static void wallLine(ServerWorld world, BlockPos start, int dx, int dz) {
        for (int step = 0; step <= 3; step++) for (int y = 0; y < 3; y++) {
            set(world, start.add(dx * step, y, dz * step), Blocks.OBSIDIAN.getDefaultState());
        }
    }

    private static void buildAlienRoom(ServerWorld world, Random random, BlockPos base,
                                       int size, int difficulty) {
        hollowRoom(world, base, size, 4 + difficulty, size, Blocks.IRON_BLOCK.getDefaultState(),
            Blocks.QUARTZ_BLOCK.getDefaultState());
        BlockPos center = base.add(size / 2, 1, size / 2);
        for (int i = 0; i < difficulty; i++) {
            placeSpawner(world, center.up(i), random, "alien", "gamma_metroid");
            placeSpawner(world, center.add(1, i, 1), random, "alien", "gamma_metroid");
        }
        BlockPos[] rewards = new BlockPos[]{
            base.add(size / 2, 1, 1), base.add(size / 2, 1, size - 2),
            base.add(1, 1, size / 2), base.add(size - 2, 1, size / 2)
        };
        for (int i = 0; i < difficulty; i++) {
            placeChest(world, random, rewards[i], "alien_lab");
        }
    }

    private static void buildCorridorX(ServerWorld world, int y, int z, int fromX, int toX) {
        int min = Math.min(fromX, toX), max = Math.max(fromX, toX);
        for (int x = min; x <= max; x++) for (int dy = 0; dy < 5; dy++) {
            for (int dz = -2; dz <= 2; dz++) {
                boolean shell = dy == 0 || dy == 4 || Math.abs(dz) == 2;
                set(world, new BlockPos(x, y + dy, z + dz), dy == 0
                    ? Blocks.QUARTZ_BLOCK.getDefaultState()
                    : shell ? Blocks.IRON_BLOCK.getDefaultState() : Blocks.AIR.getDefaultState());
            }
        }
    }

    private static void buildCorridorZ(ServerWorld world, int x, int y, int fromZ, int toZ) {
        int min = Math.min(fromZ, toZ), max = Math.max(fromZ, toZ);
        for (int z = min; z <= max; z++) for (int dy = 0; dy < 5; dy++) {
            for (int dx = -2; dx <= 2; dx++) {
                boolean shell = dy == 0 || dy == 4 || Math.abs(dx) == 2;
                set(world, new BlockPos(x + dx, y + dy, z), dy == 0
                    ? Blocks.QUARTZ_BLOCK.getDefaultState()
                    : shell ? Blocks.IRON_BLOCK.getDefaultState() : Blocks.AIR.getDefaultState());
            }
        }
    }

    private static void lavaPool(ServerWorld world, BlockPos center) {
        for (int x = -1; x <= 1; x++) for (int z = -1; z <= 1; z++) {
            set(world, center.add(x, 0, z), x == 0 && z == 0
                ? Blocks.LAVA.getDefaultState() : Blocks.OBSIDIAN.getDefaultState());
        }
    }

    private static BlockState registeredBlock(String id, Block fallback) {
        Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(MODID, id));
        return (block == null || block == Blocks.AIR ? fallback : block).getDefaultState();
    }

    private static void hollowRoom(ServerWorld world, BlockPos base, int sx, int sy, int sz,
                                   BlockState wall, BlockState floor) {
        for (int x = 0; x < sx; x++) for (int y = 0; y < sy; y++) for (int z = 0; z < sz; z++) {
            boolean edge = x == 0 || z == 0 || x == sx - 1 || z == sz - 1 || y == sy - 1;
            set(world, base.add(x, y, z), y == 0 ? floor : edge ? wall : Blocks.AIR.getDefaultState());
        }
    }

    private static void carveDoor(ServerWorld world, BlockPos start, boolean alongX) {
        for (int side = 0; side < 3; side++) for (int y = 0; y < 4; y++) {
            set(world, start.add(alongX ? side : 0, y, alongX ? 0 : side), Blocks.AIR.getDefaultState());
        }
    }

    private static void placeSpawner(ServerWorld world, BlockPos pos, Random random, String... entityIds) {
        set(world, pos, Blocks.SPAWNER.getDefaultState());
        TileEntity tile = world.getTileEntity(pos);
        if (!(tile instanceof MobSpawnerTileEntity)) return;
        String id = entityIds[random.nextInt(entityIds.length)];
        ResourceLocation location = id.indexOf(':') >= 0
            ? new ResourceLocation(id) : new ResourceLocation(MODID, id);
        EntityType<?> type = ForgeRegistries.ENTITIES.getValue(location);
        if (type == null) type = EntityType.ZOMBIE;
        ((MobSpawnerTileEntity) tile).getSpawnerBaseLogic().setEntityType(type);
    }

    private static void placeChest(ServerWorld world, Random random, BlockPos pos, String loot) {
        set(world, pos, Blocks.CHEST.getDefaultState().with(ChestBlock.FACING, Direction.NORTH));
        LockableLootTileEntity.setLootTable(world, random, pos,
            new ResourceLocation(MODID, "chests/" + loot));
    }

    private static void fill(ServerWorld world, BlockPos from, BlockPos to, BlockState state) {
        int minX = Math.min(from.getX(), to.getX()), maxX = Math.max(from.getX(), to.getX());
        int minY = Math.max(0, Math.min(from.getY(), to.getY()));
        int maxY = Math.min(world.getHeight() - 1, Math.max(from.getY(), to.getY()));
        int minZ = Math.min(from.getZ(), to.getZ()), maxZ = Math.max(from.getZ(), to.getZ());
        for (int x = minX; x <= maxX; x++) for (int y = minY; y <= maxY; y++)
            for (int z = minZ; z <= maxZ; z++) set(world, new BlockPos(x, y, z), state);
    }

    private static void set(ServerWorld world, BlockPos pos, BlockState state) {
        if (pos.getY() >= 0 && pos.getY() < world.getHeight()) world.setBlockState(pos, state, 2);
    }
}
