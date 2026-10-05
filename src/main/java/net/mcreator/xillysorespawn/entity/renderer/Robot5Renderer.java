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
import net.mcreator.xillysorespawn.entity.Robot5Entity;
import net.mcreator.xillysorespawn.entity.OreSpawnLogic;

@OnlyIn(Dist.CLIENT)
public class Robot5Renderer {
    private static final String TEXTURE = "xillys_orespawn:textures/entities/robot5texture.png";
    public static class ModelRegisterHandler {
        @SubscribeEvent @OnlyIn(Dist.CLIENT)
        public void registerModels(ModelRegistryEvent event) {
            RenderingRegistry.registerEntityRenderingHandler(Robot5Entity.entity, manager ->
                new MobRenderer(manager, new ModelRobot5(1.0F), 0.5F) {
                    @Override public ResourceLocation getEntityTexture(Entity entity) { return new ResourceLocation(TEXTURE); }
                    @Override protected void preRenderCallback(LivingEntity entity, MatrixStack stack, float partialTick) {
                        float modelScale = 1.0F;
                        stack.scale(modelScale, modelScale, modelScale);
                    }
                });
        }
    }

public static class ModelRobot5 extends EntityModel<Entity>
 {
   private float wingspeed = 1.0F;

   private final ModelRenderer lwheel1;

   private final ModelRenderer lwheel2;
   private final ModelRenderer rwheel1;
   private final ModelRenderer rwheel2;
   private final ModelRenderer axle;
   private final ModelRenderer drivebox;
   private final ModelRenderer stand;
   private final ModelRenderer swivel;
   private final ModelRenderer barrel1;
   private final ModelRenderer barrel2;
   private final ModelRenderer ammobox;

   public ModelRobot5(float f1) {
     this.wingspeed = f1;

     this.textureWidth = 128;
     this.textureHeight = 128;

     this.lwheel1 = new ModelRenderer(this, 0, 23);
     this.lwheel1.addBox(0.0F, -4.0F, -4.0F, 2, 8, 8);
     this.lwheel1.setRotationPoint(6.0F, 19.0F, 0.0F);

     this.lwheel1.mirror = true;
     setRotation(this.lwheel1, 0.0F, 0.0F, 0.0F);
     this.lwheel2 = new ModelRenderer(this, 0, 43);
     this.lwheel2.addBox(0.0F, -4.0F, -4.0F, 2, 8, 8);
     this.lwheel2.setRotationPoint(6.0F, 19.0F, 0.0F);

     this.lwheel2.mirror = true;
     setRotation(this.lwheel2, 0.7853982F, 0.0F, 0.0F);
     this.rwheel1 = new ModelRenderer(this, 0, 23);
     this.rwheel1.addBox(0.0F, -4.0F, -4.0F, 2, 8, 8);
     this.rwheel1.setRotationPoint(-8.0F, 19.0F, 0.0F);

     this.rwheel1.mirror = true;
     setRotation(this.rwheel1, 0.0F, 0.0F, 0.0F);
     this.rwheel2 = new ModelRenderer(this, 0, 43);
     this.rwheel2.addBox(0.0F, -4.0F, -4.0F, 2, 8, 8);
     this.rwheel2.setRotationPoint(-8.0F, 19.0F, 0.0F);

     this.rwheel2.mirror = true;
     setRotation(this.rwheel2, 0.7853982F, 0.0F, 0.0F);
     this.axle = new ModelRenderer(this, 42, 0);
     this.axle.addBox(-6.0F, -0.5F, -0.5F, 12, 1, 1);
     this.axle.setRotationPoint(0.0F, 19.0F, 0.0F);

     this.axle.mirror = true;
     setRotation(this.axle, 0.0F, 0.0F, 0.0F);
     this.drivebox = new ModelRenderer(this, 47, 4);
     this.drivebox.addBox(-2.0F, -1.5F, -1.5F, 4, 3, 3);
     this.drivebox.setRotationPoint(0.0F, 19.0F, 0.0F);

     this.drivebox.mirror = true;
     setRotation(this.drivebox, 0.0F, 0.0F, 0.0F);
     this.stand = new ModelRenderer(this, 35, 0);
     this.stand.addBox(-0.5F, 0.0F, -0.5F, 1, 18, 1);
     this.stand.setRotationPoint(0.0F, 0.0F, 0.0F);

     this.stand.mirror = true;
     setRotation(this.stand, 0.0F, 0.0F, 0.0F);
     this.swivel = new ModelRenderer(this, 22, 0);
     this.swivel.addBox(-1.0F, 0.0F, -1.0F, 2, 1, 2);
     this.swivel.setRotationPoint(0.0F, 0.0F, 0.0F);

     this.swivel.mirror = true;
     setRotation(this.swivel, 0.0F, 0.0F, 0.0F);
     this.barrel1 = new ModelRenderer(this, 24, 25);
     this.barrel1.addBox(-1.0F, -2.0F, -10.0F, 2, 2, 13);
     this.barrel1.setRotationPoint(0.0F, 0.0F, 0.0F);

     this.barrel1.mirror = true;
     setRotation(this.barrel1, 0.0F, 0.0F, 0.0F);
     this.barrel2 = new ModelRenderer(this, 27, 43);
     this.barrel2.addBox(-0.5F, -1.5F, -19.0F, 1, 1, 9);
     this.barrel2.setRotationPoint(0.0F, 0.0F, 0.0F);

     this.barrel2.mirror = true;
     setRotation(this.barrel2, 0.0F, 0.0F, 0.0F);
     this.ammobox = new ModelRenderer(this, 0, 0);
     this.ammobox.addBox(-2.0F, -2.0F, 3.0F, 4, 3, 5);
     this.ammobox.setRotationPoint(0.0F, 0.0F, 0.0F);

     this.ammobox.mirror = true;
     setRotation(this.ammobox, 0.0F, 0.0F, 0.0F);
   }

   public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay,
        float red, float green, float blue, float alpha) {
        lwheel1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        lwheel2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rwheel1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rwheel2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        axle.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        drivebox.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        stand.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        swivel.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        barrel1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        barrel2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        ammobox.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }

   private void setRotation(ModelRenderer model, float x, float y, float z) {
     model.rotateAngleX = x;
     model.rotateAngleY = y;
     model.rotateAngleZ = z;
   }

   public void setRotationAngles(Entity entity, float limbSwing, float limbSwingAmount,
        float ageInTicks, float netHeadYaw, float headPitch) {
// Reset shared model state before applying this frame's legacy animation.
this.lwheel1.setRotationPoint(6.0F, 19.0F, 0.0F);
this.lwheel2.setRotationPoint(6.0F, 19.0F, 0.0F);
this.rwheel1.setRotationPoint(-8.0F, 19.0F, 0.0F);
this.rwheel2.setRotationPoint(-8.0F, 19.0F, 0.0F);
this.axle.setRotationPoint(0.0F, 19.0F, 0.0F);
this.drivebox.setRotationPoint(0.0F, 19.0F, 0.0F);
this.stand.setRotationPoint(0.0F, 0.0F, 0.0F);
this.swivel.setRotationPoint(0.0F, 0.0F, 0.0F);
this.barrel1.setRotationPoint(0.0F, 0.0F, 0.0F);
this.barrel2.setRotationPoint(0.0F, 0.0F, 0.0F);
this.ammobox.setRotationPoint(0.0F, 0.0F, 0.0F);
setRotation(this.lwheel1, 0.0F, 0.0F, 0.0F);
setRotation(this.lwheel2, 0.7853982F, 0.0F, 0.0F);
setRotation(this.rwheel1, 0.0F, 0.0F, 0.0F);
setRotation(this.rwheel2, 0.7853982F, 0.0F, 0.0F);
setRotation(this.axle, 0.0F, 0.0F, 0.0F);
setRotation(this.drivebox, 0.0F, 0.0F, 0.0F);
setRotation(this.stand, 0.0F, 0.0F, 0.0F);
setRotation(this.swivel, 0.0F, 0.0F, 0.0F);
setRotation(this.barrel1, 0.0F, 0.0F, 0.0F);
setRotation(this.barrel2, 0.0F, 0.0F, 0.0F);
setRotation(this.ammobox, 0.0F, 0.0F, 0.0F);

     Robot5Entity.CustomEntity e = (Robot5Entity.CustomEntity) entity;
     
     float newangle = 0.0F;

     if (limbSwingAmount > 0.1D) {
       newangle = ageInTicks * 0.15F % 6.2831855F;
       newangle = Math.abs(newangle);
     } else {
       newangle = 0.0F;
     } 
     
     this.lwheel1.rotateAngleX = newangle;
     this.lwheel2.rotateAngleX = (float)(newangle + 0.7853981633974483D);
     this.rwheel1.rotateAngleX = newangle;
     this.rwheel2.rotateAngleX = (float)(newangle + 0.7853981633974483D);
     
     this.ammobox.rotateAngleY = (float)Math.toRadians(netHeadYaw / 2.0D);
    }
 }
}