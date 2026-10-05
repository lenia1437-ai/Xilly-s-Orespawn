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
import net.mcreator.xillysorespawn.entity.HydroliscEntity;
import net.mcreator.xillysorespawn.entity.OreSpawnLogic;

@OnlyIn(Dist.CLIENT)
public class HydroliscRenderer {
    private static final String TEXTURE = "xillys_orespawn:textures/entities/hydrolisc.png";
    public static class ModelRegisterHandler {
        @SubscribeEvent @OnlyIn(Dist.CLIENT)
        public void registerModels(ModelRegistryEvent event) {
            RenderingRegistry.registerEntityRenderingHandler(HydroliscEntity.entity, manager ->
                new MobRenderer(manager, new ModelHydrolisc(0.65F), 0.4225F) {
                    @Override public ResourceLocation getEntityTexture(Entity entity) { return new ResourceLocation(TEXTURE); }
                    @Override protected void preRenderCallback(LivingEntity entity, MatrixStack stack, float partialTick) {
                        float modelScale = 0.65F;
                        if (entity.isChild()) modelScale *= 0.5F;
                        stack.scale(modelScale, modelScale, modelScale);
                    }
                });
        }
    }

public static class ModelHydrolisc extends EntityModel<Entity>
 {
   private float wingspeed = 1.0F;

   private final ModelRenderer tail2;

   private final ModelRenderer tail3;

   private final ModelRenderer body2;
   private final ModelRenderer lb2;
   private final ModelRenderer lb1;
   private final ModelRenderer spine3;
   private final ModelRenderer spine4;
   private final ModelRenderer rb1;
   private final ModelRenderer rb2;
   private final ModelRenderer spine1;
   private final ModelRenderer spine2;
   private final ModelRenderer lb3;
   private final ModelRenderer rb3;
   private final ModelRenderer body1;
   private final ModelRenderer body0;
   private final ModelRenderer lf1;
   private final ModelRenderer rf1;
   private final ModelRenderer rb6;
   private final ModelRenderer rb4;
   private final ModelRenderer rb5;
   private final ModelRenderer lb6;
   private final ModelRenderer lb5;
   private final ModelRenderer lb4;
   private final ModelRenderer head3;
   private final ModelRenderer feather3;
   private final ModelRenderer feather1;
   private final ModelRenderer feather2;
   private final ModelRenderer head1;
   private final ModelRenderer rf2;
   private final ModelRenderer rf3;
   private final ModelRenderer rf4;
   private final ModelRenderer rf5;
   private final ModelRenderer rf6;
   private final ModelRenderer lf2;
   private final ModelRenderer lf3;
   private final ModelRenderer lf4;
   private final ModelRenderer lf5;
   private final ModelRenderer lf6;
   private final ModelRenderer head2;
   private final ModelRenderer tail1;

   public ModelHydrolisc(float f1) {
     this.wingspeed = f1;

     this.textureWidth = 64;
     this.textureHeight = 128;

     this.tail2 = new ModelRenderer(this, 29, 3);
     this.tail2.addBox(-1.0F, 0.0F, -0.8F, 2, 8, 2);
     this.tail2.setRotationPoint(1.0F, 20.0F, 13.53333F);

     this.tail2.mirror = true;
     setRotation(this.tail2, 1.392442F, 0.0F, 0.0F);
     this.tail3 = new ModelRenderer(this, 39, 0);
     this.tail3.addBox(-1.0F, -1.0F, -2.0F, 2, 8, 2);
     this.tail3.setRotationPoint(1.0F, 20.0F, 21.0F);

     this.tail3.mirror = true;
     setRotation(this.tail3, 1.72705F, 0.0F, 0.0F);
     this.body2 = new ModelRenderer(this, 0, 99);
     this.body2.addBox(-2.0F, 14.0F, 0.0F, 6, 4, 10);
     this.body2.setRotationPoint(0.0F, 0.0F, 0.0F);

     this.body2.mirror = true;
     setRotation(this.body2, -0.0523599F, 0.0F, 0.0F);
     this.lb2 = new ModelRenderer(this, 45, 13);
     this.lb2.addBox(0.0F, 0.0F, 3.0F, 3, 2, 5);
     this.lb2.setRotationPoint(5.0F, 15.0F, 0.0F);

     this.lb2.mirror = true;
     setRotation(this.lb2, -0.4886922F, 0.0F, 0.0F);
     this.lb1 = new ModelRenderer(this, 46, 22);
     this.lb1.addBox(-1.0F, 0.0F, 0.0F, 4, 3, 3);
     this.lb1.setRotationPoint(5.0F, 15.0F, 0.0F);

     this.lb1.mirror = true;
     setRotation(this.lb1, 0.0F, 0.0F, 0.0F);
     this.spine3 = new ModelRenderer(this, 11, 31);
     this.spine3.addBox(-1.0F, -5.0F, 0.0F, 2, 6, 2);
     this.spine3.setRotationPoint(1.0F, 14.0F, 6.0F);

     this.spine3.mirror = true;
     setRotation(this.spine3, -1.117011F, 0.0F, 0.0F);
     this.spine4 = new ModelRenderer(this, 0, 30);
     this.spine4.addBox(-1.0F, -10.5F, -1.0F, 2, 6, 2);
     this.spine4.setRotationPoint(1.0F, 14.0F, 6.0F);

     this.spine4.mirror = true;
     setRotation(this.spine4, -1.343904F, 0.0F, 0.0F);
     this.rb1 = new ModelRenderer(this, 46, 22);
     this.rb1.addBox(-4.0F, 0.0F, 0.0F, 4, 3, 3);
     this.rb1.setRotationPoint(-2.0F, 15.0F, 0.0F);

     this.rb1.mirror = true;
     setRotation(this.rb1, 0.0F, 0.0F, 0.0F);
     this.rb2 = new ModelRenderer(this, 45, 13);
     this.rb2.addBox(-4.0F, 0.0F, 2.0F, 3, 2, 5);
     this.rb2.setRotationPoint(-2.0F, 15.0F, 0.0F);

     this.rb2.mirror = true;
     setRotation(this.rb2, -0.4886922F, 0.0F, 0.0F);
     this.spine1 = new ModelRenderer(this, 33, 19);
     this.spine1.addBox(-1.0F, -5.0F, 0.0F, 2, 6, 2);
     this.spine1.setRotationPoint(1.0F, 14.0F, 0.0F);

     this.spine1.mirror = true;
     setRotation(this.spine1, -0.8552113F, 0.0F, 0.0F);
     this.spine2 = new ModelRenderer(this, 21, 19);
     this.spine2.addBox(-1.0F, -10.5F, -1.5F, 2, 6, 2);
     this.spine2.setRotationPoint(1.0F, 14.0F, 0.0F);

     this.spine2.mirror = true;
     setRotation(this.spine2, -1.169371F, 0.0F, 0.0F);
     this.lb3 = new ModelRenderer(this, 0, 58);
     this.lb3.addBox(0.0F, -8.0F, -2.0F, 3, 2, 6);
     this.lb3.setRotationPoint(5.0F, 15.0F, 0.0F);

     this.lb3.mirror = true;
     setRotation(this.lb3, -2.347623F, 0.0F, 0.0F);
     this.rb3 = new ModelRenderer(this, 0, 58);
     this.rb3.addBox(-4.0F, -8.0F, -2.0F, 3, 2, 6);
     this.rb3.setRotationPoint(-2.0F, 15.0F, 0.0F);

     this.rb3.mirror = true;
     setRotation(this.rb3, -2.347623F, 0.0F, 0.0F);
     this.body1 = new ModelRenderer(this, 0, 79);
     this.body1.addBox(-2.0F, 16.0F, -7.0F, 4, 2, 5);
     this.body1.setRotationPoint(1.0F, -1.0F, 2.0F);

     this.body1.mirror = true;
     setRotation(this.body1, 0.0F, 0.0F, 0.0F);
     this.body0 = new ModelRenderer(this, 0, 0);
     this.body0.addBox(-1.0F, 14.0F, -13.0F, 4, 3, 10);
     this.body0.setRotationPoint(0.0F, 0.0F, 0.0F);

     this.body0.mirror = true;
     setRotation(this.body0, 0.0523599F, 0.0F, 0.0F);
     this.lf1 = new ModelRenderer(this, 45, 32);
     this.lf1.addBox(-1.0F, 0.0F, -2.0F, 4, 3, 3);
     this.lf1.setRotationPoint(4.0F, 14.0F, -7.0F);

     this.lf1.mirror = true;
     setRotation(this.lf1, 0.0F, 0.0F, 0.0F);
     this.rf1 = new ModelRenderer(this, 45, 32);
     this.rf1.addBox(-3.0F, 0.0F, -2.0F, 4, 3, 3);
     this.rf1.setRotationPoint(-2.0F, 14.0F, -7.0F);

     this.rf1.mirror = true;
     setRotation(this.rf1, 0.0F, 0.0F, 0.0F);
     this.rb6 = new ModelRenderer(this, 30, 39);
     this.rb6.addBox(-3.5F, 7.0F, 2.0F, 2, 3, 1);
     this.rb6.setRotationPoint(-2.0F, 15.0F, 0.0F);

     this.rb6.mirror = true;
     setRotation(this.rb6, 0.1745329F, 0.0F, 0.0F);
     this.rb4 = new ModelRenderer(this, 20, 39);
     this.rb4.addBox(-2.0F, 3.0F, 6.0F, 1, 4, 1);
     this.rb4.setRotationPoint(-2.0F, 15.0F, 0.0F);

     this.rb4.mirror = true;
     setRotation(this.rb4, -0.6283185F, 0.0F, 0.0F);
     this.rb5 = new ModelRenderer(this, 20, 39);
     this.rb5.addBox(-4.0F, 3.0F, 6.0F, 1, 4, 1);
     this.rb5.setRotationPoint(-2.0F, 15.0F, 0.0F);

     this.rb5.mirror = true;
     setRotation(this.rb5, -0.6283185F, 0.0F, 0.0F);
     this.lb6 = new ModelRenderer(this, 30, 39);
     this.lb6.addBox(0.5F, 7.0F, 2.0F, 2, 3, 1);
     this.lb6.setRotationPoint(5.0F, 15.0F, 0.0F);

     this.lb6.mirror = true;
     setRotation(this.lb6, 0.1745329F, 0.0F, 0.0F);
     this.lb5 = new ModelRenderer(this, 20, 39);
     this.lb5.addBox(2.0F, 3.0F, 6.0F, 1, 4, 1);
     this.lb5.setRotationPoint(5.0F, 15.0F, 0.0F);

     this.lb5.mirror = true;
     setRotation(this.lb5, -0.6283185F, 0.0F, 0.0F);
     this.lb4 = new ModelRenderer(this, 20, 39);
     this.lb4.addBox(0.0F, 3.0F, 6.0F, 1, 4, 1);
     this.lb4.setRotationPoint(5.0F, 15.0F, 0.0F);

     this.lb4.mirror = true;
     setRotation(this.lb4, -0.6283185F, 0.0F, 0.0F);
     this.head3 = new ModelRenderer(this, 38, 50);
     this.head3.addBox(0.0F, 0.0F, 0.0F, 4, 2, 8);
     this.head3.setRotationPoint(-1.0F, 15.0F, -13.0F);

     this.head3.mirror = true;
     setRotation(this.head3, 0.5235988F, 0.0F, 0.0F);
     this.feather3 = new ModelRenderer(this, 25, 117);
     this.feather3.addBox(0.0F, 0.0F, 1.0F, 1, 2, 9);
     this.feather3.setRotationPoint(1.0F, 12.0F, -8.0F);

     this.feather3.mirror = true;
     setRotation(this.feather3, 0.3490659F, 0.2617994F, 0.0F);
     this.feather1 = new ModelRenderer(this, 34, 100);
     this.feather1.addBox(0.0F, 0.0F, 1.0F, 1, 2, 9);
     this.feather1.setRotationPoint(0.0F, 12.0F, -8.0F);

     this.feather1.mirror = true;
     setRotation(this.feather1, 0.3490659F, -0.2617994F, 0.0F);
     this.feather2 = new ModelRenderer(this, 0, 116);
     this.feather2.addBox(0.0F, 0.0F, 0.0F, 1, 2, 10);
     this.feather2.setRotationPoint(0.5F, 11.0F, -6.0F);

     this.feather2.mirror = true;
     setRotation(this.feather2, 0.3490659F, 0.0F, 0.0F);
     this.head1 = new ModelRenderer(this, 38, 41);
     this.head1.addBox(0.0F, 0.0F, 0.0F, 4, 3, 4);
     this.head1.setRotationPoint(-1.0F, 15.0F, -15.0F);

     this.head1.mirror = true;
     setRotation(this.head1, 0.1396263F, 0.0F, 0.0F);
     this.rf2 = new ModelRenderer(this, 19, 58);
     this.rf2.addBox(-3.0F, 0.0F, 0.0F, 3, 3, 6);
     this.rf2.setRotationPoint(-2.0F, 14.0F, -7.0F);

     this.rf2.mirror = true;
     setRotation(this.rf2, -0.4886922F, 0.0F, 0.0F);
     this.rf3 = new ModelRenderer(this, 19, 47);
     this.rf3.addBox(-3.0F, -7.0F, 0.0F, 3, 3, 6);
     this.rf3.setRotationPoint(-2.0F, 14.0F, -7.0F);

     this.rf3.mirror = true;
     setRotation(this.rf3, -2.347623F, 0.0F, 0.0F);
     this.rf4 = new ModelRenderer(this, 20, 39);
     this.rf4.addBox(0.0F, 6.0F, 4.0F, 1, 4, 1);
     this.rf4.setRotationPoint(-3.0F, 14.0F, -7.0F);

     this.rf4.mirror = true;
     setRotation(this.rf4, -0.6283185F, 0.0F, 0.0F);
     this.rf5 = new ModelRenderer(this, 20, 39);
     this.rf5.addBox(-2.0F, 6.0F, 4.0F, 1, 4, 1);
     this.rf5.setRotationPoint(-3.0F, 14.0F, -7.0F);

     this.rf5.mirror = true;
     setRotation(this.rf5, -0.6283185F, 0.0F, 0.0F);
     this.rf6 = new ModelRenderer(this, 30, 39);
     this.rf6.addBox(-2.5F, 6.0F, 0.0F, 2, 5, 1);
     this.rf6.setRotationPoint(-2.0F, 14.0F, -7.0F);

     this.rf6.mirror = true;
     setRotation(this.rf6, 0.1745329F, 0.0F, 0.0F);
     this.lf2 = new ModelRenderer(this, 19, 58);
     this.lf2.addBox(0.0F, 0.0F, 0.0F, 3, 3, 6);
     this.lf2.setRotationPoint(4.0F, 14.0F, -7.0F);

     this.lf2.mirror = true;
     setRotation(this.lf2, -0.4886922F, 0.0F, 0.0F);
     this.lf3 = new ModelRenderer(this, 19, 47);
     this.lf3.addBox(0.0F, -7.0F, 0.0F, 3, 3, 6);
     this.lf3.setRotationPoint(4.0F, 14.0F, -7.0F);

     this.lf3.mirror = true;
     setRotation(this.lf3, -2.347623F, 0.0F, 0.0F);
     this.lf4 = new ModelRenderer(this, 20, 39);
     this.lf4.addBox(0.0F, 6.0F, 4.0F, 1, 4, 1);
     this.lf4.setRotationPoint(4.0F, 14.0F, -7.0F);

     this.lf4.mirror = true;
     setRotation(this.lf4, -0.6283185F, 0.0F, 0.0F);
     this.lf5 = new ModelRenderer(this, 20, 39);
     this.lf5.addBox(2.0F, 6.0F, 4.0F, 1, 4, 1);
     this.lf5.setRotationPoint(4.0F, 14.0F, -7.0F);

     this.lf5.mirror = true;
     setRotation(this.lf5, -0.6283185F, 0.0F, 0.0F);
     this.lf6 = new ModelRenderer(this, 30, 39);
     this.lf6.addBox(0.5F, 6.0F, -2.0F, 2, 5, 1);
     this.lf6.setRotationPoint(4.0F, 14.0F, -5.0F);

     this.lf6.mirror = true;
     setRotation(this.lf6, 0.1745329F, 0.0F, 0.0F);
     this.head2 = new ModelRenderer(this, 19, 80);
     this.head2.addBox(-1.0F, 16.0F, -16.0F, 4, 1, 5);
     this.head2.setRotationPoint(0.0F, 0.0F, 0.0F);

     this.head2.mirror = true;
     setRotation(this.head2, 0.1047198F, 0.0F, 0.0F);
     this.tail1 = new ModelRenderer(this, 9, 18);
     this.tail1.addBox(-1.0F, -1.0F, -3.0F, 2, 8, 2);
     this.tail1.setRotationPoint(1.0F, 15.0F, 9.0F);

     this.tail1.mirror = true;
     setRotation(this.tail1, 1.095163F, 0.0F, 0.0F);
   }

   public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay,
        float red, float green, float blue, float alpha) {
        tail2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        tail3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        body2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        lb2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        lb1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        spine3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        spine4.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rb1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rb2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        spine1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        spine2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        lb3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rb3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        body1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        body0.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        lf1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rf1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rb6.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rb4.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rb5.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        lb6.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        lb5.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        lb4.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        head3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        feather3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        feather1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        feather2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        head1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rf2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rf3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rf4.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rf5.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rf6.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        lf2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        lf3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        lf4.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        lf5.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        lf6.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        head2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        tail1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }

   private void setRotation(ModelRenderer model, float x, float y, float z) {
     model.rotateAngleX = x;
     model.rotateAngleY = y;
     model.rotateAngleZ = z;
   }

   public void setRotationAngles(Entity entity, float limbSwing, float limbSwingAmount,
        float ageInTicks, float netHeadYaw, float headPitch) {
// Reset shared model state before applying this frame's legacy animation.
this.tail2.setRotationPoint(1.0F, 20.0F, 13.53333F);
this.tail3.setRotationPoint(1.0F, 20.0F, 21.0F);
this.body2.setRotationPoint(0.0F, 0.0F, 0.0F);
this.lb2.setRotationPoint(5.0F, 15.0F, 0.0F);
this.lb1.setRotationPoint(5.0F, 15.0F, 0.0F);
this.spine3.setRotationPoint(1.0F, 14.0F, 6.0F);
this.spine4.setRotationPoint(1.0F, 14.0F, 6.0F);
this.rb1.setRotationPoint(-2.0F, 15.0F, 0.0F);
this.rb2.setRotationPoint(-2.0F, 15.0F, 0.0F);
this.spine1.setRotationPoint(1.0F, 14.0F, 0.0F);
this.spine2.setRotationPoint(1.0F, 14.0F, 0.0F);
this.lb3.setRotationPoint(5.0F, 15.0F, 0.0F);
this.rb3.setRotationPoint(-2.0F, 15.0F, 0.0F);
this.body1.setRotationPoint(1.0F, -1.0F, 2.0F);
this.body0.setRotationPoint(0.0F, 0.0F, 0.0F);
this.lf1.setRotationPoint(4.0F, 14.0F, -7.0F);
this.rf1.setRotationPoint(-2.0F, 14.0F, -7.0F);
this.rb6.setRotationPoint(-2.0F, 15.0F, 0.0F);
this.rb4.setRotationPoint(-2.0F, 15.0F, 0.0F);
this.rb5.setRotationPoint(-2.0F, 15.0F, 0.0F);
this.lb6.setRotationPoint(5.0F, 15.0F, 0.0F);
this.lb5.setRotationPoint(5.0F, 15.0F, 0.0F);
this.lb4.setRotationPoint(5.0F, 15.0F, 0.0F);
this.head3.setRotationPoint(-1.0F, 15.0F, -13.0F);
this.feather3.setRotationPoint(1.0F, 12.0F, -8.0F);
this.feather1.setRotationPoint(0.0F, 12.0F, -8.0F);
this.feather2.setRotationPoint(0.5F, 11.0F, -6.0F);
this.head1.setRotationPoint(-1.0F, 15.0F, -15.0F);
this.rf2.setRotationPoint(-2.0F, 14.0F, -7.0F);
this.rf3.setRotationPoint(-2.0F, 14.0F, -7.0F);
this.rf4.setRotationPoint(-3.0F, 14.0F, -7.0F);
this.rf5.setRotationPoint(-3.0F, 14.0F, -7.0F);
this.rf6.setRotationPoint(-2.0F, 14.0F, -7.0F);
this.lf2.setRotationPoint(4.0F, 14.0F, -7.0F);
this.lf3.setRotationPoint(4.0F, 14.0F, -7.0F);
this.lf4.setRotationPoint(4.0F, 14.0F, -7.0F);
this.lf5.setRotationPoint(4.0F, 14.0F, -7.0F);
this.lf6.setRotationPoint(4.0F, 14.0F, -5.0F);
this.head2.setRotationPoint(0.0F, 0.0F, 0.0F);
this.tail1.setRotationPoint(1.0F, 15.0F, 9.0F);
setRotation(this.tail2, 1.392442F, 0.0F, 0.0F);
setRotation(this.tail3, 1.72705F, 0.0F, 0.0F);
setRotation(this.body2, -0.0523599F, 0.0F, 0.0F);
setRotation(this.lb2, -0.4886922F, 0.0F, 0.0F);
setRotation(this.lb1, 0.0F, 0.0F, 0.0F);
setRotation(this.spine3, -1.117011F, 0.0F, 0.0F);
setRotation(this.spine4, -1.343904F, 0.0F, 0.0F);
setRotation(this.rb1, 0.0F, 0.0F, 0.0F);
setRotation(this.rb2, -0.4886922F, 0.0F, 0.0F);
setRotation(this.spine1, -0.8552113F, 0.0F, 0.0F);
setRotation(this.spine2, -1.169371F, 0.0F, 0.0F);
setRotation(this.lb3, -2.347623F, 0.0F, 0.0F);
setRotation(this.rb3, -2.347623F, 0.0F, 0.0F);
setRotation(this.body1, 0.0F, 0.0F, 0.0F);
setRotation(this.body0, 0.0523599F, 0.0F, 0.0F);
setRotation(this.lf1, 0.0F, 0.0F, 0.0F);
setRotation(this.rf1, 0.0F, 0.0F, 0.0F);
setRotation(this.rb6, 0.1745329F, 0.0F, 0.0F);
setRotation(this.rb4, -0.6283185F, 0.0F, 0.0F);
setRotation(this.rb5, -0.6283185F, 0.0F, 0.0F);
setRotation(this.lb6, 0.1745329F, 0.0F, 0.0F);
setRotation(this.lb5, -0.6283185F, 0.0F, 0.0F);
setRotation(this.lb4, -0.6283185F, 0.0F, 0.0F);
setRotation(this.head3, 0.5235988F, 0.0F, 0.0F);
setRotation(this.feather3, 0.3490659F, 0.2617994F, 0.0F);
setRotation(this.feather1, 0.3490659F, -0.2617994F, 0.0F);
setRotation(this.feather2, 0.3490659F, 0.0F, 0.0F);
setRotation(this.head1, 0.1396263F, 0.0F, 0.0F);
setRotation(this.rf2, -0.4886922F, 0.0F, 0.0F);
setRotation(this.rf3, -2.347623F, 0.0F, 0.0F);
setRotation(this.rf4, -0.6283185F, 0.0F, 0.0F);
setRotation(this.rf5, -0.6283185F, 0.0F, 0.0F);
setRotation(this.rf6, 0.1745329F, 0.0F, 0.0F);
setRotation(this.lf2, -0.4886922F, 0.0F, 0.0F);
setRotation(this.lf3, -2.347623F, 0.0F, 0.0F);
setRotation(this.lf4, -0.6283185F, 0.0F, 0.0F);
setRotation(this.lf5, -0.6283185F, 0.0F, 0.0F);
setRotation(this.lf6, 0.1745329F, 0.0F, 0.0F);
setRotation(this.head2, 0.1047198F, 0.0F, 0.0F);
setRotation(this.tail1, 1.095163F, 0.0F, 0.0F);

     HydroliscEntity.CustomEntity c = (HydroliscEntity.CustomEntity) entity;
     float hf = 0.0F;
     float newangle = 0.0F;

     if (limbSwingAmount > 0.1D) {
       newangle = MathHelper.cos(ageInTicks * 1.3F * this.wingspeed) * 3.1415927F * 0.25F * limbSwingAmount;
     } else {
       newangle = 0.0F;
     } 
 
     
     this.lf1.rotateAngleX = newangle;
     this.lf2.rotateAngleX = newangle - 0.488F;
     this.lf3.rotateAngleX = newangle - 2.347F;
     this.lf4.rotateAngleX = newangle - 0.628F;
     this.lf5.rotateAngleX = newangle - 0.628F;
     this.lf6.rotateAngleX = newangle + 0.174F;
     
     this.rf1.rotateAngleX = -newangle;
     this.rf2.rotateAngleX = -newangle - 0.488F;
     this.rf3.rotateAngleX = -newangle - 2.347F;
     this.rf4.rotateAngleX = -newangle - 0.628F;
     this.rf5.rotateAngleX = -newangle - 0.628F;
     this.rf6.rotateAngleX = -newangle + 0.174F;
     
     this.lb1.rotateAngleX = -newangle;
     this.lb2.rotateAngleX = -newangle - 0.488F;
     this.lb3.rotateAngleX = -newangle - 2.347F;
     this.lb4.rotateAngleX = -newangle - 0.628F;
     this.lb5.rotateAngleX = -newangle - 0.628F;
     this.lb6.rotateAngleX = -newangle + 0.174F;
     
     this.rb1.rotateAngleX = newangle;
     this.rb2.rotateAngleX = newangle - 0.488F;
     this.rb3.rotateAngleX = newangle - 2.347F;
     this.rb4.rotateAngleX = newangle - 0.628F;
     this.rb5.rotateAngleX = newangle - 0.628F;
     this.rb6.rotateAngleX = newangle + 0.174F;
 
     
     newangle = MathHelper.cos(ageInTicks * 1.0F * this.wingspeed) * 3.1415927F * 0.15F;
     if (c.isChildModel() == true) {
       newangle = 0.0F;
     }
     this.tail1.rotateAngleY = newangle * 0.25F;
     this.tail1.rotationPointZ += (float)Math.cos(this.tail1.rotateAngleY) * 5.0F;
     this.tail1.rotationPointX += (float)Math.sin(this.tail1.rotateAngleY) * 5.0F;
     this.tail2.rotateAngleY = newangle * 0.5F;
     this.tail2.rotationPointZ += (float)Math.cos(this.tail2.rotateAngleY) * 8.0F;
     this.tail2.rotationPointX += (float)Math.sin(this.tail2.rotateAngleY) * 8.0F;
     this.tail3.rotateAngleY = newangle * 0.75F;
 
     
     hf = c.getHydroHealth() / c.getMaxHealth();
     newangle = MathHelper.cos(ageInTicks * 1.25F * this.wingspeed * hf) * 3.1415927F * 0.2F * hf;
     this.feather2.rotateAngleY = newangle;
     newangle = MathHelper.cos(ageInTicks * 0.75F * this.wingspeed * hf) * 3.1415927F * 0.2F * hf;
     this.feather1.rotateAngleY = newangle - 0.9F;
     this.feather3.rotateAngleY = -newangle + 0.9F;
    }
 }
}