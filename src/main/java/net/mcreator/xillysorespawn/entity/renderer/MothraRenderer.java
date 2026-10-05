package net.mcreator.xillysorespawn.entity.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;
import net.mcreator.xillysorespawn.entity.MothraEntity;

/** Original Mothra presentation: ModelButterfly(0.2), scale 10 and energy overlay. */
@OnlyIn(Dist.CLIENT)
public final class MothraRenderer {
    private static final ResourceLocation TEXTURE =
        new ResourceLocation("xillys_orespawn:textures/entities/eyemoth.png");
    private static final ResourceLocation ARMOR =
        new ResourceLocation("minecraft:textures/entity/creeper/creeper_armor.png");
    private MothraRenderer() {}
    public static class ModelRegisterHandler {
        @SubscribeEvent public void registerModels(ModelRegistryEvent event) {
            RenderingRegistry.registerEntityRenderingHandler(MothraEntity.entity, Renderer::new);
        }
    }
    private static class Renderer extends MobRenderer<MothraEntity.CustomEntity, ModelButterfly<MothraEntity.CustomEntity>> {
        Renderer(EntityRendererManager manager) {
            super(manager, new ModelButterfly<>(0.2F), 7.5F);
            addLayer(new EnergyLayer(this));
        }
        @Override public ResourceLocation getEntityTexture(MothraEntity.CustomEntity entity) { return TEXTURE; }
        @Override protected void preRenderCallback(MothraEntity.CustomEntity entity, MatrixStack stack, float partial) {
            stack.scale(10.0F, 10.0F, 10.0F);
        }
    }
    private static class EnergyLayer extends LayerRenderer<MothraEntity.CustomEntity, ModelButterfly<MothraEntity.CustomEntity>> {
        private static final RenderType ENERGY = RenderType.getEyes(ARMOR);
        EnergyLayer(Renderer renderer) { super(renderer); }
        @Override public void render(MatrixStack stack, IRenderTypeBuffer buffers, int light,
                MothraEntity.CustomEntity entity, float limbSwing, float limbSwingAmount,
                float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
            IVertexBuilder builder = buffers.getBuffer(ENERGY);
            getEntityModel().render(stack, builder, 15728640,
                LivingRenderer.getPackedOverlay(entity, 0.0F), 0.5F, 0.5F, 0.5F, 1.0F);
        }
    }
}

