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
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.SnowballEntity;
import net.minecraft.item.*;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.IPacket;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.*;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.Heightmap;
import net.mcreator.xillysorespawn.XillysOrespawnModElements;
import net.mcreator.xillysorespawn.entity.renderer.WaterDragonRenderer;

@XillysOrespawnModElements.ModElement.Tag
public class WaterDragonEntity extends XillysOrespawnModElements.ModElement {
    public static EntityType entity=(EntityType.Builder.<CustomEntity>create(CustomEntity::new,EntityClassification.CREATURE)
        .setShouldReceiveVelocityUpdates(true).setTrackingRange(128).setUpdateInterval(2).immuneToFire()
        .setCustomClientFactory(CustomEntity::new).size(1.25F,1.9F)).build("water_dragon").setRegistryName("water_dragon");
    public WaterDragonEntity(XillysOrespawnModElements instance){super(instance,93);FMLJavaModLoadingContext.get().getModEventBus().register(new WaterDragonRenderer.ModelRegisterHandler());FMLJavaModLoadingContext.get().getModEventBus().register(new EntityAttributesRegisterHandler());MinecraftForge.EVENT_BUS.register(this);}
    @Override public void initElements(){elements.entities.add(()->entity);elements.items.add(()->new SpawnEggItem(entity,-1,-1,new Item.Properties().group(ItemGroup.MISC)).setRegistryName("water_dragon_spawn_egg"));}
    @SubscribeEvent public void addFeatureToBiomes(BiomeLoadingEvent event){
        if (!OreSpawnLogic.allowNaturalSpawn(event, "water_dragon")) return;
        event.getSpawns().getSpawner(EntityClassification.CREATURE).add(new MobSpawnInfo.Spawners(entity,2,1,1));}
    @Override public void init(FMLCommonSetupEvent event){EntitySpawnPlacementRegistry.register(entity,EntitySpawnPlacementRegistry.PlacementType.ON_GROUND,Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,WaterDragonEntity::canSpawn);}
    public static boolean canSpawn(EntityType<? extends MobEntity> type,IServerWorld world,SpawnReason reason,BlockPos pos,Random random){return reason!=SpawnReason.NATURAL||(pos.getY()>=50&&world.getWorld().isDaytime()&&world.getWorld().getEntitiesWithinAABB(CustomEntity.class,new net.minecraft.util.math.AxisAlignedBB(pos).grow(16,5,16)).isEmpty());}
    public static class EntityAttributesRegisterHandler{@SubscribeEvent public void onEntityAttributeCreation(EntityAttributeCreationEvent event){event.put(entity,CustomEntity.createAttributes().create());}}

    public static class CustomEntity extends OreSpawnCreatureBase {
        private int hurtTimer,streamCount,dryTicks;
        public CustomEntity(FMLPlayMessages.SpawnEntity packet,World world){this(entity,world);}
        public CustomEntity(EntityType<? extends CustomEntity> type,World world){super(type,world);experienceValue=100;}
        public static AttributeModifierMap.MutableAttribute createAttributes(){return MobEntity.func_233666_p_()
            .createMutableAttribute(Attributes.MAX_HEALTH,150D).createMutableAttribute(Attributes.MOVEMENT_SPEED,.25D)
            .createMutableAttribute(Attributes.ATTACK_DAMAGE,20D).createMutableAttribute(Attributes.ARMOR,8D)
            .createMutableAttribute(Attributes.FOLLOW_RANGE,24D).createMutableAttribute(Attributes.KNOCKBACK_RESISTANCE,.4D);}
        @Override public IPacket<?> createSpawnPacket(){return NetworkHooks.getEntitySpawningPacket(this);}
        @Override protected void registerGoals(){super.registerGoals();goalSelector.addGoal(0,new SwimGoal(this));goalSelector.addGoal(2,new MeleeAttackGoal(this,1D,false));goalSelector.addGoal(3,new TemptGoal(this,1.2D,Ingredient.fromItems(Items.COD,Items.SALMON),false));goalSelector.addGoal(6,new OreSpawnWanderGoal(this,16,80,1D,false));goalSelector.addGoal(7,new LookAtGoal(this,PlayerEntity.class,8F));targetSelector.addGoal(0,new HurtByTargetGoal(this));}
        private boolean validTarget(LivingEntity t){if(!OreSpawnEntityBase.validHostileTarget(this,t)||t instanceof CustomEntity||isChild())return false;if(t instanceof MonsterEntity)return true;if(getOwner()!=null)return false;return t instanceof PlayerEntity||OreSpawnLogic.isAttackableNonMob(t);}
        private BlockPos findWater(){BlockPos o=getPosition(),best=null;double distance=Double.MAX_VALUE;for(int x=-11;x<=11;x++)for(int y=-10;y<=10;y++)for(int z=-11;z<=11;z++){BlockPos p=o.add(x,y,z);if(world.getFluidState(p).isTagged(FluidTags.WATER)){double d=p.distanceSq(o);if(d<distance){distance=d;best=p;}}}return best;}
        private void fireWater(LivingEntity target){if(streamCount<=0){if(rand.nextInt(4)==1)streamCount=8;else{setAttacking(0);return;}}setAttacking(2);SnowballEntity ball=new SnowballEntity(world,this);ball.setPosition(getPosX(),getPosY()+1.75D,getPosZ());ball.shoot(target.getPosX()-getPosX(),target.getPosY()+.75D-(getPosY()+1.75D),target.getPosZ()-getPosZ(),1.4F,5F);world.addEntity(ball);playSound(SoundEvents.ENTITY_ARROW_SHOOT,.75F,1F/(rand.nextFloat()*.4F+.8F));if(--streamCount%2==0)target.attackEntityFrom(DamageSource.causeMobDamage(this),4F);}
        @Override public void livingTick(){super.livingTick();getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(isInWater()?0.55D:0.25D);if(world.isRemote)return;if(hurtTimer>0)--hurtTimer;LivingEntity owner=getOwner();if(isSitting()){getNavigator().clearPath();setAttackTarget(null);return;}if(!isInWater()&&owner==null&&rand.nextInt(25)==0){BlockPos p=findWater();if(p!=null){dryTicks=0;getNavigator().tryMoveToXYZ(p.getX(),p.getY()-1,p.getZ(),1.33D);}else if(++dryTicks%50==0)attackEntityFrom(DamageSource.DRYOUT,1F);}else dryTicks=0;if(owner!=null&&getDistanceSq(owner)>16D)getNavigator().tryMoveToEntityLiving(owner,2D);if(world.getDifficulty()!=Difficulty.PEACEFUL&&OreSpawnLogic.playNicely==0&&ticksExisted%5==0){LivingEntity t=getAttackTarget();if(!validTarget(t))t=OreSpawnEntityBase.nearest(this,14D,4D,this::validTarget);setAttackTarget(t);if(t!=null){getLookController().setLookPositionWithEntity(t,10,10);double reach=4D+t.getWidth()/2D;if(getDistanceSq(t)<reach*reach){setAttacking(1);if(rand.nextInt(4)==0||rand.nextInt(5)==1)attackEntityAsMob(t);}else{getNavigator().tryMoveToEntityLiving(t,1D);fireWater(t);}}else setAttacking(0);}if(rand.nextInt(100)==1&&isInWater()&&getHealth()<getMaxHealth())heal(1F);}
        @Override public boolean attackEntityAsMob(Entity target){boolean hit=super.attackEntityAsMob(target);if(hit)OreSpawnEntityBase.knockAway(this,target,1.1D,.14D);return hit;}
        @Override public boolean attackEntityFrom(DamageSource source,float amount){Entity e=source.getTrueSource();if(source==DamageSource.CACTUS||e instanceof CustomEntity||e!=null&&OreSpawnLogic.isNamed(e,"attack_squid","water_ball"))return false;if(hurtTimer>0)return false;hurtTimer=10;boolean hit=super.attackEntityFrom(source,amount);if(e instanceof LivingEntity&&validTarget((LivingEntity)e)){setAttackTarget((LivingEntity)e);getNavigator().tryMoveToEntityLiving((LivingEntity)e,1.2D);}return hit;}
        @Override public ActionResultType func_230254_b_(PlayerEntity p,Hand h){ItemStack s=p.getHeldItem(h);if(s.getItem()==Items.COD||s.getItem()==Items.SALMON){if(!world.isRemote){if(getOwner()==null&&rand.nextInt(3)==0)setOwner(p);if(isOwner(p))heal(getMaxHealth());}if(!p.abilities.isCreativeMode)s.shrink(1);return ActionResultType.func_233537_a_(world.isRemote);}if(isOwner(p)&&s.getItem()==Items.GOLD_BLOCK){if(!world.isRemote){setOwner(null);setSitting(false);}if(!p.abilities.isCreativeMode)s.shrink(1);return ActionResultType.func_233537_a_(world.isRemote);}if(isOwner(p)){setSitting(!isSitting());return ActionResultType.SUCCESS;}return super.func_230254_b_(p,h);}
        @Override protected void dropSpecialItems(DamageSource source,int looting,boolean hit){super.dropSpecialItems(source,looting,hit);OreSpawnLogic.drop(world,this,"water_dragon_scale",1,2);entityDropItem(Items.NETHER_STAR);for(int i=0;i<3+rand.nextInt(6);i++)entityDropItem(Items.COD);}
        @Override public boolean canBreatheUnderwater(){return true;}
        @Override protected SoundEvent getHurtSound(DamageSource s){return OreSpawnLogic.sound("waterdragon_hurt"+(1+rand.nextInt(3)),SoundEvents.ENTITY_ELDER_GUARDIAN_HURT);}
        @Override protected SoundEvent getDeathSound(){return OreSpawnLogic.sound("waterdragon_death",SoundEvents.ENTITY_ELDER_GUARDIAN_DEATH);}
    }
}
