package net.mcreator.xillysorespawn.entity.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

/** Exact geometry and linked wing animation from OreSpawn 1.7.10 ModelButterfly. */
public class ModelButterfly<T extends Entity> extends EntityModel<T> {
    private final ModelRenderer body;
    private final ModelRenderer leftWing;
    private final ModelRenderer rightWing;
    private final ModelRenderer leftWing2;
    private final ModelRenderer rightWing2;
    private final ModelRenderer leftWing3;
    private final ModelRenderer rightWing3;
    private final ModelRenderer head;
    private final ModelRenderer leftWing4;
    private final ModelRenderer rightWing4;
    private final float wingSpeed;

    public ModelButterfly(float wingSpeed) {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.wingSpeed = wingSpeed;
        body = part(21, 19, 0, 0, -4, 1, 1, 8, 0, 17, 0);
        leftWing = part(43, 24, 0, 0, -4, 5, 1, 5, 1, 17, 0);
        rightWing = part(43, 17, -5, 0, -4, 5, 1, 5, 0, 17, 0);
        leftWing2 = part(0, 0, 1, 0, -6, 6, 1, 7, 1, 17, 0);
        rightWing2 = part(29, 0, -7, 0, -6, 6, 1, 7, 0, 17, 0);
        leftWing3 = part(0, 9, 0, 0, 1, 5, 1, 5, 1, 17, 0);
        rightWing3 = part(27, 9, -5, 0, 1, 5, 1, 5, 0, 17, 0);
        head = part(21, 11, 0, 0, -6, 1, 1, 1, 0, 17, 1);
        leftWing4 = part(2, 24, 0, 0, 6, 1, 1, 7, 1, 17, 0);
        rightWing4 = part(2, 16, -1, 0, 6, 1, 1, 7, 0, 17, 0);
    }

    private ModelRenderer part(int u, int v, float x, float y, float z, float w, float h, float d,
            float pivotX, float pivotY, float pivotZ) {
        ModelRenderer part = new ModelRenderer(this, u, v);
        part.addBox(x, y, z, w, h, d);
        part.setRotationPoint(pivotX, pivotY, pivotZ);
        part.mirror = true;
        return part;
    }

    @Override public void setRotationAngles(T entity, float limbSwing, float limbSwingAmount,
            float ageInTicks, float netHeadYaw, float headPitch) {
        float flap = MathHelper.cos(ageInTicks * 1.3F * this.wingSpeed) * (float)Math.PI * 0.25F;
        rightWing.rotateAngleZ = flap;
        rightWing2.rotateAngleZ = flap;
        rightWing3.rotateAngleZ = flap;
        rightWing4.rotateAngleZ = flap;
        leftWing.rotateAngleZ = -flap;
        leftWing2.rotateAngleZ = -flap;
        leftWing3.rotateAngleZ = -flap;
        leftWing4.rotateAngleZ = -flap;
    }

    @Override public void render(MatrixStack stack, IVertexBuilder buffer, int light, int overlay,
            float red, float green, float blue, float alpha) {
        head.render(stack, buffer, light, overlay, red, green, blue, alpha);
        body.render(stack, buffer, light, overlay, red, green, blue, alpha);
        leftWing.render(stack, buffer, light, overlay, red, green, blue, alpha);
        rightWing.render(stack, buffer, light, overlay, red, green, blue, alpha);
        leftWing2.render(stack, buffer, light, overlay, red, green, blue, alpha);
        rightWing2.render(stack, buffer, light, overlay, red, green, blue, alpha);
        leftWing3.render(stack, buffer, light, overlay, red, green, blue, alpha);
        rightWing3.render(stack, buffer, light, overlay, red, green, blue, alpha);
        leftWing4.render(stack, buffer, light, overlay, red, green, blue, alpha);
        rightWing4.render(stack, buffer, light, overlay, red, green, blue, alpha);
    }
}
