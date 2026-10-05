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
import net.mcreator.xillysorespawn.entity.MantisEntity;
import net.mcreator.xillysorespawn.entity.OreSpawnLogic;

@OnlyIn(Dist.CLIENT)
public class MantisRenderer {
    private static final String TEXTURE = "xillys_orespawn:textures/entities/mantistexture.png";
    public static class ModelRegisterHandler {
        @SubscribeEvent @OnlyIn(Dist.CLIENT)
        public void registerModels(ModelRegistryEvent event) {
            RenderingRegistry.registerEntityRenderingHandler(MantisEntity.entity, manager ->
                new MobRenderer(manager, new ModelMantis(2.0F), 0.99F) {
                    @Override public ResourceLocation getEntityTexture(Entity entity) { return new ResourceLocation(TEXTURE); }
                    @Override protected void preRenderCallback(LivingEntity entity, MatrixStack stack, float partialTick) {
                        float modelScale = 1.1F;
                        stack.scale(modelScale, modelScale, modelScale);
                    }
                });
        }
    }

public static class ModelMantis extends EntityModel<Entity> {
   private float wingspeed = 1.0F;

   private final ModelRenderer lfleg1;

   private final ModelRenderer lfleg2;

   private final ModelRenderer lfleg3;
   private final ModelRenderer lfleg4;
   private final ModelRenderer lrleg1;
   private final ModelRenderer lrleg2;
   private final ModelRenderer lrleg3;
   private final ModelRenderer lrleg4;
   private final ModelRenderer abdomen;
   private final ModelRenderer thorax;
   private final ModelRenderer neck1;
   private final ModelRenderer neck2;
   private final ModelRenderer head1;
   private final ModelRenderer head2;
   private final ModelRenderer leye;
   private final ModelRenderer reye;
   private final ModelRenderer lantenna;
   private final ModelRenderer rantenna;
   private final ModelRenderer larm1;
   private final ModelRenderer larm2;
   private final ModelRenderer larm3;
   private final ModelRenderer lfwing;
   private final ModelRenderer rfwing;
   private final ModelRenderer lrwing;
   private final ModelRenderer rrwing;
   private final ModelRenderer rarm1;
   private final ModelRenderer rarm2;
   private final ModelRenderer rarm3;
   private final ModelRenderer rlfleg3;
   private final ModelRenderer rfleg4;
   private final ModelRenderer rfleg2;
   private final ModelRenderer rfleg1;
   private final ModelRenderer rrleg3;
   private final ModelRenderer rrleg4;
   private final ModelRenderer rrleg2;
   private final ModelRenderer rrleg1;

   public ModelMantis(float f1) {
     this.wingspeed = f1;

     this.textureWidth = 256;
     this.textureHeight = 256;

     this.lfleg1 = new ModelRenderer(this, 28, 35);
     this.lfleg1.addBox(0.0F, 0.0F, 0.0F, 1, 10, 1);
     this.lfleg1.setRotationPoint(27.0F, 16.0F, -3.0F);

     this.lfleg1.mirror = true;
     setRotation(this.lfleg1, 0.0F, 0.0F, -0.6283185F);
     this.lfleg2 = new ModelRenderer(this, 0, 32);
     this.lfleg2.addBox(0.0F, 0.0F, 0.0F, 1, 22, 1);
     this.lfleg2.setRotationPoint(21.0F, -5.0F, -3.0F);

     this.lfleg2.mirror = true;
     setRotation(this.lfleg2, 0.0F, 0.0F, -0.2792527F);
     this.lfleg3 = new ModelRenderer(this, 64, 2);
     this.lfleg3.addBox(0.0F, 0.0F, 0.0F, 20, 1, 1);
     this.lfleg3.setRotationPoint(2.0F, -5.0F, 0.0F);

     this.lfleg3.mirror = true;
     setRotation(this.lfleg3, 0.0F, 0.1570796F, 0.0F);
     this.lfleg4 = new ModelRenderer(this, 64, 20);
     this.lfleg4.addBox(15.0F, 0.0F, -2.0F, 4, 1, 5);
     this.lfleg4.setRotationPoint(2.0F, -5.0F, 0.0F);

     this.lfleg4.mirror = true;
     setRotation(this.lfleg4, 0.0F, 0.1570796F, 0.0F);
     this.lrleg1 = new ModelRenderer(this, 35, 35);
     this.lrleg1.addBox(0.0F, 0.0F, 0.0F, 1, 10, 1);
     this.lrleg1.setRotationPoint(32.0F, 18.0F, 11.0F);

     this.lrleg1.mirror = true;
     setRotation(this.lrleg1, 0.0F, 0.0F, -0.8726646F);
     this.lrleg2 = new ModelRenderer(this, 14, 32);
     this.lrleg2.addBox(0.0F, 0.0F, 0.0F, 1, 22, 1);
     this.lrleg2.setRotationPoint(21.0F, 0.0F, 11.0F);

     this.lrleg2.mirror = true;
     setRotation(this.lrleg2, 0.0F, 0.0F, -0.5410521F);
     this.lrleg3 = new ModelRenderer(this, 64, 11);
     this.lrleg3.addBox(0.0F, 0.0F, 0.0F, 20, 1, 1);
     this.lrleg3.setRotationPoint(2.0F, 0.0F, 8.0F);

     this.lrleg3.mirror = true;
     setRotation(this.lrleg3, 0.0F, -0.1570796F, 0.0F);
     this.lrleg4 = new ModelRenderer(this, 64, 36);
     this.lrleg4.addBox(15.0F, 0.0F, -2.0F, 4, 1, 5);
     this.lrleg4.setRotationPoint(2.0F, 0.0F, 8.0F);

     this.lrleg4.mirror = true;
     setRotation(this.lrleg4, 0.0F, -0.1570796F, 0.0F);
     this.abdomen = new ModelRenderer(this, 118, 0);
     this.abdomen.addBox(0.0F, 0.0F, 0.0F, 9, 5, 53);
     this.abdomen.setRotationPoint(-4.0F, -11.0F, 0.0F);

     this.abdomen.mirror = true;
     setRotation(this.abdomen, -0.5061455F, 0.0F, 0.0F);
     this.thorax = new ModelRenderer(this, 145, 62);
     this.thorax.addBox(0.0F, 0.0F, 0.0F, 15, 3, 13);
     this.thorax.setRotationPoint(-7.0F, -14.0F, -12.0F);

     this.thorax.mirror = true;
     setRotation(this.thorax, -0.2443461F, 0.0F, 0.0F);
     this.neck1 = new ModelRenderer(this, 145, 82);
     this.neck1.addBox(0.0F, 0.0F, 0.0F, 9, 1, 15);
     this.neck1.setRotationPoint(-4.0F, -15.0F, -27.0F);

     this.neck1.mirror = true;
     setRotation(this.neck1, -0.0698132F, 0.0F, 0.0F);
     this.neck2 = new ModelRenderer(this, 40, 150);
     this.neck2.addBox(0.0F, 0.0F, 0.0F, 3, 1, 2);
     this.neck2.setRotationPoint(-1.0F, -15.0F, -29.0F);

     this.neck2.mirror = true;
     setRotation(this.neck2, 0.0F, 0.0F, 0.0F);
     this.head1 = new ModelRenderer(this, 0, 150);
     this.head1.addBox(0.0F, 0.0F, 0.0F, 2, 6, 1);
     this.head1.setRotationPoint(0.0F, -16.0F, -30.0F);

     this.head1.mirror = true;
     setRotation(this.head1, 0.0F, 0.0F, 0.1396263F);
     this.head2 = new ModelRenderer(this, 10, 150);
     this.head2.addBox(-2.0F, 0.0F, 0.0F, 2, 6, 1);
     this.head2.setRotationPoint(0.0F, -16.0F, -30.0F);

     this.head2.mirror = true;
     setRotation(this.head2, 0.0F, 0.0F, -0.1745329F);
     this.leye = new ModelRenderer(this, 20, 150);
     this.leye.addBox(1.0F, 0.0F, -0.5F, 2, 2, 1);
     this.leye.setRotationPoint(0.0F, -16.0F, -30.0F);

     this.leye.mirror = true;
     setRotation(this.leye, 0.0F, 0.0F, 0.1396263F);
     this.reye = new ModelRenderer(this, 30, 150);
     this.reye.addBox(-3.0F, 0.0F, -0.5F, 2, 2, 1);
     this.reye.setRotationPoint(0.0F, -16.0F, -30.0F);

     this.reye.mirror = true;
     setRotation(this.reye, 0.0F, 0.0F, -0.1745329F);
     this.lantenna = new ModelRenderer(this, 53, 150);
     this.lantenna.addBox(0.0F, -20.0F, 0.0F, 1, 20, 1);
     this.lantenna.setRotationPoint(0.0F, -16.0F, -30.0F);

     this.lantenna.mirror = true;
     setRotation(this.lantenna, 0.0F, 0.0F, 0.2792527F);
     this.rantenna = new ModelRenderer(this, 60, 150);
     this.rantenna.addBox(-1.0F, -20.0F, 0.0F, 1, 20, 1);
     this.rantenna.setRotationPoint(0.0F, -16.0F, -30.0F);

     this.rantenna.mirror = true;
     setRotation(this.rantenna, 0.0F, 0.0F, -0.2792527F);
     this.larm1 = new ModelRenderer(this, 51, 0);
     this.larm1.addBox(0.0F, 0.0F, -1.0F, 1, 23, 4);
     this.larm1.setRotationPoint(2.0F, -14.0F, -23.0F);

     this.larm1.mirror = true;
     setRotation(this.larm1, 0.0349066F, 0.0F, 0.0F);
     this.larm2 = new ModelRenderer(this, 30, 0);
     this.larm2.addBox(0.0F, -18.0F, -2.0F, 1, 18, 2);
     this.larm2.setRotationPoint(2.0F, 8.0F, -22.0F);

     this.larm2.mirror = true;
     setRotation(this.larm2, 0.5585054F, 0.0F, 0.0F);
     this.larm3 = new ModelRenderer(this, 16, 0);
     this.larm3.addBox(0.0F, 0.0F, 0.0F, 1, 21, 1);
     this.larm3.setRotationPoint(2.0F, -7.0F, -33.0F);

     this.larm3.mirror = true;
     setRotation(this.larm3, 0.0F, 0.0F, 0.0F);
     this.lfwing = new ModelRenderer(this, 0, 67);
     this.lfwing.addBox(0.0F, 0.0F, 0.0F, 48, 1, 12);
     this.lfwing.setRotationPoint(2.0F, -11.0F, 0.0F);

     this.lfwing.mirror = true;
     setRotation(this.lfwing, -0.2268928F, 0.0F, -0.6981317F);
     this.rfwing = new ModelRenderer(this, 0, 83);
     this.rfwing.addBox(-48.0F, 0.0F, 0.0F, 48, 1, 12);
     this.rfwing.setRotationPoint(-1.0F, -11.0F, 0.0F);

     this.rfwing.mirror = true;
     setRotation(this.rfwing, -0.2268928F, 0.0F, 0.6981317F);
     this.lrwing = new ModelRenderer(this, 0, 100);
     this.lrwing.addBox(0.0F, 0.0F, 0.0F, 42, 1, 17);
     this.lrwing.setRotationPoint(2.0F, -6.0F, 10.0F);

     this.lrwing.mirror = true;
     setRotation(this.lrwing, -0.2268928F, 0.0F, -0.3490659F);
     this.rrwing = new ModelRenderer(this, 0, 122);
     this.rrwing.addBox(-42.0F, 0.0F, 0.0F, 42, 1, 17);
     this.rrwing.setRotationPoint(-1.0F, -6.0F, 10.0F);

     this.rrwing.mirror = true;
     setRotation(this.rrwing, -0.2268928F, 0.0F, 0.3490659F);
     this.rarm1 = new ModelRenderer(this, 38, 0);
     this.rarm1.addBox(0.0F, 0.0F, -1.0F, 1, 23, 4);
     this.rarm1.setRotationPoint(-1.0F, -14.0F, -23.0F);

     this.rarm1.mirror = true;
     setRotation(this.rarm1, 0.0349066F, 0.0F, 0.0F);
     this.rarm2 = new ModelRenderer(this, 22, 0);
     this.rarm2.addBox(0.0F, -18.0F, -2.0F, 1, 18, 2);
     this.rarm2.setRotationPoint(-1.0F, 8.0F, -22.0F);

     this.rarm2.mirror = true;
     setRotation(this.rarm2, 0.5585054F, 0.0F, 0.0F);
     this.rarm3 = new ModelRenderer(this, 10, 0);
     this.rarm3.addBox(0.0F, 0.0F, 0.0F, 1, 21, 1);
     this.rarm3.setRotationPoint(-1.0F, -7.0F, -33.0F);

     this.rarm3.mirror = true;
     setRotation(this.rarm3, 0.0F, 0.0F, 0.0F);
     this.rlfleg3 = new ModelRenderer(this, 64, 6);
     this.rlfleg3.addBox(-20.0F, 0.0F, 0.0F, 20, 1, 1);
     this.rlfleg3.setRotationPoint(-1.0F, -5.0F, 0.0F);

     this.rlfleg3.mirror = true;
     setRotation(this.rlfleg3, 0.0F, -0.1570796F, 0.0F);
     this.rfleg4 = new ModelRenderer(this, 64, 28);
     this.rfleg4.addBox(-19.0F, 0.0F, -2.0F, 4, 1, 5);
     this.rfleg4.setRotationPoint(-1.0F, -5.0F, 0.0F);

     this.rfleg4.mirror = true;
     setRotation(this.rfleg4, 0.0F, -0.1570796F, 0.0F);
     this.rfleg2 = new ModelRenderer(this, 7, 32);
     this.rfleg2.addBox(0.0F, 0.0F, 0.0F, 1, 22, 1);
     this.rfleg2.setRotationPoint(-21.0F, -5.0F, -3.0F);

     this.rfleg2.mirror = true;
     setRotation(this.rfleg2, 0.0F, 0.0F, 0.2792527F);
     this.rfleg1 = new ModelRenderer(this, 42, 35);
     this.rfleg1.addBox(0.0F, 0.0F, 0.0F, 1, 10, 1);
     this.rfleg1.setRotationPoint(-27.0F, 16.0F, -3.0F);

     this.rfleg1.mirror = true;
     setRotation(this.rfleg1, 0.0F, 0.0F, 0.6283185F);
     this.rrleg3 = new ModelRenderer(this, 64, 16);
     this.rrleg3.addBox(-20.0F, 0.0F, 0.0F, 20, 1, 1);
     this.rrleg3.setRotationPoint(-1.0F, 0.0F, 8.0F);

     this.rrleg3.mirror = true;
     setRotation(this.rrleg3, 0.0F, 0.1570796F, 0.0F);
     this.rrleg4 = new ModelRenderer(this, 64, 44);
     this.rrleg4.addBox(-19.0F, 0.0F, -2.0F, 4, 1, 5);
     this.rrleg4.setRotationPoint(-1.0F, 0.0F, 8.0F);

     this.rrleg4.mirror = true;
     setRotation(this.rrleg4, 0.0F, 0.1570796F, 0.0F);
     this.rrleg2 = new ModelRenderer(this, 21, 32);
     this.rrleg2.addBox(0.0F, 0.0F, 0.0F, 1, 22, 1);
     this.rrleg2.setRotationPoint(-21.0F, 0.0F, 11.0F);

     this.rrleg2.mirror = true;
     setRotation(this.rrleg2, 0.0F, 0.0F, 0.5410521F);
     this.rrleg1 = new ModelRenderer(this, 49, 35);
     this.rrleg1.addBox(0.0F, 0.0F, 0.0F, 1, 10, 1);
     this.rrleg1.setRotationPoint(-32.0F, 18.0F, 11.0F);

     this.rrleg1.mirror = true;
     setRotation(this.rrleg1, 0.0F, 0.0F, 0.8726646F);
   }

   public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay,
        float red, float green, float blue, float alpha) {
        lfleg1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        lfleg2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        lfleg3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        lfleg4.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        lrleg1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        lrleg2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        lrleg3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        lrleg4.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        abdomen.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        thorax.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        neck1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        neck2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        head1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        head2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        leye.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        reye.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        lantenna.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rantenna.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        larm1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        larm2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        larm3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        lfwing.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rfwing.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        lrwing.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rrwing.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rarm1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rarm2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rarm3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rlfleg3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rfleg4.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rfleg2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rfleg1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rrleg3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rrleg4.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rrleg2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rrleg1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }

   private void setRotation(ModelRenderer model, float x, float y, float z) {
     model.rotateAngleX = x;
     model.rotateAngleY = y;
     model.rotateAngleZ = z;
   }

   public void setRotationAngles(Entity entity, float limbSwing, float limbSwingAmount,
        float ageInTicks, float netHeadYaw, float headPitch) {
// Reset shared model state before applying this frame's legacy animation.
this.lfleg1.setRotationPoint(27.0F, 16.0F, -3.0F);
this.lfleg2.setRotationPoint(21.0F, -5.0F, -3.0F);
this.lfleg3.setRotationPoint(2.0F, -5.0F, 0.0F);
this.lfleg4.setRotationPoint(2.0F, -5.0F, 0.0F);
this.lrleg1.setRotationPoint(32.0F, 18.0F, 11.0F);
this.lrleg2.setRotationPoint(21.0F, 0.0F, 11.0F);
this.lrleg3.setRotationPoint(2.0F, 0.0F, 8.0F);
this.lrleg4.setRotationPoint(2.0F, 0.0F, 8.0F);
this.abdomen.setRotationPoint(-4.0F, -11.0F, 0.0F);
this.thorax.setRotationPoint(-7.0F, -14.0F, -12.0F);
this.neck1.setRotationPoint(-4.0F, -15.0F, -27.0F);
this.neck2.setRotationPoint(-1.0F, -15.0F, -29.0F);
this.head1.setRotationPoint(0.0F, -16.0F, -30.0F);
this.head2.setRotationPoint(0.0F, -16.0F, -30.0F);
this.leye.setRotationPoint(0.0F, -16.0F, -30.0F);
this.reye.setRotationPoint(0.0F, -16.0F, -30.0F);
this.lantenna.setRotationPoint(0.0F, -16.0F, -30.0F);
this.rantenna.setRotationPoint(0.0F, -16.0F, -30.0F);
this.larm1.setRotationPoint(2.0F, -14.0F, -23.0F);
this.larm2.setRotationPoint(2.0F, 8.0F, -22.0F);
this.larm3.setRotationPoint(2.0F, -7.0F, -33.0F);
this.lfwing.setRotationPoint(2.0F, -11.0F, 0.0F);
this.rfwing.setRotationPoint(-1.0F, -11.0F, 0.0F);
this.lrwing.setRotationPoint(2.0F, -6.0F, 10.0F);
this.rrwing.setRotationPoint(-1.0F, -6.0F, 10.0F);
this.rarm1.setRotationPoint(-1.0F, -14.0F, -23.0F);
this.rarm2.setRotationPoint(-1.0F, 8.0F, -22.0F);
this.rarm3.setRotationPoint(-1.0F, -7.0F, -33.0F);
this.rlfleg3.setRotationPoint(-1.0F, -5.0F, 0.0F);
this.rfleg4.setRotationPoint(-1.0F, -5.0F, 0.0F);
this.rfleg2.setRotationPoint(-21.0F, -5.0F, -3.0F);
this.rfleg1.setRotationPoint(-27.0F, 16.0F, -3.0F);
this.rrleg3.setRotationPoint(-1.0F, 0.0F, 8.0F);
this.rrleg4.setRotationPoint(-1.0F, 0.0F, 8.0F);
this.rrleg2.setRotationPoint(-21.0F, 0.0F, 11.0F);
this.rrleg1.setRotationPoint(-32.0F, 18.0F, 11.0F);
setRotation(this.lfleg1, 0.0F, 0.0F, -0.6283185F);
setRotation(this.lfleg2, 0.0F, 0.0F, -0.2792527F);
setRotation(this.lfleg3, 0.0F, 0.1570796F, 0.0F);
setRotation(this.lfleg4, 0.0F, 0.1570796F, 0.0F);
setRotation(this.lrleg1, 0.0F, 0.0F, -0.8726646F);
setRotation(this.lrleg2, 0.0F, 0.0F, -0.5410521F);
setRotation(this.lrleg3, 0.0F, -0.1570796F, 0.0F);
setRotation(this.lrleg4, 0.0F, -0.1570796F, 0.0F);
setRotation(this.abdomen, -0.5061455F, 0.0F, 0.0F);
setRotation(this.thorax, -0.2443461F, 0.0F, 0.0F);
setRotation(this.neck1, -0.0698132F, 0.0F, 0.0F);
setRotation(this.neck2, 0.0F, 0.0F, 0.0F);
setRotation(this.head1, 0.0F, 0.0F, 0.1396263F);
setRotation(this.head2, 0.0F, 0.0F, -0.1745329F);
setRotation(this.leye, 0.0F, 0.0F, 0.1396263F);
setRotation(this.reye, 0.0F, 0.0F, -0.1745329F);
setRotation(this.lantenna, 0.0F, 0.0F, 0.2792527F);
setRotation(this.rantenna, 0.0F, 0.0F, -0.2792527F);
setRotation(this.larm1, 0.0349066F, 0.0F, 0.0F);
setRotation(this.larm2, 0.5585054F, 0.0F, 0.0F);
setRotation(this.larm3, 0.0F, 0.0F, 0.0F);
setRotation(this.lfwing, -0.2268928F, 0.0F, -0.6981317F);
setRotation(this.rfwing, -0.2268928F, 0.0F, 0.6981317F);
setRotation(this.lrwing, -0.2268928F, 0.0F, -0.3490659F);
setRotation(this.rrwing, -0.2268928F, 0.0F, 0.3490659F);
setRotation(this.rarm1, 0.0349066F, 0.0F, 0.0F);
setRotation(this.rarm2, 0.5585054F, 0.0F, 0.0F);
setRotation(this.rarm3, 0.0F, 0.0F, 0.0F);
setRotation(this.rlfleg3, 0.0F, -0.1570796F, 0.0F);
setRotation(this.rfleg4, 0.0F, -0.1570796F, 0.0F);
setRotation(this.rfleg2, 0.0F, 0.0F, 0.2792527F);
setRotation(this.rfleg1, 0.0F, 0.0F, 0.6283185F);
setRotation(this.rrleg3, 0.0F, 0.1570796F, 0.0F);
setRotation(this.rrleg4, 0.0F, 0.1570796F, 0.0F);
setRotation(this.rrleg2, 0.0F, 0.0F, 0.5410521F);
setRotation(this.rrleg1, 0.0F, 0.0F, 0.8726646F);

     float a1, newangle = 0.0F;
     
     MantisEntity.CustomEntity b = (MantisEntity.CustomEntity) entity;

     newangle = MathHelper.cos(ageInTicks * 0.9F * this.wingspeed) * 3.1415927F * 0.25F;
     this.lfwing.rotateAngleZ = -0.698F - newangle;
     this.rfwing.rotateAngleZ = 0.698F + newangle;
     newangle = MathHelper.cos(ageInTicks * 0.9F * this.wingspeed) * 3.1415927F * 0.35F;
     this.lrwing.rotateAngleZ = -0.349F + newangle;
     this.rrwing.rotateAngleZ = 0.349F - newangle;
 
     
     if (b.getAttacking() == 0) {
       newangle = MathHelper.cos(ageInTicks * 0.051F * this.wingspeed) * 3.1415927F * 0.013F;
       a1 = -0.2F;
     } else {
       newangle = MathHelper.cos(ageInTicks * 0.51F * this.wingspeed) * 3.1415927F * 0.25F;
       a1 = -0.698F;
     } 
     this.larm1.rotateAngleX = a1 + newangle;
     this.larm2.rotationPointZ = (float)((this.larm1.rotationPointZ + 1.0F) + Math.sin(this.larm1.rotateAngleX) * 22.0D);
     this.larm2.rotationPointY = (float)(this.larm1.rotationPointY + Math.cos(this.larm1.rotateAngleX) * 22.0D);
     this.larm2.rotateAngleX = -a1 - newangle;
     this.larm3.rotationPointZ = (float)((this.larm2.rotationPointZ + 1.0F) - Math.sin(this.larm2.rotateAngleX) * 17.0D);
     this.larm3.rotationPointY = (float)(this.larm2.rotationPointY - Math.cos(this.larm2.rotateAngleX) * 17.0D);
     this.larm3.rotateAngleX = a1 + newangle;
     
     this.rarm1.rotateAngleX = a1 - newangle;
     this.rarm2.rotationPointZ = (float)((this.rarm1.rotationPointZ + 1.0F) + Math.sin(this.rarm1.rotateAngleX) * 22.0D);
     this.rarm2.rotationPointY = (float)(this.rarm1.rotationPointY + Math.cos(this.rarm1.rotateAngleX) * 22.0D);
     this.rarm2.rotateAngleX = -a1 + newangle;
     this.rarm3.rotationPointZ = (float)((this.rarm2.rotationPointZ + 1.0F) - Math.sin(this.rarm2.rotateAngleX) * 17.0D);
     this.rarm3.rotationPointY = (float)(this.rarm2.rotationPointY - Math.cos(this.rarm2.rotateAngleX) * 17.0D);
     this.rarm3.rotateAngleX = a1 - newangle;
    }
 }
}