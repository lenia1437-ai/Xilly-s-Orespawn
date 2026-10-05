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
import net.mcreator.xillysorespawn.entity.ChipmunkEntity;

@OnlyIn(Dist.CLIENT)
public class ChipmunkRenderer {
    private static final String TEXTURE = "xillys_orespawn:textures/entities/chipmunktexture.png";
    public static class ModelRegisterHandler {
        @SubscribeEvent @OnlyIn(Dist.CLIENT)
        public void registerModels(ModelRegistryEvent event) {
            RenderingRegistry.registerEntityRenderingHandler(ChipmunkEntity.entity, manager ->
                new MobRenderer(manager, new ModelChipmunk(1.0F), 0.5F) {
                    @Override public ResourceLocation getEntityTexture(Entity entity) { return new ResourceLocation(TEXTURE); }
                });
        }
    }

public static class ModelChipmunk extends EntityModel<Entity> {
   private float wingspeed = 1.0F;

   private final ModelRenderer Cheek2;

   private final ModelRenderer Leg1;

   private final ModelRenderer Leg2;
   private final ModelRenderer Leg3;
   private final ModelRenderer Leg4;
   private final ModelRenderer Tail2;
   private final ModelRenderer Neck;
   private final ModelRenderer Head;
   private final ModelRenderer MouthUnder;
   private final ModelRenderer Cheek1;
   private final ModelRenderer Ear2;
   private final ModelRenderer Nose;
   private final ModelRenderer Ear1;
   private final ModelRenderer Body;
   private final ModelRenderer BodyTail;
   private final ModelRenderer Tail1;
   private final ModelRenderer Hat1;
   private final ModelRenderer Hat2;

   public ModelChipmunk(float f1) {
     this.wingspeed = f1;

     this.textureWidth = 64;
     this.textureHeight = 32;

     this.Cheek2 = new ModelRenderer(this, 14, 0);
     this.Cheek2.addBox(0.5F, -1.5F, -3.5F, 2, 2, 2);
     this.Cheek2.setRotationPoint(0.0F, 20.0F, -3.0F);

     this.Cheek2.mirror = true;
     setRotation(this.Cheek2, 0.0F, 0.0F, 0.0F);
     this.Leg1 = new ModelRenderer(this, 22, 7);
     this.Leg1.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1);
     this.Leg1.setRotationPoint(-2.0F, 23.0F, -4.0F);

     this.Leg1.mirror = true;
     setRotation(this.Leg1, 0.0F, 0.0F, 0.0F);
     this.Leg2 = new ModelRenderer(this, 22, 9);
     this.Leg2.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1);
     this.Leg2.setRotationPoint(1.0F, 23.0F, -4.0F);

     this.Leg2.mirror = true;
     setRotation(this.Leg2, 0.0F, 0.0F, 0.0F);
     this.Leg3 = new ModelRenderer(this, 22, 11);
     this.Leg3.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1);
     this.Leg3.setRotationPoint(1.0F, 23.0F, 0.0F);

     this.Leg3.mirror = true;
     setRotation(this.Leg3, 0.0F, 0.0F, 0.0F);
     this.Leg4 = new ModelRenderer(this, 22, 13);
     this.Leg4.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1);
     this.Leg4.setRotationPoint(-2.0F, 23.0F, 0.0F);

     this.Leg4.mirror = true;
     setRotation(this.Leg4, 0.0F, 0.0F, 0.0F);
     this.Tail2 = new ModelRenderer(this, 28, 15);
     this.Tail2.addBox(-0.5F, 1.0F, 2.5F, 3, 3, 4);
     this.Tail2.setRotationPoint(-1.0F, 20.0F, 1.0F);

     this.Tail2.mirror = true;
     setRotation(this.Tail2, 0.7662421F, 0.0F, 0.0F);
     this.Neck = new ModelRenderer(this, 26, 9);
     this.Neck.addBox(0.0F, 0.0F, 0.0F, 3, 2, 4);
     this.Neck.setRotationPoint(-1.5F, 22.0F, -5.0F);

     this.Neck.mirror = true;
     setRotation(this.Neck, 1.570796F, 0.0F, 0.0F);
     this.Head = new ModelRenderer(this, 0, 0);
     this.Head.addBox(-2.0F, -3.0F, 0.0F, 4, 4, 3);
     this.Head.setRotationPoint(0.0F, 20.0F, -3.0F);

     this.Head.mirror = true;
     setRotation(this.Head, 1.570796F, 0.0F, 0.0F);
     this.MouthUnder = new ModelRenderer(this, 20, 4);
     this.MouthUnder.addBox(-1.0F, -1.9F, -3.8F, 2, 2, 1);
     this.MouthUnder.setRotationPoint(0.0F, 20.0F, -3.0F);

     this.MouthUnder.mirror = true;
     setRotation(this.MouthUnder, 0.0F, 0.0F, 0.0F);
     this.Cheek1 = new ModelRenderer(this, 22, 0);
     this.Cheek1.addBox(-2.5F, -1.5F, -3.5F, 2, 2, 2);
     this.Cheek1.setRotationPoint(0.0F, 20.0F, -3.0F);

     this.Cheek1.mirror = true;
     setRotation(this.Cheek1, 0.0F, 0.0F, 0.0F);
     this.Ear2 = new ModelRenderer(this, 18, 11);
     this.Ear2.addBox(1.0F, 0.0F, 3.0F, 1, 1, 1);
     this.Ear2.setRotationPoint(0.0F, 20.0F, -3.0F);

     this.Ear2.mirror = true;
     setRotation(this.Ear2, 1.570796F, 0.0F, 0.0F);
     this.Nose = new ModelRenderer(this, 18, 7);
     this.Nose.addBox(-0.5F, -2.0F, -4.2F, 1, 1, 1);
     this.Nose.setRotationPoint(0.0F, 20.0F, -3.0F);

     this.Nose.mirror = true;
     setRotation(this.Nose, 0.0F, 0.0F, 0.0F);
     this.Ear1 = new ModelRenderer(this, 18, 9);
     this.Ear1.addBox(-2.0F, 0.0F, 3.0F, 1, 1, 1);
     this.Ear1.setRotationPoint(0.0F, 20.0F, -3.0F);

     this.Ear1.mirror = true;
     setRotation(this.Ear1, 1.570796F, 0.0F, 0.0F);
     this.Body = new ModelRenderer(this, 0, 7);
     this.Body.addBox(0.0F, 0.0F, 0.0F, 4, 3, 5);
     this.Body.setRotationPoint(-2.0F, 20.0F, -4.0F);

     this.Body.mirror = true;
     setRotation(this.Body, 0.0F, 0.0F, 0.0F);
     this.BodyTail = new ModelRenderer(this, 0, 15);
     this.BodyTail.addBox(0.0F, 0.0F, 0.0F, 5, 4, 3);
     this.BodyTail.setRotationPoint(-2.5F, 19.0F, -1.0F);

     this.BodyTail.mirror = true;
     setRotation(this.BodyTail, 0.0F, 0.0F, 0.0F);
     this.Tail1 = new ModelRenderer(this, 16, 15);
     this.Tail1.addBox(0.0F, 0.0F, 0.0F, 2, 2, 4);
     this.Tail1.setRotationPoint(-1.0F, 20.0F, 1.0F);

     this.Tail1.mirror = true;
     setRotation(this.Tail1, 0.3064968F, 0.0F, 0.0F);
     this.Hat1 = new ModelRenderer(this, 40, 0);
     this.Hat1.addBox(-2.5F, -4.0F, -4.0F, 5, 1, 5);
     this.Hat1.setRotationPoint(0.0F, 20.0F, -3.0F);

     this.Hat1.mirror = true;
     setRotation(this.Hat1, 0.0F, 0.0F, 0.0F);
     this.Hat2 = new ModelRenderer(this, 40, 0);
     this.Hat2.addBox(-2.0F, -6.0F, -3.0F, 4, 2, 4);
     this.Hat2.setRotationPoint(0.0F, 20.0F, -3.0F);

     this.Hat2.mirror = true;
     setRotation(this.Hat2, 0.0F, 0.0F, 0.0F);
   }

   public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay,
        float red, float green, float blue, float alpha) {
        Cheek2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Leg1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Leg2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Leg3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Leg4.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Tail2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Neck.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Head.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        MouthUnder.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Cheek1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Ear2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Nose.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Ear1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Body.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        BodyTail.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Tail1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Hat1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Hat2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }

   private void setRotation(ModelRenderer model, float x, float y, float z) {
     model.rotateAngleX = x;
     model.rotateAngleY = y;
     model.rotateAngleZ = z;
   }

   public void setRotationAngles(Entity entity, float limbSwing, float limbSwingAmount,
        float ageInTicks, float netHeadYaw, float headPitch) {
ChipmunkEntity.CustomEntity c = (ChipmunkEntity.CustomEntity) entity;
     float hf = 0.0F;
     float newangle = 0.0F;

     if (limbSwingAmount > 0.1D) {
       newangle = MathHelper.cos(ageInTicks * 2.3F * this.wingspeed) * 3.1415927F * 0.25F * limbSwingAmount;
     } else {
       newangle = 0.0F;
     } 
 
     
     this.Leg1.rotateAngleX = newangle;
     this.Leg3.rotateAngleX = newangle;
     this.Leg2.rotateAngleX = -newangle;
     this.Leg4.rotateAngleX = -newangle;
     
     this.Head.rotateAngleY = (float)Math.toRadians(netHeadYaw) * 0.45F;
     this.Nose.rotateAngleY = this.Head.rotateAngleY;
     this.Ear1.rotateAngleY = this.Head.rotateAngleY;
     this.Ear2.rotateAngleY = this.Head.rotateAngleY;
     this.MouthUnder.rotateAngleY = this.Head.rotateAngleY;
     this.Cheek1.rotateAngleY = this.Head.rotateAngleY;
     this.Cheek2.rotateAngleY = this.Head.rotateAngleY;
     this.Hat1.rotateAngleY = this.Head.rotateAngleY;
     this.Hat2.rotateAngleY = this.Head.rotateAngleY;
     this.Hat1.showModel = c.get_is_activated() != 0;
     this.Hat2.showModel = c.get_is_activated() > 1;
     
     if (!c.isChildModel()) {
       this.Tail1.rotateAngleX = 0.306F + MathHelper.cos(ageInTicks * 0.25F) * 3.1415927F * 0.06F;
       newangle = MathHelper.cos(ageInTicks * 1.3F * this.wingspeed) * 3.1415927F * 0.25F * limbSwingAmount;
       this.Tail1.rotateAngleX += newangle;
       this.Tail2.rotateAngleX = 0.306F + this.Tail1.rotateAngleX;
     } 


    }
 }
}

