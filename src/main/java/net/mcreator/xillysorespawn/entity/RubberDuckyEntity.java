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
import net.mcreator.xillysorespawn.entity.renderer.RubberDuckyRenderer;

@XillysOrespawnModElements.ModElement.Tag
public class RubberDuckyEntity extends XillysOrespawnModElements.ModElement {
    public static EntityType entity = (EntityType.Builder.<CustomEntity>create(CustomEntity::new,EntityClassification.CREATURE)
        .setShouldReceiveVelocityUpdates(true).setTrackingRange(128).setUpdateInterval(3)
        .setCustomClientFactory(CustomEntity::new).size(0.33F,0.5F))
        .build("rubber_ducky").setRegistryName("rubber_ducky");
    public RubberDuckyEntity(XillysOrespawnModElements instance){super(instance,67);FMLJavaModLoadingContext.get().getModEventBus().register(new RubberDuckyRenderer.ModelRegisterHandler());FMLJavaModLoadingContext.get().getModEventBus().register(new EntityAttributesRegisterHandler());MinecraftForge.EVENT_BUS.register(this);}
    @Override public void initElements(){elements.entities.add(()->entity);elements.items.add(()->new SpawnEggItem(entity,-1,-1,new Item.Properties().group(ItemGroup.MISC)).setRegistryName("rubber_ducky_spawn_egg"));}
    @SubscribeEvent public void addFeatureToBiomes(BiomeLoadingEvent event){
        if (!OreSpawnLogic.allowNaturalSpawn(event, "rubber_ducky")) return;
        event.getSpawns().getSpawner(EntityClassification.CREATURE).add(new MobSpawnInfo.Spawners(entity,8,1,2));}
    @Override public void init(FMLCommonSetupEvent event){EntitySpawnPlacementRegistry.register(entity,EntitySpawnPlacementRegistry.PlacementType.ON_GROUND,Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,RubberDuckyEntity::canSpawn);}
    public static boolean canSpawn(EntityType<? extends MobEntity> type,IServerWorld world,SpawnReason reason,BlockPos pos,Random random){return reason!=SpawnReason.NATURAL||((pos.getY() >= 50));}
    public static class EntityAttributesRegisterHandler{@SubscribeEvent public void onEntityAttributeCreation(EntityAttributeCreationEvent event){event.put(entity,CustomEntity.createAttributes().create());}}
    public static class CustomEntity extends OreSpawnCreatureBase {
        public CustomEntity(FMLPlayMessages.SpawnEntity packet, World world) { this(entity, world); }
        public CustomEntity(EntityType<? extends CustomEntity> type, World world) {
            super(type, world); this.experienceValue = 15;
        }
        public static AttributeModifierMap.MutableAttribute createAttributes() {
            return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 5D)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.22D)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 1D)
                .createMutableAttribute(Attributes.ARMOR, 1D)
                .createMutableAttribute(Attributes.FOLLOW_RANGE, 18D);
        }
        @Override public IPacket<?> createSpawnPacket() { return NetworkHooks.getEntitySpawningPacket(this); }
        @Override protected void registerGoals(){super.registerGoals();goalSelector.addGoal(0,new SwimGoal(this));goalSelector.addGoal(2,new MeleeAttackGoal(this,1.2,false));goalSelector.addGoal(3,new TemptGoal(this,1.25,Ingredient.fromItems(Items.SUGAR),false));goalSelector.addGoal(4,new OreSpawnWanderGoal(this,16,100,1,false));goalSelector.addGoal(5,new LookAtGoal(this,LivingEntity.class,6));targetSelector.addGoal(1,new HurtByTargetGoal(this));}
        @Override public ActionResultType func_230254_b_(PlayerEntity p,Hand h){ItemStack s=p.getHeldItem(h);if(s.getItem()==Items.SUGAR){if(!world.isRemote&&(!isOwner(p)?rand.nextInt(2)==0:true)){setOwner(p);heal(getMaxHealth());}if(!p.abilities.isCreativeMode)s.shrink(1);return ActionResultType.func_233537_a_(world.isRemote);}if(isOwner(p)&&s.getItem()==Blocks.GOLD_BLOCK.asItem()){if(!world.isRemote){ownerId=null;setSitting(false);}if(!p.abilities.isCreativeMode)s.shrink(1);return ActionResultType.func_233537_a_(world.isRemote);}if(isOwner(p)){setSitting(!isSitting()&&getKillCount()<5);return ActionResultType.SUCCESS;}return super.func_230254_b_(p,h);}
        @Override public void livingTick(){super.livingTick();if(isInWater())setMotion(getMotion().add(0,.1,0));if(!world.isRemote){LivingEntity o=getOwner();if(o!=null&&!isSitting()&&getDistanceSq(o)>4)getNavigator().tryMoveToEntityLiving(o,2);if(rand.nextInt(300)==1)heal(1);if(OreSpawnLogic.playNicely==0&&rand.nextInt(6)==0){LivingEntity e=OreSpawnEntityBase.nearest(this,9,2,t->OreSpawnEntityBase.validHostileTarget(this,t)&&(!isOwner((t instanceof PlayerEntity)?(PlayerEntity)t:null)));if(e!=null){getNavigator().tryMoveToEntityLiving(e,1.2);if(getDistanceSq(e)<5)attackEntityAsMob(e);}}}}
        @Override public boolean attackEntityAsMob(Entity e){float damage=getKillCount()>=5?2:1;boolean h=e.attackEntityFrom(DamageSource.causeMobDamage(this),damage);if(h){setAttacking(1);attackAnimationTicks=10;}return h;}
        @Override public boolean attackEntityFrom(DamageSource s,float a){boolean h=super.attackEntityFrom(s,a);if(h&&!world.isRemote&&getHealth()<=0&&s.getTrueSource() instanceof PlayerEntity&&getKillCount()<10){Entity made=OreSpawnLogic.spawn(world,"rubber_ducky",getPosX()+rand.nextInt(5)-2,getPosY()+1,getPosZ()+rand.nextInt(5)-2);if(made instanceof CustomEntity)((CustomEntity)made).setKillCount(getKillCount()+1);}return h;}
        @Override protected SoundEvent getAmbientSound(){return rand.nextInt(10)==1?OreSpawnLogic.sound("duck_hurt",SoundEvents.ENTITY_CHICKEN_AMBIENT):null;}
        @Override protected SoundEvent getHurtSound(DamageSource s){return OreSpawnLogic.sound("duck_hurt",SoundEvents.ENTITY_CHICKEN_HURT);}
        @Override protected SoundEvent getDeathSound(){return OreSpawnLogic.sound("duck_hurt",SoundEvents.ENTITY_CHICKEN_DEATH);}
        @Override protected void dropSpecialItems(DamageSource s,int l,boolean h){if(rand.nextBoolean())entityDropItem(Items.CHICKEN);else OreSpawnLogic.drop(world,this,"rubber_ducky_egg",1,0);}
    }
}
