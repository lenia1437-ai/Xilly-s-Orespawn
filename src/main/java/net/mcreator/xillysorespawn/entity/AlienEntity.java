
package net.mcreator.xillysorespawn.entity;

import java.util.Random;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.common.MinecraftForge;

import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.World;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.Difficulty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.DamageSource;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.network.IPacket;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.item.Item;
import net.minecraft.block.Blocks;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.RandomWalkingGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.MoveThroughVillageGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.Entity;
import net.minecraft.entity.CreatureAttribute;

import net.mcreator.xillysorespawn.entity.renderer.AlienRenderer;
import net.mcreator.xillysorespawn.XillysOrespawnModElements;

@XillysOrespawnModElements.ModElement.Tag
public class AlienEntity extends XillysOrespawnModElements.ModElement {
	// ВАЖНО: в старом коде эти значения брались из OreSpawnMain.Alien_stats (health / attack / defense).
	// Самого OreSpawnMain в присланном коде не было, поэтому подставь сюда свои числа.
	private static final double ALIEN_HEALTH = 200.0D;
	private static final double ALIEN_ATTACK = 12.0D;
	private static final double ALIEN_ARMOR = 5.0D;

	// modid для звуков (alien_living / alien_hurt / alien_death). Если звуков в проекте нет - используются ванильные.
	private static final String MODID = "xillys_orespawn";

	public static EntityType entity = (EntityType.Builder.<CustomEntity>create(CustomEntity::new, EntityClassification.MONSTER)
			.setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(CustomEntity::new)
			.size(1.1f, 3.25f)).build("alien").setRegistryName("alien");

	public AlienEntity(XillysOrespawnModElements instance) {
		super(instance, 1);
		FMLJavaModLoadingContext.get().getModEventBus().register(new AlienRenderer.ModelRegisterHandler());
		FMLJavaModLoadingContext.get().getModEventBus().register(new EntityAttributesRegisterHandler());
		MinecraftForge.EVENT_BUS.register(this);
	}

	@Override
	public void initElements() {
		elements.entities.add(() -> entity);
		elements.items.add(() -> new SpawnEggItem(entity, -1, -1, new Item.Properties().group(ItemGroup.MISC)).setRegistryName("alien_spawn_egg"));
	}

	@SubscribeEvent
	public void addFeatureToBiomes(BiomeLoadingEvent event) {
	    if (!OreSpawnLogic.allowNaturalSpawn(event, "alien")) return;
		event.getSpawns().getSpawner(EntityClassification.MONSTER).add(new MobSpawnInfo.Spawners(entity, 5, 1, 1));
	}

	@Override
	public void init(FMLCommonSetupEvent event) {
		EntitySpawnPlacementRegistry.register(entity, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
				AlienEntity::canSpawn);
	}

	// Аналог getCanSpawnHere из 1.7.10: только в пещерах (y <= 50) и только там, где над головой есть 3 блока воздуха
	public static boolean canSpawn(EntityType<? extends MonsterEntity> type, IServerWorld world, SpawnReason reason, BlockPos pos, Random random) {
		if (!MonsterEntity.canMonsterSpawn(type, world, reason, pos, random))
			return false;
		if (reason == SpawnReason.SPAWNER)
			return true;
		if (pos.getY() > 50)
			return false;
		for (int dx = -1; dx <= 1; dx++) {
			for (int dz = -1; dz <= 1; dz++) {
				for (int dy = 1; dy < 4; dy++) {
					if (!world.isAirBlock(pos.add(dx, dy, dz)))
						return false;
				}
			}
		}
		return true;
	}

	private static class EntityAttributesRegisterHandler {
		@SubscribeEvent
		public void onEntityAttributeCreation(EntityAttributeCreationEvent event) {
			AttributeModifierMap.MutableAttribute ammma = MobEntity.func_233666_p_();
			ammma = ammma.createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.65);
			ammma = ammma.createMutableAttribute(Attributes.MAX_HEALTH, ALIEN_HEALTH);
			ammma = ammma.createMutableAttribute(Attributes.ARMOR, ALIEN_ARMOR);
			ammma = ammma.createMutableAttribute(Attributes.ATTACK_DAMAGE, ALIEN_ATTACK);
			ammma = ammma.createMutableAttribute(Attributes.FOLLOW_RANGE, 16);
			event.put(entity, ammma.create());
		}
	}

	private static SoundEvent sound(String custom, String fallback) {
		SoundEvent s = ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(MODID, custom));
		if (s == null && fallback != null)
			s = ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(fallback));
		return s;
	}

	public static class CustomEntity extends MonsterEntity {
		// Аналог OreSpawnMain.PlayNicely: true = не атакует и не ломает факелы
		public static boolean playNicely = false;

		public CustomEntity(FMLPlayMessages.SpawnEntity packet, World world) {
			this(entity, world);
		}

		public CustomEntity(EntityType<CustomEntity> type, World world) {
			super(type, world);
			experienceValue = 100;
			setNoAI(false);
			this.jumpMovementFactor = 0.6F;
			((GroundPathNavigator) this.getNavigator()).setBreakDoors(true);
		}

		@Override
		public IPacket<?> createSpawnPacket() {
			return NetworkHooks.getEntitySpawningPacket(this);
		}

		@Override
		protected void registerGoals() {
			super.registerGoals();
			this.goalSelector.addGoal(0, new SwimGoal(this));
			this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.2D, false) {
				@Override
				protected double getAttackReachSqr(LivingEntity target) {
					return 16.0D + target.getWidth() * target.getWidth();
				}
			});
			this.goalSelector.addGoal(2, new MoveThroughVillageGoal(this, 1.0D, false, 4, () -> true));
			this.goalSelector.addGoal(3, new RandomWalkingGoal(this, 1.0D, 10));
			this.goalSelector.addGoal(4, new LookAtGoal(this, PlayerEntity.class, 8.0F));
			this.goalSelector.addGoal(5, new LookRandomlyGoal(this));
			this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
			// Атакует игроков (кроме креативных) без проверки видимости, как в оригинале
			this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<PlayerEntity>(this, PlayerEntity.class, 10, false, false, null) {
				@Override
				public boolean shouldExecute() {
					return !playNicely && super.shouldExecute();
				}
			});
		}

		@Override
		public CreatureAttribute getCreatureAttribute() {
			return CreatureAttribute.UNDEFINED;
		}

		// ---- звуки ----
		@Override
		protected SoundEvent getAmbientSound() {
			return this.rand.nextInt(4) == 0 ? sound("alien_living", null) : null;
		}

		@Override
		public SoundEvent getHurtSound(DamageSource ds) {
			return sound("alien_hurt", "entity.generic.hurt");
		}

		@Override
		public SoundEvent getDeathSound() {
			return sound("alien_death", "entity.generic.death");
		}

		// ---- прыжок выше обычного ----
		@Override
		protected void jump() {
			super.jump();
			net.minecraft.util.math.vector.Vector3d v = this.getMotion();
			this.setMotion(v.x, v.y + 0.25D, v.z);
		}

		// ---- дроп ----
		private void dropItemRand(Item item, int count) {
			ItemEntity drop = new ItemEntity(this.world, this.getPosX() + this.rand.nextInt(4) - this.rand.nextInt(4), this.getPosY() + 1.0D,
					this.getPosZ() + this.rand.nextInt(4) - this.rand.nextInt(4), new ItemStack(item, count));
			this.world.addEntity(drop);
		}

		@Override
		protected void dropSpecialItems(DamageSource source, int looting, boolean recentlyHitIn) {
			super.dropSpecialItems(source, looting, recentlyHitIn);
			int n = 5 + this.rand.nextInt(6);
			for (int i = 0; i < n; i++)
				dropItemRand(Items.SPIDER_EYE, 1);
			n = 5 + this.rand.nextInt(6);
			for (int i = 0; i < n; i++)
				dropItemRand(Items.FLINT, 1);
			dropItemRand(Items.MAP, 1);
			dropItemRand(Items.CLOCK, 1);
			dropItemRand(Items.COMPASS, 1);
		}

		// ---- атака: яд (шанс 1/5) + сильный отброс ----
		@Override
		public boolean attackEntityAsMob(Entity target) {
			if (!super.attackEntityAsMob(target))
				return false;
			if (target instanceof LivingEntity) {
				// В оригинале из-за вложенных if сложность реально учитывалась только для EASY;
				// здесь сделано как задумано: чем выше сложность, тем дольше яд.
				int var2 = 6;
				Difficulty difficulty = this.world.getDifficulty();
				if (difficulty == Difficulty.EASY)
					var2 = 8;
				else if (difficulty == Difficulty.NORMAL)
					var2 = 10;
				else if (difficulty == Difficulty.HARD)
					var2 = 12;
				if (this.rand.nextInt(5) == 1)
					((LivingEntity) target).addPotionEffect(new EffectInstance(Effects.POISON, var2 * 5, 0));
				double ks = 1.1D;
				double inair = 0.1D;
				float f3 = (float) Math.atan2(target.getPosZ() - this.getPosZ(), target.getPosX() - this.getPosX());
				if (!target.isAlive() || target instanceof PlayerEntity)
					inair *= 2.0D;
				target.addVelocity(Math.cos(f3) * ks, inair, Math.sin(f3) * ks);
				target.velocityChanged = true;
			}
			return true;
		}

		// ---- получение урона: кактус не страшен, на мобов-обидчиков реагирует ----
		@Override
		public boolean attackEntityFrom(DamageSource source, float amount) {
			if (source == DamageSource.CACTUS)
				return false;
			boolean ret = super.attackEntityFrom(source, amount);
			Entity attacker = source.getTrueSource();
			if (attacker instanceof MobEntity && attacker != this) {
				this.setAttackTarget((LivingEntity) attacker);
				this.getNavigator().tryMoveToEntityLiving(attacker, 1.2D);
				ret = true;
			}
			return ret;
		}

		// ---- каждый тик: капающая лава (клиент), охота на факелы, регенерация ----
		@Override
		public void livingTick() {
			super.livingTick();
			if (this.world.isRemote) {
				float f = 1.7F + Math.abs(this.rand.nextFloat() * 0.75F);
				if (this.rand.nextInt(20) == 1)
					this.world.addParticle(ParticleTypes.DRIPPING_LAVA, this.getPosX() - f * Math.sin(Math.toRadians(this.rotationYawHead)),
							this.getPosY() + 1.6D, this.getPosZ() + f * Math.cos(Math.toRadians(this.rotationYawHead)), 0.0D, 0.0D, 0.0D);
				return;
			}
			if (!playNicely && this.rand.nextInt(30) == 0)
				huntTorches();
			if (this.rand.nextInt(40) == 1 && this.getHealth() < this.getMaxHealth())
				this.heal(1.0F);
		}

		private static boolean isTorch(Block b) {
			return b == Blocks.TORCH || b == Blocks.WALL_TORCH || b == Blocks.SOUL_TORCH || b == Blocks.SOUL_WALL_TORCH;
		}

		// Ищет ближайший факел (расширяющимися "оболочками" радиусом 2..10, затем 12 и 14), идёт к нему и гасит
		private void huntTorches() {
			BlockPos origin = this.getPosition();
			BlockPos best = null;
			int bestD = Integer.MAX_VALUE;
			for (int r = 2; r < 15 && best == null; r += (r >= 10 ? 2 : 1)) {
				for (int dx = -r; dx <= r; dx++) {
					for (int dy = -r; dy <= r; dy++) {
						boolean edge = Math.abs(dx) == r || Math.abs(dy) == r;
						int step = edge ? 1 : 2 * r;
						for (int dz = -r; dz <= r; dz += step) {
							BlockPos p = origin.add(dx, dy, dz);
							if (!this.world.isBlockLoaded(p))
								continue;
							if (isTorch(this.world.getBlockState(p).getBlock())) {
								int d = dx * dx + dy * dy + dz * dz;
								if (d < bestD) {
									bestD = d;
									best = p;
								}
							}
						}
					}
				}
			}
			if (best != null) {
				this.getNavigator().tryMoveToXYZ(best.getX(), best.getY(), best.getZ(), 1.0D);
				if (bestD < 27 && ForgeEventFactory.getMobGriefingEvent(this.world, this))
					this.world.setBlockState(best, Blocks.AIR.getDefaultState(), 3);
			}
		}
	}
}
