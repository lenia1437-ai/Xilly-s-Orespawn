package net.mcreator.xillysorespawn.item;

import net.mcreator.xillysorespawn.XillysOrespawnModElements;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.SpriteRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IRendersAsItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.SnowballEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.world.Explosion;

import java.util.HashMap;
import java.util.Map;

/** Shared original item behaviour which cannot be represented by MCreator 2022.2 procedures. */
@XillysOrespawnModElements.ModElement.Tag
public class OreSpawnGameplayEvents extends XillysOrespawnModElements.ModElement {
    @SuppressWarnings("unchecked")
    public static final EntityType<ThrownItemEntity> THROWN = (EntityType<ThrownItemEntity>) (EntityType<?>) EntityType.Builder
        .<ThrownItemEntity>create(ThrownItemEntity::new, EntityClassification.MISC)
        .setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(1)
        .setCustomClientFactory(ThrownItemEntity::new).size(0.35F, 0.35F)
        .build("orespawn_thrown_item").setRegistryName("orespawn_thrown_item");

    private static final Map<String, Integer> THROW_DAMAGE = new HashMap<>();
    static {
        THROW_DAMAGE.put("rock_small", 2); THROW_DAMAGE.put("rock", 4);
        THROW_DAMAGE.put("rock_red", 5); THROW_DAMAGE.put("rock_green", 5);
        THROW_DAMAGE.put("rock_blue", 5); THROW_DAMAGE.put("rock_purple", 5);
        THROW_DAMAGE.put("rock_spikey", 8); THROW_DAMAGE.put("rock_tnt", 10);
        THROW_DAMAGE.put("rock_crystal_red", 9); THROW_DAMAGE.put("rock_crystal_green", 9);
        THROW_DAMAGE.put("rock_crystal_blue", 9); THROW_DAMAGE.put("rock_crystal_tnt", 14);
        THROW_DAMAGE.put("boots", 3); THROW_DAMAGE.put("slippers", 6);
        THROW_DAMAGE.put("black_heels", 7); THROW_DAMAGE.put("red_heels", 7);
        THROW_DAMAGE.put("gamecontroller", 5);
    }

    public OreSpawnGameplayEvents(XillysOrespawnModElements instance) {
        super(instance, 2);
        MinecraftForge.EVENT_BUS.register(this);
        FMLJavaModLoadingContext.get().getModEventBus().register(new RenderRegistration());
    }
    @Override public void initElements() { elements.entities.add(() -> THROWN); }

    @SubscribeEvent
    public void rightClick(PlayerInteractEvent.RightClickItem event) {
        PlayerEntity player = event.getPlayer();
        ItemStack stack = event.getItemStack();
        ResourceLocation key = stack.getItem().getRegistryName();
        if (key == null || !"xillys_orespawn".equals(key.getNamespace())) return;
        String id = key.getPath();
        if ("ray_gun".equals(id)) {
            launch(player, stack, "minecraft:redstone", 20, 3.0F, true, event.getHand());
            event.setCanceled(true);
            return;
        }
        if ("thunder_staff".equals(id)) {
            launch(player, stack, "xillys_orespawn:thunder_staff", 8, 3.0F, false, event.getHand());
            event.setCanceled(true);
            return;
        }
        Integer damage = THROW_DAMAGE.get(id);
        if (damage != null) {
            launch(player, stack, key.toString(), damage, 1.5F, id.contains("tnt"), event.getHand());
            if (!player.abilities.isCreativeMode) stack.shrink(1);
            event.setCanceled(true);
        }
    }

    private static void launch(PlayerEntity player, ItemStack stack, String shownItem, int damage,
                               float speed, boolean explosive, Hand hand) {
        World world = player.world;
        world.playSound(null, player.getPosX(), player.getPosY(), player.getPosZ(), SoundEvents.ENTITY_SNOWBALL_THROW,
            SoundCategory.PLAYERS, 0.7F, 0.85F + world.rand.nextFloat() * 0.25F);
        if (!world.isRemote) {
            ThrownItemEntity projectile = new ThrownItemEntity(THROWN, player, world);
            projectile.configure(shownItem, damage, explosive);
            if ("thunder_staff".equals(stack.getItem().getRegistryName().getPath())) projectile.setLightning();
            projectile.func_234612_a_(player, player.rotationPitch, player.rotationYaw, 0F, speed, 0.5F);
            world.addEntity(projectile);
            if ("ray_gun".equals(stack.getItem().getRegistryName().getPath()) && !player.abilities.isCreativeMode)
                stack.damageItem(1, player, p -> p.sendBreakAnimation(hand));
            if ("thunder_staff".equals(stack.getItem().getRegistryName().getPath()) && !player.abilities.isCreativeMode)
                stack.damageItem(1, player, p -> p.sendBreakAnimation(hand));
            if ("ray_gun".equals(stack.getItem().getRegistryName().getPath())) {
                Vector3d recoil = player.getLookVec().scale(-1.5D);
                player.addVelocity(recoil.x, 0.3D, recoil.z);
                player.velocityChanged = true;
            }
        }
        player.getCooldownTracker().setCooldown(stack.getItem(), "ray_gun".equals(stack.getItem().getRegistryName().getPath()) ? 5 : 8);
    }

    @SubscribeEvent
    public void placeZoo(PlayerInteractEvent.RightClickBlock event) {
        ResourceLocation key = event.getItemStack().getItem().getRegistryName();
        if (key == null || !"xillys_orespawn".equals(key.getNamespace()) || !key.getPath().startsWith("zoo_")) return;
        int size;
        try { size = Integer.parseInt(key.getPath().substring(4)); } catch (NumberFormatException ignored) { return; }
        if (!(event.getWorld() instanceof ServerWorld)) return;
        ServerWorld world = (ServerWorld) event.getWorld();
        BlockPos base = event.getPos().offset(event.getFace());
        int height = Math.max(3, size / 2 + 2);
        for (int x = 0; x < size; x++) for (int z = 0; z < size; z++) for (int y = 0; y <= height; y++) {
            boolean floorOrRoof = y == 0 || y == height;
            boolean wall = x == 0 || z == 0 || x == size - 1 || z == size - 1;
            BlockPos at = base.add(x - size / 2, y, z - size / 2);
            if (floorOrRoof) world.setBlockState(at, Blocks.QUARTZ_BLOCK.getDefaultState(), 3);
            else if (wall) world.setBlockState(at, Blocks.GLASS.getDefaultState(), 3);
            else world.setBlockState(at, Blocks.AIR.getDefaultState(), 3);
        }
        // Two-block doorway facing the player.
        world.setBlockState(base.add(0, 1, -size / 2), Blocks.AIR.getDefaultState(), 3);
        world.setBlockState(base.add(0, 2, -size / 2), Blocks.AIR.getDefaultState(), 3);
        if (!event.getPlayer().abilities.isCreativeMode) event.getItemStack().shrink(1);
        event.setCanceled(true);
    }

    @SubscribeEvent
    public void glide(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END || event.player.world.isRemote) return;
        PlayerEntity player = event.player;
        ItemStack boots = player.getItemStackFromSlot(net.minecraft.inventory.EquipmentSlotType.FEET);
        ResourceLocation id = boots.getItem().getRegistryName();
        if (id == null || !"xillys_orespawn".equals(id.getNamespace())) return;
        double limit = "royal_armor_boots".equals(id.getPath()) ? -0.1D
            : ("queen_armor_boots".equals(id.getPath()) ? -0.25D : -100.0D);
        if (limit == -100.0D || player.isOnGround() || player.abilities.isFlying) return;
        Vector3d motion = player.getMotion();
        if (motion.y < limit) player.setMotion(motion.x, limit, motion.z);
        player.fallDistance = 0.0F;
    }

    @SubscribeEvent
    public void keepOriginalEnchantments(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END || event.player.world.isRemote || event.player.ticksExisted % 40 != 0) return;
        for (ItemStack stack : event.player.inventory.mainInventory) {
            ResourceLocation id = stack.getItem().getRegistryName();
            if (id == null || !"xillys_orespawn".equals(id.getNamespace())) continue;
            String name = id.getPath();
            if ("emerald_pickaxe".equals(name)) add(stack, Enchantments.SILK_TOUCH, 1);
            if (name.startsWith("ultimate_")) {
                add(stack, Enchantments.UNBREAKING, 3);
                if (name.endsWith("sword")) {
                    add(stack, Enchantments.SHARPNESS, 5); add(stack, Enchantments.SMITE, 5);
                    add(stack, Enchantments.BANE_OF_ARTHROPODS, 5); add(stack, Enchantments.LOOTING, 3);
                    add(stack, Enchantments.FIRE_ASPECT, 2); add(stack, Enchantments.KNOCKBACK, 2);
                } else if (name.endsWith("bow")) {
                    add(stack, Enchantments.POWER, 5); add(stack, Enchantments.PUNCH, 2);
                    add(stack, Enchantments.FLAME, 1); add(stack, Enchantments.INFINITY, 1);
                } else if (name.contains("pickaxe") || name.contains("axe") || name.contains("shovel")) {
                    add(stack, Enchantments.EFFICIENCY, 5); add(stack, Enchantments.FORTUNE, 3);
                } else if (name.contains("helmet")) {
                    armor(stack); add(stack, Enchantments.RESPIRATION, 3); add(stack, Enchantments.AQUA_AFFINITY, 1);
                } else if (name.contains("boots")) {
                    armor(stack); add(stack, Enchantments.FEATHER_FALLING, 4);
                } else if (name.contains("chestplate") || name.contains("leggings")) armor(stack);
            }
            if ("queen_battle_axe".equals(name)) {
                add(stack, Enchantments.LOOTING, 3); add(stack, Enchantments.UNBREAKING, 3);
            }
        }
    }

    private static void armor(ItemStack s) {
        add(s, Enchantments.PROTECTION, 5); add(s, Enchantments.FIRE_PROTECTION, 5);
        add(s, Enchantments.BLAST_PROTECTION, 5); add(s, Enchantments.PROJECTILE_PROTECTION, 5);
        add(s, Enchantments.THORNS, 3);
    }
    private static void add(ItemStack s, Enchantment e, int level) {
        if (EnchantmentHelper.getEnchantmentLevel(e, s) < level) s.addEnchantment(e, level);
    }

    public static class ThrownItemEntity extends SnowballEntity implements IRendersAsItem {
        private static final DataParameter<String> ITEM = EntityDataManager.createKey(ThrownItemEntity.class, DataSerializers.STRING);
        private static final DataParameter<Integer> DAMAGE = EntityDataManager.createKey(ThrownItemEntity.class, DataSerializers.VARINT);
        private static final DataParameter<Boolean> EXPLOSIVE = EntityDataManager.createKey(ThrownItemEntity.class, DataSerializers.BOOLEAN);
        private static final DataParameter<Boolean> LIGHTNING = EntityDataManager.createKey(ThrownItemEntity.class, DataSerializers.BOOLEAN);
        public ThrownItemEntity(FMLPlayMessages.SpawnEntity packet, World world) { this(THROWN, world); }
        public ThrownItemEntity(EntityType<? extends SnowballEntity> type, World world) { super(type, world); }
        public ThrownItemEntity(EntityType<? extends SnowballEntity> type, LivingEntity owner, World world) {
            super(type, world);
            setShooter(owner);
            setPosition(owner.getPosX(), owner.getPosYEye() - 0.1D, owner.getPosZ());
        }
        @Override protected void registerData() {
            super.registerData(); dataManager.register(ITEM, "minecraft:stone");
            dataManager.register(DAMAGE, 4); dataManager.register(EXPLOSIVE, false);
            dataManager.register(LIGHTNING, false);
        }
        void configure(String item, int damage, boolean explosive) {
            dataManager.set(ITEM, item); dataManager.set(DAMAGE, damage); dataManager.set(EXPLOSIVE, explosive);
        }
        void setLightning() { dataManager.set(LIGHTNING, true); }
        @Override public ItemStack getItem() {
            Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(dataManager.get(ITEM)));
            return item == null ? new ItemStack(Blocks.STONE) : new ItemStack(item);
        }
        @Override protected void onImpact(RayTraceResult result) {
            Entity owner = func_234616_v_();
            if (result instanceof EntityRayTraceResult) {
                ((EntityRayTraceResult) result).getEntity().attackEntityFrom(DamageSource.causeThrownDamage(this, owner), dataManager.get(DAMAGE));
            }
            if (!world.isRemote) {
                if (dataManager.get(LIGHTNING)) {
                    LightningBoltEntity bolt = EntityType.LIGHTNING_BOLT.create(world);
                    if (bolt != null) {
                        bolt.setPosition(getPosX(), getPosY(), getPosZ());
                        world.addEntity(bolt);
                    }
                }
                if (dataManager.get(EXPLOSIVE)) world.createExplosion(owner, getPosX(), getPosY(), getPosZ(), 2.5F, Explosion.Mode.BREAK);
                remove();
            }
        }
        @Override public IPacket<?> createSpawnPacket() { return NetworkHooks.getEntitySpawningPacket(this); }
    }

    @OnlyIn(Dist.CLIENT)
    public static class RenderRegistration {
        @SubscribeEvent public void register(ModelRegistryEvent event) {
            RenderingRegistry.registerEntityRenderingHandler(THROWN,
                manager -> new SpriteRenderer<>(manager, Minecraft.getInstance().getItemRenderer()));
        }
    }
}
