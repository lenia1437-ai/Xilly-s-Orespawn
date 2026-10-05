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
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.*;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.entity.projectile.SmallFireballEntity;
import net.minecraft.item.*;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.server.ServerWorld;
import net.mcreator.xillysorespawn.XillysOrespawnModElements;
import net.mcreator.xillysorespawn.entity.renderer.MothraRenderer;

/** Behavioural port of OreSpawn 20.3 Mothra for Forge 1.16.5. */
@XillysOrespawnModElements.ModElement.Tag
public class MothraEntity extends XillysOrespawnModElements.ModElement {
    public static final EntityType entity = EntityType.Builder
        .<CustomEntity>create(CustomEntity::new, EntityClassification.AMBIENT)
        .setShouldReceiveVelocityUpdates(true).setTrackingRange(128).setUpdateInterval(1)
        .setCustomClientFactory(CustomEntity::new).size(5.0F, 2.0F)
        .build("mothra").setRegistryName("mothra");

    public MothraEntity(XillysOrespawnModElements instance) {
        super(instance, 1);
        FMLJavaModLoadingContext.get().getModEventBus().register(new MothraRenderer.ModelRegisterHandler());
        FMLJavaModLoadingContext.get().getModEventBus().register(new AttributesHandler());
        MinecraftForge.EVENT_BUS.register(this);
    }
    @Override public void initElements() {
        elements.entities.add(() -> entity);
        elements.items.add(() -> new SpawnEggItem(entity, 0x5F466F, 0xD6DB64,
            new Item.Properties().group(ItemGroup.MISC)).setRegistryName("mothra_spawn_egg"));
    }
    @SubscribeEvent public void addFeatureToBiomes(BiomeLoadingEvent event) {
        if (!OreSpawnLogic.allowNaturalSpawn(event, "mothra")) return;
        ResourceLocation id = event.getName();
        if (id != null && (id.getPath().equals("mountains") || id.getPath().equals("wooded_mountains")))
            event.getSpawns().getSpawner(EntityClassification.AMBIENT)
                .add(new MobSpawnInfo.Spawners(entity, 2, 1, 1));
    }
    @Override public void init(FMLCommonSetupEvent event) {
        EntitySpawnPlacementRegistry.register(entity, EntitySpawnPlacementRegistry.PlacementType.NO_RESTRICTIONS,
            Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MothraEntity::canSpawn);
    }
    public static boolean canSpawn(EntityType<? extends MonsterEntity> type, IServerWorld world,
            SpawnReason reason, BlockPos pos, Random random) {
        if (reason == SpawnReason.SPAWNER || reason == SpawnReason.SPAWN_EGG || reason == SpawnReason.COMMAND)
            return true;
        if (pos.getY() < 70 || world.getWorld().isDaytime() || !world.getWorld().isAirBlock(pos)) return false;
        for (int x = -4; x < 4; x++) for (int z = -4; z < 4; z++) for (int y = 1; y < 10; y++)
            if (!world.getWorld().isAirBlock(pos.add(x, y, z))) return false;
        return world.getEntitiesWithinAABB(CustomEntity.class,
            new AxisAlignedBB(pos).grow(64.0D, 32.0D, 64.0D), e -> true).isEmpty();
    }
    public static class AttributesHandler {
        @SubscribeEvent public void register(EntityAttributeCreationEvent event) {
            event.put(entity, CustomEntity.createAttributes().create());
        }
    }

    public static class CustomEntity extends MonsterEntity {
        private BlockPos flightTarget;
        private int lastX, lastY, lastZ, stuckCount, wingSound;
        private int healthTicker = 100;

        public CustomEntity(FMLPlayMessages.SpawnEntity packet, World world) { this(entity, world); }
        public CustomEntity(EntityType<? extends CustomEntity> type, World world) {
            super(type, world);
            this.experienceValue = 100;
            this.setNoGravity(true);
        }
        public static AttributeModifierMap.MutableAttribute createAttributes() {
            return MonsterEntity.func_233666_p_()
                .createMutableAttribute(Attributes.MAX_HEALTH, 150.0D)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.35D)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 12.0D)
                .createMutableAttribute(Attributes.ARMOR, 8.0D)
                .createMutableAttribute(Attributes.FOLLOW_RANGE, 64.0D);
        }
        @Override public IPacket<?> createSpawnPacket() { return NetworkHooks.getEntitySpawningPacket(this); }
        @Override protected void registerGoals() { super.registerGoals(); }
        public int getMothraHealth() { return MathHelper.ceil(this.getHealth()); }

        @Override public void livingTick() {
            super.livingTick();
            this.setNoGravity(true);
            this.setMotion(this.getMotion().mul(1.0D, 0.6D, 1.0D));
            if (++wingSound > 30) {
                if (!world.isRemote) playSound(OreSpawnLogic.sound("mothrawings",
                    SoundEvents.ENTITY_PHANTOM_FLAP), 1.0F, 1.0F);
                wingSound = 0;
            }
            if (--healthTicker <= 0) {
                if (getHealth() < getMaxHealth()) heal(1.0F);
                healthTicker = 200;
            }
            if (!world.isRemote) updateFlightAndCombat();
        }

        private void updateFlightAndCombat() {
            int x = MathHelper.floor(getPosX()), y = MathHelper.floor(getPosY()), z = MathHelper.floor(getPosZ());
            if (x == lastX && y == lastY && z == lastZ) stuckCount++;
            else { stuckCount = 0; lastX = x; lastY = y; lastZ = z; }
            if (flightTarget == null) flightTarget = getPosition();
            if (stuckCount > 50 || rand.nextInt(300) == 0 || flightTarget.distanceSq(getPosition()) < 9.0D)
                chooseFlightTarget();

            if (world.getDifficulty() != Difficulty.PEACEFUL && rand.nextInt(10) == 0) {
                LivingEntity target = findPlayerTarget();
                if (target == null && rand.nextInt(3) == 0) target = findSomethingToAttack();
                if (target != null) {
                    flightTarget = target.getPosition().up(target instanceof PlayerEntity ? 4 : 5);
                    if (rand.nextInt(world.getDifficulty() == Difficulty.HARD ? 2 : 3) == 0) rangedAttack(target);
                }
            }
            OreSpawnEntityBase.steerFlight(this, flightTarget, 0.5D, 0.7D, 0.30001D, 0.20001D, 4.0F);
        }

        private LivingEntity findPlayerTarget() {
            PlayerEntity player = world.getClosestPlayer(this, 25.0D);
            return player != null && Math.abs(player.getPosY() - getPosY()) <= 20.0D
                && !player.abilities.isCreativeMode && canEntityBeSeen(player) ? player : null;
        }

        private LivingEntity findSomethingToAttack() {
            if (OreSpawnLogic.playNicely != 0) return null;
            return OreSpawnLogic.nearestTarget(this, getBoundingBox().grow(15.0D, 20.0D, 15.0D), target -> {
                if (target == this || !target.isAlive() || OreSpawnLogic.isIgnoreable(target)
                        || !canEntityBeSeen(target)) return false;
                if (target instanceof PlayerEntity && ((PlayerEntity) target).abilities.isCreativeMode) return false;
                return !OreSpawnLogic.isNamed(target, "mothra", "brutalfly", "vortex", "velocity_raptor",
                    "cryolophosaurus", "terrible_terror", "lurking_terror", "cloud_shark",
                    "rotator", "bee", "mantis");
            });
        }

        private void chooseFlightTarget() {
            int distance = 20;
            for (int ox = -5; ox <= 5; ox += 5) for (int oz = -5; oz <= 5; oz += 5)
                for (int d = 1; d < 20; d++) {
                    if (!world.isAirBlock(getPosition().add(ox, -d, oz))) {
                        distance = Math.min(distance, d);
                        break;
                    }
                }
            int down = distance > 10 ? distance - 9 : 0;
            BlockPos origin = getPosition();
            for (int tries = 0; tries < 50; tries++) {
                int dx = (rand.nextInt(20) + 8) * (rand.nextBoolean() ? 1 : -1);
                int dz = (rand.nextInt(20) + 8) * (rand.nextBoolean() ? 1 : -1);
                BlockPos candidate = origin.add(dx, rand.nextInt(7) - 1 - down, dz);
                if (world.isAirBlock(candidate) && OreSpawnLogic.hasClearPath(this,
                        candidate.getX(), candidate.getY(), candidate.getZ())) {
                    flightTarget = candidate;
                    break;
                }
            }
            stuckCount = 0;
        }

        private void rangedAttack(LivingEntity target) {
            if (world.getDifficulty() == Difficulty.PEACEFUL) return;
            double cx = getPosX() - 2.25D * Math.sin(Math.toRadians(rotationYaw));
            double cy = getPosY();
            double cz = getPosZ() + 2.25D * Math.cos(Math.toRadians(rotationYaw));
            double ax = target.getPosX() - cx;
            double ay = target.getPosY() + 0.55D - cy;
            double az = target.getPosZ() - cz;
            Entity projectile;
            boolean small = world.getDifficulty() == Difficulty.EASY
                || world.getDifficulty() == Difficulty.NORMAL && rand.nextBoolean();
            if (small) {
                projectile = new SmallFireballEntity(world, this, ax, ay, az);
                playSound(SoundEvents.ENTITY_ARROW_SHOOT, 0.75F, 1.0F / (rand.nextFloat() * 0.4F + 0.8F));
            } else {
                projectile = new FireballEntity(world, this, ax, ay, az);
                playSound(SoundEvents.ENTITY_CREEPER_PRIMED, 1.0F, 1.0F / (rand.nextFloat() * 0.4F + 0.8F));
            }
            projectile.setPosition(cx, cy, cz);
            world.addEntity(projectile);
            if (getHealth() < getMaxHealth()) heal(1.0F);
        }

        @Override public boolean attackEntityFrom(DamageSource source, float amount) {
            Entity attacker = source.getTrueSource();
            if (attacker instanceof CustomEntity) return false;
            boolean result = super.attackEntityFrom(source, amount);
            if (attacker != null) flightTarget = attacker.getPosition().up(2);
            return result;
        }

        @Override protected void dropSpecialItems(DamageSource source, int looting, boolean recentlyHit) {
            super.dropSpecialItems(source, looting, recentlyHit);
            OreSpawnLogic.drop(world, this, Items.ITEM_FRAME, 1, 8);
            for (int i = 0; i < 53; i++) OreSpawnLogic.drop(world, this, Items.GOLD_NUGGET, 1, 8);
            for (int i = 0; i < 25; i++) OreSpawnLogic.drop(world, this, "moth_scale", 1, 8);
            for (int i = 0; i < 3; i++) OreSpawnLogic.drop(world, this, Items.BLAZE_ROD, 1, 8);
            OreSpawnLogic.drop(world, this, Items.NETHER_STAR, 1, 8);
            if (world instanceof ServerWorld) {
                ((ServerWorld) world).spawnParticle(ParticleTypes.EXPLOSION, getPosX(), getPosY() + 2.0D,
                    getPosZ(), 20, 4.0D, 2.0D, 4.0D, 0.0D);
                for (int i = 0; i < 20; i++) OreSpawnLogic.spawn(world, "moth",
                    getPosX() + 0.5D, getPosY() + 1.0D, getPosZ() + 0.5D);
            }
        }

        @Override protected SoundEvent getAmbientSound() { return null; }
        @Override protected SoundEvent getHurtSound(DamageSource source) { return null; }
        @Override protected SoundEvent getDeathSound() { return SoundEvents.ENTITY_GENERIC_EXPLODE; }
        @Override protected float getSoundVolume() { return 1.5F; }
        @Override public boolean onLivingFall(float distance, float multiplier) { return false; }
        @Override public boolean isOnLadder() { return false; }
        @Override public boolean canBePushed() { return false; }
        @Override protected void collideWithEntity(Entity entityIn) {}
        @Override protected void collideWithNearbyEntities() {}
    }
}
