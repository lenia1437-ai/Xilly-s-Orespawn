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
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.IPacket;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.*;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.Heightmap;
import net.mcreator.xillysorespawn.XillysOrespawnModElements;
import net.mcreator.xillysorespawn.entity.renderer.VelocityRaptorRenderer;

@XillysOrespawnModElements.ModElement.Tag
public class VelocityRaptorEntity extends XillysOrespawnModElements.ModElement {
    public static EntityType entity=(EntityType.Builder.<CustomEntity>create(CustomEntity::new,EntityClassification.CREATURE)
        .setShouldReceiveVelocityUpdates(true).setTrackingRange(96).setUpdateInterval(2)
        .setCustomClientFactory(CustomEntity::new).size(.5F,.6F)).build("velocity_raptor").setRegistryName("velocity_raptor");
    public VelocityRaptorEntity(XillysOrespawnModElements instance){super(instance,91);FMLJavaModLoadingContext.get().getModEventBus().register(new VelocityRaptorRenderer.ModelRegisterHandler());FMLJavaModLoadingContext.get().getModEventBus().register(new EntityAttributesRegisterHandler());MinecraftForge.EVENT_BUS.register(this);}
    @Override public void initElements(){elements.entities.add(()->entity);elements.items.add(()->new SpawnEggItem(entity,-1,-1,new Item.Properties().group(ItemGroup.MISC)).setRegistryName("velocity_raptor_spawn_egg"));}
    @SubscribeEvent public void addFeatureToBiomes(BiomeLoadingEvent event){
        if (!OreSpawnLogic.allowNaturalSpawn(event, "velocity_raptor")) return;
        event.getSpawns().getSpawner(EntityClassification.CREATURE).add(new MobSpawnInfo.Spawners(entity,5,1,2));}
    @Override public void init(FMLCommonSetupEvent event){EntitySpawnPlacementRegistry.register(entity,EntitySpawnPlacementRegistry.PlacementType.ON_GROUND,Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,VelocityRaptorEntity::canSpawn);}
    public static boolean canSpawn(EntityType<? extends MobEntity> type,IServerWorld world,SpawnReason reason,BlockPos pos,Random random){return reason!=SpawnReason.NATURAL||(pos.getY()>=50&&world.getWorld().isDaytime());}
    public static class EntityAttributesRegisterHandler{@SubscribeEvent public void onEntityAttributeCreation(EntityAttributeCreationEvent event){event.put(entity,CustomEntity.createAttributes().create());}}

    public static class CustomEntity extends OreSpawnCreatureBase {
        private BlockPos flowerTarget;
        public CustomEntity(FMLPlayMessages.SpawnEntity packet,World world){this(entity,world);}
        public CustomEntity(EntityType<? extends CustomEntity> type,World world){super(type,world);experienceValue=5;}
        public static AttributeModifierMap.MutableAttribute createAttributes(){return MobEntity.func_233666_p_()
            .createMutableAttribute(Attributes.MAX_HEALTH,10D).createMutableAttribute(Attributes.MOVEMENT_SPEED,.55D)
            .createMutableAttribute(Attributes.ATTACK_DAMAGE,2D).createMutableAttribute(Attributes.FOLLOW_RANGE,24D);}
        @Override public IPacket<?> createSpawnPacket(){return NetworkHooks.getEntitySpawningPacket(this);}
        @Override protected void registerGoals(){super.registerGoals();goalSelector.addGoal(0,new SwimGoal(this));
            goalSelector.addGoal(3,new AvoidEntityGoal<>(this,MonsterEntity.class,8F,1D,1.4D));
            goalSelector.addGoal(4,new TemptGoal(this,1.25D,Ingredient.fromItems(Items.CARROT),false));goalSelector.addGoal(5,new PanicGoal(this,1.6D));
            goalSelector.addGoal(6,new LookAtGoal(this,PlayerEntity.class,6F));goalSelector.addGoal(7,new OreSpawnWanderGoal(this,12,100,.9D,false));goalSelector.addGoal(8,new LookRandomlyGoal(this));}
        public int getVHealth(){return (int)getHealth();}
        private boolean isFlower(Block block){return block==Blocks.DANDELION||block==Blocks.POPPY||block==Blocks.BLUE_ORCHID||block==Blocks.ALLIUM||block==Blocks.AZURE_BLUET||block==Blocks.RED_TULIP||block==Blocks.ORANGE_TULIP||block==Blocks.WHITE_TULIP||block==Blocks.PINK_TULIP||block==Blocks.OXEYE_DAISY||block==Blocks.CORNFLOWER||block==Blocks.LILY_OF_THE_VALLEY;}
        private void findFlower(){flowerTarget=null;double best=Double.MAX_VALUE;BlockPos o=getPosition();for(int r=1;r<10;r++){int vr=Math.min(r,2);for(int x=-r;x<=r;x++)for(int y=-vr;y<=vr;y++)for(int z=-r;z<=r;z++){BlockPos p=o.add(x,y,z);if(isFlower(world.getBlockState(p).getBlock())){double d=p.distanceSq(o);if(d<best){best=d;flowerTarget=p;}}}if(flowerTarget!=null)break;}}
        @Override public void livingTick(){super.livingTick();if(world.isRemote)return;LivingEntity owner=getOwner();if(isSitting()){getNavigator().clearPath();return;}if(owner!=null&&getDistanceSq(owner)>4D)getNavigator().tryMoveToEntityLiving(owner,1.5D);if(owner==null&&(rand.nextInt(20)==0&&getHealth()<getMaxHealth()||rand.nextInt(250)==0)){findFlower();if(flowerTarget!=null){getNavigator().tryMoveToXYZ(flowerTarget.getX(),flowerTarget.getY(),flowerTarget.getZ(),1D);if(getDistanceSq(flowerTarget.getX()+.5,flowerTarget.getY()+.5,flowerTarget.getZ()+.5)<12D){if(world.getGameRules().getBoolean(GameRules.MOB_GRIEFING))world.destroyBlock(flowerTarget,false);heal(2F);}}}}
        @Override public ActionResultType func_230254_b_(PlayerEntity p,Hand h){ItemStack s=p.getHeldItem(h);if(s.getItem()==Items.CARROT){if(!world.isRemote){if(getOwner()==null&&rand.nextBoolean())setOwner(p);if(isOwner(p)){getAttribute(Attributes.MAX_HEALTH).setBaseValue(20D);heal(getMaxHealth());}}if(!p.abilities.isCreativeMode)s.shrink(1);return ActionResultType.func_233537_a_(world.isRemote);}if(isOwner(p)&&s.getItem()==Items.GOLD_BLOCK){if(!world.isRemote){setOwner(null);setSitting(false);getAttribute(Attributes.MAX_HEALTH).setBaseValue(10D);setHealth(Math.min(getHealth(),10F));}if(!p.abilities.isCreativeMode)s.shrink(1);return ActionResultType.func_233537_a_(world.isRemote);}if(isOwner(p)){setSitting(!isSitting());return ActionResultType.SUCCESS;}return super.func_230254_b_(p,h);}
        @Override public boolean attackEntityFrom(DamageSource source,float amount){return super.attackEntityFrom(source,Math.min(10F,amount));}
        @Override public boolean onLivingFall(float distance,float multiplier){float damage=Math.min(2F,Math.max(0F,distance-3F));return damage>0&&attackEntityFrom(DamageSource.FALL,damage);}
        @Override protected void dropSpecialItems(DamageSource source,int lootingLevel,boolean recentlyHitIn){super.dropSpecialItems(source,lootingLevel,recentlyHitIn);if(getOwner()!=null)for(int i=0;i<2+rand.nextInt(5);i++)entityDropItem(Items.POPPY);}
        @Override protected SoundEvent getHurtSound(DamageSource s){return OreSpawnLogic.sound("cryo_hurt",SoundEvents.ENTITY_PARROT_HURT);}
        @Override protected SoundEvent getDeathSound(){return OreSpawnLogic.sound("cryo_death",SoundEvents.ENTITY_PARROT_DEATH);}
    }
}
