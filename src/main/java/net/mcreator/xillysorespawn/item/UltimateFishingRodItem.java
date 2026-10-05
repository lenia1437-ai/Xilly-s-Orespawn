package net.mcreator.xillysorespawn.item;

import net.mcreator.xillysorespawn.XillysOrespawnModElements;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tags.FluidTags;
import net.minecraft.item.FishingRodItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.registries.ObjectHolder;

@XillysOrespawnModElements.ModElement.Tag
public class UltimateFishingRodItem extends XillysOrespawnModElements.ModElement {
    @ObjectHolder("xillys_orespawn:ultimate_fishing_rod") public static final Item block = null;
    public UltimateFishingRodItem(XillysOrespawnModElements instance) { super(instance, 219); }
    @Override public void initElements() { elements.items.add(ItemCustom::new); }

    public static class ItemCustom extends FishingRodItem {
        public ItemCustom() {
            super(new Item.Properties().group(ItemGroup.TOOLS).maxDamage(3000));
            setRegistryName("ultimate_fishing_rod");
        }

        @Override public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
            ItemStack stack = player.getHeldItem(hand);
            CompoundNBT tag = stack.getOrCreateTag();
            if (tag.contains("OreSpawnLavaCast")) {
                if (!world.isRemote) {
                    boolean ready = world.getGameTime() >= tag.getLong("OreSpawnLavaReadyAt");
                    if (ready && tag.getString("OreSpawnLavaDimension").equals(world.getDimensionKey().getLocation().toString())) {
                        Item catchItem = chooseLavaCatch(world);
                        ItemStack fish = new ItemStack(catchItem);
                        if (!player.addItemStackToInventory(fish)) player.dropItem(fish, false);
                        if (!player.abilities.isCreativeMode) stack.damageItem(1, player, p -> p.sendBreakAnimation(hand));
                        player.sendStatusMessage(new StringTextComponent("Lava catch!"), true);
                    } else {
                        player.sendStatusMessage(new StringTextComponent("Nothing bit yet."), true);
                    }
                }
                tag.remove("OreSpawnLavaCast");
                tag.remove("OreSpawnLavaReadyAt");
                tag.remove("OreSpawnLavaDimension");
                return new ActionResult<>(ActionResultType.SUCCESS, stack);
            }

            Vector3d eye = player.getEyePosition(1.0F);
            Vector3d end = eye.add(player.getLookVec().scale(32.0D));
            BlockRayTraceResult hit = world.rayTraceBlocks(new RayTraceContext(eye, end,
                RayTraceContext.BlockMode.OUTLINE, RayTraceContext.FluidMode.ANY, player));
            if (hit.getType() == RayTraceResult.Type.BLOCK
                    && world.getFluidState(hit.getPos()).isTagged(FluidTags.LAVA)) {
                if (!world.isRemote) {
                    tag.putBoolean("OreSpawnLavaCast", true);
                    tag.putLong("OreSpawnLavaReadyAt", world.getGameTime() + 100 + world.rand.nextInt(200));
                    tag.putString("OreSpawnLavaDimension", world.getDimensionKey().getLocation().toString());
                    player.sendStatusMessage(new StringTextComponent("Fishing in lava..."), true);
                }
                return new ActionResult<>(ActionResultType.SUCCESS, stack);
            }
            return super.onItemRightClick(world, player, hand);
        }

        private static Item chooseLavaCatch(World world) {
            int roll = world.rand.nextInt(65);
            if (roll < 25) return SunspotUrchinItem.block;
            if (roll < 40) return SunfishItem.block;
            if (roll < 50) return SparkfishItem.block;
            return FireFishItem.block;
        }
    }
}
