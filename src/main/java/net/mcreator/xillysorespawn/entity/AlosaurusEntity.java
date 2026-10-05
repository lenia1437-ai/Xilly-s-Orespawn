
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
import net.minecraftforge.common.MinecraftForge;

import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.World;
import net.minecraft.world.IServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.DamageSource;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.network.IPacket;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.item.Item;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.item.ArmorStandEntity;
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

import net.mcreator.xillysorespawn.entity.renderer.AlosaurusRenderer;
import net.mcreator.xillysorespawn.XillysOrespawnModElements;

@XillysOrespawnModElements.ModElement.Tag
public class AlosaurusEntity extends XillysOrespawnModElements.ModElement {
	// ВАЖНО: в старом коде эти значения брались из OreSpawnMain.Alosaurus_stats (health / attack / defense).
	// Самого OreSpawnMain в присланном коде не было, поэтому подставь сюда свои числа.
	private static final double ALOSAURUS_HEALTH = 150.0D;
	private static final double ALOSAURUS_ATTACK = 14.0D;
	private static final double ALOSAURUS_ARMOR = 6.0D;

	// modid для звуков (alo_living / alo_hurt / alo_death). Если звуков в проекте нет - используются ванильные.
	private static final String MODID = "xillys_orespawn";

	public static EntityType entity = (EntityType.Builder.<CustomEntity>create(CustomEntity::new, EntityClassification.MONSTER)
			.setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(CustomEntity::new)
			.size(1.9f, 3.6f)).build("alosaurus").setRegistryName("alosaurus");

	public AlosaurusEntity(XillysOrespawnModElements instance) {
		super(instance, 2);
		FMLJavaModLoadingContext.get().getModEventBus().register(new AlosaurusRenderer.ModelRegisterHandler());
		FMLJavaModLoadingContext.get().getModEventBus().register(new EntityAttributesRegisterHandler());
		MinecraftForge.EVENT_BUS.register(this);
	}

	@Override
	public void initElements() {
		elements.entities.add(() -> entity);
		elements.items.add(() -> new SpawnEggItem(entity, -1, -1, new Item.Properties().group(ItemGroup.MISC)).setRegistryName("alosaurus_spawn_egg"));
	}

	@SubscribeEvent
	public void addFeatureToBiomes(BiomeLoadingEvent event) {
	    if (!OreSpawnLogic.allowNaturalSpawn(event, "alosaurus")) return;
		event.getSpawns().getSpawner(EntityClassification.MONSTER).add(new MobSpawnInfo.Spawners(entity, 10, 1, 1));
	}

	@Override
	public void init(FMLCommonSetupEvent event) {
		EntitySpawnPlacementRegistry.register(entity, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
				AlosaurusEntity::canSpawn);
	}

	// Аналог getCanSpawnHere из 1.7.10: на поверхности (y >= 50), только ночью, нужно 5 блоков воздуха над головой
	// и поблизости не должно быть другого Alosaurus
	public static boolean canSpawn(EntityType<? extends MonsterEntity> type, IServerWorld world, SpawnReason reason, BlockPos pos, Random random) {
		if (reason == SpawnReason.SPAWNER)
			return true;
		if (!MonsterEntity.canMonsterSpawn(type, world, reason, pos, random))
			return false;
		if (pos.getY() < 50)
			return false;
		if (world.getWorld().isDaytime())
			return false;
		for (int dx = -1; dx < 1; dx++) {
			for (int dz = -1; dz < 1; dz++) {
				for (int dy = 1; dy < 6; dy++) {
					if (!world.isAirBlock(pos.add(dx, dy, dz)))
						return false;
				}
			}
		}
		AxisAlignedBB area = new AxisAlignedBB(pos).grow(16.0D, 8.0D, 16.0D);
		return world.getEntitiesWithinAABB(CustomEntity.class, area, e -> true).isEmpty();
	}

	private static class EntityAttributesRegisterHandler {
		@SubscribeEvent
		public void onEntityAttributeCreation(EntityAttributeCreationEvent event) {
			AttributeModifierMap.MutableAttribute ammma = MobEntity.func_233666_p_();
			ammma = ammma.createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.35);
			ammma = ammma.createMutableAttribute(Attributes.MAX_HEALTH, ALOSAURUS_HEALTH);
			ammma = ammma.createMutableAttribute(Attributes.ARMOR, ALOSAURUS_ARMOR);
			ammma = ammma.createMutableAttribute(Attributes.ATTACK_DAMAGE, ALOSAURUS_ATTACK);
			ammma = ammma.createMutableAttribute(Attributes.FOLLOW_RANGE, 12);
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
		// Аналог OreSpawnMain.PlayNicely: true = не атакует
		public static boolean playNicely = false;

		public CustomEntity(FMLPlayMessages.SpawnEntity packet, World world) {
			this(entity, world);
		}

		public CustomEntity(EntityType<CustomEntity> type, World world) {
			super(type, world);
			experienceValue = 40;
			setNoAI(false);
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
			// Бьёт с расстояния ~4 блоков (лапы + длинная шея), как в оригинале
			this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.25D, false) {
				@Override
				protected double getAttackReachSqr(LivingEntity target) {
					float r = 4.0F + target.getWidth() / 2.0F;
					return r * r;
				}
			});
			this.goalSelector.addGoal(2, new MoveThroughVillageGoal(this, 1.0D, false, 4, () -> true));
			this.goalSelector.addGoal(3, new RandomWalkingGoal(this, 1.0D, 16));
			this.goalSelector.addGoal(4, new LookAtGoal(this, PlayerEntity.class, 8.0F));
			this.goalSelector.addGoal(5, new LookRandomlyGoal(this));
			this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
			// Нападает на ВСЕХ живых существ (кроме себе подобных, креативных игроков и стоек для брони), нужна видимость
			this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<LivingEntity>(this, LivingEntity.class, 5, true, false, target -> {
				if (target instanceof CustomEntity || target instanceof ArmorStandEntity)
					return false;
				if (target instanceof PlayerEntity && ((PlayerEntity) target).isCreative())
					return false;
				return true;
			}) {
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
			return this.rand.nextInt(4) == 0 ? sound("alo_living", null) : null;
		}

		@Override
		public SoundEvent getHurtSound(DamageSource ds) {
			return sound("alo_hurt", "entity.generic.hurt");
		}

		@Override
		public SoundEvent getDeathSound() {
			return sound("alo_death", "entity.generic.death");
		}

		@Override
		protected float getSoundVolume() {
			return 1.5F;
		}

		// ---- дроп: 10 золотых самородков и 6 сырой говядины ----
		private void dropItemRand(Item item, int count) {
			ItemEntity drop = new ItemEntity(this.world, this.getPosX() + this.rand.nextInt(4) - this.rand.nextInt(4), this.getPosY() + 1.0D,
					this.getPosZ() + this.rand.nextInt(4) - this.rand.nextInt(4), new ItemStack(item, count));
			this.world.addEntity(drop);
		}

		@Override
		protected void dropSpecialItems(DamageSource source, int looting, boolean recentlyHitIn) {
			super.dropSpecialItems(source, looting, recentlyHitIn);
			for (int i = 0; i < 10; i++)
				dropItemRand(Items.GOLD_NUGGET, 1);
			for (int i = 0; i < 6; i++)
				dropItemRand(Items.BEEF, 1);
		}

		// ---- атака: сильный отброс ----
		@Override
		public boolean attackEntityAsMob(Entity target) {
			if (!super.attackEntityAsMob(target))
				return false;
			if (target instanceof LivingEntity) {
				double ks = 1.2D;
				double inair = 0.1D;
				float f3 = (float) Math.atan2(target.getPosZ() - this.getPosZ(), target.getPosX() - this.getPosX());
				if (!target.isAlive() || target instanceof PlayerEntity)
					inair *= 2.0D;
				target.addVelocity(Math.cos(f3) * ks, inair, Math.sin(f3) * ks);
				target.velocityChanged = true;
			}
			return true;
		}
	}
}
