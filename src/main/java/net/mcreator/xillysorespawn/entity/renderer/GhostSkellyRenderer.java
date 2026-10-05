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
import net.mcreator.xillysorespawn.entity.GhostSkellyEntity;
import net.mcreator.xillysorespawn.entity.OreSpawnLogic;

@OnlyIn(Dist.CLIENT)
public class GhostSkellyRenderer {
    private static final String TEXTURE = "xillys_orespawn:textures/entities/ghostskellytexture.png";
    public static class ModelRegisterHandler {
        @SubscribeEvent @OnlyIn(Dist.CLIENT)
        public void registerModels(ModelRegistryEvent event) {
            RenderingRegistry.registerEntityRenderingHandler(GhostSkellyEntity.entity, manager ->
                new MobRenderer(manager, new ModelGhostSkelly(), 0.0F) {
                    @Override public ResourceLocation getEntityTexture(Entity entity) { return new ResourceLocation(TEXTURE); }
                    @Override protected RenderType func_230496_a_(LivingEntity entity, boolean visible, boolean translucent, boolean outline) { return RenderType.getEntityTranslucent(getEntityTexture(entity)); }
                    @Override protected void preRenderCallback(LivingEntity entity, MatrixStack stack, float partialTick) {
                        float modelScale = 1.05F;
                        stack.scale(modelScale, modelScale, modelScale);
                    }
                });
        }
    }

public static class ModelGhostSkelly extends EntityModel<Entity>
 {
   private final ModelRenderer body;
   private final ModelRenderer shirt;
   private final ModelRenderer head;
   private final ModelRenderer stem;
   private final ModelRenderer rarm;
   private final ModelRenderer larm;
   private final ModelRenderer rsleeve;
   private final ModelRenderer lsleeve;
   private final ModelRenderer lchains;
   private final ModelRenderer rchains;

   public ModelGhostSkelly() {
     this.textureWidth = 128;
     this.textureHeight = 64;

     this.body = new ModelRenderer(this, 0, 0);
     this.body.addBox(0.0F, 0.0F, 0.0F, 1, 21, 1);
     this.body.setRotationPoint(0.0F, -1.0F, 0.0F);

     this.body.mirror = true;
     setRotation(this.body, 0.0F, 0.0F, 0.0F);
     this.shirt = new ModelRenderer(this, 42, 43);
     this.shirt.addBox(-2.0F, 0.0F, -2.0F, 5, 12, 5);
     this.shirt.setRotationPoint(0.0F, 0.0F, 0.0F);

     this.shirt.mirror = true;
     setRotation(this.shirt, 0.0F, 0.0F, 0.0F);
     this.head = new ModelRenderer(this, 40, 29);
     this.head.addBox(-3.0F, 0.0F, -3.0F, 7, 5, 7);
     this.head.setRotationPoint(0.0F, -6.0F, 0.0F);

     this.head.mirror = true;
     setRotation(this.head, 0.0F, 0.0F, 0.0F);
     this.stem = new ModelRenderer(this, 49, 23);
     this.stem.addBox(0.0F, 0.0F, 0.0F, 1, 2, 1);
     this.stem.setRotationPoint(0.0F, -8.0F, 0.0F);

     this.stem.mirror = true;
     setRotation(this.stem, 0.1745329F, 0.0F, 0.1745329F);
     this.rarm = new ModelRenderer(this, 26, 0);
     this.rarm.addBox(-14.0F, 0.0F, 0.0F, 15, 1, 1);
     this.rarm.setRotationPoint(0.0F, 0.0F, 0.0F);

     this.rarm.mirror = true;
     setRotation(this.rarm, 0.0F, 0.0F, 0.0F);
     this.larm = new ModelRenderer(this, 63, 0);
     this.larm.addBox(0.0F, 0.0F, 0.0F, 15, 1, 1);
     this.larm.setRotationPoint(0.0F, 0.0F, 0.0F);

     this.larm.mirror = true;
     setRotation(this.larm, 0.0F, 0.0F, 0.0F);
     this.rsleeve = new ModelRenderer(this, 31, 7);
     this.rsleeve.addBox(-11.0F, 0.0F, -1.0F, 9, 8, 3);
     this.rsleeve.setRotationPoint(0.0F, 0.0F, 0.0F);

     this.rsleeve.mirror = true;
     setRotation(this.rsleeve, 0.0F, 0.0F, 0.0F);
     this.lsleeve = new ModelRenderer(this, 71, 7);
     this.lsleeve.addBox(3.0F, 0.0F, -1.0F, 9, 8, 3);
     this.lsleeve.setRotationPoint(0.0F, 0.0F, 0.0F);

     this.lsleeve.mirror = true;
     setRotation(this.lsleeve, 0.0F, 0.0F, 0.0F);
     this.lchains = new ModelRenderer(this, 98, 0);
     this.lchains.addBox(11.0F, -1.0F, 0.0F, 3, 16, 1);
     this.lchains.setRotationPoint(0.0F, 0.0F, 0.0F);

     this.lchains.mirror = true;
     setRotation(this.lchains, 0.0F, 0.0F, 0.0F);
     this.rchains = new ModelRenderer(this, 12, 0);
     this.rchains.addBox(-13.0F, -1.0F, 0.0F, 3, 10, 1);
     this.rchains.setRotationPoint(0.0F, 0.0F, 0.0F);

     this.rchains.mirror = true;
     setRotation(this.rchains, 0.0F, 0.0F, 0.0F);
   }

   public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay,
        float red, float green, float blue, float alpha) {
        red *= 0.75F; green *= 0.75F; blue *= 0.75F; alpha *= 0.25F;
        body.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        shirt.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        head.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        stem.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rarm.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        larm.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rsleeve.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        lsleeve.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        lchains.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rchains.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }

   private void setRotation(ModelRenderer model, float x, float y, float z) {
     model.rotateAngleX = x;
     model.rotateAngleY = y;
     model.rotateAngleZ = z;
   }

   public void setRotationAngles(Entity entity, float limbSwing, float limbSwingAmount,
        float ageInTicks, float netHeadYaw, float headPitch) {
// Reset shared model state before applying this frame's legacy animation.
this.body.setRotationPoint(0.0F, -1.0F, 0.0F);
this.shirt.setRotationPoint(0.0F, 0.0F, 0.0F);
this.head.setRotationPoint(0.0F, -6.0F, 0.0F);
this.stem.setRotationPoint(0.0F, -8.0F, 0.0F);
this.rarm.setRotationPoint(0.0F, 0.0F, 0.0F);
this.larm.setRotationPoint(0.0F, 0.0F, 0.0F);
this.rsleeve.setRotationPoint(0.0F, 0.0F, 0.0F);
this.lsleeve.setRotationPoint(0.0F, 0.0F, 0.0F);
this.lchains.setRotationPoint(0.0F, 0.0F, 0.0F);
this.rchains.setRotationPoint(0.0F, 0.0F, 0.0F);
setRotation(this.body, 0.0F, 0.0F, 0.0F);
setRotation(this.shirt, 0.0F, 0.0F, 0.0F);
setRotation(this.head, 0.0F, 0.0F, 0.0F);
setRotation(this.stem, 0.1745329F, 0.0F, 0.1745329F);
setRotation(this.rarm, 0.0F, 0.0F, 0.0F);
setRotation(this.larm, 0.0F, 0.0F, 0.0F);
setRotation(this.rsleeve, 0.0F, 0.0F, 0.0F);
setRotation(this.lsleeve, 0.0F, 0.0F, 0.0F);
setRotation(this.lchains, 0.0F, 0.0F, 0.0F);
setRotation(this.rchains, 0.0F, 0.0F, 0.0F);

     GhostSkellyEntity.CustomEntity e = (GhostSkellyEntity.CustomEntity) entity;
     RenderInfo r = null;
     float newangle = 0.0F;
     float newrf1 = 0.0F;

     r = e.getRenderInfo();
     
     this.lchains.rotateAngleZ = MathHelper.cos(ageInTicks * 0.2F) * 3.1415927F * 0.05F;
     this.rchains.rotateAngleZ = MathHelper.cos(ageInTicks * 0.22F) * 3.1415927F * 0.05F;
     this.lchains.rotateAngleY = MathHelper.cos(ageInTicks * 0.24F) * 3.1415927F * 0.05F;
     this.rchains.rotateAngleY = MathHelper.cos(ageInTicks * 0.26F) * 3.1415927F * 0.05F;
 
 
     
     newangle = MathHelper.cos(ageInTicks * 0.05F) * 3.1415927F * 2.0F;
     newrf1 = ageInTicks * 0.05F % 6.2831855F;
     newrf1 = Math.abs(newrf1);
     
     if (newrf1 < r.rf2) {
       
       r.ri2 = 0;
       if (e.getModelRandom().nextInt(3) == 1) r.ri2 |= 0x1; 
     } 
     r.rf2 = newrf1;
     if ((r.ri2 & 0x1) == 0) {
       newangle = 0.0F;
     }
     
     this.head.rotateAngleY = newangle;
 
     
     e.setRenderInfo(r);
    }
 }
}