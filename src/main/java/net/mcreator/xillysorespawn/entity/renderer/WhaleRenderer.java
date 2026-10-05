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
import net.mcreator.xillysorespawn.entity.WhaleEntity;
import net.mcreator.xillysorespawn.entity.OreSpawnLogic;

@OnlyIn(Dist.CLIENT)
public class WhaleRenderer {
    private static final String TEXTURE = "xillys_orespawn:textures/entities/whaletexture.png";
    public static class ModelRegisterHandler {
        @SubscribeEvent @OnlyIn(Dist.CLIENT)
        public void registerModels(ModelRegistryEvent event) {
            RenderingRegistry.registerEntityRenderingHandler(WhaleEntity.entity, manager ->
                new MobRenderer(manager, new ModelWhale(), 0.1F) {
                    @Override public ResourceLocation getEntityTexture(Entity entity) { return new ResourceLocation(TEXTURE); }
                    @Override protected void preRenderCallback(LivingEntity entity, MatrixStack stack, float partialTick) {
                        float modelScale = entity.isChild() ? 0.5F : 1.0F;
                        stack.scale(modelScale, modelScale, modelScale);
                    }
                });
        }
    }

public static class ModelWhale extends EntityModel<Entity> {
    // Exact legacy render order recovered from bytecode; do not add a second addChild rig.
    private Entity frameEntity;
    private float frameSwing, frameAmount, frameAge, frameYaw, framePitch;
    @Override public void setRotationAngles(Entity entity, float swing, float amount, float age, float yaw, float pitch) {
        frameEntity=entity; frameSwing=swing; frameAmount=amount; frameAge=age; frameYaw=yaw; framePitch=pitch;
    }

    private final ModelRenderer belly;
    private final ModelRenderer body;
    private final ModelRenderer back;
    private final ModelRenderer tail1;
    private final ModelRenderer tail2;
    private final ModelRenderer tailfin1;
    private final ModelRenderer tailfin2;
    private final ModelRenderer backfin;
    private final ModelRenderer head;
    private final ModelRenderer jaw;
    private final ModelRenderer lfin1;
    private final ModelRenderer lfin2;
    private final ModelRenderer rfin1;
    private final ModelRenderer rfin2;

    public ModelWhale() {
        this.textureWidth = 256;
        this.textureHeight = 256;
        this.belly = new ModelRenderer(this, 0, 92);
        this.belly.addBox(-6.0f, 0.0f, 0.0f, 12, 2, 32);
        this.belly.setRotationPoint(0.0f, 22.0f, 6.0f);
        this.belly.mirror = true;
        this.setRotation(this.belly, 0.0f, 0.0f, 0.0f);
        this.body = new ModelRenderer(this, 0, 188);
        this.body.addBox(-10.0f, 0.0f, 0.0f, 20, 12, 52);
        this.body.setRotationPoint(0.0f, 10.0f, 0.0f);
        this.body.mirror = true;
        this.setRotation(this.body, 0.0f, 0.0f, 0.0f);
        this.back = new ModelRenderer(this, 0, 45);
        this.back.addBox(-4.0f, 0.0f, 0.0f, 8, 2, 40);
        this.back.setRotationPoint(0.0f, 8.0f, 3.0f);
        this.back.mirror = true;
        this.setRotation(this.back, 0.0f, 0.0f, 0.0f);
        this.tail1 = new ModelRenderer(this, 186, 0);
        this.tail1.addBox(-6.0f, 0.0f, 0.0f, 12, 7, 14);
        this.tail1.setRotationPoint(0.0f, 11.0f, 52.0f);
        this.tail1.mirror = true;
        this.setRotation(this.tail1, 0.0f, 0.0f, 0.0f);
        this.tail2 = new ModelRenderer(this, 186, 24);
        this.tail2.addBox(-4.0f, 0.0f, 0.0f, 8, 5, 10);
        this.tail2.setRotationPoint(0.0f, 12.0f, 66.0f);
        this.tail2.mirror = true;
        this.setRotation(this.tail2, 0.0f, 0.0f, 0.0f);
        this.tailfin1 = new ModelRenderer(this, 186, 43);
        this.tailfin1.addBox(0.0f, 0.0f, 0.0f, 17, 2, 11);
        this.tailfin1.setRotationPoint(2.0f, 13.0f, 74.0f);
        this.tailfin1.mirror = true;
        this.setRotation(this.tailfin1, 0.0872665f, -0.0872665f, 0.0f);
        this.tailfin2 = new ModelRenderer(this, 186, 59);
        this.tailfin2.addBox(-17.0f, 0.0f, 0.0f, 17, 2, 11);
        this.tailfin2.setRotationPoint(-2.0f, 13.0f, 74.0f);
        this.tailfin2.mirror = true;
        this.setRotation(this.tailfin2, 0.0872665f, 0.0872665f, 0.0f);
        this.backfin = new ModelRenderer(this, 0, 15);
        this.backfin.addBox(-0.5f, 0.0f, 0.0f, 1, 4, 8);
        this.backfin.setRotationPoint(0.0f, 8.0f, 11.0f);
        this.backfin.mirror = true;
        this.setRotation(this.backfin, 0.3665191f, 0.0f, 0.0f);
        this.head = new ModelRenderer(this, 0, 155);
        this.head.addBox(-8.0f, 0.0f, -16.0f, 16, 8, 22);
        this.head.setRotationPoint(0.0f, 11.0f, -6.0f);
        this.head.mirror = true;
        this.setRotation(this.head, 0.0f, 0.0f, 0.0f);
        this.jaw = new ModelRenderer(this, 0, 130);
        this.jaw.addBox(-7.0f, -1.0f, -20.0f, 14, 2, 20);
        this.jaw.setRotationPoint(0.0f, 20.0f, 0.0f);
        this.jaw.mirror = true;
        this.setRotation(this.jaw, 0.0698132f, 0.0f, 0.0f);
        this.lfin1 = new ModelRenderer(this, 96, 0);
        this.lfin1.addBox(0.0f, -1.0f, -3.0f, 4, 3, 6);
        this.lfin1.setRotationPoint(10.0f, 18.0f, 8.0f);
        this.lfin1.mirror = true;
        this.setRotation(this.lfin1, 0.0f, -0.0872665f, 0.0f);
        this.lfin2 = new ModelRenderer(this, 120, 0);
        this.lfin2.addBox(2.0f, -0.5f, -3.0f, 22, 2, 8);
        this.lfin2.setRotationPoint(10.0f, 18.0f, 8.0f);
        this.lfin2.mirror = true;
        this.setRotation(this.lfin2, 0.0f, -0.0872665f, 0.0f);
        this.rfin1 = new ModelRenderer(this, 96, 12);
        this.rfin1.addBox(-4.0f, -1.0f, -3.0f, 4, 3, 6);
        this.rfin1.setRotationPoint(-10.0f, 18.0f, 8.0f);
        this.rfin1.mirror = true;
        this.setRotation(this.rfin1, 0.0f, 0.0872665f, 0.0f);
        this.rfin2 = new ModelRenderer(this, 120, 13);
        this.rfin2.addBox(-24.0f, -0.5f, -3.0f, 22, 2, 8);
        this.rfin2.setRotationPoint(-10.0f, 18.0f, 8.0f);
        this.rfin2.mirror = true;
        this.setRotation(this.rfin2, 0.0f, 0.0872665f, 0.0f);
    }

    public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        if (frameEntity == null) return;
        Entity entity=frameEntity;
        float f=frameSwing, f1=frameAmount, f2=frameAge, f3=frameYaw, f4=framePitch, f5=0.0625F;
        matrixStack.push();
this.belly.setRotationPoint(0.0f, 22.0f, 6.0f);
this.setRotation(this.belly, 0.0f, 0.0f, 0.0f);
this.body.setRotationPoint(0.0f, 10.0f, 0.0f);
this.setRotation(this.body, 0.0f, 0.0f, 0.0f);
this.back.setRotationPoint(0.0f, 8.0f, 3.0f);
this.setRotation(this.back, 0.0f, 0.0f, 0.0f);
this.tail1.setRotationPoint(0.0f, 11.0f, 52.0f);
this.setRotation(this.tail1, 0.0f, 0.0f, 0.0f);
this.tail2.setRotationPoint(0.0f, 12.0f, 66.0f);
this.setRotation(this.tail2, 0.0f, 0.0f, 0.0f);
this.tailfin1.setRotationPoint(2.0f, 13.0f, 74.0f);
this.setRotation(this.tailfin1, 0.0872665f, -0.0872665f, 0.0f);
this.tailfin2.setRotationPoint(-2.0f, 13.0f, 74.0f);
this.setRotation(this.tailfin2, 0.0872665f, 0.0872665f, 0.0f);
this.backfin.setRotationPoint(0.0f, 8.0f, 11.0f);
this.setRotation(this.backfin, 0.3665191f, 0.0f, 0.0f);
this.head.setRotationPoint(0.0f, 11.0f, -6.0f);
this.setRotation(this.head, 0.0f, 0.0f, 0.0f);
this.jaw.setRotationPoint(0.0f, 20.0f, 0.0f);
this.setRotation(this.jaw, 0.0698132f, 0.0f, 0.0f);
this.lfin1.setRotationPoint(10.0f, 18.0f, 8.0f);
this.setRotation(this.lfin1, 0.0f, -0.0872665f, 0.0f);
this.lfin2.setRotationPoint(10.0f, 18.0f, 8.0f);
this.setRotation(this.lfin2, 0.0f, -0.0872665f, 0.0f);
this.rfin1.setRotationPoint(-10.0f, 18.0f, 8.0f);
this.setRotation(this.rfin1, 0.0f, 0.0872665f, 0.0f);
this.rfin2.setRotationPoint(-10.0f, 18.0f, 8.0f);
this.setRotation(this.rfin2, 0.0f, 0.0872665f, 0.0f);

        this.legacyAngles(f, f1, f2, f3, f4, f5, entity);
        float newangle = MathHelper.cos((float)(f2 * 0.55f)) * (float)Math.PI * 0.15f;
        newangle = (double)f1 > 0.1 ? MathHelper.cos((float)(f2 * 0.3f)) * (float)Math.PI * 0.2f * f1 : MathHelper.cos((float)(f2 * 0.08f)) * (float)Math.PI * 0.05f;
        this.lfin2.rotateAngleZ = 0.436f + newangle;
        this.lfin1.rotateAngleZ = this.lfin2.rotateAngleZ / 2.0f;
        this.rfin2.rotateAngleZ = -0.436f - newangle;
        this.rfin1.rotateAngleZ = this.rfin2.rotateAngleZ / 2.0f;
        newangle = MathHelper.cos((float)(f2 * 0.03f)) * (float)Math.PI * 0.02f;
        this.jaw.rotateAngleX = 0.087f + newangle;
        newangle = (double)f1 > 0.1 ? MathHelper.cos((float)(f2 * 0.4f)) * (float)Math.PI * 0.16f * f1 : MathHelper.cos((float)(f2 * 0.05f)) * (float)Math.PI * 0.03f;
        this.tail1.rotateAngleX = newangle * 0.5f;
        this.tail2.rotateAngleX = newangle * 1.25f;
        this.tailfin1.rotateAngleX = this.tailfin2.rotateAngleX = newangle * 2.25f;
        this.tail2.rotationPointZ = this.tail1.rotationPointZ + (float)Math.cos(this.tail1.rotateAngleX) * 14.0f;
        this.tail2.rotationPointY = this.tail1.rotationPointY - (float)Math.sin(this.tail1.rotateAngleX) * 14.0f;
        this.tailfin1.rotationPointZ = this.tailfin2.rotationPointZ = this.tail2.rotationPointZ + (float)Math.cos(this.tail2.rotateAngleX) * 8.0f;
        this.tailfin1.rotationPointY = this.tailfin2.rotationPointY = this.tail2.rotationPointY - (float)Math.sin(this.tail2.rotateAngleX) * 8.0f;
        this.belly.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.body.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.back.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.tail1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.tail2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.tailfin1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.tailfin2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.backfin.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.head.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.jaw.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.lfin1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.lfin2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.rfin1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.rfin2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    
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
