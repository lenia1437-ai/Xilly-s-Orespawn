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
import net.mcreator.xillysorespawn.entity.PointysaurusEntity;
import net.mcreator.xillysorespawn.entity.OreSpawnLogic;

@OnlyIn(Dist.CLIENT)
public class PointysaurusRenderer {
    private static final String TEXTURE = "xillys_orespawn:textures/entities/pointysaurustexture.png";
    public static class ModelRegisterHandler {
        @SubscribeEvent @OnlyIn(Dist.CLIENT)
        public void registerModels(ModelRegistryEvent event) {
            RenderingRegistry.registerEntityRenderingHandler(PointysaurusEntity.entity, manager ->
                new MobRenderer(manager, new ModelPointysaurus(1.0F), 1.0F) {
                    @Override public ResourceLocation getEntityTexture(Entity entity) { return new ResourceLocation(TEXTURE); }
                    @Override protected void preRenderCallback(LivingEntity entity, MatrixStack stack, float partialTick) {
                        float modelScale = 1.0F;
                        stack.scale(modelScale, modelScale, modelScale);
                    }
                });
        }
    }

public static class ModelPointysaurus extends EntityModel<Entity>
 {
   private float wingspeed = 1.0F;

   private final ModelRenderer lfleg;

   private final ModelRenderer rfleg;

   private final ModelRenderer lrleg;
   private final ModelRenderer rrleg;
   private final ModelRenderer body1;
   private final ModelRenderer head;
   private final ModelRenderer body2;
   private final ModelRenderer body3;
   private final ModelRenderer guard;
   private final ModelRenderer nose;
   private final ModelRenderer lhorn;
   private final ModelRenderer rhorn;
   private final ModelRenderer chorn;
   private final ModelRenderer tail;
   private final ModelRenderer bump1;
   private final ModelRenderer bump2;
   private final ModelRenderer bump3;
   private final ModelRenderer bump4;
   private final ModelRenderer bump5;
   private final ModelRenderer bump6;
   private final ModelRenderer bump7;
   private final ModelRenderer bump8;
   private final ModelRenderer bump9;
   private final ModelRenderer bump10;
   private final ModelRenderer bump11;
   private final ModelRenderer bump12;
   private final ModelRenderer bump13;
   private final ModelRenderer bump14;
   private final ModelRenderer bump15;
   private final ModelRenderer bump16;

   public ModelPointysaurus(float f1) {
     this.wingspeed = f1;

     this.textureWidth = 128;
     this.textureHeight = 128;

     this.lfleg = new ModelRenderer(this, 102, 66);
     this.lfleg.addBox(-3.0F, 0.0F, -3.0F, 6, 8, 6);
     this.lfleg.setRotationPoint(9.0F, 16.0F, -8.0F);

     this.lfleg.mirror = true;
     setRotation(this.lfleg, 0.0F, 0.0F, 0.0F);
     this.rfleg = new ModelRenderer(this, 102, 66);
     this.rfleg.addBox(-3.0F, 0.0F, -3.0F, 6, 8, 6);
     this.rfleg.setRotationPoint(-9.0F, 16.0F, -8.0F);

     this.rfleg.mirror = true;
     setRotation(this.rfleg, 0.0F, 0.0F, 0.0F);
     this.lrleg = new ModelRenderer(this, 0, 0);
     this.lrleg.addBox(-4.0F, 0.0F, -4.0F, 8, 8, 8);
     this.lrleg.setRotationPoint(9.0F, 16.0F, 12.0F);

     this.lrleg.mirror = true;
     setRotation(this.lrleg, 0.0F, 0.0F, 0.0F);
     this.rrleg = new ModelRenderer(this, 0, 0);
     this.rrleg.addBox(-4.0F, 0.0F, -4.0F, 8, 8, 8);
     this.rrleg.setRotationPoint(-9.0F, 16.0F, 12.0F);

     this.rrleg.mirror = true;
     setRotation(this.rrleg, 0.0F, 0.0F, 0.0F);
     this.body1 = new ModelRenderer(this, 0, 87);
     this.body1.addBox(-4.0F, 0.0F, 0.0F, 22, 9, 30);
     this.body1.setRotationPoint(-7.0F, 9.0F, -12.0F);

     this.body1.mirror = true;
     setRotation(this.body1, 0.0F, 0.0F, 0.0F);
     this.head = new ModelRenderer(this, 70, 0);
     this.head.addBox(-6.0F, -10.0F, -12.0F, 12, 10, 12);
     this.head.setRotationPoint(0.0F, 11.0F, -7.0F);

     this.head.mirror = true;
     setRotation(this.head, -0.1919862F, 0.0F, 0.0F);
     this.body2 = new ModelRenderer(this, 0, 63);
     this.body2.addBox(-9.0F, 0.0F, 0.0F, 18, 7, 15);
     this.body2.setRotationPoint(0.0F, 2.0F, -9.0F);

     this.body2.mirror = true;
     setRotation(this.body2, 0.0F, 0.0F, 0.0F);
     this.body3 = new ModelRenderer(this, 0, 44);
     this.body3.addBox(-8.0F, 0.0F, 0.0F, 16, 6, 11);
     this.body3.setRotationPoint(0.0F, 3.0F, 6.0F);

     this.body3.mirror = true;
     setRotation(this.body3, 0.0F, 0.0F, 0.0F);
     this.guard = new ModelRenderer(this, 60, 34);
     this.guard.addBox(-14.0F, -20.0F, -8.0F, 28, 23, 3);
     this.guard.setRotationPoint(0.0F, 11.0F, -7.0F);

     this.guard.mirror = true;
     setRotation(this.guard, -0.2617994F, 0.0F, 0.0F);
     this.nose = new ModelRenderer(this, 39, 0);
     this.nose.addBox(-5.0F, -9.0F, -15.0F, 10, 6, 5);
     this.nose.setRotationPoint(0.0F, 11.0F, -7.0F);

     this.nose.mirror = true;
     setRotation(this.nose, 0.0F, 0.0F, 0.0F);
     this.lhorn = new ModelRenderer(this, 0, 18);
     this.lhorn.addBox(8.0F, -16.0F, -29.0F, 2, 2, 23);
     this.lhorn.setRotationPoint(0.0F, 11.0F, -7.0F);

     this.lhorn.mirror = true;
     setRotation(this.lhorn, -0.1570796F, -0.1396263F, 0.0F);
     this.rhorn = new ModelRenderer(this, 0, 18);
     this.rhorn.addBox(-9.0F, -16.0F, -29.0F, 2, 2, 23);
     this.rhorn.setRotationPoint(0.0F, 11.0F, -7.0F);

     this.rhorn.mirror = true;
     setRotation(this.rhorn, -0.1570796F, 0.1396263F, 0.0F);
     this.chorn = new ModelRenderer(this, 52, 13);
     this.chorn.addBox(-1.5F, -9.0F, -20.0F, 3, 3, 5);
     this.chorn.setRotationPoint(0.0F, 11.0F, -7.0F);

     this.chorn.mirror = true;
     setRotation(this.chorn, 0.0F, 0.0F, 0.0F);
     this.tail = new ModelRenderer(this, 68, 70);
     this.tail.addBox(-3.0F, -3.0F, 0.0F, 6, 6, 9);
     this.tail.setRotationPoint(0.0F, 7.0F, 15.0F);

     this.tail.mirror = true;
     setRotation(this.tail, 0.2792527F, 0.0F, 0.0F);
     this.bump1 = new ModelRenderer(this, 57, 17);
     this.bump1.addBox(14.0F, -20.0F, -8.0F, 2, 2, 2);
     this.bump1.setRotationPoint(0.0F, 11.0F, -7.0F);

     this.bump1.mirror = true;
     setRotation(this.bump1, -0.2617994F, 0.0F, 0.0F);
     this.bump2 = new ModelRenderer(this, 57, 17);
     this.bump2.addBox(14.0F, -15.0F, -8.0F, 2, 2, 2);
     this.bump2.setRotationPoint(0.0F, 11.0F, -7.0F);

     this.bump2.mirror = true;
     setRotation(this.bump2, -0.2617994F, 0.0F, 0.0F);
     this.bump3 = new ModelRenderer(this, 57, 17);
     this.bump3.addBox(14.0F, -10.0F, -8.0F, 2, 2, 2);
     this.bump3.setRotationPoint(0.0F, 11.0F, -7.0F);

     this.bump3.mirror = true;
     setRotation(this.bump3, -0.2617994F, 0.0F, 0.0F);
     this.bump4 = new ModelRenderer(this, 57, 17);
     this.bump4.addBox(14.0F, -5.0F, -8.0F, 2, 2, 2);
     this.bump4.setRotationPoint(0.0F, 11.0F, -7.0F);

     this.bump4.mirror = true;
     setRotation(this.bump4, -0.2617994F, 0.0F, 0.0F);
     this.bump5 = new ModelRenderer(this, 57, 17);
     this.bump5.addBox(14.0F, 0.0F, -8.0F, 2, 2, 2);
     this.bump5.setRotationPoint(0.0F, 11.0F, -7.0F);

     this.bump5.mirror = true;
     setRotation(this.bump5, -0.2617994F, 0.0F, 0.0F);
     this.bump6 = new ModelRenderer(this, 57, 17);
     this.bump6.addBox(-16.0F, -20.0F, -8.0F, 2, 2, 2);
     this.bump6.setRotationPoint(0.0F, 11.0F, -7.0F);

     this.bump6.mirror = true;
     setRotation(this.bump6, -0.2617994F, 0.0F, 0.0F);
     this.bump7 = new ModelRenderer(this, 57, 17);
     this.bump7.addBox(-16.0F, -15.0F, -8.0F, 2, 2, 2);
     this.bump7.setRotationPoint(0.0F, 11.0F, -7.0F);

     this.bump7.mirror = true;
     setRotation(this.bump7, -0.2617994F, 0.0F, 0.0F);
     this.bump8 = new ModelRenderer(this, 57, 17);
     this.bump8.addBox(-16.0F, -10.0F, -8.0F, 2, 2, 2);
     this.bump8.setRotationPoint(0.0F, 11.0F, -7.0F);

     this.bump8.mirror = true;
     setRotation(this.bump8, -0.2617994F, 0.0F, 0.0F);
     this.bump9 = new ModelRenderer(this, 57, 17);
     this.bump9.addBox(-16.0F, -5.0F, -8.0F, 2, 2, 2);
     this.bump9.setRotationPoint(0.0F, 11.0F, -7.0F);

     this.bump9.mirror = true;
     setRotation(this.bump9, -0.2617994F, 0.0F, 0.0F);
     this.bump10 = new ModelRenderer(this, 57, 17);
     this.bump10.addBox(-16.0F, 0.0F, -8.0F, 2, 2, 2);
     this.bump10.setRotationPoint(0.0F, 11.0F, -7.0F);

     this.bump10.mirror = true;
     setRotation(this.bump10, -0.2617994F, 0.0F, 0.0F);
     this.bump11 = new ModelRenderer(this, 57, 17);
     this.bump11.addBox(12.0F, -22.0F, -8.0F, 2, 2, 2);
     this.bump11.setRotationPoint(0.0F, 11.0F, -7.0F);

     this.bump11.mirror = true;
     setRotation(this.bump11, -0.2617994F, 0.0F, 0.0F);
     this.bump12 = new ModelRenderer(this, 57, 17);
     this.bump12.addBox(7.0F, -22.0F, -8.0F, 2, 2, 2);
     this.bump12.setRotationPoint(0.0F, 11.0F, -7.0F);

     this.bump12.mirror = true;
     setRotation(this.bump12, -0.2617994F, 0.0F, 0.0F);
     this.bump13 = new ModelRenderer(this, 57, 17);
     this.bump13.addBox(2.0F, -22.0F, -8.0F, 2, 2, 2);
     this.bump13.setRotationPoint(0.0F, 11.0F, -7.0F);

     this.bump13.mirror = true;
     setRotation(this.bump13, -0.2617994F, 0.0F, 0.0F);
     this.bump14 = new ModelRenderer(this, 57, 17);
     this.bump14.addBox(-4.0F, -22.0F, -8.0F, 2, 2, 2);
     this.bump14.setRotationPoint(0.0F, 11.0F, -7.0F);

     this.bump14.mirror = true;
     setRotation(this.bump14, -0.2617994F, 0.0F, 0.0F);
     this.bump15 = new ModelRenderer(this, 57, 17);
     this.bump15.addBox(-9.0F, -22.0F, -8.0F, 2, 2, 2);
     this.bump15.setRotationPoint(0.0F, 11.0F, -7.0F);

     this.bump15.mirror = true;
     setRotation(this.bump15, -0.2617994F, 0.0F, 0.0F);
     this.bump16 = new ModelRenderer(this, 57, 17);
     this.bump16.addBox(-14.0F, -22.0F, -8.0F, 2, 2, 2);
     this.bump16.setRotationPoint(0.0F, 11.0F, -7.0F);

     this.bump16.mirror = true;
     setRotation(this.bump16, -0.2617994F, 0.0F, 0.0F);
   }

   public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay,
        float red, float green, float blue, float alpha) {
        lfleg.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rfleg.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        lrleg.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rrleg.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        body1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        head.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        body2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        body3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        guard.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        nose.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        lhorn.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rhorn.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        chorn.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        tail.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        bump1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        bump2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        bump3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        bump4.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        bump5.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        bump6.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        bump7.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        bump8.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        bump9.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        bump10.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        bump11.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        bump12.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        bump13.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        bump14.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        bump15.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        bump16.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }

   private void setRotation(ModelRenderer model, float x, float y, float z) {
     model.rotateAngleX = x;
     model.rotateAngleY = y;
     model.rotateAngleZ = z;
   }

   public void setRotationAngles(Entity entity, float limbSwing, float limbSwingAmount,
        float ageInTicks, float netHeadYaw, float headPitch) {
// Reset shared model state before applying this frame's legacy animation.
this.lfleg.setRotationPoint(9.0F, 16.0F, -8.0F);
this.rfleg.setRotationPoint(-9.0F, 16.0F, -8.0F);
this.lrleg.setRotationPoint(9.0F, 16.0F, 12.0F);
this.rrleg.setRotationPoint(-9.0F, 16.0F, 12.0F);
this.body1.setRotationPoint(-7.0F, 9.0F, -12.0F);
this.head.setRotationPoint(0.0F, 11.0F, -7.0F);
this.body2.setRotationPoint(0.0F, 2.0F, -9.0F);
this.body3.setRotationPoint(0.0F, 3.0F, 6.0F);
this.guard.setRotationPoint(0.0F, 11.0F, -7.0F);
this.nose.setRotationPoint(0.0F, 11.0F, -7.0F);
this.lhorn.setRotationPoint(0.0F, 11.0F, -7.0F);
this.rhorn.setRotationPoint(0.0F, 11.0F, -7.0F);
this.chorn.setRotationPoint(0.0F, 11.0F, -7.0F);
this.tail.setRotationPoint(0.0F, 7.0F, 15.0F);
this.bump1.setRotationPoint(0.0F, 11.0F, -7.0F);
this.bump2.setRotationPoint(0.0F, 11.0F, -7.0F);
this.bump3.setRotationPoint(0.0F, 11.0F, -7.0F);
this.bump4.setRotationPoint(0.0F, 11.0F, -7.0F);
this.bump5.setRotationPoint(0.0F, 11.0F, -7.0F);
this.bump6.setRotationPoint(0.0F, 11.0F, -7.0F);
this.bump7.setRotationPoint(0.0F, 11.0F, -7.0F);
this.bump8.setRotationPoint(0.0F, 11.0F, -7.0F);
this.bump9.setRotationPoint(0.0F, 11.0F, -7.0F);
this.bump10.setRotationPoint(0.0F, 11.0F, -7.0F);
this.bump11.setRotationPoint(0.0F, 11.0F, -7.0F);
this.bump12.setRotationPoint(0.0F, 11.0F, -7.0F);
this.bump13.setRotationPoint(0.0F, 11.0F, -7.0F);
this.bump14.setRotationPoint(0.0F, 11.0F, -7.0F);
this.bump15.setRotationPoint(0.0F, 11.0F, -7.0F);
this.bump16.setRotationPoint(0.0F, 11.0F, -7.0F);
setRotation(this.lfleg, 0.0F, 0.0F, 0.0F);
setRotation(this.rfleg, 0.0F, 0.0F, 0.0F);
setRotation(this.lrleg, 0.0F, 0.0F, 0.0F);
setRotation(this.rrleg, 0.0F, 0.0F, 0.0F);
setRotation(this.body1, 0.0F, 0.0F, 0.0F);
setRotation(this.head, -0.1919862F, 0.0F, 0.0F);
setRotation(this.body2, 0.0F, 0.0F, 0.0F);
setRotation(this.body3, 0.0F, 0.0F, 0.0F);
setRotation(this.guard, -0.2617994F, 0.0F, 0.0F);
setRotation(this.nose, 0.0F, 0.0F, 0.0F);
setRotation(this.lhorn, -0.1570796F, -0.1396263F, 0.0F);
setRotation(this.rhorn, -0.1570796F, 0.1396263F, 0.0F);
setRotation(this.chorn, 0.0F, 0.0F, 0.0F);
setRotation(this.tail, 0.2792527F, 0.0F, 0.0F);
setRotation(this.bump1, -0.2617994F, 0.0F, 0.0F);
setRotation(this.bump2, -0.2617994F, 0.0F, 0.0F);
setRotation(this.bump3, -0.2617994F, 0.0F, 0.0F);
setRotation(this.bump4, -0.2617994F, 0.0F, 0.0F);
setRotation(this.bump5, -0.2617994F, 0.0F, 0.0F);
setRotation(this.bump6, -0.2617994F, 0.0F, 0.0F);
setRotation(this.bump7, -0.2617994F, 0.0F, 0.0F);
setRotation(this.bump8, -0.2617994F, 0.0F, 0.0F);
setRotation(this.bump9, -0.2617994F, 0.0F, 0.0F);
setRotation(this.bump10, -0.2617994F, 0.0F, 0.0F);
setRotation(this.bump11, -0.2617994F, 0.0F, 0.0F);
setRotation(this.bump12, -0.2617994F, 0.0F, 0.0F);
setRotation(this.bump13, -0.2617994F, 0.0F, 0.0F);
setRotation(this.bump14, -0.2617994F, 0.0F, 0.0F);
setRotation(this.bump15, -0.2617994F, 0.0F, 0.0F);
setRotation(this.bump16, -0.2617994F, 0.0F, 0.0F);

     PointysaurusEntity.CustomEntity e = (PointysaurusEntity.CustomEntity) entity;

     float newangle = 0.0F;
     
     if (limbSwingAmount > 0.1D) {
       newangle = MathHelper.cos(ageInTicks * 1.3F * this.wingspeed) * 3.1415927F * 0.25F * limbSwingAmount;
     } else {
       newangle = 0.0F;
     } 
     
     this.lfleg.rotateAngleX = newangle;
     this.rrleg.rotateAngleX = newangle;
     this.rfleg.rotateAngleX = -newangle;
     this.lrleg.rotateAngleX = -newangle;
     
     this.head.rotateAngleY = (float)Math.toRadians(netHeadYaw) * 0.45F;
     this.nose.rotateAngleY = this.head.rotateAngleY;
     this.chorn.rotateAngleY = this.head.rotateAngleY;
     this.head.rotateAngleY -= 0.14F;
     this.head.rotateAngleY += 0.14F;
     this.guard.rotateAngleY = this.head.rotateAngleY;
     this.bump1.rotateAngleY = this.head.rotateAngleY;
     this.bump2.rotateAngleY = this.head.rotateAngleY;
     this.bump3.rotateAngleY = this.head.rotateAngleY;
     this.bump4.rotateAngleY = this.head.rotateAngleY;
     this.bump5.rotateAngleY = this.head.rotateAngleY;
     this.bump6.rotateAngleY = this.head.rotateAngleY;
     this.bump7.rotateAngleY = this.head.rotateAngleY;
     this.bump8.rotateAngleY = this.head.rotateAngleY;
     this.bump9.rotateAngleY = this.head.rotateAngleY;
     this.bump10.rotateAngleY = this.head.rotateAngleY;
     this.bump11.rotateAngleY = this.head.rotateAngleY;
     this.bump12.rotateAngleY = this.head.rotateAngleY;
     this.bump13.rotateAngleY = this.head.rotateAngleY;
     this.bump14.rotateAngleY = this.head.rotateAngleY;
     this.bump15.rotateAngleY = this.head.rotateAngleY;
     this.bump16.rotateAngleY = this.head.rotateAngleY;
     
     this.head.rotateAngleX = (float)Math.toRadians(headPitch) * 0.45F;
     this.nose.rotateAngleX = this.head.rotateAngleX;
     this.chorn.rotateAngleX = this.head.rotateAngleX;
     this.head.rotateAngleX -= 0.16F;
     this.head.rotateAngleX -= 0.16F;
     this.head.rotateAngleX -= 0.262F;
     this.bump1.rotateAngleX = this.guard.rotateAngleX;
     this.bump2.rotateAngleX = this.guard.rotateAngleX;
     this.bump3.rotateAngleX = this.guard.rotateAngleX;
     this.bump4.rotateAngleX = this.guard.rotateAngleX;
     this.bump5.rotateAngleX = this.guard.rotateAngleX;
     this.bump6.rotateAngleX = this.guard.rotateAngleX;
     this.bump7.rotateAngleX = this.guard.rotateAngleX;
     this.bump8.rotateAngleX = this.guard.rotateAngleX;
     this.bump9.rotateAngleX = this.guard.rotateAngleX;
     this.bump10.rotateAngleX = this.guard.rotateAngleX;
     this.bump11.rotateAngleX = this.guard.rotateAngleX;
     this.bump12.rotateAngleX = this.guard.rotateAngleX;
     this.bump13.rotateAngleX = this.guard.rotateAngleX;
     this.bump14.rotateAngleX = this.guard.rotateAngleX;
     this.bump15.rotateAngleX = this.guard.rotateAngleX;
     this.bump16.rotateAngleX = this.guard.rotateAngleX;
     this.lhorn.rotateAngleY = this.head.rotateAngleY - 0.1396263F;
     this.rhorn.rotateAngleY = this.head.rotateAngleY + 0.1396263F;
     this.lhorn.rotateAngleX = this.head.rotateAngleX + 0.0349066F;
     this.rhorn.rotateAngleX = this.head.rotateAngleX + 0.0349066F;
     
     if (e.getAttacking() != 0) {
       newangle = MathHelper.cos(ageInTicks * 1.3F * this.wingspeed) * 3.1415927F * 0.25F;
     } else {
       newangle = MathHelper.cos(ageInTicks * 0.3F * this.wingspeed) * 3.1415927F * 0.05F;
     } 
     this.tail.rotateAngleY = newangle;
     
     newangle = MathHelper.cos(ageInTicks * 0.02F * this.wingspeed) * 3.1415927F * 0.15F;
     this.tail.rotateAngleX = newangle + 0.28F;
    }
 }
}
