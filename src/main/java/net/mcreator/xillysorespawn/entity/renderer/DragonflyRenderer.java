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
import net.mcreator.xillysorespawn.entity.DragonflyEntity;

@OnlyIn(Dist.CLIENT)
public class DragonflyRenderer {
    private static final String TEXTURE = "xillys_orespawn:textures/entities/dragonfly.png";
    public static class ModelRegisterHandler {
        @SubscribeEvent @OnlyIn(Dist.CLIENT)
        public void registerModels(ModelRegistryEvent event) {
            RenderingRegistry.registerEntityRenderingHandler(DragonflyEntity.entity, manager ->
                new MobRenderer(manager, new ModelDragonfly(1.0F), 0.5F) {
                    @Override public ResourceLocation getEntityTexture(Entity entity) { return new ResourceLocation(TEXTURE); }
                });
        }
    }

public static class ModelDragonfly extends EntityModel<Entity>
 {
   private float wingspeed = 1.0F;

   private final ModelRenderer Shape1;

   private final ModelRenderer lfwing;
   private final ModelRenderer Shape3;
   private final ModelRenderer Shape4;
   private final ModelRenderer Shape5;
   private final ModelRenderer rjaw;
   private final ModelRenderer ljaw;
   private final ModelRenderer tail1;
   private final ModelRenderer tail2;
   private final ModelRenderer Shape10;
   private final ModelRenderer Shape11;
   private final ModelRenderer Shape12;
   private final ModelRenderer Shape13;
   private final ModelRenderer Shape14;
   private final ModelRenderer Shape15;
   private final ModelRenderer Shape16;
   private final ModelRenderer Shape17;
   private final ModelRenderer Shape18;
   private final ModelRenderer Shape19;
   private final ModelRenderer Shape20;
   private final ModelRenderer Shape21;
   private final ModelRenderer Shape22;
   private final ModelRenderer Shape23;
   private final ModelRenderer lrwing;
   private final ModelRenderer rfwing;
   private final ModelRenderer rrwing;

   public ModelDragonfly(float f1) {
     this.wingspeed = f1;

     this.textureWidth = 64;
     this.textureHeight = 64;

     this.Shape1 = new ModelRenderer(this, 0, 0);
     this.Shape1.addBox(0.0F, 0.0F, 0.0F, 5, 4, 7);
     this.Shape1.setRotationPoint(0.0F, 16.0F, 0.0F);

     this.Shape1.mirror = true;
     setRotation(this.Shape1, 0.0F, 0.0F, 0.0F);
     this.lfwing = new ModelRenderer(this, 0, 33);
     this.lfwing.addBox(0.0F, 0.0F, 0.0F, 10, 1, 3);
     this.lfwing.setRotationPoint(5.0F, 16.0F, 1.0F);

     this.lfwing.mirror = true;
     setRotation(this.lfwing, 0.0F, 0.4886922F, 0.0F);
     this.Shape3 = new ModelRenderer(this, 0, 13);
     this.Shape3.addBox(-2.0F, 0.0F, -4.0F, 4, 3, 4);
     this.Shape3.setRotationPoint(2.5F, 16.0F, -1.0F);

     this.Shape3.mirror = true;
     setRotation(this.Shape3, 0.4886922F, 0.0F, 0.0F);
     this.Shape4 = new ModelRenderer(this, 9, 21);
     this.Shape4.addBox(0.0F, 0.0F, 0.0F, 1, 2, 3);
     this.Shape4.setRotationPoint(1.0F, 18.0F, -6.0F);

     this.Shape4.mirror = true;
     setRotation(this.Shape4, 0.4886922F, 0.1745329F, 0.0F);
     this.Shape5 = new ModelRenderer(this, 0, 21);
     this.Shape5.addBox(0.0F, 0.0F, 0.0F, 1, 2, 3);
     this.Shape5.setRotationPoint(3.0F, 18.0F, -6.0F);

     this.Shape5.mirror = true;
     setRotation(this.Shape5, 0.4886922F, -0.1745329F, 0.0F);
     this.rjaw = new ModelRenderer(this, 0, 27);
     this.rjaw.addBox(-1.0F, 0.0F, 0.0F, 1, 3, 1);
     this.rjaw.setRotationPoint(2.0F, 19.0F, -5.0F);

     this.rjaw.mirror = true;
     setRotation(this.rjaw, 0.4363323F, 0.1745329F, 0.0F);
     this.ljaw = new ModelRenderer(this, 5, 27);
     this.ljaw.addBox(0.0F, 0.0F, 0.0F, 1, 3, 1);
     this.ljaw.setRotationPoint(3.0F, 19.0F, -5.0F);

     this.ljaw.mirror = true;
     setRotation(this.ljaw, 0.4363323F, -0.1745329F, 0.0F);
     this.tail1 = new ModelRenderer(this, 25, 0);
     this.tail1.addBox(-1.0F, 0.0F, 0.0F, 3, 3, 7);
     this.tail1.setRotationPoint(2.0F, 16.0F, 7.0F);

     this.tail1.mirror = true;
     setRotation(this.tail1, 0.0F, 0.0F, 0.0F);
     this.tail2 = new ModelRenderer(this, 25, 11);
     this.tail2.addBox(0.0F, 0.0F, 0.0F, 1, 2, 9);
     this.tail2.setRotationPoint(2.0F, 16.0F, 14.0F);

     this.tail2.mirror = true;
     setRotation(this.tail2, 0.0F, 0.0F, 0.0F);
     this.Shape10 = new ModelRenderer(this, 23, 0);
     this.Shape10.addBox(-1.0F, 0.0F, 0.0F, 1, 4, 1);
     this.Shape10.setRotationPoint(1.0F, 18.0F, 0.0F);

     this.Shape10.mirror = true;
     setRotation(this.Shape10, -0.2792527F, 0.0F, 0.3490659F);
     this.Shape11 = new ModelRenderer(this, 40, 0);
     this.Shape11.addBox(0.0F, 0.0F, -4.0F, 1, 1, 4);
     this.Shape11.setRotationPoint(-1.0F, 21.0F, 0.0F);

     this.Shape11.mirror = true;
     setRotation(this.Shape11, 0.0F, 0.0F, 0.0F);
     this.Shape12 = new ModelRenderer(this, 18, 12);
     this.Shape12.addBox(-1.0F, 0.0F, 0.0F, 1, 3, 1);
     this.Shape12.setRotationPoint(0.0F, 21.0F, -4.0F);

     this.Shape12.mirror = true;
     setRotation(this.Shape12, 0.0F, 0.0F, -0.1919862F);
     this.Shape13 = new ModelRenderer(this, 18, 0);
     this.Shape13.addBox(0.0F, 0.0F, 0.0F, 1, 4, 1);
     this.Shape13.setRotationPoint(4.0F, 18.0F, 0.0F);

     this.Shape13.mirror = true;
     setRotation(this.Shape13, -0.2792527F, 0.0F, -0.3490659F);
     this.Shape14 = new ModelRenderer(this, 51, 0);
     this.Shape14.addBox(0.0F, 0.0F, -4.0F, 1, 1, 4);
     this.Shape14.setRotationPoint(5.0F, 21.0F, 0.0F);

     this.Shape14.mirror = true;
     setRotation(this.Shape14, 0.0F, 0.0F, 0.0F);
     this.Shape15 = new ModelRenderer(this, 13, 12);
     this.Shape15.addBox(0.0F, 0.0F, 0.0F, 1, 3, 1);
     this.Shape15.setRotationPoint(5.0F, 21.0F, -4.0F);

     this.Shape15.mirror = true;
     setRotation(this.Shape15, 0.0F, 0.0F, 0.1919862F);
     this.Shape16 = new ModelRenderer(this, 9, 53);
     this.Shape16.addBox(0.0F, 0.0F, 0.0F, 3, 1, 1);
     this.Shape16.setRotationPoint(5.0F, 19.5F, 3.0F);

     this.Shape16.mirror = true;
     setRotation(this.Shape16, 0.0F, 0.0F, 0.6457718F);
     this.Shape17 = new ModelRenderer(this, 0, 56);
     this.Shape17.addBox(0.0F, 0.0F, 0.0F, 1, 3, 1);
     this.Shape17.setRotationPoint(6.0F, 21.0F, 3.0F);

     this.Shape17.mirror = true;
     setRotation(this.Shape17, 0.0F, 0.0F, 0.0F);
     this.Shape18 = new ModelRenderer(this, 0, 53);
     this.Shape18.addBox(-3.0F, 0.0F, 0.0F, 3, 1, 1);
     this.Shape18.setRotationPoint(0.0F, 19.5F, 3.0F);

     this.Shape18.mirror = true;
     setRotation(this.Shape18, 0.0F, 0.0F, -0.6457718F);
     this.Shape19 = new ModelRenderer(this, 5, 56);
     this.Shape19.addBox(-1.0F, 0.0F, 0.0F, 1, 3, 1);
     this.Shape19.setRotationPoint(-1.0F, 21.0F, 3.0F);

     this.Shape19.mirror = true;
     setRotation(this.Shape19, 0.0F, 0.0F, 0.0F);
     this.Shape20 = new ModelRenderer(this, 9, 61);
     this.Shape20.addBox(0.0F, 0.0F, 0.0F, 3, 1, 1);
     this.Shape20.setRotationPoint(4.0F, 19.5F, 6.0F);

     this.Shape20.mirror = true;
     setRotation(this.Shape20, 0.0F, -0.6457718F, 0.5061455F);
     this.Shape21 = new ModelRenderer(this, 0, 61);
     this.Shape21.addBox(0.0F, 0.0F, 0.0F, 3, 1, 1);
     this.Shape21.setRotationPoint(1.5F, 19.5F, 7.0F);

     this.Shape21.mirror = true;
     setRotation(this.Shape21, 0.0F, -2.391101F, 0.5061455F);
     this.Shape22 = new ModelRenderer(this, 0, 0);
     this.Shape22.addBox(0.0F, 0.0F, 0.0F, 1, 3, 1);
     this.Shape22.setRotationPoint(-1.0F, 21.0F, 7.5F);

     this.Shape22.mirror = true;
     setRotation(this.Shape22, 0.0F, 0.0F, 0.0F);
     this.Shape23 = new ModelRenderer(this, 0, 13);
     this.Shape23.addBox(0.0F, 0.0F, 0.0F, 1, 3, 1);
     this.Shape23.setRotationPoint(5.0F, 21.0F, 7.5F);

     this.Shape23.mirror = true;
     setRotation(this.Shape23, 0.0F, 0.0F, 0.0F);
     this.lrwing = new ModelRenderer(this, 0, 38);
     this.lrwing.addBox(0.0F, 0.0F, -3.0F, 10, 1, 3);
     this.lrwing.setRotationPoint(5.0F, 16.0F, 6.0F);

     this.lrwing.mirror = true;
     setRotation(this.lrwing, 0.0F, -0.3839724F, 0.0F);
     this.rfwing = new ModelRenderer(this, 0, 48);
     this.rfwing.addBox(-10.0F, 0.0F, 0.0F, 10, 1, 3);
     this.rfwing.setRotationPoint(0.0F, 16.0F, 1.0F);

     this.rfwing.mirror = true;
     setRotation(this.rfwing, 0.0F, -0.4886922F, 0.0F);
     this.rrwing = new ModelRenderer(this, 0, 43);
     this.rrwing.addBox(-10.0F, 0.0F, -3.0F, 10, 1, 3);
     this.rrwing.setRotationPoint(0.0F, 16.0F, 6.0F);

     this.rrwing.mirror = true;
     setRotation(this.rrwing, 0.0F, 0.3839724F, 0.0F);
   }

   public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay,
        float red, float green, float blue, float alpha) {
        Shape1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        lfwing.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Shape3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Shape4.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Shape5.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rjaw.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        ljaw.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        tail1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        tail2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Shape10.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Shape11.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Shape12.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Shape13.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Shape14.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Shape15.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Shape16.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Shape17.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Shape18.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Shape19.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Shape20.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Shape21.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Shape22.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Shape23.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        lrwing.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rfwing.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rrwing.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }

   private void setRotation(ModelRenderer model, float x, float y, float z) {
     model.rotateAngleX = x;
     model.rotateAngleY = y;
     model.rotateAngleZ = z;
   }

   public void setRotationAngles(Entity entity, float limbSwing, float limbSwingAmount,
        float ageInTicks, float netHeadYaw, float headPitch) {
float newangle = 0.0F;

     newangle = MathHelper.cos(ageInTicks * 1.3F * this.wingspeed) * 3.1415927F * 0.25F;
     this.lfwing.rotateAngleZ = newangle;
     this.rfwing.rotateAngleZ = -newangle;
     this.lrwing.rotateAngleZ = newangle + 3.14F;
     this.rrwing.rotateAngleZ = -newangle + 3.14F;
     
     newangle = MathHelper.cos(ageInTicks * 0.3F * this.wingspeed) * 3.1415927F * 0.1F;
     this.ljaw.rotateAngleX = newangle;
     this.rjaw.rotateAngleX = -newangle;
    }
 }
}

