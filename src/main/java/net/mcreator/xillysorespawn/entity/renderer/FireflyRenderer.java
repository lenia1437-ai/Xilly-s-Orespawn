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
import net.mcreator.xillysorespawn.entity.FireflyEntity;
import net.mcreator.xillysorespawn.entity.OreSpawnLogic;

@OnlyIn(Dist.CLIENT)
public class FireflyRenderer {
    private static final String TEXTURE = "xillys_orespawn:textures/entities/fireflytexture.png";
    public static class ModelRegisterHandler {
        @SubscribeEvent @OnlyIn(Dist.CLIENT)
        public void registerModels(ModelRegistryEvent event) {
            RenderingRegistry.registerEntityRenderingHandler(FireflyEntity.entity, manager ->
                new MobRenderer(manager, new ModelFirefly(2.5F), 0.15F) {
                    @Override public ResourceLocation getEntityTexture(Entity entity) { return new ResourceLocation(TEXTURE); }
                    @Override protected void preRenderCallback(LivingEntity entity, MatrixStack stack, float partialTick) {
                        float modelScale = 0.75F;
                        stack.scale(modelScale, modelScale, modelScale);
                    }
                });
        }
    }

public static class ModelFirefly extends EntityModel<Entity>
 {
   private float wingspeed = 1.0F;
   private int blinkLight = 15728640;

   private final ModelRenderer body;
   private final ModelRenderer wing_left;
   private final ModelRenderer wing_right;
   private final ModelRenderer head;
   private final ModelRenderer mouth;
   private final ModelRenderer eye_left;
   private final ModelRenderer eye_right;
   private final ModelRenderer front_leg_left_;
   private final ModelRenderer front_leg_right;
   private final ModelRenderer back_leg_left;
   private final ModelRenderer back_leg_right;
   private final ModelRenderer TailLight;

   public ModelFirefly(float f1) {
     this.textureWidth = 64;
     this.textureHeight = 128;
     this.wingspeed = f1;

     this.body = new ModelRenderer(this, 38, 12);
     this.body.addBox(-3.0F, -3.0F, -3.0F, 5, 5, 5);
     this.body.setRotationPoint(-1.0F, 9.0F, -1.0F);

     this.body.mirror = true;
     setRotation(this.body, 0.0F, 0.0F, 0.0F);
     this.wing_left = new ModelRenderer(this, 46, 0);
     this.wing_left.addBox(0.0F, -6.0F, 0.0F, 0, 6, 2);
     this.wing_left.setRotationPoint(1.0F, 6.0F, -2.0F);

     this.wing_left.mirror = true;
     setRotation(this.wing_left, 0.0F, 0.0174533F, 0.6981317F);
     this.wing_right = new ModelRenderer(this, 53, 0);
     this.wing_right.addBox(0.0F, -6.0F, 0.0F, 0, 6, 2);
     this.wing_right.setRotationPoint(-4.0F, 6.0F, -2.0F);

     this.wing_right.mirror = true;
     setRotation(this.wing_right, 0.0F, 0.0F, -0.6981317F);
     this.head = new ModelRenderer(this, 3, 14);
     this.head.addBox(0.0F, 0.0F, 0.0F, 3, 3, 3);
     this.head.setRotationPoint(-3.0F, 7.0F, -7.0F);

     this.head.mirror = true;
     setRotation(this.head, 0.2230717F, 0.0F, 0.0F);
     this.mouth = new ModelRenderer(this, 26, 15);
     this.mouth.addBox(0.0F, 0.0F, 0.0F, 1, 1, 3);
     this.mouth.setRotationPoint(-2.0F, 9.0F, -8.0F);

     this.mouth.mirror = true;
     setRotation(this.mouth, 0.2117115F, 0.0F, 0.0F);
     this.eye_left = new ModelRenderer(this, 18, 12);
     this.eye_left.addBox(0.0F, 0.0F, 0.0F, 1, 2, 2);
     this.eye_left.setRotationPoint(-1.0F, 6.5F, -6.0F);

     this.eye_left.mirror = true;
     setRotation(this.eye_left, 0.0174533F, 0.2602503F, -0.2230717F);
     this.eye_right = new ModelRenderer(this, 18, 18);
     this.eye_right.addBox(1.0F, -0.6F, -0.6F, 1, 2, 2);
     this.eye_right.setRotationPoint(-4.0F, 6.5F, -6.0F);

     this.eye_right.mirror = true;
     setRotation(this.eye_right, 0.0F, -0.2602503F, 0.2230717F);
     this.front_leg_left_ = new ModelRenderer(this, 32, 0);
     this.front_leg_left_.addBox(0.0F, 0.0F, 0.0F, 1, 5, 1);
     this.front_leg_left_.setRotationPoint(-1.0F, 10.0F, -3.0F);

     this.front_leg_left_.mirror = true;
     setRotation(this.front_leg_left_, -0.2792527F, 0.0F, -0.2792527F);
     this.front_leg_right = new ModelRenderer(this, 22, 0);
     this.front_leg_right.addBox(0.0F, 0.0F, 0.0F, 1, 5, 1);
     this.front_leg_right.setRotationPoint(-3.0F, 10.0F, -3.0F);

     this.front_leg_right.mirror = true;
     setRotation(this.front_leg_right, -0.2792527F, 0.0F, 0.2792527F);
     this.back_leg_left = new ModelRenderer(this, 11, 0);
     this.back_leg_left.addBox(0.0F, 0.0F, 0.0F, 1, 5, 1);
     this.back_leg_left.setRotationPoint(-1.0F, 10.0F, -1.0F);

     this.back_leg_left.mirror = true;
     setRotation(this.back_leg_left, 0.2792527F, 0.0F, -0.2792527F);
     this.back_leg_right = new ModelRenderer(this, 2, 0);
     this.back_leg_right.addBox(0.0F, 0.0F, 0.0F, 1, 5, 1);
     this.back_leg_right.setRotationPoint(-3.0F, 10.0F, -1.0F);

     this.back_leg_right.mirror = true;
     setRotation(this.back_leg_right, 0.2792527F, 0.0F, 0.2792527F);
     this.TailLight = new ModelRenderer(this, 10, 27);
     this.TailLight.addBox(0.0F, 0.0F, 0.0F, 3, 3, 4);
     this.TailLight.setRotationPoint(-3.0F, 6.0F, 1.0F);

     this.TailLight.mirror = true;
     setRotation(this.TailLight, 0.0F, 0.0F, 0.0F);
   }

   public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay,
        float red, float green, float blue, float alpha) {
        body.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        wing_left.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        wing_right.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        head.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        mouth.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        eye_left.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        eye_right.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        front_leg_left_.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        front_leg_right.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        back_leg_left.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        back_leg_right.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        TailLight.render(matrixStack, buffer, Math.max(packedLight, this.blinkLight), packedOverlay, red, green, blue, alpha);
    }

   private void setRotation(ModelRenderer model, float x, float y, float z) {
     model.rotateAngleX = x;
     model.rotateAngleY = y;
     model.rotateAngleZ = z;
   }

   public void setRotationAngles(Entity entity, float limbSwing, float limbSwingAmount,
        float ageInTicks, float netHeadYaw, float headPitch) {
// Reset shared model state before applying this frame's legacy animation.
this.body.setRotationPoint(-1.0F, 9.0F, -1.0F);
this.wing_left.setRotationPoint(1.0F, 6.0F, -2.0F);
this.wing_right.setRotationPoint(-4.0F, 6.0F, -2.0F);
this.head.setRotationPoint(-3.0F, 7.0F, -7.0F);
this.mouth.setRotationPoint(-2.0F, 9.0F, -8.0F);
this.eye_left.setRotationPoint(-1.0F, 6.5F, -6.0F);
this.eye_right.setRotationPoint(-4.0F, 6.5F, -6.0F);
this.front_leg_left_.setRotationPoint(-1.0F, 10.0F, -3.0F);
this.front_leg_right.setRotationPoint(-3.0F, 10.0F, -3.0F);
this.back_leg_left.setRotationPoint(-1.0F, 10.0F, -1.0F);
this.back_leg_right.setRotationPoint(-3.0F, 10.0F, -1.0F);
this.TailLight.setRotationPoint(-3.0F, 6.0F, 1.0F);
setRotation(this.body, 0.0F, 0.0F, 0.0F);
setRotation(this.wing_left, 0.0F, 0.0174533F, 0.6981317F);
setRotation(this.wing_right, 0.0F, 0.0F, -0.6981317F);
setRotation(this.head, 0.2230717F, 0.0F, 0.0F);
setRotation(this.mouth, 0.2117115F, 0.0F, 0.0F);
setRotation(this.eye_left, 0.0174533F, 0.2602503F, -0.2230717F);
setRotation(this.eye_right, 0.0F, -0.2602503F, 0.2230717F);
setRotation(this.front_leg_left_, -0.2792527F, 0.0F, -0.2792527F);
setRotation(this.front_leg_right, -0.2792527F, 0.0F, 0.2792527F);
setRotation(this.back_leg_left, 0.2792527F, 0.0F, -0.2792527F);
setRotation(this.back_leg_right, 0.2792527F, 0.0F, 0.2792527F);
setRotation(this.TailLight, 0.0F, 0.0F, 0.0F);

     FireflyEntity.CustomEntity fly = (FireflyEntity.CustomEntity) entity;

     float onoff = 0.0F;
     
     this.wing_left.rotateAngleZ = 1.11F + MathHelper.cos(ageInTicks * this.wingspeed) * 3.1415927F * 0.35F;
     this.wing_right.rotateAngleZ = -1.11F - MathHelper.cos(ageInTicks * this.wingspeed) * 3.1415927F * 0.35F;











     onoff = fly.getBlink();
     this.blinkLight = onoff > 1.0F ? 15728880 : 15728640;
    }
 }
}