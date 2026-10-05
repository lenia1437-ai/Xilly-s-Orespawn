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
import net.mcreator.xillysorespawn.entity.RatEntity;
import net.mcreator.xillysorespawn.entity.OreSpawnLogic;

@OnlyIn(Dist.CLIENT)
public class RatRenderer {
    private static final String TEXTURE = "xillys_orespawn:textures/entities/rattexture.png";
    public static class ModelRegisterHandler {
        @SubscribeEvent @OnlyIn(Dist.CLIENT)
        public void registerModels(ModelRegistryEvent event) {
            RenderingRegistry.registerEntityRenderingHandler(RatEntity.entity, manager ->
                new MobRenderer(manager, new ModelRat(1.0F), 0.075F) {
                    @Override public ResourceLocation getEntityTexture(Entity entity) { return new ResourceLocation(TEXTURE); }
                    @Override protected void preRenderCallback(LivingEntity entity, MatrixStack stack, float partialTick) {
                        float modelScale = 0.75F;
                        stack.scale(modelScale, modelScale, modelScale);
                    }
                });
        }
    }

public static class ModelRat extends EntityModel<Entity> {
   private float wingspeed = 1.0F;

   private final ModelRenderer body;

   private final ModelRenderer tail1;

   private final ModelRenderer tail2;

   private final ModelRenderer lfleg;
   private final ModelRenderer rfleg;
   private final ModelRenderer lrleg;
   private final ModelRenderer rrleg;
   private final ModelRenderer body2;
   private final ModelRenderer head;
   private final ModelRenderer nose;
   private final ModelRenderer lear;
   private final ModelRenderer rear;

   public ModelRat(float f1) {
     this.wingspeed = f1;

     this.textureWidth = 64;
     this.textureHeight = 64;

     this.body = new ModelRenderer(this, 27, 0);
     this.body.addBox(-2.0F, -1.0F, 0.0F, 5, 3, 10);
     this.body.setRotationPoint(0.0F, 20.0F, -3.0F);

     this.body.mirror = true;
     setRotation(this.body, 0.0F, 0.0F, 0.0F);
     this.tail1 = new ModelRenderer(this, 0, 30);
     this.tail1.addBox(-0.5F, -1.0F, 0.0F, 2, 2, 9);
     this.tail1.setRotationPoint(0.0F, 21.0F, 7.0F);

     this.tail1.mirror = true;
     setRotation(this.tail1, 0.0F, 0.0F, 0.0F);
     this.tail2 = new ModelRenderer(this, 0, 43);
     this.tail2.addBox(0.0F, 0.0F, 0.0F, 1, 1, 12);
     this.tail2.setRotationPoint(0.0F, 21.0F, 16.0F);

     this.tail2.mirror = true;
     setRotation(this.tail2, 0.0F, 0.0F, 0.0F);
     this.lfleg = new ModelRenderer(this, 0, 14);
     this.lfleg.addBox(0.0F, 0.0F, 0.0F, 1, 2, 1);
     this.lfleg.setRotationPoint(2.0F, 22.0F, -2.0F);

     this.lfleg.mirror = true;
     setRotation(this.lfleg, 0.0F, 0.0F, 0.0F);
     this.rfleg = new ModelRenderer(this, 10, 14);
     this.rfleg.addBox(0.0F, 0.0F, 0.0F, 1, 2, 1);
     this.rfleg.setRotationPoint(-2.0F, 22.0F, -2.0F);

     this.rfleg.mirror = true;
     setRotation(this.rfleg, 0.0F, 0.0F, 0.0F);
     this.lrleg = new ModelRenderer(this, 0, 18);
     this.lrleg.addBox(0.0F, 0.0F, 0.0F, 2, 4, 2);
     this.lrleg.setRotationPoint(2.0F, 20.0F, 4.0F);

     this.lrleg.mirror = true;
     setRotation(this.lrleg, 0.0F, 0.0F, 0.0F);
     this.rrleg = new ModelRenderer(this, 9, 18);
     this.rrleg.addBox(0.0F, 0.0F, 0.0F, 2, 4, 2);
     this.rrleg.setRotationPoint(-3.0F, 20.0F, 4.0F);

     this.rrleg.mirror = true;
     setRotation(this.rrleg, 0.0F, 0.0F, 0.0F);
     this.body2 = new ModelRenderer(this, 0, 0);
     this.body2.addBox(0.0F, 0.0F, 0.0F, 1, 1, 6);
     this.body2.setRotationPoint(0.0F, 18.0F, 0.0F);

     this.body2.mirror = true;
     setRotation(this.body2, 0.0F, 0.0F, 0.0F);
     this.head = new ModelRenderer(this, 27, 17);
     this.head.addBox(-1.0F, -2.0F, -3.0F, 3, 2, 4);
     this.head.setRotationPoint(0.0F, 22.0F, -4.0F);

     this.head.mirror = true;
     setRotation(this.head, 0.0F, 0.0F, 0.0F);
     this.nose = new ModelRenderer(this, 27, 25);
     this.nose.addBox(0.0F, -1.0F, -5.0F, 1, 1, 2);
     this.nose.setRotationPoint(0.0F, 22.0F, -4.0F);

     this.nose.mirror = true;
     setRotation(this.nose, 0.0F, 0.0F, 0.0F);
     this.lear = new ModelRenderer(this, 0, 9);
     this.lear.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1);
     this.lear.setRotationPoint(1.5F, 19.5F, -4.0F);

     this.lear.mirror = true;
     setRotation(this.lear, 0.0F, 0.0F, 0.0F);
     this.rear = new ModelRenderer(this, 5, 9);
     this.rear.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1);
     this.rear.setRotationPoint(-1.5F, 19.5F, -4.0F);

     this.rear.mirror = true;
     setRotation(this.rear, 0.0F, 0.0F, 0.0F);
   }

   public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay,
        float red, float green, float blue, float alpha) {
        body.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        tail1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        tail2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        lfleg.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rfleg.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        lrleg.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rrleg.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        body2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        head.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        nose.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        lear.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rear.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }

   private void setRotation(ModelRenderer model, float x, float y, float z) {
     model.rotateAngleX = x;
     model.rotateAngleY = y;
     model.rotateAngleZ = z;
   }

   public void setRotationAngles(Entity entity, float limbSwing, float limbSwingAmount,
        float ageInTicks, float netHeadYaw, float headPitch) {
// Reset shared model state before applying this frame's legacy animation.
this.body.setRotationPoint(0.0F, 20.0F, -3.0F);
this.tail1.setRotationPoint(0.0F, 21.0F, 7.0F);
this.tail2.setRotationPoint(0.0F, 21.0F, 16.0F);
this.lfleg.setRotationPoint(2.0F, 22.0F, -2.0F);
this.rfleg.setRotationPoint(-2.0F, 22.0F, -2.0F);
this.lrleg.setRotationPoint(2.0F, 20.0F, 4.0F);
this.rrleg.setRotationPoint(-3.0F, 20.0F, 4.0F);
this.body2.setRotationPoint(0.0F, 18.0F, 0.0F);
this.head.setRotationPoint(0.0F, 22.0F, -4.0F);
this.nose.setRotationPoint(0.0F, 22.0F, -4.0F);
this.lear.setRotationPoint(1.5F, 19.5F, -4.0F);
this.rear.setRotationPoint(-1.5F, 19.5F, -4.0F);
setRotation(this.body, 0.0F, 0.0F, 0.0F);
setRotation(this.tail1, 0.0F, 0.0F, 0.0F);
setRotation(this.tail2, 0.0F, 0.0F, 0.0F);
setRotation(this.lfleg, 0.0F, 0.0F, 0.0F);
setRotation(this.rfleg, 0.0F, 0.0F, 0.0F);
setRotation(this.lrleg, 0.0F, 0.0F, 0.0F);
setRotation(this.rrleg, 0.0F, 0.0F, 0.0F);
setRotation(this.body2, 0.0F, 0.0F, 0.0F);
setRotation(this.head, 0.0F, 0.0F, 0.0F);
setRotation(this.nose, 0.0F, 0.0F, 0.0F);
setRotation(this.lear, 0.0F, 0.0F, 0.0F);
setRotation(this.rear, 0.0F, 0.0F, 0.0F);

     RatEntity.CustomEntity r = (RatEntity.CustomEntity) entity;

     float newangle = 0.0F;
     
     if (limbSwingAmount > 0.1D) {
       newangle = MathHelper.cos(ageInTicks * 1.7F * this.wingspeed) * 3.1415927F * 0.25F * limbSwingAmount;
     } else {
       newangle = 0.0F;
     } 
     
     this.rfleg.rotateAngleX = newangle;
     this.lfleg.rotateAngleX = -newangle;
     this.rrleg.rotateAngleX = -newangle;
     this.lrleg.rotateAngleX = newangle;
     
     if (r.getAttacking() != 0) {
       newangle = MathHelper.cos(ageInTicks * 1.5F * this.wingspeed) * 3.1415927F * 0.25F;
     } else {
       newangle = MathHelper.cos(ageInTicks * 0.4F * this.wingspeed) * 3.1415927F * 0.05F;
     } 
     this.tail1.rotateAngleY = newangle * 0.5F;
     this.tail2.rotateAngleY = newangle * 1.25F;
     this.tail2.rotationPointZ = this.tail1.rotationPointZ + (float)Math.cos(this.tail1.rotateAngleY) * 9.0F;
     this.tail2.rotationPointX = this.tail1.rotationPointX + (float)Math.sin(this.tail1.rotateAngleY) * 9.0F;
     this.tail2.rotationPointY = this.tail1.rotationPointY;
    }
 }
}
