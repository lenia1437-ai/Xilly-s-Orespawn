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
import net.mcreator.xillysorespawn.entity.renderer.SpitBugRenderer;

@XillysOrespawnModElements.ModElement.Tag
public class SpitBugEntity extends XillysOrespawnModElements.ModElement {
    public static EntityType entity=(EntityType.Builder.<CustomEntity>create(CustomEntity::new,EntityClassification.MONSTER)
        .setShouldReceiveVelocityUpdates(true).setTrackingRange(160).setUpdateInterval(1)
        .setCustomClientFactory(CustomEntity::new).size(2F,2F)).build("spit_bug").setRegistryName("spit_bug");
    public SpitBugEntity(XillysOrespawnModElements instance){super(instance,75);FMLJavaModLoadingContext.get().getModEventBus().register(new SpitBugRenderer.ModelRegisterHandler());FMLJavaModLoadingContext.get().getModEventBus().register(new EntityAttributesRegisterHandler());MinecraftForge.EVENT_BUS.register(this);}
    @Override public void initElements(){elements.entities.add(()->entity);elements.items.add(()->new SpawnEggItem(entity,-1,-1,new Item.Properties().group(ItemGroup.MISC)).setRegistryName("spit_bug_spawn_egg"));}
    @SubscribeEvent public void addFeatureToBiomes(BiomeLoadingEvent event){
        if (!OreSpawnLogic.allowNaturalSpawn(event, "spit_bug")) return;
        event.getSpawns().getSpawner(EntityClassification.MONSTER).add(new MobSpawnInfo.Spawners(entity,2,1,2));}
    @Override public void init(FMLCommonSetupEvent event){EntitySpawnPlacementRegistry.register(entity,EntitySpawnPlacementRegistry.PlacementType.ON_GROUND,Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,SpitBugEntity::canSpawn);}
    public static boolean canSpawn(EntityType<? extends MobEntity> type,IServerWorld world,SpawnReason reason,BlockPos pos,Random random){return reason!=SpawnReason.NATURAL||(world.getDifficulty()!=Difficulty.PEACEFUL && (!world.getWorld().isDaytime() && pos.getY() >= 50));}
    public static class EntityAttributesRegisterHandler{@SubscribeEvent public void onEntityAttributeCreation(EntityAttributeCreationEvent event){event.put(entity,CustomEntity.createAttributes().create());}}
    public static class CustomEntity extends OreSpawnGroundMonsterBase {
        private int localTimer;
        public CustomEntity(FMLPlayMessages.SpawnEntity packet, World world) { this(entity, world); }
        public CustomEntity(EntityType<? extends CustomEntity> type, World world) {
            super(type, world); this.experienceValue = 50; 
        }
        public static AttributeModifierMap.MutableAttribute createAttributes() {
            return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 100D)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.33D)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 10D)
                .createMutableAttribute(Attributes.ARMOR, 12D)
                .createMutableAttribute(Attributes.FOLLOW_RANGE, 32D);
        }
        @Override public IPacket<?> createSpawnPacket() { return NetworkHooks.getEntitySpawningPacket(this); }
        private int hurtCooldown,shotCooldown;
        @Override protected double searchRange(){return 12;} @Override protected double verticalSearchRange(){return 7;} @Override protected double combatSpeed(){return .5;} @Override protected double meleeDistanceSq(){return 9;}
        @Override protected boolean originalTarget(LivingEntity e){return OreSpawnEntityBase.validHostileTarget(this,e)&&!OreSpawnLogic.isNamed(e,"hydrolisc","ender_reaper","ender_knight","enderman","creeper","trooper_bug","spit_bug");}
        @Override public boolean attackEntityAsMob(Entity e){boolean h=super.attackEntityAsMob(e);if(h)OreSpawnEntityBase.knockAway(this,e,.5,.1);return h;}
        @Override public boolean attackEntityFrom(DamageSource s,float a){if(s==DamageSource.CACTUS||s==DamageSource.FALL||hurtCooldown>0)return false;hurtCooldown=15;return super.attackEntityFrom(s,a);}
        @Override public void livingTick(){super.livingTick();if(hurtCooldown>0)hurtCooldown--;if(shotCooldown>0)shotCooldown--;LivingEntity e=getAttackTarget();if(!world.isRemote&&e!=null&&getDistanceSq(e)>=9&&getDistanceSq(e)<144&&shotCooldown==0){e.attackEntityFrom(DamageSource.causeMobDamage(this),5);Vector3d v=e.getPositionVec().subtract(getPositionVec()).normalize().scale(.35);e.setMotion(e.getMotion().add(v.x,.15,v.z));shotCooldown=30;world.playSound(null,getPosition(),SoundEvents.ENTITY_DROWNED_SHOOT,SoundCategory.HOSTILE,1,1);}if(rand.nextInt(150)==1)heal(1);}
        @Override protected SoundEvent getAmbientSound(){return OreSpawnLogic.sound("clatter",SoundEvents.ENTITY_SPIDER_AMBIENT);}
        @Override protected SoundEvent getDeathSound(){return OreSpawnLogic.sound("emperorscorpion_death",SoundEvents.ENTITY_SPIDER_DEATH);}
    }
}
