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
import net.mcreator.xillysorespawn.entity.GazelleEntity;
import net.mcreator.xillysorespawn.entity.OreSpawnLogic;

@OnlyIn(Dist.CLIENT)
public class GazelleRenderer {
    private static final String TEXTURE = "xillys_orespawn:textures/entities/gazelletexture.png";
    public static class ModelRegisterHandler {
        @SubscribeEvent @OnlyIn(Dist.CLIENT)
        public void registerModels(ModelRegistryEvent event) {
            RenderingRegistry.registerEntityRenderingHandler(GazelleEntity.entity, manager ->
                new MobRenderer(manager, new ModelGazelle(0.65F), 0.45F) {
                    @Override public ResourceLocation getEntityTexture(Entity entity) { return new ResourceLocation(TEXTURE); }
                    @Override protected void preRenderCallback(LivingEntity entity, MatrixStack stack, float partialTick) {
                        float modelScale = 1.0F;
                        if (entity.isChild()) modelScale *= 0.5F;
                        stack.scale(modelScale, modelScale, modelScale);
                    }
                });
        }
    }

public static class ModelGazelle extends EntityModel<Entity> {
   private float wingspeed = 1.0F;

   private final ModelRenderer Chest;

   private final ModelRenderer lfleg1;

   private final ModelRenderer lrleg2;
   private final ModelRenderer lrleg1;
   private final ModelRenderer rfleg3;
   private final ModelRenderer rrleg2;
   private final ModelRenderer rrleg3;
   private final ModelRenderer rfleg2;
   private final ModelRenderer lrleg4;
   private final ModelRenderer tail;
   private final ModelRenderer lear;
   private final ModelRenderer rrleg1;
   private final ModelRenderer rfleg1;
   private final ModelRenderer lrleg3;
   private final ModelRenderer lfleg2;
   private final ModelRenderer rrleg5;
   private final ModelRenderer rrleg4;
   private final ModelRenderer lfleg3;
   private final ModelRenderer rfleg4;
   private final ModelRenderer lfleg4;
   private final ModelRenderer lrleg5;
   private final ModelRenderer Body;
   private final ModelRenderer neck;
   private final ModelRenderer la3;
   private final ModelRenderer throatfluff;
   private final ModelRenderer rear;
   private final ModelRenderer head;
   private final ModelRenderer ra1;
   private final ModelRenderer la1;
   private final ModelRenderer la2;
   private final ModelRenderer ra2;
   private final ModelRenderer ra3;
   private final ModelRenderer nose;
   private final ModelRenderer mouth;

   public ModelGazelle(float f1) {
     this.wingspeed = f1;

     this.textureWidth = 64;
     this.textureHeight = 64;

     this.Chest = new ModelRenderer(this, 12, 57);
     this.Chest.addBox(0.0F, 0.0F, 0.0F, 5, 2, 3);
     this.Chest.setRotationPoint(-2.5F, 8.0F, -6.0F);

     this.Chest.mirror = true;
     setRotation(this.Chest, 2.342252F, 0.0F, 0.0F);
     this.lfleg1 = new ModelRenderer(this, 0, 31);
     this.lfleg1.addBox(0.0F, 0.0F, 0.0F, 2, 6, 3);
     this.lfleg1.setRotationPoint(2.0F, 6.0F, -6.0F);

     this.lfleg1.mirror = true;
     setRotation(this.lfleg1, 0.2974289F, 0.0F, 0.0F);
     this.lrleg2 = new ModelRenderer(this, 16, 49);
     this.lrleg2.addBox(0.0F, 5.0F, -1.0F, 2, 2, 6);
     this.lrleg2.setRotationPoint(2.0F, 4.0F, 3.0F);

     this.lrleg2.mirror = true;
     setRotation(this.lrleg2, 0.1858931F, 0.0F, 0.0F);
     this.lrleg1 = new ModelRenderer(this, 23, 31);
     this.lrleg1.addBox(0.0F, 0.0F, 0.0F, 2, 6, 3);
     this.lrleg1.setRotationPoint(2.0F, 4.0F, 3.0F);

     this.lrleg1.mirror = true;
     setRotation(this.lrleg1, 0.0F, 0.0F, 0.0F);
     this.rfleg3 = new ModelRenderer(this, 40, 49);
     this.rfleg3.addBox(0.0F, 10.0F, 6.0F, 2, 6, 2);
     this.rfleg3.setRotationPoint(-4.0F, 5.966667F, -6.0F);

     this.rfleg3.mirror = true;
     setRotation(this.rfleg3, -0.4089647F, 0.0F, 0.0F);
     this.rrleg2 = new ModelRenderer(this, 16, 49);
     this.rrleg2.addBox(0.0F, 5.0F, -1.0F, 2, 2, 6);
     this.rrleg2.setRotationPoint(-4.0F, 4.0F, 3.0F);

     this.rrleg2.mirror = true;
     setRotation(this.rrleg2, 0.1858931F, 0.0F, 0.0F);
     this.rrleg3 = new ModelRenderer(this, 32, 11);
     this.rrleg3.addBox(0.0F, 4.0F, 5.0F, 2, 12, 2);
     this.rrleg3.setRotationPoint(-4.0F, 3.966667F, 3.0F);

     this.rrleg3.mirror = true;
     setRotation(this.rrleg3, -0.0743572F, 0.0F, 0.0F);
     this.rfleg2 = new ModelRenderer(this, 24, 11);
     this.rfleg2.addBox(0.0F, 2.0F, 2.0F, 2, 12, 2);
     this.rfleg2.setRotationPoint(-4.0F, 5.966667F, -6.0F);

     this.rfleg2.mirror = true;
     setRotation(this.rfleg2, -0.0743572F, 0.0F, 0.0F);
     this.lrleg4 = new ModelRenderer(this, 32, 49);
     this.lrleg4.addBox(0.0F, 11.0F, 9.5F, 2, 6, 2);
     this.lrleg4.setRotationPoint(2.0F, 4.0F, 3.0F);

     this.lrleg4.mirror = true;
     setRotation(this.lrleg4, -0.4089647F, 0.0F, 0.0F);
     this.tail = new ModelRenderer(this, 0, 49);
     this.tail.addBox(0.0F, 0.0F, 0.0F, 4, 4, 4);
     this.tail.setRotationPoint(-2.0F, 0.0F, 4.0F);

     this.tail.mirror = true;
     setRotation(this.tail, 0.9666439F, 0.0F, 0.0F);
     this.lear = new ModelRenderer(this, 18, 0);
     this.lear.addBox(-5.0F, -3.0F, 2.0F, 3, 2, 1);
     this.lear.setRotationPoint(0.0F, -9.0F, -6.0F);

     this.lear.mirror = true;
     setRotation(this.lear, -0.1047198F, 1.570796F, 0.0F);
     this.rrleg1 = new ModelRenderer(this, 23, 31);
     this.rrleg1.addBox(0.0F, 0.0F, 0.0F, 2, 6, 3);
     this.rrleg1.setRotationPoint(-4.0F, 4.0F, 3.0F);

     this.rrleg1.mirror = true;
     setRotation(this.rrleg1, 0.0F, 0.0F, 0.0F);
     this.rfleg1 = new ModelRenderer(this, 0, 31);
     this.rfleg1.addBox(0.0F, 0.0F, 0.0F, 2, 6, 3);
     this.rfleg1.setRotationPoint(-4.0F, 6.0F, -6.0F);

     this.rfleg1.mirror = true;
     setRotation(this.rfleg1, 0.2974289F, 0.0F, 0.0F);
     this.lrleg3 = new ModelRenderer(this, 32, 11);
     this.lrleg3.addBox(0.0F, 4.0F, 5.0F, 2, 12, 2);
     this.lrleg3.setRotationPoint(2.0F, 3.966667F, 3.0F);

     this.lrleg3.mirror = true;
     setRotation(this.lrleg3, -0.0743572F, 0.0F, 0.0F);
     this.lfleg2 = new ModelRenderer(this, 24, 11);
     this.lfleg2.addBox(0.0F, 2.0F, 2.0F, 2, 12, 2);
     this.lfleg2.setRotationPoint(2.0F, 5.966667F, -6.0F);

     this.lfleg2.mirror = true;
     setRotation(this.lfleg2, -0.0743572F, 0.0F, 0.0F);
     this.rrleg5 = new ModelRenderer(this, 0, 58);
     this.rrleg5.addBox(-0.5F, 17.0F, 2.0F, 3, 3, 3);
     this.rrleg5.setRotationPoint(-4.0F, 4.0F, 3.0F);

     this.rrleg5.mirror = true;
     setRotation(this.rrleg5, 0.0F, 0.0F, 0.0F);
     this.rrleg5.mirror = false;
     this.rrleg4 = new ModelRenderer(this, 32, 49);
     this.rrleg4.addBox(0.0F, 11.0F, 9.5F, 2, 6, 2);
     this.rrleg4.setRotationPoint(-4.0F, 3.966667F, 3.0F);

     this.rrleg4.mirror = true;
     setRotation(this.rrleg4, -0.4089647F, 0.0F, 0.0F);
     this.lfleg3 = new ModelRenderer(this, 40, 49);
     this.lfleg3.addBox(0.0F, 10.0F, 6.0F, 2, 6, 2);
     this.lfleg3.setRotationPoint(2.0F, 5.966667F, -6.0F);

     this.lfleg3.mirror = true;
     setRotation(this.lfleg3, -0.4089647F, 0.0F, 0.0F);
     this.rfleg4 = new ModelRenderer(this, 0, 58);
     this.rfleg4.addBox(-0.5F, 15.0F, -1.0F, 3, 3, 3);
     this.rfleg4.setRotationPoint(-4.0F, 6.0F, -6.0F);

     this.rfleg4.mirror = true;
     setRotation(this.rfleg4, 0.0F, 0.0F, 0.0F);
     this.lfleg4 = new ModelRenderer(this, 0, 58);
     this.lfleg4.addBox(-0.5F, 15.0F, -1.0F, 3, 3, 3);
     this.lfleg4.setRotationPoint(2.0F, 6.0F, -6.0F);

     this.lfleg4.mirror = true;
     setRotation(this.lfleg4, 0.0F, 0.0F, 0.0F);
     this.lrleg5 = new ModelRenderer(this, 0, 58);
     this.lrleg5.addBox(-0.5F, 17.0F, 2.0F, 3, 3, 3);
     this.lrleg5.setRotationPoint(2.0F, 4.0F, 3.0F);

     this.lrleg5.mirror = true;
     setRotation(this.lrleg5, 0.0F, 0.0F, 0.0F);
     this.Body = new ModelRenderer(this, 0, 12);
     this.Body.addBox(0.0F, 0.0F, 0.0F, 6, 6, 13);
     this.Body.setRotationPoint(-3.0F, 2.0F, -7.0F);

     this.Body.mirror = true;
     setRotation(this.Body, 0.2230717F, 0.0F, 0.0F);
     this.neck = new ModelRenderer(this, 0, 31);
     this.neck.addBox(0.0F, 0.0F, 0.0F, 5, 5, 13);
     this.neck.setRotationPoint(-2.5F, 6.0F, -8.0F);

     this.neck.mirror = true;
     setRotation(this.neck, 1.524323F, 0.0F, 0.0F);
     this.la3 = new ModelRenderer(this, 4, 12);
     this.la3.addBox(0.5F, -12.5F, 3.0F, 1, 5, 1);
     this.la3.setRotationPoint(0.0F, -9.0F, -6.0F);

     this.la3.mirror = true;
     setRotation(this.la3, -0.3346075F, 0.0F, 0.0F);
     this.throatfluff = new ModelRenderer(this, 36, 41);
     this.throatfluff.addBox(0.0F, -2.0F, 0.0F, 4, 3, 5);
     this.throatfluff.setRotationPoint(-2.0F, 0.0F, -8.0F);

     this.throatfluff.mirror = true;
     setRotation(this.throatfluff, 1.07818F, 0.0F, 0.0F);
     this.rear = new ModelRenderer(this, 18, 0);
     this.rear.addBox(-5.0F, -3.0F, -3.0F, 3, 2, 1);
     this.rear.setRotationPoint(0.0F, -9.0F, -6.0F);

     this.rear.mirror = true;
     setRotation(this.rear, 0.1047198F, 1.570796F, 0.0F);
     this.head = new ModelRenderer(this, 0, 0);
     this.head.addBox(-3.0F, -3.0F, -3.0F, 6, 6, 6);
     this.head.setRotationPoint(0.0F, -9.0F, -6.0F);

     this.head.mirror = true;
     setRotation(this.head, 0.0F, 0.0F, 0.0F);
     this.ra1 = new ModelRenderer(this, 0, 12);
     this.ra1.addBox(-1.5F, -5.0F, 0.0F, 1, 4, 1);
     this.ra1.setRotationPoint(0.0F, -9.0F, -6.0F);

     this.ra1.mirror = true;
     setRotation(this.ra1, -0.3717861F, 0.0F, 0.0F);
     this.la1 = new ModelRenderer(this, 0, 12);
     this.la1.addBox(0.5F, -5.0F, 0.0F, 1, 4, 1);
     this.la1.setRotationPoint(0.0F, -9.0F, -6.0F);

     this.la1.mirror = true;
     setRotation(this.la1, -0.3717861F, 0.0F, 0.0F);
     this.la2 = new ModelRenderer(this, 0, 17);
     this.la2.addBox(0.5F, -8.5F, -3.0F, 1, 5, 1);
     this.la2.setRotationPoint(0.0F, -9.0F, -6.0F);

     this.la2.mirror = true;
     setRotation(this.la2, -1.041001F, 0.0F, 0.0F);
     this.ra2 = new ModelRenderer(this, 0, 17);
     this.ra2.addBox(-1.5F, -8.5F, -3.0F, 1, 5, 1);
     this.ra2.setRotationPoint(0.0F, -9.0F, -6.0F);

     this.ra2.mirror = true;
     setRotation(this.ra2, -1.041001F, 0.0F, 0.0F);
     this.ra3 = new ModelRenderer(this, 4, 12);
     this.ra3.addBox(-1.5F, -12.5F, 3.0F, 1, 5, 1);
     this.ra3.setRotationPoint(0.0F, -9.0F, -6.0F);

     this.ra3.mirror = true;
     setRotation(this.ra3, -0.3346075F, 0.0F, 0.0F);
     this.nose = new ModelRenderer(this, 24, 0);
     this.nose.addBox(-2.5F, 0.0F, -7.0F, 5, 3, 5);
     this.nose.setRotationPoint(0.0F, -9.0F, -6.0F);

     this.nose.mirror = true;
     setRotation(this.nose, 0.0F, 0.0F, 0.0F);
     this.mouth = new ModelRenderer(this, 28, 57);
     this.mouth.addBox(-2.0F, 2.0F, -6.0F, 4, 2, 5);
     this.mouth.setRotationPoint(0.0F, -9.0F, -6.0F);

     this.mouth.mirror = true;
     setRotation(this.mouth, 0.0F, 0.0F, 0.0F);
   }

   public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay,
        float red, float green, float blue, float alpha) {
        Chest.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        lfleg1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        lrleg2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        lrleg1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rfleg3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rrleg2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rrleg3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rfleg2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        lrleg4.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        tail.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        lear.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rrleg1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rfleg1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        lrleg3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        lfleg2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rrleg5.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rrleg4.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        lfleg3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rfleg4.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        lfleg4.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        lrleg5.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Body.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        neck.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        la3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        throatfluff.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rear.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        head.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        ra1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        la1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        la2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        ra2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        ra3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        nose.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        mouth.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }

   private void setRotation(ModelRenderer model, float x, float y, float z) {
     model.rotateAngleX = x;
     model.rotateAngleY = y;
     model.rotateAngleZ = z;
   }

   public void setRotationAngles(Entity entity, float limbSwing, float limbSwingAmount,
        float ageInTicks, float netHeadYaw, float headPitch) {
// Reset shared model state before applying this frame's legacy animation.
this.Chest.setRotationPoint(-2.5F, 8.0F, -6.0F);
this.lfleg1.setRotationPoint(2.0F, 6.0F, -6.0F);
this.lrleg2.setRotationPoint(2.0F, 4.0F, 3.0F);
this.lrleg1.setRotationPoint(2.0F, 4.0F, 3.0F);
this.rfleg3.setRotationPoint(-4.0F, 5.966667F, -6.0F);
this.rrleg2.setRotationPoint(-4.0F, 4.0F, 3.0F);
this.rrleg3.setRotationPoint(-4.0F, 3.966667F, 3.0F);
this.rfleg2.setRotationPoint(-4.0F, 5.966667F, -6.0F);
this.lrleg4.setRotationPoint(2.0F, 4.0F, 3.0F);
this.tail.setRotationPoint(-2.0F, 0.0F, 4.0F);
this.lear.setRotationPoint(0.0F, -9.0F, -6.0F);
this.rrleg1.setRotationPoint(-4.0F, 4.0F, 3.0F);
this.rfleg1.setRotationPoint(-4.0F, 6.0F, -6.0F);
this.lrleg3.setRotationPoint(2.0F, 3.966667F, 3.0F);
this.lfleg2.setRotationPoint(2.0F, 5.966667F, -6.0F);
this.rrleg5.setRotationPoint(-4.0F, 4.0F, 3.0F);
this.rrleg4.setRotationPoint(-4.0F, 3.966667F, 3.0F);
this.lfleg3.setRotationPoint(2.0F, 5.966667F, -6.0F);
this.rfleg4.setRotationPoint(-4.0F, 6.0F, -6.0F);
this.lfleg4.setRotationPoint(2.0F, 6.0F, -6.0F);
this.lrleg5.setRotationPoint(2.0F, 4.0F, 3.0F);
this.Body.setRotationPoint(-3.0F, 2.0F, -7.0F);
this.neck.setRotationPoint(-2.5F, 6.0F, -8.0F);
this.la3.setRotationPoint(0.0F, -9.0F, -6.0F);
this.throatfluff.setRotationPoint(-2.0F, 0.0F, -8.0F);
this.rear.setRotationPoint(0.0F, -9.0F, -6.0F);
this.head.setRotationPoint(0.0F, -9.0F, -6.0F);
this.ra1.setRotationPoint(0.0F, -9.0F, -6.0F);
this.la1.setRotationPoint(0.0F, -9.0F, -6.0F);
this.la2.setRotationPoint(0.0F, -9.0F, -6.0F);
this.ra2.setRotationPoint(0.0F, -9.0F, -6.0F);
this.ra3.setRotationPoint(0.0F, -9.0F, -6.0F);
this.nose.setRotationPoint(0.0F, -9.0F, -6.0F);
this.mouth.setRotationPoint(0.0F, -9.0F, -6.0F);
setRotation(this.Chest, 2.342252F, 0.0F, 0.0F);
setRotation(this.lfleg1, 0.2974289F, 0.0F, 0.0F);
setRotation(this.lrleg2, 0.1858931F, 0.0F, 0.0F);
setRotation(this.lrleg1, 0.0F, 0.0F, 0.0F);
setRotation(this.rfleg3, -0.4089647F, 0.0F, 0.0F);
setRotation(this.rrleg2, 0.1858931F, 0.0F, 0.0F);
setRotation(this.rrleg3, -0.0743572F, 0.0F, 0.0F);
setRotation(this.rfleg2, -0.0743572F, 0.0F, 0.0F);
setRotation(this.lrleg4, -0.4089647F, 0.0F, 0.0F);
setRotation(this.tail, 0.9666439F, 0.0F, 0.0F);
setRotation(this.lear, -0.1047198F, 1.570796F, 0.0F);
setRotation(this.rrleg1, 0.0F, 0.0F, 0.0F);
setRotation(this.rfleg1, 0.2974289F, 0.0F, 0.0F);
setRotation(this.lrleg3, -0.0743572F, 0.0F, 0.0F);
setRotation(this.lfleg2, -0.0743572F, 0.0F, 0.0F);
setRotation(this.rrleg5, 0.0F, 0.0F, 0.0F);
setRotation(this.rrleg4, -0.4089647F, 0.0F, 0.0F);
setRotation(this.lfleg3, -0.4089647F, 0.0F, 0.0F);
setRotation(this.rfleg4, 0.0F, 0.0F, 0.0F);
setRotation(this.lfleg4, 0.0F, 0.0F, 0.0F);
setRotation(this.lrleg5, 0.0F, 0.0F, 0.0F);
setRotation(this.Body, 0.2230717F, 0.0F, 0.0F);
setRotation(this.neck, 1.524323F, 0.0F, 0.0F);
setRotation(this.la3, -0.3346075F, 0.0F, 0.0F);
setRotation(this.throatfluff, 1.07818F, 0.0F, 0.0F);
setRotation(this.rear, 0.1047198F, 1.570796F, 0.0F);
setRotation(this.head, 0.0F, 0.0F, 0.0F);
setRotation(this.ra1, -0.3717861F, 0.0F, 0.0F);
setRotation(this.la1, -0.3717861F, 0.0F, 0.0F);
setRotation(this.la2, -1.041001F, 0.0F, 0.0F);
setRotation(this.ra2, -1.041001F, 0.0F, 0.0F);
setRotation(this.ra3, -0.3346075F, 0.0F, 0.0F);
setRotation(this.nose, 0.0F, 0.0F, 0.0F);
setRotation(this.mouth, 0.0F, 0.0F, 0.0F);

     GazelleEntity.CustomEntity g = (GazelleEntity.CustomEntity) entity;
     float hf = 0.0F;
     float newangle = 0.0F;

     if (limbSwingAmount > 0.1D) {
       newangle = MathHelper.cos(ageInTicks * 1.1F * this.wingspeed) * 3.1415927F * 0.12F * limbSwingAmount;
     } else {
       newangle = 0.0F;
     } 
     
     this.lfleg1.rotateAngleX = 0.297F + newangle;
     this.lfleg2.rotateAngleX = -0.074F + newangle;
     this.lfleg3.rotateAngleX = -0.409F + newangle;
     this.lfleg4.rotateAngleX = newangle;
     this.rfleg1.rotateAngleX = 0.297F - newangle;
     this.rfleg2.rotateAngleX = -0.074F - newangle;
     this.rfleg3.rotateAngleX = -0.409F - newangle;
     this.rfleg4.rotateAngleX = -newangle;
     
     this.lrleg1.rotateAngleX = -newangle;
     this.lrleg2.rotateAngleX = 0.185F - newangle;
     this.lrleg3.rotateAngleX = -0.074F - newangle;
     this.lrleg4.rotateAngleX = -0.409F - newangle;
     this.lrleg5.rotateAngleX = -newangle;
     this.rrleg1.rotateAngleX = newangle;
     this.rrleg2.rotateAngleX = 0.185F + newangle;
     this.rrleg3.rotateAngleX = -0.074F + newangle;
     this.rrleg4.rotateAngleX = -0.409F + newangle;
     this.rrleg5.rotateAngleX = newangle;
     
     newangle = MathHelper.cos(ageInTicks * 0.5F) * 3.1415927F * 0.02F;
     this.head.rotateAngleY = (float)Math.toRadians(netHeadYaw) * 0.45F;
     this.nose.rotateAngleY = this.head.rotateAngleY;
     this.mouth.rotateAngleY = this.head.rotateAngleY;
     this.lear.rotateAngleY = 1.57F + this.head.rotateAngleY + newangle;
     this.rear.rotateAngleY = 1.57F + this.head.rotateAngleY + newangle;
     this.la1.rotateAngleY = this.head.rotateAngleY;
     this.la2.rotateAngleY = this.head.rotateAngleY;
     this.la3.rotateAngleY = this.head.rotateAngleY;
     this.ra1.rotateAngleY = this.head.rotateAngleY;
     this.ra2.rotateAngleY = this.head.rotateAngleY;
     this.ra3.rotateAngleY = this.head.rotateAngleY;
     
     if (!g.isChildModel()) {
       this.tail.rotateAngleX = 1.0F + MathHelper.cos(ageInTicks * 0.1F) * 3.1415927F * 0.06F;
     }
    }
 }
}