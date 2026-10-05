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
import net.mcreator.xillysorespawn.entity.renderer.GodzillaRenderer;

@XillysOrespawnModElements.ModElement.Tag
public class GodzillaEntity extends XillysOrespawnModElements.ModElement {
    public static EntityType entity = (EntityType.Builder.<CustomEntity>create(CustomEntity::new, EntityClassification.MONSTER)
        .setShouldReceiveVelocityUpdates(true).setTrackingRange(128).setUpdateInterval(1)
        .setCustomClientFactory(CustomEntity::new).size(9.9F, 25F))
        .build("godzilla").setRegistryName("godzilla");
    public GodzillaEntity(XillysOrespawnModElements instance){super(instance,48);FMLJavaModLoadingContext.get().getModEventBus().register(new GodzillaRenderer.ModelRegisterHandler());FMLJavaModLoadingContext.get().getModEventBus().register(new EntityAttributesRegisterHandler());MinecraftForge.EVENT_BUS.register(this);}
    @Override public void initElements(){elements.entities.add(()->entity);elements.items.add(()->new SpawnEggItem(entity,-1,-1,new Item.Properties().group(ItemGroup.MISC)).setRegistryName("godzilla_spawn_egg"));}
    @SubscribeEvent public void addFeatureToBiomes(BiomeLoadingEvent event){
        if (!OreSpawnLogic.allowNaturalSpawn(event, "godzilla")) return;
        event.getSpawns().getSpawner(EntityClassification.MONSTER).add(new MobSpawnInfo.Spawners(entity,1,1,1));}
    @Override public void init(FMLCommonSetupEvent event){EntitySpawnPlacementRegistry.register(entity,EntitySpawnPlacementRegistry.PlacementType.ON_GROUND,Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,GodzillaEntity::canSpawn);}
    public static boolean canSpawn(EntityType<? extends MobEntity> type,IServerWorld world,SpawnReason reason,BlockPos pos,Random random){return world.getDifficulty()!=Difficulty.PEACEFUL && (reason != SpawnReason.NATURAL || (pos.getY() >= 50 && !world.getWorld().isDaytime()));}
    private static boolean openAir(IServerWorld world,BlockPos pos,int needed){int count=0;for(int x=-1;x<=1;x++)for(int z=-1;z<=1;z++)if(world.getWorld().isAirBlock(pos.add(x,0,z)))count++;return count>=needed;}
    public static class EntityAttributesRegisterHandler{@SubscribeEvent public void onEntityAttributeCreation(EntityAttributeCreationEvent event){event.put(entity,CustomEntity.createAttributes().create());}}
    public static class CustomEntity extends OreSpawnBossBase {
        private int hurtTimer;
        public CustomEntity(FMLPlayMessages.SpawnEntity packet, World world) { this(entity, world); }
        public CustomEntity(EntityType<? extends CustomEntity> type, World world) {
            super(type, world); this.experienceValue = 10000;
        }
        public static AttributeModifierMap.MutableAttribute createAttributes() {
            return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 4000D)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.38D)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 175D)
                .createMutableAttribute(Attributes.ARMOR, 21D)
                .createMutableAttribute(Attributes.FOLLOW_RANGE, 64D);
        }
        @Override public IPacket<?> createSpawnPacket() { return NetworkHooks.getEntitySpawningPacket(this); }
        @Override protected double searchRange() { return 50; }
        @Override protected double verticalSearchRange() { return 25; }
        @Override protected double meleeDistanceSq() { return 300; }
        @Override protected double combatSpeed() { return 1; }
        @Override protected boolean originalTarget(LivingEntity target) {
            if (!OreSpawnEntityBase.validHostileTarget(this, target) || OreSpawnLogic.playNicely != 0) return false;
            if (OreSpawnLogic.isNamed(target, getType().getRegistryName().getPath(), "godzilla", "godzilla_head", "creeper", "zombie", "spider")) return false;
            return target instanceof PlayerEntity || target instanceof MonsterEntity || OreSpawnLogic.isAttackableNonMob(target);
        }
        @Override public boolean attackEntityAsMob(Entity target) {
            boolean hit = super.attackEntityAsMob(target);
            if (hit) {
                OreSpawnEntityBase.knockAway(this, target, 2.5, 0.4);
                
            }
            return hit;
        }
        @Override public boolean attackEntityFrom(DamageSource source, float amount) {
            if (source == DamageSource.CACTUS || this.hurtTimer > 0) return false;
            float applied = Math.min(amount, 750.0F); if (source.getTrueSource() instanceof LivingEntity) { LivingEntity attacker = (LivingEntity) source.getTrueSource(); if (attacker.getWidth() * attacker.getHeight() > 30.0F && !OreSpawnLogic.isNamed(attacker, "godzilla", "godzilla_head", "pitch_black", "kraken")) { applied /= 10.0F; this.hurtTimer = 50; } } boolean hit = super.attackEntityFrom(source, applied);
            if (hit) {
                this.hurtTimer = 20;
                if (source.getTrueSource() instanceof LivingEntity)
                    this.setAttackTarget((LivingEntity) source.getTrueSource());
            }
            return hit;
        }
        @Override public void livingTick() {
            super.livingTick();
            if (this.hurtTimer > 0) this.hurtTimer--;
            if (!this.world.isRemote && this.rand.nextInt(35) == 1 && this.getHealth() < this.getMaxHealth()) this.heal(2.0F);
            if(!world.isRemote&&OreSpawnLogic.playNicely==0){if(rand.nextInt(65)==1&&getAttackTarget()!=null){ServerWorld sw=(ServerWorld)world;LightningBoltEntity bolt=EntityType.LIGHTNING_BOLT.create(sw);if(bolt!=null){bolt.moveForced(Vector3d.copyCentered(getAttackTarget().getPosition()));sw.addEntity(bolt);}}if(world.getGameRules().getBoolean(GameRules.MOB_GRIEFING)){BlockPos c=getPosition();for(int x=-6;x<=6;x++)for(int z=-6;z<=6;z++){BlockPos p=c.add(x,rand.nextInt(5),z);BlockState st=world.getBlockState(p);if(!st.isAir()&&st.getBlockHardness(world,p)>=0&&st.getBlockHardness(world,p)<3)world.destroyBlock(p,rand.nextInt(15)==1,this);}}}
        }
        @Override protected SoundEvent getAmbientSound() { return OreSpawnLogic.sound("mobzilla_living",SoundEvents.ENTITY_ENDER_DRAGON_GROWL); }
        @Override protected SoundEvent getHurtSound(DamageSource source) { return OreSpawnLogic.sound("mobzilla_hurt",SoundEvents.ENTITY_ENDER_DRAGON_HURT); }
        @Override protected SoundEvent getDeathSound() { return OreSpawnLogic.sound("mobzilla_death",SoundEvents.ENTITY_ENDER_DRAGON_DEATH); }
        @Override protected float getSoundVolume() { return 1.5F; }
        @Override protected void dropSpecialItems(DamageSource source, int looting, boolean recentlyHit) {
            super.dropSpecialItems(source, looting, recentlyHit);
            OreSpawnLogic.drop(world,this,"mobzilla_scale",16+rand.nextInt(16),10);OreSpawnLogic.drop(world,this,Items.NETHER_STAR,4+rand.nextInt(4),10);
        }
    }
}
