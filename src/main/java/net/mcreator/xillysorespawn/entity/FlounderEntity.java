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
import net.mcreator.xillysorespawn.entity.renderer.FlounderRenderer;

@XillysOrespawnModElements.ModElement.Tag
public class FlounderEntity extends XillysOrespawnModElements.ModElement {
    public static EntityType entity = (EntityType.Builder.<CustomEntity>create(CustomEntity::new, EntityClassification.CREATURE)
        .setShouldReceiveVelocityUpdates(true).setTrackingRange(128).setUpdateInterval(3)
        .setCustomClientFactory(CustomEntity::new).size(0.55F, 0.25F))
        .build("flounder").setRegistryName("flounder");
    public FlounderEntity(XillysOrespawnModElements instance){super(instance,36);FMLJavaModLoadingContext.get().getModEventBus().register(new FlounderRenderer.ModelRegisterHandler());FMLJavaModLoadingContext.get().getModEventBus().register(new EntityAttributesRegisterHandler());MinecraftForge.EVENT_BUS.register(this);}
    @Override public void initElements(){elements.entities.add(()->entity);elements.items.add(()->new SpawnEggItem(entity,-1,-1,new Item.Properties().group(ItemGroup.MISC)).setRegistryName("flounder_spawn_egg"));}
    @SubscribeEvent public void addFeatureToBiomes(BiomeLoadingEvent event){
        if (!OreSpawnLogic.allowNaturalSpawn(event, "flounder")) return;
        event.getSpawns().getSpawner(EntityClassification.CREATURE).add(new MobSpawnInfo.Spawners(entity,8,1,4));}
    @Override public void init(FMLCommonSetupEvent event){EntitySpawnPlacementRegistry.register(entity,EntitySpawnPlacementRegistry.PlacementType.IN_WATER,Heightmap.Type.OCEAN_FLOOR,FlounderEntity::canSpawn);}
    public static boolean canSpawn(EntityType<? extends MobEntity> type,IServerWorld world,SpawnReason reason,BlockPos pos,Random random){return (reason != SpawnReason.NATURAL || (world.getWorld().isDaytime() && pos.getY() >= 50));}
    private static boolean openAir(IServerWorld world,BlockPos pos,int needed){int count=0;for(int x=-1;x<=1;x++)for(int z=-1;z<=1;z++)if(world.getWorld().isAirBlock(pos.add(x,0,z)))count++;return count>=needed;}
    public static class EntityAttributesRegisterHandler{@SubscribeEvent public void onEntityAttributeCreation(EntityAttributeCreationEvent event){event.put(entity,CustomEntity.createAttributes().create());}}
    public static class CustomEntity extends OreSpawnAquaticBase {
        public CustomEntity(FMLPlayMessages.SpawnEntity packet, World world) { this(entity, world); }
        public CustomEntity(EntityType<? extends CustomEntity> type, World world) {
            super(type, world); this.experienceValue = 5; this.goalSelector.addGoal(3,new AvoidEntityGoal<PlayerEntity>(this,PlayerEntity.class,8.0F,1.0D,1.4D));
        }
        public static AttributeModifierMap.MutableAttribute createAttributes() {
            return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 5D)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.25D)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 0D)
                .createMutableAttribute(Attributes.ARMOR, 0D)
                .createMutableAttribute(Attributes.FOLLOW_RANGE, 16D);
        }
        @Override public IPacket<?> createSpawnPacket() { return NetworkHooks.getEntitySpawningPacket(this); }
        @Override protected void registerGoals() {
            super.registerGoals();
            this.goalSelector.addGoal(0, new SwimGoal(this));
            this.goalSelector.addGoal(4, new PanicGoal(this, 1.5D));
            this.goalSelector.addGoal(6, new OreSpawnWanderGoal(this, 12, 100, 1.0D, false));
            this.goalSelector.addGoal(7, new LookRandomlyGoal(this));
        }        @Override protected SoundEvent getAmbientSound(){return SoundEvents.ENTITY_GENERIC_SPLASH;}
        @Override protected SoundEvent getHurtSound(DamageSource s){return OreSpawnLogic.sound("little_splat",SoundEvents.ENTITY_COD_HURT);}
        @Override protected SoundEvent getDeathSound(){return OreSpawnLogic.sound("ratdead",SoundEvents.ENTITY_COD_DEATH);}
        @Override protected void dropSpecialItems(DamageSource s,int l,boolean h){for(int i=0;i<1+this.rand.nextInt(2);i++)this.entityDropItem(Items.COD);}
    }
}
