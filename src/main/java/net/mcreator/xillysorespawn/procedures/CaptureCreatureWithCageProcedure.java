package net.mcreator.xillysorespawn.procedures;

import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

import net.minecraft.world.IWorld;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.passive.horse.HorseEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.passive.SquidEntity;
import net.minecraft.entity.passive.SnowGolemEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.entity.passive.OcelotEntity;
import net.minecraft.entity.passive.MooshroomEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.passive.BatEntity;
import net.minecraft.entity.monster.ZombifiedPiglinEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.monster.WitherSkeletonEntity;
import net.minecraft.entity.monster.WitchEntity;
import net.minecraft.entity.monster.SpiderEntity;
import net.minecraft.entity.monster.SlimeEntity;
import net.minecraft.entity.monster.SkeletonEntity;
import net.minecraft.entity.monster.SilverfishEntity;
import net.minecraft.entity.monster.MagmaCubeEntity;
import net.minecraft.entity.monster.GhastEntity;
import net.minecraft.entity.monster.EndermanEntity;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.monster.CaveSpiderEntity;
import net.minecraft.entity.monster.BlazeEntity;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Entity;

import net.mcreator.xillysorespawn.item.CageZombiepigmanItem;
import net.mcreator.xillysorespawn.item.CageZombieItem;
import net.mcreator.xillysorespawn.item.CageWolfItem;
import net.mcreator.xillysorespawn.item.CageWitherskeletonItem;
import net.mcreator.xillysorespawn.item.CageWitherbossItem;
import net.mcreator.xillysorespawn.item.CageWitchItem;
import net.mcreator.xillysorespawn.item.CageWhaleItem;
import net.mcreator.xillysorespawn.item.CageWaterdragonItem;
import net.mcreator.xillysorespawn.item.CageVortexItem;
import net.mcreator.xillysorespawn.item.CageVillagerItem;
import net.mcreator.xillysorespawn.item.CageVelocityraptorItem;
import net.mcreator.xillysorespawn.item.CageUrchinItem;
import net.mcreator.xillysorespawn.item.CageTrooperItem;
import net.mcreator.xillysorespawn.item.CageTriffidItem;
import net.mcreator.xillysorespawn.item.CageTerribleterrorItem;
import net.mcreator.xillysorespawn.item.CageTRexItem;
import net.mcreator.xillysorespawn.item.CageStinkyItem;
import net.mcreator.xillysorespawn.item.CageStinkItem;
import net.mcreator.xillysorespawn.item.CageSquidItem;
import net.mcreator.xillysorespawn.item.CageSpyroItem;
import net.mcreator.xillysorespawn.item.CageSpitItem;
import net.mcreator.xillysorespawn.item.CageSpiderdriverItem;
import net.mcreator.xillysorespawn.item.CageSpiderItem;
import net.mcreator.xillysorespawn.item.CageSnowgolemItem;
import net.mcreator.xillysorespawn.item.CageSmallwormItem;
import net.mcreator.xillysorespawn.item.CageSlimeItem;
import net.mcreator.xillysorespawn.item.CageSkeletonItem;
import net.mcreator.xillysorespawn.item.CageSkateItem;
import net.mcreator.xillysorespawn.item.CageSilverfishItem;
import net.mcreator.xillysorespawn.item.CageSheepItem;
import net.mcreator.xillysorespawn.item.CageSeaviperItem;
import net.mcreator.xillysorespawn.item.CageSeamonsterItem;
import net.mcreator.xillysorespawn.item.CageScorpionItem;
import net.mcreator.xillysorespawn.item.CageRubberduckyItem;
import net.mcreator.xillysorespawn.item.CageRotatorItem;
import net.mcreator.xillysorespawn.item.CageRedcowItem;
import net.mcreator.xillysorespawn.item.CageRatItem;
import net.mcreator.xillysorespawn.item.CagePointysaurusItem;
import net.mcreator.xillysorespawn.item.CagePigItem;
import net.mcreator.xillysorespawn.item.CagePeacockItem;
import net.mcreator.xillysorespawn.item.CageOstrichItem;
import net.mcreator.xillysorespawn.item.CageOcelotItem;
import net.mcreator.xillysorespawn.item.CageNightmareItem;
import net.mcreator.xillysorespawn.item.CageNastysaurusItem;
import net.mcreator.xillysorespawn.item.CageMothraItem;
import net.mcreator.xillysorespawn.item.CageMooshroomItem;
import net.mcreator.xillysorespawn.item.CageMolenoidItem;
import net.mcreator.xillysorespawn.item.CageMediumwormItem;
import net.mcreator.xillysorespawn.item.CageMantisItem;
import net.mcreator.xillysorespawn.item.CageMagmacubeItem;
import net.mcreator.xillysorespawn.item.CageLurkingterrorItem;
import net.mcreator.xillysorespawn.item.CageLizardItem;
import net.mcreator.xillysorespawn.item.CageLeonItem;
import net.mcreator.xillysorespawn.item.CageLeafmonsterItem;
import net.mcreator.xillysorespawn.item.CageLargewormItem;
import net.mcreator.xillysorespawn.item.CageKyuubiItem;
import net.mcreator.xillysorespawn.item.CageKrakenItem;
import net.mcreator.xillysorespawn.item.CageIrukandjiItem;
import net.mcreator.xillysorespawn.item.CageIrongolemItem;
import net.mcreator.xillysorespawn.item.CageHydroliscItem;
import net.mcreator.xillysorespawn.item.CageHorseItem;
import net.mcreator.xillysorespawn.item.CageHerculesItem;
import net.mcreator.xillysorespawn.item.CageHammerheadItem;
import net.mcreator.xillysorespawn.item.CageGoldfishItem;
import net.mcreator.xillysorespawn.item.CageGoldcowItem;
import net.mcreator.xillysorespawn.item.CageGirlfriendItem;
import net.mcreator.xillysorespawn.item.CageGhastItem;
import net.mcreator.xillysorespawn.item.CageGazelleItem;
import net.mcreator.xillysorespawn.item.CageGammaMetroidItem;
import net.mcreator.xillysorespawn.item.CageFrogItem;
import net.mcreator.xillysorespawn.item.CageFlounderItem;
import net.mcreator.xillysorespawn.item.CageFireflyItem;
import net.mcreator.xillysorespawn.item.CageFairyItem;
import net.mcreator.xillysorespawn.item.CageEnderreaperItem;
import net.mcreator.xillysorespawn.item.CageEndermanItem;
import net.mcreator.xillysorespawn.item.CageEnderknightItem;
import net.mcreator.xillysorespawn.item.CageEnderdragonItem;
import net.mcreator.xillysorespawn.item.CageEnchantedcowItem;
import net.mcreator.xillysorespawn.item.CageEmptyItem;
import net.mcreator.xillysorespawn.item.CageEmperorscorpionItem;
import net.mcreator.xillysorespawn.item.CageEasterbunnyItem;
import net.mcreator.xillysorespawn.item.CageDungeonbeastItem;
import net.mcreator.xillysorespawn.item.CageDragonflyItem;
import net.mcreator.xillysorespawn.item.CageDragonItem;
import net.mcreator.xillysorespawn.item.CageCrystalcowItem;
import net.mcreator.xillysorespawn.item.CageCryolophosaurusItem;
import net.mcreator.xillysorespawn.item.CageCricketItem;
import net.mcreator.xillysorespawn.item.CageCreepinghorrorItem;
import net.mcreator.xillysorespawn.item.CageCreeperItem;
import net.mcreator.xillysorespawn.item.CageCrabItem;
import net.mcreator.xillysorespawn.item.CageCowItem;
import net.mcreator.xillysorespawn.item.CageCockateilItem;
import net.mcreator.xillysorespawn.item.CageCloudsharkItem;
import net.mcreator.xillysorespawn.item.CageCliffracerItem;
import net.mcreator.xillysorespawn.item.CageChipmunkItem;
import net.mcreator.xillysorespawn.item.CageChickenItem;
import net.mcreator.xillysorespawn.item.CageCephadromeItem;
import net.mcreator.xillysorespawn.item.CageCavespiderItem;
import net.mcreator.xillysorespawn.item.CageCavefisherItem;
import net.mcreator.xillysorespawn.item.CageCaterkillerItem;
import net.mcreator.xillysorespawn.item.CageCassowaryItem;
import net.mcreator.xillysorespawn.item.CageCamarasaurusItem;
import net.mcreator.xillysorespawn.item.CageBrutalflyItem;
import net.mcreator.xillysorespawn.item.CageBoyfriendItem;
import net.mcreator.xillysorespawn.item.CageBlazeItem;
import net.mcreator.xillysorespawn.item.CageBeeItem;
import net.mcreator.xillysorespawn.item.CageBeaverItem;
import net.mcreator.xillysorespawn.item.CageBatItem;
import net.mcreator.xillysorespawn.item.CageBasiliscItem;
import net.mcreator.xillysorespawn.item.CageBaryonyxItem;
import net.mcreator.xillysorespawn.item.CageAttacksquidItem;
import net.mcreator.xillysorespawn.item.CageAlosaurusItem;
import net.mcreator.xillysorespawn.item.CageAlienItem;
import net.mcreator.xillysorespawn.entity.WormSmallEntity;
import net.mcreator.xillysorespawn.entity.WormMediumEntity;
import net.mcreator.xillysorespawn.entity.WormLargeEntity;
import net.mcreator.xillysorespawn.entity.WhaleEntity;
import net.mcreator.xillysorespawn.entity.WaterDragonEntity;
import net.mcreator.xillysorespawn.entity.VortexEntity;
import net.mcreator.xillysorespawn.entity.VelocityRaptorEntity;
import net.mcreator.xillysorespawn.entity.UrchinEntity;
import net.mcreator.xillysorespawn.entity.TrooperBugEntity;
import net.mcreator.xillysorespawn.entity.TriffidEntity;
import net.mcreator.xillysorespawn.entity.TerribleTerrorEntity;
import net.mcreator.xillysorespawn.entity.TRexEntity;
import net.mcreator.xillysorespawn.entity.StinkyEntity;
import net.mcreator.xillysorespawn.entity.StinkBugEntity;
import net.mcreator.xillysorespawn.entity.SpyroEntity;
import net.mcreator.xillysorespawn.entity.SpitBugEntity;
import net.mcreator.xillysorespawn.entity.SpiderRobotEntity;
import net.mcreator.xillysorespawn.entity.SkateEntity;
import net.mcreator.xillysorespawn.entity.SeaViperEntity;
import net.mcreator.xillysorespawn.entity.SeaMonsterEntity;
import net.mcreator.xillysorespawn.entity.ScorpionEntity;
import net.mcreator.xillysorespawn.entity.RubberDuckyEntity;
import net.mcreator.xillysorespawn.entity.RotatorEntity;
import net.mcreator.xillysorespawn.entity.RedCowEntity;
import net.mcreator.xillysorespawn.entity.RatEntity;
import net.mcreator.xillysorespawn.entity.PointysaurusEntity;
import net.mcreator.xillysorespawn.entity.PitchBlackEntity;
import net.mcreator.xillysorespawn.entity.PeacockEntity;
import net.mcreator.xillysorespawn.entity.OstrichEntity;
import net.mcreator.xillysorespawn.entity.NastysaurusEntity;
import net.mcreator.xillysorespawn.entity.MothraEntity;
import net.mcreator.xillysorespawn.entity.MolenoidEntity;
import net.mcreator.xillysorespawn.entity.MantisEntity;
import net.mcreator.xillysorespawn.entity.LurkingTerrorEntity;
import net.mcreator.xillysorespawn.entity.LizardEntity;
import net.mcreator.xillysorespawn.entity.LeonEntity;
import net.mcreator.xillysorespawn.entity.LeafMonsterEntity;
import net.mcreator.xillysorespawn.entity.KyuubiEntity;
import net.mcreator.xillysorespawn.entity.KrakenEntity;
import net.mcreator.xillysorespawn.entity.IrukandjiEntity;
import net.mcreator.xillysorespawn.entity.HydroliscEntity;
import net.mcreator.xillysorespawn.entity.HerculesBeetleEntity;
import net.mcreator.xillysorespawn.entity.HammerheadEntity;
import net.mcreator.xillysorespawn.entity.GoldFishEntity;
import net.mcreator.xillysorespawn.entity.GoldCowEntity;
import net.mcreator.xillysorespawn.entity.GirlfriendEntity;
import net.mcreator.xillysorespawn.entity.GazelleEntity;
import net.mcreator.xillysorespawn.entity.GammaMetroidEntity;
import net.mcreator.xillysorespawn.entity.FrogEntity;
import net.mcreator.xillysorespawn.entity.FlounderEntity;
import net.mcreator.xillysorespawn.entity.FireflyEntity;
import net.mcreator.xillysorespawn.entity.FairyEntity;
import net.mcreator.xillysorespawn.entity.EnderReaperEntity;
import net.mcreator.xillysorespawn.entity.EnderKnightEntity;
import net.mcreator.xillysorespawn.entity.EnchantedCowEntity;
import net.mcreator.xillysorespawn.entity.EmperorScorpionEntity;
import net.mcreator.xillysorespawn.entity.EasterBunnyEntity;
import net.mcreator.xillysorespawn.entity.DungeonBeastEntity;
import net.mcreator.xillysorespawn.entity.DragonflyEntity;
import net.mcreator.xillysorespawn.entity.DragonEntity;
import net.mcreator.xillysorespawn.entity.CrystalCowEntity;
import net.mcreator.xillysorespawn.entity.CryolophosaurusEntity;
import net.mcreator.xillysorespawn.entity.CricketEntity;
import net.mcreator.xillysorespawn.entity.CreepingHorrorEntity;
import net.mcreator.xillysorespawn.entity.CrabEntity;
import net.mcreator.xillysorespawn.entity.CockateilEntity;
import net.mcreator.xillysorespawn.entity.CloudSharkEntity;
import net.mcreator.xillysorespawn.entity.CliffRacerEntity;
import net.mcreator.xillysorespawn.entity.ChipmunkEntity;
import net.mcreator.xillysorespawn.entity.CephadromeEntity;
import net.mcreator.xillysorespawn.entity.CaveFisherEntity;
import net.mcreator.xillysorespawn.entity.CaterKillerEntity;
import net.mcreator.xillysorespawn.entity.CassowaryEntity;
import net.mcreator.xillysorespawn.entity.CamarasaurusEntity;
import net.mcreator.xillysorespawn.entity.BrutalflyEntity;
import net.mcreator.xillysorespawn.entity.BoyfriendEntity;
import net.mcreator.xillysorespawn.entity.BeeEntity;
import net.mcreator.xillysorespawn.entity.BeaverEntity;
import net.mcreator.xillysorespawn.entity.BasiliskEntity;
import net.mcreator.xillysorespawn.entity.BaryonyxEntity;
import net.mcreator.xillysorespawn.entity.AttackSquidEntity;
import net.mcreator.xillysorespawn.entity.AlosaurusEntity;
import net.mcreator.xillysorespawn.entity.AlienEntity;
import net.mcreator.xillysorespawn.XillysOrespawnMod;

import java.util.Map;
import java.util.HashMap;

public class CaptureCreatureWithCageProcedure {
	@Mod.EventBusSubscriber
	private static class GlobalTrigger {
		@SubscribeEvent
		public static void onRightClickEntity(PlayerInteractEvent.EntityInteract event) {
			Entity entity = event.getTarget();
			PlayerEntity sourceentity = event.getPlayer();
			if (event.getHand() != sourceentity.getActiveHand()) {
				return;
			}
			double i = event.getPos().getX();
			double j = event.getPos().getY();
			double k = event.getPos().getZ();
			IWorld world = event.getWorld();
			Map<String, Object> dependencies = new HashMap<>();
			dependencies.put("x", i);
			dependencies.put("y", j);
			dependencies.put("z", k);
			dependencies.put("world", world);
			dependencies.put("entity", entity);
			dependencies.put("sourceentity", sourceentity);
			dependencies.put("event", event);
			executeProcedure(dependencies);
		}
	}

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				XillysOrespawnMod.LOGGER.warn("Failed to load dependency entity for procedure CaptureCreatureWithCage!");
			return;
		}
		if (dependencies.get("sourceentity") == null) {
			if (!dependencies.containsKey("sourceentity"))
				XillysOrespawnMod.LOGGER.warn("Failed to load dependency sourceentity for procedure CaptureCreatureWithCage!");
			return;
		}
		Entity entity = (Entity) dependencies.get("entity");
		Entity sourceentity = (Entity) dependencies.get("sourceentity");
		if (((sourceentity instanceof LivingEntity) ? ((LivingEntity) sourceentity).getHeldItemMainhand() : ItemStack.EMPTY)
				.getItem() == CageEmptyItem.block) {
			if (entity instanceof AlienEntity.CustomEntity) {
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _stktoremove = new ItemStack(CageEmptyItem.block);
					((PlayerEntity) sourceentity).inventory.func_234564_a_(p -> _stktoremove.getItem() == p.getItem(), (int) 1,
							((PlayerEntity) sourceentity).container.func_234641_j_());
				}
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _setstack = new ItemStack(CageAlienItem.block);
					_setstack.setCount((int) 1);
					ItemHandlerHelper.giveItemToPlayer(((PlayerEntity) sourceentity), _setstack);
				}
				if (!entity.world.isRemote())
					entity.remove();
			}
			if (entity instanceof AlosaurusEntity.CustomEntity) {
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _stktoremove = new ItemStack(CageEmptyItem.block);
					((PlayerEntity) sourceentity).inventory.func_234564_a_(p -> _stktoremove.getItem() == p.getItem(), (int) 1,
							((PlayerEntity) sourceentity).container.func_234641_j_());
				}
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _setstack = new ItemStack(CageAlosaurusItem.block);
					_setstack.setCount((int) 1);
					ItemHandlerHelper.giveItemToPlayer(((PlayerEntity) sourceentity), _setstack);
				}
				if (!entity.world.isRemote())
					entity.remove();
			}
			if (entity instanceof AttackSquidEntity.CustomEntity) {
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _stktoremove = new ItemStack(CageEmptyItem.block);
					((PlayerEntity) sourceentity).inventory.func_234564_a_(p -> _stktoremove.getItem() == p.getItem(), (int) 1,
							((PlayerEntity) sourceentity).container.func_234641_j_());
				}
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _setstack = new ItemStack(CageAttacksquidItem.block);
					_setstack.setCount((int) 1);
					ItemHandlerHelper.giveItemToPlayer(((PlayerEntity) sourceentity), _setstack);
				}
				if (!entity.world.isRemote())
					entity.remove();
			}
			if (entity instanceof BaryonyxEntity.CustomEntity) {
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _stktoremove = new ItemStack(CageEmptyItem.block);
					((PlayerEntity) sourceentity).inventory.func_234564_a_(p -> _stktoremove.getItem() == p.getItem(), (int) 1,
							((PlayerEntity) sourceentity).container.func_234641_j_());
				}
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _setstack = new ItemStack(CageBaryonyxItem.block);
					_setstack.setCount((int) 1);
					ItemHandlerHelper.giveItemToPlayer(((PlayerEntity) sourceentity), _setstack);
				}
				if (!entity.world.isRemote())
					entity.remove();
			}
			if (entity instanceof BasiliskEntity.CustomEntity) {
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _stktoremove = new ItemStack(CageEmptyItem.block);
					((PlayerEntity) sourceentity).inventory.func_234564_a_(p -> _stktoremove.getItem() == p.getItem(), (int) 1,
							((PlayerEntity) sourceentity).container.func_234641_j_());
				}
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _setstack = new ItemStack(CageBasiliscItem.block);
					_setstack.setCount((int) 1);
					ItemHandlerHelper.giveItemToPlayer(((PlayerEntity) sourceentity), _setstack);
				}
				if (!entity.world.isRemote())
					entity.remove();
			}
			if (entity instanceof BatEntity) {
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _stktoremove = new ItemStack(CageEmptyItem.block);
					((PlayerEntity) sourceentity).inventory.func_234564_a_(p -> _stktoremove.getItem() == p.getItem(), (int) 1,
							((PlayerEntity) sourceentity).container.func_234641_j_());
				}
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _setstack = new ItemStack(CageBatItem.block);
					_setstack.setCount((int) 1);
					ItemHandlerHelper.giveItemToPlayer(((PlayerEntity) sourceentity), _setstack);
				}
				if (!entity.world.isRemote())
					entity.remove();
			}
			if (entity instanceof BeaverEntity.CustomEntity) {
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _stktoremove = new ItemStack(CageEmptyItem.block);
					((PlayerEntity) sourceentity).inventory.func_234564_a_(p -> _stktoremove.getItem() == p.getItem(), (int) 1,
							((PlayerEntity) sourceentity).container.func_234641_j_());
				}
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _setstack = new ItemStack(CageBeaverItem.block);
					_setstack.setCount((int) 1);
					ItemHandlerHelper.giveItemToPlayer(((PlayerEntity) sourceentity), _setstack);
				}
				if (!entity.world.isRemote())
					entity.remove();
			}
			if (entity instanceof BeeEntity.CustomEntity) {
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _stktoremove = new ItemStack(CageEmptyItem.block);
					((PlayerEntity) sourceentity).inventory.func_234564_a_(p -> _stktoremove.getItem() == p.getItem(), (int) 1,
							((PlayerEntity) sourceentity).container.func_234641_j_());
				}
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _setstack = new ItemStack(CageBeeItem.block);
					_setstack.setCount((int) 1);
					ItemHandlerHelper.giveItemToPlayer(((PlayerEntity) sourceentity), _setstack);
				}
				if (!entity.world.isRemote())
					entity.remove();
			}
			if (entity instanceof BlazeEntity) {
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _stktoremove = new ItemStack(CageEmptyItem.block);
					((PlayerEntity) sourceentity).inventory.func_234564_a_(p -> _stktoremove.getItem() == p.getItem(), (int) 1,
							((PlayerEntity) sourceentity).container.func_234641_j_());
				}
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _setstack = new ItemStack(CageBlazeItem.block);
					_setstack.setCount((int) 1);
					ItemHandlerHelper.giveItemToPlayer(((PlayerEntity) sourceentity), _setstack);
				}
				if (!entity.world.isRemote())
					entity.remove();
			}
			if (entity instanceof BoyfriendEntity.CustomEntity) {
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _stktoremove = new ItemStack(CageEmptyItem.block);
					((PlayerEntity) sourceentity).inventory.func_234564_a_(p -> _stktoremove.getItem() == p.getItem(), (int) 1,
							((PlayerEntity) sourceentity).container.func_234641_j_());
				}
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _setstack = new ItemStack(CageBoyfriendItem.block);
					_setstack.setCount((int) 1);
					ItemHandlerHelper.giveItemToPlayer(((PlayerEntity) sourceentity), _setstack);
				}
				if (!entity.world.isRemote())
					entity.remove();
			}
			if (entity instanceof BrutalflyEntity.CustomEntity) {
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _stktoremove = new ItemStack(CageEmptyItem.block);
					((PlayerEntity) sourceentity).inventory.func_234564_a_(p -> _stktoremove.getItem() == p.getItem(), (int) 1,
							((PlayerEntity) sourceentity).container.func_234641_j_());
				}
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _setstack = new ItemStack(CageBrutalflyItem.block);
					_setstack.setCount((int) 1);
					ItemHandlerHelper.giveItemToPlayer(((PlayerEntity) sourceentity), _setstack);
				}
				if (!entity.world.isRemote())
					entity.remove();
			}
			if (entity instanceof CamarasaurusEntity.CustomEntity) {
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _stktoremove = new ItemStack(CageEmptyItem.block);
					((PlayerEntity) sourceentity).inventory.func_234564_a_(p -> _stktoremove.getItem() == p.getItem(), (int) 1,
							((PlayerEntity) sourceentity).container.func_234641_j_());
				}
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _setstack = new ItemStack(CageCamarasaurusItem.block);
					_setstack.setCount((int) 1);
					ItemHandlerHelper.giveItemToPlayer(((PlayerEntity) sourceentity), _setstack);
				}
				if (!entity.world.isRemote())
					entity.remove();
			}
			if (entity instanceof CassowaryEntity.CustomEntity) {
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _stktoremove = new ItemStack(CageEmptyItem.block);
					((PlayerEntity) sourceentity).inventory.func_234564_a_(p -> _stktoremove.getItem() == p.getItem(), (int) 1,
							((PlayerEntity) sourceentity).container.func_234641_j_());
				}
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _setstack = new ItemStack(CageCassowaryItem.block);
					_setstack.setCount((int) 1);
					ItemHandlerHelper.giveItemToPlayer(((PlayerEntity) sourceentity), _setstack);
				}
				if (!entity.world.isRemote())
					entity.remove();
			}
			if (entity instanceof CaterKillerEntity.CustomEntity) {
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _stktoremove = new ItemStack(CageEmptyItem.block);
					((PlayerEntity) sourceentity).inventory.func_234564_a_(p -> _stktoremove.getItem() == p.getItem(), (int) 1,
							((PlayerEntity) sourceentity).container.func_234641_j_());
				}
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _setstack = new ItemStack(CageCaterkillerItem.block);
					_setstack.setCount((int) 1);
					ItemHandlerHelper.giveItemToPlayer(((PlayerEntity) sourceentity), _setstack);
				}
				if (!entity.world.isRemote())
					entity.remove();
			}
			if (entity instanceof CaveFisherEntity.CustomEntity) {
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _stktoremove = new ItemStack(CageEmptyItem.block);
					((PlayerEntity) sourceentity).inventory.func_234564_a_(p -> _stktoremove.getItem() == p.getItem(), (int) 1,
							((PlayerEntity) sourceentity).container.func_234641_j_());
				}
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _setstack = new ItemStack(CageCavefisherItem.block);
					_setstack.setCount((int) 1);
					ItemHandlerHelper.giveItemToPlayer(((PlayerEntity) sourceentity), _setstack);
				}
				if (!entity.world.isRemote())
					entity.remove();
			}
			if (entity instanceof CaveSpiderEntity) {
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _stktoremove = new ItemStack(CageEmptyItem.block);
					((PlayerEntity) sourceentity).inventory.func_234564_a_(p -> _stktoremove.getItem() == p.getItem(), (int) 1,
							((PlayerEntity) sourceentity).container.func_234641_j_());
				}
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _setstack = new ItemStack(CageCavespiderItem.block);
					_setstack.setCount((int) 1);
					ItemHandlerHelper.giveItemToPlayer(((PlayerEntity) sourceentity), _setstack);
				}
				if (!entity.world.isRemote())
					entity.remove();
			}
			if (entity instanceof CephadromeEntity.CustomEntity) {
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _stktoremove = new ItemStack(CageEmptyItem.block);
					((PlayerEntity) sourceentity).inventory.func_234564_a_(p -> _stktoremove.getItem() == p.getItem(), (int) 1,
							((PlayerEntity) sourceentity).container.func_234641_j_());
				}
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _setstack = new ItemStack(CageCephadromeItem.block);
					_setstack.setCount((int) 1);
					ItemHandlerHelper.giveItemToPlayer(((PlayerEntity) sourceentity), _setstack);
				}
				if (!entity.world.isRemote())
					entity.remove();
			}
			if (entity instanceof ChickenEntity) {
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _stktoremove = new ItemStack(CageEmptyItem.block);
					((PlayerEntity) sourceentity).inventory.func_234564_a_(p -> _stktoremove.getItem() == p.getItem(), (int) 1,
							((PlayerEntity) sourceentity).container.func_234641_j_());
				}
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _setstack = new ItemStack(CageChickenItem.block);
					_setstack.setCount((int) 1);
					ItemHandlerHelper.giveItemToPlayer(((PlayerEntity) sourceentity), _setstack);
				}
				if (!entity.world.isRemote())
					entity.remove();
			}
			if (entity instanceof ChipmunkEntity.CustomEntity) {
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _stktoremove = new ItemStack(CageEmptyItem.block);
					((PlayerEntity) sourceentity).inventory.func_234564_a_(p -> _stktoremove.getItem() == p.getItem(), (int) 1,
							((PlayerEntity) sourceentity).container.func_234641_j_());
				}
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _setstack = new ItemStack(CageChipmunkItem.block);
					_setstack.setCount((int) 1);
					ItemHandlerHelper.giveItemToPlayer(((PlayerEntity) sourceentity), _setstack);
				}
				if (!entity.world.isRemote())
					entity.remove();
			}
			if (entity instanceof CliffRacerEntity.CustomEntity) {
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _stktoremove = new ItemStack(CageEmptyItem.block);
					((PlayerEntity) sourceentity).inventory.func_234564_a_(p -> _stktoremove.getItem() == p.getItem(), (int) 1,
							((PlayerEntity) sourceentity).container.func_234641_j_());
				}
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _setstack = new ItemStack(CageCliffracerItem.block);
					_setstack.setCount((int) 1);
					ItemHandlerHelper.giveItemToPlayer(((PlayerEntity) sourceentity), _setstack);
				}
				if (!entity.world.isRemote())
					entity.remove();
			}
			if (entity instanceof CloudSharkEntity.CustomEntity) {
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _stktoremove = new ItemStack(CageEmptyItem.block);
					((PlayerEntity) sourceentity).inventory.func_234564_a_(p -> _stktoremove.getItem() == p.getItem(), (int) 1,
							((PlayerEntity) sourceentity).container.func_234641_j_());
				}
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _setstack = new ItemStack(CageCloudsharkItem.block);
					_setstack.setCount((int) 1);
					ItemHandlerHelper.giveItemToPlayer(((PlayerEntity) sourceentity), _setstack);
				}
				if (!entity.world.isRemote())
					entity.remove();
			}
			if (entity instanceof CockateilEntity.CustomEntity) {
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _stktoremove = new ItemStack(CageEmptyItem.block);
					((PlayerEntity) sourceentity).inventory.func_234564_a_(p -> _stktoremove.getItem() == p.getItem(), (int) 1,
							((PlayerEntity) sourceentity).container.func_234641_j_());
				}
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _setstack = new ItemStack(CageCockateilItem.block);
					_setstack.setCount((int) 1);
					ItemHandlerHelper.giveItemToPlayer(((PlayerEntity) sourceentity), _setstack);
				}
				if (!entity.world.isRemote())
					entity.remove();
			}
			if (entity instanceof CowEntity) {
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _stktoremove = new ItemStack(CageEmptyItem.block);
					((PlayerEntity) sourceentity).inventory.func_234564_a_(p -> _stktoremove.getItem() == p.getItem(), (int) 1,
							((PlayerEntity) sourceentity).container.func_234641_j_());
				}
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _setstack = new ItemStack(CageCowItem.block);
					_setstack.setCount((int) 1);
					ItemHandlerHelper.giveItemToPlayer(((PlayerEntity) sourceentity), _setstack);
				}
				if (!entity.world.isRemote())
					entity.remove();
			}
			if (entity instanceof CrabEntity.CustomEntity) {
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _stktoremove = new ItemStack(CageEmptyItem.block);
					((PlayerEntity) sourceentity).inventory.func_234564_a_(p -> _stktoremove.getItem() == p.getItem(), (int) 1,
							((PlayerEntity) sourceentity).container.func_234641_j_());
				}
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _setstack = new ItemStack(CageCrabItem.block);
					_setstack.setCount((int) 1);
					ItemHandlerHelper.giveItemToPlayer(((PlayerEntity) sourceentity), _setstack);
				}
				if (!entity.world.isRemote())
					entity.remove();
			}
			if (entity instanceof CreeperEntity) {
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _stktoremove = new ItemStack(CageEmptyItem.block);
					((PlayerEntity) sourceentity).inventory.func_234564_a_(p -> _stktoremove.getItem() == p.getItem(), (int) 1,
							((PlayerEntity) sourceentity).container.func_234641_j_());
				}
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _setstack = new ItemStack(CageCreeperItem.block);
					_setstack.setCount((int) 1);
					ItemHandlerHelper.giveItemToPlayer(((PlayerEntity) sourceentity), _setstack);
				}
				if (!entity.world.isRemote())
					entity.remove();
			}
			if (entity instanceof CreepingHorrorEntity.CustomEntity) {
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _stktoremove = new ItemStack(CageEmptyItem.block);
					((PlayerEntity) sourceentity).inventory.func_234564_a_(p -> _stktoremove.getItem() == p.getItem(), (int) 1,
							((PlayerEntity) sourceentity).container.func_234641_j_());
				}
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _setstack = new ItemStack(CageCreepinghorrorItem.block);
					_setstack.setCount((int) 1);
					ItemHandlerHelper.giveItemToPlayer(((PlayerEntity) sourceentity), _setstack);
				}
				if (!entity.world.isRemote())
					entity.remove();
			}
			if (entity instanceof CricketEntity.CustomEntity) {
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _stktoremove = new ItemStack(CageEmptyItem.block);
					((PlayerEntity) sourceentity).inventory.func_234564_a_(p -> _stktoremove.getItem() == p.getItem(), (int) 1,
							((PlayerEntity) sourceentity).container.func_234641_j_());
				}
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _setstack = new ItemStack(CageCricketItem.block);
					_setstack.setCount((int) 1);
					ItemHandlerHelper.giveItemToPlayer(((PlayerEntity) sourceentity), _setstack);
				}
				if (!entity.world.isRemote())
					entity.remove();
			}
			if (entity instanceof CryolophosaurusEntity.CustomEntity) {
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _stktoremove = new ItemStack(CageEmptyItem.block);
					((PlayerEntity) sourceentity).inventory.func_234564_a_(p -> _stktoremove.getItem() == p.getItem(), (int) 1,
							((PlayerEntity) sourceentity).container.func_234641_j_());
				}
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _setstack = new ItemStack(CageCryolophosaurusItem.block);
					_setstack.setCount((int) 1);
					ItemHandlerHelper.giveItemToPlayer(((PlayerEntity) sourceentity), _setstack);
				}
				if (!entity.world.isRemote())
					entity.remove();
			}
			if (entity instanceof CrystalCowEntity.CustomEntity) {
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _stktoremove = new ItemStack(CageEmptyItem.block);
					((PlayerEntity) sourceentity).inventory.func_234564_a_(p -> _stktoremove.getItem() == p.getItem(), (int) 1,
							((PlayerEntity) sourceentity).container.func_234641_j_());
				}
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _setstack = new ItemStack(CageCrystalcowItem.block);
					_setstack.setCount((int) 1);
					ItemHandlerHelper.giveItemToPlayer(((PlayerEntity) sourceentity), _setstack);
				}
				if (!entity.world.isRemote())
					entity.remove();
			}
			if (entity instanceof DragonEntity.CustomEntity) {
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _stktoremove = new ItemStack(CageEmptyItem.block);
					((PlayerEntity) sourceentity).inventory.func_234564_a_(p -> _stktoremove.getItem() == p.getItem(), (int) 1,
							((PlayerEntity) sourceentity).container.func_234641_j_());
				}
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _setstack = new ItemStack(CageDragonItem.block);
					_setstack.setCount((int) 1);
					ItemHandlerHelper.giveItemToPlayer(((PlayerEntity) sourceentity), _setstack);
				}
				if (!entity.world.isRemote())
					entity.remove();
			}
			if (entity instanceof DragonflyEntity.CustomEntity) {
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _stktoremove = new ItemStack(CageEmptyItem.block);
					((PlayerEntity) sourceentity).inventory.func_234564_a_(p -> _stktoremove.getItem() == p.getItem(), (int) 1,
							((PlayerEntity) sourceentity).container.func_234641_j_());
				}
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _setstack = new ItemStack(CageDragonflyItem.block);
					_setstack.setCount((int) 1);
					ItemHandlerHelper.giveItemToPlayer(((PlayerEntity) sourceentity), _setstack);
				}
				if (!entity.world.isRemote())
					entity.remove();
			}
			if (entity instanceof DungeonBeastEntity.CustomEntity) {
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _stktoremove = new ItemStack(CageEmptyItem.block);
					((PlayerEntity) sourceentity).inventory.func_234564_a_(p -> _stktoremove.getItem() == p.getItem(), (int) 1,
							((PlayerEntity) sourceentity).container.func_234641_j_());
				}
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _setstack = new ItemStack(CageDungeonbeastItem.block);
					_setstack.setCount((int) 1);
					ItemHandlerHelper.giveItemToPlayer(((PlayerEntity) sourceentity), _setstack);
				}
				if (!entity.world.isRemote())
					entity.remove();
			}
			if (entity instanceof EasterBunnyEntity.CustomEntity) {
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _stktoremove = new ItemStack(CageEmptyItem.block);
					((PlayerEntity) sourceentity).inventory.func_234564_a_(p -> _stktoremove.getItem() == p.getItem(), (int) 1,
							((PlayerEntity) sourceentity).container.func_234641_j_());
				}
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _setstack = new ItemStack(CageEasterbunnyItem.block);
					_setstack.setCount((int) 1);
					ItemHandlerHelper.giveItemToPlayer(((PlayerEntity) sourceentity), _setstack);
				}
				if (!entity.world.isRemote())
					entity.remove();
			}
			if (entity instanceof EmperorScorpionEntity.CustomEntity) {
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _stktoremove = new ItemStack(CageEmptyItem.block);
					((PlayerEntity) sourceentity).inventory.func_234564_a_(p -> _stktoremove.getItem() == p.getItem(), (int) 1,
							((PlayerEntity) sourceentity).container.func_234641_j_());
				}
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _setstack = new ItemStack(CageEmperorscorpionItem.block);
					_setstack.setCount((int) 1);
					ItemHandlerHelper.giveItemToPlayer(((PlayerEntity) sourceentity), _setstack);
				}
				if (!entity.world.isRemote())
					entity.remove();
			}
			if (entity instanceof EnchantedCowEntity.CustomEntity) {
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _stktoremove = new ItemStack(CageEmptyItem.block);
					((PlayerEntity) sourceentity).inventory.func_234564_a_(p -> _stktoremove.getItem() == p.getItem(), (int) 1,
							((PlayerEntity) sourceentity).container.func_234641_j_());
				}
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _setstack = new ItemStack(CageEnchantedcowItem.block);
					_setstack.setCount((int) 1);
					ItemHandlerHelper.giveItemToPlayer(((PlayerEntity) sourceentity), _setstack);
				}
				if (!entity.world.isRemote())
					entity.remove();
			}
			if (entity instanceof EnderDragonEntity) {
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _stktoremove = new ItemStack(CageEmptyItem.block);
					((PlayerEntity) sourceentity).inventory.func_234564_a_(p -> _stktoremove.getItem() == p.getItem(), (int) 1,
							((PlayerEntity) sourceentity).container.func_234641_j_());
				}
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _setstack = new ItemStack(CageEnderdragonItem.block);
					_setstack.setCount((int) 1);
					ItemHandlerHelper.giveItemToPlayer(((PlayerEntity) sourceentity), _setstack);
				}
				if (!entity.world.isRemote())
					entity.remove();
			}
			if (entity instanceof EnderKnightEntity.CustomEntity) {
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _stktoremove = new ItemStack(CageEmptyItem.block);
					((PlayerEntity) sourceentity).inventory.func_234564_a_(p -> _stktoremove.getItem() == p.getItem(), (int) 1,
							((PlayerEntity) sourceentity).container.func_234641_j_());
				}
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _setstack = new ItemStack(CageEnderknightItem.block);
					_setstack.setCount((int) 1);
					ItemHandlerHelper.giveItemToPlayer(((PlayerEntity) sourceentity), _setstack);
				}
				if (!entity.world.isRemote())
					entity.remove();
			}
			if (entity instanceof EndermanEntity) {
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _stktoremove = new ItemStack(CageEmptyItem.block);
					((PlayerEntity) sourceentity).inventory.func_234564_a_(p -> _stktoremove.getItem() == p.getItem(), (int) 1,
							((PlayerEntity) sourceentity).container.func_234641_j_());
				}
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _setstack = new ItemStack(CageEndermanItem.block);
					_setstack.setCount((int) 1);
					ItemHandlerHelper.giveItemToPlayer(((PlayerEntity) sourceentity), _setstack);
				}
				if (!entity.world.isRemote())
					entity.remove();
			}
			if (entity instanceof EnderReaperEntity.CustomEntity) {
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _stktoremove = new ItemStack(CageEmptyItem.block);
					((PlayerEntity) sourceentity).inventory.func_234564_a_(p -> _stktoremove.getItem() == p.getItem(), (int) 1,
							((PlayerEntity) sourceentity).container.func_234641_j_());
				}
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _setstack = new ItemStack(CageEnderreaperItem.block);
					_setstack.setCount((int) 1);
					ItemHandlerHelper.giveItemToPlayer(((PlayerEntity) sourceentity), _setstack);
				}
				if (!entity.world.isRemote())
					entity.remove();
			}
			if (entity instanceof FairyEntity.CustomEntity) {
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _stktoremove = new ItemStack(CageEmptyItem.block);
					((PlayerEntity) sourceentity).inventory.func_234564_a_(p -> _stktoremove.getItem() == p.getItem(), (int) 1,
							((PlayerEntity) sourceentity).container.func_234641_j_());
				}
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _setstack = new ItemStack(CageFairyItem.block);
					_setstack.setCount((int) 1);
					ItemHandlerHelper.giveItemToPlayer(((PlayerEntity) sourceentity), _setstack);
				}
				if (!entity.world.isRemote())
					entity.remove();
			}
			if (entity instanceof FireflyEntity.CustomEntity) {
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _stktoremove = new ItemStack(CageEmptyItem.block);
					((PlayerEntity) sourceentity).inventory.func_234564_a_(p -> _stktoremove.getItem() == p.getItem(), (int) 1,
							((PlayerEntity) sourceentity).container.func_234641_j_());
				}
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _setstack = new ItemStack(CageFireflyItem.block);
					_setstack.setCount((int) 1);
					ItemHandlerHelper.giveItemToPlayer(((PlayerEntity) sourceentity), _setstack);
				}
				if (!entity.world.isRemote())
					entity.remove();
			}
			if (entity instanceof FlounderEntity.CustomEntity) {
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _stktoremove = new ItemStack(CageEmptyItem.block);
					((PlayerEntity) sourceentity).inventory.func_234564_a_(p -> _stktoremove.getItem() == p.getItem(), (int) 1,
							((PlayerEntity) sourceentity).container.func_234641_j_());
				}
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _setstack = new ItemStack(CageFlounderItem.block);
					_setstack.setCount((int) 1);
					ItemHandlerHelper.giveItemToPlayer(((PlayerEntity) sourceentity), _setstack);
				}
				if (!entity.world.isRemote())
					entity.remove();
			}
			if (entity instanceof FrogEntity.CustomEntity) {
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _stktoremove = new ItemStack(CageEmptyItem.block);
					((PlayerEntity) sourceentity).inventory.func_234564_a_(p -> _stktoremove.getItem() == p.getItem(), (int) 1,
							((PlayerEntity) sourceentity).container.func_234641_j_());
				}
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _setstack = new ItemStack(CageFrogItem.block);
					_setstack.setCount((int) 1);
					ItemHandlerHelper.giveItemToPlayer(((PlayerEntity) sourceentity), _setstack);
				}
				if (!entity.world.isRemote())
					entity.remove();
			}
			if (entity instanceof GammaMetroidEntity.CustomEntity) {
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _stktoremove = new ItemStack(CageEmptyItem.block);
					((PlayerEntity) sourceentity).inventory.func_234564_a_(p -> _stktoremove.getItem() == p.getItem(), (int) 1,
							((PlayerEntity) sourceentity).container.func_234641_j_());
				}
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _setstack = new ItemStack(CageGammaMetroidItem.block);
					_setstack.setCount((int) 1);
					ItemHandlerHelper.giveItemToPlayer(((PlayerEntity) sourceentity), _setstack);
				}
				if (!entity.world.isRemote())
					entity.remove();
			}
			if (entity instanceof GazelleEntity.CustomEntity) {
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _stktoremove = new ItemStack(CageEmptyItem.block);
					((PlayerEntity) sourceentity).inventory.func_234564_a_(p -> _stktoremove.getItem() == p.getItem(), (int) 1,
							((PlayerEntity) sourceentity).container.func_234641_j_());
				}
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _setstack = new ItemStack(CageGazelleItem.block);
					_setstack.setCount((int) 1);
					ItemHandlerHelper.giveItemToPlayer(((PlayerEntity) sourceentity), _setstack);
				}
				if (!entity.world.isRemote())
					entity.remove();
			}
			if (entity instanceof GhastEntity) {
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _stktoremove = new ItemStack(CageEmptyItem.block);
					((PlayerEntity) sourceentity).inventory.func_234564_a_(p -> _stktoremove.getItem() == p.getItem(), (int) 1,
							((PlayerEntity) sourceentity).container.func_234641_j_());
				}
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _setstack = new ItemStack(CageGhastItem.block);
					_setstack.setCount((int) 1);
					ItemHandlerHelper.giveItemToPlayer(((PlayerEntity) sourceentity), _setstack);
				}
				if (!entity.world.isRemote())
					entity.remove();
			}
			if (entity instanceof GirlfriendEntity.CustomEntity) {
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _stktoremove = new ItemStack(CageEmptyItem.block);
					((PlayerEntity) sourceentity).inventory.func_234564_a_(p -> _stktoremove.getItem() == p.getItem(), (int) 1,
							((PlayerEntity) sourceentity).container.func_234641_j_());
				}
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _setstack = new ItemStack(CageGirlfriendItem.block);
					_setstack.setCount((int) 1);
					ItemHandlerHelper.giveItemToPlayer(((PlayerEntity) sourceentity), _setstack);
				}
				if (!entity.world.isRemote())
					entity.remove();
			}
			if (entity instanceof GoldCowEntity.CustomEntity) {
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _stktoremove = new ItemStack(CageEmptyItem.block);
					((PlayerEntity) sourceentity).inventory.func_234564_a_(p -> _stktoremove.getItem() == p.getItem(), (int) 1,
							((PlayerEntity) sourceentity).container.func_234641_j_());
				}
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _setstack = new ItemStack(CageGoldcowItem.block);
					_setstack.setCount((int) 1);
					ItemHandlerHelper.giveItemToPlayer(((PlayerEntity) sourceentity), _setstack);
				}
				if (!entity.world.isRemote())
					entity.remove();
			}
			if (entity instanceof GoldFishEntity.CustomEntity) {
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _stktoremove = new ItemStack(CageEmptyItem.block);
					((PlayerEntity) sourceentity).inventory.func_234564_a_(p -> _stktoremove.getItem() == p.getItem(), (int) 1,
							((PlayerEntity) sourceentity).container.func_234641_j_());
				}
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _setstack = new ItemStack(CageGoldfishItem.block);
					_setstack.setCount((int) 1);
					ItemHandlerHelper.giveItemToPlayer(((PlayerEntity) sourceentity), _setstack);
				}
				if (!entity.world.isRemote())
					entity.remove();
			}
			if (entity instanceof HammerheadEntity.CustomEntity) {
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _stktoremove = new ItemStack(CageEmptyItem.block);
					((PlayerEntity) sourceentity).inventory.func_234564_a_(p -> _stktoremove.getItem() == p.getItem(), (int) 1,
							((PlayerEntity) sourceentity).container.func_234641_j_());
				}
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _setstack = new ItemStack(CageHammerheadItem.block);
					_setstack.setCount((int) 1);
					ItemHandlerHelper.giveItemToPlayer(((PlayerEntity) sourceentity), _setstack);
				}
				if (!entity.world.isRemote())
					entity.remove();
			}
			if (entity instanceof HerculesBeetleEntity.CustomEntity) {
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _stktoremove = new ItemStack(CageEmptyItem.block);
					((PlayerEntity) sourceentity).inventory.func_234564_a_(p -> _stktoremove.getItem() == p.getItem(), (int) 1,
							((PlayerEntity) sourceentity).container.func_234641_j_());
				}
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _setstack = new ItemStack(CageHerculesItem.block);
					_setstack.setCount((int) 1);
					ItemHandlerHelper.giveItemToPlayer(((PlayerEntity) sourceentity), _setstack);
				}
				if (!entity.world.isRemote())
					entity.remove();
			}
			if (entity instanceof HorseEntity) {
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _stktoremove = new ItemStack(CageEmptyItem.block);
					((PlayerEntity) sourceentity).inventory.func_234564_a_(p -> _stktoremove.getItem() == p.getItem(), (int) 1,
							((PlayerEntity) sourceentity).container.func_234641_j_());
				}
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _setstack = new ItemStack(CageHorseItem.block);
					_setstack.setCount((int) 1);
					ItemHandlerHelper.giveItemToPlayer(((PlayerEntity) sourceentity), _setstack);
				}
				if (!entity.world.isRemote())
					entity.remove();
			}
			if (entity instanceof HydroliscEntity.CustomEntity) {
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _stktoremove = new ItemStack(CageEmptyItem.block);
					((PlayerEntity) sourceentity).inventory.func_234564_a_(p -> _stktoremove.getItem() == p.getItem(), (int) 1,
							((PlayerEntity) sourceentity).container.func_234641_j_());
				}
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _setstack = new ItemStack(CageHydroliscItem.block);
					_setstack.setCount((int) 1);
					ItemHandlerHelper.giveItemToPlayer(((PlayerEntity) sourceentity), _setstack);
				}
				if (!entity.world.isRemote())
					entity.remove();
			}
			if (entity instanceof IronGolemEntity) {
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _stktoremove = new ItemStack(CageEmptyItem.block);
					((PlayerEntity) sourceentity).inventory.func_234564_a_(p -> _stktoremove.getItem() == p.getItem(), (int) 1,
							((PlayerEntity) sourceentity).container.func_234641_j_());
				}
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _setstack = new ItemStack(CageIrongolemItem.block);
					_setstack.setCount((int) 1);
					ItemHandlerHelper.giveItemToPlayer(((PlayerEntity) sourceentity), _setstack);
				}
				if (!entity.world.isRemote())
					entity.remove();
			}
			if (entity instanceof IrukandjiEntity.CustomEntity) {
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _stktoremove = new ItemStack(CageEmptyItem.block);
					((PlayerEntity) sourceentity).inventory.func_234564_a_(p -> _stktoremove.getItem() == p.getItem(), (int) 1,
							((PlayerEntity) sourceentity).container.func_234641_j_());
				}
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _setstack = new ItemStack(CageIrukandjiItem.block);
					_setstack.setCount((int) 1);
					ItemHandlerHelper.giveItemToPlayer(((PlayerEntity) sourceentity), _setstack);
				}
				if (!entity.world.isRemote())
					entity.remove();
			}
			if (entity instanceof KrakenEntity.CustomEntity) {
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _stktoremove = new ItemStack(CageEmptyItem.block);
					((PlayerEntity) sourceentity).inventory.func_234564_a_(p -> _stktoremove.getItem() == p.getItem(), (int) 1,
							((PlayerEntity) sourceentity).container.func_234641_j_());
				}
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _setstack = new ItemStack(CageKrakenItem.block);
					_setstack.setCount((int) 1);
					ItemHandlerHelper.giveItemToPlayer(((PlayerEntity) sourceentity), _setstack);
				}
				if (!entity.world.isRemote())
					entity.remove();
			}
			if (entity instanceof KyuubiEntity.CustomEntity) {
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _stktoremove = new ItemStack(CageEmptyItem.block);
					((PlayerEntity) sourceentity).inventory.func_234564_a_(p -> _stktoremove.getItem() == p.getItem(), (int) 1,
							((PlayerEntity) sourceentity).container.func_234641_j_());
				}
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _setstack = new ItemStack(CageKyuubiItem.block);
					_setstack.setCount((int) 1);
					ItemHandlerHelper.giveItemToPlayer(((PlayerEntity) sourceentity), _setstack);
				}
				if (!entity.world.isRemote())
					entity.remove();
			}
			if (entity instanceof WormLargeEntity.CustomEntity) {
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _stktoremove = new ItemStack(CageEmptyItem.block);
					((PlayerEntity) sourceentity).inventory.func_234564_a_(p -> _stktoremove.getItem() == p.getItem(), (int) 1,
							((PlayerEntity) sourceentity).container.func_234641_j_());
				}
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _setstack = new ItemStack(CageLargewormItem.block);
					_setstack.setCount((int) 1);
					ItemHandlerHelper.giveItemToPlayer(((PlayerEntity) sourceentity), _setstack);
				}
				if (!entity.world.isRemote())
					entity.remove();
			}
			if (entity instanceof LeafMonsterEntity.CustomEntity) {
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _stktoremove = new ItemStack(CageEmptyItem.block);
					((PlayerEntity) sourceentity).inventory.func_234564_a_(p -> _stktoremove.getItem() == p.getItem(), (int) 1,
							((PlayerEntity) sourceentity).container.func_234641_j_());
				}
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _setstack = new ItemStack(CageLeafmonsterItem.block);
					_setstack.setCount((int) 1);
					ItemHandlerHelper.giveItemToPlayer(((PlayerEntity) sourceentity), _setstack);
				}
				if (!entity.world.isRemote())
					entity.remove();
			}
			if (entity instanceof LeonEntity.CustomEntity) {
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _stktoremove = new ItemStack(CageEmptyItem.block);
					((PlayerEntity) sourceentity).inventory.func_234564_a_(p -> _stktoremove.getItem() == p.getItem(), (int) 1,
							((PlayerEntity) sourceentity).container.func_234641_j_());
				}
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _setstack = new ItemStack(CageLeonItem.block);
					_setstack.setCount((int) 1);
					ItemHandlerHelper.giveItemToPlayer(((PlayerEntity) sourceentity), _setstack);
				}
				if (!entity.world.isRemote())
					entity.remove();
			}
			if (entity instanceof LizardEntity.CustomEntity) {
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _stktoremove = new ItemStack(CageEmptyItem.block);
					((PlayerEntity) sourceentity).inventory.func_234564_a_(p -> _stktoremove.getItem() == p.getItem(), (int) 1,
							((PlayerEntity) sourceentity).container.func_234641_j_());
				}
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _setstack = new ItemStack(CageLizardItem.block);
					_setstack.setCount((int) 1);
					ItemHandlerHelper.giveItemToPlayer(((PlayerEntity) sourceentity), _setstack);
				}
				if (!entity.world.isRemote())
					entity.remove();
			}
			if (entity instanceof LurkingTerrorEntity.CustomEntity) {
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _stktoremove = new ItemStack(CageEmptyItem.block);
					((PlayerEntity) sourceentity).inventory.func_234564_a_(p -> _stktoremove.getItem() == p.getItem(), (int) 1,
							((PlayerEntity) sourceentity).container.func_234641_j_());
				}
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _setstack = new ItemStack(CageLurkingterrorItem.block);
					_setstack.setCount((int) 1);
					ItemHandlerHelper.giveItemToPlayer(((PlayerEntity) sourceentity), _setstack);
				}
				if (!entity.world.isRemote())
					entity.remove();
			}
			if (entity instanceof MagmaCubeEntity) {
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _stktoremove = new ItemStack(CageEmptyItem.block);
					((PlayerEntity) sourceentity).inventory.func_234564_a_(p -> _stktoremove.getItem() == p.getItem(), (int) 1,
							((PlayerEntity) sourceentity).container.func_234641_j_());
				}
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _setstack = new ItemStack(CageMagmacubeItem.block);
					_setstack.setCount((int) 1);
					ItemHandlerHelper.giveItemToPlayer(((PlayerEntity) sourceentity), _setstack);
				}
				if (!entity.world.isRemote())
					entity.remove();
			}
			if (entity instanceof MantisEntity.CustomEntity) {
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _stktoremove = new ItemStack(CageEmptyItem.block);
					((PlayerEntity) sourceentity).inventory.func_234564_a_(p -> _stktoremove.getItem() == p.getItem(), (int) 1,
							((PlayerEntity) sourceentity).container.func_234641_j_());
				}
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _setstack = new ItemStack(CageMantisItem.block);
					_setstack.setCount((int) 1);
					ItemHandlerHelper.giveItemToPlayer(((PlayerEntity) sourceentity), _setstack);
				}
				if (!entity.world.isRemote())
					entity.remove();
			}
			if (entity instanceof WormMediumEntity.CustomEntity) {
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _stktoremove = new ItemStack(CageEmptyItem.block);
					((PlayerEntity) sourceentity).inventory.func_234564_a_(p -> _stktoremove.getItem() == p.getItem(), (int) 1,
							((PlayerEntity) sourceentity).container.func_234641_j_());
				}
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _setstack = new ItemStack(CageMediumwormItem.block);
					_setstack.setCount((int) 1);
					ItemHandlerHelper.giveItemToPlayer(((PlayerEntity) sourceentity), _setstack);
				}
				if (!entity.world.isRemote())
					entity.remove();
			}
			if (entity instanceof MolenoidEntity.CustomEntity) {
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _stktoremove = new ItemStack(CageEmptyItem.block);
					((PlayerEntity) sourceentity).inventory.func_234564_a_(p -> _stktoremove.getItem() == p.getItem(), (int) 1,
							((PlayerEntity) sourceentity).container.func_234641_j_());
				}
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _setstack = new ItemStack(CageMolenoidItem.block);
					_setstack.setCount((int) 1);
					ItemHandlerHelper.giveItemToPlayer(((PlayerEntity) sourceentity), _setstack);
				}
				if (!entity.world.isRemote())
					entity.remove();
			}
			if (entity instanceof MooshroomEntity) {
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _stktoremove = new ItemStack(CageEmptyItem.block);
					((PlayerEntity) sourceentity).inventory.func_234564_a_(p -> _stktoremove.getItem() == p.getItem(), (int) 1,
							((PlayerEntity) sourceentity).container.func_234641_j_());
				}
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _setstack = new ItemStack(CageMooshroomItem.block);
					_setstack.setCount((int) 1);
					ItemHandlerHelper.giveItemToPlayer(((PlayerEntity) sourceentity), _setstack);
				}
				if (!entity.world.isRemote())
					entity.remove();
			}
			if (entity instanceof MothraEntity.CustomEntity) {
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _stktoremove = new ItemStack(CageEmptyItem.block);
					((PlayerEntity) sourceentity).inventory.func_234564_a_(p -> _stktoremove.getItem() == p.getItem(), (int) 1,
							((PlayerEntity) sourceentity).container.func_234641_j_());
				}
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _setstack = new ItemStack(CageMothraItem.block);
					_setstack.setCount((int) 1);
					ItemHandlerHelper.giveItemToPlayer(((PlayerEntity) sourceentity), _setstack);
				}
				if (!entity.world.isRemote())
					entity.remove();
			}
			if (entity instanceof NastysaurusEntity.CustomEntity) {
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _stktoremove = new ItemStack(CageEmptyItem.block);
					((PlayerEntity) sourceentity).inventory.func_234564_a_(p -> _stktoremove.getItem() == p.getItem(), (int) 1,
							((PlayerEntity) sourceentity).container.func_234641_j_());
				}
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _setstack = new ItemStack(CageNastysaurusItem.block);
					_setstack.setCount((int) 1);
					ItemHandlerHelper.giveItemToPlayer(((PlayerEntity) sourceentity), _setstack);
				}
				if (!entity.world.isRemote())
					entity.remove();
			}
			if (entity instanceof PitchBlackEntity.CustomEntity) {
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _stktoremove = new ItemStack(CageEmptyItem.block);
					((PlayerEntity) sourceentity).inventory.func_234564_a_(p -> _stktoremove.getItem() == p.getItem(), (int) 1,
							((PlayerEntity) sourceentity).container.func_234641_j_());
				}
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _setstack = new ItemStack(CageNightmareItem.block);
					_setstack.setCount((int) 1);
					ItemHandlerHelper.giveItemToPlayer(((PlayerEntity) sourceentity), _setstack);
				}
				if (!entity.world.isRemote())
					entity.remove();
			}
			if (entity instanceof OcelotEntity) {
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _stktoremove = new ItemStack(CageEmptyItem.block);
					((PlayerEntity) sourceentity).inventory.func_234564_a_(p -> _stktoremove.getItem() == p.getItem(), (int) 1,
							((PlayerEntity) sourceentity).container.func_234641_j_());
				}
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _setstack = new ItemStack(CageOcelotItem.block);
					_setstack.setCount((int) 1);
					ItemHandlerHelper.giveItemToPlayer(((PlayerEntity) sourceentity), _setstack);
				}
				if (!entity.world.isRemote())
					entity.remove();
			}
			if (entity instanceof OstrichEntity.CustomEntity) {
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _stktoremove = new ItemStack(CageEmptyItem.block);
					((PlayerEntity) sourceentity).inventory.func_234564_a_(p -> _stktoremove.getItem() == p.getItem(), (int) 1,
							((PlayerEntity) sourceentity).container.func_234641_j_());
				}
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _setstack = new ItemStack(CageOstrichItem.block);
					_setstack.setCount((int) 1);
					ItemHandlerHelper.giveItemToPlayer(((PlayerEntity) sourceentity), _setstack);
				}
				if (!entity.world.isRemote())
					entity.remove();
			}
			if (entity instanceof PeacockEntity.CustomEntity) {
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _stktoremove = new ItemStack(CageEmptyItem.block);
					((PlayerEntity) sourceentity).inventory.func_234564_a_(p -> _stktoremove.getItem() == p.getItem(), (int) 1,
							((PlayerEntity) sourceentity).container.func_234641_j_());
				}
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _setstack = new ItemStack(CagePeacockItem.block);
					_setstack.setCount((int) 1);
					ItemHandlerHelper.giveItemToPlayer(((PlayerEntity) sourceentity), _setstack);
				}
				if (!entity.world.isRemote())
					entity.remove();
			}
			if (entity instanceof PigEntity) {
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _stktoremove = new ItemStack(CageEmptyItem.block);
					((PlayerEntity) sourceentity).inventory.func_234564_a_(p -> _stktoremove.getItem() == p.getItem(), (int) 1,
							((PlayerEntity) sourceentity).container.func_234641_j_());
				}
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _setstack = new ItemStack(CagePigItem.block);
					_setstack.setCount((int) 1);
					ItemHandlerHelper.giveItemToPlayer(((PlayerEntity) sourceentity), _setstack);
				}
				if (!entity.world.isRemote())
					entity.remove();
			}
			if (entity instanceof PointysaurusEntity.CustomEntity) {
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _stktoremove = new ItemStack(CageEmptyItem.block);
					((PlayerEntity) sourceentity).inventory.func_234564_a_(p -> _stktoremove.getItem() == p.getItem(), (int) 1,
							((PlayerEntity) sourceentity).container.func_234641_j_());
				}
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _setstack = new ItemStack(CagePointysaurusItem.block);
					_setstack.setCount((int) 1);
					ItemHandlerHelper.giveItemToPlayer(((PlayerEntity) sourceentity), _setstack);
				}
				if (!entity.world.isRemote())
					entity.remove();
			}
			if (entity instanceof RatEntity.CustomEntity) {
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _stktoremove = new ItemStack(CageEmptyItem.block);
					((PlayerEntity) sourceentity).inventory.func_234564_a_(p -> _stktoremove.getItem() == p.getItem(), (int) 1,
							((PlayerEntity) sourceentity).container.func_234641_j_());
				}
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _setstack = new ItemStack(CageRatItem.block);
					_setstack.setCount((int) 1);
					ItemHandlerHelper.giveItemToPlayer(((PlayerEntity) sourceentity), _setstack);
				}
				if (!entity.world.isRemote())
					entity.remove();
			}
			if (entity instanceof RedCowEntity.CustomEntity) {
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _stktoremove = new ItemStack(CageEmptyItem.block);
					((PlayerEntity) sourceentity).inventory.func_234564_a_(p -> _stktoremove.getItem() == p.getItem(), (int) 1,
							((PlayerEntity) sourceentity).container.func_234641_j_());
				}
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _setstack = new ItemStack(CageRedcowItem.block);
					_setstack.setCount((int) 1);
					ItemHandlerHelper.giveItemToPlayer(((PlayerEntity) sourceentity), _setstack);
				}
				if (!entity.world.isRemote())
					entity.remove();
			}
			if (entity instanceof RotatorEntity.CustomEntity) {
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _stktoremove = new ItemStack(CageEmptyItem.block);
					((PlayerEntity) sourceentity).inventory.func_234564_a_(p -> _stktoremove.getItem() == p.getItem(), (int) 1,
							((PlayerEntity) sourceentity).container.func_234641_j_());
				}
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _setstack = new ItemStack(CageRotatorItem.block);
					_setstack.setCount((int) 1);
					ItemHandlerHelper.giveItemToPlayer(((PlayerEntity) sourceentity), _setstack);
				}
				if (!entity.world.isRemote())
					entity.remove();
			}
			if (entity instanceof RubberDuckyEntity.CustomEntity) {
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _stktoremove = new ItemStack(CageEmptyItem.block);
					((PlayerEntity) sourceentity).inventory.func_234564_a_(p -> _stktoremove.getItem() == p.getItem(), (int) 1,
							((PlayerEntity) sourceentity).container.func_234641_j_());
				}
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _setstack = new ItemStack(CageRubberduckyItem.block);
					_setstack.setCount((int) 1);
					ItemHandlerHelper.giveItemToPlayer(((PlayerEntity) sourceentity), _setstack);
				}
				if (!entity.world.isRemote())
					entity.remove();
			}
			if (entity instanceof ScorpionEntity.CustomEntity) {
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _stktoremove = new ItemStack(CageEmptyItem.block);
					((PlayerEntity) sourceentity).inventory.func_234564_a_(p -> _stktoremove.getItem() == p.getItem(), (int) 1,
							((PlayerEntity) sourceentity).container.func_234641_j_());
				}
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _setstack = new ItemStack(CageScorpionItem.block);
					_setstack.setCount((int) 1);
					ItemHandlerHelper.giveItemToPlayer(((PlayerEntity) sourceentity), _setstack);
				}
				if (!entity.world.isRemote())
					entity.remove();
			}
			if (entity instanceof SeaMonsterEntity.CustomEntity) {
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _stktoremove = new ItemStack(CageEmptyItem.block);
					((PlayerEntity) sourceentity).inventory.func_234564_a_(p -> _stktoremove.getItem() == p.getItem(), (int) 1,
							((PlayerEntity) sourceentity).container.func_234641_j_());
				}
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _setstack = new ItemStack(CageSeamonsterItem.block);
					_setstack.setCount((int) 1);
					ItemHandlerHelper.giveItemToPlayer(((PlayerEntity) sourceentity), _setstack);
				}
				if (!entity.world.isRemote())
					entity.remove();
			}
			if (entity instanceof SeaViperEntity.CustomEntity) {
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _stktoremove = new ItemStack(CageEmptyItem.block);
					((PlayerEntity) sourceentity).inventory.func_234564_a_(p -> _stktoremove.getItem() == p.getItem(), (int) 1,
							((PlayerEntity) sourceentity).container.func_234641_j_());
				}
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _setstack = new ItemStack(CageSeaviperItem.block);
					_setstack.setCount((int) 1);
					ItemHandlerHelper.giveItemToPlayer(((PlayerEntity) sourceentity), _setstack);
				}
				if (!entity.world.isRemote())
					entity.remove();
			}
			if (entity instanceof SheepEntity) {
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _stktoremove = new ItemStack(CageEmptyItem.block);
					((PlayerEntity) sourceentity).inventory.func_234564_a_(p -> _stktoremove.getItem() == p.getItem(), (int) 1,
							((PlayerEntity) sourceentity).container.func_234641_j_());
				}
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _setstack = new ItemStack(CageSheepItem.block);
					_setstack.setCount((int) 1);
					ItemHandlerHelper.giveItemToPlayer(((PlayerEntity) sourceentity), _setstack);
				}
				if (!entity.world.isRemote())
					entity.remove();
			}
			if (entity instanceof SilverfishEntity) {
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _stktoremove = new ItemStack(CageEmptyItem.block);
					((PlayerEntity) sourceentity).inventory.func_234564_a_(p -> _stktoremove.getItem() == p.getItem(), (int) 1,
							((PlayerEntity) sourceentity).container.func_234641_j_());
				}
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _setstack = new ItemStack(CageSilverfishItem.block);
					_setstack.setCount((int) 1);
					ItemHandlerHelper.giveItemToPlayer(((PlayerEntity) sourceentity), _setstack);
				}
				if (!entity.world.isRemote())
					entity.remove();
			}
			if (entity instanceof SkateEntity.CustomEntity) {
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _stktoremove = new ItemStack(CageEmptyItem.block);
					((PlayerEntity) sourceentity).inventory.func_234564_a_(p -> _stktoremove.getItem() == p.getItem(), (int) 1,
							((PlayerEntity) sourceentity).container.func_234641_j_());
				}
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _setstack = new ItemStack(CageSkateItem.block);
					_setstack.setCount((int) 1);
					ItemHandlerHelper.giveItemToPlayer(((PlayerEntity) sourceentity), _setstack);
				}
				if (!entity.world.isRemote())
					entity.remove();
			}
			if (entity instanceof SkeletonEntity) {
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _stktoremove = new ItemStack(CageEmptyItem.block);
					((PlayerEntity) sourceentity).inventory.func_234564_a_(p -> _stktoremove.getItem() == p.getItem(), (int) 1,
							((PlayerEntity) sourceentity).container.func_234641_j_());
				}
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _setstack = new ItemStack(CageSkeletonItem.block);
					_setstack.setCount((int) 1);
					ItemHandlerHelper.giveItemToPlayer(((PlayerEntity) sourceentity), _setstack);
				}
				if (!entity.world.isRemote())
					entity.remove();
			}
			if (entity instanceof SlimeEntity) {
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _stktoremove = new ItemStack(CageEmptyItem.block);
					((PlayerEntity) sourceentity).inventory.func_234564_a_(p -> _stktoremove.getItem() == p.getItem(), (int) 1,
							((PlayerEntity) sourceentity).container.func_234641_j_());
				}
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _setstack = new ItemStack(CageSlimeItem.block);
					_setstack.setCount((int) 1);
					ItemHandlerHelper.giveItemToPlayer(((PlayerEntity) sourceentity), _setstack);
				}
				if (!entity.world.isRemote())
					entity.remove();
			}
			if (entity instanceof WormSmallEntity.CustomEntity) {
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _stktoremove = new ItemStack(CageEmptyItem.block);
					((PlayerEntity) sourceentity).inventory.func_234564_a_(p -> _stktoremove.getItem() == p.getItem(), (int) 1,
							((PlayerEntity) sourceentity).container.func_234641_j_());
				}
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _setstack = new ItemStack(CageSmallwormItem.block);
					_setstack.setCount((int) 1);
					ItemHandlerHelper.giveItemToPlayer(((PlayerEntity) sourceentity), _setstack);
				}
				if (!entity.world.isRemote())
					entity.remove();
			}
			if (entity instanceof SnowGolemEntity) {
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _stktoremove = new ItemStack(CageEmptyItem.block);
					((PlayerEntity) sourceentity).inventory.func_234564_a_(p -> _stktoremove.getItem() == p.getItem(), (int) 1,
							((PlayerEntity) sourceentity).container.func_234641_j_());
				}
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _setstack = new ItemStack(CageSnowgolemItem.block);
					_setstack.setCount((int) 1);
					ItemHandlerHelper.giveItemToPlayer(((PlayerEntity) sourceentity), _setstack);
				}
				if (!entity.world.isRemote())
					entity.remove();
			}
			if (entity instanceof SpiderEntity) {
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _stktoremove = new ItemStack(CageEmptyItem.block);
					((PlayerEntity) sourceentity).inventory.func_234564_a_(p -> _stktoremove.getItem() == p.getItem(), (int) 1,
							((PlayerEntity) sourceentity).container.func_234641_j_());
				}
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _setstack = new ItemStack(CageSpiderItem.block);
					_setstack.setCount((int) 1);
					ItemHandlerHelper.giveItemToPlayer(((PlayerEntity) sourceentity), _setstack);
				}
				if (!entity.world.isRemote())
					entity.remove();
			}
			if (entity instanceof SpiderRobotEntity.CustomEntity) {
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _stktoremove = new ItemStack(CageEmptyItem.block);
					((PlayerEntity) sourceentity).inventory.func_234564_a_(p -> _stktoremove.getItem() == p.getItem(), (int) 1,
							((PlayerEntity) sourceentity).container.func_234641_j_());
				}
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _setstack = new ItemStack(CageSpiderdriverItem.block);
					_setstack.setCount((int) 1);
					ItemHandlerHelper.giveItemToPlayer(((PlayerEntity) sourceentity), _setstack);
				}
				if (!entity.world.isRemote())
					entity.remove();
			}
			if (entity instanceof SpitBugEntity.CustomEntity) {
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _stktoremove = new ItemStack(CageEmptyItem.block);
					((PlayerEntity) sourceentity).inventory.func_234564_a_(p -> _stktoremove.getItem() == p.getItem(), (int) 1,
							((PlayerEntity) sourceentity).container.func_234641_j_());
				}
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _setstack = new ItemStack(CageSpitItem.block);
					_setstack.setCount((int) 1);
					ItemHandlerHelper.giveItemToPlayer(((PlayerEntity) sourceentity), _setstack);
				}
				if (!entity.world.isRemote())
					entity.remove();
			}
			if (entity instanceof SpyroEntity.CustomEntity) {
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _stktoremove = new ItemStack(CageEmptyItem.block);
					((PlayerEntity) sourceentity).inventory.func_234564_a_(p -> _stktoremove.getItem() == p.getItem(), (int) 1,
							((PlayerEntity) sourceentity).container.func_234641_j_());
				}
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _setstack = new ItemStack(CageSpyroItem.block);
					_setstack.setCount((int) 1);
					ItemHandlerHelper.giveItemToPlayer(((PlayerEntity) sourceentity), _setstack);
				}
				if (!entity.world.isRemote())
					entity.remove();
			}
			if (entity instanceof SquidEntity) {
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _stktoremove = new ItemStack(CageEmptyItem.block);
					((PlayerEntity) sourceentity).inventory.func_234564_a_(p -> _stktoremove.getItem() == p.getItem(), (int) 1,
							((PlayerEntity) sourceentity).container.func_234641_j_());
				}
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _setstack = new ItemStack(CageSquidItem.block);
					_setstack.setCount((int) 1);
					ItemHandlerHelper.giveItemToPlayer(((PlayerEntity) sourceentity), _setstack);
				}
				if (!entity.world.isRemote())
					entity.remove();
			}
			if (entity instanceof StinkBugEntity.CustomEntity) {
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _stktoremove = new ItemStack(CageEmptyItem.block);
					((PlayerEntity) sourceentity).inventory.func_234564_a_(p -> _stktoremove.getItem() == p.getItem(), (int) 1,
							((PlayerEntity) sourceentity).container.func_234641_j_());
				}
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _setstack = new ItemStack(CageStinkItem.block);
					_setstack.setCount((int) 1);
					ItemHandlerHelper.giveItemToPlayer(((PlayerEntity) sourceentity), _setstack);
				}
				if (!entity.world.isRemote())
					entity.remove();
			}
			if (entity instanceof StinkyEntity.CustomEntity) {
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _stktoremove = new ItemStack(CageEmptyItem.block);
					((PlayerEntity) sourceentity).inventory.func_234564_a_(p -> _stktoremove.getItem() == p.getItem(), (int) 1,
							((PlayerEntity) sourceentity).container.func_234641_j_());
				}
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _setstack = new ItemStack(CageStinkyItem.block);
					_setstack.setCount((int) 1);
					ItemHandlerHelper.giveItemToPlayer(((PlayerEntity) sourceentity), _setstack);
				}
				if (!entity.world.isRemote())
					entity.remove();
			}
			if (entity instanceof TerribleTerrorEntity.CustomEntity) {
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _stktoremove = new ItemStack(CageEmptyItem.block);
					((PlayerEntity) sourceentity).inventory.func_234564_a_(p -> _stktoremove.getItem() == p.getItem(), (int) 1,
							((PlayerEntity) sourceentity).container.func_234641_j_());
				}
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _setstack = new ItemStack(CageTerribleterrorItem.block);
					_setstack.setCount((int) 1);
					ItemHandlerHelper.giveItemToPlayer(((PlayerEntity) sourceentity), _setstack);
				}
				if (!entity.world.isRemote())
					entity.remove();
			}
			if (entity instanceof TRexEntity.CustomEntity) {
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _stktoremove = new ItemStack(CageEmptyItem.block);
					((PlayerEntity) sourceentity).inventory.func_234564_a_(p -> _stktoremove.getItem() == p.getItem(), (int) 1,
							((PlayerEntity) sourceentity).container.func_234641_j_());
				}
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _setstack = new ItemStack(CageTRexItem.block);
					_setstack.setCount((int) 1);
					ItemHandlerHelper.giveItemToPlayer(((PlayerEntity) sourceentity), _setstack);
				}
				if (!entity.world.isRemote())
					entity.remove();
			}
			if (entity instanceof TriffidEntity.CustomEntity) {
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _stktoremove = new ItemStack(CageEmptyItem.block);
					((PlayerEntity) sourceentity).inventory.func_234564_a_(p -> _stktoremove.getItem() == p.getItem(), (int) 1,
							((PlayerEntity) sourceentity).container.func_234641_j_());
				}
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _setstack = new ItemStack(CageTriffidItem.block);
					_setstack.setCount((int) 1);
					ItemHandlerHelper.giveItemToPlayer(((PlayerEntity) sourceentity), _setstack);
				}
				if (!entity.world.isRemote())
					entity.remove();
			}
			if (entity instanceof TrooperBugEntity.CustomEntity) {
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _stktoremove = new ItemStack(CageEmptyItem.block);
					((PlayerEntity) sourceentity).inventory.func_234564_a_(p -> _stktoremove.getItem() == p.getItem(), (int) 1,
							((PlayerEntity) sourceentity).container.func_234641_j_());
				}
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _setstack = new ItemStack(CageTrooperItem.block);
					_setstack.setCount((int) 1);
					ItemHandlerHelper.giveItemToPlayer(((PlayerEntity) sourceentity), _setstack);
				}
				if (!entity.world.isRemote())
					entity.remove();
			}
			if (entity instanceof UrchinEntity.CustomEntity) {
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _stktoremove = new ItemStack(CageEmptyItem.block);
					((PlayerEntity) sourceentity).inventory.func_234564_a_(p -> _stktoremove.getItem() == p.getItem(), (int) 1,
							((PlayerEntity) sourceentity).container.func_234641_j_());
				}
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _setstack = new ItemStack(CageUrchinItem.block);
					_setstack.setCount((int) 1);
					ItemHandlerHelper.giveItemToPlayer(((PlayerEntity) sourceentity), _setstack);
				}
				if (!entity.world.isRemote())
					entity.remove();
			}
			if (entity instanceof VelocityRaptorEntity.CustomEntity) {
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _stktoremove = new ItemStack(CageEmptyItem.block);
					((PlayerEntity) sourceentity).inventory.func_234564_a_(p -> _stktoremove.getItem() == p.getItem(), (int) 1,
							((PlayerEntity) sourceentity).container.func_234641_j_());
				}
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _setstack = new ItemStack(CageVelocityraptorItem.block);
					_setstack.setCount((int) 1);
					ItemHandlerHelper.giveItemToPlayer(((PlayerEntity) sourceentity), _setstack);
				}
				if (!entity.world.isRemote())
					entity.remove();
			}
			if (entity instanceof VillagerEntity) {
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _stktoremove = new ItemStack(CageEmptyItem.block);
					((PlayerEntity) sourceentity).inventory.func_234564_a_(p -> _stktoremove.getItem() == p.getItem(), (int) 1,
							((PlayerEntity) sourceentity).container.func_234641_j_());
				}
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _setstack = new ItemStack(CageVillagerItem.block);
					_setstack.setCount((int) 1);
					ItemHandlerHelper.giveItemToPlayer(((PlayerEntity) sourceentity), _setstack);
				}
				if (!entity.world.isRemote())
					entity.remove();
			}
			if (entity instanceof VortexEntity.CustomEntity) {
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _stktoremove = new ItemStack(CageEmptyItem.block);
					((PlayerEntity) sourceentity).inventory.func_234564_a_(p -> _stktoremove.getItem() == p.getItem(), (int) 1,
							((PlayerEntity) sourceentity).container.func_234641_j_());
				}
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _setstack = new ItemStack(CageVortexItem.block);
					_setstack.setCount((int) 1);
					ItemHandlerHelper.giveItemToPlayer(((PlayerEntity) sourceentity), _setstack);
				}
				if (!entity.world.isRemote())
					entity.remove();
			}
			if (entity instanceof WaterDragonEntity.CustomEntity) {
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _stktoremove = new ItemStack(CageEmptyItem.block);
					((PlayerEntity) sourceentity).inventory.func_234564_a_(p -> _stktoremove.getItem() == p.getItem(), (int) 1,
							((PlayerEntity) sourceentity).container.func_234641_j_());
				}
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _setstack = new ItemStack(CageWaterdragonItem.block);
					_setstack.setCount((int) 1);
					ItemHandlerHelper.giveItemToPlayer(((PlayerEntity) sourceentity), _setstack);
				}
				if (!entity.world.isRemote())
					entity.remove();
			}
			if (entity instanceof WhaleEntity.CustomEntity) {
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _stktoremove = new ItemStack(CageEmptyItem.block);
					((PlayerEntity) sourceentity).inventory.func_234564_a_(p -> _stktoremove.getItem() == p.getItem(), (int) 1,
							((PlayerEntity) sourceentity).container.func_234641_j_());
				}
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _setstack = new ItemStack(CageWhaleItem.block);
					_setstack.setCount((int) 1);
					ItemHandlerHelper.giveItemToPlayer(((PlayerEntity) sourceentity), _setstack);
				}
				if (!entity.world.isRemote())
					entity.remove();
			}
			if (entity instanceof WitchEntity) {
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _stktoremove = new ItemStack(CageEmptyItem.block);
					((PlayerEntity) sourceentity).inventory.func_234564_a_(p -> _stktoremove.getItem() == p.getItem(), (int) 1,
							((PlayerEntity) sourceentity).container.func_234641_j_());
				}
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _setstack = new ItemStack(CageWitchItem.block);
					_setstack.setCount((int) 1);
					ItemHandlerHelper.giveItemToPlayer(((PlayerEntity) sourceentity), _setstack);
				}
				if (!entity.world.isRemote())
					entity.remove();
			}
			if (entity instanceof WitherEntity) {
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _stktoremove = new ItemStack(CageEmptyItem.block);
					((PlayerEntity) sourceentity).inventory.func_234564_a_(p -> _stktoremove.getItem() == p.getItem(), (int) 1,
							((PlayerEntity) sourceentity).container.func_234641_j_());
				}
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _setstack = new ItemStack(CageWitherbossItem.block);
					_setstack.setCount((int) 1);
					ItemHandlerHelper.giveItemToPlayer(((PlayerEntity) sourceentity), _setstack);
				}
				if (!entity.world.isRemote())
					entity.remove();
			}
			if (entity instanceof WitherSkeletonEntity) {
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _stktoremove = new ItemStack(CageEmptyItem.block);
					((PlayerEntity) sourceentity).inventory.func_234564_a_(p -> _stktoremove.getItem() == p.getItem(), (int) 1,
							((PlayerEntity) sourceentity).container.func_234641_j_());
				}
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _setstack = new ItemStack(CageWitherskeletonItem.block);
					_setstack.setCount((int) 1);
					ItemHandlerHelper.giveItemToPlayer(((PlayerEntity) sourceentity), _setstack);
				}
				if (!entity.world.isRemote())
					entity.remove();
			}
			if (entity instanceof WolfEntity) {
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _stktoremove = new ItemStack(CageEmptyItem.block);
					((PlayerEntity) sourceentity).inventory.func_234564_a_(p -> _stktoremove.getItem() == p.getItem(), (int) 1,
							((PlayerEntity) sourceentity).container.func_234641_j_());
				}
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _setstack = new ItemStack(CageWolfItem.block);
					_setstack.setCount((int) 1);
					ItemHandlerHelper.giveItemToPlayer(((PlayerEntity) sourceentity), _setstack);
				}
				if (!entity.world.isRemote())
					entity.remove();
			}
			if (entity instanceof ZombieEntity) {
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _stktoremove = new ItemStack(CageEmptyItem.block);
					((PlayerEntity) sourceentity).inventory.func_234564_a_(p -> _stktoremove.getItem() == p.getItem(), (int) 1,
							((PlayerEntity) sourceentity).container.func_234641_j_());
				}
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _setstack = new ItemStack(CageZombieItem.block);
					_setstack.setCount((int) 1);
					ItemHandlerHelper.giveItemToPlayer(((PlayerEntity) sourceentity), _setstack);
				}
				if (!entity.world.isRemote())
					entity.remove();
			}
			if (entity instanceof ZombifiedPiglinEntity) {
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _stktoremove = new ItemStack(CageEmptyItem.block);
					((PlayerEntity) sourceentity).inventory.func_234564_a_(p -> _stktoremove.getItem() == p.getItem(), (int) 1,
							((PlayerEntity) sourceentity).container.func_234641_j_());
				}
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _setstack = new ItemStack(CageZombiepigmanItem.block);
					_setstack.setCount((int) 1);
					ItemHandlerHelper.giveItemToPlayer(((PlayerEntity) sourceentity), _setstack);
				}
				if (!entity.world.isRemote())
					entity.remove();
			}
		}
	}
}
