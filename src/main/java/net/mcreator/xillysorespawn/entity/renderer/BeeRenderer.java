package net.mcreator.xillysorespawn.entity.renderer;

import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.MobRenderer;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.mcreator.xillysorespawn.entity.BeeEntity;

@OnlyIn(Dist.CLIENT)
public class BeeRenderer {
    private static final String TEXTURE = "xillys_orespawn:textures/entities/beetexture.png";
    public static class ModelRegisterHandler {
        @SubscribeEvent @OnlyIn(Dist.CLIENT)
        public void registerModels(ModelRegistryEvent event) {
            RenderingRegistry.registerEntityRenderingHandler(BeeEntity.entity, manager ->
                new MobRenderer(manager, new ModelBee(1.0F), 0.5F) {
                    @Override public ResourceLocation getEntityTexture(Entity entity) { return new ResourceLocation(TEXTURE); }
                });
        }
    }

public static class ModelBee extends EntityModel<Entity>
 {
   private float wingspeed = 1.0F;

   private final ModelRenderer Sting;

   private final ModelRenderer Abdomnem1;
   private final ModelRenderer Abdomnem2;
   private final ModelRenderer Abdomnem3;
   private final ModelRenderer Abdomnem4;
   private final ModelRenderer Abdomnem5;
   private final ModelRenderer MainBody;
   private final ModelRenderer Neck;
   private final ModelRenderer Head;
   private final ModelRenderer WingRight;
   private final ModelRenderer WingLeft;
   private final ModelRenderer RA1;
   private final ModelRenderer LA1;
   private final ModelRenderer LA2;
   private final ModelRenderer RA2;
   private final ModelRenderer RA3;
   private final ModelRenderer LA3;
   private final ModelRenderer LeftPom;
   private final ModelRenderer RightPom;
   private final ModelRenderer LeftPincerExtra;
   private final ModelRenderer LeftPincerMain;
   private final ModelRenderer RightPincerMain;
   private final ModelRenderer RightPincerExtra;

   public ModelBee(float f1) {
     this.wingspeed = f1;

     this.textureWidth = 256;
     this.textureHeight = 256;

     this.Sting = new ModelRenderer(this, 68, 0);
     this.Sting.addBox(-1.0F, 0.0F, -1.0F, 2, 10, 2);
     this.Sting.setRotationPoint(0.0F, 16.0F, 1.0F);

     this.Sting.mirror = true;
     setRotation(this.Sting, -0.7853982F, 0.0F, 0.0F);
     this.Abdomnem1 = new ModelRenderer(this, 64, 12);
     this.Abdomnem1.addBox(-2.0F, 0.0F, 0.0F, 4, 8, 4);
     this.Abdomnem1.setRotationPoint(0.0F, 9.0F, 2.0F);

     this.Abdomnem1.mirror = true;
     setRotation(this.Abdomnem1, -0.5235988F, 0.0F, 0.0F);
     this.Abdomnem2 = new ModelRenderer(this, 60, 24);
     this.Abdomnem2.addBox(-3.0F, 0.0F, 0.0F, 6, 6, 6);
     this.Abdomnem2.setRotationPoint(0.0F, 5.0F, 0.0F);

     this.Abdomnem2.mirror = true;
     setRotation(this.Abdomnem2, 0.0F, 0.0F, 0.0F);
     this.Abdomnem3 = new ModelRenderer(this, 56, 36);
     this.Abdomnem3.addBox(-4.0F, 0.0F, 0.0F, 8, 7, 8);
     this.Abdomnem3.setRotationPoint(0.0F, 1.0F, -2.0F);

     this.Abdomnem3.mirror = true;
     setRotation(this.Abdomnem3, 0.2617994F, 0.0F, 0.0F);
     this.Abdomnem4 = new ModelRenderer(this, 53, 51);
     this.Abdomnem4.addBox(-5.0F, 0.0F, 0.0F, 10, 12, 10);
     this.Abdomnem4.setRotationPoint(0.0F, -6.0F, -8.0F);

     this.Abdomnem4.mirror = true;
     setRotation(this.Abdomnem4, 0.5934119F, 0.0F, 0.0F);
     this.Abdomnem5 = new ModelRenderer(this, 48, 73);
     this.Abdomnem5.addBox(-6.0F, 0.0F, 0.0F, 12, 12, 12);
     this.Abdomnem5.setRotationPoint(0.0F, -6.0F, -15.0F);

     this.Abdomnem5.mirror = true;
     setRotation(this.Abdomnem5, 1.099557F, 0.0F, 0.0F);
     this.MainBody = new ModelRenderer(this, 48, 97);
     this.MainBody.addBox(-6.0F, 0.0F, -6.0F, 12, 14, 12);
     this.MainBody.setRotationPoint(0.0F, -12.0F, -24.0F);

     this.MainBody.mirror = true;
     setRotation(this.MainBody, 1.48353F, 0.0F, 0.0F);
     this.Neck = new ModelRenderer(this, 55, 123);
     this.Neck.addBox(-4.0F, -4.0F, -8.0F, 8, 8, 8);
     this.Neck.setRotationPoint(0.0F, -12.0F, -23.0F);

     this.Neck.mirror = true;
     setRotation(this.Neck, 0.0F, 0.0F, 0.0F);
     this.Head = new ModelRenderer(this, 51, 139);
     this.Head.addBox(-5.0F, -5.0F, -10.0F, 10, 10, 10);
     this.Head.setRotationPoint(0.0F, -13.0F, -28.0F);

     this.Head.mirror = true;
     setRotation(this.Head, 0.2617994F, 0.0F, 0.0F);
     this.WingRight = new ModelRenderer(this, 0, 91);
     this.WingRight.addBox(0.0F, 0.0F, 0.0F, 0, 8, 24);
     this.WingRight.setRotationPoint(-4.0F, -14.0F, -15.0F);

     this.WingRight.mirror = true;
     setRotation(this.WingRight, -0.7853982F, -0.5235988F, 2.617994F);
     this.WingLeft = new ModelRenderer(this, 96, 91);
     this.WingLeft.addBox(0.0F, 0.0F, 0.0F, 0, 8, 24);
     this.WingLeft.setRotationPoint(3.0F, -14.0F, -15.0F);

     this.WingLeft.mirror = true;
     setRotation(this.WingLeft, -0.7853982F, 0.5235988F, -2.617994F);
     this.RA1 = new ModelRenderer(this, 47, 152);
     this.RA1.addBox(0.0F, -6.0F, -1.0F, 1, 6, 1);
     this.RA1.setRotationPoint(-3.0F, -17.0F, -31.0F);

     this.RA1.mirror = true;
     setRotation(this.RA1, 0.2617994F, 0.5235988F, 0.0F);
     this.LA1 = new ModelRenderer(this, 91, 152);
     this.LA1.addBox(0.0F, -6.0F, -1.0F, 1, 6, 1);
     this.LA1.setRotationPoint(2.0F, -17.0F, -32.0F);

     this.LA1.mirror = true;
     setRotation(this.LA1, 0.2617994F, -0.5235988F, 0.0F);
     this.LA2 = new ModelRenderer(this, 91, 145);
     this.LA2.addBox(0.0F, -11.0F, 0.0F, 1, 6, 1);
     this.LA2.setRotationPoint(2.0F, -17.0F, -32.0F);

     this.LA2.mirror = true;
     setRotation(this.LA2, 0.4363323F, -0.6108652F, 0.0F);
     this.RA2 = new ModelRenderer(this, 47, 145);
     this.RA2.addBox(0.0F, -11.0F, 0.0F, 1, 6, 1);
     this.RA2.setRotationPoint(-3.0F, -17.0F, -31.0F);

     this.RA2.mirror = true;
     setRotation(this.RA2, 0.4363323F, 0.6108652F, 0.0F);
     this.RA3 = new ModelRenderer(this, 47, 138);
     this.RA3.addBox(0.0F, -16.0F, 2.0F, 1, 6, 1);
     this.RA3.setRotationPoint(-3.0F, -17.0F, -31.0F);

     this.RA3.mirror = true;
     setRotation(this.RA3, 0.6108652F, 0.6981317F, 0.0F);
     this.LA3 = new ModelRenderer(this, 91, 138);
     this.LA3.addBox(0.0F, -16.0F, 2.0F, 1, 6, 1);
     this.LA3.setRotationPoint(2.0F, -17.0F, -32.0F);

     this.LA3.mirror = true;
     setRotation(this.LA3, 0.6108652F, -0.6981317F, 0.0F);
     this.LeftPom = new ModelRenderer(this, 89, 134);
     this.LeftPom.addBox(4.0F, -16.0F, -6.0F, 2, 2, 2);
     this.LeftPom.setRotationPoint(2.0F, -17.0F, -32.0F);

     this.LeftPom.mirror = true;
     setRotation(this.LeftPom, 0.0F, 0.0F, 0.0F);
     this.RightPom = new ModelRenderer(this, 45, 134);
     this.RightPom.addBox(-5.0F, -16.0F, -7.0F, 2, 2, 2);
     this.RightPom.setRotationPoint(-3.0F, -17.0F, -31.0F);

     this.RightPom.mirror = true;
     setRotation(this.RightPom, 0.0F, 0.0F, 0.0F);
     this.LeftPincerExtra = new ModelRenderer(this, 71, 166);
     this.LeftPincerExtra.addBox(-2.0F, 0.0F, -6.0F, 2, 1, 2);
     this.LeftPincerExtra.setRotationPoint(2.0F, -8.0F, -36.0F);

     this.LeftPincerExtra.mirror = true;
     setRotation(this.LeftPincerExtra, 0.1745329F, -0.1745329F, 0.0F);
     this.LeftPincerMain = new ModelRenderer(this, 71, 159);
     this.LeftPincerMain.addBox(0.0F, 0.0F, -6.0F, 2, 1, 6);
     this.LeftPincerMain.setRotationPoint(2.0F, -8.0F, -36.0F);

     this.LeftPincerMain.mirror = true;
     setRotation(this.LeftPincerMain, 0.1745329F, -0.1745329F, 0.0F);
     this.RightPincerMain = new ModelRenderer(this, 55, 159);
     this.RightPincerMain.addBox(0.0F, 0.0F, -6.0F, 2, 1, 6);
     this.RightPincerMain.setRotationPoint(-4.0F, -8.0F, -36.0F);

     this.RightPincerMain.mirror = true;
     setRotation(this.RightPincerMain, 0.1745329F, 0.1745329F, 0.0F);
     this.RightPincerExtra = new ModelRenderer(this, 63, 166);
     this.RightPincerExtra.addBox(2.0F, 0.0F, -6.0F, 2, 1, 2);
     this.RightPincerExtra.setRotationPoint(-4.0F, -8.0F, -36.0F);

     this.RightPincerExtra.mirror = true;
     setRotation(this.RightPincerExtra, 0.1745329F, 0.1745329F, 0.0F);
   }

   public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay,
        float red, float green, float blue, float alpha) {
        Sting.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Abdomnem1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Abdomnem2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Abdomnem3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Abdomnem4.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Abdomnem5.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        MainBody.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Neck.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Head.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        WingRight.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        WingLeft.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        RA1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        LA1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        LA2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        RA2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        RA3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        LA3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        LeftPom.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        RightPom.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        LeftPincerExtra.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        LeftPincerMain.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        RightPincerMain.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        RightPincerExtra.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }

   private void setRotation(ModelRenderer model, float x, float y, float z) {
     model.rotateAngleX = x;
     model.rotateAngleY = y;
     model.rotateAngleZ = z;
   }

   public void setRotationAngles(Entity entity, float limbSwing, float limbSwingAmount,
        float ageInTicks, float netHeadYaw, float headPitch) {
     float wing = MathHelper.cos(ageInTicks * 1.1F * this.wingspeed) * (float)Math.PI * 0.3F;
     this.WingLeft.rotateAngleZ = -1.745F - wing;
     this.WingRight.rotateAngleZ = 1.754F + wing;

     float pincer = MathHelper.cos(ageInTicks * 0.3F * this.wingspeed) * (float)Math.PI * 0.1F;
     this.LeftPincerMain.rotateAngleY = -0.274F + pincer;
     this.LeftPincerExtra.rotateAngleY = -0.274F + pincer;
     this.RightPincerMain.rotateAngleY = 0.274F - pincer;
     this.RightPincerExtra.rotateAngleY = 0.274F - pincer;

     float leftLeg = MathHelper.cos(ageInTicks * 0.21F * this.wingspeed) * (float)Math.PI * 0.06F;
     this.LA1.rotateAngleX = 0.261F + leftLeg;
     this.LA2.rotateAngleX = 0.436F + leftLeg;
     this.LA3.rotateAngleX = 0.611F + leftLeg;
     this.LeftPom.rotateAngleX = leftLeg;
     this.LA1.rotateAngleZ = leftLeg;
     this.LA2.rotateAngleZ = leftLeg;
     this.LA3.rotateAngleZ = leftLeg;
     this.LeftPom.rotateAngleZ = leftLeg;

     float rightLeg = MathHelper.cos(ageInTicks * 0.27F * this.wingspeed) * (float)Math.PI * 0.06F;
     this.RA1.rotateAngleX = 0.261F + rightLeg;
     this.RA2.rotateAngleX = 0.436F + rightLeg;
     this.RA3.rotateAngleX = 0.611F + rightLeg;
     this.RightPom.rotateAngleX = rightLeg;
     rightLeg = MathHelper.cos(ageInTicks * 0.37F * this.wingspeed) * (float)Math.PI * 0.06F;
     this.RA1.rotateAngleZ = rightLeg;
     this.RA2.rotateAngleZ = rightLeg;
     this.RA3.rotateAngleZ = rightLeg;
     this.RightPom.rotateAngleZ = rightLeg;

     boolean attacking = entity instanceof BeeEntity.CustomEntity && ((BeeEntity.CustomEntity) entity).isAttacking();
     float abdomen = MathHelper.cos(ageInTicks * (attacking ? 0.11F : 0.021F) * this.wingspeed)
         * (float)Math.PI * (attacking ? 0.055F : 0.023F);
     this.Abdomnem5.rotateAngleX = 1.099F + abdomen;
     this.Abdomnem4.rotateAngleX = this.Abdomnem5.rotateAngleX + abdomen - 0.35F;
     this.Abdomnem4.rotationPointY = (float)(this.Abdomnem5.rotationPointY + Math.cos(this.Abdomnem5.rotateAngleX) * 10.0D);
     this.Abdomnem4.rotationPointZ = (float)(this.Abdomnem5.rotationPointZ + Math.sin(this.Abdomnem5.rotateAngleX) * 10.0D);
     this.Abdomnem3.rotateAngleX = this.Abdomnem4.rotateAngleX + abdomen - 0.35F;
     this.Abdomnem3.rotationPointY = (float)(this.Abdomnem4.rotationPointY + Math.cos(this.Abdomnem4.rotateAngleX) * 10.0D);
     this.Abdomnem3.rotationPointZ = (float)(this.Abdomnem4.rotationPointZ + Math.sin(this.Abdomnem4.rotateAngleX) * 10.0D);
     this.Abdomnem2.rotateAngleX = this.Abdomnem3.rotateAngleX + abdomen - 0.35F;
     this.Abdomnem2.rotationPointY = (float)(this.Abdomnem3.rotationPointY + Math.cos(this.Abdomnem3.rotateAngleX) * 6.0D);
     this.Abdomnem2.rotationPointZ = (float)(this.Abdomnem3.rotationPointZ + Math.sin(this.Abdomnem3.rotateAngleX) * 6.0D);
     this.Abdomnem1.rotateAngleX = this.Abdomnem2.rotateAngleX + abdomen - 0.35F;
     this.Abdomnem1.rotationPointY = (float)(this.Abdomnem2.rotationPointY + Math.cos(this.Abdomnem2.rotateAngleX) * 5.0D);
     this.Abdomnem1.rotationPointZ = (float)(this.Abdomnem2.rotationPointZ + Math.sin(this.Abdomnem2.rotateAngleX) * 5.0D);
     this.Sting.rotateAngleX = this.Abdomnem1.rotateAngleX + abdomen - 0.35F;
     this.Sting.rotationPointY = (float)(this.Abdomnem1.rotationPointY + Math.cos(this.Abdomnem1.rotateAngleX) * 7.0D);
     this.Sting.rotationPointZ = 1.0F + (float)(this.Abdomnem1.rotationPointZ + Math.sin(this.Abdomnem1.rotateAngleX) * 7.0D);
    }
 }
}
