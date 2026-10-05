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
import net.mcreator.xillysorespawn.entity.BoyfriendEntity;

@OnlyIn(Dist.CLIENT)
public class BoyfriendRenderer {
    public static class ModelRegisterHandler { @SubscribeEvent public void registerModels(ModelRegistryEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(BoyfriendEntity.entity, Renderer::new); } }
    private static class Renderer extends BipedRenderer<BoyfriendEntity.CustomEntity, BipedModel<BoyfriendEntity.CustomEntity>> {
        Renderer(EntityRendererManager manager) {
            super(manager, new BipedModel<>(0.0F), 0.55F);
            this.addLayer(new BipedArmorLayer<>(this,
                new BipedModel<BoyfriendEntity.CustomEntity>(0.5F),
                new BipedModel<BoyfriendEntity.CustomEntity>(1.0F)));
        }
        @Override public ResourceLocation getEntityTexture(BoyfriendEntity.CustomEntity e) {
            if (e.getSpecialSkin() > 0) return new ResourceLocation("xillys_orespawn:textures/entities/" + (e.getSpecialSkin() == 1 ? "frogprince.png" : "frogprince2.png"));
            String name = e.isInWater() ? "swimshorts" + Math.floorMod(e.getWetSkin(), 18) : "boyfriend" + Math.floorMod(e.getSkin(), 28);
            return new ResourceLocation("xillys_orespawn:textures/entities/" + name + ".png");
        }
    }
}
