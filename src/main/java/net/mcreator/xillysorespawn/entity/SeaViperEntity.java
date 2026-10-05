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
import net.mcreator.xillysorespawn.entity.renderer.SeaViperRenderer;

@XillysOrespawnModElements.ModElement.Tag
public class SeaViperEntity extends XillysOrespawnModElements.ModElement {
    public static EntityType entity=(EntityType.Builder.<CustomEntity>create(CustomEntity::new,EntityClassification.MONSTER)
        .setShouldReceiveVelocityUpdates(true).setTrackingRange(160).setUpdateInterval(1)
        .setCustomClientFactory(CustomEntity::new).size(1.5F,2.5F)).build("sea_viper").setRegistryName("sea_viper");
    public SeaViperEntity(XillysOrespawnModElements instance){super(instance,72);FMLJavaModLoadingContext.get().getModEventBus().register(new SeaViperRenderer.ModelRegisterHandler());FMLJavaModLoadingContext.get().getModEventBus().register(new EntityAttributesRegisterHandler());MinecraftForge.EVENT_BUS.register(this);}
    @Override public void initElements(){elements.entities.add(()->entity);elements.items.add(()->new SpawnEggItem(entity,-1,-1,new Item.Properties().group(ItemGroup.MISC)).setRegistryName("sea_viper_spawn_egg"));}
    @SubscribeEvent public void addFeatureToBiomes(BiomeLoadingEvent event){
        if (!OreSpawnLogic.allowNaturalSpawn(event, "sea_viper")) return;
        event.getSpawns().getSpawner(EntityClassification.MONSTER).add(new MobSpawnInfo.Spawners(entity,1,1,1));}
    @Override public void init(FMLCommonSetupEvent event){EntitySpawnPlacementRegistry.register(entity,EntitySpawnPlacementRegistry.PlacementType.IN_WATER,Heightmap.Type.OCEAN_FLOOR,SeaViperEntity::canSpawn);}
    public static boolean canSpawn(EntityType<? extends MobEntity> type,IServerWorld world,SpawnReason reason,BlockPos pos,Random random){return reason!=SpawnReason.NATURAL||(world.getDifficulty()!=Difficulty.PEACEFUL && (world.getWorld().isDaytime() && pos.getY() >= 50));}
    public static class EntityAttributesRegisterHandler{@SubscribeEvent public void onEntityAttributeCreation(EntityAttributeCreationEvent event){event.put(entity,CustomEntity.createAttributes().create());}}
    public static class CustomEntity extends OreSpawnAquaticMonsterBase {
        private int localTimer;
        public CustomEntity(FMLPlayMessages.SpawnEntity packet, World world) { this(entity, world); }
        public CustomEntity(EntityType<? extends CustomEntity> type, World world) {
            super(type, world); this.experienceValue = 120; 
        }
        public static AttributeModifierMap.MutableAttribute createAttributes() {
            return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 160D)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.35D)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 22D)
                .createMutableAttribute(Attributes.ARMOR, 12D)
                .createMutableAttribute(Attributes.FOLLOW_RANGE, 36D);
        }
        @Override public IPacket<?> createSpawnPacket() { return NetworkHooks.getEntitySpawningPacket(this); }
        @Override protected void registerGoals(){super.registerGoals();goalSelector.addGoal(0,new SwimGoal(this));goalSelector.addGoal(2,new MeleeAttackGoal(this,1.5,false));goalSelector.addGoal(5,new OreSpawnWanderGoal(this,16,100,1,false));targetSelector.addGoal(0,new HurtByTargetGoal(this));}
        private boolean prey(LivingEntity e){return OreSpawnEntityBase.validHostileTarget(this,e)&&!OreSpawnLogic.isNamed(e,"sea_viper")&&(e instanceof PlayerEntity||e instanceof MonsterEntity||OreSpawnLogic.isAttackableNonMob(e));}
        @Override public void livingTick(){super.livingTick();getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(isInWater()?.75D:.25D);if(!world.isRemote&&OreSpawnLogic.playNicely==0&&++localTimer%5==0){LivingEntity e=OreSpawnEntityBase.nearest(this,18,7,this::prey);setAttackTarget(e);if(e!=null){getNavigator().tryMoveToEntityLiving(e,1.5);if(getDistanceSq(e)<Math.pow(4.5+e.getWidth()/2,2))attackEntityAsMob(e);}}if(isInWater()&&rand.nextInt(100)==1)heal(1);}
        @Override public boolean attackEntityAsMob(Entity e){boolean h=super.attackEntityAsMob(e);if(h){OreSpawnEntityBase.knockAway(this,e,.8,.14);if(e instanceof LivingEntity&&rand.nextBoolean()){int seconds=world.getDifficulty()==Difficulty.HARD?12:world.getDifficulty()==Difficulty.NORMAL?10:8;((LivingEntity)e).addPotionEffect(new EffectInstance(Effects.POISON,seconds*20));}}return h;}
        @Override protected SoundEvent getAmbientSound(){return rand.nextBoolean()?OreSpawnLogic.sound("seaviper_living",SoundEvents.ENTITY_GUARDIAN_AMBIENT):null;}
        @Override protected SoundEvent getHurtSound(DamageSource s){return OreSpawnLogic.sound("seaviper_hit",SoundEvents.ENTITY_GUARDIAN_HURT);}
        @Override protected SoundEvent getDeathSound(){return OreSpawnLogic.sound("seaviper_death",SoundEvents.ENTITY_GUARDIAN_DEATH);}
        @Override protected void dropSpecialItems(DamageSource s,int l,boolean h){OreSpawnLogic.drop(world,this,"sea_viper_tongue",1,2);OreSpawnLogic.drop(world,this,Items.SALMON,9+rand.nextInt(6),4);}
    }
}
