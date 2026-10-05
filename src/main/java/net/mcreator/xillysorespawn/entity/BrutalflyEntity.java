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

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.entity.projectile.SmallFireballEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.Difficulty;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.server.ServerWorld;

import net.mcreator.xillysorespawn.XillysOrespawnModElements;
import net.mcreator.xillysorespawn.entity.renderer.BrutalflyRenderer;

/** Behavioural port of OreSpawn 1.7.10 Brutalfly. */
@XillysOrespawnModElements.ModElement.Tag
public class BrutalflyEntity extends XillysOrespawnModElements.ModElement {
    public static EntityType entity = EntityType.Builder.<CustomEntity>create(CustomEntity::new, EntityClassification.MONSTER)
        .setShouldReceiveVelocityUpdates(true).setTrackingRange(500).setUpdateInterval(3)
        .setCustomClientFactory(CustomEntity::new).size(5.0F, 2.0F)
        .build("brutalfly").setRegistryName("brutalfly");

    public BrutalflyEntity(XillysOrespawnModElements instance) {
        super(instance, 1);
        FMLJavaModLoadingContext.get().getModEventBus().register(new BrutalflyRenderer.ModelRegisterHandler());
        FMLJavaModLoadingContext.get().getModEventBus().register(new EntityAttributesRegisterHandler());
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override public void initElements() {
        elements.entities.add(() -> entity);
        elements.items.add(() -> new SpawnEggItem(entity, -1, -1,
            new Item.Properties().group(ItemGroup.MISC)).setRegistryName("brutalfly_spawn_egg"));
    }

    @SubscribeEvent public void addFeatureToBiomes(BiomeLoadingEvent event) {
        if (!OreSpawnLogic.allowNaturalSpawn(event, "brutalfly")) return;
        event.getSpawns().getSpawner(EntityClassification.MONSTER)
            .add(new MobSpawnInfo.Spawners(entity, 5, 1, 2));
    }

    @Override public void init(FMLCommonSetupEvent event) {
        EntitySpawnPlacementRegistry.register(entity, EntitySpawnPlacementRegistry.PlacementType.NO_RESTRICTIONS,
            Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, BrutalflyEntity::canSpawn);
    }

    public static boolean canSpawn(EntityType<? extends MonsterEntity> type, IServerWorld world,
            SpawnReason reason, BlockPos pos, Random random) {
        if (reason == SpawnReason.SPAWNER || reason == SpawnReason.SPAWN_EGG
                || reason == SpawnReason.COMMAND) return true;
        if (pos.getY() < 70 || world.getWorld().isDaytime()
                || !MonsterEntity.canMonsterSpawnInLight(type, world, reason, pos, random)) return false;
        for (int x = -3; x < 3; x++) for (int z = -4; z < 4; z++) for (int y = 1; y < 10; y++)
            if (!world.getWorld().isAirBlock(pos.add(x, y, z))) return false;
        return world.getEntitiesWithinAABB(CustomEntity.class,
            new AxisAlignedBB(pos).grow(64.0D, 32.0D, 64.0D), e -> true).isEmpty();
    }

    public static class EntityAttributesRegisterHandler {
        @SubscribeEvent public void onEntityAttributeCreation(EntityAttributeCreationEvent event) {
            event.put(entity, CustomEntity.createAttributes().create());
        }
    }

    public static class CustomEntity extends MonsterEntity {
        private BlockPos flightTarget;
        private int lastX;
        private int lastY;
        private int lastZ;
        private int stuckCount;
        private int wingSound;
        private int healthTicker = 100;

        public CustomEntity(FMLPlayMessages.SpawnEntity packet, World world) { this(entity, world); }
        public CustomEntity(EntityType<? extends CustomEntity> type, World world) {
            super(type, world);
            this.experienceValue = 100;
            this.setNoAI(false);
            this.setNoGravity(true);
        }

        public static AttributeModifierMap.MutableAttribute createAttributes() {
            return MonsterEntity.func_233666_p_()
                .createMutableAttribute(Attributes.MAX_HEALTH, 110.0D)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.35D)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 10.0D)
                .createMutableAttribute(Attributes.ARMOR, 6.0D)
                .createMutableAttribute(Attributes.FOLLOW_RANGE, 64.0D);
        }

        @Override public IPacket<?> createSpawnPacket() { return NetworkHooks.getEntitySpawningPacket(this); }
        @Override protected void registerGoals() { super.registerGoals(); }

        public int getBrutalflyHealth() { return MathHelper.ceil(this.getHealth()); }
        public int getAttacking() { return this.getAttackTarget() == null ? 0 : 1; }
        public boolean isChildModel() { return false; }
        public Random getModelRandom() { return this.rand; }

        @Override public void livingTick() {
            super.livingTick();
            this.setNoGravity(true);
            this.setMotion(this.getMotion().mul(1.0D, 0.6D, 1.0D));
            if (++this.wingSound > 30) {
                if (!this.world.isRemote) this.playSound(
                    OreSpawnLogic.sound("mothra_wings", SoundEvents.ENTITY_PHANTOM_FLAP), 1.0F, 1.0F);
                this.wingSound = 0;
            }
            if (--this.healthTicker <= 0) {
                if (this.getHealth() < this.getMaxHealth()) this.heal(1.0F);
                this.healthTicker = 100;
            }
            if (!this.world.isRemote) updateFlightAndCombat();
        }

        private void updateFlightAndCombat() {
            int x = MathHelper.floor(this.getPosX());
            int y = MathHelper.floor(this.getPosY());
            int z = MathHelper.floor(this.getPosZ());
            if (x == this.lastX && y == this.lastY && z == this.lastZ) this.stuckCount++;
            else { this.stuckCount = 0; this.lastX = x; this.lastY = y; this.lastZ = z; }
            if (this.flightTarget == null) this.flightTarget = this.getPosition();

            if (this.stuckCount > 30 || this.rand.nextInt(200) == 0
                    || this.flightTarget.distanceSq(this.getPosition()) < 9.0D) chooseFlightTarget();

            int shoot = this.world.getDifficulty() == Difficulty.HARD ? 2 : 3;
            if (this.rand.nextInt(6) == 0) {
                PlayerEntity player = this.world.getClosestPlayer(this, 30.0D);
                if (player != null && Math.abs(player.getPosY() - this.getPosY()) <= 20.0D
                        && !player.abilities.isCreativeMode && this.canEntityBeSeen(player)) {
                    this.flightTarget = player.getPosition().up(4);
                    if (this.rand.nextInt(shoot) == 0) rangedAttack(player);
                } else if (this.rand.nextInt(3) == 0) {
                    LivingEntity target = findSomethingToAttack();
                    if (target != null) {
                        this.flightTarget = target.getPosition().up(5);
                        if (this.getDistanceSq(target) > 25.0D) {
                            if (this.rand.nextInt(shoot) == 0) rangedAttack(target);
                        } else this.attackEntityAsMob(target);
                    }
                }
            }

            double dx = this.flightTarget.getX() + 0.5D - this.getPosX();
            double dy = this.flightTarget.getY() + 0.1D - this.getPosY();
            double dz = this.flightTarget.getZ() + 0.5D - this.getPosZ();
            Vector3d m = this.getMotion();
            this.setMotion(m.x + (Math.signum(dx) * 0.5D - m.x) * 0.30001D,
                m.y + (Math.signum(dy) * 0.7D - m.y) * 0.20001D,
                m.z + (Math.signum(dz) * 0.5D - m.z) * 0.30001D);
            float wanted = (float)(MathHelper.atan2(this.getMotion().z, this.getMotion().x)
                * 180.0D / Math.PI) - 90.0F;
            this.rotationYaw += MathHelper.wrapDegrees(wanted - this.rotationYaw) / 8.0F;
            this.renderYawOffset = this.rotationYaw;
        }

        private void chooseFlightTarget() {
            int groundDistance = 20;
            for (int ox = -5; ox <= 5; ox += 5) for (int oz = -5; oz <= 5; oz += 5)
                for (int d = 1; d < 20; d++) if (!this.world.isAirBlock(
                        new BlockPos(this.getPosX() + ox, this.getPosY() - d, this.getPosZ() + oz))) {
                    groundDistance = Math.min(groundDistance, d); break;
                }
            int down = groundDistance > 10 ? groundDistance - 9 : 0;
            BlockPos origin = this.getPosition();
            for (int tries = 0; tries < 30; tries++) {
                int nx = (this.rand.nextInt(20) + 8) * (this.rand.nextBoolean() ? 1 : -1);
                int nz = (this.rand.nextInt(20) + 8) * (this.rand.nextBoolean() ? 1 : -1);
                BlockPos p = origin.add(nx, this.rand.nextInt(7) - 1 - down, nz);
                if (this.world.isAirBlock(p) && OreSpawnLogic.hasClearPath(this,
                        p.getX(), p.getY(), p.getZ())) { this.flightTarget = p; break; }
            }
            this.stuckCount = 0;
        }

        private LivingEntity findSomethingToAttack() {
            if (OreSpawnLogic.playNicely != 0) return null;
            return OreSpawnLogic.nearestTarget(this, this.getBoundingBox().grow(25.0D, 20.0D, 25.0D), e -> {
                if (e instanceof CustomEntity || OreSpawnLogic.isNamed(e, "mothra", "vortex")
                        || OreSpawnLogic.isIgnoreable(e) || !this.canEntityBeSeen(e)) return false;
                return e instanceof MonsterEntity
                    || (e instanceof PlayerEntity && !((PlayerEntity)e).abilities.isCreativeMode);
            });
        }

        private void rangedAttack(LivingEntity target) {
            double cx = this.getPosX() - 2.25D * Math.sin(Math.toRadians(this.rotationYaw));
            double cy = this.getPosY();
            double cz = this.getPosZ() + 2.25D * Math.cos(Math.toRadians(this.rotationYaw));
            double ax = target.getPosX() - cx;
            double ay = target.getPosY() + 0.55D - cy;
            double az = target.getPosZ() - cz;
            Entity projectile;
            boolean small = this.world.getDifficulty() == Difficulty.EASY
                || (this.world.getDifficulty() == Difficulty.NORMAL && this.rand.nextBoolean());
            if (small) {
                projectile = new SmallFireballEntity(this.world, this, ax, ay, az);
                this.playSound(SoundEvents.ENTITY_ARROW_SHOOT, 0.75F,
                    1.0F / (this.rand.nextFloat() * 0.4F + 0.8F));
            } else {
                projectile = new FireballEntity(this.world, this, ax, ay, az);
                this.playSound(SoundEvents.ENTITY_CREEPER_PRIMED, 1.0F,
                    1.0F / (this.rand.nextFloat() * 0.4F + 0.8F));
            }
            projectile.setPosition(cx, cy, cz);
            this.world.addEntity(projectile);
            if (this.getHealth() < this.getMaxHealth()) this.heal(1.0F);
        }

        @Override public boolean attackEntityFrom(DamageSource source, float amount) {
            Entity attacker = source.getTrueSource();
            if (attacker instanceof CustomEntity) return false;
            boolean result = super.attackEntityFrom(source, amount);
            if (attacker != null) this.flightTarget = attacker.getPosition().up(2);
            return result;
        }

        @Override protected void dropSpecialItems(DamageSource source, int looting, boolean recentlyHit) {
            super.dropSpecialItems(source, looting, recentlyHit);
            if (this.world instanceof ServerWorld) for (int i = 0; i < 20; i++)
                ((ServerWorld)this.world).spawnParticle(ParticleTypes.EXPLOSION,
                    this.getPosX() + (this.rand.nextFloat() - 0.5F) * 8.0F,
                    this.getPosY() + 2.0D + (this.rand.nextFloat() - 0.5F) * 4.0F,
                    this.getPosZ() + (this.rand.nextFloat() - 0.5F) * 8.0F,
                    1, 0.0D, 0.0D, 0.0D, 0.0D);
            for (int i = 0; i < 53; i++) OreSpawnLogic.drop(this.world, this,
                net.minecraft.item.Items.GOLD_NUGGET, 1, 8);
            for (int i = 0; i < 20; i++) OreSpawnLogic.spawn(this.world, "butterfly",
                this.getPosX() + 0.5D, this.getPosY() + 1.0D, this.getPosZ() + 0.5D);
        }

        @Override protected SoundEvent getAmbientSound() { return null; }
        @Override protected SoundEvent getHurtSound(DamageSource source) { return null; }
        @Override protected SoundEvent getDeathSound() { return SoundEvents.ENTITY_GENERIC_EXPLODE; }
        @Override protected float getSoundVolume() { return 1.5F; }
        @Override public boolean onLivingFall(float distance, float multiplier) { return false; }
        @Override public boolean isOnLadder() { return false; }
        @Override public boolean canBePushed() { return false; }
        @Override protected void collideWithEntity(Entity entityIn) { }
        @Override protected void collideWithNearbyEntities() { }
    }
}
