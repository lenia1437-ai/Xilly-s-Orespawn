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
import net.mcreator.xillysorespawn.entity.GoldFishEntity;
import net.mcreator.xillysorespawn.entity.OreSpawnLogic;

@OnlyIn(Dist.CLIENT)
public class GoldFishRenderer {
    private static final String TEXTURE = "xillys_orespawn:textures/entities/goldfish.png";
    public static class ModelRegisterHandler {
        @SubscribeEvent @OnlyIn(Dist.CLIENT)
        public void registerModels(ModelRegistryEvent event) {
            RenderingRegistry.registerEntityRenderingHandler(GoldFishEntity.entity, manager ->
                new MobRenderer(manager, new ModelGoldFish(0.7F), 0.2F) {
                    @Override public ResourceLocation getEntityTexture(Entity entity) { return new ResourceLocation(TEXTURE); }
                    @Override protected void preRenderCallback(LivingEntity entity, MatrixStack stack, float partialTick) {
                        float modelScale = 1.0F;
                        stack.scale(modelScale, modelScale, modelScale);
                    }
                });
        }
    }

public static class ModelGoldFish extends EntityModel<Entity> {
   private float wingspeed = 1.0F;

   private final ModelRenderer Body;

   private final ModelRenderer Head;
   private final ModelRenderer Dorsalfin;
   private final ModelRenderer Mouth;
   private final ModelRenderer Jaw;
   private final ModelRenderer Pectoralfin1;
   private final ModelRenderer Pectoralfin2;
   private final ModelRenderer Pectoralfin3;
   private final ModelRenderer Pectoralfin4;
   private final ModelRenderer Bottomfin;
   private final ModelRenderer Tail1;
   private final ModelRenderer Tail2;
   private final ModelRenderer Caudalfin1;
   private final ModelRenderer Caudalfin2;
   private final ModelRenderer Bottomfin1;
   private final ModelRenderer Bottomfin2;

   public ModelGoldFish(float f1) {
     this.wingspeed = f1;

     this.textureWidth = 64;
     this.textureHeight = 64;

     this.Body = new ModelRenderer(this, 0, 15);
     this.Body.addBox(-2.0F, -2.0F, 0.0F, 4, 4, 10);
     this.Body.setRotationPoint(0.0F, 14.0F, -5.0F);

     this.Body.mirror = true;
     setRotation(this.Body, 0.0F, 0.0F, 0.0F);
     this.Head = new ModelRenderer(this, 0, 30);
     this.Head.addBox(-1.5F, -2.0F, -3.0F, 3, 4, 3);
     this.Head.setRotationPoint(0.0F, 14.0F, -5.0F);

     this.Head.mirror = true;
     setRotation(this.Head, 0.0F, 0.0F, 0.0F);
     this.Dorsalfin = new ModelRenderer(this, 29, 0);
     this.Dorsalfin.addBox(0.0F, -6.0F, 0.0F, 0, 4, 10);
     this.Dorsalfin.setRotationPoint(0.0F, 14.0F, -5.0F);

     this.Dorsalfin.mirror = true;
     setRotation(this.Dorsalfin, 0.0F, 0.0F, 0.0F);
     this.Mouth = new ModelRenderer(this, 0, 38);
     this.Mouth.addBox(-1.5F, 0.6F, -3.5F, 3, 3, 3);
     this.Mouth.setRotationPoint(0.0F, 14.0F, -5.0F);

     this.Mouth.mirror = true;
     setRotation(this.Mouth, -0.7853982F, 0.0F, 0.0F);
     this.Jaw = new ModelRenderer(this, 13, 30);
     this.Jaw.addBox(-1.0F, 0.0F, -3.0F, 3, 1, 3);
     this.Jaw.setRotationPoint(-0.5F, 15.6F, -7.4F);

     this.Jaw.mirror = true;
     setRotation(this.Jaw, -0.2284419F, 0.0F, 0.0F);
     this.Pectoralfin1 = new ModelRenderer(this, 0, 0);
     this.Pectoralfin1.addBox(0.0F, -1.5F, 0.0F, 0, 3, 5);
     this.Pectoralfin1.setRotationPoint(-2.0F, 14.0F, -3.0F);

     this.Pectoralfin1.mirror = true;
     setRotation(this.Pectoralfin1, -0.2974289F, -0.3346075F, 0.0F);
     this.Pectoralfin2 = new ModelRenderer(this, 0, 0);
     this.Pectoralfin2.addBox(0.0F, -1.5F, 0.0F, 0, 3, 5);
     this.Pectoralfin2.setRotationPoint(2.0F, 14.0F, -3.0F);

     this.Pectoralfin2.mirror = true;
     setRotation(this.Pectoralfin2, -0.2974216F, 0.3346145F, 0.0F);
     this.Pectoralfin3 = new ModelRenderer(this, 0, 0);
     this.Pectoralfin3.addBox(0.0F, -1.5F, 0.0F, 0, 3, 5);
     this.Pectoralfin3.setRotationPoint(-2.0F, 14.0F, 1.0F);

     this.Pectoralfin3.mirror = true;
     setRotation(this.Pectoralfin3, -0.2974289F, -0.3346075F, 0.0F);
     this.Pectoralfin4 = new ModelRenderer(this, 0, 0);
     this.Pectoralfin4.addBox(0.0F, -1.5F, 0.0F, 0, 3, 5);
     this.Pectoralfin4.setRotationPoint(2.0F, 14.0F, 1.0F);

     this.Pectoralfin4.mirror = true;
     setRotation(this.Pectoralfin4, -0.2974289F, 0.3346145F, 0.0F);
     this.Bottomfin = new ModelRenderer(this, 20, 8);
     this.Bottomfin.addBox(0.0F, 2.0F, 6.0F, 0, 3, 4);
     this.Bottomfin.setRotationPoint(0.0F, 14.0F, -5.0F);

     this.Bottomfin.mirror = true;
     setRotation(this.Bottomfin, 0.0F, 0.0F, 0.0F);
     this.Tail1 = new ModelRenderer(this, 29, 15);
     this.Tail1.addBox(-1.5F, -2.0F, 0.0F, 3, 4, 6);
     this.Tail1.setRotationPoint(0.0F, 14.0F, 5.0F);

     this.Tail1.mirror = true;
     setRotation(this.Tail1, 0.0F, 0.0F, 0.0F);
     this.Tail2 = new ModelRenderer(this, 0, 8);
     this.Tail2.addBox(-1.0F, -1.5F, 6.0F, 2, 3, 4);
     this.Tail2.setRotationPoint(0.0F, 14.0F, 5.0F);

     this.Tail2.mirror = true;
     setRotation(this.Tail2, 0.0F, 0.0F, 0.0F);
     this.Caudalfin1 = new ModelRenderer(this, 13, 35);
     this.Caudalfin1.addBox(-0.5F, 5.5F, 6.0F, 1, 3, 4);
     this.Caudalfin1.setRotationPoint(0.0F, 14.0F, 5.0F);

     this.Caudalfin1.mirror = true;
     setRotation(this.Caudalfin1, 0.8179294F, 0.0F, 0.0F);
     this.Caudalfin2 = new ModelRenderer(this, 15, 35);
     this.Caudalfin2.addBox(-0.5F, 5.5F, 6.0F, 1, 4, 3);
     this.Caudalfin2.setRotationPoint(0.0F, 14.0F, 5.0F);

     this.Caudalfin2.mirror = true;
     setRotation(this.Caudalfin2, 0.8179294F, 0.0F, 0.0F);
     this.Bottomfin1 = new ModelRenderer(this, 20, 0);
     this.Bottomfin1.addBox(-1.0F, 2.0F, 1.0F, 0, 5, 2);
     this.Bottomfin1.setRotationPoint(0.0F, 14.0F, -5.0F);

     this.Bottomfin1.mirror = true;
     setRotation(this.Bottomfin1, 0.2974289F, 0.0F, 0.3346145F);
     this.Bottomfin2 = new ModelRenderer(this, 20, 0);
     this.Bottomfin2.addBox(1.0F, 2.0F, 1.0F, 0, 5, 2);
     this.Bottomfin2.setRotationPoint(0.0F, 14.0F, -5.0F);

     this.Bottomfin2.mirror = true;
     setRotation(this.Bottomfin2, 0.2974289F, 0.0F, -0.3346075F);
   }

   public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay,
        float red, float green, float blue, float alpha) {
        Body.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Head.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Dorsalfin.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Mouth.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Jaw.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Pectoralfin1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Pectoralfin2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Pectoralfin3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Pectoralfin4.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Bottomfin.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Tail1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Tail2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Caudalfin1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Caudalfin2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Bottomfin1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Bottomfin2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }

   private void setRotation(ModelRenderer model, float x, float y, float z) {
     model.rotateAngleX = x;
     model.rotateAngleY = y;
     model.rotateAngleZ = z;
   }

   public void setRotationAngles(Entity entity, float limbSwing, float limbSwingAmount,
        float ageInTicks, float netHeadYaw, float headPitch) {
// Reset shared model state before applying this frame's legacy animation.
this.Body.setRotationPoint(0.0F, 14.0F, -5.0F);
this.Head.setRotationPoint(0.0F, 14.0F, -5.0F);
this.Dorsalfin.setRotationPoint(0.0F, 14.0F, -5.0F);
this.Mouth.setRotationPoint(0.0F, 14.0F, -5.0F);
this.Jaw.setRotationPoint(-0.5F, 15.6F, -7.4F);
this.Pectoralfin1.setRotationPoint(-2.0F, 14.0F, -3.0F);
this.Pectoralfin2.setRotationPoint(2.0F, 14.0F, -3.0F);
this.Pectoralfin3.setRotationPoint(-2.0F, 14.0F, 1.0F);
this.Pectoralfin4.setRotationPoint(2.0F, 14.0F, 1.0F);
this.Bottomfin.setRotationPoint(0.0F, 14.0F, -5.0F);
this.Tail1.setRotationPoint(0.0F, 14.0F, 5.0F);
this.Tail2.setRotationPoint(0.0F, 14.0F, 5.0F);
this.Caudalfin1.setRotationPoint(0.0F, 14.0F, 5.0F);
this.Caudalfin2.setRotationPoint(0.0F, 14.0F, 5.0F);
this.Bottomfin1.setRotationPoint(0.0F, 14.0F, -5.0F);
this.Bottomfin2.setRotationPoint(0.0F, 14.0F, -5.0F);
setRotation(this.Body, 0.0F, 0.0F, 0.0F);
setRotation(this.Head, 0.0F, 0.0F, 0.0F);
setRotation(this.Dorsalfin, 0.0F, 0.0F, 0.0F);
setRotation(this.Mouth, -0.7853982F, 0.0F, 0.0F);
setRotation(this.Jaw, -0.2284419F, 0.0F, 0.0F);
setRotation(this.Pectoralfin1, -0.2974289F, -0.3346075F, 0.0F);
setRotation(this.Pectoralfin2, -0.2974216F, 0.3346145F, 0.0F);
setRotation(this.Pectoralfin3, -0.2974289F, -0.3346075F, 0.0F);
setRotation(this.Pectoralfin4, -0.2974289F, 0.3346145F, 0.0F);
setRotation(this.Bottomfin, 0.0F, 0.0F, 0.0F);
setRotation(this.Tail1, 0.0F, 0.0F, 0.0F);
setRotation(this.Tail2, 0.0F, 0.0F, 0.0F);
setRotation(this.Caudalfin1, 0.8179294F, 0.0F, 0.0F);
setRotation(this.Caudalfin2, 0.8179294F, 0.0F, 0.0F);
setRotation(this.Bottomfin1, 0.2974289F, 0.0F, 0.3346145F);
setRotation(this.Bottomfin2, 0.2974289F, 0.0F, -0.3346075F);

     float newangle = 0.0F;

     newangle = MathHelper.cos(ageInTicks * 1.3F * this.wingspeed) * 3.1415927F * 0.15F;
     this.Pectoralfin1.rotateAngleY = 0.4F + newangle;
     newangle = MathHelper.cos(ageInTicks * 1.2F * this.wingspeed) * 3.1415927F * 0.15F;
     this.Pectoralfin2.rotateAngleY = -0.4F + newangle;
     newangle = MathHelper.cos(ageInTicks * 1.1F * this.wingspeed) * 3.1415927F * 0.15F;
     this.Pectoralfin3.rotateAngleY = 0.4F + newangle;
     newangle = MathHelper.cos(ageInTicks * 1.0F * this.wingspeed) * 3.1415927F * 0.15F;
     this.Pectoralfin4.rotateAngleY = -0.4F + newangle;
     
     newangle = MathHelper.cos(ageInTicks * 1.7F * this.wingspeed) * 3.1415927F * 0.25F;
     this.Bottomfin1.rotateAngleY = newangle;
     this.Bottomfin2.rotateAngleY = -newangle;
     
     newangle = MathHelper.cos(ageInTicks * 0.7F * this.wingspeed) * 3.1415927F * 0.1F;
     this.Jaw.rotateAngleX = -0.25F + newangle;
    }
 }
}