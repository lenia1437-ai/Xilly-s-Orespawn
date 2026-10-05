package net.mcreator.xillysorespawn.entity;

import java.util.Random;
import javax.annotation.Nullable;
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
import net.minecraft.nbt.CompoundNBT;
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
import net.mcreator.xillysorespawn.entity.renderer.KrakenRenderer;

@XillysOrespawnModElements.ModElement.Tag
public class KrakenEntity extends XillysOrespawnModElements.ModElement {
    public static EntityType entity = (EntityType.Builder.<CustomEntity>create(CustomEntity::new, EntityClassification.MONSTER)
        .setShouldReceiveVelocityUpdates(true).setTrackingRange(128).setUpdateInterval(3)
        .setCustomClientFactory(CustomEntity::new).size(4F, 15F))
        .build("kraken").setRegistryName("kraken");
    public KrakenEntity(XillysOrespawnModElements instance){super(instance,47);FMLJavaModLoadingContext.get().getModEventBus().register(new KrakenRenderer.ModelRegisterHandler());FMLJavaModLoadingContext.get().getModEventBus().register(new EntityAttributesRegisterHandler());MinecraftForge.EVENT_BUS.register(this);}
    @Override public void initElements(){elements.entities.add(()->entity);elements.items.add(()->new SpawnEggItem(entity,-1,-1,new Item.Properties().group(ItemGroup.MISC)).setRegistryName("kraken_spawn_egg"));}
    @SubscribeEvent public void addFeatureToBiomes(BiomeLoadingEvent event){
        if (!OreSpawnLogic.allowNaturalSpawn(event, "kraken")) return;
        event.getSpawns().getSpawner(EntityClassification.MONSTER).add(new MobSpawnInfo.Spawners(entity,1,1,1));}
    @Override public void init(FMLCommonSetupEvent event){EntitySpawnPlacementRegistry.register(entity,EntitySpawnPlacementRegistry.PlacementType.NO_RESTRICTIONS,Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,KrakenEntity::canSpawn);}
    public static boolean canSpawn(EntityType<? extends MobEntity> type,IServerWorld world,SpawnReason reason,BlockPos pos,Random random){return world.getDifficulty()!=Difficulty.PEACEFUL && (reason != SpawnReason.NATURAL || (!world.getWorld().isDaytime() && pos.getY() >= 50));}
    private static boolean openAir(IServerWorld world,BlockPos pos,int needed){int count=0;for(int x=-1;x<=1;x++)for(int z=-1;z<=1;z++)if(world.getWorld().isAirBlock(pos.add(x,0,z)))count++;return count>=needed;}
    public static class EntityAttributesRegisterHandler{@SubscribeEvent public void onEntityAttributeCreation(EntityAttributeCreationEvent event){event.put(entity,CustomEntity.createAttributes().create());}}
    public static class CustomEntity extends OreSpawnFlyingMonsterBase {
        public CustomEntity(FMLPlayMessages.SpawnEntity packet, World world) { this(entity, world); }
        public CustomEntity(EntityType<? extends CustomEntity> type, World world) {
            super(type, world); this.experienceValue = 500; 
        }
        public static AttributeModifierMap.MutableAttribute createAttributes() {
            return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 1000D)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.37D)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 40D)
                .createMutableAttribute(Attributes.ARMOR, 10D)
                .createMutableAttribute(Attributes.FOLLOW_RANGE, 48D);
        }
        @Override public IPacket<?> createSpawnPacket() { return NetworkHooks.getEntitySpawningPacket(this); }
        @Override @Nullable public ILivingEntityData onInitialSpawn(IServerWorld world, DifficultyInstance difficulty,
                SpawnReason reason, @Nullable ILivingEntityData spawnData, @Nullable CompoundNBT dataTag) {
            ILivingEntityData data = super.onInitialSpawn(world, difficulty, reason, spawnData, dataTag);
            world.getWorld().func_241113_a_(0, 6000, true, true);
            return data;
        }
        @Override protected double horizontalSpeed() { return 0.45; }
        @Override protected double verticalSpeed() { return 0.71; }
        @Override protected double horizontalBlend() { return 0.15; }
        @Override protected double verticalBlend() { return 0.202; }
        @Override protected int horizontalRange() { return 18; }
        @Override protected int retargetFrequency() { return 250; }        private LivingEntity caught; private int release; private int longEnough=3600; private boolean hitByPlayer;
        @Override protected void chooseFlightTarget(){int bias=getPosY()<20?14:getPosY()>200?-12:0;BlockPos p=OreSpawnEntityBase.randomAirTarget(this,18,6,50,bias);if(p!=null)flightTarget=p;}
        @Override protected void originalFlightTick(){
            if(longEnough>0)longEnough--;
            LivingEntity target=getAttackTarget();
            if(target!=null&&(!target.isAlive()||getDistanceSq(target)>16384))target=null;
            if(OreSpawnLogic.playNicely!=0){target=null;caught=null;}
            else if(target==null||ticksExisted%20==0)target=OreSpawnEntityBase.nearest(this,48,80,t->OreSpawnEntityBase.validHostileTarget(this,t)&&!OreSpawnLogic.isNamed(t,"kraken"));
            setAttackTarget(target);
            if(caught!=null&&(!caught.isAlive()||++release>250)){caught=null;release=0;}
            if(caught!=null){
                flightTarget=new BlockPos(getPosX(),Math.min(220,getPosY()+12),getPosZ());
                caught.setPosition(getPosX(),getPosY()-15,getPosZ());caught.setMotion(getMotion());
                if(ticksExisted%20==0)attackEntityAsMob(caught);
            }else if(target!=null){
                // Do not invoke the generic chase afterwards: it overwrites this altitude.
                flightTarget=new BlockPos(target.getPosX(),Math.min(240,target.getPosY()+target.getHeight()+15),target.getPosZ());
                double dx=getPosX()-target.getPosX(),dz=getPosZ()-target.getPosZ(),dy=getPosY()-target.getPosY();
                if(dx*dx+dz*dz<16&&dy>8&&dy<19){caught=target;release=0;}
            }else if(flightTarget==null||ticksExisted%80==0||getDistanceSq(Vector3d.copyCentered(flightTarget))<9)chooseFlightTarget();
            setAttacking(target!=null||caught!=null?1:0);
            OreSpawnEntityBase.steerFlight(this,flightTarget,horizontalSpeed(),verticalSpeed(),horizontalBlend(),verticalBlend(),yawDivisor());
        }
        @Override public boolean attackEntityFrom(DamageSource s,float a){if(s.getTrueSource() instanceof PlayerEntity)hitByPlayer=true;return super.attackEntityFrom(s,a);}
        @Override protected SoundEvent getAmbientSound(){return rand.nextInt(3)==0?OreSpawnLogic.sound("kraken_living",SoundEvents.ENTITY_GHAST_AMBIENT):null;}
        @Override protected SoundEvent getHurtSound(DamageSource s){return OreSpawnLogic.sound("kraken_hurt",SoundEvents.ENTITY_GHAST_HURT);}
        @Override protected SoundEvent getDeathSound(){return OreSpawnLogic.sound("kraken_death",SoundEvents.ENTITY_GHAST_DEATH);}
        @Override protected void dropSpecialItems(DamageSource s,int l,boolean h){OreSpawnLogic.drop(world,this,"kraken_tooth",120+rand.nextInt(160),8);OreSpawnLogic.drop(world,this,Items.INK_SAC,5+rand.nextInt(10),8);}
    }
}
