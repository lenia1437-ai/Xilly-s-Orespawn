package net.mcreator.xillysorespawn.entity;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.function.Predicate;

import javax.annotation.Nullable;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.event.world.BiomeLoadingEvent;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.server.ServerWorld;

/**
 * Shared compatibility layer for the OreSpawn 1.7.10 entity ports.
 *
 * The original classes referenced every OreSpawn item and entity as a Java
 * static field. MCreator generates those classes in an order that changes as
 * elements are imported, so direct references make otherwise valid entity
 * ports uncompilable until the entire mod has been recreated. Registry lookup
 * preserves the original interaction while keeping each port independent: as
 * soon as the referenced element is registered under one of its old or modern
 * names, the behaviour becomes active.
 */
public final class OreSpawnLogic {
    public static final String MODID = "xillys_orespawn";

    /** Original OreSpawn.cfg defaults. */
    public static int playNicely = 0;
    public static int dragonflyHorseFriendly = 0;

    private OreSpawnLogic() {
    }

    /**
     * Keeps the generated per-entity spawn hooks from registering every ported
     * creature in every biome. This is the vanilla-biome part of OreSpawn
     * 1.7.10's EntityRegistry.addSpawn table. Custom dimensions rebuild their
     * own tables in their dimension classes and intentionally do not pass here.
     */
    public static boolean allowNaturalSpawn(BiomeLoadingEvent event, String entityPath) {
        ResourceLocation biome = event.getName();
        if (biome == null || !"minecraft".equals(biome.getNamespace())) return false;
        String path = biome.getPath();
        switch (normalize(entityPath)) {
        case "attacksquid": return biomeIs(path, "ocean,river,swamp");
        case "bandp": return biomeIs(path, "desert,plains,savanna");
        case "basilisk": return biomeIs(path, "birch_forest_hills,dark_forest,jungle,jungle_hills");
        case "beaver": return biomeIs(path, "birch_forest,birch_forest_hills,forest,giant_tree_taiga,river,taiga");
        case "bee": return biomeIs(path, "birch_forest,birch_forest_hills,forest,giant_tree_taiga,jungle,jungle_hills,savanna,savanna_plateau,taiga,wooded_hills");
        case "boyfriend":
        case "girlfriend": return biomeIs(path, "beach,birch_forest,birch_forest_hills,forest,giant_tree_taiga,plains,river,savanna,savanna_plateau,stone_shore,taiga,wooded_hills");
        case "brutalfly": return biomeIs(path, "badlands_plateau,giant_tree_taiga_hills,wooded_mountains");
        case "cassowary": return biomeIs(path, "birch_forest,birch_forest_hills,giant_tree_taiga,giant_tree_taiga_hills,mountain_edge,mountains,savanna,savanna_plateau,wooded_mountains");
        case "cater_killer": return biomeIs(path, "birch_forest,birch_forest_hills,dark_forest,forest,giant_tree_taiga,jungle,jungle_hills,taiga,wooded_hills");
        case "cephadrome": return biomeIs(path, "snowy_taiga,snowy_tundra");
        case "chipmunk": return biomeIs(path, "birch_forest,birch_forest_hills,dark_forest,forest,giant_tree_taiga,jungle,plains,taiga,wooded_hills");
        case "cockateil": return biomeIs(path, "beach,birch_forest,birch_forest_hills,forest,giant_tree_taiga,jungle,jungle_hills,mountain_edge,mountains,plains,river,savanna,savanna_plateau,stone_shore,taiga,wooded_hills");
        case "coin": return biomeIs(path, "birch_forest,forest,giant_tree_taiga,jungle,snowy_taiga,taiga");
        case "crab": return biomeIs(path, "ocean,stone_shore,swamp");
        case "cricket": return biomeIs(path, "birch_forest,birch_forest_hills,dark_forest,forest,giant_tree_taiga,jungle,jungle_hills,plains,savanna_plateau,taiga,wooded_hills");
        case "dragonfly": return biomeIs(path, "river,swamp");
        case "dungeon_beast": return biomeIs(path, "dark_forest");
        case "easter_bunny": return biomeIs(path, "birch_forest,birch_forest_hills,forest,giant_tree_taiga,plains,taiga,wooded_hills");
        case "emperor_scorpion": return biomeIs(path, "desert,savanna");
        case "enchanted_cow": return biomeIs(path, "forest,giant_tree_taiga,mushroom_fields,plains");
        case "ender_knight":
        case "ender_reaper": return biomeIs(path, "dark_forest,desert,forest,jungle_hills,mountain_edge,mountains,plains,river,wooded_hills");
        case "butterfly": return biomeIs(path, "beach,birch_forest,birch_forest_hills,forest,giant_tree_taiga,jungle,jungle_hills,mountain_edge,mountains,plains,river,savanna,savanna_plateau,swamp,taiga,wooded_hills");
        case "moth": return biomeIs(path, "birch_forest,birch_forest_hills,dark_forest,forest,giant_tree_taiga,jungle,jungle_hills,mountain_edge,mountains,plains,savanna,savanna_plateau,swamp,taiga,wooded_hills");
        case "fairy": return biomeIs(path, "dark_forest");
        case "firefly": return biomeIs(path, "birch_forest,birch_forest_hills,forest,giant_tree_taiga,giant_tree_taiga_hills,jungle,jungle_hills,savanna,savanna_plateau,stone_shore,swamp,taiga,wooded_hills");
        case "frog": return biomeIs(path, "jungle,river,swamp");
        case "ghost":
        case "ghost_skelly": return biomeIs(path, "badlands,badlands_plateau,beach,birch_forest,birch_forest_hills,dark_forest,desert,forest,frozen_river,giant_tree_taiga,jungle,jungle_hills,mountain_edge,mountains,plains,river,savanna,savanna_plateau,snowy_taiga,snowy_taiga_hills,taiga,taiga_hills,wooded_badlands_plateau,wooded_hills");
        case "gold_cow": return biomeIs(path, "forest,giant_tree_taiga,plains,taiga");
        case "hercules_beetle": return biomeIs(path, "birch_forest_hills,giant_tree_taiga_hills,jungle_hills,mountain_edge,snowy_taiga_hills,taiga_hills,wooded_hills");
        case "hydrolisc": return biomeIs(path, "jungle,jungle_hills,stone_shore,swamp");
        case "kyuubi": return biomeIs(path, "nether_wastes");
        case "leaf_monster": return biomeIs(path, "birch_forest,birch_forest_hills,forest,giant_tree_taiga,jungle,jungle_hills,taiga,wooded_hills");
        case "lizard": return biomeIs(path, "ocean,river,swamp");
        case "mantis": return biomeIs(path, "birch_forest,forest,giant_tree_taiga,jungle,plains,savanna,savanna_plateau,swamp,wooded_hills");
        case "molenoid": return biomeIs(path, "plains,savanna,savanna_plateau");
        case "mothra": return biomeIs(path, "mountains,wooded_mountains");
        case "ostrich": return biomeIs(path, "desert,savanna,savanna_plateau,stone_shore");
        case "peacock": return biomeIs(path, "badlands,badlands_plateau");
        case "rat": return biomeIs(path, "dark_forest,taiga");
        case "red_cow": return biomeIs(path, "forest,giant_tree_taiga,plains,savanna,savanna_plateau,taiga");
        case "rubber_ducky": return biomeIs(path, "river,stone_shore");
        case "scorpion": return biomeIs(path, "badlands,badlands_plateau,dark_forest,desert,savanna,savanna_plateau,wooded_badlands_plateau");
        case "sea_monster": return biomeIs(path, "ocean,swamp");
        case "sea_viper": return biomeIs(path, "ocean,stone_shore");
        case "spit_bug": return biomeIs(path, "swamp");
        case "stink_bug": return biomeIs(path, "forest,jungle,jungle_hills,savanna,wooded_hills");
        case "stinky": return biomeIs(path, "badlands,badlands_plateau,nether_wastes,wooded_badlands_plateau");
        case "trooper_bug": return biomeIs(path, "badlands,swamp");
        case "water_dragon": return biomeIs(path, "ocean,river,stone_shore,swamp");
        case "whale": return biomeIs(path, "deep_ocean");
        case "worm_large": return biomeIs(path, "plains,savanna,savanna_plateau");
        default: return false;
        }
    }

    private static boolean biomeIs(String actual, String allowed) {
        for (String candidate : allowed.split(",")) {
            if (candidate.equals(actual)) return true;
        }
        return false;
    }

    private static String normalize(String name) {
        return name.toLowerCase(Locale.ROOT).replace("orespawn_", "")
            .replace(" ", "_").replace("-", "_");
    }

    private static ResourceLocation id(String namespace, String path) {
        return new ResourceLocation(namespace, normalize(path));
    }

    @Nullable
    public static Item itemOrNull(String... paths) {
        for (String path : paths) {
            Item item = ForgeRegistries.ITEMS.getValue(id(MODID, path));
            if (item != null && item != Items.AIR) {
                return item;
            }
        }
        return null;
    }

    public static Item item(String... paths) {
        Item item = itemOrNull(paths);
        return item == null ? Items.AIR : item;
    }

    @Nullable
    public static Block blockOrNull(String... paths) {
        for (String path : paths) {
            Block block = ForgeRegistries.BLOCKS.getValue(id(MODID, path));
            if (block != null && block != Blocks.AIR) {
                return block;
            }
        }
        return null;
    }

    @Nullable
    public static SoundEvent soundOrNull(String path) {
        SoundEvent sound = ForgeRegistries.SOUND_EVENTS.getValue(id(MODID, path));
        if (sound == null) {
            sound = ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("orespawn", normalize(path)));
        }
        return sound;
    }

    public static SoundEvent sound(String path, SoundEvent fallback) {
        SoundEvent sound = soundOrNull(path);
        return sound == null ? fallback : sound;
    }

    public static boolean isNamed(Entity entity, String... paths) {
        ResourceLocation registryName = entity.getType().getRegistryName();
        if (registryName == null) {
            return false;
        }
        String actual = normalize(registryName.getPath());
        return Arrays.stream(paths).map(OreSpawnLogic::normalize).anyMatch(actual::equals);
    }

    public static boolean isIgnoreable(LivingEntity entity) {
        return isNamed(entity, "rock", "rock_base", "ant", "entity_ant", "butterfly", "mosquito",
            "entity_mosquito", "dragonfly", "firefly", "cricket", "cockateil", "termite", "ghost",
            "ghost_skelly", "elevator");
    }

    /** Port of MyUtils.isAttackableNonMob from OreSpawn 20.3. */
    public static boolean isAttackableNonMob(LivingEntity entity) {
        return entity instanceof MonsterEntity || entity instanceof VillagerEntity
            || isNamed(entity, "mothra", "leon", "dragon", "spyro", "the_prince",
                "the_prince_teen", "the_prince_adult", "the_princess", "the_king",
                "king_head", "the_queen", "queen_head", "purple_power", "gamma_metroid",
                "cephadrome", "water_dragon", "girlfriend", "boyfriend", "stinky");
    }

    public static boolean isDimension(World world, String... paths) {
        ResourceLocation registryName = world.getDimensionKey().getLocation();
        String actual = normalize(registryName.getPath());
        return Arrays.stream(paths).map(OreSpawnLogic::normalize).anyMatch(actual::equals);
    }

    public static boolean isIslandsDimension(World world) {
        return isDimension(world, "dimension_islands", "islands", "dimension_4", "orespawn_4");
    }

    public static boolean isCrystalDimension(World world) {
        return isDimension(world, "dimension_crystal", "crystal_dimension", "crystal", "dimension_5", "orespawn_5");
    }

    public static boolean isChaosDimension(World world) {
        return isDimension(world, "dimension_chaos", "chaos", "dimension_6", "orespawn_6");
    }

    /** Registry-name based replacement for the old OreSpawnTeleporter. */
    public static boolean toggleDimension(ServerPlayerEntity player, String... destinationPaths) {
        if (player == null || player.getServer() == null) return false;
        ServerWorld current = player.getServerWorld();
        ServerWorld destination = null;
        boolean alreadyThere = false;
        for (String path : destinationPaths) {
            ResourceLocation location = id(MODID, path);
            if (current.getDimensionKey().getLocation().equals(location)) {
                alreadyThere = true;
                break;
            }
        }
        if (alreadyThere) {
            destination = player.getServer().getWorld(World.OVERWORLD);
        } else {
            for (String path : destinationPaths) {
                RegistryKey<World> key = RegistryKey.getOrCreateKey(Registry.WORLD_KEY, id(MODID, path));
                destination = player.getServer().getWorld(key);
                if (destination != null) break;
            }
        }
        if (destination == null || destination == current) return false;
        BlockPos requested = new BlockPos(player.getPosX(), 80.0D, player.getPosZ());
        BlockPos arrival = findSafeArrival(destination, requested.getX(), requested.getZ());
        player.teleport(destination, arrival.getX() + 0.5D, arrival.getY() + 0.05D,
            arrival.getZ() + 0.5D, player.rotationYaw, player.rotationPitch);
        player.setMotion(Vector3d.ZERO);
        player.fallDistance = 0.0F;
        return true;
    }

    /**
     * Heightmaps of a freshly created custom dimension can temporarily report
     * Y=0. Teleporting to height + 1 then puts the player inside the bedrock
     * floor. Load the destination chunk, inspect several nearby columns and
     * only accept a surface with a solid, non-bedrock floor and two air blocks.
     */
    private static BlockPos findSafeArrival(ServerWorld world, int centerX, int centerZ) {
        world.getChunk(centerX >> 4, centerZ >> 4);

        for (int radius = 0; radius <= 32; radius += 4) {
            if (radius == 0) {
                BlockPos safe = safeSurface(world, centerX, centerZ);
                if (safe != null) return safe;
                continue;
            }
            for (int offset = -radius; offset <= radius; offset += 4) {
                BlockPos safe = safeSurface(world, centerX + offset, centerZ - radius);
                if (safe != null) return safe;
                safe = safeSurface(world, centerX + offset, centerZ + radius);
                if (safe != null) return safe;
                safe = safeSurface(world, centerX - radius, centerZ + offset);
                if (safe != null) return safe;
                safe = safeSurface(world, centerX + radius, centerZ + offset);
                if (safe != null) return safe;
            }
        }

        // A malformed/unfinished terrain column must never strand the player
        // in bedrock. Build a small, lit-independent emergency landing instead.
        int y = Math.max(64, world.getSeaLevel() + 8);
        y = Math.min(y, world.getHeight() - 4);
        BlockPos arrival = new BlockPos(centerX, y, centerZ);
        for (int x = -2; x <= 2; x++) {
            for (int z = -2; z <= 2; z++) {
                world.setBlockState(arrival.add(x, -1, z), Blocks.STONE.getDefaultState(), 2);
                world.setBlockState(arrival.add(x, 0, z), Blocks.AIR.getDefaultState(), 2);
                world.setBlockState(arrival.add(x, 1, z), Blocks.AIR.getDefaultState(), 2);
                world.setBlockState(arrival.add(x, 2, z), Blocks.AIR.getDefaultState(), 2);
            }
        }
        return arrival;
    }

    @Nullable
    private static BlockPos safeSurface(ServerWorld world, int x, int z) {
        BlockPos feet = world.getHeight(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
            new BlockPos(x, 0, z));
        if (feet.getY() < 5 || feet.getY() >= world.getHeight() - 2) return null;

        BlockPos floorPos = feet.down();
        BlockState floor = world.getBlockState(floorPos);
        if (floor.getBlock() == Blocks.BEDROCK || !floor.getFluidState().isEmpty()
                || !floor.isSolidSide(world, floorPos, Direction.UP)) return null;
        if (!world.isAirBlock(feet) || !world.isAirBlock(feet.up())) return null;
        return feet;
    }

    public static boolean hasClearPath(Entity entity, double x, double y, double z) {
        Vector3d start = new Vector3d(entity.getPosX(), entity.getPosY() + entity.getHeight() * 0.5D, entity.getPosZ());
        Vector3d end = new Vector3d(x, y, z);
        RayTraceContext context = new RayTraceContext(start, end, RayTraceContext.BlockMode.COLLIDER,
            RayTraceContext.FluidMode.NONE, entity);
        return entity.world.rayTraceBlocks(context).getType() == RayTraceResult.Type.MISS;
    }

    public static boolean isAirTarget(World world, BlockPos pos) {
        return pos != null && world.isAirBlock(pos);
    }

    @Nullable
    public static LivingEntity nearestTarget(MobEntity seeker, AxisAlignedBB box,
            Predicate<LivingEntity> predicate) {
        List<LivingEntity> candidates = seeker.world.getEntitiesWithinAABB(LivingEntity.class, box,
            e -> e != seeker && e.isAlive() && predicate.test(e));
        return candidates.stream().min(Comparator.comparingDouble(seeker::getDistanceSq)).orElse(null);
    }

    public static ItemStack drop(World world, Entity source, Item item, int count, int spread) {
        if (item == null || item == Items.AIR || count <= 0 || world.isRemote) {
            return ItemStack.EMPTY;
        }
        ItemStack stack = new ItemStack(item, count);
        double x = source.getPosX();
        double z = source.getPosZ();
        if (spread > 0) {
            x += world.rand.nextInt(spread) - world.rand.nextInt(spread);
            z += world.rand.nextInt(spread) - world.rand.nextInt(spread);
        }
        world.addEntity(new ItemEntity(world, x, source.getPosY() + 1.0D, z, stack));
        return stack;
    }

    public static ItemStack drop(World world, Entity source, String path, int count, int spread) {
        return drop(world, source, item(path), count, spread);
    }

    @Nullable
    public static Entity spawn(World world, String path, double x, double y, double z) {
        if (world.isRemote) {
            return null;
        }
        EntityType<?> type = ForgeRegistries.ENTITIES.getValue(id(MODID, path));
        if (type == null) {
            return null;
        }
        Entity entity = type.create(world);
        if (entity == null) {
            return null;
        }
        entity.setPositionAndRotation(x, y, z, world.rand.nextFloat() * 360.0F, 0.0F);
        if (entity instanceof MobEntity && world instanceof IServerWorld) {
            ((MobEntity) entity).onInitialSpawn((IServerWorld) world,
                world.getDifficultyForLocation(new BlockPos(x, y, z)), SpawnReason.MOB_SUMMONED, null, null);
        }
        world.addEntity(entity);
        return entity;
    }

    public static boolean isNearSpawner(World world, BlockPos center, String entityPath) {
        // The 1.7 implementation was only an exception to natural-spawn rules.
        // Forge calls the placement predicate for spawner and spawn-egg reasons as
        // well, so those reasons are handled in each entity before this fallback.
        return false;
    }
}
