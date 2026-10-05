package net.mcreator.xillysorespawn.entity.renderer;

import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.MobRenderer;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.mcreator.xillysorespawn.entity.CoinEntity;

@OnlyIn(Dist.CLIENT)
public class CoinRenderer {
    private static final String TEXTURE = "xillys_orespawn:textures/entities/cointexture.png";
    public static class ModelRegisterHandler {
        @SubscribeEvent @OnlyIn(Dist.CLIENT)
        public void registerModels(ModelRegistryEvent event) {
            RenderingRegistry.registerEntityRenderingHandler(CoinEntity.entity, manager ->
                new MobRenderer(manager, new ModelCoin(1.0F), 0.5F) {
                    @Override public ResourceLocation getEntityTexture(Entity entity) { return new ResourceLocation(TEXTURE); }
                    @Override protected void preRenderCallback(LivingEntity entity, MatrixStack stack, float partialTick) {
                        stack.scale(0.125F, 0.125F, 0.125F);
                    }
                });
        }
    }

public static class ModelCoin extends EntityModel<Entity>
 {
   private float wingspeed = 1.0F;

   private final ModelRenderer Shape1;

   public ModelCoin(float f1) {
     this.wingspeed = f1;

     this.textureWidth = 512;
     this.textureHeight = 512;

     this.Shape1 = new ModelRenderer(this, 0, 0);
     this.Shape1.addBox(-128.0F, -128.0F, 0.0F, 256, 256, 1);
     this.Shape1.setRotationPoint(0.0F, -109.0F, 0.0F);

     this.Shape1.mirror = true;
     setRotation(this.Shape1, 0.0F, 0.0F, 0.0F);
   }

   public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay,
        float red, float green, float blue, float alpha) {
        Shape1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }

   private void setRotation(ModelRenderer model, float x, float y, float z) {
     model.rotateAngleX = x;
     model.rotateAngleY = y;
     model.rotateAngleZ = z;
   }

   public void setRotationAngles(Entity entity, float limbSwing, float limbSwingAmount,
        float ageInTicks, float netHeadYaw, float headPitch) {
float newangle = 0.0F;
     
     newangle = MathHelper.cos(ageInTicks * 0.05F * this.wingspeed) * 3.1415927F;
     
     this.Shape1.rotateAngleY = newangle;
    }
 }
}

