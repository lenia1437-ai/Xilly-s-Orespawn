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
import net.mcreator.xillysorespawn.entity.SpyroEntity;
import net.mcreator.xillysorespawn.entity.OreSpawnLogic;

@OnlyIn(Dist.CLIENT)
public class SpyroRenderer {
    private static final String TEXTURE = "xillys_orespawn:textures/entities/spyrotexture.png";
    public static class ModelRegisterHandler {
        @SubscribeEvent @OnlyIn(Dist.CLIENT)
        public void registerModels(ModelRegistryEvent event) {
            RenderingRegistry.registerEntityRenderingHandler(SpyroEntity.entity, manager ->
                new MobRenderer(manager, new ModelSpyro(0.65F), 0.4875F) {
                    @Override public ResourceLocation getEntityTexture(Entity entity) { return new ResourceLocation(TEXTURE); }
                    @Override protected void preRenderCallback(LivingEntity entity, MatrixStack stack, float partialTick) {
                        float modelScale = 0.75F;
                        stack.scale(modelScale, modelScale, modelScale);
                    }
                });
        }
    }

public static class ModelSpyro extends EntityModel<Entity>
 {
   private float wingspeed = 1.0F;

   private final ModelRenderer RightFrontPaw;

   private final ModelRenderer WingLeft;
   private final ModelRenderer LegRightFrontTop;
   private final ModelRenderer LegRightFrontBottom;
   private final ModelRenderer LegRightBackTop;
   private final ModelRenderer LegRightBackBottom;
   private final ModelRenderer RightBackPaw;
   private final ModelRenderer LegLeftFrontTop;
   private final ModelRenderer SnoutRight;
   private final ModelRenderer LeftFrontPaw;
   private final ModelRenderer LegLeftBackTop;
   private final ModelRenderer LegLeftBackBottom;
   private final ModelRenderer LeftBackPaw;
   private final ModelRenderer LegLeftFrontBottom;
   private final ModelRenderer TailPieceSmall;
   private final ModelRenderer JawPiece;
   private final ModelRenderer HeadPieceBottom;
   private final ModelRenderer HeadPieceTop;
   private final ModelRenderer HornRightBottom;
   private final ModelRenderer HornLeftBottom;
   private final ModelRenderer HornRightTop;
   private final ModelRenderer HornLeftTop;
   private final ModelRenderer Torso;
   private final ModelRenderer SnoutLeft;
   private final ModelRenderer WingPieceLeft;
   private final ModelRenderer WingRight;
   private final ModelRenderer WingPieceRight;
   private final ModelRenderer Neck;
   private final ModelRenderer TailBack;
   private final ModelRenderer TailFront;
   private final ModelRenderer ScaleBackHead;
   private final ModelRenderer TailPieceLarge;
   private final ModelRenderer ScaleTailPiece;
   private final ModelRenderer ScaleHead;
   private final ModelRenderer ScaleTop1;
   private final ModelRenderer ScaleBackPiece1;
   private final ModelRenderer ScaleBackPiece2;

   public ModelSpyro(float f1) {
     this.wingspeed = f1;

     this.textureWidth = 64;
     this.textureHeight = 64;

     this.RightFrontPaw = new ModelRenderer(this, 12, 31);
     this.RightFrontPaw.addBox(0.0F, 5.0F, -4.0F, 2, 1, 4);
     this.RightFrontPaw.setRotationPoint(3.0F, 18.0F, -2.0F);

     this.RightFrontPaw.mirror = true;
     setRotation(this.RightFrontPaw, 0.0F, 0.0F, 0.0F);
     this.WingLeft = new ModelRenderer(this, 2, 51);
     this.WingLeft.addBox(-10.0F, -1.0F, -2.0F, 10, 0, 4);
     this.WingLeft.setRotationPoint(-1.0F, 16.0F, 0.0F);

     this.WingLeft.mirror = true;
     setRotation(this.WingLeft, 0.1745329F, 0.0F, -0.1745329F);
     this.LegRightFrontTop = new ModelRenderer(this, 20, 19);
     this.LegRightFrontTop.addBox(0.0F, 0.0F, -2.0F, 2, 3, 3);
     this.LegRightFrontTop.setRotationPoint(3.0F, 18.0F, -2.0F);

     this.LegRightFrontTop.mirror = true;
     setRotation(this.LegRightFrontTop, -0.0872665F, 0.0F, 0.0F);
     this.LegRightFrontBottom = new ModelRenderer(this, 0, 25);
     this.LegRightFrontBottom.addBox(0.0F, 2.0F, -1.5F, 2, 4, 2);
     this.LegRightFrontBottom.setRotationPoint(3.0F, 18.0F, -2.0F);

     this.LegRightFrontBottom.mirror = true;
     setRotation(this.LegRightFrontBottom, -0.1745329F, 0.0F, 0.0F);
     this.LegRightBackTop = new ModelRenderer(this, 30, 19);
     this.LegRightBackTop.addBox(0.0F, 0.0F, -2.0F, 2, 3, 3);
     this.LegRightBackTop.setRotationPoint(3.0F, 18.0F, 3.0F);

     this.LegRightBackTop.mirror = true;
     setRotation(this.LegRightBackTop, 0.1396263F, 0.0F, 0.0F);
     this.LegRightBackBottom = new ModelRenderer(this, 16, 25);
     this.LegRightBackBottom.addBox(0.0F, 2.0F, -1.0F, 2, 4, 2);
     this.LegRightBackBottom.setRotationPoint(3.0F, 18.0F, 3.0F);

     this.LegRightBackBottom.mirror = true;
     setRotation(this.LegRightBackBottom, -0.1745329F, 0.0F, 0.0F);
     this.RightBackPaw = new ModelRenderer(this, 36, 31);
     this.RightBackPaw.addBox(0.0F, 5.0F, -3.0F, 2, 1, 4);
     this.RightBackPaw.setRotationPoint(3.0F, 18.0F, 3.0F);

     this.RightBackPaw.mirror = true;
     setRotation(this.RightBackPaw, 0.0F, 0.0F, 0.0F);
     this.LegLeftFrontTop = new ModelRenderer(this, 0, 19);
     this.LegLeftFrontTop.addBox(-2.0F, 0.0F, -1.0F, 2, 3, 3);
     this.LegLeftFrontTop.setRotationPoint(-2.0F, 18.0F, -3.0F);

     this.LegLeftFrontTop.mirror = true;
     setRotation(this.LegLeftFrontTop, -0.0872665F, 0.0F, 0.0F);
     this.SnoutRight = new ModelRenderer(this, 48, 2);
     this.SnoutRight.addBox(1.0F, -3.0F, -5.0F, 1, 1, 1);
     this.SnoutRight.setRotationPoint(1.0F, 16.0F, -3.0F);

     this.SnoutRight.mirror = true;
     setRotation(this.SnoutRight, 0.0F, 0.0F, 0.0F);
     this.LeftFrontPaw = new ModelRenderer(this, 0, 31);
     this.LeftFrontPaw.addBox(-2.0F, 5.0F, -3.0F, 2, 1, 4);
     this.LeftFrontPaw.setRotationPoint(-2.0F, 18.0F, -3.0F);

     this.LeftFrontPaw.mirror = true;
     setRotation(this.LeftFrontPaw, 0.0F, 0.0F, 0.0F);
     this.LegLeftBackTop = new ModelRenderer(this, 10, 19);
     this.LegLeftBackTop.addBox(-2.0F, 0.0F, -2.0F, 2, 3, 3);
     this.LegLeftBackTop.setRotationPoint(-2.0F, 18.0F, 3.0F);

     this.LegLeftBackTop.mirror = true;
     setRotation(this.LegLeftBackTop, 0.1396263F, 0.0F, 0.0F);
     this.LegLeftBackBottom = new ModelRenderer(this, 24, 25);
     this.LegLeftBackBottom.addBox(-2.0F, 2.0F, -1.0F, 2, 4, 2);
     this.LegLeftBackBottom.setRotationPoint(-2.0F, 18.0F, 3.0F);

     this.LegLeftBackBottom.mirror = true;
     setRotation(this.LegLeftBackBottom, -0.1745329F, 0.0F, 0.0F);
     this.LeftBackPaw = new ModelRenderer(this, 24, 31);
     this.LeftBackPaw.addBox(-2.0F, 5.0F, -3.0F, 2, 1, 4);
     this.LeftBackPaw.setRotationPoint(-2.0F, 18.0F, 3.0F);

     this.LeftBackPaw.mirror = true;
     setRotation(this.LeftBackPaw, 0.0F, 0.0F, 0.0F);
     this.LegLeftFrontBottom = new ModelRenderer(this, 8, 25);
     this.LegLeftFrontBottom.addBox(-2.0F, 2.0F, -0.5F, 2, 4, 2);
     this.LegLeftFrontBottom.setRotationPoint(-2.0F, 18.0F, -3.0F);

     this.LegLeftFrontBottom.mirror = true;
     setRotation(this.LegLeftFrontBottom, -0.1745329F, 0.0F, 0.0F);
     this.TailPieceSmall = new ModelRenderer(this, 28, 36);
     this.TailPieceSmall.addBox(0.0F, -0.5F, 4.0F, 1, 1, 1);
     this.TailPieceSmall.setRotationPoint(0.0F, 16.0F, 7.0F);

     this.TailPieceSmall.mirror = true;
     setRotation(this.TailPieceSmall, 0.1745329F, 0.0F, 0.0F);
     this.JawPiece = new ModelRenderer(this, 52, 0);
     this.JawPiece.addBox(-2.0F, -1.0F, -4.0F, 3, 1, 3);
     this.JawPiece.setRotationPoint(1.0F, 16.0F, -3.0F);

     this.JawPiece.mirror = true;
     setRotation(this.JawPiece, 0.1745329F, 0.0F, 0.0F);
     this.HeadPieceBottom = new ModelRenderer(this, 30, 7);
     this.HeadPieceBottom.addBox(-3.0F, -2.0F, -5.0F, 5, 2, 6);
     this.HeadPieceBottom.setRotationPoint(1.0F, 16.0F, -3.0F);

     this.HeadPieceBottom.mirror = true;
     setRotation(this.HeadPieceBottom, 0.0F, 0.0F, 0.0F);
     this.HeadPieceTop = new ModelRenderer(this, 30, 0);
     this.HeadPieceTop.addBox(-3.0F, -5.0F, -3.0F, 5, 3, 4);
     this.HeadPieceTop.setRotationPoint(1.0F, 16.0F, -3.0F);

     this.HeadPieceTop.mirror = true;
     setRotation(this.HeadPieceTop, 0.0F, 0.0F, 0.0F);
     this.HornRightBottom = new ModelRenderer(this, 8, 14);
     this.HornRightBottom.addBox(0.0F, -6.0F, -3.5F, 2, 3, 2);
     this.HornRightBottom.setRotationPoint(1.0F, 16.0F, -3.0F);

     this.HornRightBottom.mirror = true;
     setRotation(this.HornRightBottom, -0.7853982F, 0.7853982F, 0.0F);
     this.HornLeftBottom = new ModelRenderer(this, 0, 14);
     this.HornLeftBottom.addBox(-2.75F, -6.5F, -3.0F, 2, 3, 2);
     this.HornLeftBottom.setRotationPoint(1.0F, 16.0F, -3.0F);

     this.HornLeftBottom.mirror = true;
     setRotation(this.HornLeftBottom, -0.7853982F, -0.7853982F, 0.0F);
     this.HornRightTop = new ModelRenderer(this, 20, 14);
     this.HornRightTop.addBox(0.5F, -9.0F, -3.0F, 1, 3, 1);
     this.HornRightTop.setRotationPoint(1.0F, 16.0F, -3.0F);

     this.HornRightTop.mirror = true;
     setRotation(this.HornRightTop, -0.7853982F, 0.7853982F, 0.0F);
     this.HornLeftTop = new ModelRenderer(this, 16, 14);
     this.HornLeftTop.addBox(-2.2F, -9.5F, -2.5F, 1, 3, 1);
     this.HornLeftTop.setRotationPoint(1.0F, 16.0F, -3.0F);

     this.HornLeftTop.mirror = true;
     setRotation(this.HornLeftTop, -0.7853982F, -0.7853982F, 0.0F);
     this.Torso = new ModelRenderer(this, 0, 0);
     this.Torso.addBox(-2.0F, -2.0F, -5.0F, 5, 4, 10);
     this.Torso.setRotationPoint(0.0F, 19.0F, 0.0F);

     this.Torso.mirror = true;
     setRotation(this.Torso, 0.0F, 0.0F, 0.0F);
     this.SnoutLeft = new ModelRenderer(this, 48, 0);
     this.SnoutLeft.addBox(-3.0F, -3.0F, -5.0F, 1, 1, 1);
     this.SnoutLeft.setRotationPoint(1.0F, 16.0F, -3.0F);

     this.SnoutLeft.mirror = true;
     setRotation(this.SnoutLeft, 0.0F, 0.0F, 0.0F);
     this.WingPieceLeft = new ModelRenderer(this, 4, 42);
     this.WingPieceLeft.addBox(-1.0F, -2.0F, -1.0F, 1, 2, 1);
     this.WingPieceLeft.setRotationPoint(0.0F, 17.2F, 0.0F);

     this.WingPieceLeft.mirror = true;
     setRotation(this.WingPieceLeft, 0.1745329F, 0.0F, -0.1745329F);
     this.WingRight = new ModelRenderer(this, 2, 45);
     this.WingRight.addBox(0.0F, -1.0F, -2.0F, 10, 0, 4);
     this.WingRight.setRotationPoint(2.0F, 16.0F, 0.0F);

     this.WingRight.mirror = true;
     setRotation(this.WingRight, 0.1745329F, 0.0F, 0.1745329F);
     this.WingPieceRight = new ModelRenderer(this, 0, 42);
     this.WingPieceRight.addBox(-1.0F, -2.0F, 0.0F, 1, 2, 1);
     this.WingPieceRight.setRotationPoint(2.0F, 17.5F, -1.0F);

     this.WingPieceRight.mirror = true;
     setRotation(this.WingPieceRight, 0.1745329F, 0.0F, 0.1745329F);
     this.Neck = new ModelRenderer(this, 52, 7);
     this.Neck.addBox(-1.0F, -2.0F, -1.0F, 3, 3, 3);
     this.Neck.setRotationPoint(0.0F, 17.0F, -4.0F);

     this.Neck.mirror = true;
     setRotation(this.Neck, 0.4537856F, 0.0F, 0.0F);
     this.TailBack = new ModelRenderer(this, 0, 36);
     this.TailBack.addBox(-1.0F, -1.0F, -1.0F, 2, 2, 4);
     this.TailBack.setRotationPoint(0.5F, 17.5F, 5.0F);

     this.TailBack.mirror = true;
     setRotation(this.TailBack, 0.4537856F, 0.0F, 0.0F);
     this.TailFront = new ModelRenderer(this, 12, 36);
     this.TailFront.addBox(0.0F, 0.0F, -1.0F, 1, 1, 4);
     this.TailFront.setRotationPoint(0.0F, 16.0F, 7.0F);

     this.TailFront.mirror = true;
     setRotation(this.TailFront, 0.2617994F, 0.0F, 0.0F);
     this.ScaleBackHead = new ModelRenderer(this, 38, 36);
     this.ScaleBackHead.addBox(-1.0F, -3.0F, 2.0F, 1, 2, 1);
     this.ScaleBackHead.setRotationPoint(1.0F, 16.0F, -4.0F);

     this.ScaleBackHead.mirror = true;
     setRotation(this.ScaleBackHead, 0.0F, 0.0F, 0.0F);
     this.TailPieceLarge = new ModelRenderer(this, 22, 36);
     this.TailPieceLarge.addBox(0.0F, -1.0F, 2.0F, 1, 2, 2);
     this.TailPieceLarge.setRotationPoint(0.0F, 16.0F, 7.0F);

     this.TailPieceLarge.mirror = true;
     setRotation(this.TailPieceLarge, 0.1745329F, 0.0F, 0.0F);
     this.ScaleTailPiece = new ModelRenderer(this, 48, 36);
     this.ScaleTailPiece.addBox(-0.5F, -2.0F, 0.2F, 1, 1, 2);
     this.ScaleTailPiece.setRotationPoint(0.5F, 17.5F, 5.0F);

     this.ScaleTailPiece.mirror = true;
     setRotation(this.ScaleTailPiece, 0.4537856F, 0.0F, 0.0F);
     this.ScaleHead = new ModelRenderer(this, 42, 36);
     this.ScaleHead.addBox(-1.0F, -6.0F, 0.0F, 1, 2, 2);
     this.ScaleHead.setRotationPoint(1.0F, 16.0F, -3.0F);

     this.ScaleHead.mirror = true;
     setRotation(this.ScaleHead, 0.0F, 0.0F, 0.0F);
     this.ScaleTop1 = new ModelRenderer(this, 48, 36);
     this.ScaleTop1.addBox(-1.0F, -6.0F, -4.0F, 1, 1, 2);
     this.ScaleTop1.setRotationPoint(1.0F, 16.0F, -2.0F);

     this.ScaleTop1.mirror = true;
     setRotation(this.ScaleTop1, 0.0F, 0.0F, 0.0F);
     this.ScaleBackPiece1 = new ModelRenderer(this, 48, 36);
     this.ScaleBackPiece1.addBox(0.0F, -1.0F, -1.0F, 1, 1, 2);
     this.ScaleBackPiece1.setRotationPoint(0.0F, 17.0F, 0.0F);

     this.ScaleBackPiece1.mirror = true;
     setRotation(this.ScaleBackPiece1, 0.0F, 0.0F, 0.0F);
     this.ScaleBackPiece2 = new ModelRenderer(this, 48, 36);
     this.ScaleBackPiece2.addBox(0.0F, -1.0F, -1.0F, 1, 1, 2);
     this.ScaleBackPiece2.setRotationPoint(0.0F, 17.0F, 3.0F);

     this.ScaleBackPiece2.mirror = true;
     setRotation(this.ScaleBackPiece2, 0.0F, 0.0F, 0.0F);
   }

   public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay,
        float red, float green, float blue, float alpha) {
        RightFrontPaw.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        WingLeft.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        LegRightFrontTop.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        LegRightFrontBottom.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        LegRightBackTop.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        LegRightBackBottom.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        RightBackPaw.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        LegLeftFrontTop.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        SnoutRight.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        LeftFrontPaw.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        LegLeftBackTop.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        LegLeftBackBottom.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        LeftBackPaw.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        LegLeftFrontBottom.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        TailPieceSmall.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        JawPiece.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        HeadPieceBottom.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        HeadPieceTop.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        HornRightBottom.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        HornLeftBottom.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        HornRightTop.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        HornLeftTop.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Torso.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        SnoutLeft.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        WingPieceLeft.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        WingRight.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        WingPieceRight.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Neck.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        TailBack.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        TailFront.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        ScaleBackHead.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        TailPieceLarge.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        ScaleTailPiece.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        ScaleHead.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        ScaleTop1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        ScaleBackPiece1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        ScaleBackPiece2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }

   private void setRotation(ModelRenderer model, float x, float y, float z) {
     model.rotateAngleX = x;
     model.rotateAngleY = y;
     model.rotateAngleZ = z;
   }

   public void setRotationAngles(Entity entity, float limbSwing, float limbSwingAmount,
        float ageInTicks, float netHeadYaw, float headPitch) {
// Reset shared model state before applying this frame's legacy animation.
this.RightFrontPaw.setRotationPoint(3.0F, 18.0F, -2.0F);
this.WingLeft.setRotationPoint(-1.0F, 16.0F, 0.0F);
this.LegRightFrontTop.setRotationPoint(3.0F, 18.0F, -2.0F);
this.LegRightFrontBottom.setRotationPoint(3.0F, 18.0F, -2.0F);
this.LegRightBackTop.setRotationPoint(3.0F, 18.0F, 3.0F);
this.LegRightBackBottom.setRotationPoint(3.0F, 18.0F, 3.0F);
this.RightBackPaw.setRotationPoint(3.0F, 18.0F, 3.0F);
this.LegLeftFrontTop.setRotationPoint(-2.0F, 18.0F, -3.0F);
this.SnoutRight.setRotationPoint(1.0F, 16.0F, -3.0F);
this.LeftFrontPaw.setRotationPoint(-2.0F, 18.0F, -3.0F);
this.LegLeftBackTop.setRotationPoint(-2.0F, 18.0F, 3.0F);
this.LegLeftBackBottom.setRotationPoint(-2.0F, 18.0F, 3.0F);
this.LeftBackPaw.setRotationPoint(-2.0F, 18.0F, 3.0F);
this.LegLeftFrontBottom.setRotationPoint(-2.0F, 18.0F, -3.0F);
this.TailPieceSmall.setRotationPoint(0.0F, 16.0F, 7.0F);
this.JawPiece.setRotationPoint(1.0F, 16.0F, -3.0F);
this.HeadPieceBottom.setRotationPoint(1.0F, 16.0F, -3.0F);
this.HeadPieceTop.setRotationPoint(1.0F, 16.0F, -3.0F);
this.HornRightBottom.setRotationPoint(1.0F, 16.0F, -3.0F);
this.HornLeftBottom.setRotationPoint(1.0F, 16.0F, -3.0F);
this.HornRightTop.setRotationPoint(1.0F, 16.0F, -3.0F);
this.HornLeftTop.setRotationPoint(1.0F, 16.0F, -3.0F);
this.Torso.setRotationPoint(0.0F, 19.0F, 0.0F);
this.SnoutLeft.setRotationPoint(1.0F, 16.0F, -3.0F);
this.WingPieceLeft.setRotationPoint(0.0F, 17.2F, 0.0F);
this.WingRight.setRotationPoint(2.0F, 16.0F, 0.0F);
this.WingPieceRight.setRotationPoint(2.0F, 17.5F, -1.0F);
this.Neck.setRotationPoint(0.0F, 17.0F, -4.0F);
this.TailBack.setRotationPoint(0.5F, 17.5F, 5.0F);
this.TailFront.setRotationPoint(0.0F, 16.0F, 7.0F);
this.ScaleBackHead.setRotationPoint(1.0F, 16.0F, -4.0F);
this.TailPieceLarge.setRotationPoint(0.0F, 16.0F, 7.0F);
this.ScaleTailPiece.setRotationPoint(0.5F, 17.5F, 5.0F);
this.ScaleHead.setRotationPoint(1.0F, 16.0F, -3.0F);
this.ScaleTop1.setRotationPoint(1.0F, 16.0F, -2.0F);
this.ScaleBackPiece1.setRotationPoint(0.0F, 17.0F, 0.0F);
this.ScaleBackPiece2.setRotationPoint(0.0F, 17.0F, 3.0F);
setRotation(this.RightFrontPaw, 0.0F, 0.0F, 0.0F);
setRotation(this.WingLeft, 0.1745329F, 0.0F, -0.1745329F);
setRotation(this.LegRightFrontTop, -0.0872665F, 0.0F, 0.0F);
setRotation(this.LegRightFrontBottom, -0.1745329F, 0.0F, 0.0F);
setRotation(this.LegRightBackTop, 0.1396263F, 0.0F, 0.0F);
setRotation(this.LegRightBackBottom, -0.1745329F, 0.0F, 0.0F);
setRotation(this.RightBackPaw, 0.0F, 0.0F, 0.0F);
setRotation(this.LegLeftFrontTop, -0.0872665F, 0.0F, 0.0F);
setRotation(this.SnoutRight, 0.0F, 0.0F, 0.0F);
setRotation(this.LeftFrontPaw, 0.0F, 0.0F, 0.0F);
setRotation(this.LegLeftBackTop, 0.1396263F, 0.0F, 0.0F);
setRotation(this.LegLeftBackBottom, -0.1745329F, 0.0F, 0.0F);
setRotation(this.LeftBackPaw, 0.0F, 0.0F, 0.0F);
setRotation(this.LegLeftFrontBottom, -0.1745329F, 0.0F, 0.0F);
setRotation(this.TailPieceSmall, 0.1745329F, 0.0F, 0.0F);
setRotation(this.JawPiece, 0.1745329F, 0.0F, 0.0F);
setRotation(this.HeadPieceBottom, 0.0F, 0.0F, 0.0F);
setRotation(this.HeadPieceTop, 0.0F, 0.0F, 0.0F);
setRotation(this.HornRightBottom, -0.7853982F, 0.7853982F, 0.0F);
setRotation(this.HornLeftBottom, -0.7853982F, -0.7853982F, 0.0F);
setRotation(this.HornRightTop, -0.7853982F, 0.7853982F, 0.0F);
setRotation(this.HornLeftTop, -0.7853982F, -0.7853982F, 0.0F);
setRotation(this.Torso, 0.0F, 0.0F, 0.0F);
setRotation(this.SnoutLeft, 0.0F, 0.0F, 0.0F);
setRotation(this.WingPieceLeft, 0.1745329F, 0.0F, -0.1745329F);
setRotation(this.WingRight, 0.1745329F, 0.0F, 0.1745329F);
setRotation(this.WingPieceRight, 0.1745329F, 0.0F, 0.1745329F);
setRotation(this.Neck, 0.4537856F, 0.0F, 0.0F);
setRotation(this.TailBack, 0.4537856F, 0.0F, 0.0F);
setRotation(this.TailFront, 0.2617994F, 0.0F, 0.0F);
setRotation(this.ScaleBackHead, 0.0F, 0.0F, 0.0F);
setRotation(this.TailPieceLarge, 0.1745329F, 0.0F, 0.0F);
setRotation(this.ScaleTailPiece, 0.4537856F, 0.0F, 0.0F);
setRotation(this.ScaleHead, 0.0F, 0.0F, 0.0F);
setRotation(this.ScaleTop1, 0.0F, 0.0F, 0.0F);
setRotation(this.ScaleBackPiece1, 0.0F, 0.0F, 0.0F);
setRotation(this.ScaleBackPiece2, 0.0F, 0.0F, 0.0F);

     SpyroEntity.CustomEntity c = (SpyroEntity.CustomEntity) entity;
     float hf = 0.0F;
     float newangle = 0.0F;
     int current_activity = c.getActivity();

     if (limbSwingAmount > 0.1D) {
       newangle = MathHelper.cos(ageInTicks * 2.3F * this.wingspeed) * 3.1415927F * 0.4F * limbSwingAmount;
     } else {
       newangle = 0.0F;
     } 
     if (current_activity == 3) newangle *= 0.5F;
     
     this.WingLeft.rotateAngleZ = newangle;
     this.WingRight.rotateAngleZ = -newangle;
 
 
     
     if (limbSwingAmount > 0.1D) {
       newangle = MathHelper.cos(ageInTicks * 2.0F * this.wingspeed) * 3.1415927F * 0.25F * limbSwingAmount;
     } else {
       newangle = 0.0F;
     } 
     if (current_activity == 3) newangle = 0.0F;
     
     if (current_activity != 2) {
       this.LegRightFrontTop.rotateAngleX = newangle - 0.087F;
       this.LegRightFrontBottom.rotateAngleX = newangle - 0.17F;
       this.RightFrontPaw.rotateAngleX = newangle;
       
       this.LegLeftFrontTop.rotateAngleX = -newangle - 0.087F;
       this.LegLeftFrontBottom.rotateAngleX = -newangle - 0.17F;
       this.LeftFrontPaw.rotateAngleX = -newangle;
       
       this.LegRightBackBottom.rotateAngleX = -newangle + 0.139F;
       this.LegRightBackTop.rotateAngleX = -newangle - 0.174F;
       this.RightBackPaw.rotateAngleX = -newangle;
       
       this.LegLeftBackBottom.rotateAngleX = newangle + 0.139F;
       this.LegLeftBackTop.rotateAngleX = newangle - 0.174F;
       this.LeftBackPaw.rotateAngleX = newangle;
     } else {
       
       newangle = -1.0F;
       this.LegRightFrontTop.rotateAngleX = newangle - 0.087F;
       this.LegRightFrontBottom.rotateAngleX = newangle - 0.17F;
       this.RightFrontPaw.rotateAngleX = newangle;
       this.LegLeftFrontTop.rotateAngleX = newangle - 0.087F;
       this.LegLeftFrontBottom.rotateAngleX = newangle - 0.17F;
       this.LeftFrontPaw.rotateAngleX = newangle;
       
       newangle = 1.0F;
       this.LegRightBackBottom.rotateAngleX = newangle + 0.139F;
       this.LegRightBackTop.rotateAngleX = newangle - 0.174F;
       this.RightBackPaw.rotateAngleX = newangle;
       this.LegLeftBackBottom.rotateAngleX = newangle + 0.139F;
       this.LegLeftBackTop.rotateAngleX = newangle - 0.174F;
       this.LeftBackPaw.rotateAngleX = newangle;
     } 
 
 
     
     newangle = MathHelper.cos(ageInTicks * 1.2F * this.wingspeed) * 3.1415927F * 0.25F;
     if (c.isChildModel() == true || current_activity == 3) {
       newangle = 0.0F;
     }
     this.TailBack.rotateAngleY = newangle;
     this.ScaleTailPiece.rotateAngleY = newangle;
     
     this.TailBack.rotationPointZ += (float)Math.cos(this.TailBack.rotateAngleY) * 3.0F;
     this.TailFront.rotationPointX = this.TailBack.rotationPointX + (float)Math.sin(this.TailBack.rotateAngleY) * 3.0F - 0.5F;
     this.TailFront.rotateAngleY = newangle * 1.6F;
     this.TailPieceLarge.rotationPointZ = this.TailFront.rotationPointZ;
     this.TailPieceLarge.rotationPointX = this.TailFront.rotationPointX;
     this.TailPieceLarge.rotateAngleY = this.TailFront.rotateAngleY;
     this.TailPieceSmall.rotationPointZ = this.TailFront.rotationPointZ;
     this.TailPieceSmall.rotationPointX = this.TailFront.rotationPointX;
     this.TailPieceSmall.rotateAngleY = this.TailFront.rotateAngleY;
 
 
     
     this.HeadPieceTop.rotateAngleY = (float)Math.toRadians(netHeadYaw);
     this.HeadPieceBottom.rotateAngleY = (float)Math.toRadians(netHeadYaw);
     this.JawPiece.rotateAngleY = (float)Math.toRadians(netHeadYaw);
     this.SnoutRight.rotateAngleY = (float)Math.toRadians(netHeadYaw);
     this.SnoutLeft.rotateAngleY = (float)Math.toRadians(netHeadYaw);
     this.ScaleTop1.rotateAngleY = (float)Math.toRadians(netHeadYaw);
     this.ScaleHead.rotateAngleY = (float)Math.toRadians(netHeadYaw);
     this.ScaleBackHead.rotateAngleY = (float)Math.toRadians(netHeadYaw);
     this.HornRightBottom.rotateAngleY = (float)Math.toRadians(netHeadYaw) + 0.785F;
     this.HornRightTop.rotateAngleY = (float)Math.toRadians(netHeadYaw) + 0.785F;
     this.HornLeftBottom.rotateAngleY = (float)Math.toRadians(netHeadYaw) - 0.785F;
     this.HornLeftTop.rotateAngleY = (float)Math.toRadians(netHeadYaw) - 0.785F;
 
     
     this.HeadPieceTop.rotateAngleX = (float)Math.toRadians(headPitch);
     this.HeadPieceBottom.rotateAngleX = (float)Math.toRadians(headPitch);
     this.JawPiece.rotateAngleX = (float)Math.toRadians(headPitch);
     this.SnoutRight.rotateAngleX = (float)Math.toRadians(headPitch);
     this.SnoutLeft.rotateAngleX = (float)Math.toRadians(headPitch);
     this.ScaleTop1.rotateAngleX = (float)Math.toRadians(headPitch);
     this.ScaleHead.rotateAngleX = (float)Math.toRadians(headPitch);
     this.ScaleBackHead.rotateAngleX = (float)Math.toRadians(headPitch);
     this.HornRightBottom.rotateAngleX = (float)Math.toRadians(headPitch) - 0.785F;
     this.HornRightTop.rotateAngleX = (float)Math.toRadians(headPitch) - 0.785F;
     this.HornLeftBottom.rotateAngleX = (float)Math.toRadians(headPitch) - 0.785F;
     this.HornLeftTop.rotateAngleX = (float)Math.toRadians(headPitch) - 0.785F;
    }
 }
}