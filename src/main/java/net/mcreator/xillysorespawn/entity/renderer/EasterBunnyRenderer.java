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
import net.mcreator.xillysorespawn.entity.EasterBunnyEntity;

@OnlyIn(Dist.CLIENT)
public class EasterBunnyRenderer {
    private static final String TEXTURE = "xillys_orespawn:textures/entities/easterbunnytexture.png";
    public static class ModelRegisterHandler {
        @SubscribeEvent @OnlyIn(Dist.CLIENT)
        public void registerModels(ModelRegistryEvent event) {
            RenderingRegistry.registerEntityRenderingHandler(EasterBunnyEntity.entity, manager ->
                new MobRenderer(manager, new ModelEasterBunny(1.0F), 0.5F) {
                    @Override public ResourceLocation getEntityTexture(Entity entity) { return new ResourceLocation(TEXTURE); }
                });
        }
    }

public static class ModelEasterBunny extends EntityModel<Entity>
 {
   private float wingspeed = 1.0F;

   private final ModelRenderer body;
   private final ModelRenderer tail;
   private final ModelRenderer lfoot;
   private final ModelRenderer lleg;
   private final ModelRenderer upperbody;
   private final ModelRenderer head;
   private final ModelRenderer nose;
   private final ModelRenderer lear;
   private final ModelRenderer lpaw;
   private final ModelRenderer rleg;
   private final ModelRenderer rfoot;
   private final ModelRenderer rear;
   private final ModelRenderer rpaw;

   public ModelEasterBunny(float f1) {
     this.wingspeed = f1;

     this.textureWidth = 64;
     this.textureHeight = 128;

     this.body = new ModelRenderer(this, 0, 44);
     this.body.addBox(-3.0F, 0.0F, -3.0F, 6, 6, 7);
     this.body.setRotationPoint(0.0F, 17.0F, 0.0F);

     this.body.mirror = true;
     setRotation(this.body, 0.0F, 0.0F, 0.0F);
     this.tail = new ModelRenderer(this, 0, 58);
     this.tail.addBox(-2.0F, 0.0F, -2.0F, 4, 4, 4);
     this.tail.setRotationPoint(0.0F, 19.0F, 6.0F);

     this.tail.mirror = true;
     setRotation(this.tail, 0.0F, 0.0F, 0.0F);
     this.lfoot = new ModelRenderer(this, 0, 30);
     this.lfoot.addBox(-1.0F, 2.0F, -5.0F, 3, 1, 7);
     this.lfoot.setRotationPoint(3.0F, 21.0F, 1.0F);

     this.lfoot.mirror = true;
     setRotation(this.lfoot, 0.0F, 0.0F, 0.0F);
     this.lleg = new ModelRenderer(this, 0, 20);
     this.lleg.addBox(0.0F, -2.0F, -2.0F, 1, 4, 5);
     this.lleg.setRotationPoint(3.0F, 21.0F, 1.0F);

     this.lleg.mirror = true;
     setRotation(this.lleg, 0.0F, 0.0F, 0.0F);
     this.upperbody = new ModelRenderer(this, 42, 27);
     this.upperbody.addBox(-2.0F, 0.0F, -2.0F, 4, 1, 5);
     this.upperbody.setRotationPoint(0.0F, 16.0F, -1.0F);

     this.upperbody.mirror = true;
     setRotation(this.upperbody, 0.0F, 0.0F, 0.0F);
     this.head = new ModelRenderer(this, 40, 17);
     this.head.addBox(-2.5F, 0.0F, -2.0F, 5, 4, 5);
     this.head.setRotationPoint(0.0F, 12.0F, -2.0F);

     this.head.mirror = true;
     setRotation(this.head, 0.0F, 0.0F, 0.0F);
     this.nose = new ModelRenderer(this, 44, 9);
     this.nose.addBox(-1.0F, -1.0F, 0.0F, 2, 2, 1);
     this.nose.setRotationPoint(0.0F, 15.0F, -5.0F);

     this.nose.mirror = true;
     setRotation(this.nose, 0.0F, 0.0F, 0.0F);
     this.lear = new ModelRenderer(this, 54, 0);
     this.lear.addBox(0.0F, -10.0F, -1.0F, 1, 10, 3);
     this.lear.setRotationPoint(2.0F, 13.0F, -1.0F);

     this.lear.mirror = true;
     setRotation(this.lear, -0.2268928F, 0.0F, 0.0F);
     this.lpaw = new ModelRenderer(this, 6, 7);
     this.lpaw.addBox(0.0F, 0.0F, 0.0F, 1, 3, 1);
     this.lpaw.setRotationPoint(0.5F, 19.0F, -4.0F);

     this.lpaw.mirror = true;
     setRotation(this.lpaw, 0.0F, 0.0F, 0.0F);
     this.rleg = new ModelRenderer(this, 21, 20);
     this.rleg.addBox(0.0F, -2.0F, -2.0F, 1, 4, 5);
     this.rleg.setRotationPoint(-4.0F, 21.0F, 1.0F);

     this.rleg.mirror = true;
     setRotation(this.rleg, 0.0F, 0.0F, 0.0F);
     this.rfoot = new ModelRenderer(this, 21, 30);
     this.rfoot.addBox(-1.0F, 2.0F, -5.0F, 3, 1, 7);
     this.rfoot.setRotationPoint(-4.0F, 21.0F, 1.0F);

     this.rfoot.mirror = true;
     setRotation(this.rfoot, 0.0F, 0.0F, 0.0F);
     this.rear = new ModelRenderer(this, 32, 0);
     this.rear.addBox(0.0F, -10.0F, -1.0F, 1, 10, 3);
     this.rear.setRotationPoint(-3.0F, 13.0F, -1.0F);

     this.rear.mirror = true;
     setRotation(this.rear, -0.418879F, 0.0F, 0.0F);
     this.rpaw = new ModelRenderer(this, 0, 7);
     this.rpaw.addBox(0.0F, 0.0F, 0.0F, 1, 3, 1);
     this.rpaw.setRotationPoint(-1.5F, 19.0F, -4.0F);

     this.rpaw.mirror = true;
     setRotation(this.rpaw, 0.0F, 0.0F, 0.0F);
   }

   public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay,
        float red, float green, float blue, float alpha) {
        body.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        tail.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        lfoot.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        lleg.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        upperbody.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        head.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        nose.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        lear.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        lpaw.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rleg.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rfoot.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rear.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rpaw.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }

   private void setRotation(ModelRenderer model, float x, float y, float z) {
     model.rotateAngleX = x;
     model.rotateAngleY = y;
     model.rotateAngleZ = z;
   }

   public void setRotationAngles(Entity entity, float limbSwing, float limbSwingAmount,
        float ageInTicks, float netHeadYaw, float headPitch) {
EasterBunnyEntity.CustomEntity e = (EasterBunnyEntity.CustomEntity) entity;

     float newangle = 0.0F;
     float newangle2 = 0.0F;
     
     if (limbSwingAmount > 0.1D) {
       newangle = MathHelper.cos(ageInTicks * 2.6F * this.wingspeed) * 3.1415927F * 0.15F * limbSwingAmount;
       newangle2 = MathHelper.cos(ageInTicks * 1.3F * this.wingspeed) * 3.1415927F * 0.1F * limbSwingAmount;
     } else {
       newangle = 0.0F;
       newangle2 = MathHelper.cos(ageInTicks * 1.3F * this.wingspeed) * 3.1415927F * 0.01F;
     } 
     
     this.lfoot.rotateAngleX = newangle;
     this.rfoot.rotateAngleX = -newangle;
     
     this.lear.rotateAngleX = -0.226F + newangle2;
     this.rear.rotateAngleX = -0.418F - newangle2;
    }
 }
}

