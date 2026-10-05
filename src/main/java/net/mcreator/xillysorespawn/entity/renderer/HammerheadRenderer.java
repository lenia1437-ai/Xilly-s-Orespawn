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
import net.mcreator.xillysorespawn.entity.HammerheadEntity;
import net.mcreator.xillysorespawn.entity.OreSpawnLogic;

@OnlyIn(Dist.CLIENT)
public class HammerheadRenderer {
    private static final String TEXTURE = "xillys_orespawn:textures/entities/hammerheadtexture.png";
    public static class ModelRegisterHandler {
        @SubscribeEvent @OnlyIn(Dist.CLIENT)
        public void registerModels(ModelRegistryEvent event) {
            RenderingRegistry.registerEntityRenderingHandler(HammerheadEntity.entity, manager ->
                new MobRenderer(manager, new ModelHammerhead(0.33F), 2.5F) {
                    @Override public ResourceLocation getEntityTexture(Entity entity) { return new ResourceLocation(TEXTURE); }
                    @Override protected void preRenderCallback(LivingEntity entity, MatrixStack stack, float partialTick) {
                        float modelScale = 2.5F;
                        stack.scale(modelScale, modelScale, modelScale);
                    }
                });
        }
    }

public static class ModelHammerhead extends EntityModel<Entity>
 {
   private float wingspeed = 1.0F;

   private final ModelRenderer chest;

   private final ModelRenderer abdomen;

   private final ModelRenderer neck;
   private final ModelRenderer head;
   private final ModelRenderer snout;
   private final ModelRenderer neck_armour;
   private final ModelRenderer horn_base;
   private final ModelRenderer horn_1;
   private final ModelRenderer horn_2;
   private final ModelRenderer horn_R;
   private final ModelRenderer horn_L;
   private final ModelRenderer back_armour1;
   private final ModelRenderer back_armour_2;
   private final ModelRenderer back_armour_3;
   private final ModelRenderer back_armour_3R;
   private final ModelRenderer back_armour_4;
   private final ModelRenderer back_armour_4R;
   private final ModelRenderer tail;
   private final ModelRenderer leg_1R;
   private final ModelRenderer leg_1;
   private final ModelRenderer leg_2;
   private final ModelRenderer leg_2R;
   private final ModelRenderer leg_3R;
   private final ModelRenderer leg_3;
   private final ModelRenderer leg_1Rb;
   private final ModelRenderer leg_1b;
   private final ModelRenderer leg_2b;
   private final ModelRenderer leg_2Rb;
   private final ModelRenderer leg_3Rb;
   private final ModelRenderer leg_3b;
   private final ModelRenderer fan1;
   private final ModelRenderer Lfan2;
   private final ModelRenderer Rfan2;
   private final ModelRenderer Lfan3;
   private final ModelRenderer Rfan3;
   private final ModelRenderer Lear;
   private final ModelRenderer Rear;

   public ModelHammerhead(float f1) {
     this.wingspeed = f1;

     this.textureWidth = 222;
     this.textureHeight = 256;

     this.chest = new ModelRenderer(this, 0, 0);
     this.chest.addBox(-9.0F, -1.0F, 0.0F, 19, 16, 17);
     this.chest.setRotationPoint(0.0F, -1.0F, -12.0F);

     this.chest.mirror = true;
     setRotation(this.chest, 0.0349066F, 0.0F, 0.0F);
     this.abdomen = new ModelRenderer(this, 0, 34);
     this.abdomen.addBox(-7.5F, 0.0F, 0.0F, 16, 14, 16);
     this.abdomen.setRotationPoint(0.0F, -2.0F, 4.0F);

     this.abdomen.mirror = true;
     setRotation(this.abdomen, -0.0349066F, 0.0F, 0.0F);
     this.neck = new ModelRenderer(this, 146, 59);
     this.neck.addBox(-6.5F, -0.5F, -12.0F, 14, 13, 13);
     this.neck.setRotationPoint(0.0F, -1.0F, -12.0F);

     this.neck.mirror = true;
     setRotation(this.neck, 0.1570796F, 0.0F, 0.0F);
     this.head = new ModelRenderer(this, 101, 59);
     this.head.addBox(-6.0F, -0.5F, -21.0F, 13, 11, 9);
     this.head.setRotationPoint(0.0F, -1.0F, -12.0F);

     this.head.mirror = true;
     setRotation(this.head, 0.2094395F, 0.0F, 0.0F);
     this.snout = new ModelRenderer(this, 166, 86);
     this.snout.addBox(-4.0F, -6.0F, -27.0F, 9, 8, 8);
     this.snout.setRotationPoint(0.0F, -1.0F, -12.0F);

     this.snout.mirror = true;
     setRotation(this.snout, 0.6108652F, 0.0F, 0.0F);
     this.neck_armour = new ModelRenderer(this, 73, 0);
     this.neck_armour.addBox(-7.0F, -1.5F, -18.0F, 15, 4, 18);
     this.neck_armour.setRotationPoint(0.0F, -1.0F, -12.0F);

     this.neck_armour.mirror = true;
     setRotation(this.neck_armour, 0.1570796F, 0.0F, 0.0F);
     this.horn_base = new ModelRenderer(this, 49, 35);
     this.horn_base.addBox(-7.0F, -1.5F, -27.0F, 15, 5, 9);
     this.horn_base.setRotationPoint(0.0F, -1.0F, -12.0F);

     this.horn_base.mirror = true;
     setRotation(this.horn_base, 0.0872665F, 0.0F, 0.0F);
     this.horn_1 = new ModelRenderer(this, 122, 23);
     this.horn_1.addBox(-12.0F, -4.5F, -40.0F, 25, 6, 14);
     this.horn_1.setRotationPoint(0.0F, -1.0F, -12.0F);

     this.horn_1.mirror = true;
     setRotation(this.horn_1, 0.1919862F, 0.0F, 0.0F);
     this.horn_2 = new ModelRenderer(this, 106, 44);
     this.horn_2.addBox(-18.0F, -3.5F, -37.0F, 37, 4, 10);
     this.horn_2.setRotationPoint(0.0F, -1.0F, -12.0F);

     this.horn_2.mirror = true;
     setRotation(this.horn_2, 0.1919862F, 0.0F, 0.0F);
     this.horn_R = new ModelRenderer(this, 158, 0);
     this.horn_R.addBox(-26.0F, -5.5F, -38.5F, 8, 7, 13);
     this.horn_R.setRotationPoint(0.0F, -1.0F, -12.0F);

     this.horn_R.mirror = true;
     setRotation(this.horn_R, 0.1919862F, 0.0F, -0.0174533F);
     this.horn_L = new ModelRenderer(this, 158, 0);
     this.horn_L.addBox(19.0F, -5.5F, -38.5F, 8, 7, 13);
     this.horn_L.setRotationPoint(0.0F, -1.0F, -12.0F);

     this.horn_L.mirror = true;
     setRotation(this.horn_L, 0.1919862F, 0.0F, -0.0174533F);
     this.back_armour1 = new ModelRenderer(this, 0, 98);
     this.back_armour1.addBox(-5.0F, -2.5F, -6.0F, 9, 3, 7);
     this.back_armour1.setRotationPoint(1.0F, -4.0F, -15.0F);

     this.back_armour1.mirror = true;
     setRotation(this.back_armour1, -0.0872665F, 0.0F, 0.0F);
     this.back_armour_2 = new ModelRenderer(this, 0, 65);
     this.back_armour_2.addBox(-8.0F, -4.5F, -13.0F, 17, 4, 28);
     this.back_armour_2.setRotationPoint(0.0F, -1.0F, -3.0F);

     this.back_armour_2.mirror = true;
     setRotation(this.back_armour_2, -0.122173F, 0.0F, 0.0F);
     this.back_armour_3 = new ModelRenderer(this, 15, 104);
     this.back_armour_3.addBox(0.5F, -3.5F, -13.0F, 4, 4, 20);
     this.back_armour_3.setRotationPoint(8.0F, 1.0F, -2.0F);

     this.back_armour_3.mirror = true;
     setRotation(this.back_armour_3, 0.0174533F, 0.1570796F, 0.0F);
     this.back_armour_3R = new ModelRenderer(this, 15, 104);
     this.back_armour_3R.addBox(-3.5F, -3.5F, -13.0F, 4, 4, 20);
     this.back_armour_3R.setRotationPoint(-8.0F, 1.0F, -2.0F);

     this.back_armour_3R.mirror = true;
     setRotation(this.back_armour_3R, 0.0174533F, -0.1570796F, 0.0F);
     this.back_armour_4 = new ModelRenderer(this, 0, 65);
     this.back_armour_4.addBox(1.5F, -1.5F, -3.0F, 3, 4, 10);
     this.back_armour_4.setRotationPoint(6.0F, 5.0F, -10.0F);

     this.back_armour_4.mirror = true;
     setRotation(this.back_armour_4, -0.1396263F, 0.3490659F, 0.0F);
     this.back_armour_4R = new ModelRenderer(this, 0, 65);
     this.back_armour_4R.addBox(-1.5F, -1.5F, -3.0F, 3, 4, 10);
     this.back_armour_4R.setRotationPoint(-8.0F, 5.0F, -11.0F);

     this.back_armour_4R.mirror = true;
     setRotation(this.back_armour_4R, -0.1396263F, -0.3490659F, 0.0F);
     this.tail = new ModelRenderer(this, 66, 52);
     this.tail.addBox(-2.0F, 0.0F, -3.0F, 5, 5, 3);
     this.tail.setRotationPoint(0.0F, 0.0F, 20.0F);

     this.tail.mirror = true;
     setRotation(this.tail, 0.5061455F, 0.0F, 0.0F);
     this.leg_1R = new ModelRenderer(this, 71, 102);
     this.leg_1R.addBox(-2.5F, -2.5F, -3.0F, 5, 10, 6);
     this.leg_1R.setRotationPoint(-9.0F, 11.0F, -10.0F);

     this.leg_1R.mirror = true;
     setRotation(this.leg_1R, -0.0872665F, 0.0F, 0.0F);
     this.leg_1 = new ModelRenderer(this, 64, 76);
     this.leg_1.addBox(-1.5F, -2.5F, -3.0F, 5, 10, 6);
     this.leg_1.setRotationPoint(9.0F, 11.0F, -10.0F);

     this.leg_1.mirror = true;
     setRotation(this.leg_1, -0.0872665F, 0.0F, 0.0F);
     this.leg_2 = new ModelRenderer(this, 98, 28);
     this.leg_2.addBox(-1.5F, -2.5F, -3.0F, 5, 9, 6);
     this.leg_2.setRotationPoint(9.0F, 12.0F, -2.0F);

     this.leg_2.mirror = true;
     setRotation(this.leg_2, -0.0523599F, 0.0F, 0.0F);
     this.leg_2R = new ModelRenderer(this, 98, 80);
     this.leg_2R.addBox(-1.5F, -2.5F, -3.0F, 5, 9, 6);
     this.leg_2R.setRotationPoint(-10.0F, 12.0F, -2.0F);

     this.leg_2R.mirror = true;
     setRotation(this.leg_2R, -0.0523599F, 0.0F, 0.0F);
     this.leg_3R = new ModelRenderer(this, 44, 129);
     this.leg_3R.addBox(-3.5F, -2.5F, -3.0F, 5, 11, 8);
     this.leg_3R.setRotationPoint(-7.0F, 9.0F, 14.0F);

     this.leg_3R.mirror = true;
     setRotation(this.leg_3R, -0.3490659F, 0.0F, 0.0F);
     this.leg_3 = new ModelRenderer(this, 44, 99);
     this.leg_3.addBox(-3.5F, -2.5F, -3.0F, 5, 11, 8);
     this.leg_3.setRotationPoint(10.0F, 9.0F, 14.0F);

     this.leg_3.mirror = true;
     setRotation(this.leg_3, -0.3490659F, 0.0F, 0.0F);
     this.leg_1Rb = new ModelRenderer(this, 15, 129);
     this.leg_1Rb.addBox(-2.0F, 5.5F, -3.0F, 4, 8, 5);
     this.leg_1Rb.setRotationPoint(-9.0F, 11.0F, -10.0F);

     this.leg_1Rb.mirror = true;
     setRotation(this.leg_1Rb, 0.0F, 0.0F, 0.0F);
     this.leg_1b = new ModelRenderer(this, 15, 110);
     this.leg_1b.addBox(-1.0F, 5.5F, -3.0F, 4, 8, 5);
     this.leg_1b.setRotationPoint(9.0F, 11.0F, -10.0F);

     this.leg_1b.mirror = true;
     setRotation(this.leg_1b, 0.0F, 0.0F, 0.0F);
     this.leg_2b = new ModelRenderer(this, 57, 1);
     this.leg_2b.addBox(-1.0F, 5.5F, -3.0F, 4, 7, 5);
     this.leg_2b.setRotationPoint(9.0F, 12.0F, -2.0F);

     this.leg_2b.mirror = true;
     setRotation(this.leg_2b, 0.0523599F, 0.0F, 0.0F);
     this.leg_2Rb = new ModelRenderer(this, 94, 106);
     this.leg_2Rb.addBox(-2.0F, 5.5F, -3.0F, 4, 7, 5);
     this.leg_2Rb.setRotationPoint(-9.0F, 12.0F, -2.0F);

     this.leg_2Rb.mirror = true;
     setRotation(this.leg_2Rb, 0.0523599F, 0.0F, 0.0F);
     this.leg_3Rb = new ModelRenderer(this, 122, 81);
     this.leg_3Rb.addBox(-2.0F, 6.5F, -5.0F, 4, 9, 5);
     this.leg_3Rb.setRotationPoint(-8.0F, 9.0F, 14.0F);

     this.leg_3Rb.mirror = true;
     setRotation(this.leg_3Rb, 0.122173F, 0.0F, 0.0F);
     this.leg_3b = new ModelRenderer(this, 122, 0);
     this.leg_3b.addBox(-3.0F, 6.5F, -5.0F, 4, 9, 5);
     this.leg_3b.setRotationPoint(10.0F, 9.0F, 14.0F);

     this.leg_3b.mirror = true;
     setRotation(this.leg_3b, 0.122173F, 0.0F, 0.0F);
     this.fan1 = new ModelRenderer(this, 0, 109);
     this.fan1.addBox(-1.0F, -7.0F, -34.0F, 4, 15, 1);
     this.fan1.setRotationPoint(0.0F, -1.0F, -12.0F);

     this.fan1.mirror = true;
     setRotation(this.fan1, -0.1396263F, 0.0F, 0.0F);
     this.Lfan2 = new ModelRenderer(this, 0, 109);
     this.Lfan2.addBox(-1.0F, -3.0F, -31.5F, 4, 12, 1);
     this.Lfan2.setRotationPoint(0.0F, -1.0F, -14.0F);

     this.Lfan2.mirror = true;
     setRotation(this.Lfan2, -0.2094395F, -0.122173F, 0.0F);
     this.Rfan2 = new ModelRenderer(this, 0, 109);
     this.Rfan2.addBox(-1.0F, -3.0F, -33.5F, 4, 12, 1);
     this.Rfan2.setRotationPoint(0.0F, -1.0F, -12.0F);

     this.Rfan2.mirror = true;
     setRotation(this.Rfan2, -0.2094395F, 0.122173F, 0.0F);
     this.Lfan3 = new ModelRenderer(this, 0, 109);
     this.Lfan3.addBox(-1.0F, 4.0F, -32.0F, 4, 9, 1);
     this.Lfan3.setRotationPoint(0.0F, -1.0F, -12.0F);

     this.Lfan3.mirror = true;
     setRotation(this.Lfan3, -0.3316126F, -0.2268928F, 0.0F);
     this.Rfan3 = new ModelRenderer(this, 0, 109);
     this.Rfan3.addBox(-1.0F, 4.0F, -32.0F, 4, 9, 1);
     this.Rfan3.setRotationPoint(0.0F, -1.0F, -12.0F);

     this.Rfan3.mirror = true;
     setRotation(this.Rfan3, -0.3316126F, 0.2443461F, 0.0F);
     this.Lear = new ModelRenderer(this, 0, 80);
     this.Lear.addBox(8.5F, 2.5F, -10.0F, 1, 1, 10);
     this.Lear.setRotationPoint(0.0F, -1.0F, -12.0F);

     this.Lear.mirror = true;
     setRotation(this.Lear, 0.3665191F, 0.2268928F, 0.0F);
     this.Rear = new ModelRenderer(this, 0, 80);
     this.Rear.addBox(-8.5F, 2.5F, -11.0F, 1, 1, 10);
     this.Rear.setRotationPoint(0.0F, -1.0F, -12.0F);

     this.Rear.mirror = true;
     setRotation(this.Rear, 0.3665191F, -0.2268928F, 0.0F);
   }

   public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay,
        float red, float green, float blue, float alpha) {
        chest.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        abdomen.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        neck.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        head.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        snout.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        neck_armour.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        horn_base.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        horn_1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        horn_2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        horn_R.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        horn_L.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        back_armour1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        back_armour_2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        back_armour_3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        back_armour_3R.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        back_armour_4.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        back_armour_4R.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        tail.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        leg_1R.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        leg_1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        leg_2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        leg_2R.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        leg_3R.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        leg_3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        leg_1Rb.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        leg_1b.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        leg_2b.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        leg_2Rb.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        leg_3Rb.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        leg_3b.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        fan1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Lfan2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Rfan2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Lfan3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Rfan3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Lear.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Rear.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }

   private void setRotation(ModelRenderer model, float x, float y, float z) {
     model.rotateAngleX = x;
     model.rotateAngleY = y;
     model.rotateAngleZ = z;
   }

   public void setRotationAngles(Entity entity, float limbSwing, float limbSwingAmount,
        float ageInTicks, float netHeadYaw, float headPitch) {
// Reset shared model state before applying this frame's legacy animation.
this.chest.setRotationPoint(0.0F, -1.0F, -12.0F);
this.abdomen.setRotationPoint(0.0F, -2.0F, 4.0F);
this.neck.setRotationPoint(0.0F, -1.0F, -12.0F);
this.head.setRotationPoint(0.0F, -1.0F, -12.0F);
this.snout.setRotationPoint(0.0F, -1.0F, -12.0F);
this.neck_armour.setRotationPoint(0.0F, -1.0F, -12.0F);
this.horn_base.setRotationPoint(0.0F, -1.0F, -12.0F);
this.horn_1.setRotationPoint(0.0F, -1.0F, -12.0F);
this.horn_2.setRotationPoint(0.0F, -1.0F, -12.0F);
this.horn_R.setRotationPoint(0.0F, -1.0F, -12.0F);
this.horn_L.setRotationPoint(0.0F, -1.0F, -12.0F);
this.back_armour1.setRotationPoint(1.0F, -4.0F, -15.0F);
this.back_armour_2.setRotationPoint(0.0F, -1.0F, -3.0F);
this.back_armour_3.setRotationPoint(8.0F, 1.0F, -2.0F);
this.back_armour_3R.setRotationPoint(-8.0F, 1.0F, -2.0F);
this.back_armour_4.setRotationPoint(6.0F, 5.0F, -10.0F);
this.back_armour_4R.setRotationPoint(-8.0F, 5.0F, -11.0F);
this.tail.setRotationPoint(0.0F, 0.0F, 20.0F);
this.leg_1R.setRotationPoint(-9.0F, 11.0F, -10.0F);
this.leg_1.setRotationPoint(9.0F, 11.0F, -10.0F);
this.leg_2.setRotationPoint(9.0F, 12.0F, -2.0F);
this.leg_2R.setRotationPoint(-10.0F, 12.0F, -2.0F);
this.leg_3R.setRotationPoint(-7.0F, 9.0F, 14.0F);
this.leg_3.setRotationPoint(10.0F, 9.0F, 14.0F);
this.leg_1Rb.setRotationPoint(-9.0F, 11.0F, -10.0F);
this.leg_1b.setRotationPoint(9.0F, 11.0F, -10.0F);
this.leg_2b.setRotationPoint(9.0F, 12.0F, -2.0F);
this.leg_2Rb.setRotationPoint(-9.0F, 12.0F, -2.0F);
this.leg_3Rb.setRotationPoint(-8.0F, 9.0F, 14.0F);
this.leg_3b.setRotationPoint(10.0F, 9.0F, 14.0F);
this.fan1.setRotationPoint(0.0F, -1.0F, -12.0F);
this.Lfan2.setRotationPoint(0.0F, -1.0F, -14.0F);
this.Rfan2.setRotationPoint(0.0F, -1.0F, -12.0F);
this.Lfan3.setRotationPoint(0.0F, -1.0F, -12.0F);
this.Rfan3.setRotationPoint(0.0F, -1.0F, -12.0F);
this.Lear.setRotationPoint(0.0F, -1.0F, -12.0F);
this.Rear.setRotationPoint(0.0F, -1.0F, -12.0F);
setRotation(this.chest, 0.0349066F, 0.0F, 0.0F);
setRotation(this.abdomen, -0.0349066F, 0.0F, 0.0F);
setRotation(this.neck, 0.1570796F, 0.0F, 0.0F);
setRotation(this.head, 0.2094395F, 0.0F, 0.0F);
setRotation(this.snout, 0.6108652F, 0.0F, 0.0F);
setRotation(this.neck_armour, 0.1570796F, 0.0F, 0.0F);
setRotation(this.horn_base, 0.0872665F, 0.0F, 0.0F);
setRotation(this.horn_1, 0.1919862F, 0.0F, 0.0F);
setRotation(this.horn_2, 0.1919862F, 0.0F, 0.0F);
setRotation(this.horn_R, 0.1919862F, 0.0F, -0.0174533F);
setRotation(this.horn_L, 0.1919862F, 0.0F, -0.0174533F);
setRotation(this.back_armour1, -0.0872665F, 0.0F, 0.0F);
setRotation(this.back_armour_2, -0.122173F, 0.0F, 0.0F);
setRotation(this.back_armour_3, 0.0174533F, 0.1570796F, 0.0F);
setRotation(this.back_armour_3R, 0.0174533F, -0.1570796F, 0.0F);
setRotation(this.back_armour_4, -0.1396263F, 0.3490659F, 0.0F);
setRotation(this.back_armour_4R, -0.1396263F, -0.3490659F, 0.0F);
setRotation(this.tail, 0.5061455F, 0.0F, 0.0F);
setRotation(this.leg_1R, -0.0872665F, 0.0F, 0.0F);
setRotation(this.leg_1, -0.0872665F, 0.0F, 0.0F);
setRotation(this.leg_2, -0.0523599F, 0.0F, 0.0F);
setRotation(this.leg_2R, -0.0523599F, 0.0F, 0.0F);
setRotation(this.leg_3R, -0.3490659F, 0.0F, 0.0F);
setRotation(this.leg_3, -0.3490659F, 0.0F, 0.0F);
setRotation(this.leg_1Rb, 0.0F, 0.0F, 0.0F);
setRotation(this.leg_1b, 0.0F, 0.0F, 0.0F);
setRotation(this.leg_2b, 0.0523599F, 0.0F, 0.0F);
setRotation(this.leg_2Rb, 0.0523599F, 0.0F, 0.0F);
setRotation(this.leg_3Rb, 0.122173F, 0.0F, 0.0F);
setRotation(this.leg_3b, 0.122173F, 0.0F, 0.0F);
setRotation(this.fan1, -0.1396263F, 0.0F, 0.0F);
setRotation(this.Lfan2, -0.2094395F, -0.122173F, 0.0F);
setRotation(this.Rfan2, -0.2094395F, 0.122173F, 0.0F);
setRotation(this.Lfan3, -0.3316126F, -0.2268928F, 0.0F);
setRotation(this.Rfan3, -0.3316126F, 0.2443461F, 0.0F);
setRotation(this.Lear, 0.3665191F, 0.2268928F, 0.0F);
setRotation(this.Rear, 0.3665191F, -0.2268928F, 0.0F);

     HammerheadEntity.CustomEntity e = (HammerheadEntity.CustomEntity) entity;

     float newangle = 0.0F;
     float newangle2 = 0.0F;
     
     if (limbSwingAmount > 0.1D) {
       newangle = MathHelper.cos(ageInTicks * 1.3F * this.wingspeed) * 3.1415927F * 0.1F * limbSwingAmount;
       newangle2 = MathHelper.cos((float)((ageInTicks * 1.3F * this.wingspeed) + 0.7853981633974483D)) * 3.1415927F * 0.1F * limbSwingAmount;
     } else {
       newangle = 0.0F;
     } 
     
     this.leg_1.rotateAngleX = -0.087F + newangle;
     this.leg_1b.rotateAngleX = newangle;
     this.leg_1R.rotateAngleX = -0.087F - newangle;
     this.leg_1Rb.rotateAngleX = -newangle;
     
     this.leg_2.rotateAngleX = -0.052F + newangle2;
     this.leg_2b.rotateAngleX = newangle2;
     this.leg_2R.rotateAngleX = -0.052F - newangle2;
     this.leg_2Rb.rotateAngleX = -newangle2;
     
     this.leg_3.rotateAngleX = -0.349F - newangle;
     this.leg_3b.rotateAngleX = -newangle;
     this.leg_3R.rotateAngleX = -0.349F + newangle;
     this.leg_3Rb.rotateAngleX = newangle;
     
     this.neck.rotateAngleY = (float)Math.toRadians(netHeadYaw) * 0.25F;
     this.neck_armour.rotateAngleY = this.neck.rotateAngleY;
     this.horn_base.rotateAngleY = this.neck.rotateAngleY;
     this.horn_1.rotateAngleY = this.neck.rotateAngleY;
     this.horn_2.rotateAngleY = this.neck.rotateAngleY;
     this.horn_L.rotateAngleY = this.neck.rotateAngleY;
     this.horn_R.rotateAngleY = this.neck.rotateAngleY;
     this.head.rotateAngleY = this.neck.rotateAngleY;
     this.snout.rotateAngleY = this.neck.rotateAngleY;
     this.fan1.rotateAngleY = this.neck.rotateAngleY;
     this.neck.rotateAngleY -= 0.122F;
     this.neck.rotateAngleY -= 0.226F;
     this.neck.rotateAngleY += 0.122F;
     this.neck.rotateAngleY += 0.226F;
     this.neck.rotateAngleY += 0.227F;
     this.neck.rotateAngleY -= 0.227F;
     
     newangle = MathHelper.cos(ageInTicks * 0.3F * this.wingspeed) * 3.1415927F * 0.03F;
     this.back_armour_4.rotateAngleY = 0.349F + newangle;
     this.back_armour_4R.rotateAngleY = -0.349F - newangle;
     
     if (e.getAttacking() != 0) {
       newangle = MathHelper.cos(ageInTicks * 1.3F * this.wingspeed) * 3.1415927F * 0.13F;
     } else {
       newangle = 0.0F;
     } 
     
     this.neck.rotateAngleX = newangle + 0.157F;
     this.neck_armour.rotateAngleX = newangle + 0.157F;
     this.horn_base.rotateAngleX = newangle + 0.087F;
     this.horn_1.rotateAngleX = newangle + 0.192F;
     this.horn_2.rotateAngleX = newangle + 0.192F;
     this.horn_L.rotateAngleX = newangle + 0.192F;
     this.horn_R.rotateAngleX = newangle + 0.192F;
     this.head.rotateAngleX = newangle + 0.209F;
     this.snout.rotateAngleX = newangle + 0.611F;
     this.fan1.rotateAngleX = newangle - 0.139F;
     this.Lfan2.rotateAngleX = newangle - 0.209F;
     this.Lfan3.rotateAngleX = newangle - 0.331F;
     this.Rfan2.rotateAngleX = newangle - 0.209F;
     this.Rfan3.rotateAngleX = newangle - 0.331F;
     this.Lear.rotateAngleX = newangle + 0.366F;
     this.Rear.rotateAngleX = newangle + 0.366F;
    }
 }
}