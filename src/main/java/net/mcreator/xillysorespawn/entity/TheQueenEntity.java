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
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.IPacket;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.*;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.Heightmap;
import net.mcreator.xillysorespawn.XillysOrespawnModElements;
import net.mcreator.xillysorespawn.entity.renderer.TheQueenRenderer;

@XillysOrespawnModElements.ModElement.Tag
public class TheQueenEntity extends XillysOrespawnModElements.ModElement {
    public static EntityType entity=(EntityType.Builder.<CustomEntity>create(CustomEntity::new,EntityClassification.MONSTER)
        .setShouldReceiveVelocityUpdates(true).setTrackingRange(160).setUpdateInterval(1)
        .setCustomClientFactory(CustomEntity::new).size(22F,24F)).build("the_queen").setRegistryName("the_queen");
    public TheQueenEntity(XillysOrespawnModElements instance){super(instance,85);FMLJavaModLoadingContext.get().getModEventBus().register(new TheQueenRenderer.ModelRegisterHandler());FMLJavaModLoadingContext.get().getModEventBus().register(new EntityAttributesRegisterHandler());MinecraftForge.EVENT_BUS.register(this);}
    @Override public void initElements(){elements.entities.add(()->entity);elements.items.add(()->new SpawnEggItem(entity,-1,-1,new Item.Properties().group(ItemGroup.MISC)).setRegistryName("the_queen_spawn_egg"));}
    @SubscribeEvent public void addFeatureToBiomes(BiomeLoadingEvent event){
        if (!OreSpawnLogic.allowNaturalSpawn(event, "the_queen")) return;
        event.getSpawns().getSpawner(EntityClassification.MONSTER).add(new MobSpawnInfo.Spawners(entity,1,1,1));}
    @Override public void init(FMLCommonSetupEvent event){EntitySpawnPlacementRegistry.register(entity,EntitySpawnPlacementRegistry.PlacementType.NO_RESTRICTIONS,Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,TheQueenEntity::canSpawn);}
    public static boolean canSpawn(EntityType<? extends MobEntity> type,IServerWorld world,SpawnReason reason,BlockPos pos,Random random){return reason!=SpawnReason.NATURAL||(world.getDifficulty()!=Difficulty.PEACEFUL && (OreSpawnLogic.isCrystalDimension(world.getWorld()) && pos.getY() >= 80 && random.nextInt(200)==1));}
    public static class EntityAttributesRegisterHandler{@SubscribeEvent public void onEntityAttributeCreation(EntityAttributeCreationEvent event){event.put(entity,CustomEntity.createAttributes().create());}}
    public static class CustomEntity extends OreSpawnFlyingMonsterBase {
        private int localTimer;
        public CustomEntity(FMLPlayMessages.SpawnEntity packet, World world) { this(entity, world); }
        public CustomEntity(EntityType<? extends CustomEntity> type, World world) {
            super(type, world); this.experienceValue = 25000; 
        }
        public static AttributeModifierMap.MutableAttribute createAttributes() {
            return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 6000D)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.32D)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 225D)
                .createMutableAttribute(Attributes.ARMOR, 21D)
                .createMutableAttribute(Attributes.FOLLOW_RANGE, 128D);
        }
        @Override public IPacket<?> createSpawnPacket() { return NetworkHooks.getEntitySpawningPacket(this); }
        private int summonCooldown,blastCooldown;
        { setActivity(1); noClip=true; }
        @Override protected void originalFlightTick(){noClip=true;flightTarget=OreSpawnEntityBase.royalFlight(this,flightTarget,0.7D,this::originalTarget);boolean angry=getAttackTarget()!=null;setAttacking(angry?1:0);setActivity(angry?0:1);}
        @Override protected int horizontalRange(){return 40;} @Override protected int verticalRange(){return 18;} @Override protected int retargetFrequency(){return 80;} @Override protected double horizontalSpeed(){return .42;} @Override protected double horizontalBlend(){return .12;} @Override protected double verticalBlend(){return .1;} @Override protected double searchRange(){return 72;} @Override protected double verticalSearchRange(){return 40;} @Override protected double meleeDistanceSq(){return 196;}
        @Override protected boolean originalTarget(LivingEntity e){return OreSpawnEntityBase.royalTarget(this,e);}
        @Override public EntitySize getSize(Pose pose){return OreSpawnLogic.playNicely!=0?EntitySize.flexible(5.5F,6F):super.getSize(pose);}
        @Override public boolean attackEntityAsMob(Entity e){boolean h=super.attackEntityAsMob(e);if(h)OreSpawnEntityBase.knockAway(this,e,3,.6);return h;}
        @Override public boolean attackEntityFrom(DamageSource s,float a){if(s==DamageSource.IN_WALL||s==DamageSource.FALL||s==DamageSource.CACTUS||s==DamageSource.IN_FIRE||s==DamageSource.ON_FIRE)return false;return super.attackEntityFrom(s,Math.min(a,100));}
        @Override public void livingTick(){super.livingTick();if(summonCooldown>0)summonCooldown--;if(blastCooldown>0)blastCooldown--;LivingEntity e=getAttackTarget();if(!world.isRemote&&e!=null){if(blastCooldown==0&&getDistanceSq(e)<1600){for(LivingEntity x:world.getEntitiesWithinAABB(LivingEntity.class,e.getBoundingBox().grow(5),v->v!=this&&originalTarget(v)))x.attackEntityFrom(DamageSource.causeMobDamage(this),35);OreSpawnLogic.spawn(world,"purple_power",e.getPosX(),e.getPosY()+1,e.getPosZ());blastCooldown=40;}if(summonCooldown==0){OreSpawnLogic.spawn(world,"the_princess",getPosX()+rand.nextInt(9)-4,getPosY()+2,getPosZ()+rand.nextInt(9)-4);summonCooldown=240;}if(OreSpawnLogic.playNicely==0&&world.getGameRules().getBoolean(GameRules.MOB_GRIEFING)&&rand.nextInt(12)==0){BlockPos p=e.getPosition().add(rand.nextInt(9)-4,rand.nextInt(7)-3,rand.nextInt(9)-4);BlockState b=world.getBlockState(p);if(!b.isAir()&&b.getBlockHardness(world,p)>=0&&b.getBlockHardness(world,p)<20)world.destroyBlock(p,false,this);}}if(rand.nextInt(80)==1)heal(1);}
        @Override protected SoundEvent getAmbientSound(){return OreSpawnLogic.sound("king_living",SoundEvents.ENTITY_ENDER_DRAGON_AMBIENT);}
        @Override protected SoundEvent getHurtSound(DamageSource s){return OreSpawnLogic.sound("king_hit",SoundEvents.ENTITY_ENDER_DRAGON_HURT);}
        @Override protected SoundEvent getDeathSound(){return OreSpawnLogic.sound("trex_death",SoundEvents.ENTITY_ENDER_DRAGON_DEATH);}
        @Override protected void dropSpecialItems(DamageSource s,int l,boolean h){OreSpawnLogic.drop(world,this,"royal_guardian_sword",1,1);OreSpawnLogic.drop(world,this,Items.DIAMOND,32+rand.nextInt(32),12);OreSpawnLogic.drop(world,this,Items.EMERALD,32+rand.nextInt(32),12);}
    }
}
