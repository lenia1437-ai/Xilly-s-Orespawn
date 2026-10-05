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
import net.mcreator.xillysorespawn.entity.WormMediumEntity;
import net.mcreator.xillysorespawn.entity.OreSpawnLogic;

@OnlyIn(Dist.CLIENT)
public class WormMediumRenderer {
    private static final String TEXTURE = "xillys_orespawn:textures/entities/wormmediumtexture.png";
    public static class ModelRegisterHandler {
        @SubscribeEvent @OnlyIn(Dist.CLIENT)
        public void registerModels(ModelRegistryEvent event) {
            RenderingRegistry.registerEntityRenderingHandler(WormMediumEntity.entity, manager ->
                new MobRenderer(manager, new ModelWormMedium(), 0.25F) {
                    @Override public ResourceLocation getEntityTexture(Entity entity) { return new ResourceLocation(TEXTURE); }
                    @Override protected void preRenderCallback(LivingEntity entity, MatrixStack stack, float partialTick) {
                        float modelScale = 1.0F;
                        stack.scale(modelScale, modelScale, modelScale);
                    }
                });
        }
    }

public static class ModelWormMedium extends EntityModel<Entity> {
    // Exact legacy render order recovered from bytecode; do not add a second addChild rig.
    private Entity frameEntity;
    private float frameSwing, frameAmount, frameAge, frameYaw, framePitch;
    @Override public void setRotationAngles(Entity entity, float swing, float amount, float age, float yaw, float pitch) {
        frameEntity=entity; frameSwing=swing; frameAmount=amount; frameAge=age; frameYaw=yaw; framePitch=pitch;
    }

    private final ModelRenderer head;
    private final ModelRenderer body;
    private final ModelRenderer tail;
    private final ModelRenderer tooth1;
    private final ModelRenderer tooth2;
    private final ModelRenderer tooth3;
    private final ModelRenderer tooth4;
    private final ModelRenderer head2;

    public ModelWormMedium() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.head = new ModelRenderer(this, 24, 0);
        this.head.addBox(-1.5f, -12.0f, -1.5f, 3, 12, 3);
        this.head.setRotationPoint(0.0f, 1.0f, 0.0f);
        this.head.mirror = true;
        this.setRotation(this.head, 0.0f, 0.0f, 0.0f);
        this.body = new ModelRenderer(this, 37, 0);
        this.body.addBox(-1.5f, -12.0f, -1.5f, 3, 12, 3);
        this.body.setRotationPoint(0.0f, 13.0f, 0.0f);
        this.body.mirror = true;
        this.setRotation(this.body, 0.0f, 0.0f, 0.0f);
        this.tail = new ModelRenderer(this, 50, 0);
        this.tail.addBox(-1.5f, -12.0f, -1.5f, 3, 12, 3);
        this.tail.setRotationPoint(0.0f, 25.0f, 0.0f);
        this.tail.mirror = true;
        this.setRotation(this.tail, 0.0f, 0.0f, 0.0f);
        this.tooth1 = new ModelRenderer(this, 15, 0);
        this.tooth1.addBox(-0.5f, -3.0f, -0.5f, 1, 3, 1);
        this.tooth1.setRotationPoint(1.0f, -11.0f, 0.0f);
        this.tooth1.mirror = true;
        this.setRotation(this.tooth1, 0.0f, 0.0f, 0.0f);
        this.tooth2 = new ModelRenderer(this, 5, 0);
        this.tooth2.addBox(-0.5f, -3.0f, -0.5f, 1, 3, 1);
        this.tooth2.setRotationPoint(-1.0f, -11.0f, 0.0f);
        this.tooth2.mirror = true;
        this.setRotation(this.tooth2, 0.0f, 0.0f, 0.0f);
        this.tooth3 = new ModelRenderer(this, 0, 0);
        this.tooth3.addBox(-0.5f, -3.0f, -0.5f, 1, 3, 1);
        this.tooth3.setRotationPoint(0.0f, -11.0f, 1.0f);
        this.tooth3.mirror = true;
        this.setRotation(this.tooth3, 0.0f, 0.0f, 0.0f);
        this.tooth4 = new ModelRenderer(this, 10, 0);
        this.tooth4.addBox(-0.5f, -3.0f, -0.5f, 1, 3, 1);
        this.tooth4.setRotationPoint(0.0f, -11.0f, -1.0f);
        this.tooth4.mirror = true;
        this.setRotation(this.tooth4, 0.0f, 0.0f, 0.0f);
        this.head2 = new ModelRenderer(this, 0, 6);
        this.head2.addBox(-2.0f, -8.0f, -2.0f, 4, 8, 4);
        this.head2.setRotationPoint(0.0f, 0.0f, 0.0f);
        this.head2.mirror = true;
        this.setRotation(this.head2, 0.0f, 0.0f, 0.0f);
    }

    public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        if (frameEntity == null) return;
        Entity entity=frameEntity;
        float f=frameSwing, f1=frameAmount, f2=frameAge, f3=frameYaw, f4=framePitch, f5=0.0625F;
        matrixStack.push();
this.head.setRotationPoint(0.0f, 1.0f, 0.0f);
this.setRotation(this.head, 0.0f, 0.0f, 0.0f);
this.body.setRotationPoint(0.0f, 13.0f, 0.0f);
this.setRotation(this.body, 0.0f, 0.0f, 0.0f);
this.tail.setRotationPoint(0.0f, 25.0f, 0.0f);
this.setRotation(this.tail, 0.0f, 0.0f, 0.0f);
this.tooth1.setRotationPoint(1.0f, -11.0f, 0.0f);
this.setRotation(this.tooth1, 0.0f, 0.0f, 0.0f);
this.tooth2.setRotationPoint(-1.0f, -11.0f, 0.0f);
this.setRotation(this.tooth2, 0.0f, 0.0f, 0.0f);
this.tooth3.setRotationPoint(0.0f, -11.0f, 1.0f);
this.setRotation(this.tooth3, 0.0f, 0.0f, 0.0f);
this.tooth4.setRotationPoint(0.0f, -11.0f, -1.0f);
this.setRotation(this.tooth4, 0.0f, 0.0f, 0.0f);
this.head2.setRotationPoint(0.0f, 0.0f, 0.0f);
this.setRotation(this.head2, 0.0f, 0.0f, 0.0f);

        float newangle;
        this.legacyAngles(f, f1, f2, f3, f4, f5, entity);
        this.tail.rotateAngleX = newangle = MathHelper.cos((float)(f2 * 0.45f)) * (float)Math.PI * 0.1f;
        float d1 = (float)(Math.sin(newangle) * 12.0);
        float d2 = (float)(Math.cos(newangle) * 12.0);
        this.body.rotationPointZ = this.tail.rotationPointZ - d1;
        this.tail.rotateAngleZ = newangle = MathHelper.cos((float)(f2 * 0.25f)) * (float)Math.PI * 0.08f;
        float d3 = (float)(Math.cos(newangle) * (double)d2);
        float d4 = (float)(Math.sin(newangle) * (double)d2);
        this.body.rotationPointX = this.tail.rotationPointX + d4;
        this.body.rotationPointY = (float)((double)this.tail.rotationPointY - 12.0 + (12.0 - (double)d3));
        this.body.rotateAngleX = newangle = MathHelper.cos((float)(f2 * 0.35f)) * (float)Math.PI * 0.1f;
        d1 = (float)(Math.sin(newangle) * 12.0);
        d2 = (float)(Math.cos(newangle) * 12.0);
        this.head2.rotationPointZ = this.head.rotationPointZ = this.body.rotationPointZ - d1;
        this.body.rotateAngleZ = newangle = MathHelper.cos((float)(f2 * 0.15f)) * (float)Math.PI * 0.07f;
        d3 = (float)(Math.cos(newangle) * (double)d2);
        d4 = (float)(Math.sin(newangle) * (double)d2);
        this.head2.rotationPointX = this.head.rotationPointX = this.body.rotationPointX + d4;
        this.head2.rotationPointY = this.head.rotationPointY = (float)((double)this.body.rotationPointY - 12.0 + (12.0 - (double)d3));
        this.head2.rotateAngleX = this.head.rotateAngleX = 0.62f + MathHelper.cos((float)(f2 * 0.55f)) * (float)Math.PI * 0.15f;
        this.head2.rotateAngleZ = this.head.rotateAngleZ = MathHelper.cos((float)(f2 * 0.25f)) * (float)Math.PI * 0.05f;
        this.tooth3.rotateAngleX = this.tooth4.rotateAngleX = (newangle = this.head.rotateAngleX);
        this.tooth2.rotateAngleX = this.tooth4.rotateAngleX;
        this.tooth1.rotateAngleX = this.tooth4.rotateAngleX;
        d1 = (float)(Math.sin(newangle) * 12.0);
        d2 = (float)(Math.cos(newangle) * 12.0);
        this.tooth3.rotationPointZ = this.tooth4.rotationPointZ = this.head.rotationPointZ - d1;
        this.tooth2.rotationPointZ = this.tooth4.rotationPointZ;
        this.tooth1.rotationPointZ = this.tooth4.rotationPointZ;
        this.tooth3.rotateAngleZ = this.tooth4.rotateAngleZ = (newangle = this.head.rotateAngleZ);
        this.tooth2.rotateAngleZ = this.tooth4.rotateAngleZ;
        this.tooth1.rotateAngleZ = this.tooth4.rotateAngleZ;
        d3 = (float)(Math.cos(newangle) * (double)d2);
        d4 = (float)(Math.sin(newangle) * (double)d2);
        this.tooth3.rotationPointX = this.tooth4.rotationPointX = this.head.rotationPointX + d4;
        this.tooth2.rotationPointX = this.tooth4.rotationPointX;
        this.tooth1.rotationPointX = this.tooth4.rotationPointX;
        this.tooth3.rotationPointY = this.tooth4.rotationPointY = (float)((double)this.head.rotationPointY - 12.0 + (12.0 - (double)d3));
        this.tooth2.rotationPointY = this.tooth4.rotationPointY;
        this.tooth1.rotationPointY = this.tooth4.rotationPointY;
        this.tooth1.rotationPointZ += 1.0f;
        this.tooth2.rotationPointZ -= 1.0f;
        this.tooth1.rotateAngleX = this.tooth1.rotateAngleX - 0.4f - MathHelper.cos((float)(f2 * 0.55f)) * (float)Math.PI * 0.15f;
        this.tooth2.rotateAngleX = this.tooth2.rotateAngleX + 0.4f + MathHelper.cos((float)(f2 * 0.55f)) * (float)Math.PI * 0.15f;
        this.tooth3.rotationPointX += 1.0f;
        this.tooth4.rotationPointX -= 1.0f;
        this.tooth3.rotateAngleZ = this.tooth3.rotateAngleZ + 0.4f + MathHelper.cos((float)(f2 * 0.55f)) * (float)Math.PI * 0.15f;
        this.tooth4.rotateAngleZ = this.tooth4.rotateAngleZ - 0.4f - MathHelper.cos((float)(f2 * 0.55f)) * (float)Math.PI * 0.15f;
        this.head.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.body.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.tail.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.tooth1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.tooth2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.tooth3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.tooth4.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.head2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    
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
