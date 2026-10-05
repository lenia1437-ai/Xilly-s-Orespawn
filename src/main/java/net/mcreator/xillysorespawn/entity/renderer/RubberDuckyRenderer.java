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
import net.mcreator.xillysorespawn.entity.RubberDuckyEntity;
import net.mcreator.xillysorespawn.entity.OreSpawnLogic;

@OnlyIn(Dist.CLIENT)
public class RubberDuckyRenderer {
    private static final String TEXTURE = "xillys_orespawn:textures/entities/rubberduckytexture.png";
    private static final String EVIL_TEXTURE = "xillys_orespawn:textures/entities/evilrubberduckytexture.png";
    public static class ModelRegisterHandler {
        @SubscribeEvent @OnlyIn(Dist.CLIENT)
        public void registerModels(ModelRegistryEvent event) {
            RenderingRegistry.registerEntityRenderingHandler(RubberDuckyEntity.entity, manager ->
                new MobRenderer(manager, new ModelRubberDucky(1.0F), 0.1125F) {
                    @Override public ResourceLocation getEntityTexture(Entity entity) {
                        return new ResourceLocation(((RubberDuckyEntity.CustomEntity) entity).getKillCount() >= 5
                            ? EVIL_TEXTURE : TEXTURE);
                    }
                    @Override protected void preRenderCallback(LivingEntity entity, MatrixStack stack, float partialTick) {
                        float modelScale = 0.75F;
                        if (entity.isChild()) modelScale *= 0.5F;
                        stack.scale(modelScale, modelScale, modelScale);
                    }
                });
        }
    }

public static class ModelRubberDucky extends EntityModel<Entity> {
   private float wingspeed = 1.0F;

   private final ModelRenderer bottom;

   private final ModelRenderer body;

   private final ModelRenderer back;
   private final ModelRenderer neck;
   private final ModelRenderer head;
   private final ModelRenderer beak;
   private final ModelRenderer Lwing;
   private final ModelRenderer Rwing;

   public ModelRubberDucky(float f1) {
     this.wingspeed = f1;

     this.textureWidth = 64;
     this.textureHeight = 64;

     this.bottom = new ModelRenderer(this, 0, 56);
     this.bottom.addBox(-2.0F, 0.0F, -2.0F, 4, 1, 4);
     this.bottom.setRotationPoint(0.0F, 23.0F, 0.0F);

     this.bottom.mirror = true;
     setRotation(this.bottom, 0.0F, 0.0F, 0.0F);
     this.body = new ModelRenderer(this, 0, 45);
     this.body.addBox(-3.0F, 0.0F, -3.0F, 6, 2, 8);
     this.body.setRotationPoint(0.0F, 21.0F, 0.0F);

     this.body.mirror = true;
     setRotation(this.body, 0.0F, 0.0F, 0.0F);
     this.back = new ModelRenderer(this, 0, 33);
     this.back.addBox(-3.0F, 0.0F, -3.0F, 6, 1, 10);
     this.back.setRotationPoint(0.0F, 20.0F, 0.0F);

     this.back.mirror = true;
     setRotation(this.back, 0.0F, 0.0F, 0.0F);
     this.neck = new ModelRenderer(this, 17, 27);
     this.neck.addBox(-1.0F, 0.0F, -1.0F, 2, 1, 2);
     this.neck.setRotationPoint(0.0F, 19.0F, -1.0F);

     this.neck.mirror = true;
     setRotation(this.neck, 0.0F, 0.0F, 0.0F);
     this.head = new ModelRenderer(this, 13, 18);
     this.head.addBox(-2.0F, -4.0F, -2.0F, 4, 4, 4);
     this.head.setRotationPoint(0.0F, 19.0F, -1.0F);

     this.head.mirror = true;
     setRotation(this.head, 0.0F, 0.0F, 0.0F);
     this.beak = new ModelRenderer(this, 0, 21);
     this.beak.addBox(-1.5F, -1.0F, -5.0F, 3, 1, 3);
     this.beak.setRotationPoint(0.0F, 19.0F, -1.0F);

     this.beak.mirror = true;
     setRotation(this.beak, 0.0F, 0.0F, 0.0F);
     this.Lwing = new ModelRenderer(this, 0, 0);
     this.Lwing.addBox(0.0F, -0.5F, 0.0F, 2, 1, 5);
     this.Lwing.setRotationPoint(3.0F, 21.0F, -2.0F);

     this.Lwing.mirror = true;
     setRotation(this.Lwing, 0.0F, 0.0F, 0.0F);
     this.Rwing = new ModelRenderer(this, 17, 0);
     this.Rwing.addBox(-2.0F, -0.5F, 0.0F, 2, 1, 5);
     this.Rwing.setRotationPoint(-3.0F, 21.0F, -2.0F);

     this.Rwing.mirror = true;
     setRotation(this.Rwing, 0.0F, 0.0F, 0.0F);
   }

   public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay,
        float red, float green, float blue, float alpha) {
        bottom.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        body.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        back.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        neck.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        head.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        beak.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Lwing.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Rwing.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }

   private void setRotation(ModelRenderer model, float x, float y, float z) {
     model.rotateAngleX = x;
     model.rotateAngleY = y;
     model.rotateAngleZ = z;
   }

   public void setRotationAngles(Entity entity, float limbSwing, float limbSwingAmount,
        float ageInTicks, float netHeadYaw, float headPitch) {
// Reset shared model state before applying this frame's legacy animation.
this.bottom.setRotationPoint(0.0F, 23.0F, 0.0F);
this.body.setRotationPoint(0.0F, 21.0F, 0.0F);
this.back.setRotationPoint(0.0F, 20.0F, 0.0F);
this.neck.setRotationPoint(0.0F, 19.0F, -1.0F);
this.head.setRotationPoint(0.0F, 19.0F, -1.0F);
this.beak.setRotationPoint(0.0F, 19.0F, -1.0F);
this.Lwing.setRotationPoint(3.0F, 21.0F, -2.0F);
this.Rwing.setRotationPoint(-3.0F, 21.0F, -2.0F);
setRotation(this.bottom, 0.0F, 0.0F, 0.0F);
setRotation(this.body, 0.0F, 0.0F, 0.0F);
setRotation(this.back, 0.0F, 0.0F, 0.0F);
setRotation(this.neck, 0.0F, 0.0F, 0.0F);
setRotation(this.head, 0.0F, 0.0F, 0.0F);
setRotation(this.beak, 0.0F, 0.0F, 0.0F);
setRotation(this.Lwing, 0.0F, 0.0F, 0.0F);
setRotation(this.Rwing, 0.0F, 0.0F, 0.0F);

     RubberDuckyEntity.CustomEntity c = (RubberDuckyEntity.CustomEntity) entity;
     RenderInfo r = null;
     float hf = 0.0F;
     float newangle = 0.0F;
     float nextangle = 0.0F;

     if (limbSwingAmount > 0.1D) {
       newangle = MathHelper.cos(ageInTicks * 2.3F * this.wingspeed) * 3.1415927F * 0.25F * limbSwingAmount;
     } else {
       newangle = 0.0F;
     } 
     
     this.head.rotateAngleY = (float)Math.toRadians(netHeadYaw) * 0.45F;
     this.beak.rotateAngleY = this.head.rotateAngleY;
     this.head.rotateAngleX = (float)Math.toRadians(headPitch) * 0.65F;
     this.beak.rotateAngleX = this.head.rotateAngleX;
 
     
     r = c.getRenderInfo();
     
     newangle = MathHelper.cos(ageInTicks * 1.0F * this.wingspeed) * 3.1415927F * 0.15F;
     nextangle = MathHelper.cos((ageInTicks + 0.3F) * 1.0F * this.wingspeed) * 3.1415927F * 0.15F;
 
     
     if (nextangle > 0.0F && newangle < 0.0F) {
       
       r.ri1 = 0;
       if (c.getModelRandom().nextInt(3) == 1) {
         r.ri1 = 1;
       }
       if (c.getKillCount() >= 5) {
         if (c.getModelRandom().nextInt(2) == 1) {
           r.ri1 = 1;
         }
         newangle *= 4.0F;
       } 
     } 
     
     if (r.ri1 == 0) {
       newangle = 0.0F;
     }
     if (c.isChildModel()) {
       newangle = 0.0F;
     }
     newangle = Math.abs(newangle);
     this.Lwing.rotateAngleZ = -newangle;
     this.Lwing.rotateAngleY = newangle / 2.0F;
     this.Rwing.rotateAngleZ = newangle;
     this.Rwing.rotateAngleY = -newangle / 2.0F;
 
     
     c.setRenderInfo(r);
    }
 }
}
