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
import net.mcreator.xillysorespawn.entity.EnderReaperEntity;
import net.mcreator.xillysorespawn.entity.OreSpawnLogic;

@OnlyIn(Dist.CLIENT)
public class EnderReaperRenderer {
    private static final String TEXTURE = "xillys_orespawn:textures/entities/enderreapertexture.png";
    public static class ModelRegisterHandler {
        @SubscribeEvent @OnlyIn(Dist.CLIENT)
        public void registerModels(ModelRegistryEvent event) {
            RenderingRegistry.registerEntityRenderingHandler(EnderReaperEntity.entity, manager ->
                new MobRenderer(manager, new ModelEnderReaper(0.23F), 0.2F) {
                    @Override public ResourceLocation getEntityTexture(Entity entity) { return new ResourceLocation(TEXTURE); }
                    @Override protected void preRenderCallback(LivingEntity entity, MatrixStack stack, float partialTick) {
                        float modelScale = 1.0F;
                        stack.scale(modelScale, modelScale, modelScale);
                    }
                });
        }
    }

public static class ModelEnderReaper extends EntityModel<Entity>
 {
   private final ModelRenderer rwing1;
   private final ModelRenderer lwing1;
   private final ModelRenderer Shape3;
   private final ModelRenderer Shape4;
   private final ModelRenderer Shape5;
   private final ModelRenderer Shape6;
   private final ModelRenderer Shape7;
   private final ModelRenderer Shape8;
   private final ModelRenderer Shape9;
   private final ModelRenderer Shape10;
   private final ModelRenderer Shape11;
   private final ModelRenderer Shape12;
   private final ModelRenderer Shape13;
   private final ModelRenderer Shape14;
   private final ModelRenderer Shape15;
   private final ModelRenderer Shape16;
   private final ModelRenderer Shape17;
   private final ModelRenderer Shape18;
   private final ModelRenderer Shape19;
   private final ModelRenderer Shape20;
   private final ModelRenderer Shape21;
   private final ModelRenderer Shape22;
   private final ModelRenderer Shape23;
   private final ModelRenderer Shape24;
   private final ModelRenderer Shape25;
   private final ModelRenderer Shape26;
   private final ModelRenderer Shape27;
   private final ModelRenderer Shape28;
   private final ModelRenderer Shape29;
   private final ModelRenderer Shape30;
   private final ModelRenderer Shape31;
   private final ModelRenderer Shape32;
   private final ModelRenderer Shape33;
   private final ModelRenderer Shape34;
   private final ModelRenderer Shape35;
   private final ModelRenderer Shape36;
   private final ModelRenderer Shape37;
   private final ModelRenderer Shape38;
   private final ModelRenderer Shape39;
   private final ModelRenderer Shape40;
   private final ModelRenderer Shape41;
   private final ModelRenderer Shape42;
   private final ModelRenderer Shape43;
   private final ModelRenderer Shape44;
   private final ModelRenderer Shape45;
   private final ModelRenderer Shape46;
   private final ModelRenderer Shape47;
   private final ModelRenderer Shape48;
   private final ModelRenderer Shape49;
   private final ModelRenderer rarm2;
   private final ModelRenderer rarm3;
   private final ModelRenderer relbow;
   private final ModelRenderer rarm1;
   private final ModelRenderer Shape54;
   private final ModelRenderer larm3;
   private final ModelRenderer larm2;
   private final ModelRenderer lelbow;
   private final ModelRenderer larm1;
   private final ModelRenderer scythe1;
   private final ModelRenderer scythe2;
   private final ModelRenderer scythe3;
   private final ModelRenderer head;
   private final ModelRenderer lwing3;
   private final ModelRenderer lwing2;
   private final ModelRenderer rwing3;
   private final ModelRenderer rwing2;
   private float wingspeed = 1.0F;

   public ModelEnderReaper(float f1) {
     this.wingspeed = f1;

     this.textureWidth = 512;
     this.textureHeight = 512;

     this.rwing1 = new ModelRenderer(this, 20, 430);
     this.rwing1.addBox(0.0F, 0.0F, 0.0F, 0, 50, 17);
     this.rwing1.setRotationPoint(-4.0F, -6.9F, 8.5F);

     this.rwing1.mirror = true;
     setRotation(this.rwing1, 1.745F, -0.785F, 0.0F);
     this.lwing1 = new ModelRenderer(this, 20, 350);
     this.lwing1.addBox(0.0F, 0.0F, 0.0F, 0, 50, 17);
     this.lwing1.setRotationPoint(4.0F, -6.9F, 8.5F);

     this.lwing1.mirror = true;
     setRotation(this.lwing1, 1.745F, 0.785F, 0.0F);
     this.Shape3 = new ModelRenderer(this, 20, 320);
     this.Shape3.addBox(-4.0F, 0.0F, -2.0F, 2, 12, 1);
     this.Shape3.setRotationPoint(3.0F, -14.0F, 10.0F);

     this.Shape3.mirror = true;
     setRotation(this.Shape3, 0.0F, 0.0F, 0.0F);
     this.Shape4 = new ModelRenderer(this, 40, 320);
     this.Shape4.addBox(-4.0F, 0.0F, -2.0F, 2, 6, 1);
     this.Shape4.setRotationPoint(3.0F, -2.0F, 10.0F);

     this.Shape4.mirror = true;
     setRotation(this.Shape4, -0.247F, 0.0F, 0.0F);
     this.Shape5 = new ModelRenderer(this, 20, 310);
     this.Shape5.addBox(-4.0F, 0.0F, -2.0F, 1, 3, 1);
     this.Shape5.setRotationPoint(3.5F, 4.0F, 8.0F);

     this.Shape5.mirror = true;
     setRotation(this.Shape5, -0.768F, 0.0F, 0.0F);
     this.Shape6 = new ModelRenderer(this, 20, 292);
     this.Shape6.addBox(-4.0F, 0.0F, -2.0F, 2, 6, 2);
     this.Shape6.setRotationPoint(3.0F, -12.0F, 7.5F);

     this.Shape6.mirror = true;
     setRotation(this.Shape6, -2.356F, 0.0F, 0.0F);
     this.Shape7 = new ModelRenderer(this, 20, 280);
     this.Shape7.addBox(-4.0F, 0.0F, -2.0F, 4, 1, 1);
     this.Shape7.setRotationPoint(5.0F, -14.0F, 10.0F);

     this.Shape7.mirror = true;
     setRotation(this.Shape7, 0.0F, 0.0F, 0.0F);
     this.Shape8 = new ModelRenderer(this, 20, 269);
     this.Shape8.addBox(-4.0F, 0.0F, -2.0F, 4, 1, 1);
     this.Shape8.setRotationPoint(-1.0F, -14.0F, 10.0F);

     this.Shape8.mirror = true;
     setRotation(this.Shape8, 0.0F, 0.0F, 0.0F);
     this.Shape9 = new ModelRenderer(this, 20, 257);
     this.Shape9.addBox(-4.0F, 0.0F, -2.0F, 4, 1, 1);
     this.Shape9.setRotationPoint(-1.0F, -12.0F, 10.0F);

     this.Shape9.mirror = true;
     setRotation(this.Shape9, 0.0F, 0.0F, 0.0F);
     this.Shape10 = new ModelRenderer(this, 20, 246);
     this.Shape10.addBox(-4.0F, 0.0F, -2.0F, 4, 1, 1);
     this.Shape10.setRotationPoint(-1.0F, -10.0F, 10.0F);

     this.Shape10.mirror = true;
     setRotation(this.Shape10, 0.0F, 0.0F, 0.0F);
     this.Shape11 = new ModelRenderer(this, 20, 237);
     this.Shape11.addBox(-4.0F, 0.0F, -2.0F, 4, 1, 1);
     this.Shape11.setRotationPoint(-1.0F, -8.0F, 10.0F);

     this.Shape11.mirror = true;
     setRotation(this.Shape11, 0.0F, 0.0F, 0.0F);
     this.Shape12 = new ModelRenderer(this, 20, 228);
     this.Shape12.addBox(-4.0F, 0.0F, -2.0F, 2, 1, 1);
     this.Shape12.setRotationPoint(1.0F, -6.0F, 10.0F);

     this.Shape12.mirror = true;
     setRotation(this.Shape12, 0.0F, 0.0F, 0.0F);
     this.Shape13 = new ModelRenderer(this, 20, 219);
     this.Shape13.addBox(-4.0F, 0.0F, -2.0F, 3, 1, 1);
     this.Shape13.setRotationPoint(1.0F, -4.0F, 10.0F);

     this.Shape13.mirror = true;
     setRotation(this.Shape13, 0.0F, 0.0F, 0.0F);
     this.Shape14 = new ModelRenderer(this, 20, 209);
     this.Shape14.addBox(-4.0F, 0.0F, -2.0F, 1, 1, 1);
     this.Shape14.setRotationPoint(3.5F, -14.0F, 11.0F);

     this.Shape14.mirror = true;
     setRotation(this.Shape14, 0.0F, 0.0F, 0.0F);
     this.Shape15 = new ModelRenderer(this, 20, 201);
     this.Shape15.addBox(-4.0F, 0.0F, -2.0F, 1, 1, 1);
     this.Shape15.setRotationPoint(3.5F, -12.0F, 11.0F);

     this.Shape15.mirror = true;
     setRotation(this.Shape15, 0.0F, 0.0F, 0.0F);
     this.Shape16 = new ModelRenderer(this, 20, 194);
     this.Shape16.addBox(-4.0F, 0.0F, -2.0F, 1, 1, 1);
     this.Shape16.setRotationPoint(3.5F, -10.0F, 11.0F);

     this.Shape16.mirror = true;
     setRotation(this.Shape16, 0.0F, 0.0F, 0.0F);
     this.Shape17 = new ModelRenderer(this, 20, 185);
     this.Shape17.addBox(-4.0F, 0.0F, -2.0F, 1, 1, 1);
     this.Shape17.setRotationPoint(3.5F, -8.0F, 11.0F);

     this.Shape17.mirror = true;
     setRotation(this.Shape17, 0.0F, 0.0F, 0.0F);
     this.Shape18 = new ModelRenderer(this, 20, 175);
     this.Shape18.addBox(-4.0F, 0.0F, -2.0F, 1, 1, 1);
     this.Shape18.setRotationPoint(3.5F, -6.0F, 11.0F);

     this.Shape18.mirror = true;
     setRotation(this.Shape18, 0.0F, 0.0F, 0.0F);
     this.Shape19 = new ModelRenderer(this, 20, 165);
     this.Shape19.addBox(-4.0F, 0.0F, -2.0F, 1, 1, 1);
     this.Shape19.setRotationPoint(3.5F, -4.0F, 11.0F);

     this.Shape19.mirror = true;
     setRotation(this.Shape19, 0.0F, 0.0F, 0.0F);
     this.Shape20 = new ModelRenderer(this, 20, 155);
     this.Shape20.addBox(-4.0F, 0.0F, -2.0F, 4, 1, 1);
     this.Shape20.setRotationPoint(5.0F, -12.0F, 10.0F);

     this.Shape20.mirror = true;
     setRotation(this.Shape20, 0.0F, 0.0F, 0.0F);
     this.Shape21 = new ModelRenderer(this, 20, 146);
     this.Shape21.addBox(-4.0F, 0.0F, -2.0F, 4, 1, 1);
     this.Shape21.setRotationPoint(5.0F, -10.0F, 10.0F);

     this.Shape21.mirror = true;
     setRotation(this.Shape21, 0.0F, 0.0F, 0.0F);
     this.Shape22 = new ModelRenderer(this, 20, 139);
     this.Shape22.addBox(-4.0F, 0.0F, -2.0F, 4, 1, 1);
     this.Shape22.setRotationPoint(5.0F, -8.0F, 10.0F);

     this.Shape22.mirror = true;
     setRotation(this.Shape22, 0.0F, 0.0F, 0.0F);
     this.Shape23 = new ModelRenderer(this, 20, 132);
     this.Shape23.addBox(-4.0F, 0.0F, -2.0F, 3, 1, 1);
     this.Shape23.setRotationPoint(5.0F, -6.0F, 10.0F);

     this.Shape23.mirror = true;
     setRotation(this.Shape23, 0.0F, 0.0F, 0.0F);
     this.Shape24 = new ModelRenderer(this, 20, 124);
     this.Shape24.addBox(-4.0F, 0.0F, -2.0F, 2, 1, 1);
     this.Shape24.setRotationPoint(5.0F, -4.0F, 10.0F);

     this.Shape24.mirror = true;
     setRotation(this.Shape24, 0.0F, 0.0F, 0.0F);
     this.Shape25 = new ModelRenderer(this, 20, 114);
     this.Shape25.addBox(-4.0F, 0.0F, -2.0F, 1, 1, 3);
     this.Shape25.setRotationPoint(6.0F, -4.0F, 8.0F);

     this.Shape25.mirror = true;
     setRotation(this.Shape25, 0.0F, 0.0F, 0.0F);
     this.Shape26 = new ModelRenderer(this, 20, 106);
     this.Shape26.addBox(-4.0F, 0.0F, -2.0F, 2, 1, 1);
     this.Shape26.setRotationPoint(5.0F, -4.0F, 8.0F);

     this.Shape26.mirror = true;
     setRotation(this.Shape26, 0.0F, 0.0F, 0.0F);
     this.Shape27 = new ModelRenderer(this, 20, 94);
     this.Shape27.addBox(-4.0F, 0.0F, -2.0F, 1, 1, 5);
     this.Shape27.setRotationPoint(7.0F, -6.0F, 6.0F);

     this.Shape27.mirror = true;
     setRotation(this.Shape27, 0.0F, 0.0F, 0.0F);
     this.Shape28 = new ModelRenderer(this, 20, 83);
     this.Shape28.addBox(-4.0F, 0.0F, -2.0F, 1, 1, 5);
     this.Shape28.setRotationPoint(8.0F, -8.0F, 5.0F);

     this.Shape28.mirror = true;
     setRotation(this.Shape28, 0.0F, 0.0F, 0.0F);
     this.Shape29 = new ModelRenderer(this, 20, 70);
     this.Shape29.addBox(-4.0F, 0.0F, -2.0F, 1, 1, 6);
     this.Shape29.setRotationPoint(8.0F, -10.0F, 4.0F);

     this.Shape29.mirror = true;
     setRotation(this.Shape29, 0.0F, 0.0F, 0.0F);
     this.Shape30 = new ModelRenderer(this, 20, 59);
     this.Shape30.addBox(-4.0F, 0.0F, -2.0F, 1, 1, 6);
     this.Shape30.setRotationPoint(8.0F, -12.0F, 4.0F);

     this.Shape30.mirror = true;
     setRotation(this.Shape30, 0.0F, 0.0F, 0.0F);
     this.Shape31 = new ModelRenderer(this, 20, 47);
     this.Shape31.addBox(-4.0F, 0.0F, -2.0F, 1, 1, 6);
     this.Shape31.setRotationPoint(8.0F, -14.0F, 4.0F);

     this.Shape31.mirror = true;
     setRotation(this.Shape31, 0.0F, 0.0F, 0.0F);
     this.Shape32 = new ModelRenderer(this, 20, 37);
     this.Shape32.addBox(-4.0F, 0.0F, -2.0F, 2, 1, 1);
     this.Shape32.setRotationPoint(6.0F, -6.0F, 6.0F);

     this.Shape32.mirror = true;
     setRotation(this.Shape32, 0.0F, 0.0F, 0.0F);
     this.Shape33 = new ModelRenderer(this, 20, 29);
     this.Shape33.addBox(-4.0F, 0.0F, -2.0F, 4, 1, 1);
     this.Shape33.setRotationPoint(5.0F, -8.0F, 5.0F);

     this.Shape33.mirror = true;
     setRotation(this.Shape33, 0.0F, 0.0F, 0.0F);
     this.Shape34 = new ModelRenderer(this, 40, 312);
     this.Shape34.addBox(-4.0F, 0.0F, -2.0F, 4, 1, 1);
     this.Shape34.setRotationPoint(5.0F, -10.0F, 4.0F);

     this.Shape34.mirror = true;
     setRotation(this.Shape34, 0.0F, 0.0F, 0.0F);
     this.Shape35 = new ModelRenderer(this, 40, 301);
     this.Shape35.addBox(-4.0F, 0.0F, -2.0F, 4, 1, 1);
     this.Shape35.setRotationPoint(5.0F, -12.0F, 4.0F);

     this.Shape35.mirror = true;
     setRotation(this.Shape35, 0.0F, 0.0F, 0.0F);
     this.Shape36 = new ModelRenderer(this, 40, 291);
     this.Shape36.addBox(-4.0F, 0.0F, -2.0F, 4, 1, 1);
     this.Shape36.setRotationPoint(5.0F, -14.0F, 4.0F);

     this.Shape36.mirror = true;
     setRotation(this.Shape36, 0.0F, 0.0F, 0.0F);
     this.Shape37 = new ModelRenderer(this, 40, 278);
     this.Shape37.addBox(-4.0F, 0.0F, -2.0F, 1, 1, 3);
     this.Shape37.setRotationPoint(1.0F, -4.0F, 8.0F);

     this.Shape37.mirror = true;
     setRotation(this.Shape37, 0.0F, 0.0F, 0.0F);
     this.Shape38 = new ModelRenderer(this, 40, 265);
     this.Shape38.addBox(-4.0F, 0.0F, -2.0F, 1, 1, 5);
     this.Shape38.setRotationPoint(0.0F, -6.0F, 6.0F);

     this.Shape38.mirror = true;
     setRotation(this.Shape38, 0.0F, 0.0F, 0.0F);
     this.Shape39 = new ModelRenderer(this, 40, 251);
     this.Shape39.addBox(-4.0F, 0.0F, -2.0F, 1, 1, 6);
     this.Shape39.setRotationPoint(-1.0F, -8.0F, 5.0F);

     this.Shape39.mirror = true;
     setRotation(this.Shape39, 0.0F, 0.0F, 0.0F);
     this.Shape40 = new ModelRenderer(this, 40, 235);
     this.Shape40.addBox(-4.0F, 0.0F, -2.0F, 1, 1, 6);
     this.Shape40.setRotationPoint(-1.0F, -10.0F, 4.0F);

     this.Shape40.mirror = true;
     setRotation(this.Shape40, 0.0F, 0.0F, 0.0F);
     this.Shape41 = new ModelRenderer(this, 40, 222);
     this.Shape41.addBox(-4.0F, 0.0F, -2.0F, 1, 1, 6);
     this.Shape41.setRotationPoint(-1.0F, -12.0F, 4.0F);

     this.Shape41.mirror = true;
     setRotation(this.Shape41, 0.0F, 0.0F, 0.0F);
     this.Shape42 = new ModelRenderer(this, 40, 209);
     this.Shape42.addBox(-4.0F, 0.0F, -2.0F, 1, 1, 6);
     this.Shape42.setRotationPoint(-1.0F, -14.0F, 4.0F);

     this.Shape42.mirror = true;
     setRotation(this.Shape42, 0.0F, 0.0F, 0.0F);
     this.Shape43 = new ModelRenderer(this, 40, 200);
     this.Shape43.addBox(-4.0F, 0.0F, -2.0F, 2, 1, 1);
     this.Shape43.setRotationPoint(1.0F, -4.0F, 8.0F);

     this.Shape43.mirror = true;
     setRotation(this.Shape43, 0.0F, 0.0F, 0.0F);
     this.Shape44 = new ModelRenderer(this, 40, 189);
     this.Shape44.addBox(-4.0F, 0.0F, -2.0F, 2, 1, 1);
     this.Shape44.setRotationPoint(0.0F, -6.0F, 6.0F);

     this.Shape44.mirror = true;
     setRotation(this.Shape44, 0.0F, 0.0F, 0.0F);
     this.Shape45 = new ModelRenderer(this, 40, 180);
     this.Shape45.addBox(-4.0F, 0.0F, -2.0F, 4, 1, 1);
     this.Shape45.setRotationPoint(-1.0F, -8.0F, 5.0F);

     this.Shape45.mirror = true;
     setRotation(this.Shape45, 0.0F, 0.0F, 0.0F);
     this.Shape46 = new ModelRenderer(this, 40, 170);
     this.Shape46.addBox(-4.0F, 0.0F, -2.0F, 4, 1, 1);
     this.Shape46.setRotationPoint(-1.0F, -10.0F, 4.0F);

     this.Shape46.mirror = true;
     setRotation(this.Shape46, 0.0F, 0.0F, 0.0F);
     this.Shape47 = new ModelRenderer(this, 40, 161);
     this.Shape47.addBox(-4.0F, 0.0F, -2.0F, 4, 1, 1);
     this.Shape47.setRotationPoint(-1.0F, -12.0F, 4.0F);

     this.Shape47.mirror = true;
     setRotation(this.Shape47, 0.0F, 0.0F, 0.0F);
     this.Shape48 = new ModelRenderer(this, 40, 151);
     this.Shape48.addBox(-4.0F, 0.0F, -2.0F, 4, 1, 1);
     this.Shape48.setRotationPoint(-1.0F, -14.0F, 4.0F);

     this.Shape48.mirror = true;
     setRotation(this.Shape48, 0.0F, 0.0F, 0.0F);
     this.Shape49 = new ModelRenderer(this, 40, 140);
     this.Shape49.addBox(0.0F, 0.0F, 0.0F, 3, 2, 3);
     this.Shape49.setRotationPoint(-7.5F, -15.5F, 3.0F);

     this.Shape49.mirror = true;
     setRotation(this.Shape49, 0.0F, 0.0F, 0.524F);
     this.rarm2 = new ModelRenderer(this, 40, 122);
     this.rarm2.addBox(-4.0F, 0.0F, -2.0F, 1, 12, 1);
     this.rarm2.setRotationPoint(-5.0F, -11.5F, 8.0F);

     this.rarm2.mirror = true;
     setRotation(this.rarm2, 0.0F, -0.5F, 0.524F);
     this.rarm3 = new ModelRenderer(this, 49, 122);
     this.rarm3.addBox(-4.0F, 0.0F, -2.0F, 1, 12, 1);
     this.rarm3.setRotationPoint(-4.0F, -11.5F, 6.0F);

     this.rarm3.mirror = true;
     setRotation(this.rarm3, 0.0F, -0.5F, 0.524F);
     this.relbow = new ModelRenderer(this, 40, 111);
     this.relbow.addBox(0.0F, 0.0F, 0.0F, 2, 2, 3);
     this.relbow.setRotationPoint(-11.0F, -3.5F, 3.0F);

     this.relbow.mirror = true;
     setRotation(this.relbow, 0.0F, -0.5F, 0.524F);
     this.rarm1 = new ModelRenderer(this, 40, 91);
     this.rarm1.addBox(-2.0F, -1.0F, -1.0F, 1, 11, 2);
     this.rarm1.setRotationPoint(-10.5F, -2.0F, 2.5F);

     this.rarm1.mirror = true;
     setRotation(this.rarm1, -0.76F, 0.0F, 0.3F);
     this.Shape54 = new ModelRenderer(this, 40, 78);
     this.Shape54.addBox(0.0F, 0.0F, 0.0F, 3, 2, 3);
     this.Shape54.setRotationPoint(5.0F, -14.0F, 3.0F);

     this.Shape54.mirror = true;
     setRotation(this.Shape54, 0.0F, 0.0F, -0.524F);
     this.larm3 = new ModelRenderer(this, 40, 58);
     this.larm3.addBox(-4.0F, 0.0F, -2.0F, 1, 12, 1);
     this.larm3.setRotationPoint(9.5F, -15.0F, 3.0F);

     this.larm3.mirror = true;
     setRotation(this.larm3, 0.0F, 0.5F, -0.524F);
     this.larm2 = new ModelRenderer(this, 40, 35);
     this.larm2.addBox(-4.0F, 0.0F, -2.0F, 1, 12, 1);
     this.larm2.setRotationPoint(10.5F, -15.0F, 5.0F);

     this.larm2.mirror = true;
     setRotation(this.larm2, 0.0F, 0.5F, -0.524F);
     this.lelbow = new ModelRenderer(this, 55, 38);
     this.lelbow.addBox(0.0F, 0.0F, 0.0F, 2, 2, 4);
     this.lelbow.setRotationPoint(10.0F, -3.0F, 3.0F);

     this.lelbow.mirror = true;
     setRotation(this.lelbow, 0.0F, 0.5F, -0.524F);
     this.larm1 = new ModelRenderer(this, 56, 53);
     this.larm1.addBox(0.0F, 0.0F, -1.0F, 1, 9, 2);
     this.larm1.setRotationPoint(12.0F, -3.0F, 2.5F);

     this.larm1.mirror = true;
     setRotation(this.larm1, 0.0F, -0.6F, -0.3F);
     this.scythe1 = new ModelRenderer(this, 57, 70);
     this.scythe1.addBox(0.0F, -39.0F, 1.0F, 1, 39, 1);
     this.scythe1.setRotationPoint(-17.0F, 6.0F, -2.0F);

     this.scythe1.mirror = true;
     setRotation(this.scythe1, 0.0F, 0.0F, 1.0F);
     this.scythe2 = new ModelRenderer(this, 58, 118);
     this.scythe2.addBox(0.0F, -39.0F, 1.0F, 16, 6, 0);
     this.scythe2.setRotationPoint(-17.0F, 6.0F, -2.0F);

     this.scythe2.mirror = true;
     setRotation(this.scythe2, 0.0F, 0.0F, 1.0F);
     this.scythe3 = new ModelRenderer(this, 61, 133);
     this.scythe3.addBox(9.0F, -34.0F, 1.0F, 7, 5, 0);
     this.scythe3.setRotationPoint(-17.0F, 6.0F, -2.0F);

     this.scythe3.mirror = true;
     setRotation(this.scythe3, 0.0F, 0.0F, 1.0F);
     this.head = new ModelRenderer(this, 58, 145);
     this.head.addBox(-3.0F, -6.0F, -3.0F, 6, 6, 5);
     this.head.setRotationPoint(0.0F, -16.0F, 4.0F);

     this.head.mirror = true;
     setRotation(this.head, 0.0F, 0.0F, 0.0F);
     this.lwing3 = new ModelRenderer(this, 71, 58);
     this.lwing3.addBox(-0.5F, 0.0F, 0.0F, 1, 19, 3);
     this.lwing3.setRotationPoint(4.0F, -11.7F, 8.5F);

     this.lwing3.mirror = true;
     setRotation(this.lwing3, 2.356F, 0.785F, 0.0F);
     this.lwing2 = new ModelRenderer(this, 58, 168);
     this.lwing2.addBox(-0.5F, 11.0F, -2.0F, 1, 19, 3);
     this.lwing2.setRotationPoint(4.0F, -23.9F, 8.5F);

     this.lwing2.mirror = true;
     setRotation(this.lwing2, 1.745F, 0.785F, 0.0F);
     this.rwing3 = new ModelRenderer(this, 71, 88);
     this.rwing3.addBox(-0.5F, 0.0F, 0.0F, 1, 19, 3);
     this.rwing3.setRotationPoint(-4.0F, -11.7F, 8.5F);

     this.rwing3.mirror = true;
     setRotation(this.rwing3, 2.356F, -0.785F, 0.0F);
     this.rwing2 = new ModelRenderer(this, 73, 168);
     this.rwing2.addBox(-0.5F, 12.0F, -2.0F, 1, 19, 3);
     this.rwing2.setRotationPoint(-4.0F, -23.9F, 8.5F);

     this.rwing2.mirror = true;
     setRotation(this.rwing2, 1.745F, -0.785F, 0.0F);
   }

   public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay,
        float red, float green, float blue, float alpha) {
        rwing1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        lwing1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Shape3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Shape4.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Shape5.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Shape6.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Shape7.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Shape8.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Shape9.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Shape10.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Shape11.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Shape12.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Shape13.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Shape14.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Shape15.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Shape16.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Shape17.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Shape18.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Shape19.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Shape20.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Shape21.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Shape22.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Shape23.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Shape24.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Shape25.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Shape26.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Shape27.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Shape28.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Shape29.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Shape30.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Shape31.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Shape32.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Shape33.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Shape34.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Shape35.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Shape36.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Shape37.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Shape38.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Shape39.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Shape40.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Shape41.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Shape42.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Shape43.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Shape44.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Shape45.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Shape46.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Shape47.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Shape48.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Shape49.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rarm2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rarm3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        relbow.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rarm1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Shape54.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        larm3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        larm2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        lelbow.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        larm1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        scythe1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        scythe2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        scythe3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        head.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        lwing3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        lwing2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rwing3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rwing2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }

   private void setRotation(ModelRenderer model, float x, float y, float z) {
     model.rotateAngleX = x;
     model.rotateAngleY = y;
     model.rotateAngleZ = z;
   }

   public void setRotationAngles(Entity entity, float limbSwing, float limbSwingAmount,
        float ageInTicks, float netHeadYaw, float headPitch) {
// Reset shared model state before applying this frame's legacy animation.
this.rwing1.setRotationPoint(-4.0F, -6.9F, 8.5F);
this.lwing1.setRotationPoint(4.0F, -6.9F, 8.5F);
this.Shape3.setRotationPoint(3.0F, -14.0F, 10.0F);
this.Shape4.setRotationPoint(3.0F, -2.0F, 10.0F);
this.Shape5.setRotationPoint(3.5F, 4.0F, 8.0F);
this.Shape6.setRotationPoint(3.0F, -12.0F, 7.5F);
this.Shape7.setRotationPoint(5.0F, -14.0F, 10.0F);
this.Shape8.setRotationPoint(-1.0F, -14.0F, 10.0F);
this.Shape9.setRotationPoint(-1.0F, -12.0F, 10.0F);
this.Shape10.setRotationPoint(-1.0F, -10.0F, 10.0F);
this.Shape11.setRotationPoint(-1.0F, -8.0F, 10.0F);
this.Shape12.setRotationPoint(1.0F, -6.0F, 10.0F);
this.Shape13.setRotationPoint(1.0F, -4.0F, 10.0F);
this.Shape14.setRotationPoint(3.5F, -14.0F, 11.0F);
this.Shape15.setRotationPoint(3.5F, -12.0F, 11.0F);
this.Shape16.setRotationPoint(3.5F, -10.0F, 11.0F);
this.Shape17.setRotationPoint(3.5F, -8.0F, 11.0F);
this.Shape18.setRotationPoint(3.5F, -6.0F, 11.0F);
this.Shape19.setRotationPoint(3.5F, -4.0F, 11.0F);
this.Shape20.setRotationPoint(5.0F, -12.0F, 10.0F);
this.Shape21.setRotationPoint(5.0F, -10.0F, 10.0F);
this.Shape22.setRotationPoint(5.0F, -8.0F, 10.0F);
this.Shape23.setRotationPoint(5.0F, -6.0F, 10.0F);
this.Shape24.setRotationPoint(5.0F, -4.0F, 10.0F);
this.Shape25.setRotationPoint(6.0F, -4.0F, 8.0F);
this.Shape26.setRotationPoint(5.0F, -4.0F, 8.0F);
this.Shape27.setRotationPoint(7.0F, -6.0F, 6.0F);
this.Shape28.setRotationPoint(8.0F, -8.0F, 5.0F);
this.Shape29.setRotationPoint(8.0F, -10.0F, 4.0F);
this.Shape30.setRotationPoint(8.0F, -12.0F, 4.0F);
this.Shape31.setRotationPoint(8.0F, -14.0F, 4.0F);
this.Shape32.setRotationPoint(6.0F, -6.0F, 6.0F);
this.Shape33.setRotationPoint(5.0F, -8.0F, 5.0F);
this.Shape34.setRotationPoint(5.0F, -10.0F, 4.0F);
this.Shape35.setRotationPoint(5.0F, -12.0F, 4.0F);
this.Shape36.setRotationPoint(5.0F, -14.0F, 4.0F);
this.Shape37.setRotationPoint(1.0F, -4.0F, 8.0F);
this.Shape38.setRotationPoint(0.0F, -6.0F, 6.0F);
this.Shape39.setRotationPoint(-1.0F, -8.0F, 5.0F);
this.Shape40.setRotationPoint(-1.0F, -10.0F, 4.0F);
this.Shape41.setRotationPoint(-1.0F, -12.0F, 4.0F);
this.Shape42.setRotationPoint(-1.0F, -14.0F, 4.0F);
this.Shape43.setRotationPoint(1.0F, -4.0F, 8.0F);
this.Shape44.setRotationPoint(0.0F, -6.0F, 6.0F);
this.Shape45.setRotationPoint(-1.0F, -8.0F, 5.0F);
this.Shape46.setRotationPoint(-1.0F, -10.0F, 4.0F);
this.Shape47.setRotationPoint(-1.0F, -12.0F, 4.0F);
this.Shape48.setRotationPoint(-1.0F, -14.0F, 4.0F);
this.Shape49.setRotationPoint(-7.5F, -15.5F, 3.0F);
this.rarm2.setRotationPoint(-5.0F, -11.5F, 8.0F);
this.rarm3.setRotationPoint(-4.0F, -11.5F, 6.0F);
this.relbow.setRotationPoint(-11.0F, -3.5F, 3.0F);
this.rarm1.setRotationPoint(-10.5F, -2.0F, 2.5F);
this.Shape54.setRotationPoint(5.0F, -14.0F, 3.0F);
this.larm3.setRotationPoint(9.5F, -15.0F, 3.0F);
this.larm2.setRotationPoint(10.5F, -15.0F, 5.0F);
this.lelbow.setRotationPoint(10.0F, -3.0F, 3.0F);
this.larm1.setRotationPoint(12.0F, -3.0F, 2.5F);
this.scythe1.setRotationPoint(-17.0F, 6.0F, -2.0F);
this.scythe2.setRotationPoint(-17.0F, 6.0F, -2.0F);
this.scythe3.setRotationPoint(-17.0F, 6.0F, -2.0F);
this.head.setRotationPoint(0.0F, -16.0F, 4.0F);
this.lwing3.setRotationPoint(4.0F, -11.7F, 8.5F);
this.lwing2.setRotationPoint(4.0F, -23.9F, 8.5F);
this.rwing3.setRotationPoint(-4.0F, -11.7F, 8.5F);
this.rwing2.setRotationPoint(-4.0F, -23.9F, 8.5F);
setRotation(this.rwing1, 1.745F, -0.785F, 0.0F);
setRotation(this.lwing1, 1.745F, 0.785F, 0.0F);
setRotation(this.Shape3, 0.0F, 0.0F, 0.0F);
setRotation(this.Shape4, -0.247F, 0.0F, 0.0F);
setRotation(this.Shape5, -0.768F, 0.0F, 0.0F);
setRotation(this.Shape6, -2.356F, 0.0F, 0.0F);
setRotation(this.Shape7, 0.0F, 0.0F, 0.0F);
setRotation(this.Shape8, 0.0F, 0.0F, 0.0F);
setRotation(this.Shape9, 0.0F, 0.0F, 0.0F);
setRotation(this.Shape10, 0.0F, 0.0F, 0.0F);
setRotation(this.Shape11, 0.0F, 0.0F, 0.0F);
setRotation(this.Shape12, 0.0F, 0.0F, 0.0F);
setRotation(this.Shape13, 0.0F, 0.0F, 0.0F);
setRotation(this.Shape14, 0.0F, 0.0F, 0.0F);
setRotation(this.Shape15, 0.0F, 0.0F, 0.0F);
setRotation(this.Shape16, 0.0F, 0.0F, 0.0F);
setRotation(this.Shape17, 0.0F, 0.0F, 0.0F);
setRotation(this.Shape18, 0.0F, 0.0F, 0.0F);
setRotation(this.Shape19, 0.0F, 0.0F, 0.0F);
setRotation(this.Shape20, 0.0F, 0.0F, 0.0F);
setRotation(this.Shape21, 0.0F, 0.0F, 0.0F);
setRotation(this.Shape22, 0.0F, 0.0F, 0.0F);
setRotation(this.Shape23, 0.0F, 0.0F, 0.0F);
setRotation(this.Shape24, 0.0F, 0.0F, 0.0F);
setRotation(this.Shape25, 0.0F, 0.0F, 0.0F);
setRotation(this.Shape26, 0.0F, 0.0F, 0.0F);
setRotation(this.Shape27, 0.0F, 0.0F, 0.0F);
setRotation(this.Shape28, 0.0F, 0.0F, 0.0F);
setRotation(this.Shape29, 0.0F, 0.0F, 0.0F);
setRotation(this.Shape30, 0.0F, 0.0F, 0.0F);
setRotation(this.Shape31, 0.0F, 0.0F, 0.0F);
setRotation(this.Shape32, 0.0F, 0.0F, 0.0F);
setRotation(this.Shape33, 0.0F, 0.0F, 0.0F);
setRotation(this.Shape34, 0.0F, 0.0F, 0.0F);
setRotation(this.Shape35, 0.0F, 0.0F, 0.0F);
setRotation(this.Shape36, 0.0F, 0.0F, 0.0F);
setRotation(this.Shape37, 0.0F, 0.0F, 0.0F);
setRotation(this.Shape38, 0.0F, 0.0F, 0.0F);
setRotation(this.Shape39, 0.0F, 0.0F, 0.0F);
setRotation(this.Shape40, 0.0F, 0.0F, 0.0F);
setRotation(this.Shape41, 0.0F, 0.0F, 0.0F);
setRotation(this.Shape42, 0.0F, 0.0F, 0.0F);
setRotation(this.Shape43, 0.0F, 0.0F, 0.0F);
setRotation(this.Shape44, 0.0F, 0.0F, 0.0F);
setRotation(this.Shape45, 0.0F, 0.0F, 0.0F);
setRotation(this.Shape46, 0.0F, 0.0F, 0.0F);
setRotation(this.Shape47, 0.0F, 0.0F, 0.0F);
setRotation(this.Shape48, 0.0F, 0.0F, 0.0F);
setRotation(this.Shape49, 0.0F, 0.0F, 0.524F);
setRotation(this.rarm2, 0.0F, -0.5F, 0.524F);
setRotation(this.rarm3, 0.0F, -0.5F, 0.524F);
setRotation(this.relbow, 0.0F, -0.5F, 0.524F);
setRotation(this.rarm1, -0.76F, 0.0F, 0.3F);
setRotation(this.Shape54, 0.0F, 0.0F, -0.524F);
setRotation(this.larm3, 0.0F, 0.5F, -0.524F);
setRotation(this.larm2, 0.0F, 0.5F, -0.524F);
setRotation(this.lelbow, 0.0F, 0.5F, -0.524F);
setRotation(this.larm1, 0.0F, -0.6F, -0.3F);
setRotation(this.scythe1, 0.0F, 0.0F, 1.0F);
setRotation(this.scythe2, 0.0F, 0.0F, 1.0F);
setRotation(this.scythe3, 0.0F, 0.0F, 1.0F);
setRotation(this.head, 0.0F, 0.0F, 0.0F);
setRotation(this.lwing3, 2.356F, 0.785F, 0.0F);
setRotation(this.lwing2, 1.745F, 0.785F, 0.0F);
setRotation(this.rwing3, 2.356F, -0.785F, 0.0F);
setRotation(this.rwing2, 1.745F, -0.785F, 0.0F);

     EnderReaperEntity.CustomEntity e = (EnderReaperEntity.CustomEntity) entity;

     float newangle = 0.0F;
     if (limbSwingAmount > 0.1D) {
       newangle = MathHelper.cos(ageInTicks * 1.3F * this.wingspeed) * 3.1415927F * 0.25F * limbSwingAmount;
     } else {
       newangle = 0.0F;
     } 
     this.scythe1.rotateAngleZ = 1.0F - Math.abs(newangle);
 
     
     if (e.isScreaming()) {
       newangle = MathHelper.cos(ageInTicks * 1.9F * this.wingspeed) * 3.1415927F * 0.25F;
       this.scythe1.rotateAngleZ = 1.0F + newangle;
       this.larm1.rotateAngleX = -0.436F;
       this.larm1.rotateAngleY = -0.488F;
       newangle = MathHelper.cos(ageInTicks * 2.7F * this.wingspeed) * 3.1415927F * 0.3F;
     } else {
       this.larm1.rotateAngleX = -2.436F;
       this.larm1.rotateAngleY = 1.0F;
       newangle = MathHelper.cos(ageInTicks * 0.7F * this.wingspeed) * 3.1415927F * 0.06F;
     } 
     this.lwing3.rotateAngleY = 0.785F + newangle;
     this.rwing3.rotateAngleY = -0.785F - newangle;
     
     this.head.rotateAngleY = (float)Math.toRadians(netHeadYaw) * 0.45F;
     if (this.head.rotateAngleY > 0.45F) this.head.rotateAngleY = 0.45F; 
     if (this.head.rotateAngleY < -0.45F) this.head.rotateAngleY = -0.45F;
     this.scythe2.setRotationPoint(this.scythe1.rotationPointX, this.scythe1.rotationPointY, this.scythe1.rotationPointZ);
     this.scythe3.setRotationPoint(this.scythe1.rotationPointX, this.scythe1.rotationPointY, this.scythe1.rotationPointZ);
     this.scythe2.rotateAngleX = this.scythe3.rotateAngleX = this.scythe1.rotateAngleX;
     this.scythe2.rotateAngleY = this.scythe3.rotateAngleY = this.scythe1.rotateAngleY;
     this.scythe2.rotateAngleZ = this.scythe3.rotateAngleZ = this.scythe1.rotateAngleZ;
    }
 }
}
