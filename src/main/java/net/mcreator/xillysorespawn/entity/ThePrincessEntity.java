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
import net.mcreator.xillysorespawn.entity.renderer.ThePrincessRenderer;

@XillysOrespawnModElements.ModElement.Tag
public class ThePrincessEntity extends XillysOrespawnModElements.ModElement {
    public static EntityType entity=(EntityType.Builder.<CustomEntity>create(CustomEntity::new,EntityClassification.CREATURE)
        .setShouldReceiveVelocityUpdates(true).setTrackingRange(160).setUpdateInterval(1)
        .setCustomClientFactory(CustomEntity::new).size(0.75F,1.25F)).build("the_princess").setRegistryName("the_princess");
    public ThePrincessEntity(XillysOrespawnModElements instance){super(instance,83);FMLJavaModLoadingContext.get().getModEventBus().register(new ThePrincessRenderer.ModelRegisterHandler());FMLJavaModLoadingContext.get().getModEventBus().register(new EntityAttributesRegisterHandler());MinecraftForge.EVENT_BUS.register(this);}
    @Override public void initElements(){elements.entities.add(()->entity);elements.items.add(()->new SpawnEggItem(entity,-1,-1,new Item.Properties().group(ItemGroup.MISC)).setRegistryName("the_princess_spawn_egg"));}
    @SubscribeEvent public void addFeatureToBiomes(BiomeLoadingEvent event){
        if (!OreSpawnLogic.allowNaturalSpawn(event, "the_princess")) return;
        event.getSpawns().getSpawner(EntityClassification.CREATURE).add(new MobSpawnInfo.Spawners(entity,1,1,1));}
    @Override public void init(FMLCommonSetupEvent event){EntitySpawnPlacementRegistry.register(entity,EntitySpawnPlacementRegistry.PlacementType.NO_RESTRICTIONS,Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,ThePrincessEntity::canSpawn);}
    public static boolean canSpawn(EntityType<? extends MobEntity> type,IServerWorld world,SpawnReason reason,BlockPos pos,Random random){return reason!=SpawnReason.NATURAL||((OreSpawnLogic.isCrystalDimension(world.getWorld()) && world.getWorld().isDaytime()));}
    public static class EntityAttributesRegisterHandler{@SubscribeEvent public void onEntityAttributeCreation(EntityAttributeCreationEvent event){event.put(entity,CustomEntity.createAttributes().create());}}
    public static class CustomEntity extends OreSpawnFlyingBase {
        private int localTimer;
        public CustomEntity(FMLPlayMessages.SpawnEntity packet, World world) { this(entity, world); }
        public CustomEntity(EntityType<? extends CustomEntity> type, World world) {
            super(type, world); this.experienceValue = 50; setActivity(1);
        }
        public static AttributeModifierMap.MutableAttribute createAttributes() {
            return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 400D)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.32D)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 20D)
                .createMutableAttribute(Attributes.ARMOR, 14D)
                .createMutableAttribute(Attributes.FOLLOW_RANGE, 48D);
        }
        @Override public IPacket<?> createSpawnPacket() { return NetworkHooks.getEntitySpawningPacket(this); }
        private int kills,fed,days;

        @Override protected int horizontalRange(){return 10;} @Override protected int verticalRange(){return 6;} @Override protected int retargetFrequency(){return 90;} @Override protected double horizontalSpeed(){return .45;} @Override protected double horizontalBlend(){return .2;}
        @Override protected void registerGoals(){super.registerGoals();goalSelector.addGoal(3,new TemptGoal(this,1.25,Ingredient.fromItems(Items.COOKED_BEEF),false));goalSelector.addGoal(6,new LookAtGoal(this,LivingEntity.class,16));targetSelector.addGoal(0,new HurtByTargetGoal(this));}
        private boolean prey(LivingEntity e){return OreSpawnLogic.playNicely==0&&OreSpawnEntityBase.validHostileTarget(this,e)&&e instanceof MonsterEntity;}
        @Override public ActionResultType func_230254_b_(PlayerEntity p,Hand h){ItemStack s=p.getHeldItem(h);if(s.getItem()==Blocks.DIAMOND_BLOCK.asItem()){if(!world.isRemote){setOwner(p);heal(getMaxHealth());setKillCount(1000);}if(!p.abilities.isCreativeMode)s.shrink(1);return ActionResultType.func_233537_a_(world.isRemote);}if(isOwner(p)&&s.getItem().isFood()){if(!world.isRemote){heal(20);fed++;}if(!p.abilities.isCreativeMode)s.shrink(1);return ActionResultType.func_233537_a_(world.isRemote);}if(isOwner(p)&&s.getItem()==Items.FLINT_AND_STEEL){set_is_activated(1);if(!p.abilities.isCreativeMode)s.damageItem(1,p,x->x.sendBreakAnimation(h));return ActionResultType.SUCCESS;}if(isOwner(p)){setSitting(!isSitting());setActivity(isSitting()?0:1);return ActionResultType.SUCCESS;}return super.func_230254_b_(p,h);}
        @Override public boolean attackEntityAsMob(Entity e){boolean h=super.attackEntityAsMob(e);if(h&&e instanceof LivingEntity&&!((LivingEntity)e).isAlive())kills++;return h;}
        @Override public void livingTick(){super.livingTick();if(!world.isRemote&&!isSitting()){if(world.isDaytime()&&ticksExisted%24000==0)days++;LivingEntity e=OreSpawnEntityBase.nearest(this,32,12,this::prey);setAttackTarget(e);if(e!=null){flightTarget=e.getPosition().up(2);if(getDistanceSq(e)<Math.pow(3+getWidth()/2,2))attackEntityAsMob(e);setActivity(2);}else setActivity(1);}int wave=(ticksExisted/6)%60;setHead1Ext(Math.max(0,15-Math.abs(wave-15)));setHead2Ext(Math.max(0,15-Math.abs(wave-30)));setHead3Ext(Math.max(0,15-Math.abs(wave-45)));}
        @Override protected SoundEvent getAmbientSound(){return OreSpawnLogic.sound("roar",SoundEvents.ENTITY_ENDER_DRAGON_GROWL);}
        @Override protected SoundEvent getHurtSound(DamageSource s){return OreSpawnLogic.sound("alo_hurt",SoundEvents.ENTITY_ENDER_DRAGON_HURT);}
        @Override protected SoundEvent getDeathSound(){return OreSpawnLogic.sound("alo_death",SoundEvents.ENTITY_ENDER_DRAGON_DEATH);}
    }
}
