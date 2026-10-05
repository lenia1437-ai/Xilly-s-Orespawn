package net.mcreator.xillysorespawn.item;

import net.mcreator.xillysorespawn.XillysOrespawnModElements;
import net.mcreator.xillysorespawn.entity.AttackSquidEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.registries.ObjectHolder;

/** Original OreSpawn Squidzooka: launches a live hostile Attack Squid. */
@XillysOrespawnModElements.ModElement.Tag
public class SquidzookaItem extends XillysOrespawnModElements.ModElement {
    @ObjectHolder("xillys_orespawn:squidzooka") public static final Item block = null;
    public SquidzookaItem(XillysOrespawnModElements instance) { super(instance, 197); }
    @Override public void initElements() { elements.items.add(ItemCustom::new); }

    public static class ItemCustom extends Item {
        public ItemCustom() {
            // A damageable item is already limited to one per stack in 1.16.5.
            super(new Item.Properties().group(ItemGroup.COMBAT).maxDamage(100));
            setRegistryName("squidzooka");
        }

        @Override
        public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
            ItemStack stack = player.getHeldItem(hand);
            world.playSound(null, player.getPosX(), player.getPosY(), player.getPosZ(),
                SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.PLAYERS, 0.5F, 0.5F);
            if (!world.isRemote) {
                AttackSquidEntity.CustomEntity squid = new AttackSquidEntity.CustomEntity(AttackSquidEntity.entity, world);
                Vector3d look = player.getLookVec();
                squid.setPosition(player.getPosX() + look.x * 2.5D,
                    player.getPosYEye() - 0.25D + look.y, player.getPosZ() + look.z * 2.5D);
                squid.setMotion(look.scale(3.6D));
                squid.setWasShot();
                world.addEntity(squid);
                if (!player.abilities.isCreativeMode)
                    stack.damageItem(1, player, p -> p.sendBreakAnimation(hand));
            }
            player.applyKnockback(0.45F, lookX(player), lookZ(player));
            player.getCooldownTracker().setCooldown(this, 8);
            return new ActionResult<>(ActionResultType.SUCCESS, stack);
        }

        private static double lookX(PlayerEntity p) { return Math.sin(Math.toRadians(p.rotationYaw)); }
        private static double lookZ(PlayerEntity p) { return -Math.cos(Math.toRadians(p.rotationYaw)); }
    }
}
