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
import net.mcreator.xillysorespawn.entity.StinkBugEntity;
import net.mcreator.xillysorespawn.entity.OreSpawnLogic;

@OnlyIn(Dist.CLIENT)
public class StinkBugRenderer {
    private static final String TEXTURE = "xillys_orespawn:textures/entities/stinkbug.png";
    public static class ModelRegisterHandler {
        @SubscribeEvent @OnlyIn(Dist.CLIENT)
        public void registerModels(ModelRegistryEvent event) {
            RenderingRegistry.registerEntityRenderingHandler(StinkBugEntity.entity, manager ->
                new MobRenderer(manager, new ModelStinkBug(0.75F), 0.2975F) {
                    @Override public ResourceLocation getEntityTexture(Entity entity) { return new ResourceLocation(TEXTURE); }
                    @Override protected void preRenderCallback(LivingEntity entity, MatrixStack stack, float partialTick) {
                        float modelScale = 0.85F;
                        if (entity.isChild()) modelScale *= 0.5F;
                        stack.scale(modelScale, modelScale, modelScale);
                    }
                });
        }
    }

public static class ModelStinkBug extends EntityModel<Entity>
 {
   private float wingspeed = 1.0F;

   private final ModelRenderer f6;

   private final ModelRenderer b10;
   private final ModelRenderer l6;
   private final ModelRenderer l4;
   private final ModelRenderer f4;
   private final ModelRenderer l5;
   private final ModelRenderer f5;
   private final ModelRenderer l3;
   private final ModelRenderer l2;
   private final ModelRenderer l1;
   private final ModelRenderer f3;
   private final ModelRenderer f2;
   private final ModelRenderer f1;
   private final ModelRenderer jaw;
   private final ModelRenderer b9;
   private final ModelRenderer head;
   private final ModelRenderer b4;
   private final ModelRenderer h1;
   private final ModelRenderer h2;
   private final ModelRenderer body;
   private final ModelRenderer t21;
   private final ModelRenderer tail;
   private final ModelRenderer t22;
   private final ModelRenderer t20;
   private final ModelRenderer t19;
   private final ModelRenderer t6;
   private final ModelRenderer t11;
   private final ModelRenderer t9;
   private final ModelRenderer t4;
   private final ModelRenderer t2;
   private final ModelRenderer t7;
   private final ModelRenderer t12;
   private final ModelRenderer t10;
   private final ModelRenderer t8;
   private final ModelRenderer t5;
   private final ModelRenderer t3;
   private final ModelRenderer t1;
   private final ModelRenderer t18;
   private final ModelRenderer t16;
   private final ModelRenderer t14;
   private final ModelRenderer t13;
   private final ModelRenderer t15;
   private final ModelRenderer t17;
   private final ModelRenderer b1;
   private final ModelRenderer b2;
   private final ModelRenderer b3;
   private final ModelRenderer b8;
   private final ModelRenderer b7;
   private final ModelRenderer b6;
   private final ModelRenderer b5;

   public ModelStinkBug(float ff1) {
     this.wingspeed = ff1;

     this.textureWidth = 64;
     this.textureHeight = 32;

     this.f6 = new ModelRenderer(this, 20, 16);
     this.f6.addBox(-2.0F, 0.0F, -1.0F, 2, 2, 2);
     this.f6.setRotationPoint(-3.5F, 16.0F, 3.0F);

     this.f6.mirror = true;
     setRotation(this.f6, 0.0F, 0.0F, 0.0F);
     this.b10 = new ModelRenderer(this, 0, 2);
     this.b10.addBox(-0.5F, -1.5F, -0.5F, 1, 2, 1);
     this.b10.setRotationPoint(0.0F, 11.0F, 1.0F);

     this.b10.mirror = true;
     setRotation(this.b10, -0.5235988F, 0.0F, 0.0F);
     this.l6 = new ModelRenderer(this, 20, 13);
     this.l6.addBox(-2.0F, 0.0F, -1.0F, 2, 1, 2);
     this.l6.setRotationPoint(-3.0F, 15.0F, 3.0F);

     this.l6.mirror = true;
     setRotation(this.l6, 0.0F, 0.0F, 0.0F);
     this.l4 = new ModelRenderer(this, 20, 13);
     this.l4.addBox(-2.0F, 0.0F, -1.0F, 2, 1, 2);
     this.l4.setRotationPoint(-3.0F, 15.0F, -3.0F);

     this.l4.mirror = true;
     setRotation(this.l4, 0.0F, 0.0F, 0.0F);
     this.f4 = new ModelRenderer(this, 20, 16);
     this.f4.addBox(-2.0F, 0.0F, -1.0F, 2, 2, 2);
     this.f4.setRotationPoint(-3.5F, 16.0F, -3.0F);

     this.f4.mirror = true;
     setRotation(this.f4, 0.0F, 0.0F, 0.0F);
     this.l5 = new ModelRenderer(this, 20, 13);
     this.l5.addBox(-2.0F, 0.0F, -1.0F, 2, 1, 2);
     this.l5.setRotationPoint(-3.0F, 15.0F, 0.0F);

     this.l5.mirror = true;
     setRotation(this.l5, 0.0F, 0.0F, 0.0F);
     this.f5 = new ModelRenderer(this, 20, 16);
     this.f5.addBox(-2.0F, 0.0F, -1.0F, 2, 2, 2);
     this.f5.setRotationPoint(-3.5F, 16.0F, 0.0F);

     this.f5.mirror = true;
     setRotation(this.f5, 0.0F, 0.0F, 0.0F);
     this.l3 = new ModelRenderer(this, 20, 13);
     this.l3.addBox(0.0F, 0.0F, -1.0F, 2, 1, 2);
     this.l3.setRotationPoint(3.0F, 15.0F, 3.0F);

     this.l3.mirror = true;
     setRotation(this.l3, 0.0F, 0.0F, 0.0F);
     this.l2 = new ModelRenderer(this, 20, 13);
     this.l2.addBox(0.0F, 0.0F, -1.0F, 2, 1, 2);
     this.l2.setRotationPoint(3.0F, 15.0F, 0.0F);

     this.l2.mirror = true;
     setRotation(this.l2, 0.0F, 0.0F, 0.0F);
     this.l1 = new ModelRenderer(this, 20, 13);
     this.l1.addBox(0.0F, 0.0F, -1.0F, 2, 1, 2);
     this.l1.setRotationPoint(3.0F, 15.0F, -3.0F);

     this.l1.mirror = true;
     setRotation(this.l1, 0.0F, 0.0F, 0.0F);
     this.f3 = new ModelRenderer(this, 20, 16);
     this.f3.addBox(0.0F, 0.0F, -1.0F, 2, 2, 2);
     this.f3.setRotationPoint(3.5F, 16.0F, 3.0F);

     this.f3.mirror = true;
     setRotation(this.f3, 0.0F, 0.0F, 0.0F);
     this.f2 = new ModelRenderer(this, 20, 16);
     this.f2.addBox(0.0F, 0.0F, -1.0F, 2, 2, 2);
     this.f2.setRotationPoint(3.5F, 16.0F, 0.0F);

     this.f2.mirror = true;
     setRotation(this.f2, 0.0F, 0.0F, 0.0F);
     this.f1 = new ModelRenderer(this, 20, 16);
     this.f1.addBox(0.0F, 0.0F, -1.0F, 2, 2, 2);
     this.f1.setRotationPoint(3.5F, 16.0F, -3.0F);

     this.f1.mirror = true;
     setRotation(this.f1, 0.0F, 0.0F, 0.0F);
     this.jaw = new ModelRenderer(this, 28, 8);
     this.jaw.addBox(-3.5F, 0.0F, -8.0F, 5, 1, 4);
     this.jaw.setRotationPoint(1.0F, 15.0F, 0.0F);

     this.jaw.mirror = true;
     setRotation(this.jaw, 0.122173F, 0.0F, 0.0F);
     this.b9 = new ModelRenderer(this, 0, 2);
     this.b9.addBox(-0.5F, -1.5F, -0.5F, 1, 2, 1);
     this.b9.setRotationPoint(0.0F, 11.0F, -1.0F);

     this.b9.mirror = true;
     setRotation(this.b9, 0.5235988F, 0.0F, 0.0F);
     this.head = new ModelRenderer(this, 28, 0);
     this.head.addBox(-3.5F, -3.5F, -8.0F, 5, 4, 4);
     this.head.setRotationPoint(1.0F, 15.0F, 0.0F);

     this.head.mirror = true;
     setRotation(this.head, 0.0F, 0.0F, 0.0F);
     this.b4 = new ModelRenderer(this, 0, 0);
     this.b4.addBox(1.0F, -0.5F, 2.5F, 1, 1, 1);
     this.b4.setRotationPoint(0.0F, 11.0F, 0.0F);

     this.b4.mirror = true;
     setRotation(this.b4, 0.0F, 0.0F, 0.0F);
     this.h1 = new ModelRenderer(this, 0, 2);
     this.h1.addBox(-0.5F, -2.0F, -0.5F, 1, 2, 1);
     this.h1.setRotationPoint(-1.5F, 12.0F, -7.0F);

     this.h1.mirror = true;
     setRotation(this.h1, 0.5235988F, 0.3490659F, 0.0F);
     this.h2 = new ModelRenderer(this, 0, 2);
     this.h2.addBox(-0.5F, -2.0F, -0.5F, 1, 2, 1);
     this.h2.setRotationPoint(1.5F, 12.0F, -7.0F);

     this.h2.mirror = true;
     setRotation(this.h2, 0.5235988F, -0.3490659F, 0.0F);
     this.body = new ModelRenderer(this, 0, 0);
     this.body.addBox(-4.0F, -4.0F, -4.0F, 6, 5, 8);
     this.body.setRotationPoint(1.0F, 15.0F, 0.0F);

     this.body.mirror = true;
     setRotation(this.body, 0.0F, 0.0F, 0.0F);
     this.t21 = new ModelRenderer(this, 0, 0);
     this.t21.addBox(0.5F, 3.5F, 4.0F, 1, 1, 1);
     this.t21.setRotationPoint(0.0F, 11.5F, 4.0F);

     this.t21.mirror = true;
     setRotation(this.t21, -0.3316126F, 0.0F, 0.0F);
     this.tail = new ModelRenderer(this, 0, 13);
     this.tail.addBox(-2.0F, 0.0F, 0.0F, 4, 4, 6);
     this.tail.setRotationPoint(0.0F, 11.5F, 4.0F);

     this.tail.mirror = true;
     setRotation(this.tail, -0.3316126F, 0.0F, 0.0F);
     this.t22 = new ModelRenderer(this, 0, 0);
     this.t22.addBox(-1.5F, 3.5F, 4.0F, 1, 1, 1);
     this.t22.setRotationPoint(0.0F, 11.5F, 4.0F);

     this.t22.mirror = true;
     setRotation(this.t22, -0.3316126F, 0.0F, 0.0F);
     this.t20 = new ModelRenderer(this, 0, 0);
     this.t20.addBox(-1.5F, 3.5F, 2.0F, 1, 1, 1);
     this.t20.setRotationPoint(0.0F, 11.5F, 4.0F);

     this.t20.mirror = true;
     setRotation(this.t20, -0.3316126F, 0.0F, 0.0F);
     this.t19 = new ModelRenderer(this, 0, 0);
     this.t19.addBox(0.5F, 3.5F, 2.0F, 1, 1, 1);
     this.t19.setRotationPoint(0.0F, 11.5F, 4.0F);

     this.t19.mirror = true;
     setRotation(this.t19, -0.3316126F, 0.0F, 0.0F);
     this.t6 = new ModelRenderer(this, 0, 0);
     this.t6.addBox(1.5F, 2.5F, 4.0F, 1, 1, 1);
     this.t6.setRotationPoint(0.0F, 11.5F, 4.0F);

     this.t6.mirror = true;
     setRotation(this.t6, -0.3316126F, 0.0F, 0.0F);
     this.t11 = new ModelRenderer(this, 0, 0);
     this.t11.addBox(0.5F, -0.5F, 4.0F, 1, 1, 1);
     this.t11.setRotationPoint(0.0F, 11.5F, 4.0F);

     this.t11.mirror = true;
     setRotation(this.t11, -0.3316126F, 0.0F, 0.0F);
     this.t9 = new ModelRenderer(this, 0, 0);
     this.t9.addBox(0.5F, -0.5F, 2.0F, 1, 1, 1);
     this.t9.setRotationPoint(0.0F, 11.5F, 4.0F);

     this.t9.mirror = true;
     setRotation(this.t9, -0.3316126F, 0.0F, 0.0F);
     this.t4 = new ModelRenderer(this, 0, 0);
     this.t4.addBox(1.5F, 2.5F, 2.0F, 1, 1, 1);
     this.t4.setRotationPoint(0.0F, 11.5F, 4.0F);

     this.t4.mirror = true;
     setRotation(this.t4, -0.3316126F, 0.0F, 0.0F);
     this.t2 = new ModelRenderer(this, 0, 0);
     this.t2.addBox(1.5F, 2.5F, 0.0F, 1, 1, 1);
     this.t2.setRotationPoint(0.0F, 11.5F, 4.0F);

     this.t2.mirror = true;
     setRotation(this.t2, -0.3316126F, 0.0F, 0.0F);
     this.t7 = new ModelRenderer(this, 0, 0);
     this.t7.addBox(0.5F, -0.5F, 0.0F, 1, 1, 1);
     this.t7.setRotationPoint(0.0F, 11.5F, 4.0F);

     this.t7.mirror = true;
     setRotation(this.t7, -0.3316126F, 0.0F, 0.0F);
     this.t12 = new ModelRenderer(this, 0, 0);
     this.t12.addBox(-1.5F, -0.5F, 4.0F, 1, 1, 1);
     this.t12.setRotationPoint(0.0F, 11.5F, 4.0F);

     this.t12.mirror = true;
     setRotation(this.t12, -0.3316126F, 0.0F, 0.0F);
     this.t10 = new ModelRenderer(this, 0, 0);
     this.t10.addBox(-1.5F, -0.5F, 2.0F, 1, 1, 1);
     this.t10.setRotationPoint(0.0F, 11.5F, 4.0F);

     this.t10.mirror = true;
     setRotation(this.t10, -0.3316126F, 0.0F, 0.0F);
     this.t8 = new ModelRenderer(this, 0, 0);
     this.t8.addBox(-1.5F, -0.5F, 0.0F, 1, 1, 1);
     this.t8.setRotationPoint(0.0F, 11.5F, 4.0F);

     this.t8.mirror = true;
     setRotation(this.t8, -0.3316126F, 0.0F, 0.0F);
     this.t5 = new ModelRenderer(this, 0, 0);
     this.t5.addBox(1.5F, 0.5F, 4.0F, 1, 1, 1);
     this.t5.setRotationPoint(0.0F, 11.5F, 4.0F);

     this.t5.mirror = true;
     setRotation(this.t5, -0.3316126F, 0.0F, 0.0F);
     this.t3 = new ModelRenderer(this, 0, 0);
     this.t3.addBox(1.5F, 0.5F, 2.0F, 1, 1, 1);
     this.t3.setRotationPoint(0.0F, 11.5F, 4.0F);

     this.t3.mirror = true;
     setRotation(this.t3, -0.3316126F, 0.0F, 0.0F);
     this.t1 = new ModelRenderer(this, 0, 0);
     this.t1.addBox(1.5F, 0.5F, 0.0F, 1, 1, 1);
     this.t1.setRotationPoint(0.0F, 11.5F, 4.0F);

     this.t1.mirror = true;
     setRotation(this.t1, -0.3316126F, 0.0F, 0.0F);
     this.t18 = new ModelRenderer(this, 0, 0);
     this.t18.addBox(-2.5F, 2.5F, 4.0F, 1, 1, 1);
     this.t18.setRotationPoint(0.0F, 11.5F, 4.0F);

     this.t18.mirror = true;
     setRotation(this.t18, -0.3316126F, 0.0F, 0.0F);
     this.t16 = new ModelRenderer(this, 0, 0);
     this.t16.addBox(-2.5F, 2.5F, 2.0F, 1, 1, 1);
     this.t16.setRotationPoint(0.0F, 11.5F, 4.0F);

     this.t16.mirror = true;
     setRotation(this.t16, -0.3316126F, 0.0F, 0.0F);
     this.t14 = new ModelRenderer(this, 0, 0);
     this.t14.addBox(-2.5F, 2.5F, 0.0F, 1, 1, 1);
     this.t14.setRotationPoint(0.0F, 11.5F, 4.0F);

     this.t14.mirror = true;
     setRotation(this.t14, -0.3316126F, 0.0F, 0.0F);
     this.t13 = new ModelRenderer(this, 0, 0);
     this.t13.addBox(-2.5F, 0.5F, 0.0F, 1, 1, 1);
     this.t13.setRotationPoint(0.0F, 11.5F, 4.0F);

     this.t13.mirror = true;
     setRotation(this.t13, -0.3316126F, 0.0F, 0.0F);
     this.t15 = new ModelRenderer(this, 0, 0);
     this.t15.addBox(-2.5F, 0.5F, 2.0F, 1, 1, 1);
     this.t15.setRotationPoint(0.0F, 11.5F, 4.0F);

     this.t15.mirror = true;
     setRotation(this.t15, -0.3316126F, 0.0F, 0.0F);
     this.t17 = new ModelRenderer(this, 0, 0);
     this.t17.addBox(-2.5F, 0.5F, 4.0F, 1, 1, 1);
     this.t17.setRotationPoint(0.0F, 11.5F, 4.0F);

     this.t17.mirror = true;
     setRotation(this.t17, -0.3316126F, 0.0F, 0.0F);
     this.b1 = new ModelRenderer(this, 0, 0);
     this.b1.addBox(1.0F, -0.5F, -3.5F, 1, 1, 1);
     this.b1.setRotationPoint(0.0F, 11.0F, 0.0F);

     this.b1.mirror = true;
     setRotation(this.b1, 0.0F, 0.0F, 0.0F);
     this.b2 = new ModelRenderer(this, 0, 0);
     this.b2.addBox(1.5F, -0.5F, -1.5F, 1, 1, 1);
     this.b2.setRotationPoint(0.0F, 11.0F, 0.0F);

     this.b2.mirror = true;
     setRotation(this.b2, 0.0F, 0.0F, 0.0F);
     this.b3 = new ModelRenderer(this, 0, 0);
     this.b3.addBox(1.5F, -0.5F, 0.5F, 1, 1, 1);
     this.b3.setRotationPoint(0.0F, 11.0F, 0.0F);

     this.b3.mirror = true;
     setRotation(this.b3, 0.0F, 0.0F, 0.0F);
     this.b8 = new ModelRenderer(this, 0, 0);
     this.b8.addBox(-2.0F, -0.5F, 2.5F, 1, 1, 1);
     this.b8.setRotationPoint(0.0F, 11.0F, 0.0F);

     this.b8.mirror = true;
     setRotation(this.b8, 0.0F, 0.0F, 0.0F);
     this.b7 = new ModelRenderer(this, 0, 0);
     this.b7.addBox(-2.5F, -0.5F, 0.5F, 1, 1, 1);
     this.b7.setRotationPoint(0.0F, 11.0F, 0.0F);

     this.b7.mirror = true;
     setRotation(this.b7, 0.0F, 0.0F, 0.0F);
     this.b6 = new ModelRenderer(this, 0, 0);
     this.b6.addBox(-2.5F, -0.5F, -1.5F, 1, 1, 1);
     this.b6.setRotationPoint(0.0F, 11.0F, 0.0F);

     this.b6.mirror = true;
     setRotation(this.b6, 0.0F, 0.0F, 0.0F);
     this.b5 = new ModelRenderer(this, 0, 0);
     this.b5.addBox(-1.966667F, -0.5F, -3.5F, 1, 1, 1);
     this.b5.setRotationPoint(0.0F, 11.0F, 0.0F);

     this.b5.mirror = true;
     setRotation(this.b5, 0.0F, 0.0F, 0.0F);

     this.f6.rotationPointY += 6.0F;
     this.b10.rotationPointY += 6.0F;
     this.l6.rotationPointY += 6.0F;
     this.l4.rotationPointY += 6.0F;
     this.f4.rotationPointY += 6.0F;
     this.l5.rotationPointY += 6.0F;
     this.f5.rotationPointY += 6.0F;
     this.l3.rotationPointY += 6.0F;
     this.l2.rotationPointY += 6.0F;
     this.l1.rotationPointY += 6.0F;
     this.f3.rotationPointY += 6.0F;
     this.f2.rotationPointY += 6.0F;
     this.f1.rotationPointY += 6.0F;
     this.jaw.rotationPointY += 6.0F;
     this.b9.rotationPointY += 6.0F;
     this.head.rotationPointY += 6.0F;
     this.b4.rotationPointY += 6.0F;
     this.h1.rotationPointY += 6.0F;
     this.h2.rotationPointY += 6.0F;
     this.body.rotationPointY += 6.0F;
     this.t21.rotationPointY += 6.0F;
     this.tail.rotationPointY += 6.0F;
     this.t22.rotationPointY += 6.0F;
     this.t20.rotationPointY += 6.0F;
     this.t19.rotationPointY += 6.0F;
     this.t6.rotationPointY += 6.0F;
     this.t11.rotationPointY += 6.0F;
     this.t9.rotationPointY += 6.0F;
     this.t4.rotationPointY += 6.0F;
     this.t2.rotationPointY += 6.0F;
     this.t7.rotationPointY += 6.0F;
     this.t12.rotationPointY += 6.0F;
     this.t10.rotationPointY += 6.0F;
     this.t8.rotationPointY += 6.0F;
     this.t5.rotationPointY += 6.0F;
     this.t3.rotationPointY += 6.0F;
     this.t1.rotationPointY += 6.0F;
     this.t18.rotationPointY += 6.0F;
     this.t16.rotationPointY += 6.0F;
     this.t14.rotationPointY += 6.0F;
     this.t13.rotationPointY += 6.0F;
     this.t15.rotationPointY += 6.0F;
     this.t17.rotationPointY += 6.0F;
     this.b1.rotationPointY += 6.0F;
     this.b2.rotationPointY += 6.0F;
     this.b3.rotationPointY += 6.0F;
     this.b8.rotationPointY += 6.0F;
     this.b7.rotationPointY += 6.0F;
     this.b6.rotationPointY += 6.0F;
     this.b5.rotationPointY += 6.0F;
   }

   @Override public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay,
        float red, float green, float blue, float alpha) {
        this.f6.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.b10.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.l6.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.l4.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.f4.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.l5.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.f5.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.l3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.l2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.l1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.f3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.f2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.f1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.jaw.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.b9.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.head.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.b4.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.h1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.h2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.body.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.t21.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.tail.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.t22.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.t20.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.t19.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.t6.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.t11.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.t9.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.t4.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.t2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.t7.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.t12.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.t10.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.t8.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.t5.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.t3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.t1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.t18.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.t16.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.t14.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.t13.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.t15.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.t17.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.b1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.b2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.b3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.b8.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.b7.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.b6.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.b5.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }

   private void setRotation(ModelRenderer model, float x, float y, float z) {
     model.rotateAngleX = x;
     model.rotateAngleY = y;
     model.rotateAngleZ = z;
   }

   @Override public void setRotationAngles(Entity entity, float limbSwing, float limbSwingAmount,
        float ageInTicks, float netHeadYaw, float headPitch) {
        float walk = MathHelper.sin(ageInTicks * 3.1F * this.wingspeed)
            * (float)Math.PI * 0.30F * limbSwingAmount;
        this.f1.rotateAngleX = walk; this.f4.rotateAngleX = -walk;
        this.f2.rotateAngleX = -walk; this.f5.rotateAngleX = walk;
        this.f3.rotateAngleX = walk; this.f6.rotateAngleX = -walk;
        float wing = MathHelper.sin(ageInTicks * 0.4F * this.wingspeed)
            * (float)Math.PI * 0.20F;
        this.b9.rotateAngleZ = wing;
        this.b10.rotateAngleZ = -wing;
        this.jaw.rotateAngleX = 0.18F + MathHelper.sin(ageInTicks * 0.2F * this.wingspeed)
            * (float)Math.PI * 0.04F;
        this.h1.rotateAngleX = 0.52F + MathHelper.sin(ageInTicks * 0.4F * this.wingspeed) * 0.47F;
        this.h1.rotateAngleY = -0.3F + MathHelper.sin(ageInTicks * 0.43F * this.wingspeed) * 0.47F;
        this.h2.rotateAngleX = 0.52F + MathHelper.sin(ageInTicks * 0.46F * this.wingspeed) * 0.47F;
        this.h2.rotateAngleY = 0.3F + MathHelper.sin(ageInTicks * 0.49F * this.wingspeed) * 0.47F;
        float tailWave = -0.2F + MathHelper.sin(ageInTicks * 0.1F * this.wingspeed) * 0.31F;
        this.tail.rotateAngleX = tailWave; this.t1.rotateAngleX = tailWave; this.t2.rotateAngleX = tailWave;
        this.t3.rotateAngleX = tailWave; this.t4.rotateAngleX = tailWave; this.t5.rotateAngleX = tailWave;
        this.t6.rotateAngleX = tailWave; this.t7.rotateAngleX = tailWave; this.t8.rotateAngleX = tailWave;
        this.t9.rotateAngleX = tailWave; this.t10.rotateAngleX = tailWave; this.t11.rotateAngleX = tailWave;
        this.t12.rotateAngleX = tailWave; this.t13.rotateAngleX = tailWave; this.t14.rotateAngleX = tailWave;
        this.t15.rotateAngleX = tailWave; this.t16.rotateAngleX = tailWave; this.t17.rotateAngleX = tailWave;
        this.t18.rotateAngleX = tailWave; this.t19.rotateAngleX = tailWave; this.t20.rotateAngleX = tailWave;
        this.t21.rotateAngleX = tailWave; this.t22.rotateAngleX = tailWave;
    }
 }
}