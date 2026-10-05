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
import net.mcreator.xillysorespawn.entity.CloudSharkEntity;

@OnlyIn(Dist.CLIENT)
public class CloudSharkRenderer {
    private static final String TEXTURE = "xillys_orespawn:textures/entities/cloudshark.png";
    public static class ModelRegisterHandler {
        @SubscribeEvent @OnlyIn(Dist.CLIENT)
        public void registerModels(ModelRegistryEvent event) {
            RenderingRegistry.registerEntityRenderingHandler(CloudSharkEntity.entity, manager ->
                new MobRenderer(manager, new ModelCloudShark(1.0F), 0.5F) {
                    @Override public ResourceLocation getEntityTexture(Entity entity) { return new ResourceLocation(TEXTURE); }
                });
        }
    }

public static class ModelCloudShark extends EntityModel<Entity> {
   private float wingspeed = 1.0F;

   private final ModelRenderer body;

   private final ModelRenderer head;
   private final ModelRenderer jaw;
   private final ModelRenderer topfin;
   private final ModelRenderer bbody;
   private final ModelRenderer fins;
   private final ModelRenderer leftfin;
   private final ModelRenderer rightfin;

   public ModelCloudShark(float f1) {
     this.wingspeed = f1;

     this.textureWidth = 64;
     this.textureHeight = 64;

     this.body = new ModelRenderer(this, 0, 0);
     this.body.addBox(0.0F, 0.0F, 0.0F, 6, 8, 15);
     this.body.setRotationPoint(-4.0F, 11.0F, 0.0F);

     this.body.mirror = true;
     setRotation(this.body, 0.0F, 0.0F, 0.0F);
     this.head = new ModelRenderer(this, 0, 51);
     this.head.addBox(-2.5F, 0.0F, -8.0F, 5, 5, 8);
     this.head.setRotationPoint(-1.0F, 11.0F, 0.0F);

     this.head.mirror = true;
     setRotation(this.head, 0.0F, 0.0F, 0.0F);
     this.jaw = new ModelRenderer(this, 42, 0);
     this.jaw.addBox(-2.5F, 0.0F, -6.0F, 5, 2, 6);
     this.jaw.setRotationPoint(-1.0F, 15.0F, 0.0F);

     this.jaw.mirror = true;
     setRotation(this.jaw, 0.5056291F, 0.0F, 0.0F);
     this.topfin = new ModelRenderer(this, 0, 0);
     this.topfin.addBox(0.0F, 0.0F, 0.0F, 1, 3, 6);
     this.topfin.setRotationPoint(-1.5F, 11.0F, 5.0F);

     this.topfin.mirror = true;
     setRotation(this.topfin, 0.935765F, 0.0F, 0.0F);
     this.bbody = new ModelRenderer(this, 0, 9);
     this.bbody.addBox(-2.0F, 0.0F, 0.0F, 4, 8, 6);
     this.bbody.setRotationPoint(-1.0F, 11.0F, 15.0F);

     this.bbody.mirror = true;
     setRotation(this.bbody, 0.0F, 0.0F, 0.0F);
     this.fins = new ModelRenderer(this, 0, 24);
     this.fins.addBox(0.0F, 0.0F, 0.0F, 0, 10, 10);
     this.fins.setRotationPoint(-1.0F, 16.0F, 16.0F);

     this.fins.mirror = true;
     setRotation(this.fins, 0.9220296F, 0.0F, 0.0F);
     this.leftfin = new ModelRenderer(this, 0, 0);
     this.leftfin.addBox(0.0F, 0.0F, 0.0F, 0, 3, 7);
     this.leftfin.setRotationPoint(2.0F, 16.0F, 6.0F);

     this.leftfin.mirror = true;
     setRotation(this.leftfin, -0.6108652F, 1.134464F, -0.6108652F);
     this.rightfin = new ModelRenderer(this, 0, 0);
     this.rightfin.addBox(0.0F, 0.0F, 0.0F, 0, 3, 7);
     this.rightfin.setRotationPoint(-4.0F, 16.0F, 6.0F);

     this.rightfin.mirror = true;
     setRotation(this.rightfin, -0.6283185F, -1.134464F, 0.6108652F);
   }

   public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay,
        float red, float green, float blue, float alpha) {
        body.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        head.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        jaw.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        topfin.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        bbody.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        fins.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        leftfin.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rightfin.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }

   private void setRotation(ModelRenderer model, float x, float y, float z) {
     model.rotateAngleX = x;
     model.rotateAngleY = y;
     model.rotateAngleZ = z;
   }

   public void setRotationAngles(Entity entity, float limbSwing, float limbSwingAmount,
        float ageInTicks, float netHeadYaw, float headPitch) {
float newangle = 0.0F;

     newangle = MathHelper.cos(ageInTicks * 0.7F * this.wingspeed) * 3.1415927F * 0.15F;
     this.leftfin.rotateAngleY = 1.15F + newangle;
     newangle = MathHelper.cos(ageInTicks * 1.5F * this.wingspeed) * 3.1415927F * 0.15F;
     this.rightfin.rotateAngleY = -0.9F + newangle;
     newangle = MathHelper.cos(ageInTicks * 1.5F * this.wingspeed) * 3.1415927F * 0.25F;
     this.fins.rotateAngleY = newangle;
     newangle = MathHelper.cos(ageInTicks * 0.5F * this.wingspeed) * 3.1415927F * 0.1F;
     this.jaw.rotateAngleX = 0.5F + newangle;
    }
 }
}

