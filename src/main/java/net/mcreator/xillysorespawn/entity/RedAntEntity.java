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
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.network.IPacket;
import net.minecraft.world.World;
import net.mcreator.xillysorespawn.XillysOrespawnModElements;
import net.mcreator.xillysorespawn.entity.renderer.RedAntRenderer;

@XillysOrespawnModElements.ModElement.Tag
public class RedAntEntity extends XillysOrespawnModElements.ModElement {
    public static final EntityType entity = EntityType.Builder
        .<CustomEntity>create(CustomEntity::new, EntityClassification.CREATURE)
        .setShouldReceiveVelocityUpdates(true).setTrackingRange(16).setUpdateInterval(3)
        .setCustomClientFactory(CustomEntity::new).size(0.2F, 0.2F)
        .build("red_ant").setRegistryName("red_ant");
    public RedAntEntity(XillysOrespawnModElements instance) {
        super(instance, 1);
        FMLJavaModLoadingContext.get().getModEventBus().register(new RedAntRenderer.ModelRegisterHandler());
        FMLJavaModLoadingContext.get().getModEventBus().register(new AttributesHandler());
    }
    @Override public void initElements() {
        elements.entities.add(() -> entity);
        elements.items.add(() -> new SpawnEggItem(entity, 0x9B1111, 0x2B0909,
            new Item.Properties().group(ItemGroup.MISC)).setRegistryName("red_ant_spawn_egg"));
    }
    public static class AttributesHandler { @SubscribeEvent public void register(EntityAttributeCreationEvent event) { event.put(entity, CustomEntity.attributes().create()); } }
    public static class CustomEntity extends OreSpawnAntBase {
        public CustomEntity(FMLPlayMessages.SpawnEntity packet, World world) { this(entity, world); }
        public CustomEntity(EntityType<? extends CustomEntity> type, World world) { super(type, world); this.experienceValue = 1; }
        static AttributeModifierMap.MutableAttribute attributes() { return AnimalEntity.func_233666_p_()
            .createMutableAttribute(Attributes.MAX_HEALTH, 2.0D).createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.2D)
            .createMutableAttribute(Attributes.ATTACK_DAMAGE, 1.0D).createMutableAttribute(Attributes.FOLLOW_RANGE, 8.0D); }
        @Override protected void registerGoals() { super.registerGoals(); this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0D, false));
            if (OreSpawnLogic.playNicely == 0) this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, 4, true, false,
                p -> !(p instanceof PlayerEntity) || !((PlayerEntity)p).abilities.isCreativeMode)); }
        @Override protected boolean randomlyBitesPlayers() { return true; }
        @Override protected String[] destinationDimensions() { return new String[]{"extreme"}; }
        @Override public IPacket<?> createSpawnPacket() { return NetworkHooks.getEntitySpawningPacket(this); }
    }
}
