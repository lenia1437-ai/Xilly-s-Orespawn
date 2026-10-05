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
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.Heightmap;
import net.mcreator.xillysorespawn.XillysOrespawnModElements;
import net.mcreator.xillysorespawn.entity.renderer.WormLargeRenderer;

@XillysOrespawnModElements.ModElement.Tag
public class WormLargeEntity extends XillysOrespawnModElements.ModElement {
    public static EntityType entity=(EntityType.Builder.<CustomEntity>create(CustomEntity::new,EntityClassification.MONSTER)
        .setShouldReceiveVelocityUpdates(true).setTrackingRange(128).setUpdateInterval(1)
        .setCustomClientFactory(CustomEntity::new).size(1.55F,2.5F)).build("worm_large").setRegistryName("worm_large");
    public WormLargeEntity(XillysOrespawnModElements instance){super(instance,95);FMLJavaModLoadingContext.get().getModEventBus().register(new WormLargeRenderer.ModelRegisterHandler());FMLJavaModLoadingContext.get().getModEventBus().register(new EntityAttributesRegisterHandler());MinecraftForge.EVENT_BUS.register(this);}
    @Override public void initElements(){elements.entities.add(()->entity);elements.items.add(()->new SpawnEggItem(entity,-1,-1,new Item.Properties().group(ItemGroup.MISC)).setRegistryName("worm_large_spawn_egg"));}
    @SubscribeEvent public void addFeatureToBiomes(BiomeLoadingEvent event){
        if (!OreSpawnLogic.allowNaturalSpawn(event, "worm_large")) return;
        event.getSpawns().getSpawner(EntityClassification.MONSTER).add(new MobSpawnInfo.Spawners(entity,1,1,1));}
    @Override public void init(FMLCommonSetupEvent event){EntitySpawnPlacementRegistry.register(entity,EntitySpawnPlacementRegistry.PlacementType.NO_RESTRICTIONS,Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,WormLargeEntity::canSpawn);}
    public static boolean canSpawn(EntityType<? extends MobEntity> type,IServerWorld access,SpawnReason reason,BlockPos pos,Random random){if(reason!=SpawnReason.NATURAL)return true;World world=access.getWorld();if(pos.getY()<50||!world.getEntitiesWithinAABB(CustomEntity.class,new AxisAlignedBB(pos).grow(32,8,32)).isEmpty())return false;for(int x=-6;x<=6;x++)for(int z=-6;z<=6;z++){for(int y=-2;y>=-8;y--)if(world.isAirBlock(pos.add(x,y,z)))return false;for(int y=2;y<=8;y++)if(!world.isAirBlock(pos.add(x,y,z)))return false;}return true;}
    public static class EntityAttributesRegisterHandler{@SubscribeEvent public void onEntityAttributeCreation(EntityAttributeCreationEvent event){event.put(entity,CustomEntity.createAttributes().create());}}
    public static class CustomEntity extends OreSpawnWormBase {
        public CustomEntity(FMLPlayMessages.SpawnEntity packet,World world){this(entity,world);}public CustomEntity(EntityType<? extends CustomEntity> type,World world){super(type,world);experienceValue=2050;}
        public static AttributeModifierMap.MutableAttribute createAttributes(){return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH,90D).createMutableAttribute(Attributes.MOVEMENT_SPEED,.2D).createMutableAttribute(Attributes.ATTACK_DAMAGE,18D).createMutableAttribute(Attributes.ARMOR,14D).createMutableAttribute(Attributes.FOLLOW_RANGE,24D).createMutableAttribute(Attributes.KNOCKBACK_RESISTANCE,.9D);}
        @Override public IPacket<?> createSpawnPacket(){return NetworkHooks.getEntitySpawningPacket(this);}
        @Override protected int initialUpCount(){return 1;}@Override protected int nextUpCount(){return 1;}@Override protected int downDuration(){return 0;}@Override protected double noticeRange(){return 8D;}@Override protected double attackRange(){return 3D;}@Override protected int attackChance(){return 10;}@Override protected double hiddenDamping(){return .85D;}@Override protected int wormTier(){return 3;}
        @Override protected void dropSpecialItems(DamageSource source,int looting,boolean recentlyHit){super.dropSpecialItems(source,looting,recentlyHit);OreSpawnLogic.drop(world,this,"worm_tooth",1,2);entityDropItem(Items.NETHER_STAR);for(int i=0;i<2;i++)entityDropItem(Items.SPIDER_EYE);for(int i=0;i<3;i++)entityDropItem(Items.ENDER_PEARL);}
        @Override protected SoundEvent getHurtSound(DamageSource s){return OreSpawnLogic.sound("big_splat",SoundEvents.ENTITY_SLIME_HURT);}
        @Override protected SoundEvent getDeathSound(){return OreSpawnLogic.sound("big_splat",SoundEvents.ENTITY_SLIME_DEATH);}
    }
}
