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
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.MobRenderer;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.mcreator.xillysorespawn.entity.GhostEntity;
import net.mcreator.xillysorespawn.entity.OreSpawnLogic;

@OnlyIn(Dist.CLIENT)
public class GhostRenderer {
    private static final String TEXTURE = "xillys_orespawn:textures/entities/ghosttexture.png";
    public static class ModelRegisterHandler {
        @SubscribeEvent @OnlyIn(Dist.CLIENT)
        public void registerModels(ModelRegistryEvent event) {
            RenderingRegistry.registerEntityRenderingHandler(GhostEntity.entity, manager ->
                new MobRenderer(manager, new ModelGhost(), 0.0F) {
                    @Override public ResourceLocation getEntityTexture(Entity entity) { return new ResourceLocation(TEXTURE); }
                    @Override protected RenderType func_230496_a_(LivingEntity entity, boolean visible, boolean translucent, boolean outline) { return RenderType.getEntityTranslucent(getEntityTexture(entity)); }
                    @Override protected void preRenderCallback(LivingEntity entity, MatrixStack stack, float partialTick) {
                        float modelScale = 0.65F;
                        stack.scale(modelScale, modelScale, modelScale);
                    }
                });
        }
    }

public static class ModelGhost extends EntityModel<Entity>
 {
   private final ModelRenderer HeadAndBody;
   private final ModelRenderer LArm;
   private final ModelRenderer RArm;

   public ModelGhost() {
     this.textureWidth = 64;
     this.textureHeight = 64;

     this.HeadAndBody = new ModelRenderer(this, 0, 0);
     this.HeadAndBody.addBox(-3.0F, 0.0F, -3.0F, 6, 21, 6);
     this.HeadAndBody.setRotationPoint(0.0F, 0.0F, 0.0F);

     this.HeadAndBody.mirror = true;
     setRotation(this.HeadAndBody, 0.0F, 0.0F, 0.0F);
     this.LArm = new ModelRenderer(this, 34, 0);
     this.LArm.addBox(-1.0F, -1.0F, -1.0F, 2, 11, 2);
     this.LArm.setRotationPoint(3.0F, 6.0F, 0.0F);

     this.LArm.mirror = true;
     setRotation(this.LArm, 0.0F, 0.0F, -0.3316126F);
     this.RArm = new ModelRenderer(this, 25, 0);
     this.RArm.addBox(-1.0F, -1.0F, -1.0F, 2, 11, 2);
     this.RArm.setRotationPoint(-3.0F, 6.0F, 0.0F);

     this.RArm.mirror = true;
     setRotation(this.RArm, 0.0F, 0.0F, 0.3316126F);
   }

   public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay,
        float red, float green, float blue, float alpha) {
        red *= 0.75F; green *= 0.75F; blue *= 0.75F; alpha *= 0.25F;
        HeadAndBody.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        LArm.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        RArm.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }

   private void setRotation(ModelRenderer model, float x, float y, float z) {
     model.rotateAngleX = x;
     model.rotateAngleY = y;
     model.rotateAngleZ = z;
   }

   public void setRotationAngles(Entity entity, float limbSwing, float limbSwingAmount,
        float ageInTicks, float netHeadYaw, float headPitch) {
// Reset shared model state before applying this frame's legacy animation.
this.HeadAndBody.setRotationPoint(0.0F, 0.0F, 0.0F);
this.LArm.setRotationPoint(3.0F, 6.0F, 0.0F);
this.RArm.setRotationPoint(-3.0F, 6.0F, 0.0F);
setRotation(this.HeadAndBody, 0.0F, 0.0F, 0.0F);
setRotation(this.LArm, 0.0F, 0.0F, -0.3316126F);
setRotation(this.RArm, 0.0F, 0.0F, 0.3316126F);

     this.LArm.rotateAngleZ = -0.33F + MathHelper.cos(ageInTicks * 0.3F) * 3.1415927F * 0.05F;
     this.RArm.rotateAngleZ = 0.33F + MathHelper.cos(ageInTicks * 0.32F) * 3.1415927F * 0.05F;
     this.LArm.rotateAngleX = -0.33F + MathHelper.cos(ageInTicks * 0.34F) * 3.1415927F * 0.05F;
     this.RArm.rotateAngleX = 0.33F + MathHelper.cos(ageInTicks * 0.36F) * 3.1415927F * 0.05F;
    }
 }
}