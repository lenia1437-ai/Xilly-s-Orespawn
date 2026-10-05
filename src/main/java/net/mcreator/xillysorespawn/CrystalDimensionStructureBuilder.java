package net.mcreator.xillysorespawn.world.dimension;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.tileentity.MobSpawnerTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Random;

/** Original Crystal-Dimension decorations and recognisable ports of its legacy structures. */
final class CrystalDimensionStructureBuilder {
    private static final String MODID = CrystalDimension.MODID;
    private CrystalDimensionStructureBuilder() {}

    static void buildMaze(ServerWorld world, Random random, int chunkX, int chunkZ) {
        CrystalMazeBuilder.build(world, random, chunkX, chunkZ, state("crystal_stone", Blocks.STONE));
        int ox = chunkX << 4, oz = chunkZ << 4;
        for (int i = 0; i < 3; i++) {
            BlockPos pos = new BlockPos(ox + 1 + random.nextInt(14), 25, oz + 1 + random.nextInt(14));
            if (!world.isAirBlock(pos)) continue;
            if (random.nextInt(3) == 0) chest(world, random, pos, "maze");
            else spawner(world, pos, random.nextBoolean() ? "dungeon_beast" : "rat");
            break;
        }
    }

    static void buildSurfaceDecorations(ServerWorld world, Random random, int chunkX, int chunkZ) {
        int ox = chunkX << 4, oz = chunkZ << 4;
        if (random.nextInt(30) == 1) crystalSpikePatch(world, random,
            new BlockPos(ox + 3 + random.nextInt(10), 30 + random.nextInt(5), oz + 3 + random.nextInt(10)),
            state("crystal_crystal", Blocks.PURPLE_STAINED_GLASS), 1 + random.nextInt(10), true);
        if (random.nextInt(30) == 1) crystalSpikePatch(world, random,
            new BlockPos(ox + 3 + random.nextInt(10), 5 + random.nextInt(5), oz + 3 + random.nextInt(10)),
            state("tigerseye", Blocks.GOLD_ORE), 1 + random.nextInt(5), false);

        if (random.nextInt(5) == 0) {
            int kind = random.nextInt(5);
            int count = random.nextInt(8) * (kind == 0 ? 1 : 2);
            for (int i = 0; i < count; i++) {
                BlockPos surface = surface(world, ox + 4 + random.nextInt(8), oz + 4 + random.nextInt(8));
                if (surface.getY() > 40) {
                    if (kind == 0) tallCrystalTree(world, random, surface);
                    else scragglyCrystalTree(world, random, surface);
                }
            }
        }

        for (int i = 0, count = 3 + random.nextInt(8); i < count; i++)
            oreVein(world, random, ox + 2 + random.nextInt(12), random.nextInt(128),
                oz + 2 + random.nextInt(12), state("crystal_coal", Blocks.COAL_ORE), 6);

        if (random.nextInt(3) == 0) {
            String[] flowers = {"crystalflower_red", "crystalflower_green", "crystalflower_blue", "crystalflower_yellow"};
            String flower = flowers[random.nextInt(flowers.length)];
            int count = 1 + random.nextInt(13);
            for (int i = 0; i < count; i++) placePlant(world, ox + random.nextInt(16), oz + random.nextInt(16), flower);
        }
        if (random.nextInt(10) == 0) for (int i = 0; i < 5; i++)
            placePlant(world, ox + random.nextInt(16), oz + random.nextInt(16), "rice_0");
        if (random.nextInt(20) == 0) for (int i = 0; i < 5; i++)
            placePlant(world, ox + random.nextInt(16), oz + random.nextInt(16), "quinoa_0");
        if (random.nextInt(40) == 0) for (int i = 0; i < 3; i++)
            buildTermiteNest(world, random, ox + random.nextInt(16), oz + random.nextInt(16));
        if (random.nextInt(4) == 1) buildRockField(world, random,
            surface(world, ox + 4 + random.nextInt(8), oz + 4 + random.nextInt(8)));
    }

    static void buildFairyTree(ServerWorld world, Random random, BlockPos ground, boolean castle) {
        BlockState log = state("crystal_tree_log", Blocks.OAK_LOG);
        BlockState leaves = state("crystal_tree_leaves_3", Blocks.OAK_LEAVES);
        int height = castle ? 20 + random.nextInt(8) : 13 + random.nextInt(7);
        for (int y = 0; y < height; y++) {
            int radius = y < 5 ? 1 : 0;
            for (int x = -radius; x <= radius; x++) for (int z = -radius; z <= radius; z++)
                set(world, ground.add(x, y, z), log);
            if (y > 5 && y % 4 == 1) {
                int dx = random.nextBoolean() ? 1 : -1, dz = random.nextBoolean() ? 1 : -1;
                branch(world, random, ground.add(0, y, 0), 5 + random.nextInt(4), dx, dz, log, leaves);
            }
        }
        BlockPos crown = ground.up(height);
        leafBall(world, crown, castle ? 6 : 5, leaves);
        if (castle) {
            BlockState planks = state("crystal_planks", Blocks.OAK_PLANKS);
            int y = height - 3;
            for (int x = -5; x <= 5; x++) for (int z = -5; z <= 5; z++)
                if (x * x + z * z <= 29) set(world, ground.add(x, y, z), planks);
            chest(world, random, ground.add(2, y + 1, 0), "fairy_tree");
            spawner(world, ground.add(-2, y + 1, 0), "fairy");
        }
    }

    /** 0 station, 1 urchin shrine, 2 haunted house, 3 round rotator, 4 battle tower. */
    static void buildMajor(ServerWorld world, Random random, BlockPos base, int kind) {
        if (kind == 0) buildRotatorStation(world, random, base, false);
        else if (kind == 1) buildUrchinShrine(world, random, base);
        else if (kind == 2) buildHauntedHouse(world, random, base);
        else if (kind == 3) buildRotatorStation(world, random, base, true);
        else buildBattleTower(world, random, base);
    }

    static void buildIrukandjiSpawner(ServerWorld world, BlockPos pos) {
        if (world.getBlockState(pos.down()).getBlock() == Blocks.WATER) spawner(world, pos, "irukandji");
    }

    private static void buildRotatorStation(ServerWorld world, Random random, BlockPos base, boolean round) {
        BlockState stone = state("crystal_stone", Blocks.QUARTZ_BLOCK);
        BlockState glass = state("crystal_crystal", Blocks.GLASS);
        int outer = round ? 9 : 7;
        for (int x = -outer; x <= outer; x++) for (int z = -outer; z <= outer; z++) {
            int d = x * x + z * z;
            if (d <= outer * outer && d >= (outer - 2) * (outer - 2))
                for (int y = 0; y <= 5; y++) set(world, base.add(x, y, z), y == 2 ? glass : stone);
            if (d < (outer - 2) * (outer - 2)) set(world, base.add(x, 0, z), stone);
        }
        for (int y = 1; y < 4; y++) set(world, base.add(0, y, 0), glass);
        spawner(world, base.up(), "rotator");
        chest(world, random, base.add(2, 1, 0), "rotator_station");
    }

    private static void buildUrchinShrine(ServerWorld world, Random random, BlockPos base) {
        BlockState stone = state("crystal_stone", Blocks.QUARTZ_BLOCK);
        fill(world, base.add(-5, 0, -5), base.add(5, 0, 5), stone);
        for (int i = -5; i <= 5; i += 10) for (int j = -5; j <= 5; j += 10)
            fill(world, base.add(i, 1, j), base.add(i, 7, j), state("crystal_crystal", Blocks.GLASS));
        for (int x = -4; x <= 4; x++) for (int z = -4; z <= 4; z++)
            if (Math.abs(x) + Math.abs(z) > 5) set(world, base.add(x, 6, z), stone);
        spawner(world, base.up(), "urchin");
        chest(world, random, base.add(0, 1, 3), "urchin_shrine");
    }

    private static void buildHauntedHouse(ServerWorld world, Random random, BlockPos base) {
        BlockState wall = state("crystal_planks", Blocks.DARK_OAK_PLANKS);
        BlockState trim = state("crystal_tree_log", Blocks.DARK_OAK_LOG);
        fill(world, base.add(-7, 0, -5), base.add(7, 0, 5), state("crystal_stone", Blocks.COBBLESTONE));
        for (int y = 1; y <= 7; y++) for (int x = -7; x <= 7; x++) for (int z = -5; z <= 5; z++) {
            boolean shell = x == -7 || x == 7 || z == -5 || z == 5;
            if (shell) set(world, base.add(x, y, z), (x % 7 == 0 || z % 5 == 0) ? trim : wall);
        }
        fill(world, base.add(-8, 8, -6), base.add(8, 8, 6), state("crystal_tree_leaves_2", Blocks.DARK_OAK_PLANKS));
        for (int y = 1; y <= 3; y++) set(world, base.add(0, y, -5), Blocks.AIR.getDefaultState());
        spawner(world, base.add(-3, 1, 0), "dungeon_beast");
        spawner(world, base.add(3, 1, 0), "rat");
        chest(world, random, base.add(0, 1, 3), "haunted_house");
    }

    private static void buildBattleTower(ServerWorld world, Random random, BlockPos base) {
        BlockState wall = state("crystal_stone", Blocks.QUARTZ_BLOCK);
        BlockState window = state("crystal_crystal", Blocks.GLASS);
        for (int floor = 0; floor < 6; floor++) {
            int y0 = floor * 6;
            fill(world, base.add(-6, y0, -6), base.add(6, y0, 6), wall);
            for (int y = y0 + 1; y <= y0 + 5; y++) for (int x = -6; x <= 6; x++) for (int z = -6; z <= 6; z++) {
                if (Math.abs(x) == 6 || Math.abs(z) == 6)
                    set(world, base.add(x, y, z), y == y0 + 3 && (x == 0 || z == 0) ? window : wall);
            }
            for (int y = y0 + 1; y <= y0 + 5; y++) set(world, base.add(0, y, 0), Blocks.LADDER.getDefaultState());
            spawner(world, base.add(floor % 2 == 0 ? -3 : 3, y0 + 1, 0),
                floor < 3 ? "rat" : floor < 5 ? "dungeon_beast" : "vortex");
            chest(world, random, base.add(0, y0 + 1, 3), floor == 5 ? "battle_tower_top" : "battle_tower");
        }
        fill(world, base.add(-7, 36, -7), base.add(7, 36, 7), wall);
    }

    private static void buildTermiteNest(ServerWorld world, Random random, int x, int z) {
        BlockPos top = surface(world, x, z);
        if (top.getY() <= 50 || world.getBlockState(top.down()).getBlock() != block("crystal_grass", Blocks.GRASS_BLOCK)) return;
        set(world, top.down(), state("crystal_termite", Blocks.COARSE_DIRT));
        EntityType<?> termite = ForgeRegistries.ENTITIES.getValue(new ResourceLocation(MODID, "termite"));
        if (termite == null) return;
        int count = 2 + random.nextInt(6);
        for (int i = 0; i < count; i++) {
            Entity entity = termite.create(world);
            if (entity == null) continue;
            entity.setPosition(x + 0.3D + random.nextDouble() * 0.4D, top.getY() + 0.01D,
                z + 0.3D + random.nextDouble() * 0.4D);
            world.addEntity(entity);
        }
    }

    private static void crystalSpikePatch(ServerWorld world, Random random, BlockPos origin,
                                          BlockState state, int count, boolean wide) {
        for (int i = 0; i < count; i++) {
            double x = origin.getX(), y = origin.getY(), z = origin.getZ();
            double dx = random.nextFloat() - random.nextFloat();
            double dz = random.nextFloat() - random.nextFloat();
            double dy = 0.5D + random.nextFloat() / 2.0D;
            int radius = wide ? random.nextInt(2) : 0;
            int length = 1 + radius * 3 + random.nextInt(wide ? 15 : 6);
            for (int step = 0; step <= length; step++) {
                for (int ox = 0; ox <= radius; ox++) for (int oz = 0; oz <= radius; oz++)
                    set(world, new BlockPos(x + ox, y, z + oz), state);
                x += dx; y += dy; z += dz;
            }
        }
    }

    private static void oreVein(ServerWorld world, Random random, int x, int y, int z, BlockState ore, int size) {
        Block crystalStone = block("crystal_stone", Blocks.STONE);
        BlockPos pos = new BlockPos(x, y, z);
        for (int i = 0; i < size; i++) {
            if (world.getBlockState(pos).getBlock() == crystalStone) set(world, pos, ore);
            pos = pos.add(random.nextInt(3) - 1, random.nextInt(3) - 1, random.nextInt(3) - 1);
        }
    }

    private static void tallCrystalTree(ServerWorld world, Random random, BlockPos root) {
        BlockState log = state("crystal_tree_log", Blocks.OAK_LOG);
        BlockState leaves = state("crystal_tree_leaves", Blocks.OAK_LEAVES);
        int trunk = 10 + random.nextInt(12), top = trunk + random.nextInt(18);
        for (int y = 0; y < top; y++) {
            set(world, root.up(y), log);
            if (y >= trunk && y % 4 == 0) for (int x = -1; x <= 1; x++) for (int z = -1; z <= 1; z++)
                if (random.nextBoolean() && world.isAirBlock(root.add(x, y, z))) set(world, root.add(x, y, z), leaves);
        }
        leafBall(world, root.up(top), 3, leaves);
    }

    private static void scragglyCrystalTree(ServerWorld world, Random random, BlockPos root) {
        BlockState log = state("crystal_tree_log", Blocks.OAK_LOG);
        BlockState leaves = state("crystal_tree_leaves_2", Blocks.OAK_LEAVES);
        int straight = 1 + random.nextInt(2), length = straight + random.nextInt(8);
        BlockPos pos = root;
        for (int i = 0; i < straight; i++) { set(world, pos, log); pos = pos.up(); }
        for (int i = straight; i < length; i++) {
            pos = pos.add(random.nextInt(2) - random.nextInt(2), random.nextInt(4) > 0 ? 1 : 0,
                random.nextInt(2) - random.nextInt(2));
            set(world, pos, log);
            for (int x = -1; x <= 1; x++) for (int z = -1; z <= 1; z++)
                if (random.nextBoolean() && world.isAirBlock(pos.add(x, 0, z))) set(world, pos.add(x, 0, z), leaves);
            if (random.nextInt(4) == 1) branch(world, random, pos, 1 + random.nextInt(Math.max(1, length - i)),
                random.nextInt(2) - random.nextInt(2), random.nextInt(2) - random.nextInt(2), log, leaves);
        }
    }

    private static void branch(ServerWorld world, Random random, BlockPos start, int length, int bx, int bz,
                               BlockState log, BlockState leaves) {
        BlockPos pos = start;
        for (int i = 0; i < length; i++) {
            int dx = Math.max(-1, Math.min(1, bx + random.nextInt(2) - random.nextInt(2)));
            int dz = Math.max(-1, Math.min(1, bz + random.nextInt(2) - random.nextInt(2)));
            pos = pos.add(dx, random.nextInt(3) > 0 ? 1 : 0, dz);
            set(world, pos, log);
            if (random.nextBoolean()) set(world, pos.up(), leaves);
            for (int x = -1; x <= 1; x++) for (int z = -1; z <= 1; z++)
                if (random.nextBoolean() && world.isAirBlock(pos.add(x, 0, z))) set(world, pos.add(x, 0, z), leaves);
        }
    }

    private static void leafBall(ServerWorld world, BlockPos center, int radius, BlockState leaves) {
        for (int x = -radius; x <= radius; x++) for (int y = -2; y <= 2; y++) for (int z = -radius; z <= radius; z++)
            if (x * x + z * z + y * y * 2 <= radius * radius + 2 && world.isAirBlock(center.add(x, y, z)))
                set(world, center.add(x, y, z), leaves);
    }

    private static void buildRockField(ServerWorld world, Random random, BlockPos center) {
        BlockState stone = state("crystal_stone", Blocks.STONE);
        int count = 3 + random.nextInt(7);
        for (int i = 0; i < count; i++) {
            BlockPos p = surface(world, center.getX() + random.nextInt(13) - 6, center.getZ() + random.nextInt(13) - 6);
            int radius = 1 + random.nextInt(2);
            for (int x = -radius; x <= radius; x++) for (int y = 0; y <= radius; y++) for (int z = -radius; z <= radius; z++)
                if (x * x + y * y + z * z <= radius * radius + 1) set(world, p.add(x, y, z), stone);
        }
    }

    private static void placePlant(ServerWorld world, int x, int z, String id) {
        BlockPos pos = surface(world, x, z);
        if (pos.getY() > 40 && world.isAirBlock(pos)
            && world.getBlockState(pos.down()).getBlock() == block("crystal_grass", Blocks.GRASS_BLOCK))
            set(world, pos, state(id, Blocks.AIR));
    }

    private static BlockPos surface(ServerWorld world, int x, int z) {
        return world.getHeight(Heightmap.Type.WORLD_SURFACE, new BlockPos(x, 0, z));
    }

    private static void fill(ServerWorld world, BlockPos a, BlockPos b, BlockState state) {
        for (int x = Math.min(a.getX(), b.getX()); x <= Math.max(a.getX(), b.getX()); x++)
            for (int y = Math.min(a.getY(), b.getY()); y <= Math.max(a.getY(), b.getY()); y++)
                for (int z = Math.min(a.getZ(), b.getZ()); z <= Math.max(a.getZ(), b.getZ()); z++)
                    set(world, new BlockPos(x, y, z), state);
    }

    private static void chest(ServerWorld world, Random random, BlockPos pos, String loot) {
        set(world, pos, Blocks.CHEST.getDefaultState());
        LockableLootTileEntity.setLootTable(world, random, pos,
            new ResourceLocation(MODID, "chests/crystal/" + loot));
    }

    private static void spawner(ServerWorld world, BlockPos pos, String entityId) {
        EntityType<?> type = ForgeRegistries.ENTITIES.getValue(new ResourceLocation(MODID, entityId));
        if (type == null) return;
        set(world, pos, Blocks.SPAWNER.getDefaultState());
        TileEntity tile = world.getTileEntity(pos);
        if (tile instanceof MobSpawnerTileEntity)
            ((MobSpawnerTileEntity) tile).getSpawnerBaseLogic().setEntityType(type);
    }

    private static Block block(String id, Block fallback) {
        Block value = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(MODID, id));
        return value == null || value == Blocks.AIR && fallback != Blocks.AIR ? fallback : value;
    }

    private static BlockState state(String id, Block fallback) { return block(id, fallback).getDefaultState(); }
    private static void set(ServerWorld world, BlockPos pos, BlockState state) { world.setBlockState(pos, state, 2); }
}
