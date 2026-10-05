package net.mcreator.xillysorespawn.entity.renderer;

import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;
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
import net.mcreator.xillysorespawn.entity.KyuubiEntity;
import net.mcreator.xillysorespawn.entity.OreSpawnLogic;

@OnlyIn(Dist.CLIENT)
public class KyuubiRenderer {
    private static final String TEXTURE = "xillys_orespawn:textures/entities/kyuubi.png";
    public static class ModelRegisterHandler {
        @SubscribeEvent @OnlyIn(Dist.CLIENT)
        public void registerModels(ModelRegistryEvent event) {
            RenderingRegistry.registerEntityRenderingHandler(KyuubiEntity.entity, KyuubiMobRenderer::new);
        }
    }

    private static class KyuubiMobRenderer extends MobRenderer<KyuubiEntity.CustomEntity, ModelKyuubi> {
        KyuubiMobRenderer(EntityRendererManager manager) {
            super(manager, new ModelKyuubi(0.5F), 0.1F);
            this.addLayer(new KyuubiFireLayer(this));
        }
        @Override public ResourceLocation getEntityTexture(KyuubiEntity.CustomEntity entity) {
            return new ResourceLocation(TEXTURE);
        }
    }

    private static class KyuubiFireLayer extends LayerRenderer<KyuubiEntity.CustomEntity, ModelKyuubi> {
        KyuubiFireLayer(KyuubiMobRenderer renderer) { super(renderer); }
        @Override public void render(MatrixStack stack, IRenderTypeBuffer buffers, int packedLight,
                KyuubiEntity.CustomEntity entity, float limbSwing, float limbSwingAmount,
                float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
            IVertexBuilder fire = buffers.getBuffer(RenderType.getEntityTranslucent(new ResourceLocation(TEXTURE)));
            getEntityModel().renderFire(stack, fire, packedLight,
                LivingRenderer.getPackedOverlay(entity, 0.0F), 1.0F, 0.75F, 0.35F, 0.62F);
        }
    }

public static class ModelKyuubi extends EntityModel<KyuubiEntity.CustomEntity>
 {
   private float wingspeed = 1.0F;

   private final ModelRenderer rtHorn5;
   private final ModelRenderer lfHorn5;
   private final ModelRenderer tail9;
   private final ModelRenderer tail8;
   private final ModelRenderer tail7;
   private final ModelRenderer tail6;
   private final ModelRenderer tail5;
   private final ModelRenderer tail2;
   private final ModelRenderer tail1;
   private final ModelRenderer tail0;
   private final ModelRenderer lfLegLower;
   private final ModelRenderer rtLegLower;
   private final ModelRenderer head;
   private final ModelRenderer chest;
   private final ModelRenderer lfArmUpper;
   private final ModelRenderer rtArmLower;
   private final ModelRenderer lfLegUpper;
   private final ModelRenderer rtLegUpper;
   private final ModelRenderer body;
   private final ModelRenderer rtArmUpper;
   private final ModelRenderer lfArmLower;
   private final ModelRenderer tail3;
   private final ModelRenderer tail4;
   private final ModelRenderer lfHorn2;
   private final ModelRenderer rtHorn1;
   private final ModelRenderer rtHorn2;
   private final ModelRenderer lfHorn1;
   private final ModelRenderer lfHorn3;
   private final ModelRenderer rtHorn3;
   private final ModelRenderer lfHorn4;
   private final ModelRenderer rtHorn4;
   private final ModelRenderer headFire;
   private final ModelRenderer lfArmUpperFire;
   private final ModelRenderer chestFire;
   private final ModelRenderer bodyFire;
   private final ModelRenderer lfArmLowerFire;
   private final ModelRenderer rtArmUpperFire;
   private final ModelRenderer rtArmLowerFire;
   private final ModelRenderer lfLegUppperFire;
   private final ModelRenderer lfLegLowerFire;
   private final ModelRenderer rtLegUpperFire;
   private final ModelRenderer rtLegLowerFire;

   public ModelKyuubi(float f1) {
     this.wingspeed = f1;

     this.textureWidth = 512;
     this.textureHeight = 256;

     this.rtHorn5 = new ModelRenderer(this, 56, 8);
     this.rtHorn5.addBox(-0.5F, -0.5F, -2.0F, 1, 1, 2);
     this.rtHorn5.setRotationPoint(3.0F, -11.5F, -7.0F);

     this.rtHorn5.mirror = false;
     setRotation(this.rtHorn5, -0.4461433F, 0.0F, 0.0F);
     this.lfHorn5 = new ModelRenderer(this, 56, 24);
     this.lfHorn5.addBox(-0.5F, -0.5F, -2.0F, 1, 1, 2);
     this.lfHorn5.setRotationPoint(-3.0F, -11.5F, -7.0F);

     this.lfHorn5.mirror = false;
     setRotation(this.lfHorn5, -0.4461433F, 0.0F, 0.0F);
     this.tail9 = new ModelRenderer(this, 145, 47);
     this.tail9.addBox(-0.5F, -0.5F, -1.0F, 1, 1, 1);
     this.tail9.setRotationPoint(0.0F, 9.0F, -26.0F);

     this.tail9.mirror = false;
     setRotation(this.tail9, 2.007645F, 0.0F, 0.0F);
     this.tail8 = new ModelRenderer(this, 135, 45);
     this.tail8.addBox(-1.0F, -1.0F, -2.0F, 2, 2, 2);
     this.tail8.setRotationPoint(0.0F, 7.0F, -25.75F);

     this.tail8.mirror = false;
     setRotation(this.tail8, 1.524323F, 0.0F, 0.0F);
     this.tail7 = new ModelRenderer(this, 122, 44);
     this.tail7.addBox(-1.5F, -1.5F, -3.0F, 3, 3, 3);
     this.tail7.setRotationPoint(0.0F, 5.0F, -24.0F);

     this.tail7.mirror = false;
     setRotation(this.tail7, 0.8922867F, 0.0F, 0.0F);
     this.tail6 = new ModelRenderer(this, 105, 43);
     this.tail6.addBox(-2.0F, -2.0F, -4.0F, 4, 4, 4);
     this.tail6.setRotationPoint(0.0F, 3.0F, -21.0F);

     this.tail6.mirror = false;
     setRotation(this.tail6, 0.6320364F, 0.0F, 0.0F);
     this.tail5 = new ModelRenderer(this, 84, 42);
     this.tail5.addBox(-2.5F, -2.5F, -5.0F, 5, 5, 5);
     this.tail5.setRotationPoint(0.0F, 2.0F, -17.0F);

     this.tail5.mirror = false;
     setRotation(this.tail5, 0.2230717F, 0.0F, 0.0F);
     this.tail2 = new ModelRenderer(this, 20, 43);
     this.tail2.addBox(-2.0F, -2.0F, -5.0F, 4, 4, 5);
     this.tail2.setRotationPoint(0.0F, 10.0F, -7.0F);

     this.tail2.mirror = false;
     setRotation(this.tail2, -0.7807508F, 0.0F, 0.0F);
     this.tail1 = new ModelRenderer(this, 9, 36);
     this.tail1.addBox(-1.5F, -1.5F, -3.0F, 3, 3, 3);
     this.tail1.setRotationPoint(0.0F, 10.0F, -4.333333F);

     this.tail1.mirror = false;
     setRotation(this.tail1, -0.2602503F, 0.0F, 0.0F);
     this.tail0 = new ModelRenderer(this, 0, 46);
     this.tail0.addBox(-1.0F, -1.0F, -3.0F, 4, 4, 3);
     this.tail0.setRotationPoint(-1.0F, 9.0F, -2.0F);

     this.tail0.mirror = false;
     setRotation(this.tail0, 0.0F, 0.0F, 0.0F);
     this.lfLegLower = new ModelRenderer(this, 205, 55);
     this.lfLegLower.addBox(-2.0F, 0.0F, -3.0F, 4, 6, 4);
     this.lfLegLower.setRotationPoint(-3.0F, 18.0F, 2.0F);

     this.lfLegLower.mirror = false;
     setRotation(this.lfLegLower, -0.4461433F, 0.0F, 0.0F);
     this.rtLegLower = new ModelRenderer(this, 149, 53);
     this.rtLegLower.addBox(-2.0F, -1.0F, -3.0F, 4, 7, 4);
     this.rtLegLower.setRotationPoint(3.0F, 18.0F, 4.0F);

     this.rtLegLower.mirror = false;
     setRotation(this.rtLegLower, -0.1487144F, 0.0F, 0.0F);
     this.head = new ModelRenderer(this, 168, 0);
     this.head.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8);
     this.head.setRotationPoint(0.0F, 0.0F, 6.0F);

     this.head.mirror = false;
     setRotation(this.head, 0.2230705F, 3.141593F, 0.0F);
     this.chest = new ModelRenderer(this, 170, 17);
     this.chest.addBox(-4.0F, 0.0F, -2.0F, 8, 7, 6);
     this.chest.setRotationPoint(0.0F, 0.0F, 5.0F);

     this.chest.mirror = false;
     setRotation(this.chest, -0.8551081F, 0.0F, 0.0F);
     this.lfArmUpper = new ModelRenderer(this, 205, 16);
     this.lfArmUpper.addBox(-3.0F, -2.0F, -2.0F, 4, 7, 4);
     this.lfArmUpper.setRotationPoint(-5.0F, 2.0F, 3.0F);

     this.lfArmUpper.mirror = false;
     setRotation(this.lfArmUpper, 0.0F, 0.0F, 0.3020292F);
     this.rtArmLower = new ModelRenderer(this, 136, 29);
     this.rtArmLower.addBox(-2.0F, 0.0F, -2.0F, 4, 7, 4);
     this.rtArmLower.setRotationPoint(7.0F, 6.0F, 2.0F);

     this.rtArmLower.mirror = false;
     setRotation(this.rtArmLower, 0.4833219F, 0.0F, 0.0F);
     this.lfLegUpper = new ModelRenderer(this, 188, 46);
     this.lfLegUpper.addBox(-2.0F, 0.0F, -2.0F, 4, 7, 4);
     this.lfLegUpper.setRotationPoint(-2.0F, 12.0F, 0.0F);

     this.lfLegUpper.mirror = false;
     setRotation(this.lfLegUpper, 0.260246F, 0.0F, 0.2602503F);
     this.rtLegUpper = new ModelRenderer(this, 168, 46);
     this.rtLegUpper.addBox(-2.0F, 0.0F, -2.0F, 4, 7, 4);
     this.rtLegUpper.setRotationPoint(2.0F, 12.0F, 0.0F);

     this.rtLegUpper.mirror = false;
     setRotation(this.rtLegUpper, 0.5948578F, 0.0F, -0.260246F);
     this.body = new ModelRenderer(this, 170, 31);
     this.body.addBox(-4.0F, 0.0F, -3.0F, 8, 7, 6);
     this.body.setRotationPoint(0.0F, 5.0F, 1.0F);

     this.body.mirror = false;
     setRotation(this.body, -0.2974289F, 0.0F, 0.0F);
     this.rtArmUpper = new ModelRenderer(this, 142, 16);
     this.rtArmUpper.addBox(-1.0F, -2.0F, -2.0F, 4, 7, 4);
     this.rtArmUpper.setRotationPoint(5.0F, 2.0F, 2.0F);

     this.rtArmUpper.mirror = false;
     setRotation(this.rtArmUpper, 0.0F, 0.0F, -0.302028F);
     this.lfArmLower = new ModelRenderer(this, 208, 31);
     this.lfArmLower.addBox(-2.0F, 0.0F, -2.0F, 4, 7, 4);
     this.lfArmLower.setRotationPoint(-7.0F, 6.0F, 2.0F);

     this.lfArmLower.mirror = false;
     setRotation(this.lfArmLower, 0.4833219F, 0.0F, 0.0F);
     this.tail3 = new ModelRenderer(this, 38, 42);
     this.tail3.addBox(-2.5F, -2.0F, -5.0F, 5, 5, 5);
     this.tail3.setRotationPoint(0.0F, 6.5F, -10.0F);

     this.tail3.mirror = false;
     setRotation(this.tail3, -0.96F, 0.0F, 0.0F);
     this.tail4 = new ModelRenderer(this, 59, 41);
     this.tail4.addBox(-3.0F, -3.0F, -6.0F, 6, 6, 6);
     this.tail4.setRotationPoint(0.0F, 3.0F, -12.0F);

     this.tail4.mirror = false;
     setRotation(this.tail4, -0.22F, 0.0F, 0.0F);
     this.lfHorn2 = new ModelRenderer(this, 13, 5);
     this.lfHorn2.addBox(-2.0F, -2.0F, -4.0F, 4, 4, 4);
     this.lfHorn2.setRotationPoint(-3.0F, -10.0F, 2.0F);

     this.lfHorn2.mirror = false;
     setRotation(this.lfHorn2, -0.2230705F, 0.0F, 0.0F);
     this.rtHorn1 = new ModelRenderer(this, 0, 22);
     this.rtHorn1.addBox(-1.5F, -1.5F, -3.0F, 3, 3, 3);
     this.rtHorn1.setRotationPoint(3.0F, -8.7F, 4.0F);

     this.rtHorn1.mirror = false;
     setRotation(this.rtHorn1, -0.5576792F, 0.0F, 0.0F);
     this.rtHorn2 = new ModelRenderer(this, 13, 21);
     this.rtHorn2.addBox(-2.0F, -2.0F, -4.0F, 4, 4, 4);
     this.rtHorn2.setRotationPoint(3.0F, -10.0F, 2.0F);

     this.rtHorn2.mirror = false;
     setRotation(this.rtHorn2, -0.2230705F, 0.0F, 0.0F);
     this.lfHorn1 = new ModelRenderer(this, 0, 6);
     this.lfHorn1.addBox(-1.5F, -1.5F, -3.0F, 3, 3, 3);
     this.lfHorn1.setRotationPoint(-3.0F, -8.7F, 4.0F);

     this.lfHorn1.mirror = false;
     setRotation(this.lfHorn1, -0.5576792F, 0.0F, 0.0F);
     this.lfHorn3 = new ModelRenderer(this, 31, 6);
     this.lfHorn3.addBox(-1.5F, -1.5F, -3.0F, 3, 3, 3);
     this.lfHorn3.setRotationPoint(-3.0F, -11.0F, -2.0F);

     this.lfHorn3.mirror = false;
     setRotation(this.lfHorn3, -0.0371786F, 0.0F, 0.0F);
     this.rtHorn3 = new ModelRenderer(this, 31, 22);
     this.rtHorn3.addBox(-1.5F, -1.5F, -3.0F, 3, 3, 3);
     this.rtHorn3.setRotationPoint(3.0F, -11.0F, -2.0F);

     this.rtHorn3.mirror = false;
     setRotation(this.rtHorn3, -0.0371786F, 0.0F, 0.0F);
     this.lfHorn4 = new ModelRenderer(this, 45, 23);
     this.lfHorn4.addBox(-1.0F, -1.0F, -2.0F, 2, 2, 2);
     this.lfHorn4.setRotationPoint(-3.0F, -11.0F, -5.0F);

     this.lfHorn4.mirror = false;
     setRotation(this.lfHorn4, -0.2230717F, 0.0F, 0.0F);
     this.rtHorn4 = new ModelRenderer(this, 45, 7);
     this.rtHorn4.addBox(-1.0F, -1.0F, -2.0F, 2, 2, 2);
     this.rtHorn4.setRotationPoint(3.0F, -11.0F, -5.0F);

     this.rtHorn4.mirror = false;
     setRotation(this.rtHorn4, -0.2230717F, 0.0F, 0.0F);
     this.headFire = new ModelRenderer(this, 168, 84);
     this.headFire.addBox(-5.0F, -10.0F, -5.0F, 10, 10, 10);
     this.headFire.setRotationPoint(0.0F, 1.0F, 6.0F);

     this.headFire.mirror = false;
     setRotation(this.headFire, -0.2230717F, 0.0F, 0.0F);
     this.lfArmUpperFire = new ModelRenderer(this, 209, 108);
     this.lfArmUpperFire.addBox(-6.0F, -1.0F, -3.0F, 6, 9, 6);
     this.lfArmUpperFire.setRotationPoint(-3.0F, 1.0F, 3.0F);

     this.lfArmUpperFire.mirror = false;
     setRotation(this.lfArmUpperFire, 0.0F, 0.0F, 0.3020292F);
     this.chestFire = new ModelRenderer(this, 170, 105);
     this.chestFire.addBox(-5.0F, 0.0F, -3.0F, 10, 9, 8);
     this.chestFire.setRotationPoint(0.0F, -1.0F, 6.0F);

     this.chestFire.mirror = false;
     setRotation(this.chestFire, -0.8551081F, 0.0F, 0.0F);
     this.bodyFire = new ModelRenderer(this, 170, 125);
     this.bodyFire.addBox(-5.0F, 0.0F, -4.0F, 10, 9, 8);
     this.bodyFire.setRotationPoint(0.0F, 4.0F, 1.0F);

     this.bodyFire.mirror = false;
     setRotation(this.bodyFire, -0.2974289F, 0.0F, 0.0F);
     this.lfArmLowerFire = new ModelRenderer(this, 208, 126);
     this.lfArmLowerFire.addBox(-3.0F, 0.0F, -3.0F, 6, 9, 6);
     this.lfArmLowerFire.setRotationPoint(-7.333333F, 5.0F, 1.5F);

     this.lfArmLowerFire.mirror = false;
     setRotation(this.lfArmLowerFire, 0.4833219F, 0.0F, 0.0F);
     this.rtArmUpperFire = new ModelRenderer(this, 142, 105);
     this.rtArmUpperFire.addBox(-3.0F, 0.0F, -3.0F, 6, 9, 6);
     this.rtArmUpperFire.setRotationPoint(5.0F, -1.0F, 2.0F);

     this.rtArmUpperFire.mirror = false;
     setRotation(this.rtArmUpperFire, 0.0F, 0.0F, -0.302028F);
     this.rtArmLowerFire = new ModelRenderer(this, 136, 122);
     this.rtArmLowerFire.addBox(-3.0F, 0.0F, -3.0F, 6, 9, 6);
     this.rtArmLowerFire.setRotationPoint(7.0F, 5.0F, 1.0F);

     this.rtArmLowerFire.mirror = false;
     setRotation(this.rtArmLowerFire, 0.4833219F, 0.0F, 0.0F);
     this.lfLegUppperFire = new ModelRenderer(this, 188, 146);
     this.lfLegUppperFire.addBox(-3.0F, 0.0F, -3.0F, 6, 9, 6);
     this.lfLegUppperFire.setRotationPoint(-2.0F, 11.0F, 0.0F);

     this.lfLegUppperFire.mirror = false;
     setRotation(this.lfLegUppperFire, 0.260246F, 0.0F, 0.2602503F);
     this.lfLegLowerFire = new ModelRenderer(this, 205, 163);
     this.lfLegLowerFire.addBox(-3.0F, 0.0F, -3.0F, 6, 8, 6);
     this.lfLegLowerFire.setRotationPoint(-3.0F, 16.0F, 2.0F);

     this.lfLegLowerFire.mirror = false;
     setRotation(this.lfLegLowerFire, -0.4461433F, 0.0F, 0.0F);
     this.rtLegUpperFire = new ModelRenderer(this, 160, 146);
     this.rtLegUpperFire.addBox(-3.0F, 0.0F, -4.0F, 6, 9, 6);
     this.rtLegUpperFire.setRotationPoint(2.0F, 11.0F, 0.0F);

     this.rtLegUpperFire.mirror = false;
     setRotation(this.rtLegUpperFire, 0.5948578F, 0.0F, -0.260246F);
     this.rtLegLowerFire = new ModelRenderer(this, 150, 167);
     this.rtLegLowerFire.addBox(-3.0F, 0.0F, -3.0F, 6, 9, 6);
     this.rtLegLowerFire.setRotationPoint(3.0F, 15.0F, 4.0F);

     this.rtLegLowerFire.mirror = false;
     setRotation(this.rtLegLowerFire, -0.1487144F, 0.0F, 0.0F);
   }

   public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay,
        float red, float green, float blue, float alpha) {
        matrixStack.push();
        matrixStack.rotate(Vector3f.YP.rotationDegrees(180.0F));
        rtHorn5.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        lfHorn5.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        tail9.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        tail8.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        tail7.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        tail6.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        tail5.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        tail2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        tail1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        tail0.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        lfLegLower.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rtLegLower.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        head.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        chest.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        lfArmUpper.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rtArmLower.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        lfLegUpper.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rtLegUpper.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        body.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rtArmUpper.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        lfArmLower.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        tail3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        tail4.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        lfHorn2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rtHorn1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rtHorn2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        lfHorn1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        lfHorn3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rtHorn3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        lfHorn4.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rtHorn4.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        matrixStack.pop();
    }

   public void renderFire(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay,
        float red, float green, float blue, float alpha) {
     matrixStack.push();
     matrixStack.rotate(Vector3f.YP.rotationDegrees(180.0F));
     ModelRenderer[] fireParts = {this.headFire,this.lfArmUpperFire,this.chestFire,this.bodyFire,
        this.lfArmLowerFire,this.rtArmUpperFire,this.rtArmLowerFire,this.lfLegUppperFire,
        this.lfLegLowerFire,this.rtLegUpperFire,this.rtLegLowerFire};
     for (ModelRenderer part : fireParts)
       part.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
     matrixStack.pop();
   }

   private void setRotation(ModelRenderer model, float x, float y, float z) {
     model.rotateAngleX = x;
     model.rotateAngleY = y;
     model.rotateAngleZ = z;
   }

   public void setRotationAngles(KyuubiEntity.CustomEntity entity, float limbSwing, float limbSwingAmount,
        float ageInTicks, float netHeadYaw, float headPitch) {
// Reset shared model state before applying this frame's legacy animation.
this.rtHorn5.setRotationPoint(3.0F, -11.5F, -7.0F);
this.lfHorn5.setRotationPoint(-3.0F, -11.5F, -7.0F);
this.tail9.setRotationPoint(0.0F, 9.0F, -26.0F);
this.tail8.setRotationPoint(0.0F, 7.0F, -25.75F);
this.tail7.setRotationPoint(0.0F, 5.0F, -24.0F);
this.tail6.setRotationPoint(0.0F, 3.0F, -21.0F);
this.tail5.setRotationPoint(0.0F, 2.0F, -17.0F);
this.tail2.setRotationPoint(0.0F, 10.0F, -7.0F);
this.tail1.setRotationPoint(0.0F, 10.0F, -4.333333F);
this.tail0.setRotationPoint(-1.0F, 9.0F, -2.0F);
this.lfLegLower.setRotationPoint(-3.0F, 18.0F, 2.0F);
this.rtLegLower.setRotationPoint(3.0F, 18.0F, 4.0F);
this.head.setRotationPoint(0.0F, 0.0F, 6.0F);
this.chest.setRotationPoint(0.0F, 0.0F, 5.0F);
this.lfArmUpper.setRotationPoint(-5.0F, 2.0F, 3.0F);
this.rtArmLower.setRotationPoint(7.0F, 6.0F, 2.0F);
this.lfLegUpper.setRotationPoint(-2.0F, 12.0F, 0.0F);
this.rtLegUpper.setRotationPoint(2.0F, 12.0F, 0.0F);
this.body.setRotationPoint(0.0F, 5.0F, 1.0F);
this.rtArmUpper.setRotationPoint(5.0F, 2.0F, 2.0F);
this.lfArmLower.setRotationPoint(-7.0F, 6.0F, 2.0F);
this.tail3.setRotationPoint(0.0F, 6.5F, -10.0F);
this.tail4.setRotationPoint(0.0F, 3.0F, -12.0F);
this.lfHorn2.setRotationPoint(-3.0F, -10.0F, 2.0F);
this.rtHorn1.setRotationPoint(3.0F, -8.7F, 4.0F);
this.rtHorn2.setRotationPoint(3.0F, -10.0F, 2.0F);
this.lfHorn1.setRotationPoint(-3.0F, -8.7F, 4.0F);
this.lfHorn3.setRotationPoint(-3.0F, -11.0F, -2.0F);
this.rtHorn3.setRotationPoint(3.0F, -11.0F, -2.0F);
this.lfHorn4.setRotationPoint(-3.0F, -11.0F, -5.0F);
this.rtHorn4.setRotationPoint(3.0F, -11.0F, -5.0F);
this.headFire.setRotationPoint(0.0F, 1.0F, 6.0F);
this.lfArmUpperFire.setRotationPoint(-3.0F, 1.0F, 3.0F);
this.chestFire.setRotationPoint(0.0F, -1.0F, 6.0F);
this.bodyFire.setRotationPoint(0.0F, 4.0F, 1.0F);
this.lfArmLowerFire.setRotationPoint(-7.333333F, 5.0F, 1.5F);
this.rtArmUpperFire.setRotationPoint(5.0F, -1.0F, 2.0F);
this.rtArmLowerFire.setRotationPoint(7.0F, 5.0F, 1.0F);
this.lfLegUppperFire.setRotationPoint(-2.0F, 11.0F, 0.0F);
this.lfLegLowerFire.setRotationPoint(-3.0F, 16.0F, 2.0F);
this.rtLegUpperFire.setRotationPoint(2.0F, 11.0F, 0.0F);
this.rtLegLowerFire.setRotationPoint(3.0F, 15.0F, 4.0F);
setRotation(this.rtHorn5, -0.4461433F, 0.0F, 0.0F);
setRotation(this.lfHorn5, -0.4461433F, 0.0F, 0.0F);
setRotation(this.tail9, 2.007645F, 0.0F, 0.0F);
setRotation(this.tail8, 1.524323F, 0.0F, 0.0F);
setRotation(this.tail7, 0.8922867F, 0.0F, 0.0F);
setRotation(this.tail6, 0.6320364F, 0.0F, 0.0F);
setRotation(this.tail5, 0.2230717F, 0.0F, 0.0F);
setRotation(this.tail2, -0.7807508F, 0.0F, 0.0F);
setRotation(this.tail1, -0.2602503F, 0.0F, 0.0F);
setRotation(this.tail0, 0.0F, 0.0F, 0.0F);
setRotation(this.lfLegLower, -0.4461433F, 0.0F, 0.0F);
setRotation(this.rtLegLower, -0.1487144F, 0.0F, 0.0F);
setRotation(this.head, 0.2230705F, 3.141593F, 0.0F);
setRotation(this.chest, -0.8551081F, 0.0F, 0.0F);
setRotation(this.lfArmUpper, 0.0F, 0.0F, 0.3020292F);
setRotation(this.rtArmLower, 0.4833219F, 0.0F, 0.0F);
setRotation(this.lfLegUpper, 0.260246F, 0.0F, 0.2602503F);
setRotation(this.rtLegUpper, 0.5948578F, 0.0F, -0.260246F);
setRotation(this.body, -0.2974289F, 0.0F, 0.0F);
setRotation(this.rtArmUpper, 0.0F, 0.0F, -0.302028F);
setRotation(this.lfArmLower, 0.4833219F, 0.0F, 0.0F);
setRotation(this.tail3, -0.96F, 0.0F, 0.0F);
setRotation(this.tail4, -0.22F, 0.0F, 0.0F);
setRotation(this.lfHorn2, -0.2230705F, 0.0F, 0.0F);
setRotation(this.rtHorn1, -0.5576792F, 0.0F, 0.0F);
setRotation(this.rtHorn2, -0.2230705F, 0.0F, 0.0F);
setRotation(this.lfHorn1, -0.5576792F, 0.0F, 0.0F);
setRotation(this.lfHorn3, -0.0371786F, 0.0F, 0.0F);
setRotation(this.rtHorn3, -0.0371786F, 0.0F, 0.0F);
setRotation(this.lfHorn4, -0.2230717F, 0.0F, 0.0F);
setRotation(this.rtHorn4, -0.2230717F, 0.0F, 0.0F);
setRotation(this.headFire, -0.2230717F, 0.0F, 0.0F);
setRotation(this.lfArmUpperFire, 0.0F, 0.0F, 0.3020292F);
setRotation(this.chestFire, -0.8551081F, 0.0F, 0.0F);
setRotation(this.bodyFire, -0.2974289F, 0.0F, 0.0F);
setRotation(this.lfArmLowerFire, 0.4833219F, 0.0F, 0.0F);
setRotation(this.rtArmUpperFire, 0.0F, 0.0F, -0.302028F);
setRotation(this.rtArmLowerFire, 0.4833219F, 0.0F, 0.0F);
setRotation(this.lfLegUppperFire, 0.260246F, 0.0F, 0.2602503F);
setRotation(this.lfLegLowerFire, -0.4461433F, 0.0F, 0.0F);
setRotation(this.rtLegUpperFire, 0.5948578F, 0.0F, -0.260246F);
setRotation(this.rtLegLowerFire, -0.1487144F, 0.0F, 0.0F);

     KyuubiEntity.CustomEntity e = (KyuubiEntity.CustomEntity) entity;

     float newangle = 0.0F;
 
 
 
 
 
     
     if (limbSwingAmount > 0.1D) {
       newangle = MathHelper.cos(ageInTicks * 1.1F * this.wingspeed) * 3.1415927F * 0.2F * limbSwingAmount;
     } else {
       newangle = 0.0F;
     } 
     
     this.rtLegUpper.rotateAngleX = 0.59F + newangle;
     this.rtLegUpperFire.rotateAngleX = 0.59F + newangle;
     this.rtLegLower.rotateAngleX = -0.15F + newangle;
     this.rtLegLowerFire.rotateAngleX = -0.15F + newangle;
     this.rtLegLower.rotationPointZ = (float)(Math.sin(this.rtLegUpperFire.rotateAngleX) * 8.0D);
     this.rtLegLowerFire.rotationPointZ = (float)(Math.sin(this.rtLegUpperFire.rotateAngleX) * 8.0D);
     
     this.lfLegUpper.rotateAngleX = 0.26F - newangle;
     this.lfLegUppperFire.rotateAngleX = 0.26F - newangle;
     this.lfLegLower.rotateAngleX = -0.44F - newangle;
     this.lfLegLowerFire.rotateAngleX = -0.44F - newangle;
     this.lfLegLower.rotationPointZ = (float)(Math.sin(this.lfLegUppperFire.rotateAngleX) * 8.0D);
     this.lfLegLowerFire.rotationPointZ = (float)(Math.sin(this.lfLegUppperFire.rotateAngleX) * 8.0D);
 
 
     
     newangle = MathHelper.cos(ageInTicks * 1.1F * this.wingspeed) * 3.1415927F * 0.08F * limbSwingAmount;
     
     newangle += MathHelper.cos(ageInTicks * 0.5F * this.wingspeed) * 3.1415927F * 0.01F;
     
     this.rtArmUpper.rotateAngleX = newangle;
     this.rtArmUpperFire.rotateAngleX = newangle;
     this.rtArmLower.rotateAngleX = 0.48F + newangle;
     this.rtArmLowerFire.rotateAngleX = 0.48F + newangle;
     this.rtArmLower.rotationPointZ = (float)(Math.sin(this.rtArmUpperFire.rotateAngleX) * 8.0D);
     this.rtArmLowerFire.rotationPointZ = (float)(Math.sin(this.rtArmUpperFire.rotateAngleX) * 8.0D);
     
     this.lfArmUpper.rotateAngleX = -newangle;
     this.lfArmUpperFire.rotateAngleX = -newangle;
     this.lfArmLower.rotateAngleX = 0.48F - newangle;
     this.lfArmLowerFire.rotateAngleX = 0.48F - newangle;
     this.lfArmLower.rotationPointZ = (float)(Math.sin(this.lfArmUpperFire.rotateAngleX) * 8.0D);
     this.lfArmLowerFire.rotationPointZ = (float)(Math.sin(this.lfArmUpperFire.rotateAngleX) * 8.0D);
 
 
     
     float pi4 = 0.7853975F;
     
     this.head.rotateAngleY = (float)Math.toRadians(netHeadYaw) + pi4 * 4.0F;
     this.headFire.rotateAngleY = (float)Math.toRadians(netHeadYaw);
 
 
 
     
     float fc = (float)Math.cos((this.headFire.rotateAngleY + pi4));
     float fs = (float)Math.sin((this.headFire.rotateAngleY + pi4));
 
     
     this.headFire.rotationPointZ -= fc * 3.6F;
     this.headFire.rotationPointX -= fs * 3.6F;
     this.lfHorn1.rotateAngleY = this.headFire.rotateAngleY + 0.244F + MathHelper.cos(ageInTicks * 1.3F * this.wingspeed) * 3.1415927F * 0.1F;
     this.lfHorn1.rotationPointZ -= (float)Math.cos(this.lfHorn1.rotateAngleY) * 2.0F;
     this.lfHorn1.rotationPointX -= (float)Math.sin(this.lfHorn1.rotateAngleY) * 2.0F;
     this.lfHorn2.rotateAngleY = this.headFire.rotateAngleY + 0.244F + MathHelper.cos(ageInTicks * 1.3F * this.wingspeed - pi4) * 3.1415927F * 0.1F;
     this.lfHorn2.rotationPointZ -= (float)Math.cos(this.lfHorn2.rotateAngleY) * 4.0F;
     this.lfHorn2.rotationPointX -= (float)Math.sin(this.lfHorn2.rotateAngleY) * 4.0F;
     this.lfHorn3.rotateAngleY = this.headFire.rotateAngleY + 0.244F + MathHelper.cos(ageInTicks * 1.3F * this.wingspeed - 2.0F * pi4) * 3.1415927F * 0.1F;
     this.lfHorn3.rotationPointZ -= (float)Math.cos(this.lfHorn3.rotateAngleY) * 3.0F;
     this.lfHorn3.rotationPointX -= (float)Math.sin(this.lfHorn3.rotateAngleY) * 3.0F;
     this.lfHorn4.rotateAngleY = this.headFire.rotateAngleY + 0.244F + MathHelper.cos(ageInTicks * 1.3F * this.wingspeed - 3.0F * pi4) * 3.1415927F * 0.1F;
     this.lfHorn4.rotationPointZ -= (float)Math.cos(this.lfHorn4.rotateAngleY) * 2.0F;
     this.lfHorn4.rotationPointX -= (float)Math.sin(this.lfHorn4.rotateAngleY) * 2.0F;
     this.lfHorn5.rotateAngleY = this.headFire.rotateAngleY + 0.244F + MathHelper.cos(ageInTicks * 1.3F * this.wingspeed - 4.0F * pi4) * 3.1415927F * 0.1F;
     
     fc = (float)Math.cos((this.headFire.rotateAngleY - pi4));
     fs = (float)Math.sin((this.headFire.rotateAngleY - pi4));
     this.headFire.rotationPointZ -= fc * 3.6F;
     this.headFire.rotationPointX -= fs * 3.6F;
     this.rtHorn1.rotateAngleY = this.headFire.rotateAngleY + -0.244F - MathHelper.cos(ageInTicks * 1.3F * this.wingspeed) * 3.1415927F * 0.1F;
     this.rtHorn1.rotationPointZ -= (float)Math.cos(this.rtHorn1.rotateAngleY) * 2.0F;
     this.rtHorn1.rotationPointX -= (float)Math.sin(this.rtHorn1.rotateAngleY) * 2.0F;
     this.rtHorn2.rotateAngleY = this.headFire.rotateAngleY + -0.244F - MathHelper.cos(ageInTicks * 1.3F * this.wingspeed - pi4) * 3.1415927F * 0.1F;
     this.rtHorn2.rotationPointZ -= (float)Math.cos(this.rtHorn2.rotateAngleY) * 4.0F;
     this.rtHorn2.rotationPointX -= (float)Math.sin(this.rtHorn2.rotateAngleY) * 4.0F;
     this.rtHorn3.rotateAngleY = this.headFire.rotateAngleY + -0.244F - MathHelper.cos(ageInTicks * 1.3F * this.wingspeed - 2.0F * pi4) * 3.1415927F * 0.1F;
     this.rtHorn3.rotationPointZ -= (float)Math.cos(this.rtHorn3.rotateAngleY) * 3.0F;
     this.rtHorn3.rotationPointX -= (float)Math.sin(this.rtHorn3.rotateAngleY) * 3.0F;
     this.rtHorn4.rotateAngleY = this.headFire.rotateAngleY + -0.244F - MathHelper.cos(ageInTicks * 1.3F * this.wingspeed - 3.0F * pi4) * 3.1415927F * 0.1F;
     this.rtHorn4.rotationPointZ -= (float)Math.cos(this.rtHorn4.rotateAngleY) * 2.0F;
     this.rtHorn4.rotationPointX -= (float)Math.sin(this.rtHorn4.rotateAngleY) * 2.0F;
     this.rtHorn5.rotateAngleY = this.headFire.rotateAngleY + -0.244F - MathHelper.cos(ageInTicks * 1.3F * this.wingspeed - 4.0F * pi4) * 3.1415927F * 0.1F;
 
 
 
     
     this.tail1.rotateAngleY = MathHelper.cos(ageInTicks * 0.9F * this.wingspeed) * 3.1415927F * 0.2F;
     
     this.tail1.rotationPointX -= (float)Math.sin(this.tail1.rotateAngleY) * 3.0F;
     this.tail2.rotateAngleY = MathHelper.cos(ageInTicks * 0.9F * this.wingspeed - pi4) * 3.1415927F * 0.2F;
     
     this.tail2.rotationPointX -= (float)Math.sin(this.tail2.rotateAngleY) * 4.0F;
     this.tail3.rotateAngleY = MathHelper.cos(ageInTicks * 0.9F * this.wingspeed - 2.0F * pi4) * 3.1415927F * 0.2F;
     
     this.tail3.rotationPointX -= (float)Math.sin(this.tail3.rotateAngleY) * 3.5F;
     this.tail4.rotateAngleY = MathHelper.cos(ageInTicks * 0.9F * this.wingspeed - 3.0F * pi4) * 3.1415927F * 0.2F;
     
     this.tail4.rotationPointX -= (float)Math.sin(this.tail4.rotateAngleY) * 5.0F;
     this.tail5.rotateAngleY = MathHelper.cos(ageInTicks * 0.9F * this.wingspeed - 4.0F * pi4) * 3.1415927F * 0.2F;
     
     this.tail5.rotationPointX -= (float)Math.sin(this.tail5.rotateAngleY) * 4.0F;
     this.tail6.rotateAngleY = MathHelper.cos(ageInTicks * 0.9F * this.wingspeed - 5.0F * pi4) * 3.1415927F * 0.2F;
     
     this.tail6.rotationPointX -= (float)Math.sin(this.tail6.rotateAngleY) * 3.0F;
     this.tail7.rotateAngleY = MathHelper.cos(ageInTicks * 0.9F * this.wingspeed - 6.0F * pi4) * 3.1415927F * 0.2F;
     
     this.tail7.rotationPointX -= (float)Math.sin(this.tail7.rotateAngleY) * 2.0F;
     this.tail8.rotateAngleY = MathHelper.cos(ageInTicks * 0.9F * this.wingspeed - 7.0F * pi4) * 3.1415927F * 0.2F;
     
     this.tail8.rotationPointX -= (float)Math.sin(this.tail8.rotateAngleY) * 1.0F;
     this.tail9.rotateAngleY = MathHelper.cos(ageInTicks * 0.9F * this.wingspeed - 8.0F * pi4) * 3.1415927F * 0.2F;
 
     
     this.tail1.rotateAngleX = -0.26F + MathHelper.cos(ageInTicks * 0.5F * this.wingspeed) * 3.1415927F * 0.1F;
     this.tail1.rotationPointY += (float)Math.sin(this.tail1.rotateAngleX) * 3.0F;
     this.tail1.rotationPointZ -= (float)Math.cos(this.tail1.rotateAngleX) * 3.0F;
     this.tail2.rotateAngleX = -0.78F + MathHelper.cos(ageInTicks * 0.5F * this.wingspeed - pi4) * 3.1415927F * 0.1F;
     this.tail2.rotationPointY += (float)Math.sin(this.tail2.rotateAngleX) * 4.0F;
     this.tail2.rotationPointZ -= (float)Math.cos(this.tail2.rotateAngleX) * 4.0F;
     this.tail3.rotateAngleX = -1.11F + MathHelper.cos(ageInTicks * 0.5F * this.wingspeed - 2.0F * pi4) * 3.1415927F * 0.1F;
     this.tail3.rotationPointY += (float)Math.sin(this.tail3.rotateAngleX) * 3.5F;
     this.tail3.rotationPointZ -= (float)Math.cos(this.tail3.rotateAngleX) * 3.5F;
     this.tail4.rotateAngleX = -0.18F + MathHelper.cos(ageInTicks * 0.5F * this.wingspeed - 3.0F * pi4) * 3.1415927F * 0.1F;
     this.tail4.rotationPointY += (float)Math.sin(this.tail4.rotateAngleX) * 5.0F;
     this.tail4.rotationPointZ -= (float)Math.cos(this.tail4.rotateAngleX) * 5.0F;
     this.tail5.rotateAngleX = 0.22F + MathHelper.cos(ageInTicks * 0.5F * this.wingspeed - 4.0F * pi4) * 3.1415927F * 0.1F;
     this.tail5.rotationPointY += (float)Math.sin(this.tail5.rotateAngleX) * 4.0F;
     this.tail5.rotationPointZ -= (float)Math.cos(this.tail5.rotateAngleX) * 4.0F;
     this.tail6.rotateAngleX = 0.63F + MathHelper.cos(ageInTicks * 0.5F * this.wingspeed - 5.0F * pi4) * 3.1415927F * 0.1F;
     this.tail6.rotationPointY += (float)Math.sin(this.tail6.rotateAngleX) * 3.0F;
     this.tail6.rotationPointZ -= (float)Math.cos(this.tail6.rotateAngleX) * 3.0F;
     this.tail7.rotateAngleX = 0.89F + MathHelper.cos(ageInTicks * 0.5F * this.wingspeed - 6.0F * pi4) * 3.1415927F * 0.1F;
     this.tail7.rotationPointY += (float)Math.sin(this.tail7.rotateAngleX) * 2.0F;
     this.tail7.rotationPointZ -= (float)Math.cos(this.tail7.rotateAngleX) * 2.0F;
     this.tail8.rotateAngleX = 1.52F + MathHelper.cos(ageInTicks * 0.5F * this.wingspeed - 7.0F * pi4) * 3.1415927F * 0.1F;
     this.tail8.rotationPointY += (float)Math.sin(this.tail8.rotateAngleX) * 2.0F;
     this.tail8.rotationPointZ -= (float)Math.cos(this.tail8.rotateAngleX) * 2.0F;
     this.tail9.rotateAngleX = 2.0F + MathHelper.cos(ageInTicks * 0.5F * this.wingspeed - 8.0F * pi4) * 3.1415927F * 0.1F;
    }
 }
}
