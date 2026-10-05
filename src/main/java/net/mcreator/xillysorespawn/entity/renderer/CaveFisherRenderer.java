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
import net.mcreator.xillysorespawn.entity.CaveFisherEntity;

@OnlyIn(Dist.CLIENT)
public class CaveFisherRenderer {
    private static final String TEXTURE = "xillys_orespawn:textures/entities/cavefisher.png";
    public static class ModelRegisterHandler {
        @SubscribeEvent @OnlyIn(Dist.CLIENT)
        public void registerModels(ModelRegistryEvent event) {
            RenderingRegistry.registerEntityRenderingHandler(CaveFisherEntity.entity, manager ->
                new MobRenderer(manager, new ModelCaveFisher(1.0F), 0.5F) {
                    @Override public ResourceLocation getEntityTexture(Entity entity) { return new ResourceLocation(TEXTURE); }
                });
        }
    }

public static class ModelCaveFisher extends EntityModel<Entity>
 {
   private float wingspeed = 1.0F;

   private final ModelRenderer Nose;

   private final ModelRenderer EyeLeft;
   private final ModelRenderer HeadMid;
   private final ModelRenderer HeadEnd;
   private final ModelRenderer TailTuft;
   private final ModelRenderer EyeRight;
   private final ModelRenderer BodyTopLeft4;
   private final ModelRenderer BodyTopRight4;
   private final ModelRenderer BodyTopLeft1;
   private final ModelRenderer BodyTopRight1;
   private final ModelRenderer BodyTopRight2;
   private final ModelRenderer BodyTopLeft2;
   private final ModelRenderer BodyTopRight3;
   private final ModelRenderer BodyTopLeft3;
   private final ModelRenderer HeadBase;
   private final ModelRenderer TailBase;
   private final ModelRenderer BodyLow2;
   private final ModelRenderer BodyLow1;
   private final ModelRenderer Spine5;
   private final ModelRenderer Spine1;
   private final ModelRenderer Spine2;
   private final ModelRenderer Spine3;
   private final ModelRenderer Spine4;
   private final ModelRenderer RightArmSeg4;
   private final ModelRenderer LeftArmSeg1;
   private final ModelRenderer LeftArmSeg3;
   private final ModelRenderer RightArmSeg2;
   private final ModelRenderer RightArmSeg1;
   private final ModelRenderer LeftArmSeg5;
   private final ModelRenderer LeftArmSeg2;
   private final ModelRenderer LeftClawTop;
   private final ModelRenderer RightArmSeg3;
   private final ModelRenderer RightArmSeg5;
   private final ModelRenderer LeftArmSeg4;
   private final ModelRenderer LeftClawBase;
   private final ModelRenderer RightClawBase;
   private final ModelRenderer LeftClawLow;
   private final ModelRenderer RightClawTop;
   private final ModelRenderer RightClawLow;
   private final ModelRenderer LBLeg1;
   private final ModelRenderer LBLeg3;
   private final ModelRenderer RBLeg1;
   private final ModelRenderer RBLeg3;
   private final ModelRenderer LBLeg2;
   private final ModelRenderer RBLeg2;
   private final ModelRenderer LBLeg4;
   private final ModelRenderer RBLeg4;
   private final ModelRenderer RBLeg5;
   private final ModelRenderer LBLeg6;
   private final ModelRenderer RBLeg6;
   private final ModelRenderer LBLeg5;
   private final ModelRenderer RFLeg1;
   private final ModelRenderer RFLeg2;
   private final ModelRenderer RFLeg3;
   private final ModelRenderer RFLeg4;
   private final ModelRenderer RFLeg5;
   private final ModelRenderer RFLeg6;
   private final ModelRenderer RMLeg1;
   private final ModelRenderer RMLeg2;
   private final ModelRenderer RMLeg3;
   private final ModelRenderer RMLeg4;
   private final ModelRenderer RMLeg5;
   private final ModelRenderer RMLeg6;
   private final ModelRenderer LFLeg1;
   private final ModelRenderer LFLeg2;
   private final ModelRenderer LFLeg3;
   private final ModelRenderer LFLeg4;
   private final ModelRenderer LFLeg5;
   private final ModelRenderer LFLeg6;
   private final ModelRenderer LMLeg1;
   private final ModelRenderer LMLeg2;
   private final ModelRenderer LMLeg4;
   private final ModelRenderer LMLeg3;
   private final ModelRenderer LMLeg5;
   private final ModelRenderer LMLeg6;

   public ModelCaveFisher(float f1) {
     this.wingspeed = f1;

     this.textureWidth = 64;
     this.textureHeight = 32;

     this.Nose = new ModelRenderer(this, 0, 0);
     this.Nose.addBox(-0.5F, -0.5F, -12.0F, 1, 1, 6);
     this.Nose.setRotationPoint(0.0F, 19.0F, -4.0F);

     this.Nose.mirror = true;
     setRotation(this.Nose, 0.0F, 0.0F, 0.0F);
     this.EyeLeft = new ModelRenderer(this, 0, 28);
     this.EyeLeft.addBox(0.5F, -2.5F, -2.5F, 3, 2, 2);
     this.EyeLeft.setRotationPoint(0.0F, 19.0F, -4.0F);

     this.EyeLeft.mirror = true;
     setRotation(this.EyeLeft, 0.0F, 0.0F, 0.0F);
     this.HeadMid = new ModelRenderer(this, 0, 0);
     this.HeadMid.addBox(-2.5F, -1.5F, -5.0F, 5, 3, 2);
     this.HeadMid.setRotationPoint(0.0F, 19.0F, -4.0F);

     this.HeadMid.mirror = true;
     setRotation(this.HeadMid, 0.0F, 0.0F, 0.0F);
     this.HeadEnd = new ModelRenderer(this, 0, 0);
     this.HeadEnd.addBox(-2.0F, -1.0F, -6.0F, 4, 2, 1);
     this.HeadEnd.setRotationPoint(0.0F, 19.0F, -4.0F);

     this.HeadEnd.mirror = true;
     setRotation(this.HeadEnd, 0.0F, 0.0F, 0.0F);
     this.TailTuft = new ModelRenderer(this, 0, 23);
     this.TailTuft.addBox(-2.0F, -1.0F, 3.0F, 4, 1, 2);
     this.TailTuft.setRotationPoint(0.0F, 19.0F, 10.0F);

     this.TailTuft.mirror = true;
     setRotation(this.TailTuft, 0.0F, 0.0F, 0.0F);
     this.EyeRight = new ModelRenderer(this, 0, 28);
     this.EyeRight.addBox(-3.5F, -2.5F, -2.5F, 3, 2, 2);
     this.EyeRight.setRotationPoint(0.0F, 19.0F, -4.0F);

     this.EyeRight.mirror = true;
     setRotation(this.EyeRight, 0.0F, 0.0F, 0.0F);
     this.BodyTopLeft4 = new ModelRenderer(this, 0, 0);
     this.BodyTopLeft4.addBox(0.0F, 0.0F, 0.0F, 6, 3, 4);
     this.BodyTopLeft4.setRotationPoint(0.0F, 16.2F, 7.0F);

     this.BodyTopLeft4.mirror = true;
     setRotation(this.BodyTopLeft4, 0.1047198F, 0.1047198F, 0.1047198F);
     this.BodyTopRight4 = new ModelRenderer(this, 0, 0);
     this.BodyTopRight4.addBox(-5.0F, 0.0F, 0.0F, 6, 3, 4);
     this.BodyTopRight4.setRotationPoint(-1.0F, 16.2F, 7.0F);

     this.BodyTopRight4.mirror = true;
     setRotation(this.BodyTopRight4, 0.1047198F, -0.1047198F, -0.1047198F);
     this.BodyTopLeft1 = new ModelRenderer(this, 0, 0);
     this.BodyTopLeft1.addBox(0.0F, 0.0F, 0.0F, 5, 3, 4);
     this.BodyTopLeft1.setRotationPoint(0.0F, 16.0F, -4.0F);

     this.BodyTopLeft1.mirror = true;
     setRotation(this.BodyTopLeft1, 0.1745329F, 0.1745329F, 0.1047198F);
     this.BodyTopRight1 = new ModelRenderer(this, 0, 0);
     this.BodyTopRight1.addBox(-5.0F, 0.0F, 0.0F, 5, 3, 4);
     this.BodyTopRight1.setRotationPoint(0.0F, 16.0F, -4.0F);

     this.BodyTopRight1.mirror = true;
     setRotation(this.BodyTopRight1, 0.1745329F, -0.1745329F, -0.1047198F);
     this.BodyTopRight2 = new ModelRenderer(this, 0, 0);
     this.BodyTopRight2.addBox(-5.0F, 0.0F, 0.0F, 7, 3, 4);
     this.BodyTopRight2.setRotationPoint(-1.0F, 16.0F, -1.0F);

     this.BodyTopRight2.mirror = true;
     setRotation(this.BodyTopRight2, 0.2094395F, -0.1745329F, -0.1047198F);
     this.BodyTopLeft2 = new ModelRenderer(this, 0, 0);
     this.BodyTopLeft2.addBox(-1.0F, 0.0F, 0.0F, 7, 3, 4);
     this.BodyTopLeft2.setRotationPoint(0.0F, 16.0F, -1.0F);

     this.BodyTopLeft2.mirror = true;
     setRotation(this.BodyTopLeft2, 0.2094395F, 0.1745329F, 0.1047198F);
     this.BodyTopRight3 = new ModelRenderer(this, 0, 0);
     this.BodyTopRight3.addBox(-5.0F, 0.0F, 0.0F, 6, 3, 4);
     this.BodyTopRight3.setRotationPoint(-1.0F, 16.0F, 3.0F);

     this.BodyTopRight3.mirror = true;
     setRotation(this.BodyTopRight3, 0.1396263F, -0.1396263F, -0.1047198F);
     this.BodyTopLeft3 = new ModelRenderer(this, 0, 0);
     this.BodyTopLeft3.addBox(0.0F, 0.0F, 0.0F, 6, 3, 4);
     this.BodyTopLeft3.setRotationPoint(0.0F, 16.0F, 3.0F);

     this.BodyTopLeft3.mirror = true;
     setRotation(this.BodyTopLeft3, 0.1396263F, 0.1396263F, 0.1047198F);
     this.HeadBase = new ModelRenderer(this, 0, 0);
     this.HeadBase.addBox(-3.0F, -2.0F, -3.0F, 6, 4, 3);
     this.HeadBase.setRotationPoint(0.0F, 19.0F, -4.0F);

     this.HeadBase.mirror = true;
     setRotation(this.HeadBase, 0.0F, 0.0F, 0.0F);
     this.TailBase = new ModelRenderer(this, 0, 0);
     this.TailBase.addBox(-3.0F, -2.0F, 0.0F, 6, 3, 3);
     this.TailBase.setRotationPoint(0.0F, 19.0F, 10.0F);

     this.TailBase.mirror = true;
     setRotation(this.TailBase, 0.0F, 0.0F, 0.0F);
     this.BodyLow2 = new ModelRenderer(this, 34, 0);
     this.BodyLow2.addBox(0.0F, 0.0F, 0.0F, 8, 2, 7);
     this.BodyLow2.setRotationPoint(-4.0F, 18.3F, 3.0F);

     this.BodyLow2.mirror = true;
     setRotation(this.BodyLow2, 0.0F, 0.0F, 0.0F);
     this.BodyLow1 = new ModelRenderer(this, 34, 0);
     this.BodyLow1.addBox(0.0F, 0.0F, 0.0F, 8, 2, 7);
     this.BodyLow1.setRotationPoint(-4.0F, 18.7F, -4.0F);

     this.BodyLow1.mirror = true;
     setRotation(this.BodyLow1, 0.0F, 0.0F, 0.0F);
     this.Spine5 = new ModelRenderer(this, 0, 0);
     this.Spine5.addBox(-0.5F, 0.0F, 0.0F, 1, 1, 4);
     this.Spine5.setRotationPoint(0.0F, 16.0F, 8.6F);

     this.Spine5.mirror = true;
     setRotation(this.Spine5, 0.0F, 0.0F, 0.0F);
     this.Spine1 = new ModelRenderer(this, 0, 0);
     this.Spine1.addBox(-0.5F, 0.0F, 0.0F, 1, 1, 4);
     this.Spine1.setRotationPoint(0.0F, 16.0F, -4.2F);

     this.Spine1.mirror = true;
     setRotation(this.Spine1, 0.2443461F, 0.0F, 0.0F);
     this.Spine2 = new ModelRenderer(this, 0, 0);
     this.Spine2.addBox(-0.5F, 0.0F, 0.0F, 1, 1, 5);
     this.Spine2.setRotationPoint(0.0F, 16.0F, -1.2F);

     this.Spine2.mirror = true;
     setRotation(this.Spine2, 0.3141593F, 0.0F, 0.0F);
     this.Spine3 = new ModelRenderer(this, 0, 0);
     this.Spine3.addBox(-0.5F, 0.0F, 0.0F, 1, 1, 6);
     this.Spine3.setRotationPoint(0.0F, 16.0F, 1.8F);

     this.Spine3.mirror = true;
     setRotation(this.Spine3, 0.2792527F, 0.0F, 0.0F);
     this.Spine4 = new ModelRenderer(this, 0, 0);
     this.Spine4.addBox(-0.5F, 0.0F, 0.0F, 1, 1, 8);
     this.Spine4.setRotationPoint(0.0F, 16.0F, 3.8F);

     this.Spine4.mirror = true;
     setRotation(this.Spine4, 0.1745329F, 0.0F, 0.0F);
     this.RightArmSeg4 = new ModelRenderer(this, 0, 0);
     this.RightArmSeg4.addBox(-3.2F, -1.0F, -10.5F, 2, 2, 4);
     this.RightArmSeg4.setRotationPoint(-4.7F, 17.5F, -3.0F);

     this.RightArmSeg4.mirror = true;
     setRotation(this.RightArmSeg4, 0.0F, 0.0872665F, 0.0F);
     this.LeftArmSeg1 = new ModelRenderer(this, 0, 13);
     this.LeftArmSeg1.addBox(-0.5F, -0.5F, -4.0F, 1, 1, 4);
     this.LeftArmSeg1.setRotationPoint(4.7F, 17.5F, -3.0F);

     this.LeftArmSeg1.mirror = true;
     setRotation(this.LeftArmSeg1, 0.0F, -0.5235988F, 0.0F);
     this.LeftArmSeg3 = new ModelRenderer(this, 0, 13);
     this.LeftArmSeg3.addBox(1.0F, -0.5F, -8.0F, 1, 1, 3);
     this.LeftArmSeg3.setRotationPoint(4.7F, 17.5F, -3.0F);

     this.LeftArmSeg3.mirror = true;
     setRotation(this.LeftArmSeg3, 0.0F, -0.1745329F, 0.0F);
     this.RightArmSeg2 = new ModelRenderer(this, 0, 0);
     this.RightArmSeg2.addBox(-1.5F, -1.0F, -6.0F, 2, 2, 4);
     this.RightArmSeg2.setRotationPoint(-4.7F, 17.5F, -3.0F);

     this.RightArmSeg2.mirror = true;
     setRotation(this.RightArmSeg2, 0.0F, 0.3490659F, 0.0F);
     this.RightArmSeg1 = new ModelRenderer(this, 0, 13);
     this.RightArmSeg1.addBox(-0.5F, -0.5F, -4.0F, 1, 1, 4);
     this.RightArmSeg1.setRotationPoint(-4.7F, 17.5F, -3.0F);

     this.RightArmSeg1.mirror = true;
     setRotation(this.RightArmSeg1, 0.0F, 0.5235988F, 0.0F);
     this.LeftArmSeg5 = new ModelRenderer(this, 0, 13);
     this.LeftArmSeg5.addBox(2.4F, -0.5F, -12.0F, 1, 1, 3);
     this.LeftArmSeg5.setRotationPoint(4.7F, 17.5F, -3.0F);

     this.LeftArmSeg5.mirror = true;
     setRotation(this.LeftArmSeg5, 0.0F, 0.0F, 0.0F);
     this.LeftArmSeg2 = new ModelRenderer(this, 0, 0);
     this.LeftArmSeg2.addBox(-0.5F, -1.0F, -6.0F, 2, 2, 4);
     this.LeftArmSeg2.setRotationPoint(4.7F, 17.5F, -3.0F);

     this.LeftArmSeg2.mirror = true;
     setRotation(this.LeftArmSeg2, 0.0F, -0.3490659F, 0.0F);
     this.LeftClawTop = new ModelRenderer(this, 15, 15);
     this.LeftClawTop.addBox(1.8F, 4.7F, -15.0F, 2, 2, 5);
     this.LeftClawTop.setRotationPoint(4.7F, 17.5F, -3.0F);

     this.LeftClawTop.mirror = true;
     setRotation(this.LeftClawTop, -0.5410521F, 0.0F, 0.0F);
     this.RightArmSeg3 = new ModelRenderer(this, 0, 13);
     this.RightArmSeg3.addBox(-2.0F, -0.5F, -8.0F, 1, 1, 3);
     this.RightArmSeg3.setRotationPoint(-4.7F, 17.5F, -3.0F);

     this.RightArmSeg3.mirror = true;
     setRotation(this.RightArmSeg3, 0.0F, 0.1745329F, 0.0F);
     this.RightArmSeg5 = new ModelRenderer(this, 0, 13);
     this.RightArmSeg5.addBox(-3.6F, -0.5F, -12.0F, 1, 1, 3);
     this.RightArmSeg5.setRotationPoint(-4.7F, 17.5F, -3.0F);

     this.RightArmSeg5.mirror = true;
     setRotation(this.RightArmSeg5, 0.0F, 0.0F, 0.0F);
     this.LeftArmSeg4 = new ModelRenderer(this, 0, 0);
     this.LeftArmSeg4.addBox(1.1F, -1.0F, -10.5F, 2, 2, 4);
     this.LeftArmSeg4.setRotationPoint(4.7F, 17.5F, -3.0F);

     this.LeftArmSeg4.mirror = true;
     setRotation(this.LeftArmSeg4, 0.0F, -0.0872665F, 0.0F);
     this.LeftClawBase = new ModelRenderer(this, 0, 0);
     this.LeftClawBase.addBox(1.8F, -1.0F, -13.0F, 2, 2, 2);
     this.LeftClawBase.setRotationPoint(4.7F, 17.5F, -3.0F);

     this.LeftClawBase.mirror = true;
     setRotation(this.LeftClawBase, 0.0F, 0.0F, 0.0F);
     this.RightClawBase = new ModelRenderer(this, 0, 0);
     this.RightClawBase.addBox(-4.2F, -1.0F, -13.0F, 2, 2, 2);
     this.RightClawBase.setRotationPoint(-4.7F, 17.5F, -3.0F);

     this.RightClawBase.mirror = true;
     setRotation(this.RightClawBase, 0.0F, 0.0F, 0.0F);
     this.LeftClawLow = new ModelRenderer(this, 25, 25);
     this.LeftClawLow.addBox(1.8F, -4.3F, -15.0F, 2, 1, 4);
     this.LeftClawLow.setRotationPoint(4.7F, 17.5F, -3.0F);

     this.LeftClawLow.mirror = true;
     setRotation(this.LeftClawLow, 0.3490659F, 0.0F, 0.0F);
     this.RightClawTop = new ModelRenderer(this, 15, 15);
     this.RightClawTop.addBox(-4.2F, 4.7F, -15.0F, 2, 2, 5);
     this.RightClawTop.setRotationPoint(-4.7F, 17.5F, -3.0F);

     this.RightClawTop.mirror = true;
     setRotation(this.RightClawTop, -0.5410521F, 0.0F, 0.0F);
     this.RightClawLow = new ModelRenderer(this, 25, 25);
     this.RightClawLow.addBox(-4.2F, -4.3F, -15.0F, 2, 1, 4);
     this.RightClawLow.setRotationPoint(-4.7F, 17.5F, -3.0F);

     this.RightClawLow.mirror = true;
     setRotation(this.RightClawLow, 0.3490659F, 0.0F, 0.0F);
     this.LBLeg1 = new ModelRenderer(this, 0, 13);
     this.LBLeg1.addBox(0.5F, -0.5F, -0.5F, 3, 1, 1);
     this.LBLeg1.setRotationPoint(5.0F, 18.0F, 8.5F);

     this.LBLeg1.mirror = true;
     setRotation(this.LBLeg1, 0.0F, 0.0F, -0.4363323F);
     this.LBLeg3 = new ModelRenderer(this, 2, 0);
     this.LBLeg3.addBox(5.1F, -1.5F, -1.0F, 3, 1, 2);
     this.LBLeg3.setRotationPoint(5.0F, 18.0F, 8.5F);

     this.LBLeg3.mirror = true;
     setRotation(this.LBLeg3, 0.0F, 0.0F, -0.5759587F);
     this.RBLeg1 = new ModelRenderer(this, 0, 13);
     this.RBLeg1.addBox(-3.5F, -0.5F, -0.5F, 3, 1, 1);
     this.RBLeg1.setRotationPoint(-5.0F, 18.0F, 8.5F);

     this.RBLeg1.mirror = true;
     setRotation(this.RBLeg1, 0.0F, 0.0F, 0.4363323F);
     this.RBLeg3 = new ModelRenderer(this, 2, 0);
     this.RBLeg3.addBox(-8.1F, -1.5F, -1.0F, 3, 1, 2);
     this.RBLeg3.setRotationPoint(-5.0F, 18.0F, 8.5F);

     this.RBLeg3.mirror = true;
     setRotation(this.RBLeg3, 0.0F, 0.0F, 0.5759587F);
     this.LBLeg2 = new ModelRenderer(this, 0, 0);
     this.LBLeg2.addBox(2.5F, 0.5F, -1.0F, 3, 2, 2);
     this.LBLeg2.setRotationPoint(5.0F, 18.0F, 8.5F);

     this.LBLeg2.mirror = true;
     setRotation(this.LBLeg2, 0.0F, 0.0F, -0.9599311F);
     this.RBLeg2 = new ModelRenderer(this, 0, 0);
     this.RBLeg2.addBox(-5.5F, 0.5F, -1.0F, 3, 2, 2);
     this.RBLeg2.setRotationPoint(-5.0F, 18.0F, 8.5F);

     this.RBLeg2.mirror = true;
     setRotation(this.RBLeg2, 0.0F, 0.0F, 0.9599311F);
     this.LBLeg4 = new ModelRenderer(this, 0, 13);
     this.LBLeg4.addBox(5.0F, -3.0F, -0.5F, 1, 3, 1);
     this.LBLeg4.setRotationPoint(5.0F, 18.0F, 8.5F);

     this.LBLeg4.mirror = true;
     setRotation(this.LBLeg4, 0.0F, 0.0F, -0.2094395F);
     this.RBLeg4 = new ModelRenderer(this, 0, 13);
     this.RBLeg4.addBox(-6.0F, -3.0F, -0.5F, 1, 3, 1);
     this.RBLeg4.setRotationPoint(-5.0F, 18.0F, 8.5F);

     this.RBLeg4.mirror = true;
     setRotation(this.RBLeg4, 0.0F, 0.0F, 0.2094395F);
     this.RBLeg5 = new ModelRenderer(this, 0, 0);
     this.RBLeg5.addBox(-6.4F, -1.0F, -1.0F, 2, 6, 2);
     this.RBLeg5.setRotationPoint(-5.0F, 18.0F, 8.5F);

     this.RBLeg5.mirror = true;
     setRotation(this.RBLeg5, 0.0F, 0.0F, 0.1047198F);
     this.LBLeg6 = new ModelRenderer(this, 0, 13);
     this.LBLeg6.addBox(5.5F, 3.0F, -0.5F, 1, 3, 1);
     this.LBLeg6.setRotationPoint(5.0F, 18.0F, 8.5F);

     this.LBLeg6.mirror = true;
     setRotation(this.LBLeg6, 0.0F, 0.0F, 0.0F);
     this.RBLeg6 = new ModelRenderer(this, 0, 13);
     this.RBLeg6.addBox(-6.5F, 3.0F, -0.5F, 1, 3, 1);
     this.RBLeg6.setRotationPoint(-5.0F, 18.0F, 8.5F);

     this.RBLeg6.mirror = true;
     setRotation(this.RBLeg6, 0.0F, 0.0F, 0.0F);
     this.LBLeg5 = new ModelRenderer(this, 0, 0);
     this.LBLeg5.addBox(4.6F, -1.0F, -1.0F, 2, 6, 2);
     this.LBLeg5.setRotationPoint(5.0F, 18.0F, 8.5F);

     this.LBLeg5.mirror = true;
     setRotation(this.LBLeg5, 0.0F, 0.0F, -0.1047198F);
     this.RFLeg1 = new ModelRenderer(this, 0, 13);
     this.RFLeg1.addBox(-3.5F, -0.5F, -0.5F, 3, 1, 1);
     this.RFLeg1.setRotationPoint(-5.0F, 18.0F, 0.5F);

     this.RFLeg1.mirror = true;
     setRotation(this.RFLeg1, 0.0F, 0.0F, 0.4363323F);
     this.RFLeg2 = new ModelRenderer(this, 0, 0);
     this.RFLeg2.addBox(-5.5F, 0.5F, -1.0F, 3, 2, 2);
     this.RFLeg2.setRotationPoint(-5.0F, 18.0F, 0.5F);

     this.RFLeg2.mirror = true;
     setRotation(this.RFLeg2, 0.0F, 0.0F, 0.9599311F);
     this.RFLeg3 = new ModelRenderer(this, 2, 0);
     this.RFLeg3.addBox(-8.1F, -1.5F, -1.0F, 3, 1, 2);
     this.RFLeg3.setRotationPoint(-5.0F, 18.0F, 0.5F);

     this.RFLeg3.mirror = true;
     setRotation(this.RFLeg3, 0.0F, 0.0F, 0.5759587F);
     this.RFLeg4 = new ModelRenderer(this, 0, 13);
     this.RFLeg4.addBox(-6.0F, -3.0F, -0.5F, 1, 3, 1);
     this.RFLeg4.setRotationPoint(-5.0F, 18.0F, 0.5F);

     this.RFLeg4.mirror = true;
     setRotation(this.RFLeg4, 0.0F, 0.0F, 0.2094395F);
     this.RFLeg5 = new ModelRenderer(this, 0, 0);
     this.RFLeg5.addBox(-6.4F, -1.0F, -1.0F, 2, 6, 2);
     this.RFLeg5.setRotationPoint(-5.0F, 18.0F, 0.5F);

     this.RFLeg5.mirror = true;
     setRotation(this.RFLeg5, 0.0F, 0.0F, 0.1047198F);
     this.RFLeg6 = new ModelRenderer(this, 0, 13);
     this.RFLeg6.addBox(-6.5F, 3.0F, -0.5F, 1, 3, 1);
     this.RFLeg6.setRotationPoint(-5.0F, 18.0F, 0.5F);

     this.RFLeg6.mirror = true;
     setRotation(this.RFLeg6, 0.0F, 0.0F, 0.0F);
     this.RMLeg1 = new ModelRenderer(this, 0, 13);
     this.RMLeg1.addBox(-3.5F, -0.5F, -0.5F, 3, 1, 1);
     this.RMLeg1.setRotationPoint(-5.0F, 18.0F, 4.5F);

     this.RMLeg1.mirror = true;
     setRotation(this.RMLeg1, 0.0F, 0.0F, 0.4363323F);
     this.RMLeg2 = new ModelRenderer(this, 0, 0);
     this.RMLeg2.addBox(-5.5F, 0.5F, -1.0F, 3, 2, 2);
     this.RMLeg2.setRotationPoint(-5.0F, 18.0F, 4.5F);

     this.RMLeg2.mirror = true;
     setRotation(this.RMLeg2, 0.0F, 0.0F, 0.9599311F);
     this.RMLeg3 = new ModelRenderer(this, 2, 0);
     this.RMLeg3.addBox(-8.1F, -1.5F, -1.0F, 3, 1, 2);
     this.RMLeg3.setRotationPoint(-5.0F, 18.0F, 4.5F);

     this.RMLeg3.mirror = true;
     setRotation(this.RMLeg3, 0.0F, 0.0F, 0.5759587F);
     this.RMLeg4 = new ModelRenderer(this, 0, 13);
     this.RMLeg4.addBox(-6.0F, -3.0F, -0.5F, 1, 3, 1);
     this.RMLeg4.setRotationPoint(-5.0F, 18.0F, 4.5F);

     this.RMLeg4.mirror = true;
     setRotation(this.RMLeg4, 0.0F, 0.0F, 0.2094395F);
     this.RMLeg5 = new ModelRenderer(this, 0, 0);
     this.RMLeg5.addBox(-6.4F, -1.0F, -1.0F, 2, 6, 2);
     this.RMLeg5.setRotationPoint(-5.0F, 18.0F, 4.5F);

     this.RMLeg5.mirror = true;
     setRotation(this.RMLeg5, 0.0F, 0.0F, 0.1047198F);
     this.RMLeg6 = new ModelRenderer(this, 0, 13);
     this.RMLeg6.addBox(-6.5F, 3.0F, -0.5F, 1, 3, 1);
     this.RMLeg6.setRotationPoint(-5.0F, 18.0F, 4.5F);

     this.RMLeg6.mirror = true;
     setRotation(this.RMLeg6, 0.0F, 0.0F, 0.0F);
     this.LFLeg1 = new ModelRenderer(this, 0, 13);
     this.LFLeg1.addBox(0.5F, -0.5F, -0.5F, 3, 1, 1);
     this.LFLeg1.setRotationPoint(5.0F, 18.0F, 0.5F);

     this.LFLeg1.mirror = true;
     setRotation(this.LFLeg1, 0.0F, 0.0F, -0.4363323F);
     this.LFLeg2 = new ModelRenderer(this, 0, 0);
     this.LFLeg2.addBox(2.5F, 0.5F, -1.0F, 3, 2, 2);
     this.LFLeg2.setRotationPoint(5.0F, 18.0F, 0.5F);

     this.LFLeg2.mirror = true;
     setRotation(this.LFLeg2, 0.0F, 0.0F, -0.9599311F);
     this.LFLeg3 = new ModelRenderer(this, 2, 0);
     this.LFLeg3.addBox(5.1F, -1.5F, -1.0F, 3, 1, 2);
     this.LFLeg3.setRotationPoint(5.0F, 18.0F, 0.5F);

     this.LFLeg3.mirror = true;
     setRotation(this.LFLeg3, 0.0F, 0.0F, -0.5759587F);
     this.LFLeg4 = new ModelRenderer(this, 0, 13);
     this.LFLeg4.addBox(5.0F, -3.0F, -0.5F, 1, 3, 1);
     this.LFLeg4.setRotationPoint(5.0F, 18.0F, 0.5F);

     this.LFLeg4.mirror = true;
     setRotation(this.LFLeg4, 0.0F, 0.0F, -0.2094395F);
     this.LFLeg5 = new ModelRenderer(this, 0, 0);
     this.LFLeg5.addBox(4.6F, -1.0F, -1.0F, 2, 6, 2);
     this.LFLeg5.setRotationPoint(5.0F, 18.0F, 0.5F);

     this.LFLeg5.mirror = true;
     setRotation(this.LFLeg5, 0.0F, 0.0F, -0.1047198F);
     this.LFLeg6 = new ModelRenderer(this, 0, 13);
     this.LFLeg6.addBox(5.5F, 3.0F, -0.5F, 1, 3, 1);
     this.LFLeg6.setRotationPoint(5.0F, 18.0F, 0.5F);

     this.LFLeg6.mirror = true;
     setRotation(this.LFLeg6, 0.0F, 0.0F, 0.0F);
     this.LMLeg1 = new ModelRenderer(this, 0, 13);
     this.LMLeg1.addBox(0.5F, -0.5F, -0.5F, 3, 1, 1);
     this.LMLeg1.setRotationPoint(5.0F, 18.0F, 4.5F);

     this.LMLeg1.mirror = true;
     setRotation(this.LMLeg1, 0.0F, 0.0F, -0.4363323F);
     this.LMLeg2 = new ModelRenderer(this, 0, 0);
     this.LMLeg2.addBox(2.5F, 0.5F, -1.0F, 3, 2, 2);
     this.LMLeg2.setRotationPoint(5.0F, 18.0F, 4.5F);

     this.LMLeg2.mirror = true;
     setRotation(this.LMLeg2, 0.0F, 0.0F, -0.9599311F);
     this.LMLeg4 = new ModelRenderer(this, 0, 13);
     this.LMLeg4.addBox(5.0F, -3.0F, -0.5F, 1, 3, 1);
     this.LMLeg4.setRotationPoint(5.0F, 18.0F, 4.5F);

     this.LMLeg4.mirror = true;
     setRotation(this.LMLeg4, 0.0F, 0.0F, -0.2094395F);
     this.LMLeg3 = new ModelRenderer(this, 2, 0);
     this.LMLeg3.addBox(5.1F, -1.5F, -1.0F, 3, 1, 2);
     this.LMLeg3.setRotationPoint(5.0F, 18.0F, 4.5F);

     this.LMLeg3.mirror = true;
     setRotation(this.LMLeg3, 0.0F, 0.0F, -0.5759587F);
     this.LMLeg5 = new ModelRenderer(this, 0, 0);
     this.LMLeg5.addBox(4.6F, -1.0F, -1.0F, 2, 6, 2);
     this.LMLeg5.setRotationPoint(5.0F, 18.0F, 4.5F);

     this.LMLeg5.mirror = true;
     setRotation(this.LMLeg5, 0.0F, 0.0F, -0.1047198F);
     this.LMLeg6 = new ModelRenderer(this, 0, 13);
     this.LMLeg6.addBox(5.5F, 3.0F, -0.5F, 1, 3, 1);
     this.LMLeg6.setRotationPoint(5.0F, 18.0F, 4.5F);

     this.LMLeg6.mirror = true;
     setRotation(this.LMLeg6, 0.0F, 0.0F, 0.0F);
   }

   public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay,
        float red, float green, float blue, float alpha) {
        Nose.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        EyeLeft.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        HeadMid.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        HeadEnd.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        TailTuft.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        EyeRight.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        BodyTopLeft4.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        BodyTopRight4.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        BodyTopLeft1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        BodyTopRight1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        BodyTopRight2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        BodyTopLeft2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        BodyTopRight3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        BodyTopLeft3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        HeadBase.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        TailBase.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        BodyLow2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        BodyLow1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Spine5.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Spine1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Spine2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Spine3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Spine4.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        RightArmSeg4.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        LeftArmSeg1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        LeftArmSeg3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        RightArmSeg2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        RightArmSeg1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        LeftArmSeg5.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        LeftArmSeg2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        LeftClawTop.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        RightArmSeg3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        RightArmSeg5.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        LeftArmSeg4.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        LeftClawBase.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        RightClawBase.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        LeftClawLow.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        RightClawTop.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        RightClawLow.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        LBLeg1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        LBLeg3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        RBLeg1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        RBLeg3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        LBLeg2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        RBLeg2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        LBLeg4.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        RBLeg4.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        RBLeg5.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        LBLeg6.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        RBLeg6.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        LBLeg5.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        RFLeg1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        RFLeg2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        RFLeg3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        RFLeg4.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        RFLeg5.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        RFLeg6.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        RMLeg1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        RMLeg2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        RMLeg3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        RMLeg4.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        RMLeg5.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        RMLeg6.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        LFLeg1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        LFLeg2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        LFLeg3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        LFLeg4.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        LFLeg5.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        LFLeg6.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        LMLeg1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        LMLeg2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        LMLeg4.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        LMLeg3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        LMLeg5.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        LMLeg6.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }

   private void setRotation(ModelRenderer model, float x, float y, float z) {
     model.rotateAngleX = x;
     model.rotateAngleY = y;
     model.rotateAngleZ = z;
   }

   public void setRotationAngles(Entity entity, float limbSwing, float limbSwingAmount,
        float ageInTicks, float netHeadYaw, float headPitch) {
CaveFisherEntity.CustomEntity e = (CaveFisherEntity.CustomEntity) entity;
     RenderInfo r = null;

     float newangle = 0.0F, upangle = 0.0F, nextangle = 0.0F;
 
     
     float pi4 = 1.570795F;
 
     
     newangle = MathHelper.cos(ageInTicks * 2.0F * this.wingspeed) * 3.1415927F * 0.12F * limbSwingAmount;
     this.LFLeg1.rotateAngleY = newangle;
     this.LFLeg2.rotateAngleY = newangle;
     this.LFLeg3.rotateAngleY = newangle;
     this.LFLeg4.rotateAngleY = newangle;
     this.LFLeg5.rotateAngleY = newangle;
     this.LFLeg6.rotateAngleY = newangle;
     this.RFLeg1.rotateAngleY = -newangle;
     this.RFLeg2.rotateAngleY = -newangle;
     this.RFLeg3.rotateAngleY = -newangle;
     this.RFLeg4.rotateAngleY = -newangle;
     this.RFLeg5.rotateAngleY = -newangle;
     this.RFLeg6.rotateAngleY = -newangle;
     
     newangle = MathHelper.cos(ageInTicks * 2.0F * this.wingspeed - 1.0F * pi4) * 3.1415927F * 0.12F * limbSwingAmount;
     this.LMLeg1.rotateAngleY = newangle;
     this.LMLeg2.rotateAngleY = newangle;
     this.LMLeg3.rotateAngleY = newangle;
     this.LMLeg4.rotateAngleY = newangle;
     this.LMLeg5.rotateAngleY = newangle;
     this.LMLeg6.rotateAngleY = newangle;
     this.RMLeg1.rotateAngleY = -newangle;
     this.RMLeg2.rotateAngleY = -newangle;
     this.RMLeg3.rotateAngleY = -newangle;
     this.RMLeg4.rotateAngleY = -newangle;
     this.RMLeg5.rotateAngleY = -newangle;
     this.RMLeg6.rotateAngleY = -newangle;
     
     newangle = MathHelper.cos(ageInTicks * 2.0F * this.wingspeed - 2.0F * pi4) * 3.1415927F * 0.12F * limbSwingAmount;
     this.LBLeg1.rotateAngleY = newangle;
     this.LBLeg2.rotateAngleY = newangle;
     this.LBLeg3.rotateAngleY = newangle;
     this.LBLeg4.rotateAngleY = newangle;
     this.LBLeg5.rotateAngleY = newangle;
     this.LBLeg6.rotateAngleY = newangle;
     this.RBLeg1.rotateAngleY = -newangle;
     this.RBLeg2.rotateAngleY = -newangle;
     this.RBLeg3.rotateAngleY = -newangle;
     this.RBLeg4.rotateAngleY = -newangle;
     this.RBLeg5.rotateAngleY = -newangle;
     this.RBLeg6.rotateAngleY = -newangle;
 
 
 
 
     
     r = e.getRenderInfo();
 
     
     newangle = MathHelper.cos(ageInTicks * 3.0F * this.wingspeed) * 3.1415927F * 0.15F;
     nextangle = MathHelper.cos((ageInTicks + 0.1F) * 3.0F * this.wingspeed) * 3.1415927F * 0.15F;
 
 
 
     
     if (nextangle > 0.0F && newangle < 0.0F) {
       
       r.ri1 = 0;
       if (e.getAttacking() == 0) {
         r.ri1 = e.getModelRandom().nextInt(20);
         r.ri2 = e.getModelRandom().nextInt(25);
       } else {
         r.ri1 = e.getModelRandom().nextInt(4);
         r.ri2 = e.getModelRandom().nextInt(3);
       } 
     } 
 
     
     if (r.ri1 == 1 || r.ri1 == 3) {
       doLeftClaw(newangle);
       doRightClaw(newangle);
     } else {
       doLeftClaw(0.0F);
       doRightClaw(0.0F);
     } 
 
     
     e.setRenderInfo(r);
    }

   private void doLeftClaw(float angle) {
     this.LeftArmSeg1.rotateAngleX = Math.abs(angle);
     this.LeftArmSeg2.rotateAngleX = Math.abs(angle);
     this.LeftArmSeg3.rotateAngleX = Math.abs(angle);
     this.LeftArmSeg4.rotateAngleX = Math.abs(angle);
     this.LeftArmSeg5.rotateAngleX = Math.abs(angle);
     this.LeftClawBase.rotateAngleX = Math.abs(angle);
     this.LeftClawTop.rotateAngleX = Math.abs(angle) - 0.54F;
     this.LeftClawLow.rotateAngleX = Math.abs(angle) + 0.35F;
   }

   private void doRightClaw(float angle) {
     this.RightArmSeg1.rotateAngleX = Math.abs(angle);
     this.RightArmSeg2.rotateAngleX = Math.abs(angle);
     this.RightArmSeg3.rotateAngleX = Math.abs(angle);
     this.RightArmSeg4.rotateAngleX = Math.abs(angle);
     this.RightArmSeg5.rotateAngleX = Math.abs(angle);
     this.RightClawBase.rotateAngleX = Math.abs(angle);
     this.RightClawTop.rotateAngleX = Math.abs(angle) - 0.54F;
     this.RightClawLow.rotateAngleX = Math.abs(angle) + 0.35F;
   }
 }
}

