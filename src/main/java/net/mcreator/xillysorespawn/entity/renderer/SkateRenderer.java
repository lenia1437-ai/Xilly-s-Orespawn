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
import net.mcreator.xillysorespawn.entity.SkateEntity;
import net.mcreator.xillysorespawn.entity.OreSpawnLogic;

@OnlyIn(Dist.CLIENT)
public class SkateRenderer {
    private static final String TEXTURE = "xillys_orespawn:textures/entities/skatetexture.png";
    public static class ModelRegisterHandler {
        @SubscribeEvent @OnlyIn(Dist.CLIENT)
        public void registerModels(ModelRegistryEvent event) {
            RenderingRegistry.registerEntityRenderingHandler(SkateEntity.entity, manager ->
                new MobRenderer(manager, new ModelSkate(1.0F), 0.075F) {
                    @Override public ResourceLocation getEntityTexture(Entity entity) { return new ResourceLocation(TEXTURE); }
                    @Override protected void preRenderCallback(LivingEntity entity, MatrixStack stack, float partialTick) {
                        float modelScale = 0.75F;
                        stack.scale(modelScale, modelScale, modelScale);
                    }
                });
        }
    }

public static class ModelSkate extends EntityModel<Entity>
 {
   private float wingspeed = 1.0F;

   private final ModelRenderer body;

   private final ModelRenderer tail1;
   private final ModelRenderer Shape1;

   public ModelSkate(float f1) {
     this.wingspeed = f1;

     this.textureWidth = 64;
     this.textureHeight = 32;

     this.body = new ModelRenderer(this, 0, 13);
     this.body.addBox(-3.0F, 0.0F, -3.0F, 6, 1, 6);
     this.body.setRotationPoint(0.0F, 22.0F, 0.0F);

     this.body.mirror = true;
     setRotation(this.body, 0.0F, 0.7853982F, 0.0F);
     this.tail1 = new ModelRenderer(this, 0, 0);
     this.tail1.addBox(-0.5F, 0.0F, 0.0F, 1, 1, 11);
     this.tail1.setRotationPoint(0.0F, 22.0F, 3.0F);

     this.tail1.mirror = true;
     setRotation(this.tail1, 0.0F, 0.0F, 0.0F);
     this.Shape1 = new ModelRenderer(this, 0, 21);
     this.Shape1.addBox(-0.5F, 0.0F, 0.0F, 1, 1, 4);
     this.Shape1.setRotationPoint(0.0F, 22.0F, 5.0F);

     this.Shape1.mirror = true;
     setRotation(this.Shape1, 0.7853982F, 0.0F, 0.0F);
   }

   public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay,
        float red, float green, float blue, float alpha) {
        body.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        tail1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Shape1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }

   private void setRotation(ModelRenderer model, float x, float y, float z) {
     model.rotateAngleX = x;
     model.rotateAngleY = y;
     model.rotateAngleZ = z;
   }

   public void setRotationAngles(Entity entity, float limbSwing, float limbSwingAmount,
        float ageInTicks, float netHeadYaw, float headPitch) {
// Reset shared model state before applying this frame's legacy animation.
this.body.setRotationPoint(0.0F, 22.0F, 0.0F);
this.tail1.setRotationPoint(0.0F, 22.0F, 3.0F);
this.Shape1.setRotationPoint(0.0F, 22.0F, 5.0F);
setRotation(this.body, 0.0F, 0.7853982F, 0.0F);
setRotation(this.tail1, 0.0F, 0.0F, 0.0F);
setRotation(this.Shape1, 0.7853982F, 0.0F, 0.0F);

     SkateEntity.CustomEntity e = (SkateEntity.CustomEntity) entity;

     float newangle = 0.0F;
     
     if (limbSwingAmount > 0.1D) {
       newangle = MathHelper.cos(ageInTicks * 1.2F) * 3.1415927F * 0.15F * limbSwingAmount;
     } else {
       newangle = MathHelper.cos(ageInTicks * 0.4F) * 3.1415927F * 0.05F;
     } 
     this.Shape1.rotateAngleX = 0.785F + newangle;
    }
 }
}