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

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.MoveThroughVillageGoal;
import net.minecraft.entity.ai.goal.PanicGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.network.IPacket;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.Heightmap;

import net.mcreator.xillysorespawn.XillysOrespawnModElements;
import net.mcreator.xillysorespawn.entity.renderer.CreepingHorrorRenderer;

@XillysOrespawnModElements.ModElement.Tag
public class CreepingHorrorEntity extends XillysOrespawnModElements.ModElement {
    public static EntityType entity = (EntityType.Builder.<CustomEntity>create(CustomEntity::new, EntityClassification.MONSTER)
        .setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3)
        .setCustomClientFactory(CustomEntity::new).size(0.75F, 0.5F))
        .build("creeping_horror").setRegistryName("creeping_horror");

    public CreepingHorrorEntity(XillysOrespawnModElements instance) {
        super(instance, 13);
        FMLJavaModLoadingContext.get().getModEventBus().register(new CreepingHorrorRenderer.ModelRegisterHandler());
        FMLJavaModLoadingContext.get().getModEventBus().register(new EntityAttributesRegisterHandler());
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public void initElements() {
        elements.entities.add(() -> entity);
        elements.items.add(() -> new net.minecraft.item.SpawnEggItem(entity, -1, -1,
            new net.minecraft.item.Item.Properties().group(net.minecraft.item.ItemGroup.MISC))
            .setRegistryName("creeping_horror_spawn_egg"));
    }

    @SubscribeEvent
    public void addFeatureToBiomes(BiomeLoadingEvent event) {
        if (!OreSpawnLogic.allowNaturalSpawn(event, "creeping_horror")) return;
        event.getSpawns().getSpawner(EntityClassification.MONSTER)
            .add(new MobSpawnInfo.Spawners(entity, 8, 1, 3));
    }

    @Override
    public void init(FMLCommonSetupEvent event) {
        EntitySpawnPlacementRegistry.register(entity, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND,
            Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, CreepingHorrorEntity::canSpawn);
    }

    public static boolean canSpawn(EntityType<? extends MonsterEntity> type, IServerWorld world,
            SpawnReason reason, BlockPos pos, Random random) {
        if (reason == SpawnReason.SPAWNER || reason == SpawnReason.SPAWN_EGG || reason == SpawnReason.COMMAND) return true;
        if (world.getWorld().isDaytime() || !MonsterEntity.canMonsterSpawnInLight(type, world, reason, pos, random)) return false;
        return OreSpawnLogic.isChaosDimension(world.getWorld()) || pos.getY() <= 15;
    }

    public static class EntityAttributesRegisterHandler {
        @SubscribeEvent
        public void onEntityAttributeCreation(EntityAttributeCreationEvent event) {
            event.put(entity, MobEntity.func_233666_p_()
                .createMutableAttribute(Attributes.MAX_HEALTH, 10.0D)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.25D)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 3.0D)
                .createMutableAttribute(Attributes.ARMOR, 2.0D)
                .createMutableAttribute(Attributes.FOLLOW_RANGE, 24.0D).create());
        }
    }

    public static class CustomEntity extends MonsterEntity {
        public CustomEntity(FMLPlayMessages.SpawnEntity packet, World world) { this(entity, world); }

        public CustomEntity(EntityType<? extends CustomEntity> type, World world) {
            super(type, world);
            this.experienceValue = 5;
        }

        @Override public IPacket<?> createSpawnPacket() { return NetworkHooks.getEntitySpawningPacket(this); }

        public boolean isChildModel() { return false; }

        @Override
        protected void registerGoals() {
            super.registerGoals();
            this.goalSelector.addGoal(0, new SwimGoal(this));
            this.goalSelector.addGoal(1, new PanicGoal(this, 1.35D));
            this.goalSelector.addGoal(2, new MoveThroughVillageGoal(this, 1.0D, false, 4, () -> false));
            this.goalSelector.addGoal(3, new OreSpawnWanderGoal(this, 10, 30, 1.0D, false));
            this.goalSelector.addGoal(4, new LookAtGoal(this, PlayerEntity.class, 8.0F));
            this.goalSelector.addGoal(5, new LookRandomlyGoal(this));
            this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        }

        private boolean isOriginalTarget(LivingEntity target) {
            if (!this.getEntitySenses().canSee(target)) return false;
            if (OreSpawnLogic.isNamed(target, "creeping_horror", "rock", "rock_base", "ender_reaper",
                    "leaf_monster", "dragon", "terrible_terror", "lurking_terror", "pitch_black", "firefly",
                    "island", "island_too")) return false;
            return !(target instanceof PlayerEntity) || !((PlayerEntity) target).abilities.isCreativeMode;
        }

        private void tickOriginalCombat() {
            if (this.rand.nextInt(200) == 1) this.setAttackTarget(null);
            if (this.rand.nextInt(5) != 1 || OreSpawnLogic.playNicely != 0) return;
            LivingEntity target = OreSpawnLogic.nearestTarget(this, this.getBoundingBox().grow(16.0D, 4.0D, 16.0D),
                this::isOriginalTarget);
            if (target != null) {
                this.getNavigator().tryMoveToEntityLiving(target, 1.25D);
                if (this.getDistanceSq(target) < 5.0D
                        && (this.rand.nextInt(12) == 0 || this.rand.nextInt(14) == 1)) {
                    this.attackEntityAsMob(target);
                }
            }
        }

        @Override
        public void livingTick() {
            super.livingTick();
            if (!this.world.isRemote) {
                if (!this.isNoDespawnRequired() && this.world.getDayTime() % 24000L <= 11000L
                        && this.rand.nextInt(500) == 1) {
                    this.remove();
                    return;
                }
                this.tickOriginalCombat();
            }
        }

        @Override protected SoundEvent getAmbientSound() {
            return OreSpawnLogic.sound("creepinghorror_living", SoundEvents.ENTITY_SILVERFISH_AMBIENT);
        }

        @Override protected SoundEvent getHurtSound(DamageSource source) {
            return OreSpawnLogic.sound("creepinghorror_hit", SoundEvents.ENTITY_SILVERFISH_HURT);
        }

        @Override protected SoundEvent getDeathSound() {
            return OreSpawnLogic.sound("creepinghorror_dead", SoundEvents.ENTITY_SILVERFISH_DEATH);
        }

        @Override protected float getSoundVolume() { return 0.65F; }

        @Override
        protected void dropSpecialItems(DamageSource source, int looting, boolean recentlyHit) {
            super.dropSpecialItems(source, looting, recentlyHit);
            int count = this.rand.nextInt(3);
            if (looting > 0) count += this.rand.nextInt(looting + 1);
            for (int i = 0; i < count; i++) {
                int roll = this.rand.nextInt(3);
                Item drop = roll == 0 ? Items.ROTTEN_FLESH : roll == 1 ? Items.BONE : Items.STRING;
                this.entityDropItem(drop);
            }
        }
    }
}
