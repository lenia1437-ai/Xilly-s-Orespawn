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
import net.mcreator.xillysorespawn.entity.CassowaryEntity;

@OnlyIn(Dist.CLIENT)
public class CassowaryRenderer {
    private static final String TEXTURE = "xillys_orespawn:textures/entities/cassowary.png";
    public static class ModelRegisterHandler {
        @SubscribeEvent @OnlyIn(Dist.CLIENT)
        public void registerModels(ModelRegistryEvent event) {
            RenderingRegistry.registerEntityRenderingHandler(CassowaryEntity.entity, manager ->
                new MobRenderer(manager, new ModelCassowary(1.0F), 0.5F) {
                    @Override public ResourceLocation getEntityTexture(Entity entity) { return new ResourceLocation(TEXTURE); }
                });
        }
    }

public static class ModelCassowary extends EntityModel<Entity>
 {
   private float wingspeed = 1.0F;

   private final ModelRenderer tail;
   private final ModelRenderer body;
   private final ModelRenderer neck1;
   private final ModelRenderer neck;
   private final ModelRenderer head;
   private final ModelRenderer beak;
   private final ModelRenderer leg1;
   private final ModelRenderer leg2;
   private final ModelRenderer crest;
   private final ModelRenderer foot1;
   private final ModelRenderer foot2;
   private final ModelRenderer gobbler;

   public ModelCassowary(float f1) {
     this.wingspeed = f1;

     this.textureWidth = 64;
     this.textureHeight = 32;

     this.tail = new ModelRenderer(this, 38, 16);
     this.tail.addBox(-3.0F, 0.0F, 0.0F, 6, 9, 7);
     this.tail.setRotationPoint(0.0F, 8.0F, 1.0F);

     this.tail.mirror = true;
     setRotation(this.tail, 0.8922867F, 0.0F, 0.0F);
     this.body = new ModelRenderer(this, 0, 13);
     this.body.addBox(-4.0F, 0.0F, 0.0F, 8, 10, 9);
     this.body.setRotationPoint(0.0F, 5.0F, -3.0F);

     this.body.mirror = true;
     setRotation(this.body, 0.3346075F, 0.0F, 0.0F);
     this.neck1 = new ModelRenderer(this, 48, 0);
     this.neck1.addBox(-2.0F, 0.0F, 0.0F, 4, 5, 4);
     this.neck1.setRotationPoint(0.0F, 4.0F, -1.0F);

     this.neck1.mirror = true;
     setRotation(this.neck1, -1.189716F, 0.0F, 0.0F);
     this.neck = new ModelRenderer(this, 38, 0);
     this.neck.addBox(-1.0F, 0.0F, 0.0F, 2, 7, 2);
     this.neck.setRotationPoint(0.0F, 8.0F, -3.0F);

     this.neck.mirror = true;
     setRotation(this.neck, -2.806985F, 0.0F, 0.0F);
     this.head = new ModelRenderer(this, 24, 0);
     this.head.addBox(-1.0F, -2.0F, -3.0F, 2, 2, 4);
     this.head.setRotationPoint(0.0F, 2.0F, -6.0F);

     this.head.mirror = true;
     setRotation(this.head, 0.0371786F, 0.0F, 0.0F);
     this.beak = new ModelRenderer(this, 28, 7);
     this.beak.addBox(-0.5F, 0.0F, 3.0F, 1, 1, 3);
     this.beak.setRotationPoint(0.0F, 2.0F, -6.0F);

     this.beak.mirror = true;
     setRotation(this.beak, -3.104414F, 0.0F, 0.0F);
     this.leg1 = new ModelRenderer(this, 0, 0);
     this.leg1.addBox(-0.5F, 0.0F, -1.0F, 1, 11, 2);
     this.leg1.setRotationPoint(3.0F, 12.0F, 3.0F);

     this.leg1.mirror = true;
     setRotation(this.leg1, 0.0F, 0.0F, 0.0F);
     this.leg2 = new ModelRenderer(this, 0, 0);
     this.leg2.addBox(-0.5F, 0.0F, -1.0F, 1, 11, 2);
     this.leg2.setRotationPoint(-3.0F, 12.0F, 3.0F);

     this.leg2.mirror = true;
     setRotation(this.leg2, 0.0F, 0.0F, 0.0F);
     this.crest = new ModelRenderer(this, 10, 0);
     this.crest.addBox(-0.5F, -4.0F, 1.0F, 1, 4, 5);
     this.crest.setRotationPoint(0.0F, 2.0F, -6.0F);

     this.crest.mirror = true;
     setRotation(this.crest, 1.710216F, 0.0F, 0.0F);
     this.foot1 = new ModelRenderer(this, 47, 10);
     this.foot1.addBox(-1.033333F, 11.0F, -2.0F, 2, 1, 3);
     this.foot1.setRotationPoint(-3.0F, 12.0F, 3.0F);

     this.foot1.mirror = true;
     setRotation(this.foot1, 0.0F, 0.0F, 0.0F);
     this.foot2 = new ModelRenderer(this, 47, 10);
     this.foot2.addBox(-1.0F, 11.0F, -2.0F, 2, 1, 3);
     this.foot2.setRotationPoint(3.0F, 12.0F, 3.0F);

     this.foot2.mirror = true;
     setRotation(this.foot2, 0.0F, 0.0F, 0.0F);
     this.gobbler = new ModelRenderer(this, 38, 10);
     this.gobbler.addBox(-0.5F, -1.0F, -2.5F, 1, 5, 1);
     this.gobbler.setRotationPoint(0.0F, 8.0F, -3.0F);

     this.gobbler.mirror = true;
     setRotation(this.gobbler, 0.0F, 0.0F, 0.0F);
   }

   public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay,
        float red, float green, float blue, float alpha) {
        tail.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        body.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        neck1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        neck.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        head.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        beak.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        leg1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        leg2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        crest.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        foot1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        foot2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        gobbler.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }

   private void setRotation(ModelRenderer model, float x, float y, float z) {
     model.rotateAngleX = x;
     model.rotateAngleY = y;
     model.rotateAngleZ = z;
   }

   public void setRotationAngles(Entity entity, float limbSwing, float limbSwingAmount,
        float ageInTicks, float netHeadYaw, float headPitch) {
float walk = limbSwingAmount > 0.1F
    ? MathHelper.cos(ageInTicks * 1.3F * this.wingspeed) * (float)Math.PI * 0.15F * limbSwingAmount
    : 0.0F;
float neckBob = limbSwingAmount > 0.1F
    ? MathHelper.cos(ageInTicks * 2.6F * this.wingspeed) * (float)Math.PI * 0.1F * limbSwingAmount
    : 0.0F;
this.leg1.rotateAngleX = this.foot2.rotateAngleX = walk;
this.leg2.rotateAngleX = this.foot1.rotateAngleX = -walk;
this.neck.rotateAngleX = -2.827F + neckBob;
this.gobbler.rotateAngleX = neckBob;
float headZ = this.neck.rotationPointZ + MathHelper.sin(this.neck.rotateAngleX) * 7.0F;
this.head.rotationPointZ = this.crest.rotationPointZ = this.beak.rotationPointZ = headZ;
float headY = this.neck.rotationPointY + MathHelper.cos(this.neck.rotateAngleX) * 7.0F;
this.head.rotationPointY = this.crest.rotationPointY = this.beak.rotationPointY = headY;
    }
 }
}

