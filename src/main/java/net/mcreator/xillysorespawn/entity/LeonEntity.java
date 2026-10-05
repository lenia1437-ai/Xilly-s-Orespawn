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
import net.mcreator.xillysorespawn.entity.renderer.LeonRenderer;

@XillysOrespawnModElements.ModElement.Tag
public class LeonEntity extends XillysOrespawnModElements.ModElement {
    public static EntityType entity = (EntityType.Builder.<CustomEntity>create(CustomEntity::new,EntityClassification.CREATURE)
        .setShouldReceiveVelocityUpdates(true).setTrackingRange(128).setUpdateInterval(3)
        .setCustomClientFactory(CustomEntity::new).size(3.5F,8.25F))
        .build("leon").setRegistryName("leon");
    public LeonEntity(XillysOrespawnModElements instance){super(instance,50);FMLJavaModLoadingContext.get().getModEventBus().register(new LeonRenderer.ModelRegisterHandler());FMLJavaModLoadingContext.get().getModEventBus().register(new EntityAttributesRegisterHandler());MinecraftForge.EVENT_BUS.register(this);}
    @Override public void initElements(){elements.entities.add(()->entity);elements.items.add(()->new SpawnEggItem(entity,-1,-1,new Item.Properties().group(ItemGroup.MISC)).setRegistryName("leon_spawn_egg"));}
    @SubscribeEvent public void addFeatureToBiomes(BiomeLoadingEvent event){
        if (!OreSpawnLogic.allowNaturalSpawn(event, "leon")) return;
        event.getSpawns().getSpawner(EntityClassification.CREATURE).add(new MobSpawnInfo.Spawners(entity,1,1,1));}
    @Override public void init(FMLCommonSetupEvent event){EntitySpawnPlacementRegistry.register(entity,EntitySpawnPlacementRegistry.PlacementType.NO_RESTRICTIONS,Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,LeonEntity::canSpawn);}
    public static boolean canSpawn(EntityType<? extends MobEntity> type,IServerWorld world,SpawnReason reason,BlockPos pos,Random random){return reason!=SpawnReason.NATURAL||((pos.getY() >= 50));}
    public static class EntityAttributesRegisterHandler{@SubscribeEvent public void onEntityAttributeCreation(EntityAttributeCreationEvent event){event.put(entity,CustomEntity.createAttributes().create());}}
    public static class CustomEntity extends OreSpawnFlyingBase {
        public CustomEntity(FMLPlayMessages.SpawnEntity packet, World world) { this(entity, world); }
        public CustomEntity(EntityType<? extends CustomEntity> type, World world) {
            super(type, world); this.experienceValue = 300;
        }
        public static AttributeModifierMap.MutableAttribute createAttributes() {
            return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 250D)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.25D)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 55D)
                .createMutableAttribute(Attributes.ARMOR, 16D)
                .createMutableAttribute(Attributes.FOLLOW_RANGE, 64D);
        }
        @Override public IPacket<?> createSpawnPacket() { return NetworkHooks.getEntitySpawningPacket(this); }
        @Override public void livingTick(){super.livingTick();if(!world.isRemote)setActivity(isSitting()&&isOnGround()?0:1);}
        @Override protected int horizontalRange(){return 24;} @Override protected int verticalRange(){return 10;}
        @Override protected int retargetFrequency(){return 120;} @Override protected double horizontalSpeed(){return .55D;} @Override protected double horizontalBlend(){return .18D;}
        @Override protected void registerGoals(){super.registerGoals();goalSelector.addGoal(6,new LookAtGoal(this,PlayerEntity.class,16));goalSelector.addGoal(7,new LookRandomlyGoal(this));}
        @Override protected void originalFlightTick(){LivingEntity owner=getOwner();if(owner!=null&&!isSitting()){if(getDistanceSq(owner)>64)flightTarget=owner.getPosition().up(5);if(getDistanceSq(owner)>1600)setPosition(owner.getPosX(),owner.getPosY()+3,owner.getPosZ());}if(OreSpawnLogic.playNicely==0&&!isSitting()&&rand.nextInt(8)==0){LivingEntity e=OreSpawnEntityBase.nearest(this,16,10,t->OreSpawnEntityBase.validHostileTarget(this,t)&&!OreSpawnLogic.isNamed(t,"leon"));if(e!=null){flightTarget=e.getPosition().up(2);if(getDistanceSq(e)<64)attackEntityAsMob(e);}}super.originalFlightTick();setActivity(isSitting()&&onGround?0:1);}
        @Override public boolean attackEntityAsMob(Entity e){boolean h=super.attackEntityAsMob(e);if(h)OreSpawnEntityBase.knockAway(this,e,2.2,.3);return h;}
        @Override public boolean attackEntityFrom(DamageSource s,float a){if(s==DamageSource.CACTUS)return false;boolean h=super.attackEntityFrom(s,a);if(h&&!world.isRemote)setActivity(1);return h;}
        @Override public ActionResultType func_230254_b_(PlayerEntity p,Hand hand){ItemStack s=p.getHeldItem(hand);if(s.getItem()==Blocks.DIAMOND_BLOCK.asItem()||s.getItem()==Items.COOKED_BEEF){if(!world.isRemote&&(!isOwner(p)?rand.nextInt(3)==0:true)){setOwner(p);heal(getMaxHealth());setActivity(0);}if(!p.abilities.isCreativeMode)s.shrink(1);return ActionResultType.func_233537_a_(world.isRemote);}if(isOwner(p)&&s.getItem()==Blocks.GOLD_BLOCK.asItem()){if(!world.isRemote){ownerId=null;setSitting(false);}if(!p.abilities.isCreativeMode)s.shrink(1);return ActionResultType.func_233537_a_(world.isRemote);}if(isOwner(p)){setSitting(!isSitting());setActivity(isSitting()?0:1);return ActionResultType.SUCCESS;}return super.func_230254_b_(p,hand);}
        @Override protected SoundEvent getAmbientSound(){return OreSpawnLogic.sound("leon_living",SoundEvents.ENTITY_ENDER_DRAGON_AMBIENT);}
        @Override protected SoundEvent getHurtSound(DamageSource s){return OreSpawnLogic.sound("leon_hurt",SoundEvents.ENTITY_ENDER_DRAGON_HURT);}
        @Override protected SoundEvent getDeathSound(){return OreSpawnLogic.sound("leon_death",SoundEvents.ENTITY_ENDER_DRAGON_DEATH);}
        @Override protected void dropSpecialItems(DamageSource s,int l,boolean h){OreSpawnLogic.drop(world,this,Items.FEATHER,10+rand.nextInt(20),8);OreSpawnLogic.drop(world,this,Items.BEEF,5+rand.nextInt(10),5);}
    }
}
