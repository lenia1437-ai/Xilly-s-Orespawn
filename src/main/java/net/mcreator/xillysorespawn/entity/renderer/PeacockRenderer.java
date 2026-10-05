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
import net.mcreator.xillysorespawn.entity.PeacockEntity;
import net.mcreator.xillysorespawn.entity.OreSpawnLogic;

@OnlyIn(Dist.CLIENT)
public class PeacockRenderer {
    private static final String TEXTURE = "xillys_orespawn:textures/entities/peacocktexture.png";
    public static class ModelRegisterHandler {
        @SubscribeEvent @OnlyIn(Dist.CLIENT)
        public void registerModels(ModelRegistryEvent event) {
            RenderingRegistry.registerEntityRenderingHandler(PeacockEntity.entity, manager ->
                new MobRenderer(manager, new ModelPeacock(0.75F), 0.25F) {
                    @Override public ResourceLocation getEntityTexture(Entity entity) { return new ResourceLocation(TEXTURE); }
                    @Override protected void preRenderCallback(LivingEntity entity, MatrixStack stack, float partialTick) {
                        float modelScale = 1.0F;
                        if (entity.isChild()) modelScale *= 0.5F;
                        stack.scale(modelScale, modelScale, modelScale);
                    }
                });
        }
    }

public static class ModelPeacock extends EntityModel<Entity>
 {
   private float wingspeed = 1.0F;

   private final ModelRenderer lleg;
   private final ModelRenderer rleg;
   private final ModelRenderer body;
   private final ModelRenderer neck;
   private final ModelRenderer head1;
   private final ModelRenderer head2;
   private final ModelRenderer hf1;
   private final ModelRenderer hf2;
   private final ModelRenderer hf3;
   private final ModelRenderer tailf1;
   private final ModelRenderer tailf2;
   private final ModelRenderer tailf3;
   private final ModelRenderer tailf4;
   private final ModelRenderer tailf5;
   private final ModelRenderer tailf6;
   private final ModelRenderer tailf7;

   public ModelPeacock(float f1) {
     this.wingspeed = f1;

     this.textureWidth = 128;
     this.textureHeight = 128;

     this.lleg = new ModelRenderer(this, 0, 20);
     this.lleg.addBox(0.0F, 0.0F, 0.0F, 1, 7, 1);
     this.lleg.setRotationPoint(1.0F, 17.0F, 0.0F);

     this.lleg.mirror = true;
     setRotation(this.lleg, 0.0F, 0.0F, 0.0F);
     this.rleg = new ModelRenderer(this, 5, 20);
     this.rleg.addBox(0.0F, 0.0F, 0.0F, 1, 7, 1);
     this.rleg.setRotationPoint(-1.0F, 17.0F, 0.0F);

     this.rleg.mirror = true;
     setRotation(this.rleg, 0.0F, 0.0F, 0.0F);
     this.body = new ModelRenderer(this, 88, 0);
     this.body.addBox(-2.0F, -2.0F, -5.0F, 5, 4, 11);
     this.body.setRotationPoint(0.0F, 15.0F, 1.0F);

     this.body.mirror = true;
     setRotation(this.body, -0.1396263F, 0.0F, 0.0F);
     this.neck = new ModelRenderer(this, 70, 0);
     this.neck.addBox(-0.5F, -1.0F, -6.0F, 2, 2, 6);
     this.neck.setRotationPoint(0.0F, 14.0F, -3.0F);

     this.neck.mirror = true;
     setRotation(this.neck, -0.5585054F, 0.0F, 0.0F);
     this.head1 = new ModelRenderer(this, 56, 0);
     this.head1.addBox(-0.5F, -2.0F, -2.0F, 2, 2, 4);
     this.head1.setRotationPoint(0.0F, 12.0F, -8.0F);

     this.head1.mirror = true;
     setRotation(this.head1, 0.0F, 0.0F, 0.0F);
     this.head2 = new ModelRenderer(this, 48, 0);
     this.head2.addBox(0.0F, -1.0F, -4.0F, 1, 1, 2);
     this.head2.setRotationPoint(0.0F, 12.0F, -8.0F);

     this.head2.mirror = true;
     setRotation(this.head2, 0.0F, 0.0F, 0.0F);
     this.hf1 = new ModelRenderer(this, 8, 0);
     this.hf1.addBox(0.5F, -9.0F, -1.5F, 0, 7, 3);
     this.hf1.setRotationPoint(0.0F, 12.0F, -8.0F);

     this.hf1.mirror = true;
     setRotation(this.hf1, 0.4014257F, 0.0F, 0.0F);
     this.hf2 = new ModelRenderer(this, 8, 0);
     this.hf2.addBox(0.5F, -9.0F, -1.5F, 0, 7, 3);
     this.hf2.setRotationPoint(0.0F, 12.0F, -8.0F);

     this.hf2.mirror = true;
     setRotation(this.hf2, -0.1745329F, 0.0F, 0.0F);
     this.hf3 = new ModelRenderer(this, 8, 0);
     this.hf3.addBox(0.5F, -9.0F, -1.5F, 0, 7, 3);
     this.hf3.setRotationPoint(0.0F, 12.0F, -8.0F);

     this.hf3.mirror = true;
     setRotation(this.hf3, -0.6981317F, 0.0F, 0.0F);
     this.tailf1 = new ModelRenderer(this, 0, 50);
     this.tailf1.addBox(-4.0F, 0.0F, 0.0F, 8, 0, 30);
     this.tailf1.setRotationPoint(0.5F, 14.0F, 7.0F);

     this.tailf1.mirror = true;
     setRotation(this.tailf1, 0.0F, 0.0F, 0.0F);
     this.tailf2 = new ModelRenderer(this, 0, 50);
     this.tailf2.addBox(-4.0F, 0.0F, 0.0F, 8, 0, 30);
     this.tailf2.setRotationPoint(0.5F, 14.0F, 7.0F);

     this.tailf2.mirror = true;
     setRotation(this.tailf2, 0.0F, 0.0F, 0.0F);
     this.tailf3 = new ModelRenderer(this, 0, 50);
     this.tailf3.addBox(-4.0F, 0.0F, 0.0F, 8, 0, 30);
     this.tailf3.setRotationPoint(0.5F, 14.0F, 7.0F);

     this.tailf3.mirror = true;
     setRotation(this.tailf3, 0.0F, 0.0F, 0.0F);
     this.tailf4 = new ModelRenderer(this, 0, 50);
     this.tailf4.addBox(-4.0F, 0.0F, 0.0F, 8, 0, 30);
     this.tailf4.setRotationPoint(0.5F, 14.0F, 7.0F);

     this.tailf4.mirror = true;
     setRotation(this.tailf4, 0.0F, 0.0F, 0.0F);
     this.tailf5 = new ModelRenderer(this, 0, 50);
     this.tailf5.addBox(-4.0F, 0.0F, 0.0F, 8, 0, 30);
     this.tailf5.setRotationPoint(0.5F, 14.0F, 7.0F);

     this.tailf5.mirror = true;
     setRotation(this.tailf5, 0.0F, 0.0F, 0.0F);
     this.tailf6 = new ModelRenderer(this, 0, 50);
     this.tailf6.addBox(-4.0F, 0.0F, 0.0F, 8, 0, 30);
     this.tailf6.setRotationPoint(0.5F, 14.0F, 7.0F);

     this.tailf6.mirror = true;
     setRotation(this.tailf6, 0.0F, 0.0F, 0.0F);
     this.tailf7 = new ModelRenderer(this, 0, 50);
     this.tailf7.addBox(-4.0F, 0.0F, 0.0F, 8, 0, 30);
     this.tailf7.setRotationPoint(0.514F, 14.0F, 7.0F);

     this.tailf7.mirror = true;
     setRotation(this.tailf7, 0.0F, 0.0F, 0.0F);
   }

   public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay,
        float red, float green, float blue, float alpha) {
        lleg.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rleg.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        body.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        neck.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        head1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        head2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        hf1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        hf2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        hf3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        tailf1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        tailf2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        tailf3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        tailf4.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        tailf5.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        tailf6.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        tailf7.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }

   private void setRotation(ModelRenderer model, float x, float y, float z) {
     model.rotateAngleX = x;
     model.rotateAngleY = y;
     model.rotateAngleZ = z;
   }

   public void setRotationAngles(Entity entity, float limbSwing, float limbSwingAmount,
        float ageInTicks, float netHeadYaw, float headPitch) {
// Reset shared model state before applying this frame's legacy animation.
this.lleg.setRotationPoint(1.0F, 17.0F, 0.0F);
this.rleg.setRotationPoint(-1.0F, 17.0F, 0.0F);
this.body.setRotationPoint(0.0F, 15.0F, 1.0F);
this.neck.setRotationPoint(0.0F, 14.0F, -3.0F);
this.head1.setRotationPoint(0.0F, 12.0F, -8.0F);
this.head2.setRotationPoint(0.0F, 12.0F, -8.0F);
this.hf1.setRotationPoint(0.0F, 12.0F, -8.0F);
this.hf2.setRotationPoint(0.0F, 12.0F, -8.0F);
this.hf3.setRotationPoint(0.0F, 12.0F, -8.0F);
this.tailf1.setRotationPoint(0.5F, 14.0F, 7.0F);
this.tailf2.setRotationPoint(0.5F, 14.0F, 7.0F);
this.tailf3.setRotationPoint(0.5F, 14.0F, 7.0F);
this.tailf4.setRotationPoint(0.5F, 14.0F, 7.0F);
this.tailf5.setRotationPoint(0.5F, 14.0F, 7.0F);
this.tailf6.setRotationPoint(0.5F, 14.0F, 7.0F);
this.tailf7.setRotationPoint(0.514F, 14.0F, 7.0F);
setRotation(this.lleg, 0.0F, 0.0F, 0.0F);
setRotation(this.rleg, 0.0F, 0.0F, 0.0F);
setRotation(this.body, -0.1396263F, 0.0F, 0.0F);
setRotation(this.neck, -0.5585054F, 0.0F, 0.0F);
setRotation(this.head1, 0.0F, 0.0F, 0.0F);
setRotation(this.head2, 0.0F, 0.0F, 0.0F);
setRotation(this.hf1, 0.4014257F, 0.0F, 0.0F);
setRotation(this.hf2, -0.1745329F, 0.0F, 0.0F);
setRotation(this.hf3, -0.6981317F, 0.0F, 0.0F);
setRotation(this.tailf1, 0.0F, 0.0F, 0.0F);
setRotation(this.tailf2, 0.0F, 0.0F, 0.0F);
setRotation(this.tailf3, 0.0F, 0.0F, 0.0F);
setRotation(this.tailf4, 0.0F, 0.0F, 0.0F);
setRotation(this.tailf5, 0.0F, 0.0F, 0.0F);
setRotation(this.tailf6, 0.0F, 0.0F, 0.0F);
setRotation(this.tailf7, 0.0F, 0.0F, 0.0F);

     PeacockEntity.CustomEntity p = (PeacockEntity.CustomEntity) entity;

     float newangle = 0.0F;
 
     
     if (limbSwingAmount > 0.1D) {
       newangle = MathHelper.cos(ageInTicks * 1.3F * this.wingspeed) * 3.1415927F * 0.15F * limbSwingAmount;
     } else {
       newangle = 0.0F;
     } 
     
     this.lleg.rotateAngleX = newangle;
     this.rleg.rotateAngleX = -newangle;
     
     if (p.getBlink() > 0) {
       this.hf1.rotateAngleX = 0.401F;
       this.hf2.rotateAngleX = -0.174F;
       this.hf3.rotateAngleX = -0.698F;
       this.tailf1.rotateAngleX = 1.047F;
       this.tailf2.rotateAngleX = 1.047F;
       this.tailf3.rotateAngleX = 1.047F;
       this.tailf4.rotateAngleX = 1.047F;
       this.tailf5.rotateAngleX = 1.047F;
       this.tailf6.rotateAngleX = 1.047F;
       this.tailf7.rotateAngleX = 1.047F;
       
       this.tailf1.rotateAngleZ = -0.4F;
       this.tailf2.rotateAngleZ = -0.8F;
       this.tailf3.rotateAngleZ = -1.2F;
       this.tailf4.rotateAngleZ = 0.4F;
       this.tailf5.rotateAngleZ = 0.8F;
       this.tailf6.rotateAngleZ = 1.2F;
     } else {
       this.hf1.rotateAngleX = -1.06F;
       this.hf2.rotateAngleX = -1.06F;
       this.hf3.rotateAngleX = -1.06F;
       this.tailf1.rotateAngleX = 0.0F;
       this.tailf2.rotateAngleX = 0.0F;
       this.tailf3.rotateAngleX = 0.0F;
       this.tailf4.rotateAngleX = 0.0F;
       this.tailf5.rotateAngleX = 0.0F;
       this.tailf6.rotateAngleX = 0.0F;
       this.tailf7.rotateAngleX = 0.0F;
       this.tailf1.rotateAngleZ = 0.0F;
       this.tailf2.rotateAngleZ = 0.0F;
       this.tailf3.rotateAngleZ = 0.0F;
       this.tailf4.rotateAngleZ = 0.0F;
       this.tailf5.rotateAngleZ = 0.0F;
       this.tailf6.rotateAngleZ = 0.0F;
     }
    }
 }
}