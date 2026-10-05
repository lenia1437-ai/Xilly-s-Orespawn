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
import net.mcreator.xillysorespawn.entity.renderer.MolenoidRenderer;

@XillysOrespawnModElements.ModElement.Tag
public class MolenoidEntity extends XillysOrespawnModElements.ModElement {
    public static EntityType entity = (EntityType.Builder.<CustomEntity>create(CustomEntity::new,EntityClassification.MONSTER)
        .setShouldReceiveVelocityUpdates(true).setTrackingRange(128).setUpdateInterval(3)
        .setCustomClientFactory(CustomEntity::new).size(3.9F,2.6F))
        .build("molenoid").setRegistryName("molenoid");
    public MolenoidEntity(XillysOrespawnModElements instance){super(instance,54);FMLJavaModLoadingContext.get().getModEventBus().register(new MolenoidRenderer.ModelRegisterHandler());FMLJavaModLoadingContext.get().getModEventBus().register(new EntityAttributesRegisterHandler());MinecraftForge.EVENT_BUS.register(this);}
    @Override public void initElements(){elements.entities.add(()->entity);elements.items.add(()->new SpawnEggItem(entity,-1,-1,new Item.Properties().group(ItemGroup.MISC)).setRegistryName("molenoid_spawn_egg"));}
    @SubscribeEvent public void addFeatureToBiomes(BiomeLoadingEvent event){
        if (!OreSpawnLogic.allowNaturalSpawn(event, "molenoid")) return;
        event.getSpawns().getSpawner(EntityClassification.MONSTER).add(new MobSpawnInfo.Spawners(entity,2,1,1));}
    @Override public void init(FMLCommonSetupEvent event){EntitySpawnPlacementRegistry.register(entity,EntitySpawnPlacementRegistry.PlacementType.ON_GROUND,Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,MolenoidEntity::canSpawn);}
    public static boolean canSpawn(EntityType<? extends MobEntity> type,IServerWorld world,SpawnReason reason,BlockPos pos,Random random){return reason!=SpawnReason.NATURAL||(world.getDifficulty()!=Difficulty.PEACEFUL && (!world.getWorld().isDaytime() && pos.getY() >= 50));}
    public static class EntityAttributesRegisterHandler{@SubscribeEvent public void onEntityAttributeCreation(EntityAttributeCreationEvent event){event.put(entity,CustomEntity.createAttributes().create());}}
    public static class CustomEntity extends OreSpawnGroundMonsterBase {
        public CustomEntity(FMLPlayMessages.SpawnEntity packet, World world) { this(entity, world); }
        public CustomEntity(EntityType<? extends CustomEntity> type, World world) {
            super(type, world); this.experienceValue = 40;
        }
        public static AttributeModifierMap.MutableAttribute createAttributes() {
            return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 200D)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.35D)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 18D)
                .createMutableAttribute(Attributes.ARMOR, 12D)
                .createMutableAttribute(Attributes.FOLLOW_RANGE, 32D);
        }
        @Override public IPacket<?> createSpawnPacket() { return NetworkHooks.getEntitySpawningPacket(this); }
        @Override protected double searchRange(){return 22;} @Override protected double meleeDistanceSq(){return 49;} @Override protected double combatSpeed(){return 1.25;}
        @Override public boolean attackEntityAsMob(Entity e){boolean h=super.attackEntityAsMob(e);if(h)OreSpawnEntityBase.knockAway(this,e,.8,.1);return h;}
        @Override public boolean attackEntityFrom(DamageSource s,float a){return s==DamageSource.IN_WALL?false:super.attackEntityFrom(s,a);}
        @Override public void livingTick(){super.livingTick();Vector3d m=getMotion();if(!world.isRemote&&OreSpawnLogic.playNicely==0&&world.getGameRules().getBoolean(GameRules.MOB_GRIEFING)&&m.x*m.x+m.z*m.z>.02&&rand.nextInt(2)==0){BlockPos front=getPosition().offset(getHorizontalFacing(),3);for(int y=0;y<3;y++){BlockPos p=front.up(y);BlockState b=world.getBlockState(p);if(b.getBlock()==Blocks.DIRT||b.getBlock()==Blocks.GRASS_BLOCK||b.getBlock()==Blocks.SAND||b.getBlock()==Blocks.GRAVEL||b.getBlock()==Blocks.OAK_LEAVES)world.destroyBlock(p,false,this);}}}
        @Override protected SoundEvent getAmbientSound(){return OreSpawnLogic.sound("molenoid_living",SoundEvents.ENTITY_RAVAGER_AMBIENT);}
        @Override protected SoundEvent getHurtSound(DamageSource s){return OreSpawnLogic.sound("molenoid_hurt",SoundEvents.ENTITY_RAVAGER_HURT);}
        @Override protected SoundEvent getDeathSound(){return OreSpawnLogic.sound("molenoid_death",SoundEvents.ENTITY_RAVAGER_DEATH);}
    }
}
