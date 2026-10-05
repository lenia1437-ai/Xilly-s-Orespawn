package net.mcreator.xillysorespawn.world.dimension;

import java.util.*;
import net.minecraft.block.*;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * Coordinate-preserving port of GenericDungeon from OreSpawn 20.3.
 * Recovered with CFR from the original class: JD-GUI omitted whole loops.
 * Plans collapse repeated writes and are applied incrementally by the world generator.
 */
public final class IslandsLegacyCastles {
    // Internal markers never enter the world: legacy monster egg blocks become spawners.
    private static final Block ENDER_KNIGHT_MARKER = new Block(AbstractBlock.Properties.from(Blocks.STONE));
    private static final Block ENDER_REAPER_MARKER = new Block(AbstractBlock.Properties.from(Blocks.STONE));
    private static final Block ENDERMAN_MARKER = new Block(AbstractBlock.Properties.from(Blocks.STONE));
    private static final Block ENDER_DRAGON_MARKER = new Block(AbstractBlock.Properties.from(Blocks.STONE));

    public static Plan create(Random random, BlockPos ground, boolean ender) {
        Plan plan = new Plan(random);
        IslandsLegacyCastles builder = new IslandsLegacyCastles();
        if (ender) builder.makeEnderCastle(plan, ground.getX()-11, ground.getY(), ground.getZ()-11);
        else if (random.nextBoolean()) builder.makeEnormousCastle(plan, ground.getX()-14, ground.getY(), ground.getZ()-14);
        else builder.makeEnormousCastleQ(plan, ground.getX()-14, ground.getY(), ground.getZ()-14);
        return plan;
    }

    private static Block registered(String id) {
        Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation("xillys_orespawn", id));
        if (block == null || block == Blocks.AIR)
            throw new IllegalStateException("Missing structure block: xillys_orespawn:" + id);
        return block;
    }

    public static final class Plan {
        final Random random;
        final LinkedHashMap<BlockPos, Placement> blocks = new LinkedHashMap<>();
        private Iterator<Map.Entry<BlockPos, Placement>> cursor;
        private Map.Entry<BlockPos, Placement> next;
        Plan(Random random) { this.random = random; }

        void setBlock(int x, int y, int z, Block block) { setBlock(x,y,z,block,0,2); }
        void setBlock(int x, int y, int z, Block block, int meta, int flags) {
            BlockPos p = new BlockPos(x,y,z);
            String mob = block == ENDER_KNIGHT_MARKER ? "ender_knight"
                : block == ENDER_REAPER_MARKER ? "ender_reaper"
                : block == ENDERMAN_MARKER ? "minecraft:enderman"
                : block == ENDER_DRAGON_MARKER ? "minecraft:ender_dragon" : null;
            if (mob != null) block = Blocks.SPAWNER;
            BlockState state = block.getDefaultState();
            Direction facing = meta == 3 ? Direction.SOUTH : meta == 4 ? Direction.WEST
                : meta == 5 ? Direction.EAST : Direction.NORTH;
            if (block == Blocks.CHEST) state = state.with(ChestBlock.FACING, facing);
            if (block == Blocks.ENDER_CHEST) state = state.with(EnderChestBlock.FACING, facing);
            Placement placement = new Placement(state);
            placement.mob = mob;
            blocks.put(p, placement);
        }
        void loot(BlockPos p, String name) { blocks.get(p).loot = name; }
        public boolean apply(ServerWorld world, int budget) {
            if (cursor == null) cursor = blocks.entrySet().iterator();
            while (budget-- > 0) {
                if (next == null) {
                    if (!cursor.hasNext()) return true;
                    next = cursor.next();
                }
                BlockPos p = next.getKey();
                if (!world.isAreaLoaded(p, 0)) return false;
                Placement v = next.getValue();
                if (p.getY() >= 0 && p.getY() < world.getHeight()) {
                    boolean connects = v.state.getBlock() instanceof PaneBlock || v.state.getBlock() instanceof FenceBlock;
                    BlockState state = connects ? Block.getValidBlockForPosition(v.state, world, p) : v.state;
                    if (!world.getBlockState(p).equals(state)) world.setBlockState(p, state, connects ? 3 : 2);
                    if (v.mob != null) {
                        TileEntity tile = world.getTileEntity(p);
                        if (tile instanceof MobSpawnerTileEntity) {
                            // Keep the true ID even when that mob has not yet been ported.
                            CompoundNBT nbt = tile.write(new CompoundNBT());
                            CompoundNBT spawn = new CompoundNBT();
                            spawn.putString("id", v.mob.indexOf(':') >= 0 ? v.mob : "xillys_orespawn:" + v.mob);
                            nbt.put("SpawnData", spawn);
                            nbt.remove("SpawnPotentials");
                            ((MobSpawnerTileEntity)tile).getSpawnerBaseLogic().read(nbt);
                            tile.markDirty();
                        }
                    }
                    if (v.loot != null)
                        LockableLootTileEntity.setLootTable(world, random, p,
                            new ResourceLocation("xillys_orespawn", "chests/islands/" + v.loot));
                }
                next = null;
            }
            return false;
        }
    }
    private static final class Placement {
        final BlockState state;
        String mob, loot;
        Placement(BlockState state) { this.state = state; }
    }
    private static final class Spawner {
        final Plan plan; final BlockPos pos;
        Spawner(Plan plan, BlockPos pos) { this.plan=plan; this.pos=pos; }
        void entity(String name) {
            String id;
            switch(name) {
                case "Nightmare": id="pitch_black"; break;
                case "Jumpy Bug": id="trooper_bug"; break;
                case "T. Rex": id="t_rex"; break;
                case "Large Worm": id="worm_large"; break;
                case "CaveFisher": id="cave_fisher"; break;
                case "CaterKiller": id="cater_killer"; break;
                default: id=name.toLowerCase(Locale.ROOT).replace(' ', '_');
            }
            plan.blocks.get(pos).mob=id;
        }
    }
    private Spawner getSpawnerTileEntity(Plan world, int x, int y, int z) {
        return new Spawner(world, new BlockPos(x,y,z));
    }
    private void FastSetBlock(Plan world, int x, int y, int z, Block block) {
        world.setBlock(x,y,z,block);
    }
    private void fill_chests(Plan world, int x, int y, int z, int width, int height, int decor, int reward) {
        int[][] positions={{1,width/2},{width-2,width/2},{width/2,1},{width/2,width-2}};
        int[] faces={5,4,3,2};
        for(int i=0;i<4;i++) {
            BlockPos p=new BlockPos(x+positions[i][0],y+1,z+positions[i][1]);
            world.setBlock(p.getX(),p.getY(),p.getZ(),Blocks.CHEST,faces[i],2);
            world.loot(p,reward==6 ? "king_final_"+i : "king_level_"+reward);
        }
    }

    public void makeEnormousCastleQ(Plan world, int cposx, int cposy, int cposz) {
        int k;
        int j;
        int i;
        int width = 28;
        int height = 16;
        int platformwidth = 11;
        int level = 0;
        if (false) {
            return;
        }
        level = 1 + world.random.nextInt(6);
        if (level <= 3 && world.random.nextInt(3) != 1) {
            level += 3;
        }
        for (i = -20; i < width + 4; ++i) {
            for (j = 1; j < height + 10; ++j) {
                for (k = -4; k < width + 4; ++k) {
                    this.FastSetBlock(world, cposx + i, cposy + j, cposz + k, Blocks.AIR);
                }
            }
        }
        for (i = 0; i < width; ++i) {
            j = 0;
            for (k = 0; k < width; ++k) {
                this.FastSetBlock(world, cposx + i, cposy + j, cposz + k, Blocks.OBSIDIAN);
            }
        }
        for (i = 0; i < width; ++i) {
            j = height;
            for (k = 0; k < width; ++k) {
                this.FastSetBlock(world, cposx + i, cposy + j, cposz + k, Blocks.BEDROCK);
            }
        }
        for (i = 0; i < width; ++i) {
            for (j = 1; j < height; ++j) {
                k = 0;
                this.FastSetBlock(world, cposx + i, cposy + j, cposz + k, Blocks.IRON_BARS);
                k = width - 1;
                this.FastSetBlock(world, cposx + i, cposy + j, cposz + k, Blocks.IRON_BARS);
            }
        }
        for (k = 0; k < width; ++k) {
            for (j = 1; j < height; ++j) {
                i = 0;
                this.FastSetBlock(world, cposx + i, cposy + j, cposz + k, Blocks.IRON_BARS);
                i = width - 1;
                this.FastSetBlock(world, cposx + i, cposy + j, cposz + k, Blocks.IRON_BARS);
            }
        }
        world.setBlock(cposx + 1, cposy + 1, cposz + 1, registered("extreme_torch"));
        world.setBlock(cposx + 1, cposy + 1, cposz + width - 2, registered("extreme_torch"));
        world.setBlock(cposx + width - 2, cposy + 1, cposz + 1, registered("extreme_torch"));
        world.setBlock(cposx + width - 2, cposy + 1, cposz + width - 2, registered("extreme_torch"));
        for (i = -4; i < width + 4; ++i) {
            for (k = -4; k < width + 4; ++k) {
                if (i < 0 || k < 0 || i >= width || k >= width) {
                    this.FastSetBlock(world, cposx + i, cposy, cposz + k, Blocks.OBSIDIAN);
                }
                if (i != -4 && k != -4 && i != width + 3 && k != width + 3) continue;
                this.FastSetBlock(world, cposx + i, cposy + 1, cposz + k, Blocks.NETHER_BRICK_FENCE);
            }
        }
        Spawner tileentitymobspawner = null;
        for (j = 0; j < 4; ++j) {
            world.setBlock(cposx - 3, cposy + 1 + j, cposz - 3, Blocks.SPAWNER, 0, 2);
            tileentitymobspawner = this.getSpawnerTileEntity(world, cposx - 3, cposy + 1 + j, cposz - 3);
            if (tileentitymobspawner != null) {
                tileentitymobspawner.entity("Lurking Terror");
            }
            world.setBlock(cposx - 3, cposy + 1 + j, cposz + width + 2, Blocks.SPAWNER, 0, 2);
            tileentitymobspawner = this.getSpawnerTileEntity(world, cposx - 3, cposy + 1 + j, cposz + width + 2);
            if (tileentitymobspawner != null) {
                tileentitymobspawner.entity("Lurking Terror");
            }
            world.setBlock(cposx + width + 2, cposy + 1 + j, cposz - 3, Blocks.SPAWNER, 0, 2);
            tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width + 2, cposy + 1 + j, cposz - 3);
            if (tileentitymobspawner != null) {
                tileentitymobspawner.entity("Lurking Terror");
            }
            world.setBlock(cposx + width + 2, cposy + 1 + j, cposz + width + 2, Blocks.SPAWNER, 0, 2);
            tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width + 2, cposy + 1 + j, cposz + width + 2);
            if (tileentitymobspawner == null) continue;
            tileentitymobspawner.entity("Lurking Terror");
        }
        world.setBlock(cposx + width / 2, cposy + 2, cposz + width / 2, Blocks.SPAWNER, 0, 2);
        tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2, cposy + 2, cposz + width / 2);
        if (tileentitymobspawner != null) {
            tileentitymobspawner.entity("Emperor Scorpion");
        }
        world.setBlock(cposx + width / 2, cposy + 3, cposz + width / 2, Blocks.SPAWNER, 0, 2);
        tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2, cposy + 3, cposz + width / 2);
        if (tileentitymobspawner != null) {
            tileentitymobspawner.entity("Emperor Scorpion");
        }
        world.setBlock(cposx + width / 2, cposy + 4, cposz + width / 2, Blocks.SPAWNER, 0, 2);
        tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2, cposy + 4, cposz + width / 2);
        if (tileentitymobspawner != null) {
            tileentitymobspawner.entity("Emperor Scorpion");
        }
        j = height;
        this.buildLevelQ(world, cposx + 1, cposy + j, cposz + 1, width - 2, 10, 4, "Rotator", 1, -1, 5, 1, level);
        j += 10;
        if (level >= 2) {
            this.buildLevelQ(world, cposx + 1, cposy + j, cposz + 1, width - 2, 10, 4, "Bee", 0, 0, 4, 2, level);
        }
        j += 10;
        if (level >= 3) {
            this.buildLevelQ(world, cposx + 2, cposy + j, cposz + 2, width - 4, 9, 4, "Mantis", 1, 1, 4, 3, level);
        }
        j += 9;
        if (level >= 4) {
            this.buildLevelQ(world, cposx + 2, cposy + j, cposz + 2, width - 4, 9, 3, "Mothra", 0, 0, 4, 4, level);
        }
        j += 9;
        if (level >= 5) {
            this.buildLevelQ(world, cposx + 3, cposy + j, cposz + 3, width - 6, 8, 3, "Brutalfly", 1, 1, 4, 5, level);
        }
        j += 8;
        if (level >= 6) {
            this.buildLevelQ(world, cposx + 3, cposy + j, cposz + 3, width - 6, 16, 3, "Vortex", 0, 0, 3, 6, level);
        }
        j += 16;
        for (i = 0; i < platformwidth; ++i) {
            j = height;
            for (k = -(platformwidth / 2); k <= platformwidth / 2; ++k) {
                this.FastSetBlock(world, cposx + i - 20, cposy + j, cposz + k + width / 2, registered("amethyst_block"));
                if (i != 0 && i != platformwidth - 1 && k != -(platformwidth / 2) && k != platformwidth / 2 || i == 0 && k >= -1 && k <= 1) continue;
                this.FastSetBlock(world, cposx + i - 20, cposy + j + 1, cposz + k + width / 2, Blocks.NETHER_BRICK_FENCE);
            }
        }
        for (i = -10; i <= -3; ++i) {
            j = height;
            for (k = -2; k < 3; ++k) {
                if (i == -3 || i == -10) {
                    if (k != -2 && k != 2) {
                        this.FastSetBlock(world, cposx + i, cposy + j + 1, cposz + k + width / 2, Blocks.AIR);
                        continue;
                    }
                    this.FastSetBlock(world, cposx + i, cposy + j + 1, cposz + k + width / 2, Blocks.NETHERRACK);
                    this.FastSetBlock(world, cposx + i, cposy + j + 2, cposz + k + width / 2, Blocks.NETHERRACK);
                    this.FastSetBlock(world, cposx + i, cposy + j + 3, cposz + k + width / 2, (Block)Blocks.FIRE);
                    continue;
                }
                this.FastSetBlock(world, cposx + i, cposy + j, cposz + k + width / 2, registered("amethyst_block"));
                if (k != -2 && k != 2) continue;
                this.FastSetBlock(world, cposx + i, cposy + j + 1, cposz + k + width / 2, Blocks.NETHER_BRICK_FENCE);
            }
        }
        i = -21;
        for (j = height; j >= 0; --j) {
            for (k = -2; k < 3; ++k) {
                for (int t = 0; t < 6; ++t) {
                    this.FastSetBlock(world, cposx + i, cposy + j + t + 1, cposz + k + width / 2, Blocks.AIR);
                }
                if (j == 0) {
                    if (k != -2 && k != 2) {
                        this.FastSetBlock(world, cposx + i, cposy + j + 1, cposz + k + width / 2, Blocks.AIR);
                        continue;
                    }
                    this.FastSetBlock(world, cposx + i, cposy + j + 1, cposz + k + width / 2, Blocks.NETHERRACK);
                    this.FastSetBlock(world, cposx + i, cposy + j + 2, cposz + k + width / 2, Blocks.NETHERRACK);
                    this.FastSetBlock(world, cposx + i, cposy + j + 3, cposz + k + width / 2, (Block)Blocks.FIRE);
                    continue;
                }
                this.FastSetBlock(world, cposx + i, cposy + j, cposz + k + width / 2, registered("amethyst_block"));
                if (k != -2 && k != 2) continue;
                this.FastSetBlock(world, cposx + i, cposy + j + 1, cposz + k + width / 2, Blocks.NETHER_BRICK_FENCE);
            }
            --i;
        }
        if (level >= 6) {
            int span = width * 3;
            for (int tries = 0; tries < 100; ++tries) {
                j = -1;
                i = world.random.nextInt(span);
                k = world.random.nextInt(span);
                if (i >= span / 4 && i <= span * 3 / 4 && k >= span / 4 && k <= span * 3 / 4) continue;
                world.setBlock(cposx + (i -= span / 2) + width / 2, cposy + j, cposz + (k -= span / 2) + width / 2, Blocks.SPAWNER, 0, 2);
                tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + i + width / 2, cposy + j, cposz + k + width / 2);
                if (tileentitymobspawner == null) continue;
                tileentitymobspawner.entity("Large Worm");
            }
        }
    }

    public void buildLevelQ(Plan world, int cposx, int cposy, int cposz, int width, int height, int pw, String critter, int stepside, int stepoff, int holelen, int decor, int level) {
        int k;
        int j;
        int i;
        for (i = -pw; i < width + pw; ++i) {
            for (j = 1; j < height; ++j) {
                for (k = -pw; k < width + pw; ++k) {
                    this.FastSetBlock(world, cposx + i, cposy + j, cposz + k, Blocks.AIR);
                }
            }
        }
        for (i = 0; i < width; ++i) {
            j = 0;
            for (k = 0; k < width; ++k) {
                this.FastSetBlock(world, cposx + i, cposy + j, cposz + k, Blocks.BEDROCK);
            }
        }
        for (i = 0; i < width; ++i) {
            j = height;
            for (k = 0; k < width; ++k) {
                this.FastSetBlock(world, cposx + i, cposy + j, cposz + k, Blocks.BEDROCK);
            }
        }
        for (i = 0; i < width; ++i) {
            for (j = 1; j < height; ++j) {
                k = 0;
                this.FastSetBlock(world, cposx + i, cposy + j, cposz + k, Blocks.BEDROCK);
                k = width - 1;
                this.FastSetBlock(world, cposx + i, cposy + j, cposz + k, Blocks.BEDROCK);
            }
        }
        for (k = 0; k < width; ++k) {
            for (j = 1; j < height; ++j) {
                Block blk = Blocks.BEDROCK;
                if (k == 0 || k == width - 1) {
                    blk = registered("ruby_block");
                }
                i = 0;
                this.FastSetBlock(world, cposx + i, cposy + j, cposz + k, blk);
                i = width - 1;
                this.FastSetBlock(world, cposx + i, cposy + j, cposz + k, blk);
            }
        }
        for (i = -pw; i < width + pw; ++i) {
            for (k = -pw; k < width + pw; ++k) {
                if (i < 0 || k < 0 || i >= width || k >= width) {
                    this.FastSetBlock(world, cposx + i, cposy, cposz + k, Blocks.OBSIDIAN);
                }
                if (i != -pw && k != -pw && i != width + (pw - 1) && k != width + (pw - 1)) continue;
                this.FastSetBlock(world, cposx + i, cposy + 1, cposz + k, Blocks.NETHER_BRICK_FENCE);
            }
        }
        i = -(height / 2);
        i += width / 2;
        for (j = 1; j < height; ++j) {
            if (stepside != 0) {
                k = -1;
                this.FastSetBlock(world, cposx + i, cposy + j, cposz + k, Blocks.OBSIDIAN);
            } else {
                k = width;
                this.FastSetBlock(world, cposx + i, cposy + j, cposz + k, Blocks.OBSIDIAN);
            }
            ++i;
        }
        if (stepoff >= 0) {
            if (stepside == 0) {
                k = -1;
                k -= stepoff;
            } else {
                k = width;
                k += stepoff;
            }
            i = width / 2;
            j = 0;
            for (int l = 0; l < holelen; ++l) {
                this.FastSetBlock(world, cposx + i + l, cposy + j, cposz + k, Blocks.AIR);
            }
        }
        Spawner tileentitymobspawner = null;
        for (j = 0; j < 4; ++j) {
            world.setBlock(cposx - (pw - 1), cposy + j + 1, cposz - (pw - 1), Blocks.SPAWNER, 0, 2);
            tileentitymobspawner = this.getSpawnerTileEntity(world, cposx - (pw - 1), cposy + j + 1, cposz - (pw - 1));
            if (tileentitymobspawner != null) {
                tileentitymobspawner.entity(critter);
            }
            world.setBlock(cposx - (pw - 1), cposy + j + 1, cposz + width + (pw - 2), Blocks.SPAWNER, 0, 2);
            tileentitymobspawner = this.getSpawnerTileEntity(world, cposx - (pw - 1), cposy + j + 1, cposz + width + (pw - 2));
            if (tileentitymobspawner != null) {
                tileentitymobspawner.entity(critter);
            }
            world.setBlock(cposx + width + (pw - 2), cposy + j + 1, cposz - (pw - 1), Blocks.SPAWNER, 0, 2);
            tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width + (pw - 2), cposy + j + 1, cposz - (pw - 1));
            if (tileentitymobspawner != null) {
                tileentitymobspawner.entity(critter);
            }
            world.setBlock(cposx + width + (pw - 2), cposy + j + 1, cposz + width + (pw - 2), Blocks.SPAWNER, 0, 2);
            tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width + (pw - 2), cposy + j + 1, cposz + width + (pw - 2));
            if (tileentitymobspawner == null) continue;
            tileentitymobspawner.entity(critter);
        }
        this.addLevelDecorationsQ(world, cposx, cposy, cposz, width, height, decor, level);
    }

    public void addLevelDecorationsQ(Plan world, int cposx, int cposy, int cposz, int width, int height, int decor, int difficulty) {
        int j;
        Spawner tileentitymobspawner = null;
        int reward = 1;
        String critter = "T. Rex";
        if (decor == 6) {
            this.FastSetBlock(world, cposx, cposy + height, cposz, Blocks.NETHERRACK);
            this.FastSetBlock(world, cposx, cposy + height + 1, cposz, (Block)Blocks.FIRE);
            this.FastSetBlock(world, cposx, cposy + height, cposz + width - 1, Blocks.NETHERRACK);
            this.FastSetBlock(world, cposx, cposy + height + 1, cposz + width - 1, (Block)Blocks.FIRE);
            this.FastSetBlock(world, cposx + width - 1, cposy + height, cposz, Blocks.NETHERRACK);
            this.FastSetBlock(world, cposx + width - 1, cposy + height + 1, cposz, (Block)Blocks.FIRE);
            this.FastSetBlock(world, cposx + width - 1, cposy + height, cposz + width - 1, Blocks.NETHERRACK);
            this.FastSetBlock(world, cposx + width - 1, cposy + height + 1, cposz + width - 1, (Block)Blocks.FIRE);
            this.FastSetBlock(world, cposx + width / 2, cposy + height, cposz + width / 2, Blocks.AIR);
            world.setBlock(cposx + width / 2 - 1, cposy + height + 2, cposz + width / 2, Blocks.SPAWNER, 0, 2);
            tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2 - 1, cposy + height + 2, cposz + width / 2);
            if (tileentitymobspawner != null) {
                tileentitymobspawner.entity("Nightmare");
            }
            world.setBlock(cposx + width / 2 + 1, cposy + height + 2, cposz + width / 2, Blocks.SPAWNER, 0, 2);
            tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2 + 1, cposy + height + 2, cposz + width / 2);
            if (tileentitymobspawner != null) {
                tileentitymobspawner.entity("Nightmare");
            }
            world.setBlock(cposx + width / 2, cposy + height + 2, cposz + width / 2 - 1, Blocks.SPAWNER, 0, 2);
            tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2, cposy + height + 2, cposz + width / 2 - 1);
            if (tileentitymobspawner != null) {
                tileentitymobspawner.entity("Nightmare");
            }
            world.setBlock(cposx + width / 2, cposy + height + 2, cposz + width / 2 + 1, Blocks.SPAWNER, 0, 2);
            tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2, cposy + height + 2, cposz + width / 2 + 1);
            if (tileentitymobspawner != null) {
                tileentitymobspawner.entity("Nightmare");
            }
            for (int i = 1; i < width - 1; ++i) {
                for (j = 1; j < 5; ++j) {
                    for (int k = 1; k < width - 1; ++k) {
                        this.FastSetBlock(world, cposx + i, cposy + j, cposz + k, Blocks.DIRT);
                    }
                }
            }
            world.setBlock(cposx + width / 2, cposy + 2, cposz + width / 2, Blocks.SPAWNER, 0, 2);
            tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2, cposy + 2, cposz + width / 2);
            if (tileentitymobspawner != null) {
                tileentitymobspawner.entity("Large Worm");
            }
            world.setBlock(cposx + width / 2, cposy + 3, cposz + width / 2, Blocks.SPAWNER, 0, 2);
            tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2, cposy + 3, cposz + width / 2);
            if (tileentitymobspawner != null) {
                tileentitymobspawner.entity("Large Worm");
            }
            world.setBlock(cposx + width / 2, cposy + 4, cposz + width / 2, Blocks.SPAWNER, 0, 2);
            tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2, cposy + 4, cposz + width / 2);
            if (tileentitymobspawner != null) {
                tileentitymobspawner.entity("Large Worm");
            }
            for (j = 0; j < 10; ++j) {
                this.FastSetBlock(world, cposx + 1, cposy + j, cposz + 1, Blocks.AIR);
            }
            this.fill_chests(world, cposx, cposy + 4, cposz, width, height, decor, reward);
        }
        if (decor == 5) {
            if (difficulty == 5) {
                critter = "T. Rex";
                reward = 1;
            }
            if (difficulty == 6) {
                critter = "Nastysaurus";
                reward = 2;
            }
            world.setBlock(cposx + width / 2, cposy + 2, cposz + width / 2, Blocks.SPAWNER, 0, 2);
            tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2, cposy + 2, cposz + width / 2);
            if (tileentitymobspawner != null) {
                tileentitymobspawner.entity(critter);
            }
            world.setBlock(cposx + width / 2, cposy + 3, cposz + width / 2, Blocks.SPAWNER, 0, 2);
            tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2, cposy + 3, cposz + width / 2);
            if (tileentitymobspawner != null) {
                tileentitymobspawner.entity(critter);
            }
            for (j = 1; j < 5; ++j) {
                this.FastSetBlock(world, cposx + width / 2 - 1, cposy + j, cposz + width / 2, Blocks.BEDROCK);
                this.FastSetBlock(world, cposx + width / 2 + 1, cposy + j, cposz + width / 2, Blocks.BEDROCK);
                this.FastSetBlock(world, cposx + width / 2, cposy + j, cposz + width / 2 - 1, Blocks.BEDROCK);
                this.FastSetBlock(world, cposx + width / 2, cposy + j, cposz + width / 2 + 1, Blocks.BEDROCK);
            }
            this.FastSetBlock(world, cposx + width - 2, cposy, cposz + width - 2, Blocks.AIR);
            this.FastSetBlock(world, cposx + 1, cposy + height, cposz + 1, Blocks.AIR);
            this.fill_chests(world, cposx, cposy, cposz, width, height, decor, reward);
        }
        if (decor == 4) {
            if (difficulty == 4) {
                critter = "T. Rex";
                reward = 1;
            }
            if (difficulty == 5) {
                critter = "Nastysaurus";
                reward = 2;
            }
            if (difficulty == 6) {
                critter = "Basilisk";
                reward = 3;
            }
            world.setBlock(cposx + width / 2, cposy + 2, cposz + width / 2, Blocks.SPAWNER, 0, 2);
            tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2, cposy + 2, cposz + width / 2);
            if (tileentitymobspawner != null) {
                tileentitymobspawner.entity(critter);
            }
            world.setBlock(cposx + width / 2, cposy + 3, cposz + width / 2, Blocks.SPAWNER, 0, 2);
            tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2, cposy + 3, cposz + width / 2);
            if (tileentitymobspawner != null) {
                tileentitymobspawner.entity(critter);
            }
            for (j = 1; j < 5; ++j) {
                this.FastSetBlock(world, cposx + width / 2 - 1, cposy + j, cposz + width / 2, Blocks.BEDROCK);
                this.FastSetBlock(world, cposx + width / 2 + 1, cposy + j, cposz + width / 2, Blocks.BEDROCK);
                this.FastSetBlock(world, cposx + width / 2, cposy + j, cposz + width / 2 - 1, Blocks.BEDROCK);
                this.FastSetBlock(world, cposx + width / 2, cposy + j, cposz + width / 2 + 1, Blocks.BEDROCK);
            }
            this.FastSetBlock(world, cposx + 1, cposy, cposz + 1, Blocks.AIR);
            this.FastSetBlock(world, cposx + width - 2, cposy + height, cposz + width - 2, Blocks.AIR);
            this.fill_chests(world, cposx, cposy, cposz, width, height, decor, reward);
        }
        if (decor == 3) {
            if (difficulty == 3) {
                critter = "T. Rex";
                reward = 1;
            }
            if (difficulty == 4) {
                critter = "Nastysaurus";
                reward = 2;
            }
            if (difficulty == 5) {
                critter = "Basilisk";
                reward = 3;
            }
            if (difficulty == 6) {
                critter = "Hercules Beetle";
                reward = 4;
            }
            world.setBlock(cposx + width / 2, cposy + 2, cposz + width / 2, Blocks.SPAWNER, 0, 2);
            tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2, cposy + 2, cposz + width / 2);
            if (tileentitymobspawner != null) {
                tileentitymobspawner.entity(critter);
            }
            world.setBlock(cposx + width / 2, cposy + 3, cposz + width / 2, Blocks.SPAWNER, 0, 2);
            tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2, cposy + 3, cposz + width / 2);
            if (tileentitymobspawner != null) {
                tileentitymobspawner.entity(critter);
            }
            for (j = 1; j < 5; ++j) {
                this.FastSetBlock(world, cposx + width / 2 - 1, cposy + j, cposz + width / 2, Blocks.BEDROCK);
                this.FastSetBlock(world, cposx + width / 2 + 1, cposy + j, cposz + width / 2, Blocks.BEDROCK);
                this.FastSetBlock(world, cposx + width / 2, cposy + j, cposz + width / 2 - 1, Blocks.BEDROCK);
                this.FastSetBlock(world, cposx + width / 2, cposy + j, cposz + width / 2 + 1, Blocks.BEDROCK);
            }
            this.FastSetBlock(world, cposx + width - 2, cposy, cposz + width - 2, Blocks.AIR);
            this.FastSetBlock(world, cposx + 1, cposy + height, cposz + 1, Blocks.AIR);
            this.fill_chests(world, cposx, cposy, cposz, width, height, decor, reward);
        }
        if (decor == 2) {
            if (difficulty == 2) {
                critter = "T. Rex";
                reward = 1;
            }
            if (difficulty == 3) {
                critter = "Nastysaurus";
                reward = 2;
            }
            if (difficulty == 4) {
                critter = "Basilisk";
                reward = 3;
            }
            if (difficulty == 5) {
                critter = "Hercules Beetle";
                reward = 4;
            }
            if (difficulty == 6) {
                critter = "Jumpy Bug";
                reward = 5;
            }
            world.setBlock(cposx + width / 2, cposy + 2, cposz + width / 2, Blocks.SPAWNER, 0, 2);
            tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2, cposy + 2, cposz + width / 2);
            if (tileentitymobspawner != null) {
                tileentitymobspawner.entity(critter);
            }
            world.setBlock(cposx + width / 2, cposy + 3, cposz + width / 2, Blocks.SPAWNER, 0, 2);
            tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2, cposy + 3, cposz + width / 2);
            if (tileentitymobspawner != null) {
                tileentitymobspawner.entity(critter);
            }
            for (j = 1; j < 5; ++j) {
                this.FastSetBlock(world, cposx + width / 2 - 1, cposy + j, cposz + width / 2, Blocks.BEDROCK);
                this.FastSetBlock(world, cposx + width / 2 + 1, cposy + j, cposz + width / 2, Blocks.BEDROCK);
                this.FastSetBlock(world, cposx + width / 2, cposy + j, cposz + width / 2 - 1, Blocks.BEDROCK);
                this.FastSetBlock(world, cposx + width / 2, cposy + j, cposz + width / 2 + 1, Blocks.BEDROCK);
            }
            this.FastSetBlock(world, cposx + 1, cposy, cposz + 1, Blocks.AIR);
            this.FastSetBlock(world, cposx + width - 2, cposy + height, cposz + width - 2, Blocks.AIR);
            this.fill_chests(world, cposx, cposy, cposz, width, height, decor, reward);
        }
        if (decor == 1) {
            if (difficulty == 1) {
                critter = "T. Rex";
            }
            if (difficulty == 2) {
                critter = "Nastysaurus";
            }
            if (difficulty == 3) {
                critter = "Basilisk";
            }
            if (difficulty == 4) {
                critter = "Hercules Beetle";
            }
            if (difficulty == 5) {
                critter = "Jumpy Bug";
            }
            if (difficulty == 6) {
                critter = "CaterKiller";
            }
            reward = difficulty;
            world.setBlock(cposx + width / 2, cposy + 2, cposz + width / 2, Blocks.SPAWNER, 0, 2);
            tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2, cposy + 2, cposz + width / 2);
            if (tileentitymobspawner != null) {
                tileentitymobspawner.entity(critter);
            }
            world.setBlock(cposx + width / 2, cposy + 3, cposz + width / 2, Blocks.SPAWNER, 0, 2);
            tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2, cposy + 3, cposz + width / 2);
            if (tileentitymobspawner != null) {
                tileentitymobspawner.entity(critter);
            }
            for (j = 1; j < 5; ++j) {
                this.FastSetBlock(world, cposx + width / 2 - 1, cposy + j, cposz + width / 2, Blocks.BEDROCK);
                this.FastSetBlock(world, cposx + width / 2 + 1, cposy + j, cposz + width / 2, Blocks.BEDROCK);
                this.FastSetBlock(world, cposx + width / 2, cposy + j, cposz + width / 2 - 1, Blocks.BEDROCK);
                this.FastSetBlock(world, cposx + width / 2, cposy + j, cposz + width / 2 + 1, Blocks.BEDROCK);
            }
            this.FastSetBlock(world, cposx + width / 2 - 1, cposy + 1, cposz + width / 2 - 1, registered("teleport_block"));
            this.FastSetBlock(world, cposx + width / 2 + 1, cposy + 1, cposz + width / 2 + 1, registered("teleport_block"));
            this.FastSetBlock(world, cposx + width / 2 + 1, cposy + 1, cposz + width / 2 - 1, registered("teleport_block"));
            this.FastSetBlock(world, cposx + width / 2 - 1, cposy + 1, cposz + width / 2 + 1, registered("teleport_block"));
            this.FastSetBlock(world, cposx + 1, cposy + height, cposz + 1, Blocks.AIR);
            this.fill_chestsQ(world, cposx, cposy, cposz, width, height, decor, reward);
        }
    }

    private void fill_chestsQ(Plan world, int x, int y, int z, int width, int height, int decor, int reward) {
        fill_chests(world,x,y,z,width,height,decor,reward);
        if(reward==6) world.loot(new BlockPos(x+1,y+1,z+width/2),"queen_final_0");
        if(reward==6) world.loot(new BlockPos(x+width-2,y+1,z+width/2),"queen_final_1");
        if(reward==6) world.loot(new BlockPos(x+width/2,y+1,z+1),"queen_final_2");
    }

    public void makeEnormousCastle(Plan world, int cposx, int cposy, int cposz) {
        int k;
        int j;
        int i;
        int width = 28;
        int height = 16;
        int platformwidth = 11;
        int level = 0;
        if (false) {
            return;
        }
        level = 1 + world.random.nextInt(6);
        if (level <= 3 && world.random.nextInt(3) != 1) {
            level += 3;
        }
        for (i = -20; i < width + 4; ++i) {
            for (j = 1; j < height + 10; ++j) {
                for (k = -4; k < width + 4; ++k) {
                    this.FastSetBlock(world, cposx + i, cposy + j, cposz + k, Blocks.AIR);
                }
            }
        }
        for (i = 0; i < width; ++i) {
            j = 0;
            for (k = 0; k < width; ++k) {
                this.FastSetBlock(world, cposx + i, cposy + j, cposz + k, Blocks.STONE);
            }
        }
        for (i = 0; i < width; ++i) {
            j = height;
            for (k = 0; k < width; ++k) {
                this.FastSetBlock(world, cposx + i, cposy + j, cposz + k, Blocks.BEDROCK);
            }
        }
        for (i = 0; i < width; ++i) {
            for (j = 1; j < height; ++j) {
                k = 0;
                this.FastSetBlock(world, cposx + i, cposy + j, cposz + k, Blocks.IRON_BARS);
                k = width - 1;
                this.FastSetBlock(world, cposx + i, cposy + j, cposz + k, Blocks.IRON_BARS);
            }
        }
        for (k = 0; k < width; ++k) {
            for (j = 1; j < height; ++j) {
                i = 0;
                this.FastSetBlock(world, cposx + i, cposy + j, cposz + k, Blocks.IRON_BARS);
                i = width - 1;
                this.FastSetBlock(world, cposx + i, cposy + j, cposz + k, Blocks.IRON_BARS);
            }
        }
        world.setBlock(cposx + 1, cposy + 1, cposz + 1, registered("extreme_torch"));
        world.setBlock(cposx + 1, cposy + 1, cposz + width - 2, registered("extreme_torch"));
        world.setBlock(cposx + width - 2, cposy + 1, cposz + 1, registered("extreme_torch"));
        world.setBlock(cposx + width - 2, cposy + 1, cposz + width - 2, registered("extreme_torch"));
        for (i = -4; i < width + 4; ++i) {
            for (k = -4; k < width + 4; ++k) {
                if (i < 0 || k < 0 || i >= width || k >= width) {
                    this.FastSetBlock(world, cposx + i, cposy, cposz + k, Blocks.STONE);
                }
                if (i != -4 && k != -4 && i != width + 3 && k != width + 3) continue;
                this.FastSetBlock(world, cposx + i, cposy + 1, cposz + k, Blocks.NETHER_BRICK_FENCE);
            }
        }
        Spawner tileentitymobspawner = null;
        for (j = 0; j < 4; ++j) {
            world.setBlock(cposx - 3, cposy + 1 + j, cposz - 3, Blocks.SPAWNER, 0, 2);
            tileentitymobspawner = this.getSpawnerTileEntity(world, cposx - 3, cposy + 1 + j, cposz - 3);
            if (tileentitymobspawner != null) {
                tileentitymobspawner.entity("Terrible Terror");
            }
            world.setBlock(cposx - 3, cposy + 1 + j, cposz + width + 2, Blocks.SPAWNER, 0, 2);
            tileentitymobspawner = this.getSpawnerTileEntity(world, cposx - 3, cposy + 1 + j, cposz + width + 2);
            if (tileentitymobspawner != null) {
                tileentitymobspawner.entity("Terrible Terror");
            }
            world.setBlock(cposx + width + 2, cposy + 1 + j, cposz - 3, Blocks.SPAWNER, 0, 2);
            tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width + 2, cposy + 1 + j, cposz - 3);
            if (tileentitymobspawner != null) {
                tileentitymobspawner.entity("Terrible Terror");
            }
            world.setBlock(cposx + width + 2, cposy + 1 + j, cposz + width + 2, Blocks.SPAWNER, 0, 2);
            tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width + 2, cposy + 1 + j, cposz + width + 2);
            if (tileentitymobspawner == null) continue;
            tileentitymobspawner.entity("Terrible Terror");
        }
        world.setBlock(cposx + width / 2, cposy + 2, cposz + width / 2, Blocks.SPAWNER, 0, 2);
        tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2, cposy + 2, cposz + width / 2);
        if (tileentitymobspawner != null) {
            tileentitymobspawner.entity("Emperor Scorpion");
        }
        world.setBlock(cposx + width / 2, cposy + 3, cposz + width / 2, Blocks.SPAWNER, 0, 2);
        tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2, cposy + 3, cposz + width / 2);
        if (tileentitymobspawner != null) {
            tileentitymobspawner.entity("Emperor Scorpion");
        }
        world.setBlock(cposx + width / 2, cposy + 4, cposz + width / 2, Blocks.SPAWNER, 0, 2);
        tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2, cposy + 4, cposz + width / 2);
        if (tileentitymobspawner != null) {
            tileentitymobspawner.entity("Emperor Scorpion");
        }
        j = height;
        this.buildLevel(world, cposx + 1, cposy + j, cposz + 1, width - 2, 10, 4, "Cloud Shark", 1, -1, 5, 1, level);
        j += 10;
        if (level >= 2) {
            this.buildLevel(world, cposx + 1, cposy + j, cposz + 1, width - 2, 10, 4, "Lurking Terror", 0, 0, 4, 2, level);
        }
        j += 10;
        if (level >= 3) {
            this.buildLevel(world, cposx + 2, cposy + j, cposz + 2, width - 4, 9, 4, "Rotator", 1, 1, 4, 3, level);
        }
        j += 9;
        if (level >= 4) {
            this.buildLevel(world, cposx + 2, cposy + j, cposz + 2, width - 4, 9, 3, "Bee", 0, 0, 4, 4, level);
        }
        j += 9;
        if (level >= 5) {
            this.buildLevel(world, cposx + 3, cposy + j, cposz + 3, width - 6, 8, 3, "Mantis", 1, 1, 4, 5, level);
        }
        j += 8;
        if (level >= 6) {
            this.buildLevel(world, cposx + 3, cposy + j, cposz + 3, width - 6, 16, 3, "Mothra", 0, 0, 3, 6, level);
        }
        j += 16;
        for (i = 0; i < platformwidth; ++i) {
            j = height;
            for (k = -(platformwidth / 2); k <= platformwidth / 2; ++k) {
                this.FastSetBlock(world, cposx + i - 20, cposy + j, cposz + k + width / 2, Blocks.QUARTZ_BLOCK);
                if (i != 0 && i != platformwidth - 1 && k != -(platformwidth / 2) && k != platformwidth / 2 || i == 0 && k >= -1 && k <= 1) continue;
                this.FastSetBlock(world, cposx + i - 20, cposy + j + 1, cposz + k + width / 2, Blocks.NETHER_BRICK_FENCE);
            }
        }
        for (i = -10; i <= -3; ++i) {
            j = height;
            for (k = -2; k < 3; ++k) {
                if (i == -3 || i == -10) {
                    if (k != -2 && k != 2) {
                        this.FastSetBlock(world, cposx + i, cposy + j + 1, cposz + k + width / 2, Blocks.AIR);
                        continue;
                    }
                    this.FastSetBlock(world, cposx + i, cposy + j + 1, cposz + k + width / 2, Blocks.NETHERRACK);
                    this.FastSetBlock(world, cposx + i, cposy + j + 2, cposz + k + width / 2, Blocks.NETHERRACK);
                    this.FastSetBlock(world, cposx + i, cposy + j + 3, cposz + k + width / 2, (Block)Blocks.FIRE);
                    continue;
                }
                this.FastSetBlock(world, cposx + i, cposy + j, cposz + k + width / 2, Blocks.QUARTZ_BLOCK);
                if (k != -2 && k != 2) continue;
                this.FastSetBlock(world, cposx + i, cposy + j + 1, cposz + k + width / 2, Blocks.NETHER_BRICK_FENCE);
            }
        }
        i = -21;
        for (j = height; j >= 0; --j) {
            for (k = -2; k < 3; ++k) {
                for (int t = 0; t < 6; ++t) {
                    this.FastSetBlock(world, cposx + i, cposy + j + t + 1, cposz + k + width / 2, Blocks.AIR);
                }
                if (j == 0) {
                    if (k != -2 && k != 2) {
                        this.FastSetBlock(world, cposx + i, cposy + j + 1, cposz + k + width / 2, Blocks.AIR);
                        continue;
                    }
                    this.FastSetBlock(world, cposx + i, cposy + j + 1, cposz + k + width / 2, Blocks.NETHERRACK);
                    this.FastSetBlock(world, cposx + i, cposy + j + 2, cposz + k + width / 2, Blocks.NETHERRACK);
                    this.FastSetBlock(world, cposx + i, cposy + j + 3, cposz + k + width / 2, (Block)Blocks.FIRE);
                    continue;
                }
                this.FastSetBlock(world, cposx + i, cposy + j, cposz + k + width / 2, Blocks.QUARTZ_BLOCK);
                if (k != -2 && k != 2) continue;
                this.FastSetBlock(world, cposx + i, cposy + j + 1, cposz + k + width / 2, Blocks.NETHER_BRICK_FENCE);
            }
            --i;
        }
        if (level >= 6) {
            int span = width * 3;
            for (int tries = 0; tries < 100; ++tries) {
                j = -1;
                i = world.random.nextInt(span);
                k = world.random.nextInt(span);
                if (i >= span / 4 && i <= span * 3 / 4 && k >= span / 4 && k <= span * 3 / 4) continue;
                world.setBlock(cposx + (i -= span / 2) + width / 2, cposy + j, cposz + (k -= span / 2) + width / 2, Blocks.SPAWNER, 0, 2);
                tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + i + width / 2, cposy + j, cposz + k + width / 2);
                if (tileentitymobspawner == null) continue;
                tileentitymobspawner.entity("Large Worm");
            }
        }
    }

    public void buildLevel(Plan world, int cposx, int cposy, int cposz, int width, int height, int pw, String critter, int stepside, int stepoff, int holelen, int decor, int level) {
        int k;
        int j;
        int i;
        for (i = -pw; i < width + pw; ++i) {
            for (j = 1; j < height; ++j) {
                for (k = -pw; k < width + pw; ++k) {
                    this.FastSetBlock(world, cposx + i, cposy + j, cposz + k, Blocks.AIR);
                }
            }
        }
        for (i = 0; i < width; ++i) {
            j = 0;
            for (k = 0; k < width; ++k) {
                this.FastSetBlock(world, cposx + i, cposy + j, cposz + k, Blocks.BEDROCK);
            }
        }
        for (i = 0; i < width; ++i) {
            j = height;
            for (k = 0; k < width; ++k) {
                this.FastSetBlock(world, cposx + i, cposy + j, cposz + k, Blocks.BEDROCK);
            }
        }
        for (i = 0; i < width; ++i) {
            for (j = 1; j < height; ++j) {
                k = 0;
                this.FastSetBlock(world, cposx + i, cposy + j, cposz + k, Blocks.BEDROCK);
                k = width - 1;
                this.FastSetBlock(world, cposx + i, cposy + j, cposz + k, Blocks.BEDROCK);
            }
        }
        for (k = 0; k < width; ++k) {
            for (j = 1; j < height; ++j) {
                Block blk = Blocks.BEDROCK;
                if (k == 0 || k == width - 1) {
                    blk = Blocks.GOLD_BLOCK;
                }
                i = 0;
                this.FastSetBlock(world, cposx + i, cposy + j, cposz + k, blk);
                i = width - 1;
                this.FastSetBlock(world, cposx + i, cposy + j, cposz + k, blk);
            }
        }
        for (i = -pw; i < width + pw; ++i) {
            for (k = -pw; k < width + pw; ++k) {
                if (i < 0 || k < 0 || i >= width || k >= width) {
                    this.FastSetBlock(world, cposx + i, cposy, cposz + k, Blocks.STONE);
                }
                if (i != -pw && k != -pw && i != width + (pw - 1) && k != width + (pw - 1)) continue;
                this.FastSetBlock(world, cposx + i, cposy + 1, cposz + k, Blocks.NETHER_BRICK_FENCE);
            }
        }
        i = -(height / 2);
        i += width / 2;
        for (j = 1; j < height; ++j) {
            if (stepside != 0) {
                k = -1;
                this.FastSetBlock(world, cposx + i, cposy + j, cposz + k, Blocks.STONE);
            } else {
                k = width;
                this.FastSetBlock(world, cposx + i, cposy + j, cposz + k, Blocks.STONE);
            }
            ++i;
        }
        if (stepoff >= 0) {
            if (stepside == 0) {
                k = -1;
                k -= stepoff;
            } else {
                k = width;
                k += stepoff;
            }
            i = width / 2;
            j = 0;
            for (int l = 0; l < holelen; ++l) {
                this.FastSetBlock(world, cposx + i + l, cposy + j, cposz + k, Blocks.AIR);
            }
        }
        Spawner tileentitymobspawner = null;
        for (j = 0; j < 4; ++j) {
            world.setBlock(cposx - (pw - 1), cposy + j + 1, cposz - (pw - 1), Blocks.SPAWNER, 0, 2);
            tileentitymobspawner = this.getSpawnerTileEntity(world, cposx - (pw - 1), cposy + j + 1, cposz - (pw - 1));
            if (tileentitymobspawner != null) {
                tileentitymobspawner.entity(critter);
            }
            world.setBlock(cposx - (pw - 1), cposy + j + 1, cposz + width + (pw - 2), Blocks.SPAWNER, 0, 2);
            tileentitymobspawner = this.getSpawnerTileEntity(world, cposx - (pw - 1), cposy + j + 1, cposz + width + (pw - 2));
            if (tileentitymobspawner != null) {
                tileentitymobspawner.entity(critter);
            }
            world.setBlock(cposx + width + (pw - 2), cposy + j + 1, cposz - (pw - 1), Blocks.SPAWNER, 0, 2);
            tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width + (pw - 2), cposy + j + 1, cposz - (pw - 1));
            if (tileentitymobspawner != null) {
                tileentitymobspawner.entity(critter);
            }
            world.setBlock(cposx + width + (pw - 2), cposy + j + 1, cposz + width + (pw - 2), Blocks.SPAWNER, 0, 2);
            tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width + (pw - 2), cposy + j + 1, cposz + width + (pw - 2));
            if (tileentitymobspawner == null) continue;
            tileentitymobspawner.entity(critter);
        }
        this.addLevelDecorations(world, cposx, cposy, cposz, width, height, decor, level);
    }

    public void addLevelDecorations(Plan world, int cposx, int cposy, int cposz, int width, int height, int decor, int difficulty) {
        int j;
        Spawner tileentitymobspawner = null;
        int reward = 1;
        String critter = "Alosaurus";
        if (decor == 6) {
            this.FastSetBlock(world, cposx, cposy + height, cposz, Blocks.NETHERRACK);
            this.FastSetBlock(world, cposx, cposy + height + 1, cposz, (Block)Blocks.FIRE);
            this.FastSetBlock(world, cposx, cposy + height, cposz + width - 1, Blocks.NETHERRACK);
            this.FastSetBlock(world, cposx, cposy + height + 1, cposz + width - 1, (Block)Blocks.FIRE);
            this.FastSetBlock(world, cposx + width - 1, cposy + height, cposz, Blocks.NETHERRACK);
            this.FastSetBlock(world, cposx + width - 1, cposy + height + 1, cposz, (Block)Blocks.FIRE);
            this.FastSetBlock(world, cposx + width - 1, cposy + height, cposz + width - 1, Blocks.NETHERRACK);
            this.FastSetBlock(world, cposx + width - 1, cposy + height + 1, cposz + width - 1, (Block)Blocks.FIRE);
            this.FastSetBlock(world, cposx + width / 2, cposy + height, cposz + width / 2, Blocks.AIR);
            world.setBlock(cposx + width / 2 - 1, cposy + height + 2, cposz + width / 2, Blocks.SPAWNER, 0, 2);
            tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2 - 1, cposy + height + 2, cposz + width / 2);
            if (tileentitymobspawner != null) {
                tileentitymobspawner.entity("Nightmare");
            }
            world.setBlock(cposx + width / 2 + 1, cposy + height + 2, cposz + width / 2, Blocks.SPAWNER, 0, 2);
            tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2 + 1, cposy + height + 2, cposz + width / 2);
            if (tileentitymobspawner != null) {
                tileentitymobspawner.entity("Nightmare");
            }
            world.setBlock(cposx + width / 2, cposy + height + 2, cposz + width / 2 - 1, Blocks.SPAWNER, 0, 2);
            tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2, cposy + height + 2, cposz + width / 2 - 1);
            if (tileentitymobspawner != null) {
                tileentitymobspawner.entity("Nightmare");
            }
            world.setBlock(cposx + width / 2, cposy + height + 2, cposz + width / 2 + 1, Blocks.SPAWNER, 0, 2);
            tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2, cposy + height + 2, cposz + width / 2 + 1);
            if (tileentitymobspawner != null) {
                tileentitymobspawner.entity("Nightmare");
            }
            for (int i = 1; i < width - 1; ++i) {
                for (j = 1; j < 5; ++j) {
                    for (int k = 1; k < width - 1; ++k) {
                        this.FastSetBlock(world, cposx + i, cposy + j, cposz + k, Blocks.DIRT);
                    }
                }
            }
            world.setBlock(cposx + width / 2, cposy + 2, cposz + width / 2, Blocks.SPAWNER, 0, 2);
            tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2, cposy + 2, cposz + width / 2);
            if (tileentitymobspawner != null) {
                tileentitymobspawner.entity("Large Worm");
            }
            world.setBlock(cposx + width / 2, cposy + 3, cposz + width / 2, Blocks.SPAWNER, 0, 2);
            tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2, cposy + 3, cposz + width / 2);
            if (tileentitymobspawner != null) {
                tileentitymobspawner.entity("Large Worm");
            }
            world.setBlock(cposx + width / 2, cposy + 4, cposz + width / 2, Blocks.SPAWNER, 0, 2);
            tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2, cposy + 4, cposz + width / 2);
            if (tileentitymobspawner != null) {
                tileentitymobspawner.entity("Large Worm");
            }
            for (j = 0; j < 10; ++j) {
                this.FastSetBlock(world, cposx + 1, cposy + j, cposz + 1, Blocks.AIR);
            }
            this.fill_chests(world, cposx, cposy + 4, cposz, width, height, decor, reward);
        }
        if (decor == 5) {
            if (difficulty == 5) {
                critter = "Alosaurus";
                reward = 1;
            }
            if (difficulty == 6) {
                critter = "T. Rex";
                reward = 2;
            }
            world.setBlock(cposx + width / 2, cposy + 2, cposz + width / 2, Blocks.SPAWNER, 0, 2);
            tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2, cposy + 2, cposz + width / 2);
            if (tileentitymobspawner != null) {
                tileentitymobspawner.entity(critter);
            }
            world.setBlock(cposx + width / 2, cposy + 3, cposz + width / 2, Blocks.SPAWNER, 0, 2);
            tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2, cposy + 3, cposz + width / 2);
            if (tileentitymobspawner != null) {
                tileentitymobspawner.entity(critter);
            }
            for (j = 1; j < 5; ++j) {
                this.FastSetBlock(world, cposx + width / 2 - 1, cposy + j, cposz + width / 2, Blocks.BEDROCK);
                this.FastSetBlock(world, cposx + width / 2 + 1, cposy + j, cposz + width / 2, Blocks.BEDROCK);
                this.FastSetBlock(world, cposx + width / 2, cposy + j, cposz + width / 2 - 1, Blocks.BEDROCK);
                this.FastSetBlock(world, cposx + width / 2, cposy + j, cposz + width / 2 + 1, Blocks.BEDROCK);
            }
            this.FastSetBlock(world, cposx + width - 2, cposy, cposz + width - 2, Blocks.AIR);
            this.FastSetBlock(world, cposx + 1, cposy + height, cposz + 1, Blocks.AIR);
            this.fill_chests(world, cposx, cposy, cposz, width, height, decor, reward);
        }
        if (decor == 4) {
            if (difficulty == 4) {
                critter = "Alosaurus";
                reward = 1;
            }
            if (difficulty == 5) {
                critter = "T. Rex";
                reward = 2;
            }
            if (difficulty == 6) {
                critter = "Basilisk";
                reward = 3;
            }
            world.setBlock(cposx + width / 2, cposy + 2, cposz + width / 2, Blocks.SPAWNER, 0, 2);
            tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2, cposy + 2, cposz + width / 2);
            if (tileentitymobspawner != null) {
                tileentitymobspawner.entity(critter);
            }
            world.setBlock(cposx + width / 2, cposy + 3, cposz + width / 2, Blocks.SPAWNER, 0, 2);
            tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2, cposy + 3, cposz + width / 2);
            if (tileentitymobspawner != null) {
                tileentitymobspawner.entity(critter);
            }
            for (j = 1; j < 5; ++j) {
                this.FastSetBlock(world, cposx + width / 2 - 1, cposy + j, cposz + width / 2, Blocks.BEDROCK);
                this.FastSetBlock(world, cposx + width / 2 + 1, cposy + j, cposz + width / 2, Blocks.BEDROCK);
                this.FastSetBlock(world, cposx + width / 2, cposy + j, cposz + width / 2 - 1, Blocks.BEDROCK);
                this.FastSetBlock(world, cposx + width / 2, cposy + j, cposz + width / 2 + 1, Blocks.BEDROCK);
            }
            this.FastSetBlock(world, cposx + 1, cposy, cposz + 1, Blocks.AIR);
            this.FastSetBlock(world, cposx + width - 2, cposy + height, cposz + width - 2, Blocks.AIR);
            this.fill_chests(world, cposx, cposy, cposz, width, height, decor, reward);
        }
        if (decor == 3) {
            if (difficulty == 3) {
                critter = "Alosaurus";
                reward = 1;
            }
            if (difficulty == 4) {
                critter = "T. Rex";
                reward = 2;
            }
            if (difficulty == 5) {
                critter = "Basilisk";
                reward = 3;
            }
            if (difficulty == 6) {
                critter = "Hercules Beetle";
                reward = 4;
            }
            world.setBlock(cposx + width / 2, cposy + 2, cposz + width / 2, Blocks.SPAWNER, 0, 2);
            tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2, cposy + 2, cposz + width / 2);
            if (tileentitymobspawner != null) {
                tileentitymobspawner.entity(critter);
            }
            world.setBlock(cposx + width / 2, cposy + 3, cposz + width / 2, Blocks.SPAWNER, 0, 2);
            tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2, cposy + 3, cposz + width / 2);
            if (tileentitymobspawner != null) {
                tileentitymobspawner.entity(critter);
            }
            for (j = 1; j < 5; ++j) {
                this.FastSetBlock(world, cposx + width / 2 - 1, cposy + j, cposz + width / 2, Blocks.BEDROCK);
                this.FastSetBlock(world, cposx + width / 2 + 1, cposy + j, cposz + width / 2, Blocks.BEDROCK);
                this.FastSetBlock(world, cposx + width / 2, cposy + j, cposz + width / 2 - 1, Blocks.BEDROCK);
                this.FastSetBlock(world, cposx + width / 2, cposy + j, cposz + width / 2 + 1, Blocks.BEDROCK);
            }
            this.FastSetBlock(world, cposx + width - 2, cposy, cposz + width - 2, Blocks.AIR);
            this.FastSetBlock(world, cposx + 1, cposy + height, cposz + 1, Blocks.AIR);
            this.fill_chests(world, cposx, cposy, cposz, width, height, decor, reward);
        }
        if (decor == 2) {
            if (difficulty == 2) {
                critter = "Alosaurus";
                reward = 1;
            }
            if (difficulty == 3) {
                critter = "T. Rex";
                reward = 2;
            }
            if (difficulty == 4) {
                critter = "Basilisk";
                reward = 3;
            }
            if (difficulty == 5) {
                critter = "Hercules Beetle";
                reward = 4;
            }
            if (difficulty == 6) {
                critter = "Jumpy Bug";
                reward = 5;
            }
            world.setBlock(cposx + width / 2, cposy + 2, cposz + width / 2, Blocks.SPAWNER, 0, 2);
            tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2, cposy + 2, cposz + width / 2);
            if (tileentitymobspawner != null) {
                tileentitymobspawner.entity(critter);
            }
            world.setBlock(cposx + width / 2, cposy + 3, cposz + width / 2, Blocks.SPAWNER, 0, 2);
            tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2, cposy + 3, cposz + width / 2);
            if (tileentitymobspawner != null) {
                tileentitymobspawner.entity(critter);
            }
            for (j = 1; j < 5; ++j) {
                this.FastSetBlock(world, cposx + width / 2 - 1, cposy + j, cposz + width / 2, Blocks.BEDROCK);
                this.FastSetBlock(world, cposx + width / 2 + 1, cposy + j, cposz + width / 2, Blocks.BEDROCK);
                this.FastSetBlock(world, cposx + width / 2, cposy + j, cposz + width / 2 - 1, Blocks.BEDROCK);
                this.FastSetBlock(world, cposx + width / 2, cposy + j, cposz + width / 2 + 1, Blocks.BEDROCK);
            }
            this.FastSetBlock(world, cposx + 1, cposy, cposz + 1, Blocks.AIR);
            this.FastSetBlock(world, cposx + width - 2, cposy + height, cposz + width - 2, Blocks.AIR);
            this.fill_chests(world, cposx, cposy, cposz, width, height, decor, reward);
        }
        if (decor == 1) {
            if (difficulty == 1) {
                critter = "Alosaurus";
            }
            if (difficulty == 2) {
                critter = "T. Rex";
            }
            if (difficulty == 3) {
                critter = "Basilisk";
            }
            if (difficulty == 4) {
                critter = "Hercules Beetle";
            }
            if (difficulty == 5) {
                critter = "Jumpy Bug";
            }
            if (difficulty == 6) {
                critter = "Hammerhead";
            }
            reward = difficulty;
            world.setBlock(cposx + width / 2, cposy + 2, cposz + width / 2, Blocks.SPAWNER, 0, 2);
            tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2, cposy + 2, cposz + width / 2);
            if (tileentitymobspawner != null) {
                tileentitymobspawner.entity(critter);
            }
            world.setBlock(cposx + width / 2, cposy + 3, cposz + width / 2, Blocks.SPAWNER, 0, 2);
            tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2, cposy + 3, cposz + width / 2);
            if (tileentitymobspawner != null) {
                tileentitymobspawner.entity(critter);
            }
            for (j = 1; j < 5; ++j) {
                this.FastSetBlock(world, cposx + width / 2 - 1, cposy + j, cposz + width / 2, Blocks.BEDROCK);
                this.FastSetBlock(world, cposx + width / 2 + 1, cposy + j, cposz + width / 2, Blocks.BEDROCK);
                this.FastSetBlock(world, cposx + width / 2, cposy + j, cposz + width / 2 - 1, Blocks.BEDROCK);
                this.FastSetBlock(world, cposx + width / 2, cposy + j, cposz + width / 2 + 1, Blocks.BEDROCK);
            }
            this.FastSetBlock(world, cposx + width / 2 - 1, cposy + 1, cposz + width / 2 - 1, registered("teleport_block"));
            this.FastSetBlock(world, cposx + width / 2 + 1, cposy + 1, cposz + width / 2 + 1, registered("teleport_block"));
            this.FastSetBlock(world, cposx + width / 2 + 1, cposy + 1, cposz + width / 2 - 1, registered("teleport_block"));
            this.FastSetBlock(world, cposx + width / 2 - 1, cposy + 1, cposz + width / 2 + 1, registered("teleport_block"));
            this.FastSetBlock(world, cposx + 1, cposy + height, cposz + 1, Blocks.AIR);
            this.fill_chests(world, cposx, cposy, cposz, width, height, decor, reward);
        }
    }

    public void makeEnderCastle(Plan world, int cposx, int cposy, int cposz) {
        int m;
        int j;
        int k;
        int i;
        int width = 22;
        int height = 12;
        Spawner tileentitymobspawner = null;
        Block bid = Blocks.OBSIDIAN;
        for (i = -3; i <= width + 3; ++i) {
            for (k = -3; k <= width + 3; ++k) {
                for (j = 0; j <= 1; ++j) {
                    bid = Blocks.AIR;
                    if (j == 0) {
                        bid = Blocks.OBSIDIAN;
                    }
                    if (j == 1 && (i == -3 || i == width + 3 || k == width + 3 | k == -3)) {
                        bid = Blocks.IRON_BARS;
                    }
                    world.setBlock( cposx + i, cposy + j, cposz + k, bid, 0, 2);
                }
            }
        }
        for (i = 0; i <= width; ++i) {
            for (k = 0; k <= width; ++k) {
                for (j = 1; j <= height; ++j) {
                    bid = Blocks.AIR;
                    if (i == 0 || i == width || k == width | k == 0) {
                        bid = Blocks.BEDROCK;
                    }
                    if (j == height && bid == Blocks.BEDROCK && (i + k & 1) == 0) {
                        bid = Blocks.AIR;
                    }
                    if (j == height - 2 && bid == Blocks.BEDROCK && (i + k & 1) == 0) {
                        int which = world.random.nextInt(4);
                        if (which == 0) {
                            bid = ENDER_KNIGHT_MARKER;
                        }
                        if (which == 1) {
                            bid = ENDER_REAPER_MARKER;
                        }
                        if (which == 2) {
                            bid = ENDERMAN_MARKER;
                        }
                        if (which == 3) {
                            bid = ENDER_DRAGON_MARKER;
                        }
                    }
                    if (j == 7 && bid == Blocks.BEDROCK && (i + k & 1) != 0) {
                        bid = registered("eye_of_ender_block");
                    }
                    world.setBlock( cposx + i, cposy + j, cposz + k, bid, 0, 2);
                }
            }
        }
        for (i = -1; i <= width + 1; ++i) {
            for (k = -1; k <= width + 1; ++k) {
                for (j = 1; j <= height - 1; ++j) {
                    bid = Blocks.AIR;
                    if (j == 6 || j > 8) {
                        if (i == -1 || i == width + 1 || k == width + 1 | k == -1) {
                            bid = Blocks.BEDROCK;
                        }
                        if (j == 6 && bid != Blocks.AIR && world.random.nextInt(2) == 1) {
                            world.setBlock( cposx + i, cposy + j - 1, cposz + k, registered("enderpearl_block"), 0, 2);
                            if (world.random.nextInt(3) == 1) {
                                world.setBlock( cposx + i, cposy + j - 2, cposz + k, registered("enderpearl_block"), 0, 2);
                            }
                        }
                    }
                    if (j == 7) {
                        if (i == -1 || i == width + 1 || k == width + 1 | k == -1) {
                            bid = Blocks.BEDROCK;
                        }
                        if (bid == Blocks.BEDROCK && (i + k & 1) == 0) {
                            bid = Blocks.AIR;
                        }
                    }
                    if (bid == Blocks.AIR) continue;
                    world.setBlock( cposx + i, cposy + j, cposz + k, bid, 0, 2);
                }
            }
        }
        this.makeAColumn(world, cposx - 2, cposy, cposz - 2, height + 1, 0);
        this.makeAColumn(world, cposx + width - 2, cposy, cposz - 2, height + 1, 1);
        this.makeAColumn(world, cposx - 2, cposy, cposz + width - 2, height + 1, 2);
        this.makeAColumn(world, cposx + width - 2, cposy, cposz + width - 2, height + 1, 3);
        j = 8;
        for (i = 1; i <= width - 1; ++i) {
            for (k = 1; k <= width - 1; ++k) {
                bid = Blocks.OBSIDIAN;
                if (i == width / 2 || k == width / 2 || i == k || i == width - k) {
                    bid = Blocks.BEDROCK;
                }
                world.setBlock( cposx + i, cposy + j, cposz + k, bid, 0, 2);
            }
        }
        j = 9;
        for (i = -2; i <= 2; ++i) {
            for (k = -2; k <= 2; ++k) {
                bid = Blocks.LAVA;
                world.setBlock( cposx + i + width / 2, cposy + j, cposz + k + width / 2, bid, 0, 2);
            }
        }
        for (m = -1; m <= 1; ++m) {
            world.setBlock( cposx + width / 2 + m, cposy + j, cposz + width / 2 + 3, Blocks.BEDROCK, 0, 2);
            world.setBlock( cposx + width / 2 + m, cposy + j, cposz + width / 2 - 3, Blocks.BEDROCK, 0, 2);
            world.setBlock( cposx + width / 2 + 3, cposy + j, cposz + width / 2 + m, Blocks.BEDROCK, 0, 2);
            world.setBlock( cposx + width / 2 - 3, cposy + j, cposz + width / 2 + m, Blocks.BEDROCK, 0, 2);
        }
        world.setBlock( cposx + width / 2 - 2, cposy + j, cposz + width / 2 - 2, Blocks.BEDROCK, 0, 2);
        world.setBlock( cposx + width / 2 + 2, cposy + j, cposz + width / 2 + 2, Blocks.BEDROCK, 0, 2);
        world.setBlock( cposx + width / 2 - 2, cposy + j, cposz + width / 2 + 2, Blocks.BEDROCK, 0, 2);
        world.setBlock( cposx + width / 2 + 2, cposy + j, cposz + width / 2 - 2, Blocks.BEDROCK, 0, 2);
        world.setBlock( cposx + width / 2, cposy + j, cposz + width / 2, Blocks.BEDROCK, 0, 2);
        world.setBlock(cposx + width / 2, cposy + j + 1, cposz + width / 2, Blocks.ENDER_CHEST, 2, 2);
        world.setBlock( cposx + width / 2, cposy + j + 2, cposz + width / 2, Blocks.OBSIDIAN, 0, 2);
        world.setBlock( cposx + width / 2, cposy + j + 3, cposz + width / 2, Blocks.BEDROCK, 0, 2);
        world.setBlock( cposx + width / 2 - 1, cposy + j + 3, cposz + width / 2, Blocks.BEDROCK, 0, 2);
        world.setBlock( cposx + width / 2 + 1, cposy + j + 3, cposz + width / 2, Blocks.BEDROCK, 0, 2);
        world.setBlock( cposx + width / 2, cposy + j + 3, cposz + width / 2 - 1, Blocks.BEDROCK, 0, 2);
        world.setBlock( cposx + width / 2, cposy + j + 3, cposz + width / 2 + 1, Blocks.BEDROCK, 0, 2);
        world.setBlock( cposx + width / 2 - 1, cposy + j + 4, cposz + width / 2, Blocks.TORCH, 0, 2);
        world.setBlock( cposx + width / 2 + 1, cposy + j + 4, cposz + width / 2, Blocks.TORCH, 0, 2);
        world.setBlock( cposx + width / 2, cposy + j + 4, cposz + width / 2 - 1, Blocks.TORCH, 0, 2);
        world.setBlock( cposx + width / 2, cposy + j + 4, cposz + width / 2 + 1, Blocks.TORCH, 0, 2);
        world.setBlock( cposx + width / 2, cposy + j + 4, cposz + width / 2, Blocks.BEDROCK, 0, 2);
        world.setBlock( cposx + width / 2, cposy + j + 5, cposz + width / 2, Blocks.BEDROCK, 0, 2);
        world.setBlock( cposx + width / 2, cposy + j + 6, cposz + width / 2, Blocks.DRAGON_EGG, 0, 2);
        world.setBlock(cposx + width / 2 + 5, cposy + j, cposz + width / 2 + 5, Blocks.SPAWNER, 0, 2);
        tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2 + 5, cposy + j, cposz + width / 2 + 5);
        if (tileentitymobspawner != null) {
            tileentitymobspawner.entity("Ender Reaper");
        }
        world.setBlock(cposx + width / 2 + 5, cposy + j + 1, cposz + width / 2 + 5, Blocks.SPAWNER, 0, 2);
        tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2 + 5, cposy + j + 1, cposz + width / 2 + 5);
        if (tileentitymobspawner != null) {
            tileentitymobspawner.entity("Ender Knight");
        }
        world.setBlock(cposx + width / 2 - 5, cposy + j, cposz + width / 2 + 5, Blocks.SPAWNER, 0, 2);
        tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2 - 5, cposy + j, cposz + width / 2 + 5);
        if (tileentitymobspawner != null) {
            tileentitymobspawner.entity("Ender Reaper");
        }
        world.setBlock(cposx + width / 2 - 5, cposy + j + 1, cposz + width / 2 + 5, Blocks.SPAWNER, 0, 2);
        tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2 - 5, cposy + j + 1, cposz + width / 2 + 5);
        if (tileentitymobspawner != null) {
            tileentitymobspawner.entity("Ender Knight");
        }
        world.setBlock(cposx + width / 2 + 5, cposy + j, cposz + width / 2 - 5, Blocks.SPAWNER, 0, 2);
        tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2 + 5, cposy + j, cposz + width / 2 - 5);
        if (tileentitymobspawner != null) {
            tileentitymobspawner.entity("Ender Reaper");
        }
        world.setBlock(cposx + width / 2 + 5, cposy + j + 1, cposz + width / 2 - 5, Blocks.SPAWNER, 0, 2);
        tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2 + 5, cposy + j + 1, cposz + width / 2 - 5);
        if (tileentitymobspawner != null) {
            tileentitymobspawner.entity("Ender Knight");
        }
        world.setBlock(cposx + width / 2 - 5, cposy + j, cposz + width / 2 - 5, Blocks.SPAWNER, 0, 2);
        tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2 - 5, cposy + j, cposz + width / 2 - 5);
        if (tileentitymobspawner != null) {
            tileentitymobspawner.entity("Ender Reaper");
        }
        world.setBlock(cposx + width / 2 - 5, cposy + j + 1, cposz + width / 2 - 5, Blocks.SPAWNER, 0, 2);
        tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2 - 5, cposy + j + 1, cposz + width / 2 - 5);
        if (tileentitymobspawner != null) {
            tileentitymobspawner.entity("Ender Knight");
        }
        j = 4;
        for (i = 1; i <= width - 1; ++i) {
            for (k = 1; k <= width - 1; ++k) {
                bid = Blocks.AIR;
                if (i <= 5 || k <= 5 || i >= width - 5 || k >= width - 5) {
                    bid = Blocks.BEDROCK;
                }
                if (bid != Blocks.AIR) {
                    world.setBlock( cposx + i, cposy + j, cposz + k, bid, 0, 2);
                }
                if (i == 5 && k >= 5 && k <= width - 5) {
                    world.setBlock( cposx + i, cposy + j + 1, cposz + k, Blocks.IRON_BARS, 0, 2);
                    world.setBlock( cposx + i, cposy + j + 2, cposz + k, Blocks.IRON_BARS, 0, 2);
                    world.setBlock( cposx + i, cposy + j + 3, cposz + k, Blocks.IRON_BARS, 0, 2);
                }
                if (i == width - 5 && k >= 5 && k <= width - 5) {
                    world.setBlock( cposx + i, cposy + j + 1, cposz + k, Blocks.IRON_BARS, 0, 2);
                    world.setBlock( cposx + i, cposy + j + 2, cposz + k, Blocks.IRON_BARS, 0, 2);
                    world.setBlock( cposx + i, cposy + j + 3, cposz + k, Blocks.IRON_BARS, 0, 2);
                }
                if (k == 5 && i >= 5 && i <= width - 5) {
                    world.setBlock( cposx + i, cposy + j + 1, cposz + k, Blocks.IRON_BARS, 0, 2);
                    world.setBlock( cposx + i, cposy + j + 2, cposz + k, Blocks.IRON_BARS, 0, 2);
                    world.setBlock( cposx + i, cposy + j + 3, cposz + k, Blocks.IRON_BARS, 0, 2);
                }
                if (k != width - 5 || i < 5 || i > width - 5) continue;
                world.setBlock( cposx + i, cposy + j + 1, cposz + k, Blocks.IRON_BARS, 0, 2);
                world.setBlock( cposx + i, cposy + j + 2, cposz + k, Blocks.IRON_BARS, 0, 2);
                world.setBlock( cposx + i, cposy + j + 3, cposz + k, Blocks.IRON_BARS, 0, 2);
            }
        }
        bid = Blocks.BEDROCK;
        j = 3;
        k = width / 2;
        i = width - 6;
        for (m = -1; m <= 1; ++m) {
            world.setBlock( cposx + i, cposy + j, cposz + k + m, bid, 0, 2);
        }
        j = 2;
        k = width / 2;
        i = width - 7;
        for (m = -1; m <= 1; ++m) {
            world.setBlock( cposx + i, cposy + j, cposz + k + m, bid, 0, 2);
        }
        j = 1;
        k = width / 2;
        i = width - 8;
        for (m = -1; m <= 1; ++m) {
            world.setBlock( cposx + i, cposy + j, cposz + k + m, bid, 0, 2);
        }
        j = 4;
        i = width - 5;
        for (m = -1; m <= 1; ++m) {
            world.setBlock( cposx + i, cposy + j + 1, cposz + k + m, Blocks.AIR, 0, 2);
            world.setBlock( cposx + i, cposy + j + 2, cposz + k + m, Blocks.AIR, 0, 2);
            world.setBlock( cposx + i, cposy + j + 3, cposz + k + m, Blocks.AIR, 0, 2);
        }
        j = 1;
        world.setBlock(cposx + width / 2, cposy + j, cposz + width / 2, Blocks.SPAWNER, 0, 2);
        tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2, cposy + j, cposz + width / 2);
        if (tileentitymobspawner != null) {
            tileentitymobspawner.entity("Ender Reaper");
        }
        world.setBlock(cposx + width / 2, cposy + j + 1, cposz + width / 2, Blocks.SPAWNER, 0, 2);
        tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2, cposy + j + 1, cposz + width / 2);
        if (tileentitymobspawner != null) {
            tileentitymobspawner.entity("Ender Knight");
        }
        j = 5;
        world.setBlock(cposx + 1, cposy + j, cposz + width / 2 - 1, Blocks.SPAWNER, 0, 2);
        tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + 1, cposy + j, cposz + width / 2 - 1);
        if (tileentitymobspawner != null) {
            tileentitymobspawner.entity("CaveFisher");
        }
        world.setBlock(cposx + 1, cposy + j, cposz + width / 2 + 1, Blocks.SPAWNER, 0, 2);
        tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + 1, cposy + j, cposz + width / 2 + 1);
        if (tileentitymobspawner != null) {
            tileentitymobspawner.entity("CaveFisher");
        }
        world.setBlock(cposx + 1, cposy + j, cposz + width / 2, (Block)Blocks.CHEST, 2, 2);
        world.loot(new BlockPos(cposx + 1, cposy + j, cposz + width / 2), "ender_castle");
        world.setBlock(cposx + width / 2 - 1, cposy + j, cposz + 1, Blocks.SPAWNER, 0, 2);
        tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2 - 1, cposy + j, cposz + 1);
        if (tileentitymobspawner != null) {
            tileentitymobspawner.entity("CaveFisher");
        }
        world.setBlock(cposx + width / 2 + 1, cposy + j, cposz + 1, Blocks.SPAWNER, 0, 2);
        tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2 + 1, cposy + j, cposz + 1);
        if (tileentitymobspawner != null) {
            tileentitymobspawner.entity("CaveFisher");
        }
        world.setBlock(cposx + width / 2, cposy + j, cposz + 1, (Block)Blocks.CHEST, 3, 2);
        world.loot(new BlockPos(cposx + width / 2, cposy + j, cposz + 1), "ender_castle");
        world.setBlock(cposx + width / 2 - 1, cposy + j, cposz + width - 1, Blocks.SPAWNER, 0, 2);
        tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2 - 1, cposy + j, cposz + width - 1);
        if (tileentitymobspawner != null) {
            tileentitymobspawner.entity("CaveFisher");
        }
        world.setBlock(cposx + width / 2 + 1, cposy + j, cposz + width - 1, Blocks.SPAWNER, 0, 2);
        tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2 + 1, cposy + j, cposz + width - 1);
        if (tileentitymobspawner != null) {
            tileentitymobspawner.entity("CaveFisher");
        }
        world.setBlock(cposx + width / 2, cposy + j, cposz + width - 1, (Block)Blocks.CHEST, 4, 2);
        world.loot(new BlockPos(cposx + width / 2, cposy + j, cposz + width - 1), "ender_castle");
    }

    private void makeAColumn(Plan world, int cposx, int cposy, int cposz, int height, int dir) {
        Block bid;
        int j;
        int k;
        int i;
        int width = 4;
        int halfwidth = 2;
        int step = dir;
        for (i = -2; i <= width + 2; ++i) {
            for (k = -2; k <= width + 2; ++k) {
                j = height + 2;
                world.setBlock( cposx + i, cposy + j, cposz + k, Blocks.OBSIDIAN, 0, 2);
            }
        }
        for (i = -2; i <= width + 2; ++i) {
            for (k = -2; k <= width + 2; ++k) {
                bid = Blocks.AIR;
                if (i == -2 || i == width + 2 || k == width + 2 | k == -2) {
                    bid = Blocks.OBSIDIAN;
                }
                j = height + 3;
                if (bid != Blocks.AIR && (i + k & 1) == 0) {
                    bid = Blocks.AIR;
                }
                world.setBlock( cposx + i, cposy + j, cposz + k, bid, 0, 2);
            }
        }
        for (i = 0; i <= width; ++i) {
            for (k = 0; k <= width; ++k) {
                for (j = 1; j <= height + 2; ++j) {
                    bid = Blocks.AIR;
                    if (i == 0 || i == width || k == width | k == 0) {
                        bid = Blocks.OBSIDIAN;
                    }
                    if (!(j % 3 != 0 && j % 3 != 1 || j == height + 2 || bid != Blocks.OBSIDIAN || i != halfwidth && k != halfwidth)) {
                        bid = Blocks.IRON_BARS;
                    }
                    world.setBlock( cposx + i, cposy + j, cposz + k, bid, 0, 2);
                }
            }
        }
        if (dir == 0) {
            for (j = 1; j <= 2; ++j) {
                world.setBlock( cposx + width, cposy + j, cposz + width, Blocks.AIR, 0, 2);
                world.setBlock( cposx + width - 1, cposy + j, cposz + width, Blocks.AIR, 0, 2);
                world.setBlock( cposx + width, cposy + j, cposz + width - 1, Blocks.AIR, 0, 2);
            }
            for (j = 9; j <= 10; ++j) {
                world.setBlock( cposx + width, cposy + j, cposz + width, Blocks.AIR, 0, 2);
                world.setBlock( cposx + width - 1, cposy + j, cposz + width, Blocks.AIR, 0, 2);
                world.setBlock( cposx + width, cposy + j, cposz + width - 1, Blocks.AIR, 0, 2);
            }
        }
        if (dir == 1) {
            for (j = 1; j <= 2; ++j) {
                world.setBlock( cposx, cposy + j, cposz + width, Blocks.AIR, 0, 2);
                world.setBlock( cposx + 1, cposy + j, cposz + width, Blocks.AIR, 0, 2);
                world.setBlock( cposx, cposy + j, cposz + width - 1, Blocks.AIR, 0, 2);
            }
            for (j = 9; j <= 10; ++j) {
                world.setBlock( cposx, cposy + j, cposz + width, Blocks.AIR, 0, 2);
                world.setBlock( cposx + 1, cposy + j, cposz + width, Blocks.AIR, 0, 2);
                world.setBlock( cposx, cposy + j, cposz + width - 1, Blocks.AIR, 0, 2);
            }
            if (++step > 3) {
                step = 0;
            }
        }
        if (dir == 2) {
            for (j = 1; j <= 2; ++j) {
                world.setBlock( cposx + width, cposy + j, cposz, Blocks.AIR, 0, 2);
                world.setBlock( cposx + width - 1, cposy + j, cposz, Blocks.AIR, 0, 2);
                world.setBlock( cposx + width, cposy + j, cposz + 1, Blocks.AIR, 0, 2);
            }
            for (j = 9; j <= 10; ++j) {
                world.setBlock( cposx + width, cposy + j, cposz, Blocks.AIR, 0, 2);
                world.setBlock( cposx + width - 1, cposy + j, cposz, Blocks.AIR, 0, 2);
                world.setBlock( cposx + width, cposy + j, cposz + 1, Blocks.AIR, 0, 2);
            }
            if (++step > 3) {
                step = 0;
            }
            if (++step > 3) {
                step = 0;
            }
        }
        if (dir == 3) {
            for (j = 1; j <= 2; ++j) {
                world.setBlock( cposx, cposy + j, cposz, Blocks.AIR, 0, 2);
                world.setBlock( cposx + 1, cposy + j, cposz, Blocks.AIR, 0, 2);
                world.setBlock( cposx, cposy + j, cposz + 1, Blocks.AIR, 0, 2);
            }
            for (j = 9; j <= 10; ++j) {
                world.setBlock( cposx, cposy + j, cposz, Blocks.AIR, 0, 2);
                world.setBlock( cposx + 1, cposy + j, cposz, Blocks.AIR, 0, 2);
                world.setBlock( cposx, cposy + j, cposz + 1, Blocks.AIR, 0, 2);
            }
            if (++step > 3) {
                step = 0;
            }
            if (++step > 3) {
                step = 0;
            }
        }
        bid = Blocks.NETHER_BRICKS;
        k = 0;
        for (j = 1; j <= height + 2; ++j) {
            if (step == 0) {
                k = 1;
                i = 1;
            }
            if (step == 1) {
                i = 1;
                k = 3;
            }
            if (step == 2) {
                i = 3;
                k = 3;
            }
            if (step == 3) {
                i = 3;
                k = 1;
            }
            if (++step > 3) {
                step = 0;
            }
            world.setBlock( cposx + i, cposy + j, cposz + k, bid, 0, 2);
        }
    }
}
