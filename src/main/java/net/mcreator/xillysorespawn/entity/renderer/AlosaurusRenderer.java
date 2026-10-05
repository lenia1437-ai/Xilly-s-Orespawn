package net.mcreator.xillysorespawn.entity.renderer;

import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.util.math.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Entity;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.MobRenderer;

import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.mojang.blaze3d.matrix.MatrixStack;

import net.mcreator.xillysorespawn.entity.AlosaurusEntity;

@OnlyIn(Dist.CLIENT)
public class AlosaurusRenderer {
	// Если у тебя другой modid - поменяй здесь (текстура: assets/<modid>/textures/alosaurus.png, размер 128x128)
	private static final String TEXTURE = "xillys_orespawn:textures/entities/alosaurus.png";

	public static class ModelRegisterHandler {
		@SubscribeEvent
		@OnlyIn(Dist.CLIENT)
		public void registerModels(ModelRegistryEvent event) {
			RenderingRegistry.registerEntityRenderingHandler(AlosaurusEntity.entity, renderManager -> {
				return new MobRenderer(renderManager, new ModelAlosaurus(), 1.5f) {
					@Override
					public ResourceLocation getEntityTexture(Entity entity) {
						return new ResourceLocation(TEXTURE);
					}
				};
			});
		}
	}

	// Модель Alosaurus из 1.7.10 (ModelAlosaurus), портирована на 1.16.5 вместе с анимацией
	public static class ModelAlosaurus extends EntityModel<Entity> {
		private final ModelRenderer Shape18;
		private final ModelRenderer Shape19;
		private final ModelRenderer Shape20;
		private final ModelRenderer Shape21;
		private final ModelRenderer Shape1;
		private final ModelRenderer Shape2;
		private final ModelRenderer Shape3;
		private final ModelRenderer Shape4;
		private final ModelRenderer Shape5;
		private final ModelRenderer Shape6;
		private final ModelRenderer jaw;
		private final ModelRenderer leftleg;
		private final ModelRenderer leftleg2;
		private final ModelRenderer leftleg3;
		private final ModelRenderer Shape11;
		private final ModelRenderer rightleg;
		private final ModelRenderer rightleg2;
		private final ModelRenderer rightleg3;
		private final ModelRenderer leftleg4;
		private final ModelRenderer rightleg4;
		private final ModelRenderer Shape17;

		private static final float PI = (float) Math.PI;
		private static final float WING_SPEED = 1.0F;

		public ModelAlosaurus() {
			textureWidth = 128;
			textureHeight = 128;

			Shape18 = new ModelRenderer(this);
			Shape18.setRotationPoint(3.3F, -25.0F, -27.0F);
			setRotationAngle(Shape18, 0.5759587F, 0.0F, 0.5585054F);
			Shape18.setTextureOffset(91, 114).addBox(0.0F, 0.0F, 0.0F, 2.0F, 4.0F, 5.0F, 0.0F, false);

			Shape19 = new ModelRenderer(this);
			Shape19.setRotationPoint(-4.0F, -24.0F, -28.0F);
			setRotationAngle(Shape19, 0.5759587F, 0.0F, -0.5585054F);
			Shape19.setTextureOffset(71, 114).addBox(0.0F, 0.0F, 0.0F, 2.0F, 4.0F, 5.0F, 0.0F, false);

			Shape20 = new ModelRenderer(this);
			Shape20.setRotationPoint(5.0F, -8.0F, -6.0F);
			setRotationAngle(Shape20, 0.3839724F, 0.0F, 0.0F);
			Shape20.setTextureOffset(91, 30).addBox(0.0F, 0.0F, 0.0F, 2.0F, 7.0F, 5.0F, 0.0F, false);

			Shape21 = new ModelRenderer(this);
			Shape21.setRotationPoint(-4.0F, -8.0F, -6.0F);
			setRotationAngle(Shape21, 0.3839724F, 0.0F, 0.0F);
			Shape21.setTextureOffset(93, 46).addBox(-2.0F, 0.0F, 0.0F, 2.0F, 7.0F, 5.0F, 0.0F, false);

			Shape1 = new ModelRenderer(this);
			Shape1.setRotationPoint(2.5F, -19.0F, -8.0F);
			Shape1.setTextureOffset(0, 0).addBox(-7.0F, 0.0F, 0.0F, 10.0F, 18.0F, 31.0F, 0.0F, false);

			Shape2 = new ModelRenderer(this);
			Shape2.setRotationPoint(0.5F, -19.0F, 23.0F);
			Shape2.setTextureOffset(62, 0).addBox(-5.0F, 0.0F, 0.0F, 10.0F, 11.0F, 11.0F, 0.0F, false);

			Shape3 = new ModelRenderer(this);
			Shape3.setRotationPoint(0.0F, -19.0F, 34.0F);
			Shape3.setTextureOffset(10, 54).addBox(-3.0F, 0.0F, 0.0F, 7.0F, 7.0F, 25.0F, 0.0F, false);

			Shape4 = new ModelRenderer(this);
			Shape4.setRotationPoint(1.5F, -25.0F, -16.0F);
			setRotationAngle(Shape4, -0.4014257F, 0.0F, 0.0F);
			Shape4.setTextureOffset(68, 88).addBox(-5.0F, 0.0F, 0.0F, 8.0F, 9.0F, 16.0F, 0.0F, false);

			Shape5 = new ModelRenderer(this);
			Shape5.setRotationPoint(-4.0F, -25.0F, -27.0F);
			Shape5.setTextureOffset(75, 65).addBox(0.0F, 0.0F, 0.0F, 9.0F, 9.0F, 12.0F, 0.0F, false);

			Shape6 = new ModelRenderer(this);
			Shape6.setRotationPoint(-3.0F, -25.0F, -36.0F);
			Shape6.setTextureOffset(0, 50).addBox(0.0F, 0.0F, 0.0F, 7.0F, 9.0F, 9.0F, 0.0F, false);

			jaw = new ModelRenderer(this);
			jaw.setRotationPoint(2.0F, -15.0F, -24.0F);
			setRotationAngle(jaw, 0.5201081F, 0.0F, 0.0F);
			jaw.setTextureOffset(0, 86).addBox(-5.0F, 0.0F, -10.0F, 7.0F, 1.0F, 13.0F, 0.0F, false);

			leftleg = new ModelRenderer(this);
			leftleg.setRotationPoint(6.0F, -10.0F, 11.0F);
			setRotationAngle(leftleg, -0.1745329F, 0.0F, 0.0F);
			leftleg.setTextureOffset(0, 0).addBox(-1.0F, 0.0F, 0.0F, 3.0F, 16.0F, 10.0F, 0.0F, false);

			leftleg2 = new ModelRenderer(this);
			leftleg2.setRotationPoint(6.0F, -10.0F, 11.0F);
			setRotationAngle(leftleg2, 0.5061455F, 0.0F, 0.0F);
			leftleg2.setTextureOffset(0, 106).addBox(-1.0F, 12.0F, -8.0F, 3.0F, 15.0F, 5.0F, 0.0F, false);

			leftleg3 = new ModelRenderer(this);
			leftleg3.setRotationPoint(6.0F, -10.0F, 11.0F);
			setRotationAngle(leftleg3, -0.4014257F, 0.0F, 0.0F);
			leftleg3.setTextureOffset(112, 89).addBox(-1.0F, 19.0F, 16.0F, 3.0F, 9.0F, 3.0F, 0.0F, false);

			Shape11 = new ModelRenderer(this);
			Shape11.setRotationPoint(5.0F, -5.0F, -3.0F);
			setRotationAngle(Shape11, -0.5235988F, 0.0F, 0.0F);
			Shape11.setTextureOffset(0, 72).addBox(0.0F, 0.0F, 0.0F, 2.0F, 10.0F, 2.0F, 0.0F, false);

			rightleg = new ModelRenderer(this);
			rightleg.setRotationPoint(-7.0F, -10.0F, 11.0F);
			setRotationAngle(rightleg, -0.1745329F, 0.0F, 0.0F);
			rightleg.setTextureOffset(54, 51).addBox(0.0F, 0.0F, 0.0F, 3.0F, 16.0F, 10.0F, 0.0F, false);

			rightleg2 = new ModelRenderer(this);
			rightleg2.setRotationPoint(-7.0F, -10.0F, 11.0F);
			setRotationAngle(rightleg2, 0.5061455F, 0.0F, 0.0F);
			rightleg2.setTextureOffset(23, 106).addBox(0.0F, 12.0F, -8.0F, 3.0F, 15.0F, 5.0F, 0.0F, false);

			rightleg3 = new ModelRenderer(this);
			rightleg3.setRotationPoint(-7.0F, -10.0F, 11.0F);
			setRotationAngle(rightleg3, -0.4014257F, 0.0F, 0.0F);
			rightleg3.setTextureOffset(70, 90).addBox(0.0F, 19.0F, 16.0F, 3.0F, 9.0F, 3.0F, 0.0F, false);

			leftleg4 = new ModelRenderer(this);
			leftleg4.setRotationPoint(6.0F, -10.0F, 11.0F);
			leftleg4.setTextureOffset(42, 113).addBox(-1.0F, 31.0F, -1.0F, 3.0F, 3.0F, 8.0F, 0.0F, false);

			rightleg4 = new ModelRenderer(this);
			rightleg4.setRotationPoint(-7.0F, -10.0F, 11.0F);
			rightleg4.setTextureOffset(44, 93).addBox(0.0F, 31.0F, -1.0F, 3.0F, 3.0F, 8.0F, 0.0F, false);

			Shape17 = new ModelRenderer(this);
			Shape17.setRotationPoint(-4.0F, -3.533333F, -3.0F);
			setRotationAngle(Shape17, -0.5235988F, 0.0F, 0.0F);
			Shape17.setTextureOffset(112, 60).addBox(-2.0F, 0.0F, 0.0F, 2.0F, 10.0F, 2.0F, 0.0F, false);
		}

		@Override
		public void setRotationAngles(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
			float angle = 0.0F;
			if (limbSwingAmount > 0.1F)
				angle = MathHelper.cos(ageInTicks * 1.3F * WING_SPEED) * PI * 0.25F * limbSwingAmount;

			rightleg.rotateAngleX = -0.174F + angle;
			rightleg2.rotateAngleX = 0.506F + angle;
			rightleg3.rotateAngleX = -0.401F + angle;
			rightleg4.rotateAngleX = angle;

			leftleg.rotateAngleX = -0.174F - angle;
			leftleg2.rotateAngleX = 0.506F - angle;
			leftleg3.rotateAngleX = -0.401F - angle;
			leftleg4.rotateAngleX = -angle;

			// isAggressive() синхронизируется с сервером (ставит MeleeAttackGoal) - аналог getAttacking() из 1.7.10
			boolean attacking = entity instanceof MobEntity && ((MobEntity) entity).isAggressive();
			if (attacking)
				jaw.rotateAngleX = 0.52F + MathHelper.cos(ageInTicks * 0.45F) * PI * 0.18F;
			else
				jaw.rotateAngleX = 0.1F;

			Shape17.rotateAngleX = -0.523F + MathHelper.cos(ageInTicks * 0.1F) * PI * 0.05F;
			Shape11.rotateAngleX = -0.523F + MathHelper.cos(ageInTicks * 0.1F) * PI * 0.05F;
		}

		@Override
		public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
			Shape18.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			Shape19.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			Shape20.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			Shape21.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			Shape1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			Shape2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			Shape3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			Shape4.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			Shape5.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			Shape6.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			jaw.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			leftleg.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			leftleg2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			leftleg3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			Shape11.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			rightleg.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			rightleg2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			rightleg3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			leftleg4.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			rightleg4.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			Shape17.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		}

		public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
			modelRenderer.rotateAngleX = x;
			modelRenderer.rotateAngleY = y;
			modelRenderer.rotateAngleZ = z;
		}
	}
}
