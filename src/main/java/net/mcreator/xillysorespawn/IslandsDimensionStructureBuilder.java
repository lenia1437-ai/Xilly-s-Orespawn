package net.mcreator.xillysorespawn.world.dimension;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.LadderBlock;
import net.mcreator.xillysorespawn.block.AntNestBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.tileentity.MobSpawnerTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Random;

/** Procedural ports of OreSpawn's Dimension-Islands landmarks. */
public final class IslandsDimensionStructureBuilder {
    private static final String MODID = IslandsDimension.MODID;

    private IslandsDimensionStructureBuilder() {}

    public static void buildScragglyTree(ServerWorld world, Random random, BlockPos ground) {
        BlockState log = registeredBlock("sky_tree_log", Blocks.OAK_LOG);
        BlockState leaves = registeredBlock("scary_leaves", Blocks.DARK_OAK_LEAVES);
        int height = 4 + random.nextInt(5);
        BlockPos trunk = ground.up();
        int bendX = 0, bendZ = 0;
        for (int y = 0; y < height; y++) {
            if (y > 1 && random.nextInt(3) == 0) {
                bendX += random.nextInt(3) - 1;
                bendZ += random.nextInt(3) - 1;
            }
            set(world, trunk.add(bendX, y, bendZ), log);
        }
        BlockPos crown = trunk.add(bendX, height - 1, bendZ);
        for (int x = -2; x <= 2; x++) for (int y = -1; y <= 2; y++) for (int z = -2; z <= 2; z++) {
            if (x * x + z * z + y * y <= 6 && random.nextInt(8) != 0) set(world, crown.add(x, y, z), leaves);
        }
        int branches = 2 + random.nextInt(3);
        for (int i = 0; i < branches; i++) {
            int dx = random.nextBoolean() ? 1 : -1;
            int dz = random.nextBoolean() ? 1 : -1;
            BlockPos branch = trunk.add(bendX, height - 2 - random.nextInt(2), bendZ);
            for (int step = 1; step <= 2; step++) set(world, branch.add(dx * step, 0, dz * step), log);
            BlockPos tip = branch.add(dx * 2, 0, dz * 2);
            for (int x = -1; x <= 1; x++) for (int y = -1; y <= 1; y++) for (int z = -1; z <= 1; z++) {
                if (Math.abs(x) + Math.abs(y) + Math.abs(z) < 3) set(world, tip.add(x, y, z), leaves);
            }
        }
    }

    /** Static 1.16.5 counterpart of the old moving Island/IslandToo block entities. */
    public static void buildFloatingIsland(ServerWorld world, Random random, BlockPos center) {
        int radiusX = 4 + random.nextInt(5);
        int radiusZ = 4 + random.nextInt(5);
        int depth = 3 + random.nextInt(4);
        BlockState ruby = registeredBlock("ruby_block", Blocks.REDSTONE_BLOCK);
        BlockState amethyst = registeredBlock("amethyst_block", Blocks.PURPUR_BLOCK);
        BlockState titanium = registeredBlock("titanium_block", Blocks.IRON_BLOCK);
        BlockState uranium = registeredBlock("uranium_block", Blocks.EMERALD_BLOCK);
        for (int x = -radiusX; x <= radiusX; x++) for (int z = -radiusZ; z <= radiusZ; z++) {
            double edge = x * x / (double) (radiusX * radiusX) + z * z / (double) (radiusZ * radiusZ);
            if (edge > 1.0D) continue;
            int columnDepth = Math.max(1, (int) Math.round(depth * (1.0D - edge)) + 1);
            set(world, center.add(x, 0, z), Blocks.GRASS_BLOCK.getDefaultState());
            for (int down = 1; down <= columnDepth; down++) {
                BlockState state = down == 1 ? Blocks.DIRT.getDefaultState() : Blocks.STONE.getDefaultState();
                int roll = random.nextInt(180);
                if (down > 1 && roll == 0) state = titanium;
                else if (down > 1 && roll == 1) state = uranium;
                else if (down > 1 && roll < 4) state = ruby;
                else if (down > 1 && roll < 7) state = amethyst;
                set(world, center.add(x, -down, z), state);
            }
        }
        if (random.nextBoolean()) buildScragglyTree(world, random, center);
        if (random.nextInt(4) == 0) {
            BlockState flower = registeredBlock(random.nextBoolean() ? "flower_pink" : "flower_blue", Blocks.DANDELION);
            set(world, center.add(2, 1, 0), flower);
        }
    }

    public static void buildAntNests(ServerWorld world, Random random, BlockPos ground) {
        BlockState nest = AntNestBlock.nest(3);
        for (int i = 0; i < 3; i++) {
            int x = random.nextInt(11) - 5, z = random.nextInt(11) - 5;
            BlockPos p = ground.add(x, 1, z);
            set(world, p.down(), nest);
        }
    }

    public static void buildRockField(ServerWorld world, Random random, BlockPos ground) {
        int count = 2 + random.nextInt(5);
        for (int i = 0; i < count; i++) {
            BlockPos c = ground.add(random.nextInt(13) - 6, 1, random.nextInt(13) - 6);
            int radius = 1 + random.nextInt(2);
            BlockState rock = random.nextBoolean() ? Blocks.STONE.getDefaultState() : Blocks.COBBLESTONE.getDefaultState();
            for (int x = -radius; x <= radius; x++) for (int y = -1; y <= radius; y++) for (int z = -radius; z <= radius; z++) {
                if (x * x + y * y + z * z <= radius * radius + 1) set(world, c.add(x, y, z), rock);
            }
        }
    }

    public static void buildMajor(ServerWorld world, Random random, BlockPos ground, int selector) {
        // Castles (0..2 and 7) use the incremental IslandsLegacyCastles plan.
        if (selector < 3 || selector == 7) return;
        if (selector < 7) buildGenericDungeon(world, random, ground);
        else switch (selector) {

            case 8: buildIncaPyramid(world, random, ground); break;
            case 9: buildRobotLab(world, random, ground); break;
            case 10: buildMiniDungeon(world, random, ground); break;
            case 11: buildRubyDungeon(world, random, ground); break;
            case 12: buildCephadromeAltar(world, random, ground); break;
            case 13: buildGreenhouse(world, random, ground); break;
            case 14: buildNightmareRookery(world, random, ground); break;
            case 15: buildStinkyHouse(world, random, ground); break;
            case 16: buildWhiteHouse(world, random, ground); break;
            case 17: buildPumpkin(world, random, ground); break;
            default: buildRainbow(world, random, ground); break;
        }
    }

    public static void buildGenericDungeon(ServerWorld world, Random random, BlockPos ground) {
        BlockPos base = ground.up().add(-6, 0, -6);
        hollowRoom(world, base, 12, 6, 12, Blocks.COBBLESTONE.getDefaultState(), Blocks.MOSSY_COBBLESTONE.getDefaultState());
        placeSpawner(world, base.add(6, 1, 6), random, "scorpion", "alien", "cryolophosaurus",
            "gamma_metroid", "kyuubi", "bee", "cloud_shark", "lurking_terror",
            "terrible_terror", "rotator", "rat", "dungeon_beast");
        placeChest(world, random, base.add(2, 1, 2), "generic_dungeon");
        carveDoor(world, base.add(5, 1, 0), true);
    }

    public static void buildCloudSharkDungeon(ServerWorld world, Random random, BlockPos center) {
        BlockPos base = center.add(-3, -1, -3);
        for (int x = 0; x < 7; x++) for (int y = 0; y < 4; y++) for (int z = 0; z < 7; z++) {
            double d = Math.pow(x - 3, 2) + Math.pow((y - 1.5D) * 1.6D, 2) + Math.pow(z - 3, 2);
            if (d <= 12) set(world, base.add(x, y, z), y == 0 ? Blocks.PACKED_ICE.getDefaultState() : Blocks.SNOW_BLOCK.getDefaultState());
        }
        BlockPos c = center;
        placeSpawner(world, c.add(2, 0, 0), random, "cloud_shark");
        placeSpawner(world, c.add(-2, 0, 0), random, "cloud_shark");
        placeSpawner(world, c.add(0, 0, 2), random, "cloud_shark");
        placeSpawner(world, c.add(0, 0, -2), random, "cloud_shark");
        placeChest(world, random, c.up(), "cloud_shark");
    }





    private static void buildIncaPyramid(ServerWorld world, Random random, BlockPos ground) {
        BlockPos base = ground.up().add(-20, 0, -15);
        for (int level = 0; level < 10; level++) {
            int sx = 41 - level * 4, sz = 31 - level * 3;
            BlockPos layer = base.add(level * 2, level, level + level / 2);
            for (int x = 0; x < sx; x++) for (int z = 0; z < sz; z++) {
                if (x == 0 || z == 0 || x == sx - 1 || z == sz - 1) set(world, layer.add(x, 0, z),
                    random.nextInt(5) == 0 ? Blocks.CHISELED_SANDSTONE.getDefaultState() : Blocks.CUT_SANDSTONE.getDefaultState());
            }
        }
        hollowRoom(world, base.add(12, 1, 8), 17, 7, 15, Blocks.SANDSTONE.getDefaultState(), Blocks.GOLD_BLOCK.getDefaultState());
        placeSpawner(world, base.add(20, 2, 15), random, "scorpion", "emperor_scorpion");
        placeChest(world, random, base.add(20, 2, 18), "inca_pyramid");
    }

    private static void buildRobotLab(ServerWorld world, Random random, BlockPos ground) {
        BlockPos base = ground.up().add(-5, 0, -10);
        hollowRoom(world, base, 11, 6, 21, Blocks.IRON_BLOCK.getDefaultState(), Blocks.QUARTZ_BLOCK.getDefaultState());
        for (int z = 4; z <= 16; z += 4) {
            fill(world, base.add(1, 1, z), base.add(9, 4, z), Blocks.IRON_BARS.getDefaultState());
            set(world, base.add(5, 1, z), Blocks.AIR.getDefaultState());
            set(world, base.add(5, 2, z), Blocks.AIR.getDefaultState());
        }
        placeSpawner(world, base.add(3, 1, 7), random, "robot_1", "robot_2", "spider_robot", "giant_robot");
        placeSpawner(world, base.add(7, 1, 15), random, "robot_3", "robot_4", "robot_5", "giant_robot");
        placeChest(world, random, base.add(5, 1, 19), "robot_lab");
        carveDoor(world, base.add(4, 1, 0), true);
    }

    private static void buildMiniDungeon(ServerWorld world, Random random, BlockPos ground) {
        BlockPos base = ground.up().add(-5, 0, -5);
        hollowRoom(world, base, 10, 6, 10, Blocks.MOSSY_COBBLESTONE.getDefaultState(), Blocks.COBBLESTONE.getDefaultState());
        for (int[] p : new int[][]{{2,1,2},{7,1,2},{2,1,7},{7,1,7}}) placeSpawner(world, base.add(p[0],p[1],p[2]), random, "butterfly");
        placeChest(world, random, base.add(5, 1, 5), "mini_dungeon");
    }

    private static void buildRubyDungeon(ServerWorld world, Random random, BlockPos ground) {
        BlockState rubyOre = registeredBlock("ruby_ore", Blocks.REDSTONE_ORE);
        BlockPos base = ground.up().add(-5, 0, -5);
        hollowRoom(world, base, 10, 5, 10, Blocks.MOSSY_COBBLESTONE.getDefaultState(), rubyOre);
        // Ruby Bird is absent from this port; Brutalfly is the closest flying guardian available.
        placeSpawner(world, base.add(5, 1, 5), random, "brutalfly", "peacock");
        placeChest(world, random, base.add(2, 1, 2), "ruby_dungeon");
        placeChest(world, random, base.add(7, 1, 7), "ruby_dungeon");
    }

    private static void buildCephadromeAltar(ServerWorld world, Random random, BlockPos ground) {
        BlockPos c = ground.up();
        for (int r = 7; r >= 1; r -= 2) {
            int y = (7 - r) / 2;
            for (int x = -r; x <= r; x++) for (int z = -r; z <= r; z++) {
                if (Math.max(Math.abs(x), Math.abs(z)) == r) set(world, c.add(x, y, z), Blocks.SANDSTONE.getDefaultState());
            }
        }
        placeSpawner(world, c.add(0, 3, 0), random, "cephadrome");
        placeChest(world, random, c.add(0, 1, 0), "cephadrome_altar");
    }

    private static void buildGreenhouse(ServerWorld world, Random random, BlockPos ground) {
        BlockPos base = ground.up().add(-7, 0, -5);
        hollowRoom(world, base, 15, 7, 11, Blocks.GLASS.getDefaultState(), Blocks.GRASS_BLOCK.getDefaultState());
        for (int x = 2; x < 13; x += 2) for (int z = 2; z < 9; z += 3) {
            set(world, base.add(x, 1, z), registeredBlock(random.nextBoolean() ? "flower_pink" : "flower_blue", Blocks.POPPY));
        }
        placeSpawner(world, base.add(7, 1, 5), random, "leaf_monster");
        placeChest(world, random, base.add(2, 1, 8), "greenhouse");
    }

    private static void buildNightmareRookery(ServerWorld world, Random random, BlockPos ground) {
        BlockPos c = ground.up();
        for (int y = 0; y < 13; y++) {
            int r = Math.max(2, 7 - y / 2);
            for (int x = -r; x <= r; x++) for (int z = -r; z <= r; z++) {
                if (Math.max(Math.abs(x), Math.abs(z)) == r) set(world, c.add(x, y, z),
                    random.nextBoolean() ? Blocks.BLACKSTONE.getDefaultState() : Blocks.OBSIDIAN.getDefaultState());
            }
        }
        placeSpawner(world, c.add(0, 3, 0), random, "pitch_black", "dragon");
        placeChest(world, random, c.add(0, 1, 0), "nightmare_rookery");
    }

    private static void buildStinkyHouse(ServerWorld world, Random random, BlockPos ground) {
        BlockPos yard = ground.up().add(-12, 0, -8);
        fill(world, yard, yard.add(23, 0, 15), Blocks.OAK_FENCE.getDefaultState());
        fill(world, yard.add(1, 0, 1), yard.add(22, 0, 14), Blocks.GRASS_BLOCK.getDefaultState());
        BlockPos base = yard.add(6, 1, 3);
        hollowRoom(world, base, 12, 9, 9, Blocks.OAK_PLANKS.getDefaultState(), Blocks.OAK_PLANKS.getDefaultState());
        for (int i = 0; i < 14; i++) set(world, base.add(random.nextInt(12), 2 + random.nextInt(6), random.nextInt(9)), Blocks.AIR.getDefaultState());
        carveDoor(world, base.add(5, 1, 0), true);
        placeSpawner(world, base.add(3, 1, 4), random, "stink_bug", "stinky");
        placeSpawner(world, base.add(8, 1, 4), random, "stink_bug", "stinky");
        placeChest(world, random, base.add(6, 1, 7), "stinky_house");
    }

    private static void buildWhiteHouse(ServerWorld world, Random random, BlockPos ground) {
        BlockPos base = ground.up().add(-12, 0, -12);
        hollowRoom(world, base, 25, 9, 25, Blocks.QUARTZ_BLOCK.getDefaultState(), Blocks.SMOOTH_QUARTZ.getDefaultState());
        for (int x = 3; x < 22; x += 6) for (int z = 3; z < 22; z += 6) {
            fill(world, base.add(x, 1, z), base.add(x + 2, 1, z + 2), Blocks.WATER.getDefaultState());
        }
        fill(world, base.add(11, 1, 0), base.add(13, 1, 24), Blocks.POLISHED_DIORITE.getDefaultState());
        carveDoor(world, base.add(11, 1, 0), true);
        placeChest(world, random, base.add(12, 1, 20), "white_house");
    }

    private static void buildPumpkin(ServerWorld world, Random random, BlockPos ground) {
        BlockPos c = ground.up(7);
        for (int x = -7; x <= 7; x++) for (int y = -6; y <= 6; y++) for (int z = -7; z <= 7; z++) {
            double d = x * x / 49.0D + y * y / 36.0D + z * z / 49.0D;
            if (d >= 0.72D && d <= 1.10D) set(world, c.add(x, y, z), Blocks.ORANGE_CONCRETE.getDefaultState());
        }
        fill(world, c.add(-1, 7, -1), c.add(1, 10, 1), Blocks.OAK_LOG.getDefaultState());
        // Face and walkable entrance.
        fill(world, c.add(-4, 1, -7), c.add(-2, 3, -6), Blocks.AIR.getDefaultState());
        fill(world, c.add(2, 1, -7), c.add(4, 3, -6), Blocks.AIR.getDefaultState());
        fill(world, c.add(-1, -3, -7), c.add(1, 0, -5), Blocks.AIR.getDefaultState());
        placeChest(world, random, c.add(0, -5, 2), "giant_pumpkin");
    }

    private static void buildRainbow(ServerWorld world, Random random, BlockPos ground) {
        BlockState[] colors = new BlockState[]{Blocks.RED_WOOL.getDefaultState(), Blocks.ORANGE_WOOL.getDefaultState(),
            Blocks.YELLOW_WOOL.getDefaultState(), Blocks.LIME_WOOL.getDefaultState(), Blocks.LIGHT_BLUE_WOOL.getDefaultState(),
            Blocks.BLUE_WOOL.getDefaultState(), Blocks.PURPLE_WOOL.getDefaultState()};
        BlockPos c = ground.up(7);
        for (int band = 0; band < colors.length; band++) {
            int radius = 16 - band;
            for (int x = -radius; x <= radius; x++) {
                double curve = Math.sqrt(Math.max(0, radius * radius - x * x));
                int y = (int) Math.round(curve);
                set(world, c.add(x, y, band - 3), colors[band]);
            }
        }
        placeSpawner(world, c.add(-16, 1, 0), random, "cloud_shark");
        placeSpawner(world, c.add(16, 1, 0), random, "cloud_shark");
        placeChest(world, random, c.add(0, 16, 0), "rainbow");
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
        ResourceLocation location = id.indexOf(':') >= 0 ? new ResourceLocation(id) : new ResourceLocation(MODID, id);
        EntityType<?> type = ForgeRegistries.ENTITIES.getValue(location);
        if (type == null) type = EntityType.ZOMBIE;
        ((MobSpawnerTileEntity) tile).getSpawnerBaseLogic().setEntityType(type);
    }

    private static void placeChest(ServerWorld world, Random random, BlockPos pos, String loot) {
        set(world, pos, Blocks.CHEST.getDefaultState().with(ChestBlock.FACING, Direction.NORTH));
        LockableLootTileEntity.setLootTable(world, random, pos, new ResourceLocation(MODID, "chests/islands/" + loot));
    }

    private static void fill(ServerWorld world, BlockPos from, BlockPos to, BlockState state) {
        int minX = Math.min(from.getX(), to.getX()), maxX = Math.max(from.getX(), to.getX());
        int minY = Math.max(0, Math.min(from.getY(), to.getY()));
        int maxY = Math.min(world.getHeight() - 1, Math.max(from.getY(), to.getY()));
        int minZ = Math.min(from.getZ(), to.getZ()), maxZ = Math.max(from.getZ(), to.getZ());
        for (int x = minX; x <= maxX; x++) for (int y = minY; y <= maxY; y++) for (int z = minZ; z <= maxZ; z++) {
            set(world, new BlockPos(x, y, z), state);
        }
    }

    private static void set(ServerWorld world, BlockPos pos, BlockState state) {
        if (pos.getY() >= 0 && pos.getY() < world.getHeight()) world.setBlockState(pos, state, 2);
    }
}
