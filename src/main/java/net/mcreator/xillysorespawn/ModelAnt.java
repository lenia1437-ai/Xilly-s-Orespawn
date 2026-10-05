package net.mcreator.xillysorespawn.entity.renderer;

import net.minecraft.util.math.MathHelper;
import net.minecraft.entity.Entity;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;

import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.mojang.blaze3d.matrix.MatrixStack;

// Модель Ant из 1.7.10 (ModelAnt), портирована на 1.16.5 вместе с анимацией. Текстура 64x32.
// Использование в рендерере:  new MobRenderer(renderManager, new ModelAnt(), 0.3f) { ... }
public class ModelAnt<T extends Entity> extends EntityModel<T> {
	private final ModelRenderer thorax;
	private final ModelRenderer thorax1;
	private final ModelRenderer thorax3;
	private final ModelRenderer abdomen;
	private final ModelRenderer abdomen1;
	private final ModelRenderer head;
	private final ModelRenderer jawsr;
	private final ModelRenderer jawsl;
	private final ModelRenderer llegtop1;
	private final ModelRenderer llegbot1;
	private final ModelRenderer llegtop2;
	private final ModelRenderer llegbot2;
	private final ModelRenderer llegtop3;
	private final ModelRenderer llegbot3;
	private final ModelRenderer rlegtop1;
	private final ModelRenderer rlegbot1;
	private final ModelRenderer rlegtop2;
	private final ModelRenderer rlegbot2;
	private final ModelRenderer rlegtop3;
	private final ModelRenderer rlegbot3;

	private static final float PI = (float) Math.PI;

	public ModelAnt() {
		textureWidth = 64;
		textureHeight = 32;

		thorax = new ModelRenderer(this);
		thorax.setRotationPoint(0.0F, 17.0F, 0.0F);
		thorax.setTextureOffset(22, 0).addBox(0.0F, 0.0F, 0.0F, 3.0F, 3.0F, 3.0F, 0.0F, false);

		thorax1 = new ModelRenderer(this);
		thorax1.setRotationPoint(0.0F, 17.0F, 0.0F);
		thorax1.setTextureOffset(18, 0).addBox(1.0F, 1.0F, -1.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);

		thorax3 = new ModelRenderer(this);
		thorax3.setRotationPoint(0.0F, 17.0F, 0.0F);
		thorax3.setTextureOffset(34, 0).addBox(1.0F, 1.0F, 3.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);

		abdomen = new ModelRenderer(this);
		abdomen.setRotationPoint(0.0F, 17.0F, 0.0F);
		abdomen.setTextureOffset(38, 0).addBox(0.0F, 0.0F, 4.0F, 3.0F, 3.0F, 5.0F, 0.0F, false);

		abdomen1 = new ModelRenderer(this);
		abdomen1.setRotationPoint(0.0F, 17.0F, 0.0F);
		abdomen1.setTextureOffset(54, 0).addBox(1.0F, 1.0F, 9.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);

		head = new ModelRenderer(this);
		head.setRotationPoint(0.0F, 17.0F, 0.0F);
		head.setTextureOffset(6, 0).addBox(0.0F, -1.0F, -4.0F, 3.0F, 3.0F, 3.0F, 0.0F, false);

		jawsr = new ModelRenderer(this);
		jawsr.setRotationPoint(0.0F, 17.0F, 0.0F);
		jawsr.setTextureOffset(0, 9).addBox(-1.0F, 0.0F, -6.0F, 1.0F, 1.0F, 3.0F, 0.0F, false);

		jawsl = new ModelRenderer(this);
		jawsl.setRotationPoint(0.0F, 17.0F, 0.0F);
		jawsl.setTextureOffset(0, 14).addBox(3.0F, 0.0F, -6.0F, 1.0F, 1.0F, 3.0F, 0.0F, false);

		llegtop1 = new ModelRenderer(this);
		llegtop1.setRotationPoint(0.0F, 17.0F, 0.0F);
		setRotationAngle(llegtop1, 0.0F, 0.0F, 0.3839724F);
		llegtop1.setTextureOffset(15, 10).addBox(3.0F, 1.0F, 1.0F, 3.0F, 1.0F, 1.0F, 0.0F, false);

		llegbot1 = new ModelRenderer(this);
		llegbot1.setRotationPoint(0.0F, 17.0F, 0.0F);
		setRotationAngle(llegbot1, 0.0F, 0.0F, 1.064651F);
		llegbot1.setTextureOffset(15, 19).addBox(5.0F, -3.0F, 1.0F, 3.0F, 1.0F, 1.0F, 0.0F, false);

		llegtop2 = new ModelRenderer(this);
		llegtop2.setRotationPoint(0.0F, 17.0F, 0.0F);
		setRotationAngle(llegtop2, 0.0F, -0.2094395F, 0.3839724F);
		llegtop2.setTextureOffset(15, 13).addBox(3.0F, 1.0F, 2.0F, 3.0F, 1.0F, 1.0F, 0.0F, false);

		llegbot2 = new ModelRenderer(this);
		llegbot2.setRotationPoint(0.0F, 17.0F, 0.0F);
		setRotationAngle(llegbot2, 0.0F, -0.2268928F, 1.064651F);
		llegbot2.setTextureOffset(15, 22).addBox(5.0F, -3.0F, 2.0F, 3.0F, 1.0F, 1.0F, 0.0F, false);

		llegtop3 = new ModelRenderer(this);
		llegtop3.setRotationPoint(0.0F, 17.0F, 0.0F);
		setRotationAngle(llegtop3, 0.0F, 0.3490659F, 0.3839724F);
		llegtop3.setTextureOffset(15, 16).addBox(3.0F, 1.0F, 0.0F, 3.0F, 1.0F, 1.0F, 0.0F, false);

		llegbot3 = new ModelRenderer(this);
		llegbot3.setRotationPoint(0.0F, 17.0F, 0.0F);
		setRotationAngle(llegbot3, 0.0F, 0.3490659F, 1.064651F);
		llegbot3.setTextureOffset(15, 25).addBox(5.0F, -3.0F, 0.0F, 3.0F, 1.0F, 1.0F, 0.0F, false);

		rlegtop1 = new ModelRenderer(this);
		rlegtop1.setRotationPoint(0.0F, 17.0F, 0.0F);
		setRotationAngle(rlegtop1, 0.0F, 0.0F, -0.4712389F);
		rlegtop1.setTextureOffset(25, 10).addBox(-4.0F, 2.0F, 1.0F, 3.0F, 1.0F, 1.0F, 0.0F, false);

		rlegbot1 = new ModelRenderer(this);
		rlegbot1.setRotationPoint(0.0F, 17.0F, 0.0F);
		setRotationAngle(rlegbot1, 0.0F, 0.0F, -0.9773844F);
		rlegbot1.setTextureOffset(25, 19).addBox(-7.0F, 0.0F, 1.0F, 3.0F, 1.0F, 1.0F, 0.0F, false);

		rlegtop2 = new ModelRenderer(this);
		rlegtop2.setRotationPoint(0.0F, 17.0F, 0.0F);
		setRotationAngle(rlegtop2, 0.0F, -0.5934119F, -0.4712389F);
		rlegtop2.setTextureOffset(25, 13).addBox(-4.0F, 2.0F, 0.0F, 3.0F, 1.0F, 1.0F, 0.0F, false);

		rlegbot2 = new ModelRenderer(this);
		rlegbot2.setRotationPoint(0.0F, 17.0F, 0.0F);
		setRotationAngle(rlegbot2, 0.0F, -0.5934119F, -0.9773844F);
		rlegbot2.setTextureOffset(25, 22).addBox(-7.0F, 0.0F, 0.0F, 3.0F, 1.0F, 1.0F, 0.0F, false);

		rlegtop3 = new ModelRenderer(this);
		rlegtop3.setRotationPoint(0.0F, 17.0F, 0.0F);
		setRotationAngle(rlegtop3, 0.0F, 0.418879F, -0.4712389F);
		rlegtop3.setTextureOffset(25, 16).addBox(-4.0F, 2.0F, 2.0F, 3.0F, 1.0F, 1.0F, 0.0F, false);

		rlegbot3 = new ModelRenderer(this);
		rlegbot3.setRotationPoint(0.0F, 17.0F, 0.0F);
		setRotationAngle(rlegbot3, 0.0F, 0.418879F, -0.9773844F);
		rlegbot3.setTextureOffset(25, 25).addBox(-7.0F, 0.0F, 2.0F, 3.0F, 1.0F, 1.0F, 0.0F, false);
	}

	@Override
	public void setRotationAngles(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		float a = MathHelper.cos(ageInTicks * 2.7F) * PI * 0.45F * limbSwingAmount;
		llegtop1.rotateAngleX = a;
		llegbot1.rotateAngleX = a;
		rlegtop2.rotateAngleX = a;
		rlegbot2.rotateAngleX = a;
		rlegtop3.rotateAngleX = a;
		rlegbot3.rotateAngleX = a;

		rlegtop1.rotateAngleX = -a;
		rlegbot1.rotateAngleX = -a;
		llegtop2.rotateAngleX = -a;
		llegbot2.rotateAngleX = -a;
		llegtop3.rotateAngleX = -a;
		llegbot3.rotateAngleX = -a;

		jawsl.rotateAngleY = MathHelper.cos(ageInTicks * 0.4F) * PI * 0.05F;
		jawsr.rotateAngleY = -jawsl.rotateAngleY;
	}

	@Override
	public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		thorax.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		thorax1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		thorax3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		abdomen.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		abdomen1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		head.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		jawsr.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		jawsl.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		llegtop1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		llegbot1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		llegtop2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		llegbot2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		llegtop3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		llegbot3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		rlegtop1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		rlegbot1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		rlegtop2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		rlegbot2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		rlegtop3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		rlegbot3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}
