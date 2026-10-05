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
import net.mcreator.xillysorespawn.entity.renderer.RatRenderer;

@XillysOrespawnModElements.ModElement.Tag
public class RatEntity extends XillysOrespawnModElements.ModElement {
    public static EntityType entity = (EntityType.Builder.<CustomEntity>create(CustomEntity::new,EntityClassification.MONSTER)
        .setShouldReceiveVelocityUpdates(true).setTrackingRange(128).setUpdateInterval(3)
        .setCustomClientFactory(CustomEntity::new).size(0.25F,0.5F))
        .build("rat").setRegistryName("rat");
    public RatEntity(XillysOrespawnModElements instance){super(instance,60);FMLJavaModLoadingContext.get().getModEventBus().register(new RatRenderer.ModelRegisterHandler());FMLJavaModLoadingContext.get().getModEventBus().register(new EntityAttributesRegisterHandler());MinecraftForge.EVENT_BUS.register(this);}
    @Override public void initElements(){elements.entities.add(()->entity);elements.items.add(()->new SpawnEggItem(entity,-1,-1,new Item.Properties().group(ItemGroup.MISC)).setRegistryName("rat_spawn_egg"));}
    @SubscribeEvent public void addFeatureToBiomes(BiomeLoadingEvent event){
        if (!OreSpawnLogic.allowNaturalSpawn(event, "rat")) return;
        event.getSpawns().getSpawner(EntityClassification.MONSTER).add(new MobSpawnInfo.Spawners(entity,10,1,3));}
    @Override public void init(FMLCommonSetupEvent event){EntitySpawnPlacementRegistry.register(entity,EntitySpawnPlacementRegistry.PlacementType.ON_GROUND,Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,RatEntity::canSpawn);}
    public static boolean canSpawn(EntityType<? extends MobEntity> type,IServerWorld world,SpawnReason reason,BlockPos pos,Random random){return reason!=SpawnReason.NATURAL||(world.getDifficulty()!=Difficulty.PEACEFUL && (!world.getWorld().isDaytime()));}
    public static class EntityAttributesRegisterHandler{@SubscribeEvent public void onEntityAttributeCreation(EntityAttributeCreationEvent event){event.put(entity,CustomEntity.createAttributes().create());}}
    public static class CustomEntity extends OreSpawnGroundMonsterBase {
        public CustomEntity(FMLPlayMessages.SpawnEntity packet, World world) { this(entity, world); }
        public CustomEntity(EntityType<? extends CustomEntity> type, World world) {
            super(type, world); this.experienceValue = 5;
        }
        public static AttributeModifierMap.MutableAttribute createAttributes() {
            return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 5D)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.25D)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 3D)
                .createMutableAttribute(Attributes.ARMOR, 1D)
                .createMutableAttribute(Attributes.FOLLOW_RANGE, 18D);
        }
        @Override public IPacket<?> createSpawnPacket() { return NetworkHooks.getEntitySpawningPacket(this); }
        @Override protected double searchRange(){return 9;} @Override protected double verticalSearchRange(){return 2;} @Override protected double meleeDistanceSq(){return 5;} @Override protected double combatSpeed(){return 1.2;}
        @Override protected boolean originalTarget(LivingEntity e){if(!OreSpawnEntityBase.validHostileTarget(this,e)||OreSpawnLogic.isNamed(e,"rat","irukandji","skate","whale","flounder","ghost","ghost_skelly","dungeon_beast"))return false;if(e instanceof PlayerEntity&&isOwner((PlayerEntity)e))return false;return true;}
        @Override public ActionResultType func_230254_b_(PlayerEntity p,Hand h){ItemStack s=p.getHeldItem(h);if(s.getItem()==Items.WHEAT){if(!world.isRemote){setOwner(p);heal(getMaxHealth());}if(!p.abilities.isCreativeMode)s.shrink(1);return ActionResultType.func_233537_a_(world.isRemote);}return super.func_230254_b_(p,h);}
        @Override public void livingTick(){super.livingTick();if(!world.isRemote&&getOwner()!=null&&getDistanceSq(getOwner())>64)getNavigator().tryMoveToEntityLiving(getOwner(),1.75);if(rand.nextInt(250)==1)heal(1);}
        @Override protected SoundEvent getAmbientSound(){return OreSpawnLogic.sound("rat_living",SoundEvents.ENTITY_SILVERFISH_AMBIENT);}
        @Override protected SoundEvent getHurtSound(DamageSource s){return OreSpawnLogic.sound("rat_hit",SoundEvents.ENTITY_SILVERFISH_HURT);}
        @Override protected SoundEvent getDeathSound(){return OreSpawnLogic.sound("ratdead",SoundEvents.ENTITY_SILVERFISH_DEATH);}
    }
}
