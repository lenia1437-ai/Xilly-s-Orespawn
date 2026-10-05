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
import net.mcreator.xillysorespawn.entity.SeaMonsterEntity;
import net.mcreator.xillysorespawn.entity.OreSpawnLogic;

@OnlyIn(Dist.CLIENT)
public class SeaMonsterRenderer {
    private static final String TEXTURE = "xillys_orespawn:textures/entities/seamonstertexture.png";
    public static class ModelRegisterHandler {
        @SubscribeEvent @OnlyIn(Dist.CLIENT)
        public void registerModels(ModelRegistryEvent event) {
            RenderingRegistry.registerEntityRenderingHandler(SeaMonsterEntity.entity, manager ->
                new MobRenderer(manager, new ModelSeaMonster(0.5F), 1.0F) {
                    @Override public ResourceLocation getEntityTexture(Entity entity) { return new ResourceLocation(TEXTURE); }
                    @Override protected void preRenderCallback(LivingEntity entity, MatrixStack stack, float partialTick) {
                        float modelScale = 1.0F;
                        stack.scale(modelScale, modelScale, modelScale);
                    }
                });
        }
    }

public static class ModelSeaMonster extends EntityModel<Entity> {
    // Exact legacy render order recovered from bytecode; do not add a second addChild rig.
    private Entity frameEntity;
    private float frameSwing, frameAmount, frameAge, frameYaw, framePitch;
    @Override public void setRotationAngles(Entity entity, float swing, float amount, float age, float yaw, float pitch) {
        frameEntity=entity; frameSwing=swing; frameAmount=amount; frameAge=age; frameYaw=yaw; framePitch=pitch;
    }

    private float wingspeed = 1.0f;
    private final ModelRenderer TailTip;
    private final ModelRenderer TailBase;
    private final ModelRenderer Tail2;
    private final ModelRenderer Tail3;
    private final ModelRenderer BodyBack;
    private final ModelRenderer Neck6;
    private final ModelRenderer BodyFront;
    private final ModelRenderer NeckBase;
    private final ModelRenderer Neck2;
    private final ModelRenderer Neck3;
    private final ModelRenderer Neck4;
    private final ModelRenderer Neck5;
    private final ModelRenderer BottomJaw;
    private final ModelRenderer FinBackRight;
    private final ModelRenderer FinBackLeft;
    private final ModelRenderer FinFrontLeft;
    private final ModelRenderer FinFrontRight;
    private final ModelRenderer Tail4;
    private final ModelRenderer Tail5;
    private final ModelRenderer Tail6;
    private final ModelRenderer TopJaw;
    private final ModelRenderer RightEye;
    private final ModelRenderer LeftEye;

    public ModelSeaMonster(float f1) {
        this.wingspeed = f1;
        this.textureWidth = 256;
        this.textureHeight = 128;
        this.TailTip = new ModelRenderer(this, 158, 36);
        this.TailTip.addBox(-1.0f, -1.0f, 0.0f, 2, 2, 6);
        this.TailTip.setRotationPoint(0.0f, 16.0f, 70.0f);
        this.TailTip.mirror = true;
        this.setRotation(this.TailTip, 0.0f, 0.0f, 0.0f);
        this.TailBase = new ModelRenderer(this, 68, 64);
        this.TailBase.addBox(-7.0f, -7.0f, 0.0f, 14, 14, 12);
        this.TailBase.setRotationPoint(0.0f, 16.0f, 26.0f);
        this.TailBase.mirror = true;
        this.setRotation(this.TailBase, 0.0f, 0.0f, 0.0f);
        this.Tail2 = new ModelRenderer(this, 74, 90);
        this.Tail2.addBox(-6.0f, -6.0f, 0.0f, 12, 12, 8);
        this.Tail2.setRotationPoint(0.0f, 16.0f, 38.0f);
        this.Tail2.mirror = true;
        this.setRotation(this.Tail2, 0.0f, 0.0f, 0.0f);
        this.Tail3 = new ModelRenderer(this, 78, 110);
        this.Tail3.addBox(-5.0f, -5.0f, 0.0f, 10, 10, 6);
        this.Tail3.setRotationPoint(0.0f, 16.0f, 46.0f);
        this.Tail3.mirror = true;
        this.setRotation(this.Tail3, 0.0f, 0.0f, 0.0f);
        this.BodyBack = new ModelRenderer(this, 62, 32);
        this.BodyBack.addBox(-8.0f, -8.0f, 0.0f, 16, 16, 16);
        this.BodyBack.setRotationPoint(0.0f, 16.0f, 10.0f);
        this.BodyBack.mirror = true;
        this.setRotation(this.BodyBack, 0.0f, 0.0f, 0.0f);
        this.Neck6 = new ModelRenderer(this, 20, 28);
        this.Neck6.addBox(-2.0f, -6.0f, -2.0f, 4, 6, 4);
        this.Neck6.setRotationPoint(0.0f, -21.0f, -25.0f);
        this.Neck6.mirror = true;
        this.setRotation(this.Neck6, 1.22173f, 0.0f, 0.0f);
        this.BodyFront = new ModelRenderer(this, 62, 0);
        this.BodyFront.addBox(-8.0f, -8.0f, -16.0f, 16, 16, 16);
        this.BodyFront.setRotationPoint(0.0f, 16.0f, 10.0f);
        this.BodyFront.mirror = true;
        this.setRotation(this.BodyFront, 0.0f, 0.0f, 0.0f);
        this.NeckBase = new ModelRenderer(this, 8, 96);
        this.NeckBase.addBox(-5.0f, -10.0f, -5.0f, 10, 10, 10);
        this.NeckBase.setRotationPoint(0.0f, 12.0f, -2.0f);
        this.NeckBase.mirror = true;
        this.setRotation(this.NeckBase, 0.7853982f, 0.0f, 0.0f);
        this.Neck2 = new ModelRenderer(this, 14, 78);
        this.Neck2.addBox(-3.0f, -10.0f, -4.0f, 6, 10, 8);
        this.Neck2.setRotationPoint(0.0f, 6.0f, -9.0f);
        this.Neck2.mirror = true;
        this.setRotation(this.Neck2, 0.6981317f, 0.0f, 0.0f);
        this.Neck3 = new ModelRenderer(this, 16, 62);
        this.Neck3.addBox(-3.0f, -10.0f, -3.0f, 6, 10, 6);
        this.Neck3.setRotationPoint(0.0f, -1.0f, -15.0f);
        this.Neck3.mirror = true;
        this.setRotation(this.Neck3, 0.5235988f, 0.0f, 0.0f);
        this.Neck4 = new ModelRenderer(this, 20, 48);
        this.Neck4.addBox(-2.0f, -10.0f, -2.0f, 4, 10, 4);
        this.Neck4.setRotationPoint(0.0f, -9.0f, -20.0f);
        this.Neck4.mirror = true;
        this.setRotation(this.Neck4, 0.2617994f, 0.0f, 0.0f);
        this.Neck5 = new ModelRenderer(this, 20, 38);
        this.Neck5.addBox(-2.0f, -6.0f, -2.0f, 4, 6, 4);
        this.Neck5.setRotationPoint(0.0f, -17.6f, -22.0f);
        this.Neck5.mirror = true;
        this.setRotation(this.Neck5, 0.7853982f, 0.0f, 0.0f);
        this.BottomJaw = new ModelRenderer(this, 10, 0);
        this.BottomJaw.addBox(-4.0f, 0.0f, -10.0f, 8, 3, 10);
        this.BottomJaw.setRotationPoint(0.0f, -23.0f, -29.0f);
        this.BottomJaw.mirror = true;
        this.setRotation(this.BottomJaw, 0.1745329f, 0.0f, 0.0f);
        this.FinBackRight = new ModelRenderer(this, 132, 95);
        this.FinBackRight.addBox(-8.0f, 0.0f, 0.0f, 8, 1, 16);
        this.FinBackRight.setRotationPoint(-7.0f, 16.0f, 16.0f);
        this.FinBackRight.mirror = true;
        this.setRotation(this.FinBackRight, -0.5235988f, -0.6981317f, 0.0f);
        this.FinBackLeft = new ModelRenderer(this, 132, 61);
        this.FinBackLeft.addBox(0.0f, 0.0f, 0.0f, 8, 1, 16);
        this.FinBackLeft.setRotationPoint(7.0f, 16.0f, 16.0f);
        this.FinBackLeft.mirror = true;
        this.setRotation(this.FinBackLeft, -0.5235988f, 0.6981317f, 0.0f);
        this.FinFrontLeft = new ModelRenderer(this, 132, 44);
        this.FinFrontLeft.addBox(0.0f, 0.0f, 0.0f, 8, 1, 16);
        this.FinFrontLeft.setRotationPoint(7.0f, 16.0f, -1.0f);
        this.FinFrontLeft.mirror = true;
        this.setRotation(this.FinFrontLeft, -0.5235988f, 0.6981317f, 0.0f);
        this.FinFrontRight = new ModelRenderer(this, 132, 78);
        this.FinFrontRight.addBox(-8.0f, 0.0f, 0.0f, 8, 1, 16);
        this.FinFrontRight.setRotationPoint(-7.0f, 16.0f, -1.0f);
        this.FinFrontRight.mirror = true;
        this.setRotation(this.FinFrontRight, -0.5235988f, -0.6981317f, 0.0f);
        this.Tail4 = new ModelRenderer(this, 152, 0);
        this.Tail4.addBox(-4.0f, -4.0f, 0.0f, 8, 8, 6);
        this.Tail4.setRotationPoint(0.0f, 16.0f, 52.0f);
        this.Tail4.mirror = true;
        this.setRotation(this.Tail4, 0.0f, 0.0f, 0.0f);
        this.Tail5 = new ModelRenderer(this, 154, 14);
        this.Tail5.addBox(-3.0f, -3.0f, 0.0f, 6, 6, 6);
        this.Tail5.setRotationPoint(0.0f, 16.0f, 58.0f);
        this.Tail5.mirror = true;
        this.setRotation(this.Tail5, 0.0f, 0.0f, 0.0f);
        this.Tail6 = new ModelRenderer(this, 156, 26);
        this.Tail6.addBox(-2.0f, -2.0f, 0.0f, 4, 4, 6);
        this.Tail6.setRotationPoint(0.0f, 16.0f, 64.0f);
        this.Tail6.mirror = true;
        this.setRotation(this.Tail6, 0.0f, 0.0f, 0.0f);
        this.TopJaw = new ModelRenderer(this, 10, 13);
        this.TopJaw.addBox(-4.0f, -4.0f, -10.0f, 8, 4, 10);
        this.TopJaw.setRotationPoint(0.0f, -23.0f, -29.0f);
        this.TopJaw.mirror = true;
        this.setRotation(this.TopJaw, 0.0f, 0.0f, 0.0f);
        this.RightEye = new ModelRenderer(this, 46, 16);
        this.RightEye.addBox(-3.0f, -6.0f, -5.0f, 2, 2, 1);
        this.RightEye.setRotationPoint(0.0f, -23.0f, -29.0f);
        this.RightEye.mirror = true;
        this.setRotation(this.RightEye, 0.0f, 0.0f, 0.0f);
        this.LeftEye = new ModelRenderer(this, 4, 16);
        this.LeftEye.addBox(1.0f, -6.0f, -5.0f, 2, 2, 1);
        this.LeftEye.setRotationPoint(0.0f, -23.0f, -29.0f);
        this.LeftEye.mirror = true;
        this.setRotation(this.LeftEye, 0.0f, 0.0f, 0.0f);
    }

    public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        if (frameEntity == null) return;
        Entity entity=frameEntity;
        float f=frameSwing, f1=frameAmount, f2=frameAge, f3=frameYaw, f4=framePitch, f5=0.0625F;
        matrixStack.push();
this.TailTip.setRotationPoint(0.0f, 16.0f, 70.0f);
this.setRotation(this.TailTip, 0.0f, 0.0f, 0.0f);
this.TailBase.setRotationPoint(0.0f, 16.0f, 26.0f);
this.setRotation(this.TailBase, 0.0f, 0.0f, 0.0f);
this.Tail2.setRotationPoint(0.0f, 16.0f, 38.0f);
this.setRotation(this.Tail2, 0.0f, 0.0f, 0.0f);
this.Tail3.setRotationPoint(0.0f, 16.0f, 46.0f);
this.setRotation(this.Tail3, 0.0f, 0.0f, 0.0f);
this.BodyBack.setRotationPoint(0.0f, 16.0f, 10.0f);
this.setRotation(this.BodyBack, 0.0f, 0.0f, 0.0f);
this.Neck6.setRotationPoint(0.0f, -21.0f, -25.0f);
this.setRotation(this.Neck6, 1.22173f, 0.0f, 0.0f);
this.BodyFront.setRotationPoint(0.0f, 16.0f, 10.0f);
this.setRotation(this.BodyFront, 0.0f, 0.0f, 0.0f);
this.NeckBase.setRotationPoint(0.0f, 12.0f, -2.0f);
this.setRotation(this.NeckBase, 0.7853982f, 0.0f, 0.0f);
this.Neck2.setRotationPoint(0.0f, 6.0f, -9.0f);
this.setRotation(this.Neck2, 0.6981317f, 0.0f, 0.0f);
this.Neck3.setRotationPoint(0.0f, -1.0f, -15.0f);
this.setRotation(this.Neck3, 0.5235988f, 0.0f, 0.0f);
this.Neck4.setRotationPoint(0.0f, -9.0f, -20.0f);
this.setRotation(this.Neck4, 0.2617994f, 0.0f, 0.0f);
this.Neck5.setRotationPoint(0.0f, -17.6f, -22.0f);
this.setRotation(this.Neck5, 0.7853982f, 0.0f, 0.0f);
this.BottomJaw.setRotationPoint(0.0f, -23.0f, -29.0f);
this.setRotation(this.BottomJaw, 0.1745329f, 0.0f, 0.0f);
this.FinBackRight.setRotationPoint(-7.0f, 16.0f, 16.0f);
this.setRotation(this.FinBackRight, -0.5235988f, -0.6981317f, 0.0f);
this.FinBackLeft.setRotationPoint(7.0f, 16.0f, 16.0f);
this.setRotation(this.FinBackLeft, -0.5235988f, 0.6981317f, 0.0f);
this.FinFrontLeft.setRotationPoint(7.0f, 16.0f, -1.0f);
this.setRotation(this.FinFrontLeft, -0.5235988f, 0.6981317f, 0.0f);
this.FinFrontRight.setRotationPoint(-7.0f, 16.0f, -1.0f);
this.setRotation(this.FinFrontRight, -0.5235988f, -0.6981317f, 0.0f);
this.Tail4.setRotationPoint(0.0f, 16.0f, 52.0f);
this.setRotation(this.Tail4, 0.0f, 0.0f, 0.0f);
this.Tail5.setRotationPoint(0.0f, 16.0f, 58.0f);
this.setRotation(this.Tail5, 0.0f, 0.0f, 0.0f);
this.Tail6.setRotationPoint(0.0f, 16.0f, 64.0f);
this.setRotation(this.Tail6, 0.0f, 0.0f, 0.0f);
this.TopJaw.setRotationPoint(0.0f, -23.0f, -29.0f);
this.setRotation(this.TopJaw, 0.0f, 0.0f, 0.0f);
this.RightEye.setRotationPoint(0.0f, -23.0f, -29.0f);
this.setRotation(this.RightEye, 0.0f, 0.0f, 0.0f);
this.LeftEye.setRotationPoint(0.0f, -23.0f, -29.0f);
this.setRotation(this.LeftEye, 0.0f, 0.0f, 0.0f);

        SeaMonsterEntity.CustomEntity e = (SeaMonsterEntity.CustomEntity)entity;
        this.legacyAngles(f, f1, f2, f3, f4, f5, entity);
        float newangle = 0.0f;
        float pi4 = 0.7853982f;
        newangle = (double)f1 > 0.1 || e.getAttacking() != 0 ? MathHelper.cos((float)(f2 * 1.3f * this.wingspeed)) * (float)Math.PI * 0.2f * f1 : 0.0f;
        this.TailBase.rotateAngleY = newangle / 7.0f;
        this.Tail2.rotationPointZ = this.TailBase.rotationPointZ + (float)Math.cos(this.TailBase.rotateAngleY) * 10.0f;
        this.Tail2.rotationPointX = this.TailBase.rotationPointX + (float)Math.sin(this.TailBase.rotateAngleY) * 10.0f;
        this.Tail2.rotateAngleY = newangle / 6.0f;
        this.Tail3.rotationPointZ = this.Tail2.rotationPointZ + (float)Math.cos(this.Tail2.rotateAngleY) * 7.0f;
        this.Tail3.rotationPointX = this.Tail2.rotationPointX + (float)Math.sin(this.Tail2.rotateAngleY) * 7.0f;
        this.Tail3.rotateAngleY = newangle / 5.0f;
        this.Tail4.rotationPointZ = this.Tail3.rotationPointZ + (float)Math.cos(this.Tail3.rotateAngleY) * 5.0f;
        this.Tail4.rotationPointX = this.Tail3.rotationPointX + (float)Math.sin(this.Tail3.rotateAngleY) * 5.0f;
        this.Tail4.rotateAngleY = newangle / 4.0f;
        this.Tail5.rotationPointZ = this.Tail4.rotationPointZ + (float)Math.cos(this.Tail4.rotateAngleY) * 5.0f;
        this.Tail5.rotationPointX = this.Tail4.rotationPointX + (float)Math.sin(this.Tail4.rotateAngleY) * 5.0f;
        this.Tail5.rotateAngleY = newangle / 3.0f;
        this.Tail6.rotationPointZ = this.Tail5.rotationPointZ + (float)Math.cos(this.Tail5.rotateAngleY) * 5.0f;
        this.Tail6.rotationPointX = this.Tail5.rotationPointX + (float)Math.sin(this.Tail5.rotateAngleY) * 5.0f;
        this.Tail6.rotateAngleY = newangle / 2.0f;
        this.TailTip.rotationPointZ = this.Tail6.rotationPointZ + (float)Math.cos(this.Tail6.rotateAngleY) * 5.0f;
        this.TailTip.rotationPointX = this.Tail6.rotationPointX + (float)Math.sin(this.Tail6.rotateAngleY) * 5.0f;
        this.TailTip.rotateAngleY = newangle;
        newangle = (double)f1 > 0.1 || e.getAttacking() != 0 ? MathHelper.cos((float)(f2 * 1.2f * this.wingspeed)) * (float)Math.PI * 0.2f * f1 : MathHelper.cos((float)(f2 * 1.2f * this.wingspeed)) * (float)Math.PI * 0.02f;
        this.FinFrontLeft.rotateAngleX = newangle - 0.523f;
        this.FinFrontLeft.rotateAngleY = newangle + 0.698f;
        this.FinBackLeft.rotateAngleX = -newangle - 0.523f;
        this.FinBackLeft.rotateAngleY = -newangle + 0.698f;
        this.FinFrontRight.rotateAngleX = newangle - 0.523f;
        this.FinFrontRight.rotateAngleY = newangle - 0.698f;
        this.FinBackRight.rotateAngleX = -newangle - 0.523f;
        this.FinBackRight.rotateAngleY = -newangle - 0.698f;
        newangle = (double)f1 > 0.1 || e.getAttacking() != 0 ? 0.455f * f1 + MathHelper.cos((float)(f2 * 0.9f * this.wingspeed)) * (float)Math.PI * 0.25f * f1 : MathHelper.cos((float)(f2 * 0.3f * this.wingspeed)) * (float)Math.PI * 0.02f;
        this.NeckBase.rotateAngleX = 0.455f + newangle / 5.0f;
        this.Neck2.rotationPointZ = this.NeckBase.rotationPointZ - (float)Math.sin(this.NeckBase.rotateAngleX) * 9.0f;
        this.Neck2.rotationPointY = this.NeckBase.rotationPointY - (float)Math.cos(this.NeckBase.rotateAngleX) * 9.0f;
        this.Neck2.rotateAngleX = this.NeckBase.rotateAngleX + newangle / 4.0f;
        this.Neck3.rotationPointZ = this.Neck2.rotationPointZ - (float)Math.sin(this.Neck2.rotateAngleX) * 9.0f;
        this.Neck3.rotationPointY = this.Neck2.rotationPointY - (float)Math.cos(this.Neck2.rotateAngleX) * 9.0f;
        this.Neck3.rotateAngleX = this.Neck2.rotateAngleX + newangle / 3.0f;
        this.Neck4.rotationPointZ = this.Neck3.rotationPointZ - (float)Math.sin(this.Neck3.rotateAngleX) * 9.0f;
        this.Neck4.rotationPointY = this.Neck3.rotationPointY - (float)Math.cos(this.Neck3.rotateAngleX) * 9.0f;
        this.Neck4.rotateAngleX = this.Neck3.rotateAngleX + newangle / 2.0f;
        this.Neck5.rotationPointZ = this.Neck4.rotationPointZ - (float)Math.sin(this.Neck4.rotateAngleX) * 9.0f;
        this.Neck5.rotationPointY = this.Neck4.rotationPointY - (float)Math.cos(this.Neck4.rotateAngleX) * 9.0f;
        this.Neck5.rotateAngleX = this.Neck4.rotateAngleX - newangle / 2.0f;
        this.Neck6.rotationPointZ = this.Neck5.rotationPointZ - (float)Math.sin(this.Neck5.rotateAngleX) * 5.0f;
        this.Neck6.rotationPointY = this.Neck5.rotationPointY - (float)Math.cos(this.Neck5.rotateAngleX) * 5.0f;
        this.Neck6.rotateAngleX = this.Neck5.rotateAngleX - newangle / 3.0f;
        this.RightEye.rotationPointZ = this.TopJaw.rotationPointZ = this.Neck6.rotationPointZ - (float)Math.sin(this.Neck6.rotateAngleX) * 5.0f;
        this.LeftEye.rotationPointZ = this.TopJaw.rotationPointZ;
        this.BottomJaw.rotationPointZ = this.TopJaw.rotationPointZ;
        this.RightEye.rotationPointY = this.TopJaw.rotationPointY = this.Neck6.rotationPointY - (float)Math.cos(this.Neck6.rotateAngleX) * 5.0f;
        this.LeftEye.rotationPointY = this.TopJaw.rotationPointY;
        this.BottomJaw.rotationPointY = this.TopJaw.rotationPointY;
        this.RightEye.rotateAngleY = this.TopJaw.rotateAngleY = (newangle = (float)Math.toRadians(f3) * 0.5f);
        this.LeftEye.rotateAngleY = this.TopJaw.rotateAngleY;
        this.BottomJaw.rotateAngleY = this.TopJaw.rotateAngleY;
        if (e.getAttacking() != 0) {
            newangle = MathHelper.cos((float)(f2 * 1.7f * this.wingspeed)) * (float)Math.PI * 0.17f;
            this.BottomJaw.rotateAngleX = 0.45f + newangle;
        } else {
            newangle = MathHelper.cos((float)(f2 * 0.2f * this.wingspeed)) * (float)Math.PI * 0.05f;
            this.BottomJaw.rotateAngleX = 0.17f + newangle;
        }
        this.TailTip.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.TailBase.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.Tail2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.Tail3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.BodyBack.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.Neck6.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.BodyFront.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.NeckBase.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.Neck2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.Neck3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.Neck4.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.Neck5.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.BottomJaw.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.FinBackRight.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.FinBackLeft.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.FinFrontLeft.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.FinFrontRight.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.Tail4.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.Tail5.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.Tail6.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.TopJaw.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.RightEye.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.LeftEye.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    
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
