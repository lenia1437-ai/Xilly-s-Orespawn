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

import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.World;
import net.minecraft.world.IServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.DamageSource;
import net.minecraft.network.IPacket;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.item.Item;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
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

import net.mcreator.xillysorespawn.entity.renderer.BaryonyxRenderer;
import net.mcreator.xillysorespawn.XillysOrespawnModElements;

@XillysOrespawnModElements.ModElement.Tag
public class BaryonyxEntity extends XillysOrespawnModElements.ModElement {
	// Предмет для разведения. В оригинале это "кристальное яблоко" OreSpawn (MyCrystalApple) - здесь золотое яблоко.
	private static final Item BREEDING_ITEM = Items.GOLDEN_APPLE;

	// modid для звуков (duck_hurt). Если звука нет - используется ванильный звук получения урона.
	private static final String MODID = "xillys_orespawn";

	public static EntityType entity = (EntityType.Builder.<CustomEntity>create(CustomEntity::new, EntityClassification.CREATURE)
			.setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(CustomEntity::new)
			.size(1.5f, 2.8f)).build("baryonyx").setRegistryName("baryonyx");

	public BaryonyxEntity(XillysOrespawnModElements instance) {
		super(instance, 7);
		FMLJavaModLoadingContext.get().getModEventBus().register(new BaryonyxRenderer.ModelRegisterHandler());
		FMLJavaModLoadingContext.get().getModEventBus().register(new EntityAttributesRegisterHandler());
		MinecraftForge.EVENT_BUS.register(this);
	}

	@Override
	public void initElements() {
		elements.entities.add(() -> entity);
		elements.items.add(() -> new SpawnEggItem(entity, -1, -1, new Item.Properties().group(ItemGroup.MISC)).setRegistryName("baryonyx_spawn_egg"));
	}

	@SubscribeEvent
	public void addFeatureToBiomes(BiomeLoadingEvent event) {
	    if (!OreSpawnLogic.allowNaturalSpawn(event, "baryonyx")) return;
		event.getSpawns().getSpawner(EntityClassification.CREATURE).add(new MobSpawnInfo.Spawners(entity, 6, 2, 4));
	}

	@Override
	public void init(FMLCommonSetupEvent event) {
		EntitySpawnPlacementRegistry.register(entity, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
				BaryonyxEntity::canSpawn);
	}

	// Аналог getCanSpawnHere из 1.7.10: на траве, y >= 50, только днём и не больше 8 сородичей рядом
	public static boolean canSpawn(EntityType<? extends AnimalEntity> type, IServerWorld world, SpawnReason reason, BlockPos pos, Random random) {
		if (!AnimalEntity.canAnimalSpawn(type, world, reason, pos, random))
			return false;
		if (pos.getY() < 50)
			return false;
		if (!world.getWorld().isDaytime())
			return false;
		AxisAlignedBB area = new AxisAlignedBB(pos).grow(20.0D, 10.0D, 20.0D);
		return world.getEntitiesWithinAABB(CustomEntity.class, area, e -> true).size() <= 8;
	}

	private static class EntityAttributesRegisterHandler {
		@SubscribeEvent
		public void onEntityAttributeCreation(EntityAttributeCreationEvent event) {
			AttributeModifierMap.MutableAttribute ammma = MobEntity.func_233666_p_();
			ammma = ammma.createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.25);
			ammma = ammma.createMutableAttribute(Attributes.MAX_HEALTH, 40);
			ammma = ammma.createMutableAttribute(Attributes.ARMOR, 0);
			ammma = ammma.createMutableAttribute(Attributes.ATTACK_DAMAGE, 8);
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
		// Аналог OreSpawnMain.PlayNicely: true = не ест траву
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
			this.goalSelector.addGoal(2, new AvoidEntityGoal<MonsterEntity>(this, MonsterEntity.class, 8.0F, 1.0D, 1.4D));
			this.goalSelector.addGoal(4, new PanicGoal(this, 1.5D));
			this.goalSelector.addGoal(5, new LookAtGoal(this, PlayerEntity.class, 12.0F));
			this.goalSelector.addGoal(6, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
			this.goalSelector.addGoal(7, new LookRandomlyGoal(this));
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
			return sound("duck_hurt", "entity.generic.hurt");
		}

		@Override
		public SoundEvent getDeathSound() {
			return sound("duck_hurt", "entity.generic.death");
		}

		@Override
		protected float getSoundVolume() {
			return 0.4F;
		}

		// ---- дроп: 2-6 сырой говядины ----
		@Override
		protected void dropSpecialItems(DamageSource source, int looting, boolean recentlyHitIn) {
			super.dropSpecialItems(source, looting, recentlyHitIn);
			int n = this.rand.nextInt(5) + 2;
			for (int i = 0; i < n; i++)
				this.entityDropItem(new ItemStack(Items.BEEF));
		}

		// ---- ест траву: находит ближайший блок травы, идёт к нему, превращает в землю и лечится ----
		@Override
		public void livingTick() {
			super.livingTick();
			if (this.world.isRemote || !this.isAlive())
				return;
			if (this.rand.nextInt(200) == 1)
				this.setRevengeTarget(null);
			if (this.rand.nextInt(60) == 0 && !playNicely) {
				BlockPos origin = this.getPosition().up();
				BlockPos grass = null;
				for (int i = 1; i < 11 && grass == null; i++) {
					int j = Math.min(i, 2);
					grass = OreLoot.scanShell(this.world, origin, i, j, i, (w, p) -> w.getBlockState(p).isIn(Blocks.GRASS_BLOCK));
					if (grass == null && i >= 6)
						i++;
				}
				if (grass != null) {
					this.getNavigator().tryMoveToXYZ(grass.getX(), grass.getY(), grass.getZ(), 1.0D);
					if (origin.distanceSq(grass) < 12) {
						if (ForgeEventFactory.getMobGriefingEvent(this.world, this))
							this.world.setBlockState(grass, Blocks.DIRT.getDefaultState(), 2);
						this.heal(1.0F);
						this.playSound(SoundEvents.ENTITY_PLAYER_BURP, 1.0F, this.rand.nextFloat() * 0.2F + 0.9F);
					}
				}
			}
		}
	}
}
