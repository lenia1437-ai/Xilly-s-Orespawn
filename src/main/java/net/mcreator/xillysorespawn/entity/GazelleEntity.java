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
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.SmallFireballEntity;
import net.minecraft.entity.projectile.SnowballEntity;
import net.minecraft.item.*;
import net.minecraft.network.IPacket;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.*;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.server.ServerWorld;
import net.mcreator.xillysorespawn.XillysOrespawnModElements;
import net.mcreator.xillysorespawn.entity.renderer.GazelleRenderer;

@XillysOrespawnModElements.ModElement.Tag
public class GazelleEntity extends XillysOrespawnModElements.ModElement {
    public static EntityType entity = (EntityType.Builder.<CustomEntity>create(CustomEntity::new, EntityClassification.CREATURE)
        .setShouldReceiveVelocityUpdates(true).setTrackingRange(128).setUpdateInterval(3)
        .setCustomClientFactory(CustomEntity::new).size(0.6F, 1.8F))
        .build("gazelle").setRegistryName("gazelle");
    public GazelleEntity(XillysOrespawnModElements instance){super(instance,40);FMLJavaModLoadingContext.get().getModEventBus().register(new GazelleRenderer.ModelRegisterHandler());FMLJavaModLoadingContext.get().getModEventBus().register(new EntityAttributesRegisterHandler());MinecraftForge.EVENT_BUS.register(this);}
    @Override public void initElements(){elements.entities.add(()->entity);elements.items.add(()->new SpawnEggItem(entity,-1,-1,new Item.Properties().group(ItemGroup.MISC)).setRegistryName("gazelle_spawn_egg"));}
    @SubscribeEvent public void addFeatureToBiomes(BiomeLoadingEvent event){
        if (!OreSpawnLogic.allowNaturalSpawn(event, "gazelle")) return;
        event.getSpawns().getSpawner(EntityClassification.CREATURE).add(new MobSpawnInfo.Spawners(entity,8,2,4));}
    @Override public void init(FMLCommonSetupEvent event){EntitySpawnPlacementRegistry.register(entity,EntitySpawnPlacementRegistry.PlacementType.ON_GROUND,Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,GazelleEntity::canSpawn);}
    public static boolean canSpawn(EntityType<? extends MobEntity> type,IServerWorld world,SpawnReason reason,BlockPos pos,Random random){return (reason != SpawnReason.NATURAL || (pos.getY() >= 50 && pos.getY() <= 100));}
    private static boolean openAir(IServerWorld world,BlockPos pos,int needed){int count=0;for(int x=-1;x<=1;x++)for(int z=-1;z<=1;z++)if(world.getWorld().isAirBlock(pos.add(x,0,z)))count++;return count>=needed;}
    public static class EntityAttributesRegisterHandler{@SubscribeEvent public void onEntityAttributeCreation(EntityAttributeCreationEvent event){event.put(entity,CustomEntity.createAttributes().create());}}
    public static class CustomEntity extends OreSpawnCreatureBase {
        public CustomEntity(FMLPlayMessages.SpawnEntity p,World w){this(entity,w);} public CustomEntity(EntityType<? extends CustomEntity> t,World w){super(t,w);experienceValue=5;}
        public static AttributeModifierMap.MutableAttribute createAttributes(){return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH,15D).createMutableAttribute(Attributes.MOVEMENT_SPEED,0.3D).createMutableAttribute(Attributes.FOLLOW_RANGE,20D);}
        @Override public IPacket<?> createSpawnPacket(){return NetworkHooks.getEntitySpawningPacket(this);}
        @Override protected void registerGoals(){super.registerGoals();goalSelector.addGoal(0,new SwimGoal(this));goalSelector.addGoal(1,new PanicGoal(this,1.5));goalSelector.addGoal(2,new AvoidEntityGoal<MonsterEntity>(this,MonsterEntity.class,8,1,1.7));goalSelector.addGoal(3,new AvoidEntityGoal<PlayerEntity>(this,PlayerEntity.class,12,1,2));goalSelector.addGoal(5,new OreSpawnWanderGoal(this,12,100,1,false));goalSelector.addGoal(6,new LookAtGoal(this,PlayerEntity.class,6));}
        @Override public ActionResultType func_230254_b_(PlayerEntity p,Hand h){ItemStack s=p.getHeldItem(h);if(s.getItem()==Items.WHEAT){if(!world.isRemote&&((!isOwner(p)&&rand.nextInt(2)==0)||isOwner(p))){setOwner(p);heal(getMaxHealth());}if(!p.abilities.isCreativeMode)s.shrink(1);return ActionResultType.func_233537_a_(world.isRemote);}if(isOwner(p)&&s.getItem()==Blocks.GOLD_BLOCK.asItem()){if(!world.isRemote){ownerId=null;setSitting(false);}if(!p.abilities.isCreativeMode)s.shrink(1);return ActionResultType.func_233537_a_(world.isRemote);}if(isOwner(p)){setSitting(!isSitting());return ActionResultType.SUCCESS;}return super.func_230254_b_(p,h);}
        @Override public void livingTick(){super.livingTick();if(!world.isRemote&&getOwner()!=null&&!isSitting()&&getDistanceSq(getOwner())>4)getNavigator().tryMoveToEntityLiving(getOwner(),2D);}
        @Override protected SoundEvent getAmbientSound(){return null;}
        @Override protected SoundEvent getHurtSound(DamageSource s){return OreSpawnLogic.sound("gazelle_hurt",SoundEvents.ENTITY_HORSE_HURT);}
        @Override protected SoundEvent getDeathSound(){return OreSpawnLogic.sound("gazelle_death",SoundEvents.ENTITY_HORSE_DEATH);}
        @Override protected void dropSpecialItems(DamageSource s,int l,boolean h){this.entityDropItem(Items.BEEF);if(rand.nextBoolean())this.entityDropItem(Items.LEATHER);}
    }
}
