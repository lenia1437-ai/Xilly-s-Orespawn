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
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.SnowballEntity;
import net.minecraft.item.*;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.*;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.Heightmap;
import net.mcreator.xillysorespawn.XillysOrespawnModElements;
import net.mcreator.xillysorespawn.entity.renderer.PitchBlackRenderer;

@XillysOrespawnModElements.ModElement.Tag
public class PitchBlackEntity extends XillysOrespawnModElements.ModElement {
    public static EntityType entity = (EntityType.Builder.<CustomEntity>create(CustomEntity::new,EntityClassification.MONSTER)
        .setShouldReceiveVelocityUpdates(true).setTrackingRange(128).setUpdateInterval(1)
        .setCustomClientFactory(CustomEntity::new).size(2.5F,3.5F))
        .build("pitch_black").setRegistryName("pitch_black");
    public PitchBlackEntity(XillysOrespawnModElements instance){super(instance,58);FMLJavaModLoadingContext.get().getModEventBus().register(new PitchBlackRenderer.ModelRegisterHandler());FMLJavaModLoadingContext.get().getModEventBus().register(new EntityAttributesRegisterHandler());MinecraftForge.EVENT_BUS.register(this);}
    @Override public void initElements(){elements.entities.add(()->entity);elements.items.add(()->new SpawnEggItem(entity,-1,-1,new Item.Properties().group(ItemGroup.MISC)).setRegistryName("pitch_black_spawn_egg"));}
    @SubscribeEvent public void addFeatureToBiomes(BiomeLoadingEvent event){
        if (!OreSpawnLogic.allowNaturalSpawn(event, "pitch_black")) return;
        event.getSpawns().getSpawner(EntityClassification.MONSTER).add(new MobSpawnInfo.Spawners(entity,1,1,1));}
    @Override public void init(FMLCommonSetupEvent event){EntitySpawnPlacementRegistry.register(entity,EntitySpawnPlacementRegistry.PlacementType.NO_RESTRICTIONS,Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,PitchBlackEntity::canSpawn);}
    public static boolean canSpawn(EntityType<? extends MobEntity> type,IServerWorld world,SpawnReason reason,BlockPos pos,Random random){return reason!=SpawnReason.NATURAL||(world.getDifficulty()!=Difficulty.PEACEFUL && (!world.getWorld().isDaytime() && pos.getY() >= 50));}
    public static class EntityAttributesRegisterHandler{@SubscribeEvent public void onEntityAttributeCreation(EntityAttributeCreationEvent event){event.put(entity,CustomEntity.createAttributes().create());}}
    public static class CustomEntity extends OreSpawnFlyingMonsterBase {
        public CustomEntity(FMLPlayMessages.SpawnEntity packet, World world) { this(entity, world); }
        public CustomEntity(EntityType<? extends CustomEntity> type, World world) {
            super(type, world); this.experienceValue = 200;
        }
        public static AttributeModifierMap.MutableAttribute createAttributes() {
            return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 1000D)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.4D)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 120D)
                .createMutableAttribute(Attributes.ARMOR, 18D)
                .createMutableAttribute(Attributes.FOLLOW_RANGE, 64D);
        }
        @Override public IPacket<?> createSpawnPacket() { return NetworkHooks.getEntitySpawningPacket(this); }
        private int damageTimer;
        {float s=.5F;if(rand.nextInt(4)==1)s=1;if(rand.nextInt(8)==2)s=2;if(rand.nextInt(32)==3)s=3;if(rand.nextInt(64)==4)s=4;setPitchBlackScale(s);recalculateSize();getAttribute(Attributes.MAX_HEALTH).setBaseValue(250D*s);getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(30D*s);getAttribute(Attributes.ARMOR).setBaseValue(10D+2D*s);setHealth(getMaxHealth());experienceValue=(int)(100*s);setActivity(1);}
        @Override public EntitySize getSize(Pose pose){float s=Math.max(0.5F,getPitchBlackScale());return EntitySize.flexible(2.5F*s,3.5F*s);}
        private float lastSizeScale;
        @Override public void notifyDataManagerChange(DataParameter<?> key){super.notifyDataManagerChange(key);float s=getPitchBlackScale();if(lastSizeScale!=s){lastSizeScale=s;recalculateSize();}}
        @Override protected int horizontalRange(){return 20+5*(int)getPitchBlackScale();} @Override protected int verticalRange(){return 5;} @Override protected int retargetFrequency(){return 150;}
        @Override protected double horizontalSpeed(){return .5D+getPitchBlackScale()/10D;} @Override protected double horizontalBlend(){return .33;} @Override protected double verticalBlend(){return .2;}
        @Override protected double searchRange(){return 16+getPitchBlackScale()*6;} @Override protected double verticalSearchRange(){return 10+getPitchBlackScale()*4;} @Override protected double meleeDistanceSq(){double d=5+getWidth()/2+getPitchBlackScale();return d*d;}
        @Override public boolean attackEntityAsMob(Entity e){boolean h=e.attackEntityFrom(DamageSource.causeMobDamage(this),(float)(30*getPitchBlackScale()));if(h){setAttacking(1);attackAnimationTicks=10;OreSpawnEntityBase.knockAway(this,e,1.15*getPitchBlackScale(),.08*getPitchBlackScale());}return h;}
        @Override public void livingTick(){super.livingTick();if(damageTimer>0)damageTimer--;if(!world.isRemote&&rand.nextInt(250)==1)heal(1+getPitchBlackScale());}
        @Override public boolean attackEntityFrom(DamageSource s,float a){if(damageTimer>0)return false;damageTimer=20;setActivity(1);return super.attackEntityFrom(s,a);}
        @Override protected SoundEvent getAmbientSound(){return OreSpawnLogic.sound("nightmare_living",SoundEvents.ENTITY_ENDER_DRAGON_AMBIENT);}
        @Override protected SoundEvent getHurtSound(DamageSource s){return OreSpawnLogic.sound("nightmare_hurt",SoundEvents.ENTITY_ENDER_DRAGON_HURT);}
        @Override protected SoundEvent getDeathSound(){return OreSpawnLogic.sound("nightmare_death",SoundEvents.ENTITY_ENDER_DRAGON_DEATH);}
        @Override protected void dropSpecialItems(DamageSource s,int l,boolean h){OreSpawnLogic.drop(world,this,"nightmare_scale",3+rand.nextInt(2+(int)(5*getPitchBlackScale())),6);OreSpawnLogic.drop(world,this,Items.IRON_INGOT,2+(int)getPitchBlackScale()+rand.nextInt(2+(int)(5*getPitchBlackScale())),6);}
    }
}
