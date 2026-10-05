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
import net.mcreator.xillysorespawn.entity.LeafMonsterEntity;
import net.mcreator.xillysorespawn.entity.OreSpawnLogic;

@OnlyIn(Dist.CLIENT)
public class LeafMonsterRenderer {
    private static final String TEXTURE = "xillys_orespawn:textures/entities/leafmonstertexture.png";
    public static class ModelRegisterHandler {
        @SubscribeEvent @OnlyIn(Dist.CLIENT)
        public void registerModels(ModelRegistryEvent event) {
            RenderingRegistry.registerEntityRenderingHandler(LeafMonsterEntity.entity, manager ->
                new MobRenderer(manager, new ModelLeafMonster(), 0.65F) {
                    @Override public ResourceLocation getEntityTexture(Entity entity) { return new ResourceLocation(TEXTURE); }
                    @Override protected void preRenderCallback(LivingEntity entity, MatrixStack stack, float partialTick) {
                        float modelScale = 1.0F;
                        stack.scale(modelScale, modelScale, modelScale);
                    }
                });
        }
    }

public static class ModelLeafMonster extends EntityModel<Entity>
 {
   private final ModelRenderer body;
   private final ModelRenderer larm;
   private final ModelRenderer rarm;
   private final ModelRenderer lleg;
   private final ModelRenderer rleg;

   public ModelLeafMonster() {
     this.textureWidth = 128;
     this.textureHeight = 128;

     this.body = new ModelRenderer(this, 32, 32);
     this.body.addBox(-8.0F, -8.0F, -8.0F, 16, 16, 16);
     this.body.setRotationPoint(0.0F, 0.0F, 0.0F);

     this.body.mirror = true;
     setRotation(this.body, 0.0F, 0.0F, 0.0F);
     this.larm = new ModelRenderer(this, 64, 0);
     this.larm.addBox(0.0F, -16.0F, -8.0F, 16, 16, 16);
     this.larm.setRotationPoint(8.0F, -8.0F, 0.0F);

     this.larm.mirror = true;
     setRotation(this.larm, 0.0F, 0.0F, 0.0F);
     this.rarm = new ModelRenderer(this, 0, 0);
     this.rarm.addBox(-16.0F, -16.0F, -8.0F, 16, 16, 16);
     this.rarm.setRotationPoint(-8.0F, -8.0F, 0.0F);

     this.rarm.mirror = true;
     setRotation(this.rarm, 0.0F, 0.0F, 0.0F);
     this.lleg = new ModelRenderer(this, 64, 64);
     this.lleg.addBox(0.0F, 0.0F, -8.0F, 16, 16, 16);
     this.lleg.setRotationPoint(8.0F, 8.0F, 0.0F);

     this.lleg.mirror = true;
     setRotation(this.lleg, 0.0F, 0.0F, 0.0F);
     this.rleg = new ModelRenderer(this, 0, 64);
     this.rleg.addBox(-16.0F, 0.0F, -8.0F, 16, 16, 16);
     this.rleg.setRotationPoint(-8.0F, 8.0F, 0.0F);

     this.rleg.mirror = true;
     setRotation(this.rleg, 0.0F, 0.0F, 0.0F);
   }

   public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay,
        float red, float green, float blue, float alpha) {
        body.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        larm.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rarm.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        lleg.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rleg.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }

   private void setRotation(ModelRenderer model, float x, float y, float z) {
     model.rotateAngleX = x;
     model.rotateAngleY = y;
     model.rotateAngleZ = z;
   }

   public void setRotationAngles(Entity entity, float limbSwing, float limbSwingAmount,
        float ageInTicks, float netHeadYaw, float headPitch) {
// Reset shared model state before applying this frame's legacy animation.
this.body.setRotationPoint(0.0F, 0.0F, 0.0F);
this.larm.setRotationPoint(8.0F, -8.0F, 0.0F);
this.rarm.setRotationPoint(-8.0F, -8.0F, 0.0F);
this.lleg.setRotationPoint(8.0F, 8.0F, 0.0F);
this.rleg.setRotationPoint(-8.0F, 8.0F, 0.0F);
setRotation(this.body, 0.0F, 0.0F, 0.0F);
setRotation(this.larm, 0.0F, 0.0F, 0.0F);
setRotation(this.rarm, 0.0F, 0.0F, 0.0F);
setRotation(this.lleg, 0.0F, 0.0F, 0.0F);
setRotation(this.rleg, 0.0F, 0.0F, 0.0F);

     LeafMonsterEntity.CustomEntity lm = (LeafMonsterEntity.CustomEntity) entity;
     float newangle;

     if (lm.getAttacking() == 0) {
       this.body.rotationPointY = 16.0F;
       this.rarm.rotationPointY = 8.0F;
       this.larm.rotationPointY = 8.0F;
       this.rarm.rotateAngleY = 0.0F;
       this.larm.rotateAngleY = 0.0F;
       this.rarm.rotateAngleX = 0.0F;
       this.larm.rotateAngleX = 0.0F;
       this.lleg.rotateAngleX = 0.0F;
       this.rleg.rotateAngleX = 0.0F;
     } else {
       this.body.rotationPointY = 0.0F;
       this.rarm.rotationPointY = -8.0F;
       this.larm.rotationPointY = -8.0F;
       if (limbSwingAmount > 0.1D) {
         newangle = MathHelper.cos(ageInTicks * 0.95F) * 3.1415927F * 0.25F * limbSwingAmount;
       } else {
         newangle = 0.0F;
       } 
       this.lleg.rotateAngleX = newangle;
       this.rleg.rotateAngleX = -newangle;
       
       newangle = MathHelper.cos(ageInTicks * 0.7F) * 3.1415927F * 0.55F;
       this.rarm.rotateAngleY = -Math.abs(newangle);
       this.larm.rotateAngleY = Math.abs(newangle);
       this.rarm.rotateAngleX = -Math.abs(newangle);
       this.larm.rotateAngleX = -Math.abs(newangle);
     }
    }
 }
}
