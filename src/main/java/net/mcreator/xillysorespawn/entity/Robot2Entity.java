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
import net.mcreator.xillysorespawn.entity.renderer.Robot2Renderer;

@XillysOrespawnModElements.ModElement.Tag
public class Robot2Entity extends XillysOrespawnModElements.ModElement {
    public static EntityType entity = (EntityType.Builder.<CustomEntity>create(CustomEntity::new,EntityClassification.MONSTER)
        .setShouldReceiveVelocityUpdates(true).setTrackingRange(128).setUpdateInterval(3)
        .setCustomClientFactory(CustomEntity::new).size(3F,6.2F))
        .build("robot_2").setRegistryName("robot_2");
    public Robot2Entity(XillysOrespawnModElements instance){super(instance,62);FMLJavaModLoadingContext.get().getModEventBus().register(new Robot2Renderer.ModelRegisterHandler());FMLJavaModLoadingContext.get().getModEventBus().register(new EntityAttributesRegisterHandler());MinecraftForge.EVENT_BUS.register(this);}
    @Override public void initElements(){elements.entities.add(()->entity);elements.items.add(()->new SpawnEggItem(entity,-1,-1,new Item.Properties().group(ItemGroup.MISC)).setRegistryName("robot_2_spawn_egg"));}
    @SubscribeEvent public void addFeatureToBiomes(BiomeLoadingEvent event){
        if (!OreSpawnLogic.allowNaturalSpawn(event, "robot_2")) return;
        event.getSpawns().getSpawner(EntityClassification.MONSTER).add(new MobSpawnInfo.Spawners(entity,2,1,1));}
    @Override public void init(FMLCommonSetupEvent event){EntitySpawnPlacementRegistry.register(entity,EntitySpawnPlacementRegistry.PlacementType.ON_GROUND,Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,Robot2Entity::canSpawn);}
    public static boolean canSpawn(EntityType<? extends MobEntity> type,IServerWorld world,SpawnReason reason,BlockPos pos,Random random){return reason!=SpawnReason.NATURAL||(world.getDifficulty()!=Difficulty.PEACEFUL && (!world.getWorld().isDaytime() && pos.getY() >= 50));}
    public static class EntityAttributesRegisterHandler{@SubscribeEvent public void onEntityAttributeCreation(EntityAttributeCreationEvent event){event.put(entity,CustomEntity.createAttributes().create());}}
    public static class CustomEntity extends OreSpawnGroundMonsterBase {
        public CustomEntity(FMLPlayMessages.SpawnEntity packet, World world) { this(entity, world); }
        public CustomEntity(EntityType<? extends CustomEntity> type, World world) {
            super(type, world); this.experienceValue = 100;
        }
        public static AttributeModifierMap.MutableAttribute createAttributes() {
            return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 200D)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.3D)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 22D)
                .createMutableAttribute(Attributes.ARMOR, 18D)
                .createMutableAttribute(Attributes.FOLLOW_RANGE, 32D);
        }
        @Override public IPacket<?> createSpawnPacket() { return NetworkHooks.getEntitySpawningPacket(this); }
        @Override protected double searchRange(){return 24;} @Override protected double meleeDistanceSq(){return 42;} @Override protected double combatSpeed(){return 1;}
        @Override public void jump(){setMotion(getMotion().add(0,.25,0));super.jump();}
        @Override public void livingTick(){super.livingTick();if(!world.isRemote&&OreSpawnLogic.playNicely==0&&world.getGameRules().getBoolean(GameRules.MOB_GRIEFING)&&getAttackTarget()!=null&&getDistanceSq(getAttackTarget())<42){for(int i=0;i<8;i++){BlockPos p=getPosition().add(rand.nextInt(13)-6,rand.nextInt(8),rand.nextInt(13)-6);BlockState b=world.getBlockState(p);if(!b.isAir()&&b.getBlockHardness(world,p)>=0&&b.getBlockHardness(world,p)<5)world.destroyBlock(p,false,this);}}}
        @Override protected void dropSpecialItems(DamageSource s,int l,boolean h){OreSpawnLogic.drop(world,this,Blocks.IRON_BLOCK.asItem(),2+rand.nextInt(8),4);OreSpawnLogic.drop(world,this,Items.IRON_INGOT,5+rand.nextInt(6),4);}
    }
}
