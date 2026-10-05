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
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.MobRenderer;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.mcreator.xillysorespawn.entity.RotatorEntity;
import net.mcreator.xillysorespawn.entity.OreSpawnLogic;

@OnlyIn(Dist.CLIENT)
public class RotatorRenderer {
    private static final String TEXTURE = "xillys_orespawn:textures/entities/rotatortexture.png";
    public static class ModelRegisterHandler {
        @SubscribeEvent @OnlyIn(Dist.CLIENT)
        public void registerModels(ModelRegistryEvent event) {
            RenderingRegistry.registerEntityRenderingHandler(RotatorEntity.entity, manager ->
                new MobRenderer(manager, new ModelRotator(0.25F), 0.1F) {
                    @Override public ResourceLocation getEntityTexture(Entity entity) { return new ResourceLocation(TEXTURE); }
                    @Override protected void preRenderCallback(LivingEntity entity, MatrixStack stack, float partialTick) {
                        float modelScale = 1.0F;
                        stack.scale(modelScale, modelScale, modelScale);
                    }
                });
        }
    }

public static class ModelRotator extends EntityModel<Entity>
 {
   float wingspeed = 1.0F;

   private final ModelRenderer Shape1;
   private final ModelRenderer Shape2;
   private final ModelRenderer Shape3;
   private float spin;

   public ModelRotator(float f1) {
     this.wingspeed = f1;

     this.textureWidth = 64;
     this.textureHeight = 32;

     this.Shape1 = new ModelRenderer(this, 0, 12);
     this.Shape1.addBox(-2.0F, 3.9F, 0.0F, 4, 1, 1);
     this.Shape1.setRotationPoint(0.0F, 0.0F, 0.0F);

     this.Shape1.mirror = true;
     setRotation(this.Shape1, 0.0F, 0.0F, 0.0F);
     this.Shape2 = new ModelRenderer(this, 0, 7);
     this.Shape2.addBox(-4.0F, 7.6F, 0.0F, 8, 2, 2);
     this.Shape2.setRotationPoint(0.0F, 0.0F, -0.5F);

     this.Shape2.mirror = true;
     setRotation(this.Shape2, 0.0F, 0.0F, 0.0F);
     this.Shape3 = new ModelRenderer(this, 0, 0);
     this.Shape3.addBox(-7.0F, 13.7F, 0.0F, 14, 3, 3);
     this.Shape3.setRotationPoint(0.0F, 0.0F, -1.0F);

     this.Shape3.mirror = true;
     setRotation(this.Shape3, 0.0F, 0.0F, 0.0F);
   }

   public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay,
        float red, float green, float blue, float alpha) {
        renderRing(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha, Shape1, Vector3f.XP);
        renderRing(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha, Shape2, Vector3f.YP);
        renderRing(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha, Shape3, Vector3f.ZP);
    }

   private void renderRing(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay,
        float red, float green, float blue, float alpha, ModelRenderer segment, Vector3f axis) {
     matrixStack.push();
     matrixStack.rotate(axis.rotationDegrees(this.spin));
     for (int i = 0; i < 8; i++) {
       segment.rotateAngleZ = i * ((float)Math.PI / 4.0F);
       segment.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
     }
     matrixStack.pop();
   }

   private void setRotation(ModelRenderer model, float x, float y, float z) {
     model.rotateAngleX = x;
     model.rotateAngleY = y;
     model.rotateAngleZ = z;
   }

   public void setRotationAngles(Entity entity, float limbSwing, float limbSwingAmount,
        float ageInTicks, float netHeadYaw, float headPitch) {
// Reset shared model state before applying this frame's legacy animation.
this.Shape1.setRotationPoint(0.0F, 0.0F, 0.0F);
this.Shape2.setRotationPoint(0.0F, 0.0F, -0.5F);
this.Shape3.setRotationPoint(0.0F, 0.0F, -1.0F);
setRotation(this.Shape1, 0.0F, 0.0F, 0.0F);
setRotation(this.Shape2, 0.0F, 0.0F, 0.0F);
setRotation(this.Shape3, 0.0F, 0.0F, 0.0F);

     RotatorEntity.CustomEntity r = (RotatorEntity.CustomEntity) entity;
     RenderInfo ri = null;
     ri = r.getRenderInfo();
     ri.rf1 += 2.0F;
     if (ri.rf1 > 359.0D) ri.rf1 = 0.0F;
     this.spin = ri.rf1;
     
     r.setRenderInfo(ri);
    }
 }
}
