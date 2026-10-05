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
import net.mcreator.xillysorespawn.entity.WormSmallEntity;
import net.mcreator.xillysorespawn.entity.OreSpawnLogic;

@OnlyIn(Dist.CLIENT)
public class WormSmallRenderer {
    private static final String TEXTURE = "xillys_orespawn:textures/entities/wormsmalltexture.png";
    public static class ModelRegisterHandler {
        @SubscribeEvent @OnlyIn(Dist.CLIENT)
        public void registerModels(ModelRegistryEvent event) {
            RenderingRegistry.registerEntityRenderingHandler(WormSmallEntity.entity, manager ->
                new MobRenderer(manager, new ModelWormSmall(), 0.1F) {
                    @Override public ResourceLocation getEntityTexture(Entity entity) { return new ResourceLocation(TEXTURE); }
                    @Override protected void preRenderCallback(LivingEntity entity, MatrixStack stack, float partialTick) {
                        float modelScale = 1.0F;
                        stack.scale(modelScale, modelScale, modelScale);
                    }
                });
        }
    }

public static class ModelWormSmall extends EntityModel<Entity> {
    // Exact legacy render order recovered from bytecode; do not add a second addChild rig.
    private Entity frameEntity;
    private float frameSwing, frameAmount, frameAge, frameYaw, framePitch;
    @Override public void setRotationAngles(Entity entity, float swing, float amount, float age, float yaw, float pitch) {
        frameEntity=entity; frameSwing=swing; frameAmount=amount; frameAge=age; frameYaw=yaw; framePitch=pitch;
    }

    private final ModelRenderer head;
    private final ModelRenderer body;
    private final ModelRenderer tail;

    public ModelWormSmall() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.head = new ModelRenderer(this, 0, 0);
        this.head.addBox(-0.5f, -5.0f, -0.5f, 1, 5, 1);
        this.head.setRotationPoint(0.0f, 14.0f, 0.0f);
        this.head.mirror = true;
        this.setRotation(this.head, 0.0f, 0.0f, 0.0f);
        this.body = new ModelRenderer(this, 6, 0);
        this.body.addBox(-0.5f, -5.0f, -0.5f, 1, 5, 1);
        this.body.setRotationPoint(0.0f, 19.0f, 0.0f);
        this.body.mirror = true;
        this.setRotation(this.body, 0.0f, 0.0f, 0.0f);
        this.tail = new ModelRenderer(this, 12, 0);
        this.tail.addBox(-0.5f, -5.0f, -0.5f, 1, 5, 1);
        this.tail.setRotationPoint(0.0f, 24.0f, 0.0f);
        this.tail.mirror = true;
        this.setRotation(this.tail, 0.0f, 0.0f, 0.0f);
    }

    public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        if (frameEntity == null) return;
        Entity entity=frameEntity;
        float f=frameSwing, f1=frameAmount, f2=frameAge, f3=frameYaw, f4=framePitch, f5=0.0625F;
        matrixStack.push();
this.head.setRotationPoint(0.0f, 14.0f, 0.0f);
this.setRotation(this.head, 0.0f, 0.0f, 0.0f);
this.body.setRotationPoint(0.0f, 19.0f, 0.0f);
this.setRotation(this.body, 0.0f, 0.0f, 0.0f);
this.tail.setRotationPoint(0.0f, 24.0f, 0.0f);
this.setRotation(this.tail, 0.0f, 0.0f, 0.0f);

        float newangle;
        this.legacyAngles(f, f1, f2, f3, f4, f5, entity);
        this.tail.rotateAngleX = newangle = MathHelper.cos((float)(f2 * 0.55f)) * (float)Math.PI * 0.15f;
        float d1 = (float)(Math.sin(newangle) * 5.0);
        float d2 = (float)(Math.cos(newangle) * 5.0);
        this.body.rotationPointZ = this.tail.rotationPointZ - d1;
        this.tail.rotateAngleZ = newangle = MathHelper.cos((float)(f2 * 0.35f)) * (float)Math.PI * 0.1f;
        float d3 = (float)(Math.cos(newangle) * (double)d2);
        float d4 = (float)(Math.sin(newangle) * (double)d2);
        this.body.rotationPointX = this.tail.rotationPointX + d4;
        this.body.rotationPointY = (float)((double)this.tail.rotationPointY - 5.0 + (5.0 - (double)d3));
        this.body.rotateAngleX = newangle = MathHelper.cos((float)(f2 * 0.45f)) * (float)Math.PI * 0.15f;
        d1 = (float)(Math.sin(newangle) * 5.0);
        d2 = (float)(Math.cos(newangle) * 5.0);
        this.head.rotationPointZ = this.body.rotationPointZ - d1;
        this.body.rotateAngleZ = newangle = MathHelper.cos((float)(f2 * 0.25f)) * (float)Math.PI * 0.1f;
        d3 = (float)(Math.cos(newangle) * (double)d2);
        d4 = (float)(Math.sin(newangle) * (double)d2);
        this.head.rotationPointX = this.body.rotationPointX + d4;
        this.head.rotationPointY = (float)((double)this.body.rotationPointY - 5.0 + (5.0 - (double)d3));
        this.head.rotateAngleX = 0.62f + MathHelper.cos((float)(f2 * 0.65f)) * (float)Math.PI * 0.15f;
        this.head.rotateAngleZ = MathHelper.cos((float)(f2 * 0.3f)) * (float)Math.PI * 0.05f;
        this.head.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.body.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.tail.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    
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
