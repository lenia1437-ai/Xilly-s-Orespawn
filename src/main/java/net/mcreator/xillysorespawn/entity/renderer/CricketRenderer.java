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
import net.mcreator.xillysorespawn.entity.CricketEntity;

@OnlyIn(Dist.CLIENT)
public class CricketRenderer {
    private static final String TEXTURE = "xillys_orespawn:textures/entities/crickettexture.png";
    public static class ModelRegisterHandler {
        @SubscribeEvent @OnlyIn(Dist.CLIENT)
        public void registerModels(ModelRegistryEvent event) {
            RenderingRegistry.registerEntityRenderingHandler(CricketEntity.entity, manager ->
                new MobRenderer(manager, new ModelCricket(1.0F), 0.5F) {
                    @Override public ResourceLocation getEntityTexture(Entity entity) { return new ResourceLocation(TEXTURE); }
                });
        }
    }

public static class ModelCricket extends EntityModel<Entity>
 {
   private float wingspeed = 1.0F;

   private final ModelRenderer body;

   private final ModelRenderer head;

   private final ModelRenderer abdomen;
   private final ModelRenderer lfleg;
   private final ModelRenderer lrleg;
   private final ModelRenderer rfleg;
   private final ModelRenderer rrleg;
   private final ModelRenderer lleg1;
   private final ModelRenderer rleg1;
   private final ModelRenderer lleg2;
   private final ModelRenderer rleg2;

   public ModelCricket(float f1) {
     this.wingspeed = f1;

     this.textureWidth = 64;
     this.textureHeight = 64;

     this.body = new ModelRenderer(this, 0, 25);
     this.body.addBox(-1.0F, -1.0F, -3.0F, 3, 3, 6);
     this.body.setRotationPoint(0.0F, 21.0F, 0.0F);

     this.body.mirror = true;
     setRotation(this.body, 0.0F, 0.0F, 0.0F);
     this.head = new ModelRenderer(this, 0, 17);
     this.head.addBox(-1.0F, -2.0F, -1.0F, 3, 4, 3);
     this.head.setRotationPoint(0.0F, 21.0F, -5.0F);

     this.head.mirror = true;
     setRotation(this.head, -0.1745329F, 0.0F, 0.0F);
     this.abdomen = new ModelRenderer(this, 0, 36);
     this.abdomen.addBox(-0.5F, -1.0F, 3.0F, 2, 2, 3);
     this.abdomen.setRotationPoint(0.0F, 21.0F, 0.0F);

     this.abdomen.mirror = true;
     setRotation(this.abdomen, 0.0F, 0.0F, 0.0F);
     this.lfleg = new ModelRenderer(this, 25, 0);
     this.lfleg.addBox(2.0F, 0.0F, 0.0F, 5, 1, 1);
     this.lfleg.setRotationPoint(0.0F, 21.0F, -2.0F);

     this.lfleg.mirror = true;
     setRotation(this.lfleg, 0.0F, 0.4712389F, 0.418879F);
     this.lrleg = new ModelRenderer(this, 23, 4);
     this.lrleg.addBox(1.0F, 0.0F, -2.0F, 6, 1, 1);
     this.lrleg.setRotationPoint(0.0F, 21.0F, 0.0F);

     this.lrleg.mirror = true;
     setRotation(this.lrleg, 0.0F, -0.296706F, 0.418879F);
     this.rfleg = new ModelRenderer(this, 25, 8);
     this.rfleg.addBox(-7.0F, 0.0F, 0.0F, 5, 1, 1);
     this.rfleg.setRotationPoint(1.0F, 21.0F, -2.0F);

     this.rfleg.mirror = true;
     setRotation(this.rfleg, 0.0F, -0.5410521F, -0.4363323F);
     this.rrleg = new ModelRenderer(this, 25, 12);
     this.rrleg.addBox(-7.0F, -1.0F, 0.0F, 5, 1, 1);
     this.rrleg.setRotationPoint(1.0F, 22.0F, -2.0F);

     this.rrleg.mirror = true;
     setRotation(this.rrleg, 0.0F, 0.3839724F, -0.418879F);
     this.lleg1 = new ModelRenderer(this, 40, 0);
     this.lleg1.addBox(-1.0F, -1.0F, 0.0F, 1, 2, 8);
     this.lleg1.setRotationPoint(2.0F, 22.0F, 0.0F);

     this.lleg1.mirror = true;
     setRotation(this.lleg1, 0.5585054F, 0.4363323F, 0.0F);
     this.rleg1 = new ModelRenderer(this, 40, 11);
     this.rleg1.addBox(0.0F, -1.0F, 0.0F, 1, 2, 8);
     this.rleg1.setRotationPoint(-1.0F, 22.0F, 0.0F);

     this.rleg1.mirror = true;
     setRotation(this.rleg1, 0.5585054F, -0.4363323F, 0.0F);
     this.lleg2 = new ModelRenderer(this, 21, 23);
     this.lleg2.addBox(-0.5F, -6.5F, 4.5F, 1, 1, 8);
     this.lleg2.setRotationPoint(2.0F, 22.0F, 0.0F);

     this.lleg2.mirror = true;
     setRotation(this.lleg2, -0.3665191F, 0.3490659F, 0.0F);
     this.rleg2 = new ModelRenderer(this, 21, 34);
     this.rleg2.addBox(-0.5F, -6.5F, 4.0F, 1, 1, 8);
     this.rleg2.setRotationPoint(-1.0F, 22.0F, 0.0F);

     this.rleg2.mirror = true;
     setRotation(this.rleg2, -0.3665191F, -0.3490659F, 0.0F);
   }

   public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay,
        float red, float green, float blue, float alpha) {
        body.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        head.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        abdomen.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        lfleg.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        lrleg.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rfleg.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rrleg.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        lleg1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rleg1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        lleg2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rleg2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }

   private void setRotation(ModelRenderer model, float x, float y, float z) {
     model.rotateAngleX = x;
     model.rotateAngleY = y;
     model.rotateAngleZ = z;
   }

   public void setRotationAngles(Entity entity, float limbSwing, float limbSwingAmount,
        float ageInTicks, float netHeadYaw, float headPitch) {
CricketEntity.CustomEntity c = (CricketEntity.CustomEntity) entity;

     float newangle = 0.0F;
     
     if (limbSwingAmount > 0.1D) {
       newangle = MathHelper.cos(ageInTicks * this.wingspeed) * 3.1415927F * 0.25F * limbSwingAmount;
     } else {
       newangle = 0.0F;
     } 
     
     this.lfleg.rotateAngleY = 0.47F + newangle;
     this.rfleg.rotateAngleY = -0.54F + newangle;
     this.lrleg.rotateAngleY = -0.296F - newangle;
     this.rrleg.rotateAngleY = 0.384F - newangle;
     
     if (c.getSinging() != 0) {
       newangle = MathHelper.cos(ageInTicks * 3.0F * this.wingspeed) * 3.1415927F * 0.25F;
       this.lleg1.rotateAngleY = -0.035F;
       this.lleg2.rotateAngleY = -0.105F;
       this.rleg1.rotateAngleY = 0.035F;
       this.rleg2.rotateAngleY = 0.105F;
     } else {
       newangle = 0.0F;
       this.lleg1.rotateAngleY = 0.436F;
       this.lleg2.rotateAngleY = 0.349F;
       this.rleg1.rotateAngleY = -0.436F;
       this.rleg2.rotateAngleY = -0.349F;
     } 
     this.lleg1.rotateAngleX = newangle + 0.558F;
     this.lleg2.rotateAngleX = newangle - 0.366F;
     this.rleg1.rotateAngleX = -newangle + 0.558F;
     this.rleg2.rotateAngleX = -newangle - 0.366F;
    }
 }
}

