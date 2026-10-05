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
import net.mcreator.xillysorespawn.entity.renderer.LeafMonsterRenderer;

@XillysOrespawnModElements.ModElement.Tag
public class LeafMonsterEntity extends XillysOrespawnModElements.ModElement {
    public static EntityType entity = (EntityType.Builder.<CustomEntity>create(CustomEntity::new,EntityClassification.MONSTER)
        .setShouldReceiveVelocityUpdates(true).setTrackingRange(128).setUpdateInterval(3)
        .setCustomClientFactory(CustomEntity::new).size(1F,2.5F))
        .build("leaf_monster").setRegistryName("leaf_monster");
    public LeafMonsterEntity(XillysOrespawnModElements instance){super(instance,49);FMLJavaModLoadingContext.get().getModEventBus().register(new LeafMonsterRenderer.ModelRegisterHandler());FMLJavaModLoadingContext.get().getModEventBus().register(new EntityAttributesRegisterHandler());MinecraftForge.EVENT_BUS.register(this);}
    @Override public void initElements(){elements.entities.add(()->entity);elements.items.add(()->new SpawnEggItem(entity,-1,-1,new Item.Properties().group(ItemGroup.MISC)).setRegistryName("leaf_monster_spawn_egg"));}
    @SubscribeEvent public void addFeatureToBiomes(BiomeLoadingEvent event){
        if (!OreSpawnLogic.allowNaturalSpawn(event, "leaf_monster")) return;
        event.getSpawns().getSpawner(EntityClassification.MONSTER).add(new MobSpawnInfo.Spawners(entity,8,1,2));}
    @Override public void init(FMLCommonSetupEvent event){EntitySpawnPlacementRegistry.register(entity,EntitySpawnPlacementRegistry.PlacementType.ON_GROUND,Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,LeafMonsterEntity::canSpawn);}
    public static boolean canSpawn(EntityType<? extends MobEntity> type,IServerWorld world,SpawnReason reason,BlockPos pos,Random random){return reason!=SpawnReason.NATURAL||(world.getDifficulty()!=Difficulty.PEACEFUL && (!world.getWorld().isDaytime() && pos.getY() >= 50));}
    public static class EntityAttributesRegisterHandler{@SubscribeEvent public void onEntityAttributeCreation(EntityAttributeCreationEvent event){event.put(entity,CustomEntity.createAttributes().create());}}
    public static class CustomEntity extends OreSpawnGroundMonsterBase {
        public CustomEntity(FMLPlayMessages.SpawnEntity packet, World world) { this(entity, world); }
        public CustomEntity(EntityType<? extends CustomEntity> type, World world) {
            super(type, world); this.experienceValue = 5;
        }
        public static AttributeModifierMap.MutableAttribute createAttributes() {
            return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 6D)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.25D)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 2D)
                .createMutableAttribute(Attributes.ARMOR, 1D)
                .createMutableAttribute(Attributes.FOLLOW_RANGE, 12D);
        }
        @Override public IPacket<?> createSpawnPacket() { return NetworkHooks.getEntitySpawningPacket(this); }
        @Override protected int targetInterval(){return 4;}
        @Override protected double searchRange(){return 4;}
        @Override protected double verticalSearchRange(){return 6;}
        @Override protected double combatSpeed(){return 1.25D;}
        @Override protected double meleeDistanceSq(){return 5.0D;}
        @Override protected boolean originalTarget(LivingEntity e){return OreSpawnEntityBase.validHostileTarget(this,e)&&(e instanceof PlayerEntity||OreSpawnLogic.isNamed(e,"ant","butterfly","luna_moth"));}
        @Override public void livingTick(){super.livingTick();if(!world.isRemote&&getAttackTarget()==null){getNavigator().clearPath();setMotion(0,getMotion().y,0);rotationPitch=0;rotationYaw=Math.round(rotationYaw/90F)*90F;renderYawOffset=rotationYaw;}}
        @Override protected SoundEvent getHurtSound(DamageSource s){return OreSpawnLogic.sound("leaves_hit",SoundEvents.BLOCK_GRASS_BREAK);}
        @Override protected SoundEvent getDeathSound(){return OreSpawnLogic.sound("leaves_death",SoundEvents.BLOCK_GRASS_BREAK);}
        @Override protected void dropSpecialItems(DamageSource s,int l,boolean h){int n=rand.nextInt(3);entityDropItem(n==0?Blocks.OAK_LOG.asItem():n==1?Blocks.DARK_OAK_LOG.asItem():Items.APPLE);}
    }
}
