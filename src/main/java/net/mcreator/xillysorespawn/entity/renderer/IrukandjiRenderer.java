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
import net.mcreator.xillysorespawn.entity.IrukandjiEntity;
import net.mcreator.xillysorespawn.entity.OreSpawnLogic;

@OnlyIn(Dist.CLIENT)
public class IrukandjiRenderer {
    private static final String TEXTURE = "xillys_orespawn:textures/entities/irukandjitexture.png";
    public static class ModelRegisterHandler {
        @SubscribeEvent @OnlyIn(Dist.CLIENT)
        public void registerModels(ModelRegistryEvent event) {
            RenderingRegistry.registerEntityRenderingHandler(IrukandjiEntity.entity, manager ->
                new MobRenderer(manager, new ModelIrukandji(1.0F), 0.025F) {
                    @Override public ResourceLocation getEntityTexture(Entity entity) { return new ResourceLocation(TEXTURE); }
                    @Override protected void preRenderCallback(LivingEntity entity, MatrixStack stack, float partialTick) {
                        float modelScale = 0.25F;
                        stack.scale(modelScale, modelScale, modelScale);
                    }
                });
        }
    }

public static class ModelIrukandji extends EntityModel<Entity>
 {
   private float wingspeed = 1.0F;

   private final ModelRenderer body;

   private final ModelRenderer t11;
   private final ModelRenderer t12;
   private final ModelRenderer t21;
   private final ModelRenderer t22;
   private final ModelRenderer t31;
   private final ModelRenderer t32;
   private final ModelRenderer t41;
   private final ModelRenderer t42;

   public ModelIrukandji(float f1) {
     this.wingspeed = f1;

     this.textureWidth = 64;
     this.textureHeight = 32;

     this.body = new ModelRenderer(this, 0, 9);
     this.body.addBox(-2.0F, 0.0F, -2.0F, 4, 4, 4);
     this.body.setRotationPoint(0.0F, 6.0F, 0.0F);

     this.body.mirror = true;
     setRotation(this.body, 0.0F, 0.0F, 0.0F);
     this.t11 = new ModelRenderer(this, 25, 0);
     this.t11.addBox(0.0F, 0.0F, 0.0F, 1, 7, 1);
     this.t11.setRotationPoint(1.0F, 10.0F, -2.0F);

     this.t11.mirror = true;
     setRotation(this.t11, 0.0F, 0.0F, 0.0F);
     this.t12 = new ModelRenderer(this, 5, 0);
     this.t12.addBox(0.0F, 0.0F, 0.0F, 1, 7, 1);
     this.t12.setRotationPoint(1.0F, 17.0F, -2.0F);

     this.t12.mirror = true;
     setRotation(this.t12, 0.0F, 0.0F, 0.0F);
     this.t21 = new ModelRenderer(this, 0, 0);
     this.t21.addBox(0.0F, 0.0F, 0.0F, 1, 7, 1);
     this.t21.setRotationPoint(-2.0F, 10.0F, -2.0F);

     this.t21.mirror = true;
     setRotation(this.t21, 0.0F, 0.0F, 0.0F);
     this.t22 = new ModelRenderer(this, 20, 0);
     this.t22.addBox(0.0F, 0.0F, 0.0F, 1, 7, 1);
     this.t22.setRotationPoint(-2.0F, 17.0F, -2.0F);

     this.t22.mirror = true;
     setRotation(this.t22, 0.0F, 0.0F, 0.0F);
     this.t31 = new ModelRenderer(this, 30, 0);
     this.t31.addBox(0.0F, 0.0F, 0.0F, 1, 7, 1);
     this.t31.setRotationPoint(1.0F, 10.0F, 1.0F);

     this.t31.mirror = true;
     setRotation(this.t31, 0.0F, 0.0F, 0.0F);
     this.t32 = new ModelRenderer(this, 10, 0);
     this.t32.addBox(0.0F, 0.0F, 0.0F, 1, 7, 1);
     this.t32.setRotationPoint(1.0F, 17.0F, 1.0F);

     this.t32.mirror = true;
     setRotation(this.t32, 0.0F, 0.0F, 0.0F);
     this.t41 = new ModelRenderer(this, 35, 0);
     this.t41.addBox(0.0F, 0.0F, 0.0F, 1, 7, 1);
     this.t41.setRotationPoint(-2.0F, 10.0F, 1.0F);

     this.t41.mirror = true;
     setRotation(this.t41, 0.0F, 0.0F, 0.0F);
     this.t42 = new ModelRenderer(this, 15, 0);
     this.t42.addBox(0.0F, 0.0F, 0.0F, 1, 7, 1);
     this.t42.setRotationPoint(-2.0F, 17.0F, 1.0F);

     this.t42.mirror = true;
     setRotation(this.t42, 0.0F, 0.0F, 0.0F);
   }

   public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay,
        float red, float green, float blue, float alpha) {
        body.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        t11.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        t12.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        t21.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        t22.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        t31.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        t32.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        t41.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        t42.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }

   private void setRotation(ModelRenderer model, float x, float y, float z) {
     model.rotateAngleX = x;
     model.rotateAngleY = y;
     model.rotateAngleZ = z;
   }

   public void setRotationAngles(Entity entity, float limbSwing, float limbSwingAmount,
        float ageInTicks, float netHeadYaw, float headPitch) {
// Reset shared model state before applying this frame's legacy animation.
this.body.setRotationPoint(0.0F, 6.0F, 0.0F);
this.t11.setRotationPoint(1.0F, 10.0F, -2.0F);
this.t12.setRotationPoint(1.0F, 17.0F, -2.0F);
this.t21.setRotationPoint(-2.0F, 10.0F, -2.0F);
this.t22.setRotationPoint(-2.0F, 17.0F, -2.0F);
this.t31.setRotationPoint(1.0F, 10.0F, 1.0F);
this.t32.setRotationPoint(1.0F, 17.0F, 1.0F);
this.t41.setRotationPoint(-2.0F, 10.0F, 1.0F);
this.t42.setRotationPoint(-2.0F, 17.0F, 1.0F);
setRotation(this.body, 0.0F, 0.0F, 0.0F);
setRotation(this.t11, 0.0F, 0.0F, 0.0F);
setRotation(this.t12, 0.0F, 0.0F, 0.0F);
setRotation(this.t21, 0.0F, 0.0F, 0.0F);
setRotation(this.t22, 0.0F, 0.0F, 0.0F);
setRotation(this.t31, 0.0F, 0.0F, 0.0F);
setRotation(this.t32, 0.0F, 0.0F, 0.0F);
setRotation(this.t41, 0.0F, 0.0F, 0.0F);
setRotation(this.t42, 0.0F, 0.0F, 0.0F);

     IrukandjiEntity.CustomEntity e = (IrukandjiEntity.CustomEntity) entity;

     float newangle = 0.0F;
 
     
     newangle = MathHelper.cos(ageInTicks * 0.55F) * 3.1415927F * 0.15F;
     this.t11.rotateAngleX = newangle;
     float d1 = (float)(Math.sin(newangle) * 7.0D);
     float d2 = (float)(Math.cos(newangle) * 7.0D);
     this.t11.rotationPointZ += d1;
     newangle = MathHelper.cos(ageInTicks * 0.35F) * 3.1415927F * 0.1F;
     this.t11.rotateAngleZ = newangle;
     float d3 = (float)(Math.cos(newangle) * d2);
     float d4 = (float)(Math.sin(newangle) * d2);
     this.t11.rotationPointX -= d4;
     this.t11.rotationPointY += d3;
     newangle = MathHelper.cos(ageInTicks * 0.45F) * 3.1415927F * 0.15F;
     this.t12.rotateAngleX = newangle;
     newangle = MathHelper.cos(ageInTicks * 0.25F) * 3.1415927F * 0.1F;
     this.t12.rotateAngleZ = newangle;
 
     
     newangle = MathHelper.cos(ageInTicks * 0.65F) * 3.1415927F * 0.15F;
     this.t21.rotateAngleX = newangle;
     d1 = (float)(Math.sin(newangle) * 7.0D);
     d2 = (float)(Math.cos(newangle) * 7.0D);
     this.t21.rotationPointZ += d1;
     newangle = MathHelper.cos(ageInTicks * 0.45F) * 3.1415927F * 0.1F;
     this.t21.rotateAngleZ = newangle;
     d3 = (float)(Math.cos(newangle) * d2);
     d4 = (float)(Math.sin(newangle) * d2);
     this.t21.rotationPointX -= d4;
     this.t21.rotationPointY += d3;
     newangle = MathHelper.cos(ageInTicks * 0.55F) * 3.1415927F * 0.15F;
     this.t22.rotateAngleX = newangle;
     newangle = MathHelper.cos(ageInTicks * 0.35F) * 3.1415927F * 0.1F;
     this.t22.rotateAngleZ = newangle;
 
     
     newangle = MathHelper.cos(ageInTicks * 0.5F) * 3.1415927F * 0.15F;
     this.t31.rotateAngleX = newangle;
     d1 = (float)(Math.sin(newangle) * 7.0D);
     d2 = (float)(Math.cos(newangle) * 7.0D);
     this.t31.rotationPointZ += d1;
     newangle = MathHelper.cos(ageInTicks * 0.3F) * 3.1415927F * 0.1F;
     this.t31.rotateAngleZ = newangle;
     d3 = (float)(Math.cos(newangle) * d2);
     d4 = (float)(Math.sin(newangle) * d2);
     this.t31.rotationPointX -= d4;
     this.t31.rotationPointY += d3;
     newangle = MathHelper.cos(ageInTicks * 0.4F) * 3.1415927F * 0.15F;
     this.t32.rotateAngleX = newangle;
     newangle = MathHelper.cos(ageInTicks * 0.2F) * 3.1415927F * 0.1F;
     this.t32.rotateAngleZ = newangle;
 
     
     newangle = MathHelper.cos(ageInTicks * 0.57F) * 3.1415927F * 0.15F;
     this.t41.rotateAngleX = newangle;
     d1 = (float)(Math.sin(newangle) * 7.0D);
     d2 = (float)(Math.cos(newangle) * 7.0D);
     this.t41.rotationPointZ += d1;
     newangle = MathHelper.cos(ageInTicks * 0.37F) * 3.1415927F * 0.1F;
     this.t41.rotateAngleZ = newangle;
     d3 = (float)(Math.cos(newangle) * d2);
     d4 = (float)(Math.sin(newangle) * d2);
     this.t41.rotationPointX -= d4;
     this.t41.rotationPointY += d3;
     newangle = MathHelper.cos(ageInTicks * 0.48F) * 3.1415927F * 0.15F;
     this.t42.rotateAngleX = newangle;
     newangle = MathHelper.cos(ageInTicks * 0.29F) * 3.1415927F * 0.1F;
     this.t42.rotateAngleZ = newangle;
    }
 }
}