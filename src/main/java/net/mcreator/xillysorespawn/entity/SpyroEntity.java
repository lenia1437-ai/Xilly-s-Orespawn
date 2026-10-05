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
import net.mcreator.xillysorespawn.entity.renderer.SpyroRenderer;

@XillysOrespawnModElements.ModElement.Tag
public class SpyroEntity extends XillysOrespawnModElements.ModElement {
    public static EntityType entity=(EntityType.Builder.<CustomEntity>create(CustomEntity::new,EntityClassification.CREATURE)
        .setShouldReceiveVelocityUpdates(true).setTrackingRange(160).setUpdateInterval(1)
        .setCustomClientFactory(CustomEntity::new).size(0.5F,0.5F)).build("spyro").setRegistryName("spyro");
    public SpyroEntity(XillysOrespawnModElements instance){super(instance,76);FMLJavaModLoadingContext.get().getModEventBus().register(new SpyroRenderer.ModelRegisterHandler());FMLJavaModLoadingContext.get().getModEventBus().register(new EntityAttributesRegisterHandler());MinecraftForge.EVENT_BUS.register(this);}
    @Override public void initElements(){elements.entities.add(()->entity);elements.items.add(()->new SpawnEggItem(entity,-1,-1,new Item.Properties().group(ItemGroup.MISC)).setRegistryName("spyro_spawn_egg"));}
    @SubscribeEvent public void addFeatureToBiomes(BiomeLoadingEvent event){
        if (!OreSpawnLogic.allowNaturalSpawn(event, "spyro")) return;
        event.getSpawns().getSpawner(EntityClassification.CREATURE).add(new MobSpawnInfo.Spawners(entity,2,1,1));}
    @Override public void init(FMLCommonSetupEvent event){EntitySpawnPlacementRegistry.register(entity,EntitySpawnPlacementRegistry.PlacementType.ON_GROUND,Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,SpyroEntity::canSpawn);}
    public static boolean canSpawn(EntityType<? extends MobEntity> type,IServerWorld world,SpawnReason reason,BlockPos pos,Random random){return reason!=SpawnReason.NATURAL||((world.getWorld().isDaytime() && pos.getY() >= 50));}
    public static class EntityAttributesRegisterHandler{@SubscribeEvent public void onEntityAttributeCreation(EntityAttributeCreationEvent event){event.put(entity,CustomEntity.createAttributes().create());}}
    public static class CustomEntity extends OreSpawnCreatureBase {
        private int localTimer;
        public CustomEntity(FMLPlayMessages.SpawnEntity packet, World world) { this(entity, world); }
        public CustomEntity(EntityType<? extends CustomEntity> type, World world) {
            super(type, world); this.experienceValue = 35; setActivity(0);
        }
        public static AttributeModifierMap.MutableAttribute createAttributes() {
            return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 200D)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.3D)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 10D)
                .createMutableAttribute(Attributes.ARMOR, 5D)
                .createMutableAttribute(Attributes.FOLLOW_RANGE, 24D);
        }
        @Override public IPacket<?> createSpawnPacket() { return NetworkHooks.getEntitySpawningPacket(this); }
        @Override protected void registerGoals(){super.registerGoals();goalSelector.addGoal(0,new SwimGoal(this));goalSelector.addGoal(2,new AvoidEntityGoal<>(this,MonsterEntity.class,8,.3,.4));goalSelector.addGoal(4,new TemptGoal(this,1.25,Ingredient.fromItems(Items.COOKED_BEEF),false));goalSelector.addGoal(5,new PanicGoal(this,1.5));goalSelector.addGoal(7,new OreSpawnWanderGoal(this,12,100,.75,false));}
        @Override public ActionResultType func_230254_b_(PlayerEntity p,Hand h){ItemStack s=p.getHeldItem(h);if(s.getItem()==Items.COOKED_BEEF){if(!world.isRemote&&(!isOwner(p)?rand.nextBoolean():true)){setOwner(p);heal(getMaxHealth());}if(!p.abilities.isCreativeMode)s.shrink(1);return ActionResultType.func_233537_a_(world.isRemote);}if(isOwner(p)&&s.getItem()==Items.DIAMOND){if(!world.isRemote){Entity made=OreSpawnLogic.spawn(world,"dragon",getPosX(),getPosY(),getPosZ());if(made instanceof OreSpawnCreatureBase)((OreSpawnCreatureBase)made).setOwner(p);remove();}if(!p.abilities.isCreativeMode)s.shrink(1);return ActionResultType.func_233537_a_(world.isRemote);}if(isOwner(p)&&s.getItem()==Items.FLINT_AND_STEEL){setSpyroFire(1);if(!p.abilities.isCreativeMode)s.damageItem(1,p,x->x.sendBreakAnimation(h));return ActionResultType.SUCCESS;}if(isOwner(p)){setSitting(!isSitting());return ActionResultType.SUCCESS;}return super.func_230254_b_(p,h);}
        @Override public void livingTick(){super.livingTick();if(!world.isRemote){LivingEntity o=getOwner();if(o!=null&&!isSitting()&&getDistanceSq(o)>4)getNavigator().tryMoveToEntityLiving(o,1.15);if(rand.nextInt(100)==1)heal(1);}}
        @Override public boolean attackEntityFrom(DamageSource s,float a){return s==DamageSource.CACTUS?false:super.attackEntityFrom(s,a);}
        @Override protected SoundEvent getAmbientSound(){return OreSpawnLogic.sound("roar",SoundEvents.ENTITY_ENDER_DRAGON_GROWL);}
        @Override protected SoundEvent getHurtSound(DamageSource s){return OreSpawnLogic.sound("duck_hurt",SoundEvents.ENTITY_PARROT_HURT);}
        @Override protected SoundEvent getDeathSound(){return OreSpawnLogic.sound("cryo_death",SoundEvents.ENTITY_PARROT_DEATH);}
    }
}
