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
import net.mcreator.xillysorespawn.entity.renderer.Robot1Renderer;

@XillysOrespawnModElements.ModElement.Tag
public class Robot1Entity extends XillysOrespawnModElements.ModElement {
    public static EntityType entity = (EntityType.Builder.<CustomEntity>create(CustomEntity::new,EntityClassification.MONSTER)
        .setShouldReceiveVelocityUpdates(true).setTrackingRange(128).setUpdateInterval(3)
        .setCustomClientFactory(CustomEntity::new).size(0.5F,0.5F))
        .build("robot_1").setRegistryName("robot_1");
    public Robot1Entity(XillysOrespawnModElements instance){super(instance,61);FMLJavaModLoadingContext.get().getModEventBus().register(new Robot1Renderer.ModelRegisterHandler());FMLJavaModLoadingContext.get().getModEventBus().register(new EntityAttributesRegisterHandler());MinecraftForge.EVENT_BUS.register(this);}
    @Override public void initElements(){elements.entities.add(()->entity);elements.items.add(()->new SpawnEggItem(entity,-1,-1,new Item.Properties().group(ItemGroup.MISC)).setRegistryName("robot_1_spawn_egg"));}
    @SubscribeEvent public void addFeatureToBiomes(BiomeLoadingEvent event){
        if (!OreSpawnLogic.allowNaturalSpawn(event, "robot_1")) return;
        event.getSpawns().getSpawner(EntityClassification.MONSTER).add(new MobSpawnInfo.Spawners(entity,2,1,1));}
    @Override public void init(FMLCommonSetupEvent event){EntitySpawnPlacementRegistry.register(entity,EntitySpawnPlacementRegistry.PlacementType.ON_GROUND,Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,Robot1Entity::canSpawn);}
    public static boolean canSpawn(EntityType<? extends MobEntity> type,IServerWorld world,SpawnReason reason,BlockPos pos,Random random){return reason!=SpawnReason.NATURAL||(world.getDifficulty()!=Difficulty.PEACEFUL && (!world.getWorld().isDaytime() && pos.getY() >= 50));}
    public static class EntityAttributesRegisterHandler{@SubscribeEvent public void onEntityAttributeCreation(EntityAttributeCreationEvent event){event.put(entity,CustomEntity.createAttributes().create());}}
    public static class CustomEntity extends OreSpawnGroundMonsterBase {
        public CustomEntity(FMLPlayMessages.SpawnEntity packet, World world) { this(entity, world); }
        public CustomEntity(EntityType<? extends CustomEntity> type, World world) {
            super(type, world); this.experienceValue = 5;
        }
        public static AttributeModifierMap.MutableAttribute createAttributes() {
            return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 5D)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.2D)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 4D)
                .createMutableAttribute(Attributes.ARMOR, 2D)
                .createMutableAttribute(Attributes.FOLLOW_RANGE, 16D);
        }
        @Override public IPacket<?> createSpawnPacket() { return NetworkHooks.getEntitySpawningPacket(this); }
        @Override protected double searchRange(){return 8;} @Override protected double verticalSearchRange(){return 3;} @Override protected double combatSpeed(){return 1.2;}
        @Override public void livingTick(){super.livingTick();if(!world.isRemote&&getAttackTarget()!=null&&getDistanceSq(getAttackTarget())<5&&rand.nextInt(18)==1){world.createExplosion(this,getPosX(),getPosY(),getPosZ(),2.5F,world.getGameRules().getBoolean(GameRules.MOB_GRIEFING)?Explosion.Mode.DESTROY:Explosion.Mode.NONE);remove();}if(world.isRemote&&getAttackTarget()!=null){world.addParticle(net.minecraft.particles.ParticleTypes.SMOKE,getPosX(),getPosY()+1,getPosZ(),0,0,0);world.addParticle(net.minecraft.particles.ParticleTypes.FLAME,getPosX(),getPosY()+1,getPosZ(),0,0,0);}}
        @Override protected SoundEvent getAmbientSound(){return OreSpawnLogic.sound("kyuubi_living",SoundEvents.ENTITY_CREEPER_PRIMED);}
        @Override protected SoundEvent getHurtSound(DamageSource s){return OreSpawnLogic.sound("scorpion_hit",SoundEvents.ENTITY_IRON_GOLEM_HURT);}
        @Override protected SoundEvent getDeathSound(){return OreSpawnLogic.sound("robot1_death",SoundEvents.ENTITY_GENERIC_EXPLODE);}
        @Override protected void dropSpecialItems(DamageSource s,int l,boolean h){entityDropItem(Items.GUNPOWDER);}
    }
}
