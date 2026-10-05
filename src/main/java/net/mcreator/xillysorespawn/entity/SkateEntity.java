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
import net.mcreator.xillysorespawn.entity.renderer.SkateRenderer;

@XillysOrespawnModElements.ModElement.Tag
public class SkateEntity extends XillysOrespawnModElements.ModElement {
    public static EntityType entity=(EntityType.Builder.<CustomEntity>create(CustomEntity::new,EntityClassification.MONSTER)
        .setShouldReceiveVelocityUpdates(true).setTrackingRange(160).setUpdateInterval(1)
        .setCustomClientFactory(CustomEntity::new).size(0.75F,0.25F)).build("skate").setRegistryName("skate");
    public SkateEntity(XillysOrespawnModElements instance){super(instance,73);FMLJavaModLoadingContext.get().getModEventBus().register(new SkateRenderer.ModelRegisterHandler());FMLJavaModLoadingContext.get().getModEventBus().register(new EntityAttributesRegisterHandler());MinecraftForge.EVENT_BUS.register(this);}
    @Override public void initElements(){elements.entities.add(()->entity);elements.items.add(()->new SpawnEggItem(entity,-1,-1,new Item.Properties().group(ItemGroup.MISC)).setRegistryName("skate_spawn_egg"));}
    @SubscribeEvent public void addFeatureToBiomes(BiomeLoadingEvent event){
        if (!OreSpawnLogic.allowNaturalSpawn(event, "skate")) return;
        event.getSpawns().getSpawner(EntityClassification.MONSTER).add(new MobSpawnInfo.Spawners(entity,2,1,2));}
    @Override public void init(FMLCommonSetupEvent event){EntitySpawnPlacementRegistry.register(entity,EntitySpawnPlacementRegistry.PlacementType.IN_WATER,Heightmap.Type.OCEAN_FLOOR,SkateEntity::canSpawn);}
    public static boolean canSpawn(EntityType<? extends MobEntity> type,IServerWorld world,SpawnReason reason,BlockPos pos,Random random){return reason!=SpawnReason.NATURAL||(world.getDifficulty()!=Difficulty.PEACEFUL && (world.getWorld().isDaytime() && pos.getY() >= 50 && random.nextInt(30)==1));}
    public static class EntityAttributesRegisterHandler{@SubscribeEvent public void onEntityAttributeCreation(EntityAttributeCreationEvent event){event.put(entity,CustomEntity.createAttributes().create());}}
    public static class CustomEntity extends OreSpawnAquaticMonsterBase {
        private int localTimer;
        public CustomEntity(FMLPlayMessages.SpawnEntity packet, World world) { this(entity, world); }
        public CustomEntity(EntityType<? extends CustomEntity> type, World world) {
            super(type, world); this.experienceValue = 10; 
        }
        public static AttributeModifierMap.MutableAttribute createAttributes() {
            return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 8D)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.25D)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 8D)
                .createMutableAttribute(Attributes.ARMOR, 4D)
                .createMutableAttribute(Attributes.FOLLOW_RANGE, 18D);
        }
        @Override public IPacket<?> createSpawnPacket() { return NetworkHooks.getEntitySpawningPacket(this); }
        @Override protected void registerGoals(){super.registerGoals();goalSelector.addGoal(0,new SwimGoal(this));goalSelector.addGoal(2,new MeleeAttackGoal(this,1.2,false));goalSelector.addGoal(5,new OreSpawnWanderGoal(this,12,100,1,false));}
        @Override public void livingTick(){super.livingTick();if(!world.isRemote&&OreSpawnLogic.playNicely==0&&++localTimer%8==0){LivingEntity e=OreSpawnEntityBase.nearest(this,10,4,t->OreSpawnEntityBase.validHostileTarget(this,t)&&t instanceof PlayerEntity);setAttackTarget(e);if(e!=null){getNavigator().tryMoveToEntityLiving(e,1.2);if(getDistanceSq(e)<4)attackEntityAsMob(e);}}}
        @Override protected SoundEvent getDeathSound(){return OreSpawnLogic.sound("ratdead",SoundEvents.ENTITY_COD_DEATH);}
        @Override protected void dropSpecialItems(DamageSource s,int l,boolean h){entityDropItem(Items.COD);}
    }
}
