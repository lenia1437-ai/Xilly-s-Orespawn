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
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;
import net.mcreator.xillysorespawn.entity.MothEntity;

@OnlyIn(Dist.CLIENT)
public class MothRenderer {
    private static final String[] TEXTURES = {"lunamoth.png", "eyemoth.png", "darkmoth.png", "firemoth.png"};
    public static class ModelRegisterHandler { @SubscribeEvent public void registerModels(ModelRegistryEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(MothEntity.entity, Renderer::new); } }
    private static class Renderer extends MobRenderer<MothEntity.CustomEntity, ModelButterfly<MothEntity.CustomEntity>> {
        Renderer(EntityRendererManager manager) { super(manager, new ModelButterfly<>(0.75F), 0.6F); this.addLayer(new ShineLayer(this)); }
        @Override public ResourceLocation getEntityTexture(MothEntity.CustomEntity e) {
            return new ResourceLocation("xillys_orespawn:textures/entities/" + TEXTURES[Math.floorMod(e.getVariant(), TEXTURES.length)]);
        }
        @Override protected void preRenderCallback(MothEntity.CustomEntity e, MatrixStack stack, float partial) { stack.scale(1.5F, 1.5F, 1.5F); }
    }
    private static class ShineLayer extends LayerRenderer<MothEntity.CustomEntity, ModelButterfly<MothEntity.CustomEntity>> {
        private static final RenderType SHINE = RenderType.getEyes(new ResourceLocation("minecraft:textures/entity/creeper/creeper_armor.png"));
        ShineLayer(Renderer renderer) { super(renderer); }
        @Override public void render(MatrixStack stack, IRenderTypeBuffer buffers, int light, MothEntity.CustomEntity entity,
                float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
            if (entity.getVariant() != 0) return;
            IVertexBuilder builder = buffers.getBuffer(SHINE);
            getEntityModel().render(stack, builder, 15728640, LivingRenderer.getPackedOverlay(entity, 0.0F), 0.5F, 0.5F, 0.5F, 1.0F);
        }
    }
}
