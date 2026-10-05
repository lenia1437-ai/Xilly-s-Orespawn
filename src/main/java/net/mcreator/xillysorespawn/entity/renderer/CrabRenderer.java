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
import net.mcreator.xillysorespawn.entity.CrabEntity;

@OnlyIn(Dist.CLIENT)
public class CrabRenderer {
    private static final String TEXTURE = "xillys_orespawn:textures/entities/robotcrabtexture.png";
    public static class ModelRegisterHandler {
        @SubscribeEvent @OnlyIn(Dist.CLIENT)
        public void registerModels(ModelRegistryEvent event) {
            RenderingRegistry.registerEntityRenderingHandler(CrabEntity.entity, manager ->
                new MobRenderer(manager, new ModelCrab(1.0F), 0.5F) {
                    @Override public ResourceLocation getEntityTexture(Entity entity) { return new ResourceLocation(TEXTURE); }
                    @Override protected void preRenderCallback(LivingEntity entity, MatrixStack stack, float partialTick) {
                        float scale = ((CrabEntity.CustomEntity) entity).getCrabScale();
                        stack.scale(scale, scale, scale);
                    }
                });
        }
    }

public static class ModelCrab extends EntityModel<Entity>
 {
   private final ModelRenderer body1;
   private final ModelRenderer body2;
   private final ModelRenderer leg1;
   private final ModelRenderer body3;
   private final ModelRenderer body4;
   private final ModelRenderer leg2;
   private final ModelRenderer leg3;
   private final ModelRenderer body5;
   private final ModelRenderer body6;
   private final ModelRenderer Leye1;
   private final ModelRenderer Reye1;
   private final ModelRenderer Leye2;
   private final ModelRenderer Reye2;
   private final ModelRenderer Lclaw1;
   private final ModelRenderer Lclaw2;
   private final ModelRenderer Lclaw3;
   private final ModelRenderer Lclaw4;
   private final ModelRenderer Lclaw5;
   private final ModelRenderer Rclaw1;
   private final ModelRenderer Rclaw2;
   private final ModelRenderer Rclaw3;
   private final ModelRenderer Rclaw4;
   private final ModelRenderer Rclaw5;
   private final ModelRenderer Rmouth;
   private final ModelRenderer Lmouth;
   private float legSwing;

   public ModelCrab(float f) {
     this.textureWidth = 256;
     this.textureHeight = 512;

     this.body1 = new ModelRenderer(this, 0, 450);
     this.body1.addBox(-38.0F, -5.0F, -8.0F, 76, 10, 48);
     this.body1.setRotationPoint(0.0F, 0.0F, 0.0F);

     this.body1.mirror = true;
     setRotation(this.body1, 0.0F, 0.0F, 0.0F);
     this.body2 = new ModelRenderer(this, 0, 406);
     this.body2.addBox(-32.0F, -10.0F, -10.0F, 64, 5, 34);
     this.body2.setRotationPoint(0.0F, 0.0F, 0.0F);

     this.body2.mirror = true;
     setRotation(this.body2, 0.0F, 0.0F, 0.0F);
     this.leg1 = new ModelRenderer(this, 128, 0);
     this.leg1.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4);
     this.leg1.setRotationPoint(36.0F, 3.0F, 0.0F);

     this.leg1.mirror = true;
     setRotation(this.leg1, -1.343904F, -1.500983F, 0.0F);
     this.body3 = new ModelRenderer(this, 0, 357);
     this.body3.addBox(0.0F, 0.0F, 0.0F, 8, 4, 40);
     this.body3.setRotationPoint(38.0F, -5.0F, -6.0F);

     this.body3.mirror = true;
     setRotation(this.body3, 0.0F, 0.0F, 0.0F);
     this.body4 = new ModelRenderer(this, 100, 357);
     this.body4.addBox(0.0F, 0.0F, 0.0F, 8, 4, 40);
     this.body4.setRotationPoint(-46.0F, -5.0F, -6.0F);

     this.body4.mirror = true;
     setRotation(this.body4, 0.0F, 0.0F, 0.0F);
     this.leg2 = new ModelRenderer(this, 128, 20);
     this.leg2.addBox(-1.0F, 10.0F, -6.0F, 3, 16, 3);
     this.leg2.setRotationPoint(36.0F, 3.0F, 0.0F);

     this.leg2.mirror = true;
     setRotation(this.leg2, -0.9599311F, -1.500983F, 0.0F);
     this.leg3 = new ModelRenderer(this, 128, 43);
     this.leg3.addBox(0.0F, 21.0F, -15.0F, 2, 16, 2);
     this.leg3.setRotationPoint(36.0F, 3.0F, 0.0F);

     this.leg3.mirror = true;
     setRotation(this.leg3, -0.5759587F, -1.500983F, 0.0F);
     this.body5 = new ModelRenderer(this, 0, 339);
     this.body5.addBox(-25.0F, 0.0F, 0.0F, 50, 4, 10);
     this.body5.setRotationPoint(0.0F, -4.0F, 40.0F);

     this.body5.mirror = true;
     setRotation(this.body5, 0.0F, 0.0F, 0.0F);
     this.body6 = new ModelRenderer(this, 124, 342);
     this.body6.addBox(-14.0F, 0.0F, 0.0F, 28, 3, 4);
     this.body6.setRotationPoint(0.0F, -10.0F, -14.0F);

     this.body6.mirror = true;
     setRotation(this.body6, 0.0F, 0.0F, 0.0F);
     this.Leye1 = new ModelRenderer(this, 62, 0);
     this.Leye1.addBox(-0.5F, -12.0F, -0.5F, 1, 12, 1);
     this.Leye1.setRotationPoint(9.0F, -9.0F, -11.0F);

     this.Leye1.mirror = true;
     setRotation(this.Leye1, 0.0F, 0.0F, 0.4886922F);
     this.Reye1 = new ModelRenderer(this, 40, 0);
     this.Reye1.addBox(-0.5F, -12.0F, -0.5F, 1, 12, 1);
     this.Reye1.setRotationPoint(-9.0F, -9.0F, -11.0F);

     this.Reye1.mirror = true;
     setRotation(this.Reye1, 0.0F, 0.0F, -0.4886922F);
     this.Leye2 = new ModelRenderer(this, 50, 0);
     this.Leye2.addBox(-1.0F, -14.0F, -1.0F, 2, 2, 2);
     this.Leye2.setRotationPoint(9.0F, -9.0F, -11.0F);

     this.Leye2.mirror = true;
     setRotation(this.Leye2, 0.0F, 0.0F, 0.4886922F);
     this.Reye2 = new ModelRenderer(this, 26, 0);
     this.Reye2.addBox(-1.0F, -14.0F, -1.0F, 2, 2, 2);
     this.Reye2.setRotationPoint(-9.0F, -9.0F, -11.0F);

     this.Reye2.mirror = true;
     setRotation(this.Reye2, 0.0F, 0.0F, -0.4886922F);
     this.Lclaw1 = new ModelRenderer(this, 0, 80);
     this.Lclaw1.addBox(-4.0F, 0.0F, -14.0F, 8, 4, 18);
     this.Lclaw1.setRotationPoint(31.0F, -2.0F, -8.0F);

     this.Lclaw1.mirror = true;
     setRotation(this.Lclaw1, 0.0F, -0.4886922F, 0.0F);
     this.Lclaw2 = new ModelRenderer(this, 0, 105);
     this.Lclaw2.addBox(-7.0F, -3.0F, -12.0F, 17, 6, 16);
     this.Lclaw2.setRotationPoint(37.0F, 0.0F, -20.0F);

     this.Lclaw2.mirror = true;
     setRotation(this.Lclaw2, 0.0F, -0.1745329F, 0.0F);
     this.Lclaw3 = new ModelRenderer(this, 0, 131);
     this.Lclaw3.addBox(0.0F, -5.0F, -25.0F, 17, 10, 30);
     this.Lclaw3.setRotationPoint(37.0F, 0.0F, -31.0F);

     this.Lclaw3.mirror = true;
     setRotation(this.Lclaw3, 0.0F, -0.4537856F, 0.0F);
     this.Lclaw4 = new ModelRenderer(this, 0, 175);
     this.Lclaw4.addBox(2.0F, -3.0F, -32.0F, 11, 5, 12);
     this.Lclaw4.setRotationPoint(37.0F, 0.0F, -31.0F);

     this.Lclaw4.mirror = true;
     setRotation(this.Lclaw4, 0.0F, -0.3490659F, 0.0F);
     this.Lclaw5 = new ModelRenderer(this, 0, 197);
     this.Lclaw5.addBox(-4.0F, -3.0F, -27.0F, 7, 5, 32);
     this.Lclaw5.setRotationPoint(36.0F, 0.0F, -31.0F);

     this.Lclaw5.mirror = true;
     setRotation(this.Lclaw5, 0.0F, 0.3839724F, 0.0F);
     this.Rclaw1 = new ModelRenderer(this, 102, 78);
     this.Rclaw1.addBox(-4.0F, 0.0F, -14.0F, 8, 4, 18);
     this.Rclaw1.setRotationPoint(-31.0F, -2.0F, -8.0F);

     this.Rclaw1.mirror = true;
     setRotation(this.Rclaw1, 0.0F, 0.4886922F, 0.0F);
     this.Rclaw2 = new ModelRenderer(this, 103, 106);
     this.Rclaw2.addBox(-10.0F, -3.0F, -12.0F, 17, 6, 16);
     this.Rclaw2.setRotationPoint(-37.0F, 0.0F, -20.0F);

     this.Rclaw2.mirror = true;
     setRotation(this.Rclaw2, 0.0F, 0.1745329F, 0.0F);
     this.Rclaw3 = new ModelRenderer(this, 100, 131);
     this.Rclaw3.addBox(-17.0F, -5.0F, -25.0F, 17, 10, 30);
     this.Rclaw3.setRotationPoint(-37.0F, 0.0F, -31.0F);

     this.Rclaw3.mirror = true;
     setRotation(this.Rclaw3, 0.0F, 0.4537856F, 0.0F);
     this.Rclaw4 = new ModelRenderer(this, 101, 175);
     this.Rclaw4.addBox(-13.0F, -3.0F, -32.0F, 11, 5, 12);
     this.Rclaw4.setRotationPoint(-37.0F, 0.0F, -31.0F);

     this.Rclaw4.mirror = true;
     setRotation(this.Rclaw4, 0.0F, 0.3490659F, 0.0F);
     this.Rclaw5 = new ModelRenderer(this, 100, 197);
     this.Rclaw5.addBox(-4.0F, -3.0F, -27.0F, 7, 5, 32);
     this.Rclaw5.setRotationPoint(-36.0F, 0.0F, -31.0F);

     this.Rclaw5.mirror = true;
     setRotation(this.Rclaw5, 0.0F, -0.3839724F, 0.0F);
     this.Rmouth = new ModelRenderer(this, 0, 28);
     this.Rmouth.addBox(0.0F, 0.0F, -0.5F, 6, 3, 1);
     this.Rmouth.setRotationPoint(-7.0F, 0.0F, -7.5F);

     this.Rmouth.mirror = true;
     setRotation(this.Rmouth, 0.0F, 0.3665191F, 0.0F);
     this.Lmouth = new ModelRenderer(this, 0, 19);
     this.Lmouth.addBox(-6.0F, 0.0F, -0.5F, 6, 3, 1);
     this.Lmouth.setRotationPoint(7.0F, 0.0F, -7.5F);

     this.Lmouth.mirror = true;
     setRotation(this.Lmouth, 0.0F, -0.3665191F, 0.0F);
   }

   public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay,
        float red, float green, float blue, float alpha) {
        body1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        body2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        body3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        body4.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        body5.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        body6.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Leye1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Reye1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Leye2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Reye2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Lclaw1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Lclaw2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Lclaw3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Lclaw4.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Lclaw5.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Rclaw1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Rclaw2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Rclaw3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Rclaw4.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Rclaw5.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Rmouth.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Lmouth.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        for (int i = 0; i < 4; i++) {
            float step = ((i & 1) == 0 ? legSwing : -legSwing);
            renderLeg(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha,
                36.0F, i * 10.0F, -(float)Math.PI / 2.0F + step);
            renderLeg(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha,
                -36.0F, i * 10.0F, (float)Math.PI / 2.0F - step);
        }
    }

   private void renderLeg(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay,
        float red, float green, float blue, float alpha, float x, float z, float yaw) {
     ModelRenderer[] segments = {this.leg1, this.leg2, this.leg3};
     for (ModelRenderer segment : segments) {
       segment.setRotationPoint(x, 3.0F, z);
       segment.rotateAngleY = yaw;
       segment.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
     }
   }

   private void setRotation(ModelRenderer model, float x, float y, float z) {
     model.rotateAngleX = x;
     model.rotateAngleY = y;
     model.rotateAngleZ = z;
   }

   public void setRotationAngles(Entity entity, float limbSwing, float limbSwingAmount,
        float ageInTicks, float netHeadYaw, float headPitch) {
CrabEntity.CustomEntity e = (CrabEntity.CustomEntity) entity;
     this.legSwing = MathHelper.cos(ageInTicks * 1.7F) * (float)Math.PI * 0.15F * limbSwingAmount;

     if (e.getAttacking() == 0) {
       this.Leye2.rotateAngleX = MathHelper.cos(ageInTicks * 0.35F) * 3.1415927F * 0.05F;
       this.Leye2.rotateAngleZ = 0.54F + MathHelper.cos(ageInTicks * 0.25F) * 3.1415927F * 0.05F;
       this.Reye2.rotateAngleX = MathHelper.cos(ageInTicks * 0.3F) * 3.1415927F * 0.05F;
       this.Reye2.rotateAngleZ = -0.54F + MathHelper.cos(ageInTicks * 0.45F) * 3.1415927F * 0.05F;
       this.Lmouth.rotateAngleY = -0.72F + MathHelper.cos(ageInTicks * 0.25F) * 3.1415927F * 0.05F;
       this.Rmouth.rotateAngleY = 0.72F - MathHelper.cos(ageInTicks * 0.25F) * 3.1415927F * 0.05F;
       float newangle = MathHelper.cos(ageInTicks * 0.15F) * 3.1415927F * 0.03F;
       this.Lclaw3.rotateAngleY = -0.453F + newangle;
       this.Lclaw4.rotateAngleY = -0.349F + newangle;
       this.Lclaw5.rotateAngleY = 0.384F - newangle;
       newangle = MathHelper.cos(ageInTicks * 0.13F) * 3.1415927F * 0.02F;
       this.Rclaw3.rotateAngleY = 0.453F + newangle;
       this.Rclaw4.rotateAngleY = 0.349F + newangle;
       this.Rclaw5.rotateAngleY = -0.384F - newangle;
     } else {
       this.Leye2.rotateAngleX = MathHelper.cos(ageInTicks * 0.45F) * 3.1415927F * 0.1F;
       this.Leye2.rotateAngleZ = 0.54F + MathHelper.cos(ageInTicks * 0.35F) * 3.1415927F * 0.1F;
       this.Reye2.rotateAngleX = MathHelper.cos(ageInTicks * 0.4F) * 3.1415927F * 0.1F;
       this.Reye2.rotateAngleZ = -0.54F + MathHelper.cos(ageInTicks * 0.55F) * 3.1415927F * 0.1F;
       this.Lmouth.rotateAngleY = -0.72F + MathHelper.cos(ageInTicks * 0.45F) * 3.1415927F * 0.15F;
       this.Rmouth.rotateAngleY = 0.72F - MathHelper.cos(ageInTicks * 0.45F) * 3.1415927F * 0.15F;
       float newangle = MathHelper.cos(ageInTicks * 0.35F) * 3.1415927F * 0.13F;
       this.Lclaw3.rotateAngleY = -0.453F + newangle;
       this.Lclaw4.rotateAngleY = -0.349F + newangle;
       this.Lclaw5.rotateAngleY = 0.384F - newangle;
       newangle = MathHelper.cos(ageInTicks * 0.43F) * 3.1415927F * 0.12F;
       this.Rclaw3.rotateAngleY = 0.453F + newangle;
       this.Rclaw4.rotateAngleY = 0.349F + newangle;
       this.Rclaw5.rotateAngleY = -0.384F - newangle;
     }
    }
 }
}

