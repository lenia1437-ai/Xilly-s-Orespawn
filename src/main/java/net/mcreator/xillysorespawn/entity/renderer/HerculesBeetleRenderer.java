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
import net.mcreator.xillysorespawn.entity.HerculesBeetleEntity;
import net.mcreator.xillysorespawn.entity.OreSpawnLogic;

@OnlyIn(Dist.CLIENT)
public class HerculesBeetleRenderer {
    private static final String TEXTURE = "xillys_orespawn:textures/entities/beetletexture.png";
    public static class ModelRegisterHandler {
        @SubscribeEvent @OnlyIn(Dist.CLIENT)
        public void registerModels(ModelRegistryEvent event) {
            RenderingRegistry.registerEntityRenderingHandler(HerculesBeetleEntity.entity, manager ->
                new MobRenderer(manager, new ModelHerculesBeetle(1.0F), 1.089F) {
                    @Override public ResourceLocation getEntityTexture(Entity entity) { return new ResourceLocation(TEXTURE); }
                    @Override protected void preRenderCallback(LivingEntity entity, MatrixStack stack, float partialTick) {
                        float modelScale = 1.1F;
                        stack.scale(modelScale, modelScale, modelScale);
                    }
                });
        }
    }

public static class ModelHerculesBeetle extends EntityModel<Entity> {
   private float wingspeed = 1.0F;

   private final ModelRenderer body1;

   private final ModelRenderer body2;

   private final ModelRenderer head1;
   private final ModelRenderer head2;
   private final ModelRenderer head3;
   private final ModelRenderer head4;
   private final ModelRenderer head5;
   private final ModelRenderer head6;
   private final ModelRenderer head8;
   private final ModelRenderer jaw1;
   private final ModelRenderer jaw2;
   private final ModelRenderer jaw3;
   private final ModelRenderer jaw4;
   private final ModelRenderer head7;
   private final ModelRenderer lfleg1;
   private final ModelRenderer lfleg2;
   private final ModelRenderer lfleg3;
   private final ModelRenderer lmleg1;
   private final ModelRenderer lmleg2;
   private final ModelRenderer lmleg3;
   private final ModelRenderer lrleg1;
   private final ModelRenderer lrleg2;
   private final ModelRenderer lrleg3;
   private final ModelRenderer jaw5;
   private final ModelRenderer jaw6;
   private final ModelRenderer jaw7;
   private final ModelRenderer jaw8;
   private final ModelRenderer rfleg1;
   private final ModelRenderer rfleg2;
   private final ModelRenderer rfleg3;
   private final ModelRenderer rmleg1;
   private final ModelRenderer rmleg2;
   private final ModelRenderer rmleg3;
   private final ModelRenderer rrleg1;
   private final ModelRenderer rrleg2;
   private final ModelRenderer rrleg3;
   private final ModelRenderer jaw9;

   public ModelHerculesBeetle(float f1) {
     this.wingspeed = f1;

     this.textureWidth = 256;
     this.textureHeight = 256;

     this.body1 = new ModelRenderer(this, 0, 30);
     this.body1.addBox(-8.0F, 0.0F, 0.0F, 16, 16, 23);
     this.body1.setRotationPoint(0.0F, 0.0F, 0.0F);

     this.body1.mirror = true;
     setRotation(this.body1, 0.0F, 0.0F, 0.0F);
     this.body2 = new ModelRenderer(this, 80, 41);
     this.body2.addBox(-6.0F, 0.0F, 0.0F, 12, 12, 4);
     this.body2.setRotationPoint(0.0F, 3.0F, 23.0F);

     this.body2.mirror = true;
     setRotation(this.body2, 0.0F, 0.0F, 0.0F);
     this.head1 = new ModelRenderer(this, 0, 71);
     this.head1.addBox(-9.0F, 0.0F, 0.0F, 18, 16, 12);
     this.head1.setRotationPoint(0.0F, -1.0F, -10.0F);

     this.head1.mirror = true;
     setRotation(this.head1, -0.122173F, 0.0F, 0.0F);
     this.head2 = new ModelRenderer(this, 0, 100);
     this.head2.addBox(-7.0F, 0.0F, 0.0F, 14, 10, 6);
     this.head2.setRotationPoint(0.0F, -2.0F, -16.0F);

     this.head2.mirror = true;
     setRotation(this.head2, -0.122173F, 0.0F, 0.0F);
     this.head3 = new ModelRenderer(this, 0, 117);
     this.head3.addBox(-5.0F, 0.0F, 0.0F, 10, 6, 9);
     this.head3.setRotationPoint(0.0F, -3.0F, -25.0F);

     this.head3.mirror = true;
     setRotation(this.head3, -0.122173F, 0.0F, 0.0F);
     this.head4 = new ModelRenderer(this, 0, 133);
     this.head4.addBox(-4.0F, 0.0F, 0.0F, 8, 4, 12);
     this.head4.setRotationPoint(0.0F, -4.0F, -37.0F);

     this.head4.mirror = true;
     setRotation(this.head4, -0.122173F, 0.0F, 0.0F);
     this.head5 = new ModelRenderer(this, 0, 150);
     this.head5.addBox(-3.0F, 0.0F, 0.0F, 6, 3, 21);
     this.head5.setRotationPoint(0.0F, -4.0F, -58.0F);

     this.head5.mirror = true;
     setRotation(this.head5, 0.0F, 0.0F, 0.0F);
     this.head6 = new ModelRenderer(this, 0, 175);
     this.head6.addBox(-2.0F, 0.0F, 0.0F, 4, 2, 14);
     this.head6.setRotationPoint(0.0F, -2.0F, -72.0F);

     this.head6.mirror = true;
     setRotation(this.head6, 0.122173F, 0.0F, 0.0F);
     this.head8 = new ModelRenderer(this, 6, 193);
     this.head8.addBox(0.0F, 0.0F, 0.0F, 1, 3, 1);
     this.head8.setRotationPoint(0.0F, -2.0F, -46.0F);

     this.head8.mirror = true;
     setRotation(this.head8, -0.2094395F, 0.0F, 0.0F);
     this.jaw1 = new ModelRenderer(this, 114, 0);
     this.jaw1.addBox(-3.0F, -3.0F, -4.0F, 6, 7, 5);
     this.jaw1.setRotationPoint(0.0F, 12.0F, -12.0F);

     this.jaw1.mirror = true;
     setRotation(this.jaw1, 0.122173F, 0.0F, 0.0F);
     this.jaw2 = new ModelRenderer(this, 115, 14);
     this.jaw2.addBox(-2.5F, -3.0F, -27.0F, 5, 5, 23);
     this.jaw2.setRotationPoint(0.0F, 12.0F, -12.0F);

     this.jaw2.mirror = true;
     setRotation(this.jaw2, 0.122173F, 0.0F, 0.0F);
     this.jaw3 = new ModelRenderer(this, 115, 43);
     this.jaw3.addBox(-1.5F, 0.0F, -44.0F, 3, 5, 18);
     this.jaw3.setRotationPoint(0.0F, 12.0F, -12.0F);

     this.jaw3.mirror = true;
     setRotation(this.jaw3, 0.0F, 0.0F, 0.0F);
     this.jaw4 = new ModelRenderer(this, 115, 70);
     this.jaw4.addBox(-0.5F, -2.0F, -45.0F, 1, 5, 1);
     this.jaw4.setRotationPoint(0.0F, 12.0F, -12.0F);

     this.jaw4.mirror = true;
     setRotation(this.jaw4, 0.0F, 0.0F, 0.0F);
     this.head7 = new ModelRenderer(this, 0, 193);
     this.head7.addBox(-0.5F, 0.0F, 0.0F, 1, 4, 1);
     this.head7.setRotationPoint(0.0F, -2.0F, -73.0F);

     this.head7.mirror = true;
     setRotation(this.head7, 0.122173F, 0.0F, 0.0F);
     this.lfleg1 = new ModelRenderer(this, 60, 0);
     this.lfleg1.addBox(0.0F, 0.0F, -0.5F, 10, 3, 3);
     this.lfleg1.setRotationPoint(6.0F, 15.0F, -5.0F);

     this.lfleg1.mirror = true;
     setRotation(this.lfleg1, 0.0F, 0.3490659F, 0.0872665F);
     this.lfleg2 = new ModelRenderer(this, 60, 8);
     this.lfleg2.addBox(10.0F, -1.0F, 0.0F, 11, 2, 2);
     this.lfleg2.setRotationPoint(6.0F, 15.0F, -5.0F);

     this.lfleg2.mirror = true;
     setRotation(this.lfleg2, 0.0F, 0.3490659F, 0.2617994F);
     this.lfleg3 = new ModelRenderer(this, 60, 14);
     this.lfleg3.addBox(21.0F, -2.0F, 0.5F, 10, 1, 1);
     this.lfleg3.setRotationPoint(6.0F, 15.0F, -5.0F);

     this.lfleg3.mirror = true;
     setRotation(this.lfleg3, 0.0F, 0.3490659F, 0.3490659F);
     this.lmleg1 = new ModelRenderer(this, 60, 0);
     this.lmleg1.addBox(0.0F, 0.0F, -0.5F, 10, 3, 3);
     this.lmleg1.setRotationPoint(6.0F, 15.0F, 0.0F);

     this.lmleg1.mirror = true;
     setRotation(this.lmleg1, 0.0F, 0.0F, 0.0872665F);
     this.lmleg2 = new ModelRenderer(this, 60, 8);
     this.lmleg2.addBox(10.0F, -1.0F, 0.0F, 11, 2, 2);
     this.lmleg2.setRotationPoint(6.0F, 15.0F, 0.0F);

     this.lmleg2.mirror = true;
     setRotation(this.lmleg2, 0.0F, 0.0F, 0.2617994F);
     this.lmleg3 = new ModelRenderer(this, 60, 14);
     this.lmleg3.addBox(21.0F, -2.0F, 0.5F, 10, 1, 1);
     this.lmleg3.setRotationPoint(6.0F, 15.0F, 0.0F);

     this.lmleg3.mirror = true;
     setRotation(this.lmleg3, 0.0F, 0.0F, 0.3490659F);
     this.lrleg1 = new ModelRenderer(this, 60, 0);
     this.lrleg1.addBox(0.0F, 0.0F, -0.5F, 10, 3, 3);
     this.lrleg1.setRotationPoint(6.0F, 15.0F, 5.0F);

     this.lrleg1.mirror = true;
     setRotation(this.lrleg1, 0.0F, -0.3490659F, 0.0872665F);
     this.lrleg2 = new ModelRenderer(this, 60, 8);
     this.lrleg2.addBox(10.0F, -1.0F, 0.0F, 11, 2, 2);
     this.lrleg2.setRotationPoint(6.0F, 15.0F, 5.0F);

     this.lrleg2.mirror = true;
     setRotation(this.lrleg2, 0.0F, -0.3490659F, 0.2617994F);
     this.lrleg3 = new ModelRenderer(this, 60, 14);
     this.lrleg3.addBox(21.0F, -2.0F, 0.5F, 10, 1, 1);
     this.lrleg3.setRotationPoint(6.0F, 15.0F, 5.0F);

     this.lrleg3.mirror = true;
     setRotation(this.lrleg3, 0.0F, -0.3490659F, 0.3490659F);
     this.jaw5 = new ModelRenderer(this, 115, 78);
     this.jaw5.addBox(2.0F, -2.0F, -9.0F, 2, 3, 3);
     this.jaw5.setRotationPoint(0.0F, 12.0F, -12.0F);

     this.jaw5.mirror = true;
     setRotation(this.jaw5, 0.122173F, 0.0F, 0.0F);
     this.jaw6 = new ModelRenderer(this, 127, 78);
     this.jaw6.addBox(-4.0F, -2.0F, -9.0F, 2, 3, 3);
     this.jaw6.setRotationPoint(0.0F, 12.0F, -12.0F);

     this.jaw6.mirror = true;
     setRotation(this.jaw6, 0.122173F, 0.0F, 0.0F);
     this.jaw7 = new ModelRenderer(this, 115, 86);
     this.jaw7.addBox(5.0F, 1.0F, -6.0F, 9, 1, 1);
     this.jaw7.setRotationPoint(0.0F, 12.0F, -12.0F);

     this.jaw7.mirror = true;
     setRotation(this.jaw7, 0.0F, 0.5585054F, 0.2268928F);
     this.jaw8 = new ModelRenderer(this, 115, 89);
     this.jaw8.addBox(-14.0F, 1.0F, -6.0F, 9, 1, 1);
     this.jaw8.setRotationPoint(0.0F, 12.0F, -12.0F);

     this.jaw8.mirror = true;
     setRotation(this.jaw8, 0.0F, -0.5585054F, -0.2268928F);
     this.rfleg1 = new ModelRenderer(this, 30, 0);
     this.rfleg1.addBox(-10.0F, 0.0F, -0.5F, 10, 3, 3);
     this.rfleg1.setRotationPoint(-6.0F, 15.0F, -5.0F);

     this.rfleg1.mirror = true;
     setRotation(this.rfleg1, 0.0F, -0.3490659F, -0.0872665F);
     this.rfleg2 = new ModelRenderer(this, 30, 8);
     this.rfleg2.addBox(-21.0F, -1.0F, 0.0F, 11, 2, 2);
     this.rfleg2.setRotationPoint(-6.0F, 15.0F, -5.0F);

     this.rfleg2.mirror = true;
     setRotation(this.rfleg2, 0.0F, -0.3490659F, -0.2617994F);
     this.rfleg3 = new ModelRenderer(this, 30, 14);
     this.rfleg3.addBox(-31.0F, -2.0F, 0.5F, 10, 1, 1);
     this.rfleg3.setRotationPoint(-6.0F, 15.0F, -5.0F);

     this.rfleg3.mirror = true;
     setRotation(this.rfleg3, 0.0F, -0.3490659F, -0.3490659F);
     this.rmleg1 = new ModelRenderer(this, 30, 0);
     this.rmleg1.addBox(-10.0F, 0.0F, -0.5F, 10, 3, 3);
     this.rmleg1.setRotationPoint(-6.0F, 15.0F, 0.0F);

     this.rmleg1.mirror = true;
     setRotation(this.rmleg1, 0.0F, 0.0F, -0.0872665F);
     this.rmleg2 = new ModelRenderer(this, 30, 8);
     this.rmleg2.addBox(-21.0F, -1.0F, 0.0F, 11, 2, 2);
     this.rmleg2.setRotationPoint(-6.0F, 15.0F, 0.0F);

     this.rmleg2.mirror = true;
     setRotation(this.rmleg2, 0.0F, 0.0F, -0.2617994F);
     this.rmleg3 = new ModelRenderer(this, 30, 14);
     this.rmleg3.addBox(-31.0F, -2.0F, 0.5F, 10, 1, 1);
     this.rmleg3.setRotationPoint(-6.0F, 15.0F, 0.0F);

     this.rmleg3.mirror = true;
     setRotation(this.rmleg3, 0.0F, 0.0F, -0.3490659F);
     this.rrleg1 = new ModelRenderer(this, 30, 0);
     this.rrleg1.addBox(-10.0F, 0.0F, -0.5F, 10, 3, 3);
     this.rrleg1.setRotationPoint(-6.0F, 15.0F, 5.0F);

     this.rrleg1.mirror = true;
     setRotation(this.rrleg1, 0.0F, 0.3490659F, -0.0872665F);
     this.rrleg2 = new ModelRenderer(this, 30, 8);
     this.rrleg2.addBox(-21.0F, -1.0F, 0.0F, 11, 2, 2);
     this.rrleg2.setRotationPoint(-6.0F, 15.0F, 5.0F);

     this.rrleg2.mirror = true;
     setRotation(this.rrleg2, 0.0F, 0.3490659F, -0.2617994F);
     this.rrleg3 = new ModelRenderer(this, 30, 14);
     this.rrleg3.addBox(-31.0F, -2.0F, 0.5F, 10, 1, 1);
     this.rrleg3.setRotationPoint(-6.0F, 15.0F, 5.0F);

     this.rrleg3.mirror = true;
     setRotation(this.rrleg3, 0.0F, 0.3490659F, -0.3490659F);
     this.jaw9 = new ModelRenderer(this, 121, 70);
     this.jaw9.addBox(-0.5F, -12.0F, -25.0F, 1, 5, 1);
     this.jaw9.setRotationPoint(0.0F, 12.0F, -12.0F);

     this.jaw9.mirror = true;
     setRotation(this.jaw9, 0.3141593F, 0.0F, 0.0F);
   }

   public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay,
        float red, float green, float blue, float alpha) {
        body1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        body2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        head1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        head2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        head3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        head4.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        head5.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        head6.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        head8.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        jaw1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        jaw2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        jaw3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        jaw4.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        head7.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        lfleg1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        lfleg2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        lfleg3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        lmleg1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        lmleg2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        lmleg3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        lrleg1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        lrleg2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        lrleg3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        jaw5.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        jaw6.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        jaw7.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        jaw8.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rfleg1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rfleg2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rfleg3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rmleg1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rmleg2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rmleg3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rrleg1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rrleg2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rrleg3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        jaw9.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }

   private void setRotation(ModelRenderer model, float x, float y, float z) {
     model.rotateAngleX = x;
     model.rotateAngleY = y;
     model.rotateAngleZ = z;
   }

   public void setRotationAngles(Entity entity, float limbSwing, float limbSwingAmount,
        float ageInTicks, float netHeadYaw, float headPitch) {
// Reset shared model state before applying this frame's legacy animation.
this.body1.setRotationPoint(0.0F, 0.0F, 0.0F);
this.body2.setRotationPoint(0.0F, 3.0F, 23.0F);
this.head1.setRotationPoint(0.0F, -1.0F, -10.0F);
this.head2.setRotationPoint(0.0F, -2.0F, -16.0F);
this.head3.setRotationPoint(0.0F, -3.0F, -25.0F);
this.head4.setRotationPoint(0.0F, -4.0F, -37.0F);
this.head5.setRotationPoint(0.0F, -4.0F, -58.0F);
this.head6.setRotationPoint(0.0F, -2.0F, -72.0F);
this.head8.setRotationPoint(0.0F, -2.0F, -46.0F);
this.jaw1.setRotationPoint(0.0F, 12.0F, -12.0F);
this.jaw2.setRotationPoint(0.0F, 12.0F, -12.0F);
this.jaw3.setRotationPoint(0.0F, 12.0F, -12.0F);
this.jaw4.setRotationPoint(0.0F, 12.0F, -12.0F);
this.head7.setRotationPoint(0.0F, -2.0F, -73.0F);
this.lfleg1.setRotationPoint(6.0F, 15.0F, -5.0F);
this.lfleg2.setRotationPoint(6.0F, 15.0F, -5.0F);
this.lfleg3.setRotationPoint(6.0F, 15.0F, -5.0F);
this.lmleg1.setRotationPoint(6.0F, 15.0F, 0.0F);
this.lmleg2.setRotationPoint(6.0F, 15.0F, 0.0F);
this.lmleg3.setRotationPoint(6.0F, 15.0F, 0.0F);
this.lrleg1.setRotationPoint(6.0F, 15.0F, 5.0F);
this.lrleg2.setRotationPoint(6.0F, 15.0F, 5.0F);
this.lrleg3.setRotationPoint(6.0F, 15.0F, 5.0F);
this.jaw5.setRotationPoint(0.0F, 12.0F, -12.0F);
this.jaw6.setRotationPoint(0.0F, 12.0F, -12.0F);
this.jaw7.setRotationPoint(0.0F, 12.0F, -12.0F);
this.jaw8.setRotationPoint(0.0F, 12.0F, -12.0F);
this.rfleg1.setRotationPoint(-6.0F, 15.0F, -5.0F);
this.rfleg2.setRotationPoint(-6.0F, 15.0F, -5.0F);
this.rfleg3.setRotationPoint(-6.0F, 15.0F, -5.0F);
this.rmleg1.setRotationPoint(-6.0F, 15.0F, 0.0F);
this.rmleg2.setRotationPoint(-6.0F, 15.0F, 0.0F);
this.rmleg3.setRotationPoint(-6.0F, 15.0F, 0.0F);
this.rrleg1.setRotationPoint(-6.0F, 15.0F, 5.0F);
this.rrleg2.setRotationPoint(-6.0F, 15.0F, 5.0F);
this.rrleg3.setRotationPoint(-6.0F, 15.0F, 5.0F);
this.jaw9.setRotationPoint(0.0F, 12.0F, -12.0F);
setRotation(this.body1, 0.0F, 0.0F, 0.0F);
setRotation(this.body2, 0.0F, 0.0F, 0.0F);
setRotation(this.head1, -0.122173F, 0.0F, 0.0F);
setRotation(this.head2, -0.122173F, 0.0F, 0.0F);
setRotation(this.head3, -0.122173F, 0.0F, 0.0F);
setRotation(this.head4, -0.122173F, 0.0F, 0.0F);
setRotation(this.head5, 0.0F, 0.0F, 0.0F);
setRotation(this.head6, 0.122173F, 0.0F, 0.0F);
setRotation(this.head8, -0.2094395F, 0.0F, 0.0F);
setRotation(this.jaw1, 0.122173F, 0.0F, 0.0F);
setRotation(this.jaw2, 0.122173F, 0.0F, 0.0F);
setRotation(this.jaw3, 0.0F, 0.0F, 0.0F);
setRotation(this.jaw4, 0.0F, 0.0F, 0.0F);
setRotation(this.head7, 0.122173F, 0.0F, 0.0F);
setRotation(this.lfleg1, 0.0F, 0.3490659F, 0.0872665F);
setRotation(this.lfleg2, 0.0F, 0.3490659F, 0.2617994F);
setRotation(this.lfleg3, 0.0F, 0.3490659F, 0.3490659F);
setRotation(this.lmleg1, 0.0F, 0.0F, 0.0872665F);
setRotation(this.lmleg2, 0.0F, 0.0F, 0.2617994F);
setRotation(this.lmleg3, 0.0F, 0.0F, 0.3490659F);
setRotation(this.lrleg1, 0.0F, -0.3490659F, 0.0872665F);
setRotation(this.lrleg2, 0.0F, -0.3490659F, 0.2617994F);
setRotation(this.lrleg3, 0.0F, -0.3490659F, 0.3490659F);
setRotation(this.jaw5, 0.122173F, 0.0F, 0.0F);
setRotation(this.jaw6, 0.122173F, 0.0F, 0.0F);
setRotation(this.jaw7, 0.0F, 0.5585054F, 0.2268928F);
setRotation(this.jaw8, 0.0F, -0.5585054F, -0.2268928F);
setRotation(this.rfleg1, 0.0F, -0.3490659F, -0.0872665F);
setRotation(this.rfleg2, 0.0F, -0.3490659F, -0.2617994F);
setRotation(this.rfleg3, 0.0F, -0.3490659F, -0.3490659F);
setRotation(this.rmleg1, 0.0F, 0.0F, -0.0872665F);
setRotation(this.rmleg2, 0.0F, 0.0F, -0.2617994F);
setRotation(this.rmleg3, 0.0F, 0.0F, -0.3490659F);
setRotation(this.rrleg1, 0.0F, 0.3490659F, -0.0872665F);
setRotation(this.rrleg2, 0.0F, 0.3490659F, -0.2617994F);
setRotation(this.rrleg3, 0.0F, 0.3490659F, -0.3490659F);
setRotation(this.jaw9, 0.3141593F, 0.0F, 0.0F);

     float newangle = 0.0F;
     HerculesBeetleEntity.CustomEntity b = (HerculesBeetleEntity.CustomEntity) entity;

     newangle = MathHelper.cos(ageInTicks * this.wingspeed * 0.45F) * 3.1415927F * 0.12F * limbSwingAmount;
     this.lfleg1.rotateAngleY = 0.349F + newangle;
     this.lfleg2.rotateAngleY = this.lfleg1.rotateAngleY;
     this.lfleg3.rotateAngleY = this.lfleg1.rotateAngleY;
     this.lmleg1.rotateAngleY = -newangle;
     this.lmleg2.rotateAngleY = this.lmleg1.rotateAngleY;
     this.lmleg3.rotateAngleY = this.lmleg1.rotateAngleY;
     this.lrleg1.rotateAngleY = -0.349F + newangle;
     this.lrleg2.rotateAngleY = this.lrleg1.rotateAngleY;
     this.lrleg3.rotateAngleY = this.lrleg1.rotateAngleY;
     
     this.rfleg1.rotateAngleY = -0.349F + newangle;
     this.rfleg2.rotateAngleY = this.rfleg1.rotateAngleY;
     this.rfleg3.rotateAngleY = this.rfleg1.rotateAngleY;
     this.rmleg1.rotateAngleY = -newangle;
     this.rmleg2.rotateAngleY = this.rmleg1.rotateAngleY;
     this.rmleg3.rotateAngleY = this.rmleg1.rotateAngleY;
     this.rrleg1.rotateAngleY = 0.349F + newangle;
     this.rrleg2.rotateAngleY = this.rrleg1.rotateAngleY;
     this.rrleg3.rotateAngleY = this.rrleg1.rotateAngleY;
 
     
     if (b.getAttacking() == 0) {
       newangle = MathHelper.cos(ageInTicks * 0.051F * this.wingspeed) * 3.1415927F * 0.01F;
     } else {
       newangle = MathHelper.cos(ageInTicks * 0.51F * this.wingspeed) * 3.1415927F * 0.07F;
     } 
     this.jaw1.rotateAngleX = 0.122F + newangle;
     this.jaw2.rotateAngleX = 0.122F + newangle;
     this.jaw3.rotateAngleX = 0.0F + newangle;
     this.jaw4.rotateAngleX = 0.0F + newangle;
     this.jaw5.rotateAngleX = 0.122F + newangle;
     this.jaw6.rotateAngleX = 0.122F + newangle;
     this.jaw7.rotateAngleX = 0.0F + newangle;
     this.jaw8.rotateAngleX = 0.0F + newangle;
     this.jaw9.rotateAngleX = 0.314F + newangle;
    }
 }
}
