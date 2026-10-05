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
import net.mcreator.xillysorespawn.entity.RainbowAntEntity;

@OnlyIn(Dist.CLIENT)
public class RainbowAntRenderer {
    public static class ModelRegisterHandler { @SubscribeEvent public void registerModels(ModelRegistryEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(RainbowAntEntity.entity, Renderer::new); } }
    private static class Renderer extends MobRenderer<RainbowAntEntity.CustomEntity, ModelAnt<RainbowAntEntity.CustomEntity>> {
        Renderer(EntityRendererManager manager) { super(manager, new ModelAnt<>(), 0.025F); }
        @Override public ResourceLocation getEntityTexture(RainbowAntEntity.CustomEntity e) { return new ResourceLocation("xillys_orespawn:textures/entities/rainbow_ant.png"); }
        @Override protected void preRenderCallback(RainbowAntEntity.CustomEntity e, MatrixStack stack, float partial) { stack.scale(0.25F, 0.25F, 0.25F); }
    }
}
