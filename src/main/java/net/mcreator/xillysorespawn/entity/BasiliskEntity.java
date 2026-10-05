package net.mcreator.xillysorespawn.entity;

import java.util.List;
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
import net.minecraft.world.Difficulty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.DamageSource;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.network.IPacket;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.item.Item;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.RandomWalkingGoal;
import net.minecraft.entity.ai.goal.MoveThroughVillageGoal;
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
import net.minecraft.entity.item.ArmorStandEntity;

import net.mcreator.xillysorespawn.entity.renderer.BasiliskRenderer;
import net.mcreator.xillysorespawn.XillysOrespawnModElements;

@XillysOrespawnModElements.ModElement.Tag
public class BasiliskEntity extends XillysOrespawnModElements.ModElement {
	// ВАЖНО: в старом коде эти значения брались из OreSpawnMain.Basilisk_stats (health / attack / defense).
	// Самого OreSpawnMain в присланном коде не было, поэтому подставь сюда свои числа.
	private static final double BASILISK_HEALTH = 250.0D;
	private static final double BASILISK_ATTACK = 16.0D;
	private static final double BASILISK_ARMOR = 8.0D;

	// В оригинале дропались предметы OreSpawn (чешуя василиска и изумрудные инструменты/броня).
	// Здесь они заменены ванильными - подставь свои предметы, если они есть в проекте.
	private static final Item SCALE = Items.SCUTE;
	private static final Item EMERALD_SWORD = Items.DIAMOND_SWORD;
	private static final Item EMERALD_SHOVEL = Items.DIAMOND_SHOVEL;
	private static final Item EMERALD_PICKAXE = Items.DIAMOND_PICKAXE;
	private static final Item EMERALD_AXE = Items.DIAMOND_AXE;
	private static final Item EMERALD_HOE = Items.DIAMOND_HOE;
	private static final Item EMERALD_HELMET = Items.DIAMOND_HELMET;
	private static final Item EMERALD_BODY = Items.DIAMOND_CHESTPLATE;
	private static final Item EMERALD_LEGS = Items.DIAMOND_LEGGINGS;
	private static final Item EMERALD_BOOTS = Items.DIAMOND_BOOTS;

	// modid для звуков (basilisk_living / alo_hurt / emperorscorpion_death). Если звуков нет - ванильные или без звука.
	private static final String MODID = "xillys_orespawn";

	public static EntityType entity = (EntityType.Builder.<CustomEntity>create(CustomEntity::new, EntityClassification.MONSTER)
			.setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(CustomEntity::new)
			.immuneToFire().size(1.6f, 3.5f)).build("basilisk").setRegistryName("basilisk");

	public BasiliskEntity(XillysOrespawnModElements instance) {
		super(instance, 6);
		FMLJavaModLoadingContext.get().getModEventBus().register(new BasiliskRenderer.ModelRegisterHandler());
		FMLJavaModLoadingContext.get().getModEventBus().register(new EntityAttributesRegisterHandler());
		MinecraftForge.EVENT_BUS.register(this);
	}

	@Override
	public void initElements() {
		elements.entities.add(() -> entity);
		elements.items.add(() -> new SpawnEggItem(entity, -1, -1, new Item.Properties().group(ItemGroup.MISC)).setRegistryName("basilisk_spawn_egg"));
	}

	@SubscribeEvent
	public void addFeatureToBiomes(BiomeLoadingEvent event) {
	    if (!OreSpawnLogic.allowNaturalSpawn(event, "basilisk")) return;
		event.getSpawns().getSpawner(EntityClassification.MONSTER).add(new MobSpawnInfo.Spawners(entity, 5, 1, 1));
	}

	@Override
	public void init(FMLCommonSetupEvent event) {
		EntitySpawnPlacementRegistry.register(entity, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
				BasiliskEntity::canSpawn);
	}

	// Аналог getCanSpawnHere из 1.7.10: только ночью, нужно 4 блока воздуха над головой и рядом нет другого Basilisk
	public static boolean canSpawn(EntityType<? extends MonsterEntity> type, IServerWorld world, SpawnReason reason, BlockPos pos, Random random) {
		if (reason == SpawnReason.SPAWNER)
			return true;
		if (!MonsterEntity.canMonsterSpawn(type, world, reason, pos, random))
			return false;
		if (world.getWorld().isDaytime())
			return false;
		for (int dx = -1; dx < 2; dx++) {
			for (int dz = -1; dz < 2; dz++) {
				for (int dy = 1; dy < 5; dy++) {
					if (!world.isAirBlock(pos.add(dx, dy, dz)))
						return false;
				}
			}
		}
		AxisAlignedBB area = new AxisAlignedBB(pos).grow(20.0D, 6.0D, 20.0D);
		return world.getEntitiesWithinAABB(CustomEntity.class, area, e -> true).isEmpty();
	}

	private static class EntityAttributesRegisterHandler {
		@SubscribeEvent
		public void onEntityAttributeCreation(EntityAttributeCreationEvent event) {
			AttributeModifierMap.MutableAttribute ammma = MobEntity.func_233666_p_();
			ammma = ammma.createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.4);
			ammma = ammma.createMutableAttribute(Attributes.MAX_HEALTH, BASILISK_HEALTH);
			ammma = ammma.createMutableAttribute(Attributes.ARMOR, BASILISK_ARMOR);
			ammma = ammma.createMutableAttribute(Attributes.ATTACK_DAMAGE, BASILISK_ATTACK);
			ammma = ammma.createMutableAttribute(Attributes.FOLLOW_RANGE, 24);
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

		private int hurtTimer = 0;

		public CustomEntity(FMLPlayMessages.SpawnEntity packet, World world) {
			this(entity, world);
		}

		public CustomEntity(EntityType<CustomEntity> type, World world) {
			super(type, world);
			experienceValue = 150;
			setNoAI(false);
		}

		@Override
		public IPacket<?> createSpawnPacket() {
			return NetworkHooks.getEntitySpawningPacket(this);
		}

		@Override
		protected void registerGoals() {
			super.registerGoals();
			this.goalSelector.addGoal(0, new SwimGoal(this));
			this.goalSelector.addGoal(1, new MoveThroughVillageGoal(this, 1.0D, false, 4, () -> true));
			this.goalSelector.addGoal(2, new RandomWalkingGoal(this, 1.0D, 20));
			this.goalSelector.addGoal(3, new LookAtGoal(this, PlayerEntity.class, 8.0F));
			this.goalSelector.addGoal(4, new LookRandomlyGoal(this));
			this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
		}

		@Override
		public CreatureAttribute getCreatureAttribute() {
			return CreatureAttribute.UNDEFINED;
		}

		// ---- звуки ----
		@Override
		protected SoundEvent getAmbientSound() {
			return this.rand.nextInt(2) == 0 ? sound("basilisk_living", null) : null;
		}

		@Override
		public SoundEvent getHurtSound(DamageSource ds) {
			return sound("alo_hurt", "entity.generic.hurt");
		}

		@Override
		public SoundEvent getDeathSound() {
			return sound("emperorscorpion_death", "entity.generic.death");
		}

		// ---- после удара 1.5 секунды неуязвим ----
		@Override
		public boolean attackEntityFrom(DamageSource source, float amount) {
			if (this.hurtTimer > 0)
				return false;
			this.hurtTimer = 30;
			return super.attackEntityFrom(source, amount);
		}

		// ---- атака: яд (шанс 1/3, дольше на высокой сложности) + сильный отброс ----
		@Override
		public boolean attackEntityAsMob(Entity target) {
			if (!super.attackEntityAsMob(target))
				return false;
			if (target instanceof LivingEntity) {
				int var2 = 8;
				Difficulty difficulty = this.world.getDifficulty();
				if (difficulty == Difficulty.EASY)
					var2 = 10;
				if (difficulty == Difficulty.NORMAL)
					var2 = 12;
				else if (difficulty == Difficulty.HARD)
					var2 = 14;
				if (this.rand.nextInt(3) == 0)
					((LivingEntity) target).addPotionEffect(new EffectInstance(Effects.POISON, var2 * 20, 0));
				double ks = 1.5D;
				double inair = 0.15D;
				float f3 = (float) Math.atan2(target.getPosZ() - this.getPosZ(), target.getPosX() - this.getPosX());
				if (!target.isAlive() || target instanceof PlayerEntity)
					inair *= 2.0D;
				target.addVelocity(Math.cos(f3) * ks, inair, Math.sin(f3) * ks);
				target.velocityChanged = true;
			}
			return true;
		}

		// ---- каждый тик: регенерация, охота, "взгляд василиска" (сильное замедление цели) ----
		@Override
		public void livingTick() {
			super.livingTick();
			if (this.world.isRemote || !this.isAlive())
				return;
			if (this.hurtTimer > 0)
				this.hurtTimer--;
			if (this.rand.nextInt(200) == 0)
				this.heal(1.0F);
			if (this.rand.nextInt(75) == 1 && this.getHealth() < this.getMaxHealth())
				this.heal(1.0F);

			if (this.rand.nextInt(5) == 0) {
				LivingEntity e = findSomethingToAttack();
				if (e != null) {
					this.faceEntity(e, 10.0F, 10.0F);
					float reach = 6.0F + e.getWidth() / 2.0F;
					if (this.getDistanceSq(e) < reach * reach) {
						this.setAggroed(true);
						if (this.rand.nextInt(3) == 0 || this.rand.nextInt(4) == 1)
							this.attackEntityAsMob(e);
					} else {
						this.getNavigator().tryMoveToEntityLiving(e, 1.25D);
					}
					e.addPotionEffect(new EffectInstance(Effects.SLOWNESS, 100, 5));
				} else {
					this.setAggroed(false);
				}
			}
		}

		private boolean isSuitableTarget(LivingEntity target) {
			if (target == null || target == this || !target.isAlive())
				return false;
			if (target instanceof CustomEntity || target instanceof ArmorStandEntity)
				return false;
			if (!this.getEntitySenses().canSee(target))
				return false;
			if (target instanceof PlayerEntity && ((PlayerEntity) target).isCreative())
				return false;
			return true;
		}

		private LivingEntity findSomethingToAttack() {
			if (playNicely)
				return null;
			List<LivingEntity> list = this.world.getEntitiesWithinAABB(LivingEntity.class, this.getBoundingBox().grow(24.0D, 7.0D, 24.0D));
			list.sort((a, b) -> Double.compare(a.getDistanceSq(this), b.getDistanceSq(this)));
			for (LivingEntity e : list) {
				if (isSuitableTarget(e))
					return e;
			}
			return null;
		}

		// ---- дроп ----
		private ItemStack dropRand(Item item, int count) {
			return OreLoot.drop(this.world, this.rand, this.getPosX(), this.getPosY() + 1.0D, this.getPosZ(), 4, new ItemStack(item, count));
		}

		@Override
		protected void dropSpecialItems(DamageSource source, int looting, boolean recentlyHitIn) {
			super.dropSpecialItems(source, looting, recentlyHitIn);
			dropRand(SCALE, 1);
			dropRand(Items.ITEM_FRAME, 1);
			int n = 12 + this.rand.nextInt(6);
			for (int i = 0; i < n; i++)
				dropRand(Items.EMERALD, 1);
			n = 8 + this.rand.nextInt(5);
			for (int i = 0; i < n; i++)
				dropRand(Items.CHICKEN, 1);
			n = 3 + this.rand.nextInt(5);
			for (int i = 0; i < n; i++) {
				ItemStack is;
				switch (this.rand.nextInt(15)) {
					case 1 :
						dropRand(Items.EMERALD, 1);
						break;
					case 2 :
						dropRand(Blocks.EMERALD_BLOCK.asItem(), 1);
						break;
					case 3 :
						is = dropRand(EMERALD_SWORD, 1);
						OreLoot.sword(this.rand, is);
						break;
					case 4 :
						is = dropRand(EMERALD_SHOVEL, 1);
						OreLoot.shovelOrHoeOrAxe(this.rand, is);
						break;
					case 5 :
						is = dropRand(EMERALD_PICKAXE, 1);
						OreLoot.pickaxe(this.rand, is);
						break;
					case 6 :
						is = dropRand(EMERALD_AXE, 1);
						OreLoot.shovelOrHoeOrAxe(this.rand, is);
						break;
					case 7 :
						is = dropRand(EMERALD_HOE, 1);
						OreLoot.shovelOrHoeOrAxe(this.rand, is);
						break;
					case 8 :
						is = dropRand(EMERALD_HELMET, 1);
						OreLoot.helmet(this.rand, is);
						break;
					case 9 :
						is = dropRand(EMERALD_BODY, 1);
						OreLoot.chestOrLegs(this.rand, is);
						break;
					case 10 :
						is = dropRand(EMERALD_LEGS, 1);
						OreLoot.chestOrLegs(this.rand, is);
						break;
					case 11 :
						is = dropRand(EMERALD_BOOTS, 1);
						OreLoot.boots(this.rand, is);
						break;
					default :
						break;
				}
			}
		}
	}
}
