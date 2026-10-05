package net.mcreator.xillysorespawn.entity;

import java.util.Random;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.*;
import net.minecraft.item.*;
import net.minecraft.network.IPacket;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.*;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.Heightmap;
import net.mcreator.xillysorespawn.XillysOrespawnModElements;
import net.mcreator.xillysorespawn.entity.renderer.WormSmallRenderer;

@XillysOrespawnModElements.ModElement.Tag
public class WormSmallEntity extends XillysOrespawnModElements.ModElement {
    public static EntityType entity=(EntityType.Builder.<CustomEntity>create(CustomEntity::new,EntityClassification.MONSTER)
        .setShouldReceiveVelocityUpdates(true).setTrackingRange(80).setUpdateInterval(1)
        .setCustomClientFactory(CustomEntity::new).size(.25F,1F)).build("worm_small").setRegistryName("worm_small");
    public WormSmallEntity(XillysOrespawnModElements instance){super(instance,97);FMLJavaModLoadingContext.get().getModEventBus().register(new WormSmallRenderer.ModelRegisterHandler());FMLJavaModLoadingContext.get().getModEventBus().register(new EntityAttributesRegisterHandler());MinecraftForge.EVENT_BUS.register(this);}
    @Override public void initElements(){elements.entities.add(()->entity);elements.items.add(()->new SpawnEggItem(entity,-1,-1,new Item.Properties().group(ItemGroup.MISC)).setRegistryName("worm_small_spawn_egg"));}
    @SubscribeEvent public void addFeatureToBiomes(BiomeLoadingEvent event){
        if (!OreSpawnLogic.allowNaturalSpawn(event, "worm_small")) return;
        event.getSpawns().getSpawner(EntityClassification.MONSTER).add(new MobSpawnInfo.Spawners(entity,3,1,3));}
    @Override public void init(FMLCommonSetupEvent event){EntitySpawnPlacementRegistry.register(entity,EntitySpawnPlacementRegistry.PlacementType.NO_RESTRICTIONS,Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,WormSmallEntity::canSpawn);}
    public static boolean canSpawn(EntityType<? extends MobEntity> type,IServerWorld world,SpawnReason reason,BlockPos pos,Random random){return reason!=SpawnReason.NATURAL||!world.getWorld().isDaytime();}
    public static class EntityAttributesRegisterHandler{@SubscribeEvent public void onEntityAttributeCreation(EntityAttributeCreationEvent event){event.put(entity,CustomEntity.createAttributes().create());}}
    public static class CustomEntity extends OreSpawnWormBase {
        public CustomEntity(FMLPlayMessages.SpawnEntity packet,World world){this(entity,world);}public CustomEntity(EntityType<? extends CustomEntity> type,World world){super(type,world);experienceValue=0;upCount=50;}
        public static AttributeModifierMap.MutableAttribute createAttributes(){return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH,10D).createMutableAttribute(Attributes.MOVEMENT_SPEED,.1D).createMutableAttribute(Attributes.ATTACK_DAMAGE,3D).createMutableAttribute(Attributes.ARMOR,0D).createMutableAttribute(Attributes.FOLLOW_RANGE,12D);}
        @Override public IPacket<?> createSpawnPacket(){return NetworkHooks.getEntitySpawningPacket(this);}
        @Override protected int initialUpCount(){return 50;}@Override protected int nextUpCount(){return 25+rand.nextInt(50);}@Override protected int downDuration(){return 100+rand.nextInt(150);}@Override protected double noticeRange(){return 8D;}@Override protected double attackRange(){return 1.5D;}@Override protected int attackChance(){return 15;}@Override protected double hiddenDamping(){return .75D;}@Override protected int wormTier(){return 1;}
        @Override protected SoundEvent getHurtSound(DamageSource s){return OreSpawnLogic.sound("little_splat",SoundEvents.ENTITY_SLIME_HURT);}
    }
}
