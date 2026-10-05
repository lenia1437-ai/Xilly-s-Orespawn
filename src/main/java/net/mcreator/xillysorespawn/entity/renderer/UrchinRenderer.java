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
import net.mcreator.xillysorespawn.entity.UrchinEntity;
import net.mcreator.xillysorespawn.entity.OreSpawnLogic;

@OnlyIn(Dist.CLIENT)
public class UrchinRenderer {
    private static final String TEXTURE = "xillys_orespawn:textures/entities/urchintexture.png";
    public static class ModelRegisterHandler {
        @SubscribeEvent @OnlyIn(Dist.CLIENT)
        public void registerModels(ModelRegistryEvent event) {
            RenderingRegistry.registerEntityRenderingHandler(UrchinEntity.entity, manager ->
                new MobRenderer(manager, new ModelUrchin(1.0F), 0.4375F) {
                    @Override public ResourceLocation getEntityTexture(Entity entity) { return new ResourceLocation(TEXTURE); }
                    @Override protected void preRenderCallback(LivingEntity entity, MatrixStack stack, float partialTick) {
                        float modelScale = 1.25F;
                        stack.scale(modelScale, modelScale, modelScale);
                    }
                });
        }
    }

public static class ModelUrchin extends EntityModel<Entity> {
   private float wingspeed = 1.0F;

   private final ModelRenderer if1;

   private final ModelRenderer if2;

   private final ModelRenderer if3;

   private final ModelRenderer if4;
   private final ModelRenderer of1;
   private final ModelRenderer of2;
   private final ModelRenderer of3;
   private final ModelRenderer of4;
   private final ModelRenderer center;
   private final ModelRenderer tis1;
   private final ModelRenderer tis2;
   private final ModelRenderer tis3;
   private final ModelRenderer tis4;
   private final ModelRenderer tos1;
   private final ModelRenderer tos2;
   private final ModelRenderer tos3;
   private final ModelRenderer tos4;

   public ModelUrchin(float f1) {
     this.wingspeed = f1;

     this.textureWidth = 128;
     this.textureHeight = 128;

     this.if1 = new ModelRenderer(this, 0, 35);
     this.if1.addBox(0.0F, 0.0F, 0.0F, 1, 8, 1);
     this.if1.setRotationPoint(0.0F, 16.0F, 0.0F);

     this.if1.mirror = true;
     setRotation(this.if1, 0.2617994F, 0.0F, 0.0F);
     this.if2 = new ModelRenderer(this, 5, 35);
     this.if2.addBox(0.0F, 0.0F, 0.0F, 1, 8, 1);
     this.if2.setRotationPoint(0.0F, 16.0F, 0.0F);

     this.if2.mirror = true;
     setRotation(this.if2, -0.2617994F, 0.0F, 0.0F);
     this.if3 = new ModelRenderer(this, 10, 35);
     this.if3.addBox(0.0F, 0.0F, 0.0F, 1, 8, 1);
     this.if3.setRotationPoint(0.0F, 16.0F, 0.0F);

     this.if3.mirror = true;
     setRotation(this.if3, 0.0F, 0.0F, 0.2617994F);
     this.if4 = new ModelRenderer(this, 15, 35);
     this.if4.addBox(0.0F, 0.0F, 0.0F, 1, 8, 1);
     this.if4.setRotationPoint(0.0F, 16.0F, 0.0F);

     this.if4.mirror = true;
     setRotation(this.if4, 0.0F, 0.0F, -0.2617994F);
     this.of1 = new ModelRenderer(this, 0, 45);
     this.of1.addBox(0.0F, 0.0F, 0.0F, 1, 8, 1);
     this.of1.setRotationPoint(2.0F, 16.0F, 0.0F);

     this.of1.mirror = true;
     setRotation(this.of1, 0.0F, 0.0F, -0.5235988F);
     this.of2 = new ModelRenderer(this, 5, 45);
     this.of2.addBox(0.0F, 0.0F, 0.0F, 1, 8, 1);
     this.of2.setRotationPoint(-2.0F, 16.0F, 0.0F);

     this.of2.mirror = true;
     setRotation(this.of2, 0.0F, 0.0F, 0.5235988F);
     this.of3 = new ModelRenderer(this, 10, 45);
     this.of3.addBox(0.0F, 0.0F, 0.0F, 1, 8, 1);
     this.of3.setRotationPoint(0.0F, 16.0F, -2.0F);

     this.of3.mirror = true;
     setRotation(this.of3, -0.5235988F, 0.0F, 0.0F);
     this.of4 = new ModelRenderer(this, 15, 45);
     this.of4.addBox(0.0F, 0.0F, 0.0F, 1, 8, 1);
     this.of4.setRotationPoint(0.0F, 16.0F, 2.0F);

     this.of4.mirror = true;
     setRotation(this.of4, 0.5235988F, 0.0F, 0.0F);
     this.center = new ModelRenderer(this, 0, 0);
     this.center.addBox(0.0F, -30.0F, 0.0F, 1, 30, 1);
     this.center.setRotationPoint(0.0F, 16.0F, 0.0F);

     this.center.mirror = true;
     setRotation(this.center, 0.0F, 0.0F, 0.0F);
     this.tis1 = new ModelRenderer(this, 25, 0);
     this.tis1.addBox(0.0F, -25.0F, 0.0F, 1, 25, 1);
     this.tis1.setRotationPoint(0.0F, 16.0F, 0.0F);

     this.tis1.mirror = true;
     setRotation(this.tis1, 0.2617994F, 0.0F, 0.0F);
     this.tis2 = new ModelRenderer(this, 30, 0);
     this.tis2.addBox(0.0F, -25.0F, 0.0F, 1, 25, 1);
     this.tis2.setRotationPoint(0.0F, 16.0F, 0.0F);

     this.tis2.mirror = true;
     setRotation(this.tis2, -0.2617994F, 0.0F, 0.0F);
     this.tis3 = new ModelRenderer(this, 35, 0);
     this.tis3.addBox(0.0F, -25.0F, 0.0F, 1, 25, 1);
     this.tis3.setRotationPoint(0.0F, 16.0F, 0.0F);

     this.tis3.mirror = true;
     setRotation(this.tis3, 0.0F, 0.0F, 0.2617994F);
     this.tis4 = new ModelRenderer(this, 40, 0);
     this.tis4.addBox(0.0F, -25.0F, 0.0F, 1, 25, 1);
     this.tis4.setRotationPoint(0.0F, 16.0F, 0.0F);

     this.tis4.mirror = true;
     setRotation(this.tis4, 0.0F, 0.0F, -0.2617994F);
     this.tos1 = new ModelRenderer(this, 5, 0);
     this.tos1.addBox(0.0F, -20.0F, 0.0F, 1, 20, 1);
     this.tos1.setRotationPoint(0.0F, 16.0F, 2.0F);

     this.tos1.mirror = true;
     setRotation(this.tos1, -0.5235988F, 0.0F, 0.0F);
     this.tos2 = new ModelRenderer(this, 10, 0);
     this.tos2.addBox(-2.0F, -20.0F, 0.0F, 1, 20, 1);
     this.tos2.setRotationPoint(0.0F, 16.0F, 0.0F);

     this.tos2.mirror = true;
     setRotation(this.tos2, 0.0F, 0.0F, -0.5235988F);
     this.tos3 = new ModelRenderer(this, 15, 0);
     this.tos3.addBox(0.0F, -20.0F, 0.0F, 1, 20, 1);
     this.tos3.setRotationPoint(2.0F, 16.0F, 0.0F);

     this.tos3.mirror = true;
     setRotation(this.tos3, 0.0F, 0.0F, 0.5235988F);
     this.tos4 = new ModelRenderer(this, 20, 0);
     this.tos4.addBox(0.0F, -20.0F, 0.0F, 1, 20, 1);
     this.tos4.setRotationPoint(0.0F, 16.0F, -2.0F);

     this.tos4.mirror = true;
     setRotation(this.tos4, 0.5235988F, 0.0F, 0.0F);
   }

   public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay,
        float red, float green, float blue, float alpha) {
        if1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        if2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        if3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        if4.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        of1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        of2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        of3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        of4.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        center.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        tis1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        tis2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        tis3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        tis4.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        tos1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        tos2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        tos3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        tos4.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }

   private void setRotation(ModelRenderer model, float x, float y, float z) {
     model.rotateAngleX = x;
     model.rotateAngleY = y;
     model.rotateAngleZ = z;
   }

   public void setRotationAngles(Entity entity, float limbSwing, float limbSwingAmount,
        float ageInTicks, float netHeadYaw, float headPitch) {
// Reset shared model state before applying this frame's legacy animation.
this.if1.setRotationPoint(0.0F, 16.0F, 0.0F);
this.if2.setRotationPoint(0.0F, 16.0F, 0.0F);
this.if3.setRotationPoint(0.0F, 16.0F, 0.0F);
this.if4.setRotationPoint(0.0F, 16.0F, 0.0F);
this.of1.setRotationPoint(2.0F, 16.0F, 0.0F);
this.of2.setRotationPoint(-2.0F, 16.0F, 0.0F);
this.of3.setRotationPoint(0.0F, 16.0F, -2.0F);
this.of4.setRotationPoint(0.0F, 16.0F, 2.0F);
this.center.setRotationPoint(0.0F, 16.0F, 0.0F);
this.tis1.setRotationPoint(0.0F, 16.0F, 0.0F);
this.tis2.setRotationPoint(0.0F, 16.0F, 0.0F);
this.tis3.setRotationPoint(0.0F, 16.0F, 0.0F);
this.tis4.setRotationPoint(0.0F, 16.0F, 0.0F);
this.tos1.setRotationPoint(0.0F, 16.0F, 2.0F);
this.tos2.setRotationPoint(0.0F, 16.0F, 0.0F);
this.tos3.setRotationPoint(2.0F, 16.0F, 0.0F);
this.tos4.setRotationPoint(0.0F, 16.0F, -2.0F);
setRotation(this.if1, 0.2617994F, 0.0F, 0.0F);
setRotation(this.if2, -0.2617994F, 0.0F, 0.0F);
setRotation(this.if3, 0.0F, 0.0F, 0.2617994F);
setRotation(this.if4, 0.0F, 0.0F, -0.2617994F);
setRotation(this.of1, 0.0F, 0.0F, -0.5235988F);
setRotation(this.of2, 0.0F, 0.0F, 0.5235988F);
setRotation(this.of3, -0.5235988F, 0.0F, 0.0F);
setRotation(this.of4, 0.5235988F, 0.0F, 0.0F);
setRotation(this.center, 0.0F, 0.0F, 0.0F);
setRotation(this.tis1, 0.2617994F, 0.0F, 0.0F);
setRotation(this.tis2, -0.2617994F, 0.0F, 0.0F);
setRotation(this.tis3, 0.0F, 0.0F, 0.2617994F);
setRotation(this.tis4, 0.0F, 0.0F, -0.2617994F);
setRotation(this.tos1, -0.5235988F, 0.0F, 0.0F);
setRotation(this.tos2, 0.0F, 0.0F, -0.5235988F);
setRotation(this.tos3, 0.0F, 0.0F, 0.5235988F);
setRotation(this.tos4, 0.5235988F, 0.0F, 0.0F);

     float newangle, newangle1, newangle2, newangle3, newangle4, newangle5, newangle6, newangle7, newangle8;
     UrchinEntity.CustomEntity u = (UrchinEntity.CustomEntity) entity;

     if (limbSwingAmount > 0.1D) {
       newangle = MathHelper.cos(ageInTicks * 0.7F * this.wingspeed) * 3.1415927F * 0.15F * limbSwingAmount;
       newangle1 = MathHelper.cos(ageInTicks * 1.7F * this.wingspeed) * 3.1415927F * 0.15F * limbSwingAmount;
       newangle2 = MathHelper.cos(ageInTicks * 1.65F * this.wingspeed) * 3.1415927F * 0.15F * limbSwingAmount;
       newangle3 = MathHelper.cos(ageInTicks * 1.75F * this.wingspeed) * 3.1415927F * 0.15F * limbSwingAmount;
       newangle4 = MathHelper.cos(ageInTicks * 1.8F * this.wingspeed) * 3.1415927F * 0.15F * limbSwingAmount;
     } else {
       newangle = 0.0F;
       newangle1 = 0.0F;
       newangle2 = 0.0F;
       newangle3 = 0.0F;
       newangle4 = 0.0F;
     } 
     
     this.if1.rotateAngleX = 0.261F + newangle1;
     this.if2.rotateAngleX = -0.261F - newangle2;
     this.if3.rotateAngleX = newangle3;
     this.if4.rotateAngleX = -newangle4;
     
     this.of1.rotateAngleZ = -0.523F + newangle;
     this.of2.rotateAngleZ = 0.523F - newangle;
     this.of3.rotateAngleX = -0.523F + newangle;
     this.of4.rotateAngleX = 0.523F - newangle;
     
     if (u.getAttacking() != 0) {
       newangle = (float)((ageInTicks * 0.2F) % 6.283185307179586D);
       newangle1 = MathHelper.cos(ageInTicks * 0.7F * this.wingspeed) * 3.1415927F * 0.06F;
       newangle2 = MathHelper.cos(ageInTicks * 0.65F * this.wingspeed) * 3.1415927F * 0.06F;
       newangle3 = MathHelper.cos(ageInTicks * 0.75F * this.wingspeed) * 3.1415927F * 0.06F;
       newangle4 = MathHelper.cos(ageInTicks * 0.8F * this.wingspeed) * 3.1415927F * 0.06F;
       newangle5 = MathHelper.cos(ageInTicks * 0.55F * this.wingspeed) * 3.1415927F * 0.06F;
       newangle6 = MathHelper.cos(ageInTicks * 0.45F * this.wingspeed) * 3.1415927F * 0.06F;
       newangle7 = MathHelper.cos(ageInTicks * 0.35F * this.wingspeed) * 3.1415927F * 0.06F;
       newangle8 = MathHelper.cos(ageInTicks * 0.4F * this.wingspeed) * 3.1415927F * 0.06F;
     } else {
       newangle = (float)((ageInTicks * 0.02F) % 6.283185307179586D);
       newangle1 = MathHelper.cos(ageInTicks * 0.07F * this.wingspeed) * 3.1415927F * 0.02F;
       newangle2 = MathHelper.cos(ageInTicks * 0.065F * this.wingspeed) * 3.1415927F * 0.02F;
       newangle3 = MathHelper.cos(ageInTicks * 0.075F * this.wingspeed) * 3.1415927F * 0.02F;
       newangle4 = MathHelper.cos(ageInTicks * 0.08F * this.wingspeed) * 3.1415927F * 0.02F;
       newangle5 = MathHelper.cos(ageInTicks * 0.055F * this.wingspeed) * 3.1415927F * 0.02F;
       newangle6 = MathHelper.cos(ageInTicks * 0.045F * this.wingspeed) * 3.1415927F * 0.02F;
       newangle7 = MathHelper.cos(ageInTicks * 0.035F * this.wingspeed) * 3.1415927F * 0.02F;
       newangle8 = MathHelper.cos(ageInTicks * 0.04F * this.wingspeed) * 3.1415927F * 0.02F;
     } 
     this.center.rotateAngleY = newangle;
     
     this.tis1.rotateAngleX = 0.261F + newangle1;
     this.tis2.rotateAngleX = -0.261F + newangle2;
     this.tis3.rotateAngleX = newangle3;
     this.tis4.rotateAngleX = newangle4;
     this.tis1.rotateAngleZ = newangle5;
     this.tis2.rotateAngleZ = newangle6;
     this.tis3.rotateAngleZ = 0.261F + newangle7;
     this.tis4.rotateAngleZ = -0.261F + newangle8;
 
     
     this.tos1.rotateAngleX = -0.532F + newangle1;
     this.tos2.rotateAngleX = newangle7;
     this.tos3.rotateAngleX = newangle3;
     this.tos4.rotateAngleX = 0.532F + newangle5;
     this.tos1.rotateAngleZ = newangle4;
     this.tos2.rotateAngleZ = -0.523F + newangle6;
     this.tos3.rotateAngleZ = 0.523F + newangle2;
     this.tos4.rotateAngleZ = newangle8;
    }
 }
}