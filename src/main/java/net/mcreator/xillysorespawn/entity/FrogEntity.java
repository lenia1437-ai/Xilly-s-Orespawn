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
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.*;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.server.ServerWorld;
import net.mcreator.xillysorespawn.XillysOrespawnModElements;
import net.mcreator.xillysorespawn.entity.renderer.FrogRenderer;

@XillysOrespawnModElements.ModElement.Tag
public class FrogEntity extends XillysOrespawnModElements.ModElement {
    public static EntityType entity = (EntityType.Builder.<CustomEntity>create(CustomEntity::new, EntityClassification.CREATURE)
        .setShouldReceiveVelocityUpdates(true).setTrackingRange(128).setUpdateInterval(3)
        .setCustomClientFactory(CustomEntity::new).size(0.75F, 0.75F))
        .build("frog").setRegistryName("frog");
    public FrogEntity(XillysOrespawnModElements instance){super(instance,38);FMLJavaModLoadingContext.get().getModEventBus().register(new FrogRenderer.ModelRegisterHandler());FMLJavaModLoadingContext.get().getModEventBus().register(new EntityAttributesRegisterHandler());MinecraftForge.EVENT_BUS.register(this);}
    @Override public void initElements(){elements.entities.add(()->entity);elements.items.add(()->new SpawnEggItem(entity,-1,-1,new Item.Properties().group(ItemGroup.MISC)).setRegistryName("frog_spawn_egg"));}
    @SubscribeEvent public void addFeatureToBiomes(BiomeLoadingEvent event){
        if (!OreSpawnLogic.allowNaturalSpawn(event, "frog")) return;
        event.getSpawns().getSpawner(EntityClassification.CREATURE).add(new MobSpawnInfo.Spawners(entity,10,1,3));}
    @Override public void init(FMLCommonSetupEvent event){EntitySpawnPlacementRegistry.register(entity,EntitySpawnPlacementRegistry.PlacementType.ON_GROUND,Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,FrogEntity::canSpawn);}
    public static boolean canSpawn(EntityType<? extends MobEntity> type,IServerWorld world,SpawnReason reason,BlockPos pos,Random random){
        if(reason==SpawnReason.SPAWN_EGG||reason==SpawnReason.SPAWNER||reason==SpawnReason.COMMAND||reason==SpawnReason.STRUCTURE)return true;
        // Never query ServerWorld entities from the asynchronous chunk
        // population pass. That query intermittently stalled creation of a new
        // overworld at an arbitrary spawn-preparation percentage. Frogs will
        // enter the world through the normal server-thread spawn cycle instead.
        if(reason==SpawnReason.CHUNK_GENERATION)return false;
        if(!world.getWorld().isDaytime()||pos.getY()<50)return false;
        // Exact 1.7.10 population guard: at most five nearby frogs. The old
        // port bypassed every check for CHUNK_GENERATION, so each new Utopia
        // chunk could add another full group until hundreds accumulated.
        AxisAlignedBB area=new AxisAlignedBB(pos).grow(20.0D,8.0D,20.0D);
        return world.getWorld().getEntitiesWithinAABB(CustomEntity.class,area).size()<=5;
    }
    private static boolean openAir(IServerWorld world,BlockPos pos,int needed){int count=0;for(int x=-1;x<=1;x++)for(int z=-1;z<=1;z++)if(world.getWorld().isAirBlock(pos.add(x,0,z)))count++;return count>=needed;}
    public static class EntityAttributesRegisterHandler{@SubscribeEvent public void onEntityAttributeCreation(EntityAttributeCreationEvent event){event.put(entity,CustomEntity.createAttributes().create());}}
    public static class CustomEntity extends OreSpawnCreatureBase {
        private int jumpCooldown;
        public CustomEntity(FMLPlayMessages.SpawnEntity p,World w){this(entity,w);} public CustomEntity(EntityType<? extends CustomEntity> t,World w){super(t,w);experienceValue=5;}
        public static AttributeModifierMap.MutableAttribute createAttributes(){return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH,8D).createMutableAttribute(Attributes.MOVEMENT_SPEED,0.1D).createMutableAttribute(Attributes.ATTACK_DAMAGE,3D).createMutableAttribute(Attributes.FOLLOW_RANGE,16D);}
        @Override public IPacket<?> createSpawnPacket(){return NetworkHooks.getEntitySpawningPacket(this);}
        @Override protected void registerGoals(){super.registerGoals();goalSelector.addGoal(0,new SwimGoal(this));goalSelector.addGoal(1,new PanicGoal(this,1.4D));goalSelector.addGoal(2,new OreSpawnWanderGoal(this,10,100,1.0D,false));}
        private void jumpAround(){float f=.7F+Math.abs(rand.nextFloat()*.75F);float a=(float)Math.toRadians(rotationYaw);setMotion(getMotion().add(-f*Math.sin(a),.75F+Math.abs(rand.nextFloat()*.55F),f*Math.cos(a)));velocityChanged=true;}
        @Override public void livingTick(){super.livingTick();if(!world.isRemote){if(jumpCooldown>0)jumpCooldown--;if(jumpCooldown==0&&rand.nextInt(70)==1){jumpAround();jumpCooldown=50;}if(rand.nextInt(12)==0&&OreSpawnLogic.playNicely==0){LivingEntity e=OreSpawnEntityBase.nearest(this,8,3,t->OreSpawnLogic.isNamed(t,"ant","butterfly","cricket","mosquito","firefly","worm_small"));if(e!=null){getNavigator().tryMoveToEntityLiving(e,1.25D);if(getDistanceSq(e)<6)attackEntityAsMob(e);}}}}
        @Override public boolean attackEntityFrom(DamageSource s,float a){boolean h=super.attackEntityFrom(s,a);if(h&&jumpCooldown<=0){jumpAround();jumpCooldown=25;}return h;}
        @Override public ActionResultType func_230254_b_(PlayerEntity p,Hand h){if(p.isSneaking()&&p.getHeldItem(h).isEmpty()){if(!world.isRemote){OreSpawnLogic.spawn(world,rand.nextBoolean()?"boyfriend":"girlfriend",getPosX(),getPosY()+.01D,getPosZ());remove();}world.playSound(null,getPosition(),SoundEvents.ENTITY_GENERIC_EXPLODE,SoundCategory.NEUTRAL,1F,.9F+rand.nextFloat()*.2F);return ActionResultType.func_233537_a_(world.isRemote);}return super.func_230254_b_(p,h);}
        @Override protected SoundEvent getAmbientSound(){if(!world.isRemote)setSinging(35);return OreSpawnLogic.sound("frog",SoundEvents.ENTITY_SLIME_SQUISH);}
        @Override protected SoundEvent getHurtSound(DamageSource s){return OreSpawnLogic.sound("scorpion_hit",SoundEvents.ENTITY_SLIME_HURT);}
        @Override protected SoundEvent getDeathSound(){return OreSpawnLogic.sound("big_splat",SoundEvents.ENTITY_SLIME_DEATH);}
        @Override protected void dropSpecialItems(DamageSource s,int l,boolean h){for(int i=0;i<4;i++)entityDropItem(Items.SLIME_BALL);}
    }
}
