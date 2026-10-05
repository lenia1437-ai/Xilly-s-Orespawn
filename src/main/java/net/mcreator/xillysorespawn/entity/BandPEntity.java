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
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.NonNullList;
import net.minecraft.util.DamageSource;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.nbt.ListNBT;
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
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.ai.goal.RandomWalkingGoal;
import net.minecraft.entity.ai.goal.OpenDoorGoal;
import net.minecraft.entity.ai.goal.MoveThroughVillageGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.CreatureAttribute;

import net.mcreator.xillysorespawn.entity.renderer.BandPRenderer;
import net.mcreator.xillysorespawn.XillysOrespawnModElements;

@XillysOrespawnModElements.ModElement.Tag
public class BandPEntity extends XillysOrespawnModElements.ModElement {
	// ВАЖНО: в старом коде эти значения брались из OreSpawnMain.BandP_stats (health / attack / defense).
	// Самого OreSpawnMain в присланном коде не было, поэтому подставь сюда свои числа.
	private static final double BANDP_HEALTH = 60.0D;
	private static final double BANDP_ATTACK = 6.0D;
	private static final double BANDP_ARMOR = 4.0D;

	public static EntityType entity = (EntityType.Builder.<CustomEntity>create(CustomEntity::new, EntityClassification.MONSTER)
			.setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(CustomEntity::new)
			.size(0.75f, 1.75f)).build("bandp").setRegistryName("bandp");

	public BandPEntity(XillysOrespawnModElements instance) {
		super(instance, 5);
		FMLJavaModLoadingContext.get().getModEventBus().register(new BandPRenderer.ModelRegisterHandler());
		FMLJavaModLoadingContext.get().getModEventBus().register(new EntityAttributesRegisterHandler());
		MinecraftForge.EVENT_BUS.register(this);
	}

	@Override
	public void initElements() {
		elements.entities.add(() -> entity);
		elements.items.add(() -> new SpawnEggItem(entity, -1, -1, new Item.Properties().group(ItemGroup.MISC)).setRegistryName("bandp_spawn_egg"));
	}

	@SubscribeEvent
	public void addFeatureToBiomes(BiomeLoadingEvent event) {
	    if (!OreSpawnLogic.allowNaturalSpawn(event, "bandp")) return;
		event.getSpawns().getSpawner(EntityClassification.MONSTER).add(new MobSpawnInfo.Spawners(entity, 3, 1, 1));
	}

	@Override
	public void init(FMLCommonSetupEvent event) {
		EntitySpawnPlacementRegistry.register(entity, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
				BandPEntity::canSpawn);
	}

	// Аналог getCanSpawnHere из 1.7.10: только днём, на высоте y >= 100, рядом должна быть деревня (житель в 36 блоках)
	// и не должно быть другого BandP в 32 блоках
	public static boolean canSpawn(EntityType<? extends MonsterEntity> type, IServerWorld world, SpawnReason reason, BlockPos pos, Random random) {
		if (reason == SpawnReason.SPAWNER)
			return true;
		if (!world.getWorld().isDaytime())
			return false;
		if (pos.getY() < 100)
			return false;
		AxisAlignedBB near = new AxisAlignedBB(pos).grow(32.0D, 12.0D, 32.0D);
		if (!world.getEntitiesWithinAABB(CustomEntity.class, near, e -> true).isEmpty())
			return false;
		AxisAlignedBB far = new AxisAlignedBB(pos).grow(36.0D, 12.0D, 36.0D);
		return !world.getEntitiesWithinAABB(VillagerEntity.class, far, e -> true).isEmpty();
	}

	private static class EntityAttributesRegisterHandler {
		@SubscribeEvent
		public void onEntityAttributeCreation(EntityAttributeCreationEvent event) {
			AttributeModifierMap.MutableAttribute ammma = MobEntity.func_233666_p_();
			ammma = ammma.createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.32);
			ammma = ammma.createMutableAttribute(Attributes.MAX_HEALTH, BANDP_HEALTH);
			ammma = ammma.createMutableAttribute(Attributes.ARMOR, BANDP_ARMOR);
			ammma = ammma.createMutableAttribute(Attributes.ATTACK_DAMAGE, BANDP_ATTACK);
			ammma = ammma.createMutableAttribute(Attributes.FOLLOW_RANGE, 20);
			event.put(entity, ammma.create());
		}
	}

	public static class CustomEntity extends MonsterEntity {
		// какой из двух "скинов" (0 или 1); -1 = ещё не выбран. Рендерер берёт по нему текстуру.
		private static final DataParameter<Integer> WHAT = EntityDataManager.createKey(CustomEntity.class, DataSerializers.VARINT);

		// Аналог OreSpawnMain.PlayNicely: true = не атакует
		public static boolean playNicely = false;

		// Украденные у игроков вещи (до 100 штук)
		private NonNullList<ItemStack> stolen = NonNullList.withSize(100, ItemStack.EMPTY);
		private int gotStuff = 0;

		public CustomEntity(FMLPlayMessages.SpawnEntity packet, World world) {
			this(entity, world);
		}

		public CustomEntity(EntityType<CustomEntity> type, World world) {
			super(type, world);
			experienceValue = 1000;
			setNoAI(false);
			((GroundPathNavigator) this.getNavigator()).setBreakDoors(true);
		}

		@Override
		public IPacket<?> createSpawnPacket() {
			return NetworkHooks.getEntitySpawningPacket(this);
		}

		@Override
		protected void registerData() {
			super.registerData();
			this.dataManager.register(WHAT, -1);
		}

		public int getWhat() {
			return Math.max(0, this.dataManager.get(WHAT));
		}

		public void setWhat(int v) {
			this.dataManager.set(WHAT, v);
		}

		@Override
		protected void registerGoals() {
			super.registerGoals();
			this.goalSelector.addGoal(0, new MoveThroughVillageGoal(this, 0.5D, false, 4, () -> true));
			this.goalSelector.addGoal(1, new RandomWalkingGoal(this, 0.5D, 16));
			this.goalSelector.addGoal(2, new LookAtGoal(this, PlayerEntity.class, 10.0F));
			this.goalSelector.addGoal(3, new LookRandomlyGoal(this));
			this.goalSelector.addGoal(4, new OpenDoorGoal(this, true));
		}

		@Override
		public CreatureAttribute getCreatureAttribute() {
			return CreatureAttribute.UNDEFINED;
		}

		// Не исчезает, если что-то украл
		@Override
		public boolean canDespawn(double distanceToClosestPlayer) {
			return this.gotStuff == 0;
		}

		// ---- звуки жителя ----
		@Override
		protected SoundEvent getAmbientSound() {
			return SoundEvents.ENTITY_VILLAGER_AMBIENT;
		}

		@Override
		public SoundEvent getHurtSound(DamageSource ds) {
			return SoundEvents.ENTITY_VILLAGER_HURT;
		}

		@Override
		public SoundEvent getDeathSound() {
			return SoundEvents.ENTITY_VILLAGER_DEATH;
		}

		@Override
		protected float getSoundVolume() {
			return 1.5F;
		}

		@Override
		public void tick() {
			super.tick();
			if (!this.world.isRemote && this.dataManager.get(WHAT) < 0)
				setWhat(this.rand.nextInt(2));
		}

		// ---- охота: жители и игроки, при ударе по игроку крадёт у него вещь ----
		@Override
		public void livingTick() {
			super.livingTick();
			if (this.world.isRemote || !this.isAlive())
				return;
			if (this.rand.nextInt(12) == 1) {
				LivingEntity e = findSomethingToAttack();
				if (e != null) {
					this.faceEntity(e, 10.0F, 10.0F);
					if (this.getDistanceSq(e) < 9.0D) {
						this.attackEntityAsMob(e);
						if (e instanceof PlayerEntity)
							steal((PlayerEntity) e);
					} else {
						this.getNavigator().tryMoveToEntityLiving(e, 1.25D);
					}
				}
			}
		}

		// Забирает у игрока последний предмет брони, а если брони нет - последний предмет из инвентаря
		private void steal(PlayerEntity p) {
			int slot = -1;
			for (int i = 0; i < this.stolen.size(); i++) {
				if (this.stolen.get(i).isEmpty()) {
					slot = i;
					break;
				}
			}
			if (slot < 0)
				return;
			for (int i = p.inventory.armorInventory.size() - 1; i >= 0; i--) {
				if (!p.inventory.armorInventory.get(i).isEmpty()) {
					this.stolen.set(slot, p.inventory.armorInventory.get(i));
					p.inventory.armorInventory.set(i, ItemStack.EMPTY);
					this.gotStuff++;
					return;
				}
			}
			for (int i = p.inventory.mainInventory.size() - 1; i >= 0; i--) {
				if (!p.inventory.mainInventory.get(i).isEmpty()) {
					this.stolen.set(slot, p.inventory.mainInventory.get(i));
					p.inventory.mainInventory.set(i, ItemStack.EMPTY);
					this.gotStuff++;
					return;
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
			return target instanceof VillagerEntity;
		}

		private LivingEntity findSomethingToAttack() {
			if (playNicely)
				return null;
			List<LivingEntity> list = this.world.getEntitiesWithinAABB(LivingEntity.class, this.getBoundingBox().grow(20.0D, 6.0D, 20.0D));
			list.sort((a, b) -> Double.compare(a.getDistanceSq(this), b.getDistanceSq(this)));
			for (LivingEntity e : list) {
				if (isSuitableTarget(e))
					return e;
			}
			return null;
		}

		// ---- дроп: изумруды, самородки, а также всё, что украл ----
		private void dropRand(Item item, int count) {
			OreLoot.drop(this.world, this.rand, this.getPosX(), this.getPosY() + 1.0D, this.getPosZ(), 2, new ItemStack(item, count));
		}

		@Override
		protected void dropSpecialItems(DamageSource source, int looting, boolean recentlyHitIn) {
			super.dropSpecialItems(source, looting, recentlyHitIn);
			int n = 10 + this.rand.nextInt(5);
			for (int i = 0; i < n; i++)
				dropRand(Items.EMERALD, 1);
			if (getWhat() == 0) {
				n = 2 + this.rand.nextInt(3);
				// В оригинале тут урановые и титановые самородки (предметы OreSpawn) - заменены на железные и золотые.
				// Подставь свои предметы, если они есть в проекте.
				for (int i = 0; i < n; i++) {
					dropRand(Items.IRON_NUGGET, 1);
					dropRand(Items.GOLD_NUGGET, 1);
				}
			}
			for (ItemStack s : this.stolen) {
				if (!s.isEmpty())
					OreLoot.drop(this.world, this.rand, this.getPosX(), this.getPosY() + 1.0D, this.getPosZ(), 2, s.copy());
			}
		}

		// ---- сохранение украденного ----
		@Override
		public void writeAdditional(CompoundNBT nbt) {
			super.writeAdditional(nbt);
			nbt.putInt("GotStuff", this.gotStuff);
			nbt.putInt("BandPWhat", this.dataManager.get(WHAT));
			if (this.gotStuff != 0) {
				ListNBT list = new ListNBT();
				for (int i = 0; i < this.stolen.size(); i++) {
					if (!this.stolen.get(i).isEmpty()) {
						CompoundNBT c = new CompoundNBT();
						c.putByte("Slot", (byte) i);
						this.stolen.get(i).write(c);
						list.add(c);
					}
				}
				nbt.put("Inventory", list);
			}
		}

		@Override
		public void readAdditional(CompoundNBT nbt) {
			super.readAdditional(nbt);
			this.gotStuff = nbt.getInt("GotStuff");
			if (nbt.contains("BandPWhat"))
				this.dataManager.set(WHAT, nbt.getInt("BandPWhat"));
			this.stolen = NonNullList.withSize(100, ItemStack.EMPTY);
			if (this.gotStuff != 0) {
				ListNBT list = nbt.getList("Inventory", 10);
				for (int i = 0; i < list.size(); i++) {
					CompoundNBT c = list.getCompound(i);
					int slot = c.getByte("Slot") & 0xFF;
					ItemStack s = ItemStack.read(c);
					if (!s.isEmpty() && slot < this.stolen.size())
						this.stolen.set(slot, s);
				}
			}
		}
	}
}
