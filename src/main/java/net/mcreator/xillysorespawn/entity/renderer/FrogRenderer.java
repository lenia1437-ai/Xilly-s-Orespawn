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
import net.mcreator.xillysorespawn.entity.FrogEntity;
import net.mcreator.xillysorespawn.entity.OreSpawnLogic;

@OnlyIn(Dist.CLIENT)
public class FrogRenderer {
    private static final String TEXTURE = "xillys_orespawn:textures/entities/frogtexture.png";
    public static class ModelRegisterHandler {
        @SubscribeEvent @OnlyIn(Dist.CLIENT)
        public void registerModels(ModelRegistryEvent event) {
            RenderingRegistry.registerEntityRenderingHandler(FrogEntity.entity, manager ->
                new MobRenderer(manager, new ModelFrog(1.0F), 0.35F) {
                    @Override public ResourceLocation getEntityTexture(Entity entity) { return new ResourceLocation(TEXTURE); }
                    @Override protected void preRenderCallback(LivingEntity entity, MatrixStack stack, float partialTick) {
                        float modelScale = 1.0F;
                        stack.scale(modelScale, modelScale, modelScale);
                    }
                });
        }
    }

public static class ModelFrog extends EntityModel<Entity> {
    // Exact legacy render order recovered from bytecode; do not add a second addChild rig.
    private Entity frameEntity;
    private float frameSwing, frameAmount, frameAge, frameYaw, framePitch;
    @Override public void setRotationAngles(Entity entity, float swing, float amount, float age, float yaw, float pitch) {
        frameEntity=entity; frameSwing=swing; frameAmount=amount; frameAge=age; frameYaw=yaw; framePitch=pitch;
    }

    private float wingspeed = 1.0f;
    private final ModelRenderer body;
    private final ModelRenderer jaw;
    private final ModelRenderer lfleg;
    private final ModelRenderer rfleg;
    private final ModelRenderer lleg1;
    private final ModelRenderer rleg1;
    private final ModelRenderer lleg2;
    private final ModelRenderer rleg2;
    private final ModelRenderer leye;
    private final ModelRenderer reye;

    public ModelFrog(float f1) {
        this.wingspeed = f1;
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.body = new ModelRenderer(this, 41, 0);
        this.body.addBox(-4.0f, -10.0f, 0.0f, 8, 11, 2);
        this.body.setRotationPoint(0.0f, 24.0f, 2.0f);
        this.body.mirror = true;
        this.setRotation(this.body, 0.7330383f, 0.0f, 0.0f);
        this.jaw = new ModelRenderer(this, 42, 15);
        this.jaw.addBox(-4.0f, -8.0f, 0.0f, 8, 8, 1);
        this.jaw.setRotationPoint(0.0f, 24.0f, 2.0f);
        this.jaw.mirror = true;
        this.setRotation(this.jaw, 1.22173f, 0.0f, 0.0f);
        this.lfleg = new ModelRenderer(this, 14, 0);
        this.lfleg.addBox(0.0f, 0.0f, 0.0f, 1, 5, 1);
        this.lfleg.setRotationPoint(3.0f, 20.0f, 0.0f);
        this.lfleg.mirror = true;
        this.setRotation(this.lfleg, -0.5235988f, 0.0f, -0.4712389f);
        this.rfleg = new ModelRenderer(this, 20, 0);
        this.rfleg.addBox(-1.0f, 0.0f, 0.0f, 1, 5, 1);
        this.rfleg.setRotationPoint(-3.0f, 20.0f, 0.0f);
        this.rfleg.mirror = true;
        this.setRotation(this.rfleg, -0.5235988f, 0.0f, 0.4712389f);
        this.lleg1 = new ModelRenderer(this, 10, 8);
        this.lleg1.addBox(0.0f, -9.0f, -1.0f, 1, 9, 2);
        this.lleg1.setRotationPoint(3.0f, 24.0f, 3.0f);
        this.lleg1.mirror = true;
        this.setRotation(this.lleg1, 0.0f, 0.0f, 0.2268928f);
        this.rleg1 = new ModelRenderer(this, 18, 8);
        this.rleg1.addBox(-1.0f, -9.0f, -1.0f, 1, 9, 2);
        this.rleg1.setRotationPoint(-3.0f, 24.0f, 3.0f);
        this.rleg1.mirror = true;
        this.setRotation(this.rleg1, 0.0f, 0.0f, -0.2268928f);
        this.lleg2 = new ModelRenderer(this, 11, 20);
        this.lleg2.addBox(0.0f, 0.0f, 0.0f, 1, 10, 1);
        this.lleg2.setRotationPoint(5.0f, 15.0f, 3.0f);
        this.lleg2.mirror = true;
        this.setRotation(this.lleg2, 0.0f, 0.0f, -0.3839724f);
        this.rleg2 = new ModelRenderer(this, 19, 20);
        this.rleg2.addBox(-1.0f, 0.0f, 0.0f, 1, 10, 1);
        this.rleg2.setRotationPoint(-5.0f, 15.0f, 3.0f);
        this.rleg2.mirror = true;
        this.setRotation(this.rleg2, 0.0f, 0.0f, 0.3839724f);
        this.leye = new ModelRenderer(this, 0, 8);
        this.leye.addBox(0.0f, 0.0f, 0.0f, 1, 2, 1);
        this.leye.setRotationPoint(2.0f, 17.0f, -2.0f);
        this.leye.mirror = true;
        this.setRotation(this.leye, 0.7330383f, 0.0f, 0.0f);
        this.reye = new ModelRenderer(this, 0, 4);
        this.reye.addBox(0.0f, 0.0f, 0.0f, 1, 2, 1);
        this.reye.setRotationPoint(-3.0f, 17.0f, -2.0f);
        this.reye.mirror = true;
        this.setRotation(this.reye, 0.7330383f, 0.0f, 0.0f);
    }

    public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        if (frameEntity == null) return;
        Entity entity=frameEntity;
        float f=frameSwing, f1=frameAmount, f2=frameAge, f3=frameYaw, f4=framePitch, f5=0.0625F;
        matrixStack.push();
this.body.setRotationPoint(0.0f, 24.0f, 2.0f);
this.setRotation(this.body, 0.7330383f, 0.0f, 0.0f);
this.jaw.setRotationPoint(0.0f, 24.0f, 2.0f);
this.setRotation(this.jaw, 1.22173f, 0.0f, 0.0f);
this.lfleg.setRotationPoint(3.0f, 20.0f, 0.0f);
this.setRotation(this.lfleg, -0.5235988f, 0.0f, -0.4712389f);
this.rfleg.setRotationPoint(-3.0f, 20.0f, 0.0f);
this.setRotation(this.rfleg, -0.5235988f, 0.0f, 0.4712389f);
this.lleg1.setRotationPoint(3.0f, 24.0f, 3.0f);
this.setRotation(this.lleg1, 0.0f, 0.0f, 0.2268928f);
this.rleg1.setRotationPoint(-3.0f, 24.0f, 3.0f);
this.setRotation(this.rleg1, 0.0f, 0.0f, -0.2268928f);
this.lleg2.setRotationPoint(5.0f, 15.0f, 3.0f);
this.setRotation(this.lleg2, 0.0f, 0.0f, -0.3839724f);
this.rleg2.setRotationPoint(-5.0f, 15.0f, 3.0f);
this.setRotation(this.rleg2, 0.0f, 0.0f, 0.3839724f);
this.leye.setRotationPoint(2.0f, 17.0f, -2.0f);
this.setRotation(this.leye, 0.7330383f, 0.0f, 0.0f);
this.reye.setRotationPoint(-3.0f, 17.0f, -2.0f);
this.setRotation(this.reye, 0.7330383f, 0.0f, 0.0f);

        FrogEntity.CustomEntity c = (FrogEntity.CustomEntity)entity;
        this.legacyAngles(f, f1, f2, f3, f4, f5, entity);
        float newangle = 0.0f;
        newangle = (double)f1 > 0.1 ? MathHelper.cos((float)(f2 * this.wingspeed * 1.4f)) * (float)Math.PI * 0.55f * f1 : 0.0f;
        this.lfleg.rotateAngleY = newangle;
        this.rfleg.rotateAngleY = -newangle;
        this.lleg2.rotateAngleY = -newangle / 2.0f;
        this.rleg2.rotateAngleY = newangle / 2.0f;
        newangle = c.getSinging() != 0 ? MathHelper.cos((float)(f2 * 0.85f * this.wingspeed)) * (float)Math.PI * 0.15f : 0.0f;
        this.jaw.rotateAngleX = newangle + 1.22f;
        if (!c.isOnGround() && Math.abs(c.getMotion().y) > 0.1D) {
            this.lleg1.rotateAngleZ = 2.44f;
            this.rleg1.rotateAngleZ = -2.44f;
        } else {
            this.lleg1.rotateAngleZ = 0.227f;
            this.rleg1.rotateAngleZ = -0.227f;
        }
        this.lleg2.rotationPointY = this.lleg1.rotationPointY - (float)Math.cos(this.lleg1.rotateAngleZ) * 9.0f;
        this.lleg2.rotationPointX = this.lleg1.rotationPointX + (float)Math.sin(this.lleg1.rotateAngleZ) * 9.0f;
        this.rleg2.rotationPointY = this.rleg1.rotationPointY - (float)Math.cos(this.rleg1.rotateAngleZ) * 9.0f;
        this.rleg2.rotationPointX = this.rleg1.rotationPointX + (float)Math.sin(this.rleg1.rotateAngleZ) * 9.0f;
        this.body.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.jaw.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.lfleg.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.rfleg.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.lleg1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.rleg1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.lleg2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.rleg2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.leye.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.reye.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    
        matrixStack.pop();
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    public void legacyAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity par7Entity) {
    }
}


}
