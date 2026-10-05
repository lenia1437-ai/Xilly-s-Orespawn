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
import net.mcreator.xillysorespawn.entity.StinkyEntity;
import net.mcreator.xillysorespawn.entity.OreSpawnLogic;

@OnlyIn(Dist.CLIENT)
public class StinkyRenderer {
    private static final ResourceLocation[] TEXTURES = new ResourceLocation[19];
    static {
        for (int i = 0; i < TEXTURES.length; i++) TEXTURES[i] = new ResourceLocation(
            "xillys_orespawn:textures/entities/stinkytexture" + (i + 1) + ".png");
    }
    public static class ModelRegisterHandler {
        @SubscribeEvent @OnlyIn(Dist.CLIENT)
        public void registerModels(ModelRegistryEvent event) {
            RenderingRegistry.registerEntityRenderingHandler(StinkyEntity.entity, manager ->
                new MobRenderer(manager, new ModelStinky(0.65F), 0.75F) {
                    @Override public ResourceLocation getEntityTexture(Entity entity) {
                        int skin = entity instanceof StinkyEntity.CustomEntity
                            ? ((StinkyEntity.CustomEntity) entity).getSkin() : 0;
                        return TEXTURES[MathHelper.clamp(skin, 0, TEXTURES.length - 1)];
                    }
                    @Override protected void preRenderCallback(LivingEntity entity, MatrixStack stack, float partialTick) {
                        float modelScale = 1.0F;
                        stack.scale(modelScale, modelScale, modelScale);
                    }
                });
        }
    }

public static class ModelStinky extends EntityModel<Entity> {
    // Exact legacy render order recovered from bytecode; do not add a second addChild rig.
    private Entity frameEntity;
    private float frameSwing, frameAmount, frameAge, frameYaw, framePitch;
    @Override public void setRotationAngles(Entity entity, float swing, float amount, float age, float yaw, float pitch) {
        frameEntity=entity; frameSwing=swing; frameAmount=amount; frameAge=age; frameYaw=yaw; framePitch=pitch;
    }

    private float wingspeed = 1.0f;
    private final ModelRenderer body;
    private final ModelRenderer neck1;
    private final ModelRenderer neck;
    private final ModelRenderer neckbase;
    private final ModelRenderer head;
    private final ModelRenderer Rleg1;
    private final ModelRenderer Lleg1;
    private final ModelRenderer Lhorn1;
    private final ModelRenderer Rhorn1;
    private final ModelRenderer snout;
    private final ModelRenderer Lhorn2;
    private final ModelRenderer Rhorn2;
    private final ModelRenderer tail1;
    private final ModelRenderer Rleg2;
    private final ModelRenderer Lleg2;
    private final ModelRenderer tail2;
    private final ModelRenderer tail3;
    private final ModelRenderer tail4;
    private final ModelRenderer Lwing;
    private final ModelRenderer Rwing;

    public ModelStinky(float f1) {
        this.wingspeed = f1;
        this.textureWidth = 128;
        this.textureHeight = 64;
        this.body = new ModelRenderer(this, 0, 12);
        this.body.addBox(-4.5f, -3.0f, -5.0f, 8, 8, 10);
        this.body.setRotationPoint(0.5f, 15.0f, 1.0f);
        this.body.mirror = true;
        this.setRotation(this.body, 0.0f, 0.0f, 0.0f);
        this.neck1 = new ModelRenderer(this, 0, 31);
        this.neck1.addBox(-2.0f, -3.0f, -2.0f, 4, 5, 5);
        this.neck1.setRotationPoint(0.0f, 16.0f, -5.0f);
        this.neck1.mirror = true;
        this.setRotation(this.neck1, 0.715585f, 0.0f, 0.0f);
        this.neck = new ModelRenderer(this, 0, 42);
        this.neck.addBox(-2.0f, -8.0f, -3.0f, 4, 8, 4);
        this.neck.setRotationPoint(0.0f, 15.0f, -5.5f);
        this.neck.mirror = true;
        this.setRotation(this.neck, 0.0f, 0.0f, 0.0f);
        this.neckbase = new ModelRenderer(this, 0, 55);
        this.neckbase.addBox(-3.0f, -4.0f, 0.0f, 6, 6, 3);
        this.neckbase.setRotationPoint(0.0f, 17.0f, 5.0f);
        this.neckbase.mirror = true;
        this.setRotation(this.neckbase, 0.0f, 0.0f, 0.0f);
        this.head = new ModelRenderer(this, 0, 0);
        this.head.addBox(-2.5f, -10.0f, -3.5f, 5, 5, 5);
        this.head.setRotationPoint(0.0f, 15.0f, -5.5f);
        this.head.mirror = true;
        this.setRotation(this.head, 0.0f, 0.0f, 0.0f);
        this.Rleg1 = new ModelRenderer(this, 19, 53);
        this.Rleg1.addBox(-1.5f, 0.0f, -1.0f, 3, 8, 3);
        this.Rleg1.setRotationPoint(2.0f, 16.0f, 5.5f);
        this.Rleg1.mirror = true;
        this.setRotation(this.Rleg1, 0.0f, 0.0f, 0.0f);
        this.Lleg1 = new ModelRenderer(this, 19, 53);
        this.Lleg1.addBox(-1.5f, 0.0f, -0.5f, 3, 8, 3);
        this.Lleg1.setRotationPoint(-2.0f, 16.0f, 5.0f);
        this.Lleg1.mirror = true;
        this.setRotation(this.Lleg1, 0.0f, 0.0f, 0.0f);
        this.Lhorn1 = new ModelRenderer(this, 19, 47);
        this.Lhorn1.addBox(-3.0f, -10.5f, -1.0f, 2, 2, 3);
        this.Lhorn1.setRotationPoint(0.0f, 15.0f, -5.5f);
        this.Lhorn1.mirror = true;
        this.setRotation(this.Lhorn1, 0.0f, 0.0f, 0.0f);
        this.Rhorn1 = new ModelRenderer(this, 19, 47);
        this.Rhorn1.addBox(1.0f, -10.5f, -1.0f, 2, 2, 3);
        this.Rhorn1.setRotationPoint(0.0f, 15.0f, -5.5f);
        this.Rhorn1.mirror = true;
        this.setRotation(this.Rhorn1, 0.0f, 0.0f, 0.0f);
        this.snout = new ModelRenderer(this, 32, 57);
        this.snout.addBox(-1.5f, -8.0f, -6.5f, 3, 3, 4);
        this.snout.setRotationPoint(0.0f, 15.0f, -5.5f);
        this.snout.mirror = true;
        this.setRotation(this.snout, 0.0f, 0.0f, 0.0f);
        this.Lhorn2 = new ModelRenderer(this, 19, 42);
        this.Lhorn2.addBox(-2.5f, -10.0f, 1.0f, 1, 1, 3);
        this.Lhorn2.setRotationPoint(0.0f, 15.0f, -5.5f);
        this.Lhorn2.mirror = true;
        this.setRotation(this.Lhorn2, 0.0f, 0.0f, 0.0f);
        this.Rhorn2 = new ModelRenderer(this, 19, 42);
        this.Rhorn2.addBox(1.5f, -10.0f, 1.0f, 1, 1, 3);
        this.Rhorn2.setRotationPoint(0.0f, 15.0f, -5.5f);
        this.Rhorn2.mirror = true;
        this.setRotation(this.Rhorn2, 0.0f, 0.0f, 0.0f);
        this.tail1 = new ModelRenderer(this, 47, 55);
        this.tail1.addBox(-3.0f, -3.0f, -3.0f, 6, 6, 3);
        this.tail1.setRotationPoint(0.0f, 16.5f, -2.0f);
        this.tail1.mirror = true;
        this.setRotation(this.tail1, 0.0f, 0.0f, 0.0f);
        this.Rleg2 = new ModelRenderer(this, 19, 53);
        this.Rleg2.addBox(-1.5f, 0.0f, -1.5f, 3, 8, 3);
        this.Rleg2.setRotationPoint(2.0f, 16.0f, -3.0f);
        this.Rleg2.mirror = true;
        this.setRotation(this.Rleg2, 0.0f, 0.0f, 0.0f);
        this.Lleg2 = new ModelRenderer(this, 19, 53);
        this.Lleg2.addBox(-1.5f, 0.0f, -1.5f, 3, 8, 3);
        this.Lleg2.setRotationPoint(-2.0f, 16.0f, -3.0f);
        this.Lleg2.mirror = true;
        this.setRotation(this.Lleg2, 0.0f, 0.0f, 0.0f);
        this.tail2 = new ModelRenderer(this, 19, 31);
        this.tail2.addBox(-2.5f, -2.5f, 0.0f, 5, 5, 5);
        this.tail2.setRotationPoint(0.0f, 16.0f, 7.0f);
        this.tail2.mirror = true;
        this.setRotation(this.tail2, -0.3839724f, 0.0f, 0.0f);
        this.tail3 = new ModelRenderer(this, 32, 46);
        this.tail3.addBox(-2.0f, -2.0f, 0.0f, 4, 4, 4);
        this.tail3.setRotationPoint(0.0f, 17.2f, 11.0f);
        this.tail3.mirror = true;
        this.setRotation(this.tail3, -0.2094395f, 0.0f, 0.0f);
        this.tail4 = new ModelRenderer(this, 37, 13);
        this.tail4.addBox(-1.5f, -1.5f, 0.0f, 3, 3, 5);
        this.tail4.setRotationPoint(0.0f, 17.5f, 14.0f);
        this.tail4.mirror = true;
        this.setRotation(this.tail4, -0.0698132f, 0.0f, 0.0f);
        this.Lwing = new ModelRenderer(this, 59, 0);
        this.Lwing.addBox(-18.0f, 0.0f, -5.0f, 18, 0, 10);
        this.Lwing.setRotationPoint(-2.0f, 12.6f, 0.0f);
        this.Lwing.mirror = true;
        this.setRotation(this.Lwing, 0.0f, 0.0f, 0.4014257f);
        this.Rwing = new ModelRenderer(this, 59, 11);
        this.Rwing.addBox(0.0f, 0.0f, -5.0f, 18, 0, 10);
        this.Rwing.setRotationPoint(2.0f, 12.6f, 0.0f);
        this.Rwing.mirror = true;
        this.setRotation(this.Rwing, 0.0f, 0.0f, -0.4014257f);
    }

    public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        if (frameEntity == null) return;
        Entity entity=frameEntity;
        float f=frameSwing, f1=frameAmount, f2=frameAge, f3=frameYaw, f4=framePitch, f5=0.0625F;
        matrixStack.push();
this.body.setRotationPoint(0.5f, 15.0f, 1.0f);
this.setRotation(this.body, 0.0f, 0.0f, 0.0f);
this.neck1.setRotationPoint(0.0f, 16.0f, -5.0f);
this.setRotation(this.neck1, 0.715585f, 0.0f, 0.0f);
this.neck.setRotationPoint(0.0f, 15.0f, -5.5f);
this.setRotation(this.neck, 0.0f, 0.0f, 0.0f);
this.neckbase.setRotationPoint(0.0f, 17.0f, 5.0f);
this.setRotation(this.neckbase, 0.0f, 0.0f, 0.0f);
this.head.setRotationPoint(0.0f, 15.0f, -5.5f);
this.setRotation(this.head, 0.0f, 0.0f, 0.0f);
this.Rleg1.setRotationPoint(2.0f, 16.0f, 5.5f);
this.setRotation(this.Rleg1, 0.0f, 0.0f, 0.0f);
this.Lleg1.setRotationPoint(-2.0f, 16.0f, 5.0f);
this.setRotation(this.Lleg1, 0.0f, 0.0f, 0.0f);
this.Lhorn1.setRotationPoint(0.0f, 15.0f, -5.5f);
this.setRotation(this.Lhorn1, 0.0f, 0.0f, 0.0f);
this.Rhorn1.setRotationPoint(0.0f, 15.0f, -5.5f);
this.setRotation(this.Rhorn1, 0.0f, 0.0f, 0.0f);
this.snout.setRotationPoint(0.0f, 15.0f, -5.5f);
this.setRotation(this.snout, 0.0f, 0.0f, 0.0f);
this.Lhorn2.setRotationPoint(0.0f, 15.0f, -5.5f);
this.setRotation(this.Lhorn2, 0.0f, 0.0f, 0.0f);
this.Rhorn2.setRotationPoint(0.0f, 15.0f, -5.5f);
this.setRotation(this.Rhorn2, 0.0f, 0.0f, 0.0f);
this.tail1.setRotationPoint(0.0f, 16.5f, -2.0f);
this.setRotation(this.tail1, 0.0f, 0.0f, 0.0f);
this.Rleg2.setRotationPoint(2.0f, 16.0f, -3.0f);
this.setRotation(this.Rleg2, 0.0f, 0.0f, 0.0f);
this.Lleg2.setRotationPoint(-2.0f, 16.0f, -3.0f);
this.setRotation(this.Lleg2, 0.0f, 0.0f, 0.0f);
this.tail2.setRotationPoint(0.0f, 16.0f, 7.0f);
this.setRotation(this.tail2, -0.3839724f, 0.0f, 0.0f);
this.tail3.setRotationPoint(0.0f, 17.2f, 11.0f);
this.setRotation(this.tail3, -0.2094395f, 0.0f, 0.0f);
this.tail4.setRotationPoint(0.0f, 17.5f, 14.0f);
this.setRotation(this.tail4, -0.0698132f, 0.0f, 0.0f);
this.Lwing.setRotationPoint(-2.0f, 12.6f, 0.0f);
this.setRotation(this.Lwing, 0.0f, 0.0f, 0.4014257f);
this.Rwing.setRotationPoint(2.0f, 12.6f, 0.0f);
this.setRotation(this.Rwing, 0.0f, 0.0f, -0.4014257f);

        StinkyEntity.CustomEntity c = (StinkyEntity.CustomEntity)entity;
        float hf = 0.0f;
        float newangle = 0.0f;
        int current_activity = c.getActivity();
        this.legacyAngles(f, f1, f2, f3, f4, f5, entity);
        newangle = (double)f1 > 0.1 ? MathHelper.cos((float)(f2 * 2.3f * this.wingspeed)) * (float)Math.PI * 0.4f * f1 : 0.0f;
        this.Rwing.rotateAngleZ = newangle - 0.4f;
        this.Lwing.rotateAngleZ = -newangle + 0.4f;
        newangle = (double)f1 > 0.1 ? MathHelper.cos((float)(f2 * 2.0f * this.wingspeed)) * (float)Math.PI * 0.25f * f1 : 0.0f;
        if (current_activity != 2) {
            this.Rleg1.rotateAngleX = newangle;
            this.Lleg1.rotateAngleX = -newangle;
            this.Rleg2.rotateAngleX = -newangle;
            this.Lleg2.rotateAngleX = newangle;
        } else {
            this.Rleg2.rotateAngleX = newangle = -1.0f;
            this.Lleg2.rotateAngleX = newangle;
            this.Rleg1.rotateAngleX = newangle = 1.0f;
            this.Lleg1.rotateAngleX = newangle;
        }
        newangle = MathHelper.cos((float)(f2 * 1.0f * this.wingspeed)) * (float)Math.PI * 0.2f;
        if (c.isChildModel()) {
            newangle = 0.0f;
        }
        this.tail2.rotateAngleY = newangle;
        this.tail3.rotationPointZ = this.tail2.rotationPointZ + (float)Math.cos(this.tail2.rotateAngleY) * 4.0f;
        this.tail3.rotationPointX = this.tail2.rotationPointX + (float)Math.sin(this.tail2.rotateAngleY) * 4.0f - 0.5f;
        this.tail3.rotateAngleY = newangle * 1.6f;
        this.tail4.rotationPointZ = this.tail3.rotationPointZ + (float)Math.cos(this.tail3.rotateAngleY) * 3.0f;
        this.tail4.rotationPointX = this.tail3.rotationPointX + (float)Math.sin(this.tail3.rotateAngleY) * 3.0f - 0.5f;
        this.tail4.rotateAngleY = newangle * 2.6f;
        this.head.rotateAngleY = (float)Math.toRadians(f3);
        this.snout.rotateAngleY = (float)Math.toRadians(f3);
        this.neck.rotateAngleY = (float)Math.toRadians(f3) / 2.0f;
        this.Rhorn1.rotateAngleY = (float)Math.toRadians(f3);
        this.Rhorn2.rotateAngleY = (float)Math.toRadians(f3);
        this.Lhorn1.rotateAngleY = (float)Math.toRadians(f3);
        this.Lhorn2.rotateAngleY = (float)Math.toRadians(f3);
        this.head.rotateAngleX = (float)Math.toRadians(f4) / 3.0f;
        this.snout.rotateAngleX = (float)Math.toRadians(f4) / 3.0f;
        this.neck.rotateAngleX = (float)Math.toRadians(f4) / 3.0f;
        this.Rhorn1.rotateAngleX = (float)Math.toRadians(f4) / 3.0f;
        this.Rhorn2.rotateAngleX = (float)Math.toRadians(f4) / 3.0f;
        this.Lhorn1.rotateAngleX = (float)Math.toRadians(f4) / 3.0f;
        this.Lhorn2.rotateAngleX = (float)Math.toRadians(f4) / 3.0f;
        this.body.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.neck1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.neck.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.neckbase.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.head.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.Rleg1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.Lleg1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.Lhorn1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.Rhorn1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.snout.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.Lhorn2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.Rhorn2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.tail1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.Rleg2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.Lleg2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.tail2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.tail3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.tail4.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.Lwing.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.Rwing.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    
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
