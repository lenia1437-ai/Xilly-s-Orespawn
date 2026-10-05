package net.mcreator.xillysorespawn.entity;

import java.util.List;

import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.network.IPacket;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.mcreator.xillysorespawn.XillysOrespawnModElements;
import net.mcreator.xillysorespawn.entity.renderer.TermiteRenderer;

@XillysOrespawnModElements.ModElement.Tag
public class TermiteEntity extends XillysOrespawnModElements.ModElement {
    public static final EntityType entity = EntityType.Builder
        .<CustomEntity>create(CustomEntity::new, EntityClassification.CREATURE)
        .setShouldReceiveVelocityUpdates(true).setTrackingRange(32).setUpdateInterval(3)
        .setCustomClientFactory(CustomEntity::new).size(0.2F, 0.2F)
        .build("termite").setRegistryName("termite");
    public TermiteEntity(XillysOrespawnModElements instance) { super(instance, 1);
        FMLJavaModLoadingContext.get().getModEventBus().register(new TermiteRenderer.ModelRegisterHandler());
        FMLJavaModLoadingContext.get().getModEventBus().register(new AttributesHandler()); }
    @Override public void initElements() { elements.entities.add(() -> entity); elements.items.add(() -> new SpawnEggItem(entity, 0xDED6BD, 0x6A5D42,
        new Item.Properties().group(ItemGroup.MISC)).setRegistryName("termite_spawn_egg")); }
    public static class AttributesHandler { @SubscribeEvent public void register(EntityAttributeCreationEvent event) { event.put(entity, CustomEntity.attributes().create()); } }

    public static class CustomEntity extends OreSpawnAntBase {
        private int woodSearchCooldown;
        public CustomEntity(FMLPlayMessages.SpawnEntity packet, World world) { this(entity, world); }
        public CustomEntity(EntityType<? extends CustomEntity> type, World world) { super(type, world); this.experienceValue = 1; }
        static AttributeModifierMap.MutableAttribute attributes() { return AnimalEntity.func_233666_p_()
            .createMutableAttribute(Attributes.MAX_HEALTH, 5.0D).createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.2D)
            .createMutableAttribute(Attributes.ATTACK_DAMAGE, 2.0D).createMutableAttribute(Attributes.FOLLOW_RANGE, 16.0D); }
        @Override protected void registerGoals() { super.registerGoals(); this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0D, false));
            if (OreSpawnLogic.playNicely == 0) this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, 6, true, false,
                p -> !(p instanceof PlayerEntity) || !((PlayerEntity)p).abilities.isCreativeMode)); }
        @Override protected boolean randomlyBitesPlayers() { return true; }
        @Override protected boolean needsEmptyInventory() { return true; }
        @Override protected String[] destinationDimensions() { return new String[]{"dimension_crystal", "crystal_dimension", "crystal", "termite_dimension"}; }
        @Override public IPacket<?> createSpawnPacket() { return NetworkHooks.getEntitySpawningPacket(this); }

        private boolean isWood(Block block) {
            Block crystalPlanks = ForgeRegistries.BLOCKS.getValue(new ResourceLocation("xillys_orespawn", "crystal_planks"));
            return block.isIn(BlockTags.LOGS) || block.isIn(BlockTags.PLANKS)
                || block == Blocks.BOOKSHELF || block == Blocks.CRAFTING_TABLE
                || block == Blocks.CHEST || block == Blocks.TRAPPED_CHEST
                || block == Blocks.JUKEBOX || block == Blocks.NOTE_BLOCK
                || block == Blocks.BARREL || block == Blocks.LOOM
                || block == Blocks.CARTOGRAPHY_TABLE || block == Blocks.FLETCHING_TABLE
                || block == Blocks.SMITHING_TABLE || block == crystalPlanks;
        }

        private BlockPos nearestWood() {
            BlockPos origin = this.getPosition();
            BlockPos best = null;
            double bestDistance = Double.MAX_VALUE;
            for (int radius = 1; radius < 8; radius++) {
                int yRadius = Math.min(radius, 4);
                for (int x = -radius; x <= radius; x++) for (int y = -yRadius; y <= yRadius; y++)
                    for (int z = -radius; z <= radius; z++) {
                        if (Math.abs(x) != radius && Math.abs(z) != radius && Math.abs(y) != yRadius) continue;
                        BlockPos pos = origin.add(x, y, z);
                        if (!this.isWood(this.world.getBlockState(pos).getBlock())) continue;
                        double distance = origin.distanceSq(pos);
                        if (distance < bestDistance) { bestDistance = distance; best = pos; }
                    }
                if (best != null) return best;
            }
            return null;
        }

        @Override public void livingTick() {
            super.livingTick();
            if (this.world.isRemote || OreSpawnLogic.playNicely != 0 || --this.woodSearchCooldown > 0) return;
            this.woodSearchCooldown = 200;
            BlockPos wood = this.nearestWood();
            if (wood == null) return;
            this.getNavigator().tryMoveToXYZ(wood.getX() + 0.5D, wood.getY(), wood.getZ() + 0.5D, 1.0D);
            if (this.getDistanceSq(wood.getX() + 0.5D, wood.getY() + 0.5D, wood.getZ() + 0.5D) >= 6.0D) return;
            boolean vanished = this.rand.nextInt(3) == 0;
            if (this.world.getGameRules().getBoolean(GameRules.MOB_GRIEFING))
                this.world.setBlockState(wood, vanished ? Blocks.AIR.getDefaultState() : Blocks.DIRT.getDefaultState(), 2);
            List<CustomEntity> nearby = this.world.getEntitiesWithinAABB(CustomEntity.class,
                this.getBoundingBox().grow(3.0D), e -> e.isAlive());
            if (nearby.size() < 10) {
                // OreSpawn spawned the 2/3 dirt replacement beside the parent,
                // and the 1/3 fully eaten block at the consumed block itself.
                OreSpawnLogic.spawn(this.world, "termite",
                    vanished ? wood.getX() + 0.1D : this.getPosX() + 0.1D,
                    vanished ? wood.getY() + 0.1D : this.getPosY() + 0.1D,
                    vanished ? wood.getZ() + 0.1D : this.getPosZ() + 0.1D);
            }
            this.heal(1.0F);
        }
    }
}
