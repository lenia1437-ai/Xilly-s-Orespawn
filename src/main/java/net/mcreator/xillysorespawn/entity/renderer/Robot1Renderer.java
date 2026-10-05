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
import net.mcreator.xillysorespawn.entity.Robot1Entity;
import net.mcreator.xillysorespawn.entity.OreSpawnLogic;

@OnlyIn(Dist.CLIENT)
public class Robot1Renderer {
    private static final String TEXTURE = "xillys_orespawn:textures/entities/robot1.png";
    public static class ModelRegisterHandler {
        @SubscribeEvent @OnlyIn(Dist.CLIENT)
        public void registerModels(ModelRegistryEvent event) {
            RenderingRegistry.registerEntityRenderingHandler(Robot1Entity.entity, manager ->
                new MobRenderer(manager, new ModelRobot1(2.0F), 0.3F) {
                    @Override public ResourceLocation getEntityTexture(Entity entity) { return new ResourceLocation(TEXTURE); }
                    @Override protected void preRenderCallback(LivingEntity entity, MatrixStack stack, float partialTick) {
                        float modelScale = 1.0F;
                        stack.scale(modelScale, modelScale, modelScale);
                    }
                });
        }
    }

public static class ModelRobot1 extends EntityModel<Entity>
 {
   private float wingspeed = 1.0F;

   private final ModelRenderer Shape1;

   private final ModelRenderer Shape2;

   private final ModelRenderer Shape2a;
   private final ModelRenderer Shape3;
   private final ModelRenderer Shape4;
   private final ModelRenderer Shape5;
   private final ModelRenderer Shape6;
   private final ModelRenderer Shape7;
   private final ModelRenderer Shape8;
   private final ModelRenderer Shape9;
   private final ModelRenderer Shape10;
   private final ModelRenderer Shape11;
   private final ModelRenderer Shape12;
   private final ModelRenderer Shape13;
   private final ModelRenderer Shape14;
   private final ModelRenderer Shape15;
   private final ModelRenderer Shape15a;
   private final ModelRenderer Shape16;
   private final ModelRenderer Shape17;
   private final ModelRenderer Shape18;
   private final ModelRenderer rfoot;
   private final ModelRenderer lfoot;
   private final ModelRenderer key2;
   private final ModelRenderer key1;
   private final ModelRenderer key3;
   private final ModelRenderer key4;
   private final ModelRenderer key5;

   public ModelRobot1(float f1) {
     this.wingspeed = f1;

     this.textureWidth = 64;
     this.textureHeight = 32;

     this.Shape1 = new ModelRenderer(this, 0, 0);
     this.Shape1.addBox(0.0F, 0.0F, 0.0F, 3, 9, 3);
     this.Shape1.setRotationPoint(-1.0F, 13.0F, -1.0F);

     this.Shape1.mirror = true;
     setRotation(this.Shape1, 0.0F, 0.0F, 0.0F);
     this.Shape2 = new ModelRenderer(this, 0, 0);
     this.Shape2.addBox(0.0F, 0.0F, 0.0F, 1, 9, 5);
     this.Shape2.setRotationPoint(0.0F, 13.0F, -2.0F);

     this.Shape2.mirror = true;
     setRotation(this.Shape2, 0.0F, 0.0F, 0.0F);
     this.Shape2a = new ModelRenderer(this, 0, 0);
     this.Shape2a.addBox(0.0F, 0.0F, 0.0F, 5, 9, 1);
     this.Shape2a.setRotationPoint(-2.0F, 13.0F, 0.0F);

     this.Shape2a.mirror = true;
     setRotation(this.Shape2a, 0.0F, 0.0F, 0.0F);
     this.Shape3 = new ModelRenderer(this, 0, 0);
     this.Shape3.addBox(0.0F, 0.0F, 0.0F, 7, 7, 3);
     this.Shape3.setRotationPoint(-3.0F, 14.0F, -1.0F);

     this.Shape3.mirror = true;
     setRotation(this.Shape3, 0.0F, 0.0F, 0.0F);
     this.Shape4 = new ModelRenderer(this, 0, 0);
     this.Shape4.addBox(0.0F, 0.0F, 0.0F, 3, 7, 7);
     this.Shape4.setRotationPoint(-1.0F, 14.0F, -3.0F);

     this.Shape4.mirror = true;
     setRotation(this.Shape4, 0.0F, 0.0F, 0.0F);
     this.Shape5 = new ModelRenderer(this, 0, 0);
     this.Shape5.addBox(0.0F, 0.0F, 0.0F, 5, 7, 5);
     this.Shape5.setRotationPoint(-2.0F, 14.0F, -2.0F);

     this.Shape5.mirror = true;
     setRotation(this.Shape5, 0.0F, 0.0F, 0.0F);
     this.Shape6 = new ModelRenderer(this, 0, 0);
     this.Shape6.addBox(0.0F, 0.0F, 0.0F, 5, 5, 7);
     this.Shape6.setRotationPoint(-2.0F, 15.0F, -3.0F);

     this.Shape6.mirror = true;
     setRotation(this.Shape6, 0.0F, 0.0F, 0.0F);
     this.Shape7 = new ModelRenderer(this, 0, 0);
     this.Shape7.addBox(0.0F, 0.0F, 0.0F, 1, 5, 1);
     this.Shape7.setRotationPoint(0.0F, 15.0F, 4.0F);

     this.Shape7.mirror = true;
     setRotation(this.Shape7, 0.0F, 0.0F, 0.0F);
     this.Shape8 = new ModelRenderer(this, 0, 0);
     this.Shape8.addBox(0.0F, 0.0F, 0.0F, 7, 5, 5);
     this.Shape8.setRotationPoint(-3.0F, 15.0F, -2.0F);

     this.Shape8.mirror = true;
     setRotation(this.Shape8, 0.0F, 0.0F, 0.0F);
     this.Shape9 = new ModelRenderer(this, 0, 0);
     this.Shape9.addBox(0.0F, 0.0F, 0.0F, 9, 5, 1);
     this.Shape9.setRotationPoint(-4.0F, 15.0F, 0.0F);

     this.Shape9.mirror = true;
     setRotation(this.Shape9, 0.0F, 0.0F, 0.0F);
     this.Shape10 = new ModelRenderer(this, 0, 0);
     this.Shape10.addBox(0.0F, 0.0F, 1.0F, 3, 3, 8);
     this.Shape10.setRotationPoint(-1.0F, 16.0F, -4.0F);

     this.Shape10.mirror = true;
     setRotation(this.Shape10, 0.0F, 0.0F, 0.0F);
     this.Shape11 = new ModelRenderer(this, 0, 0);
     this.Shape11.addBox(0.0F, 0.0F, 0.0F, 9, 3, 3);
     this.Shape11.setRotationPoint(-4.0F, 16.0F, -1.0F);

     this.Shape11.mirror = true;
     setRotation(this.Shape11, 0.0F, 0.0F, 0.0F);
     this.Shape12 = new ModelRenderer(this, 0, 0);
     this.Shape12.addBox(0.0F, 0.0F, 0.0F, 7, 3, 7);
     this.Shape12.setRotationPoint(-3.0F, 16.0F, -3.0F);

     this.Shape12.mirror = true;
     setRotation(this.Shape12, 0.0F, 0.0F, 0.0F);
     this.Shape13 = new ModelRenderer(this, 0, 0);
     this.Shape13.addBox(0.0F, 0.0F, 0.0F, 9, 1, 5);
     this.Shape13.setRotationPoint(-4.0F, 17.0F, -2.0F);

     this.Shape13.mirror = true;
     setRotation(this.Shape13, 0.0F, 0.0F, 0.0F);
     this.Shape14 = new ModelRenderer(this, 0, 0);
     this.Shape14.addBox(0.0F, 0.0F, 0.0F, 5, 1, 1);
     this.Shape14.setRotationPoint(-2.0F, 17.0F, 4.0F);

     this.Shape14.mirror = true;
     setRotation(this.Shape14, 0.0F, 0.0F, 0.0F);
     this.Shape15 = new ModelRenderer(this, 32, 0);
     this.Shape15.addBox(0.0F, 0.0F, 0.0F, 2, 3, 1);
     this.Shape15.setRotationPoint(-2.0F, 15.0F, -4.0F);

     this.Shape15.mirror = true;
     setRotation(this.Shape15, 0.0F, 0.0F, 0.0F);
     this.Shape15a = new ModelRenderer(this, 32, 0);
     this.Shape15a.addBox(0.0F, 0.0F, 0.0F, 2, 3, 1);
     this.Shape15a.setRotationPoint(1.0F, 15.0F, -4.0F);

     this.Shape15a.mirror = true;
     setRotation(this.Shape15a, 0.0F, 0.0F, 0.0F);
     this.Shape16 = new ModelRenderer(this, 45, 0);
     this.Shape16.addBox(0.0F, 0.0F, 0.0F, 3, 1, 3);
     this.Shape16.setRotationPoint(-1.0F, 12.0F, -1.0F);

     this.Shape16.mirror = true;
     setRotation(this.Shape16, 0.0F, 0.0F, 0.0F);
     this.Shape17 = new ModelRenderer(this, 33, 7);
     this.Shape17.addBox(0.0F, 0.0F, 0.0F, 1, 2, 1);
     this.Shape17.setRotationPoint(0.0F, 10.0F, 0.0F);

     this.Shape17.mirror = true;
     setRotation(this.Shape17, 0.0F, 0.0F, 0.0F);
     this.Shape18 = new ModelRenderer(this, 33, 7);
     this.Shape18.addBox(0.0F, 0.0F, 0.0F, 1, 2, 1);
     this.Shape18.setRotationPoint(1.7F, 8.733334F, 0.0F);

     this.Shape18.mirror = true;
     setRotation(this.Shape18, 0.0F, 0.0F, 0.9667472F);
     this.rfoot = new ModelRenderer(this, 46, 8);
     this.rfoot.addBox(0.0F, 3.0F, -2.0F, 2, 2, 4);
     this.rfoot.setRotationPoint(-3.0F, 19.0F, 0.0F);

     this.rfoot.mirror = true;
     setRotation(this.rfoot, 0.0F, 0.0F, 0.0F);
     this.lfoot = new ModelRenderer(this, 46, 8);
     this.lfoot.addBox(0.0F, 3.0F, -2.0F, 2, 2, 4);
     this.lfoot.setRotationPoint(2.0F, 19.0F, 0.0F);

     this.lfoot.mirror = true;
     setRotation(this.lfoot, 0.0F, 0.0F, 0.0F);
     this.key2 = new ModelRenderer(this, 46, 8);
     this.key2.addBox(-0.5F, -1.5F, 1.0F, 1, 3, 1);
     this.key2.setRotationPoint(0.5F, 17.5F, 5.0F);

     this.key2.mirror = true;
     setRotation(this.key2, 0.0F, 0.0F, 0.0F);
     this.key1 = new ModelRenderer(this, 46, 8);
     this.key1.addBox(-0.5F, -0.5F, 0.0F, 1, 1, 3);
     this.key1.setRotationPoint(0.5F, 17.5F, 5.0F);

     this.key1.mirror = true;
     setRotation(this.key1, 0.0F, 0.0F, 0.0F);
     this.key3 = new ModelRenderer(this, 46, 8);
     this.key3.addBox(-0.5F, -2.5F, 1.0F, 1, 1, 2);
     this.key3.setRotationPoint(0.5F, 17.5F, 5.0F);

     this.key3.mirror = true;
     setRotation(this.key3, 0.0F, 0.0F, 0.0F);
     this.key4 = new ModelRenderer(this, 46, 8);
     this.key4.addBox(-0.5F, 1.5F, 1.0F, 1, 1, 2);
     this.key4.setRotationPoint(0.5F, 17.5F, 5.0F);

     this.key4.mirror = true;
     setRotation(this.key4, 0.0F, 0.0F, 0.0F);
     this.key5 = new ModelRenderer(this, 46, 8);
     this.key5.addBox(-0.5F, -1.5F, 3.0F, 1, 3, 1);
     this.key5.setRotationPoint(0.5F, 17.5F, 5.0F);

     this.key5.mirror = true;
     setRotation(this.key5, 0.0F, 0.0F, 0.0F);
   }

   public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay,
        float red, float green, float blue, float alpha) {
        Shape1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Shape2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Shape2a.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Shape3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Shape4.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Shape5.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Shape6.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Shape7.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Shape8.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Shape9.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Shape10.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Shape11.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Shape12.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Shape13.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Shape14.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Shape15.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Shape15a.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Shape16.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Shape17.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Shape18.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rfoot.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        lfoot.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        key2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        key1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        key3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        key4.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        key5.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }

   private void setRotation(ModelRenderer model, float x, float y, float z) {
     model.rotateAngleX = x;
     model.rotateAngleY = y;
     model.rotateAngleZ = z;
   }

   public void setRotationAngles(Entity entity, float limbSwing, float limbSwingAmount,
        float ageInTicks, float netHeadYaw, float headPitch) {
// Reset shared model state before applying this frame's legacy animation.
this.Shape1.setRotationPoint(-1.0F, 13.0F, -1.0F);
this.Shape2.setRotationPoint(0.0F, 13.0F, -2.0F);
this.Shape2a.setRotationPoint(-2.0F, 13.0F, 0.0F);
this.Shape3.setRotationPoint(-3.0F, 14.0F, -1.0F);
this.Shape4.setRotationPoint(-1.0F, 14.0F, -3.0F);
this.Shape5.setRotationPoint(-2.0F, 14.0F, -2.0F);
this.Shape6.setRotationPoint(-2.0F, 15.0F, -3.0F);
this.Shape7.setRotationPoint(0.0F, 15.0F, 4.0F);
this.Shape8.setRotationPoint(-3.0F, 15.0F, -2.0F);
this.Shape9.setRotationPoint(-4.0F, 15.0F, 0.0F);
this.Shape10.setRotationPoint(-1.0F, 16.0F, -4.0F);
this.Shape11.setRotationPoint(-4.0F, 16.0F, -1.0F);
this.Shape12.setRotationPoint(-3.0F, 16.0F, -3.0F);
this.Shape13.setRotationPoint(-4.0F, 17.0F, -2.0F);
this.Shape14.setRotationPoint(-2.0F, 17.0F, 4.0F);
this.Shape15.setRotationPoint(-2.0F, 15.0F, -4.0F);
this.Shape15a.setRotationPoint(1.0F, 15.0F, -4.0F);
this.Shape16.setRotationPoint(-1.0F, 12.0F, -1.0F);
this.Shape17.setRotationPoint(0.0F, 10.0F, 0.0F);
this.Shape18.setRotationPoint(1.7F, 8.733334F, 0.0F);
this.rfoot.setRotationPoint(-3.0F, 19.0F, 0.0F);
this.lfoot.setRotationPoint(2.0F, 19.0F, 0.0F);
this.key2.setRotationPoint(0.5F, 17.5F, 5.0F);
this.key1.setRotationPoint(0.5F, 17.5F, 5.0F);
this.key3.setRotationPoint(0.5F, 17.5F, 5.0F);
this.key4.setRotationPoint(0.5F, 17.5F, 5.0F);
this.key5.setRotationPoint(0.5F, 17.5F, 5.0F);
setRotation(this.Shape1, 0.0F, 0.0F, 0.0F);
setRotation(this.Shape2, 0.0F, 0.0F, 0.0F);
setRotation(this.Shape2a, 0.0F, 0.0F, 0.0F);
setRotation(this.Shape3, 0.0F, 0.0F, 0.0F);
setRotation(this.Shape4, 0.0F, 0.0F, 0.0F);
setRotation(this.Shape5, 0.0F, 0.0F, 0.0F);
setRotation(this.Shape6, 0.0F, 0.0F, 0.0F);
setRotation(this.Shape7, 0.0F, 0.0F, 0.0F);
setRotation(this.Shape8, 0.0F, 0.0F, 0.0F);
setRotation(this.Shape9, 0.0F, 0.0F, 0.0F);
setRotation(this.Shape10, 0.0F, 0.0F, 0.0F);
setRotation(this.Shape11, 0.0F, 0.0F, 0.0F);
setRotation(this.Shape12, 0.0F, 0.0F, 0.0F);
setRotation(this.Shape13, 0.0F, 0.0F, 0.0F);
setRotation(this.Shape14, 0.0F, 0.0F, 0.0F);
setRotation(this.Shape15, 0.0F, 0.0F, 0.0F);
setRotation(this.Shape15a, 0.0F, 0.0F, 0.0F);
setRotation(this.Shape16, 0.0F, 0.0F, 0.0F);
setRotation(this.Shape17, 0.0F, 0.0F, 0.0F);
setRotation(this.Shape18, 0.0F, 0.0F, 0.9667472F);
setRotation(this.rfoot, 0.0F, 0.0F, 0.0F);
setRotation(this.lfoot, 0.0F, 0.0F, 0.0F);
setRotation(this.key2, 0.0F, 0.0F, 0.0F);
setRotation(this.key1, 0.0F, 0.0F, 0.0F);
setRotation(this.key3, 0.0F, 0.0F, 0.0F);
setRotation(this.key4, 0.0F, 0.0F, 0.0F);
setRotation(this.key5, 0.0F, 0.0F, 0.0F);

     Robot1Entity.CustomEntity e = (Robot1Entity.CustomEntity) entity;
     float newangle;

     if (limbSwingAmount > 0.1D) {
       newangle = MathHelper.cos(ageInTicks * 1.5F * this.wingspeed) * 3.1415927F * 0.75F * limbSwingAmount;
     } else {
       newangle = 0.0F;
     } 
     this.lfoot.rotateAngleX = newangle;
     this.rfoot.rotateAngleX = -newangle;
     
     newangle = (float)Math.toRadians((ageInTicks * 0.75F * this.wingspeed));
     this.key1.rotateAngleZ = newangle;
     this.key2.rotateAngleZ = newangle;
     this.key3.rotateAngleZ = newangle;
     this.key4.rotateAngleZ = newangle;
     this.key5.rotateAngleZ = newangle;
    }
 }
}
