package net.mcreator.xillysorespawn.entity;

import java.util.Random;
import java.util.function.BiPredicate;

import net.minecraft.world.World;
import net.minecraft.util.math.BlockPos;
import net.minecraft.item.ItemStack;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.item.ItemEntity;

// Общие помощники для мобов из OreSpawn: случайные зачарования дропа, выброс предмета и поиск блока "оболочками"
public class OreLoot {
	// С шансом 1/chance добавляет зачарование уровня base + [0..range)
	public static void ench(Random r, ItemStack s, Enchantment e, int chance, int base, int range) {
		if (r.nextInt(chance) == 1)
			s.addEnchantment(e, base + r.nextInt(range));
	}

	public static void sword(Random r, ItemStack s) {
		ench(r, s, Enchantments.SHARPNESS, 6, 1, 5);
		ench(r, s, Enchantments.BANE_OF_ARTHROPODS, 6, 1, 5);
		ench(r, s, Enchantments.KNOCKBACK, 6, 1, 5);
		ench(r, s, Enchantments.LOOTING, 6, 1, 5);
		ench(r, s, Enchantments.UNBREAKING, 2, 2, 4);
		ench(r, s, Enchantments.FIRE_ASPECT, 6, 1, 5);
		ench(r, s, Enchantments.SHARPNESS, 6, 1, 5);
	}

	public static void shovelOrHoeOrAxe(Random r, ItemStack s) {
		ench(r, s, Enchantments.UNBREAKING, 2, 2, 4);
		ench(r, s, Enchantments.EFFICIENCY, 6, 1, 5);
	}

	public static void pickaxe(Random r, ItemStack s) {
		ench(r, s, Enchantments.UNBREAKING, 2, 2, 4);
		ench(r, s, Enchantments.EFFICIENCY, 6, 1, 5);
		ench(r, s, Enchantments.FORTUNE, 6, 1, 5);
	}

	public static void helmet(Random r, ItemStack s) {
		ench(r, s, Enchantments.PROTECTION, 6, 1, 5);
		ench(r, s, Enchantments.BLAST_PROTECTION, 6, 1, 5);
		ench(r, s, Enchantments.FIRE_PROTECTION, 6, 1, 5);
		ench(r, s, Enchantments.PROJECTILE_PROTECTION, 6, 1, 5);
		ench(r, s, Enchantments.UNBREAKING, 2, 2, 4);
		ench(r, s, Enchantments.RESPIRATION, 6, 1, 2);
		ench(r, s, Enchantments.AQUA_AFFINITY, 6, 1, 5);
	}

	public static void chestOrLegs(Random r, ItemStack s) {
		ench(r, s, Enchantments.PROTECTION, 6, 1, 5);
		ench(r, s, Enchantments.BLAST_PROTECTION, 6, 1, 5);
		ench(r, s, Enchantments.FIRE_PROTECTION, 6, 1, 5);
		ench(r, s, Enchantments.PROJECTILE_PROTECTION, 6, 1, 5);
		ench(r, s, Enchantments.UNBREAKING, 2, 2, 4);
	}

	public static void boots(Random r, ItemStack s) {
		ench(r, s, Enchantments.FEATHER_FALLING, 6, 5, 5);
		ench(r, s, Enchantments.UNBREAKING, 2, 2, 4);
	}

	// Выбрасывает предмет рядом с точкой (случайный разброс +-spread блоков по X/Z)
	public static ItemStack drop(World w, Random r, double x, double y, double z, int spread, ItemStack s) {
		ItemEntity e = new ItemEntity(w, x + r.nextInt(spread) - r.nextInt(spread), y, z + r.nextInt(spread) - r.nextInt(spread), s);
		w.addEntity(e);
		return s;
	}

	// Ищет ближайший подходящий блок на поверхности параллелепипеда с полуразмерами rx, ry, rz вокруг c (как scan_it в 1.7.10)
	public static BlockPos scanShell(World world, BlockPos c, int rx, int ry, int rz, BiPredicate<World, BlockPos> test) {
		BlockPos best = null;
		int bestD = Integer.MAX_VALUE;
		for (int dx = -rx; dx <= rx; dx++) {
			for (int dy = -ry; dy <= ry; dy++) {
				boolean edge = Math.abs(dx) == rx || Math.abs(dy) == ry;
				int step = edge ? 1 : Math.max(1, 2 * rz);
				for (int dz = -rz; dz <= rz; dz += step) {
					BlockPos p = c.add(dx, dy, dz);
					if (!world.isBlockLoaded(p))
						continue;
					if (test.test(world, p)) {
						int d = dx * dx + dy * dy + dz * dz;
						if (d < bestD) {
							bestD = d;
							best = p;
						}
					}
				}
			}
		}
		return best;
	}
}
