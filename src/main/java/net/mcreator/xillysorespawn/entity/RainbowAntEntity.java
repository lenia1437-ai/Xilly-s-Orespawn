package net.mcreator.xillysorespawn.entity;

import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.network.IPacket;
import net.minecraft.world.World;
import net.mcreator.xillysorespawn.XillysOrespawnModElements;
import net.mcreator.xillysorespawn.entity.renderer.RainbowAntRenderer;

@XillysOrespawnModElements.ModElement.Tag
public class RainbowAntEntity extends XillysOrespawnModElements.ModElement {
    public static final EntityType entity = EntityType.Builder
        .<CustomEntity>create(CustomEntity::new, EntityClassification.AMBIENT)
        .setShouldReceiveVelocityUpdates(true).setTrackingRange(16).setUpdateInterval(3)
        .setCustomClientFactory(CustomEntity::new).size(0.1F, 0.1F)
        .build("rainbow_ant").setRegistryName("rainbow_ant");
    public RainbowAntEntity(XillysOrespawnModElements instance) { super(instance, 1);
        FMLJavaModLoadingContext.get().getModEventBus().register(new RainbowAntRenderer.ModelRegisterHandler());
        FMLJavaModLoadingContext.get().getModEventBus().register(new AttributesHandler()); }
    @Override public void initElements() { elements.entities.add(() -> entity); elements.items.add(() -> new SpawnEggItem(entity, 0x58C4F1, 0xF7D34B,
        new Item.Properties().group(ItemGroup.MISC)).setRegistryName("rainbow_ant_spawn_egg")); }
    public static class AttributesHandler { @SubscribeEvent public void register(EntityAttributeCreationEvent event) { event.put(entity, CustomEntity.attributes().create()); } }
    public static class CustomEntity extends OreSpawnAntBase {
        public CustomEntity(FMLPlayMessages.SpawnEntity packet, World world) { this(entity, world); }
        public CustomEntity(EntityType<? extends CustomEntity> type, World world) { super(type, world); }
        static AttributeModifierMap.MutableAttribute attributes() { return AnimalEntity.func_233666_p_()
            .createMutableAttribute(Attributes.MAX_HEALTH, 1.0D).createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.15D)
            .createMutableAttribute(Attributes.ATTACK_DAMAGE, 0.0D).createMutableAttribute(Attributes.FOLLOW_RANGE, 16.0D); }
        @Override protected String[] destinationDimensions() { return new String[]{"village"}; }
        @Override public IPacket<?> createSpawnPacket() { return NetworkHooks.getEntitySpawningPacket(this); }
    }
}
