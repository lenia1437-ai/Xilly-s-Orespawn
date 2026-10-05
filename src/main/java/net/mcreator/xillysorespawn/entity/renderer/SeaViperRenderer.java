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
import net.mcreator.xillysorespawn.entity.SeaViperEntity;
import net.mcreator.xillysorespawn.entity.OreSpawnLogic;

@OnlyIn(Dist.CLIENT)
public class SeaViperRenderer {
    private static final String TEXTURE = "xillys_orespawn:textures/entities/seavipertexture.png";
    public static class ModelRegisterHandler {
        @SubscribeEvent @OnlyIn(Dist.CLIENT)
        public void registerModels(ModelRegistryEvent event) {
            RenderingRegistry.registerEntityRenderingHandler(SeaViperEntity.entity, manager ->
                new MobRenderer(manager, new ModelSeaViper(0.5F), 1.0F) {
                    @Override public ResourceLocation getEntityTexture(Entity entity) { return new ResourceLocation(TEXTURE); }
                    @Override protected void preRenderCallback(LivingEntity entity, MatrixStack stack, float partialTick) {
                        float modelScale = 1.0F;
                        stack.scale(modelScale, modelScale, modelScale);
                    }
                });
        }
    }

public static class ModelSeaViper extends EntityModel<Entity>
 {
   private float wingspeed = 1.0F;

   private final ModelRenderer TailTip;

   private final ModelRenderer Neck;
   private final ModelRenderer tBase;
   private final ModelRenderer t2;
   private final ModelRenderer t3;
   private final ModelRenderer t4;
   private final ModelRenderer t5;
   private final ModelRenderer t6;
   private final ModelRenderer t7;
   private final ModelRenderer t8;
   private final ModelRenderer t9;
   private final ModelRenderer t10;
   private final ModelRenderer t12;
   private final ModelRenderer t11;
   private final ModelRenderer t13;
   private final ModelRenderer t14;
   private final ModelRenderer t15;
   private final ModelRenderer t16;
   private final ModelRenderer t17;
   private final ModelRenderer t18;
   private final ModelRenderer t19;
   private final ModelRenderer t20;
   private final ModelRenderer t21;
   private final ModelRenderer MouthBottom;
   private final ModelRenderer ToungBase;
   private final ModelRenderer MiddleTounge;
   private final ModelRenderer EyeRight;
   private final ModelRenderer EyeLeft;
   private final ModelRenderer MouthTop;
   private final ModelRenderer Head;
   private final ModelRenderer FangRight;
   private final ModelRenderer FangLeft;
   private final ModelRenderer ForkRight;
   private final ModelRenderer ForkLeft;

   public ModelSeaViper(float f1) {
     this.wingspeed = f1;

     this.textureWidth = 128;
     this.textureHeight = 128;

     this.TailTip = new ModelRenderer(this, 0, 90);
     this.TailTip.addBox(-1.0F, -1.0F, 0.0F, 2, 5, 10);
     this.TailTip.setRotationPoint(1.0F, 20.0F, 120.0F);

     this.TailTip.mirror = true;
     setRotation(this.TailTip, 0.0F, -0.6981317F, 0.0F);
     this.Neck = new ModelRenderer(this, 60, 60);
     this.Neck.addBox(-4.0F, -4.0F, -10.0F, 8, 8, 10);
     this.Neck.setRotationPoint(0.0F, 4.5F, -33.0F);

     this.Neck.mirror = true;
     setRotation(this.Neck, -0.2617994F, 0.0F, 0.0F);
     this.tBase = new ModelRenderer(this, 0, 31);
     this.tBase.addBox(-4.0F, -4.0F, 0.0F, 8, 8, 10);
     this.tBase.setRotationPoint(0.0F, 4.0F, -34.0F);

     this.tBase.mirror = true;
     setRotation(this.tBase, -0.5235988F, 0.0F, 0.0F);
     this.t2 = new ModelRenderer(this, 0, 31);
     this.t2.addBox(-4.0F, -4.0F, 0.0F, 8, 8, 10);
     this.t2.setRotationPoint(0.0F, 7.0F, -27.0F);

     this.t2.mirror = true;
     setRotation(this.t2, -1.047198F, 0.0F, 0.0F);
     this.t3 = new ModelRenderer(this, 0, 31);
     this.t3.addBox(-4.0F, -4.0F, 0.0F, 8, 8, 10);
     this.t3.setRotationPoint(0.0F, 14.0F, -24.0F);

     this.t3.mirror = true;
     setRotation(this.t3, -0.5235988F, 0.0F, 0.0F);
     this.t4 = new ModelRenderer(this, 0, 31);
     this.t4.addBox(-4.0F, -4.0F, 0.0F, 8, 8, 10);
     this.t4.setRotationPoint(0.0F, 19.0F, -17.0F);

     this.t4.mirror = true;
     setRotation(this.t4, -0.0872665F, 0.0F, 0.0F);
     this.t5 = new ModelRenderer(this, 0, 31);
     this.t5.addBox(-4.0F, -4.0F, 0.0F, 8, 8, 10);
     this.t5.setRotationPoint(0.0F, 20.0F, -9.0F);

     this.t5.mirror = true;
     setRotation(this.t5, 0.0F, 0.0F, 0.0F);
     this.t6 = new ModelRenderer(this, 0, 31);
     this.t6.addBox(-4.0F, -4.0F, 0.0F, 8, 8, 10);
     this.t6.setRotationPoint(0.0F, 20.0F, -1.0F);

     this.t6.mirror = true;
     setRotation(this.t6, 0.0F, 0.3490659F, 0.0F);
     this.t7 = new ModelRenderer(this, 0, 31);
     this.t7.addBox(-4.0F, -4.0F, 0.0F, 8, 8, 10);
     this.t7.setRotationPoint(2.0F, 20.0F, 6.0F);

     this.t7.mirror = true;
     setRotation(this.t7, 0.0F, 0.6981317F, 0.0F);
     this.t8 = new ModelRenderer(this, 0, 31);
     this.t8.addBox(-4.0F, -4.0F, 0.0F, 8, 8, 10);
     this.t8.setRotationPoint(7.0F, 20.0F, 12.0F);

     this.t8.mirror = true;
     setRotation(this.t8, 0.0F, 0.3490659F, 0.0F);
     this.t9 = new ModelRenderer(this, 0, 31);
     this.t9.addBox(-4.0F, -4.0F, 0.0F, 8, 8, 10);
     this.t9.setRotationPoint(10.0F, 20.0F, 20.0F);

     this.t9.mirror = true;
     setRotation(this.t9, 0.0F, 0.0F, 0.0F);
     this.t10 = new ModelRenderer(this, 0, 31);
     this.t10.addBox(-4.0F, -4.0F, 0.0F, 8, 8, 10);
     this.t10.setRotationPoint(10.0F, 20.0F, 28.0F);

     this.t10.mirror = true;
     setRotation(this.t10, 0.0F, -0.3490659F, 0.0F);
     this.t12 = new ModelRenderer(this, 0, 31);
     this.t12.addBox(-4.0F, -4.0F, 0.0F, 8, 8, 10);
     this.t12.setRotationPoint(2.0F, 20.0F, 42.0F);

     this.t12.mirror = true;
     setRotation(this.t12, 0.0F, -0.6981317F, 0.0F);
     this.t11 = new ModelRenderer(this, 0, 31);
     this.t11.addBox(-4.0F, -4.0F, 0.0F, 8, 8, 10);
     this.t11.setRotationPoint(8.0F, 20.0F, 35.0F);

     this.t11.mirror = true;
     setRotation(this.t11, 0.0F, -0.6981317F, 0.0F);
     this.t13 = new ModelRenderer(this, 0, 31);
     this.t13.addBox(-4.0F, -4.0F, 0.0F, 8, 8, 10);
     this.t13.setRotationPoint(-4.0F, 20.0F, 48.0F);

     this.t13.mirror = true;
     setRotation(this.t13, 0.0F, -0.3490659F, 0.0F);
     this.t14 = new ModelRenderer(this, 0, 51);
     this.t14.addBox(-3.0F, -3.0F, 0.0F, 6, 7, 10);
     this.t14.setRotationPoint(-8.0F, 20.0F, 56.0F);

     this.t14.mirror = true;
     setRotation(this.t14, 0.0F, 0.0F, 0.0F);
     this.t15 = new ModelRenderer(this, 0, 51);
     this.t15.addBox(-3.0F, -3.0F, 0.0F, 6, 7, 10);
     this.t15.setRotationPoint(-8.0F, 20.0F, 65.0F);

     this.t15.mirror = true;
     setRotation(this.t15, 0.0F, 0.3490659F, 0.0F);
     this.t16 = new ModelRenderer(this, 0, 51);
     this.t16.addBox(-3.0F, -3.0F, 0.0F, 6, 7, 10);
     this.t16.setRotationPoint(-5.0F, 20.0F, 73.0F);

     this.t16.mirror = true;
     setRotation(this.t16, 0.0F, 0.6981317F, 0.0F);
     this.t17 = new ModelRenderer(this, 0, 70);
     this.t17.addBox(-2.0F, -2.0F, 0.0F, 4, 6, 10);
     this.t17.setRotationPoint(1.0F, 20.0F, 80.0F);

     this.t17.mirror = true;
     setRotation(this.t17, 0.0F, 0.6981317F, 0.0F);
     this.t18 = new ModelRenderer(this, 0, 70);
     this.t18.addBox(-2.0F, -2.0F, 0.0F, 4, 6, 10);
     this.t18.setRotationPoint(7.0F, 20.0F, 87.0F);

     this.t18.mirror = true;
     setRotation(this.t18, 0.0F, 0.3490659F, 0.0F);
     this.t19 = new ModelRenderer(this, 0, 70);
     this.t19.addBox(-2.0F, -2.0F, 0.0F, 4, 6, 10);
     this.t19.setRotationPoint(10.0F, 20.0F, 95.0F);

     this.t19.mirror = true;
     setRotation(this.t19, 0.0F, 0.0F, 0.0F);
     this.t20 = new ModelRenderer(this, 0, 90);
     this.t20.addBox(-1.0F, -1.0F, 0.0F, 2, 5, 10);
     this.t20.setRotationPoint(10.0F, 20.0F, 104.0F);

     this.t20.mirror = true;
     setRotation(this.t20, 0.0F, -0.3490659F, 0.0F);
     this.t21 = new ModelRenderer(this, 0, 90);
     this.t21.addBox(-1.0F, -1.0F, 0.0F, 2, 5, 10);
     this.t21.setRotationPoint(7.0F, 20.0F, 113.0F);

     this.t21.mirror = true;
     setRotation(this.t21, 0.0F, -0.6981317F, 0.0F);
     this.MouthBottom = new ModelRenderer(this, 58, 78);
     this.MouthBottom.addBox(-4.0F, 0.0F, -12.0F, 8, 2, 12);
     this.MouthBottom.setRotationPoint(0.0F, 4.0F, -42.0F);

     this.MouthBottom.mirror = true;
     setRotation(this.MouthBottom, 0.5235988F, 0.0F, 0.0F);
     this.ToungBase = new ModelRenderer(this, 70, 17);
     this.ToungBase.addBox(-1.0F, -2.0F, -11.0F, 2, 1, 6);
     this.ToungBase.setRotationPoint(0.0F, 6.0F, -40.0F);

     this.ToungBase.mirror = true;
     setRotation(this.ToungBase, 0.2617994F, 0.0F, 0.0F);
     this.MiddleTounge = new ModelRenderer(this, 70, 10);
     this.MiddleTounge.addBox(-1.0F, -1.0F, -17.0F, 2, 1, 6);
     this.MiddleTounge.setRotationPoint(0.0F, 6.0F, -40.0F);

     this.MiddleTounge.mirror = true;
     setRotation(this.MiddleTounge, 0.1745329F, 0.0F, 0.0F);
     this.EyeRight = new ModelRenderer(this, 96, 60);
     this.EyeRight.addBox(-7.0F, -7.0F, -3.0F, 1, 3, 4);
     this.EyeRight.setRotationPoint(0.0F, 6.0F, -40.0F);

     this.EyeRight.mirror = true;
     setRotation(this.EyeRight, 0.3490659F, 0.0F, 0.0F);
     this.EyeLeft = new ModelRenderer(this, 50, 60);
     this.EyeLeft.addBox(6.0F, -7.0F, -3.0F, 1, 3, 4);
     this.EyeLeft.setRotationPoint(0.0F, 6.0F, -40.0F);

     this.EyeLeft.mirror = true;
     setRotation(this.EyeLeft, 0.3490659F, 0.0F, 0.0F);
     this.MouthTop = new ModelRenderer(this, 52, 24);
     this.MouthTop.addBox(-5.0F, -6.0F, -16.0F, 10, 6, 16);
     this.MouthTop.setRotationPoint(0.0F, 6.0F, -40.0F);

     this.MouthTop.mirror = true;
     setRotation(this.MouthTop, 0.0F, 0.0F, 0.0F);
     this.Head = new ModelRenderer(this, 60, 46);
     this.Head.addBox(-6.0F, -8.0F, -6.0F, 12, 8, 6);
     this.Head.setRotationPoint(0.0F, 6.0F, -40.0F);

     this.Head.mirror = true;
     setRotation(this.Head, 0.0F, 0.0F, 0.0F);
     this.FangRight = new ModelRenderer(this, 92, 18);
     this.FangRight.addBox(-4.0F, -3.0F, -15.0F, 1, 5, 1);
     this.FangRight.setRotationPoint(0.0F, 6.0F, -40.0F);

     this.FangRight.mirror = true;
     setRotation(this.FangRight, 0.1745329F, 0.0F, 0.0F);
     this.FangLeft = new ModelRenderer(this, 60, 18);
     this.FangLeft.addBox(3.0F, -3.0F, -15.0F, 1, 5, 1);
     this.FangLeft.setRotationPoint(0.0F, 6.0F, -40.0F);

     this.FangLeft.mirror = true;
     setRotation(this.FangLeft, 0.1745329F, 0.0F, 0.0F);
     this.ForkRight = new ModelRenderer(this, 60, 3);
     this.ForkRight.addBox(6.0F, 0.6F, -21.0F, 2, 1, 6);
     this.ForkRight.setRotationPoint(0.0F, 6.0F, -40.0F);

     this.ForkRight.mirror = true;
     setRotation(this.ForkRight, 0.0872665F, 0.4363323F, 0.0F);
     this.ForkLeft = new ModelRenderer(this, 80, 3);
     this.ForkLeft.addBox(-8.0F, 0.6F, -21.0F, 2, 1, 6);
     this.ForkLeft.setRotationPoint(0.0F, 6.0F, -40.0F);

     this.ForkLeft.mirror = true;
     setRotation(this.ForkLeft, 0.0872665F, -0.4363323F, 0.0F);

     this.TailTip.rotationPointZ += 32.0F;
     this.Neck.rotationPointZ += 32.0F;
     this.tBase.rotationPointZ += 32.0F;
     this.t2.rotationPointZ += 32.0F;
     this.t3.rotationPointZ += 32.0F;
     this.t4.rotationPointZ += 32.0F;
     this.t5.rotationPointZ += 32.0F;
     this.t6.rotationPointZ += 32.0F;
     this.t7.rotationPointZ += 32.0F;
     this.t8.rotationPointZ += 32.0F;
     this.t9.rotationPointZ += 32.0F;
     this.t10.rotationPointZ += 32.0F;
     this.t12.rotationPointZ += 32.0F;
     this.t11.rotationPointZ += 32.0F;
     this.t13.rotationPointZ += 32.0F;
     this.t14.rotationPointZ += 32.0F;
     this.t15.rotationPointZ += 32.0F;
     this.t16.rotationPointZ += 32.0F;
     this.t17.rotationPointZ += 32.0F;
     this.t18.rotationPointZ += 32.0F;
     this.t19.rotationPointZ += 32.0F;
     this.t20.rotationPointZ += 32.0F;
     this.t21.rotationPointZ += 32.0F;
     this.MouthBottom.rotationPointZ += 32.0F;
     this.ToungBase.rotationPointZ += 32.0F;
     this.MiddleTounge.rotationPointZ += 32.0F;
     this.EyeRight.rotationPointZ += 32.0F;
     this.EyeLeft.rotationPointZ += 32.0F;
     this.MouthTop.rotationPointZ += 32.0F;
     this.Head.rotationPointZ += 32.0F;
     this.FangRight.rotationPointZ += 32.0F;
     this.FangLeft.rotationPointZ += 32.0F;
     this.ForkRight.rotationPointZ += 32.0F;
     this.ForkLeft.rotationPointZ += 32.0F;
   }

   public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay,
        float red, float green, float blue, float alpha) {
        TailTip.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Neck.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        tBase.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        t2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        t3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        t4.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        t5.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        t6.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        t7.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        t8.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        t9.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        t10.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        t12.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        t11.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        t13.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        t14.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        t15.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        t16.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        t17.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        t18.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        t19.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        t20.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        t21.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        MouthBottom.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        ToungBase.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        MiddleTounge.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        EyeRight.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        EyeLeft.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        MouthTop.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Head.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        FangRight.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        FangLeft.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        ForkRight.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        ForkLeft.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }

   private void doseg(ModelRenderer inn, ModelRenderer notinn, float f, float f1, float f2) {
     float pi4 = 0.7853982F;
     float newangle = 0.0F;

     notinn.rotationPointZ = (float)(inn.rotationPointZ + (float)Math.cos(inn.rotateAngleY) * 9.0D * Math.abs(Math.cos(inn.rotateAngleX)));
     notinn.rotationPointX = (float)(inn.rotationPointX + ((float)Math.sin(inn.rotateAngleY) * 9.0F) * Math.abs(Math.cos(inn.rotateAngleX)));
     newangle = MathHelper.cos(f2 * 1.3F * this.wingspeed - pi4 * f) * 3.1415927F * 0.2F * f1;
     float a = MathHelper.cos(-(pi4 * f));
     notinn.rotateAngleY = newangle + a - a * f1;
   }

   private void setRotation(ModelRenderer model, float x, float y, float z) {
     model.rotateAngleX = x;
     model.rotateAngleY = y;
     model.rotateAngleZ = z;
   }

   public void setRotationAngles(Entity entity, float limbSwing, float limbSwingAmount,
        float ageInTicks, float netHeadYaw, float headPitch) {
// Reset shared model state before applying this frame's legacy animation.
this.TailTip.setRotationPoint(1.0F, 20.0F, 120.0F);
this.Neck.setRotationPoint(0.0F, 4.5F, -33.0F);
this.tBase.setRotationPoint(0.0F, 4.0F, -34.0F);
this.t2.setRotationPoint(0.0F, 7.0F, -27.0F);
this.t3.setRotationPoint(0.0F, 14.0F, -24.0F);
this.t4.setRotationPoint(0.0F, 19.0F, -17.0F);
this.t5.setRotationPoint(0.0F, 20.0F, -9.0F);
this.t6.setRotationPoint(0.0F, 20.0F, -1.0F);
this.t7.setRotationPoint(2.0F, 20.0F, 6.0F);
this.t8.setRotationPoint(7.0F, 20.0F, 12.0F);
this.t9.setRotationPoint(10.0F, 20.0F, 20.0F);
this.t10.setRotationPoint(10.0F, 20.0F, 28.0F);
this.t12.setRotationPoint(2.0F, 20.0F, 42.0F);
this.t11.setRotationPoint(8.0F, 20.0F, 35.0F);
this.t13.setRotationPoint(-4.0F, 20.0F, 48.0F);
this.t14.setRotationPoint(-8.0F, 20.0F, 56.0F);
this.t15.setRotationPoint(-8.0F, 20.0F, 65.0F);
this.t16.setRotationPoint(-5.0F, 20.0F, 73.0F);
this.t17.setRotationPoint(1.0F, 20.0F, 80.0F);
this.t18.setRotationPoint(7.0F, 20.0F, 87.0F);
this.t19.setRotationPoint(10.0F, 20.0F, 95.0F);
this.t20.setRotationPoint(10.0F, 20.0F, 104.0F);
this.t21.setRotationPoint(7.0F, 20.0F, 113.0F);
this.MouthBottom.setRotationPoint(0.0F, 4.0F, -42.0F);
this.ToungBase.setRotationPoint(0.0F, 6.0F, -40.0F);
this.MiddleTounge.setRotationPoint(0.0F, 6.0F, -40.0F);
this.EyeRight.setRotationPoint(0.0F, 6.0F, -40.0F);
this.EyeLeft.setRotationPoint(0.0F, 6.0F, -40.0F);
this.MouthTop.setRotationPoint(0.0F, 6.0F, -40.0F);
this.Head.setRotationPoint(0.0F, 6.0F, -40.0F);
this.FangRight.setRotationPoint(0.0F, 6.0F, -40.0F);
this.FangLeft.setRotationPoint(0.0F, 6.0F, -40.0F);
this.ForkRight.setRotationPoint(0.0F, 6.0F, -40.0F);
this.ForkLeft.setRotationPoint(0.0F, 6.0F, -40.0F);
setRotation(this.TailTip, 0.0F, -0.6981317F, 0.0F);
setRotation(this.Neck, -0.2617994F, 0.0F, 0.0F);
setRotation(this.tBase, -0.5235988F, 0.0F, 0.0F);
setRotation(this.t2, -1.047198F, 0.0F, 0.0F);
setRotation(this.t3, -0.5235988F, 0.0F, 0.0F);
setRotation(this.t4, -0.0872665F, 0.0F, 0.0F);
setRotation(this.t5, 0.0F, 0.0F, 0.0F);
setRotation(this.t6, 0.0F, 0.3490659F, 0.0F);
setRotation(this.t7, 0.0F, 0.6981317F, 0.0F);
setRotation(this.t8, 0.0F, 0.3490659F, 0.0F);
setRotation(this.t9, 0.0F, 0.0F, 0.0F);
setRotation(this.t10, 0.0F, -0.3490659F, 0.0F);
setRotation(this.t12, 0.0F, -0.6981317F, 0.0F);
setRotation(this.t11, 0.0F, -0.6981317F, 0.0F);
setRotation(this.t13, 0.0F, -0.3490659F, 0.0F);
setRotation(this.t14, 0.0F, 0.0F, 0.0F);
setRotation(this.t15, 0.0F, 0.3490659F, 0.0F);
setRotation(this.t16, 0.0F, 0.6981317F, 0.0F);
setRotation(this.t17, 0.0F, 0.6981317F, 0.0F);
setRotation(this.t18, 0.0F, 0.3490659F, 0.0F);
setRotation(this.t19, 0.0F, 0.0F, 0.0F);
setRotation(this.t20, 0.0F, -0.3490659F, 0.0F);
setRotation(this.t21, 0.0F, -0.6981317F, 0.0F);
setRotation(this.MouthBottom, 0.5235988F, 0.0F, 0.0F);
setRotation(this.ToungBase, 0.2617994F, 0.0F, 0.0F);
setRotation(this.MiddleTounge, 0.1745329F, 0.0F, 0.0F);
setRotation(this.EyeRight, 0.3490659F, 0.0F, 0.0F);
setRotation(this.EyeLeft, 0.3490659F, 0.0F, 0.0F);
setRotation(this.MouthTop, 0.0F, 0.0F, 0.0F);
setRotation(this.Head, 0.0F, 0.0F, 0.0F);
setRotation(this.FangRight, 0.1745329F, 0.0F, 0.0F);
setRotation(this.FangLeft, 0.1745329F, 0.0F, 0.0F);
setRotation(this.ForkRight, 0.0872665F, 0.4363323F, 0.0F);
setRotation(this.ForkLeft, 0.0872665F, -0.4363323F, 0.0F);

     SeaViperEntity.CustomEntity e = (SeaViperEntity.CustomEntity) entity;

     float newangle = 0.0F;
 
 
     
     if (limbSwingAmount < 0.0F) limbSwingAmount = 0.0F;
     
     newangle = MathHelper.cos(ageInTicks * 1.3F * this.wingspeed) * 3.1415927F * 0.1F * limbSwingAmount;
 
     
     this.tBase.rotateAngleY = newangle;
     
     doseg(this.tBase, this.t2, 2.0F, limbSwingAmount, ageInTicks);
     doseg(this.t2, this.t3, 2.0F, limbSwingAmount, ageInTicks);
     doseg(this.t3, this.t4, 3.0F, limbSwingAmount, ageInTicks);
     doseg(this.t4, this.t5, 4.0F, limbSwingAmount, ageInTicks);
     doseg(this.t5, this.t6, 5.0F, limbSwingAmount, ageInTicks);
     doseg(this.t6, this.t7, 6.0F, limbSwingAmount, ageInTicks);
     doseg(this.t7, this.t8, 7.0F, limbSwingAmount, ageInTicks);
     doseg(this.t8, this.t9, 8.0F, limbSwingAmount, ageInTicks);
     doseg(this.t9, this.t10, 9.0F, limbSwingAmount, ageInTicks);
     doseg(this.t10, this.t11, 10.0F, limbSwingAmount, ageInTicks);
     doseg(this.t11, this.t12, 11.0F, limbSwingAmount, ageInTicks);
     doseg(this.t12, this.t13, 12.0F, limbSwingAmount, ageInTicks);
     doseg(this.t13, this.t14, 13.0F, limbSwingAmount, ageInTicks);
     doseg(this.t14, this.t15, 14.0F, limbSwingAmount, ageInTicks);
     doseg(this.t15, this.t16, 15.0F, limbSwingAmount, ageInTicks);
     doseg(this.t16, this.t17, 16.0F, limbSwingAmount, ageInTicks);
     doseg(this.t17, this.t18, 17.0F, limbSwingAmount, ageInTicks);
     doseg(this.t18, this.t19, 18.0F, limbSwingAmount, ageInTicks);
     doseg(this.t19, this.t20, 19.0F, limbSwingAmount, ageInTicks);
     doseg(this.t20, this.t21, 20.0F, limbSwingAmount, ageInTicks);
     doseg(this.t21, this.TailTip, 21.0F, limbSwingAmount, ageInTicks);
     
     if (e.getAttacking() != 0) {
       newangle = MathHelper.cos(ageInTicks * 1.7F * this.wingspeed) * 3.1415927F * 0.17F;
       this.MouthBottom.rotateAngleX = 0.65F + newangle;
       newangle = MathHelper.cos(ageInTicks * 4.7F * this.wingspeed) * 3.1415927F * 0.07F;
       this.ToungBase.rotateAngleX = 0.261F + newangle;
       this.MiddleTounge.rotateAngleX = 0.174F + newangle;
       this.ForkLeft.rotateAngleX = 0.087F + newangle;
       this.ForkRight.rotateAngleX = 0.087F + newangle;
       newangle = MathHelper.cos(ageInTicks * 1.5F * this.wingspeed) * 3.1415927F * 0.05F;
       this.ForkRight.rotateAngleZ = newangle;
     } else {
       newangle = MathHelper.cos(ageInTicks * 0.2F * this.wingspeed) * 3.1415927F * 0.02F;
       this.MouthBottom.rotateAngleX = 0.45F + newangle;
       newangle = MathHelper.cos(ageInTicks * 1.7F * this.wingspeed) * 3.1415927F * 0.03F;
       this.ToungBase.rotateAngleX = 0.261F + newangle;
       this.MiddleTounge.rotateAngleX = 0.174F + newangle;
       this.ForkLeft.rotateAngleX = 0.087F + newangle;
       this.ForkRight.rotateAngleX = 0.087F + newangle;
       newangle = MathHelper.cos(ageInTicks * 0.5F * this.wingspeed) * 3.1415927F * 0.05F;
       this.ForkRight.rotateAngleZ = newangle;
     } 
 
 
     
     newangle = (float)Math.toRadians(netHeadYaw) * 0.5F;
     this.EyeRight.rotateAngleY = newangle;
     this.FangRight.rotateAngleY = newangle;
     
     this.MouthBottom.rotateAngleY = newangle;
     this.Head.rotationPointZ -= (float)Math.cos(this.Head.rotateAngleY) * 2.0F;
     this.Head.rotationPointX -= (float)Math.sin(this.Head.rotateAngleY) * 2.0F;
     
     this.ToungBase.rotateAngleY = newangle;
     this.MiddleTounge.rotateAngleY = newangle;
     this.ForkLeft.rotateAngleY = newangle - 0.436F;
     this.ForkRight.rotateAngleY = newangle + 0.436F;
    }
 }
}