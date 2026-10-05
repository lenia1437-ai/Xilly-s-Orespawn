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
import net.mcreator.xillysorespawn.entity.renderer.GhostRenderer;

@XillysOrespawnModElements.ModElement.Tag
public class GhostEntity extends XillysOrespawnModElements.ModElement {
    public static EntityType entity = (EntityType.Builder.<CustomEntity>create(CustomEntity::new, EntityClassification.CREATURE)
        .setShouldReceiveVelocityUpdates(true).setTrackingRange(128).setUpdateInterval(3)
        .setCustomClientFactory(CustomEntity::new).size(0.5F, 1.5F))
        .build("ghost").setRegistryName("ghost");
    public GhostEntity(XillysOrespawnModElements instance){super(instance,34);FMLJavaModLoadingContext.get().getModEventBus().register(new GhostRenderer.ModelRegisterHandler());FMLJavaModLoadingContext.get().getModEventBus().register(new EntityAttributesRegisterHandler());MinecraftForge.EVENT_BUS.register(this);}
    @Override public void initElements(){elements.entities.add(()->entity);elements.items.add(()->new SpawnEggItem(entity,-1,-1,new Item.Properties().group(ItemGroup.MISC)).setRegistryName("ghost_spawn_egg"));}
    @SubscribeEvent public void addFeatureToBiomes(BiomeLoadingEvent event){
        if (!OreSpawnLogic.allowNaturalSpawn(event, "ghost")) return;
        event.getSpawns().getSpawner(EntityClassification.CREATURE).add(new MobSpawnInfo.Spawners(entity,3,1,2));}
    @Override public void init(FMLCommonSetupEvent event){EntitySpawnPlacementRegistry.register(entity,EntitySpawnPlacementRegistry.PlacementType.NO_RESTRICTIONS,Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,GhostEntity::canSpawn);}
    public static boolean canSpawn(EntityType<? extends MobEntity> type,IServerWorld world,SpawnReason reason,BlockPos pos,Random random){return (reason != SpawnReason.NATURAL || !world.getWorld().isDaytime());}
    private static boolean openAir(IServerWorld world,BlockPos pos,int needed){int count=0;for(int x=-1;x<=1;x++)for(int z=-1;z<=1;z++)if(world.getWorld().isAirBlock(pos.add(x,0,z)))count++;return count>=needed;}
    public static class EntityAttributesRegisterHandler{@SubscribeEvent public void onEntityAttributeCreation(EntityAttributeCreationEvent event){event.put(entity,CustomEntity.createAttributes().create());}}
    public static class CustomEntity extends OreSpawnFlyingBase {
        public CustomEntity(FMLPlayMessages.SpawnEntity packet, World world) { this(entity, world); }
        public CustomEntity(EntityType<? extends CustomEntity> type, World world) {
            super(type, world); this.experienceValue = 5; this.noClip = true;
        }
        public static AttributeModifierMap.MutableAttribute createAttributes() {
            return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 2D)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.1D)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 0D)
                .createMutableAttribute(Attributes.ARMOR, 0D)
                .createMutableAttribute(Attributes.FOLLOW_RANGE, 24D);
        }
        @Override public IPacket<?> createSpawnPacket() { return NetworkHooks.getEntitySpawningPacket(this); }
        @Override protected double horizontalSpeed() { return 0.1; }
        @Override protected double verticalSpeed() { return 0.7; }
        @Override protected double horizontalBlend() { return 0.05; }
        @Override protected double verticalBlend() { return 0.1; }
        @Override protected int horizontalRange() { return 10; }
        @Override protected int retargetFrequency() { return 40; }        @Override protected float yawDivisor() { return 6.0F; }
        @Override protected void chooseFlightTarget() {
            PlayerEntity player = this.world.getClosestPlayer(this, 16.0D);
            if (player != null && !player.abilities.isCreativeMode) this.flightTarget = player.getPosition().up();
            else super.chooseFlightTarget();
        }
        @Override public void livingTick() { if (this.isEntityInsideOpaqueBlock()) this.noClip = false; super.livingTick(); }
        @Override public boolean attackEntityFrom(DamageSource source, float amount) { return source == DamageSource.IN_WALL ? false : super.attackEntityFrom(source, amount); }
        @Override protected SoundEvent getAmbientSound() { return this.rand.nextBoolean() ? OreSpawnLogic.sound("ghost_sound", SoundEvents.ENTITY_VEX_AMBIENT) : null; }
        @Override protected float getSoundVolume() { return .3F; }
    }
}
