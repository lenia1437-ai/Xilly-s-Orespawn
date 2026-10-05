package net.mcreator.xillysorespawn.block;

import net.mcreator.xillysorespawn.XillysOrespawnModElements;
import net.minecraft.world.biome.BiomeColors;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;

/** Applies the original greyscale foliage/ant-nest textures' biome tint. */
@XillysOrespawnModElements.ModElement.Tag
public class OreSpawnBiomeTint extends XillysOrespawnModElements.ModElement {
    public OreSpawnBiomeTint(XillysOrespawnModElements instance) {
        super(instance, 3);
        if (FMLEnvironment.dist == Dist.CLIENT)
            FMLJavaModLoadingContext.get().getModEventBus().register(new ClientColors());
    }

    @OnlyIn(Dist.CLIENT)
    public static final class ClientColors {
        @SubscribeEvent
        public void blocks(ColorHandlerEvent.Block event) {
            event.getBlockColors().register((state, reader, pos, tint) ->
                    reader != null && pos != null ? BiomeColors.getFoliageColor(reader, pos) : 0x65A540,
                LeavesAppleBlock.block, LeavesCherryBlock.block, LeavesPeachBlock.block,
                LeavesExperienceBlock.block, ScaryLeavesBlock.block);
            event.getBlockColors().register((state, reader, pos, tint) ->
                    reader != null && pos != null ? BiomeColors.getGrassColor(reader, pos) : 0x79C05A,
                AntNestBlock.block);
        }

        @SubscribeEvent
        public void items(ColorHandlerEvent.Item event) {
            event.getItemColors().register((stack, tint) -> 0x65A540,
                LeavesAppleBlock.block.asItem(), LeavesCherryBlock.block.asItem(),
                LeavesPeachBlock.block.asItem(), LeavesExperienceBlock.block.asItem(),
                ScaryLeavesBlock.block.asItem());
            event.getItemColors().register((stack, tint) -> 0x79C05A, AntNestBlock.block.asItem());
        }
    }
}
