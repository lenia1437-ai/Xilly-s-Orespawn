package net.mcreator.xillysorespawn.entity.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.mcreator.xillysorespawn.entity.ButterflyEntity;
import net.mcreator.xillysorespawn.entity.OreSpawnLogic;

@OnlyIn(Dist.CLIENT)
public class ButterflyRenderer {
    private static final String[] TEXTURES = {"butterfly.png", "butterfly2.png", "butterfly3.png", "butterfly4.png"};
    public static class ModelRegisterHandler { @SubscribeEvent public void registerModels(ModelRegistryEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(ButterflyEntity.entity, Renderer::new); } }
    private static class Renderer extends MobRenderer<ButterflyEntity.CustomEntity, ModelButterfly<ButterflyEntity.CustomEntity>> {
        Renderer(EntityRendererManager manager) { super(manager, new ModelButterfly<>(1.0F), 0.3F); }
        @Override public ResourceLocation getEntityTexture(ButterflyEntity.CustomEntity e) {
            if (e.getVariant() == 1 && OreSpawnLogic.isDimension(e.world, "dimension_danger", "danger", "unstable_ant_dimension"))
                return new ResourceLocation("xillys_orespawn:textures/entities/vbutterfly1.png");
            return new ResourceLocation("xillys_orespawn:textures/entities/" + TEXTURES[Math.floorMod(e.getVariant(), TEXTURES.length)]);
        }
        @Override protected void preRenderCallback(ButterflyEntity.CustomEntity e, MatrixStack stack, float partial) { stack.scale(1.0F, 1.0F, 1.0F); }
    }
}
