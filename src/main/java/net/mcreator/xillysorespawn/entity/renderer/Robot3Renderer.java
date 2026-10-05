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
import net.mcreator.xillysorespawn.entity.Robot3Entity;
import net.mcreator.xillysorespawn.entity.OreSpawnLogic;

@OnlyIn(Dist.CLIENT)
public class Robot3Renderer {
    private static final String TEXTURE = "xillys_orespawn:textures/entities/robot3.png";
    public static class ModelRegisterHandler {
        @SubscribeEvent @OnlyIn(Dist.CLIENT)
        public void registerModels(ModelRegistryEvent event) {
            RenderingRegistry.registerEntityRenderingHandler(Robot3Entity.entity, manager ->
                new MobRenderer(manager, new ModelRobot3(1.0F), 0.5F) {
                    @Override public ResourceLocation getEntityTexture(Entity entity) { return new ResourceLocation(TEXTURE); }
                    @Override protected void preRenderCallback(LivingEntity entity, MatrixStack stack, float partialTick) {
                        float modelScale = 0.5F;
                        stack.scale(modelScale, modelScale, modelScale);
                    }
                });
        }
    }

public static class ModelRobot3 extends EntityModel<Entity>
 {
   private float wingspeed = 1.0F;

   private final ModelRenderer rleg1;

   private final ModelRenderer lleg1;
   private final ModelRenderer rleg2;
   private final ModelRenderer lleg2;
   private final ModelRenderer hips;
   private final ModelRenderer waist1;
   private final ModelRenderer waist2;
   private final ModelRenderer body3;
   private final ModelRenderer lazer;
   private final ModelRenderer body2;
   private final ModelRenderer body1;
   private final ModelRenderer body4;
   private final ModelRenderer waist3;
   private final ModelRenderer larm3;
   private final ModelRenderer rarm3;
   private final ModelRenderer larm2;
   private final ModelRenderer rarm2;
   private final ModelRenderer larm1;
   private final ModelRenderer rarm1;

   public ModelRobot3(float f1) {
     this.wingspeed = f1;

     this.textureWidth = 512;
     this.textureHeight = 512;

     this.rleg1 = new ModelRenderer(this, 20, 100);
     this.rleg1.addBox(-23.0F, 26.0F, -8.0F, 16, 29, 16);
     this.rleg1.setRotationPoint(-9.0F, -31.0F, 0.0F);

     this.rleg1.mirror = true;
     setRotation(this.rleg1, 0.0F, 0.0F, 0.0F);
     this.lleg1 = new ModelRenderer(this, 20, 159);
     this.lleg1.addBox(7.0F, 25.0F, -8.0F, 16, 29, 16);
     this.lleg1.setRotationPoint(9.0F, -30.0F, 0.0F);

     this.lleg1.mirror = true;
     setRotation(this.lleg1, 0.0F, 0.0F, 0.0F);
     this.rleg2 = new ModelRenderer(this, 20, 212);
     this.rleg2.addBox(-14.0F, 0.0F, -7.0F, 14, 29, 14);
     this.rleg2.setRotationPoint(-9.0F, -31.0F, 0.0F);

     this.rleg2.mirror = true;
     setRotation(this.rleg2, 0.0F, 0.0F, 0.2792527F);
     this.lleg2 = new ModelRenderer(this, 20, 265);
     this.lleg2.addBox(0.0F, 0.0F, -7.0F, 13, 29, 14);
     this.lleg2.setRotationPoint(9.0F, -31.0F, 0.0F);

     this.lleg2.mirror = true;
     setRotation(this.lleg2, 0.0F, 0.0F, -0.2792527F);
     this.hips = new ModelRenderer(this, 20, 316);
     this.hips.addBox(0.0F, 0.0F, 0.0F, 18, 16, 16);
     this.hips.setRotationPoint(-9.0F, -43.0F, -8.0F);

     this.hips.mirror = true;
     setRotation(this.hips, 0.0F, 0.0F, 0.0F);
     this.waist1 = new ModelRenderer(this, 20, 359);
     this.waist1.addBox(0.0F, 0.0F, 0.0F, 12, 12, 12);
     this.waist1.setRotationPoint(-6.0F, -55.0F, -4.0F);

     this.waist1.mirror = true;
     setRotation(this.waist1, -0.1F, 0.0F, 0.0F);
     this.waist2 = new ModelRenderer(this, 20, 391);
     this.waist2.addBox(0.0F, 0.0F, 0.0F, 12, 12, 12);
     this.waist2.setRotationPoint(-6.0F, -67.0F, -4.0F);

     this.waist2.mirror = true;
     setRotation(this.waist2, 0.0F, 0.0F, 0.0F);
     this.body3 = new ModelRenderer(this, 20, 426);
     this.body3.addBox(-23.0F, -25.0F, 10.0F, 47, 47, 25);
     this.body3.setRotationPoint(0.0F, -88.0F, -10.0F);

     this.body3.mirror = true;
     setRotation(this.body3, 0.2F, 0.0F, 0.0F);
     this.lazer = new ModelRenderer(this, 20, 50);
     this.lazer.addBox(-8.0F, -8.0F, -22.0F, 17, 16, 22);
     this.lazer.setRotationPoint(0.0F, -88.0F, -11.0F);

     this.lazer.mirror = true;
     setRotation(this.lazer, 0.4F, 0.0F, 0.0F);
     this.body2 = new ModelRenderer(this, 101, 103);
     this.body2.addBox(9.0F, -24.0F, -12.0F, 15, 47, 47);
     this.body2.setRotationPoint(0.0F, -88.0F, -11.0F);

     this.body2.mirror = true;
     setRotation(this.body2, 0.2F, 0.0F, 0.0F);
     this.body1 = new ModelRenderer(this, 101, 210);
     this.body1.addBox(-23.0F, -24.0F, -12.0F, 15, 47, 47);
     this.body1.setRotationPoint(0.0F, -88.0F, -11.0F);

     this.body1.mirror = true;
     setRotation(this.body1, 0.2F, 0.0F, 0.0F);
     this.body4 = new ModelRenderer(this, 101, 321);
     this.body4.addBox(-8.0F, -24.0F, -12.0F, 18, 16, 22);
     this.body4.setRotationPoint(0.0F, -88.0F, -11.0F);

     this.body4.mirror = true;
     setRotation(this.body4, 0.2F, 0.0F, 0.0F);
     this.waist3 = new ModelRenderer(this, 99, 375);
     this.waist3.addBox(0.0F, 0.0F, -1.0F, 12, 17, 12);
     this.waist3.setRotationPoint(-6.0F, -83.0F, -6.0F);

     this.waist3.mirror = true;
     setRotation(this.waist3, 0.2F, 0.0F, 0.0F);
     this.larm3 = new ModelRenderer(this, 121, 54);
     this.larm3.addBox(0.0F, -10.0F, -9.0F, 20, 18, 18);
     this.larm3.setRotationPoint(24.0F, -92.0F, 2.0F);

     this.larm3.mirror = true;
     setRotation(this.larm3, 1.0F, 0.0F, 0.0F);
     this.rarm3 = new ModelRenderer(this, 26, 8);
     this.rarm3.addBox(-20.0F, -9.0F, -9.0F, 20, 18, 18);
     this.rarm3.setRotationPoint(-23.0F, -92.0F, 2.0F);

     this.rarm3.mirror = true;
     setRotation(this.rarm3, 1.0F, 0.0F, 0.0F);
     this.larm2 = new ModelRenderer(this, 207, 47);
     this.larm2.addBox(3.0F, 8.0F, -7.0F, 14, 29, 14);
     this.larm2.setRotationPoint(24.0F, -92.0F, 2.0F);

     this.larm2.mirror = true;
     setRotation(this.larm2, 1.0F, 0.0F, 0.0F);
     this.rarm2 = new ModelRenderer(this, 161, 372);
     this.rarm2.addBox(-17.0F, 9.0F, -7.0F, 14, 29, 14);
     this.rarm2.setRotationPoint(-23.0F, -92.0F, 2.0F);

     this.rarm2.mirror = true;
     setRotation(this.rarm2, 1.0F, 0.0F, 0.0F);
     this.larm1 = new ModelRenderer(this, 185, 433);
     this.larm1.addBox(0.0F, -12.0F, 30.0F, 14, 37, 14);
     this.larm1.setRotationPoint(27.0F, -92.0F, 2.0F);

     this.larm1.mirror = true;
     setRotation(this.larm1, -1.0F, 0.0F, 0.0F);
     this.rarm1 = new ModelRenderer(this, 239, 105);
     this.rarm1.addBox(-17.0F, -12.0F, 30.0F, 14, 37, 14);
     this.rarm1.setRotationPoint(-23.0F, -92.0F, 2.0F);

     this.rarm1.mirror = true;
     setRotation(this.rarm1, -1.0F, 0.0F, 0.0F);
   }

   public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay,
        float red, float green, float blue, float alpha) {
        rleg1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        lleg1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rleg2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        lleg2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        hips.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        waist1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        waist2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        body3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        lazer.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        body2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        body1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        body4.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        waist3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        larm3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rarm3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        larm2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rarm2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        larm1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rarm1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }

   private void setRotation(ModelRenderer model, float x, float y, float z) {
     model.rotateAngleX = x;
     model.rotateAngleY = y;
     model.rotateAngleZ = z;
   }

   public void setRotationAngles(Entity entity, float limbSwing, float limbSwingAmount,
        float ageInTicks, float netHeadYaw, float headPitch) {
// Reset shared model state before applying this frame's legacy animation.
this.rleg1.setRotationPoint(-9.0F, -31.0F, 0.0F);
this.lleg1.setRotationPoint(9.0F, -30.0F, 0.0F);
this.rleg2.setRotationPoint(-9.0F, -31.0F, 0.0F);
this.lleg2.setRotationPoint(9.0F, -31.0F, 0.0F);
this.hips.setRotationPoint(-9.0F, -43.0F, -8.0F);
this.waist1.setRotationPoint(-6.0F, -55.0F, -4.0F);
this.waist2.setRotationPoint(-6.0F, -67.0F, -4.0F);
this.body3.setRotationPoint(0.0F, -88.0F, -10.0F);
this.lazer.setRotationPoint(0.0F, -88.0F, -11.0F);
this.body2.setRotationPoint(0.0F, -88.0F, -11.0F);
this.body1.setRotationPoint(0.0F, -88.0F, -11.0F);
this.body4.setRotationPoint(0.0F, -88.0F, -11.0F);
this.waist3.setRotationPoint(-6.0F, -83.0F, -6.0F);
this.larm3.setRotationPoint(24.0F, -92.0F, 2.0F);
this.rarm3.setRotationPoint(-23.0F, -92.0F, 2.0F);
this.larm2.setRotationPoint(24.0F, -92.0F, 2.0F);
this.rarm2.setRotationPoint(-23.0F, -92.0F, 2.0F);
this.larm1.setRotationPoint(27.0F, -92.0F, 2.0F);
this.rarm1.setRotationPoint(-23.0F, -92.0F, 2.0F);
setRotation(this.rleg1, 0.0F, 0.0F, 0.0F);
setRotation(this.lleg1, 0.0F, 0.0F, 0.0F);
setRotation(this.rleg2, 0.0F, 0.0F, 0.2792527F);
setRotation(this.lleg2, 0.0F, 0.0F, -0.2792527F);
setRotation(this.hips, 0.0F, 0.0F, 0.0F);
setRotation(this.waist1, -0.1F, 0.0F, 0.0F);
setRotation(this.waist2, 0.0F, 0.0F, 0.0F);
setRotation(this.body3, 0.2F, 0.0F, 0.0F);
setRotation(this.lazer, 0.4F, 0.0F, 0.0F);
setRotation(this.body2, 0.2F, 0.0F, 0.0F);
setRotation(this.body1, 0.2F, 0.0F, 0.0F);
setRotation(this.body4, 0.2F, 0.0F, 0.0F);
setRotation(this.waist3, 0.2F, 0.0F, 0.0F);
setRotation(this.larm3, 1.0F, 0.0F, 0.0F);
setRotation(this.rarm3, 1.0F, 0.0F, 0.0F);
setRotation(this.larm2, 1.0F, 0.0F, 0.0F);
setRotation(this.rarm2, 1.0F, 0.0F, 0.0F);
setRotation(this.larm1, -1.0F, 0.0F, 0.0F);
setRotation(this.rarm1, -1.0F, 0.0F, 0.0F);

     Robot3Entity.CustomEntity e = (Robot3Entity.CustomEntity) entity;
     RenderInfo r = null;
     float newangle = 0.0F;
     float nextangle = 0.0F;

     if (limbSwingAmount > 0.1D) {
       newangle = MathHelper.cos(ageInTicks * 0.55F * this.wingspeed) * 3.1415927F * 0.12F * limbSwingAmount;
     } else {
       newangle = 0.0F;
     } 
     
     this.lleg1.rotateAngleX = newangle;
     this.lleg2.rotateAngleX = newangle;
     this.rleg1.rotateAngleX = -newangle;
     this.rleg2.rotateAngleX = -newangle;
     
     this.lazer.rotateAngleY = (float)Math.toRadians(netHeadYaw / 2.0D);
 
     
     r = e.getRenderInfo();
 
 
     
     newangle = MathHelper.cos(ageInTicks * 1.0F * this.wingspeed) * 3.1415927F * 0.15F;
     nextangle = MathHelper.cos((ageInTicks + 0.3F) * 1.0F * this.wingspeed) * 3.1415927F * 0.15F;
 
     
     if (nextangle > 0.0F && newangle < 0.0F) {
       
       r.ri1 = 0;
       if (e.getAttacking() != 0) {
         r.ri1 = 1;
       }
     } 
 
     
     if (r.ri1 == 0) {
       newangle = 0.0F;
     }
     this.rarm1.rotateAngleX = newangle - 1.0F;
     this.rarm2.rotateAngleX = newangle + 1.0F;
     this.rarm3.rotateAngleX = newangle + 1.0F;
     this.larm1.rotateAngleX = newangle - 1.0F;
     this.larm2.rotateAngleX = newangle + 1.0F;
     this.larm3.rotateAngleX = newangle + 1.0F;
 
 
 
 
 
 
 
     
     e.setRenderInfo(r);
    }
 }
}