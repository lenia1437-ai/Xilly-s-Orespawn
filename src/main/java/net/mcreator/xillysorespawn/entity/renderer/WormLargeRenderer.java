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
import net.mcreator.xillysorespawn.entity.WormLargeEntity;
import net.mcreator.xillysorespawn.entity.OreSpawnLogic;

@OnlyIn(Dist.CLIENT)
public class WormLargeRenderer {
    private static final String TEXTURE = "xillys_orespawn:textures/entities/wormlargetexture.png";
    public static class ModelRegisterHandler {
        @SubscribeEvent @OnlyIn(Dist.CLIENT)
        public void registerModels(ModelRegistryEvent event) {
            RenderingRegistry.registerEntityRenderingHandler(WormLargeEntity.entity, manager ->
                new MobRenderer(manager, new ModelWormLarge(), 0.9F) {
                    @Override public ResourceLocation getEntityTexture(Entity entity) { return new ResourceLocation(TEXTURE); }
                    @Override protected void preRenderCallback(LivingEntity entity, MatrixStack stack, float partialTick) {
                        float modelScale = 1.0F;
                        stack.scale(modelScale, modelScale, modelScale);
                    }
                });
        }
    }

public static class ModelWormLarge extends EntityModel<Entity> {
    // Exact legacy render order recovered from bytecode; do not add a second addChild rig.
    private Entity frameEntity;
    private float frameSwing, frameAmount, frameAge, frameYaw, framePitch;
    @Override public void setRotationAngles(Entity entity, float swing, float amount, float age, float yaw, float pitch) {
        frameEntity=entity; frameSwing=swing; frameAmount=amount; frameAge=age; frameYaw=yaw; framePitch=pitch;
    }

    private final ModelRenderer head1;
    private final ModelRenderer head2;
    private final ModelRenderer head3;
    private final ModelRenderer head4;
    private final ModelRenderer head5;
    private final ModelRenderer neck1;
    private final ModelRenderer neck4;
    private final ModelRenderer neck5;
    private final ModelRenderer neck2;
    private final ModelRenderer neck3;
    private final ModelRenderer tail1;
    private final ModelRenderer tailtip;
    private final ModelRenderer tail2;
    private final ModelRenderer tail3;
    private final ModelRenderer tail4;
    private final ModelRenderer tooth1;
    private final ModelRenderer tooth2;
    private final ModelRenderer tooth3;
    private final ModelRenderer tooth4;
    private final ModelRenderer tooth5;
    private final ModelRenderer tooth6;
    private final ModelRenderer tooth7;
    private final ModelRenderer tooth8;

    public ModelWormLarge() {
        this.textureWidth = 256;
        this.textureHeight = 256;
        this.head1 = new ModelRenderer(this, 0, 0);
        this.head1.addBox(-8.0f, -8.0f, -20.0f, 16, 16, 20);
        this.head1.setRotationPoint(0.0f, 0.0f, 10.0f);
        this.head1.mirror = true;
        this.setRotation(this.head1, 0.0f, 0.0f, 0.0f);
        this.head2 = new ModelRenderer(this, 83, 27);
        this.head2.addBox(8.0f, -3.0f, -20.0f, 3, 6, 19);
        this.head2.setRotationPoint(0.0f, 0.0f, 10.0f);
        this.head2.mirror = true;
        this.setRotation(this.head2, 0.0f, 0.0f, 0.0f);
        this.head3 = new ModelRenderer(this, 9, 65);
        this.head3.addBox(-11.0f, -3.0f, -20.0f, 3, 6, 19);
        this.head3.setRotationPoint(0.0f, 0.0f, 10.0f);
        this.head3.mirror = true;
        this.setRotation(this.head3, 0.0f, 0.0f, 0.0f);
        this.head4 = new ModelRenderer(this, 77, 0);
        this.head4.addBox(-3.0f, -11.0f, -20.0f, 6, 3, 20);
        this.head4.setRotationPoint(0.0f, 0.0f, 10.0f);
        this.head4.mirror = true;
        this.setRotation(this.head4, 0.0f, 0.0f, 0.0f);
        this.head5 = new ModelRenderer(this, 10, 39);
        this.head5.addBox(-3.0f, 8.0f, -20.0f, 6, 3, 20);
        this.head5.setRotationPoint(0.0f, 0.0f, 10.0f);
        this.head5.mirror = true;
        this.setRotation(this.head5, 0.0f, 0.0f, 0.0f);
        this.neck1 = new ModelRenderer(this, 25, 94);
        this.neck1.addBox(-6.0f, -6.0f, -36.0f, 12, 12, 36);
        this.neck1.setRotationPoint(0.0f, 20.0f, 33.0f);
        this.neck1.mirror = true;
        this.setRotation(this.neck1, -0.6981317f, 0.0f, 0.0f);
        this.neck4 = new ModelRenderer(this, 25, 146);
        this.neck4.addBox(-2.0f, -8.0f, -38.0f, 4, 2, 38);
        this.neck4.setRotationPoint(0.0f, 20.0f, 33.0f);
        this.neck4.mirror = true;
        this.setRotation(this.neck4, -0.6981317f, 0.0f, 0.0f);
        this.neck5 = new ModelRenderer(this, 125, 189);
        this.neck5.addBox(-2.0f, 6.0f, -31.0f, 4, 2, 31);
        this.neck5.setRotationPoint(0.0f, 20.0f, 33.0f);
        this.neck5.mirror = true;
        this.setRotation(this.neck5, -0.6981317f, 0.0f, 0.0f);
        this.neck2 = new ModelRenderer(this, 25, 189);
        this.neck2.addBox(6.0f, -2.0f, -34.0f, 2, 4, 34);
        this.neck2.setRotationPoint(0.0f, 20.0f, 33.0f);
        this.neck2.mirror = true;
        this.setRotation(this.neck2, -0.6981317f, 0.0f, 0.0f);
        this.neck3 = new ModelRenderer(this, 125, 147);
        this.neck3.addBox(-8.0f, -2.0f, -34.0f, 2, 4, 34);
        this.neck3.setRotationPoint(0.0f, 20.0f, 33.0f);
        this.neck3.mirror = true;
        this.setRotation(this.neck3, -0.6981317f, 0.0f, 0.0f);
        this.tail1 = new ModelRenderer(this, 145, 21);
        this.tail1.addBox(-4.0f, -4.0f, 0.0f, 8, 8, 24);
        this.tail1.setRotationPoint(0.0f, 20.0f, 29.0f);
        this.tail1.mirror = true;
        this.setRotation(this.tail1, 0.0f, 0.0f, 0.0f);
        this.tailtip = new ModelRenderer(this, 180, 0);
        this.tailtip.addBox(-1.5f, -1.5f, 0.0f, 3, 3, 12);
        this.tailtip.setRotationPoint(0.0f, 19.5f, 52.0f);
        this.tailtip.mirror = true;
        this.setRotation(this.tailtip, 0.3490659f, 0.0f, 0.0f);
        this.tail2 = new ModelRenderer(this, 145, 56);
        this.tail2.addBox(4.0f, -1.0f, 2.0f, 1, 2, 14);
        this.tail2.setRotationPoint(0.0f, 20.0f, 29.0f);
        this.tail2.mirror = true;
        this.setRotation(this.tail2, 0.0f, 0.0f, 0.0f);
        this.tail3 = new ModelRenderer(this, 145, 90);
        this.tail3.addBox(-5.0f, -1.0f, 2.0f, 1, 2, 14);
        this.tail3.setRotationPoint(0.0f, 20.0f, 29.0f);
        this.tail3.mirror = true;
        this.setRotation(this.tail3, 0.0f, 0.0f, 0.0f);
        this.tail4 = new ModelRenderer(this, 145, 76);
        this.tail4.addBox(-1.0f, -5.0f, 7.0f, 2, 1, 9);
        this.tail4.setRotationPoint(0.0f, 20.0f, 29.0f);
        this.tail4.mirror = true;
        this.setRotation(this.tail4, 0.0f, 0.0f, 0.0f);
        this.tooth1 = new ModelRenderer(this, 0, 220);
        this.tooth1.addBox(-0.5f, -0.5f, -7.0f, 1, 1, 7);
        this.tooth1.setRotationPoint(0.0f, 9.0f, -10.0f);
        this.tooth1.mirror = true;
        this.setRotation(this.tooth1, 0.0f, 0.0f, 0.0f);
        this.tooth2 = new ModelRenderer(this, 0, 210);
        this.tooth2.addBox(-0.5f, -0.5f, -7.0f, 1, 1, 7);
        this.tooth2.setRotationPoint(0.0f, -9.0f, -10.0f);
        this.tooth2.mirror = true;
        this.setRotation(this.tooth2, 0.0f, 0.0f, 0.0f);
        this.tooth3 = new ModelRenderer(this, 0, 200);
        this.tooth3.addBox(-0.5f, -0.5f, -7.0f, 1, 1, 7);
        this.tooth3.setRotationPoint(9.0f, 0.0f, -10.0f);
        this.tooth3.mirror = true;
        this.setRotation(this.tooth3, 0.0f, 0.0f, 0.0f);
        this.tooth4 = new ModelRenderer(this, 0, 190);
        this.tooth4.addBox(-0.5f, -0.5f, -7.0f, 1, 1, 7);
        this.tooth4.setRotationPoint(-9.0f, 0.0f, -10.0f);
        this.tooth4.mirror = true;
        this.setRotation(this.tooth4, 0.0f, 0.0f, 0.0f);
        this.tooth5 = new ModelRenderer(this, 0, 180);
        this.tooth5.addBox(-0.5f, -0.5f, -7.0f, 1, 1, 7);
        this.tooth5.setRotationPoint(-6.0f, -6.0f, -10.0f);
        this.tooth5.mirror = true;
        this.setRotation(this.tooth5, 0.0f, 0.0f, 0.0f);
        this.tooth6 = new ModelRenderer(this, 0, 170);
        this.tooth6.addBox(-0.5f, -0.5f, -7.0f, 1, 1, 7);
        this.tooth6.setRotationPoint(6.0f, 6.0f, -10.0f);
        this.tooth6.mirror = true;
        this.setRotation(this.tooth6, 0.0f, 0.0f, 0.0f);
        this.tooth7 = new ModelRenderer(this, 0, 160);
        this.tooth7.addBox(-0.5f, -0.5f, -7.0f, 1, 1, 7);
        this.tooth7.setRotationPoint(6.0f, -6.0f, -10.0f);
        this.tooth7.mirror = true;
        this.setRotation(this.tooth7, 0.0f, 0.0f, 0.0f);
        this.tooth8 = new ModelRenderer(this, 0, 150);
        this.tooth8.addBox(-0.5f, -0.5f, -7.0f, 1, 1, 7);
        this.tooth8.setRotationPoint(-6.0f, 6.0f, -10.0f);
        this.tooth8.mirror = true;
        this.setRotation(this.tooth8, 0.0f, 0.0f, 0.0f);
    }

    public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        if (frameEntity == null) return;
        Entity entity=frameEntity;
        float f=frameSwing, f1=frameAmount, f2=frameAge, f3=frameYaw, f4=framePitch, f5=0.0625F;
        matrixStack.push();
this.head1.setRotationPoint(0.0f, 0.0f, 10.0f);
this.setRotation(this.head1, 0.0f, 0.0f, 0.0f);
this.head2.setRotationPoint(0.0f, 0.0f, 10.0f);
this.setRotation(this.head2, 0.0f, 0.0f, 0.0f);
this.head3.setRotationPoint(0.0f, 0.0f, 10.0f);
this.setRotation(this.head3, 0.0f, 0.0f, 0.0f);
this.head4.setRotationPoint(0.0f, 0.0f, 10.0f);
this.setRotation(this.head4, 0.0f, 0.0f, 0.0f);
this.head5.setRotationPoint(0.0f, 0.0f, 10.0f);
this.setRotation(this.head5, 0.0f, 0.0f, 0.0f);
this.neck1.setRotationPoint(0.0f, 20.0f, 33.0f);
this.setRotation(this.neck1, -0.6981317f, 0.0f, 0.0f);
this.neck4.setRotationPoint(0.0f, 20.0f, 33.0f);
this.setRotation(this.neck4, -0.6981317f, 0.0f, 0.0f);
this.neck5.setRotationPoint(0.0f, 20.0f, 33.0f);
this.setRotation(this.neck5, -0.6981317f, 0.0f, 0.0f);
this.neck2.setRotationPoint(0.0f, 20.0f, 33.0f);
this.setRotation(this.neck2, -0.6981317f, 0.0f, 0.0f);
this.neck3.setRotationPoint(0.0f, 20.0f, 33.0f);
this.setRotation(this.neck3, -0.6981317f, 0.0f, 0.0f);
this.tail1.setRotationPoint(0.0f, 20.0f, 29.0f);
this.setRotation(this.tail1, 0.0f, 0.0f, 0.0f);
this.tailtip.setRotationPoint(0.0f, 19.5f, 52.0f);
this.setRotation(this.tailtip, 0.3490659f, 0.0f, 0.0f);
this.tail2.setRotationPoint(0.0f, 20.0f, 29.0f);
this.setRotation(this.tail2, 0.0f, 0.0f, 0.0f);
this.tail3.setRotationPoint(0.0f, 20.0f, 29.0f);
this.setRotation(this.tail3, 0.0f, 0.0f, 0.0f);
this.tail4.setRotationPoint(0.0f, 20.0f, 29.0f);
this.setRotation(this.tail4, 0.0f, 0.0f, 0.0f);
this.tooth1.setRotationPoint(0.0f, 9.0f, -10.0f);
this.setRotation(this.tooth1, 0.0f, 0.0f, 0.0f);
this.tooth2.setRotationPoint(0.0f, -9.0f, -10.0f);
this.setRotation(this.tooth2, 0.0f, 0.0f, 0.0f);
this.tooth3.setRotationPoint(9.0f, 0.0f, -10.0f);
this.setRotation(this.tooth3, 0.0f, 0.0f, 0.0f);
this.tooth4.setRotationPoint(-9.0f, 0.0f, -10.0f);
this.setRotation(this.tooth4, 0.0f, 0.0f, 0.0f);
this.tooth5.setRotationPoint(-6.0f, -6.0f, -10.0f);
this.setRotation(this.tooth5, 0.0f, 0.0f, 0.0f);
this.tooth6.setRotationPoint(6.0f, 6.0f, -10.0f);
this.setRotation(this.tooth6, 0.0f, 0.0f, 0.0f);
this.tooth7.setRotationPoint(6.0f, -6.0f, -10.0f);
this.setRotation(this.tooth7, 0.0f, 0.0f, 0.0f);
this.tooth8.setRotationPoint(-6.0f, 6.0f, -10.0f);
this.setRotation(this.tooth8, 0.0f, 0.0f, 0.0f);

        float newangle2;
        double dist = 32.0;
        this.legacyAngles(f, f1, f2, f3, f4, f5, entity);
        float newangle = MathHelper.cos((float)(f2 * 0.25f)) * (float)Math.PI * 0.08f;
        this.neck1.rotateAngleX = newangle -= 0.698f;
        this.neck1.rotateAngleY = newangle2 = MathHelper.cos((float)(f2 * 0.15f)) * (float)Math.PI * 0.07f;
        this.neck4.rotateAngleX = this.neck5.rotateAngleX = this.neck1.rotateAngleX;
        this.neck3.rotateAngleX = this.neck5.rotateAngleX;
        this.neck2.rotateAngleX = this.neck5.rotateAngleX;
        this.neck4.rotateAngleY = this.neck5.rotateAngleY = this.neck1.rotateAngleY;
        this.neck3.rotateAngleY = this.neck5.rotateAngleY;
        this.neck2.rotateAngleY = this.neck5.rotateAngleY;
        double d1 = (float)(Math.cos(newangle) * dist);
        double d2 = (float)(Math.sin(newangle) * dist);
        this.head1.rotationPointZ = (float)((double)this.neck1.rotationPointZ - d1);
        double d3 = (float)(Math.sin(newangle2) * d1);
        double d4 = (float)(Math.cos(newangle2) * d1);
        this.head1.rotationPointX = (float)((double)this.neck1.rotationPointX - d3);
        this.head1.rotationPointY = (float)((double)this.neck1.rotationPointY + d2);
        this.head1.rotateAngleX = newangle = MathHelper.cos((float)(f2 * 0.35f)) * (float)Math.PI * 0.15f;
        this.head1.rotateAngleY = newangle2 = MathHelper.cos((float)(f2 * 0.45f)) * (float)Math.PI * 0.05f;
        this.head4.rotationPointX = this.head5.rotationPointX = this.head1.rotationPointX;
        this.head3.rotationPointX = this.head5.rotationPointX;
        this.head2.rotationPointX = this.head5.rotationPointX;
        this.head4.rotationPointY = this.head5.rotationPointY = this.head1.rotationPointY;
        this.head3.rotationPointY = this.head5.rotationPointY;
        this.head2.rotationPointY = this.head5.rotationPointY;
        this.head4.rotationPointZ = this.head5.rotationPointZ = this.head1.rotationPointZ;
        this.head3.rotationPointZ = this.head5.rotationPointZ;
        this.head2.rotationPointZ = this.head5.rotationPointZ;
        this.head4.rotateAngleX = this.head5.rotateAngleX = this.head1.rotateAngleX;
        this.head3.rotateAngleX = this.head5.rotateAngleX;
        this.head2.rotateAngleX = this.head5.rotateAngleX;
        this.head4.rotateAngleY = this.head5.rotateAngleY = this.head1.rotateAngleY;
        this.head3.rotateAngleY = this.head5.rotateAngleY;
        this.head2.rotateAngleY = this.head5.rotateAngleY;
        dist = 19.0;
        d1 = (float)(Math.cos(newangle) * dist);
        d2 = (float)(Math.sin(newangle) * dist);
        this.tooth1.rotationPointZ = (float)((double)this.head1.rotationPointZ - d1);
        d3 = (float)(Math.sin(newangle2) * d1);
        d4 = (float)(Math.cos(newangle2) * d1);
        this.tooth1.rotationPointX = (float)((double)this.head1.rotationPointX - d3);
        this.tooth1.rotationPointY = (float)((double)this.head1.rotationPointY + d2 - 9.0);
        this.tooth2.rotationPointZ = this.tooth1.rotationPointZ;
        this.tooth2.rotationPointX = this.tooth1.rotationPointX;
        this.tooth2.rotationPointY = this.tooth1.rotationPointY + 18.0f;
        this.tooth3.rotationPointZ = this.tooth1.rotationPointZ;
        this.tooth3.rotationPointX = this.tooth1.rotationPointX + 9.0f;
        this.tooth3.rotationPointY = this.tooth1.rotationPointY + 9.0f;
        this.tooth4.rotationPointZ = this.tooth1.rotationPointZ;
        this.tooth4.rotationPointX = this.tooth1.rotationPointX - 9.0f;
        this.tooth4.rotationPointY = this.tooth1.rotationPointY + 9.0f;
        this.tooth5.rotationPointZ = this.tooth1.rotationPointZ;
        this.tooth5.rotationPointX = this.tooth1.rotationPointX - 6.0f;
        this.tooth5.rotationPointY = this.tooth1.rotationPointY + 9.0f - 6.0f;
        this.tooth6.rotationPointZ = this.tooth1.rotationPointZ;
        this.tooth6.rotationPointX = this.tooth1.rotationPointX + 6.0f;
        this.tooth6.rotationPointY = this.tooth1.rotationPointY + 9.0f + 6.0f;
        this.tooth7.rotationPointZ = this.tooth1.rotationPointZ;
        this.tooth7.rotationPointX = this.tooth1.rotationPointX + 6.0f;
        this.tooth7.rotationPointY = this.tooth1.rotationPointY + 9.0f - 6.0f;
        this.tooth8.rotationPointZ = this.tooth1.rotationPointZ;
        this.tooth8.rotationPointX = this.tooth1.rotationPointX - 6.0f;
        this.tooth8.rotationPointY = this.tooth1.rotationPointY + 9.0f + 6.0f;
        this.tooth1.rotationPointZ = (float)((double)this.tooth1.rotationPointZ - Math.sin(this.head1.rotateAngleX) * 9.0);
        this.tooth2.rotationPointZ = (float)((double)this.tooth2.rotationPointZ + Math.sin(this.head1.rotateAngleX) * 9.0);
        this.tooth3.rotationPointZ = (float)((double)this.tooth3.rotationPointZ - Math.sin(this.head1.rotateAngleY) * 9.0);
        this.tooth4.rotationPointZ = (float)((double)this.tooth4.rotationPointZ + Math.sin(this.head1.rotateAngleY) * 9.0);
        this.tooth7.rotationPointZ = (float)((double)this.tooth7.rotationPointZ - Math.sin(this.head1.rotateAngleX) * 6.0);
        this.tooth7.rotationPointZ = (float)((double)this.tooth7.rotationPointZ - Math.sin(this.head1.rotateAngleY) * 6.0);
        this.tooth6.rotationPointZ = (float)((double)this.tooth6.rotationPointZ + Math.sin(this.head1.rotateAngleX) * 6.0);
        this.tooth6.rotationPointZ = (float)((double)this.tooth6.rotationPointZ - Math.sin(this.head1.rotateAngleY) * 6.0);
        this.tooth5.rotationPointZ = (float)((double)this.tooth5.rotationPointZ - Math.sin(this.head1.rotateAngleX) * 6.0);
        this.tooth5.rotationPointZ = (float)((double)this.tooth5.rotationPointZ + Math.sin(this.head1.rotateAngleY) * 6.0);
        this.tooth8.rotationPointZ = (float)((double)this.tooth8.rotationPointZ + Math.sin(this.head1.rotateAngleX) * 6.0);
        this.tooth8.rotationPointZ = (float)((double)this.tooth8.rotationPointZ + Math.sin(this.head1.rotateAngleY) * 6.0);
        newangle = MathHelper.cos((float)(f2 * 0.57f)) * (float)Math.PI * 0.35f;
        this.tooth1.rotateAngleX = this.head1.rotateAngleX + newangle;
        this.tooth2.rotateAngleX = this.head1.rotateAngleX - newangle;
        this.tooth3.rotateAngleY = this.head1.rotateAngleY + newangle;
        this.tooth4.rotateAngleY = this.head1.rotateAngleY - newangle;
        this.tooth5.rotateAngleX = this.head1.rotateAngleX + newangle;
        this.tooth7.rotateAngleX = this.head1.rotateAngleX + newangle;
        this.tooth6.rotateAngleX = this.head1.rotateAngleX - newangle;
        this.tooth8.rotateAngleX = this.head1.rotateAngleX - newangle;
        this.tooth6.rotateAngleY = this.head1.rotateAngleY + newangle;
        this.tooth7.rotateAngleY = this.head1.rotateAngleY + newangle;
        this.tooth5.rotateAngleY = this.head1.rotateAngleY - newangle;
        this.tooth8.rotateAngleY = this.head1.rotateAngleY - newangle;
        newangle = MathHelper.cos((float)(f2 * 0.63f)) * (float)Math.PI * 0.15f;
        this.tailtip.rotateAngleX = newangle + 0.35f;
        this.tailtip.rotateAngleY = newangle = MathHelper.cos((float)((float)((double)(f2 * 0.63f) + 1.57075))) * (float)Math.PI * 0.15f;
        this.head1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.head2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.head3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.head4.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.head5.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.neck1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.neck4.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.neck5.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.neck2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.neck3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.tail1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.tailtip.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.tail2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.tail3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.tail4.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.tooth1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.tooth2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.tooth3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.tooth4.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.tooth5.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.tooth6.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.tooth7.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.tooth8.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    
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
