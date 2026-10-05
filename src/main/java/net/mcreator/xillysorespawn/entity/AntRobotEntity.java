
package net.mcreator.xillysorespawn.entity;

import java.util.List;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.common.MinecraftForge;

import net.minecraft.world.World;
import net.minecraft.world.Difficulty;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Hand;
import net.minecraft.util.Direction;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ActionResultType;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.IPacket;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.item.Item;
import net.minecraft.block.Blocks;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.item.ArmorStandEntity;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.Entity;

import net.mcreator.xillysorespawn.entity.renderer.AntRobotRenderer;
import net.mcreator.xillysorespawn.XillysOrespawnModElements;

@XillysOrespawnModElements.ModElement.Tag
public class AntRobotEntity extends XillysOrespawnModElements.ModElement {
	// ВАЖНО: в старом коде эти значения брались из OreSpawnMain.AntRobot_stats (health / attack / defense).
	// Самого OreSpawnMain в присланном коде не было, поэтому подставь сюда свои числа.
	private static final double ROBOT_HEALTH = 300.0D;
	private static final double ROBOT_ATTACK = 20.0D;
	private static final double ROBOT_ARMOR = 8.0D;

	// modid для звуков (robotspider / robotspidermount). Если звуков в проекте нет - просто без звука.
	private static final String MODID = "xillys_orespawn";

	public static EntityType entity = (EntityType.Builder.<CustomEntity>create(CustomEntity::new, EntityClassification.MONSTER)
			.setShouldReceiveVelocityUpdates(true).setTrackingRange(128).setUpdateInterval(3).setCustomClientFactory(CustomEntity::new)
			.immuneToFire().size(2.75f, 1.25f)).build("antrobot").setRegistryName("antrobot");

	public AntRobotEntity(XillysOrespawnModElements instance) {
		super(instance, 3);
		FMLJavaModLoadingContext.get().getModEventBus().register(new AntRobotRenderer.ModelRegisterHandler());
		FMLJavaModLoadingContext.get().getModEventBus().register(new EntityAttributesRegisterHandler());
		MinecraftForge.EVENT_BUS.register(this);
	}

	@Override
	public void initElements() {
		elements.entities.add(() -> entity);
		elements.items.add(() -> new SpawnEggItem(entity, -1, -1, new Item.Properties().group(ItemGroup.MISC)).setRegistryName("antrobot_spawn_egg"));
	}

	private static class EntityAttributesRegisterHandler {
		@SubscribeEvent
		public void onEntityAttributeCreation(EntityAttributeCreationEvent event) {
			AttributeModifierMap.MutableAttribute ammma = MobEntity.func_233666_p_();
			ammma = ammma.createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.3);
			ammma = ammma.createMutableAttribute(Attributes.MAX_HEALTH, ROBOT_HEALTH);
			ammma = ammma.createMutableAttribute(Attributes.ARMOR, ROBOT_ARMOR);
			ammma = ammma.createMutableAttribute(Attributes.ATTACK_DAMAGE, ROBOT_ATTACK);
			ammma = ammma.createMutableAttribute(Attributes.FOLLOW_RANGE, 24);
			event.put(entity, ammma.create());
		}
	}

	// Данные анимации ног (в 1.7.10 это был отдельный класс RenderSpiderRobotInfo), считаются на клиенте
	public static class RenderSpiderRobotInfo {
		public float[] ycurrentangle = new float[6];
		public float[] ywantedangle = new float[6];
		public float[] ydisplayangle = new float[6];
		public float[] yvelocity = new float[6];
		public float[] ymid = new float[6];
		public float[] yoff = new float[6];
		public float[] yrange = new float[6];
		public float[] udcurrentangle = new float[6];
		public float[] udwantedangle = new float[6];
		public float[] uddisplayangle = new float[6];
		public float[] udvelocity = new float[6];
		public double[] p1xangle = new double[6];
		public double[] p2xangle = new double[6];
		public double[] p3xangle = new double[6];
		public float[] pxvelocity = new float[6];
		public float[] foot_xpos = new float[6];
		public float[] foot_ypos = new float[6];
		public float[] foot_zpos = new float[6];
		public float[] realposx = new float[6];
		public float[] realposy = new float[6];
		public float[] realposz = new float[6];
		public float[] legoff = new float[6];
		public int[] footup = new int[6];
		public float[] uppoint = new float[6];
		public int[] footingticker = new int[6];
		public int[] pairedwith = new int[6];
		public int gpcounter = 0;
	}

	public static class CustomEntity extends MobEntity {
		private static final DataParameter<Boolean> OWNED = EntityDataManager.createKey(CustomEntity.class, DataSerializers.BOOLEAN);

		// Аналог OreSpawnMain.PlayNicely: true = не атакует
		public static boolean playNicely = false;

		private int playing = 0;
		private int rideTicker = 0;
		private RenderSpiderRobotInfo renderdata = new RenderSpiderRobotInfo();
		private boolean legsInitialised = false;
		private double lastPosX, lastPosY, lastPosZ;

		public CustomEntity(FMLPlayMessages.SpawnEntity packet, World world) {
			this(entity, world);
		}

		public CustomEntity(EntityType<CustomEntity> type, World world) {
			super(type, world);
			experienceValue = (int) (ROBOT_HEALTH / 2.0D);
			setNoAI(false);
			setNoGravity(true);
			initLegData();
		}

		@Override
		public IPacket<?> createSpawnPacket() {
			return NetworkHooks.getEntitySpawningPacket(this);
		}

		@Override
		protected void registerData() {
			super.registerData();
			this.dataManager.register(OWNED, false);
		}

		@Override
		protected void registerGoals() {
			super.registerGoals();
			this.goalSelector.addGoal(1, new LookAtGoal(this, PlayerEntity.class, 12.0F));
			this.goalSelector.addGoal(2, new LookRandomlyGoal(this));
		}

		// ---- "приручённый" робот: на нём можно ездить, сам не нападает ----
		// Как сделать своего: /data merge entity @e[type=xillys_orespawn:antrobot,limit=1,sort=nearest] {AntRobotOwned:1}
		public void setOwned() {
			this.dataManager.set(OWNED, true);
		}

		public boolean isOwned() {
			return this.dataManager.get(OWNED);
		}

		@Override
		public void writeAdditional(CompoundNBT nbt) {
			super.writeAdditional(nbt);
			nbt.putInt("AntRobotOwned", isOwned() ? 1 : 0);
		}

		@Override
		public void readAdditional(CompoundNBT nbt) {
			super.readAdditional(nbt);
			this.dataManager.set(OWNED, nbt.getInt("AntRobotOwned") != 0);
		}

		public RenderSpiderRobotInfo getRenderSpiderRobotInfo() {
			return this.renderdata;
		}

		// ---- физика "тела": не отталкивается, не получает урон от падения, никогда не пропадает ----
		@Override
		public boolean canDespawn(double distanceToClosestPlayer) {
			return false;
		}

		@Override
		public boolean canBePushed() {
			return false;
		}

		@Override
		public boolean isPushedByWater() {
			return false;
		}

		@Override
		public boolean onLivingFall(float distance, float damageMultiplier) {
			return false;
		}

		@Override
		public boolean attackEntityFrom(DamageSource source, float amount) {
			if (source == DamageSource.IN_WALL || source == DamageSource.CACTUS || source == DamageSource.IN_FIRE || source == DamageSource.ON_FIRE
					|| source == DamageSource.MAGIC || source == DamageSource.STARVE)
				return false;
			Entity e = source.getTrueSource();
			if (e instanceof MobEntity) {
				this.setAttackTarget((LivingEntity) e);
				this.faceEntity(e, 20.0F, 20.0F);
			}
			return super.attackEntityFrom(source, amount);
		}

		// ---- езда верхом ----
		@Override
		public Entity getControllingPassenger() {
			return this.getPassengers().isEmpty() ? null : this.getPassengers().get(0);
		}

		@Override
		public double getMountedYOffset() {
			return 0.55D + Math.cos(this.rideTicker * 0.19F) * 0.02D;
		}

		@Override
		public void updatePassenger(Entity passenger) {
			if (this.isPassenger(passenger)) {
				float f = -1.25F + (float) (Math.cos(this.rideTicker * 0.33F) * 0.05D);
				passenger.setPosition(this.getPosX() - f * Math.sin(Math.toRadians(this.rotationYaw)),
						this.getPosY() + this.getMountedYOffset() + passenger.getYOffset(),
						this.getPosZ() + f * Math.cos(Math.toRadians(this.rotationYaw)));
			}
		}

		private void playWorldSound(String name, float volume) {
			SoundEvent s = ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(MODID, name));
			if (s != null)
				this.world.playSound(null, this.getPosX(), this.getPosY(), this.getPosZ(), s, SoundCategory.NEUTRAL, volume, 1.0F);
		}

		@Override
		public ActionResultType func_230254_b_(PlayerEntity player, Hand hand) {
			ItemStack stack = player.getHeldItem(hand);
			if (!isOwned())
				return ActionResultType.CONSUME;
			// железный слиток - ремонт
			if (stack.getItem() == Items.IRON_INGOT && player.getDistanceSq(this) < 25.0D) {
				if (!this.world.isRemote) {
					float f = this.getMaxHealth() - this.getHealth();
					if (f > 100.0F)
						f = 100.0F;
					if (f > 0.0F)
						this.heal(f);
				}
				if (!player.abilities.isCreativeMode)
					stack.shrink(1);
				return ActionResultType.SUCCESS;
			}
			// занято другим игроком
			if (this.isBeingRidden() && this.getControllingPassenger() instanceof PlayerEntity && this.getControllingPassenger() != player)
				return ActionResultType.SUCCESS;
			if (!this.world.isRemote && !this.isBeingRidden() && player.getDistanceSq(this) < 16.0D) {
				player.startRiding(this);
				playWorldSound("robotspidermount", 0.45F);
			}
			return ActionResultType.SUCCESS;
		}

		// ---- движение: робот парит над землёй ----
		// Верхняя граница ближайшего твёрдого блока в колонке (x, z) ниже робота, либо -бесконечность
		private double groundTop(double x, double z) {
			int bx = MathHelper.floor(x);
			int bz = MathHelper.floor(z);
			int startY = MathHelper.floor(this.getPosY()) + 1;
			BlockPos.Mutable p = new BlockPos.Mutable();
			for (int by = startY; by >= startY - 10; by--) {
				p.setPos(bx, by, bz);
				BlockState state = this.world.getBlockState(p);
				VoxelShape shape = state.getCollisionShape(this.world, p);
				if (!shape.isEmpty())
					return by + shape.getEnd(Direction.Axis.Y);
			}
			return Double.NEGATIVE_INFINITY;
		}

		// Вертикальная скорость, чтобы держаться на высоте hover над землёй (с заглядыванием вперёд - перелезает через препятствия)
		private double hoverMotion(double mx, double mz, double hover) {
			double speed = Math.sqrt(mx * mx + mz * mz);
			double x = this.getPosX();
			double z = this.getPosZ();
			double ground = groundTop(x, z);
			ground = Math.max(ground, groundTop(x + 1.2D, z));
			ground = Math.max(ground, groundTop(x - 1.2D, z));
			ground = Math.max(ground, groundTop(x, z + 1.2D));
			ground = Math.max(ground, groundTop(x, z - 1.2D));
			if (speed > 0.01D) {
				double dx = mx / speed;
				double dz = mz / speed;
				double look = 2.0D + speed * 8.0D;
				ground = Math.max(ground, groundTop(x + dx * look * 0.5D, z + dz * look * 0.5D));
				ground = Math.max(ground, groundTop(x + dx * look, z + dz * look));
			}
			if (ground == Double.NEGATIVE_INFINITY)
				return -0.05D;
			double dy = (ground + hover) - this.getPosY();
			return MathHelper.clamp(dy * 0.25D, -0.12D, 0.35D);
		}

		@Override
		public void travel(Vector3d travelVector) {
			Entity rider = this.getControllingPassenger();
			if (rider instanceof PlayerEntity) {
				// движением управляет клиент игрока-наездника
				if (this.canPassengerSteer())
					steer((PlayerEntity) rider);
				else
					this.setMotion(Vector3d.ZERO);
			} else if (!this.world.isRemote) {
				Vector3d m = this.getMotion();
				double vy = hoverMotion(m.x, m.z, 1.75D);
				this.setMotion(m.x, vy, m.z);
				this.move(MoverType.SELF, this.getMotion());
				Vector3d after = this.getMotion();
				this.setMotion(after.x * 0.8D, after.y, after.z * 0.8D);
			}
		}

		// Управление игроком: поворот за взглядом, W/S - вперёд/назад (макс. скорость 0.3 / 0.25), высота парения 2.25
		private void steer(PlayerEntity player) {
			Vector3d m = this.getMotion();
			double hv = Math.sqrt(m.x * m.x + m.z * m.z);
			float diff = MathHelper.wrapDegrees(player.rotationYaw - this.rotationYaw);
			this.rotationYaw += diff * (hv > 0.01D ? 0.3F : 1.0F);
			this.rotationPitch = 0.0F;
			this.renderYawOffset = this.rotationYaw;
			this.rotationYawHead = this.rotationYaw;

			double yawRad = Math.toRadians(this.rotationYaw);
			double fx = -Math.sin(yawRad);
			double fz = Math.cos(yawRad);
			double signed = m.x * fx + m.z * fz;
			float im = player.moveForward;
			if (Math.abs(im) > 0.001F) {
				signed += im > 0.0F ? 0.05D : -0.05D;
				signed = MathHelper.clamp(signed, -0.25D, 0.3D);
			} else {
				signed *= 0.8D;
			}
			double vy = hoverMotion(fx * signed, fz * signed, 2.25D);
			this.setMotion(fx * signed, vy, fz * signed);
			this.move(MoverType.SELF, this.getMotion());
		}

		public void goThisWay(double mx, double mz) {
			this.setMotion(mx, this.getMotion().y, mz);
		}

		// ---- каждый тик ----
		@Override
		public void livingTick() {
			this.lastPosX = this.getPosX();
			this.lastPosY = this.getPosY();
			this.lastPosZ = this.getPosZ();
			super.livingTick();
			this.rideTicker += this.rand.nextInt(3);
			if (this.playing > 0)
				this.playing--;
			if (this.world.isRemote) {
				updateLegs();
				return;
			}
			if (this.isBeingRidden()) {
				if (this.playing == 0 && this.rand.nextInt(80) == 1) {
					playWorldSound("robotspider", 0.35F);
					this.playing = 125;
				}
				if (this.world.getDifficulty() != Difficulty.PEACEFUL && !playNicely) {
					if (this.rand.nextInt(50) == 0)
						feetFindSomethingToHit();
					if (this.rand.nextInt(9) == 0) {
						LivingEntity e = findSomethingToAttack(1.0F, true);
						if (e != null) {
							if (this.getDistanceSq(e) < sq(6.0F + e.getWidth() / 2.0F)) {
								this.setAggroed(true);
								this.attackEntityAsMob(e);
							}
						} else {
							this.setAggroed(false);
						}
					}
				}
			} else {
				if (isOwned())
					this.setAggroed(false);
				else
					wildAI();
			}
		}

		@Override
		public void tick() {
			super.tick();
			if (this.world.isRemote) {
				// огонь, дым и искры из двигателей
				float f = 4.0F;
				float dx2 = (float) (f * Math.cos(Math.toRadians(this.rotationYaw - 90.0F)));
				float dz2 = (float) (f * Math.sin(Math.toRadians(this.rotationYaw - 90.0F)));
				float dx = (float) (f * Math.cos(Math.toRadians(this.rotationYaw - 80.0F)));
				float dz = (float) (f * Math.sin(Math.toRadians(this.rotationYaw - 80.0F)));
				jetParticles(dx, dz, dx2, dz2, f);
				dx = (float) (f * Math.cos(Math.toRadians(this.rotationYaw - 100.0F)));
				dz = (float) (f * Math.sin(Math.toRadians(this.rotationYaw - 100.0F)));
				jetParticles(dx, dz, dx2, dz2, f);
			}
		}

		private float jitter() {
			return this.rand.nextFloat() - this.rand.nextFloat();
		}

		private void jetParticles(float dx, float dz, float dx2, float dz2, float f) {
			double px = this.getPosX() + dx;
			double py = this.getPosY() + 0.5D;
			double pz = this.getPosZ() + dz;
			if (this.rand.nextInt(18) == 0)
				this.world.addParticle(ParticleTypes.FLAME, px, py, pz, dx2 / f + jitter() / 20.0F, jitter() / 10.0F, dz2 / f + jitter() / 20.0F);
			if (this.rand.nextInt(7) == 0)
				this.world.addParticle(ParticleTypes.SMOKE, px, py, pz, dx2 / f + jitter() / 20.0F, jitter() / 10.0F, dz2 / f + jitter() / 20.0F);
			if (this.rand.nextInt(16) == 0)
				this.world.addParticle(ParticleTypes.FIREWORK, px, py, pz, dx2 / f + jitter() / 20.0F, jitter() / 5.0F, dz2 / f + jitter() / 20.0F);
		}

		// ---- дикий (не приручённый) робот: идёт на цель и бьёт ----
		private static double sq(double v) {
			return v * v;
		}

		private void wildAI() {
			if (this.world.getDifficulty() == Difficulty.PEACEFUL || playNicely)
				return;
			if (this.rand.nextInt(20) == 0)
				feetFindSomethingToHit();
			if (this.rand.nextInt(150) == 0)
				this.setAttackTarget(null);
			LivingEntity e = this.getAttackTarget();
			if (e != null && !e.isAlive()) {
				this.setAttackTarget(null);
				e = null;
			}
			if (e == null)
				e = findSomethingToAttack(2.0F, false);
			if (e != null) {
				this.faceEntity(e, 10.0F, 10.0F);
				if (this.getDistanceSq(e) > 16.0D) {
					double d1 = e.getPosZ() - this.getPosZ();
					double d2 = e.getPosX() - this.getPosX();
					double dd = Math.atan2(d1, d2);
					goThisWay(0.2D * Math.cos(dd), 0.2D * Math.sin(dd));
				}
			} else {
				this.setAggroed(false);
			}
			if (e != null && this.rand.nextInt(15) == 0) {
				e = this.getAttackTarget();
				if (e == null)
					e = findSomethingToAttack(2.0F, true);
				if (e != null) {
					if (this.getDistanceSq(e) < sq(6.0F + e.getWidth() / 2.0F)) {
						this.setAggroed(true);
						this.attackEntityAsMob(e);
					} else {
						this.setAggroed(false);
					}
				} else {
					this.setAggroed(false);
				}
			}
		}

		// ---- выбор целей ----
		private boolean isSuitableTarget(LivingEntity target, boolean dircheck) {
			if (target == null || target == this || !target.isAlive())
				return false;
			if (target instanceof CustomEntity || target instanceof ArmorStandEntity)
				return false;
			if (target == this.getControllingPassenger())
				return false;
			if (!this.getEntitySenses().canSee(target))
				return false;
			if (dircheck) {
				double rr = Math.atan2(target.getPosZ() - this.getPosZ(), target.getPosX() - this.getPosX());
				double rhdir = Math.toRadians(((this.rotationYaw + 90.0F) % 360.0F));
				double pi = 3.1415926545D;
				double rdd = Math.abs(rr - rhdir) % pi * 2.0D;
				if (rdd > pi)
					rdd -= pi * 2.0D;
				rdd = Math.abs(rdd);
				if (this.getDistanceSq(target) < 36.0D)
					return !(target instanceof PlayerEntity && ((PlayerEntity) target).isCreative());
				if (rdd > 0.75D)
					return false;
			}
			if (target instanceof PlayerEntity && ((PlayerEntity) target).isCreative())
				return false;
			return true;
		}

		private LivingEntity findSomethingToAttack(float distmul, boolean dircheck) {
			if (playNicely)
				return null;
			List<LivingEntity> list = this.world.getEntitiesWithinAABB(LivingEntity.class,
					this.getBoundingBox().grow(12.0D * distmul, 12.0D, 12.0D * distmul));
			for (LivingEntity e : list) {
				if (isSuitableTarget(e, dircheck))
					return e;
			}
			return null;
		}

		// "Ногами" бьёт всех на расстоянии 6-9 блоков (слабее обычной атаки)
		private void feetFindSomethingToHit() {
			if (playNicely)
				return;
			List<LivingEntity> list = this.world.getEntitiesWithinAABB(LivingEntity.class, this.getBoundingBox().grow(10.0D, 8.0D, 10.0D));
			for (LivingEntity e : list) {
				if (e == null || e == this || !e.isAlive())
					continue;
				if (e instanceof CustomEntity || e instanceof ArmorStandEntity || e == this.getControllingPassenger())
					continue;
				if (!this.getEntitySenses().canSee(e))
					continue;
				double dd = Math.sqrt(this.getDistanceSq(e));
				if (dd > 9.0D || dd < 6.0D)
					continue;
				if (e instanceof PlayerEntity && ((PlayerEntity) e).isCreative())
					continue;
				feetAttack(e);
			}
		}

		private boolean feetAttack(Entity target) {
			boolean ret = false;
			if (target instanceof LivingEntity) {
				double ks = 0.6D;
				double inair = 0.1D;
				float f3 = (float) Math.atan2(target.getPosZ() - this.getPosZ(), target.getPosX() - this.getPosX());
				ret = target.attackEntityFrom(DamageSource.causeMobDamage(this), (float) (ROBOT_ATTACK / 10.0D));
				if (!target.isAlive() || target instanceof PlayerEntity)
					inair *= 2.0D;
				if (ret) {
					target.addVelocity(Math.cos(f3) * ks, inair, Math.sin(f3) * ks);
					target.velocityChanged = true;
				}
			}
			return ret;
		}

		// Основная атака (челюсти)
		@Override
		public boolean attackEntityAsMob(Entity target) {
			boolean ret = false;
			if (target instanceof LivingEntity) {
				double ks = 0.7D;
				double inair = 0.1D;
				float f3 = (float) Math.atan2(target.getPosZ() - this.getPosZ(), target.getPosX() - this.getPosX());
				ret = target.attackEntityFrom(DamageSource.causeMobDamage(this), (float) ROBOT_ATTACK);
				if (!target.isAlive() || target instanceof PlayerEntity)
					inair *= 2.0D;
				if (ret) {
					target.addVelocity(Math.cos(f3) * ks, inair, Math.sin(f3) * ks);
					target.velocityChanged = true;
				}
			}
			return ret;
		}

		// ---- дроп: 7-13 случайных редстоун-деталей ----
		private void dropItemRand(Item item, int count) {
			ItemEntity drop = new ItemEntity(this.world, this.getPosX() + this.rand.nextInt(2) - this.rand.nextInt(2), this.getPosY() + 1.0D,
					this.getPosZ() + this.rand.nextInt(2) - this.rand.nextInt(2), new ItemStack(item, count));
			this.world.addEntity(drop);
		}

		@Override
		protected void dropSpecialItems(DamageSource source, int looting, boolean recentlyHitIn) {
			super.dropSpecialItems(source, looting, recentlyHitIn);
			int n = 7 + this.rand.nextInt(7);
			for (int i = 0; i < n; i++) {
				switch (this.rand.nextInt(12)) {
					case 0 :
						dropItemRand(Items.REDSTONE, 1);
						break;
					case 1 :
						dropItemRand(Items.REPEATER, 1);
						break;
					case 2 :
						dropItemRand(Items.COMPARATOR, 1);
						break;
					case 3 :
					case 8 :
						dropItemRand(Blocks.REDSTONE_BLOCK.asItem(), 1);
						break;
					case 4 :
						dropItemRand(Blocks.DISPENSER.asItem(), 1);
						break;
					case 5 :
						dropItemRand(Blocks.STICKY_PISTON.asItem(), 1);
						break;
					case 6 :
						dropItemRand(Blocks.PISTON.asItem(), 1);
						break;
					case 7 :
						dropItemRand(Blocks.LEVER.asItem(), 1);
						break;
					case 9 :
						dropItemRand(Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE.asItem(), 1);
						break;
					case 10 :
						dropItemRand(Items.IRON_INGOT, 1);
						break;
					default :
						break;
				}
			}
		}

		// ================== анимация ног (только клиент) ==================
		// Перенесено из updateLegs / findNewFooting / initLegData оригинального AntRobot
		private void initLegData() {
		    if (this.renderdata == null) {
		      this.renderdata = new RenderSpiderRobotInfo();
		}
		    for (int i = 0; i < 6; i++) {
		      this.renderdata.ycurrentangle[i] = 0.0F;
		      this.renderdata.ywantedangle[i] = 0.0F;
		      this.renderdata.ydisplayangle[i] = 0.0F;
		      this.renderdata.yvelocity[i] = 0.0F;
		      this.renderdata.ymid[i] = 0.0F;
		      this.renderdata.yoff[i] = 0.0F;
		      this.renderdata.yrange[i] = 0.0F;
		      this.renderdata.udcurrentangle[i] = 0.0F;
		      this.renderdata.udwantedangle[i] = 0.0F;
		      this.renderdata.uddisplayangle[i] = 0.0F;
		      this.renderdata.udvelocity[i] = 0.0F;
		      this.renderdata.p1xangle[i] = 0.7853981633974483D;
		      this.renderdata.p2xangle[i] = 0.0D;
		      this.renderdata.p3xangle[i] = -0.7853981633974483D;
		      this.renderdata.pxvelocity[i] = 0.0F;
		      this.renderdata.foot_xpos[i] = (float)this.getPosX();
		      this.renderdata.foot_ypos[i] = (float)this.getPosY();
		      this.renderdata.foot_zpos[i] = (float)this.getPosZ();
		      this.renderdata.realposx[i] = 0.0F;
		      this.renderdata.realposy[i] = 0.0F;
		      this.renderdata.realposz[i] = 0.0F;
		      this.renderdata.legoff[i] = 0.0F;
		      this.renderdata.footup[i] = 1;
		      this.renderdata.uppoint[i] = 0.0F;
		      this.renderdata.footingticker[i] = 0;
		      this.renderdata.gpcounter = 0;
		      if (i == 0) {
		        this.renderdata.legoff[i] = 0.75F;
		        this.renderdata.ymid[i] = 0.0F;
		        this.renderdata.yrange[i] = 0.2617994F;
		        this.renderdata.pairedwith[i] = 1;
		        this.renderdata.yoff[i] = -0.75F;
		} 
		      if (i == 1) {
		        this.renderdata.legoff[i] = 0.75F;
		        this.renderdata.ymid[i] = 3.1415927F;
		        this.renderdata.yrange[i] = -0.2617994F;
		        this.renderdata.pairedwith[i] = 0;
		        this.renderdata.yoff[i] = -0.75F;
		} 
		      if (i == 2) {
		        this.renderdata.legoff[i] = 1.0F;
		        this.renderdata.ymid[i] = -0.7853982F;
		        this.renderdata.yrange[i] = 0.2617994F;
		        this.renderdata.pairedwith[i] = 3;
		        this.renderdata.yoff[i] = -0.75F;
		} 
		      if (i == 3) {
		        this.renderdata.legoff[i] = 1.0F;
		        this.renderdata.ymid[i] = 3.9269907F;
		        this.renderdata.yrange[i] = -0.2617994F;
		        this.renderdata.pairedwith[i] = 2;
		        this.renderdata.yoff[i] = -0.75F;
		} 
		if (i == 4) {
		        this.renderdata.legoff[i] = 1.15F;
		        this.renderdata.ymid[i] = 0.7853982F;
		        this.renderdata.yrange[i] = 0.2617994F;
		        this.renderdata.pairedwith[i] = 5;
		        this.renderdata.yoff[i] = -0.75F;
		} 
		      if (i == 5) {
		        this.renderdata.legoff[i] = 1.15F;
		        this.renderdata.ymid[i] = 2.3561945F;
		        this.renderdata.yrange[i] = -0.2617994F;
		        this.renderdata.pairedwith[i] = 4;
		        this.renderdata.yoff[i] = -0.75F;
		} 
		} 
		}
		private float getNewVelocity(float v, float diff, float curval) {
		    float tv = v;
		tv *= 18.0F;
		    if (tv < 2.0F) tv = 2.0F; 
		    if (tv > 8.0F) tv = 8.0F;
		if (diff > 0.0F) {
		      if (diff < 0.008726646259971648D * tv) {
		        curval = 0.0F;
		} else {
		        curval = (float)(curval + 0.004363323129985824D * tv);
		        if (diff < 0.06981317007977318D * tv) curval = (float)(0.017453292519943295D * tv); 
		        if (diff < 0.03490658503988659D * tv) curval = (float)(0.008726646259971648D * tv); 
		        if (curval > 0.06981317007977318D * tv) curval = (float)(0.06981317007977318D * tv);
		} 
		    } else if (diff > -0.008726646259971648D * tv) {
		      curval = 0.0F;
		} else {
		      curval = (float)(curval - 0.004363323129985824D * tv);
		      if (diff > -0.06981317007977318D * tv) curval = -((float)(0.017453292519943295D * tv)); 
		      if (diff > -0.03490658503988659D * tv) curval = -((float)(0.008726646259971648D * tv)); 
		      if (curval < -0.06981317007977318D * tv) curval = -((float)(0.06981317007977318D * tv));
		} 
		    return curval;
		}
		private void updateLegs() {
		    if (!this.world.isRemote) return;
		    final float yaw = ((this.rotationYaw % 360.0F) + 360.0F) % 360.0F;
		    this.renderdata.gpcounter++;
		    if (!this.legsInitialised) {
		      this.legsInitialised = true;
		      initLegData();
		    }
		    float d1 = (float)(this.lastPosX - this.getPosX());
		    float d2 = (float)(this.lastPosY - this.getPosY());
		    float d3 = (float)(this.lastPosZ - this.getPosZ());
		    float realv = (float)Math.sqrt((d1 * d1 + d2 * d2 + d3 * d3));
		int i = 0;
		for (i = 0; i < 6; i++) {
		      int fcount = 0;
		      this.renderdata.footingticker[i] = this.renderdata.footingticker[i] + 1;
		this.renderdata.realposx[i] = (float)(this.getPosX() - this.renderdata.legoff[i] * Math.sin(Math.toRadians(MathHelper.wrapDegrees((yaw + 90.0F))) + this.renderdata.ymid[i]));
		      this.renderdata.realposz[i] = (float)(this.getPosZ() + this.renderdata.legoff[i] * Math.cos(Math.toRadians(MathHelper.wrapDegrees((yaw + 90.0F))) + this.renderdata.ymid[i]));
		      this.renderdata.realposy[i] = (float)this.getPosY() + this.renderdata.yoff[i];
		int it = this.renderdata.footingticker[i] + this.renderdata.footingticker[this.renderdata.pairedwith[i]];
		      if (it > 50 && this.renderdata.footingticker[i] > this.renderdata.footingticker[this.renderdata.pairedwith[i]]) {
		        this.renderdata.footingticker[i] = 0;
		}
		d1 = this.renderdata.realposx[i] - this.renderdata.foot_xpos[i];
		      d2 = this.renderdata.realposy[i] - this.renderdata.foot_ypos[i];
		      d3 = this.renderdata.realposz[i] - this.renderdata.foot_zpos[i];
		      float dd = (float)Math.sqrt((d1 * d1 + d2 * d2 + d3 * d3));
		      dd *= 16.0F;
		float da = (float)(Math.abs(this.renderdata.ycurrentangle[i] - Math.toRadians(MathHelper.wrapDegrees(yaw)) + this.renderdata.ymid[i]) % 6.283185307179586D);
		      if (da > Math.PI) da = (float)(da - 6.283185307179586D); 
		      if (da < -3.141592653589793D) da = (float)(da + 6.283185307179586D); 
		      da = Math.abs(da);
		if (dd > 144.0F || dd < 22.0F || da > Math.abs(this.renderdata.yrange[i]) * 8.0F / 6.0F || Math.abs(this.renderdata.udcurrentangle[i]) > 1.25D || this.renderdata.footingticker[i] == 0) {
		findNewFooting(i);
		        d1 = this.renderdata.realposx[i] - this.renderdata.foot_xpos[i];
		        d2 = this.renderdata.realposy[i] - this.renderdata.foot_ypos[i];
		        d3 = this.renderdata.realposz[i] - this.renderdata.foot_zpos[i];
		        dd = (float)Math.sqrt((d1 * d1 + d2 * d2 + d3 * d3));
		        dd *= 16.0F;
		} 
		float c1 = (float)(49.0D * Math.cos(this.renderdata.p2xangle[i] - this.renderdata.p1xangle[i]));
		      float c2 = 49.0F;
		      float c3 = (float)(49.0D * Math.cos(this.renderdata.p2xangle[i] - this.renderdata.p3xangle[i]));
		      float cc = c1 + c2 + c3;
		float diff = cc - dd;
		      this.renderdata.pxvelocity[i] = getNewVelocity(realv, (float)(diff * Math.PI / 360.0D), this.renderdata.pxvelocity[i]);
		      if (this.renderdata.pxvelocity[i] == 0.0F || Math.abs(diff) < 8.0F) fcount++; 
		      this.renderdata.p1xangle[i] = this.renderdata.p1xangle[i] + this.renderdata.pxvelocity[i];
		      this.renderdata.p2xangle[i] = 0.0D;
		      this.renderdata.p3xangle[i] = -this.renderdata.p1xangle[i];
		if (this.renderdata.uppoint[i] != 0.0F) {
		        dd = (float)Math.atan2(dd, (this.renderdata.realposy[i] - this.renderdata.uppoint[i]) * 16.0D);
		} else {
		        dd = (float)Math.atan2(dd, (this.renderdata.realposy[i] - this.renderdata.foot_ypos[i]) * 16.0D);
		} 
		      this.renderdata.udwantedangle[i] = (float)(dd - 1.5707963267948966D);
		      for (; this.renderdata.udwantedangle[i] > Math.PI; this.renderdata.udwantedangle[i] = (float)(this.renderdata.udwantedangle[i] - 6.283185307179586D));
		      for (; this.renderdata.udwantedangle[i] < -3.141592653589793D; this.renderdata.udwantedangle[i] = (float)(this.renderdata.udwantedangle[i] + 6.283185307179586D));
		      double rhm = this.renderdata.udwantedangle[i];
		      double rhdir = this.renderdata.udcurrentangle[i];
		double rdv = (rhm - rhdir) % 6.283185307179586D;
		      for (; rdv > Math.PI; rdv -= 6.283185307179586D);
		      for (; rdv < -3.141592653589793D; rdv += 6.283185307179586D);
		      diff = (float)rdv;
		this.renderdata.udvelocity[i] = getNewVelocity(realv * 2.0F, diff, this.renderdata.udvelocity[i]);
		      if (this.renderdata.udvelocity[i] == 0.0F || Math.abs(diff) < 0.03490658503988659D) {
		        this.renderdata.uppoint[i] = 0.0F;
		        fcount++;
		} 
		      rhdir += this.renderdata.udvelocity[i];
		for (; rhdir > Math.PI; rhdir -= 6.283185307179586D);
		      for (; rhdir < -3.141592653589793D; rhdir += 6.283185307179586D);
		dd = this.renderdata.udcurrentangle[i] = (float)rhdir;
		      this.renderdata.uddisplayangle[i] = dd;
		d1 = this.renderdata.realposx[i] - this.renderdata.foot_xpos[i];
		      d3 = this.renderdata.realposz[i] - this.renderdata.foot_zpos[i];
		      dd = (float)Math.atan2(d3, d1);
		      this.renderdata.ywantedangle[i] = dd; rhm = dd;
		      rhdir = this.renderdata.ycurrentangle[i];
		rdv = (rhm - rhdir) % 6.283185307179586D;
		      if (rdv > Math.PI) rdv -= 6.283185307179586D; 
		      if (rdv < -3.141592653589793D) rdv += 6.283185307179586D; 
		      diff = (float)rdv;
		this.renderdata.yvelocity[i] = getNewVelocity(realv, diff, this.renderdata.yvelocity[i]);
		      if (this.renderdata.yvelocity[i] == 0.0F || Math.abs(diff) < 0.03490658503988659D) fcount++; 
		      this.renderdata.ycurrentangle[i] = this.renderdata.ycurrentangle[i] + this.renderdata.yvelocity[i];
		      for (; this.renderdata.ycurrentangle[i] > Math.PI; this.renderdata.ycurrentangle[i] = (float)(this.renderdata.ycurrentangle[i] - 6.283185307179586D));
		      for (; this.renderdata.ycurrentangle[i] < -3.141592653589793D; this.renderdata.ycurrentangle[i] = (float)(this.renderdata.ycurrentangle[i] + 6.283185307179586D));
		dd = (float)(this.renderdata.ycurrentangle[i] - Math.toRadians(MathHelper.wrapDegrees(yaw)) - 1.5707963267948966D);
		      for (; dd > Math.PI; dd = (float)(dd - 6.283185307179586D));
		      for (; dd < -3.141592653589793D; dd = (float)(dd + 6.283185307179586D));
		      this.renderdata.ydisplayangle[i] = dd;
		if (fcount == 3) {
		        this.renderdata.footup[i] = 0;
		}
		} 
		}
		private void findNewFooting(int i) {
		    final float yaw = ((this.rotationYaw % 360.0F) + 360.0F) % 360.0F;
		    float f = 9.0F;
		int found = 0;
		float range = 0.0F;
		double rhdir = Math.toRadians(((yaw + 90.0F) % 360.0F));
		double pi = 3.1415926545D;
		this.renderdata.footingticker[i] = 0;
		float d1 = (float)(this.getPosX() - this.lastPosX);
		    float d3 = (float)(this.getPosZ() - this.lastPosZ);
		    double rhm = Math.atan2(d3, d1);
		    double velocity = Math.sqrt((d1 * d1 + d3 * d3));
		double rdv = Math.abs(rhm - rhdir) % pi * 2.0D;
		    if (rdv > pi) rdv -= pi * 2.0D; 
		    rdv = Math.abs(rdv);
		    if (Math.abs(velocity) < 0.01D) rdv = 0.0D; 
		    range = this.renderdata.yrange[i];
		    range *= 0.8F;
		    if (Math.abs((this.prevRotationYaw - yaw) % 360.0F) > 0.75F) range = 0.0F;
		if (i >= 4) f = 4.0F; 
		    if (rdv > 1.5D) {
		range = -range;
		      f = 4.0F;
		      if (i >= 4) f = 9.0F; 
		} 
		    if (i == 0 || i == 1) f = 6.0F;
		float fx = (float)(this.renderdata.realposx[i] - (f / 2.0F) * Math.sin(Math.toRadians(MathHelper.wrapDegrees((yaw + 90.0F))) + this.renderdata.ymid[i])), deffx = fx;
		    float fz = (float)(this.renderdata.realposz[i] + (f / 2.0F) * Math.cos(Math.toRadians(MathHelper.wrapDegrees((yaw + 90.0F))) + this.renderdata.ymid[i])), deffz = fz;
		    float fy = this.renderdata.realposy[i] - 1.0F, deffy = fy;
		    float oldf = f;
		    int span = 1;
		    while (found == 0 && f > 2.5F) {
		      fx = (float)(this.renderdata.realposx[i] - f * Math.sin(Math.toRadians(MathHelper.wrapDegrees((yaw + 90.0F))) + this.renderdata.ymid[i] - range));
		      fz = (float)(this.renderdata.realposz[i] + f * Math.cos(Math.toRadians(MathHelper.wrapDegrees((yaw + 90.0F))) + this.renderdata.ymid[i] - range));
		      fy = this.renderdata.realposy[i];
		for (int j = 8; found == 0 && j > -9; j--) {
		        for (int m = -span; found == 0 && m <= span; m++) {
		          for (int n = -span; found == 0 && n <= span; n++) {
		            BlockPos bp = new BlockPos(MathHelper.floor(fx) + m, MathHelper.floor(fy) + j, MathHelper.floor(fz) + n);
		            if (this.world.getBlockState(bp).getMaterial().isSolid()) {
		              d1 = this.renderdata.realposx[i] - fx + m;
		              float d2 = this.renderdata.realposy[i] - fy + j + 1.0F;
		              d3 = this.renderdata.realposz[i] - fz + n;
		              float dd = (float)Math.sqrt((d1 * d1 + d2 * d2 + d3 * d3));
		              dd *= 16.0F;
		              if (dd <= 144.0F) {
		fy += (j + 1);
		                fx += m;
		                fz += n;
		                found = 1;
		break;
		} 
		} 
		} 
		} 
		} 
		f--;
		      if (f < 2.5F && 
		        range != 0.0F) {
		        range = 0.0F;
		        span = 3;
		        f = oldf;
		} 
		} 
		if (found == 0) {
		      fx = deffx;
		      fy = deffy;
		      fz = deffz;
		} 
		float sfx = this.renderdata.foot_xpos[i];
		    float sfy = this.renderdata.foot_ypos[i];
		    float sfz = this.renderdata.foot_zpos[i];
		    this.renderdata.foot_xpos[i] = fx;
		    this.renderdata.foot_ypos[i] = fy;
		    this.renderdata.foot_zpos[i] = fz;
		if (this.renderdata.footup[i] == 0) {
		      this.renderdata.footup[i] = 1;
		d1 = sfx - fx;
		      float d2 = sfy - fy;
		      d3 = sfz - fz;
		      float dd = (float)Math.sqrt((d1 * d1 + d2 * d2 + d3 * d3));
		      dd *= 16.0F;
		      d1 = (sfy + fy) / 2.0F;
		      if (dd > 3.0F) {
		        d1 += 0.3F;
		}
		      if (dd > 24.0F) {
		        d1 += 0.6F;
		}
		      if (dd > 50.0F) {
		        d1 += 0.6F;
		}
		      this.renderdata.uppoint[i] = d1;
		} 
		}

	}
}
