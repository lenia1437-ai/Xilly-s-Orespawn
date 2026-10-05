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
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.SmallFireballEntity;
import net.minecraft.entity.projectile.SnowballEntity;
import net.minecraft.item.*;
import net.minecraft.network.IPacket;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.*;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.server.ServerWorld;
import net.mcreator.xillysorespawn.XillysOrespawnModElements;
import net.mcreator.xillysorespawn.entity.renderer.GammaMetroidRenderer;

@XillysOrespawnModElements.ModElement.Tag
public class GammaMetroidEntity extends XillysOrespawnModElements.ModElement {
    public static EntityType entity = (EntityType.Builder.<CustomEntity>create(CustomEntity::new, EntityClassification.CREATURE)
        .setShouldReceiveVelocityUpdates(true).setTrackingRange(128).setUpdateInterval(3)
        .setCustomClientFactory(CustomEntity::new).size(1.5F, 1.5F))
        .build("gamma_metroid").setRegistryName("gamma_metroid");
    public GammaMetroidEntity(XillysOrespawnModElements instance){super(instance,39);FMLJavaModLoadingContext.get().getModEventBus().register(new GammaMetroidRenderer.ModelRegisterHandler());FMLJavaModLoadingContext.get().getModEventBus().register(new EntityAttributesRegisterHandler());MinecraftForge.EVENT_BUS.register(this);}
    @Override public void initElements(){elements.entities.add(()->entity);elements.items.add(()->new SpawnEggItem(entity,-1,-1,new Item.Properties().group(ItemGroup.MISC)).setRegistryName("gamma_metroid_spawn_egg"));}
    @SubscribeEvent public void addFeatureToBiomes(BiomeLoadingEvent event){
        if (!OreSpawnLogic.allowNaturalSpawn(event, "gamma_metroid")) return;
        event.getSpawns().getSpawner(EntityClassification.CREATURE).add(new MobSpawnInfo.Spawners(entity,2,1,1));}
    @Override public void init(FMLCommonSetupEvent event){EntitySpawnPlacementRegistry.register(entity,EntitySpawnPlacementRegistry.PlacementType.ON_GROUND,Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,GammaMetroidEntity::canSpawn);}
    public static boolean canSpawn(EntityType<? extends MobEntity> type,IServerWorld world,SpawnReason reason,BlockPos pos,Random random){return (reason != SpawnReason.NATURAL || (!world.getWorld().isDaytime() && pos.getY() <= 50));}
    private static boolean openAir(IServerWorld world,BlockPos pos,int needed){int count=0;for(int x=-1;x<=1;x++)for(int z=-1;z<=1;z++)if(world.getWorld().isAirBlock(pos.add(x,0,z)))count++;return count>=needed;}
    public static class EntityAttributesRegisterHandler{@SubscribeEvent public void onEntityAttributeCreation(EntityAttributeCreationEvent event){event.put(entity,CustomEntity.createAttributes().create());}}
    public static class CustomEntity extends OreSpawnCreatureBase {
        public CustomEntity(FMLPlayMessages.SpawnEntity p,World w){this(entity,w);} public CustomEntity(EntityType<? extends CustomEntity> t,World w){super(t,w);experienceValue=20;}
        public static AttributeModifierMap.MutableAttribute createAttributes(){return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH,100D).createMutableAttribute(Attributes.MOVEMENT_SPEED,0.15D).createMutableAttribute(Attributes.ATTACK_DAMAGE,10D).createMutableAttribute(Attributes.ARMOR,12D).createMutableAttribute(Attributes.FOLLOW_RANGE,24D);}
        @Override public IPacket<?> createSpawnPacket(){return NetworkHooks.getEntitySpawningPacket(this);}
        @Override protected void registerGoals(){super.registerGoals();goalSelector.addGoal(0,new SwimGoal(this));goalSelector.addGoal(4,new OreSpawnWanderGoal(this,16,80,1,false));goalSelector.addGoal(5,new LookAtGoal(this,PlayerEntity.class,8));targetSelector.addGoal(1,new HurtByTargetGoal(this));}
        @Override public ActionResultType func_230254_b_(PlayerEntity p,Hand h){ItemStack s=p.getHeldItem(h);if(s.getItem()==Items.GOLD_INGOT){if(!world.isRemote&&((!isOwner(p)&&rand.nextInt(3)==0)||isOwner(p))){setOwner(p);heal(getMaxHealth());}if(!p.abilities.isCreativeMode)s.shrink(1);return ActionResultType.func_233537_a_(world.isRemote);}if(isOwner(p)&&s.getItem()==Blocks.GOLD_BLOCK.asItem()){if(!world.isRemote){ownerId=null;setSitting(false);}if(!p.abilities.isCreativeMode)s.shrink(1);return ActionResultType.func_233537_a_(world.isRemote);}if(isOwner(p)){setSitting(!isSitting());return ActionResultType.SUCCESS;}return super.func_230254_b_(p,h);}
        @Override public void livingTick(){super.livingTick();if(!world.isRemote){LivingEntity owner=getOwner();if(owner!=null&&!isSitting()&&getDistanceSq(owner)>4)getNavigator().tryMoveToEntityLiving(owner,2D);if(OreSpawnLogic.playNicely==0&&!isSitting()&&ownerId==null&&rand.nextInt(5)==0){LivingEntity e=OreSpawnEntityBase.nearest(this,10,3,t->OreSpawnEntityBase.validHostileTarget(this,t)&&!(t instanceof MonsterEntity)&&!OreSpawnLogic.isNamed(t,"gamma_metroid"));if(e!=null){getNavigator().tryMoveToEntityLiving(e,1.25D);if(getDistanceSq(e)<=9)attackEntityAsMob(e);}}}}
        @Override protected SoundEvent getAmbientSound(){return rand.nextInt(5)==1?OreSpawnLogic.sound("wtf_living",SoundEvents.ENTITY_SILVERFISH_AMBIENT):null;}
        @Override protected void dropSpecialItems(DamageSource s,int l,boolean h){OreSpawnLogic.drop(world,this,Items.GOLD_INGOT,5+rand.nextInt(10),4);OreSpawnLogic.drop(world,this,Items.IRON_INGOT,6+rand.nextInt(10),4);}
    }
}
