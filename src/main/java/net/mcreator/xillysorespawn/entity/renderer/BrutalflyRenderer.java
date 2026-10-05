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
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.mcreator.xillysorespawn.entity.BrutalflyEntity;

@OnlyIn(Dist.CLIENT)
public class BrutalflyRenderer {
    private static final String TEXTURE = "xillys_orespawn:textures/entities/brutalflytexture.png";
    private static final ResourceLocation FIRE_TEXTURE = new ResourceLocation("xillys_orespawn:textures/entities/brutalfly_overlay2.png");
    public static class ModelRegisterHandler {
        @SubscribeEvent @OnlyIn(Dist.CLIENT)
        public void registerModels(ModelRegistryEvent event) {
            RenderingRegistry.registerEntityRenderingHandler(BrutalflyEntity.entity, BrutalflyMobRenderer::new);
        }
    }

    private static class BrutalflyMobRenderer extends MobRenderer<BrutalflyEntity.CustomEntity, ModelBrutalfly> {
        BrutalflyMobRenderer(EntityRendererManager manager) {
            super(manager, new ModelBrutalfly(1.0F), 4.5F);
            addLayer(new BrutalflyFireLayer(this));
        }
        @Override public ResourceLocation getEntityTexture(BrutalflyEntity.CustomEntity entity) {
            return new ResourceLocation(TEXTURE);
        }
        @Override protected void preRenderCallback(BrutalflyEntity.CustomEntity entity, MatrixStack stack, float partialTick) {
            stack.scale(9.0F, 9.0F, 9.0F);
        }
    }

    private static class BrutalflyFireLayer extends LayerRenderer<BrutalflyEntity.CustomEntity, ModelBrutalfly> {
        private static final RenderType FIRE = RenderType.getEyes(FIRE_TEXTURE);
        BrutalflyFireLayer(BrutalflyMobRenderer renderer) { super(renderer); }
        @Override public void render(MatrixStack stack, IRenderTypeBuffer buffers, int packedLight,
                BrutalflyEntity.CustomEntity entity, float limbSwing, float limbSwingAmount,
                float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
            getEntityModel().render(stack, buffers.getBuffer(FIRE), 15728640,
                LivingRenderer.getPackedOverlay(entity, 0.0F), 0.8F, 0.45F, 0.15F, 1.0F);
        }
    }

public static class ModelBrutalfly extends EntityModel<BrutalflyEntity.CustomEntity>
 {
   private final ModelRenderer body;
   private final ModelRenderer leftwing;
   private final ModelRenderer rightwing;
   private final ModelRenderer leftwing2;
   private final ModelRenderer rightwing2;
   private final ModelRenderer leftwing3;
   private final ModelRenderer rightwing3;
   private final ModelRenderer head;
   private final ModelRenderer leftwing4;
   private final ModelRenderer rightwing4;
   private final ModelRenderer leftwing5;
   private final ModelRenderer leftwing6;
   private final ModelRenderer rightwing5;
   private final ModelRenderer rightwing6;
   private float wingspeed = 1.0F;

   public ModelBrutalfly(float f1) {
     this.textureWidth = 64;
     this.textureHeight = 32;
     this.wingspeed = f1;

     this.body = new ModelRenderer(this, 21, 19);
     this.body.addBox(0.0F, 0.0F, -4.0F, 1, 1, 8);
     this.body.setRotationPoint(0.0F, 17.0F, 0.0F);

     this.body.mirror = true;
     setRotation(this.body, 0.0F, 0.0F, 0.0F);
     this.leftwing = new ModelRenderer(this, 43, 24);
     this.leftwing.addBox(0.0F, 0.0F, -4.0F, 5, 1, 5);
     this.leftwing.setRotationPoint(1.0F, 17.0F, 0.0F);

     this.leftwing.mirror = true;
     setRotation(this.leftwing, 0.0F, 0.0F, 0.0F);
     this.rightwing = new ModelRenderer(this, 43, 17);
     this.rightwing.addBox(-5.0F, 0.0F, -4.0F, 5, 1, 5);
     this.rightwing.setRotationPoint(0.0F, 17.0F, 0.0F);

     this.rightwing.mirror = true;
     setRotation(this.rightwing, 0.0F, 0.0F, 0.0F);
     this.leftwing2 = new ModelRenderer(this, 0, 0);
     this.leftwing2.addBox(1.0F, 0.0F, -6.0F, 6, 1, 7);
     this.leftwing2.setRotationPoint(1.0F, 17.0F, 0.0F);

     this.leftwing2.mirror = true;
     setRotation(this.leftwing2, 0.0F, 0.0F, 0.0F);
     this.rightwing2 = new ModelRenderer(this, 29, 0);
     this.rightwing2.addBox(-7.0F, 0.0F, -6.0F, 6, 1, 7);
     this.rightwing2.setRotationPoint(0.0F, 17.0F, 0.0F);

     this.rightwing2.mirror = true;
     setRotation(this.rightwing2, 0.0F, 0.0F, 0.0F);
     this.leftwing3 = new ModelRenderer(this, 0, 9);
     this.leftwing3.addBox(0.0F, 0.0F, 1.0F, 5, 1, 5);
     this.leftwing3.setRotationPoint(1.0F, 17.0F, 0.0F);

     this.leftwing3.mirror = true;
     setRotation(this.leftwing3, 0.0F, 0.0F, 0.0F);
     this.rightwing3 = new ModelRenderer(this, 27, 9);
     this.rightwing3.addBox(-5.0F, 0.0F, 1.0F, 5, 1, 5);
     this.rightwing3.setRotationPoint(0.0F, 17.0F, 0.0F);

     this.rightwing3.mirror = true;
     setRotation(this.rightwing3, 0.0F, 0.0F, 0.0F);
     this.head = new ModelRenderer(this, 21, 11);
     this.head.addBox(0.0F, 0.0F, -6.0F, 1, 1, 1);
     this.head.setRotationPoint(0.0F, 17.0F, 1.0F);

     this.head.mirror = true;
     setRotation(this.head, 0.0F, 0.0F, 0.0F);
     this.leftwing4 = new ModelRenderer(this, 2, 24);
     this.leftwing4.addBox(0.0F, 0.0F, 6.0F, 2, 1, 7);
     this.leftwing4.setRotationPoint(1.0F, 17.0F, 0.0F);

     this.leftwing4.mirror = true;
     setRotation(this.leftwing4, 0.0F, 0.0F, 0.0F);
     this.rightwing4 = new ModelRenderer(this, 2, 16);
     this.rightwing4.addBox(-2.0F, 0.0F, 6.0F, 2, 1, 7);
     this.rightwing4.setRotationPoint(0.0F, 17.0F, 0.0F);

     this.rightwing4.mirror = true;
     setRotation(this.rightwing4, 0.0F, 0.0F, 0.0F);
     this.leftwing5 = new ModelRenderer(this, 21, 16);
     this.leftwing5.addBox(1.0F, 0.0F, -7.0F, 1, 1, 1);
     this.leftwing5.setRotationPoint(1.0F, 17.0F, 0.0F);

     this.leftwing5.mirror = true;
     setRotation(this.leftwing5, 0.0F, 0.0F, 0.0F);
     this.leftwing6 = new ModelRenderer(this, 50, 10);
     this.leftwing6.addBox(7.0F, 0.0F, -6.0F, 2, 1, 1);
     this.leftwing6.setRotationPoint(1.0F, 17.0F, 0.0F);

     this.leftwing6.mirror = true;
     setRotation(this.leftwing6, 0.0F, 0.0F, 0.0F);
     this.rightwing5 = new ModelRenderer(this, 27, 16);
     this.rightwing5.addBox(-2.0F, 0.0F, -7.0F, 1, 1, 1);
     this.rightwing5.setRotationPoint(0.0F, 17.0F, 0.0F);

     this.rightwing5.mirror = true;
     setRotation(this.rightwing5, 0.0F, 0.0F, 0.0F);
     this.rightwing6 = new ModelRenderer(this, 50, 13);
     this.rightwing6.addBox(-9.0F, 0.0F, -6.0F, 2, 1, 1);
     this.rightwing6.setRotationPoint(0.0F, 17.0F, 0.0F);

     this.rightwing6.mirror = true;
     setRotation(this.rightwing6, 0.0F, 0.0F, 0.0F);
   }

   public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay,
        float red, float green, float blue, float alpha) {
        body.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        leftwing.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rightwing.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        leftwing2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rightwing2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        leftwing3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rightwing3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        head.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        leftwing4.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rightwing4.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }

   private void setRotation(ModelRenderer model, float x, float y, float z) {
     model.rotateAngleX = x;
     model.rotateAngleY = y;
     model.rotateAngleZ = z;
   }

   public void setRotationAngles(BrutalflyEntity.CustomEntity entity, float limbSwing, float limbSwingAmount,
        float ageInTicks, float netHeadYaw, float headPitch) {
this.rightwing.rotateAngleZ = MathHelper.cos(ageInTicks * 1.3F * this.wingspeed) * 3.1415927F * 0.25F;
     this.rightwing2.rotateAngleZ = this.rightwing.rotateAngleZ;
     this.rightwing3.rotateAngleZ = this.rightwing.rotateAngleZ;
     this.rightwing4.rotateAngleZ = this.rightwing.rotateAngleZ;
     this.rightwing5.rotateAngleZ = this.rightwing.rotateAngleZ;
     this.rightwing6.rotateAngleZ = this.rightwing.rotateAngleZ;
     
     this.leftwing.rotateAngleZ = -this.rightwing.rotateAngleZ;
     this.leftwing2.rotateAngleZ = -this.rightwing.rotateAngleZ;
     this.leftwing3.rotateAngleZ = -this.rightwing.rotateAngleZ;
     this.leftwing4.rotateAngleZ = -this.rightwing.rotateAngleZ;
     this.leftwing5.rotateAngleZ = -this.rightwing.rotateAngleZ;
     this.leftwing6.rotateAngleZ = -this.rightwing.rotateAngleZ;
    }
 }
}

