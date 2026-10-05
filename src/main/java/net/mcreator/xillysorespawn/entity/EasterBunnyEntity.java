package net.mcreator.xillysorespawn.entity;

import java.util.Random;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.fml.network.NetworkHooks;

import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.entity.ai.goal.BreedGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.PanicGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.IPacket;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.server.ServerWorld;

import net.mcreator.xillysorespawn.XillysOrespawnModElements;
import net.mcreator.xillysorespawn.entity.renderer.EasterBunnyRenderer;

@XillysOrespawnModElements.ModElement.Tag
public class EasterBunnyEntity extends XillysOrespawnModElements.ModElement {
    public static EntityType entity = (EntityType.Builder.<CustomEntity>create(CustomEntity::new, EntityClassification.CREATURE)
        .setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3)
        .setCustomClientFactory(CustomEntity::new).size(0.5F, 0.75F))
        .build("easter_bunny").setRegistryName("easter_bunny");

    public EasterBunnyEntity(XillysOrespawnModElements instance) {
        super(instance, 19);
        FMLJavaModLoadingContext.get().getModEventBus().register(new EasterBunnyRenderer.ModelRegisterHandler());
        FMLJavaModLoadingContext.get().getModEventBus().register(new EntityAttributesRegisterHandler());
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public void initElements() {
        elements.entities.add(() -> entity);
        elements.items.add(() -> new net.minecraft.item.SpawnEggItem(entity, -1, -1,
            new net.minecraft.item.Item.Properties().group(net.minecraft.item.ItemGroup.MISC))
            .setRegistryName("easter_bunny_spawn_egg"));
    }

    @SubscribeEvent
    public void addFeatureToBiomes(BiomeLoadingEvent event) {
        if (!OreSpawnLogic.allowNaturalSpawn(event, "easter_bunny")) return;
        event.getSpawns().getSpawner(EntityClassification.CREATURE)
            .add(new MobSpawnInfo.Spawners(entity, 1, 1, 1));
    }

    @Override
    public void init(FMLCommonSetupEvent event) {
        EntitySpawnPlacementRegistry.register(entity, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND,
            Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, EasterBunnyEntity::canSpawn);
    }

    public static boolean canSpawn(EntityType<? extends AnimalEntity> type, IServerWorld world,
            SpawnReason reason, BlockPos pos, Random random) {
        if (reason == SpawnReason.SPAWNER || reason == SpawnReason.SPAWN_EGG || reason == SpawnReason.COMMAND) {
            return true;
        }
        if (pos.getY() < 50 || !world.getWorld().isDaytime()) {
            return false;
        }
        AxisAlignedBB area = new AxisAlignedBB(pos).grow(32.0D, 8.0D, 32.0D);
        return world.getEntitiesWithinAABB(CustomEntity.class, area, e -> true).isEmpty();
    }

    public static class EntityAttributesRegisterHandler {
        @SubscribeEvent
        public void onEntityAttributeCreation(EntityAttributeCreationEvent event) {
            event.put(entity, MobEntity.func_233666_p_()
                .createMutableAttribute(Attributes.MAX_HEALTH, 10.0D)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.45D)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 8.0D)
                .createMutableAttribute(Attributes.FOLLOW_RANGE, 16.0D).create());
        }
    }

    public static class CustomEntity extends AnimalEntity {
        private static final String[] EGG_TYPES = {
            "girlfriend", "red_cow", "gold_cow", "enchanted_cow", "mothra", "alosaurus",
            "cryolophosaurus", "camarasaurus", "velocity_raptor", "hydrolisc", "basilisk", "dragonfly",
            "emperor_scorpion", "scorpion", "cave_fisher", "spyro", "baryonyx", "gamma_metroid",
            "cockateil", "kyuubi", "alien", "attack_squid", "water_dragon", "cephadrome", "dragon",
            "kraken", "lizard", "bee", "trooper_bug", "spit_bug", "stink_bug", "ostrich", "gazelle",
            "chipmunk", "creeping_horror", "terrible_terror", "cliff_racer", "triffid", "pitch_black",
            "lurking_terror", "mobzilla", "worm_small", "worm_medium", "worm_large", "cassowary",
            "cloud_shark", "gold_fish", "leaf_monster", "tshirt", "ender_knight", "ender_reaper",
            "beaver", "rotator", "vortex", "peacock", "fairy", "dungeon_beast", "rat", "flounder",
            "whale", "irukandji", "skate", "urchin", "robot_1", "robot_2", "robot_3", "robot_4",
            "ghost", "ghost_skelly", "brown_ant", "red_ant", "rainbow_ant", "unstable_ant", "termite",
            "butterfly", "moth", "mosquito", "firefly", "t_rex", "hercules_beetle", "mantis", "stinky",
            "robot_5", "coin", "boyfriend", "the_king", "the_prince", "easter_bunny", "molenoid",
            "sea_monster", "sea_viper", "cater_killer", "leonopteryx", "hammerhead", "rubber_ducky",
            "crystal_cow", "criminal", "the_queen", "brutalfly", "nastysaurus", "pointysaurus", "cricket",
            "the_princess", "frog", "jeffery", "ant_robot", "spider_robot", "spider_driver", "crab"
        };

        public CustomEntity(FMLPlayMessages.SpawnEntity packet, World world) {
            this(entity, world);
        }

        public CustomEntity(EntityType<? extends CustomEntity> type, World world) {
            super(type, world);
            this.experienceValue = 5;
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
            this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, MonsterEntity.class, 8.0F, 1.0D, 1.4D));
            this.goalSelector.addGoal(3, new AvoidEntityGoal<>(this, PlayerEntity.class, 8.0F, 1.0D, 1.4D));
            this.goalSelector.addGoal(4, new PanicGoal(this, 1.5D));
            this.goalSelector.addGoal(5, new LookAtGoal(this, LivingEntity.class, 8.0F));
            this.goalSelector.addGoal(6, new OreSpawnWanderGoal(this, 16, 30, 1.0D, false));
            this.goalSelector.addGoal(7, new LookRandomlyGoal(this));
        }

        private void layEgg(int count) {
            int roll = this.rand.nextInt(115);
            if (roll < 5 || roll > 113) {
                return;
            }
            String type = EGG_TYPES[roll - 5];
            Item egg = OreSpawnLogic.itemOrNull(type + "_spawn_egg", type + "_egg");
            if (egg != null) {
                OreSpawnLogic.drop(this.world, this, egg, count, 2);
            }
        }

        @Override
        public void livingTick() {
            super.livingTick();
            if (!this.world.isRemote) {
                if (this.rand.nextInt(200) == 1) {
                    this.setAttackTarget(null);
                }
                if (this.rand.nextInt(600) == 1) {
                    this.layEgg(1 + this.rand.nextInt(3));
                }
            }
        }

        @Override
        public AgeableEntity func_241840_a(ServerWorld world, AgeableEntity mate) {
            return (AgeableEntity) entity.create(world);
        }

        @Override
        public boolean isBreedingItem(ItemStack stack) {
            return !stack.isEmpty() && stack.getItem() == OreSpawnLogic.item("crystal_apple", "my_crystal_apple");
        }

        public boolean isWheat(ItemStack stack) {
            return !stack.isEmpty() && stack.getItem() == Items.APPLE;
        }

        @Override
        protected SoundEvent getAmbientSound() {
            return null;
        }

        @Override
        protected SoundEvent getHurtSound(DamageSource source) {
            return OreSpawnLogic.sound("duck_hurt", SoundEvents.ENTITY_RABBIT_HURT);
        }

        @Override
        protected SoundEvent getDeathSound() {
            return OreSpawnLogic.sound("duck_hurt", SoundEvents.ENTITY_RABBIT_DEATH);
        }

        @Override
        protected float getSoundVolume() {
            return 0.4F;
        }

        @Override
        protected void dropSpecialItems(DamageSource source, int looting, boolean recentlyHit) {
            super.dropSpecialItems(source, looting, recentlyHit);
            int count = this.rand.nextInt(3) + 2;
            for (int i = 0; i < count; i++) {
                this.entityDropItem(Items.CHICKEN);
            }
        }
    }
}
