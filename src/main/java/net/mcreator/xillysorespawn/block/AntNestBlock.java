package net.mcreator.xillysorespawn.block;

import net.mcreator.xillysorespawn.XillysOrespawnModElements;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ObjectHolder;

import java.util.List;
import java.util.Random;

/** One block with five OreSpawn ant-nest types; type 0 is the placed brown nest. */
@XillysOrespawnModElements.ModElement.Tag
public class AntNestBlock extends XillysOrespawnModElements.ModElement {
    public static final IntegerProperty NEST_TYPE = IntegerProperty.create("nest_type", 0, 4);
    private static final String[] MOBS = {"brown_ant", "red_ant", "rainbow_ant", "unstable_ant", "termite"};
    @ObjectHolder("xillys_orespawn:ant_nest") public static final Block block = null;
    public AntNestBlock(XillysOrespawnModElements instance) { super(instance, 237); }
    @Override public void initElements() {
        elements.blocks.add(CustomBlock::new);
        elements.items.add(() -> new BlockItem(block, new Item.Properties().group(ItemGroup.BUILDING_BLOCKS))
            .setRegistryName(block.getRegistryName()));
    }

    public static BlockState nest(int type) {
        return block.getDefaultState().with(NEST_TYPE, Math.max(0, Math.min(4, type)));
    }

    public static class CustomBlock extends Block {
        public CustomBlock() {
            super(Block.Properties.create(Material.EARTH).sound(SoundType.GROUND)
                .hardnessAndResistance(1.0F, 10.0F).tickRandomly());
            setDefaultState(stateContainer.getBaseState().with(NEST_TYPE, 0));
            setRegistryName("ant_nest");
        }

        @Override protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
            builder.add(NEST_TYPE);
        }

        @Override public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
            spawnFromNest(world, pos, random, state.get(NEST_TYPE));
        }
    }

    public static void spawnFromNest(ServerWorld world, BlockPos pos, Random random, int typeIndex) {
            if (!world.isAirBlock(pos.up()) || world.isRainingAt(pos.up())) return;
            List<Entity> nearby = world.getEntitiesWithinAABB(Entity.class,
                new AxisAlignedBB(pos).grow(8.0D), entity -> {
                    ResourceLocation id = entity.getType().getRegistryName();
                    if (id == null || !"xillys_orespawn".equals(id.getNamespace())) return false;
                    for (String mob : MOBS) if (mob.equals(id.getPath())) return true;
                    return false;
                });
            if (nearby.size() >= 12) return;
            String id = MOBS[Math.max(0, Math.min(4, typeIndex))];
            EntityType<?> type = ForgeRegistries.ENTITIES.getValue(new ResourceLocation("xillys_orespawn", id));
            if (type == null) return;
            int count = Math.min(2 + random.nextInt(6), 12 - nearby.size());
            for (int i = 0; i < count; i++) {
                Entity ant = type.create(world);
                if (ant == null) break;
                ant.setLocationAndAngles(pos.getX() + 0.5D, pos.getY() + 1.01D,
                    pos.getZ() + 0.5D, random.nextFloat() * 360.0F, 0.0F);
                world.addEntity(ant);
            }
    }
}
