package net.mcreator.xillysorespawn.entity.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.CowModel;
import net.minecraft.util.ResourceLocation;
import net.mcreator.xillysorespawn.entity.EnchantedCowEntity;

@OnlyIn(Dist.CLIENT)
public final class EnchantedCowRenderer {
    private static final ResourceLocation TEXTURE =
        new ResourceLocation("xillys_orespawn:textures/entities/gold_cow.png");
    private EnchantedCowRenderer() {}
    public static class ModelRegisterHandler {
        @SubscribeEvent public void registerModels(ModelRegistryEvent event) {
            RenderingRegistry.registerEntityRenderingHandler(EnchantedCowEntity.entity, Renderer::new);
        }
    }
    private static class Renderer extends MobRenderer<EnchantedCowEntity.CustomEntity,
            CowModel<EnchantedCowEntity.CustomEntity>> {
        Renderer(EntityRendererManager manager) {
            super(manager, new CowModel<>(), 0.7F);
            addLayer(new GlintLayer(this));
        }
        @Override public ResourceLocation getEntityTexture(EnchantedCowEntity.CustomEntity entity) {
            return TEXTURE;
        }
    }
    private static class GlintLayer extends LayerRenderer<EnchantedCowEntity.CustomEntity,
            CowModel<EnchantedCowEntity.CustomEntity>> {
        GlintLayer(Renderer renderer) { super(renderer); }
        @Override public void render(MatrixStack stack, IRenderTypeBuffer buffers, int light,
                EnchantedCowEntity.CustomEntity entity, float limbSwing, float limbSwingAmount,
                float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
            IVertexBuilder builder = ItemRenderer.getBuffer(buffers,
                getEntityModel().getRenderType(TEXTURE), false, true);
            getEntityModel().render(stack, builder, 15728640,
                LivingRenderer.getPackedOverlay(entity, 0.0F), 1.0F, 1.0F, 1.0F, 0.65F);
        }
    }
}
