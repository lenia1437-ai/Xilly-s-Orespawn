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
import net.mcreator.xillysorespawn.entity.renderer.StinkyRenderer;

@XillysOrespawnModElements.ModElement.Tag
public class StinkyEntity extends XillysOrespawnModElements.ModElement {
    public static EntityType entity=(EntityType.Builder.<CustomEntity>create(CustomEntity::new,EntityClassification.CREATURE)
        .setShouldReceiveVelocityUpdates(true).setTrackingRange(160).setUpdateInterval(1)
        .setCustomClientFactory(CustomEntity::new).size(0.75F,0.75F)).build("stinky").setRegistryName("stinky");
    public StinkyEntity(XillysOrespawnModElements instance){super(instance,78);FMLJavaModLoadingContext.get().getModEventBus().register(new StinkyRenderer.ModelRegisterHandler());FMLJavaModLoadingContext.get().getModEventBus().register(new EntityAttributesRegisterHandler());MinecraftForge.EVENT_BUS.register(this);}
    @Override public void initElements(){elements.entities.add(()->entity);elements.items.add(()->new SpawnEggItem(entity,-1,-1,new Item.Properties().group(ItemGroup.MISC)).setRegistryName("stinky_spawn_egg"));}
    @SubscribeEvent public void addFeatureToBiomes(BiomeLoadingEvent event){
        if (!OreSpawnLogic.allowNaturalSpawn(event, "stinky")) return;
        event.getSpawns().getSpawner(EntityClassification.CREATURE).add(new MobSpawnInfo.Spawners(entity,2,1,2));}
    @Override public void init(FMLCommonSetupEvent event){EntitySpawnPlacementRegistry.register(entity,EntitySpawnPlacementRegistry.PlacementType.NO_RESTRICTIONS,Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,StinkyEntity::canSpawn);}
    public static boolean canSpawn(EntityType<? extends MobEntity> type,IServerWorld world,SpawnReason reason,BlockPos pos,Random random){return reason!=SpawnReason.NATURAL||((world.getWorld().isDaytime() && pos.getY() >= 50));}
    public static class EntityAttributesRegisterHandler{@SubscribeEvent public void onEntityAttributeCreation(EntityAttributeCreationEvent event){event.put(entity,CustomEntity.createAttributes().create());}}
    public static class CustomEntity extends OreSpawnFlyingBase {
        private int localTimer;
        public CustomEntity(FMLPlayMessages.SpawnEntity packet, World world) { this(entity, world); }
        public CustomEntity(EntityType<? extends CustomEntity> type, World world) {
            super(type, world); this.experienceValue = 35; setActivity(1);setSkin(rand.nextInt(19));
        }
        public static AttributeModifierMap.MutableAttribute createAttributes() {
            return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 100D)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.3D)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 10D)
                .createMutableAttribute(Attributes.ARMOR, 6D)
                .createMutableAttribute(Attributes.FOLLOW_RANGE, 24D);
        }
        @Override public IPacket<?> createSpawnPacket() { return NetworkHooks.getEntitySpawningPacket(this); }
        @Override protected int horizontalRange(){return 12;} @Override protected int verticalRange(){return 4;} @Override protected double horizontalSpeed(){return .38;} @Override protected int retargetFrequency(){return 80;}
        @Override protected void registerGoals(){super.registerGoals();goalSelector.addGoal(6,new LookAtGoal(this,PlayerEntity.class,8));}
        @Override public ActionResultType func_230254_b_(PlayerEntity p,Hand h){ItemStack s=p.getHeldItem(h);if(s.getItem()==Items.COOKED_BEEF){if(!world.isRemote&&(!isOwner(p)?rand.nextBoolean():true)){setOwner(p);heal(getMaxHealth());setActivity(1);}if(!p.abilities.isCreativeMode)s.shrink(1);return ActionResultType.func_233537_a_(world.isRemote);}if(isOwner(p)){setSitting(!isSitting());setActivity(isSitting()?0:1);return ActionResultType.SUCCESS;}return super.func_230254_b_(p,h);}
        @Override public boolean attackEntityFrom(DamageSource s,float a){if(s==DamageSource.CACTUS)return false;setSitting(false);setActivity(2);return super.attackEntityFrom(s,a);}
        @Override public void livingTick(){super.livingTick();if(!world.isRemote){if(rand.nextInt(2000)==1)setSkin(rand.nextInt(19));if(rand.nextInt(100)==1)heal(1);LivingEntity o=getOwner();if(o!=null&&!isSitting()&&getDistanceSq(o)>256){setActivity(2);flightTarget=o.getPosition().up(2);}}}
        @Override protected SoundEvent getHurtSound(DamageSource s){return OreSpawnLogic.sound("duck_hurt",SoundEvents.ENTITY_PARROT_HURT);}
        @Override protected SoundEvent getDeathSound(){return OreSpawnLogic.sound("cryo_death",SoundEvents.ENTITY_PARROT_DEATH);}
    }
}
