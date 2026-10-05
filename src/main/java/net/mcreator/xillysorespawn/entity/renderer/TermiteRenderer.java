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
import net.mcreator.xillysorespawn.entity.TermiteEntity;

@OnlyIn(Dist.CLIENT)
public class TermiteRenderer {
    public static class ModelRegisterHandler { @SubscribeEvent public void registerModels(ModelRegistryEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(TermiteEntity.entity, Renderer::new); } }
    private static class Renderer extends MobRenderer<TermiteEntity.CustomEntity, ModelAnt<TermiteEntity.CustomEntity>> {
        Renderer(EntityRendererManager manager) { super(manager, new ModelAnt<>(), 0.0525F); }
        @Override public ResourceLocation getEntityTexture(TermiteEntity.CustomEntity e) { return new ResourceLocation("xillys_orespawn:textures/entities/termite.png"); }
        @Override protected void preRenderCallback(TermiteEntity.CustomEntity e, MatrixStack stack, float partial) { stack.scale(0.35F, 0.35F, 0.35F); }
    }
}
