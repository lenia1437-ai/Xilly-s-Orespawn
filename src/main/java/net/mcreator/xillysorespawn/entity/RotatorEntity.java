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
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.*;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.Heightmap;
import net.mcreator.xillysorespawn.XillysOrespawnModElements;
import net.mcreator.xillysorespawn.entity.renderer.RotatorRenderer;

@XillysOrespawnModElements.ModElement.Tag
public class RotatorEntity extends XillysOrespawnModElements.ModElement {
    public static EntityType entity = (EntityType.Builder.<CustomEntity>create(CustomEntity::new,EntityClassification.MONSTER)
        .setShouldReceiveVelocityUpdates(true).setTrackingRange(128).setUpdateInterval(3)
        .setCustomClientFactory(CustomEntity::new).size(1F,2F))
        .build("rotator").setRegistryName("rotator");
    public RotatorEntity(XillysOrespawnModElements instance){super(instance,66);FMLJavaModLoadingContext.get().getModEventBus().register(new RotatorRenderer.ModelRegisterHandler());FMLJavaModLoadingContext.get().getModEventBus().register(new EntityAttributesRegisterHandler());MinecraftForge.EVENT_BUS.register(this);}
    @Override public void initElements(){elements.entities.add(()->entity);elements.items.add(()->new SpawnEggItem(entity,-1,-1,new Item.Properties().group(ItemGroup.MISC)).setRegistryName("rotator_spawn_egg"));}
    @SubscribeEvent public void addFeatureToBiomes(BiomeLoadingEvent event){
        if (!OreSpawnLogic.allowNaturalSpawn(event, "rotator")) return;
        event.getSpawns().getSpawner(EntityClassification.MONSTER).add(new MobSpawnInfo.Spawners(entity,3,1,1));}
    @Override public void init(FMLCommonSetupEvent event){EntitySpawnPlacementRegistry.register(entity,EntitySpawnPlacementRegistry.PlacementType.NO_RESTRICTIONS,Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,RotatorEntity::canSpawn);}
    public static boolean canSpawn(EntityType<? extends MobEntity> type,IServerWorld world,SpawnReason reason,BlockPos pos,Random random){return reason!=SpawnReason.NATURAL||(world.getDifficulty()!=Difficulty.PEACEFUL && (!world.getWorld().isDaytime()));}
    public static class EntityAttributesRegisterHandler{@SubscribeEvent public void onEntityAttributeCreation(EntityAttributeCreationEvent event){event.put(entity,CustomEntity.createAttributes().create());}}
    public static class CustomEntity extends OreSpawnFlyingMonsterBase {
        public CustomEntity(FMLPlayMessages.SpawnEntity packet, World world) { this(entity, world); }
        public CustomEntity(EntityType<? extends CustomEntity> type, World world) {
            super(type, world); this.experienceValue = 35;
        }
        public static AttributeModifierMap.MutableAttribute createAttributes() {
            return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 35D)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.25D)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 10D)
                .createMutableAttribute(Attributes.ARMOR, 8D)
                .createMutableAttribute(Attributes.FOLLOW_RANGE, 28D);
        }
        @Override public IPacket<?> createSpawnPacket() { return NetworkHooks.getEntitySpawningPacket(this); }
        @Override protected int horizontalRange(){return 18;} @Override protected int verticalRange(){return 3;} @Override protected int retargetFrequency(){return 300;}
        @Override protected double horizontalSpeed(){return .4;} @Override protected double horizontalBlend(){return .2;} @Override protected double verticalBlend(){return .2;}
        @Override protected double searchRange(){return 20;} @Override protected double meleeDistanceSq(){return 9;}
        @Override protected void originalFlightTick(){LivingEntity e=getAttackTarget();if(e!=null){double a=Math.atan2(e.getPosZ()-getPosZ(),e.getPosX()-getPosX())+Math.PI/2;flightTarget=new BlockPos(e.getPosX()+2.5*Math.cos(a),e.getPosY(),e.getPosZ()+2.5*Math.sin(a));}super.originalFlightTick();}
        @Override public boolean attackEntityFrom(DamageSource s,float a){return s.isProjectile()?false:super.attackEntityFrom(s,a);}
        @Override protected SoundEvent getAmbientSound(){return OreSpawnLogic.sound("rotator_living",SoundEvents.ENTITY_PHANTOM_FLAP);}
        @Override protected SoundEvent getHurtSound(DamageSource s){return OreSpawnLogic.sound("rotator_hurt",SoundEvents.ENTITY_PHANTOM_HURT);}
        @Override protected SoundEvent getDeathSound(){return OreSpawnLogic.sound("rotator_death",SoundEvents.ENTITY_PHANTOM_DEATH);}
    }
}
