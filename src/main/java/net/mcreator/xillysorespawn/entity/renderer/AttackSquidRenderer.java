package net.mcreator.xillysorespawn.entity.renderer;

import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.util.math.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.Entity;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.MobRenderer;

import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.mojang.blaze3d.matrix.MatrixStack;

import net.mcreator.xillysorespawn.entity.AttackSquidEntity;

@OnlyIn(Dist.CLIENT)
public class AttackSquidRenderer {
	// Если у тебя другой modid - поменяй здесь (текстура: assets/<modid>/textures/attacksquid.png, размер 64x32)
	private static final String TEXTURE = "xillys_orespawn:textures/entities/attacksquid.png";

	public static class ModelRegisterHandler {
		@SubscribeEvent
		@OnlyIn(Dist.CLIENT)
		public void registerModels(ModelRegistryEvent event) {
			RenderingRegistry.registerEntityRenderingHandler(AttackSquidEntity.entity, renderManager -> {
				return new MobRenderer(renderManager, new ModelAttackSquid(), 0.5f) {
					@Override
					public ResourceLocation getEntityTexture(Entity entity) {
						return new ResourceLocation(TEXTURE);
					}
				};
			});
		}
	}

	// Модель AttackSquid из 1.7.10 (ModelAttackSquid), портирована на 1.16.5 вместе с анимацией
	public static class ModelAttackSquid extends EntityModel<Entity> {
		private final ModelRenderer tent1;
		private final ModelRenderer tent2;
		private final ModelRenderer tent3;
		private final ModelRenderer tent4;
		private final ModelRenderer tent5;
		private final ModelRenderer tent6;
		private final ModelRenderer tent7;
		private final ModelRenderer body;
		private final ModelRenderer tent8;

		private static final float PI = (float) Math.PI;
		private static final float WING_SPEED = 1.0F;

		public ModelAttackSquid() {
			textureWidth = 64;
			textureHeight = 32;

			tent1 = new ModelRenderer(this);
			tent1.setRotationPoint(5.0F, 15.0F, -1.0F);
			setRotationAngle(tent1, -0.9250245F, -1.745329F, 0.0F);
			tent1.setTextureOffset(0, 18).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 9.0F, 2.0F, 0.0F, false);

			tent2 = new ModelRenderer(this);
			tent2.setRotationPoint(-2.0F, 15.0F, -3.0F);
			setRotationAngle(tent2, -0.1745329F, -0.6632251F, -0.2443461F);
			tent2.setTextureOffset(0, 18).addBox(-8.0F, -1.0F, -1.0F, 8.0F, 2.0F, 2.0F, 0.0F, false);

			tent3 = new ModelRenderer(this);
			tent3.setRotationPoint(1.0F, 15.0F, -4.0F);
			setRotationAngle(tent3, -1.134464F, 0.3316126F, 0.0F);
			tent3.setTextureOffset(0, 18).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 10.0F, 2.0F, 0.0F, false);

			tent4 = new ModelRenderer(this);
			tent4.setRotationPoint(-3.0F, 15.0F, -1.0F);
			setRotationAngle(tent4, 0.5585054F, -1.692969F, 0.0F);
			tent4.setTextureOffset(0, 18).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 10.0F, 2.0F, 0.0F, false);

			tent5 = new ModelRenderer(this);
			tent5.setRotationPoint(1.0F, 15.0F, 3.0F);
			setRotationAngle(tent5, 0.5410521F, 0.2268928F, 0.0F);
			tent5.setTextureOffset(0, 18).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 10.0F, 2.0F, 0.0F, false);

			tent6 = new ModelRenderer(this);
			tent6.setRotationPoint(-2.0F, 15.0F, 2.0F);
			setRotationAngle(tent6, -0.418879F, -0.6806784F, 0.0F);
			tent6.setTextureOffset(0, 18).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 8.0F, 0.0F, false);

			tent7 = new ModelRenderer(this);
			tent7.setRotationPoint(3.0F, 15.0F, 1.0F);
			setRotationAngle(tent7, -0.1919862F, -0.6632251F, 0.418879F);
			tent7.setTextureOffset(0, 18).addBox(0.0F, -1.0F, -1.0F, 8.0F, 2.0F, 2.0F, 0.0F, false);

			body = new ModelRenderer(this);
			body.setRotationPoint(1.0F, 16.0F, -1.0F);
			setRotationAngle(body, -0.1919862F, -0.6806784F, 0.0F);
			body.setTextureOffset(0, 0).addBox(-4.0F, -10.0F, -4.0F, 8.0F, 10.0F, 8.0F, 0.0F, false);

			tent8 = new ModelRenderer(this);
			tent8.setRotationPoint(3.0F, 15.0F, -4.0F);
			setRotationAngle(tent8, 0.1919862F, -0.6806784F, 0.0F);
			tent8.setTextureOffset(0, 18).addBox(-1.0F, -1.0F, -8.0F, 2.0F, 2.0F, 8.0F, 0.0F, false);
		}

		@Override
		public void setRotationAngles(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
			float lsa = limbSwingAmount;
			boolean moving = lsa > 0.1F;
			float amp = moving ? 0.4F * lsa : 0.1F;
			float ampBody = moving ? 0.04F * lsa : 0.01F;
			float a = MathHelper.cos(ageInTicks * 0.25F * WING_SPEED) * PI * ampBody;
			float b = MathHelper.cos(ageInTicks * 0.39F * WING_SPEED) * PI * ampBody;
			float n1 = MathHelper.cos(ageInTicks * 1.2F * WING_SPEED) * PI * amp;
			float n2 = MathHelper.cos(ageInTicks * 1.1F * WING_SPEED) * PI * amp;
			float n3 = MathHelper.cos(ageInTicks * 1.0F * WING_SPEED) * PI * amp;
			float n4 = MathHelper.cos(ageInTicks * 1.9F * WING_SPEED) * PI * amp;
			float n5 = MathHelper.cos(ageInTicks * 1.8F * WING_SPEED) * PI * amp;
			float n6 = MathHelper.cos(ageInTicks * 1.7F * WING_SPEED) * PI * amp;
			float n7 = MathHelper.cos(ageInTicks * 1.6F * WING_SPEED) * PI * amp;
			float n8 = MathHelper.cos(ageInTicks * 1.5F * WING_SPEED) * PI * amp;

			tent1.rotateAngleX = n1 - 1.03F;
			tent7.rotateAngleZ = n2 + 0.37F;
			tent5.rotateAngleX = n3 + 0.6F;
			tent6.rotateAngleX = n4 - 0.48F;
			tent4.rotateAngleX = n5 + 0.63F;
			tent2.rotateAngleZ = n6 - 0.26F;
			tent3.rotateAngleX = n7 - 1.03F;
			tent8.rotateAngleX = n8 + 0.43F;

			body.rotateAngleX = a;
			body.rotateAngleZ = b;
			body.rotateAngleY = (float) Math.toRadians(netHeadYaw) * 0.75F;
		}

		@Override
		public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
			tent1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			tent2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			tent3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			tent4.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			tent5.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			tent6.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			tent7.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			body.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			tent8.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		}

		public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
			modelRenderer.rotateAngleX = x;
			modelRenderer.rotateAngleY = y;
			modelRenderer.rotateAngleZ = z;
		}
	}
}
