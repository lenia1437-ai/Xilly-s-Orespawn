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
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.common.MinecraftForge;

import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.World;
import net.minecraft.world.IServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.DamageSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.network.IPacket;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.item.Item;
import net.minecraft.block.Blocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.RandomWalkingGoal;
import net.minecraft.entity.ai.goal.PanicGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.BreedGoal;
import net.minecraft.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.item.ItemEntity;

import net.mcreator.xillysorespawn.entity.renderer.BeaverRenderer;
import net.mcreator.xillysorespawn.XillysOrespawnModElements;

@XillysOrespawnModElements.ModElement.Tag
public class BeaverEntity extends XillysOrespawnModElements.ModElement {
	// Предмет для разведения. В оригинале это "кристальное яблоко" OreSpawn (MyCrystalApple) - здесь золотое яблоко.
	private static final Item BREEDING_ITEM = Items.GOLDEN_APPLE;

	// modid для звуков (scorpion_hit / cryo_death / chainsaw). Если звуков нет - используются ванильные.
	private static final String MODID = "xillys_orespawn";

	public static EntityType entity = (EntityType.Builder.<CustomEntity>create(CustomEntity::new, EntityClassification.CREATURE)
			.setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(CustomEntity::new)
			.size(0.6f, 0.8f)).build("beaver").setRegistryName("beaver");

	public BeaverEntity(XillysOrespawnModElements instance) {
		super(instance, 8);
		FMLJavaModLoadingContext.get().getModEventBus().register(new BeaverRenderer.ModelRegisterHandler());
		FMLJavaModLoadingContext.get().getModEventBus().register(new EntityAttributesRegisterHandler());
		MinecraftForge.EVENT_BUS.register(this);
	}

	@Override
	public void initElements() {
		elements.entities.add(() -> entity);
		elements.items.add(() -> new SpawnEggItem(entity, -1, -1, new Item.Properties().group(ItemGroup.MISC)).setRegistryName("beaver_spawn_egg"));
	}

	@SubscribeEvent
	public void addFeatureToBiomes(BiomeLoadingEvent event) {
	    if (!OreSpawnLogic.allowNaturalSpawn(event, "beaver")) return;
		event.getSpawns().getSpawner(EntityClassification.CREATURE).add(new MobSpawnInfo.Spawners(entity, 8, 2, 4));
	}

	@Override
	public void init(FMLCommonSetupEvent event) {
		EntitySpawnPlacementRegistry.register(entity, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
				BeaverEntity::canSpawn);
	}

	// Аналог getCanSpawnHere из 1.7.10: высота 50-100, под ногами земля / трава / листва
	public static boolean canSpawn(EntityType<? extends AnimalEntity> type, IServerWorld world, SpawnReason reason, BlockPos pos, Random random) {
		if (pos.getY() < 50 || pos.getY() > 100)
			return false;
		BlockState below = world.getBlockState(pos.down());
		return below.isIn(Blocks.DIRT) || below.isIn(Blocks.GRASS_BLOCK) || below.isIn(BlockTags.LEAVES);
	}

	private static class EntityAttributesRegisterHandler {
		@SubscribeEvent
		public void onEntityAttributeCreation(EntityAttributeCreationEvent event) {
			AttributeModifierMap.MutableAttribute ammma = MobEntity.func_233666_p_();
			ammma = ammma.createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.2);
			ammma = ammma.createMutableAttribute(Attributes.MAX_HEALTH, 15);
			ammma = ammma.createMutableAttribute(Attributes.ARMOR, 0);
			ammma = ammma.createMutableAttribute(Attributes.ATTACK_DAMAGE, 1);
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

	public static class CustomEntity extends AnimalEntity {
		// Аналог OreSpawnMain.PlayNicely: true = не валит деревья
		public static boolean playNicely = false;

		public CustomEntity(FMLPlayMessages.SpawnEntity packet, World world) {
			this(entity, world);
		}

		public CustomEntity(EntityType<CustomEntity> type, World world) {
			super(type, world);
			experienceValue = 5;
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
			this.goalSelector.addGoal(1, new BreedGoal(this, 1.0D));
			this.goalSelector.addGoal(2, new AvoidEntityGoal<MonsterEntity>(this, MonsterEntity.class, 8.0F, 1.0D, 1.5D));
			this.goalSelector.addGoal(4, new PanicGoal(this, 1.5D));
			this.goalSelector.addGoal(5, new AvoidEntityGoal<PlayerEntity>(this, PlayerEntity.class, 8.0F, 1.0D, 1.5D));
			this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 6.0F));
			this.goalSelector.addGoal(7, new RandomWalkingGoal(this, 1.0D, 10));
			this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
		}

		@Override
		public boolean canBreatheUnderwater() {
			return true;
		}

		@Override
		public boolean canDespawn(double distanceToClosestPlayer) {
			return false;
		}

		// ---- размножение ----
		@Override
		public AgeableEntity func_241840_a(ServerWorld serverWorld, AgeableEntity ageable) {
		    return (CustomEntity) entity.create(serverWorld);
		}

		@Override
		public boolean isBreedingItem(ItemStack stack) {
			return stack.getItem() == BREEDING_ITEM;
		}

		// ---- звуки ----
		@Override
		protected SoundEvent getAmbientSound() {
			return null;
		}

		@Override
		public SoundEvent getHurtSound(DamageSource ds) {
			return sound("scorpion_hit", "entity.generic.hurt");
		}

		@Override
		public SoundEvent getDeathSound() {
			return sound("cryo_death", "entity.generic.death");
		}

		@Override
		protected float getSoundVolume() {
			return 0.4F;
		}

		// ---- дроп: 0-2 сырой свинины ----
		@Override
		protected void dropSpecialItems(DamageSource source, int looting, boolean recentlyHitIn) {
			super.dropSpecialItems(source, looting, recentlyHitIn);
			int n = this.rand.nextInt(3);
			if (looting > 0)
				n += this.rand.nextInt(looting + 1);
			for (int i = 0; i < n; i++)
				this.entityDropItem(new ItemStack(Items.PORKCHOP));
		}

		// ---- что считается "деревом": брёвна, деревянные заборы, калитки и таблички ----
		public static boolean isWood(BlockState s) {
			return s.isIn(BlockTags.LOGS_THAT_BURN) || s.isIn(BlockTags.WOODEN_FENCES) || s.isIn(BlockTags.FENCE_GATES)
					|| s.isIn(BlockTags.STANDING_SIGNS) || s.isIn(BlockTags.WALL_SIGNS);
		}

		private void dropWood(World world, BlockPos p, Block block) {
			ItemEntity drop = new ItemEntity(world, p.getX() + this.rand.nextInt(4) - this.rand.nextInt(4), p.getY() + 4.0D + this.rand.nextInt(4),
					p.getZ() + this.rand.nextInt(4) - this.rand.nextInt(4), new ItemStack(block.asItem(), 1));
			world.addEntity(drop);
		}

		// Валит всё дерево: рекурсивно ломает соседние деревянные блоки (до 200 уровней вглубь)
		private void breakRecursor(World world, int x, int y, int z, int xf, int yf, int zf, int recursion) {
			int r = 1;
			if (recursion > 200)
				return;
			for (int dx = -r; dx <= r; dx++) {
				for (int dy = -r; dy <= r; dy++) {
					for (int dz = -r; dz <= r; dz++) {
						if (dx == 0 && dy == 0 && dz == 0)
							continue;
						if (x + dx == xf && y + dy == yf && z + dz == zf)
							continue;
						if (recursion > 0 && x + dx >= xf - r && x + dx <= xf + r && y + dy >= yf - r && y + dy <= yf + r && z + dz >= zf - r
								&& z + dz <= zf + r)
							continue;
						BlockPos p = new BlockPos(x + dx, y + dy, z + dz);
						BlockState state = world.getBlockState(p);
						if (isWood(state)) {
							world.setBlockState(p, Blocks.AIR.getDefaultState(), 2);
							dropWood(world, p, state.getBlock());
							breakRecursor(world, p.getX(), p.getY(), p.getZ(), x, y, z, recursion + 1);
						}
					}
				}
			}
		}

		private CustomEntity findBuddy() {
			List<CustomEntity> list = this.world.getEntitiesWithinAABB(CustomEntity.class, this.getBoundingBox().grow(16.0D, 6.0D, 16.0D));
			list.sort((a, b) -> Double.compare(a.getDistanceSq(this), b.getDistanceSq(this)));
			for (CustomEntity e : list) {
				if (e != this)
					return e;
			}
			return null;
		}

		@Override
		public void livingTick() {
			super.livingTick();
			if (this.world.isRemote || !this.isAlive())
				return;
			if (this.rand.nextInt(200) == 1)
				this.setRevengeTarget(null);

			// Когда ранен (или просто иногда) - идёт к ближайшему дереву и валит его, лечась при этом
			if ((this.rand.nextInt(30) == 0 && this.getHealth() < this.getMaxHealth()) || this.rand.nextInt(350) == 1) {
				if (!playNicely) {
					BlockPos origin = this.getPosition().up();
					BlockPos wood = null;
					for (int i = 1; i < 11 && wood == null; i++) {
						int j = Math.min(i, 2);
						wood = OreLoot.scanShell(this.world, origin, i, j, i, (w, p) -> isWood(w.getBlockState(p)));
						if (wood == null && i >= 6)
							i++;
					}
					if (wood != null) {
						this.getNavigator().tryMoveToXYZ(wood.getX(), wood.getY(), wood.getZ(), 1.0D);
						if (origin.distanceSq(wood) < 12) {
							if (ForgeEventFactory.getMobGriefingEvent(this.world, this)) {
								this.world.setBlockState(wood, Blocks.AIR.getDefaultState(), 2);
								breakRecursor(this.world, wood.getX(), wood.getY(), wood.getZ(), wood.getX(), wood.getY(), wood.getZ(), 0);
							}
							this.heal(1.0F);
							SoundEvent s = sound("chainsaw", "block.wood.break");
							if (s != null)
								this.playSound(s, 1.0F, this.rand.nextFloat() * 0.2F + 0.9F);
						}
					}
				}
			}

			// Иногда идёт к ближайшему сородичу
			if (this.rand.nextInt(200) == 1) {
				CustomEntity buddy = findBuddy();
				if (buddy != null)
					this.getNavigator().tryMoveToXYZ(buddy.getPosX(), buddy.getPosY(), buddy.getPosZ(), 0.5D);
			}
		}
	}
}
