package net.mcreator.xillysorespawn.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.SnowballEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.Explosion;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * OreSpawn's laser projectile, transported through the vanilla snowball entity
 * type so the port does not require a separate MCreator entity element.
 */
public class OreSpawnLaserBall extends SnowballEntity {
    private boolean special;

    public OreSpawnLaserBall(World world, LivingEntity shooter) {
        super(world, shooter);
        Item laser = ForgeRegistries.ITEMS.getValue(new ResourceLocation("xillys_orespawn", "laser_ball"));
        setItem(new ItemStack(laser == null || laser == Items.AIR ? Items.FIRE_CHARGE : laser));
    }

    public OreSpawnLaserBall setSpecial() {
        special = true;
        return this;
    }

    private static boolean isRobotImmune(Entity target) {
        ResourceLocation id = target.getType().getRegistryName();
        if (id == null || !"xillys_orespawn".equals(id.getNamespace())) return false;
        String path = id.getPath();
        return "robot_2".equals(path) || "robot_3".equals(path) || "robot_4".equals(path)
            || "robot_5".equals(path) || "giant_robot".equals(path);
    }

    @Override
    protected void onEntityHit(EntityRayTraceResult result) {
        Entity target = result.getEntity();
        if (isRobotImmune(target)) return;
        target.attackEntityFrom(DamageSource.causeThrownDamage(this, func_234616_v_()), 16.0F);
        target.setFire(1);
    }

    @Override
    protected void onImpact(RayTraceResult result) {
        super.onImpact(result);
        if (!world.isRemote && special) {
            Explosion.Mode mode = world.getGameRules().getBoolean(GameRules.MOB_GRIEFING)
                ? Explosion.Mode.DESTROY : Explosion.Mode.NONE;
            world.createExplosion(this, getPosX(), getPosY(), getPosZ(), 3.0F, mode);
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (!isAlive()) return;
        rotationPitch = prevRotationPitch = (rotationPitch + 50.0F) % 360.0F;
        if (ticksExisted > 200) {
            remove();
            return;
        }
        if (world.isRemote) {
            int count = special ? 10 : 4;
            for (int i = 0; i < count; i++) {
                world.addParticle(ParticleTypes.FIREWORK, getPosX(), getPosY(), getPosZ(),
                    rand.nextGaussian() * 0.5D, rand.nextGaussian() * 0.5D, rand.nextGaussian() * 0.5D);
                world.addParticle(ParticleTypes.FLAME, getPosX(), getPosY(), getPosZ(), 0.0D, 0.0D, 0.0D);
            }
        }
    }
}
