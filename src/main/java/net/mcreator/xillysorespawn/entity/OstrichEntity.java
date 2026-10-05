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
import net.mcreator.xillysorespawn.entity.renderer.OstrichRenderer;

@XillysOrespawnModElements.ModElement.Tag
public class OstrichEntity extends XillysOrespawnModElements.ModElement {
    public static EntityType entity = (EntityType.Builder.<CustomEntity>create(CustomEntity::new,EntityClassification.CREATURE)
        .setShouldReceiveVelocityUpdates(true).setTrackingRange(128).setUpdateInterval(3)
        .setCustomClientFactory(CustomEntity::new).size(0.85F,2.1F))
        .build("ostrich").setRegistryName("ostrich");
    public OstrichEntity(XillysOrespawnModElements instance){super(instance,56);FMLJavaModLoadingContext.get().getModEventBus().register(new OstrichRenderer.ModelRegisterHandler());FMLJavaModLoadingContext.get().getModEventBus().register(new EntityAttributesRegisterHandler());MinecraftForge.EVENT_BUS.register(this);}
    @Override public void initElements(){elements.entities.add(()->entity);elements.items.add(()->new SpawnEggItem(entity,-1,-1,new Item.Properties().group(ItemGroup.MISC)).setRegistryName("ostrich_spawn_egg"));}
    @SubscribeEvent public void addFeatureToBiomes(BiomeLoadingEvent event){
        if (!OreSpawnLogic.allowNaturalSpawn(event, "ostrich")) return;
        event.getSpawns().getSpawner(EntityClassification.CREATURE).add(new MobSpawnInfo.Spawners(entity,8,1,3));}
    @Override public void init(FMLCommonSetupEvent event){EntitySpawnPlacementRegistry.register(entity,EntitySpawnPlacementRegistry.PlacementType.ON_GROUND,Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,OstrichEntity::canSpawn);}
    public static boolean canSpawn(EntityType<? extends MobEntity> type,IServerWorld world,SpawnReason reason,BlockPos pos,Random random){return reason!=SpawnReason.NATURAL||((pos.getY() >= 50));}
    public static class EntityAttributesRegisterHandler{@SubscribeEvent public void onEntityAttributeCreation(EntityAttributeCreationEvent event){event.put(entity,CustomEntity.createAttributes().create());}}
    public static class CustomEntity extends OreSpawnCreatureBase {
        public CustomEntity(FMLPlayMessages.SpawnEntity packet, World world) { this(entity, world); }
        public CustomEntity(EntityType<? extends CustomEntity> type, World world) {
            super(type, world); this.experienceValue = 10;
        }
        public static AttributeModifierMap.MutableAttribute createAttributes() {
            return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 25D)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.38D)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 6D)
                .createMutableAttribute(Attributes.ARMOR, 3D)
                .createMutableAttribute(Attributes.FOLLOW_RANGE, 24D);
        }
        @Override public IPacket<?> createSpawnPacket() { return NetworkHooks.getEntitySpawningPacket(this); }
        @Override protected void registerGoals(){super.registerGoals();goalSelector.addGoal(0,new SwimGoal(this));goalSelector.addGoal(1,new PanicGoal(this,1.45));goalSelector.addGoal(2,new MeleeAttackGoal(this,1.2,false));goalSelector.addGoal(3,new TemptGoal(this,1.2,Ingredient.fromItems(Items.WHEAT_SEEDS),false));goalSelector.addGoal(4,new OreSpawnWanderGoal(this,18,100,1,false));goalSelector.addGoal(5,new LookAtGoal(this,PlayerEntity.class,8));}
        @Override public ActionResultType func_230254_b_(PlayerEntity p,Hand h){ItemStack s=p.getHeldItem(h);if(s.getItem()==Items.WHEAT_SEEDS){if(!world.isRemote&&(!isOwner(p)?rand.nextInt(3)==0:true)){setOwner(p);set_is_activated(1);setHatColor(1+rand.nextInt(3));heal(getMaxHealth());}if(!p.abilities.isCreativeMode)s.shrink(1);return ActionResultType.func_233537_a_(world.isRemote);}if(isOwner(p)&&s.getItem()==Blocks.GOLD_BLOCK.asItem()){if(!world.isRemote){ownerId=null;setSitting(false);set_is_activated(0);}if(!p.abilities.isCreativeMode)s.shrink(1);return ActionResultType.func_233537_a_(world.isRemote);}if(isOwner(p)){setSitting(!isSitting());return ActionResultType.SUCCESS;}return super.func_230254_b_(p,h);}
        @Override public void livingTick(){super.livingTick();if(!world.isRemote&&getOwner()!=null&&!isSitting()){if(getDistanceSq(getOwner())>4)getNavigator().tryMoveToEntityLiving(getOwner(),2);if(rand.nextInt(300)==1)heal(1);}}
        @Override protected SoundEvent getAmbientSound(){return OreSpawnLogic.sound("ostrich_living",SoundEvents.ENTITY_CHICKEN_AMBIENT);}
        @Override protected SoundEvent getHurtSound(DamageSource s){return OreSpawnLogic.sound("ostrich_hurt",SoundEvents.ENTITY_CHICKEN_HURT);}
        @Override protected SoundEvent getDeathSound(){return OreSpawnLogic.sound("ostrich_death",SoundEvents.ENTITY_CHICKEN_DEATH);}
        @Override protected void dropSpecialItems(DamageSource s,int l,boolean h){entityDropItem(Items.CHICKEN);if(rand.nextInt(5)==0)entityDropItem(Items.EGG);}
    }
}
