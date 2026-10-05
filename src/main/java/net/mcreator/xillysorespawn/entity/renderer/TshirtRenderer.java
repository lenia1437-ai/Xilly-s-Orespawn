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
import net.mcreator.xillysorespawn.entity.TshirtEntity;
import net.mcreator.xillysorespawn.entity.OreSpawnLogic;

@OnlyIn(Dist.CLIENT)
public class TshirtRenderer {
    private static final String TEXTURE = "xillys_orespawn:textures/entities/tshirttexture.png";
    public static class ModelRegisterHandler {
        @SubscribeEvent @OnlyIn(Dist.CLIENT)
        public void registerModels(ModelRegistryEvent event) {
            RenderingRegistry.registerEntityRenderingHandler(TshirtEntity.entity, manager ->
                new MobRenderer(manager, new ModelTshirt(0.22F), 0.1089F) {
                    @Override public ResourceLocation getEntityTexture(Entity entity) { return new ResourceLocation(TEXTURE); }
                    @Override protected void preRenderCallback(LivingEntity entity, MatrixStack stack, float partialTick) {
                        float modelScale = 0.33F;
                        stack.scale(modelScale, modelScale, modelScale);
                    }
                });
        }
    }

public static class ModelTshirt extends EntityModel<Entity>
 {
   private float wingspeed = 1.0F;

   private final ModelRenderer Shape1;

   private final ModelRenderer Shape2;

   public ModelTshirt(float f1) {
     this.wingspeed = f1;

     this.textureWidth = 512;
     this.textureHeight = 256;

     this.Shape1 = new ModelRenderer(this, 0, 0);
     this.Shape1.addBox(-128.0F, -64.0F, 0.0F, 256, 64, 1);
     this.Shape1.setRotationPoint(0.0F, -128.0F, 0.0F);

     this.Shape1.mirror = true;
     setRotation(this.Shape1, 0.0F, 0.0F, 0.0F);
     this.Shape2 = new ModelRenderer(this, 0, 64);
     this.Shape2.addBox(-64.0F, 0.0F, 0.0F, 128, 128, 1);
     this.Shape2.setRotationPoint(0.0F, -128.0F, 0.0F);

     this.Shape2.mirror = true;
     setRotation(this.Shape2, 0.0F, 0.0F, 0.0F);
   }

   public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay,
        float red, float green, float blue, float alpha) {
        Shape1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Shape2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }

   private void setRotation(ModelRenderer model, float x, float y, float z) {
     model.rotateAngleX = x;
     model.rotateAngleY = y;
     model.rotateAngleZ = z;
   }

   public void setRotationAngles(Entity entity, float limbSwing, float limbSwingAmount,
        float ageInTicks, float netHeadYaw, float headPitch) {
// Reset shared model state before applying this frame's legacy animation.
this.Shape1.setRotationPoint(0.0F, -128.0F, 0.0F);
this.Shape2.setRotationPoint(0.0F, -128.0F, 0.0F);
setRotation(this.Shape1, 0.0F, 0.0F, 0.0F);
setRotation(this.Shape2, 0.0F, 0.0F, 0.0F);

     float newangle = 0.0F;
     
     newangle = MathHelper.cos(ageInTicks * 0.05F * this.wingspeed) * 3.1415927F;
     
     this.Shape1.rotateAngleY = newangle;
     this.Shape2.rotateAngleY = newangle;
    }
 }
}