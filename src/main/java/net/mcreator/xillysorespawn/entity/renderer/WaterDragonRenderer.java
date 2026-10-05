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
import net.mcreator.xillysorespawn.entity.WaterDragonEntity;
import net.mcreator.xillysorespawn.entity.OreSpawnLogic;

@OnlyIn(Dist.CLIENT)
public class WaterDragonRenderer {
    private static final String TEXTURE = "xillys_orespawn:textures/entities/waterdragon.png";
    public static class ModelRegisterHandler {
        @SubscribeEvent @OnlyIn(Dist.CLIENT)
        public void registerModels(ModelRegistryEvent event) {
            RenderingRegistry.registerEntityRenderingHandler(WaterDragonEntity.entity, manager ->
                new MobRenderer(manager, new ModelWaterDragon(0.5F), 0.935F) {
                    @Override public ResourceLocation getEntityTexture(Entity entity) { return new ResourceLocation(TEXTURE); }
                    @Override protected void preRenderCallback(LivingEntity entity, MatrixStack stack, float partialTick) {
                        float modelScale = entity.isChild() ? 0.55F : 1.1F;
                        stack.scale(modelScale, modelScale, modelScale);
                    }
                });
        }
    }

public static class ModelWaterDragon extends EntityModel<Entity> {
    // Exact legacy render order recovered from bytecode; do not add a second addChild rig.
    private Entity frameEntity;
    private float frameSwing, frameAmount, frameAge, frameYaw, framePitch;
    @Override public void setRotationAngles(Entity entity, float swing, float amount, float age, float yaw, float pitch) {
        frameEntity=entity; frameSwing=swing; frameAmount=amount; frameAge=age; frameYaw=yaw; framePitch=pitch;
    }

    private float wingspeed = 1.0f;
    private final ModelRenderer Head;
    private final ModelRenderer neck1;
    private final ModelRenderer body1;
    private final ModelRenderer Leg8;
    private final ModelRenderer Leg2;
    private final ModelRenderer Leg7;
    private final ModelRenderer Leg1;
    private final ModelRenderer neck2;
    private final ModelRenderer neck3;
    private final ModelRenderer neck4;
    private final ModelRenderer body2;
    private final ModelRenderer body3;
    private final ModelRenderer body4;
    private final ModelRenderer tail1;
    private final ModelRenderer tailmiddle;
    private final ModelRenderer tailtop;
    private final ModelRenderer tailbottom;
    private final ModelRenderer nose;
    private final ModelRenderer headfin;
    private final ModelRenderer rightear;
    private final ModelRenderer leftear;
    private final ModelRenderer neackfin;
    private final ModelRenderer Bodyfin;
    private final ModelRenderer jaw;

    public ModelWaterDragon(float f1) {
        this.wingspeed = f1;
        this.textureWidth = 128;
        this.textureHeight = 128;
        this.Head = new ModelRenderer(this, 79, 64);
        this.Head.addBox(-4.0f, -4.0f, -8.0f, 7, 8, 8);
        this.Head.setRotationPoint(0.0f, 0.0f, -3.0f);
        this.Head.mirror = true;
        this.setRotation(this.Head, 0.0f, 0.0f, 0.0f);
        this.neck1 = new ModelRenderer(this, 29, 70);
        this.neck1.addBox(-2.0f, 0.0f, -3.0f, 5, 5, 5);
        this.neck1.setRotationPoint(-1.0f, 4.0f, -5.0f);
        this.neck1.mirror = true;
        this.setRotation(this.neck1, -0.1858931f, 0.0f, 0.0f);
        this.body1 = new ModelRenderer(this, 0, 33);
        this.body1.addBox(-5.0f, -4.0f, -6.0f, 9, 9, 9);
        this.body1.setRotationPoint(0.0f, 19.0f, 2.0f);
        this.body1.mirror = true;
        this.setRotation(this.body1, 0.0f, 0.0f, 0.0f);
        this.Leg8 = new ModelRenderer(this, 23, 25);
        this.Leg8.addBox(0.0f, -1.0f, -1.0f, 9, 2, 3);
        this.Leg8.setRotationPoint(3.0f, 22.0f, -2.0f);
        this.Leg8.mirror = true;
        this.setRotation(this.Leg8, 0.0f, 0.5759587f, 0.1919862f);
        this.Leg2 = new ModelRenderer(this, 80, 18);
        this.Leg2.addBox(0.0f, -1.0f, -1.0f, 9, 2, 3);
        this.Leg2.setRotationPoint(2.0f, 22.0f, 13.0f);
        this.Leg2.mirror = true;
        this.setRotation(this.Leg2, 0.0f, -0.5759587f, 0.1919862f);
        this.Leg7 = new ModelRenderer(this, 23, 18);
        this.Leg7.addBox(-9.0f, -1.0f, -1.0f, 9, 2, 3);
        this.Leg7.setRotationPoint(-4.0f, 22.0f, -1.0f);
        this.Leg7.mirror = true;
        this.setRotation(this.Leg7, 0.0f, -0.5759587f, -0.1919862f);
        this.Leg1 = new ModelRenderer(this, 80, 25);
        this.Leg1.addBox(-9.0f, -1.0f, -2.0f, 9, 2, 3);
        this.Leg1.setRotationPoint(-3.0f, 22.0f, 14.0f);
        this.Leg1.mirror = true;
        this.setRotation(this.Leg1, 0.0f, 0.5759587f, -0.1919862f);
        this.neck2 = new ModelRenderer(this, 0, 11);
        this.neck2.addBox(-2.0f, 0.0f, -2.0f, 5, 5, 5);
        this.neck2.setRotationPoint(-1.0f, 9.0f, -7.0f);
        this.neck2.mirror = true;
        this.setRotation(this.neck2, 0.1115358f, 0.0f, 0.0f);
        this.neck3 = new ModelRenderer(this, 0, 22);
        this.neck3.addBox(-2.0f, 0.0f, -2.0f, 5, 5, 5);
        this.neck3.setRotationPoint(-1.0f, 14.0f, -6.0f);
        this.neck3.mirror = true;
        this.setRotation(this.neck3, 0.4461433f, 0.0f, 0.0f);
        this.neck4 = new ModelRenderer(this, 26, 12);
        this.neck4.addBox(-3.0f, 0.0f, -2.0f, 5, 3, 3);
        this.neck4.setRotationPoint(0.0f, 18.0f, -4.0f);
        this.neck4.mirror = true;
        this.setRotation(this.neck4, 1.226894f, 0.0f, 0.0f);
        this.body2 = new ModelRenderer(this, 0, 52);
        this.body2.addBox(-5.0f, -5.0f, 0.0f, 7, 7, 9);
        this.body2.setRotationPoint(1.0f, 21.0f, 5.0f);
        this.body2.mirror = true;
        this.setRotation(this.body2, 0.0f, 0.0f, 0.0f);
        this.body3 = new ModelRenderer(this, 0, 69);
        this.body3.addBox(-3.0f, -3.0f, 0.0f, 5, 5, 7);
        this.body3.setRotationPoint(0.0f, 20.0f, 14.0f);
        this.body3.mirror = true;
        this.setRotation(this.body3, 0.0f, 0.0f, 0.0f);
        this.body4 = new ModelRenderer(this, 0, 89);
        this.body4.addBox(-1.0f, -1.0f, 0.0f, 3, 3, 5);
        this.body4.setRotationPoint(-1.0f, 19.0f, 21.0f);
        this.body4.mirror = true;
        this.setRotation(this.body4, 0.0f, 0.0f, 0.0f);
        this.tail1 = new ModelRenderer(this, 0, 82);
        this.tail1.addBox(0.0f, 0.0f, 0.0f, 1, 2, 3);
        this.tail1.setRotationPoint(-1.0f, 19.0f, 25.0f);
        this.tail1.mirror = true;
        this.setRotation(this.tail1, 0.0f, 0.0f, 0.0f);
        this.tailmiddle = new ModelRenderer(this, 55, 37);
        this.tailmiddle.addBox(-1.0f, -6.0f, 0.0f, 2, 11, 9);
        this.tailmiddle.setRotationPoint(0.0f, 19.0f, 28.0f);
        this.tailmiddle.mirror = true;
        this.setRotation(this.tailmiddle, 0.0f, 0.0f, 0.0f);
        this.tailtop = new ModelRenderer(this, 82, 36);
        this.tailtop.addBox(-1.0f, -11.0f, 0.0f, 2, 11, 9);
        this.tailtop.setRotationPoint(0.0f, 14.0f, 28.0f);
        this.tailtop.mirror = true;
        this.setRotation(this.tailtop, -0.6320364f, 0.0f, 0.0f);
        this.tailbottom = new ModelRenderer(this, 56, 60);
        this.tailbottom.addBox(0.0f, 0.0f, 0.0f, 2, 11, 9);
        this.tailbottom.setRotationPoint(-1.0f, 23.0f, 28.0f);
        this.tailbottom.mirror = true;
        this.setRotation(this.tailbottom, 0.6320361f, 0.0f, -0.0174533f);
        this.nose = new ModelRenderer(this, 54, 19);
        this.nose.addBox(-3.0f, -2.0f, -5.0f, 5, 5, 5);
        this.nose.setRotationPoint(0.0f, -2.0f, -11.0f);
        this.nose.mirror = true;
        this.setRotation(this.nose, 0.0f, 0.0f, 0.0f);
        this.headfin = new ModelRenderer(this, 0, 99);
        this.headfin.addBox(0.0f, -5.0f, 0.0f, 0, 10, 9);
        this.headfin.setRotationPoint(0.0f, -4.0f, -6.0f);
        this.headfin.mirror = true;
        this.setRotation(this.headfin, 0.1396263f, 0.0f, 0.0f);
        this.rightear = new ModelRenderer(this, 38, 32);
        this.rightear.addBox(0.0f, 0.0f, 0.0f, 0, 5, 5);
        this.rightear.setRotationPoint(-4.0f, -2.0f, -5.0f);
        this.rightear.mirror = true;
        this.setRotation(this.rightear, 0.0698132f, -0.418879f, 0.0f);
        this.leftear = new ModelRenderer(this, 38, 32);
        this.leftear.addBox(0.0f, 0.0f, 0.0f, 0, 5, 5);
        this.leftear.setRotationPoint(3.0f, -2.0f, -5.0f);
        this.leftear.mirror = true;
        this.setRotation(this.leftear, 0.0698132f, 0.418879f, 0.0f);
        this.neackfin = new ModelRenderer(this, 42, 47);
        this.neackfin.addBox(0.0f, -1.0f, 0.0f, 0, 5, 5);
        this.neackfin.setRotationPoint(0.0f, 3.0f, -3.0f);
        this.neackfin.mirror = true;
        this.setRotation(this.neackfin, -0.185895f, 0.0f, 0.0f);
        this.Bodyfin = new ModelRenderer(this, 21, 91);
        this.Bodyfin.addBox(0.0f, -6.0f, -3.0f, 0, 10, 9);
        this.Bodyfin.setRotationPoint(0.0f, 15.0f, 2.0f);
        this.Bodyfin.mirror = true;
        this.setRotation(this.Bodyfin, -0.0698132f, 0.0f, 0.0f);
        this.jaw = new ModelRenderer(this, 76, 8);
        this.jaw.addBox(-2.0f, 0.0f, -5.0f, 5, 1, 5);
        this.jaw.setRotationPoint(-1.0f, 3.0f, -10.0f);
        this.jaw.mirror = true;
        this.setRotation(this.jaw, 0.0f, 0.0f, 0.0f);
    }

    public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        if (frameEntity == null) return;
        Entity entity=frameEntity;
        float f=frameSwing, f1=frameAmount, f2=frameAge, f3=frameYaw, f4=framePitch, f5=0.0625F;
        matrixStack.push();
this.Head.setRotationPoint(0.0f, 0.0f, -3.0f);
this.setRotation(this.Head, 0.0f, 0.0f, 0.0f);
this.neck1.setRotationPoint(-1.0f, 4.0f, -5.0f);
this.setRotation(this.neck1, -0.1858931f, 0.0f, 0.0f);
this.body1.setRotationPoint(0.0f, 19.0f, 2.0f);
this.setRotation(this.body1, 0.0f, 0.0f, 0.0f);
this.Leg8.setRotationPoint(3.0f, 22.0f, -2.0f);
this.setRotation(this.Leg8, 0.0f, 0.5759587f, 0.1919862f);
this.Leg2.setRotationPoint(2.0f, 22.0f, 13.0f);
this.setRotation(this.Leg2, 0.0f, -0.5759587f, 0.1919862f);
this.Leg7.setRotationPoint(-4.0f, 22.0f, -1.0f);
this.setRotation(this.Leg7, 0.0f, -0.5759587f, -0.1919862f);
this.Leg1.setRotationPoint(-3.0f, 22.0f, 14.0f);
this.setRotation(this.Leg1, 0.0f, 0.5759587f, -0.1919862f);
this.neck2.setRotationPoint(-1.0f, 9.0f, -7.0f);
this.setRotation(this.neck2, 0.1115358f, 0.0f, 0.0f);
this.neck3.setRotationPoint(-1.0f, 14.0f, -6.0f);
this.setRotation(this.neck3, 0.4461433f, 0.0f, 0.0f);
this.neck4.setRotationPoint(0.0f, 18.0f, -4.0f);
this.setRotation(this.neck4, 1.226894f, 0.0f, 0.0f);
this.body2.setRotationPoint(1.0f, 21.0f, 5.0f);
this.setRotation(this.body2, 0.0f, 0.0f, 0.0f);
this.body3.setRotationPoint(0.0f, 20.0f, 14.0f);
this.setRotation(this.body3, 0.0f, 0.0f, 0.0f);
this.body4.setRotationPoint(-1.0f, 19.0f, 21.0f);
this.setRotation(this.body4, 0.0f, 0.0f, 0.0f);
this.tail1.setRotationPoint(-1.0f, 19.0f, 25.0f);
this.setRotation(this.tail1, 0.0f, 0.0f, 0.0f);
this.tailmiddle.setRotationPoint(0.0f, 19.0f, 28.0f);
this.setRotation(this.tailmiddle, 0.0f, 0.0f, 0.0f);
this.tailtop.setRotationPoint(0.0f, 14.0f, 28.0f);
this.setRotation(this.tailtop, -0.6320364f, 0.0f, 0.0f);
this.tailbottom.setRotationPoint(-1.0f, 23.0f, 28.0f);
this.setRotation(this.tailbottom, 0.6320361f, 0.0f, -0.0174533f);
this.nose.setRotationPoint(0.0f, -2.0f, -11.0f);
this.setRotation(this.nose, 0.0f, 0.0f, 0.0f);
this.headfin.setRotationPoint(0.0f, -4.0f, -6.0f);
this.setRotation(this.headfin, 0.1396263f, 0.0f, 0.0f);
this.rightear.setRotationPoint(-4.0f, -2.0f, -5.0f);
this.setRotation(this.rightear, 0.0698132f, -0.418879f, 0.0f);
this.leftear.setRotationPoint(3.0f, -2.0f, -5.0f);
this.setRotation(this.leftear, 0.0698132f, 0.418879f, 0.0f);
this.neackfin.setRotationPoint(0.0f, 3.0f, -3.0f);
this.setRotation(this.neackfin, -0.185895f, 0.0f, 0.0f);
this.Bodyfin.setRotationPoint(0.0f, 15.0f, 2.0f);
this.setRotation(this.Bodyfin, -0.0698132f, 0.0f, 0.0f);
this.jaw.setRotationPoint(-1.0f, 3.0f, -10.0f);
this.setRotation(this.jaw, 0.0f, 0.0f, 0.0f);

        WaterDragonEntity.CustomEntity e = (WaterDragonEntity.CustomEntity)entity;
        this.legacyAngles(f, f1, f2, f3, f4, f5, entity);
        float newangle = 0.0f;
        float pi4 = 0.7853982f;
        float root13 = (float)Math.sqrt(13.0);
        float root20 = (float)Math.sqrt(20.0);
        newangle = (double)f1 > 0.1 ? MathHelper.cos((float)(f2 * 1.3f * this.wingspeed)) * (float)Math.PI * 0.2f * f1 : 0.0f;
        this.body3.rotateAngleY = MathHelper.cos((float)(f2 * 1.3f * this.wingspeed)) * (float)Math.PI * 0.4f * f1;
        this.body4.rotationPointZ = this.body3.rotationPointZ + (float)Math.cos(this.body3.rotateAngleY) * 7.0f;
        this.body4.rotationPointX = this.body3.rotationPointX - 1.0f + (float)Math.sin(this.body3.rotateAngleY) * 7.0f;
        this.body4.rotateAngleY = MathHelper.cos((float)(f2 * 1.3f * this.wingspeed - pi4)) * (float)Math.PI * 0.4f * f1;
        this.tail1.rotationPointZ = this.body4.rotationPointZ + (float)Math.cos(this.body4.rotateAngleY) * 5.0f;
        this.tail1.rotationPointX = this.body4.rotationPointX + (float)Math.sin(this.body4.rotateAngleY) * 5.0f;
        this.tail1.rotateAngleY = MathHelper.cos((float)(f2 * 1.3f * this.wingspeed - 2.0f * pi4)) * (float)Math.PI * 0.4f * f1;
        this.tailmiddle.rotationPointZ = this.tail1.rotationPointZ + (float)Math.cos(this.tail1.rotateAngleY) * 3.0f;
        this.tailmiddle.rotationPointX = this.tail1.rotationPointX + (float)Math.sin(this.tail1.rotateAngleY) * 3.0f;
        this.tailtop.rotateAngleY = this.tailmiddle.rotateAngleY = MathHelper.cos((float)(f2 * 1.3f * this.wingspeed - 3.0f * pi4)) * (float)Math.PI * 0.4f * f1;
        this.tailtop.rotationPointZ = this.tailmiddle.rotationPointZ;
        this.tailtop.rotationPointX = this.tailmiddle.rotationPointX;
        this.tailbottom.rotateAngleY = this.tailmiddle.rotateAngleY;
        this.tailbottom.rotationPointZ = this.tailmiddle.rotationPointZ;
        this.tailbottom.rotationPointX = this.tailmiddle.rotationPointX;
        this.Leg8.rotateAngleY = 0.58f + newangle;
        this.Leg2.rotateAngleY = -0.58f + newangle;
        this.Leg7.rotateAngleY = -0.58f - newangle;
        this.Leg1.rotateAngleY = 0.58f - newangle;
        newangle = MathHelper.cos((float)(f2 * 0.8f * this.wingspeed)) * (float)Math.PI * 0.1f;
        this.leftear.rotateAngleY = 0.62f + newangle;
        this.rightear.rotateAngleY = -0.62f - newangle;
        newangle = MathHelper.cos((float)(f2 * 0.7f * this.wingspeed)) * (float)Math.PI * 0.02f;
        if (e.isChildModel()) {
            newangle = 0.0f;
        }
        this.Bodyfin.rotateAngleZ = newangle;
        newangle = MathHelper.cos((float)(f2 * 0.6f * this.wingspeed)) * (float)Math.PI * 0.1f;
        if (e.isChildModel()) {
            newangle = 0.0f;
        }
        this.neackfin.rotateAngleY = newangle;
        newangle = MathHelper.cos((float)(f2 * 0.5f * this.wingspeed)) * (float)Math.PI * 0.05f;
        if (e.isChildModel()) {
            newangle = 0.0f;
        }
        this.headfin.rotateAngleY = newangle;
        this.jaw.rotateAngleX = e.getAttacking() == 1 ? (newangle = MathHelper.cos((float)(f2 * 1.2f * this.wingspeed)) * (float)Math.PI * 0.25f) : (e.getAttacking() == 2 ? 0.45f : -0.25f);
        this.Head.rotateAngleY = newangle = (float)Math.toRadians(f3) * 0.75f;
        this.nose.rotateAngleY = newangle;
        this.nose.rotationPointZ = this.Head.rotationPointZ - (float)Math.cos(this.Head.rotateAngleY) * 8.0f;
        this.nose.rotationPointX = this.Head.rotationPointX - (float)Math.sin(this.Head.rotateAngleY) * 8.0f;
        this.jaw.rotateAngleY = newangle;
        this.jaw.rotationPointZ = this.Head.rotationPointZ - (float)Math.cos(this.Head.rotateAngleY) * 7.0f;
        this.jaw.rotationPointX = this.Head.rotationPointX - (float)Math.sin(this.Head.rotateAngleY) * 7.0f - 1.0f;
        this.headfin.rotateAngleY = newangle;
        this.headfin.rotationPointZ = this.Head.rotationPointZ - (float)Math.cos(this.Head.rotateAngleY) * 3.0f;
        this.headfin.rotationPointX = this.Head.rotationPointX - (float)Math.sin(this.Head.rotateAngleY) * 3.0f;
        this.leftear.rotateAngleY += newangle;
        this.leftear.rotationPointZ = this.Head.rotationPointZ - (float)Math.cos(this.Head.rotateAngleY - pi4) * root13;
        this.leftear.rotationPointX = this.Head.rotationPointX - (float)Math.sin(this.Head.rotateAngleY - pi4) * root13;
        this.rightear.rotateAngleY += newangle;
        this.rightear.rotationPointZ = this.Head.rotationPointZ - (float)Math.cos(this.Head.rotateAngleY + pi4) * root20;
        this.rightear.rotationPointX = this.Head.rotationPointX - (float)Math.sin(this.Head.rotateAngleY + pi4) * root20;
        this.Head.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.neck1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.body1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.Leg8.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.Leg2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.Leg7.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.Leg1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.neck2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.neck3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.neck4.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.body2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.body3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.body4.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.tail1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.tailmiddle.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.tailtop.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.tailbottom.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.nose.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.headfin.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.rightear.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.leftear.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.neackfin.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.Bodyfin.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.jaw.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    
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
