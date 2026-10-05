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

import net.mcreator.xillysorespawn.entity.BasiliskEntity;

@OnlyIn(Dist.CLIENT)
public class BasiliskRenderer {
	// Если у тебя другой modid - поменяй здесь (текстура: assets/<modid>/textures/basilisk.png, размер 256x64)
	private static final String TEXTURE = "xillys_orespawn:textures/entities/basilisk.png";

	public static class ModelRegisterHandler {
		@SubscribeEvent
		@OnlyIn(Dist.CLIENT)
		public void registerModels(ModelRegistryEvent event) {
			RenderingRegistry.registerEntityRenderingHandler(BasiliskEntity.entity, renderManager -> {
				return new MobRenderer(renderManager, new ModelBasilisk(), 1.5f) {
					@Override
					public ResourceLocation getEntityTexture(Entity entity) {
						return new ResourceLocation(TEXTURE);
					}
				};
			});
		}
	}

	// Модель Basilisk из 1.7.10 (ModelBasilisk), портирована на 1.16.5 вместе с анимацией
	public static class ModelBasilisk extends EntityModel<Entity> {
		private final ModelRenderer body3;
		private final ModelRenderer body2;
		private final ModelRenderer body1;
		private final ModelRenderer body4;
		private final ModelRenderer body5;
		private final ModelRenderer body6;
		private final ModelRenderer tail1;
		private final ModelRenderer tail2;
		private final ModelRenderer tail3;
		private final ModelRenderer tail4;
		private final ModelRenderer neck2;
		private final ModelRenderer neck1;
		private final ModelRenderer head;
		private final ModelRenderer rog_1;
		private final ModelRenderer rog_2;
		private final ModelRenderer rog_3;
		private final ModelRenderer rog_4;
		private final ModelRenderer rog_5;
		private final ModelRenderer rog_6;
		private final ModelRenderer snout;
		private final ModelRenderer jaw;
		// Сегменты тела и хвоста: каждый следующий крепится к концу предыдущего (змеиная волна)
		private final ModelRenderer[] seg;
		private final float[] segLen = {12.0F, 11.0F, 12.0F, 12.0F, 12.0F, 12.0F, 10.0F, 10.0F, 10.0F, 0.0F};
		private final float[] baseX = new float[10];
		private final float[] baseZ = new float[10];

		private static final float PI = (float) Math.PI;
		private static final float WING_SPEED = 1.0F;

		public ModelBasilisk() {
			textureWidth = 256;
			textureHeight = 64;

			body3 = new ModelRenderer(this);
			body3.setRotationPoint(-8.0F, 8.0F, 0.0F);
			body3.setTextureOffset(0, 32).addBox(0.0F, 0.0F, 0.0F, 16.0F, 16.0F, 16.0F, 0.0F, false);

			body2 = new ModelRenderer(this);
			body2.setRotationPoint(-8.0F, 4.0F, -10.0F);
			setRotationAngle(body2, -0.2974289F, 0.0F, 0.0F);
			body2.setTextureOffset(0, 32).addBox(0.0F, 0.0F, 0.0F, 16.0F, 16.0F, 16.0F, 0.0F, false);

			body1 = new ModelRenderer(this);
			body1.setRotationPoint(-8.0F, 2.0F, -25.0F);
			setRotationAngle(body1, -0.1487144F, 0.0F, 0.0F);
			body1.setTextureOffset(0, 32).addBox(0.0F, 0.0F, 0.0F, 16.0F, 16.0F, 16.0F, 0.0F, false);

			body4 = new ModelRenderer(this);
			body4.setRotationPoint(-8.0F, 8.0F, 13.0F);
			setRotationAngle(body4, 0.1487144F, 0.0F, 0.0F);
			body4.setTextureOffset(0, 32).addBox(0.0F, 0.0F, 0.0F, 16.0F, 16.0F, 16.0F, 0.0F, false);

			body5 = new ModelRenderer(this);
			body5.setRotationPoint(-8.0F, 5.8F, 28.8F);
			body5.setTextureOffset(0, 32).addBox(0.0F, 0.0F, 0.0F, 16.0F, 16.0F, 16.0F, 0.0F, false);

			body6 = new ModelRenderer(this);
			body6.setRotationPoint(-7.5F, 6.166667F, 44.0F);
			setRotationAngle(body6, -0.1115358F, 0.0F, 0.0F);
			body6.setTextureOffset(148, 4).addBox(0.0F, 0.0F, 0.0F, 15.0F, 15.0F, 17.0F, 0.0F, false);

			tail1 = new ModelRenderer(this);
			tail1.setRotationPoint(-6.5F, 9.0F, 58.0F);
			setRotationAngle(tail1, 0.1115358F, 0.0F, 0.0F);
			tail1.setTextureOffset(140, 36).addBox(0.0F, 0.0F, 0.0F, 13.0F, 13.0F, 15.0F, 0.0F, false);

			tail2 = new ModelRenderer(this);
			tail2.setRotationPoint(-5.0F, 10.0F, 70.0F);
			setRotationAngle(tail2, 0.4089647F, 0.0F, 0.0F);
			tail2.setTextureOffset(64, 41).addBox(0.0F, 0.0F, 0.0F, 10.0F, 10.0F, 13.0F, 0.0F, false);

			tail3 = new ModelRenderer(this);
			tail3.setRotationPoint(-4.0F, 6.0F, 82.0F);
			setRotationAngle(tail3, 0.2230717F, 0.0F, 0.0F);
			tail3.setTextureOffset(64, 20).addBox(0.0F, 0.0F, 0.0F, 8.0F, 8.0F, 13.0F, 0.0F, false);

			tail4 = new ModelRenderer(this);
			tail4.setRotationPoint(-3.0F, 4.0F, 95.0F);
			setRotationAngle(tail4, -0.0743572F, 0.0F, 0.0F);
			tail4.setTextureOffset(64, 1).addBox(0.0F, 0.0F, 0.0F, 6.0F, 6.0F, 13.0F, 0.0F, false);

			neck2 = new ModelRenderer(this);
			neck2.setRotationPoint(-8.0F, -4.9F, -26.0F);
			setRotationAngle(neck2, -0.8464847F, 0.0F, 0.0F);
			neck2.setTextureOffset(0, 32).addBox(0.0F, 0.0F, 0.0F, 16.0F, 16.0F, 16.0F, 0.0F, false);

			neck1 = new ModelRenderer(this);
			neck1.setRotationPoint(-8.0F, -15.0F, -29.0F);
			setRotationAngle(neck1, -1.181092F, 0.0F, 0.0F);
			neck1.setTextureOffset(0, 32).addBox(0.0F, 0.0F, 0.0F, 16.0F, 16.0F, 16.0F, 0.0F, false);

			head = new ModelRenderer(this);
			head.setRotationPoint(-8.0F, -21.0F, -30.0F);
			setRotationAngle(head, -1.404164F, 0.0F, 0.0F);
			head.setTextureOffset(0, 0).addBox(0.0F, 0.0F, 0.0F, 16.0F, 18.0F, 10.0F, 0.0F, false);

			rog_1 = new ModelRenderer(this);
			rog_1.setRotationPoint(3.0F, -21.0F, -32.0F);
			setRotationAngle(rog_1, 0.6320364F, 0.2230717F, 0.0F);
			rog_1.setTextureOffset(110, 45).addBox(0.0F, 0.0F, 0.0F, 3.0F, 3.0F, 5.0F, 0.0F, false);

			rog_2 = new ModelRenderer(this);
			rog_2.setRotationPoint(-6.0F, -21.0F, -32.8F);
			setRotationAngle(rog_2, 0.6320364F, -0.2230705F, 0.0F);
			rog_2.setTextureOffset(110, 45).addBox(0.0F, 0.0F, 0.0F, 3.0F, 3.0F, 5.0F, 0.0F, false);

			rog_3 = new ModelRenderer(this);
			rog_3.setRotationPoint(0.4666667F, -21.0F, -31.0F);
			setRotationAngle(rog_3, 0.6320364F, 0.2230717F, 0.0F);
			rog_3.setTextureOffset(52, 0).addBox(0.0F, 0.0F, 0.0F, 2.0F, 2.0F, 4.0F, 0.0F, false);

			rog_4 = new ModelRenderer(this);
			rog_4.setRotationPoint(-2.466667F, -21.0F, -31.46667F);
			setRotationAngle(rog_4, 0.6320364F, -0.2230705F, 0.0F);
			rog_4.setTextureOffset(52, 0).addBox(0.0F, 0.0F, 0.0F, 2.0F, 2.0F, 4.0F, 0.0F, false);

			rog_5 = new ModelRenderer(this);
			rog_5.setRotationPoint(-8.0F, -17.0F, -32.0F);
			setRotationAngle(rog_5, 0.6320364F, -0.6692139F, 0.0F);
			rog_5.setTextureOffset(52, 0).addBox(0.0F, 0.0F, 0.0F, 2.0F, 2.0F, 4.0F, 0.0F, false);

			rog_6 = new ModelRenderer(this);
			rog_6.setRotationPoint(6.4F, -17.0F, -32.0F);
			setRotationAngle(rog_6, 0.6320364F, 0.6692116F, 0.0F);
			rog_6.setTextureOffset(52, 0).addBox(0.0F, 0.0F, 0.0F, 2.0F, 2.0F, 4.0F, 0.0F, false);

			snout = new ModelRenderer(this);
			snout.setRotationPoint(-7.0F, -17.0F, -43.0F);
			setRotationAngle(snout, -1.404164F, 0.0F, 0.0F);
			snout.setTextureOffset(102, 1).addBox(0.0F, 0.0F, 0.0F, 14.0F, 16.0F, 9.0F, 0.0F, false);

			jaw = new ModelRenderer(this);
			jaw.setRotationPoint(-7.0F, -11.0F, -39.0F);
			setRotationAngle(jaw, -0.8836633F, 0.0F, 0.0F);
			jaw.setTextureOffset(106, 26).addBox(0.0F, 0.0F, 0.0F, 14.0F, 16.0F, 3.0F, 0.0F, false);

			seg = new ModelRenderer[]{body1, body2, body3, body4, body5, body6, tail1, tail2, tail3, tail4};
			for (int i = 0; i < seg.length; i++) {
				baseX[i] = seg[i].rotationPointX;
				baseZ[i] = seg[i].rotationPointZ;
			}
		}

		@Override
		public void setRotationAngles(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
			float pi4 = 0.7853975F;
			float dx = 0.0F;
			float dz = 0.0F;
			for (int k = 0; k < seg.length; k++) {
				float yaw = MathHelper.cos(ageInTicks * 1.3F * WING_SPEED - k * pi4) * PI * 0.1F * limbSwingAmount;
				seg[k].rotateAngleY = yaw;
				// сдвиг относительно исходной позы, чтобы в покое всё стояло как в оригинальной модели
				seg[k].rotationPointX = baseX[k] + dx;
				seg[k].rotationPointZ = baseZ[k] + dz;
				dx += MathHelper.sin(yaw) * segLen[k];
				dz += (MathHelper.cos(yaw) - 1.0F) * segLen[k];
			}

			// isAggressive() синхронизируется с сервером - аналог getAttacking() из 1.7.10
			boolean attacking = entity instanceof MobEntity && ((MobEntity) entity).isAggressive();
			if (attacking)
				jaw.rotateAngleX = -1.0F + MathHelper.cos(ageInTicks * 0.45F) * PI * 0.18F;
			else
				jaw.rotateAngleX = -1.1F;
		}

		@Override
		public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
			body3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			body2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			body1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			body4.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			body5.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			body6.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			tail1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			tail2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			tail3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			tail4.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			neck2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			neck1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			head.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			rog_1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			rog_2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			rog_3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			rog_4.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			rog_5.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			rog_6.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			snout.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			jaw.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		}

		public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
			modelRenderer.rotateAngleX = x;
			modelRenderer.rotateAngleY = y;
			modelRenderer.rotateAngleZ = z;
		}
	}
}
