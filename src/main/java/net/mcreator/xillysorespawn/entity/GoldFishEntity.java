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
import net.mcreator.xillysorespawn.entity.renderer.GoldFishRenderer;

@XillysOrespawnModElements.ModElement.Tag
public class GoldFishEntity extends XillysOrespawnModElements.ModElement {
    public static EntityType entity = (EntityType.Builder.<CustomEntity>create(CustomEntity::new, EntityClassification.CREATURE)
        .setShouldReceiveVelocityUpdates(true).setTrackingRange(128).setUpdateInterval(3)
        .setCustomClientFactory(CustomEntity::new).size(0.75F, 0.5F))
        .build("gold_fish").setRegistryName("gold_fish");
    public GoldFishEntity(XillysOrespawnModElements instance){super(instance,37);FMLJavaModLoadingContext.get().getModEventBus().register(new GoldFishRenderer.ModelRegisterHandler());FMLJavaModLoadingContext.get().getModEventBus().register(new EntityAttributesRegisterHandler());MinecraftForge.EVENT_BUS.register(this);}
    @Override public void initElements(){elements.entities.add(()->entity);elements.items.add(()->new SpawnEggItem(entity,-1,-1,new Item.Properties().group(ItemGroup.MISC)).setRegistryName("gold_fish_spawn_egg"));}
    @SubscribeEvent public void addFeatureToBiomes(BiomeLoadingEvent event){
        if (!OreSpawnLogic.allowNaturalSpawn(event, "gold_fish")) return;
        event.getSpawns().getSpawner(EntityClassification.CREATURE).add(new MobSpawnInfo.Spawners(entity,8,1,4));}
    @Override public void init(FMLCommonSetupEvent event){EntitySpawnPlacementRegistry.register(entity,EntitySpawnPlacementRegistry.PlacementType.NO_RESTRICTIONS,Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,GoldFishEntity::canSpawn);}
    public static boolean canSpawn(EntityType<? extends MobEntity> type,IServerWorld world,SpawnReason reason,BlockPos pos,Random random){return (true);}
    private static boolean openAir(IServerWorld world,BlockPos pos,int needed){int count=0;for(int x=-1;x<=1;x++)for(int z=-1;z<=1;z++)if(world.getWorld().isAirBlock(pos.add(x,0,z)))count++;return count>=needed;}
    public static class EntityAttributesRegisterHandler{@SubscribeEvent public void onEntityAttributeCreation(EntityAttributeCreationEvent event){event.put(entity,CustomEntity.createAttributes().create());}}
    public static class CustomEntity extends OreSpawnFlyingBase {
        public CustomEntity(FMLPlayMessages.SpawnEntity packet, World world) { this(entity, world); }
        public CustomEntity(EntityType<? extends CustomEntity> type, World world) {
            super(type, world); this.experienceValue = 5; 
        }
        public static AttributeModifierMap.MutableAttribute createAttributes() {
            return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 6D)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.22D)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 1D)
                .createMutableAttribute(Attributes.ARMOR, 0D)
                .createMutableAttribute(Attributes.FOLLOW_RANGE, 16D);
        }
        @Override public IPacket<?> createSpawnPacket() { return NetworkHooks.getEntitySpawningPacket(this); }
        @Override protected double horizontalSpeed() { return 0.4; }
        @Override protected double verticalSpeed() { return 0.7; }
        @Override protected double horizontalBlend() { return 0.3; }
        @Override protected double verticalBlend() { return 0.2; }
        @Override protected int horizontalRange() { return 9; }
        @Override protected int retargetFrequency() { return 300; }        @Override protected float yawDivisor(){return 6.0F;}
        @Override protected void chooseFlightTarget(){int bias=getPosY()<120?2:getPosY()>140?-2:0;BlockPos p=OreSpawnEntityBase.randomAirTarget(this,9,5,50,bias);if(p!=null)this.flightTarget=p;}
        @Override protected SoundEvent getAmbientSound(){return SoundEvents.ENTITY_GENERIC_SPLASH;}
        @Override protected SoundEvent getHurtSound(DamageSource s){return SoundEvents.ENTITY_GENERIC_SPLASH;}
        @Override protected SoundEvent getDeathSound(){return OreSpawnLogic.sound("little_splat",SoundEvents.ENTITY_COD_DEATH);}
        @Override protected void dropSpecialItems(DamageSource s,int l,boolean h){int r=rand.nextInt(3);if(r==0)this.entityDropItem(Blocks.GOLD_BLOCK.asItem());else if(r==1)OreSpawnLogic.drop(world,this,"uranium_nugget",1,0);else OreSpawnLogic.drop(world,this,"titanium_nugget",1,0);}
    }
}
