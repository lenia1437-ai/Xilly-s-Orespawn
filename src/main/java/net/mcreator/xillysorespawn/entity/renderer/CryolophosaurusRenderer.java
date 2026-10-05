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
import net.mcreator.xillysorespawn.entity.CryolophosaurusEntity;

@OnlyIn(Dist.CLIENT)
public class CryolophosaurusRenderer {
    private static final String TEXTURE = "xillys_orespawn:textures/entities/cryolophosaurus.png";
    public static class ModelRegisterHandler {
        @SubscribeEvent @OnlyIn(Dist.CLIENT)
        public void registerModels(ModelRegistryEvent event) {
            RenderingRegistry.registerEntityRenderingHandler(CryolophosaurusEntity.entity, manager ->
                new MobRenderer(manager, new ModelCryolophosaurus(1.0F), 0.5F) {
                    @Override public ResourceLocation getEntityTexture(Entity entity) { return new ResourceLocation(TEXTURE); }
                });
        }
    }

public static class ModelCryolophosaurus extends EntityModel<Entity>
 {
   private float wingspeed = 1.0F;

   private final ModelRenderer Shape1;

   private final ModelRenderer Shape2;

   private final ModelRenderer Shape3;
   private final ModelRenderer jaw;
   private final ModelRenderer Shape5;
   private final ModelRenderer Shape6;
   private final ModelRenderer Shape7;
   private final ModelRenderer Shape8;
   private final ModelRenderer Shape9;
   private final ModelRenderer rightleg;
   private final ModelRenderer Shape11;
   private final ModelRenderer rightleg2;
   private final ModelRenderer rightleg3;
   private final ModelRenderer rightleg4;
   private final ModelRenderer leftleg;
   private final ModelRenderer Shape16;
   private final ModelRenderer Shape17;
   private final ModelRenderer leftleg2;
   private final ModelRenderer leftleg3;
   private final ModelRenderer leftleg4;

   public ModelCryolophosaurus(float f1) {
     this.textureWidth = 128;
     this.textureHeight = 128;
     this.wingspeed = f1;

     this.Shape1 = new ModelRenderer(this, 0, 0);
     this.Shape1.addBox(0.0F, 0.0F, 0.0F, 8, 9, 18);
     this.Shape1.setRotationPoint(0.0F, 0.0F, 0.0F);

     this.Shape1.mirror = true;
     setRotation(this.Shape1, 0.0F, 0.0F, 0.0F);
     this.Shape2 = new ModelRenderer(this, 53, 0);
     this.Shape2.addBox(0.0F, 0.0F, 0.0F, 6, 4, 11);
     this.Shape2.setRotationPoint(1.0F, -2.0F, -7.0F);

     this.Shape2.mirror = true;
     setRotation(this.Shape2, -0.2268928F, 0.0F, 0.0F);
     this.Shape3 = new ModelRenderer(this, 0, 41);
     this.Shape3.addBox(0.0F, 0.0F, 0.0F, 6, 4, 10);
     this.Shape3.setRotationPoint(1.0F, -2.0F, -15.0F);

     this.Shape3.mirror = true;
     setRotation(this.Shape3, 0.0F, 0.0F, 0.0F);
     this.jaw = new ModelRenderer(this, 0, 30);
     this.jaw.addBox(0.0F, 0.0F, 0.0F, 4, 9, 1);
     this.jaw.setRotationPoint(2.0F, 1.0F, -8.0F);

     this.jaw.mirror = true;
     setRotation(this.jaw, -1.256637F, 0.0F, 0.0F);
     this.Shape5 = new ModelRenderer(this, 91, 0);
     this.Shape5.addBox(0.0F, 0.0F, 0.0F, 6, 6, 7);
     this.Shape5.setRotationPoint(1.0F, 0.0F, 18.0F);

     this.Shape5.mirror = true;
     setRotation(this.Shape5, 0.0F, 0.0F, 0.0F);
     this.Shape6 = new ModelRenderer(this, 36, 31);
     this.Shape6.addBox(0.0F, 0.0F, 0.0F, 4, 4, 14);
     this.Shape6.setRotationPoint(2.0F, 0.0F, 25.0F);

     this.Shape6.mirror = true;
     setRotation(this.Shape6, 0.0F, 0.0F, 0.0F);
     this.Shape7 = new ModelRenderer(this, 43, 8);
     this.Shape7.addBox(0.0F, 0.0F, 0.0F, 1, 4, 2);
     this.Shape7.setRotationPoint(-1.0F, 8.0F, 0.0F);

     this.Shape7.mirror = true;
     setRotation(this.Shape7, 0.1919862F, 0.0F, 0.0F);
     this.Shape8 = new ModelRenderer(this, 9, 0);
     this.Shape8.addBox(0.0F, 0.0F, 0.0F, 1, 3, 1);
     this.Shape8.setRotationPoint(-1.0F, 11.0F, 1.0F);

     this.Shape8.mirror = true;
     setRotation(this.Shape8, -0.2617994F, 0.0F, 0.0F);
     this.Shape9 = new ModelRenderer(this, 0, 0);
     this.Shape9.addBox(0.0F, 0.0F, 0.0F, 2, 4, 1);
     this.Shape9.setRotationPoint(3.0F, -4.0F, -9.0F);

     this.Shape9.mirror = true;
     setRotation(this.Shape9, -0.9424778F, 0.0F, 0.0F);
     this.rightleg = new ModelRenderer(this, 0, 58);
     this.rightleg.addBox(0.0F, 0.0F, 0.0F, 2, 10, 6);
     this.rightleg.setRotationPoint(-1.0F, 2.0F, 12.0F);

     this.rightleg.mirror = true;
     setRotation(this.rightleg, -0.2792527F, 0.0F, 0.0F);
     this.Shape11 = new ModelRenderer(this, 39, 0);
     this.Shape11.addBox(0.0F, 0.0F, 0.0F, 4, 3, 3);
     this.Shape11.setRotationPoint(2.0F, -1.0F, -18.0F);

     this.Shape11.mirror = true;
     setRotation(this.Shape11, 0.0F, 0.0F, 0.0F);
     this.rightleg2 = new ModelRenderer(this, 0, 77);
     this.rightleg2.addBox(0.0F, 7.0F, -5.0F, 2, 10, 3);
     this.rightleg2.setRotationPoint(-1.0F, 2.0F, 12.0F);

     this.rightleg2.mirror = true;
     setRotation(this.rightleg2, 0.3839724F, 0.0F, 0.0F);
     this.rightleg3 = new ModelRenderer(this, 35, 31);
     this.rightleg3.addBox(0.0F, 10.0F, 12.0F, 2, 7, 2);
     this.rightleg3.setRotationPoint(-1.0F, 2.0F, 12.0F);

     this.rightleg3.mirror = true;
     setRotation(this.rightleg3, -0.6806784F, 0.0F, 0.0F);
     this.rightleg4 = new ModelRenderer(this, 68, 55);
     this.rightleg4.addBox(0.0F, 20.0F, -5.0F, 2, 2, 6);
     this.rightleg4.setRotationPoint(-1.0F, 2.0F, 12.0F);

     this.rightleg4.mirror = true;
     setRotation(this.rightleg4, 0.0F, 0.0F, 0.0F);
     this.leftleg = new ModelRenderer(this, 22, 58);
     this.leftleg.addBox(0.0F, 0.0F, 0.0F, 2, 10, 6);
     this.leftleg.setRotationPoint(7.0F, 2.0F, 12.0F);

     this.leftleg.mirror = true;
     setRotation(this.leftleg, -0.2792527F, 0.0F, 0.0F);
     this.Shape16 = new ModelRenderer(this, 0, 8);
     this.Shape16.addBox(0.0F, 0.0F, 0.0F, 1, 4, 2);
     this.Shape16.setRotationPoint(8.0F, 8.0F, 0.0F);

     this.Shape16.mirror = true;
     setRotation(this.Shape16, 0.1919862F, 0.0F, 0.0F);
     this.Shape17 = new ModelRenderer(this, 9, 9);
     this.Shape17.addBox(0.0F, 0.0F, 0.0F, 1, 3, 1);
     this.Shape17.setRotationPoint(8.0F, 11.0F, 1.0F);

     this.Shape17.mirror = true;
     setRotation(this.Shape17, -0.2617994F, 0.0F, 0.0F);
     this.leftleg2 = new ModelRenderer(this, 16, 77);
     this.leftleg2.addBox(0.0F, 7.0F, -5.0F, 2, 10, 3);
     this.leftleg2.setRotationPoint(7.0F, 2.0F, 12.0F);

     this.leftleg2.mirror = true;
     setRotation(this.leftleg2, 0.3839724F, 0.0F, 0.0F);
     this.leftleg3 = new ModelRenderer(this, 67, 31);
     this.leftleg3.addBox(0.0F, 10.0F, 12.0F, 2, 7, 2);
     this.leftleg3.setRotationPoint(7.0F, 2.0F, 12.0F);

     this.leftleg3.mirror = true;
     setRotation(this.leftleg3, -0.6806784F, 0.0F, 0.0F);
     this.leftleg4 = new ModelRenderer(this, 47, 56);
     this.leftleg4.addBox(0.0F, 20.0F, -5.0F, 2, 2, 6);
     this.leftleg4.setRotationPoint(7.0F, 2.0F, 12.0F);

     this.leftleg4.mirror = true;
     setRotation(this.leftleg4, 0.0F, 0.0F, 0.0F);
   }

   public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay,
        float red, float green, float blue, float alpha) {
        Shape1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Shape2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Shape3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        jaw.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Shape5.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Shape6.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Shape7.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Shape8.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Shape9.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rightleg.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Shape11.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rightleg2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rightleg3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rightleg4.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        leftleg.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Shape16.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Shape17.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        leftleg2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        leftleg3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        leftleg4.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }

   private void setRotation(ModelRenderer model, float x, float y, float z) {
     model.rotateAngleX = x;
     model.rotateAngleY = y;
     model.rotateAngleZ = z;
   }

   public void setRotationAngles(Entity entity, float limbSwing, float limbSwingAmount,
        float ageInTicks, float netHeadYaw, float headPitch) {
float newangle = 0.0F;
     
     if (limbSwingAmount > 0.1D) {
       newangle = MathHelper.cos(ageInTicks * 1.3F * this.wingspeed) * 3.1415927F * 0.25F * limbSwingAmount;
     } else {
       newangle = 0.0F;
     } 
     
     this.rightleg.rotateAngleX = -0.2792527F + newangle;
     this.rightleg2.rotateAngleX = 0.384F + newangle;
     this.rightleg3.rotateAngleX = -0.68F + newangle;
     this.rightleg4.rotateAngleX = newangle;
     
     this.leftleg.rotateAngleX = -0.2792527F - newangle;
     this.leftleg2.rotateAngleX = 0.384F - newangle;
     this.leftleg3.rotateAngleX = -0.68F - newangle;
     this.leftleg4.rotateAngleX = -newangle;
     
     this.jaw.rotateAngleX = -1.15F + MathHelper.cos(ageInTicks * 0.28F) * 3.1415927F * 0.1F;
    }
 }
}

