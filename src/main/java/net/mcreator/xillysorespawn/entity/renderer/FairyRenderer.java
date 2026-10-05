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
import net.mcreator.xillysorespawn.entity.FairyEntity;
import net.mcreator.xillysorespawn.entity.OreSpawnLogic;

@OnlyIn(Dist.CLIENT)
public class FairyRenderer {
    private static final ResourceLocation[] TEXTURES = new ResourceLocation[] {
        new ResourceLocation("xillys_orespawn:textures/entities/fairytexture.png"),
        new ResourceLocation("xillys_orespawn:textures/entities/fairytexture2.png"),
        new ResourceLocation("xillys_orespawn:textures/entities/fairytexture3.png"),
        new ResourceLocation("xillys_orespawn:textures/entities/fairytexture4.png"),
        new ResourceLocation("xillys_orespawn:textures/entities/fairytexture5.png"),
        new ResourceLocation("xillys_orespawn:textures/entities/fairytexture6.png"),
        new ResourceLocation("xillys_orespawn:textures/entities/fairytexture7.png"),
        new ResourceLocation("xillys_orespawn:textures/entities/fairytexture8.png"),
        new ResourceLocation("xillys_orespawn:textures/entities/fairytexture9.png")
    };
    public static class ModelRegisterHandler {
        @SubscribeEvent @OnlyIn(Dist.CLIENT)
        public void registerModels(ModelRegistryEvent event) {
            RenderingRegistry.registerEntityRenderingHandler(FairyEntity.entity, manager ->
                new MobRenderer(manager, new ModelFairy(1.5F), 0.035F) {
                    @Override public ResourceLocation getEntityTexture(Entity entity) { int variant = ((FairyEntity.CustomEntity) entity).getVariant(); return TEXTURES[Math.max(0, Math.min(TEXTURES.length - 1, variant))]; }
                    @Override protected void preRenderCallback(LivingEntity entity, MatrixStack stack, float partialTick) {
                        float modelScale = 0.35F;
                        stack.scale(modelScale, modelScale, modelScale);
                    }
                });
        }
    }

public static class ModelFairy extends EntityModel<Entity>
 {
   private float wingspeed = 1.0F;
   private int blinkLight = 15728640;

   private final ModelRenderer head;
   private final ModelRenderer chest;
   private final ModelRenderer waist;
   private final ModelRenderer hips;
   private final ModelRenderer lleg1;
   private final ModelRenderer lleg2;
   private final ModelRenderer rleg;
   private final ModelRenderer b1;
   private final ModelRenderer b2;
   private final ModelRenderer larm;
   private final ModelRenderer rarm;
   private final ModelRenderer lwing2;
   private final ModelRenderer lwing1;
   private final ModelRenderer rwing2;
   private final ModelRenderer rwing1;

   public ModelFairy(float f1) {
     this.wingspeed = f1;
     this.textureWidth = 64;
     this.textureHeight = 64;

     this.head = new ModelRenderer(this, 0, 0);
     this.head.addBox(-2.5F, -5.0F, -2.5F, 5, 5, 5);
     this.head.setRotationPoint(0.0F, 0.0F, 0.0F);

     this.head.mirror = true;
     setRotation(this.head, 0.0F, 0.0F, 0.0F);
     this.chest = new ModelRenderer(this, 31, 5);
     this.chest.addBox(-3.5F, 0.0F, -1.0F, 7, 4, 3);
     this.chest.setRotationPoint(0.0F, 0.0F, 0.0F);

     this.chest.mirror = true;
     setRotation(this.chest, 0.0F, 0.0F, 0.0F);
     this.waist = new ModelRenderer(this, 33, 13);
     this.waist.addBox(-2.5F, 4.0F, -1.0F, 5, 3, 3);
     this.waist.setRotationPoint(0.0F, 0.0F, 0.0F);

     this.waist.mirror = true;
     setRotation(this.waist, 0.0F, 0.0F, 0.0F);
     this.hips = new ModelRenderer(this, 31, 20);
     this.hips.addBox(-3.0F, 7.0F, -1.0F, 6, 4, 4);
     this.hips.setRotationPoint(0.0F, 0.0F, 0.0F);

     this.hips.mirror = true;
     setRotation(this.hips, 0.0F, 0.0F, 0.0F);
     this.lleg1 = new ModelRenderer(this, 53, 8);
     this.lleg1.addBox(0.0F, 0.0F, 0.0F, 2, 7, 2);
     this.lleg1.setRotationPoint(1.0F, 10.0F, 0.0F);

     this.lleg1.mirror = true;
     setRotation(this.lleg1, -0.7853982F, 0.0F, 0.0F);
     this.lleg2 = new ModelRenderer(this, 53, 18);
     this.lleg2.addBox(0.0F, 0.0F, 0.0F, 2, 8, 2);
     this.lleg2.setRotationPoint(1.0F, 15.0F, -5.0F);

     this.lleg2.mirror = true;
     setRotation(this.lleg2, 0.7679449F, 0.0F, 0.0F);
     this.rleg = new ModelRenderer(this, 51, 30);
     this.rleg.addBox(-3.0F, 0.0F, 0.0F, 2, 13, 2);
     this.rleg.setRotationPoint(0.0F, 11.0F, 0.0F);

     this.rleg.mirror = true;
     setRotation(this.rleg, 0.0F, 0.0F, 0.0F);
     this.b1 = new ModelRenderer(this, 42, 1);
     this.b1.addBox(1.0F, 1.0F, -2.0F, 2, 2, 1);
     this.b1.setRotationPoint(0.0F, 1.0F, 0.0F);

     this.b1.mirror = true;
     setRotation(this.b1, 0.0F, 0.0F, 0.0F);
     this.b2 = new ModelRenderer(this, 32, 1);
     this.b2.addBox(-3.0F, 2.0F, -2.0F, 2, 2, 1);
     this.b2.setRotationPoint(0.0F, 0.0F, 0.0F);

     this.b2.mirror = true;
     setRotation(this.b2, 0.0F, 0.0F, 0.0F);
     this.larm = new ModelRenderer(this, 7, 14);
     this.larm.addBox(0.0F, 0.0F, 0.0F, 1, 10, 1);
     this.larm.setRotationPoint(3.0F, 0.0F, 0.0F);

     this.larm.mirror = true;
     setRotation(this.larm, -0.0174533F, 0.0F, -0.122173F);
     this.rarm = new ModelRenderer(this, 2, 14);
     this.rarm.addBox(-1.0F, 0.0F, 0.0F, 1, 10, 1);
     this.rarm.setRotationPoint(-3.0F, 0.0F, 0.0F);

     this.rarm.mirror = true;
     setRotation(this.rarm, -0.0174533F, 0.0F, 0.122173F);
     this.lwing2 = new ModelRenderer(this, 0, 47);
     this.lwing2.addBox(0.0F, -9.0F, 0.0F, 26, 16, 0);
     this.lwing2.setRotationPoint(2.0F, 0.0F, 2.0F);

     this.lwing2.mirror = true;
     setRotation(this.lwing2, 0.0F, -0.5934119F, 0.0F);
     this.lwing1 = new ModelRenderer(this, 0, 30);
     this.lwing1.addBox(0.0F, -7.0F, 0.0F, 24, 16, 0);
     this.lwing1.setRotationPoint(2.0F, 3.0F, 2.0F);

     this.lwing1.mirror = true;
     setRotation(this.lwing1, 0.0F, -0.8203047F, 0.0F);
     this.rwing2 = new ModelRenderer(this, 0, 30);
     this.rwing2.addBox(0.0F, -7.0F, 0.0F, 24, 16, 0);
     this.rwing2.setRotationPoint(-2.0F, 3.0F, 2.0F);

     this.rwing2.mirror = true;
     setRotation(this.rwing2, 0.0F, -2.356194F, 0.0F);
     this.rwing1 = new ModelRenderer(this, 0, 47);
     this.rwing1.addBox(0.0F, -9.0F, 0.0F, 26, 16, 0);
     this.rwing1.setRotationPoint(-2.0F, 0.0F, 2.0F);

     this.rwing1.mirror = true;
     setRotation(this.rwing1, 0.0F, -2.548181F, 0.0F);
   }

   public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay,
        float red, float green, float blue, float alpha) {
        lwing2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        lwing1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rwing2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rwing1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        int bodyLight = Math.max(packedLight, this.blinkLight);
        head.render(matrixStack, buffer, bodyLight, packedOverlay, red, green, blue, alpha);
        chest.render(matrixStack, buffer, bodyLight, packedOverlay, red, green, blue, alpha);
        waist.render(matrixStack, buffer, bodyLight, packedOverlay, red, green, blue, alpha);
        hips.render(matrixStack, buffer, bodyLight, packedOverlay, red, green, blue, alpha);
        lleg1.render(matrixStack, buffer, bodyLight, packedOverlay, red, green, blue, alpha);
        lleg2.render(matrixStack, buffer, bodyLight, packedOverlay, red, green, blue, alpha);
        rleg.render(matrixStack, buffer, bodyLight, packedOverlay, red, green, blue, alpha);
        b1.render(matrixStack, buffer, bodyLight, packedOverlay, red, green, blue, alpha);
        b2.render(matrixStack, buffer, bodyLight, packedOverlay, red, green, blue, alpha);
        larm.render(matrixStack, buffer, bodyLight, packedOverlay, red, green, blue, alpha);
        rarm.render(matrixStack, buffer, bodyLight, packedOverlay, red, green, blue, alpha);
    }

   private void setRotation(ModelRenderer model, float x, float y, float z) {
     model.rotateAngleX = x;
     model.rotateAngleY = y;
     model.rotateAngleZ = z;
   }

   public void setRotationAngles(Entity entity, float limbSwing, float limbSwingAmount,
        float ageInTicks, float netHeadYaw, float headPitch) {
// Reset shared model state before applying this frame's legacy animation.
this.head.setRotationPoint(0.0F, 0.0F, 0.0F);
this.chest.setRotationPoint(0.0F, 0.0F, 0.0F);
this.waist.setRotationPoint(0.0F, 0.0F, 0.0F);
this.hips.setRotationPoint(0.0F, 0.0F, 0.0F);
this.lleg1.setRotationPoint(1.0F, 10.0F, 0.0F);
this.lleg2.setRotationPoint(1.0F, 15.0F, -5.0F);
this.rleg.setRotationPoint(0.0F, 11.0F, 0.0F);
this.b1.setRotationPoint(0.0F, 1.0F, 0.0F);
this.b2.setRotationPoint(0.0F, 0.0F, 0.0F);
this.larm.setRotationPoint(3.0F, 0.0F, 0.0F);
this.rarm.setRotationPoint(-3.0F, 0.0F, 0.0F);
this.lwing2.setRotationPoint(2.0F, 0.0F, 2.0F);
this.lwing1.setRotationPoint(2.0F, 3.0F, 2.0F);
this.rwing2.setRotationPoint(-2.0F, 3.0F, 2.0F);
this.rwing1.setRotationPoint(-2.0F, 0.0F, 2.0F);
setRotation(this.head, 0.0F, 0.0F, 0.0F);
setRotation(this.chest, 0.0F, 0.0F, 0.0F);
setRotation(this.waist, 0.0F, 0.0F, 0.0F);
setRotation(this.hips, 0.0F, 0.0F, 0.0F);
setRotation(this.lleg1, -0.7853982F, 0.0F, 0.0F);
setRotation(this.lleg2, 0.7679449F, 0.0F, 0.0F);
setRotation(this.rleg, 0.0F, 0.0F, 0.0F);
setRotation(this.b1, 0.0F, 0.0F, 0.0F);
setRotation(this.b2, 0.0F, 0.0F, 0.0F);
setRotation(this.larm, -0.0174533F, 0.0F, -0.122173F);
setRotation(this.rarm, -0.0174533F, 0.0F, 0.122173F);
setRotation(this.lwing2, 0.0F, -0.5934119F, 0.0F);
setRotation(this.lwing1, 0.0F, -0.8203047F, 0.0F);
setRotation(this.rwing2, 0.0F, -2.356194F, 0.0F);
setRotation(this.rwing1, 0.0F, -2.548181F, 0.0F);

     FairyEntity.CustomEntity fly = (FairyEntity.CustomEntity) entity;

     float onoff = 0.0F;
 
     
     this.lwing1.rotateAngleY = -0.6F + MathHelper.cos(ageInTicks * this.wingspeed) * 3.1415927F * 0.35F;
     this.rwing1.rotateAngleY = -2.55F - MathHelper.cos(ageInTicks * this.wingspeed) * 3.1415927F * 0.35F;
     this.lwing2.rotateAngleY = -0.6F + MathHelper.cos(ageInTicks * this.wingspeed * 0.85F) * 3.1415927F * 0.25F;
     this.rwing2.rotateAngleY = -2.55F - MathHelper.cos(ageInTicks * this.wingspeed * 0.85F) * 3.1415927F * 0.25F;
     
     this.head.rotateAngleY = (float)Math.toRadians(netHeadYaw) * 0.45F;
     if (this.head.rotateAngleY > 0.45F) this.head.rotateAngleY = 0.45F; 
     if (this.head.rotateAngleY < -0.45F) this.head.rotateAngleY = -0.45F; 
     this.head.rotateAngleX = (float)Math.toRadians(headPitch);
     
     this.larm.rotateAngleX = -0.2F + MathHelper.cos(ageInTicks * this.wingspeed * 0.15F) * 3.1415927F * 0.05F;
     this.rarm.rotateAngleX = -0.2F + MathHelper.cos(ageInTicks * this.wingspeed * 0.12F) * 3.1415927F * 0.05F;
     this.larm.rotateAngleZ = -0.15F + MathHelper.cos(ageInTicks * this.wingspeed * 0.1F) * 3.1415927F * 0.03F;
     this.rarm.rotateAngleZ = 0.15F + MathHelper.cos(ageInTicks * this.wingspeed * 0.11F) * 3.1415927F * 0.03F;




     onoff = fly.getBlink();
     this.blinkLight = onoff > 1.0F ? 15728880 : 15728640;
    }
 }
}