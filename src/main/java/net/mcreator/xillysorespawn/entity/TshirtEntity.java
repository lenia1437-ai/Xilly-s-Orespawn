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
import net.mcreator.xillysorespawn.entity.renderer.TshirtRenderer;

@XillysOrespawnModElements.ModElement.Tag
public class TshirtEntity extends XillysOrespawnModElements.ModElement {
    public static EntityType entity=(EntityType.Builder.<CustomEntity>create(CustomEntity::new,EntityClassification.CREATURE)
        .setShouldReceiveVelocityUpdates(true).setTrackingRange(160).setUpdateInterval(1)
        .setCustomClientFactory(CustomEntity::new).size(4F,4F)).build("tshirt").setRegistryName("tshirt");
    public TshirtEntity(XillysOrespawnModElements instance){super(instance,89);FMLJavaModLoadingContext.get().getModEventBus().register(new TshirtRenderer.ModelRegisterHandler());FMLJavaModLoadingContext.get().getModEventBus().register(new EntityAttributesRegisterHandler());MinecraftForge.EVENT_BUS.register(this);}
    @Override public void initElements(){elements.entities.add(()->entity);elements.items.add(()->new SpawnEggItem(entity,-1,-1,new Item.Properties().group(ItemGroup.MISC)).setRegistryName("tshirt_spawn_egg"));}
    @SubscribeEvent public void addFeatureToBiomes(BiomeLoadingEvent event){
        if (!OreSpawnLogic.allowNaturalSpawn(event, "tshirt")) return;
        event.getSpawns().getSpawner(EntityClassification.CREATURE).add(new MobSpawnInfo.Spawners(entity,1,1,1));}
    @Override public void init(FMLCommonSetupEvent event){EntitySpawnPlacementRegistry.register(entity,EntitySpawnPlacementRegistry.PlacementType.ON_GROUND,Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,TshirtEntity::canSpawn);}
    public static boolean canSpawn(EntityType<? extends MobEntity> type,IServerWorld world,SpawnReason reason,BlockPos pos,Random random){return reason!=SpawnReason.NATURAL||((world.getWorld().isDaytime() && pos.getY() >= 50));}
    public static class EntityAttributesRegisterHandler{@SubscribeEvent public void onEntityAttributeCreation(EntityAttributeCreationEvent event){event.put(entity,CustomEntity.createAttributes().create());}}
    public static class CustomEntity extends OreSpawnCreatureBase {
        private int localTimer;
        public CustomEntity(FMLPlayMessages.SpawnEntity packet, World world) { this(entity, world); }
        public CustomEntity(EntityType<? extends CustomEntity> type, World world) {
            super(type, world); this.experienceValue = 40; setNoAI(true);
        }
        public static AttributeModifierMap.MutableAttribute createAttributes() {
            return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 1D)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0D)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 0D)
                .createMutableAttribute(Attributes.ARMOR, 0D)
                .createMutableAttribute(Attributes.FOLLOW_RANGE, 8D);
        }
        @Override public IPacket<?> createSpawnPacket() { return NetworkHooks.getEntitySpawningPacket(this); }
        @Override protected void registerGoals(){super.registerGoals();}
        @Override public void livingTick(){super.livingTick();setMotion(0,getMotion().y,0);}
        @Override protected void dropSpecialItems(DamageSource s,int l,boolean h){entityDropItem(Items.LEATHER_CHESTPLATE);}
    }
}
