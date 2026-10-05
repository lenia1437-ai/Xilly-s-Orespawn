package net.mcreator.xillysorespawn.entity.renderer;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.model.CowModel;
import net.minecraft.util.ResourceLocation;
import net.mcreator.xillysorespawn.entity.RedCowEntity;

@OnlyIn(Dist.CLIENT)
public final class RedCowRenderer {
    private RedCowRenderer() {}
    public static class ModelRegisterHandler {
        @SubscribeEvent public void registerModels(ModelRegistryEvent event) {
            RenderingRegistry.registerEntityRenderingHandler(RedCowEntity.entity, Renderer::new);
        }
    }
    private static class Renderer extends MobRenderer<RedCowEntity.CustomEntity, CowModel<RedCowEntity.CustomEntity>> {
        Renderer(EntityRendererManager manager) { super(manager, new CowModel<>(), 0.7F); }
        @Override public ResourceLocation getEntityTexture(RedCowEntity.CustomEntity entity) {
            return new ResourceLocation("xillys_orespawn:textures/entities/red_cow.png");
        }
    }
}