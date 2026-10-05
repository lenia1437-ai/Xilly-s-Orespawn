package net.mcreator.xillysorespawn.entity.renderer;

import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.MobRenderer;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.mcreator.xillysorespawn.entity.CockateilEntity;

@OnlyIn(Dist.CLIENT)
public class CockateilRenderer {
    public static class ModelRegisterHandler {
        @SubscribeEvent @OnlyIn(Dist.CLIENT)
        public void registerModels(ModelRegistryEvent event) {
            RenderingRegistry.registerEntityRenderingHandler(CockateilEntity.entity, manager ->
                new MobRenderer(manager, new ModelCockateil(1.0F), 0.5F) {
                    @Override public ResourceLocation getEntityTexture(Entity entity) {
                        int variant = entity instanceof CockateilEntity.CustomEntity
                            ? ((CockateilEntity.CustomEntity) entity).getBirdType() : 0;
                        variant = MathHelper.clamp(variant, 0, 5) + 1;
                        return new ResourceLocation("xillys_orespawn:textures/entities/bird" + variant + ".png");
                    }
                });
        }
    }

public static class ModelCockateil extends EntityModel<Entity>
 {
   private float wingspeed = 1.0F;

   private final ModelRenderer Body;

   private final ModelRenderer Head;
   private final ModelRenderer Beak;
   private final ModelRenderer LowerBeak;
   private final ModelRenderer feather2;
   private final ModelRenderer feather1;
   private final ModelRenderer feather3;
   private final ModelRenderer tailfeather1;
   private final ModelRenderer rwing1;
   private final ModelRenderer lwing1;
   private final ModelRenderer leg;
   private final ModelRenderer otherleg;
   private final ModelRenderer lwing2;
   private final ModelRenderer rwing2;
   private final ModelRenderer tailfeather2;
   private final ModelRenderer tailfeather3;

   public ModelCockateil(float f1) {
     this.wingspeed = f1;

     this.textureWidth = 64;
     this.textureHeight = 32;

     this.Body = new ModelRenderer(this, 0, 0);
     this.Body.addBox(0.0F, 0.0F, 0.0F, 5, 3, 6);
     this.Body.setRotationPoint(-1.0F, 18.0F, 0.0F);

     this.Body.mirror = true;
     setRotation(this.Body, 0.0F, 0.0F, 0.0F);
     this.Head = new ModelRenderer(this, 22, 0);
     this.Head.addBox(0.0F, 0.0F, 0.0F, 3, 3, 4);
     this.Head.setRotationPoint(0.0F, 16.0F, -3.0F);

     this.Head.mirror = true;
     setRotation(this.Head, 0.0F, 0.0F, 0.0F);
     this.Beak = new ModelRenderer(this, 0, 21);
     this.Beak.addBox(0.0F, 0.0F, 0.0F, 1, 1, 3);
     this.Beak.setRotationPoint(1.0F, 17.0F, -6.0F);

     this.Beak.mirror = true;
     setRotation(this.Beak, 0.0F, 0.0F, 0.0F);
     this.LowerBeak = new ModelRenderer(this, 1, 17);
     this.LowerBeak.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1);
     this.LowerBeak.setRotationPoint(1.0F, 18.0F, -4.0F);

     this.LowerBeak.mirror = true;
     setRotation(this.LowerBeak, 0.0F, 0.0F, 0.0F);
     this.feather2 = new ModelRenderer(this, 15, 9);
     this.feather2.addBox(0.0F, -2.5F, -0.75F, 1, 3, 1);
     this.feather2.setRotationPoint(1.0F, 16.0F, 0.0F);

     this.feather2.mirror = true;
     setRotation(this.feather2, -0.6426736F, 0.0F, 0.0F);
     this.feather1 = new ModelRenderer(this, 11, 9);
     this.feather1.addBox(0.0F, -2.5F, -0.5F, 1, 3, 1);
     this.feather1.setRotationPoint(1.0F, 16.0F, -2.0F);

     this.feather1.mirror = true;
     setRotation(this.feather1, -0.2230717F, 0.0F, 0.0F);
     this.feather3 = new ModelRenderer(this, 19, 9);
     this.feather3.addBox(0.0F, -3.0F, 0.5F, 1, 4, 1);
     this.feather3.setRotationPoint(1.0F, 16.0F, 1.0F);

     this.feather3.mirror = true;
     setRotation(this.feather3, -1.276259F, 0.0F, 0.0F);
     this.tailfeather1 = new ModelRenderer(this, 46, 15);
     this.tailfeather1.addBox(0.0F, 0.0F, 0.0F, 3, 2, 3);
     this.tailfeather1.setRotationPoint(0.0F, 18.0F, 6.0F);

     this.tailfeather1.mirror = true;
     setRotation(this.tailfeather1, 0.0F, 0.0F, 0.0F);
     this.rwing1 = new ModelRenderer(this, 23, 9);
     this.rwing1.addBox(0.0F, 0.0F, 0.0F, 1, 4, 4);
     this.rwing1.setRotationPoint(-1.0F, 18.0F, 1.0F);

     this.rwing1.mirror = true;
     setRotation(this.rwing1, 0.0F, 0.0F, 1.595066F);
     this.lwing1 = new ModelRenderer(this, 33, 9);
     this.lwing1.addBox(-1.0F, 0.0F, 0.0F, 1, 4, 4);
     this.lwing1.setRotationPoint(4.0F, 18.0F, 1.0F);

     this.lwing1.mirror = true;
     setRotation(this.lwing1, 0.0F, 0.0F, -1.561488F);
     this.leg = new ModelRenderer(this, 4, 12);
     this.leg.addBox(0.0F, 0.0F, 0.0F, 1, 3, 1);
     this.leg.setRotationPoint(2.0F, 21.0F, 3.0F);

     this.leg.mirror = true;
     setRotation(this.leg, 0.8726646F, 0.0F, 0.0F);
     this.otherleg = new ModelRenderer(this, 0, 12);
     this.otherleg.addBox(0.0F, 0.0F, 0.0F, 1, 3, 1);
     this.otherleg.setRotationPoint(0.0F, 21.0F, 3.0F);

     this.otherleg.mirror = true;
     setRotation(this.otherleg, 0.6108652F, 0.0F, 0.0F);
     this.lwing2 = new ModelRenderer(this, 10, 14);
     this.lwing2.addBox(4.0F, 0.0F, 0.0F, 3, 1, 3);
     this.lwing2.setRotationPoint(4.0F, 18.0F, 1.0F);

     this.lwing2.mirror = true;
     setRotation(this.lwing2, 0.0F, 0.0F, 0.0F);
     this.rwing2 = new ModelRenderer(this, 10, 19);
     this.rwing2.addBox(-7.0F, 0.0F, 0.0F, 3, 1, 3);
     this.rwing2.setRotationPoint(-1.0F, 18.0F, 1.0F);

     this.rwing2.mirror = true;
     setRotation(this.rwing2, 0.0F, 0.0F, 0.0F);
     this.tailfeather2 = new ModelRenderer(this, 44, 20);
     this.tailfeather2.addBox(-0.5F, 0.0F, 3.0F, 4, 1, 4);
     this.tailfeather2.setRotationPoint(0.0F, 18.0F, 6.0F);

     this.tailfeather2.mirror = true;
     setRotation(this.tailfeather2, 0.0F, 0.0F, 0.0F);
     this.tailfeather3 = new ModelRenderer(this, 36, 26);
     this.tailfeather3.addBox(-1.0F, 0.0F, 7.0F, 5, 1, 4);
     this.tailfeather3.setRotationPoint(0.0F, 18.0F, 6.0F);

     this.tailfeather3.mirror = true;
     setRotation(this.tailfeather3, 0.0F, 0.0F, 0.0F);
   }

   public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay,
        float red, float green, float blue, float alpha) {
        Body.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Head.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Beak.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        LowerBeak.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        feather2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        feather1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        feather3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        tailfeather1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rwing1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        lwing1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        leg.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        otherleg.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        lwing2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rwing2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        tailfeather2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        tailfeather3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }

   private void setRotation(ModelRenderer model, float x, float y, float z) {
     model.rotateAngleX = x;
     model.rotateAngleY = y;
     model.rotateAngleZ = z;
   }

   public void setRotationAngles(Entity entity, float limbSwing, float limbSwingAmount,
        float ageInTicks, float netHeadYaw, float headPitch) {
float newangle = 0.0F;

     newangle = MathHelper.cos(ageInTicks * 1.5F * this.wingspeed) * 3.1415927F * 0.35F;
     this.lwing1.rotateAngleZ = -1.5F + newangle;
     this.lwing2.rotateAngleZ = newangle;
     this.rwing1.rotateAngleZ = 1.5F - newangle;
     this.rwing2.rotateAngleZ = -newangle;
     
     newangle = MathHelper.cos(ageInTicks * 0.3F * this.wingspeed) * 3.1415927F * 0.1F;
     this.tailfeather1.rotateAngleX = newangle;
     this.tailfeather2.rotateAngleX = newangle;
     this.tailfeather3.rotateAngleX = newangle;
     
     newangle = MathHelper.cos(ageInTicks * 1.1F * this.wingspeed) * 3.1415927F * 0.08F;
     this.feather1.rotateAngleZ = newangle;
     newangle = MathHelper.cos(ageInTicks * 1.2F * this.wingspeed) * 3.1415927F * 0.08F;
     this.feather2.rotateAngleZ = newangle;
     newangle = MathHelper.cos(ageInTicks * 1.3F * this.wingspeed) * 3.1415927F * 0.08F;
     this.feather3.rotateAngleZ = newangle;
    }
 }
}

