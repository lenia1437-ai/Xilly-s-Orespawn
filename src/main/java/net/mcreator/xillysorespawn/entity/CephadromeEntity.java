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
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
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
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.Direction;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.server.ServerWorld;

import net.mcreator.xillysorespawn.XillysOrespawnModElements;
import net.mcreator.xillysorespawn.entity.renderer.CephadromeRenderer;
import net.mcreator.xillysorespawn.entity.renderer.RenderInfo;

/** Behavioural port of OreSpawn 1.7.10 Cephadrome. */
@XillysOrespawnModElements.ModElement.Tag
public class CephadromeEntity extends XillysOrespawnModElements.ModElement {
    public static EntityType entity = EntityType.Builder.<CustomEntity>create(CustomEntity::new, EntityClassification.CREATURE)
        .setShouldReceiveVelocityUpdates(true).setTrackingRange(128).setUpdateInterval(1)
        .setCustomClientFactory(CustomEntity::new).size(2.5F, 2.25F)
        .build("cephadrome").setRegistryName("cephadrome");

    public CephadromeEntity(XillysOrespawnModElements instance) {
        super(instance, 1);
        FMLJavaModLoadingContext.get().getModEventBus().register(new CephadromeRenderer.ModelRegisterHandler());
        FMLJavaModLoadingContext.get().getModEventBus().register(new EntityAttributesRegisterHandler());
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override public void initElements() {
        elements.entities.add(() -> entity);
        elements.items.add(() -> new SpawnEggItem(entity, -1, -1,
            new Item.Properties().group(ItemGroup.MISC)).setRegistryName("cephadrome_spawn_egg"));
    }

    @SubscribeEvent public void addFeatureToBiomes(BiomeLoadingEvent event) {
        if (!OreSpawnLogic.allowNaturalSpawn(event, "cephadrome")) return;
        event.getSpawns().getSpawner(EntityClassification.CREATURE)
            .add(new MobSpawnInfo.Spawners(entity, 5, 1, 2));
    }

    @Override public void init(FMLCommonSetupEvent event) {
        EntitySpawnPlacementRegistry.register(entity, EntitySpawnPlacementRegistry.PlacementType.NO_RESTRICTIONS,
            Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, CephadromeEntity::canSpawn);
    }

    public static boolean canSpawn(EntityType<? extends net.minecraft.entity.CreatureEntity> type, IServerWorld world,
            SpawnReason reason, BlockPos pos, Random random) {
        if (reason == SpawnReason.SPAWNER || reason == SpawnReason.SPAWN_EGG
                || reason == SpawnReason.COMMAND) return true;
        if (!world.getWorld().isDaytime() || pos.getY() < 50) return false;
        for (int x = -2; x < 2; x++) for (int z = -2; z < 2; z++) for (int y = 1; y < 5; y++)
            if (!world.getWorld().isAirBlock(pos.add(x, y, z))) return false;
        return world.getEntitiesWithinAABB(CustomEntity.class,
            new AxisAlignedBB(pos).grow(16.0D, 6.0D, 16.0D), e -> true).isEmpty();
    }

    public static class EntityAttributesRegisterHandler {
        @SubscribeEvent public void onEntityAttributeCreation(EntityAttributeCreationEvent event) {
            event.put(entity, CustomEntity.createAttributes().create());
        }
    }

    public static class CustomEntity extends net.minecraft.entity.CreatureEntity {
        private static final DataParameter<Byte> ATTACKING = EntityDataManager.createKey(CustomEntity.class, DataSerializers.BYTE);
        private static final DataParameter<Byte> ACTIVITY = EntityDataManager.createKey(CustomEntity.class, DataSerializers.BYTE);
        private final RenderInfo renderData = new RenderInfo();
        private int hurtTimer;
        private int wasFed;
        private int shouldAttack;
        private int wingSound;
        private int hitByPlayer;
        private int badMood;

        public CustomEntity(FMLPlayMessages.SpawnEntity packet, World world) { this(entity, world); }
        public CustomEntity(EntityType<? extends CustomEntity> type, World world) {
            super(type, world);
            this.experienceValue = 200;
            this.setNoAI(false);
        }

        public static AttributeModifierMap.MutableAttribute createAttributes() {
            return net.minecraft.entity.CreatureEntity.func_233666_p_()
                .createMutableAttribute(Attributes.MAX_HEALTH, 300.0D)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.32D)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 70.0D)
                .createMutableAttribute(Attributes.ARMOR, 16.0D)
                .createMutableAttribute(Attributes.FOLLOW_RANGE, 32.0D);
        }

        @Override protected void registerData() {
            super.registerData();
            this.dataManager.register(ATTACKING, (byte)0);
            this.dataManager.register(ACTIVITY, (byte)0);
        }

        @Override protected void registerGoals() {
            super.registerGoals();
            this.goalSelector.addGoal(0, new SwimGoal(this));
            this.goalSelector.addGoal(1, new OreSpawnWanderGoal(this, 16, 30, 1.0D, false));
            this.goalSelector.addGoal(2, new LookAtGoal(this, PlayerEntity.class, 9.0F));
            this.goalSelector.addGoal(3, new LookRandomlyGoal(this));
            this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        }

        @Override public IPacket<?> createSpawnPacket() { return NetworkHooks.getEntitySpawningPacket(this); }

        @Override @Nullable
        public ILivingEntityData onInitialSpawn(IServerWorld world, DifficultyInstance difficulty, SpawnReason reason,
                @Nullable ILivingEntityData spawnData, @Nullable CompoundNBT dataTag) {
            ILivingEntityData data = super.onInitialSpawn(world, difficulty, reason, spawnData, dataTag);
            if (reason == SpawnReason.SPAWNER) this.badMood = 1;
            return data;
        }

        public int getCephadromeHealth() { return MathHelper.ceil(this.getHealth()); }
        public int getAttacking() { return this.dataManager.get(ATTACKING); }
        public void setAttacking(int value) { if (!this.world.isRemote) this.dataManager.set(ATTACKING, (byte)value); }
        public int getActivity() { return this.dataManager.get(ACTIVITY); }
        public void setActivity(int value) { if (!this.world.isRemote) this.dataManager.set(ACTIVITY, (byte)value); }
        public RenderInfo getRenderInfo() { return this.renderData; }
        public void setRenderInfo(RenderInfo value) {
            this.renderData.rf1 = value.rf1; this.renderData.rf2 = value.rf2;
            this.renderData.rf3 = value.rf3; this.renderData.rf4 = value.rf4;
            this.renderData.ri1 = value.ri1; this.renderData.ri2 = value.ri2;
            this.renderData.ri3 = value.ri3; this.renderData.ri4 = value.ri4;
        }
        public Random getModelRandom() { return this.rand; }
        public boolean isChildModel() { return false; }

        @Override public Entity getControllingPassenger() {
            return this.getPassengers().isEmpty() ? null : this.getPassengers().get(0);
        }

        @Override public double getMountedYOffset() { return 2.5D; }

        @Override public void updatePassenger(Entity passenger) {
            if (this.isPassenger(passenger)) {
                float offset = 0.75F;
                passenger.setPosition(this.getPosX() - offset * Math.sin(Math.toRadians(this.rotationYaw)),
                    this.getPosY() + this.getMountedYOffset() + passenger.getYOffset(),
                    this.getPosZ() + offset * Math.cos(Math.toRadians(this.rotationYaw)));
            }
        }

        @Override public ActionResultType func_230254_b_(PlayerEntity player, Hand hand) {
            ItemStack stack = player.getHeldItem(hand);
            if ((stack.getItem() == Items.BEEF || stack.getItem() == Items.CHICKEN
                    || stack.getItem() == Items.PORKCHOP) && player.getDistanceSq(this) < 25.0D) {
                if (!this.world.isRemote) {
                    this.heal(this.getMaxHealth() - this.getHealth());
                    this.wasFed = 1;
                    this.shouldAttack = 0;
                    showHearts();
                }
                if (!player.abilities.isCreativeMode) stack.shrink(1);
                return ActionResultType.SUCCESS;
            }
            if (this.isBeingRidden() && this.getControllingPassenger() instanceof PlayerEntity
                    && this.getControllingPassenger() != player) return ActionResultType.SUCCESS;
            if (stack.isEmpty() && player.getDistanceSq(this) < 25.0D && !this.world.isRemote) {
                if (this.wasFed == 0) {
                    this.getNavigator().tryMoveToEntityLiving(player, 1.2D);
                    this.shouldAttack = 1;
                } else {
                    player.startRiding(this);
                    this.wasFed = 0;
                }
            }
            return ActionResultType.SUCCESS;
        }

        private void showHearts() {
            if (!(this.world instanceof ServerWorld)) return;
            ServerWorld server = (ServerWorld)this.world;
            for (int i = 0; i < 20; i++) server.spawnParticle(ParticleTypes.HEART,
                this.getPosX() + (this.rand.nextFloat() - this.rand.nextFloat()) * 2.5F,
                this.getPosY() + 0.5D + this.rand.nextFloat() * 1.5D,
                this.getPosZ() + (this.rand.nextFloat() - this.rand.nextFloat()) * 2.5F,
                1, this.rand.nextGaussian() * 0.08D, this.rand.nextGaussian() * 0.08D,
                this.rand.nextGaussian() * 0.08D, 0.0D);
        }

        private double groundTop(double x, double z) {
            int bx = MathHelper.floor(x), bz = MathHelper.floor(z);
            int start = MathHelper.floor(this.getPosY()) + 1;
            BlockPos.Mutable p = new BlockPos.Mutable();
            for (int y = start; y >= start - 10; y--) {
                p.setPos(bx, y, bz);
                BlockState state = this.world.getBlockState(p);
                VoxelShape shape = state.getCollisionShape(this.world, p);
                if (!shape.isEmpty()) return y + shape.getEnd(Direction.Axis.Y);
            }
            return Double.NEGATIVE_INFINITY;
        }

        private double flightVertical(double mx, double mz, double height) {
            double speed = Math.sqrt(mx * mx + mz * mz);
            double ground = groundTop(this.getPosX(), this.getPosZ());
            if (speed > 0.01D) {
                double dx = mx / speed, dz = mz / speed;
                int distance = 2 + (int)(speed * 6.0D);
                for (int i = 1; i < distance * 2; i++)
                    ground = Math.max(ground, groundTop(this.getPosX() + dx * i, this.getPosZ() + dz * i));
            }
            if (ground == Double.NEGATIVE_INFINITY) return this.getMotion().y - 0.018D;
            double desired = ground + height - this.getPosY();
            return MathHelper.clamp(this.getMotion().y + desired * 0.09D, -2.0D, 2.0D);
        }

        @Override public void travel(Vector3d input) {
            Entity rider = getControllingPassenger();
            if (!(rider instanceof PlayerEntity)) { super.travel(input); return; }
            PlayerEntity player = (PlayerEntity)rider;
            if (!this.canPassengerSteer()) { this.setMotion(Vector3d.ZERO); return; }
            Vector3d motion = this.getMotion();
            double speed = Math.sqrt(motion.x * motion.x + motion.z * motion.z);
            float turn = MathHelper.wrapDegrees(player.rotationYaw - this.rotationYaw);
            double correction = speed > 0.1D ? MathHelper.clamp(Math.abs(1.5D - speed), 0.01D, 0.9D) : 1.0D;
            this.rotationYaw = player.rotationYaw + (float)(turn * correction);
            this.renderYawOffset = this.rotationYaw;
            this.rotationYawHead = this.rotationYaw;
            double forward = motion.x * -Math.sin(Math.toRadians(this.rotationYaw))
                + motion.z * Math.cos(Math.toRadians(this.rotationYaw));
            if (Math.abs(player.moveForward) > 0.001F) {
                forward += player.moveForward > 0 ? 0.08D : -0.03D;
                forward = MathHelper.clamp(forward, -0.35D, 1.15D);
            }
            double mx = -Math.sin(Math.toRadians(this.rotationYaw)) * forward;
            double mz = Math.cos(Math.toRadians(this.rotationYaw)) * forward;
            double my = flightVertical(mx, mz, 1.55D);
            if (player.moveVertical > 0.0F) my += 0.04D + speed * 0.05D;
            this.setMotion(mx, MathHelper.clamp(my, -2.0D, 2.0D), mz);
            this.move(MoverType.SELF, this.getMotion());
            this.setMotion(this.getMotion().mul(0.985D, 0.94D, 0.985D));
        }

        @Override public void livingTick() {
            super.livingTick();
            if (this.world.isRemote) return;
            setActivity(this.isBeingRidden() ? 1 : 0);
            if (this.hurtTimer > 0) this.hurtTimer--;
            if (this.rand.nextInt(100) == 1 && this.getHealth() < this.getMaxHealth()) this.heal(2.0F);
            if (getActivity() == 1 && ++this.wingSound > 22) {
                this.playSound(OreSpawnLogic.sound("mothra_wings", SoundEvents.ENTITY_PHANTOM_FLAP), 0.5F, 1.0F);
                this.wingSound = 0;
            }
            if (OreSpawnLogic.playNicely == 0) this.wasFed = 1;
            if (this.world.getDifficulty() != Difficulty.PEACEFUL && this.rand.nextInt(7) == 1) updateCombat();
        }

        private void updateCombat() {
            LivingEntity target = this.getAttackTarget();
            if (target != null && !target.isAlive()) { this.setAttackTarget(null); target = null; }
            if (target == null) target = findSomethingToAttack();
            if (target == null) { setAttacking(0); return; }
            if (getActivity() == 0) this.getNavigator().tryMoveToEntityLiving(target, 1.7D);
            this.getLookController().setLookPositionWithEntity(target, 10.0F, 10.0F);
            setAttacking(1);
            double max = (getActivity() == 0 ? 6.0D : 10.0D) + target.getWidth() / 2.0D;
            double horizontal = Math.pow(this.getPosX() - target.getPosX(), 2)
                + Math.pow(this.getPosZ() - target.getPosZ(), 2);
            if (this.getDistanceSq(target) < max * max
                    || (OreSpawnLogic.isNamed(target, "kraken") && horizontal < max * max)) attackEntityAsMob(target);
        }

        private LivingEntity findSomethingToAttack() {
            if (OreSpawnLogic.playNicely != 0 || this.world.getDifficulty() == Difficulty.PEACEFUL) return null;
            LivingEntity found = OreSpawnLogic.nearestTarget(this,
                this.getBoundingBox().grow(16.0D, 20.0D, 16.0D), e -> {
                    if (e instanceof CustomEntity || !this.canEntityBeSeen(e)) return false;
                    if (e instanceof PlayerEntity) return !((PlayerEntity)e).abilities.isCreativeMode
                        && (this.hitByPlayer != 0 || this.badMood != 0 || this.shouldAttack > 0);
                    if (e instanceof EnderDragonEntity || e instanceof MonsterEntity
                            || OreSpawnLogic.isNamed(e, "mothra")) return true;
                    if (OreSpawnLogic.isNamed(e, "leon", "gamma_metroid", "water_dragon"))
                        return !(e instanceof TameableEntity) || !((TameableEntity)e).isTamed();
                    return false;
                });
            if (found instanceof PlayerEntity && this.shouldAttack > 0) this.shouldAttack = 0;
            this.setAttackTarget(found);
            return found;
        }

        @Override public boolean attackEntityAsMob(Entity target) {
            float damage = OreSpawnLogic.isNamed(target, "kraken") ? 105.0F : 70.0F;
            boolean hit = target.attackEntityFrom(DamageSource.causeMobDamage(this), damage);
            if (hit && target instanceof LivingEntity) {
                double lift = target instanceof PlayerEntity ? 0.7D : 0.35D;
                float angle = (float)Math.atan2(target.getPosZ() - this.getPosZ(), target.getPosX() - this.getPosX());
                target.addVelocity(Math.cos(angle) * 2.5D, lift, Math.sin(angle) * 2.5D);
            }
            return hit;
        }

        @Override public boolean attackEntityFrom(DamageSource source, float amount) {
            if (source == DamageSource.CACTUS || this.hurtTimer > 0) return false;
            boolean result = super.attackEntityFrom(source, amount);
            this.hurtTimer = 25;
            Entity attacker = source.getTrueSource();
            if (attacker instanceof LivingEntity) {
                this.setAttackTarget((LivingEntity)attacker);
                this.getNavigator().tryMoveToEntityLiving((LivingEntity)attacker, 1.2D);
            }
            if (attacker instanceof PlayerEntity && this.getHealth() < this.getMaxHealth() * 0.9F) this.hitByPlayer = 1;
            return result || attacker instanceof LivingEntity;
        }

        @Override protected void dropSpecialItems(DamageSource source, int looting, boolean recentlyHit) {
            super.dropSpecialItems(source, looting, recentlyHit);
            for (int i = 0, n = 4 + this.rand.nextInt(6); i < n; i++) dropNamed("uranium_nugget");
            for (int i = 0, n = 4 + this.rand.nextInt(6); i < n; i++) dropNamed("titanium_nugget");
            for (int i = 0, n = 1 + this.rand.nextInt(5); i < n; i++) dropTreasure(this.rand.nextInt(20));
        }

        private ItemStack dropNamed(String... names) {
            return OreSpawnLogic.drop(this.world, this, OreSpawnLogic.item(names), 1, 5);
        }

        private void dropTreasure(int roll) {
            ItemStack stack;
            switch (roll) {
                case 0: dropNamed("ruby_sword"); break;
                case 1: OreSpawnLogic.drop(this.world, this, Items.DIAMOND, 1, 5); break;
                case 2: dropNamed("thunder_staff"); break;
                case 3: stack = dropNamed("ruby_sword"); if (!stack.isEmpty()) OreLoot.sword(this.rand, stack); break;
                case 4: stack = dropNamed("ruby_shovel"); if (!stack.isEmpty()) OreLoot.shovelOrHoeOrAxe(this.rand, stack); break;
                case 5: stack = dropNamed("ruby_pickaxe"); if (!stack.isEmpty()) OreLoot.pickaxe(this.rand, stack); break;
                case 6: stack = dropNamed("ruby_axe"); if (!stack.isEmpty()) OreLoot.shovelOrHoeOrAxe(this.rand, stack); break;
                case 7: stack = dropNamed("ruby_hoe"); if (!stack.isEmpty()) OreLoot.shovelOrHoeOrAxe(this.rand, stack); break;
                case 8: stack = dropNamed("ruby_helmet"); if (!stack.isEmpty()) OreLoot.helmet(this.rand, stack); break;
                case 9: stack = dropNamed("ruby_chestplate", "ruby_body"); if (!stack.isEmpty()) OreLoot.chestOrLegs(this.rand, stack); break;
                case 10: stack = dropNamed("ruby_leggings", "ruby_legs"); if (!stack.isEmpty()) OreLoot.chestOrLegs(this.rand, stack); break;
                case 11: stack = dropNamed("ruby_boots"); if (!stack.isEmpty()) OreLoot.boots(this.rand, stack); break;
                default: if (roll >= 12 && roll <= 17) dropNamed("ruby"); break;
            }
        }

        @Override public void writeAdditional(CompoundNBT nbt) {
            super.writeAdditional(nbt);
            nbt.putInt("CephaWasFed", this.wasFed);
            nbt.putInt("CephaAttacking", getAttacking());
            nbt.putInt("CephaActivity", getActivity());
            nbt.putInt("CephaHitByPlayer", this.hitByPlayer);
            nbt.putInt("CephaBadMood", this.badMood);
        }

        @Override public void readAdditional(CompoundNBT nbt) {
            super.readAdditional(nbt);
            this.wasFed = nbt.getInt("CephaWasFed");
            this.hitByPlayer = nbt.getInt("CephaHitByPlayer");
            this.badMood = nbt.getInt("CephaBadMood");
            setAttacking(nbt.getInt("CephaAttacking"));
            setActivity(nbt.getInt("CephaActivity"));
        }

        @Override protected SoundEvent getAmbientSound() {
            return getActivity() != 1 && this.rand.nextInt(6) == 1
                ? OreSpawnLogic.sound("mothra_wings", SoundEvents.ENTITY_PHANTOM_FLAP) : null;
        }
        @Override protected SoundEvent getHurtSound(DamageSource source) {
            return OreSpawnLogic.sound("alo_hurt", SoundEvents.ENTITY_ENDER_DRAGON_HURT);
        }
        @Override protected SoundEvent getDeathSound() {
            return OreSpawnLogic.sound("alo_death", SoundEvents.ENTITY_ENDER_DRAGON_DEATH);
        }
        @Override protected float getSoundVolume() { return 1.5F; }
        @Override public boolean onLivingFall(float distance, float multiplier) { return false; }
        @Override public boolean canBePushed() { return false; }
        @Override public boolean isOnLadder() { return true; }
    }
}
