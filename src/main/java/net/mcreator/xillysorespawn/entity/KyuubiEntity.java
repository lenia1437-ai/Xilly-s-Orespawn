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
import net.mcreator.xillysorespawn.entity.renderer.KyuubiRenderer;

@XillysOrespawnModElements.ModElement.Tag
public class KyuubiEntity extends XillysOrespawnModElements.ModElement {
    public static EntityType entity = (EntityType.Builder.<CustomEntity>create(CustomEntity::new, EntityClassification.MONSTER)
        .setShouldReceiveVelocityUpdates(true).setTrackingRange(128).setUpdateInterval(3)
        .setCustomClientFactory(CustomEntity::new).size(0.5F, 1.25F))
        .build("kyuubi").setRegistryName("kyuubi");
    public KyuubiEntity(XillysOrespawnModElements instance){super(instance,44);FMLJavaModLoadingContext.get().getModEventBus().register(new KyuubiRenderer.ModelRegisterHandler());FMLJavaModLoadingContext.get().getModEventBus().register(new EntityAttributesRegisterHandler());MinecraftForge.EVENT_BUS.register(this);}
    @Override public void initElements(){elements.entities.add(()->entity);elements.items.add(()->new SpawnEggItem(entity,-1,-1,new Item.Properties().group(ItemGroup.MISC)).setRegistryName("kyuubi_spawn_egg"));}
    @SubscribeEvent public void addFeatureToBiomes(BiomeLoadingEvent event){
        if (!OreSpawnLogic.allowNaturalSpawn(event, "kyuubi")) return;
        event.getSpawns().getSpawner(EntityClassification.MONSTER).add(new MobSpawnInfo.Spawners(entity,3,1,2));}
    @Override public void init(FMLCommonSetupEvent event){EntitySpawnPlacementRegistry.register(entity,EntitySpawnPlacementRegistry.PlacementType.ON_GROUND,Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,KyuubiEntity::canSpawn);}
    public static boolean canSpawn(EntityType<? extends MobEntity> type,IServerWorld world,SpawnReason reason,BlockPos pos,Random random){return world.getDifficulty()!=Difficulty.PEACEFUL && (true);}
    private static boolean openAir(IServerWorld world,BlockPos pos,int needed){int count=0;for(int x=-1;x<=1;x++)for(int z=-1;z<=1;z++)if(world.getWorld().isAirBlock(pos.add(x,0,z)))count++;return count>=needed;}
    public static class EntityAttributesRegisterHandler{@SubscribeEvent public void onEntityAttributeCreation(EntityAttributeCreationEvent event){event.put(entity,CustomEntity.createAttributes().create());}}
    public static class CustomEntity extends OreSpawnBossBase {
        private int hurtTimer;
        public CustomEntity(FMLPlayMessages.SpawnEntity packet, World world) { this(entity, world); }
        public CustomEntity(EntityType<? extends CustomEntity> type, World world) {
            super(type, world); this.experienceValue = 30;
        }
        public static AttributeModifierMap.MutableAttribute createAttributes() {
            return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 125D)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.25D)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 10D)
                .createMutableAttribute(Attributes.ARMOR, 10D)
                .createMutableAttribute(Attributes.FOLLOW_RANGE, 24D);
        }
        @Override public IPacket<?> createSpawnPacket() { return NetworkHooks.getEntitySpawningPacket(this); }
        @Override protected double searchRange() { return 12; }
        @Override protected double verticalSearchRange() { return 4; }
        @Override protected double meleeDistanceSq() { return 4; }
        @Override protected double combatSpeed() { return 1.25; }
        @Override protected boolean originalTarget(LivingEntity target) {
            if (!OreSpawnEntityBase.validHostileTarget(this, target) || OreSpawnLogic.playNicely != 0) return false;
            if (OreSpawnLogic.isNamed(target, getType().getRegistryName().getPath(), "zombified_piglin")) return false;
            return !(target instanceof MonsterEntity);
        }
        @Override public boolean attackEntityAsMob(Entity target) {
            boolean hit = super.attackEntityAsMob(target);
            if (hit) {
                OreSpawnEntityBase.knockAway(this, target, 0.2, 0.1);
                
            }
            return hit;
        }
        @Override public boolean attackEntityFrom(DamageSource source, float amount) {
            if (source == DamageSource.CACTUS) return false;
            boolean hit = super.attackEntityFrom(source, amount);
            if (hit) {
                this.hurtTimer = 0;
                if (source.getTrueSource() instanceof LivingEntity)
                    this.setAttackTarget((LivingEntity) source.getTrueSource());
            }
            return hit;
        }
        @Override public void livingTick() {
            super.livingTick();
            if (this.hurtTimer > 0) this.hurtTimer--;
            if (!this.world.isRemote && this.rand.nextInt(400) == 1 && this.getHealth() < this.getMaxHealth()) this.heal(2.0F);
            if(!world.isRemote&&getAttackTarget()!=null&&getDistanceSq(getAttackTarget())<64&&rand.nextInt(6)==0){LivingEntity e=getAttackTarget();SmallFireballEntity f=new SmallFireballEntity(world,this,e.getPosX()-getPosX(),e.getPosY()+.75-getPosY(),e.getPosZ()-getPosZ());f.setPosition(getPosX(),getPosY()+1.25,getPosZ());world.addEntity(f);}
        }
        @Override protected SoundEvent getAmbientSound() { return OreSpawnLogic.sound("kyuubi_living",SoundEvents.ENTITY_FOX_AMBIENT); }
        @Override protected SoundEvent getHurtSound(DamageSource source) { return OreSpawnLogic.sound("alo_hurt",SoundEvents.ENTITY_FOX_HURT); }
        @Override protected SoundEvent getDeathSound() { return OreSpawnLogic.sound("alo_death",SoundEvents.ENTITY_FOX_DEATH); }
        @Override protected float getSoundVolume() { return 1.5F; }
        @Override protected void dropSpecialItems(DamageSource source, int looting, boolean recentlyHit) {
            super.dropSpecialItems(source, looting, recentlyHit);
            OreSpawnLogic.drop(world,this,Items.GOLD_INGOT,10,4); OreSpawnLogic.drop(world,this,Items.IRON_BLOCK,3,4); OreSpawnLogic.drop(world,this,Items.REDSTONE_BLOCK,4,4);
        }
    }
}
