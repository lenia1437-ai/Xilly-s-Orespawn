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
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.*;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.Heightmap;
import net.mcreator.xillysorespawn.XillysOrespawnModElements;
import net.mcreator.xillysorespawn.entity.renderer.WhaleRenderer;

@XillysOrespawnModElements.ModElement.Tag
public class WhaleEntity extends XillysOrespawnModElements.ModElement {
    public static EntityType entity=(EntityType.Builder.<CustomEntity>create(CustomEntity::new,EntityClassification.WATER_CREATURE)
        .setShouldReceiveVelocityUpdates(true).setTrackingRange(128).setUpdateInterval(2)
        .setCustomClientFactory(CustomEntity::new).size(1.5F,2.5F)).build("whale").setRegistryName("whale");
    public WhaleEntity(XillysOrespawnModElements instance){super(instance,94);FMLJavaModLoadingContext.get().getModEventBus().register(new WhaleRenderer.ModelRegisterHandler());FMLJavaModLoadingContext.get().getModEventBus().register(new EntityAttributesRegisterHandler());MinecraftForge.EVENT_BUS.register(this);}
    @Override public void initElements(){elements.entities.add(()->entity);elements.items.add(()->new SpawnEggItem(entity,-1,-1,new Item.Properties().group(ItemGroup.MISC)).setRegistryName("whale_spawn_egg"));}
    @SubscribeEvent public void addFeatureToBiomes(BiomeLoadingEvent event){
        if (!OreSpawnLogic.allowNaturalSpawn(event, "whale")) return;
        event.getSpawns().getSpawner(EntityClassification.WATER_CREATURE).add(new MobSpawnInfo.Spawners(entity,1,1,1));}
    @Override public void init(FMLCommonSetupEvent event){EntitySpawnPlacementRegistry.register(entity,EntitySpawnPlacementRegistry.PlacementType.IN_WATER,Heightmap.Type.OCEAN_FLOOR,WhaleEntity::canSpawn);}
    public static boolean canSpawn(EntityType<? extends MobEntity> type,IServerWorld world,SpawnReason reason,BlockPos pos,Random random){return reason!=SpawnReason.NATURAL||(pos.getY()>=50&&world.getWorld().isDaytime()&&random.nextInt(50)==1&&world.getWorld().getEntitiesWithinAABB(CustomEntity.class,new net.minecraft.util.math.AxisAlignedBB(pos).grow(32,8,32)).isEmpty());}
    public static class EntityAttributesRegisterHandler{@SubscribeEvent public void onEntityAttributeCreation(EntityAttributeCreationEvent event){event.put(entity,CustomEntity.createAttributes().create());}}

    public static class CustomEntity extends OreSpawnAquaticBase {
        private int spray,sprayTimer;
        public CustomEntity(FMLPlayMessages.SpawnEntity packet,World world){this(entity,world);}
        public CustomEntity(EntityType<? extends CustomEntity> type,World world){super(type,world);experienceValue=40;}
        public static AttributeModifierMap.MutableAttribute createAttributes(){return MobEntity.func_233666_p_()
            .createMutableAttribute(Attributes.MAX_HEALTH,100D).createMutableAttribute(Attributes.MOVEMENT_SPEED,.35D)
            .createMutableAttribute(Attributes.ATTACK_DAMAGE,0D).createMutableAttribute(Attributes.FOLLOW_RANGE,20D);}
        @Override public IPacket<?> createSpawnPacket(){return NetworkHooks.getEntitySpawningPacket(this);}
        @Override protected void registerGoals(){super.registerGoals();goalSelector.addGoal(0,new SwimGoal(this));goalSelector.addGoal(2,new TemptGoal(this,1.2D,Ingredient.fromItems(Items.COD,Items.SALMON),false));goalSelector.addGoal(6,new OreSpawnWanderGoal(this,18,80,1D,false));goalSelector.addGoal(7,new LookAtGoal(this,PlayerEntity.class,8F));}
        @Override public void livingTick(){super.livingTick();if(spray==0){if(sprayTimer>0)--sprayTimer;if(sprayTimer==0){sprayTimer=250+rand.nextInt(250);spray=25+rand.nextInt(25);}}if(world.isRemote&&spray>0){for(int i=0;i<20;i++){double d=rand.nextDouble()*.75D;d*=d;double a=rand.nextDouble()*Math.PI*2D;double dx=Math.cos(a)*d/2D,dz=Math.sin(a)*d/2D;world.addParticle(i<10?ParticleTypes.BUBBLE:ParticleTypes.SPLASH,getPosX()+dx,getPosY()+1D+d,getPosZ()+dz,Math.cos(a+Math.PI/2D)*rand.nextFloat()/4D,rand.nextFloat()*2D,Math.sin(a+Math.PI/2D)*rand.nextFloat()/4D);}--spray;}if(!world.isRemote&&rand.nextInt(200)==1)heal(1F);}
        @Override protected void dropSpecialItems(DamageSource source,int looting,boolean recentlyHit){super.dropSpecialItems(source,looting,recentlyHit);for(int i=0;i<20+rand.nextInt(25);i++)entityDropItem(Items.COD);}
        @Override public boolean canBreatheUnderwater(){return true;}
        @Override protected SoundEvent getAmbientSound(){return SoundEvents.ENTITY_GENERIC_SPLASH;}
        @Override protected SoundEvent getHurtSound(DamageSource s){return OreSpawnLogic.sound("little_splat",SoundEvents.ENTITY_DOLPHIN_HURT);}
        @Override protected SoundEvent getDeathSound(){return OreSpawnLogic.sound("big_splat",SoundEvents.ENTITY_DOLPHIN_DEATH);}
    }
}
