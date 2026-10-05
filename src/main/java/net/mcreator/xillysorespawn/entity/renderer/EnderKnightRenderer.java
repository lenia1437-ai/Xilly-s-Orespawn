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
import net.mcreator.xillysorespawn.entity.EnderKnightEntity;
import net.mcreator.xillysorespawn.entity.OreSpawnLogic;

@OnlyIn(Dist.CLIENT)
public class EnderKnightRenderer {
    private static final String TEXTURE = "xillys_orespawn:textures/entities/enderknighttexture.png";
    public static class ModelRegisterHandler {
        @SubscribeEvent @OnlyIn(Dist.CLIENT)
        public void registerModels(ModelRegistryEvent event) {
            RenderingRegistry.registerEntityRenderingHandler(EnderKnightEntity.entity, manager ->
                new MobRenderer(manager, new ModelEnderKnight(0.21F), 0.3F) {
                    @Override public ResourceLocation getEntityTexture(Entity entity) { return new ResourceLocation(TEXTURE); }
                    @Override protected void preRenderCallback(LivingEntity entity, MatrixStack stack, float partialTick) {
                        float modelScale = 1.0F;
                        stack.scale(modelScale, modelScale, modelScale);
                    }
                });
        }
    }

public static class ModelEnderKnight extends EntityModel<Entity>
 {
   private final ModelRenderer rleg1;
   private final ModelRenderer rleg3;
   private final ModelRenderer pelvis;
   private final ModelRenderer spine1;
   private final ModelRenderer spine2;
   private final ModelRenderer spine3;
   private final ModelRenderer neck;
   private final ModelRenderer rleg2;
   private final ModelRenderer rhip;
   private final ModelRenderer rib4;
   private final ModelRenderer rib3;
   private final ModelRenderer rib2;
   private final ModelRenderer rib1;
   private final ModelRenderer rfoot1;
   private final ModelRenderer rfoot3;
   private final ModelRenderer rcollar;
   private final ModelRenderer lcollar;
   private final ModelRenderer lleg3;
   private final ModelRenderer lleg2;
   private final ModelRenderer lhip;
   private final ModelRenderer lleg1;
   private final ModelRenderer rfoot4;
   private final ModelRenderer rfoot2;
   private final ModelRenderer cape2;
   private final ModelRenderer cape1;
   private final ModelRenderer lfoot1;
   private final ModelRenderer lfoot3;
   private final ModelRenderer lfoot2;
   private final ModelRenderer lfoot4;
   private final ModelRenderer head;
   private final ModelRenderer lshoulder;
   private final ModelRenderer rshoulder;
   private final ModelRenderer rarm3;
   private final ModelRenderer rarm2;
   private final ModelRenderer rarm1;
   private final ModelRenderer larm3;
   private final ModelRenderer larm2;
   private final ModelRenderer larm1;
   private final ModelRenderer blade;
   private final ModelRenderer handle;
   private float wingspeed = 1.0F;

   public ModelEnderKnight(float f1) {
     this.wingspeed = f1;

     this.textureWidth = 512;
     this.textureHeight = 512;

     this.rleg1 = new ModelRenderer(this, 20, 50);
     this.rleg1.addBox(0.0F, 12.0F, -1.0F, 1, 15, 1);
     this.rleg1.setRotationPoint(-7.0F, -5.0F, -2.0F);

     this.rleg1.mirror = true;
     setRotation(this.rleg1, 0.0F, 0.0F, 0.0F);
     this.rleg3 = new ModelRenderer(this, 20, 100);
     this.rleg3.addBox(0.0F, 0.0F, 0.0F, 1, 14, 2);
     this.rleg3.setRotationPoint(-6.0F, -5.0F, -2.0F);

     this.rleg3.mirror = true;
     setRotation(this.rleg3, -0.1F, 0.0F, 0.0F);
     this.pelvis = new ModelRenderer(this, 20, 150);
     this.pelvis.addBox(0.0F, 0.0F, 0.0F, 3, 3, 3);
     this.pelvis.setRotationPoint(-5.0F, -6.0F, -2.0F);

     this.pelvis.mirror = true;
     setRotation(this.pelvis, 0.0F, 0.0F, 0.0F);
     this.spine1 = new ModelRenderer(this, 20, 200);
     this.spine1.addBox(0.0F, 0.0F, 0.0F, 1, 5, 1);
     this.spine1.setRotationPoint(-4.0F, -9.0F, 1.0F);

     this.spine1.mirror = true;
     setRotation(this.spine1, -0.3F, 0.0F, 0.0F);
     this.spine2 = new ModelRenderer(this, 20, 250);
     this.spine2.addBox(0.0F, 0.0F, 0.0F, 1, 5, 1);
     this.spine2.setRotationPoint(-4.0F, -13.0F, 1.0F);

     this.spine2.mirror = true;
     setRotation(this.spine2, 0.0F, 0.0F, 0.0F);
     this.spine3 = new ModelRenderer(this, 20, 300);
     this.spine3.addBox(0.0F, 0.0F, 0.0F, 1, 5, 1);
     this.spine3.setRotationPoint(-4.0F, -17.0F, 0.0F);

     this.spine3.mirror = true;
     setRotation(this.spine3, 0.2F, 0.0F, 0.0F);
     this.neck = new ModelRenderer(this, 20, 11);
     this.neck.addBox(0.0F, 0.0F, 0.0F, 5, 3, 3);
     this.neck.setRotationPoint(-6.0F, -20.0F, 0.0F);

     this.neck.mirror = true;
     setRotation(this.neck, 0.0F, 0.0F, 0.0F);
     this.rleg2 = new ModelRenderer(this, 20, 400);
     this.rleg2.addBox(0.0F, 0.0F, 0.0F, 1, 14, 2);
     this.rleg2.setRotationPoint(-8.0F, -5.0F, -2.0F);

     this.rleg2.mirror = true;
     setRotation(this.rleg2, -0.1F, 0.0F, 0.0F);
     this.rhip = new ModelRenderer(this, 20, 450);
     this.rhip.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1);
     this.rhip.setRotationPoint(-7.0F, -4.0F, -2.0F);

     this.rhip.mirror = true;
     setRotation(this.rhip, 0.0F, 0.0F, 0.0F);
     this.rib4 = new ModelRenderer(this, 20, 79);
     this.rib4.addBox(0.0F, 0.0F, 0.0F, 3, 1, 1);
     this.rib4.setRotationPoint(-5.0F, -9.0F, 1.0F);

     this.rib4.mirror = true;
     setRotation(this.rib4, 0.0F, 0.0F, 0.0F);
     this.rib3 = new ModelRenderer(this, 20, 86);
     this.rib3.addBox(0.0F, 0.0F, 0.0F, 3, 1, 1);
     this.rib3.setRotationPoint(-5.0F, -11.0F, 1.0F);

     this.rib3.mirror = true;
     setRotation(this.rib3, 0.0F, 0.0F, 0.0F);
     this.rib2 = new ModelRenderer(this, 20, 94);
     this.rib2.addBox(0.0F, 0.0F, 0.0F, 5, 1, 1);
     this.rib2.setRotationPoint(-6.0F, -13.0F, 1.0F);

     this.rib2.mirror = true;
     setRotation(this.rib2, 0.0F, 0.0F, 0.0F);
     this.rib1 = new ModelRenderer(this, 20, 122);
     this.rib1.addBox(0.0F, 0.0F, 0.0F, 5, 1, 1);
     this.rib1.setRotationPoint(-6.0F, -16.0F, 0.0F);

     this.rib1.mirror = true;
     setRotation(this.rib1, 0.0F, 0.0F, 0.0F);
     this.rfoot1 = new ModelRenderer(this, 20, 131);
     this.rfoot1.addBox(0.0F, 21.0F, -2.0F, 3, 8, 3);
     this.rfoot1.setRotationPoint(-8.0F, -5.0F, -2.0F);

     this.rfoot1.mirror = true;
     setRotation(this.rfoot1, 0.0F, 0.0F, 0.0F);
     this.rfoot3 = new ModelRenderer(this, 20, 162);
     this.rfoot3.addBox(0.0F, 27.0F, -5.0F, 3, 2, 6);
     this.rfoot3.setRotationPoint(-8.0F, -5.0F, -2.0F);

     this.rfoot3.mirror = true;
     setRotation(this.rfoot3, 0.0F, 0.0F, 0.0F);
     this.rcollar = new ModelRenderer(this, 20, 243);
     this.rcollar.addBox(0.0F, 0.0F, 0.0F, 5, 1, 1);
     this.rcollar.setRotationPoint(-11.0F, -19.0F, 1.0F);

     this.rcollar.mirror = true;
     setRotation(this.rcollar, 0.0F, 0.0F, 0.0F);
     this.lcollar = new ModelRenderer(this, 20, 286);
     this.lcollar.addBox(0.0F, 0.0F, 0.0F, 5, 1, 1);
     this.lcollar.setRotationPoint(-1.0F, -19.0F, 1.0F);

     this.lcollar.mirror = true;
     setRotation(this.lcollar, 0.0F, 0.0F, 0.0F);
     this.lleg3 = new ModelRenderer(this, 48, 159);
     this.lleg3.addBox(0.0F, 0.0F, 0.0F, 1, 14, 2);
     this.lleg3.setRotationPoint(-2.0F, -5.0F, -2.0F);

     this.lleg3.mirror = true;
     setRotation(this.lleg3, -0.1F, 0.0F, 0.0F);
     this.lleg2 = new ModelRenderer(this, 28, 187);
     this.lleg2.addBox(0.0F, 0.0F, 0.0F, 1, 14, 2);
     this.lleg2.setRotationPoint(0.0F, -5.0F, -2.0F);

     this.lleg2.mirror = true;
     setRotation(this.lleg2, -0.1F, 0.0F, 0.0F);
     this.lhip = new ModelRenderer(this, 32, 219);
     this.lhip.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1);
     this.lhip.setRotationPoint(-1.0F, -4.0F, -2.0F);

     this.lhip.mirror = true;
     setRotation(this.lhip, 0.0F, 0.0F, 0.0F);
     this.lleg1 = new ModelRenderer(this, 36, 224);
     this.lleg1.addBox(0.0F, 12.0F, -1.0F, 1, 15, 1);
     this.lleg1.setRotationPoint(-1.0F, -5.0F, -2.0F);

     this.lleg1.mirror = true;
     setRotation(this.lleg1, 0.0F, 0.0F, 0.0F);
     this.rfoot4 = new ModelRenderer(this, 33, 254);
     this.rfoot4.addBox(0.0F, 26.0F, -3.0F, 3, 1, 1);
     this.rfoot4.setRotationPoint(-8.0F, -5.0F, -2.0F);

     this.rfoot4.mirror = true;
     setRotation(this.rfoot4, 0.0F, 0.0F, 0.0F);
     this.rfoot2 = new ModelRenderer(this, 32, 36);
     this.rfoot2.addBox(0.0F, 19.5F, -19.0F, 3, 1, 5);
     this.rfoot2.setRotationPoint(-8.0F, -5.0F, -2.0F);

     this.rfoot2.mirror = true;
     setRotation(this.rfoot2, 0.6F, 0.0F, 0.0F);
     this.cape2 = new ModelRenderer(this, 51, 276);
     this.cape2.addBox(-4.0F, 0.0F, 0.0F, 9, 24, 0);
     this.cape2.setRotationPoint(-4.0F, -20.0F, 4.0F);

     this.cape2.mirror = true;
     setRotation(this.cape2, 0.0F, 0.0F, 0.0F);
     this.cape1 = new ModelRenderer(this, 51, 264);
     this.cape1.addBox(0.0F, 0.0F, 0.0F, 9, 1, 1);
     this.cape1.setRotationPoint(-8.0F, -20.0F, 3.0F);

     this.cape1.mirror = true;
     setRotation(this.cape1, 0.0F, 0.0F, 0.0F);
     this.lfoot1 = new ModelRenderer(this, 44, 182);
     this.lfoot1.addBox(0.0F, 21.0F, -2.0F, 3, 8, 3);
     this.lfoot1.setRotationPoint(-2.0F, -5.0F, -2.0F);

     this.lfoot1.mirror = true;
     setRotation(this.lfoot1, 0.0F, 0.0F, 0.0F);
     this.lfoot3 = new ModelRenderer(this, 52, 200);
     this.lfoot3.addBox(0.0F, 27.0F, -5.0F, 3, 2, 6);
     this.lfoot3.setRotationPoint(-2.0F, -5.0F, -2.0F);

     this.lfoot3.mirror = true;
     setRotation(this.lfoot3, 0.0F, 0.0F, 0.0F);
     this.lfoot2 = new ModelRenderer(this, 52, 218);
     this.lfoot2.addBox(0.0F, 19.5F, -19.0F, 3, 1, 5);
     this.lfoot2.setRotationPoint(-2.0F, -5.0F, -2.0F);

     this.lfoot2.mirror = true;
     setRotation(this.lfoot2, 0.6F, 0.0F, 0.0F);
     this.lfoot4 = new ModelRenderer(this, 48, 235);
     this.lfoot4.addBox(0.0F, 26.0F, -3.0F, 3, 1, 1);
     this.lfoot4.setRotationPoint(-2.0F, -5.0F, -2.0F);

     this.lfoot4.mirror = true;
     setRotation(this.lfoot4, 0.0F, 0.0F, 0.0F);
     this.head = new ModelRenderer(this, 34, 106);
     this.head.addBox(-4.0F, -8.0F, -4.0F, 7, 6, 6);
     this.head.setRotationPoint(-3.0F, -18.0F, 3.0F);

     this.head.mirror = true;
     setRotation(this.head, 0.0F, 0.0F, 0.0F);
     this.lshoulder = new ModelRenderer(this, 48, 16);
     this.lshoulder.addBox(0.0F, 0.0F, 0.0F, 5, 4, 4);
     this.lshoulder.setRotationPoint(2.0F, -21.0F, 0.0F);

     this.lshoulder.mirror = true;
     setRotation(this.lshoulder, 0.0F, 0.0F, 0.0F);
     this.rshoulder = new ModelRenderer(this, 48, 16);
     this.rshoulder.addBox(0.0F, 0.0F, 0.0F, 5, 4, 4);
     this.rshoulder.setRotationPoint(-14.0F, -21.0F, 0.0F);

     this.rshoulder.mirror = true;
     setRotation(this.rshoulder, 0.0F, 0.0F, 0.0F);
     this.rarm3 = new ModelRenderer(this, 39, 64);
     this.rarm3.addBox(0.0F, 0.0F, 0.0F, 1, 12, 1);
     this.rarm3.setRotationPoint(-11.0F, -18.0F, 1.0F);

     this.rarm3.mirror = true;
     setRotation(this.rarm3, -0.5F, 0.0F, 0.0F);
     this.rarm2 = new ModelRenderer(this, 57, 62);
     this.rarm2.addBox(0.0F, 0.0F, 0.0F, 1, 12, 1);
     this.rarm2.setRotationPoint(-13.0F, -18.0F, 1.0F);

     this.rarm2.mirror = true;
     setRotation(this.rarm2, -0.5F, 0.0F, 0.0F);
     this.rarm1 = new ModelRenderer(this, 49, 81);
     this.rarm1.addBox(0.0F, 0.0F, 0.0F, 1, 11, 1);
     this.rarm1.setRotationPoint(-12.0F, -18.0F, 2.0F);

     this.rarm1.mirror = true;
     setRotation(this.rarm1, -1.0F, -1.0F, 0.0F);
     this.larm3 = new ModelRenderer(this, 49, 129);
     this.larm3.addBox(0.0F, 0.0F, 0.0F, 1, 12, 1);
     this.larm3.setRotationPoint(3.0F, -18.0F, 1.0F);

     this.larm3.mirror = true;
     setRotation(this.larm3, -0.5F, 0.0F, 0.0F);
     this.larm2 = new ModelRenderer(this, 64, 133);
     this.larm2.addBox(0.0F, 0.0F, 0.0F, 1, 12, 1);
     this.larm2.setRotationPoint(5.0F, -18.0F, 1.0F);

     this.larm2.mirror = true;
     setRotation(this.larm2, -0.5F, 0.0F, 0.0F);
     this.larm1 = new ModelRenderer(this, 22, 316);
     this.larm1.addBox(0.0F, 0.0F, 0.0F, 1, 11, 1);
     this.larm1.setRotationPoint(4.0F, -18.0F, 1.0F);

     this.larm1.mirror = true;
     setRotation(this.larm1, -1.0F, 1.0F, 0.0F);
     this.blade = new ModelRenderer(this, 36, 304);
     this.blade.addBox(0.0F, -34.0F, -2.0F, 1, 32, 6);
     this.blade.setRotationPoint(-4.0F, -2.0F, -8.0F);

     this.blade.mirror = true;
     setRotation(this.blade, 0.35F, 0.0F, 0.0F);
     this.handle = new ModelRenderer(this, 18, 26);
     this.handle.addBox(0.0F, -2.0F, 0.0F, 1, 4, 1);
     this.handle.setRotationPoint(-4.0F, -2.0F, -8.0F);

     this.handle.mirror = true;
     setRotation(this.handle, 0.35F, 0.0F, 0.0F);
   }

   public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay,
        float red, float green, float blue, float alpha) {
        rleg1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rleg3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        pelvis.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        spine1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        spine2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        spine3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        neck.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rleg2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rhip.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rib4.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rib3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rib2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rib1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rfoot1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rfoot3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rcollar.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        lcollar.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        lleg3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        lleg2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        lhip.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        lleg1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rfoot4.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rfoot2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        cape2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        cape1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        lfoot1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        lfoot3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        lfoot2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        lfoot4.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        head.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        lshoulder.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rshoulder.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rarm3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rarm2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rarm1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        larm3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        larm2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        larm1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        blade.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        handle.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }

   private void setRotation(ModelRenderer model, float x, float y, float z) {
     model.rotateAngleX = x;
     model.rotateAngleY = y;
     model.rotateAngleZ = z;
   }

   public void setRotationAngles(Entity entity, float limbSwing, float limbSwingAmount,
        float ageInTicks, float netHeadYaw, float headPitch) {
// Reset shared model state before applying this frame's legacy animation.
this.rleg1.setRotationPoint(-7.0F, -5.0F, -2.0F);
this.rleg3.setRotationPoint(-6.0F, -5.0F, -2.0F);
this.pelvis.setRotationPoint(-5.0F, -6.0F, -2.0F);
this.spine1.setRotationPoint(-4.0F, -9.0F, 1.0F);
this.spine2.setRotationPoint(-4.0F, -13.0F, 1.0F);
this.spine3.setRotationPoint(-4.0F, -17.0F, 0.0F);
this.neck.setRotationPoint(-6.0F, -20.0F, 0.0F);
this.rleg2.setRotationPoint(-8.0F, -5.0F, -2.0F);
this.rhip.setRotationPoint(-7.0F, -4.0F, -2.0F);
this.rib4.setRotationPoint(-5.0F, -9.0F, 1.0F);
this.rib3.setRotationPoint(-5.0F, -11.0F, 1.0F);
this.rib2.setRotationPoint(-6.0F, -13.0F, 1.0F);
this.rib1.setRotationPoint(-6.0F, -16.0F, 0.0F);
this.rfoot1.setRotationPoint(-8.0F, -5.0F, -2.0F);
this.rfoot3.setRotationPoint(-8.0F, -5.0F, -2.0F);
this.rcollar.setRotationPoint(-11.0F, -19.0F, 1.0F);
this.lcollar.setRotationPoint(-1.0F, -19.0F, 1.0F);
this.lleg3.setRotationPoint(-2.0F, -5.0F, -2.0F);
this.lleg2.setRotationPoint(0.0F, -5.0F, -2.0F);
this.lhip.setRotationPoint(-1.0F, -4.0F, -2.0F);
this.lleg1.setRotationPoint(-1.0F, -5.0F, -2.0F);
this.rfoot4.setRotationPoint(-8.0F, -5.0F, -2.0F);
this.rfoot2.setRotationPoint(-8.0F, -5.0F, -2.0F);
this.cape2.setRotationPoint(-4.0F, -20.0F, 4.0F);
this.cape1.setRotationPoint(-8.0F, -20.0F, 3.0F);
this.lfoot1.setRotationPoint(-2.0F, -5.0F, -2.0F);
this.lfoot3.setRotationPoint(-2.0F, -5.0F, -2.0F);
this.lfoot2.setRotationPoint(-2.0F, -5.0F, -2.0F);
this.lfoot4.setRotationPoint(-2.0F, -5.0F, -2.0F);
this.head.setRotationPoint(-3.0F, -18.0F, 3.0F);
this.lshoulder.setRotationPoint(2.0F, -21.0F, 0.0F);
this.rshoulder.setRotationPoint(-14.0F, -21.0F, 0.0F);
this.rarm3.setRotationPoint(-11.0F, -18.0F, 1.0F);
this.rarm2.setRotationPoint(-13.0F, -18.0F, 1.0F);
this.rarm1.setRotationPoint(-12.0F, -18.0F, 2.0F);
this.larm3.setRotationPoint(3.0F, -18.0F, 1.0F);
this.larm2.setRotationPoint(5.0F, -18.0F, 1.0F);
this.larm1.setRotationPoint(4.0F, -18.0F, 1.0F);
this.blade.setRotationPoint(-4.0F, -2.0F, -8.0F);
this.handle.setRotationPoint(-4.0F, -2.0F, -8.0F);
setRotation(this.rleg1, 0.0F, 0.0F, 0.0F);
setRotation(this.rleg3, -0.1F, 0.0F, 0.0F);
setRotation(this.pelvis, 0.0F, 0.0F, 0.0F);
setRotation(this.spine1, -0.3F, 0.0F, 0.0F);
setRotation(this.spine2, 0.0F, 0.0F, 0.0F);
setRotation(this.spine3, 0.2F, 0.0F, 0.0F);
setRotation(this.neck, 0.0F, 0.0F, 0.0F);
setRotation(this.rleg2, -0.1F, 0.0F, 0.0F);
setRotation(this.rhip, 0.0F, 0.0F, 0.0F);
setRotation(this.rib4, 0.0F, 0.0F, 0.0F);
setRotation(this.rib3, 0.0F, 0.0F, 0.0F);
setRotation(this.rib2, 0.0F, 0.0F, 0.0F);
setRotation(this.rib1, 0.0F, 0.0F, 0.0F);
setRotation(this.rfoot1, 0.0F, 0.0F, 0.0F);
setRotation(this.rfoot3, 0.0F, 0.0F, 0.0F);
setRotation(this.rcollar, 0.0F, 0.0F, 0.0F);
setRotation(this.lcollar, 0.0F, 0.0F, 0.0F);
setRotation(this.lleg3, -0.1F, 0.0F, 0.0F);
setRotation(this.lleg2, -0.1F, 0.0F, 0.0F);
setRotation(this.lhip, 0.0F, 0.0F, 0.0F);
setRotation(this.lleg1, 0.0F, 0.0F, 0.0F);
setRotation(this.rfoot4, 0.0F, 0.0F, 0.0F);
setRotation(this.rfoot2, 0.6F, 0.0F, 0.0F);
setRotation(this.cape2, 0.0F, 0.0F, 0.0F);
setRotation(this.cape1, 0.0F, 0.0F, 0.0F);
setRotation(this.lfoot1, 0.0F, 0.0F, 0.0F);
setRotation(this.lfoot3, 0.0F, 0.0F, 0.0F);
setRotation(this.lfoot2, 0.6F, 0.0F, 0.0F);
setRotation(this.lfoot4, 0.0F, 0.0F, 0.0F);
setRotation(this.head, 0.0F, 0.0F, 0.0F);
setRotation(this.lshoulder, 0.0F, 0.0F, 0.0F);
setRotation(this.rshoulder, 0.0F, 0.0F, 0.0F);
setRotation(this.rarm3, -0.5F, 0.0F, 0.0F);
setRotation(this.rarm2, -0.5F, 0.0F, 0.0F);
setRotation(this.rarm1, -1.0F, -1.0F, 0.0F);
setRotation(this.larm3, -0.5F, 0.0F, 0.0F);
setRotation(this.larm2, -0.5F, 0.0F, 0.0F);
setRotation(this.larm1, -1.0F, 1.0F, 0.0F);
setRotation(this.blade, 0.35F, 0.0F, 0.0F);
setRotation(this.handle, 0.35F, 0.0F, 0.0F);

     EnderKnightEntity.CustomEntity e = (EnderKnightEntity.CustomEntity) entity;

     float newangle = 0.0F;
     
     if (limbSwingAmount > 0.1D) {
       newangle = MathHelper.cos(ageInTicks * 1.3F * this.wingspeed) * 3.1415927F * 0.25F * limbSwingAmount;
     } else {
       newangle = 0.0F;
     } 
     
     this.lfoot1.rotateAngleX = newangle;
     this.lfoot2.rotateAngleX = 0.6F + newangle;
     this.lfoot3.rotateAngleX = newangle;
     this.lfoot4.rotateAngleX = newangle;
     this.lleg1.rotateAngleX = newangle;
     this.lleg2.rotateAngleX = -0.1F + newangle;
     this.lleg3.rotateAngleX = -0.1F + newangle;
     this.rfoot1.rotateAngleX = -newangle;
     this.rfoot2.rotateAngleX = 0.6F - newangle;
     this.rfoot3.rotateAngleX = -newangle;
     this.rfoot4.rotateAngleX = -newangle;
     this.rleg1.rotateAngleX = -newangle;
     this.rleg2.rotateAngleX = -0.1F - newangle;
     this.rleg3.rotateAngleX = -0.1F - newangle;
     this.cape2.rotateAngleZ = newangle / 4.0F;
     
     newangle = MathHelper.cos(ageInTicks * 0.7F * this.wingspeed) * 3.1415927F * 0.02F;
     this.cape2.rotateAngleX = newangle;
     
     this.head.rotateAngleY = (float)Math.toRadians(netHeadYaw) * 0.45F;
     if (this.head.rotateAngleY > 0.45F) this.head.rotateAngleY = 0.45F; 
     if (this.head.rotateAngleY < -0.45F) this.head.rotateAngleY = -0.45F;
     
     newangle = MathHelper.cos(ageInTicks * 2.7F * this.wingspeed) * 3.1415927F * 0.3F;
     if (e.isScreaming()) {
       this.larm2.rotateAngleX = -1.2F + newangle;
       this.larm3.rotateAngleX = -1.2F + newangle;
       this.rarm2.rotateAngleX = -1.2F + newangle;
       this.rarm3.rotateAngleX = -1.2F + newangle;
       this.larm1.rotateAngleX = -1.8F + newangle;
       this.rarm1.rotateAngleX = -1.8F + newangle;
       this.handle.rotateAngleX = 0.5F + newangle * 3.0F / 2.0F;
     } else {
       this.larm2.rotateAngleX = -0.5F;
       this.larm3.rotateAngleX = -0.5F;
       this.larm1.rotateAngleZ = 0.0F;
       this.larm1.rotateAngleY = 1.0F;
       this.larm1.rotateAngleX = -1.0F;
       this.rarm2.rotateAngleX = -0.5F;
       this.rarm3.rotateAngleX = -0.5F;
       this.rarm1.rotateAngleZ = 0.0F;
       this.rarm1.rotateAngleY = -1.0F;
       this.rarm1.rotateAngleX = -1.0F;
       this.handle.rotateAngleX = 0.35F;
     } 
     this.larm1.rotationPointY = (float)(this.larm2.rotationPointY + Math.cos(this.larm2.rotateAngleX) * 10.0D);
     this.larm1.rotationPointZ = (float)(this.larm2.rotationPointZ + Math.sin(this.larm2.rotateAngleX) * 10.0D);
     this.rarm1.rotationPointY = (float)(this.rarm2.rotationPointY + Math.cos(this.rarm2.rotateAngleX) * 10.0D);
     this.rarm1.rotationPointZ = (float)(this.rarm2.rotationPointZ + Math.sin(this.rarm2.rotateAngleX) * 10.0D);
     this.handle.rotationPointY = (float)(this.rarm1.rotationPointY + Math.cos(this.rarm1.rotateAngleX) * 7.0D) + 1.0F;
     this.handle.rotationPointZ = (float)(this.rarm1.rotationPointZ + Math.sin(this.rarm1.rotateAngleX) * 7.0D);
     this.blade.setRotationPoint(this.handle.rotationPointX, this.handle.rotationPointY, this.handle.rotationPointZ);
     this.blade.rotateAngleX = this.handle.rotateAngleX;
     this.blade.rotateAngleY = this.handle.rotateAngleY;
     this.blade.rotateAngleZ = this.handle.rotateAngleZ;
    }
 }
}
