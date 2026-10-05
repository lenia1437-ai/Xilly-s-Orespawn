package net.mcreator.xillysorespawn.world.dimension;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ChestBlock;
import net.minecraft.tags.BlockTags;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.tileentity.MobSpawnerTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Random;

/** Structures and fruit trees used by OreSpawn's Dimension-VillageMania. */
public final class VillageDimensionStructureBuilder {
    private static final String MODID = VillageDimension.MODID;

    private VillageDimensionStructureBuilder() {}

    public static void buildGenericDungeon(ServerWorld world, Random random, BlockPos center) {
        BlockPos base = center.add(-6, 0, -6);
        hollowRoom(world, random, base, 12, 6, 12);
        placeSpawner(world, center.up(), random, "robot_1", "robot_2", "robot_3",
            "robot_4", "robot_5", "spider_robot", "scorpion");
        placeChest(world, random, center.add(0, 1, -5), "generic_dungeon");
        placeChest(world, random, center.add(0, 1, 5), "generic_dungeon");
    }

    /** Small barred stone house from WorldGenDamselInDistress. */
    public static void buildDamselInDistress(ServerWorld world, Random random, BlockPos ground) {
        BlockPos base = ground.add(-4, 1, -4);
        fill(world, base.add(0, -1, 0), base.add(8, -1, 8), Blocks.COBBLESTONE.getDefaultState());
        for (int x = 0; x <= 8; x++) for (int y = 0; y <= 4; y++) for (int z = 0; z <= 8; z++) {
            boolean wall = x == 0 || x == 8 || z == 0 || z == 8;
            BlockState state = wall ? ((x + y + z) % 5 == 0
                ? Blocks.MOSSY_COBBLESTONE : Blocks.COBBLESTONE).getDefaultState()
                : Blocks.AIR.getDefaultState();
            set(world, base.add(x, y, z), state);
        }
        // Entrance and a full-width barred cell in the rear half of the house.
        fill(world, base.add(3, 0, 0), base.add(5, 2, 0), Blocks.AIR.getDefaultState());
        for (int x = 1; x <= 7; x++) for (int y = 0; y <= 3; y++) {
            set(world, base.add(x, y, 5), Blocks.IRON_BARS.getDefaultState());
        }
        // Layered pyramid roof.
        for (int inset = 0; inset <= 4; inset++) {
            int y = 5 + inset;
            for (int x = inset; x <= 8 - inset; x++) for (int z = inset; z <= 8 - inset; z++) {
                if (x == inset || x == 8 - inset || z == inset || z == 8 - inset || inset == 4) {
                    set(world, base.add(x, y, z), Blocks.STONE_BRICKS.getDefaultState());
                }
            }
        }
        // The scorpion spawner guards the cell from the entrance side.
        placeSpawner(world, base.add(4, 1, 2), random, "scorpion");
        placeChest(world, random, base.add(4, 1, 7), "damsel_in_distress");
        spawn(world, "girlfriend", base.add(4, 1, 6), random);
    }

    /** Open gravel arena with ordinary spider spawners and one Robot Spider. */
    public static void buildSpiderHangout(ServerWorld world, Random random, BlockPos ground) {
        BlockPos base = ground.add(-10, 1, -10);
        fill(world, base.add(0, -1, 0), base.add(19, -1, 19), Blocks.STONE.getDefaultState());
        fill(world, base, base.add(19, 0, 19), Blocks.GRAVEL.getDefaultState());
        int[][] corners = {{2, 2}, {17, 2}, {2, 17}, {17, 17}};
        for (int[] c : corners) for (int y = 1; y <= 3; y++) {
            placeSpawner(world, base.add(c[0], y, c[1]), random, "minecraft:spider");
        }
        spawn(world, "spider_robot", base.add(10, 1, 10), random);
    }

    /** Red-ant nest arena with the Robot Red Ant in its centre. */
    public static void buildRedAntHangout(ServerWorld world, Random random, BlockPos ground) {
        BlockPos base = ground.add(-8, 1, -8);
        fill(world, base.add(0, -1, 0), base.add(15, -1, 15), Blocks.STONE.getDefaultState());
        fill(world, base, base.add(15, 0, 15), Blocks.GRAVEL.getDefaultState());
        BlockState nest = registeredBlock("red_ant_troll", Blocks.RED_CONCRETE);
        int[][] corners = {{1, 1}, {12, 1}, {1, 12}, {12, 12}};
        for (int[] c : corners) {
            fill(world, base.add(c[0], 1, c[1]), base.add(c[0] + 2, 2, c[1] + 2), nest);
        }
        spawn(world, "antrobot", base.add(8, 1, 8), random);
    }

    public static void buildFruitTree(ServerWorld world, Random random, BlockPos ground) {
        Block groundBlock = world.getBlockState(ground).getBlock();
        if (groundBlock != Blocks.GRASS_BLOCK && groundBlock != Blocks.DIRT
            && groundBlock != Blocks.COARSE_DIRT && groundBlock != Blocks.PODZOL) return;
        if (!world.getBlockState(ground.up()).getMaterial().isReplaceable()) return;
        // Do not put a new trunk through an existing tree or on top of its canopy.
        for (int x = -3; x <= 3; x++) for (int z = -3; z <= 3; z++) for (int y = 1; y <= 11; y++) {
            BlockState existing = world.getBlockState(ground.add(x, y, z));
            if (existing.isIn(BlockTags.LOGS) || existing.isIn(BlockTags.LEAVES)) return;
        }
        int height = 5 + random.nextInt(4);
        BlockState leaves = registeredBlock(random.nextInt(10) == 0 ? "leaves_peach" : "leaves_apple",
            Blocks.OAK_LEAVES);
        for (int y = 1; y <= height; y++) set(world, ground.up(y), Blocks.OAK_LOG.getDefaultState());
        BlockPos crown = ground.up(height);
        for (int y = -2; y <= 2; y++) {
            int radius = y == 2 ? 1 : y == -2 ? 2 : 3;
            for (int x = -radius; x <= radius; x++) for (int z = -radius; z <= radius; z++) {
                if (x * x + z * z <= radius * radius + random.nextInt(3)
                    && !(x == 0 && z == 0 && y <= 0)) set(world, crown.add(x, y, z), leaves);
            }
        }
    }

    private static void hollowRoom(ServerWorld world, Random random, BlockPos base,
                                   int sx, int sy, int sz) {
        for (int x = 0; x < sx; x++) for (int y = 0; y < sy; y++) for (int z = 0; z < sz; z++) {
            boolean edge = x == 0 || z == 0 || x == sx - 1 || z == sz - 1 || y == sy - 1;
            BlockState wall = random.nextInt(4) == 0
                ? Blocks.MOSSY_COBBLESTONE.getDefaultState() : Blocks.COBBLESTONE.getDefaultState();
            set(world, base.add(x, y, z), y == 0 ? wall : edge ? wall : Blocks.AIR.getDefaultState());
        }
    }

    private static void placeSpawner(ServerWorld world, BlockPos pos, Random random, String... ids) {
        set(world, pos, Blocks.SPAWNER.getDefaultState());
        TileEntity tile = world.getTileEntity(pos);
        if (!(tile instanceof MobSpawnerTileEntity)) return;
        EntityType<?> type = entityType(ids[random.nextInt(ids.length)]);
        if (type == null) type = EntityType.ZOMBIE;
        ((MobSpawnerTileEntity) tile).getSpawnerBaseLogic().setEntityType(type);
    }

    private static void placeChest(ServerWorld world, Random random, BlockPos pos, String loot) {
        set(world, pos, Blocks.CHEST.getDefaultState().with(ChestBlock.FACING, Direction.NORTH));
        LockableLootTileEntity.setLootTable(world, random, pos,
            new ResourceLocation(MODID, "chests/village/" + loot));
    }

    private static void spawn(ServerWorld world, String id, BlockPos pos, Random random) {
        EntityType<?> type = entityType(id);
        if (type == null) return;
        Entity entity = type.create(world);
        if (entity == null) return;
        entity.setLocationAndAngles(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D,
            random.nextFloat() * 360.0F, 0.0F);
        if (entity instanceof MobEntity) {
            ((MobEntity) entity).onInitialSpawn(world, world.getDifficultyForLocation(pos),
                SpawnReason.STRUCTURE, null, null);
        }
        world.addEntity(entity);
    }

    private static EntityType<?> entityType(String id) {
        ResourceLocation key = id.indexOf(':') >= 0 ? new ResourceLocation(id) : new ResourceLocation(MODID, id);
        return ForgeRegistries.ENTITIES.getValue(key);
    }

    private static BlockState registeredBlock(String id, Block fallback) {
        Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(MODID, id));
        return (block == null || block == Blocks.AIR ? fallback : block).getDefaultState();
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
