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
import net.mcreator.xillysorespawn.entity.FlounderEntity;
import net.mcreator.xillysorespawn.entity.OreSpawnLogic;

@OnlyIn(Dist.CLIENT)
public class FlounderRenderer {
    private static final String TEXTURE = "xillys_orespawn:textures/entities/floundertexture.png";
    public static class ModelRegisterHandler {
        @SubscribeEvent @OnlyIn(Dist.CLIENT)
        public void registerModels(ModelRegistryEvent event) {
            RenderingRegistry.registerEntityRenderingHandler(FlounderEntity.entity, manager ->
                new MobRenderer(manager, new ModelFlounder(), 0.1F) {
                    @Override public ResourceLocation getEntityTexture(Entity entity) { return new ResourceLocation(TEXTURE); }
                    @Override protected void preRenderCallback(LivingEntity entity, MatrixStack stack, float partialTick) {
                        float modelScale = 1.0F;
                        if (entity.isChild()) modelScale *= 0.5F;
                        stack.scale(modelScale, modelScale, modelScale);
                    }
                });
        }
    }

public static class ModelFlounder extends EntityModel<Entity>
 {
   private final ModelRenderer body;
   private final ModelRenderer head;
   private final ModelRenderer tail1;
   private final ModelRenderer tail2;
   private final ModelRenderer rfin;
   private final ModelRenderer lfin;

   public ModelFlounder() {
     this.textureWidth = 64;
     this.textureHeight = 32;

     this.body = new ModelRenderer(this, 0, 16);
     this.body.addBox(-4.0F, 0.0F, -5.0F, 8, 1, 12);
     this.body.setRotationPoint(0.0F, 22.0F, 0.0F);

     this.body.mirror = true;
     setRotation(this.body, 0.0F, 0.0F, 0.0F);
     this.head = new ModelRenderer(this, 0, 5);
     this.head.addBox(-2.0F, 0.0F, 0.0F, 4, 1, 2);
     this.head.setRotationPoint(0.0F, 22.0F, -7.0F);

     this.head.mirror = true;
     setRotation(this.head, 0.0F, 0.0F, 0.0F);
     this.tail1 = new ModelRenderer(this, 30, 0);
     this.tail1.addBox(-2.0F, 0.0F, 0.0F, 4, 1, 2);
     this.tail1.setRotationPoint(0.0F, 22.0F, 7.0F);

     this.tail1.mirror = true;
     setRotation(this.tail1, 0.0F, 0.0F, 0.0F);
     this.tail2 = new ModelRenderer(this, 30, 4);
     this.tail2.addBox(-3.0F, 0.0F, 2.0F, 6, 1, 3);
     this.tail2.setRotationPoint(0.0F, 22.0F, 7.0F);

     this.tail2.mirror = true;
     setRotation(this.tail2, 0.0F, 0.0F, 0.0F);
     this.rfin = new ModelRenderer(this, 12, 0);
     this.rfin.addBox(-3.0F, 0.0F, 0.0F, 3, 1, 2);
     this.rfin.setRotationPoint(-4.0F, 22.0F, -2.0F);

     this.rfin.mirror = true;
     setRotation(this.rfin, 0.0F, 0.0F, 0.0F);
     this.lfin = new ModelRenderer(this, 0, 0);
     this.lfin.addBox(0.0F, 0.0F, 0.0F, 3, 1, 2);
     this.lfin.setRotationPoint(4.0F, 22.0F, -2.0F);

     this.lfin.mirror = true;
     setRotation(this.lfin, 0.0F, 0.0F, 0.0F);
   }

   public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay,
        float red, float green, float blue, float alpha) {
        body.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        head.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        tail1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        tail2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rfin.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        lfin.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
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
this.head.setRotationPoint(0.0F, 22.0F, -7.0F);
this.tail1.setRotationPoint(0.0F, 22.0F, 7.0F);
this.tail2.setRotationPoint(0.0F, 22.0F, 7.0F);
this.rfin.setRotationPoint(-4.0F, 22.0F, -2.0F);
this.lfin.setRotationPoint(4.0F, 22.0F, -2.0F);
setRotation(this.body, 0.0F, 0.0F, 0.0F);
setRotation(this.head, 0.0F, 0.0F, 0.0F);
setRotation(this.tail1, 0.0F, 0.0F, 0.0F);
setRotation(this.tail2, 0.0F, 0.0F, 0.0F);
setRotation(this.rfin, 0.0F, 0.0F, 0.0F);
setRotation(this.lfin, 0.0F, 0.0F, 0.0F);

     float newangle, newangle2;

     if (limbSwingAmount > 0.1D) {
       newangle = MathHelper.cos(ageInTicks * 1.3F) * 3.1415927F * 0.25F * limbSwingAmount;
       newangle2 = MathHelper.cos(ageInTicks * 1.7F) * 3.1415927F * 0.25F * limbSwingAmount;
     } else {
       newangle = 0.0F;
       newangle2 = 0.0F;
     } 
     this.lfin.rotateAngleZ = newangle;
     this.rfin.rotateAngleZ = newangle2;
     
     if (limbSwingAmount > 0.1D) {
       newangle = MathHelper.cos(ageInTicks * 1.2F) * 3.1415927F * 0.25F * limbSwingAmount;
     } else {
       newangle = MathHelper.cos(ageInTicks * 0.7F) * 3.1415927F * 0.05F;
     } 
     this.tail2.rotateAngleX = newangle;
    }
 }
}