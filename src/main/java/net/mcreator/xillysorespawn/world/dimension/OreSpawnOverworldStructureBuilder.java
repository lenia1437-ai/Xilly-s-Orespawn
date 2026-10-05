package net.mcreator.xillysorespawn.world.dimension;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.DoorBlock;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.util.Direction;
import net.mcreator.xillysorespawn.block.LavaFoamBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.MobSpawnerTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Random;

/** Small 1.16.5 reproductions of the Overworld-only OreSpawn landmarks. */
final class OreSpawnOverworldStructureBuilder {
    private OreSpawnOverworldStructureBuilder() { }

    static void build(ServerWorld world, Random random, BlockPos base, int kind) {
        switch (kind) {
            case 1: playPool(world, base); break;
            case 2: waterDragonLair(world, base); break;
            case 3: goldfishBowl(world, base); break;
            case 4: island(world, base, false); break;
            case 5: island(world, base, true); break;
            case 6: pond(world, base, Blocks.LILY_PAD); break;
            case 7: smallBeeHive(world, random, base.up()); break;
            case 8: hive(world, base, Blocks.OAK_LEAVES, "mantis"); break;
            case 9: hauntedHouse(world, base.up()); break;
            case 10: leafMonster(world, base); break;
            case 11: spitBugLair(world, base.up()); break;
            case 12: igloo(world, base.down()); break;
            case 13: bouncyCastle(world, base); break;
            case 14: pond(world, base, Blocks.YELLOW_WOOL); break;
            default: break;
        }
    }

    private static void put(ServerWorld world, BlockPos base, int x, int y, int z, Block block) {
        BlockPos at = base.add(x, y, z);
        world.setBlockState(at, block.getDefaultState(), 2);
        if (block == Blocks.CHEST) {
            TileEntity tile = world.getTileEntity(at);
            if (tile instanceof ChestTileEntity)
                ((ChestTileEntity) tile).setLootTable(new ResourceLocation("minecraft:chests/simple_dungeon"), world.rand.nextLong());
        }
    }

    private static void spawner(ServerWorld world, BlockPos base, int x, int y, int z, String entityId) {
        BlockPos at = base.add(x, y, z);
        world.setBlockState(at, Blocks.SPAWNER.getDefaultState(), 2);
        TileEntity tile = world.getTileEntity(at);
        ResourceLocation location = entityId.indexOf(':') >= 0
            ? new ResourceLocation(entityId) : new ResourceLocation("xillys_orespawn", entityId);
        EntityType<?> type = ForgeRegistries.ENTITIES.getValue(location);
        if (tile instanceof MobSpawnerTileEntity && type != null)
            ((MobSpawnerTileEntity) tile).getSpawnerBaseLogic().setEntityType(type);
    }

    private static void disc(ServerWorld world, BlockPos base, int y, int radius, Block block) {
        for (int x = -radius; x <= radius; x++) for (int z = -radius; z <= radius; z++)
            if (x * x + z * z <= radius * radius) put(world, base, x, y, z, block);
    }

    private static void ring(ServerWorld world, BlockPos base, int y, int radius, Block block) {
        for (int x = -radius; x <= radius; x++) for (int z = -radius; z <= radius; z++) {
            int d = x * x + z * z;
            if (d <= radius * radius && d >= (radius - 1) * (radius - 1))
                put(world, base, x, y, z, block);
        }
    }

    private static void playPool(ServerWorld world, BlockPos base) {
        for (int x = -2; x <= 2; x++) {
            spawner(world, base, x, 16, 0, "attacksquid");
            put(world, base, x, 18, 0, Blocks.WATER);
        }
        put(world, base, 0, 17, 0, Blocks.CHEST);
        put(world, base, 1, 17, 0, Blocks.CHEST);
    }

    private static void waterDragonLair(ServerWorld world, BlockPos base) {
        disc(world, base, 7, 10, Blocks.BEDROCK);
        ring(world, base, 7, 6, Blocks.GOLD_BLOCK);
        for (int y = 1; y <= 6; y++) ring(world, base, y, 10,
            y == 2 || y == 3 ? Blocks.GOLD_ORE : Blocks.BEDROCK);
        disc(world, base, 0, 3, Blocks.SAND);
        put(world, base, 0, 3, 0, Blocks.OAK_LEAVES);
        spawner(world, base, 1, 3, 0, "water_dragon");
        spawner(world, base, -1, 3, 0, "water_dragon");
        put(world, base, 0, 8, 0, Blocks.CHEST);
    }

    private static void goldfishBowl(ServerWorld world, BlockPos base) {
        disc(world, base, 0, 6, Blocks.GLASS);
        for (int y = 1; y <= 5; y++) {
            ring(world, base, y, 6, Blocks.GLASS);
            disc(world, base, y, 5, Blocks.WATER);
        }
        disc(world, base, 6, 6, Blocks.GLASS);
        put(world, base, 0, 7, 0, Blocks.GOLD_BLOCK);
    }

    private static void island(ServerWorld world, BlockPos base, boolean monster) {
        disc(world, base, 0, 8, Blocks.SAND);
        disc(world, base, 1, 6, Blocks.GRASS_BLOCK);
        for (int y = 2; y <= 5; y++) put(world, base, 0, y, 0, Blocks.OAK_LOG);
        disc(world, base, 6, 3, Blocks.OAK_LEAVES);
        put(world, base, 3, 2, 2, Blocks.CHEST);
        if (monster) spawner(world, base, -3, 2, -2, "leaf_monster");
        else put(world, base, -3, 2, -2, Blocks.PINK_WOOL);
    }

    private static void pond(ServerWorld world, BlockPos base, Block ornament) {
        disc(world, base, -2, 6, Blocks.DIRT);
        disc(world, base, -1, 6, Blocks.WATER);
        ring(world, base, 0, 7, Blocks.GRASS_BLOCK);
        put(world, base, 0, 0, 0, ornament);
        put(world, base, 3, 0, 1, ornament);
    }

    private static void hive(ServerWorld world, BlockPos base, Block shell, String entityId) {
        for (int y = 1; y <= 8; y++) {
            int radius = y < 4 ? 3 : (y < 7 ? 4 : 2);
            ring(world, base, y, radius, shell);
        }
        disc(world, base, 0, 4, Blocks.OAK_PLANKS);
        disc(world, base, 9, 2, shell);
        spawner(world, base, 0, 1, 0, entityId);
        put(world, base, 2, 1, 1, Blocks.CHEST);
        if ("bee".equals(entityId)) {
            // Original bee hives use gold-ore walls and several guarded chests,
            // without intermediate solid floors.
            spawner(world, base, 1, 4, 0, entityId);
            spawner(world, base, -1, 6, 0, entityId);
            put(world, base, -2, 3, -1, Blocks.CHEST);
            put(world, base, 1, 5, 1, Blocks.CHEST);
        }
    }

    /** GenericDungeon.makeSmallBeeHive: sponge tiers and mossy hanging comb. */
    private static void smallBeeHive(ServerWorld world, Random random, BlockPos base) {
        for (int x = -3; x < 10; x++) for (int z = -3; z < 10; z++)
            for (int y = 14; y < 21; y++) put(world, base, x, y, z, Blocks.AIR);

        for (int x = 0; x < 7; x++) for (int z = 0; z < 7; z++) {
            put(world, base, x, 14, z, Blocks.SPONGE);
            int depth = Math.max(1, random.nextInt(7) * 2 - Math.abs(x - 3) - Math.abs(z - 3));
            if (x == 3 && z == 3) depth = 14;
            for (int y = 0; y < depth; y++)
                put(world, base, x, 14 - y, z, Blocks.MOSSY_COBBLESTONE);
        }

        int y = 14;
        for (int tier = 0; tier < 3; tier++) {
            y++;
            for (int x = 0; x < 7; x++) for (int z = 0; z < 7; z++)
                put(world, base, x, y, z,
                    x == 0 || z == 0 || x == 6 || z == 6 ? Blocks.SPONGE : Blocks.AIR);
            y++;
            for (int x = -1; x <= 7; x++) for (int z = -1; z <= 7; z++)
                put(world, base, x, y, z,
                    x == -1 || z == -1 || x == 7 || z == 7 ? Blocks.SPONGE : Blocks.AIR);
        }
        for (int x = 0; x < 7; x++) for (int z = 0; z < 7; z++)
            put(world, base, x, 21, z, Blocks.SPONGE);
        for (int x = -1; x <= 0; x++) for (int z = 2; z <= 3; z++)
            for (int doorY = 15; doorY <= 17; doorY++) put(world, base, x, doorY, z, Blocks.AIR);
        for (int spawnerY = 15; spawnerY <= 17; spawnerY++)
            spawner(world, base, 1, spawnerY, 1, "bee");
        put(world, base, 3, 15, 3, Blocks.CHEST);
    }

    private static void house(ServerWorld world, BlockPos base, Block wall, Block ornament, String entityId) {
        for (int x = -4; x <= 4; x++) for (int z = -4; z <= 4; z++) {
            put(world, base, x, 0, z, Blocks.COBBLESTONE);
            for (int y = 1; y <= 5; y++)
                put(world, base, x, y, z, Math.abs(x) == 4 || Math.abs(z) == 4 ? wall : Blocks.AIR);
            put(world, base, x, 6, z, wall);
        }
        put(world, base, 0, 1, -4, Blocks.AIR);
        put(world, base, 0, 2, -4, Blocks.AIR);
        spawner(world, base, 0, 1, 0, entityId);
        put(world, base, 2, 1, 2, Blocks.CHEST);
        put(world, base, -2, 3, 1, ornament);
    }

    /** GenericDungeon.makeHauntedHouse: seven by seven, one storey and three stacked spawners. */
    private static void hauntedHouse(ServerWorld world, BlockPos base) {
        for (int x = -3; x <= 3; x++) for (int z = -3; z <= 3; z++) for (int y = 0; y <= 4; y++) {
            Block block;
            if (y == 0) block = Blocks.COBBLESTONE;
            else if (y == 4) block = Blocks.OAK_PLANKS;
            else if (Math.abs(x) == 3 || Math.abs(z) == 3)
                block = y == 3 ? Blocks.GLASS : Blocks.OAK_PLANKS;
            else block = Blocks.AIR;
            put(world, base, x, y, z, block);
        }
        put(world, base, 3, 1, 0, Blocks.AIR);
        put(world, base, 3, 2, 0, Blocks.AIR);
        put(world, base, 2, 1, 2, Blocks.FURNACE);
        put(world, base, 1, 1, 2, Blocks.CRAFTING_TABLE);
        put(world, base, 0, 1, 2, Blocks.CHEST);
        spawner(world, base, 0, 1, 0, "rat");
        spawner(world, base, 0, 2, 0, "ghost");
        spawner(world, base, 0, 3, 0, "ghost_skelly");
    }

    /** GenericDungeon.makeBouncyCastle: lava-foam 9x9x5 shell, red terracotta corners. */
    private static void bouncyCastle(ServerWorld world, BlockPos base) {
        Block lavafoam = LavaFoamBlock.block;
        for (int x = -4; x <= 4; x++) for (int z = -4; z <= 4; z++) for (int y = 0; y < 5; y++) {
            boolean edge = Math.abs(x) == 4 || Math.abs(z) == 4;
            Block block = y == 0 || y == 4 || edge ? lavafoam : Blocks.AIR;
            if (Math.abs(x) == 4 && Math.abs(z) == 4) block = Blocks.RED_TERRACOTTA;
            if (x == 0 && z == -4 && (y == 1 || y == 2)) block = Blocks.AIR;
            put(world, base, x, y, z, block);
        }
        for (int i = -1; i <= 1; i++) {
            String mob = i == -1 ? "minecraft:silverfish" : (i == 0 ? "rat" : "scorpion");
            spawner(world, base, i, 3, 3, mob);
            spawner(world, base, 3, 3, i, mob);
            spawner(world, base, -3, 3, i, mob);
        }
        put(world, base, 3, 3, 3, Blocks.CHEST);
    }

    /** GenericDungeon.makeSpitBugLair, including the crossed tapering wings. */
    private static void spitBugLair(ServerWorld world, BlockPos base) {
        for (int i = 0; i < 9; i++) {
            for (int sign : new int[] {-1, 1}) {
                int x = sign * i;
                put(world, base, x, 11 - i, 0, Blocks.GREEN_TERRACOTTA);
                put(world, base, x, 10 - i, 0, Blocks.GREEN_TERRACOTTA);
                put(world, base, x, 9 - i, 0, Blocks.MOSSY_COBBLESTONE);
            }
        }
        for (int y = 10; y <= 12; y++) put(world, base, 0, y, 0, Blocks.EMERALD_ORE);
        for (int y = 7; y <= 9; y++) spawner(world, base, 0, y, 0, "spit_bug");
        for (int i = 0; i < 9; i++) for (int z = -i; z <= i; z++) {
            int left = -8 + i, right = 8 - i;
            for (int x : new int[] {left, right}) {
                put(world, base, x, 0, z, Blocks.LIME_TERRACOTTA);
                if (z == -i || z == i) {
                    put(world, base, x, 1, z, Blocks.GREEN_TERRACOTTA);
                    put(world, base, x, 2, z, Blocks.CHISELED_STONE_BRICKS);
                } else {
                    put(world, base, x, 1, z, Blocks.AIR);
                    put(world, base, x, 2, z, Blocks.AIR);
                }
            }
        }
        put(world, base, 0, 1, 0, Blocks.CHEST);
    }

    private static void leafMonster(ServerWorld world, BlockPos base) {
        for (int y = 1; y <= 8; y++) put(world, base, 0, y, 0, Blocks.OAK_LOG);
        for (int y = 6; y <= 9; y++) disc(world, base, y, y == 8 ? 4 : 3, Blocks.OAK_LEAVES);
        spawner(world, base, 0, 1, 0, "leaf_monster");
        put(world, base, 2, 1, 0, Blocks.CHEST);
    }

    private static void igloo(ServerWorld world, BlockPos base) {
        // GenericDungeon.makeIgloo uses five concentric angular rings.
        snowRing(world, base, 6, 1, 5, Blocks.SNOW_BLOCK);
        snowRing(world, base, 6, 2, 5, Blocks.ICE);
        snowRing(world, base, 6, 3, 5, Blocks.SNOW_BLOCK);
        snowRing(world, base, 5, 4, 5, Blocks.ICE);
        snowRing(world, base, 4, 5, 5, Blocks.SNOW_BLOCK);
        snowRing(world, base, 3, 5, 10, Blocks.ICE);
        snowRing(world, base, 2, 5, 15, Blocks.SNOW_BLOCK);
        snowRing(world, base, 1, 5, 15, Blocks.ICE);
        put(world, base, -5, 0, 0, Blocks.OAK_PLANKS);
        world.setBlockState(base.add(-5, 1, 0), Blocks.OAK_DOOR.getDefaultState()
            .with(DoorBlock.FACING, Direction.WEST).with(DoorBlock.HALF, DoubleBlockHalf.LOWER), 2);
        world.setBlockState(base.add(-5, 2, 0), Blocks.OAK_DOOR.getDefaultState()
            .with(DoorBlock.FACING, Direction.WEST).with(DoorBlock.HALF, DoubleBlockHalf.UPPER), 2);
        spawner(world, base, 2, 1, -4, "rat");
        spawner(world, base, -1, 1, 1, "ghost");
        spawner(world, base, 3, 1, 4, "ghost_skelly");
        put(world, base, -3, 1, -3, Blocks.CHEST);
    }

    private static void snowRing(ServerWorld world, BlockPos base, int radius, int y, int step, Block block) {
        for (int angle = 0; angle < 360; angle += step) {
            int x = (int) (radius * Math.cos(Math.toRadians(angle)) + 0.5D);
            int z = (int) (radius * Math.sin(Math.toRadians(angle)) + 0.5D);
            put(world, base, x, y, z, block);
        }
    }
}
