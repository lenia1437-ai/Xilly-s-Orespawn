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
import net.mcreator.xillysorespawn.entity.TRexEntity;
import net.mcreator.xillysorespawn.entity.OreSpawnLogic;

@OnlyIn(Dist.CLIENT)
public class TRexRenderer {
    private static final String TEXTURE = "xillys_orespawn:textures/entities/trextexture.png";
    public static class ModelRegisterHandler {
        @SubscribeEvent @OnlyIn(Dist.CLIENT)
        public void registerModels(ModelRegistryEvent event) {
            RenderingRegistry.registerEntityRenderingHandler(TRexEntity.entity, manager ->
                new MobRenderer(manager, new ModelTRex(0.2F), 1.2F) {
                    @Override public ResourceLocation getEntityTexture(Entity entity) { return new ResourceLocation(TEXTURE); }
                    @Override protected void preRenderCallback(LivingEntity entity, MatrixStack stack, float partialTick) {
                        float modelScale = 1.2F;
                        stack.scale(modelScale, modelScale, modelScale);
                    }
                });
        }
    }

public static class ModelTRex extends EntityModel<Entity>
 {
   private float wingspeed = 1.0F;

   private final ModelRenderer Shape18;

   private final ModelRenderer Shape19;
   private final ModelRenderer Shape20;
   private final ModelRenderer Shape21;
   private final ModelRenderer Shape1;
   private final ModelRenderer Shape2;
   private final ModelRenderer Shape3;
   private final ModelRenderer Shape4;
   private final ModelRenderer Shape5;
   private final ModelRenderer Shape6;
   private final ModelRenderer jaw;
   private final ModelRenderer leftleg;
   private final ModelRenderer leftleg2;
   private final ModelRenderer leftleg3;
   private final ModelRenderer Shape11;
   private final ModelRenderer rightleg;
   private final ModelRenderer rightleg2;
   private final ModelRenderer rightleg3;
   private final ModelRenderer leftleg4;
   private final ModelRenderer rightleg4;
   private final ModelRenderer Shape17;
   private final ModelRenderer TailExtension;
   private final ModelRenderer Spine1;
   private final ModelRenderer Spine2;
   private final ModelRenderer Spine3;
   private final ModelRenderer Spine4;
   private final ModelRenderer Spine5;

   public ModelTRex(float f1) {
     this.textureWidth = 128;
     this.textureHeight = 128;
     this.wingspeed = f1;

     this.Shape18 = new ModelRenderer(this, 91, 114);
     this.Shape18.addBox(0.0F, 0.0F, 0.0F, 2, 4, 5);
     this.Shape18.setRotationPoint(3.3F, -25.0F, -23.0F);

     this.Shape18.mirror = true;
     setRotation(this.Shape18, 0.5759587F, 0.0F, 0.5585054F);
     this.Shape19 = new ModelRenderer(this, 71, 114);
     this.Shape19.addBox(0.0F, 0.0F, 0.0F, 2, 4, 5);
     this.Shape19.setRotationPoint(-4.0F, -24.0F, -23.0F);

     this.Shape19.mirror = true;
     setRotation(this.Shape19, 0.5759587F, 0.0F, -0.5585054F);
     this.Shape20 = new ModelRenderer(this, 91, 30);
     this.Shape20.addBox(0.0F, 0.0F, 0.0F, 2, 7, 5);
     this.Shape20.setRotationPoint(5.0F, -8.0F, -6.0F);

     this.Shape20.mirror = true;
     setRotation(this.Shape20, 0.3839724F, 0.0F, 0.0F);
     this.Shape21 = new ModelRenderer(this, 93, 46);
     this.Shape21.addBox(-2.0F, 0.0F, 0.0F, 2, 7, 5);
     this.Shape21.setRotationPoint(-4.0F, -8.0F, -6.0F);

     this.Shape21.mirror = true;
     setRotation(this.Shape21, 0.3839724F, 0.0F, 0.0F);
     this.Shape1 = new ModelRenderer(this, 0, 0);
     this.Shape1.addBox(-7.0F, 0.0F, 0.0F, 10, 18, 31);
     this.Shape1.setRotationPoint(2.5F, -19.0F, -8.0F);

     this.Shape1.mirror = true;
     setRotation(this.Shape1, 0.0F, 0.0F, 0.0F);
     this.Shape2 = new ModelRenderer(this, 62, 0);
     this.Shape2.addBox(-5.0F, 0.0F, 0.0F, 10, 11, 11);
     this.Shape2.setRotationPoint(0.5F, -19.0F, 23.0F);

     this.Shape2.mirror = true;
     setRotation(this.Shape2, 0.0F, 0.0F, 0.0F);
     this.Shape3 = new ModelRenderer(this, 10, 54);
     this.Shape3.addBox(-3.0F, 0.0F, 0.0F, 7, 7, 25);
     this.Shape3.setRotationPoint(0.0F, -19.0F, 34.0F);

     this.Shape3.mirror = true;
     setRotation(this.Shape3, 0.0F, 0.0F, 0.0F);
     this.Shape4 = new ModelRenderer(this, 68, 88);
     this.Shape4.addBox(-5.0F, 0.0F, 0.0F, 8, 9, 16);
     this.Shape4.setRotationPoint(1.5F, -25.0F, -16.0F);

     this.Shape4.mirror = true;
     setRotation(this.Shape4, -0.4014257F, 0.0F, 0.0F);
     this.Shape5 = new ModelRenderer(this, 75, 65);
     this.Shape5.addBox(0.0F, 0.0F, 0.0F, 9, 9, 12);
     this.Shape5.setRotationPoint(-4.0F, -25.0F, -27.0F);

     this.Shape5.mirror = true;
     setRotation(this.Shape5, 0.0F, 0.0F, 0.0F);
     this.Shape6 = new ModelRenderer(this, 0, 50);
     this.Shape6.addBox(0.0F, 0.0F, 0.0F, 7, 9, 9);
     this.Shape6.setRotationPoint(-3.0F, -25.0F, -36.0F);

     this.Shape6.mirror = true;
     setRotation(this.Shape6, 0.0F, 0.0F, 0.0F);
     this.jaw = new ModelRenderer(this, 0, 86);
     this.jaw.addBox(-5.0F, 0.0F, -10.0F, 7, 1, 13);
     this.jaw.setRotationPoint(2.0F, -15.0F, -24.0F);

     this.jaw.mirror = true;
     setRotation(this.jaw, 0.5201081F, 0.0F, 0.0F);
     this.leftleg = new ModelRenderer(this, 0, 0);
     this.leftleg.addBox(-1.0F, 0.0F, 0.0F, 3, 16, 10);
     this.leftleg.setRotationPoint(6.0F, -10.0F, 11.0F);

     this.leftleg.mirror = true;
     setRotation(this.leftleg, -0.1745329F, 0.0F, 0.0F);
     this.leftleg2 = new ModelRenderer(this, 0, 106);
     this.leftleg2.addBox(-1.0F, 12.0F, -8.0F, 3, 15, 5);
     this.leftleg2.setRotationPoint(6.0F, -10.0F, 11.0F);

     this.leftleg2.mirror = true;
     setRotation(this.leftleg2, 0.5061455F, 0.0F, 0.0F);
     this.leftleg3 = new ModelRenderer(this, 112, 89);
     this.leftleg3.addBox(-1.0F, 19.0F, 16.0F, 3, 9, 3);
     this.leftleg3.setRotationPoint(6.0F, -10.0F, 11.0F);

     this.leftleg3.mirror = true;
     setRotation(this.leftleg3, -0.4014257F, 0.0F, 0.0F);
     this.Shape11 = new ModelRenderer(this, 0, 72);
     this.Shape11.addBox(0.0F, 0.0F, 0.0F, 2, 10, 2);
     this.Shape11.setRotationPoint(5.0F, -5.0F, -3.0F);

     this.Shape11.mirror = true;
     setRotation(this.Shape11, -0.5235988F, 0.0F, 0.0F);
     this.rightleg = new ModelRenderer(this, 54, 51);
     this.rightleg.addBox(0.0F, 0.0F, 0.0F, 3, 16, 10);
     this.rightleg.setRotationPoint(-7.0F, -10.0F, 11.0F);

     this.rightleg.mirror = true;
     setRotation(this.rightleg, -0.1745329F, 0.0F, 0.0F);
     this.rightleg2 = new ModelRenderer(this, 23, 106);
     this.rightleg2.addBox(0.0F, 12.0F, -8.0F, 3, 15, 5);
     this.rightleg2.setRotationPoint(-7.0F, -10.0F, 11.0F);

     this.rightleg2.mirror = true;
     setRotation(this.rightleg2, 0.5061455F, 0.0F, 0.0F);
     this.rightleg3 = new ModelRenderer(this, 70, 90);
     this.rightleg3.addBox(0.0F, 19.0F, 16.0F, 3, 9, 3);
     this.rightleg3.setRotationPoint(-7.0F, -10.0F, 11.0F);

     this.rightleg3.mirror = true;
     setRotation(this.rightleg3, -0.4014257F, 0.0F, 0.0F);
     this.leftleg4 = new ModelRenderer(this, 42, 113);
     this.leftleg4.addBox(-1.0F, 31.0F, -1.0F, 3, 3, 8);
     this.leftleg4.setRotationPoint(6.0F, -10.0F, 11.0F);

     this.leftleg4.mirror = true;
     setRotation(this.leftleg4, 0.0F, 0.0F, 0.0F);
     this.rightleg4 = new ModelRenderer(this, 44, 93);
     this.rightleg4.addBox(0.0F, 31.0F, -1.0F, 3, 3, 8);
     this.rightleg4.setRotationPoint(-7.0F, -10.0F, 11.0F);

     this.rightleg4.mirror = true;
     setRotation(this.rightleg4, 0.0F, 0.0F, 0.0F);
     this.Shape17 = new ModelRenderer(this, 112, 60);
     this.Shape17.addBox(-2.0F, 0.0F, 0.0F, 2, 10, 2);
     this.Shape17.setRotationPoint(-4.0F, -3.533333F, -3.0F);

     this.Shape17.mirror = true;
     setRotation(this.Shape17, -0.5235988F, 0.0F, 0.0F);
     this.TailExtension = new ModelRenderer(this, 0, 10);
     this.TailExtension.addBox(0.0F, 0.0F, 0.0F, 3, 3, 10);
     this.TailExtension.setRotationPoint(-1.0F, -19.0F, 59.0F);

     this.TailExtension.mirror = true;
     setRotation(this.TailExtension, 0.0F, 0.0F, 0.0F);
     this.Spine1 = new ModelRenderer(this, 73, 0);
     this.Spine1.addBox(0.0F, 0.0F, 0.0F, 2, 2, 3);
     this.Spine1.setRotationPoint(-1.0F, -21.0F, 0.0F);

     this.Spine1.mirror = true;
     setRotation(this.Spine1, 0.0F, 0.0F, 0.0F);
     this.Spine2 = new ModelRenderer(this, 73, 0);
     this.Spine2.addBox(0.0F, 0.0F, 0.0F, 2, 2, 3);
     this.Spine2.setRotationPoint(-0.5F, -21.0F, 6.0F);

     this.Spine2.mirror = true;
     setRotation(this.Spine2, 0.0F, 0.0F, 0.0F);
     this.Spine3 = new ModelRenderer(this, 73, 0);
     this.Spine3.addBox(0.0F, 0.0F, 0.0F, 2, 2, 3);
     this.Spine3.setRotationPoint(-0.5F, -21.0F, 12.0F);

     this.Spine3.mirror = true;
     setRotation(this.Spine3, 0.0F, 0.0F, 0.0F);
     this.Spine4 = new ModelRenderer(this, 73, 0);
     this.Spine4.addBox(0.0F, 0.0F, 0.0F, 2, 2, 3);
     this.Spine4.setRotationPoint(-0.5F, -24.0F, -9.0F);

     this.Spine4.mirror = true;
     setRotation(this.Spine4, -0.4014257F, 0.0F, 0.0F);
     this.Spine5 = new ModelRenderer(this, 73, 0);
     this.Spine5.addBox(0.0F, 0.0F, 0.0F, 2, 2, 3);
     this.Spine5.setRotationPoint(-0.5F, -26.0F, -14.0F);

     this.Spine5.mirror = true;
     setRotation(this.Spine5, -0.4014257F, 0.0F, 0.0F);
   }

   public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay,
        float red, float green, float blue, float alpha) {
        Shape18.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Shape19.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Shape20.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Shape21.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Shape1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Shape2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Shape3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Shape4.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Shape5.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Shape6.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        jaw.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        leftleg.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        leftleg2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        leftleg3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Shape11.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rightleg.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rightleg2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rightleg3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        leftleg4.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rightleg4.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Shape17.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        TailExtension.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Spine1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Spine2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Spine3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Spine4.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Spine5.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }

   private void setRotation(ModelRenderer model, float x, float y, float z) {
     model.rotateAngleX = x;
     model.rotateAngleY = y;
     model.rotateAngleZ = z;
   }

   public void setRotationAngles(Entity entity, float limbSwing, float limbSwingAmount,
        float ageInTicks, float netHeadYaw, float headPitch) {
// Reset shared model state before applying this frame's legacy animation.
this.Shape18.setRotationPoint(3.3F, -25.0F, -23.0F);
this.Shape19.setRotationPoint(-4.0F, -24.0F, -23.0F);
this.Shape20.setRotationPoint(5.0F, -8.0F, -6.0F);
this.Shape21.setRotationPoint(-4.0F, -8.0F, -6.0F);
this.Shape1.setRotationPoint(2.5F, -19.0F, -8.0F);
this.Shape2.setRotationPoint(0.5F, -19.0F, 23.0F);
this.Shape3.setRotationPoint(0.0F, -19.0F, 34.0F);
this.Shape4.setRotationPoint(1.5F, -25.0F, -16.0F);
this.Shape5.setRotationPoint(-4.0F, -25.0F, -27.0F);
this.Shape6.setRotationPoint(-3.0F, -25.0F, -36.0F);
this.jaw.setRotationPoint(2.0F, -15.0F, -24.0F);
this.leftleg.setRotationPoint(6.0F, -10.0F, 11.0F);
this.leftleg2.setRotationPoint(6.0F, -10.0F, 11.0F);
this.leftleg3.setRotationPoint(6.0F, -10.0F, 11.0F);
this.Shape11.setRotationPoint(5.0F, -5.0F, -3.0F);
this.rightleg.setRotationPoint(-7.0F, -10.0F, 11.0F);
this.rightleg2.setRotationPoint(-7.0F, -10.0F, 11.0F);
this.rightleg3.setRotationPoint(-7.0F, -10.0F, 11.0F);
this.leftleg4.setRotationPoint(6.0F, -10.0F, 11.0F);
this.rightleg4.setRotationPoint(-7.0F, -10.0F, 11.0F);
this.Shape17.setRotationPoint(-4.0F, -3.533333F, -3.0F);
this.TailExtension.setRotationPoint(-1.0F, -19.0F, 59.0F);
this.Spine1.setRotationPoint(-1.0F, -21.0F, 0.0F);
this.Spine2.setRotationPoint(-0.5F, -21.0F, 6.0F);
this.Spine3.setRotationPoint(-0.5F, -21.0F, 12.0F);
this.Spine4.setRotationPoint(-0.5F, -24.0F, -9.0F);
this.Spine5.setRotationPoint(-0.5F, -26.0F, -14.0F);
setRotation(this.Shape18, 0.5759587F, 0.0F, 0.5585054F);
setRotation(this.Shape19, 0.5759587F, 0.0F, -0.5585054F);
setRotation(this.Shape20, 0.3839724F, 0.0F, 0.0F);
setRotation(this.Shape21, 0.3839724F, 0.0F, 0.0F);
setRotation(this.Shape1, 0.0F, 0.0F, 0.0F);
setRotation(this.Shape2, 0.0F, 0.0F, 0.0F);
setRotation(this.Shape3, 0.0F, 0.0F, 0.0F);
setRotation(this.Shape4, -0.4014257F, 0.0F, 0.0F);
setRotation(this.Shape5, 0.0F, 0.0F, 0.0F);
setRotation(this.Shape6, 0.0F, 0.0F, 0.0F);
setRotation(this.jaw, 0.5201081F, 0.0F, 0.0F);
setRotation(this.leftleg, -0.1745329F, 0.0F, 0.0F);
setRotation(this.leftleg2, 0.5061455F, 0.0F, 0.0F);
setRotation(this.leftleg3, -0.4014257F, 0.0F, 0.0F);
setRotation(this.Shape11, -0.5235988F, 0.0F, 0.0F);
setRotation(this.rightleg, -0.1745329F, 0.0F, 0.0F);
setRotation(this.rightleg2, 0.5061455F, 0.0F, 0.0F);
setRotation(this.rightleg3, -0.4014257F, 0.0F, 0.0F);
setRotation(this.leftleg4, 0.0F, 0.0F, 0.0F);
setRotation(this.rightleg4, 0.0F, 0.0F, 0.0F);
setRotation(this.Shape17, -0.5235988F, 0.0F, 0.0F);
setRotation(this.TailExtension, 0.0F, 0.0F, 0.0F);
setRotation(this.Spine1, 0.0F, 0.0F, 0.0F);
setRotation(this.Spine2, 0.0F, 0.0F, 0.0F);
setRotation(this.Spine3, 0.0F, 0.0F, 0.0F);
setRotation(this.Spine4, -0.4014257F, 0.0F, 0.0F);
setRotation(this.Spine5, -0.4014257F, 0.0F, 0.0F);

     TRexEntity.CustomEntity e = (TRexEntity.CustomEntity) entity;

     float newangle = 0.0F;
     
     if (limbSwingAmount > 0.1D) {
       newangle = MathHelper.cos(ageInTicks * 1.3F * this.wingspeed) * 3.1415927F * 0.25F * limbSwingAmount;
     } else {
       newangle = 0.0F;
     } 
     
     this.rightleg.rotateAngleX = -0.174F + newangle;
     this.rightleg2.rotateAngleX = 0.506F + newangle;
     this.rightleg3.rotateAngleX = -0.401F + newangle;
     this.rightleg4.rotateAngleX = newangle;
     
     this.leftleg.rotateAngleX = -0.174F - newangle;
     this.leftleg2.rotateAngleX = 0.506F - newangle;
     this.leftleg3.rotateAngleX = -0.401F - newangle;
     this.leftleg4.rotateAngleX = -newangle;
     
     if (e.getAttacking() != 0) {
       this.jaw.rotateAngleX = 0.52F + MathHelper.cos(ageInTicks * 0.45F) * 3.1415927F * 0.18F;
     } else {
       this.jaw.rotateAngleX = 0.1F;
     } 
     
     this.Shape17.rotateAngleX = -0.523F + MathHelper.cos(ageInTicks * 0.1F) * 3.1415927F * 0.05F;
     this.Shape11.rotateAngleX = -0.523F + MathHelper.cos(ageInTicks * 0.1F) * 3.1415927F * 0.05F;
    }
 }
}