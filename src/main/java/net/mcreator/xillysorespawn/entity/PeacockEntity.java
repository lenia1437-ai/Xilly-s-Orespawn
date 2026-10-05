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
import net.mcreator.xillysorespawn.entity.renderer.PeacockRenderer;

@XillysOrespawnModElements.ModElement.Tag
public class PeacockEntity extends XillysOrespawnModElements.ModElement {
    public static EntityType entity = (EntityType.Builder.<CustomEntity>create(CustomEntity::new,EntityClassification.CREATURE)
        .setShouldReceiveVelocityUpdates(true).setTrackingRange(128).setUpdateInterval(3)
        .setCustomClientFactory(CustomEntity::new).size(0.65F,1.2F))
        .build("peacock").setRegistryName("peacock");
    public PeacockEntity(XillysOrespawnModElements instance){super(instance,57);FMLJavaModLoadingContext.get().getModEventBus().register(new PeacockRenderer.ModelRegisterHandler());FMLJavaModLoadingContext.get().getModEventBus().register(new EntityAttributesRegisterHandler());MinecraftForge.EVENT_BUS.register(this);}
    @Override public void initElements(){elements.entities.add(()->entity);elements.items.add(()->new SpawnEggItem(entity,-1,-1,new Item.Properties().group(ItemGroup.MISC)).setRegistryName("peacock_spawn_egg"));}
    @SubscribeEvent public void addFeatureToBiomes(BiomeLoadingEvent event){
        if (!OreSpawnLogic.allowNaturalSpawn(event, "peacock")) return;
        event.getSpawns().getSpawner(EntityClassification.CREATURE).add(new MobSpawnInfo.Spawners(entity,10,1,3));}
    @Override public void init(FMLCommonSetupEvent event){EntitySpawnPlacementRegistry.register(entity,EntitySpawnPlacementRegistry.PlacementType.ON_GROUND,Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,PeacockEntity::canSpawn);}
    public static boolean canSpawn(EntityType<? extends MobEntity> type,IServerWorld world,SpawnReason reason,BlockPos pos,Random random){return reason!=SpawnReason.NATURAL||((world.getWorld().isDaytime() && pos.getY() >= 50));}
    public static class EntityAttributesRegisterHandler{@SubscribeEvent public void onEntityAttributeCreation(EntityAttributeCreationEvent event){event.put(entity,CustomEntity.createAttributes().create());}}
    public static class CustomEntity extends OreSpawnCreatureBase {
        public CustomEntity(FMLPlayMessages.SpawnEntity packet, World world) { this(entity, world); }
        public CustomEntity(EntityType<? extends CustomEntity> type, World world) {
            super(type, world); this.experienceValue = 8;
        }
        public static AttributeModifierMap.MutableAttribute createAttributes() {
            return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 15D)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.38D)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 4D)
                .createMutableAttribute(Attributes.ARMOR, 0D)
                .createMutableAttribute(Attributes.FOLLOW_RANGE, 16D);
        }
        @Override public IPacket<?> createSpawnPacket() { return NetworkHooks.getEntitySpawningPacket(this); }
        @Override protected void registerGoals(){super.registerGoals();goalSelector.addGoal(0,new SwimGoal(this));goalSelector.addGoal(2,new MeleeAttackGoal(this,1.1,false));goalSelector.addGoal(4,new OreSpawnWanderGoal(this,12,100,1,false));goalSelector.addGoal(5,new LookAtGoal(this,PlayerEntity.class,8));}
        @Override public void livingTick(){super.livingTick();if(!world.isRemote&&rand.nextInt(10)==0){LivingEntity e=OreSpawnEntityBase.nearest(this,8,3,t->OreSpawnLogic.isNamed(t,"ant","butterfly","cricket","mosquito","firefly","worm_small"));if(e!=null){getNavigator().tryMoveToEntityLiving(e,1.2);if(getDistanceSq(e)<5)attackEntityAsMob(e);}}}
        @Override public boolean attackEntityAsMob(Entity e){return e.attackEntityFrom(DamageSource.causeMobDamage(this),6);}
        @Override protected SoundEvent getAmbientSound(){return OreSpawnLogic.sound("peacock_living",SoundEvents.ENTITY_PARROT_AMBIENT);}
        @Override protected SoundEvent getHurtSound(DamageSource s){return OreSpawnLogic.sound("peacock_hurt",SoundEvents.ENTITY_PARROT_HURT);}
        @Override protected SoundEvent getDeathSound(){return OreSpawnLogic.sound("peacock_death",SoundEvents.ENTITY_PARROT_DEATH);}
        @Override protected void dropSpecialItems(DamageSource s,int l,boolean h){OreSpawnLogic.drop(world,this,"raw_peacock",1+rand.nextInt(2),2);if(rand.nextBoolean())OreSpawnLogic.drop(world,this,"peacock_feather",1,1);}
    }
}
