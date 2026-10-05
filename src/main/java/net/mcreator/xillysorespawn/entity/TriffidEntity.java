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
import net.mcreator.xillysorespawn.entity.renderer.TriffidRenderer;

@XillysOrespawnModElements.ModElement.Tag
public class TriffidEntity extends XillysOrespawnModElements.ModElement {
    public static EntityType entity=(EntityType.Builder.<CustomEntity>create(CustomEntity::new,EntityClassification.MONSTER)
        .setShouldReceiveVelocityUpdates(true).setTrackingRange(160).setUpdateInterval(1)
        .setCustomClientFactory(CustomEntity::new).size(2F,4F)).build("triffid").setRegistryName("triffid");
    public TriffidEntity(XillysOrespawnModElements instance){super(instance,87);FMLJavaModLoadingContext.get().getModEventBus().register(new TriffidRenderer.ModelRegisterHandler());FMLJavaModLoadingContext.get().getModEventBus().register(new EntityAttributesRegisterHandler());MinecraftForge.EVENT_BUS.register(this);}
    @Override public void initElements(){elements.entities.add(()->entity);elements.items.add(()->new SpawnEggItem(entity,-1,-1,new Item.Properties().group(ItemGroup.MISC)).setRegistryName("triffid_spawn_egg"));}
    @SubscribeEvent public void addFeatureToBiomes(BiomeLoadingEvent event){
        if (!OreSpawnLogic.allowNaturalSpawn(event, "triffid")) return;
        event.getSpawns().getSpawner(EntityClassification.MONSTER).add(new MobSpawnInfo.Spawners(entity,3,1,1));}
    @Override public void init(FMLCommonSetupEvent event){EntitySpawnPlacementRegistry.register(entity,EntitySpawnPlacementRegistry.PlacementType.ON_GROUND,Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,TriffidEntity::canSpawn);}
    public static boolean canSpawn(EntityType<? extends MobEntity> type,IServerWorld world,SpawnReason reason,BlockPos pos,Random random){return reason!=SpawnReason.NATURAL||(world.getDifficulty()!=Difficulty.PEACEFUL && (!world.getWorld().isDaytime() && pos.getY() >= 50));}
    public static class EntityAttributesRegisterHandler{@SubscribeEvent public void onEntityAttributeCreation(EntityAttributeCreationEvent event){event.put(entity,CustomEntity.createAttributes().create());}}
    public static class CustomEntity extends OreSpawnGroundMonsterBase {
        private int localTimer;
        public CustomEntity(FMLPlayMessages.SpawnEntity packet, World world) { this(entity, world); }
        public CustomEntity(EntityType<? extends CustomEntity> type, World world) {
            super(type, world); this.experienceValue = 50; setOpenClosed(0);
        }
        public static AttributeModifierMap.MutableAttribute createAttributes() {
            return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 100D)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.13D)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 20D)
                .createMutableAttribute(Attributes.ARMOR, 12D)
                .createMutableAttribute(Attributes.FOLLOW_RANGE, 18D);
        }
        @Override public IPacket<?> createSpawnPacket() { return NetworkHooks.getEntitySpawningPacket(this); }
        private int invulnerableTicks;
        @Override protected void registerGoals(){super.registerGoals();goalSelector.addGoal(0,new SwimGoal(this));goalSelector.addGoal(6,new LookAtGoal(this,LivingEntity.class,10));}
        @Override protected void originalCombatTick(){if(invulnerableTicks>0){invulnerableTicks--;setOpenClosed(0);setAttacking(0);return;}if(rand.nextInt(80)==2)setOpenClosed(rand.nextInt(8)==1?1:0);if(rand.nextInt(10)==1){LivingEntity e=OreSpawnEntityBase.nearest(this,12,5,t->OreSpawnEntityBase.validHostileTarget(this,t)&&!OreSpawnLogic.isNamed(t,"triffid"));if(e!=null){setOpenClosed(1);getLookController().setLookPositionWithEntity(e,10,10);if(getDistanceSq(e)<25){setAttacking(1);attackEntityAsMob(e);}else setAttacking(0);}else setAttacking(0);}}
        @Override public boolean attackEntityFrom(DamageSource s,float a){if(invulnerableTicks>0||getOpenClosed()==0){invulnerableTicks=300;setOpenClosed(0);return false;}boolean h=super.attackEntityFrom(s,a);invulnerableTicks=300;setOpenClosed(0);return h;}
        @Override public void livingTick(){super.livingTick();setMotion(0,getMotion().y,0);if(rand.nextInt(250)==1)heal(1);}
        @Override protected SoundEvent getAmbientSound(){return OreSpawnLogic.sound("triffid_living",SoundEvents.BLOCK_GRASS_BREAK);}
        @Override protected SoundEvent getHurtSound(DamageSource s){return OreSpawnLogic.sound("triffid_hit",SoundEvents.ENTITY_SHULKER_HURT);}
        @Override protected SoundEvent getDeathSound(){return OreSpawnLogic.sound("triffid_dead",SoundEvents.ENTITY_SHULKER_DEATH);}
    }
}
