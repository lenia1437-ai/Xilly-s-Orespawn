package net.mcreator.xillysorespawn.procedures;

import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.server.ServerWorld;

import net.mcreator.xillysorespawn.item.MagicAppleItem;
import net.mcreator.xillysorespawn.world.dimension.UtopiaTreeBuilder;
import net.mcreator.xillysorespawn.XillysOrespawnMod;

import java.util.Map;

/** Magic Apple now grows the same full-sized tree used by Utopia. */
public final class UseMagicAppleProcedure {
    private UseMagicAppleProcedure() {
    }

    public static void executeProcedure(Map<String, Object> dependencies) {
        Object worldValue = dependencies.get("world");
        Object entityValue = dependencies.get("entity");
        if (!(worldValue instanceof IWorld) || !(entityValue instanceof Entity)
                || dependencies.get("x") == null || dependencies.get("y") == null || dependencies.get("z") == null) {
            XillysOrespawnMod.LOGGER.warn("Missing dependency for UseMagicApple");
            return;
        }
        IWorld world = (IWorld) worldValue;
        Entity entity = (Entity) entityValue;
        if (!(world instanceof ServerWorld) || !(entity instanceof LivingEntity)) return;
        ItemStack held = ((LivingEntity) entity).getHeldItemMainhand();
        if (held.getItem() != MagicAppleItem.block) return;

        BlockPos ground = new BlockPos(number(dependencies.get("x")), number(dependencies.get("y")), number(dependencies.get("z")));
        if (world.getBlockState(ground).getBlock() != Blocks.GRASS_BLOCK
                && world.getBlockState(ground).getBlock() != Blocks.DIRT
                && world.getBlockState(ground).getBlock() != Blocks.FARMLAND) return;

        ServerWorld serverWorld = (ServerWorld) world;
        if (UtopiaTreeBuilder.buildHugeTree(serverWorld, serverWorld.getRandom(), ground)) {
            if (!(entity instanceof PlayerEntity) || !((PlayerEntity) entity).abilities.isCreativeMode) {
                held.shrink(1);
            }
        }
    }

    private static int number(Object value) {
        return ((Number) value).intValue();
    }
}
