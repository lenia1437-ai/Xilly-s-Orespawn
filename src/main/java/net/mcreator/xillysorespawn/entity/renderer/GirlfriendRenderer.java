package net.mcreator.xillysorespawn.entity.renderer;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraft.client.renderer.entity.BipedRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.layers.BipedArmorLayer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.util.ResourceLocation;
import net.mcreator.xillysorespawn.entity.GirlfriendEntity;

@OnlyIn(Dist.CLIENT)
public class GirlfriendRenderer {
    public static class ModelRegisterHandler { @SubscribeEvent public void registerModels(ModelRegistryEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(GirlfriendEntity.entity, Renderer::new); } }
    private static class Renderer extends BipedRenderer<GirlfriendEntity.CustomEntity, BipedModel<GirlfriendEntity.CustomEntity>> {
        Renderer(EntityRendererManager manager) {
            super(manager, new BipedModel<>(0.0F), 0.5F);
            this.addLayer(new BipedArmorLayer<>(this,
                new BipedModel<GirlfriendEntity.CustomEntity>(0.5F),
                new BipedModel<GirlfriendEntity.CustomEntity>(1.0F)));
        }
        @Override public ResourceLocation getEntityTexture(GirlfriendEntity.CustomEntity e) {
            if (e.getSpecialSkin() > 0) return new ResourceLocation("xillys_orespawn:textures/entities/" + (e.getSpecialSkin() == 1 ? "frogprincess.png" : "frogprincess2.png"));
            String name = e.isInWater() ? "bikini" + Math.floorMod(e.getWetSkin(), 18) : "girlfriend" + Math.floorMod(e.getSkin(), 41);
            return new ResourceLocation("xillys_orespawn:textures/entities/" + name + ".png");
        }
    }
}
