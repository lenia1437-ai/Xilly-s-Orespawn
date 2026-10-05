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
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.MoveThroughVillageAtNightGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.entity.projectile.SmallFireballEntity;
import net.minecraft.entity.projectile.SnowballEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.Difficulty;
import net.minecraft.world.Explosion;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.server.ServerWorld;

import net.mcreator.xillysorespawn.XillysOrespawnModElements;
import net.mcreator.xillysorespawn.entity.renderer.DragonRenderer;
import net.mcreator.xillysorespawn.entity.renderer.RenderInfo;

/** Behavioural port of OreSpawn 1.7.10 Dragon. */
@XillysOrespawnModElements.ModElement.Tag
public class DragonEntity extends XillysOrespawnModElements.ModElement {
    public static EntityType entity = EntityType.Builder.<CustomEntity>create(CustomEntity::new, EntityClassification.CREATURE)
        .setShouldReceiveVelocityUpdates(true).setTrackingRange(128).setUpdateInterval(1).immuneToFire()
        .setCustomClientFactory(CustomEntity::new).size(1.5F, 1.25F)
        .build("dragon").setRegistryName("dragon");

    public DragonEntity(XillysOrespawnModElements instance) {
        super(instance, 1);
        FMLJavaModLoadingContext.get().getModEventBus().register(new DragonRenderer.ModelRegisterHandler());
        FMLJavaModLoadingContext.get().getModEventBus().register(new EntityAttributesRegisterHandler());
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override public void initElements() {
        elements.entities.add(() -> entity);
        elements.items.add(() -> new SpawnEggItem(entity, -1, -1,
            new Item.Properties().group(ItemGroup.MISC)).setRegistryName("dragon_spawn_egg"));
    }

    @SubscribeEvent public void addFeatureToBiomes(BiomeLoadingEvent event) {
        if (!OreSpawnLogic.allowNaturalSpawn(event, "dragon")) return;
        event.getSpawns().getSpawner(EntityClassification.CREATURE)
            .add(new MobSpawnInfo.Spawners(entity, 5, 1, 2));
    }

    @Override public void init(FMLCommonSetupEvent event) {
        EntitySpawnPlacementRegistry.register(entity, EntitySpawnPlacementRegistry.PlacementType.NO_RESTRICTIONS,
            Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DragonEntity::canSpawn);
    }

    public static boolean canSpawn(EntityType<? extends net.minecraft.entity.CreatureEntity> type, IServerWorld world,
            SpawnReason reason, BlockPos pos, Random random) {
        if (reason == SpawnReason.SPAWNER || reason == SpawnReason.SPAWN_EGG
                || reason == SpawnReason.COMMAND) return true;
        if (!world.getWorld().isDaytime()) return false;
        if (!OreSpawnLogic.isIslandsDimension(world.getWorld()) && pos.getY() < 50) return false;
        return world.getEntitiesWithinAABB(CustomEntity.class,
            new AxisAlignedBB(pos).grow(16.0D, 6.0D, 16.0D), e -> true).isEmpty();
    }

    public static class EntityAttributesRegisterHandler {
        @SubscribeEvent public void onEntityAttributeCreation(EntityAttributeCreationEvent event) {
            event.put(entity, CustomEntity.createAttributes().create());
        }
    }

    public static class CustomEntity extends TameableEntity {
        private static final DataParameter<Byte> ATTACKING = EntityDataManager.createKey(CustomEntity.class, DataSerializers.BYTE);
        private static final DataParameter<Byte> ACTIVITY = EntityDataManager.createKey(CustomEntity.class, DataSerializers.BYTE);
        private static final DataParameter<Integer> DRAGON_TYPE = EntityDataManager.createKey(CustomEntity.class, DataSerializers.VARINT);
        private static final DataParameter<Integer> DRAGON_FIRE = EntityDataManager.createKey(CustomEntity.class, DataSerializers.VARINT);
        private final RenderInfo renderData = new RenderInfo();
        private BlockPos flightTarget;
        private boolean targetInSight;
        private int ownerFlying;
        private int flyAway;
        private int stuckCount;
        private int lastX;
        private int lastZ;
        private int unstickTimer;
        private int fireballTicker;
        private int hurtTimer;
        private int wingSound;
        private float deltaSmooth;

        public CustomEntity(FMLPlayMessages.SpawnEntity packet, World world) { this(entity, world); }
        public CustomEntity(EntityType<? extends CustomEntity> type, World world) {
            super(type, world);
            this.experienceValue = 100;
            this.setNoAI(false);
            this.setTamed(false);
        }

        public static AttributeModifierMap.MutableAttribute createAttributes() {
            return TameableEntity.func_233666_p_()
                .createMutableAttribute(Attributes.MAX_HEALTH, 200.0D)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.32D)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 35.0D)
                .createMutableAttribute(Attributes.ARMOR, 14.0D)
                .createMutableAttribute(Attributes.FOLLOW_RANGE, 40.0D);
        }

        @Override protected void registerData() {
            super.registerData();
            this.dataManager.register(ATTACKING, (byte)0);
            this.dataManager.register(ACTIVITY, (byte)0);
            this.dataManager.register(DRAGON_TYPE, 0);
            this.dataManager.register(DRAGON_FIRE, 1);
        }

        @Override protected void registerGoals() {
            super.registerGoals();
            this.goalSelector.addGoal(0, new SwimGoal(this));
            this.goalSelector.addGoal(1, new OreSpawnFollowOwnerGoal(this, 1.1D, 12.0F, 2.0F));
            this.goalSelector.addGoal(2, new TemptGoal(this, 1.25D,
                net.minecraft.item.crafting.Ingredient.fromItems(Items.BEEF), false));
            this.goalSelector.addGoal(3, new OreSpawnWanderGoal(this, 10, 90, 0.75D, true));
            this.goalSelector.addGoal(4, new LookAtGoal(this, PlayerEntity.class, 9.0F));
            this.goalSelector.addGoal(5, new LookRandomlyGoal(this));
            this.goalSelector.addGoal(6, new MoveThroughVillageAtNightGoal(this, 50));
            this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
        }

        @Override public IPacket<?> createSpawnPacket() { return NetworkHooks.getEntitySpawningPacket(this); }
        public int getDragonHealth() { return MathHelper.ceil(this.getHealth()); }
        public int getAttacking() { return this.dataManager.get(ATTACKING); }
        public void setAttacking(int value) { if (!this.world.isRemote) this.dataManager.set(ATTACKING, (byte)value); }
        public int getActivity() { return this.dataManager.get(ACTIVITY); }
        public void setActivity(int value) { if (!this.world.isRemote) this.dataManager.set(ACTIVITY, (byte)value); }
        public int getDragonType() { return this.dataManager.get(DRAGON_TYPE); }
        public void setDragonType(int value) { this.dataManager.set(DRAGON_TYPE, value); }
        public int getDragonFire() { return this.dataManager.get(DRAGON_FIRE); }
        public void setDragonFire(int value) { if (!this.world.isRemote) this.dataManager.set(DRAGON_FIRE, value); }
        public RenderInfo getRenderInfo() { return this.renderData; }
        public void setRenderInfo(RenderInfo value) {
            this.renderData.rf1 = value.rf1; this.renderData.rf2 = value.rf2;
            this.renderData.rf3 = value.rf3; this.renderData.rf4 = value.rf4;
            this.renderData.ri1 = value.ri1; this.renderData.ri2 = value.ri2;
            this.renderData.ri3 = value.ri3; this.renderData.ri4 = value.ri4;
        }
        public Random getModelRandom() { return this.rand; }
        public boolean isChildModel() { return this.isChild(); }

        @Override public Entity getControllingPassenger() {
            return this.getPassengers().isEmpty() ? null : this.getPassengers().get(0);
        }

        @Override public double getMountedYOffset() { return 1.3D; }

        @Override public void updatePassenger(Entity passenger) {
            if (this.isPassenger(passenger)) {
                float offset = 0.65F;
                passenger.setPosition(this.getPosX() - offset * Math.sin(Math.toRadians(this.rotationYaw)),
                    this.getPosY() + this.getMountedYOffset() + passenger.getYOffset(),
                    this.getPosZ() + offset * Math.cos(Math.toRadians(this.rotationYaw)));
            }
        }

        @Override public ActionResultType func_230254_b_(PlayerEntity player, Hand hand) {
            ItemStack stack = player.getHeldItem(hand);
            if (!this.isTamed()) {
                if (stack.getItem() == Items.BEEF && player.getDistanceSq(this) < 25.0D) {
                    if (!this.world.isRemote) {
                        if (this.rand.nextInt(5) == 1) {
                            this.setTamedBy(player);
                            this.heal(this.getMaxHealth());
                            this.playTameEffect(true);
                            this.world.setEntityState(this, (byte)7);
                        } else {
                            this.playTameEffect(false);
                            this.world.setEntityState(this, (byte)6);
                        }
                    }
                    consume(player, stack);
                    return ActionResultType.SUCCESS;
                }
                return ActionResultType.PASS;
            }
            if (!this.isOwner(player)) return ActionResultType.PASS;
            if (stack.isEmpty() && player.getDistanceSq(this) < 16.0D) {
                if (!this.world.isRemote) {
                    player.startRiding(this);
                    setActivity(1);
                    this.func_233687_w_(false);
                }
                return ActionResultType.SUCCESS;
            }
            if (stack.getItem() == Items.BEEF && player.getDistanceSq(this) < 25.0D) {
                if (!this.world.isRemote) this.heal(this.getMaxHealth());
                consume(player, stack); return ActionResultType.SUCCESS;
            }
            if (stack.getItem() == Blocks.DEAD_BUSH.asItem()) {
                if (!this.world.isRemote) {
                    this.setTamed(false); this.setOwnerId(null); this.playTameEffect(false);
                    this.world.setEntityState(this, (byte)6);
                }
                consume(player, stack); return ActionResultType.SUCCESS;
            }
            if (stack.getItem() == Blocks.ICE.asItem()) {
                if (!this.world.isRemote) { setDragonFire(0); player.sendStatusMessage(
                    new net.minecraft.util.text.StringTextComponent("Dragon fireballs extinguished."), true); }
                consume(player, stack); return ActionResultType.SUCCESS;
            }
            if (stack.getItem() == Items.FLINT_AND_STEEL) {
                if (!this.world.isRemote) { setDragonFire(1); player.sendStatusMessage(
                    new net.minecraft.util.text.StringTextComponent("Dragon fireballs lit!"), true); }
                consume(player, stack); return ActionResultType.SUCCESS;
            }
            if (stack.getItem() == Items.GUNPOWDER && getDragonFire() > 0) {
                if (!this.world.isRemote) { setDragonFire(2); player.sendStatusMessage(
                    new net.minecraft.util.text.StringTextComponent("Dragon fireballs supercharged!"), true); }
                consume(player, stack); return ActionResultType.SUCCESS;
            }
            if (stack.getItem() == Items.SNOWBALL) {
                if (!this.world.isRemote) setDragonType(1);
                consume(player, stack); return ActionResultType.SUCCESS;
            }
            if (stack.getItem() == Items.COAL) {
                if (!this.world.isRemote) setDragonType(0);
                consume(player, stack); return ActionResultType.SUCCESS;
            }
            if (stack.getItem() == Items.DIAMOND) {
                if (!this.world.isRemote) {
                    Entity child = OreSpawnLogic.spawn(this.world, "spyro", this.getPosX(), this.getPosY(), this.getPosZ());
                    if (child == null) child = OreSpawnLogic.spawn(this.world, "baby_dragon", this.getPosX(), this.getPosY(), this.getPosZ());
                    if (child instanceof TameableEntity) {
                        ((TameableEntity)child).setTamed(true);
                        ((TameableEntity)child).setOwnerId(player.getUniqueID());
                    }
                    if (child != null) this.remove();
                }
                consume(player, stack); return ActionResultType.SUCCESS;
            }
            if (stack.getItem() == Items.NAME_TAG && stack.hasDisplayName()) {
                if (!this.world.isRemote) this.setCustomName(stack.getDisplayName());
                consume(player, stack); return ActionResultType.SUCCESS;
            }
            if (!stack.isEmpty()) {
                if (!this.world.isRemote) { this.func_233687_w_(!this.isSitting()); setActivity(0); }
                return ActionResultType.SUCCESS;
            }
            return ActionResultType.PASS;
        }

        private void consume(PlayerEntity player, ItemStack stack) {
            if (!player.abilities.isCreativeMode) stack.shrink(1);
        }

        private double groundTop(double x, double z) {
            int bx = MathHelper.floor(x), bz = MathHelper.floor(z);
            int start = MathHelper.floor(this.getPosY()) + 1;
            BlockPos.Mutable p = new BlockPos.Mutable();
            for (int y = start; y >= start - 12; y--) {
                p.setPos(bx, y, bz);
                VoxelShape shape = this.world.getBlockState(p).getCollisionShape(this.world, p);
                if (!shape.isEmpty()) return y + shape.getEnd(Direction.Axis.Y);
            }
            return Double.NEGATIVE_INFINITY;
        }

        private double riddenVertical(double mx, double mz) {
            double speed = Math.sqrt(mx * mx + mz * mz);
            double ground = groundTop(this.getPosX(), this.getPosZ());
            if (speed > 0.01D) {
                double dx = mx / speed, dz = mz / speed;
                int distance = 3 + (int)(speed * 7.0D);
                for (int i = 1; i < distance * 2; i++)
                    ground = Math.max(ground, groundTop(this.getPosX() + dx * i, this.getPosZ() + dz * i));
            }
            if (ground == Double.NEGATIVE_INFINITY) return this.getMotion().y - 0.018D;
            return MathHelper.clamp(this.getMotion().y + (ground + 1.25D - this.getPosY()) * 0.07D, -2.0D, 2.0D);
        }

        @Override public void travel(Vector3d input) {
            Entity rider = getControllingPassenger();
            if (!(rider instanceof PlayerEntity)) { super.travel(input); return; }
            PlayerEntity player = (PlayerEntity)rider;
            if (!this.canPassengerSteer()) { this.setMotion(Vector3d.ZERO); return; }
            Vector3d motion = this.getMotion();
            double velocity = Math.sqrt(motion.x * motion.x + motion.z * motion.z);
            float turn = MathHelper.wrapDegrees(player.rotationYaw - this.rotationYaw);
            double correction = velocity > 0.01D ? MathHelper.clamp(Math.abs(1.85D - velocity), 0.01D, 0.9D) : 1.0D;
            this.rotationYaw = player.rotationYaw + (float)(turn * correction);
            this.renderYawOffset = this.rotationYaw;
            this.rotationYawHead = this.rotationYaw;
            double fx = -Math.sin(Math.toRadians(this.rotationYaw));
            double fz = Math.cos(Math.toRadians(this.rotationYaw));
            double forward = motion.x * fx + motion.z * fz;
            if (Math.abs(player.moveForward) > 0.001F) {
                double wantedDelta = player.moveForward > 0.0F ? 0.025D : -0.02D;
                if (this.deltaSmooth * wantedDelta < 0) this.deltaSmooth = 0;
                this.deltaSmooth += wantedDelta / 10.0F;
                if (wantedDelta > 0) this.deltaSmooth = Math.min(this.deltaSmooth, (float)wantedDelta);
                else this.deltaSmooth = Math.max(this.deltaSmooth, (float)wantedDelta);
                forward += this.deltaSmooth;
                forward = MathHelper.clamp(forward, -0.35D, 0.95D);
            }
            double mx = fx * forward, mz = fz * forward;
            double my = riddenVertical(mx, mz);
            if (player.moveVertical > 0.0F) my += 0.03D + velocity * 0.036D;
            this.setMotion(mx, MathHelper.clamp(my, -2.0D, 2.0D), mz);
            this.move(MoverType.SELF, this.getMotion());
            this.setMotion(this.getMotion().mul(0.985D, 0.94D, 0.985D));
            if (!this.world.isRemote) {
                if (this.fireballTicker > 0) this.fireballTicker--;
                if (this.fireballTicker == 0 && Math.abs(player.moveStrafing) > 0.001F) {
                    boolean primary = player.moveStrafing > 0.0F;
                    shootFromRider(player, primary);
                }
                updateMountedCombat();
            }
        }

        private void shootFromRider(PlayerEntity rider, boolean primary) {
            double x = this.getPosX() - 4.0D * Math.sin(Math.toRadians(this.rotationYaw));
            double y = this.getPosY() - 0.25D;
            double z = this.getPosZ() + 4.0D * Math.cos(Math.toRadians(this.rotationYaw));
            double dx = -Math.sin(Math.toRadians(rider.rotationYaw)) * Math.cos(Math.toRadians(rider.rotationPitch));
            double dy = -Math.sin(Math.toRadians(rider.rotationPitch));
            double dz = Math.cos(Math.toRadians(rider.rotationYaw)) * Math.cos(Math.toRadians(rider.rotationPitch));
            if (getDragonType() == 0) {
                Entity shot = primary ? new SmallFireballEntity(this.world, this, dx, dy, dz)
                    : new FireballEntity(this.world, this, dx, dy, dz);
                shot.setPosition(x, y, z); this.world.addEntity(shot);
                this.fireballTicker = primary ? 10 : 20;
                this.playSound(primary ? SoundEvents.ENTITY_ARROW_SHOOT : SoundEvents.ENTITY_CREEPER_PRIMED,
                    primary ? 0.75F : 1.0F, 1.0F / (this.rand.nextFloat() * 0.4F + 0.8F));
            } else {
                WaterIceProjectile shot = new WaterIceProjectile(this.world, this, primary);
                shot.setPosition(x, y, z);
                shot.shoot(dx, dy, dz, primary ? 1.4F : 2.8F, 5.0F);
                this.world.addEntity(shot);
                this.fireballTicker = primary ? 5 : 15;
                this.playSound(primary ? SoundEvents.ENTITY_ARROW_SHOOT : SoundEvents.ENTITY_FIREWORK_ROCKET_LAUNCH,
                    0.75F, 1.0F / (this.rand.nextFloat() * 0.4F + 0.8F));
            }
        }

        @Override public void livingTick() {
            super.livingTick();
            if (this.world.isRemote) return;
            if (this.hurtTimer > 0) this.hurtTimer--;
            if (this.getActivity() == 1 && ++this.wingSound > 20) {
                this.playSound(OreSpawnLogic.sound("mothra_wings", SoundEvents.ENTITY_PHANTOM_FLAP), 0.5F, 1.0F);
                this.wingSound = 0;
            }
            if (this.isInWater()) this.setMotion(this.getMotion().add(0.0D, 0.07D, 0.0D));
            alwaysDo();
            if (this.isBeingRidden()) { setActivity(1); this.setNoGravity(true); }
            else if (getActivity() == 1 && !this.isSitting()) {
                this.setNoGravity(true);
                flyWithoutRider();
            } else this.setNoGravity(false);
        }

        private void alwaysDo() {
            if (this.rand.nextInt(250) == 1 && this.getHealth() < this.getMaxHealth()) this.heal(2.0F);
            this.ownerFlying = 0;
            LivingEntity owner = this.getOwner();
            if (!this.isSitting() && owner != null) {
                if (owner instanceof PlayerEntity && ((PlayerEntity)owner).abilities.isFlying && !this.isBeingRidden()) {
                    this.ownerFlying = 1; setActivity(1);
                }
                if (this.getDistanceSq(owner) > 400.0D) setActivity(1);
            }
            if (this.rand.nextInt(50) == 1 && !this.targetInSight && !this.isBeingRidden() && !this.isSitting())
                setActivity(this.rand.nextInt(15) == 1 ? 1 : 0);
            if (this.rand.nextInt(25) == 0 && !this.targetInSight && !this.isBeingRidden()) seekLava();
            if (getActivity() == 0 && !this.isSitting() && this.world.getDifficulty() != Difficulty.PEACEFUL
                    && this.rand.nextInt(10) == 1 && findSomethingToAttack() != null) setActivity(1);
        }

        private void seekLava() {
            BlockPos lava = OreLoot.scanShell(this.world, this.getPosition().down(), 10, 4, 10,
                (w, p) -> w.getFluidState(p).isTagged(FluidTags.LAVA));
            if (lava != null) {
                setActivity(0);
                this.getNavigator().tryMoveToXYZ(lava.getX(), lava.getY() - 1, lava.getZ(), 1.0D);
                if (this.isInLava()) {
                    this.heal(1.0F);
                    this.playSound(SoundEvents.ENTITY_GENERIC_SPLASH, 1.0F, this.rand.nextFloat() * 0.2F + 0.9F);
                }
            }
        }

        private void flyWithoutRider() {
            if (this.flightTarget == null) this.flightTarget = this.getPosition();
            if (this.unstickTimer > 0) this.unstickTimer--;
            int x = MathHelper.floor(this.getPosX()), z = MathHelper.floor(this.getPosZ());
            if (x == this.lastX && z == this.lastZ) {
                if (++this.stuckCount > 50) {
                    this.stuckCount = 0; this.unstickTimer = 100; this.targetInSight = false;
                    setAttacking(0); chooseFlightTarget();
                }
            } else { this.stuckCount = 0; this.lastX = x; this.lastZ = z; }
            if (this.flyAway > 0) this.flyAway--;

            LivingEntity target = null;
            LivingEntity owner = this.getOwner();
            boolean ownerFar = owner != null && this.getDistanceSq(owner) > 144.0D;
            if (!ownerFar && this.unstickTimer == 0 && this.flyAway == 0
                    && this.world.getDifficulty() != Difficulty.PEACEFUL && this.rand.nextInt(9) == 1)
                target = findSomethingToAttack();
            if (target != null) {
                if (this.isTamed() && this.getHealth() / this.getMaxHealth() < 0.25F) {
                    setAttacking(0); this.targetInSight = false;
                    this.flightTarget = new BlockPos(this.getPosX() * 2.0D - target.getPosX(),
                        this.getPosY() + 1.0D, this.getPosZ() * 2.0D - target.getPosZ());
                } else {
                    setAttacking(1); this.targetInSight = true;
                    this.flightTarget = target.getPosition().up();
                    double reach = 5.0D + target.getWidth() / 2.0D;
                    if (this.getDistanceSq(target) < reach * reach) {
                        attackEntityAsMob(target); this.flyAway = 5 + this.rand.nextInt(10); chooseFlightTarget();
                    } else if (this.getDistanceSq(target) < 256.0D && !this.isInWater() && getDragonFire() >= 1)
                        shootAtTarget(target);
                }
            } else {
                this.targetInSight = false; setAttacking(0);
                if (ownerFar || this.rand.nextInt(300) == 1
                        || this.flightTarget.distanceSq(this.getPosition()) < 5.0D) chooseFlightTarget();
            }

            double speedFactor = this.ownerFlying != 0 ? (owner != null && this.getDistanceSq(owner) > 49.0D ? 3.5D : 1.75D) : 0.5D;
            double dx = this.flightTarget.getX() + 0.5D - this.getPosX();
            double dy = this.flightTarget.getY() + 0.1D - this.getPosY();
            double dz = this.flightTarget.getZ() + 0.5D - this.getPosZ();
            Vector3d m = this.getMotion();
            this.setMotion(m.x + (Math.signum(dx) - m.x) * 0.15D * speedFactor,
                m.y + (Math.signum(dy) - m.y) * 0.21D * speedFactor,
                m.z + (Math.signum(dz) - m.z) * 0.15D * speedFactor);
            float wanted = (float)(MathHelper.atan2(this.getMotion().z, this.getMotion().x) * 180.0D / Math.PI) - 90.0F;
            this.rotationYaw += MathHelper.wrapDegrees(wanted - this.rotationYaw) / 4.0F;
            this.renderYawOffset = this.rotationYaw;
            this.move(MoverType.SELF, this.getMotion());
        }

        private void chooseFlightTarget() {
            LivingEntity owner = this.getOwner();
            int cx = MathHelper.floor(owner != null && this.unstickTimer == 0 ? owner.getPosX() : this.getPosX());
            int cy = MathHelper.floor(owner != null && this.unstickTimer == 0 ? owner.getPosY() : this.getPosY());
            int cz = MathHelper.floor(owner != null && this.unstickTimer == 0 ? owner.getPosZ() : this.getPosZ());
            for (int tries = 0; tries < 50; tries++) {
                int range = owner != null && this.unstickTimer == 0 ? (this.ownerFlying == 0 ? 10 : 6) : 10;
                int base = owner != null && this.unstickTimer == 0 ? (this.ownerFlying == 0 ? 4 : 0) : 16;
                int ox = (this.rand.nextInt(range) + base) * (this.rand.nextBoolean() ? 1 : -1);
                int oz = (this.rand.nextInt(range) + base) * (this.rand.nextBoolean() ? 1 : -1);
                BlockPos p = new BlockPos(cx + ox, cy + this.rand.nextInt(9 + this.ownerFlying * 2) - 4, cz + oz);
                if (this.world.isAirBlock(p) && OreSpawnLogic.hasClearPath(this, p.getX(), p.getY(), p.getZ())) {
                    this.flightTarget = p; return;
                }
            }
        }

        private LivingEntity findSomethingToAttack() {
            if (OreSpawnLogic.playNicely != 0 || this.world.getDifficulty() == Difficulty.PEACEFUL) return null;
            LivingEntity found = OreSpawnLogic.nearestTarget(this,
                this.getBoundingBox().grow(20.0D, 20.0D, 20.0D), e -> {
                    if (!this.canEntityBeSeen(e) || e instanceof PlayerEntity || e instanceof CustomEntity) return false;
                    if (OreSpawnLogic.isNamed(e, "lurking_terror", "ender_reaper", "terrible_terror",
                            "leaf_monster", "creeping_horror", "triffid")) return false;
                    return e instanceof MonsterEntity || OreSpawnLogic.isNamed(e, "mothra", "kraken");
                });
            this.setAttackTarget(found);
            return found;
        }

        private void updateMountedCombat() {
            if (this.world.getDifficulty() == Difficulty.PEACEFUL || this.rand.nextInt(7) != 1) return;
            if (this.rand.nextInt(250) == 0) this.setAttackTarget(null);
            LivingEntity target = this.getAttackTarget();
            if (target == null || !target.isAlive()) target = findSomethingToAttack();
            if (target != null) {
                setAttacking(1);
                double reach = 7.0D + target.getWidth() / 2.0D;
                if (this.getDistanceSq(target) < reach * reach) attackEntityAsMob(target);
            } else setAttacking(0);
        }

        private void shootAtTarget(LivingEntity target) {
            double x = this.getPosX() - 2.25D * Math.sin(Math.toRadians(this.rotationYaw));
            double y = this.getPosY() + 1.25D;
            double z = this.getPosZ() + 2.25D * Math.cos(Math.toRadians(this.rotationYaw));
            double dx = target.getPosX() - x, dy = target.getPosY() + target.getHeight() / 2.0D - y;
            double dz = target.getPosZ() - z;
            if (getDragonType() == 0) {
                Entity shot = getDragonFire() == 1 ? new SmallFireballEntity(this.world, this, dx, dy, dz)
                    : new FireballEntity(this.world, this, dx, dy, dz);
                shot.setPosition(x, y, z); this.world.addEntity(shot);
            } else {
                WaterIceProjectile shot = new WaterIceProjectile(this.world, this, getDragonFire() == 1);
                shot.setPosition(x, y, z); shot.shoot(dx, dy, dz, 1.4F, 5.0F); this.world.addEntity(shot);
            }
        }

        @Override public boolean attackEntityAsMob(Entity target) {
            float damage = OreSpawnLogic.isNamed(target, "kraken") ? 70.0F : 35.0F;
            boolean hit = target.attackEntityFrom(DamageSource.causeMobDamage(this), damage);
            if (target instanceof LivingEntity) {
                double lift = target instanceof PlayerEntity ? 0.2D : 0.1D;
                float angle = (float)Math.atan2(target.getPosZ() - this.getPosZ(), target.getPosX() - this.getPosX());
                target.addVelocity(Math.cos(angle) * 1.75D, lift, Math.sin(angle) * 1.75D);
            }
            return hit;
        }

        @Override public boolean attackEntityFrom(DamageSource source, float amount) {
            if (this.hurtTimer > 0 || source == DamageSource.CACTUS || source == DamageSource.IN_FIRE
                    || source == DamageSource.ON_FIRE || source == DamageSource.LAVA || source == DamageSource.IN_WALL) return false;
            Entity immediate = source.getImmediateSource();
            Entity attacker = source.getTrueSource();
            if ((getDragonType() == 0 && immediate instanceof SmallFireballEntity)
                    || (getDragonType() != 0 && immediate instanceof WaterIceProjectile)
                    || attacker instanceof CustomEntity || OreSpawnLogic.isNamed(attacker == null ? this : attacker, "spyro")) return false;
            this.func_233687_w_(false); setActivity(1);
            boolean result = super.attackEntityFrom(source, amount);
            this.hurtTimer = 20;
            if (attacker instanceof PlayerEntity && this.isTamed()) return false;
            if (attacker instanceof LivingEntity) {
                this.setAttackTarget((LivingEntity)attacker);
                this.getNavigator().tryMoveToEntityLiving((LivingEntity)attacker, 1.2D);
            }
            return result;
        }

        @Override protected void dropSpecialItems(DamageSource source, int looting, boolean recentlyHit) {
            super.dropSpecialItems(source, looting, recentlyHit);
            for (int i = 0, n = 1 + this.rand.nextInt(6); i < n; i++)
                OreSpawnLogic.drop(this.world, this, Items.BEEF, 1, 5);
        }

        @Override public AgeableEntity func_241840_a(ServerWorld world, AgeableEntity mate) { return null; }

        @Override public void writeAdditional(CompoundNBT nbt) {
            super.writeAdditional(nbt);
            nbt.putInt("DragonAttacking", getAttacking());
            nbt.putInt("DragonActivity", getActivity());
            nbt.putInt("DragonFire", getDragonFire());
            nbt.putInt("DragonType", getDragonType());
        }

        @Override public void readAdditional(CompoundNBT nbt) {
            super.readAdditional(nbt);
            setAttacking(nbt.getInt("DragonAttacking"));
            setActivity(nbt.getInt("DragonActivity"));
            setDragonFire(nbt.getInt("DragonFire"));
            setDragonType(nbt.getInt("DragonType"));
        }

        @Override protected SoundEvent getAmbientSound() {
            return !this.isSitting() && getAttacking() == 1 && !this.isBeingRidden()
                ? OreSpawnLogic.sound("roar", SoundEvents.ENTITY_ENDER_DRAGON_GROWL) : null;
        }
        @Override protected SoundEvent getHurtSound(DamageSource source) {
            return OreSpawnLogic.sound("alo_hurt", SoundEvents.ENTITY_ENDER_DRAGON_HURT);
        }
        @Override protected SoundEvent getDeathSound() {
            return OreSpawnLogic.sound("alo_death", SoundEvents.ENTITY_ENDER_DRAGON_DEATH);
        }
        @Override protected float getSoundVolume() { return 0.6F; }
        @Override protected float getSoundPitch() { return 0.75F; }
        @Override public boolean onLivingFall(float distance, float multiplier) { return false; }
        @Override public boolean canBePushed() { return false; }
        @Override public boolean isOnLadder() { return true; }
    }

    /** Server-side impact logic for the old WaterBall and special IceBall. */
    private static class WaterIceProjectile extends SnowballEntity {
        private final boolean waterBall;

        WaterIceProjectile(World world, LivingEntity shooter, boolean waterBall) {
            super(world, shooter);
            this.waterBall = waterBall;
        }

        @Override protected void onImpact(RayTraceResult result) {
            if (!this.world.isRemote && result instanceof EntityRayTraceResult) {
                Entity hit = ((EntityRayTraceResult)result).getEntity();
                Entity shooter = this.func_234616_v_();
                if (hit != shooter && (shooter == null
                        || hit.getLowestRidingEntity() != shooter.getLowestRidingEntity())) {
                    if (this.waterBall) {
                        float damage = hit instanceof CreeperEntity ? 5.0F : 2.0F;
                        hit.attackEntityFrom(DamageSource.causeThrownDamage(this, shooter), damage);
                        hit.extinguish();
                        if (this.rand.nextInt(10) == 1)
                            OreSpawnLogic.drop(this.world, this, OreSpawnLogic.item("water_ball"), 1, 0);
                    } else hit.attackEntityFrom(DamageSource.causeThrownDamage(this, shooter), 16.0F);
                }
            }
            if (!this.world.isRemote && !this.waterBall) this.world.createExplosion(this,
                this.getPosX(), this.getPosY(), this.getPosZ(), 3.0F,
                this.world.getGameRules().getBoolean(net.minecraft.world.GameRules.MOB_GRIEFING)
                    ? Explosion.Mode.DESTROY : Explosion.Mode.NONE);
            super.onImpact(result);
        }
    }
}
