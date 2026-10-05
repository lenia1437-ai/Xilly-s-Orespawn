package net.mcreator.xillysorespawn.item;

import net.mcreator.xillysorespawn.XillysOrespawnModElements;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.registries.ObjectHolder;

@XillysOrespawnModElements.ModElement.Tag
public class ThunderStaffItem extends XillysOrespawnModElements.ModElement {
    @ObjectHolder("xillys_orespawn:thunder_staff") public static final Item block = null;
    public ThunderStaffItem(XillysOrespawnModElements instance) { super(instance, 205); }
    @Override public void initElements() { elements.items.add(ItemCustom::new); }

    public static class ItemCustom extends Item {
        public ItemCustom() {
            super(new Item.Properties().group(ItemGroup.COMBAT).maxDamage(50));
            setRegistryName("thunder_staff");
        }
    }
}
