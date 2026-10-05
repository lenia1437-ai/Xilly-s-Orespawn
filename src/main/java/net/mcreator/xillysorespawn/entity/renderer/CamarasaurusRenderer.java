package net.mcreator.xillysorespawn.entity.renderer;

import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.MobRenderer;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.mcreator.xillysorespawn.entity.CamarasaurusEntity;

@OnlyIn(Dist.CLIENT)
public class CamarasaurusRenderer {
    private static final String TEXTURE = "xillys_orespawn:textures/entities/camarasaurus.png";
    public static class ModelRegisterHandler {
        @SubscribeEvent @OnlyIn(Dist.CLIENT)
        public void registerModels(ModelRegistryEvent event) {
            RenderingRegistry.registerEntityRenderingHandler(CamarasaurusEntity.entity, manager ->
                new MobRenderer(manager, new ModelCamarasaurus(1.0F), 0.5F) {
                    @Override public ResourceLocation getEntityTexture(Entity entity) { return new ResourceLocation(TEXTURE); }
                });
        }
    }

public static class ModelCamarasaurus extends EntityModel<Entity> {
    // Exact legacy render order recovered from bytecode; do not add a second addChild rig.
    private Entity frameEntity;
    private float frameSwing, frameAmount, frameAge, frameYaw, framePitch;
    @Override public void setRotationAngles(Entity entity, float swing, float amount, float age, float yaw, float pitch) {
        frameEntity=entity; frameSwing=swing; frameAmount=amount; frameAge=age; frameYaw=yaw; framePitch=pitch;
    }

    private float wingspeed = 1.0f;
    private final ModelRenderer Body1;
    private final ModelRenderer Body2;
    private final ModelRenderer Body3;
    private final ModelRenderer Body4;
    private final ModelRenderer Tail0;
    private final ModelRenderer Neck1;
    private final ModelRenderer Neck2;
    private final ModelRenderer Neck3;
    private final ModelRenderer Head1;
    private final ModelRenderer Head2;
    private final ModelRenderer Tail1;
    private final ModelRenderer Tail2;
    private final ModelRenderer Tail3;
    private final ModelRenderer BLegupleft;
    private final ModelRenderer FLegupleft;
    private final ModelRenderer BLegupright;
    private final ModelRenderer FLegupright;
    private final ModelRenderer BLegdownright;
    private final ModelRenderer FLegdownleft;
    private final ModelRenderer FLegdownright;
    private final ModelRenderer BLegdownleft;

    public ModelCamarasaurus(float f1) {
        this.wingspeed = f1;
        this.textureWidth = 256;
        this.textureHeight = 256;
        this.Body1 = new ModelRenderer(this, 0, 135);
        this.Body1.addBox(-6.0f, 0.0f, 0.0f, 12, 12, 12);
        this.Body1.setRotationPoint(0.0f, -1.0f, 0.0f);
        this.Body1.mirror = true;
        this.setRotation(this.Body1, 0.0f, 0.0f, 0.0f);
        this.Body2 = new ModelRenderer(this, 0, 160);
        this.Body2.addBox(-5.0f, 0.0f, 0.0f, 10, 10, 6);
        this.Body2.setRotationPoint(0.0f, -2.0f, -4.0f);
        this.Body2.mirror = true;
        this.setRotation(this.Body2, -0.1858931f, 0.0f, 0.0f);
        this.Body3 = new ModelRenderer(this, 0, 177);
        this.Body3.addBox(-4.0f, 0.0f, 0.0f, 8, 8, 4);
        this.Body3.setRotationPoint(0.0f, -3.0f, -6.0f);
        this.Body3.mirror = true;
        this.setRotation(this.Body3, -0.3346075f, 0.0f, 0.0f);
        this.Body4 = new ModelRenderer(this, 0, 120);
        this.Body4.addBox(-5.0f, 0.0f, 0.0f, 10, 10, 4);
        this.Body4.setRotationPoint(0.0f, 0.0f, 11.0f);
        this.Body4.mirror = true;
        this.setRotation(this.Body4, 0.0f, 0.0f, 0.0f);
        this.Tail0 = new ModelRenderer(this, 0, 107);
        this.Tail0.addBox(-3.0f, -2.0f, 0.0f, 6, 6, 6);
        this.Tail0.setRotationPoint(0.0f, 3.0f, 14.0f);
        this.Tail0.mirror = true;
        this.setRotation(this.Tail0, -0.0743572f, 0.0f, 0.0f);
        this.Neck1 = new ModelRenderer(this, 0, 190);
        this.Neck1.addBox(-3.0f, 0.0f, 0.0f, 6, 6, 5);
        this.Neck1.setRotationPoint(0.0f, -4.0f, -9.0f);
        this.Neck1.mirror = true;
        this.setRotation(this.Neck1, -0.4089647f, 0.0f, 0.0f);
        this.Neck2 = new ModelRenderer(this, 0, 202);
        this.Neck2.addBox(-2.0f, 0.0f, -6.0f, 4, 4, 7);
        this.Neck2.setRotationPoint(0.0f, -3.0f, -9.0f);
        this.Neck2.mirror = true;
        this.setRotation(this.Neck2, -0.5948578f, 0.0f, 0.0f);
        this.Neck3 = new ModelRenderer(this, 0, 214);
        this.Neck3.addBox(-2.0f, -2.0f, -12.0f, 4, 4, 13);
        this.Neck3.setRotationPoint(0.0f, -5.0f, -15.0f);
        this.Neck3.mirror = true;
        this.setRotation(this.Neck3, -0.8179294f, 0.0f, 0.0f);
        this.Head1 = new ModelRenderer(this, 0, 232);
        this.Head1.addBox(-4.0f, -3.0f, -6.0f, 8, 6, 6);
        this.Head1.setRotationPoint(0.0f, -13.0f, -22.0f);
        this.Head1.mirror = true;
        this.setRotation(this.Head1, -0.1115358f, 0.0f, 0.0f);
        this.Head2 = new ModelRenderer(this, 0, 245);
        this.Head2.addBox(-3.0f, -2.0f, -4.0f, 6, 4, 4);
        this.Head2.setRotationPoint(0.0f, -13.0f, -27.0f);
        this.Head2.mirror = true;
        this.setRotation(this.Head2, 0.0f, 0.0f, 0.0f);
        this.Tail1 = new ModelRenderer(this, 0, 93);
        this.Tail1.addBox(-2.0f, -3.0f, 0.0f, 4, 4, 9);
        this.Tail1.setRotationPoint(0.0f, 5.0f, 19.0f);
        this.Tail1.mirror = true;
        this.setRotation(this.Tail1, -0.1115358f, 0.0f, 0.0f);
        this.Tail2 = new ModelRenderer(this, 0, 82);
        this.Tail2.addBox(-1.0f, -1.0f, 0.0f, 2, 2, 8);
        this.Tail2.setRotationPoint(0.0f, 4.0f, 26.0f);
        this.Tail2.mirror = true;
        this.setRotation(this.Tail2, -0.0743572f, 0.0f, 0.0f);
        this.Tail3 = new ModelRenderer(this, 0, 73);
        this.Tail3.addBox(-0.5f, -0.5f, 0.0f, 1, 1, 7);
        this.Tail3.setRotationPoint(0.0f, 4.5f, 34.0f);
        this.Tail3.mirror = true;
        this.setRotation(this.Tail3, -0.0371786f, 0.0f, 0.0f);
        this.BLegupleft = new ModelRenderer(this, 49, 157);
        this.BLegupleft.addBox(0.0f, 0.0f, 0.0f, 6, 8, 6);
        this.BLegupleft.setRotationPoint(2.0f, 9.0f, 7.0f);
        this.BLegupleft.mirror = true;
        this.setRotation(this.BLegupleft, -0.1487195f, 0.0f, 0.0f);
        this.FLegupleft = new ModelRenderer(this, 49, 141);
        this.FLegupleft.addBox(0.0f, 0.0f, -6.0f, 6, 9, 6);
        this.FLegupleft.setRotationPoint(2.0f, 8.0f, 2.0f);
        this.FLegupleft.mirror = true;
        this.setRotation(this.FLegupleft, 0.0f, 0.0f, 0.0f);
        this.BLegupright = new ModelRenderer(this, 49, 126);
        this.BLegupright.addBox(-6.0f, 0.0f, 0.0f, 6, 8, 6);
        this.BLegupright.setRotationPoint(-2.0f, 9.0f, 7.0f);
        this.BLegupright.mirror = true;
        this.setRotation(this.BLegupright, -0.1487144f, 0.0f, 0.0f);
        this.FLegupright = new ModelRenderer(this, 49, 110);
        this.FLegupright.addBox(-6.0f, 0.0f, -6.0f, 6, 9, 6);
        this.FLegupright.setRotationPoint(-2.0f, 8.0f, 2.0f);
        this.FLegupright.mirror = true;
        this.setRotation(this.FLegupright, 0.0f, 0.0f, 0.0f);
        this.BLegdownright = new ModelRenderer(this, 115, 157);
        this.BLegdownright.addBox(-5.0f, 7.0f, -1.0f, 5, 8, 5);
        this.BLegdownright.setRotationPoint(-2.0f, 9.0f, 7.0f);
        this.BLegdownright.mirror = true;
        this.setRotation(this.BLegdownright, 0.0f, 0.0f, 0.0f);
        this.FLegdownleft = new ModelRenderer(this, 94, 143);
        this.FLegdownleft.addBox(0.0f, 8.0f, -6.0f, 5, 8, 5);
        this.FLegdownleft.setRotationPoint(2.0f, 8.0f, 2.0f);
        this.FLegdownleft.mirror = true;
        this.setRotation(this.FLegdownleft, 0.0f, 0.0f, 0.0f);
        this.FLegdownright = new ModelRenderer(this, 94, 157);
        this.FLegdownright.addBox(-5.0f, 8.0f, -6.0f, 5, 8, 5);
        this.FLegdownright.setRotationPoint(-2.0f, 8.0f, 2.0f);
        this.FLegdownright.mirror = true;
        this.setRotation(this.FLegdownright, 0.0f, 0.0f, 0.0f);
        this.BLegdownleft = new ModelRenderer(this, 115, 143);
        this.BLegdownleft.addBox(0.0f, 7.0f, -1.0f, 5, 8, 5);
        this.BLegdownleft.setRotationPoint(2.0f, 9.0f, 7.0f);
        this.BLegdownleft.mirror = true;
        this.setRotation(this.BLegdownleft, 0.0f, 0.0f, 0.0f);
    }

    public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        if (frameEntity == null) return;
        Entity entity=frameEntity;
        float f=frameSwing, f1=frameAmount, f2=frameAge, f3=frameYaw, f4=framePitch, f5=0.0625F;
        matrixStack.push();
this.Body1.setRotationPoint(0.0f, -1.0f, 0.0f);
this.setRotation(this.Body1, 0.0f, 0.0f, 0.0f);
this.Body2.setRotationPoint(0.0f, -2.0f, -4.0f);
this.setRotation(this.Body2, -0.1858931f, 0.0f, 0.0f);
this.Body3.setRotationPoint(0.0f, -3.0f, -6.0f);
this.setRotation(this.Body3, -0.3346075f, 0.0f, 0.0f);
this.Body4.setRotationPoint(0.0f, 0.0f, 11.0f);
this.setRotation(this.Body4, 0.0f, 0.0f, 0.0f);
this.Tail0.setRotationPoint(0.0f, 3.0f, 14.0f);
this.setRotation(this.Tail0, -0.0743572f, 0.0f, 0.0f);
this.Neck1.setRotationPoint(0.0f, -4.0f, -9.0f);
this.setRotation(this.Neck1, -0.4089647f, 0.0f, 0.0f);
this.Neck2.setRotationPoint(0.0f, -3.0f, -9.0f);
this.setRotation(this.Neck2, -0.5948578f, 0.0f, 0.0f);
this.Neck3.setRotationPoint(0.0f, -5.0f, -15.0f);
this.setRotation(this.Neck3, -0.8179294f, 0.0f, 0.0f);
this.Head1.setRotationPoint(0.0f, -13.0f, -22.0f);
this.setRotation(this.Head1, -0.1115358f, 0.0f, 0.0f);
this.Head2.setRotationPoint(0.0f, -13.0f, -27.0f);
this.setRotation(this.Head2, 0.0f, 0.0f, 0.0f);
this.Tail1.setRotationPoint(0.0f, 5.0f, 19.0f);
this.setRotation(this.Tail1, -0.1115358f, 0.0f, 0.0f);
this.Tail2.setRotationPoint(0.0f, 4.0f, 26.0f);
this.setRotation(this.Tail2, -0.0743572f, 0.0f, 0.0f);
this.Tail3.setRotationPoint(0.0f, 4.5f, 34.0f);
this.setRotation(this.Tail3, -0.0371786f, 0.0f, 0.0f);
this.BLegupleft.setRotationPoint(2.0f, 9.0f, 7.0f);
this.setRotation(this.BLegupleft, -0.1487195f, 0.0f, 0.0f);
this.FLegupleft.setRotationPoint(2.0f, 8.0f, 2.0f);
this.setRotation(this.FLegupleft, 0.0f, 0.0f, 0.0f);
this.BLegupright.setRotationPoint(-2.0f, 9.0f, 7.0f);
this.setRotation(this.BLegupright, -0.1487144f, 0.0f, 0.0f);
this.FLegupright.setRotationPoint(-2.0f, 8.0f, 2.0f);
this.setRotation(this.FLegupright, 0.0f, 0.0f, 0.0f);
this.BLegdownright.setRotationPoint(-2.0f, 9.0f, 7.0f);
this.setRotation(this.BLegdownright, 0.0f, 0.0f, 0.0f);
this.FLegdownleft.setRotationPoint(2.0f, 8.0f, 2.0f);
this.setRotation(this.FLegdownleft, 0.0f, 0.0f, 0.0f);
this.FLegdownright.setRotationPoint(-2.0f, 8.0f, 2.0f);
this.setRotation(this.FLegdownright, 0.0f, 0.0f, 0.0f);
this.BLegdownleft.setRotationPoint(2.0f, 9.0f, 7.0f);
this.setRotation(this.BLegdownleft, 0.0f, 0.0f, 0.0f);

        CamarasaurusEntity.CustomEntity c = (CamarasaurusEntity.CustomEntity)entity;
        float hf = 0.0f;
        float newangle = 0.0f;
        this.legacyAngles(f, f1, f2, f3, f4, f5, entity);
        newangle = (double)f1 > 0.1 ? MathHelper.cos((float)(f2 * 1.3f * this.wingspeed)) * (float)Math.PI * 0.25f * f1 : 0.0f;
        this.FLegupleft.rotateAngleX = newangle;
        this.FLegdownleft.rotateAngleX = newangle;
        this.FLegupright.rotateAngleX = -newangle;
        this.FLegdownright.rotateAngleX = -newangle;
        this.BLegupleft.rotateAngleX = -0.15f - newangle;
        this.BLegdownleft.rotateAngleX = -newangle;
        this.BLegupright.rotateAngleX = -0.15f + newangle;
        this.BLegdownright.rotateAngleX = newangle;
        hf = (float)c.getCamarasaurusHealth() / c.getMaxHealth();
        newangle = MathHelper.cos((float)(f2 * 1.5f * this.wingspeed * hf)) * (float)Math.PI * 0.25f * hf;
        if (c.isChildModel()) {
            newangle = 0.0f;
        }
        this.Tail0.rotateAngleY = newangle * 0.25f;
        this.Tail1.rotationPointZ = this.Tail0.rotationPointZ + (float)Math.cos(this.Tail0.rotateAngleY) * 5.0f;
        this.Tail1.rotationPointX = this.Tail0.rotationPointX + (float)Math.sin(this.Tail0.rotateAngleY) * 5.0f;
        this.Tail1.rotateAngleY = newangle * 0.5f;
        this.Tail2.rotationPointZ = this.Tail1.rotationPointZ + (float)Math.cos(this.Tail1.rotateAngleY) * 8.0f;
        this.Tail2.rotationPointX = this.Tail1.rotationPointX + (float)Math.sin(this.Tail1.rotateAngleY) * 8.0f;
        this.Tail2.rotateAngleY = newangle * 0.75f;
        this.Tail3.rotationPointZ = this.Tail2.rotationPointZ + (float)Math.cos(this.Tail2.rotateAngleY) * 7.0f;
        this.Tail3.rotationPointX = this.Tail2.rotationPointX + (float)Math.sin(this.Tail2.rotateAngleY) * 7.0f;
        this.Tail3.rotateAngleY = newangle * 1.0f;
        this.Neck1.rotateAngleY = (float)Math.toRadians(f3) * 0.125f;
        this.Neck2.rotationPointZ = this.Neck1.rotationPointZ;
        this.Neck2.rotationPointX = this.Neck1.rotationPointX;
        this.Neck2.rotateAngleY = (float)Math.toRadians(f3) * 0.25f;
        this.Neck3.rotationPointZ = this.Neck2.rotationPointZ - (float)Math.cos(this.Neck2.rotateAngleY) * 6.0f;
        this.Neck3.rotationPointX = this.Neck2.rotationPointX - (float)Math.sin(this.Neck2.rotateAngleY) * 6.0f;
        this.Neck3.rotateAngleY = (float)Math.toRadians(f3) * 0.38f;
        this.Head1.rotationPointZ = this.Neck3.rotationPointZ - (float)Math.cos(this.Neck3.rotateAngleY) * 7.0f;
        this.Head1.rotationPointX = this.Neck3.rotationPointX - (float)Math.sin(this.Neck3.rotateAngleY) * 7.0f;
        this.Head1.rotateAngleY = (float)Math.toRadians(f3);
        this.Head2.rotationPointZ = this.Head1.rotationPointZ - (float)Math.cos(this.Head1.rotateAngleY) * 5.0f;
        this.Head2.rotationPointX = this.Head1.rotationPointX - (float)Math.sin(this.Head1.rotateAngleY) * 5.0f;
        this.Head2.rotateAngleY = (float)Math.toRadians(f3);
        this.Body1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.Body2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.Body3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.Body4.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.Tail0.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.Neck1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.Neck2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.Neck3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.Head1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.Head2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.Tail1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.Tail2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.Tail3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.FLegupleft.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.FLegdownleft.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.FLegupright.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.FLegdownright.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.BLegupleft.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.BLegdownright.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.BLegupright.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.BLegdownleft.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    
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
