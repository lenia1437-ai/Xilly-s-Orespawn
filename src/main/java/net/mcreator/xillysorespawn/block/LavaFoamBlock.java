package net.mcreator.xillysorespawn.block;

import net.mcreator.xillysorespawn.XillysOrespawnModElements;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.registries.ObjectHolder;

@XillysOrespawnModElements.ModElement.Tag
public class LavaFoamBlock extends XillysOrespawnModElements.ModElement {
    @ObjectHolder("xillys_orespawn:lava_foam") public static final Block block = null;

    public LavaFoamBlock(XillysOrespawnModElements instance) { super(instance, 293); }

    @Override public void initElements() {
        elements.blocks.add(CustomBlock::new);
        elements.items.add(() -> new BlockItem(block, new Item.Properties().group(ItemGroup.BUILDING_BLOCKS))
            .setRegistryName(block.getRegistryName()));
    }

    public static class CustomBlock extends Block {
        public CustomBlock() {
            super(Block.Properties.create(Material.ROCK).sound(SoundType.STONE)
                .hardnessAndResistance(5.0F, 5.0F).slipperiness(1.1F));
            setRegistryName("lava_foam");
        }

        @Override public void onEntityWalk(World world, BlockPos pos, Entity entity) {
            super.onEntityWalk(world, pos, entity);
            if (world.isRemote || !(entity instanceof LivingEntity)) return;
            Vector3d motion = entity.getMotion();
            double x = motion.x, z = motion.z;
            double speed = Math.sqrt(x * x + z * z);
            if (speed < 0.08D) {
                double yaw = Math.toRadians(entity.rotationYaw);
                x = -Math.sin(yaw) * 0.45D;
                z = Math.cos(yaw) * 0.45D;
            } else {
                // The original Lavafoam multiplies sideways momentum by 1.35
                // each contact. Keep that acceleration, bounded for server safety.
                double boosted = Math.min(3.0D, Math.max(0.45D, speed * 1.35D));
                x = x / speed * boosted;
                z = z / speed * boosted;
            }
            entity.setMotion(x, motion.y, z);
            entity.velocityChanged = true;
        }
    }
}
