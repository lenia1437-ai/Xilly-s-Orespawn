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
import net.mcreator.xillysorespawn.entity.NastysaurusEntity;
import net.mcreator.xillysorespawn.entity.OreSpawnLogic;

@OnlyIn(Dist.CLIENT)
public class NastysaurusRenderer {
    private static final String TEXTURE = "xillys_orespawn:textures/entities/nastysaurustexture.png";
    public static class ModelRegisterHandler {
        @SubscribeEvent @OnlyIn(Dist.CLIENT)
        public void registerModels(ModelRegistryEvent event) {
            RenderingRegistry.registerEntityRenderingHandler(NastysaurusEntity.entity, manager ->
                new MobRenderer(manager, new ModelNastysaurus(0.65F), 1.5F) {
                    @Override public ResourceLocation getEntityTexture(Entity entity) { return new ResourceLocation(TEXTURE); }
                    @Override protected void preRenderCallback(LivingEntity entity, MatrixStack stack, float partialTick) {
                        float modelScale = 1.5F;
                        stack.scale(modelScale, modelScale, modelScale);
                    }
                });
        }
    }

public static class ModelNastysaurus extends EntityModel<Entity>
 {
   private float wingspeed = 1.0F;

   private final ModelRenderer lclaw1;

   private final ModelRenderer body;

   private final ModelRenderer leftleg1;
   private final ModelRenderer tail1;
   private final ModelRenderer leftleg2;
   private final ModelRenderer body2;
   private final ModelRenderer leftleg3;
   private final ModelRenderer tail2;
   private final ModelRenderer tail3;
   private final ModelRenderer lclaw2;
   private final ModelRenderer lclaw3;
   private final ModelRenderer lclaw4;
   private final ModelRenderer lclaw5;
   private final ModelRenderer lclaw6;
   private final ModelRenderer lclaw7;
   private final ModelRenderer neck3;
   private final ModelRenderer head3;
   private final ModelRenderer jaw1;
   private final ModelRenderer tooth1;
   private final ModelRenderer tooth2;
   private final ModelRenderer tooth3;
   private final ModelRenderer tooth4;
   private final ModelRenderer tooth5;
   private final ModelRenderer jaw5;
   private final ModelRenderer head7;
   private final ModelRenderer tooth6;
   private final ModelRenderer tooth7;
   private final ModelRenderer tooth8;
   private final ModelRenderer tooth9;
   private final ModelRenderer tooth10;
   private final ModelRenderer tooth11;
   private final ModelRenderer tooth12;
   private final ModelRenderer tooth13;
   private final ModelRenderer rightleg1;
   private final ModelRenderer rightleg2;
   private final ModelRenderer tooth14;
   private final ModelRenderer tooth15;
   private final ModelRenderer tooth16;
   private final ModelRenderer tooth17;
   private final ModelRenderer tooth18;
   private final ModelRenderer tooth19;
   private final ModelRenderer tooth20;
   private final ModelRenderer tooth21;
   private final ModelRenderer tooth22;
   private final ModelRenderer tooth23;
   private final ModelRenderer rightleg3;
   private final ModelRenderer rclaw2;
   private final ModelRenderer rclaw4;
   private final ModelRenderer rclaw1;
   private final ModelRenderer rclaw5;
   private final ModelRenderer rclaw7;
   private final ModelRenderer rclaw3;
   private final ModelRenderer rclaw6;
   private final ModelRenderer neck1;
   private final ModelRenderer neck2;
   private final ModelRenderer tail4;
   private final ModelRenderer Spike1;
   private final ModelRenderer Spike2;
   private final ModelRenderer Spike3;

   public ModelNastysaurus(float f1) {
     this.wingspeed = f1;

     this.textureWidth = 512;
     this.textureHeight = 256;

     this.lclaw1 = new ModelRenderer(this, 300, 111);
     this.lclaw1.addBox(-3.0F, 0.0F, -3.0F, 2, 3, 6);
     this.lclaw1.setRotationPoint(7.0F, 21.0F, 11.0F);

     this.lclaw1.mirror = true;
     setRotation(this.lclaw1, 0.0F, 0.6632251F, 0.0F);
     this.body = new ModelRenderer(this, 407, 3);
     this.body.addBox(-6.0F, -12.0F, -9.0F, 12, 17, 9);
     this.body.setRotationPoint(0.0F, -2.0F, 9.0F);

     this.body.mirror = true;
     setRotation(this.body, -0.3141593F, 0.0F, 0.0F);
     this.leftleg1 = new ModelRenderer(this, 300, 0);
     this.leftleg1.addBox(-3.0F, -4.0F, -21.0F, 6, 11, 11);
     this.leftleg1.setRotationPoint(9.0F, 2.0F, 26.0F);

     this.leftleg1.mirror = true;
     setRotation(this.leftleg1, -0.5759587F, 0.0F, 0.0F);
     this.tail1 = new ModelRenderer(this, 400, 75);
     this.tail1.addBox(-6.0F, -6.0F, 0.0F, 10, 12, 14);
     this.tail1.setRotationPoint(1.0F, -5.0F, 22.0F);

     this.tail1.mirror = true;
     setRotation(this.tail1, -0.1745329F, 0.0F, 0.0F);
     this.leftleg2 = new ModelRenderer(this, 300, 23);
     this.leftleg2.addBox(-3.0F, -10.0F, -5.0F, 5, 13, 7);
     this.leftleg2.setRotationPoint(9.0F, 2.0F, 26.0F);

     this.leftleg2.mirror = true;
     setRotation(this.leftleg2, 0.9773844F, 0.0F, 0.0F);
     this.body2 = new ModelRenderer(this, 400, 39);
     this.body2.addBox(0.0F, -3.0F, -3.0F, 12, 18, 16);
     this.body2.setRotationPoint(-6.0F, -11.0F, 10.0F);

     this.body2.mirror = true;
     setRotation(this.body2, -0.1047198F, 0.0F, 0.0F);
     this.leftleg3 = new ModelRenderer(this, 300, 51);
     this.leftleg3.addBox(-1.0F, -19.0F, 0.0F, 4, 18, 6);
     this.leftleg3.setRotationPoint(7.0F, 21.0F, 11.0F);

     this.leftleg3.mirror = true;
     setRotation(this.leftleg3, -0.5235988F, 0.0F, 0.0F);
     this.tail2 = new ModelRenderer(this, 400, 103);
     this.tail2.addBox(-4.0F, -4.0F, 0.0F, 8, 10, 12);
     this.tail2.setRotationPoint(0.0F, -4.0F, 35.0F);

     this.tail2.mirror = true;
     setRotation(this.tail2, -0.1396263F, 0.0F, 0.0F);
     this.tail3 = new ModelRenderer(this, 400, 127);
     this.tail3.addBox(-3.0F, -3.0F, 0.0F, 6, 8, 12);
     this.tail3.setRotationPoint(0.0F, -3.0F, 46.0F);

     this.tail3.mirror = true;
     setRotation(this.tail3, -0.1396263F, 0.0F, 0.0F);
     this.lclaw2 = new ModelRenderer(this, 300, 76);
     this.lclaw2.addBox(-1.0F, -1.0F, -6.0F, 4, 4, 13);
     this.lclaw2.setRotationPoint(7.0F, 21.0F, 11.0F);

     this.lclaw2.mirror = true;
     setRotation(this.lclaw2, 0.0F, 0.0F, 0.0F);
     this.lclaw3 = new ModelRenderer(this, 300, 95);
     this.lclaw3.addBox(2.0F, 0.0F, -6.0F, 2, 3, 10);
     this.lclaw3.setRotationPoint(7.0F, 21.0F, 11.0F);

     this.lclaw3.mirror = true;
     setRotation(this.lclaw3, 0.0F, -0.6632251F, 0.0F);
     this.lclaw4 = new ModelRenderer(this, 308, 123);
     this.lclaw4.addBox(0.0F, 0.0F, -10.0F, 2, 3, 4);
     this.lclaw4.setRotationPoint(7.0F, 21.0F, 11.0F);

     this.lclaw4.mirror = true;
     setRotation(this.lclaw4, 0.0F, 0.0F, 0.0F);
     this.lclaw5 = new ModelRenderer(this, 300, 123);
     this.lclaw5.addBox(-2.5F, 1.0F, -5.0F, 1, 2, 2);
     this.lclaw5.setRotationPoint(7.0F, 21.0F, 11.0F);

     this.lclaw5.mirror = true;
     setRotation(this.lclaw5, 0.0F, 0.6632251F, 0.0F);
     this.lclaw6 = new ModelRenderer(this, 322, 123);
     this.lclaw6.addBox(2.5F, 1.0F, -9.0F, 1, 2, 3);
     this.lclaw6.setRotationPoint(7.0F, 21.0F, 11.0F);

     this.lclaw6.mirror = true;
     setRotation(this.lclaw6, 0.0F, -0.6632251F, 0.0F);
     this.lclaw7 = new ModelRenderer(this, 333, 123);
     this.lclaw7.addBox(0.0F, 1.0F, 7.0F, 1, 2, 3);
     this.lclaw7.setRotationPoint(7.0F, 21.0F, 11.0F);

     this.lclaw7.mirror = true;
     setRotation(this.lclaw7, 0.0F, 0.0F, 0.0F);
     this.neck3 = new ModelRenderer(this, 375, 23);
     this.neck3.addBox(-3.0F, -3.0F, -6.0F, 6, 6, 8);
     this.neck3.setRotationPoint(0.0F, -24.0F, -9.0F);

     this.neck3.mirror = true;
     setRotation(this.neck3, -0.2443461F, 0.0F, 0.0F);
     this.head3 = new ModelRenderer(this, 130, 32);
     this.head3.addBox(-3.0F, -6.0F, -15.0F, 6, 6, 17);
     this.head3.setRotationPoint(0.0F, -26.0F, -14.0F);

     this.head3.mirror = true;
     setRotation(this.head3, -0.2443461F, 0.0F, 0.0F);
     this.jaw1 = new ModelRenderer(this, 143, 114);
     this.jaw1.addBox(-3.0F, 1.0F, -14.0F, 6, 3, 15);
     this.jaw1.setRotationPoint(0.0F, -26.0F, -14.0F);

     this.jaw1.mirror = true;
     setRotation(this.jaw1, 0.1919862F, 0.0F, 0.0F);
     this.tooth1 = new ModelRenderer(this, 0, 0);
     this.tooth1.addBox(-3.0F, 0.0F, -14.0F, 1, 3, 1);
     this.tooth1.setRotationPoint(0.0F, -26.0F, -14.0F);

     this.tooth1.mirror = true;
     setRotation(this.tooth1, -0.2443461F, 0.0F, 0.0F);
     this.tooth2 = new ModelRenderer(this, 0, 0);
     this.tooth2.addBox(-0.5F, 0.0F, -14.0F, 1, 2, 1);
     this.tooth2.setRotationPoint(0.0F, -26.0F, -14.0F);

     this.tooth2.mirror = true;
     setRotation(this.tooth2, -0.2443461F, 0.0F, 0.0F);
     this.tooth3 = new ModelRenderer(this, 0, 0);
     this.tooth3.addBox(2.0F, 0.0F, -14.0F, 1, 3, 1);
     this.tooth3.setRotationPoint(0.0F, -26.0F, -14.0F);

     this.tooth3.mirror = true;
     setRotation(this.tooth3, -0.2443461F, 0.0F, 0.0F);
     this.tooth4 = new ModelRenderer(this, 0, 0);
     this.tooth4.addBox(-2.0F, 0.0F, -12.0F, 1, 3, 1);
     this.tooth4.setRotationPoint(0.0F, -26.0F, -14.0F);

     this.tooth4.mirror = true;
     setRotation(this.tooth4, -0.2443461F, 0.0F, 0.0F);
     this.tooth5 = new ModelRenderer(this, 0, 0);
     this.tooth5.addBox(1.0F, 0.0F, -12.0F, 1, 3, 1);
     this.tooth5.setRotationPoint(0.0F, -26.0F, -14.0F);

     this.tooth5.mirror = true;
     setRotation(this.tooth5, -0.2443461F, 0.0F, 0.0F);
     this.jaw5 = new ModelRenderer(this, 151, 135);
     this.jaw5.addBox(-4.0F, 1.0F, -4.0F, 8, 4, 7);
     this.jaw5.setRotationPoint(0.0F, -26.0F, -14.0F);

     this.jaw5.mirror = true;
     setRotation(this.jaw5, 0.1919862F, 0.0F, 0.0F);
     this.head7 = new ModelRenderer(this, 185, 34);
     this.head7.addBox(-4.0F, -7.0F, -3.0F, 8, 7, 10);
     this.head7.setRotationPoint(0.0F, -26.0F, -14.0F);

     this.head7.mirror = true;
     setRotation(this.head7, -0.2443461F, 0.0F, 0.0F);
     this.tooth6 = new ModelRenderer(this, 0, 0);
     this.tooth6.addBox(-3.0F, 0.0F, -10.0F, 1, 2, 1);
     this.tooth6.setRotationPoint(0.0F, -26.0F, -14.0F);

     this.tooth6.mirror = true;
     setRotation(this.tooth6, -0.2443461F, 0.0F, 0.0F);
     this.tooth7 = new ModelRenderer(this, 0, 0);
     this.tooth7.addBox(2.0F, 0.0F, -10.0F, 1, 2, 1);
     this.tooth7.setRotationPoint(0.0F, -26.0F, -14.0F);

     this.tooth7.mirror = true;
     setRotation(this.tooth7, -0.2443461F, 0.0F, 0.0F);
     this.tooth8 = new ModelRenderer(this, 0, 0);
     this.tooth8.addBox(-2.0F, 0.0F, -8.0F, 1, 2, 1);
     this.tooth8.setRotationPoint(0.0F, -26.0F, -14.0F);

     this.tooth8.mirror = true;
     setRotation(this.tooth8, -0.2443461F, 0.0F, 0.0F);
     this.tooth9 = new ModelRenderer(this, 0, 0);
     this.tooth9.addBox(1.0F, 0.0F, -8.0F, 1, 2, 1);
     this.tooth9.setRotationPoint(0.0F, -26.0F, -14.0F);

     this.tooth9.mirror = true;
     setRotation(this.tooth9, -0.2443461F, 0.0F, 0.0F);
     this.tooth10 = new ModelRenderer(this, 0, 0);
     this.tooth10.addBox(-3.0F, 0.0F, -6.0F, 1, 2, 1);
     this.tooth10.setRotationPoint(0.0F, -26.0F, -14.0F);

     this.tooth10.mirror = true;
     setRotation(this.tooth10, -0.2443461F, 0.0F, 0.0F);
     this.tooth11 = new ModelRenderer(this, 0, 0);
     this.tooth11.addBox(2.0F, 0.0F, -6.0F, 1, 2, 1);
     this.tooth11.setRotationPoint(0.0F, -26.0F, -14.0F);

     this.tooth11.mirror = true;
     setRotation(this.tooth11, -0.2443461F, 0.0F, 0.0F);
     this.tooth12 = new ModelRenderer(this, 0, 0);
     this.tooth12.addBox(-2.0F, 0.0F, -4.0F, 1, 1, 1);
     this.tooth12.setRotationPoint(0.0F, -26.0F, -14.0F);

     this.tooth12.mirror = true;
     setRotation(this.tooth12, -0.2443461F, 0.0F, 0.0F);
     this.tooth13 = new ModelRenderer(this, -1, 0);
     this.tooth13.addBox(1.0F, 0.0F, -4.0F, 1, 1, 1);
     this.tooth13.setRotationPoint(0.0F, -26.0F, -14.0F);

     this.tooth13.mirror = true;
     setRotation(this.tooth13, -0.2443461F, 0.0F, 0.0F);
     this.rightleg1 = new ModelRenderer(this, 246, 0);
     this.rightleg1.addBox(-2.0F, -4.0F, -21.0F, 6, 11, 11);
     this.rightleg1.setRotationPoint(-10.0F, 2.0F, 26.0F);

     this.rightleg1.mirror = true;
     setRotation(this.rightleg1, -0.5934119F, 0.0F, 0.0F);
     this.rightleg2 = new ModelRenderer(this, 250, 24);
     this.rightleg2.addBox(-1.0F, -10.0F, -5.0F, 5, 13, 7);
     this.rightleg2.setRotationPoint(-10.0F, 2.0F, 26.0F);

     this.rightleg2.mirror = true;
     setRotation(this.rightleg2, 0.9773844F, 0.0F, 0.0F);
     this.tooth14 = new ModelRenderer(this, 0, 0);
     this.tooth14.addBox(0.5F, -2.0F, -14.0F, 1, 3, 1);
     this.tooth14.setRotationPoint(0.0F, -26.0F, -14.0F);

     this.tooth14.mirror = true;
     setRotation(this.tooth14, 0.1919862F, 0.0F, 0.0F);
     this.tooth15 = new ModelRenderer(this, 0, 0);
     this.tooth15.addBox(-1.5F, -2.0F, -14.0F, 1, 3, 1);
     this.tooth15.setRotationPoint(0.0F, -26.0F, -14.0F);

     this.tooth15.mirror = true;
     setRotation(this.tooth15, 0.1919862F, 0.0F, 0.0F);
     this.tooth16 = new ModelRenderer(this, 0, 0);
     this.tooth16.addBox(2.0F, -1.0F, -12.0F, 1, 2, 1);
     this.tooth16.setRotationPoint(0.0F, -26.0F, -14.0F);

     this.tooth16.mirror = true;
     setRotation(this.tooth16, 0.1919862F, 0.0F, 0.0F);
     this.tooth17 = new ModelRenderer(this, 0, 0);
     this.tooth17.addBox(-3.0F, -1.0F, -12.0F, 1, 2, 1);
     this.tooth17.setRotationPoint(0.0F, -26.0F, -14.0F);

     this.tooth17.mirror = true;
     setRotation(this.tooth17, 0.1919862F, 0.0F, 0.0F);
     this.tooth18 = new ModelRenderer(this, 0, 0);
     this.tooth18.addBox(1.0F, -1.0F, -10.0F, 1, 2, 1);
     this.tooth18.setRotationPoint(0.0F, -26.0F, -14.0F);

     this.tooth18.mirror = true;
     setRotation(this.tooth18, 0.1919862F, 0.0F, 0.0F);
     this.tooth19 = new ModelRenderer(this, 0, 0);
     this.tooth19.addBox(-2.0F, -1.0F, -10.0F, 1, 2, 1);
     this.tooth19.setRotationPoint(0.0F, -26.0F, -14.0F);

     this.tooth19.mirror = true;
     setRotation(this.tooth19, 0.1919862F, 0.0F, 0.0F);
     this.tooth20 = new ModelRenderer(this, 0, 0);
     this.tooth20.addBox(-3.0F, -1.0F, -8.0F, 1, 2, 1);
     this.tooth20.setRotationPoint(0.0F, -26.0F, -14.0F);

     this.tooth20.mirror = true;
     setRotation(this.tooth20, 0.1919862F, 0.0F, 0.0F);
     this.tooth21 = new ModelRenderer(this, 0, 0);
     this.tooth21.addBox(2.0F, -1.0F, -8.0F, 1, 2, 1);
     this.tooth21.setRotationPoint(0.0F, -26.0F, -14.0F);

     this.tooth21.mirror = true;
     setRotation(this.tooth21, 0.1919862F, 0.0F, 0.0F);
     this.tooth22 = new ModelRenderer(this, 0, 0);
     this.tooth22.addBox(1.0F, 0.0F, -6.0F, 1, 1, 1);
     this.tooth22.setRotationPoint(0.0F, -26.0F, -14.0F);

     this.tooth22.mirror = true;
     setRotation(this.tooth22, 0.1919862F, 0.0F, 0.0F);
     this.tooth23 = new ModelRenderer(this, 0, 0);
     this.tooth23.addBox(-2.0F, 0.0F, -6.0F, 1, 1, 1);
     this.tooth23.setRotationPoint(0.0F, -26.0F, -14.0F);

     this.tooth23.mirror = true;
     setRotation(this.tooth23, 0.1919862F, 0.0F, 0.0F);
     this.rightleg3 = new ModelRenderer(this, 250, 47);
     this.rightleg3.addBox(-2.0F, -19.0F, 0.0F, 4, 18, 6);
     this.rightleg3.setRotationPoint(-8.0F, 21.0F, 11.0F);

     this.rightleg3.mirror = true;
     setRotation(this.rightleg3, -0.5235988F, 0.0F, 0.0F);
     this.rclaw2 = new ModelRenderer(this, 250, 76);
     this.rclaw2.addBox(-2.0F, -1.0F, -6.0F, 4, 4, 13);
     this.rclaw2.setRotationPoint(-8.0F, 21.0F, 11.0F);

     this.rclaw2.mirror = true;
     setRotation(this.rclaw2, 0.0F, 0.0F, 0.0F);
     this.rclaw4 = new ModelRenderer(this, 247, 123);
     this.rclaw4.addBox(-1.0F, 0.0F, -10.0F, 2, 3, 4);
     this.rclaw4.setRotationPoint(-8.0F, 21.0F, 11.0F);

     this.rclaw4.mirror = true;
     setRotation(this.rclaw4, 0.0F, 0.0F, 0.0F);
     this.rclaw1 = new ModelRenderer(this, 250, 111);
     this.rclaw1.addBox(2.0F, 0.0F, -3.0F, 2, 3, 6);
     this.rclaw1.setRotationPoint(-8.0F, 21.0F, 11.0F);

     this.rclaw1.mirror = true;
     setRotation(this.rclaw1, 0.0F, -0.6632251F, 0.0F);
     this.rclaw5 = new ModelRenderer(this, 261, 123);
     this.rclaw5.addBox(2.5F, 1.0F, -5.0F, 1, 2, 2);
     this.rclaw5.setRotationPoint(-8.0F, 21.0F, 11.0F);

     this.rclaw5.mirror = true;
     setRotation(this.rclaw5, 0.0F, -0.6632251F, 0.0F);
     this.rclaw7 = new ModelRenderer(this, 283, 123);
     this.rclaw7.addBox(0.0F, 1.0F, 7.0F, 1, 2, 3);
     this.rclaw7.setRotationPoint(-8.0F, 21.0F, 11.0F);

     this.rclaw7.mirror = true;
     setRotation(this.rclaw7, 0.0F, 0.0F, 0.0F);
     this.rclaw3 = new ModelRenderer(this, 250, 95);
     this.rclaw3.addBox(-3.0F, 0.0F, -6.0F, 2, 3, 10);
     this.rclaw3.setRotationPoint(-8.0F, 21.0F, 11.0F);

     this.rclaw3.mirror = true;
     setRotation(this.rclaw3, 0.0F, 0.6632251F, 0.0F);
     this.rclaw6 = new ModelRenderer(this, 270, 123);
     this.rclaw6.addBox(-2.5F, 1.0F, -9.0F, 1, 2, 3);
     this.rclaw6.setRotationPoint(-8.0F, 21.0F, 11.0F);

     this.rclaw6.mirror = true;
     setRotation(this.rclaw6, 0.0F, 0.6632251F, 0.0F);
     this.neck1 = new ModelRenderer(this, 45, 0);
     this.neck1.addBox(-5.0F, -6.0F, -14.0F, 10, 12, 15);
     this.neck1.setRotationPoint(0.0F, -9.0F, 5.0F);

     this.neck1.mirror = true;
     setRotation(this.neck1, -0.837758F, 0.0F, 0.0F);
     this.neck2 = new ModelRenderer(this, 48, 29);
     this.neck2.addBox(-4.5F, -4.0F, -10.0F, 9, 9, 10);
     this.neck2.setRotationPoint(0.0F, -19.0F, -2.0F);

     this.neck2.mirror = true;
     setRotation(this.neck2, -0.7853982F, 0.0F, 0.0F);
     this.tail4 = new ModelRenderer(this, 400, 150);
     this.tail4.addBox(-2.0F, -3.0F, 0.0F, 4, 6, 16);
     this.tail4.setRotationPoint(0.0F, -1.0F, 56.0F);

     this.tail4.mirror = true;
     setRotation(this.tail4, -0.1396263F, 0.0F, 0.0F);
     this.Spike1 = new ModelRenderer(this, 0, 100);
     this.Spike1.addBox(-2.0F, -16.0F, -1.0F, 4, 16, 18);
     this.Spike1.setRotationPoint(0.0F, -4.0F, 7.0F);

     this.Spike1.mirror = true;
     setRotation(this.Spike1, 0.5061455F, 0.0F, 0.0F);
     this.Spike2 = new ModelRenderer(this, 0, 72);
     this.Spike2.addBox(-1.5F, -12.0F, 0.0F, 3, 12, 10);
     this.Spike2.setRotationPoint(0.0F, 0.0F, 29.0F);

     this.Spike2.mirror = true;
     setRotation(this.Spike2, 0.4886922F, 0.0F, 0.0F);
     this.Spike3 = new ModelRenderer(this, 0, 44);
     this.Spike3.addBox(-1.0F, -7.0F, 0.0F, 2, 8, 7);
     this.Spike3.setRotationPoint(0.0F, -2.0F, 41.0F);

     this.Spike3.mirror = true;
     setRotation(this.Spike3, 0.5934119F, 0.0F, 0.0F);
   }

   public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay,
        float red, float green, float blue, float alpha) {
        lclaw1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        body.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        leftleg1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        tail1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        leftleg2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        body2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        leftleg3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        tail2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        tail3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        lclaw2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        lclaw3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        lclaw4.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        lclaw5.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        lclaw6.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        lclaw7.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        neck3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        head3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        jaw1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        tooth1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        tooth2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        tooth3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        tooth4.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        tooth5.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        jaw5.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        head7.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        tooth6.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        tooth7.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        tooth8.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        tooth9.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        tooth10.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        tooth11.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        tooth12.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        tooth13.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rightleg1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rightleg2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        tooth14.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        tooth15.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        tooth16.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        tooth17.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        tooth18.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        tooth19.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        tooth20.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        tooth21.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        tooth22.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        tooth23.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rightleg3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rclaw2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rclaw4.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rclaw1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rclaw5.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rclaw7.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rclaw3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rclaw6.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        neck1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        neck2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        tail4.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Spike1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Spike2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Spike3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }

   private void setRotation(ModelRenderer model, float x, float y, float z) {
     model.rotateAngleX = x;
     model.rotateAngleY = y;
     model.rotateAngleZ = z;
   }

   public void setRotationAngles(Entity entity, float limbSwing, float limbSwingAmount,
        float ageInTicks, float netHeadYaw, float headPitch) {
// Reset shared model state before applying this frame's legacy animation.
this.lclaw1.setRotationPoint(7.0F, 21.0F, 11.0F);
this.body.setRotationPoint(0.0F, -2.0F, 9.0F);
this.leftleg1.setRotationPoint(9.0F, 2.0F, 26.0F);
this.tail1.setRotationPoint(1.0F, -5.0F, 22.0F);
this.leftleg2.setRotationPoint(9.0F, 2.0F, 26.0F);
this.body2.setRotationPoint(-6.0F, -11.0F, 10.0F);
this.leftleg3.setRotationPoint(7.0F, 21.0F, 11.0F);
this.tail2.setRotationPoint(0.0F, -4.0F, 35.0F);
this.tail3.setRotationPoint(0.0F, -3.0F, 46.0F);
this.lclaw2.setRotationPoint(7.0F, 21.0F, 11.0F);
this.lclaw3.setRotationPoint(7.0F, 21.0F, 11.0F);
this.lclaw4.setRotationPoint(7.0F, 21.0F, 11.0F);
this.lclaw5.setRotationPoint(7.0F, 21.0F, 11.0F);
this.lclaw6.setRotationPoint(7.0F, 21.0F, 11.0F);
this.lclaw7.setRotationPoint(7.0F, 21.0F, 11.0F);
this.neck3.setRotationPoint(0.0F, -24.0F, -9.0F);
this.head3.setRotationPoint(0.0F, -26.0F, -14.0F);
this.jaw1.setRotationPoint(0.0F, -26.0F, -14.0F);
this.tooth1.setRotationPoint(0.0F, -26.0F, -14.0F);
this.tooth2.setRotationPoint(0.0F, -26.0F, -14.0F);
this.tooth3.setRotationPoint(0.0F, -26.0F, -14.0F);
this.tooth4.setRotationPoint(0.0F, -26.0F, -14.0F);
this.tooth5.setRotationPoint(0.0F, -26.0F, -14.0F);
this.jaw5.setRotationPoint(0.0F, -26.0F, -14.0F);
this.head7.setRotationPoint(0.0F, -26.0F, -14.0F);
this.tooth6.setRotationPoint(0.0F, -26.0F, -14.0F);
this.tooth7.setRotationPoint(0.0F, -26.0F, -14.0F);
this.tooth8.setRotationPoint(0.0F, -26.0F, -14.0F);
this.tooth9.setRotationPoint(0.0F, -26.0F, -14.0F);
this.tooth10.setRotationPoint(0.0F, -26.0F, -14.0F);
this.tooth11.setRotationPoint(0.0F, -26.0F, -14.0F);
this.tooth12.setRotationPoint(0.0F, -26.0F, -14.0F);
this.tooth13.setRotationPoint(0.0F, -26.0F, -14.0F);
this.rightleg1.setRotationPoint(-10.0F, 2.0F, 26.0F);
this.rightleg2.setRotationPoint(-10.0F, 2.0F, 26.0F);
this.tooth14.setRotationPoint(0.0F, -26.0F, -14.0F);
this.tooth15.setRotationPoint(0.0F, -26.0F, -14.0F);
this.tooth16.setRotationPoint(0.0F, -26.0F, -14.0F);
this.tooth17.setRotationPoint(0.0F, -26.0F, -14.0F);
this.tooth18.setRotationPoint(0.0F, -26.0F, -14.0F);
this.tooth19.setRotationPoint(0.0F, -26.0F, -14.0F);
this.tooth20.setRotationPoint(0.0F, -26.0F, -14.0F);
this.tooth21.setRotationPoint(0.0F, -26.0F, -14.0F);
this.tooth22.setRotationPoint(0.0F, -26.0F, -14.0F);
this.tooth23.setRotationPoint(0.0F, -26.0F, -14.0F);
this.rightleg3.setRotationPoint(-8.0F, 21.0F, 11.0F);
this.rclaw2.setRotationPoint(-8.0F, 21.0F, 11.0F);
this.rclaw4.setRotationPoint(-8.0F, 21.0F, 11.0F);
this.rclaw1.setRotationPoint(-8.0F, 21.0F, 11.0F);
this.rclaw5.setRotationPoint(-8.0F, 21.0F, 11.0F);
this.rclaw7.setRotationPoint(-8.0F, 21.0F, 11.0F);
this.rclaw3.setRotationPoint(-8.0F, 21.0F, 11.0F);
this.rclaw6.setRotationPoint(-8.0F, 21.0F, 11.0F);
this.neck1.setRotationPoint(0.0F, -9.0F, 5.0F);
this.neck2.setRotationPoint(0.0F, -19.0F, -2.0F);
this.tail4.setRotationPoint(0.0F, -1.0F, 56.0F);
this.Spike1.setRotationPoint(0.0F, -4.0F, 7.0F);
this.Spike2.setRotationPoint(0.0F, 0.0F, 29.0F);
this.Spike3.setRotationPoint(0.0F, -2.0F, 41.0F);
setRotation(this.lclaw1, 0.0F, 0.6632251F, 0.0F);
setRotation(this.body, -0.3141593F, 0.0F, 0.0F);
setRotation(this.leftleg1, -0.5759587F, 0.0F, 0.0F);
setRotation(this.tail1, -0.1745329F, 0.0F, 0.0F);
setRotation(this.leftleg2, 0.9773844F, 0.0F, 0.0F);
setRotation(this.body2, -0.1047198F, 0.0F, 0.0F);
setRotation(this.leftleg3, -0.5235988F, 0.0F, 0.0F);
setRotation(this.tail2, -0.1396263F, 0.0F, 0.0F);
setRotation(this.tail3, -0.1396263F, 0.0F, 0.0F);
setRotation(this.lclaw2, 0.0F, 0.0F, 0.0F);
setRotation(this.lclaw3, 0.0F, -0.6632251F, 0.0F);
setRotation(this.lclaw4, 0.0F, 0.0F, 0.0F);
setRotation(this.lclaw5, 0.0F, 0.6632251F, 0.0F);
setRotation(this.lclaw6, 0.0F, -0.6632251F, 0.0F);
setRotation(this.lclaw7, 0.0F, 0.0F, 0.0F);
setRotation(this.neck3, -0.2443461F, 0.0F, 0.0F);
setRotation(this.head3, -0.2443461F, 0.0F, 0.0F);
setRotation(this.jaw1, 0.1919862F, 0.0F, 0.0F);
setRotation(this.tooth1, -0.2443461F, 0.0F, 0.0F);
setRotation(this.tooth2, -0.2443461F, 0.0F, 0.0F);
setRotation(this.tooth3, -0.2443461F, 0.0F, 0.0F);
setRotation(this.tooth4, -0.2443461F, 0.0F, 0.0F);
setRotation(this.tooth5, -0.2443461F, 0.0F, 0.0F);
setRotation(this.jaw5, 0.1919862F, 0.0F, 0.0F);
setRotation(this.head7, -0.2443461F, 0.0F, 0.0F);
setRotation(this.tooth6, -0.2443461F, 0.0F, 0.0F);
setRotation(this.tooth7, -0.2443461F, 0.0F, 0.0F);
setRotation(this.tooth8, -0.2443461F, 0.0F, 0.0F);
setRotation(this.tooth9, -0.2443461F, 0.0F, 0.0F);
setRotation(this.tooth10, -0.2443461F, 0.0F, 0.0F);
setRotation(this.tooth11, -0.2443461F, 0.0F, 0.0F);
setRotation(this.tooth12, -0.2443461F, 0.0F, 0.0F);
setRotation(this.tooth13, -0.2443461F, 0.0F, 0.0F);
setRotation(this.rightleg1, -0.5934119F, 0.0F, 0.0F);
setRotation(this.rightleg2, 0.9773844F, 0.0F, 0.0F);
setRotation(this.tooth14, 0.1919862F, 0.0F, 0.0F);
setRotation(this.tooth15, 0.1919862F, 0.0F, 0.0F);
setRotation(this.tooth16, 0.1919862F, 0.0F, 0.0F);
setRotation(this.tooth17, 0.1919862F, 0.0F, 0.0F);
setRotation(this.tooth18, 0.1919862F, 0.0F, 0.0F);
setRotation(this.tooth19, 0.1919862F, 0.0F, 0.0F);
setRotation(this.tooth20, 0.1919862F, 0.0F, 0.0F);
setRotation(this.tooth21, 0.1919862F, 0.0F, 0.0F);
setRotation(this.tooth22, 0.1919862F, 0.0F, 0.0F);
setRotation(this.tooth23, 0.1919862F, 0.0F, 0.0F);
setRotation(this.rightleg3, -0.5235988F, 0.0F, 0.0F);
setRotation(this.rclaw2, 0.0F, 0.0F, 0.0F);
setRotation(this.rclaw4, 0.0F, 0.0F, 0.0F);
setRotation(this.rclaw1, 0.0F, -0.6632251F, 0.0F);
setRotation(this.rclaw5, 0.0F, -0.6632251F, 0.0F);
setRotation(this.rclaw7, 0.0F, 0.0F, 0.0F);
setRotation(this.rclaw3, 0.0F, 0.6632251F, 0.0F);
setRotation(this.rclaw6, 0.0F, 0.6632251F, 0.0F);
setRotation(this.neck1, -0.837758F, 0.0F, 0.0F);
setRotation(this.neck2, -0.7853982F, 0.0F, 0.0F);
setRotation(this.tail4, -0.1396263F, 0.0F, 0.0F);
setRotation(this.Spike1, 0.5061455F, 0.0F, 0.0F);
setRotation(this.Spike2, 0.4886922F, 0.0F, 0.0F);
setRotation(this.Spike3, 0.5934119F, 0.0F, 0.0F);

     NastysaurusEntity.CustomEntity e = (NastysaurusEntity.CustomEntity) entity;
     float walk = MathHelper.cos(ageInTicks * 0.65F * this.wingspeed) * 0.55F * limbSwingAmount;
     this.leftleg1.rotateAngleX += walk; this.leftleg2.rotateAngleX += walk; this.leftleg3.rotateAngleX += walk;
     this.rightleg1.rotateAngleX -= walk; this.rightleg2.rotateAngleX -= walk; this.rightleg3.rotateAngleX -= walk;
     float leftCos = MathHelper.cos(walk), leftSin = MathHelper.sin(walk);
     this.leftleg3.setRotationPoint(7.0F, 2.0F + 19.0F * leftCos + 15.0F * leftSin,
        26.0F + 19.0F * leftSin - 15.0F * leftCos);
     float rightCos = MathHelper.cos(-walk), rightSin = MathHelper.sin(-walk);
     this.rightleg3.setRotationPoint(-8.0F, 2.0F + 19.0F * rightCos + 15.0F * rightSin,
        26.0F + 19.0F * rightSin - 15.0F * rightCos);
     ModelRenderer[] leftClaws = {this.lclaw1,this.lclaw2,this.lclaw3,this.lclaw4,this.lclaw5,this.lclaw6,this.lclaw7};
     for (ModelRenderer claw : leftClaws) {
       claw.setRotationPoint(this.leftleg3.rotationPointX, this.leftleg3.rotationPointY, this.leftleg3.rotationPointZ);
       claw.rotateAngleX += walk;
     }
     ModelRenderer[] rightClaws = {this.rclaw1,this.rclaw2,this.rclaw3,this.rclaw4,this.rclaw5,this.rclaw6,this.rclaw7};
     for (ModelRenderer claw : rightClaws) {
       claw.setRotationPoint(this.rightleg3.rotationPointX, this.rightleg3.rotationPointY, this.rightleg3.rotationPointZ);
       claw.rotateAngleX -= walk;
     }
     float tailWave = MathHelper.sin(ageInTicks * 0.10F * this.wingspeed) * 0.12F;
     this.tail1.rotateAngleY += tailWave * 0.25F; this.tail2.rotateAngleY += tailWave * 0.50F;
     this.tail3.rotateAngleY += tailWave * 0.75F; this.tail4.rotateAngleY += tailWave;
     float yaw = netHeadYaw * ((float)Math.PI / 180F) * 0.35F;
     float pitch = headPitch * ((float)Math.PI / 180F) * 0.25F;
     ModelRenderer[] face = {this.neck1,this.neck2,this.neck3,this.head3,this.head7,this.jaw1,this.jaw5,
        this.tooth1,this.tooth2,this.tooth3,this.tooth4,this.tooth5,this.tooth6,this.tooth7,this.tooth8,
        this.tooth9,this.tooth10,this.tooth11,this.tooth12,this.tooth13,this.tooth14,this.tooth15,this.tooth16,
        this.tooth17,this.tooth18,this.tooth19,this.tooth20,this.tooth21,this.tooth22,this.tooth23};
     for (ModelRenderer part : face) { part.rotateAngleY += yaw; part.rotateAngleX += pitch; }
     if (e.getAttacking() != 0) {
       float bite = Math.abs(MathHelper.sin(ageInTicks * 0.75F * this.wingspeed)) * 0.45F;
       this.jaw1.rotateAngleX += bite; this.jaw5.rotateAngleX += bite;
       ModelRenderer[] lowerTeeth = {this.tooth1,this.tooth2,this.tooth3,this.tooth4,this.tooth5,
          this.tooth14,this.tooth15,this.tooth16,this.tooth17,this.tooth18};
       for (ModelRenderer part : lowerTeeth) part.rotateAngleX += bite;
     }
    }
 }
}
