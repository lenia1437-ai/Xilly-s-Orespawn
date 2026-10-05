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

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.Heightmap;

import net.mcreator.xillysorespawn.XillysOrespawnModElements;
import net.mcreator.xillysorespawn.entity.renderer.CrabRenderer;

/** Behavioural port of OreSpawn 1.7.10 Crab. */
@XillysOrespawnModElements.ModElement.Tag
public class CrabEntity extends XillysOrespawnModElements.ModElement {
    public static EntityType entity = EntityType.Builder.<CustomEntity>create(CustomEntity::new, EntityClassification.MONSTER)
        .setShouldReceiveVelocityUpdates(true).setTrackingRange(30).setUpdateInterval(3)
        .setCustomClientFactory(CustomEntity::new).size(3.75F, 3.5F)
        .build("crab").setRegistryName("crab");

    public CrabEntity(XillysOrespawnModElements instance) {
        super(instance, 1);
        FMLJavaModLoadingContext.get().getModEventBus().register(new CrabRenderer.ModelRegisterHandler());
        FMLJavaModLoadingContext.get().getModEventBus().register(new EntityAttributesRegisterHandler());
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override public void initElements() {
        elements.entities.add(() -> entity);
        elements.items.add(() -> new SpawnEggItem(entity, -1, -1,
            new Item.Properties().group(ItemGroup.MISC)).setRegistryName("crab_spawn_egg"));
    }

    @SubscribeEvent public void addFeatureToBiomes(BiomeLoadingEvent event) {
        if (!OreSpawnLogic.allowNaturalSpawn(event, "crab")) return;
        event.getSpawns().getSpawner(EntityClassification.MONSTER)
            .add(new MobSpawnInfo.Spawners(entity, 5, 1, 2));
    }

    @Override public void init(FMLCommonSetupEvent event) {
        EntitySpawnPlacementRegistry.register(entity, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND,
            Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, CrabEntity::canSpawn);
    }

    public static boolean canSpawn(EntityType<? extends MonsterEntity> type, IServerWorld world,
            SpawnReason reason, BlockPos pos, Random random) {
        if (reason == SpawnReason.SPAWNER || reason == SpawnReason.SPAWN_EGG
                || reason == SpawnReason.COMMAND) return true;
        if (pos.getY() < 50 || !world.getWorld().isDaytime()) return false;
        if (OreSpawnLogic.isCrystalDimension(world.getWorld())) {
            if (random.nextInt(40) != 1) return false;
            int nearby = world.getEntitiesWithinAABB(CustomEntity.class,
                new AxisAlignedBB(pos).grow(24.0D, 8.0D, 24.0D), e -> true).size();
            if (nearby > 3) return false;
        }
        return true;
    }

    public static class EntityAttributesRegisterHandler {
        @SubscribeEvent public void onEntityAttributeCreation(EntityAttributeCreationEvent event) {
            event.put(entity, CustomEntity.createAttributes().create());
        }
    }

    public static class CustomEntity extends MonsterEntity {
        private static final DataParameter<Float> SCALE = EntityDataManager.createKey(CustomEntity.class, DataSerializers.FLOAT);
        private static final DataParameter<Byte> ATTACKING = EntityDataManager.createKey(CustomEntity.class, DataSerializers.BYTE);
        private float sizeScale = 0.25F;
        private int hurtTimer;

        public CustomEntity(FMLPlayMessages.SpawnEntity packet, World world) { this(entity, world); }
        public CustomEntity(EntityType<? extends CustomEntity> type, World world) {
            super(type, world);
            this.setNoAI(false);
            if (!world.isRemote) chooseRandomScale();
        }

        public static AttributeModifierMap.MutableAttribute createAttributes() {
            return MonsterEntity.func_233666_p_()
                .createMutableAttribute(Attributes.MAX_HEALTH, 250.0D)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.55D)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 24.0D)
                .createMutableAttribute(Attributes.ARMOR, 18.0D)
                .createMutableAttribute(Attributes.FOLLOW_RANGE, 32.0D);
        }

        @Override protected void registerData() {
            super.registerData();
            this.dataManager.register(SCALE, 0.25F);
            this.dataManager.register(ATTACKING, (byte)0);
        }

        @Override protected void registerGoals() {
            super.registerGoals();
            this.goalSelector.addGoal(0, new SwimGoal(this));
            this.goalSelector.addGoal(1, new OreSpawnWanderGoal(this, 16, 30, 1.0D, false));
            this.goalSelector.addGoal(2, new LookAtGoal(this, PlayerEntity.class, 10.0F));
            this.goalSelector.addGoal(3, new LookAtGoal(this, LivingEntity.class, 8.0F));
            this.goalSelector.addGoal(4, new LookRandomlyGoal(this));
            this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        }

        @Override public IPacket<?> createSpawnPacket() { return NetworkHooks.getEntitySpawningPacket(this); }

        private void chooseRandomScale() {
            float value = 0.25F;
            if (this.rand.nextInt(4) == 1) value = 0.5F;
            if (this.rand.nextInt(8) == 2) value = 1.0F;
            setCrabScale(value);
        }

        private void applyScaleAttributes(boolean refillHealth) {
            float scale = getCrabScale();
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(250.0D * scale);
            this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(24.0D * scale);
            this.getAttribute(Attributes.ARMOR).setBaseValue(16.0D + 2.0D * scale);
            this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue((this.isInWater() ? 0.95D : 0.55D) * scale);
            this.experienceValue = Math.max(1, (int)(400.0F * scale));
            if (refillHealth) this.setHealth(this.getMaxHealth());
        }

        @Override @Nullable
        public ILivingEntityData onInitialSpawn(IServerWorld world, DifficultyInstance difficulty, SpawnReason reason,
                @Nullable ILivingEntityData spawnData, @Nullable CompoundNBT dataTag) {
            ILivingEntityData data = super.onInitialSpawn(world, difficulty, reason, spawnData, dataTag);
            if (reason == SpawnReason.SPAWNER) setCrabScale(0.35F);
            else chooseRandomScale();
            applyScaleAttributes(true);
            return data;
        }

        public float getCrabScale() { return this.dataManager.get(SCALE); }
        public void setCrabScale(float scale) {
            this.sizeScale = scale;
            this.dataManager.set(SCALE, scale);
            this.recalculateSize();
            if (!this.world.isRemote && this.getAttribute(Attributes.MAX_HEALTH) != null)
                applyScaleAttributes(false);
        }

        @Override
        public EntitySize getSize(Pose pose) {
            float scale = this.sizeScale > 0.0F ? this.sizeScale : 0.25F;
            return EntitySize.flexible(2.5F * scale, 3.5F * scale);
        }

        @Override
        public void notifyDataManagerChange(DataParameter<?> key) {
            super.notifyDataManagerChange(key);
            if (SCALE.equals(key)) {
                this.sizeScale = this.dataManager.get(SCALE);
                this.recalculateSize();
            }
        }
        public int getCrabHealth() { return MathHelper.ceil(this.getHealth()); }
        public int getAttacking() { return this.dataManager.get(ATTACKING); }
        public void setAttacking(int value) { this.dataManager.set(ATTACKING, (byte)value); }
        public boolean isChildModel() { return false; }
        public Random getModelRandom() { return this.rand; }

        @Override public void writeAdditional(CompoundNBT nbt) {
            super.writeAdditional(nbt);
            nbt.putFloat("Fscale", getCrabScale());
        }

        @Override public void readAdditional(CompoundNBT nbt) {
            super.readAdditional(nbt);
            if (nbt.contains("Fscale")) setCrabScale(nbt.getFloat("Fscale"));
            applyScaleAttributes(false);
        }

        @Override public void livingTick() {
            super.livingTick();
            if (this.getAttribute(Attributes.MOVEMENT_SPEED) != null)
                this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(
                    (this.isInWater() ? 0.95D : 0.55D) * getCrabScale());
            if (this.world.isRemote) return;
            if (this.hurtTimer > 0) this.hurtTimer--;

            if (!this.isInWater() && this.rand.nextInt(25) == 0) seekWaterOrDryOut();
            if (this.rand.nextInt(5) == 1) updateCombat();
            if (this.rand.nextInt(120) == 1 && this.isInWater() && this.getHealth() < this.getMaxHealth()) {
                this.playSound(SoundEvents.ENTITY_GENERIC_SPLASH, 1.5F, this.rand.nextFloat() * 0.2F + 0.9F);
                this.heal(4.0F * getCrabScale());
            }
        }

        private void seekWaterOrDryOut() {
            BlockPos water = OreLoot.scanShell(this.world, this.getPosition().down(), 10, 10, 10,
                (w, p) -> w.getFluidState(p).isTagged(FluidTags.WATER));
            if (water != null) this.getNavigator().tryMoveToXYZ(
                water.getX(), water.getY() - 1, water.getZ(), 1.33D);
            else if (this.rand.nextInt(100) == 1) this.attackEntityFrom(DamageSource.STARVE, getCrabScale());
        }

        private void updateCombat() {
            LivingEntity target = this.getAttackTarget();
            if (this.rand.nextInt(100) == 1) { this.setAttackTarget(null); target = null; }
            if (target != null && !target.isAlive()) { this.setAttackTarget(null); target = null; }
            if (target == null) target = findSomethingToAttack();
            if (target == null) { setAttacking(0); return; }
            this.getLookController().setLookPositionWithEntity(target, 10.0F, 10.0F);
            double reach = (6.0F + target.getWidth() / 2.0F);
            if (this.getDistanceSq(target) < reach * reach * getCrabScale()) {
                setAttacking(1);
                if (this.rand.nextInt(4) == 0 || this.rand.nextInt(5) == 1) {
                    this.attackEntityAsMob(target);
                    this.playSound(OreSpawnLogic.sound(this.rand.nextInt(3) == 1
                        ? "scorpion_attack" : "scorpion_living", SoundEvents.ENTITY_SPIDER_HURT), 0.75F, 1.5F);
                }
            } else {
                setAttacking(0);
                this.getNavigator().tryMoveToEntityLiving(target, 1.0D);
            }
        }

        private LivingEntity findSomethingToAttack() {
            if (OreSpawnLogic.playNicely != 0) return null;
            LivingEntity current = this.getAttackTarget();
            if (current != null && current.isAlive()) return current;
            LivingEntity found = OreSpawnLogic.nearestTarget(this,
                this.getBoundingBox().grow(16.0D, 6.0D, 16.0D), e -> {
                    if (e instanceof CustomEntity || !this.canEntityBeSeen(e)) return false;
                    if (e instanceof PlayerEntity) return !((PlayerEntity)e).abilities.isCreativeMode;
                    return OreSpawnLogic.isAttackableNonMob(e)
                        || OreSpawnLogic.isNamed(e, "lizard", "rubber_ducky");
                });
            this.setAttackTarget(found);
            return found;
        }

        @Override public boolean attackEntityAsMob(Entity target) {
            boolean hit = target.attackEntityFrom(DamageSource.causeMobDamage(this), 24.0F * getCrabScale());
            if (hit && target instanceof LivingEntity) {
                double strength = 1.15D * getCrabScale();
                double lift = 0.48D * getCrabScale();
                if (target instanceof PlayerEntity) lift *= 2.0D;
                float angle = (float)Math.atan2(target.getPosZ() - this.getPosZ(), target.getPosX() - this.getPosX());
                target.addVelocity(Math.cos(angle) * strength, lift, Math.sin(angle) * strength);
            }
            return hit;
        }

        @Override public boolean attackEntityFrom(DamageSource source, float amount) {
            if (source == DamageSource.CACTUS) return false;
            Entity attacker = source.getTrueSource();
            if (attacker instanceof CustomEntity) return false;
            boolean result = false;
            if (this.hurtTimer <= 0) {
                result = super.attackEntityFrom(source, amount);
                this.hurtTimer = 8;
            }
            if (attacker instanceof LivingEntity) {
                this.setAttackTarget((LivingEntity)attacker);
                this.getNavigator().tryMoveToEntityLiving((LivingEntity)attacker, 1.2D);
            }
            return result;
        }

        @Override protected void dropSpecialItems(DamageSource source, int looting, boolean recentlyHit) {
            super.dropSpecialItems(source, looting, recentlyHit);
            int count = Math.max(1, (int)((4 + this.rand.nextInt(8)) * getCrabScale()));
            for (int i = 0; i < count; i++) OreSpawnLogic.drop(this.world, this,
                OreSpawnLogic.item("raw_crab_meat", "crab_meat_raw"), 1, 2);
        }

        @Override protected SoundEvent getAmbientSound() { return null; }
        @Override protected SoundEvent getHurtSound(DamageSource source) {
            return OreSpawnLogic.sound("leaves_hit", SoundEvents.BLOCK_GRASS_HIT);
        }
        @Override protected SoundEvent getDeathSound() { return null; }
        @Override protected float getSoundVolume() { return 0.75F; }
        @Override public boolean canBreatheUnderwater() { return true; }
    }
}
