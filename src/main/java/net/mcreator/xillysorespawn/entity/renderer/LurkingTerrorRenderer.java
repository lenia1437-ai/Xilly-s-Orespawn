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
import net.mcreator.xillysorespawn.entity.LurkingTerrorEntity;
import net.mcreator.xillysorespawn.entity.OreSpawnLogic;

@OnlyIn(Dist.CLIENT)
public class LurkingTerrorRenderer {
    private static final String TEXTURE = "xillys_orespawn:textures/entities/lurkingterror.png";
    public static class ModelRegisterHandler {
        @SubscribeEvent @OnlyIn(Dist.CLIENT)
        public void registerModels(ModelRegistryEvent event) {
            RenderingRegistry.registerEntityRenderingHandler(LurkingTerrorEntity.entity, manager ->
                new MobRenderer(manager, new ModelLurkingTerror(), 0.3825F) {
                    @Override public ResourceLocation getEntityTexture(Entity entity) { return new ResourceLocation(TEXTURE); }
                    @Override protected void preRenderCallback(LivingEntity entity, MatrixStack stack, float partialTick) {
                        float modelScale = 0.85F;
                        stack.scale(modelScale, modelScale, modelScale);
                    }
                });
        }
    }

public static class ModelLurkingTerror extends EntityModel<Entity> {
   private float wingspeed = 1.0F;

   private final ModelRenderer body;

   private final ModelRenderer leg1;

   private final ModelRenderer leg1part2;
   private final ModelRenderer leg1part3;
   private final ModelRenderer leg2;
   private final ModelRenderer leg2part2;
   private final ModelRenderer leg2part3;
   private final ModelRenderer leg3;
   private final ModelRenderer leg3part2;
   private final ModelRenderer leg3part3;
   private final ModelRenderer leg4;
   private final ModelRenderer leg4part2;
   private final ModelRenderer leg4part3;
   private final ModelRenderer leg5;
   private final ModelRenderer leg5part2;
   private final ModelRenderer leg6;
   private final ModelRenderer leg6part2;
   private final ModelRenderer thorax;
   private final ModelRenderer abdomen;
   private final ModelRenderer head;
   private final ModelRenderer jaw1;
   private final ModelRenderer jaw1part2;
   private final ModelRenderer jaw1tooth1;
   private final ModelRenderer jaw1tooth2;
   private final ModelRenderer jaw1tooth3;
   private final ModelRenderer jaw1tooth4;
   private final ModelRenderer jaw1tooth5;
   private final ModelRenderer jaw1tooth6;
   private final ModelRenderer jaw2;
   private final ModelRenderer jaw2part2;
   private final ModelRenderer jaw2tooth1;
   private final ModelRenderer jaw2tooth2;
   private final ModelRenderer jaw2tooth3;
   private final ModelRenderer jaw2tooth4;
   private final ModelRenderer jaw2tooth5;
   private final ModelRenderer jaw2tooth6;
   private final ModelRenderer jaw3;
   private final ModelRenderer jaw3part2;
   private final ModelRenderer jaw3tooth1;
   private final ModelRenderer jaw3tooth2;
   private final ModelRenderer jaw3tooth3;
   private final ModelRenderer jaw3tooth4;
   private final ModelRenderer jaw3tooth5;
   private final ModelRenderer jaw3tooth6;
   private final ModelRenderer jaw4;
   private final ModelRenderer jaw4part2;
   private final ModelRenderer jaw4tooth1;
   private final ModelRenderer jaw4tooth2;
   private final ModelRenderer jaw4tooth3;
   private final ModelRenderer jaw4tooth4;
   private final ModelRenderer jaw4tooth5;
   private final ModelRenderer jaw4tooth6;
   private final ModelRenderer tonguepart1;
   private final ModelRenderer tonguepart2;
   private final ModelRenderer tonguepart3;
   private final ModelRenderer wing_1;
   private final ModelRenderer wing_2;
   private final ModelRenderer wing_3;
   private final ModelRenderer wing_4;

   public ModelLurkingTerror() {
     this.wingspeed = 1.0F;
     this.textureWidth = 256;
     this.textureHeight = 64;

     this.body = new ModelRenderer(this, 39, 27);
     this.body.addBox(-4.0F, -4.0F, -8.0F, 8, 8, 12);
     this.body.setRotationPoint(0.0F, 10.0F, 0.0F);

     this.body.mirror = true;
     setRotation(this.body, 0.0F, 0.0F, 0.0F);
     this.leg1 = new ModelRenderer(this, 18, 0);
     this.leg1.addBox(-15.0F, -1.5F, -1.5F, 16, 3, 3);
     this.leg1.setRotationPoint(-4.0F, 10.0F, -1.0F);

     this.leg1.mirror = true;
     setRotation(this.leg1, 0.0F, -0.5759587F, -0.1919862F);
     this.leg1part2 = new ModelRenderer(this, 58, 0);
     this.leg1part2.addBox(-15.0F, -1.5F, -1.5F, 3, 8, 3);
     this.leg1part2.setRotationPoint(-4.0F, 10.0F, -1.0F);

     this.leg1part2.mirror = true;
     setRotation(this.leg1part2, 0.0F, -0.5759587F, -0.1919862F);
     this.leg1part3 = new ModelRenderer(this, 0, 0);
     this.leg1part3.addBox(-15.0F, -1.0F, -1.0F, 2, 8, 2);
     this.leg1part3.setRotationPoint(-4.0F, 10.0F, -1.0F);

     this.leg1part3.mirror = true;
     setRotation(this.leg1part3, 0.0F, -0.5759587F, -0.6753082F);
     this.leg2 = new ModelRenderer(this, 18, 0);
     this.leg2.addBox(-1.0F, -1.5F, -1.5F, 16, 3, 3);
     this.leg2.setRotationPoint(4.0F, 10.0F, -1.0F);

     this.leg2.mirror = true;
     setRotation(this.leg2, 0.0F, 0.5759587F, 0.1919862F);
     this.leg2part2 = new ModelRenderer(this, 58, 0);
     this.leg2part2.addBox(12.0F, -1.5F, -1.5F, 3, 8, 3);
     this.leg2part2.setRotationPoint(4.0F, 10.0F, -1.0F);

     this.leg2part2.mirror = true;
     setRotation(this.leg2part2, 0.0F, 0.5759587F, 0.1919862F);
     this.leg2part3 = new ModelRenderer(this, 0, 0);
     this.leg2part3.addBox(13.0F, -1.0F, -1.0F, 2, 8, 2);
     this.leg2part3.setRotationPoint(4.0F, 10.0F, -1.0F);

     this.leg2part3.mirror = true;
     setRotation(this.leg2part3, 0.0F, 0.5759587F, 0.6753028F);
     this.leg3 = new ModelRenderer(this, 18, 0);
     this.leg3.addBox(-15.0F, -1.5F, -1.5F, 16, 3, 3);
     this.leg3.setRotationPoint(-4.0F, 10.0F, 1.0F);

     this.leg3.mirror = true;
     setRotation(this.leg3, 0.0F, 0.2792527F, -0.1919862F);
     this.leg3part2 = new ModelRenderer(this, 58, 0);
     this.leg3part2.addBox(-15.0F, -1.5F, -1.5F, 3, 8, 3);
     this.leg3part2.setRotationPoint(-4.0F, 10.0F, 1.0F);

     this.leg3part2.mirror = true;
     setRotation(this.leg3part2, 0.0F, 0.2792527F, -0.1919862F);
     this.leg3part3 = new ModelRenderer(this, 0, 0);
     this.leg3part3.addBox(-15.0F, -1.0F, -1.0F, 2, 8, 2);
     this.leg3part3.setRotationPoint(-4.0F, 10.0F, 1.0F);

     this.leg3part3.mirror = true;
     setRotation(this.leg3part3, 0.0F, 0.2792527F, -0.6753028F);
     this.leg4 = new ModelRenderer(this, 18, 0);
     this.leg4.addBox(-1.0F, -1.5F, -1.5F, 16, 3, 3);
     this.leg4.setRotationPoint(4.0F, 10.0F, 1.0F);

     this.leg4.mirror = true;
     setRotation(this.leg4, 0.0F, -0.2792527F, 0.1919862F);
     this.leg4part2 = new ModelRenderer(this, 58, 0);
     this.leg4part2.addBox(12.0F, -1.5F, -1.5F, 3, 8, 3);
     this.leg4part2.setRotationPoint(4.0F, 10.0F, 1.0F);

     this.leg4part2.mirror = true;
     setRotation(this.leg4part2, 0.0F, -0.2792527F, 0.1919862F);
     this.leg4part3 = new ModelRenderer(this, 0, 0);
     this.leg4part3.addBox(13.0F, -1.0F, -1.0F, 2, 8, 2);
     this.leg4part3.setRotationPoint(4.0F, 10.0F, 1.0F);

     this.leg4part3.mirror = true;
     setRotation(this.leg4part3, 0.0F, -0.2792527F, 0.6753028F);
     this.leg5 = new ModelRenderer(this, 119, 0);
     this.leg5.addBox(-4.0F, -1.5F, -1.5F, 25, 3, 3);
     this.leg5.setRotationPoint(4.0F, 10.0F, 4.0F);

     this.leg5.mirror = true;
     setRotation(this.leg5, 0.0F, -1.134359F, 0.3407057F);
     this.leg5part2 = new ModelRenderer(this, 18, 9);
     this.leg5part2.addBox(18.0F, -1.5F, -1.5F, 3, 10, 3);
     this.leg5part2.setRotationPoint(4.0F, 10.0F, 4.0F);

     this.leg5part2.mirror = true;
     setRotation(this.leg5part2, 0.0F, -1.134359F, 0.3407057F);
     this.leg6 = new ModelRenderer(this, 119, 0);
     this.leg6.addBox(-21.0F, -1.5F, -1.5F, 25, 3, 3);
     this.leg6.setRotationPoint(-4.0F, 10.0F, 4.0F);

     this.leg6.mirror = true;
     setRotation(this.leg6, 0.0F, 1.134359F, -0.3407057F);
     this.leg6part2 = new ModelRenderer(this, 18, 9);
     this.leg6part2.addBox(-21.0F, -1.5F, -1.5F, 3, 10, 3);
     this.leg6part2.setRotationPoint(-4.0F, 10.0F, 4.0F);

     this.leg6part2.mirror = true;
     setRotation(this.leg6part2, 0.0F, 1.134359F, -0.3407057F);
     this.thorax = new ModelRenderer(this, 0, 42);
     this.thorax.addBox(-2.0F, -2.0F, -6.0F, 4, 4, 18);
     this.thorax.setRotationPoint(0.0F, 10.0F, 9.0F);

     this.thorax.mirror = true;
     setRotation(this.thorax, -0.2602503F, 0.0F, 0.0F);
     this.abdomen = new ModelRenderer(this, 118, 18);
     this.abdomen.addBox(-3.0F, -3.0F, 0.0F, 6, 6, 16);
     this.abdomen.setRotationPoint(0.0F, 13.0F, 20.0F);

     this.abdomen.mirror = true;
     setRotation(this.abdomen, 0.0F, 0.0F, 0.0F);
     this.head = new ModelRenderer(this, 27, 48);
     this.head.addBox(-3.0F, -3.0F, -3.0F, 6, 6, 5);
     this.head.setRotationPoint(0.0F, 10.0F, -8.0F);

     this.head.mirror = true;
     setRotation(this.head, 0.0F, 0.0F, 0.0F);
     this.jaw1 = new ModelRenderer(this, 96, 31);
     this.jaw1.addBox(-1.0F, -1.0F, -13.0F, 1, 2, 14);
     this.jaw1.setRotationPoint(-2.0F, 10.0F, -8.0F);

     this.jaw1.mirror = true;
     setRotation(this.jaw1, 0.0F, 0.4089647F, 0.0F);
     this.jaw1part2 = new ModelRenderer(this, 39, 17);
     this.jaw1part2.addBox(-1.1F, -2.0F, -5.0F, 1, 4, 5);
     this.jaw1part2.setRotationPoint(-2.0F, 10.0F, -8.0F);

     this.jaw1part2.mirror = true;
     setRotation(this.jaw1part2, 0.0F, 0.4089647F, 0.0F);
     this.jaw1tooth1 = new ModelRenderer(this, 39, 27);
     this.jaw1tooth1.addBox(0.0F, -0.5F, -13.0F, 1, 1, 1);
     this.jaw1tooth1.setRotationPoint(-2.0F, 10.0F, -8.0F);

     this.jaw1tooth1.mirror = true;
     setRotation(this.jaw1tooth1, 0.0F, 0.4089647F, 0.0F);
     this.jaw1tooth2 = new ModelRenderer(this, 39, 27);
     this.jaw1tooth2.addBox(0.0F, -0.5F, -11.0F, 1, 1, 1);
     this.jaw1tooth2.setRotationPoint(-2.0F, 10.0F, -8.0F);

     this.jaw1tooth2.mirror = true;
     setRotation(this.jaw1tooth2, 0.0F, 0.4089647F, 0.0F);
     this.jaw1tooth3 = new ModelRenderer(this, 39, 27);
     this.jaw1tooth3.addBox(0.0F, -0.5F, -9.0F, 1, 1, 1);
     this.jaw1tooth3.setRotationPoint(-2.0F, 10.0F, -8.0F);

     this.jaw1tooth3.mirror = true;
     setRotation(this.jaw1tooth3, 0.0F, 0.4089647F, 0.0F);
     this.jaw1tooth4 = new ModelRenderer(this, 39, 27);
     this.jaw1tooth4.addBox(0.0F, -0.5F, -7.0F, 1, 1, 1);
     this.jaw1tooth4.setRotationPoint(-2.0F, 10.0F, -8.0F);

     this.jaw1tooth4.mirror = true;
     setRotation(this.jaw1tooth4, 0.0F, 0.4089647F, 0.0F);
     this.jaw1tooth5 = new ModelRenderer(this, 39, 27);
     this.jaw1tooth5.addBox(0.0F, -1.5F, -4.5F, 1, 1, 1);
     this.jaw1tooth5.setRotationPoint(-2.0F, 10.0F, -8.0F);

     this.jaw1tooth5.mirror = true;
     setRotation(this.jaw1tooth5, 0.0F, 0.4089647F, 0.0F);
     this.jaw1tooth6 = new ModelRenderer(this, 39, 27);
     this.jaw1tooth6.addBox(0.0F, 0.5F, -4.5F, 1, 1, 1);
     this.jaw1tooth6.setRotationPoint(-2.0F, 10.0F, -8.0F);

     this.jaw1tooth6.mirror = true;
     setRotation(this.jaw1tooth6, 0.0F, 0.4089647F, 0.0F);
     this.jaw2 = new ModelRenderer(this, 96, 48);
     this.jaw2.addBox(0.0F, -1.0F, -13.0F, 1, 2, 14);
     this.jaw2.setRotationPoint(2.0F, 10.0F, -8.0F);

     this.jaw2.mirror = true;
     setRotation(this.jaw2, 0.0F, -0.4089656F, 0.0F);
     this.jaw2part2 = new ModelRenderer(this, 39, 7);
     this.jaw2part2.addBox(0.1F, -2.0F, -5.0F, 1, 4, 5);
     this.jaw2part2.setRotationPoint(2.0F, 10.0F, -8.0F);

     this.jaw2part2.mirror = true;
     setRotation(this.jaw2part2, 0.0F, -0.4089656F, 0.0F);
     this.jaw2tooth1 = new ModelRenderer(this, 96, 48);
     this.jaw2tooth1.addBox(-1.0F, -0.5F, -13.0F, 1, 1, 1);
     this.jaw2tooth1.setRotationPoint(2.0F, 10.0F, -8.0F);

     this.jaw2tooth1.mirror = true;
     setRotation(this.jaw2tooth1, 0.0F, -0.4089656F, 0.0F);
     this.jaw2tooth2 = new ModelRenderer(this, 96, 48);
     this.jaw2tooth2.addBox(-1.0F, -0.5F, -11.0F, 1, 1, 1);
     this.jaw2tooth2.setRotationPoint(2.0F, 10.0F, -8.0F);

     this.jaw2tooth2.mirror = true;
     setRotation(this.jaw2tooth2, 0.0F, -0.4089656F, 0.0F);
     this.jaw2tooth3 = new ModelRenderer(this, 96, 48);
     this.jaw2tooth3.addBox(-1.0F, -0.5F, -9.0F, 1, 1, 1);
     this.jaw2tooth3.setRotationPoint(2.0F, 10.0F, -8.0F);

     this.jaw2tooth3.mirror = true;
     setRotation(this.jaw2tooth3, 0.0F, -0.4089656F, 0.0F);
     this.jaw2tooth4 = new ModelRenderer(this, 96, 48);
     this.jaw2tooth4.addBox(-1.0F, -0.5F, -7.0F, 1, 1, 1);
     this.jaw2tooth4.setRotationPoint(2.0F, 10.0F, -8.0F);

     this.jaw2tooth4.mirror = true;
     setRotation(this.jaw2tooth4, 0.0F, -0.4089656F, 0.0F);
     this.jaw2tooth5 = new ModelRenderer(this, 96, 48);
     this.jaw2tooth5.addBox(-1.0F, -1.5F, -4.5F, 1, 1, 1);
     this.jaw2tooth5.setRotationPoint(2.0F, 10.0F, -8.0F);

     this.jaw2tooth5.mirror = true;
     setRotation(this.jaw2tooth5, 0.0F, -0.4089656F, 0.0F);
     this.jaw2tooth6 = new ModelRenderer(this, 96, 48);
     this.jaw2tooth6.addBox(-1.0F, 0.5F, -4.5F, 1, 1, 1);
     this.jaw2tooth6.setRotationPoint(2.0F, 10.0F, -8.0F);

     this.jaw2tooth6.mirror = true;
     setRotation(this.jaw2tooth6, 0.0F, -0.4089656F, 0.0F);
     this.jaw3 = new ModelRenderer(this, 95, 16);
     this.jaw3.addBox(-1.0F, -1.0F, -13.0F, 2, 1, 14);
     this.jaw3.setRotationPoint(0.0F, 8.0F, -8.0F);

     this.jaw3.mirror = true;
     setRotation(this.jaw3, -0.4089647F, 0.0F, 0.0F);
     this.jaw3part2 = new ModelRenderer(this, 0, 27);
     this.jaw3part2.addBox(-2.0F, -1.0F, -5.0F, 4, 1, 5);
     this.jaw3part2.setRotationPoint(0.0F, 7.9F, -8.0F);

     this.jaw3part2.mirror = true;
     setRotation(this.jaw3part2, -0.4089647F, 0.0F, 0.0F);
     this.jaw3tooth1 = new ModelRenderer(this, 95, 16);
     this.jaw3tooth1.addBox(-0.5F, 0.0F, -13.0F, 1, 1, 1);
     this.jaw3tooth1.setRotationPoint(0.0F, 8.0F, -8.0F);

     this.jaw3tooth1.mirror = true;
     setRotation(this.jaw3tooth1, -0.4089647F, 0.0F, 0.0F);
     this.jaw3tooth2 = new ModelRenderer(this, 95, 16);
     this.jaw3tooth2.addBox(-0.5F, 0.0F, -11.0F, 1, 1, 1);
     this.jaw3tooth2.setRotationPoint(0.0F, 8.0F, -8.0F);

     this.jaw3tooth2.mirror = true;
     setRotation(this.jaw3tooth2, -0.4089647F, 0.0F, 0.0F);
     this.jaw3tooth3 = new ModelRenderer(this, 95, 16);
     this.jaw3tooth3.addBox(-0.5F, 0.0F, -9.0F, 1, 1, 1);
     this.jaw3tooth3.setRotationPoint(0.0F, 8.0F, -8.0F);

     this.jaw3tooth3.mirror = true;
     setRotation(this.jaw3tooth3, -0.4089647F, 0.0F, 0.0F);
     this.jaw3tooth4 = new ModelRenderer(this, 95, 16);
     this.jaw3tooth4.addBox(-0.5F, 0.0F, -7.0F, 1, 1, 1);
     this.jaw3tooth4.setRotationPoint(0.0F, 8.0F, -8.0F);

     this.jaw3tooth4.mirror = true;
     setRotation(this.jaw3tooth4, -0.4089647F, 0.0F, 0.0F);
     this.jaw3tooth5 = new ModelRenderer(this, 95, 16);
     this.jaw3tooth5.addBox(-1.5F, 0.0F, -4.5F, 1, 1, 1);
     this.jaw3tooth5.setRotationPoint(0.0F, 8.0F, -8.0F);

     this.jaw3tooth5.mirror = true;
     setRotation(this.jaw3tooth5, -0.4089647F, 0.0F, 0.0F);
     this.jaw3tooth6 = new ModelRenderer(this, 95, 16);
     this.jaw3tooth6.addBox(0.5F, 0.0F, -4.5F, 1, 1, 1);
     this.jaw3tooth6.setRotationPoint(0.0F, 8.0F, -8.0F);

     this.jaw3tooth6.mirror = true;
     setRotation(this.jaw3tooth6, -0.4089647F, 0.0F, 0.0F);
     this.jaw4 = new ModelRenderer(this, 95, 0);
     this.jaw4.addBox(-1.0F, 0.0F, -13.0F, 2, 1, 14);
     this.jaw4.setRotationPoint(0.0F, 12.0F, -8.0F);

     this.jaw4.mirror = true;
     setRotation(this.jaw4, 0.4089656F, 0.0F, 0.0F);
     this.jaw4part2 = new ModelRenderer(this, 0, 20);
     this.jaw4part2.addBox(-2.0F, 0.0F, -5.0F, 4, 1, 5);
     this.jaw4part2.setRotationPoint(0.0F, 12.1F, -8.0F);

     this.jaw4part2.mirror = true;
     setRotation(this.jaw4part2, 0.4089656F, 0.0F, 0.0F);
     this.jaw4tooth1 = new ModelRenderer(this, 95, 0);
     this.jaw4tooth1.addBox(-0.5F, -1.0F, -13.0F, 1, 1, 1);
     this.jaw4tooth1.setRotationPoint(0.0F, 12.0F, -8.0F);

     this.jaw4tooth1.mirror = true;
     setRotation(this.jaw4tooth1, 0.4089656F, 0.0F, 0.0F);
     this.jaw4tooth2 = new ModelRenderer(this, 95, 0);
     this.jaw4tooth2.addBox(-0.5F, -1.0F, -11.0F, 1, 1, 1);
     this.jaw4tooth2.setRotationPoint(0.0F, 12.0F, -8.0F);

     this.jaw4tooth2.mirror = true;
     setRotation(this.jaw4tooth2, 0.4089656F, 0.0F, 0.0F);
     this.jaw4tooth3 = new ModelRenderer(this, 95, 0);
     this.jaw4tooth3.addBox(-0.5F, -1.0F, -9.0F, 1, 1, 1);
     this.jaw4tooth3.setRotationPoint(0.0F, 12.0F, -8.0F);

     this.jaw4tooth3.mirror = true;
     setRotation(this.jaw4tooth3, 0.4089656F, 0.0F, 0.0F);
     this.jaw4tooth4 = new ModelRenderer(this, 95, 0);
     this.jaw4tooth4.addBox(-0.5F, -1.0F, -7.0F, 1, 1, 1);
     this.jaw4tooth4.setRotationPoint(0.0F, 12.0F, -8.0F);

     this.jaw4tooth4.mirror = true;
     setRotation(this.jaw4tooth4, 0.4089656F, 0.0F, 0.0F);
     this.jaw4tooth5 = new ModelRenderer(this, 95, 0);
     this.jaw4tooth5.addBox(-1.5F, -1.0F, -4.5F, 1, 1, 1);
     this.jaw4tooth5.setRotationPoint(0.0F, 12.0F, -8.0F);

     this.jaw4tooth5.mirror = true;
     setRotation(this.jaw4tooth5, 0.4089656F, 0.0F, 0.0F);
     this.jaw4tooth6 = new ModelRenderer(this, 95, 0);
     this.jaw4tooth6.addBox(0.5F, -1.0F, -4.5F, 1, 1, 1);
     this.jaw4tooth6.setRotationPoint(0.0F, 12.0F, -8.0F);

     this.jaw4tooth6.mirror = true;
     setRotation(this.jaw4tooth6, 0.4089656F, 0.0F, 0.0F);
     this.tonguepart1 = new ModelRenderer(this, 24, 34);
     this.tonguepart1.addBox(-0.5F, -0.5F, -5.0F, 1, 1, 5);
     this.tonguepart1.setRotationPoint(1.6F, 9.3F, -15.0F);

     this.tonguepart1.mirror = true;
     setRotation(this.tonguepart1, 1.041001F, 1.264073F, -1.07818F);
     this.tonguepart2 = new ModelRenderer(this, 0, 46);
     this.tonguepart2.addBox(-0.5F, -0.5F, -5.0F, 1, 1, 5);
     this.tonguepart2.setRotationPoint(0.0F, 10.0F, -11.0F);

     this.tonguepart2.mirror = true;
     setRotation(this.tonguepart2, -0.1858931F, -0.2230717F, 0.669215F);
     this.tonguepart3 = new ModelRenderer(this, 24, 27);
     this.tonguepart3.addBox(-0.5F, -0.5F, -5.0F, 1, 1, 5);
     this.tonguepart3.setRotationPoint(0.2F, 11.3F, -19.0F);

     this.tonguepart3.mirror = true;
     setRotation(this.tonguepart3, -0.2602503F, 0.3717861F, -1.07818F);
     this.wing_1 = new ModelRenderer(this, 108, 42);
     this.wing_1.addBox(-4.0F, 0.0F, 0.0F, 8, 0, 22);
     this.wing_1.setRotationPoint(-2.0F, 6.0F, -5.0F);

     this.wing_1.mirror = true;
     setRotation(this.wing_1, 0.5948578F, -0.9294653F, 0.0F);
     this.wing_2 = new ModelRenderer(this, 141, 42);
     this.wing_2.addBox(-4.0F, 0.0F, 0.0F, 8, 0, 22);
     this.wing_2.setRotationPoint(2.0F, 6.0F, -5.0F);

     this.wing_2.mirror = true;
     setRotation(this.wing_2, 0.5948606F, 0.9294576F, 0.0F);
     this.wing_3 = new ModelRenderer(this, 64, 27);
     this.wing_3.addBox(-2.0F, 0.0F, 0.0F, 4, 0, 18);
     this.wing_3.setRotationPoint(-2.0F, 6.0F, -1.0F);

     this.wing_3.mirror = true;
     setRotation(this.wing_3, 0.3346075F, -0.4089647F, 0.0F);
     this.wing_4 = new ModelRenderer(this, 153, 17);
     this.wing_4.addBox(-2.0F, 0.0F, 0.0F, 4, 0, 18);
     this.wing_4.setRotationPoint(2.0F, 6.0F, -1.0F);

     this.wing_4.mirror = true;
     setRotation(this.wing_4, 0.3346075F, 0.4089656F, 0.0F);
   }

   public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay,
        float red, float green, float blue, float alpha) {
        body.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        leg1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        leg1part2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        leg1part3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        leg2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        leg2part2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        leg2part3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        leg3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        leg3part2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        leg3part3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        leg4.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        leg4part2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        leg4part3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        leg5.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        leg5part2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        leg6.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        leg6part2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        thorax.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        abdomen.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        head.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        jaw1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        jaw1part2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        jaw1tooth1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        jaw1tooth2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        jaw1tooth3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        jaw1tooth4.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        jaw1tooth5.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        jaw1tooth6.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        jaw2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        jaw2part2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        jaw2tooth1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        jaw2tooth2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        jaw2tooth3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        jaw2tooth4.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        jaw2tooth5.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        jaw2tooth6.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        jaw3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        jaw3part2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        jaw3tooth1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        jaw3tooth2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        jaw3tooth3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        jaw3tooth4.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        jaw3tooth5.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        jaw3tooth6.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        jaw4.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        jaw4part2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        jaw4tooth1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        jaw4tooth2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        jaw4tooth3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        jaw4tooth4.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        jaw4tooth5.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        jaw4tooth6.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        tonguepart1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        tonguepart2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        tonguepart3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        wing_1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        wing_2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        wing_3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        wing_4.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }

   private void setRotation(ModelRenderer model, float x, float y, float z) {
     model.rotateAngleX = x;
     model.rotateAngleY = y;
     model.rotateAngleZ = z;
   }

   public void setRotationAngles(Entity entity, float limbSwing, float limbSwingAmount,
        float ageInTicks, float netHeadYaw, float headPitch) {
// Reset shared model state before applying this frame's legacy animation.
this.body.setRotationPoint(0.0F, 10.0F, 0.0F);
this.leg1.setRotationPoint(-4.0F, 10.0F, -1.0F);
this.leg1part2.setRotationPoint(-4.0F, 10.0F, -1.0F);
this.leg1part3.setRotationPoint(-4.0F, 10.0F, -1.0F);
this.leg2.setRotationPoint(4.0F, 10.0F, -1.0F);
this.leg2part2.setRotationPoint(4.0F, 10.0F, -1.0F);
this.leg2part3.setRotationPoint(4.0F, 10.0F, -1.0F);
this.leg3.setRotationPoint(-4.0F, 10.0F, 1.0F);
this.leg3part2.setRotationPoint(-4.0F, 10.0F, 1.0F);
this.leg3part3.setRotationPoint(-4.0F, 10.0F, 1.0F);
this.leg4.setRotationPoint(4.0F, 10.0F, 1.0F);
this.leg4part2.setRotationPoint(4.0F, 10.0F, 1.0F);
this.leg4part3.setRotationPoint(4.0F, 10.0F, 1.0F);
this.leg5.setRotationPoint(4.0F, 10.0F, 4.0F);
this.leg5part2.setRotationPoint(4.0F, 10.0F, 4.0F);
this.leg6.setRotationPoint(-4.0F, 10.0F, 4.0F);
this.leg6part2.setRotationPoint(-4.0F, 10.0F, 4.0F);
this.thorax.setRotationPoint(0.0F, 10.0F, 9.0F);
this.abdomen.setRotationPoint(0.0F, 13.0F, 20.0F);
this.head.setRotationPoint(0.0F, 10.0F, -8.0F);
this.jaw1.setRotationPoint(-2.0F, 10.0F, -8.0F);
this.jaw1part2.setRotationPoint(-2.0F, 10.0F, -8.0F);
this.jaw1tooth1.setRotationPoint(-2.0F, 10.0F, -8.0F);
this.jaw1tooth2.setRotationPoint(-2.0F, 10.0F, -8.0F);
this.jaw1tooth3.setRotationPoint(-2.0F, 10.0F, -8.0F);
this.jaw1tooth4.setRotationPoint(-2.0F, 10.0F, -8.0F);
this.jaw1tooth5.setRotationPoint(-2.0F, 10.0F, -8.0F);
this.jaw1tooth6.setRotationPoint(-2.0F, 10.0F, -8.0F);
this.jaw2.setRotationPoint(2.0F, 10.0F, -8.0F);
this.jaw2part2.setRotationPoint(2.0F, 10.0F, -8.0F);
this.jaw2tooth1.setRotationPoint(2.0F, 10.0F, -8.0F);
this.jaw2tooth2.setRotationPoint(2.0F, 10.0F, -8.0F);
this.jaw2tooth3.setRotationPoint(2.0F, 10.0F, -8.0F);
this.jaw2tooth4.setRotationPoint(2.0F, 10.0F, -8.0F);
this.jaw2tooth5.setRotationPoint(2.0F, 10.0F, -8.0F);
this.jaw2tooth6.setRotationPoint(2.0F, 10.0F, -8.0F);
this.jaw3.setRotationPoint(0.0F, 8.0F, -8.0F);
this.jaw3part2.setRotationPoint(0.0F, 7.9F, -8.0F);
this.jaw3tooth1.setRotationPoint(0.0F, 8.0F, -8.0F);
this.jaw3tooth2.setRotationPoint(0.0F, 8.0F, -8.0F);
this.jaw3tooth3.setRotationPoint(0.0F, 8.0F, -8.0F);
this.jaw3tooth4.setRotationPoint(0.0F, 8.0F, -8.0F);
this.jaw3tooth5.setRotationPoint(0.0F, 8.0F, -8.0F);
this.jaw3tooth6.setRotationPoint(0.0F, 8.0F, -8.0F);
this.jaw4.setRotationPoint(0.0F, 12.0F, -8.0F);
this.jaw4part2.setRotationPoint(0.0F, 12.1F, -8.0F);
this.jaw4tooth1.setRotationPoint(0.0F, 12.0F, -8.0F);
this.jaw4tooth2.setRotationPoint(0.0F, 12.0F, -8.0F);
this.jaw4tooth3.setRotationPoint(0.0F, 12.0F, -8.0F);
this.jaw4tooth4.setRotationPoint(0.0F, 12.0F, -8.0F);
this.jaw4tooth5.setRotationPoint(0.0F, 12.0F, -8.0F);
this.jaw4tooth6.setRotationPoint(0.0F, 12.0F, -8.0F);
this.tonguepart1.setRotationPoint(1.6F, 9.3F, -15.0F);
this.tonguepart2.setRotationPoint(0.0F, 10.0F, -11.0F);
this.tonguepart3.setRotationPoint(0.2F, 11.3F, -19.0F);
this.wing_1.setRotationPoint(-2.0F, 6.0F, -5.0F);
this.wing_2.setRotationPoint(2.0F, 6.0F, -5.0F);
this.wing_3.setRotationPoint(-2.0F, 6.0F, -1.0F);
this.wing_4.setRotationPoint(2.0F, 6.0F, -1.0F);
setRotation(this.body, 0.0F, 0.0F, 0.0F);
setRotation(this.leg1, 0.0F, -0.5759587F, -0.1919862F);
setRotation(this.leg1part2, 0.0F, -0.5759587F, -0.1919862F);
setRotation(this.leg1part3, 0.0F, -0.5759587F, -0.6753082F);
setRotation(this.leg2, 0.0F, 0.5759587F, 0.1919862F);
setRotation(this.leg2part2, 0.0F, 0.5759587F, 0.1919862F);
setRotation(this.leg2part3, 0.0F, 0.5759587F, 0.6753028F);
setRotation(this.leg3, 0.0F, 0.2792527F, -0.1919862F);
setRotation(this.leg3part2, 0.0F, 0.2792527F, -0.1919862F);
setRotation(this.leg3part3, 0.0F, 0.2792527F, -0.6753028F);
setRotation(this.leg4, 0.0F, -0.2792527F, 0.1919862F);
setRotation(this.leg4part2, 0.0F, -0.2792527F, 0.1919862F);
setRotation(this.leg4part3, 0.0F, -0.2792527F, 0.6753028F);
setRotation(this.leg5, 0.0F, -1.134359F, 0.3407057F);
setRotation(this.leg5part2, 0.0F, -1.134359F, 0.3407057F);
setRotation(this.leg6, 0.0F, 1.134359F, -0.3407057F);
setRotation(this.leg6part2, 0.0F, 1.134359F, -0.3407057F);
setRotation(this.thorax, -0.2602503F, 0.0F, 0.0F);
setRotation(this.abdomen, 0.0F, 0.0F, 0.0F);
setRotation(this.head, 0.0F, 0.0F, 0.0F);
setRotation(this.jaw1, 0.0F, 0.4089647F, 0.0F);
setRotation(this.jaw1part2, 0.0F, 0.4089647F, 0.0F);
setRotation(this.jaw1tooth1, 0.0F, 0.4089647F, 0.0F);
setRotation(this.jaw1tooth2, 0.0F, 0.4089647F, 0.0F);
setRotation(this.jaw1tooth3, 0.0F, 0.4089647F, 0.0F);
setRotation(this.jaw1tooth4, 0.0F, 0.4089647F, 0.0F);
setRotation(this.jaw1tooth5, 0.0F, 0.4089647F, 0.0F);
setRotation(this.jaw1tooth6, 0.0F, 0.4089647F, 0.0F);
setRotation(this.jaw2, 0.0F, -0.4089656F, 0.0F);
setRotation(this.jaw2part2, 0.0F, -0.4089656F, 0.0F);
setRotation(this.jaw2tooth1, 0.0F, -0.4089656F, 0.0F);
setRotation(this.jaw2tooth2, 0.0F, -0.4089656F, 0.0F);
setRotation(this.jaw2tooth3, 0.0F, -0.4089656F, 0.0F);
setRotation(this.jaw2tooth4, 0.0F, -0.4089656F, 0.0F);
setRotation(this.jaw2tooth5, 0.0F, -0.4089656F, 0.0F);
setRotation(this.jaw2tooth6, 0.0F, -0.4089656F, 0.0F);
setRotation(this.jaw3, -0.4089647F, 0.0F, 0.0F);
setRotation(this.jaw3part2, -0.4089647F, 0.0F, 0.0F);
setRotation(this.jaw3tooth1, -0.4089647F, 0.0F, 0.0F);
setRotation(this.jaw3tooth2, -0.4089647F, 0.0F, 0.0F);
setRotation(this.jaw3tooth3, -0.4089647F, 0.0F, 0.0F);
setRotation(this.jaw3tooth4, -0.4089647F, 0.0F, 0.0F);
setRotation(this.jaw3tooth5, -0.4089647F, 0.0F, 0.0F);
setRotation(this.jaw3tooth6, -0.4089647F, 0.0F, 0.0F);
setRotation(this.jaw4, 0.4089656F, 0.0F, 0.0F);
setRotation(this.jaw4part2, 0.4089656F, 0.0F, 0.0F);
setRotation(this.jaw4tooth1, 0.4089656F, 0.0F, 0.0F);
setRotation(this.jaw4tooth2, 0.4089656F, 0.0F, 0.0F);
setRotation(this.jaw4tooth3, 0.4089656F, 0.0F, 0.0F);
setRotation(this.jaw4tooth4, 0.4089656F, 0.0F, 0.0F);
setRotation(this.jaw4tooth5, 0.4089656F, 0.0F, 0.0F);
setRotation(this.jaw4tooth6, 0.4089656F, 0.0F, 0.0F);
setRotation(this.tonguepart1, 1.041001F, 1.264073F, -1.07818F);
setRotation(this.tonguepart2, -0.1858931F, -0.2230717F, 0.669215F);
setRotation(this.tonguepart3, -0.2602503F, 0.3717861F, -1.07818F);
setRotation(this.wing_1, 0.5948578F, -0.9294653F, 0.0F);
setRotation(this.wing_2, 0.5948606F, 0.9294576F, 0.0F);
setRotation(this.wing_3, 0.3346075F, -0.4089647F, 0.0F);
setRotation(this.wing_4, 0.3346075F, 0.4089656F, 0.0F);

     LurkingTerrorEntity.CustomEntity e = (LurkingTerrorEntity.CustomEntity) entity;
     float newangle = 0.0F;
     float legspeed = 0.7F;
     float mouthspeed = 0.9F;
     RenderInfo r = null;

     r = e.getRenderInfo();
     
     newangle = ageInTicks * legspeed * this.wingspeed % 6.2831855F;
     newangle = Math.abs(newangle);
     
     if (newangle < r.rf1) {
       
       r.ri1 = 0;
       if (e.getModelRandom().nextInt(3) == 1) r.ri1 |= 0x1; 
       if (e.getModelRandom().nextInt(3) == 1) r.ri1 |= 0x2; 
       if (e.getModelRandom().nextInt(4) == 1) r.ri1 |= 0x4; 
       if (e.getModelRandom().nextInt(4) == 1) r.ri1 |= 0x8; 
       if (e.getModelRandom().nextInt(6) == 1) r.ri1 |= 0x10; 
       if (e.getModelRandom().nextInt(6) == 1) r.ri1 |= 0x20;
     
     } 
     r.rf1 = newangle;
     
     newangle = ageInTicks * mouthspeed * this.wingspeed % 6.2831855F;
     newangle = Math.abs(newangle);
     
     if (newangle < r.rf2) {
       
       r.ri2 = 0;
       if (e.getModelRandom().nextInt(20) == 1) r.ri2 |= 0x1; 
       if (e.getAttacking() != 0) r.ri2 = 1; 
     } 
     r.rf2 = newangle;
 
     
     newangle = 0.0F;
     if ((r.ri1 & 0x1) != 0) {
       newangle = MathHelper.sin(ageInTicks * legspeed * this.wingspeed) * 3.1415927F * 0.25F;
     }
     this.leg2part2.rotateAngleZ = 0.191F + newangle;
     this.leg2part3.rotateAngleZ = 0.675F + newangle;
     newangle = 0.0F;
     if ((r.ri1 & 0x2) != 0) {
       newangle = MathHelper.sin(ageInTicks * legspeed * this.wingspeed) * 3.1415927F * 0.25F;
     }
     this.leg1part2.rotateAngleZ = -0.191F + newangle;
     this.leg1part3.rotateAngleZ = -0.675F + newangle;
     newangle = 0.0F;
     if ((r.ri1 & 0x4) != 0) {
       newangle = MathHelper.sin(ageInTicks * legspeed * this.wingspeed) * 3.1415927F * 0.15F;
     }
     this.leg4part2.rotateAngleZ = 0.191F + newangle;
     this.leg4part3.rotateAngleZ = 0.675F + newangle;
     newangle = 0.0F;
     if ((r.ri1 & 0x8) != 0) {
       newangle = MathHelper.sin(ageInTicks * legspeed * this.wingspeed) * 3.1415927F * 0.15F;
     }
     this.leg3part2.rotateAngleZ = -0.191F + newangle;
     this.leg3part3.rotateAngleZ = -0.675F + newangle;
     newangle = 0.0F;
     if ((r.ri1 & 0x10) != 0) {
       newangle = MathHelper.sin(ageInTicks * legspeed * this.wingspeed) * 3.1415927F * 0.1F;
     }
     this.leg6part2.rotateAngleZ = -0.34F + newangle;
     newangle = 0.0F;
     if ((r.ri1 & 0x20) != 0) {
       newangle = MathHelper.sin(ageInTicks * legspeed * this.wingspeed) * 3.1415927F * 0.1F;
     }
     this.leg5part2.rotateAngleZ = 0.34F + newangle;
 
 
     
     newangle = 0.0F;
     if ((r.ri2 & 0x1) != 0) {
       newangle = MathHelper.sin(ageInTicks * mouthspeed * this.wingspeed) * 3.1415927F * 0.35F;
       newangle = Math.abs(newangle);
     } 
     this.jaw1.rotateAngleY = newangle;
     this.jaw1part2.rotateAngleY = newangle;
     this.jaw1tooth5.rotateAngleY = newangle;
     this.jaw1tooth6.rotateAngleY = newangle;
     this.jaw2.rotateAngleY = -newangle;
     this.jaw2part2.rotateAngleY = -newangle;
     this.jaw2tooth5.rotateAngleY = -newangle;
     this.jaw2tooth6.rotateAngleY = -newangle;
     this.jaw3.rotateAngleX = -newangle;
     this.jaw3part2.rotateAngleX = -newangle;
     this.jaw3tooth5.rotateAngleX = -newangle;
     this.jaw3tooth6.rotateAngleX = -newangle;
     this.jaw4.rotateAngleX = newangle;
     this.jaw4part2.rotateAngleX = newangle;
     this.jaw4tooth5.rotateAngleX = newangle;
     this.jaw4tooth6.rotateAngleX = newangle;
     
     this.tonguepart3.rotateAngleX = 0.0F;
     this.tonguepart3.rotateAngleY = 0.0F;
     this.tonguepart3.rotateAngleZ = 0.0F;
     this.tonguepart3.rotationPointX = this.tonguepart2.rotationPointX;
     this.tonguepart3.rotationPointY = this.tonguepart2.rotationPointY;
     this.tonguepart2.rotationPointZ -= newangle * 5.0F;
     this.tonguepart2.rotationPointZ -= newangle * 10.0F;
     
     newangle = MathHelper.sin(ageInTicks * 0.1F * this.wingspeed) * 3.1415927F * 0.06F;
     this.thorax.rotateAngleX = newangle;
     this.abdomen.rotationPointY = (float)(this.thorax.rotationPointY - Math.sin(newangle) * 14.0D);
     
     newangle = MathHelper.cos(ageInTicks * 1.4F * this.wingspeed) * 3.1415927F * 0.2F;
     this.wing_1.rotateAngleX = 0.455F + newangle;
     this.wing_2.rotateAngleX = 0.455F + newangle;
     this.wing_3.rotateAngleX = 0.455F - newangle;
     this.wing_4.rotateAngleX = 0.455F - newangle;
 
     
     e.setRenderInfo(r);
    }
 }
}