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
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.*;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.Heightmap;
import net.mcreator.xillysorespawn.XillysOrespawnModElements;
import net.mcreator.xillysorespawn.entity.renderer.VortexRenderer;

@XillysOrespawnModElements.ModElement.Tag
public class VortexEntity extends XillysOrespawnModElements.ModElement {
    public static EntityType entity=(EntityType.Builder.<CustomEntity>create(CustomEntity::new,EntityClassification.MONSTER)
        .setShouldReceiveVelocityUpdates(true).setTrackingRange(160).setUpdateInterval(1).immuneToFire()
        .setCustomClientFactory(CustomEntity::new).size(2F,4F)).build("vortex").setRegistryName("vortex");
    public VortexEntity(XillysOrespawnModElements instance){super(instance,92);FMLJavaModLoadingContext.get().getModEventBus().register(new VortexRenderer.ModelRegisterHandler());FMLJavaModLoadingContext.get().getModEventBus().register(new EntityAttributesRegisterHandler());MinecraftForge.EVENT_BUS.register(this);}
    @Override public void initElements(){elements.entities.add(()->entity);elements.items.add(()->new SpawnEggItem(entity,-1,-1,new Item.Properties().group(ItemGroup.MISC)).setRegistryName("vortex_spawn_egg"));}
    @SubscribeEvent public void addFeatureToBiomes(BiomeLoadingEvent event){
        if (!OreSpawnLogic.allowNaturalSpawn(event, "vortex")) return;
        event.getSpawns().getSpawner(EntityClassification.MONSTER).add(new MobSpawnInfo.Spawners(entity,2,1,1));}
    @Override public void init(FMLCommonSetupEvent event){EntitySpawnPlacementRegistry.register(entity,EntitySpawnPlacementRegistry.PlacementType.NO_RESTRICTIONS,Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,VortexEntity::canSpawn);}
    public static boolean canSpawn(EntityType<? extends MobEntity> type,IServerWorld world,SpawnReason reason,BlockPos pos,Random random){if(reason!=SpawnReason.NATURAL)return true;if(pos.getY()<50||world.getWorld().isDaytime()||random.nextBoolean())return false;for(BlockPos p:BlockPos.getAllInBoxMutable(pos.add(-2,1,-2),pos.add(2,3,2)))if(!world.getWorld().isAirBlock(p))return false;return world.getWorld().getEntitiesWithinAABB(CustomEntity.class,new net.minecraft.util.math.AxisAlignedBB(pos).grow(20,16,20)).isEmpty();}
    public static class EntityAttributesRegisterHandler{@SubscribeEvent public void onEntityAttributeCreation(EntityAttributeCreationEvent event){event.put(entity,CustomEntity.createAttributes().create());}}

    public static class CustomEntity extends OreSpawnFlyingMonsterBase {
        private int winded;
        public CustomEntity(FMLPlayMessages.SpawnEntity packet,World world){this(entity,world);}
        public CustomEntity(EntityType<? extends CustomEntity> type,World world){super(type,world);experienceValue=200;}
        public static AttributeModifierMap.MutableAttribute createAttributes(){return MobEntity.func_233666_p_()
            .createMutableAttribute(Attributes.MAX_HEALTH,150D).createMutableAttribute(Attributes.MOVEMENT_SPEED,.35D)
            .createMutableAttribute(Attributes.ATTACK_DAMAGE,26D).createMutableAttribute(Attributes.ARMOR,10D)
            .createMutableAttribute(Attributes.FOLLOW_RANGE,24D).createMutableAttribute(Attributes.KNOCKBACK_RESISTANCE,.8D);}
        @Override public IPacket<?> createSpawnPacket(){return NetworkHooks.getEntitySpawningPacket(this);}
        @Override protected int horizontalRange(){return 23;}@Override protected int verticalRange(){return 3;}@Override protected int retargetFrequency(){return 300;}
        @Override protected double horizontalSpeed(){return .4D;}@Override protected double verticalSpeed(){return .7D;}@Override protected double horizontalBlend(){return .2D;}@Override protected double verticalBlend(){return .2D;}
        @Override protected double searchRange(){return 16D;}@Override protected double verticalSearchRange(){return 10D;}@Override protected double meleeDistanceSq(){return 0D;}@Override protected int targetInterval(){return 1;}
        @Override protected boolean originalTarget(LivingEntity t){return OreSpawnEntityBase.validHostileTarget(this,t)&&!OreSpawnLogic.isNamed(t,"vortex","rotator","mothra","brutalfly","peacock","crystal_cow","irukandji","skate","whale","flounder","urchin");}
        @Override protected void originalFlightTick(){if(winded>0)--winded;LivingEntity target=getAttackTarget();super.originalFlightTick();target=getAttackTarget();if(target!=null){double d=getDistanceSq(target);if(d<81D&&winded==0){double distance=Math.sqrt(d),angle=Math.atan2(getPosZ()-target.getPosZ(),getPosX()-target.getPosX());double strength=(10D-distance)*.1D;double vertical=strength*.5D*(target instanceof PlayerEntity?2D:1D);target.setMotion(target.getMotion().add(Math.cos(angle)*strength,vertical,Math.sin(angle)*strength));target.velocityChanged=true;}double reach=4D+target.getWidth()/2D;if(d<reach*reach&&rand.nextInt(8)==2)attackEntityAsMob(target);}}
        @Override public void livingTick(){super.livingTick();setMotion(getMotion().mul(1D,.6D,1D));if(world.isRemote&&getAttackTarget()!=null)for(int i=0;i<20;i++){double d=rand.nextDouble()*3.5D;d*=d;double a=rand.nextDouble()*Math.PI*2D;world.addParticle(ParticleTypes.SMOKE,getPosX()+Math.cos(a)*d/2D,getPosY()+.75D+d,getPosZ()+Math.sin(a)*d/2D,0,rand.nextFloat()/2D,0);}if(!world.isRemote){if(rand.nextInt(200)==1)heal(1F);if(getAttackTarget()==null&&!isNoDespawnRequired()&&world.isDaytime()&&rand.nextInt(500)==1)remove();}}
        @Override public boolean attackEntityFrom(DamageSource source,float amount){Entity attacker=source.getTrueSource();boolean hit=super.attackEntityFrom(source,amount);if(attacker!=null){flightTarget=attacker.getPosition();winded=20;if(attacker instanceof LivingEntity)setAttackTarget((LivingEntity)attacker);}return hit;}
        @Override protected void dropSpecialItems(DamageSource source,int looting,boolean recentlyHit){super.dropSpecialItems(source,looting,recentlyHit);OreSpawnLogic.drop(world,this,"vortex_eye",1,1);entityDropItem(Items.NETHER_STAR);for(int i=0;i<5+rand.nextInt(7);i++)entityDropItem(Items.GOLD_INGOT);for(int i=0;i<3+rand.nextInt(5);i++)entityDropItem(Items.DIAMOND);}
        @Override protected SoundEvent getAmbientSound(){return OreSpawnLogic.sound("vortexlive",SoundEvents.ENTITY_PHANTOM_FLAP);}
        @Override protected SoundEvent getDeathSound(){return OreSpawnLogic.sound("vortexlive",SoundEvents.ENTITY_PHANTOM_DEATH);}
    }
}
