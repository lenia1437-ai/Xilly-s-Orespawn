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
import net.mcreator.xillysorespawn.entity.GiantRobotEntity;
import net.mcreator.xillysorespawn.entity.OreSpawnLogic;

@OnlyIn(Dist.CLIENT)
public class GiantRobotRenderer {
    private static final String TEXTURE = "xillys_orespawn:textures/entities/giantrobottexture.png";
    public static class ModelRegisterHandler {
        @SubscribeEvent @OnlyIn(Dist.CLIENT)
        public void registerModels(ModelRegistryEvent event) {
            RenderingRegistry.registerEntityRenderingHandler(GiantRobotEntity.entity, manager ->
                new MobRenderer(manager, new ModelGiantRobot(0.25F), 0.99F) {
                    @Override public ResourceLocation getEntityTexture(Entity entity) { return new ResourceLocation(TEXTURE); }
                    @Override protected void preRenderCallback(LivingEntity entity, MatrixStack stack, float partialTick) {
                        float modelScale = 1.0F;
                        stack.scale(modelScale, modelScale, modelScale);
                    }
                });
        }
    }

public static class ModelGiantRobot extends EntityModel<Entity>
 {
   private float wingspeed = 1.0F;
   private float hipy = 0.0F;
   private float leftThigh, rightThigh, leftShin, rightShin, leftArm, rightArm;

   private final ModelRenderer Hip;

   private final ModelRenderer Thigh;
   private final ModelRenderer Shin;
   private final ModelRenderer Foot1;
   private final ModelRenderer Foot2;
   private final ModelRenderer Foot3;
   private final ModelRenderer Thigh2;
   private final ModelRenderer Thigh3;
   private final ModelRenderer Back1;
   private final ModelRenderer Back2;
   private final ModelRenderer Back3;
   private final ModelRenderer Shoulders;
   private final ModelRenderer Neck;
   private final ModelRenderer Head;
   private final ModelRenderer Arm1;
   private final ModelRenderer Arm2;
   private final ModelRenderer Arm3;
   private final ModelRenderer Knuckles;

   public ModelGiantRobot(float f1) {
     this.wingspeed = f1;
     this.textureWidth = 256;
     this.textureHeight = 512;

     this.Hip = new ModelRenderer(this, 0, 0);
     this.Hip.addBox(-4.0F, -4.0F, -15.0F, 8, 8, 30);
     this.Hip.setRotationPoint(0.0F, -60.0F, 0.0F);

     this.Hip.mirror = true;
     setRotation(this.Hip, 0.0F, 0.0F, 0.0F);
     this.Thigh = new ModelRenderer(this, 0, 115);
     this.Thigh.addBox(-3.0F, -3.0F, -3.0F, 6, 43, 6);
     this.Thigh.setRotationPoint(0.0F, -58.0F, 0.0F);

     this.Thigh.mirror = true;
     setRotation(this.Thigh, 0.0F, 0.0F, 0.0F);
     this.Shin = new ModelRenderer(this, 0, 167);
     this.Shin.addBox(-3.0F, -3.0F, -3.0F, 6, 43, 6);
     this.Shin.setRotationPoint(0.0F, -18.0F, 0.0F);

     this.Shin.mirror = true;
     setRotation(this.Shin, 0.0F, 0.0F, 0.0F);
     this.Foot1 = new ModelRenderer(this, 0, 282);
     this.Foot1.addBox(-7.0F, 38.0F, -11.0F, 14, 4, 17);
     this.Foot1.setRotationPoint(0.0F, -18.0F, 0.0F);

     this.Foot1.mirror = true;
     setRotation(this.Foot1, 0.0F, 0.0F, 0.0F);
     this.Foot2 = new ModelRenderer(this, 0, 246);
     this.Foot2.addBox(-6.0F, 19.0F, -8.0F, 12, 19, 13);
     this.Foot2.setRotationPoint(0.0F, -18.0F, 0.0F);

     this.Foot2.mirror = true;
     setRotation(this.Foot2, 0.0F, 0.0F, 0.0F);
     this.Foot3 = new ModelRenderer(this, 0, 219);
     this.Foot3.addBox(-5.0F, 5.0F, -5.0F, 10, 14, 9);
     this.Foot3.setRotationPoint(0.0F, -18.0F, 0.0F);

     this.Foot3.mirror = true;
     setRotation(this.Foot3, 0.0F, 0.0F, 0.0F);
     this.Thigh2 = new ModelRenderer(this, 0, 43);
     this.Thigh2.addBox(-7.0F, -8.0F, -7.0F, 14, 24, 14);
     this.Thigh2.setRotationPoint(0.0F, -58.0F, 0.0F);

     this.Thigh2.mirror = true;
     setRotation(this.Thigh2, 0.0F, 0.0F, 0.0F);
     this.Thigh3 = new ModelRenderer(this, 0, 84);
     this.Thigh3.addBox(-5.0F, 16.0F, -5.0F, 10, 17, 10);
     this.Thigh3.setRotationPoint(0.0F, -58.0F, 0.0F);

     this.Thigh3.mirror = true;
     setRotation(this.Thigh3, 0.0F, 0.0F, 0.0F);
     this.Back1 = new ModelRenderer(this, 125, 138);
     this.Back1.addBox(-4.0F, -20.0F, -4.0F, 8, 24, 8);
     this.Back1.setRotationPoint(0.0F, -60.0F, 0.0F);

     this.Back1.mirror = true;
     setRotation(this.Back1, 0.0F, 0.0F, 0.0F);
     this.Back2 = new ModelRenderer(this, 125, 95);
     this.Back2.addBox(-13.0F, -42.0F, -10.0F, 26, 24, 16);
     this.Back2.setRotationPoint(0.0F, -60.0F, 0.0F);

     this.Back2.mirror = true;
     setRotation(this.Back2, 0.0F, 0.0F, 0.0F);
     this.Back3 = new ModelRenderer(this, 125, 43);
     this.Back3.addBox(-17.0F, -68.0F, -13.0F, 34, 26, 20);
     this.Back3.setRotationPoint(0.0F, -60.0F, 0.0F);

     this.Back3.mirror = true;
     setRotation(this.Back3, 0.0F, 0.0F, 0.0F);
     this.Shoulders = new ModelRenderer(this, 60, 200);
     this.Shoulders.addBox(-22.0F, -64.0F, -4.0F, 44, 8, 8);
     this.Shoulders.setRotationPoint(0.0F, -60.0F, 0.0F);

     this.Shoulders.mirror = true;
     setRotation(this.Shoulders, 0.0F, 0.0F, 0.0F);
     this.Neck = new ModelRenderer(this, 125, 29);
     this.Neck.addBox(-4.0F, -70.0F, -4.0F, 8, 2, 8);
     this.Neck.setRotationPoint(0.0F, -60.0F, 0.0F);

     this.Neck.mirror = true;
     setRotation(this.Neck, 0.0F, 0.0F, 0.0F);
     this.Head = new ModelRenderer(this, 127, 0);
     this.Head.addBox(-7.0F, -82.0F, -7.0F, 14, 12, 14);
     this.Head.setRotationPoint(0.0F, -60.0F, 0.0F);

     this.Head.mirror = true;
     setRotation(this.Head, 0.0F, 0.0F, 0.0F);
     this.Arm1 = new ModelRenderer(this, 77, 250);
     this.Arm1.addBox(-6.0F, -6.0F, -6.0F, 12, 21, 12);
     this.Arm1.setRotationPoint(28.0F, -120.0F, 0.0F);

     this.Arm1.mirror = true;
     setRotation(this.Arm1, 0.0F, 0.0F, 0.0F);
     this.Arm2 = new ModelRenderer(this, 73, 300);
     this.Arm2.addBox(-4.0F, 15.0F, -4.0F, 8, 24, 8);
     this.Arm2.setRotationPoint(28.0F, -120.0F, 0.0F);

     this.Arm2.mirror = true;
     setRotation(this.Arm2, 0.0F, 0.0F, 0.0F);
     this.Arm3 = new ModelRenderer(this, 61, 350);
     this.Arm3.addBox(-3.0F, -3.0F, -3.0F, 6, 33, 6);
     this.Arm3.setRotationPoint(28.0F, -81.0F, 0.0F);

     this.Arm3.mirror = true;
     setRotation(this.Arm3, 0.0F, 0.0F, 0.0F);
     this.Knuckles = new ModelRenderer(this, 56, 400);
     this.Knuckles.addBox(-7.0F, 30.0F, -5.0F, 14, 12, 10);
     this.Knuckles.setRotationPoint(28.0F, -81.0F, 0.0F);

     this.Knuckles.mirror = true;
     setRotation(this.Knuckles, 0.0F, 0.0F, 0.0F);

     this.hipy = this.Hip.rotationPointY;
   }

   public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay,
        float red, float green, float blue, float alpha) {
        Hip.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Back1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Back2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Back3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Shoulders.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Neck.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Head.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        renderLeg(-8.0F, this.leftThigh, this.leftShin, matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        renderLeg(8.0F, this.rightThigh, this.rightShin, matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        renderArm(-28.0F, this.leftArm, matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        renderArm(28.0F, this.rightArm, matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }

   private void renderLeg(float x, float thighAngle, float shinAngle, MatrixStack matrixStack,
        IVertexBuilder buffer, int light, int overlay, float red, float green, float blue, float alpha) {
     Thigh.setRotationPoint(x, -58.0F, 0.0F); Thigh2.setRotationPoint(x, -58.0F, 0.0F); Thigh3.setRotationPoint(x, -58.0F, 0.0F);
     Thigh.rotateAngleX = thighAngle; Thigh2.rotateAngleX = thighAngle; Thigh3.rotateAngleX = thighAngle;
     float kneeY = -58.0F + MathHelper.cos(thighAngle) * 40.0F;
     float kneeZ = MathHelper.sin(thighAngle) * 40.0F;
     Shin.setRotationPoint(x, kneeY, kneeZ); Foot1.setRotationPoint(x, kneeY, kneeZ); Foot2.setRotationPoint(x, kneeY, kneeZ); Foot3.setRotationPoint(x, kneeY, kneeZ);
     Shin.rotateAngleX = shinAngle; Foot1.rotateAngleX = shinAngle; Foot2.rotateAngleX = shinAngle; Foot3.rotateAngleX = shinAngle;
     Thigh.render(matrixStack, buffer, light, overlay, red, green, blue, alpha); Thigh2.render(matrixStack, buffer, light, overlay, red, green, blue, alpha); Thigh3.render(matrixStack, buffer, light, overlay, red, green, blue, alpha);
     Shin.render(matrixStack, buffer, light, overlay, red, green, blue, alpha); Foot1.render(matrixStack, buffer, light, overlay, red, green, blue, alpha); Foot2.render(matrixStack, buffer, light, overlay, red, green, blue, alpha); Foot3.render(matrixStack, buffer, light, overlay, red, green, blue, alpha);
   }

   private void renderArm(float x, float angle, MatrixStack matrixStack, IVertexBuilder buffer,
        int light, int overlay, float red, float green, float blue, float alpha) {
     Arm1.setRotationPoint(x, -120.0F, 0.0F); Arm2.setRotationPoint(x, -120.0F, 0.0F);
     float elbowY = -120.0F + MathHelper.cos(angle) * 39.0F;
     float elbowZ = MathHelper.sin(angle) * 39.0F;
     Arm3.setRotationPoint(x, elbowY, elbowZ); Knuckles.setRotationPoint(x, elbowY, elbowZ);
     Arm1.rotateAngleX = angle; Arm2.rotateAngleX = angle; Arm3.rotateAngleX = angle * 0.65F; Knuckles.rotateAngleX = angle * 0.65F;
     Arm1.render(matrixStack, buffer, light, overlay, red, green, blue, alpha); Arm2.render(matrixStack, buffer, light, overlay, red, green, blue, alpha); Arm3.render(matrixStack, buffer, light, overlay, red, green, blue, alpha); Knuckles.render(matrixStack, buffer, light, overlay, red, green, blue, alpha);
   }
   private void setRotation(ModelRenderer model, float x, float y, float z) {
     model.rotateAngleX = x;
     model.rotateAngleY = y;
     model.rotateAngleZ = z;
   }

   public void setRotationAngles(Entity entity, float limbSwing, float limbSwingAmount,
        float ageInTicks, float netHeadYaw, float headPitch) {
     float moveScale = Math.min(limbSwingAmount * 0.65F, 1.0F);
     float phase = -ageInTicks * this.wingspeed;
     this.Hip.rotationPointY = this.hipy + MathHelper.cos(phase * 2.0F) * moveScale * 4.0F;
     this.Hip.rotateAngleX = -MathHelper.cos(phase) * (float)Math.PI * 0.1F * moveScale;
     this.Hip.rotateAngleY = -MathHelper.sin(phase) * (float)Math.PI * 0.1F * moveScale;
     this.leftThigh = MathHelper.cos(phase + (float)Math.PI / 2.0F) * (float)Math.PI * 0.15F * moveScale - (float)Math.PI / 16.0F * moveScale;
     this.rightThigh = MathHelper.cos(phase + (float)Math.PI * 1.5F) * (float)Math.PI * 0.15F * moveScale - (float)Math.PI / 16.0F * moveScale;
     this.leftShin = MathHelper.cos(phase + (float)Math.PI) * (float)Math.PI * 0.2F * moveScale + (float)Math.PI * 0.2F * moveScale;
     this.rightShin = MathHelper.cos(phase) * (float)Math.PI * 0.2F * moveScale + (float)Math.PI * 0.2F * moveScale;
     float bodyPitch = -this.Hip.rotateAngleX * 0.35F;
     this.Back1.rotateAngleX = bodyPitch; this.Back2.rotateAngleX = bodyPitch; this.Back3.rotateAngleX = bodyPitch;
     this.Shoulders.rotateAngleX = bodyPitch; this.Neck.rotateAngleX = bodyPitch;
     this.Head.rotateAngleY = (float)Math.toRadians(netHeadYaw) * 0.55F;
     this.Head.rotateAngleX = bodyPitch + (float)Math.toRadians(headPitch) * 0.35F;
     this.leftArm = MathHelper.cos(phase + (float)Math.PI) * (float)Math.PI * 0.22F * moveScale;
     this.rightArm = MathHelper.cos(phase) * (float)Math.PI * 0.22F * moveScale;
     if (((GiantRobotEntity.CustomEntity)entity).getAttacking() != 0) {
       float strike = 0.75F + MathHelper.cos(ageInTicks * 0.45F) * 0.35F;
       this.leftArm = -strike; this.rightArm = -strike;
     }
   }
 }
}
