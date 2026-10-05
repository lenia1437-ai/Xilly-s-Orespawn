
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
import net.minecraft.world.biome.Biome;
import net.minecraft.world.World;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.Difficulty;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.DamageSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.item.Item;
import net.minecraft.block.Blocks;
import net.minecraft.entity.projectile.SnowballEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.monster.SpiderEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.RandomWalkingGoal;
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

import net.mcreator.xillysorespawn.entity.renderer.AttackSquidRenderer;
import net.mcreator.xillysorespawn.XillysOrespawnModElements;

@XillysOrespawnModElements.ModElement.Tag
public class AttackSquidEntity extends XillysOrespawnModElements.ModElement {
	// ВАЖНО: в старом коде эти значения брались из OreSpawnMain.AttackSquid_stats (health / attack / defense).
	// Самого OreSpawnMain в присланном коде не было, поэтому подставь сюда свои числа.
	private static final double SQUID_HEALTH = 20.0D;
	private static final double SQUID_ATTACK = 4.0D;
	private static final double SQUID_ARMOR = 2.0D;

	// modid для звуков (squid_hurt / squid_death). Если звуков в проекте нет - используются ванильные звуки кальмара.
	private static final String MODID = "xillys_orespawn";

	public static EntityType entity = (EntityType.Builder.<CustomEntity>create(CustomEntity::new, EntityClassification.MONSTER)
			.setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(CustomEntity::new)
			.size(1.0f, 1.25f)).build("attacksquid").setRegistryName("attacksquid");

	public AttackSquidEntity(XillysOrespawnModElements instance) {
		super(instance, 4);
		FMLJavaModLoadingContext.get().getModEventBus().register(new AttackSquidRenderer.ModelRegisterHandler());
		FMLJavaModLoadingContext.get().getModEventBus().register(new EntityAttributesRegisterHandler());
		MinecraftForge.EVENT_BUS.register(this);
	}

	@Override
	public void initElements() {
		elements.entities.add(() -> entity);
		elements.items.add(() -> new SpawnEggItem(entity, -1, -1, new Item.Properties().group(ItemGroup.MISC)).setRegistryName("attacksquid_spawn_egg"));
	}

	// Появляется в океанах и реках
	@SubscribeEvent
	public void addFeatureToBiomes(BiomeLoadingEvent event) {
	    if (!OreSpawnLogic.allowNaturalSpawn(event, "attacksquid")) return;
		String biome = event.getName().getPath();
		int weight = biome.equals("river") ? 12 : biome.equals("swamp") ? 10 : 7;
		int min = biome.equals("river") ? 6 : biome.equals("swamp") ? 5 : 4;
		int max = biome.equals("river") ? 10 : biome.equals("swamp") ? 9 : 8;
		event.getSpawns().getSpawner(EntityClassification.WATER_CREATURE)
			.add(new MobSpawnInfo.Spawners(entity, weight, min, max));
	}

	@Override
	public void init(FMLCommonSetupEvent event) {
		EntitySpawnPlacementRegistry.register(entity, EntitySpawnPlacementRegistry.PlacementType.IN_WATER, Heightmap.Type.OCEAN_FLOOR,
				AttackSquidEntity::canSpawn);
	}

	// Аналог getCanSpawnHere из 1.7.10: только днём и не ниже y = 50
	public static boolean canSpawn(EntityType<? extends MonsterEntity> type, IServerWorld world, SpawnReason reason, BlockPos pos, Random random) {
		return world.getDifficulty() != Difficulty.PEACEFUL && pos.getY() >= 50 && world.getWorld().isDaytime();
	}

	private static class EntityAttributesRegisterHandler {
		@SubscribeEvent
		public void onEntityAttributeCreation(EntityAttributeCreationEvent event) {
			AttributeModifierMap.MutableAttribute ammma = MobEntity.func_233666_p_();
			ammma = ammma.createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.25);
			ammma = ammma.createMutableAttribute(Attributes.MAX_HEALTH, SQUID_HEALTH);
			ammma = ammma.createMutableAttribute(Attributes.ARMOR, SQUID_ARMOR);
			ammma = ammma.createMutableAttribute(Attributes.ATTACK_DAMAGE, SQUID_ATTACK);
			ammma = ammma.createMutableAttribute(Attributes.FOLLOW_RANGE, 10);
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

		private int wasshot = 0;
		private LivingEntity buddy = null;

		public CustomEntity(FMLPlayMessages.SpawnEntity packet, World world) {
			this(entity, world);
		}

		public CustomEntity(EntityType<CustomEntity> type, World world) {
			super(type, world);
			experienceValue = 15;
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
			this.goalSelector.addGoal(1, new RandomWalkingGoal(this, 1.0D, 16));
			this.goalSelector.addGoal(2, new LookAtGoal(this, PlayerEntity.class, 8.0F));
			this.goalSelector.addGoal(3, new LookRandomlyGoal(this));
			this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
		}

		@Override
		public CreatureAttribute getCreatureAttribute() {
			return CreatureAttribute.WATER;
		}

		// Кальмар ищет воду, поэтому под водой не тонет
		@Override
		public boolean canBreatheUnderwater() {
			return true;
		}

		// Вызывается снарядом, который "выстрелил" кальмаром: через 250 тиков он исчезает
		public void setWasShot() {
			this.wasshot = 250;
		}

		@Override
		public void writeAdditional(CompoundNBT nbt) {
			super.writeAdditional(nbt);
			nbt.putInt("WasShot", this.wasshot);
		}

		@Override
		public void readAdditional(CompoundNBT nbt) {
			super.readAdditional(nbt);
			this.wasshot = nbt.getInt("WasShot");
		}

		@Override
		public boolean onLivingFall(float distance, float damageMultiplier) {
			if (this.wasshot != 0)
				return false;
			return super.onLivingFall(distance, damageMultiplier);
		}

		// ---- звуки ----
		@Override
		protected SoundEvent getAmbientSound() {
			return null;
		}

		@Override
		public SoundEvent getHurtSound(DamageSource ds) {
			return sound("squid_hurt", "entity.squid.hurt");
		}

		@Override
		public SoundEvent getDeathSound() {
			return sound("squid_death", "entity.squid.death");
		}

		// ---- получение урона: своих не бьёт, на мобов-обидчиков реагирует ----
		@Override
		public boolean attackEntityFrom(DamageSource source, float amount) {
			if (!this.isAlive())
				return false;
			Entity e = source.getTrueSource();
			if (e instanceof CustomEntity)
				return false;
			// В оригинале при смерти от игрока с шансом 1/15 появлялись 1-3 "The Kraken" (моб из другого мода/пака) - здесь не перенесено
			if (e instanceof MobEntity) {
				this.setAttackTarget((LivingEntity) e);
				this.getNavigator().tryMoveToEntityLiving(e, 1.2D);
			}
			return super.attackEntityFrom(source, amount);
		}

		// ---- главный цикл: поиск воды, поиск цели, водяная пушка ----
		@Override
		public void livingTick() {
			super.livingTick();
			if (this.world.isRemote || !this.isAlive())
				return;

			if (this.wasshot > 0) {
				this.wasshot--;
				if (this.wasshot == 0) {
					this.remove();
					return;
				}
			}

			// Вне воды ищет ближайшую воду, а если воды нет - постепенно погибает
			if (!this.isInWater() && this.rand.nextInt(10) == 0) {
				BlockPos origin = this.getPosition().down();
				BlockPos water = null;
				for (int i = 1; i < 12 && water == null; i++) {
					int j = Math.min(i, 5);
					water = OreLoot.scanShell(this.world, origin, i, j, i, (w, p) -> w.getFluidState(p).isTagged(FluidTags.WATER));
					if (water == null && i >= 5)
						i++;
				}
				if (water != null) {
					this.getNavigator().tryMoveToXYZ(water.getX(), water.getY() - 1, water.getZ(), 1.33D);
				} else {
					if (this.rand.nextInt(25) == 1)
						this.setHealth(this.getHealth() - 1.0F);
					if (this.getHealth() <= 0.0F) {
						this.remove();
						return;
					}
				}
			}

			if (this.rand.nextInt(10) == 1) {
				LivingEntity e = findSomethingToAttack();
				if (e != null) {
					if (this.getDistanceSq(e) < 9.0D) {
						this.setAggroed(true);
						if (this.rand.nextInt(4) == 0 || this.rand.nextInt(5) == 1)
							this.attackEntityAsMob(e);
					} else {
						this.getNavigator().tryMoveToEntityLiving(e, 1.2D);
						watercanon(e);
					}
				} else {
					if (this.buddy != null)
						this.getNavigator().tryMoveToEntityLiving(this.buddy, 1.0D);
					this.setAggroed(false);
				}
			}
		}

		private boolean isSuitableTarget(LivingEntity target) {
			if (target == null || target == this || !target.isAlive())
				return false;
			if (!this.getEntitySenses().canSee(target))
				return false;
			if (target instanceof PlayerEntity)
				return !((PlayerEntity) target).isCreative();
			if (target instanceof ZombieEntity || target instanceof VillagerEntity || target instanceof SpiderEntity)
				return true;
			if (target instanceof CustomEntity) {
				if (this.rand.nextInt(5) == 1)
					this.buddy = target;
				return false;
			}
			return this.wasshot != 0;
		}

		private LivingEntity findSomethingToAttack() {
			if (playNicely)
				return null;
			LivingEntity current = this.getAttackTarget();
			if (current != null && current.isAlive())
				return current;
			this.setAttackTarget(null);
			List<LivingEntity> list = this.world.getEntitiesWithinAABB(LivingEntity.class, this.getBoundingBox().grow(10.0D, 4.0D, 10.0D));
			list.sort((a, b) -> Double.compare(a.getDistanceSq(this), b.getDistanceSq(this)));
			for (LivingEntity e : list) {
				if (isSuitableTarget(e))
					return e;
			}
			return null;
		}

		// "Водяная пушка": с шансом 1/5 за проверку стреляет снежком-"водяным шаром" или ослепляет "чернилами".
		// WaterBall и InkSack в присланном коде не было, поэтому вместо них ванильный снежок и эффект слепоты.
		private void watercanon(LivingEntity e) {
			if (this.rand.nextInt(5) != 1)
				return;
			if (this.rand.nextInt(3) == 1) {
				e.addPotionEffect(new EffectInstance(Effects.BLINDNESS, 100, 0));
			} else {
				SnowballEntity ball = new SnowballEntity(this.world, this);
				double dx = e.getPosX() - this.getPosX();
				double dy = e.getPosYHeight(0.3333333333333333D) - ball.getPosY();
				double dz = e.getPosZ() - this.getPosZ();
				float h = MathHelper.sqrt(dx * dx + dz * dz) * 0.2F;
				ball.shoot(dx, dy + h, dz, 1.4F, 5.0F);
				this.world.addEntity(ball);
			}
			this.world.playSound(null, this.getPosX(), this.getPosY(), this.getPosZ(), SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.HOSTILE, 0.75F,
					1.0F / (this.rand.nextFloat() * 0.4F + 0.8F));
		}

		// ---- дроп: случайная золотая добыча (с зачарованиями) + 1-3 сырой рыбы ----
		private ItemStack dropRand(Item item, int count, int spread) {
			return OreLoot.drop(this.world, this.rand, this.getPosX(), this.getPosY() + 1.0D, this.getPosZ(), spread, new ItemStack(item, count));
		}

		@Override
		protected void dropSpecialItems(DamageSource source, int looting, boolean recentlyHitIn) {
			super.dropSpecialItems(source, looting, recentlyHitIn);
			ItemStack is;
			switch (this.rand.nextInt(50)) {
				case 0 :
					dropRand(Items.GOLD_NUGGET, 1, 2);
					break;
				case 1 :
					dropRand(Items.GOLD_INGOT, 1, 2);
					break;
				case 2 :
					dropRand(Items.GOLDEN_CARROT, 1, 2);
					break;
				case 3 :
					is = dropRand(Items.GOLDEN_SWORD, 1, 2);
					OreLoot.sword(this.rand, is);
					break;
				case 4 :
					is = dropRand(Items.GOLDEN_SHOVEL, 1, 2);
					OreLoot.shovelOrHoeOrAxe(this.rand, is);
					break;
				case 5 :
					is = dropRand(Items.GOLDEN_PICKAXE, 1, 2);
					OreLoot.pickaxe(this.rand, is);
					break;
				case 6 :
					is = dropRand(Items.GOLDEN_AXE, 1, 2);
					OreLoot.shovelOrHoeOrAxe(this.rand, is);
					break;
				case 7 :
					is = dropRand(Items.GOLDEN_HOE, 1, 2);
					OreLoot.shovelOrHoeOrAxe(this.rand, is);
					break;
				case 8 :
					is = dropRand(Items.GOLDEN_HELMET, 1, 2);
					OreLoot.helmet(this.rand, is);
					break;
				case 9 :
					is = dropRand(Items.GOLDEN_CHESTPLATE, 1, 2);
					OreLoot.chestOrLegs(this.rand, is);
					break;
				case 10 :
					is = dropRand(Items.GOLDEN_LEGGINGS, 1, 2);
					OreLoot.chestOrLegs(this.rand, is);
					break;
				case 11 :
					is = dropRand(Items.GOLDEN_BOOTS, 1, 2);
					OreLoot.boots(this.rand, is);
					break;
				case 12 :
					dropRand(Items.GOLDEN_APPLE, 1, 2);
					break;
				case 13 :
					dropRand(Blocks.GOLD_BLOCK.asItem(), 1, 2);
					break;
				case 14 :
					dropRand(Items.ENCHANTED_GOLDEN_APPLE, 1, 3);
					break;
				case 15 :
				case 16 :
				case 17 :
					dropRand(Items.INK_SAC, 1, 2);
					break;
				default :
					break;
			}
			int fish = 1 + this.rand.nextInt(3);
			for (int i = 0; i < fish; i++)
				dropRand(Items.COD, 1, 2);
		}
	}
}
