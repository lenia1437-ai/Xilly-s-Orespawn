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
import net.mcreator.xillysorespawn.entity.ScorpionEntity;
import net.mcreator.xillysorespawn.entity.OreSpawnLogic;

@OnlyIn(Dist.CLIENT)
public class ScorpionRenderer {
    private static final String TEXTURE = "xillys_orespawn:textures/entities/scorpion.png";
    public static class ModelRegisterHandler {
        @SubscribeEvent @OnlyIn(Dist.CLIENT)
        public void registerModels(ModelRegistryEvent event) {
            RenderingRegistry.registerEntityRenderingHandler(ScorpionEntity.entity, manager ->
                new MobRenderer(manager, new ModelScorpion(0.62F), 0.2625F) {
                    @Override public ResourceLocation getEntityTexture(Entity entity) { return new ResourceLocation(TEXTURE); }
                    @Override protected void preRenderCallback(LivingEntity entity, MatrixStack stack, float partialTick) {
                        float modelScale = 0.75F;
                        stack.scale(modelScale, modelScale, modelScale);
                    }
                });
        }
    }

public static class ModelScorpion extends EntityModel<Entity>
 {
   private float wingspeed = 1.0F;

   private final ModelRenderer body;

   private final ModelRenderer tail1;
   private final ModelRenderer tail2;
   private final ModelRenderer tail3;
   private final ModelRenderer tail4;
   private final ModelRenderer tail5;
   private final ModelRenderer tail6;
   private final ModelRenderer lleg1;
   private final ModelRenderer rleg1;
   private final ModelRenderer rleg2;
   private final ModelRenderer lleg3;
   private final ModelRenderer rleg4;
   private final ModelRenderer rleg3;
   private final ModelRenderer lleg4;
   private final ModelRenderer lleg2;
   private final ModelRenderer head;
   private final ModelRenderer larm2;
   private final ModelRenderer rarm2;
   private final ModelRenderer larm1;
   private final ModelRenderer rarm1;
   private final ModelRenderer lclaw;
   private final ModelRenderer rclaw;

   public ModelScorpion(float f1) {
     this.wingspeed = f1;

     this.textureWidth = 88;
     this.textureHeight = 24;

     this.body = new ModelRenderer(this, 0, 0);
     this.body.addBox(0.0F, 0.0F, 0.0F, 6, 4, 8);
     this.body.setRotationPoint(-3.0F, 17.0F, -4.0F);

     this.body.mirror = true;
     setRotation(this.body, 0.0F, 0.0F, 0.0F);
     this.tail1 = new ModelRenderer(this, 28, 0);
     this.tail1.addBox(0.0F, 0.0F, 0.0F, 4, 4, 5);
     this.tail1.setRotationPoint(-2.0F, 17.0F, 3.0F);

     this.tail1.mirror = true;
     setRotation(this.tail1, 0.2617994F, 0.0F, 0.0F);
     this.tail2 = new ModelRenderer(this, 46, 0);
     this.tail2.addBox(0.0F, 0.0F, 0.0F, 3, 3, 5);
     this.tail2.setRotationPoint(-1.5F, 16.8F, 6.0F);

     this.tail2.mirror = true;
     setRotation(this.tail2, 1.029744F, 0.0F, 0.0F);
     this.tail3 = new ModelRenderer(this, 62, 0);
     this.tail3.addBox(0.0F, 0.0F, 0.0F, 3, 3, 4);
     this.tail3.setRotationPoint(-1.5F, 14.5F, 8.0F);

     this.tail3.mirror = true;
     setRotation(this.tail3, 1.727876F, 0.0F, 0.0F);
     this.tail4 = new ModelRenderer(this, 0, 17);
     this.tail4.addBox(0.0F, 0.0F, 0.0F, 2, 2, 5);
     this.tail4.setRotationPoint(-1.0F, 12.0F, 9.0F);

     this.tail4.mirror = true;
     setRotation(this.tail4, 2.513274F, 0.0F, 0.0F);
     this.tail5 = new ModelRenderer(this, 70, 7);
     this.tail5.addBox(0.0F, 0.0F, 0.0F, 2, 2, 4);
     this.tail5.setRotationPoint(-1.0F, 9.0F, 6.0F);

     this.tail5.mirror = true;
     setRotation(this.tail5, 3.141593F, 0.0F, 0.0F);
     this.tail6 = new ModelRenderer(this, 62, 7);
     this.tail6.addBox(0.0F, 0.0F, 0.0F, 1, 1, 3);
     this.tail6.setRotationPoint(-0.5F, 8.0F, 2.0F);

     this.tail6.mirror = true;
     setRotation(this.tail6, 3.141593F, 0.0F, 0.0F);
     this.lleg1 = new ModelRenderer(this, 0, 12);
     this.lleg1.addBox(0.0F, 0.0F, 0.0F, 11, 2, 2);
     this.lleg1.setRotationPoint(2.0F, 18.0F, -3.0F);

     this.lleg1.mirror = true;
     setRotation(this.lleg1, 0.0F, 0.4886922F, 0.3665191F);
     this.rleg1 = new ModelRenderer(this, 0, 12);
     this.rleg1.addBox(0.0F, 0.0F, 0.0F, 11, 2, 2);
     this.rleg1.setRotationPoint(-2.0F, 18.0F, -1.0F);

     this.rleg1.mirror = true;
     setRotation(this.rleg1, 0.0F, 2.6529F, -0.3665191F);
     this.rleg2 = new ModelRenderer(this, 0, 12);
     this.rleg2.addBox(0.0F, 0.0F, 0.0F, 11, 2, 2);
     this.rleg2.setRotationPoint(-2.0F, 18.0F, 1.0F);

     this.rleg2.mirror = true;
     setRotation(this.rleg2, 0.0F, 2.897247F, -0.3665191F);
     this.lleg3 = new ModelRenderer(this, 0, 12);
     this.lleg3.addBox(0.0F, 0.0F, 0.0F, 11, 2, 2);
     this.lleg3.setRotationPoint(2.0F, 18.0F, 1.0F);

     this.lleg3.mirror = true;
     setRotation(this.lleg3, 0.0F, -0.2443461F, 0.3665191F);
     this.rleg4 = new ModelRenderer(this, 0, 12);
     this.rleg4.addBox(0.0F, 0.0F, 0.0F, 11, 2, 2);
     this.rleg4.setRotationPoint(-2.0F, 18.0F, 5.0F);

     this.rleg4.mirror = true;
     setRotation(this.rleg4, 0.0F, -2.6529F, -0.3665191F);
     this.rleg3 = new ModelRenderer(this, 0, 12);
     this.rleg3.addBox(0.0F, 0.0F, 0.0F, 11, 2, 2);
     this.rleg3.setRotationPoint(-2.0F, 18.0F, 3.0F);

     this.rleg3.mirror = true;
     setRotation(this.rleg3, 0.0F, -2.897247F, -0.3665191F);
     this.lleg4 = new ModelRenderer(this, 0, 12);
     this.lleg4.addBox(0.0F, 0.0F, 0.0F, 11, 2, 2);
     this.lleg4.setRotationPoint(2.0F, 18.0F, 3.0F);

     this.lleg4.mirror = true;
     setRotation(this.lleg4, 0.0F, -0.4886922F, 0.3665191F);
     this.lleg2 = new ModelRenderer(this, 0, 12);
     this.lleg2.addBox(0.0F, 0.0F, 0.0F, 11, 2, 2);
     this.lleg2.setRotationPoint(2.0F, 18.0F, -1.0F);

     this.lleg2.mirror = true;
     setRotation(this.lleg2, 0.0F, 0.2443461F, 0.3665191F);
     this.head = new ModelRenderer(this, 28, 9);
     this.head.addBox(0.0F, 0.0F, 0.0F, 5, 3, 4);
     this.head.setRotationPoint(-2.5F, 17.5F, -8.0F);

     this.head.mirror = true;
     setRotation(this.head, 0.0F, 0.0F, 0.0F);
     this.larm2 = new ModelRenderer(this, 46, 8);
     this.larm2.addBox(0.0F, 0.0F, 0.0F, 6, 2, 2);
     this.larm2.setRotationPoint(1.0F, 18.0F, -6.0F);

     this.larm2.mirror = true;
     setRotation(this.larm2, 0.0F, 0.5235988F, 0.1745329F);
     this.rarm2 = new ModelRenderer(this, 46, 8);
     this.rarm2.addBox(0.0F, 0.0F, -2.0F, 6, 2, 2);
     this.rarm2.setRotationPoint(-1.0F, 18.0F, -6.0F);

     this.rarm2.mirror = true;
     setRotation(this.rarm2, 0.0F, 2.617994F, -0.1745329F);
     this.larm1 = new ModelRenderer(this, 70, 13);
     this.larm1.addBox(-2.0F, 0.0F, -3.0F, 2, 2, 3);
     this.larm1.setRotationPoint(7.0F, 19.0F, -7.2F);

     this.larm1.mirror = true;
     setRotation(this.larm1, 0.1745329F, 0.1745329F, 0.0F);
     this.rarm1 = new ModelRenderer(this, 70, 13);
     this.rarm1.addBox(0.0F, 0.0F, -3.0F, 2, 2, 3);
     this.rarm1.setRotationPoint(-7.0F, 19.0F, -7.2F);

     this.rarm1.mirror = true;
     setRotation(this.rarm1, 0.1745329F, -0.1745329F, 0.0F);
     this.lclaw = new ModelRenderer(this, 46, 12);
     this.lclaw.addBox(-3.0F, 0.0F, -4.0F, 3, 2, 4);
     this.lclaw.setRotationPoint(7.0F, 19.0F, -10.0F);

     this.lclaw.mirror = true;
     setRotation(this.lclaw, 0.0174533F, 0.3839724F, 0.1396263F);
     this.rclaw = new ModelRenderer(this, 46, 12);
     this.rclaw.addBox(0.0F, 0.0F, -4.0F, 3, 2, 4);
     this.rclaw.setRotationPoint(-7.0F, 19.0F, -10.0F);

     this.rclaw.mirror = true;
     setRotation(this.rclaw, 0.0174533F, -0.3839724F, 0.1396263F);
   }

   public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay,
        float red, float green, float blue, float alpha) {
        body.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        tail1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        tail2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        tail3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        tail4.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        tail5.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        tail6.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        lleg1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rleg1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rleg2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        lleg3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rleg4.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rleg3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        lleg4.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        lleg2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        head.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        larm2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rarm2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        larm1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rarm1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        lclaw.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rclaw.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }

   private void setRotation(ModelRenderer model, float x, float y, float z) {
     model.rotateAngleX = x;
     model.rotateAngleY = y;
     model.rotateAngleZ = z;
   }

   public void setRotationAngles(Entity entity, float limbSwing, float limbSwingAmount,
        float ageInTicks, float netHeadYaw, float headPitch) {
// Reset shared model state before applying this frame's legacy animation.
this.body.setRotationPoint(-3.0F, 17.0F, -4.0F);
this.tail1.setRotationPoint(-2.0F, 17.0F, 3.0F);
this.tail2.setRotationPoint(-1.5F, 16.8F, 6.0F);
this.tail3.setRotationPoint(-1.5F, 14.5F, 8.0F);
this.tail4.setRotationPoint(-1.0F, 12.0F, 9.0F);
this.tail5.setRotationPoint(-1.0F, 9.0F, 6.0F);
this.tail6.setRotationPoint(-0.5F, 8.0F, 2.0F);
this.lleg1.setRotationPoint(2.0F, 18.0F, -3.0F);
this.rleg1.setRotationPoint(-2.0F, 18.0F, -1.0F);
this.rleg2.setRotationPoint(-2.0F, 18.0F, 1.0F);
this.lleg3.setRotationPoint(2.0F, 18.0F, 1.0F);
this.rleg4.setRotationPoint(-2.0F, 18.0F, 5.0F);
this.rleg3.setRotationPoint(-2.0F, 18.0F, 3.0F);
this.lleg4.setRotationPoint(2.0F, 18.0F, 3.0F);
this.lleg2.setRotationPoint(2.0F, 18.0F, -1.0F);
this.head.setRotationPoint(-2.5F, 17.5F, -8.0F);
this.larm2.setRotationPoint(1.0F, 18.0F, -6.0F);
this.rarm2.setRotationPoint(-1.0F, 18.0F, -6.0F);
this.larm1.setRotationPoint(7.0F, 19.0F, -7.2F);
this.rarm1.setRotationPoint(-7.0F, 19.0F, -7.2F);
this.lclaw.setRotationPoint(7.0F, 19.0F, -10.0F);
this.rclaw.setRotationPoint(-7.0F, 19.0F, -10.0F);
setRotation(this.body, 0.0F, 0.0F, 0.0F);
setRotation(this.tail1, 0.2617994F, 0.0F, 0.0F);
setRotation(this.tail2, 1.029744F, 0.0F, 0.0F);
setRotation(this.tail3, 1.727876F, 0.0F, 0.0F);
setRotation(this.tail4, 2.513274F, 0.0F, 0.0F);
setRotation(this.tail5, 3.141593F, 0.0F, 0.0F);
setRotation(this.tail6, 3.141593F, 0.0F, 0.0F);
setRotation(this.lleg1, 0.0F, 0.4886922F, 0.3665191F);
setRotation(this.rleg1, 0.0F, 2.6529F, -0.3665191F);
setRotation(this.rleg2, 0.0F, 2.897247F, -0.3665191F);
setRotation(this.lleg3, 0.0F, -0.2443461F, 0.3665191F);
setRotation(this.rleg4, 0.0F, -2.6529F, -0.3665191F);
setRotation(this.rleg3, 0.0F, -2.897247F, -0.3665191F);
setRotation(this.lleg4, 0.0F, -0.4886922F, 0.3665191F);
setRotation(this.lleg2, 0.0F, 0.2443461F, 0.3665191F);
setRotation(this.head, 0.0F, 0.0F, 0.0F);
setRotation(this.larm2, 0.0F, 0.5235988F, 0.1745329F);
setRotation(this.rarm2, 0.0F, 2.617994F, -0.1745329F);
setRotation(this.larm1, 0.1745329F, 0.1745329F, 0.0F);
setRotation(this.rarm1, 0.1745329F, -0.1745329F, 0.0F);
setRotation(this.lclaw, 0.0174533F, 0.3839724F, 0.1396263F);
setRotation(this.rclaw, 0.0174533F, -0.3839724F, 0.1396263F);

     ScorpionEntity.CustomEntity e = (ScorpionEntity.CustomEntity) entity;
     RenderInfo r = null;

     float newangle = 0.0F, upangle = 0.0F, nextangle = 0.0F;
 
     
     float pi4 = 1.570795F;
 
     
     newangle = MathHelper.cos(ageInTicks * 2.0F * this.wingspeed) * 3.1415927F * 0.12F * limbSwingAmount;
     this.lleg1.rotateAngleY = newangle + 0.49F;
     this.rleg1.rotateAngleY = -newangle + 2.65F;
     
     newangle = MathHelper.cos(ageInTicks * 2.0F * this.wingspeed - 1.0F * pi4) * 3.1415927F * 0.12F * limbSwingAmount;
     this.lleg2.rotateAngleY = newangle + 0.24F;
     this.rleg2.rotateAngleY = -newangle + 2.9F;
     
     newangle = MathHelper.cos(ageInTicks * 2.0F * this.wingspeed - 2.0F * pi4) * 3.1415927F * 0.12F * limbSwingAmount;
     this.lleg3.rotateAngleY = newangle - 0.24F;
     this.rleg3.rotateAngleY = -newangle - 2.9F;
     
     newangle = MathHelper.cos(ageInTicks * 2.0F * this.wingspeed - 3.0F * pi4) * 3.1415927F * 0.12F * limbSwingAmount;
     this.lleg4.rotateAngleY = newangle - 0.49F;
     this.rleg4.rotateAngleY = -newangle - 2.65F;
 
 
 
     
     r = e.getRenderInfo();
 
     
     newangle = MathHelper.cos(ageInTicks * 3.0F * this.wingspeed) * 3.1415927F * 0.15F;
     nextangle = MathHelper.cos((ageInTicks + 0.1F) * 3.0F * this.wingspeed) * 3.1415927F * 0.15F;
 
 
 
     
     if (nextangle > 0.0F && newangle < 0.0F) {
       
       r.ri1 = 0;
       if (e.getAttacking() == 0) {
         r.ri1 = e.getModelRandom().nextInt(20);
         r.ri2 = e.getModelRandom().nextInt(25);
       } else {
         r.ri1 = e.getModelRandom().nextInt(4);
         r.ri2 = e.getModelRandom().nextInt(3);
       } 
     } 
 
     
     if (r.ri1 == 1 || r.ri1 == 3) {
       doLeftClaw(newangle);
     } else {
       doLeftClaw(0.0F);
     } 
     if (r.ri1 == 2 || r.ri1 == 3) {
       doRightClaw(newangle);
     } else {
       doRightClaw(0.0F);
     } 
 
 
 
     
     if (r.ri2 == 1) {
       doTail(newangle);
     } else {
       doTail(0.0F);
     } 
 
     
     e.setRenderInfo(r);
    }

   private void doLeftClaw(float angle) {
     this.larm2.rotateAngleY = 0.52F + angle;
     this.larm1.rotationPointZ = (float)(this.larm2.rotationPointZ - Math.sin(this.larm2.rotateAngleY) * 4.5D);
     this.larm1.rotationPointZ -= 3.0F;
     this.lclaw.rotateAngleY = 0.381F - angle;
   }

   private void doRightClaw(float angle) {
     this.rarm2.rotateAngleY = 2.61F - angle;
     this.rarm1.rotationPointZ = (float)(this.rarm2.rotationPointZ - Math.sin(this.rarm2.rotateAngleY) * 4.5D);
     this.rarm1.rotationPointZ -= 3.0F;
     this.rclaw.rotateAngleY = -0.381F + angle;
   }

   private void doTail(float angle) {
     this.tail1.rotateAngleX = 0.26F + angle;

     this.tail2.rotateAngleX = this.tail1.rotateAngleX + 0.76900005F + angle;
     this.tail2.rotationPointY = (float)(this.tail1.rotationPointY - Math.sin(this.tail1.rotateAngleX) * 4.0D);
     this.tail2.rotationPointZ = (float)(this.tail1.rotationPointZ + Math.cos(this.tail1.rotateAngleX) * 4.0D);

     this.tail3.rotateAngleX = this.tail2.rotateAngleX + 0.701F + angle;
     this.tail3.rotationPointY = (float)(this.tail2.rotationPointY - Math.sin(this.tail2.rotateAngleX) * 4.0D);
     this.tail3.rotationPointZ = (float)(this.tail2.rotationPointZ + Math.cos(this.tail2.rotateAngleX) * 4.0D);

     this.tail4.rotateAngleX = this.tail3.rotateAngleX + -5.501F - angle * 3.0F / 2.0F - 0.4F;
     this.tail4.rotationPointY = (float)(this.tail3.rotationPointY - Math.sin(this.tail3.rotateAngleX) * 3.0D);
     this.tail4.rotationPointZ = (float)(this.tail3.rotationPointZ + Math.cos(this.tail3.rotateAngleX) * 3.0D);

     this.tail5.rotationPointY = (float)(this.tail4.rotationPointY - Math.sin(this.tail4.rotateAngleX) * 4.0D);
     this.tail5.rotationPointZ = (float)(this.tail4.rotationPointZ + Math.cos(this.tail4.rotateAngleX) * 4.0D);

     this.tail6.rotationPointY = (float)(this.tail5.rotationPointY - Math.sin(this.tail5.rotateAngleX) * 4.0D);
     this.tail6.rotationPointZ = (float)(this.tail5.rotationPointZ + Math.cos(this.tail5.rotateAngleX) * 4.0D);
   }
 }
}