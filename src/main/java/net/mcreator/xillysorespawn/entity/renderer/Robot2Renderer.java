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
import net.mcreator.xillysorespawn.entity.Robot2Entity;
import net.mcreator.xillysorespawn.entity.OreSpawnLogic;

@OnlyIn(Dist.CLIENT)
public class Robot2Renderer {
    private static final String TEXTURE = "xillys_orespawn:textures/entities/robot2.png";
    public static class ModelRegisterHandler {
        @SubscribeEvent @OnlyIn(Dist.CLIENT)
        public void registerModels(ModelRegistryEvent event) {
            RenderingRegistry.registerEntityRenderingHandler(Robot2Entity.entity, manager ->
                new MobRenderer(manager, new ModelRobot2(1.0F), 1.0F) {
                    @Override public ResourceLocation getEntityTexture(Entity entity) { return new ResourceLocation(TEXTURE); }
                    @Override protected void preRenderCallback(LivingEntity entity, MatrixStack stack, float partialTick) {
                        float modelScale = 1.0F;
                        stack.scale(modelScale, modelScale, modelScale);
                    }
                });
        }
    }

public static class ModelRobot2 extends EntityModel<Entity>
 {
   private float wingspeed = 1.0F;

   private final ModelRenderer rleg1;

   private final ModelRenderer rleg2;

   private final ModelRenderer Shape3;
   private final ModelRenderer lleg2;
   private final ModelRenderer lleg1;
   private final ModelRenderer Shape6;
   private final ModelRenderer Shape7;
   private final ModelRenderer Shape8;
   private final ModelRenderer rarm3;
   private final ModelRenderer rarm2;
   private final ModelRenderer rarm1;
   private final ModelRenderer larm3;
   private final ModelRenderer larm2;
   private final ModelRenderer larm1;
   private final ModelRenderer head;

   public ModelRobot2(float f1) {
     this.wingspeed = f1;

     this.textureWidth = 256;
     this.textureHeight = 512;

     this.rleg1 = new ModelRenderer(this, 10, 250);
     this.rleg1.addBox(-14.0F, 24.0F, -7.0F, 16, 24, 16);
     this.rleg1.setRotationPoint(-10.0F, -24.0F, 0.0F);

     this.rleg1.mirror = true;
     setRotation(this.rleg1, 0.0F, 0.0F, 0.0F);
     this.rleg2 = new ModelRenderer(this, 10, 150);
     this.rleg2.addBox(-12.0F, 0.0F, -6.0F, 12, 24, 12);
     this.rleg2.setRotationPoint(-10.0F, -24.0F, 1.0F);

     this.rleg2.mirror = true;
     setRotation(this.rleg2, 0.0F, 0.0F, 0.0F);
     this.Shape3 = new ModelRenderer(this, 10, 50);
     this.Shape3.addBox(-4.0F, 0.0F, -2.0F, 26, 8, 12);
     this.Shape3.setRotationPoint(-9.0F, -32.0F, -3.0F);

     this.Shape3.mirror = true;
     setRotation(this.Shape3, 0.0F, 0.0F, 0.0F);
     this.lleg2 = new ModelRenderer(this, 10, 200);
     this.lleg2.addBox(0.0F, 0.0F, -6.0F, 12, 24, 12);
     this.lleg2.setRotationPoint(10.0F, -24.0F, 1.0F);

     this.lleg2.mirror = true;
     setRotation(this.lleg2, 0.0F, 0.0F, 0.0F);
     this.lleg1 = new ModelRenderer(this, 10, 300);
     this.lleg1.addBox(-2.0F, 24.0F, -7.0F, 16, 24, 16);
     this.lleg1.setRotationPoint(10.0F, -24.0F, 0.0F);

     this.lleg1.mirror = true;
     setRotation(this.lleg1, 0.0F, 0.0F, 0.0F);
     this.Shape6 = new ModelRenderer(this, 10, 100);
     this.Shape6.addBox(-4.0F, -8.0F, -3.0F, 8, 8, 8);
     this.Shape6.setRotationPoint(0.0F, -32.0F, 0.0F);

     this.Shape6.mirror = true;
     setRotation(this.Shape6, 0.0F, 0.0F, 0.0F);
     this.Shape7 = new ModelRenderer(this, 10, 350);
     this.Shape7.addBox(0.0F, 0.0F, 0.0F, 26, 8, 12);
     this.Shape7.setRotationPoint(-13.0F, -48.0F, -5.0F);

     this.Shape7.mirror = true;
     setRotation(this.Shape7, 0.0F, 0.0F, 0.0F);
     this.Shape8 = new ModelRenderer(this, 16, 400);
     this.Shape8.addBox(0.0F, 0.0F, 0.0F, 44, 18, 14);
     this.Shape8.setRotationPoint(-22.0F, -66.0F, -6.0F);

     this.Shape8.mirror = true;
     setRotation(this.Shape8, 0.0F, 0.0F, 0.0F);
     this.rarm3 = new ModelRenderer(this, 100, 100);
     this.rarm3.addBox(-16.0F, -16.0F, -7.0F, 16, 24, 17);
     this.rarm3.setRotationPoint(-22.0F, -58.0F, 0.0F);

     this.rarm3.mirror = true;
     setRotation(this.rarm3, 0.0F, 0.0F, 0.0F);
     this.rarm2 = new ModelRenderer(this, 100, 200);
     this.rarm2.addBox(-14.0F, 8.0F, -5.0F, 12, 24, 12);
     this.rarm2.setRotationPoint(-22.0F, -58.0F, 0.0F);

     this.rarm2.mirror = true;
     setRotation(this.rarm2, 0.0F, 0.0F, 0.0F);
     this.rarm1 = new ModelRenderer(this, 100, 300);
     this.rarm1.addBox(-14.0F, 32.0F, -5.0F, 12, 24, 12);
     this.rarm1.setRotationPoint(-22.0F, -58.0F, 0.0F);

     this.rarm1.mirror = true;
     setRotation(this.rarm1, 0.0F, 0.0F, 0.0F);
     this.larm3 = new ModelRenderer(this, 100, 50);
     this.larm3.addBox(0.0F, -16.0F, -7.0F, 16, 24, 17);
     this.larm3.setRotationPoint(22.0F, -58.0F, 0.0F);

     this.larm3.mirror = true;
     setRotation(this.larm3, 0.0F, 0.0F, 0.0F);
     this.larm2 = new ModelRenderer(this, 100, 150);
     this.larm2.addBox(2.0F, 8.0F, -5.0F, 12, 24, 12);
     this.larm2.setRotationPoint(21.0F, -58.0F, 0.0F);

     this.larm2.mirror = true;
     setRotation(this.larm2, 0.0F, 0.0F, 0.0F);
     this.larm1 = new ModelRenderer(this, 100, 250);
     this.larm1.addBox(2.0F, 32.0F, -5.0F, 12, 24, 12);
     this.larm1.setRotationPoint(21.0F, -58.0F, 0.0F);

     this.larm1.mirror = true;
     setRotation(this.larm1, 0.0F, 0.0F, 0.0F);
     this.head = new ModelRenderer(this, 50, 10);
     this.head.addBox(-7.0F, -12.0F, -5.0F, 15, 12, 10);
     this.head.setRotationPoint(0.0F, -66.0F, 1.0F);

     this.head.mirror = true;
     setRotation(this.head, 0.0F, 0.0F, 0.0F);
   }

   public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay,
        float red, float green, float blue, float alpha) {
        rleg1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rleg2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Shape3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        lleg2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        lleg1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Shape6.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Shape7.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Shape8.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rarm3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rarm2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rarm1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        larm3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        larm2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        larm1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        head.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }

   private void setRotation(ModelRenderer model, float x, float y, float z) {
     model.rotateAngleX = x;
     model.rotateAngleY = y;
     model.rotateAngleZ = z;
   }

   public void setRotationAngles(Entity entity, float limbSwing, float limbSwingAmount,
        float ageInTicks, float netHeadYaw, float headPitch) {
// Reset shared model state before applying this frame's legacy animation.
this.rleg1.setRotationPoint(-10.0F, -24.0F, 0.0F);
this.rleg2.setRotationPoint(-10.0F, -24.0F, 1.0F);
this.Shape3.setRotationPoint(-9.0F, -32.0F, -3.0F);
this.lleg2.setRotationPoint(10.0F, -24.0F, 1.0F);
this.lleg1.setRotationPoint(10.0F, -24.0F, 0.0F);
this.Shape6.setRotationPoint(0.0F, -32.0F, 0.0F);
this.Shape7.setRotationPoint(-13.0F, -48.0F, -5.0F);
this.Shape8.setRotationPoint(-22.0F, -66.0F, -6.0F);
this.rarm3.setRotationPoint(-22.0F, -58.0F, 0.0F);
this.rarm2.setRotationPoint(-22.0F, -58.0F, 0.0F);
this.rarm1.setRotationPoint(-22.0F, -58.0F, 0.0F);
this.larm3.setRotationPoint(22.0F, -58.0F, 0.0F);
this.larm2.setRotationPoint(21.0F, -58.0F, 0.0F);
this.larm1.setRotationPoint(21.0F, -58.0F, 0.0F);
this.head.setRotationPoint(0.0F, -66.0F, 1.0F);
setRotation(this.rleg1, 0.0F, 0.0F, 0.0F);
setRotation(this.rleg2, 0.0F, 0.0F, 0.0F);
setRotation(this.Shape3, 0.0F, 0.0F, 0.0F);
setRotation(this.lleg2, 0.0F, 0.0F, 0.0F);
setRotation(this.lleg1, 0.0F, 0.0F, 0.0F);
setRotation(this.Shape6, 0.0F, 0.0F, 0.0F);
setRotation(this.Shape7, 0.0F, 0.0F, 0.0F);
setRotation(this.Shape8, 0.0F, 0.0F, 0.0F);
setRotation(this.rarm3, 0.0F, 0.0F, 0.0F);
setRotation(this.rarm2, 0.0F, 0.0F, 0.0F);
setRotation(this.rarm1, 0.0F, 0.0F, 0.0F);
setRotation(this.larm3, 0.0F, 0.0F, 0.0F);
setRotation(this.larm2, 0.0F, 0.0F, 0.0F);
setRotation(this.larm1, 0.0F, 0.0F, 0.0F);
setRotation(this.head, 0.0F, 0.0F, 0.0F);

     Robot2Entity.CustomEntity e = (Robot2Entity.CustomEntity) entity;
     RenderInfo r = null;
     float newangle;

     if (limbSwingAmount > 0.1D) {
       newangle = MathHelper.cos(ageInTicks * 0.3F * this.wingspeed) * 3.1415927F * 0.12F * limbSwingAmount;
     } else {
       newangle = 0.0F;
     } 
     
     this.lleg1.rotateAngleX = newangle;
     this.lleg2.rotateAngleX = newangle;
     this.rleg1.rotateAngleX = -newangle;
     this.rleg2.rotateAngleX = -newangle;
     
     this.head.rotateAngleY = (float)Math.toRadians(netHeadYaw);
     
     newangle = MathHelper.sin((float)Math.toRadians((ageInTicks * 20.0F * this.wingspeed)));
     float nextangle = MathHelper.sin((float)Math.toRadians((ageInTicks * 20.0F * this.wingspeed + 1.5F)));
 
 
 
     
     r = e.getRenderInfo();
     
     if (nextangle > 0.0F && newangle < 0.0F) {
       
       r.ri1 = 0;
       if (e.getAttacking() == 0) {
         r.ri1 = 0;
       } else {
         for (; r.ri1 == 0; r.ri1 = e.getModelRandom().nextInt(4));
       } 
     } 
     
     newangle = (float)Math.toRadians((ageInTicks * 20.0F * this.wingspeed));
     
     if (r.ri1 == 1 || r.ri1 == 3) {
       this.rarm1.rotateAngleX = newangle;
       this.rarm2.rotateAngleX = newangle;
       this.rarm3.rotateAngleX = newangle;
     } else {
       this.rarm1.rotateAngleX = 0.0F;
       this.rarm2.rotateAngleX = 0.0F;
       this.rarm3.rotateAngleX = 0.0F;
     } 
     if (r.ri1 == 2 || r.ri1 == 3) {
       this.larm1.rotateAngleX = newangle;
       this.larm2.rotateAngleX = newangle;
       this.larm3.rotateAngleX = newangle;
     } else {
       this.larm1.rotateAngleX = 0.0F;
       this.larm2.rotateAngleX = 0.0F;
       this.larm3.rotateAngleX = 0.0F;
     } 
 
     
     e.setRenderInfo(r);
    }
 }
}
