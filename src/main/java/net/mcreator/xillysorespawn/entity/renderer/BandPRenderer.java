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

import net.mcreator.xillysorespawn.entity.BandPEntity;

@OnlyIn(Dist.CLIENT)
public class BandPRenderer {
	// Если у тебя другой modid - поменяй здесь (текстуры: assets/<modid>/textures/bandp0.png и bandp1.png, размер 64x128)
	private static final String TEXTURE_PREFIX = "xillys_orespawn:textures/entities/bandptexture.png";

	public static class ModelRegisterHandler {
		@SubscribeEvent
		@OnlyIn(Dist.CLIENT)
		public void registerModels(ModelRegistryEvent event) {
			RenderingRegistry.registerEntityRenderingHandler(BandPEntity.entity, renderManager -> {
				return new MobRenderer(renderManager, new ModelBandP(), 0.5f) {
					@Override
					public ResourceLocation getEntityTexture(Entity entity) {
						return new ResourceLocation(TEXTURE_PREFIX);
					}
				};
			});
		}
	}

	// Модель BandP из 1.7.10 (ModelBandP), портирована на 1.16.5 вместе с анимацией
	public static class ModelBandP extends EntityModel<Entity> {
		private final ModelRenderer belly;
		private final ModelRenderer chest;
		private final ModelRenderer head;
		private final ModelRenderer lleg;
		private final ModelRenderer rleg;
		private final ModelRenderer larm;
		private final ModelRenderer rarm;

		private static final float PI = (float) Math.PI;
		private static final float WING_SPEED = 1.0F;

		public ModelBandP() {
			textureWidth = 64;
			textureHeight = 128;

			belly = new ModelRenderer(this);
			belly.setRotationPoint(0.0F, 12.0F, 0.0F);
			setRotationAngle(belly, 0.0698132F, 0.0F, 0.0F);
			belly.setTextureOffset(0, 61).addBox(-8.0F, -5.0F, -7.0F, 16.0F, 10.0F, 16.0F, 0.0F, false);

			chest = new ModelRenderer(this);
			chest.setRotationPoint(0.0F, 5.0F, 2.0F);
			chest.setTextureOffset(0, 42).addBox(-5.0F, -3.0F, -5.0F, 10.0F, 6.0F, 10.0F, 0.0F, false);

			head = new ModelRenderer(this);
			head.setRotationPoint(0.0F, 1.0F, 3.0F);
			head.setTextureOffset(0, 11).addBox(-3.0F, -5.0F, -3.0F, 6.0F, 6.0F, 6.0F, 0.0F, false);

			lleg = new ModelRenderer(this);
			lleg.setRotationPoint(2.0F, 16.0F, 2.0F);
			lleg.setTextureOffset(25, 90).addBox(-2.0F, 0.0F, -3.0F, 6.0F, 8.0F, 6.0F, 0.0F, false);

			rleg = new ModelRenderer(this);
			rleg.setRotationPoint(-2.0F, 16.0F, 2.0F);
			rleg.setTextureOffset(0, 90).addBox(-4.0F, 0.0F, -3.0F, 6.0F, 8.0F, 6.0F, 0.0F, false);

			larm = new ModelRenderer(this);
			larm.setRotationPoint(6.0F, 4.0F, 3.0F);
			setRotationAngle(larm, 0.0F, 0.0F, -0.4886922F);
			larm.setTextureOffset(0, 25).addBox(-1.0F, -1.0F, -2.0F, 4.0F, 10.0F, 4.0F, 0.0F, false);

			rarm = new ModelRenderer(this);
			rarm.setRotationPoint(-6.0F, 4.0F, 3.0F);
			setRotationAngle(rarm, 0.0F, 0.0F, 0.4886922F);
			rarm.setTextureOffset(18, 25).addBox(-3.0F, -1.0F, -2.0F, 4.0F, 10.0F, 4.0F, 0.0F, false);
		}

		@Override
		public void setRotationAngles(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
			float a;
			float a2;
			float a3;
			if (limbSwingAmount > 0.1F) {
				a = MathHelper.cos(ageInTicks * 1.3F * WING_SPEED) * PI * 0.25F * limbSwingAmount;
				a2 = MathHelper.cos(ageInTicks * 2.6F * WING_SPEED) * PI * 0.025F * limbSwingAmount;
				a3 = a;
			} else {
				a = 0.0F;
				a2 = MathHelper.cos(ageInTicks * 0.6F * WING_SPEED) * PI * 0.005F;
				a3 = MathHelper.cos(ageInTicks * 0.3F * WING_SPEED) * PI * 0.02F;
			}
			lleg.rotateAngleX = a;
			rleg.rotateAngleX = -a;
			belly.rotateAngleX = 0.07F + a2;
			larm.rotateAngleX = -a3;
			rarm.rotateAngleX = a3;
			belly.rotateAngleY = -a / 2.0F;

			head.rotateAngleY = (float) Math.toRadians(netHeadYaw);
			head.rotateAngleX = (float) Math.toRadians(headPitch);
		}

		@Override
		public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
			belly.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			chest.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			head.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			lleg.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			rleg.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			larm.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			rarm.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		}

		public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
			modelRenderer.rotateAngleX = x;
			modelRenderer.rotateAngleY = y;
			modelRenderer.rotateAngleZ = z;
		}
	}
}
