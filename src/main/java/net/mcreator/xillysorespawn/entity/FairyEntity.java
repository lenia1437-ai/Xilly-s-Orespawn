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
import net.mcreator.xillysorespawn.entity.renderer.FairyRenderer;

@XillysOrespawnModElements.ModElement.Tag
public class FairyEntity extends XillysOrespawnModElements.ModElement {
    public static EntityType entity = (EntityType.Builder.<CustomEntity>create(CustomEntity::new, EntityClassification.CREATURE)
        .setShouldReceiveVelocityUpdates(true).setTrackingRange(128).setUpdateInterval(3)
        .setCustomClientFactory(CustomEntity::new).size(0.4F, 0.8F))
        .build("fairy").setRegistryName("fairy");
    public FairyEntity(XillysOrespawnModElements instance){super(instance,32);FMLJavaModLoadingContext.get().getModEventBus().register(new FairyRenderer.ModelRegisterHandler());FMLJavaModLoadingContext.get().getModEventBus().register(new EntityAttributesRegisterHandler());MinecraftForge.EVENT_BUS.register(this);}
    @Override public void initElements(){elements.entities.add(()->entity);elements.items.add(()->new SpawnEggItem(entity,-1,-1,new Item.Properties().group(ItemGroup.MISC)).setRegistryName("fairy_spawn_egg"));}
    @SubscribeEvent public void addFeatureToBiomes(BiomeLoadingEvent event){
        if (!OreSpawnLogic.allowNaturalSpawn(event, "fairy")) return;
        event.getSpawns().getSpawner(EntityClassification.CREATURE).add(new MobSpawnInfo.Spawners(entity,5,1,2));}
    @Override public void init(FMLCommonSetupEvent event){EntitySpawnPlacementRegistry.register(entity,EntitySpawnPlacementRegistry.PlacementType.NO_RESTRICTIONS,Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,FairyEntity::canSpawn);}
    public static boolean canSpawn(EntityType<? extends MobEntity> type,IServerWorld world,SpawnReason reason,BlockPos pos,Random random){return (reason != SpawnReason.NATURAL || (pos.getY() >= 50 && openAir(world, pos, 6)));}
    private static boolean openAir(IServerWorld world,BlockPos pos,int needed){int count=0;for(int x=-1;x<=1;x++)for(int z=-1;z<=1;z++)if(world.getWorld().isAirBlock(pos.add(x,0,z)))count++;return count>=needed;}
    public static class EntityAttributesRegisterHandler{@SubscribeEvent public void onEntityAttributeCreation(EntityAttributeCreationEvent event){event.put(entity,CustomEntity.createAttributes().create());}}
    public static class CustomEntity extends OreSpawnFlyingBase {
        public CustomEntity(FMLPlayMessages.SpawnEntity packet, World world) { this(entity, world); }
        public CustomEntity(EntityType<? extends CustomEntity> type, World world) {
            super(type, world); this.experienceValue = 10; if (!world.isRemote) this.setVariant(this.rand.nextInt(9));
        }
        public static AttributeModifierMap.MutableAttribute createAttributes() {
            return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 40D)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.1D)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 3D)
                .createMutableAttribute(Attributes.ARMOR, 4D)
                .createMutableAttribute(Attributes.FOLLOW_RANGE, 32D);
        }
        @Override public IPacket<?> createSpawnPacket() { return NetworkHooks.getEntitySpawningPacket(this); }
        @Override protected double horizontalSpeed() { return 0.2; }
        @Override protected double verticalSpeed() { return 0.7; }
        @Override protected double horizontalBlend() { return 0.1; }
        @Override protected double verticalBlend() { return 0.1; }
        @Override protected int horizontalRange() { return 8; }
        @Override protected int retargetFrequency() { return 200; }        @Override protected void registerGoals() { super.registerGoals(); this.goalSelector.addGoal(6, new LookAtGoal(this, LivingEntity.class, 8.0F)); this.goalSelector.addGoal(7, new LookRandomlyGoal(this)); }
        @Override protected void originalFlightTick() {
            if (this.rand.nextInt(12) == 0 && OreSpawnLogic.playNicely == 0) {
                LivingEntity target = OreSpawnEntityBase.nearest(this, 8, 8,
                    e -> e instanceof MonsterEntity && OreSpawnEntityBase.validHostileTarget(this, e));
                if (target != null) { this.flightTarget = target.getPosition().up(); if (this.getDistanceSq(target) < 6.0D) this.attackEntityAsMob(target); }
            } else if (this.getOwner() != null) {
                LivingEntity owner = this.getOwner();
                if (this.getDistanceSq(owner) > 64.0D) this.flightTarget = owner.getPosition().up();
                if (this.getDistanceSq(owner) > 256.0D) this.setPosition(owner.getPosX(), owner.getPosY(), owner.getPosZ());
            }
            super.originalFlightTick();
            if (this.rand.nextInt(250) == 1) this.heal(1.0F);
        }
        @Override protected SoundEvent getHurtSound(DamageSource source) { return OreSpawnLogic.sound("rat_hit", SoundEvents.ENTITY_BAT_HURT); }
        @Override protected SoundEvent getDeathSound() { return OreSpawnLogic.sound("big_splat", SoundEvents.ENTITY_BAT_DEATH); }
        @Override protected void dropSpecialItems(DamageSource source, int looting, boolean recentlyHit) { OreSpawnLogic.drop(this.world, this, "crystal_torch", 1, 0); }
    }
}
