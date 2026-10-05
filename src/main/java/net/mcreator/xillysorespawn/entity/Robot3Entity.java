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
import net.mcreator.xillysorespawn.entity.renderer.Robot3Renderer;

@XillysOrespawnModElements.ModElement.Tag
public class Robot3Entity extends XillysOrespawnModElements.ModElement {
    public static EntityType entity = (EntityType.Builder.<CustomEntity>create(CustomEntity::new,EntityClassification.MONSTER)
        .setShouldReceiveVelocityUpdates(true).setTrackingRange(128).setUpdateInterval(3)
        .setCustomClientFactory(CustomEntity::new).size(2.5F,5F))
        .build("robot_3").setRegistryName("robot_3");
    public Robot3Entity(XillysOrespawnModElements instance){super(instance,63);FMLJavaModLoadingContext.get().getModEventBus().register(new Robot3Renderer.ModelRegisterHandler());FMLJavaModLoadingContext.get().getModEventBus().register(new EntityAttributesRegisterHandler());MinecraftForge.EVENT_BUS.register(this);}
    @Override public void initElements(){elements.entities.add(()->entity);elements.items.add(()->new SpawnEggItem(entity,-1,-1,new Item.Properties().group(ItemGroup.MISC)).setRegistryName("robot_3_spawn_egg"));}
    @SubscribeEvent public void addFeatureToBiomes(BiomeLoadingEvent event){
        if (!OreSpawnLogic.allowNaturalSpawn(event, "robot_3")) return;
        event.getSpawns().getSpawner(EntityClassification.MONSTER).add(new MobSpawnInfo.Spawners(entity,2,1,1));}
    @Override public void init(FMLCommonSetupEvent event){EntitySpawnPlacementRegistry.register(entity,EntitySpawnPlacementRegistry.PlacementType.ON_GROUND,Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,Robot3Entity::canSpawn);}
    public static boolean canSpawn(EntityType<? extends MobEntity> type,IServerWorld world,SpawnReason reason,BlockPos pos,Random random){return reason!=SpawnReason.NATURAL||(world.getDifficulty()!=Difficulty.PEACEFUL && (!world.getWorld().isDaytime() && pos.getY() >= 50));}
    public static class EntityAttributesRegisterHandler{@SubscribeEvent public void onEntityAttributeCreation(EntityAttributeCreationEvent event){event.put(entity,CustomEntity.createAttributes().create());}}
    public static class CustomEntity extends OreSpawnGroundMonsterBase {
        public CustomEntity(FMLPlayMessages.SpawnEntity packet, World world) { this(entity, world); }
        public CustomEntity(EntityType<? extends CustomEntity> type, World world) {
            super(type, world); this.experienceValue = 60;
        }
        public static AttributeModifierMap.MutableAttribute createAttributes() {
            return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 80D)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.35D)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 16D)
                .createMutableAttribute(Attributes.ARMOR, 14D)
                .createMutableAttribute(Attributes.FOLLOW_RANGE, 32D);
        }
        @Override public IPacket<?> createSpawnPacket() { return NetworkHooks.getEntitySpawningPacket(this); }
        private int reload;
        @Override protected double searchRange(){return 16;} @Override protected double combatSpeed(){return .5;} @Override protected double meleeDistanceSq(){return 12;}
        private boolean faces(Entity e){double a=Math.atan2(e.getPosZ()-getPosZ(),e.getPosX()-getPosX());double b=Math.toRadians((rotationYawHead+90F)%360F);double d=Math.abs(a-b)%(Math.PI*2D);if(d>Math.PI)d-=Math.PI*2D;return Math.abs(d)<.5D;}
        private void fireLaser(LivingEntity e){double y=3D,xz=1.75D,yaw=Math.toRadians(rotationYawHead);OreSpawnLaserBall shot=new OreSpawnLaserBall(world,this);shot.setPosition(getPosX()-xz*Math.sin(yaw),getPosY()+y,getPosZ()+xz*Math.cos(yaw));double dx=e.getPosX()-shot.getPosX(),dz=e.getPosZ()-shot.getPosZ(),dy=e.getPosY()-shot.getPosY();shot.shoot(dx,dy+Math.sqrt(dx*dx+dz*dz)*.2D,dz,1.4F,5F);world.playSound(null,getPosition(),SoundEvents.ENTITY_FIREWORK_ROCKET_LAUNCH,SoundCategory.HOSTILE,3F,1F);world.addEntity(shot);setAttacking(1);reload=35;}
        @Override public void livingTick(){super.livingTick();if(reload>0){reload--;if(reload<25)setAttacking(0);}LivingEntity e=getAttackTarget();if(!world.isRemote&&reload==0&&e!=null&&getDistanceSq(e)<256&&faces(e))fireLaser(e);}
        @Override protected void dropSpecialItems(DamageSource s,int l,boolean h){OreSpawnLogic.drop(world,this,"laser_ball",20+rand.nextInt(20),5);OreSpawnLogic.drop(world,this,Items.IRON_INGOT,5+rand.nextInt(10),5);}
    }
}
