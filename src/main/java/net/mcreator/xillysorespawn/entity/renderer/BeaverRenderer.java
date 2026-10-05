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

import net.mcreator.xillysorespawn.entity.BeaverEntity;

@OnlyIn(Dist.CLIENT)
public class BeaverRenderer {
	// Если у тебя другой modid - поменяй здесь (текстура: assets/<modid>/textures/beaver.png, размер 64x32)
	private static final String TEXTURE = "xillys_orespawn:textures/entities/beavertexture.png";

	public static class ModelRegisterHandler {
		@SubscribeEvent
		@OnlyIn(Dist.CLIENT)
		public void registerModels(ModelRegistryEvent event) {
			RenderingRegistry.registerEntityRenderingHandler(BeaverEntity.entity, renderManager -> {
				return new MobRenderer(renderManager, new ModelBeaver(), 0.3f) {
					@Override
					public ResourceLocation getEntityTexture(Entity entity) {
						return new ResourceLocation(TEXTURE);
					}
				};
			});
		}
	}

	// Модель Beaver из 1.7.10 (ModelBeaver), портирована на 1.16.5 вместе с анимацией
	public static class ModelBeaver extends EntityModel<Entity> {
		private final ModelRenderer head;
		private final ModelRenderer nose;
		private final ModelRenderer teeth;
		private final ModelRenderer body;
		private final ModelRenderer tail;
		private final ModelRenderer rff;
		private final ModelRenderer lff;
		private final ModelRenderer rrf;
		private final ModelRenderer lrf;

		private static final float PI = (float) Math.PI;
		private static final float WING_SPEED = 1.0F;

		public ModelBeaver() {
			textureWidth = 64;
			textureHeight = 32;

			head = new ModelRenderer(this);
			head.setRotationPoint(0.0F, 15.0F, -8.0F);
			head.setTextureOffset(0, 3).addBox(0.0F, 0.0F, 0.0F, 6.0F, 5.0F, 5.0F, 0.0F, false);

			nose = new ModelRenderer(this);
			nose.setRotationPoint(2.0F, 18.0F, -8.5F);
			nose.setTextureOffset(6, 0).addBox(0.0F, 0.0F, 0.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);

			teeth = new ModelRenderer(this);
			teeth.setRotationPoint(2.0F, 19.0F, -8.2F);
			teeth.setTextureOffset(0, 0).addBox(0.0F, 0.0F, 0.0F, 2.0F, 2.0F, 1.0F, 0.0F, false);

			body = new ModelRenderer(this);
			body.setRotationPoint(-1.0F, 14.0F, -3.0F);
			body.setTextureOffset(0, 13).addBox(0.0F, 0.0F, 0.0F, 8.0F, 8.0F, 10.0F, 0.0F, false);

			tail = new ModelRenderer(this);
			tail.setRotationPoint(0.5F, 21.0F, 7.0F);
			tail.setTextureOffset(22, 0).addBox(0.0F, -1.0F, 0.0F, 5.0F, 1.0F, 8.0F, 0.0F, false);

			rff = new ModelRenderer(this);
			rff.setRotationPoint(-0.5F, 22.0F, -2.5F);
			rff.setTextureOffset(22, 9).addBox(0.0F, 0.0F, 0.0F, 2.0F, 2.0F, 2.0F, 0.0F, false);

			lff = new ModelRenderer(this);
			lff.setRotationPoint(4.5F, 22.0F, -2.5F);
			lff.setTextureOffset(22, 9).addBox(0.0F, 0.0F, 0.0F, 2.0F, 2.0F, 2.0F, 0.0F, false);

			rrf = new ModelRenderer(this);
			rrf.setRotationPoint(-0.5F, 22.0F, 4.5F);
			rrf.setTextureOffset(22, 9).addBox(0.0F, 0.0F, 0.0F, 2.0F, 2.0F, 2.0F, 0.0F, false);

			lrf = new ModelRenderer(this);
			lrf.setRotationPoint(4.5F, 22.0F, 4.5F);
			lrf.setTextureOffset(22, 9).addBox(0.0F, 0.0F, 0.0F, 2.0F, 2.0F, 2.0F, 0.0F, false);
		}

		@Override
		public void setRotationAngles(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
			float a = MathHelper.cos(ageInTicks * 3.7F * WING_SPEED) * PI * 0.45F * limbSwingAmount;
			lrf.rotateAngleX = a;
			rrf.rotateAngleX = -a;

			teeth.rotateAngleX = MathHelper.cos(ageInTicks * 2.7F * WING_SPEED) * PI * 0.25F;
			tail.rotateAngleX = MathHelper.cos(ageInTicks * 0.5F * WING_SPEED) * PI * 0.05F;
		}

		@Override
		public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
			head.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			nose.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			teeth.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			body.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			tail.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			rff.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			lff.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			rrf.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			lrf.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		}

		public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
			modelRenderer.rotateAngleX = x;
			modelRenderer.rotateAngleY = y;
			modelRenderer.rotateAngleZ = z;
		}
	}
}
