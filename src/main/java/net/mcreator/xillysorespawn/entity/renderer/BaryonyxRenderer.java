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

import net.mcreator.xillysorespawn.entity.BaryonyxEntity;

@OnlyIn(Dist.CLIENT)
public class BaryonyxRenderer {
	// Если у тебя другой modid - поменяй здесь (текстура: assets/<modid>/textures/baryonyx.png, размер 128x128)
	private static final String TEXTURE = "xillys_orespawn:textures/entities/baryonyx.png";

	public static class ModelRegisterHandler {
		@SubscribeEvent
		@OnlyIn(Dist.CLIENT)
		public void registerModels(ModelRegistryEvent event) {
			RenderingRegistry.registerEntityRenderingHandler(BaryonyxEntity.entity, renderManager -> {
				return new MobRenderer(renderManager, new ModelBaryonyx(), 1.0f) {
					@Override
					public ResourceLocation getEntityTexture(Entity entity) {
						return new ResourceLocation(TEXTURE);
					}
				};
			});
		}
	}

	// Модель Baryonyx из 1.7.10 (ModelBaryonyx), портирована на 1.16.5 вместе с анимацией
	public static class ModelBaryonyx extends EntityModel<Entity> {
		private final ModelRenderer Shape27;
		private final ModelRenderer Shape28;
		private final ModelRenderer Shape29;
		private final ModelRenderer Shape30;
		private final ModelRenderer Shape31;
		private final ModelRenderer Shape32;
		private final ModelRenderer Shape33;
		private final ModelRenderer Shape34;
		private final ModelRenderer Shape35;
		private final ModelRenderer Shape36;
		private final ModelRenderer Shape37;
		private final ModelRenderer Shape38;
		private final ModelRenderer Shape39;
		private final ModelRenderer Shape40;
		private final ModelRenderer Shape41;
		private final ModelRenderer Shape42;
		private final ModelRenderer Shape43;
		private final ModelRenderer Shape44;
		private final ModelRenderer Shape45;
		private final ModelRenderer Shape46;
		private final ModelRenderer Shape47;
		private final ModelRenderer Shape48;
		private final ModelRenderer Shape49;
		private final ModelRenderer Shape50;
		private final ModelRenderer Shape51;
		private final ModelRenderer Shape1;
		private final ModelRenderer Shape2;
		private final ModelRenderer Shape3;
		private final ModelRenderer Shape4;
		private final ModelRenderer Shape5;
		private final ModelRenderer Shape6;
		private final ModelRenderer Shape7;
		private final ModelRenderer Shape8;
		private final ModelRenderer Shape9;
		private final ModelRenderer Shape10;
		private final ModelRenderer Shape11;
		private final ModelRenderer Shape12;
		private final ModelRenderer Shape13;
		private final ModelRenderer Shape14;
		private final ModelRenderer Shape15;
		private final ModelRenderer Shape16;
		private final ModelRenderer Shape17;
		private final ModelRenderer Shape18;
		private final ModelRenderer Shape19;
		private final ModelRenderer Shape20;
		private final ModelRenderer Shape21;
		private final ModelRenderer Shape22;
		private final ModelRenderer Shape23;
		private final ModelRenderer Shape24;
		private final ModelRenderer Shape25;
		private final ModelRenderer Shape26;
		private final ModelRenderer Shape52;

		private static final float PI = (float) Math.PI;
		private static final float WING_SPEED = 1.0F;

		public ModelBaryonyx() {
			textureWidth = 128;
			textureHeight = 128;

			Shape27 = new ModelRenderer(this);
			Shape27.setRotationPoint(0.0F, -17.0F, -10.0F);
			Shape27.setTextureOffset(0, 0).addBox(0.0F, 0.0F, 0.0F, 0.0F, 2.0F, 1.0F, 0.0F, false);

			Shape28 = new ModelRenderer(this);
			Shape28.setRotationPoint(0.0F, -17.0F, -7.0F);
			Shape28.setTextureOffset(0, 0).addBox(0.0F, 0.0F, 0.0F, 0.0F, 2.0F, 1.0F, 0.0F, false);

			Shape29 = new ModelRenderer(this);
			Shape29.setRotationPoint(0.0F, -17.0F, -4.0F);
			Shape29.setTextureOffset(0, 0).addBox(0.0F, 0.0F, 0.0F, 0.0F, 2.0F, 1.0F, 0.0F, false);

			Shape30 = new ModelRenderer(this);
			Shape30.setRotationPoint(0.0F, -17.0F, -1.0F);
			Shape30.setTextureOffset(0, 0).addBox(0.0F, 0.0F, 0.0F, 0.0F, 2.0F, 1.0F, 0.0F, false);

			Shape31 = new ModelRenderer(this);
			Shape31.setRotationPoint(0.0F, -17.0F, 2.0F);
			Shape31.setTextureOffset(0, 0).addBox(0.0F, 0.0F, 0.0F, 0.0F, 2.0F, 1.0F, 0.0F, false);

			Shape32 = new ModelRenderer(this);
			Shape32.setRotationPoint(0.0F, -17.0F, 5.0F);
			Shape32.setTextureOffset(0, 0).addBox(0.0F, 0.0F, 0.0F, 0.0F, 2.0F, 1.0F, 0.0F, false);

			Shape33 = new ModelRenderer(this);
			Shape33.setRotationPoint(0.0F, -17.0F, 8.0F);
			Shape33.setTextureOffset(0, 0).addBox(0.0F, 0.0F, 0.0F, 0.0F, 2.0F, 1.0F, 0.0F, false);

			Shape34 = new ModelRenderer(this);
			Shape34.setRotationPoint(0.0F, -17.0F, 11.0F);
			Shape34.setTextureOffset(0, 0).addBox(0.0F, 0.0F, 0.0F, 0.0F, 2.0F, 1.0F, 0.0F, false);

			Shape35 = new ModelRenderer(this);
			Shape35.setRotationPoint(0.0F, -17.0F, 14.0F);
			Shape35.setTextureOffset(0, 0).addBox(0.0F, 0.0F, 0.0F, 0.0F, 2.0F, 1.0F, 0.0F, false);

			Shape36 = new ModelRenderer(this);
			Shape36.setRotationPoint(0.0F, -17.0F, 17.0F);
			Shape36.setTextureOffset(0, 0).addBox(0.0F, 0.0F, 0.0F, 0.0F, 2.0F, 1.0F, 0.0F, false);

			Shape37 = new ModelRenderer(this);
			Shape37.setRotationPoint(0.0F, -17.0F, 20.0F);
			Shape37.setTextureOffset(0, 0).addBox(0.0F, 0.0F, 0.0F, 0.0F, 2.0F, 1.0F, 0.0F, false);

			Shape38 = new ModelRenderer(this);
			Shape38.setRotationPoint(0.0F, -17.0F, 23.0F);
			Shape38.setTextureOffset(0, 0).addBox(0.0F, 0.0F, 0.0F, 0.0F, 2.0F, 1.0F, 0.0F, false);

			Shape39 = new ModelRenderer(this);
			Shape39.setRotationPoint(0.0F, -17.0F, 26.0F);
			Shape39.setTextureOffset(0, 0).addBox(0.0F, 0.0F, 0.0F, 0.0F, 2.0F, 1.0F, 0.0F, false);

			Shape40 = new ModelRenderer(this);
			Shape40.setRotationPoint(0.0F, -17.0F, 29.0F);
			Shape40.setTextureOffset(0, 0).addBox(0.0F, 0.0F, 0.0F, 0.0F, 2.0F, 1.0F, 0.0F, false);

			Shape41 = new ModelRenderer(this);
			Shape41.setRotationPoint(0.0F, -17.0F, 32.0F);
			Shape41.setTextureOffset(0, 0).addBox(0.0F, 0.0F, 0.0F, 0.0F, 2.0F, 1.0F, 0.0F, false);

			Shape42 = new ModelRenderer(this);
			Shape42.setRotationPoint(0.0F, -17.0F, 35.0F);
			Shape42.setTextureOffset(0, 0).addBox(0.0F, 0.0F, 0.0F, 0.0F, 2.0F, 1.0F, 0.0F, false);

			Shape43 = new ModelRenderer(this);
			Shape43.setRotationPoint(0.0F, -17.0F, 38.0F);
			Shape43.setTextureOffset(0, 0).addBox(0.0F, 0.0F, 0.0F, 0.0F, 2.0F, 1.0F, 0.0F, false);

			Shape44 = new ModelRenderer(this);
			Shape44.setRotationPoint(0.0F, -17.0F, 41.0F);
			Shape44.setTextureOffset(0, 0).addBox(0.0F, 0.0F, 0.0F, 0.0F, 2.0F, 1.0F, 0.0F, false);

			Shape45 = new ModelRenderer(this);
			Shape45.setRotationPoint(0.0F, -17.0F, 44.0F);
			Shape45.setTextureOffset(0, 0).addBox(0.0F, 0.0F, 0.0F, 0.0F, 2.0F, 1.0F, 0.0F, false);

			Shape46 = new ModelRenderer(this);
			Shape46.setRotationPoint(0.0F, -12.0F, -11.0F);
			Shape46.setTextureOffset(0, 0).addBox(0.0F, 0.0F, 0.0F, 0.0F, 2.0F, 1.0F, 0.0F, false);

			Shape47 = new ModelRenderer(this);
			Shape47.setRotationPoint(0.0F, -13.0F, -13.0F);
			Shape47.setTextureOffset(0, 0).addBox(0.0F, 0.0F, 0.0F, 0.0F, 2.0F, 1.0F, 0.0F, false);

			Shape48 = new ModelRenderer(this);
			Shape48.setRotationPoint(0.0F, -15.0F, -15.0F);
			Shape48.setTextureOffset(0, 0).addBox(0.0F, 0.0F, 0.0F, 0.0F, 2.0F, 1.0F, 0.0F, false);

			Shape49 = new ModelRenderer(this);
			Shape49.setRotationPoint(0.0F, -16.0F, -16.0F);
			Shape49.setTextureOffset(0, 0).addBox(0.0F, 0.0F, 0.0F, 0.0F, 2.0F, 1.0F, 0.0F, false);

			Shape50 = new ModelRenderer(this);
			Shape50.setRotationPoint(0.0F, -19.0F, -17.0F);
			Shape50.setTextureOffset(0, 0).addBox(0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.0F, false);

			Shape51 = new ModelRenderer(this);
			Shape51.setRotationPoint(0.0F, -19.0F, -19.0F);
			Shape51.setTextureOffset(0, 0).addBox(0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.0F, false);

			Shape1 = new ModelRenderer(this);
			Shape1.setRotationPoint(-5.0F, -15.0F, -10.0F);
			Shape1.setTextureOffset(0, 0).addBox(0.0F, 0.0F, 0.0F, 10.0F, 17.0F, 25.0F, 0.0F, false);

			Shape2 = new ModelRenderer(this);
			Shape2.setRotationPoint(0.0F, -10.0F, -6.0F);
			setRotationAngle(Shape2, -0.1919862F, 0.0F, 0.0F);
			Shape2.setTextureOffset(0, 93).addBox(-3.0F, 0.0F, -11.0F, 6.0F, 10.0F, 11.0F, 0.0F, false);

			Shape3 = new ModelRenderer(this);
			Shape3.setRotationPoint(0.0F, -10.0F, -11.0F);
			setRotationAngle(Shape3, 0.7504916F, 0.0F, 0.0F);
			Shape3.setTextureOffset(29, 110).addBox(-2.0F, -9.0F, -8.0F, 4.0F, 9.0F, 8.0F, 0.0F, false);

			Shape4 = new ModelRenderer(this);
			Shape4.setRotationPoint(-3.0F, -18.0F, -28.0F);
			Shape4.setTextureOffset(54, 108).addBox(0.0F, 0.0F, 0.0F, 6.0F, 7.0F, 12.0F, 0.0F, false);

			Shape5 = new ModelRenderer(this);
			Shape5.setRotationPoint(-1.5F, -17.5F, -43.0F);
			Shape5.setTextureOffset(54, 86).addBox(0.0F, 0.0F, 0.0F, 3.0F, 6.0F, 15.0F, 0.0F, false);

			Shape6 = new ModelRenderer(this);
			Shape6.setRotationPoint(-4.0F, -15.0F, 15.0F);
			Shape6.setTextureOffset(0, 43).addBox(0.0F, 0.0F, 0.0F, 8.0F, 11.0F, 8.0F, 0.0F, false);

			Shape7 = new ModelRenderer(this);
			Shape7.setRotationPoint(-3.0F, -15.0F, 23.0F);
			Shape7.setTextureOffset(0, 63).addBox(0.0F, 0.0F, 0.0F, 6.0F, 6.0F, 23.0F, 0.0F, false);

			Shape8 = new ModelRenderer(this);
			Shape8.setRotationPoint(5.0F, 0.0F, -7.0F);
			Shape8.setTextureOffset(47, 0).addBox(0.0F, 0.0F, 0.0F, 2.0F, 5.0F, 3.0F, 0.0F, false);

			Shape9 = new ModelRenderer(this);
			Shape9.setRotationPoint(5.1F, 3.0F, -6.0F);
			setRotationAngle(Shape9, -0.3839724F, 0.0F, 0.0F);
			Shape9.setTextureOffset(49, 10).addBox(0.0F, 0.0F, 0.0F, 2.0F, 6.0F, 2.0F, 0.0F, false);

			Shape10 = new ModelRenderer(this);
			Shape10.setRotationPoint(5.0F, 7.0F, -8.0F);
			Shape10.setTextureOffset(13, 17).addBox(0.0F, 0.0F, 0.0F, 2.0F, 4.0F, 3.0F, 0.0F, false);

			Shape11 = new ModelRenderer(this);
			Shape11.setRotationPoint(5.0F, 8.0F, -8.0F);
			Shape11.setTextureOffset(0, 17).addBox(0.0F, 0.0F, -2.0F, 1.0F, 1.0F, 2.0F, 0.0F, false);

			Shape12 = new ModelRenderer(this);
			Shape12.setRotationPoint(5.0F, 9.0F, -11.0F);
			Shape12.setTextureOffset(0, 21).addBox(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);

			Shape13 = new ModelRenderer(this);
			Shape13.setRotationPoint(5.0F, -15.0F, 2.0F);
			Shape13.setTextureOffset(95, 36).addBox(0.0F, 0.0F, 0.0F, 3.0F, 21.0F, 13.0F, 0.0F, false);

			Shape14 = new ModelRenderer(this);
			Shape14.setRotationPoint(-1.5F, -17.0F, -43.0F);
			Shape14.setTextureOffset(36, 94).addBox(0.0F, 0.0F, -3.0F, 3.0F, 5.0F, 3.0F, 0.0F, false);

			Shape15 = new ModelRenderer(this);
			Shape15.setRotationPoint(5.0F, -15.0F, 2.0F);
			setRotationAngle(Shape15, -0.1745329F, 0.0F, 0.0F);
			Shape15.setTextureOffset(113, 71).addBox(0.0F, 18.0F, 8.0F, 3.0F, 18.0F, 4.0F, 0.0F, false);

			Shape16 = new ModelRenderer(this);
			Shape16.setRotationPoint(5.0F, 10.0F, -8.0F);
			Shape16.setTextureOffset(13, 11).addBox(-2.0F, 0.0F, 0.0F, 2.0F, 1.0F, 3.0F, 0.0F, false);

			Shape17 = new ModelRenderer(this);
			Shape17.setRotationPoint(5.0F, -15.0F, 2.0F);
			Shape17.setTextureOffset(0, 74).addBox(0.0F, 35.0F, -1.0F, 3.0F, 3.0F, 6.0F, 0.0F, false);

			Shape18 = new ModelRenderer(this);
			Shape18.setRotationPoint(-5.0F, 0.0F, -7.0F);
			Shape18.setTextureOffset(58, 0).addBox(-2.0F, 0.0F, 0.0F, 2.0F, 5.0F, 3.0F, 0.0F, false);

			Shape19 = new ModelRenderer(this);
			Shape19.setRotationPoint(-5.1F, 3.0F, -6.0F);
			setRotationAngle(Shape19, -0.3839724F, 0.0F, 0.0F);
			Shape19.setTextureOffset(59, 10).addBox(-2.0F, 0.0F, 0.0F, 2.0F, 6.0F, 2.0F, 0.0F, false);

			Shape20 = new ModelRenderer(this);
			Shape20.setRotationPoint(-5.0F, 7.0F, -8.0F);
			Shape20.setTextureOffset(71, 5).addBox(-2.0F, 0.0F, 0.0F, 2.0F, 4.0F, 3.0F, 0.0F, false);

			Shape21 = new ModelRenderer(this);
			Shape21.setRotationPoint(-5.0F, 10.0F, -8.0F);
			Shape21.setTextureOffset(71, 0).addBox(0.0F, 0.0F, 0.0F, 2.0F, 1.0F, 3.0F, 0.0F, false);

			Shape22 = new ModelRenderer(this);
			Shape22.setRotationPoint(-5.0F, 8.0F, -8.0F);
			Shape22.setTextureOffset(0, 10).addBox(-1.0F, 0.0F, -2.0F, 1.0F, 1.0F, 2.0F, 0.0F, false);

			Shape23 = new ModelRenderer(this);
			Shape23.setRotationPoint(-5.0F, 9.0F, -11.0F);
			Shape23.setTextureOffset(0, 14).addBox(-1.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);

			Shape24 = new ModelRenderer(this);
			Shape24.setRotationPoint(-5.0F, -15.0F, 2.0F);
			Shape24.setTextureOffset(95, 0).addBox(-3.0F, 0.0F, 0.0F, 3.0F, 22.0F, 13.0F, 0.0F, false);

			Shape25 = new ModelRenderer(this);
			Shape25.setRotationPoint(-5.0F, -15.0F, 2.0F);
			setRotationAngle(Shape25, -0.1745329F, 0.0F, 0.0F);
			Shape25.setTextureOffset(96, 71).addBox(-3.0F, 18.0F, 8.0F, 3.0F, 18.0F, 4.0F, 0.0F, false);

			Shape26 = new ModelRenderer(this);
			Shape26.setRotationPoint(-5.0F, -15.0F, 2.0F);
			Shape26.setTextureOffset(0, 64).addBox(-3.0F, 35.0F, -1.0F, 3.0F, 3.0F, 6.0F, 0.0F, false);

			Shape52 = new ModelRenderer(this);
			Shape52.setRotationPoint(0.0F, -19.0F, -30.0F);
			Shape52.setTextureOffset(9, 0).addBox(0.0F, 0.0F, 0.0F, 0.0F, 2.0F, 2.0F, 0.0F, false);
		}

		@Override
		public void setRotationAngles(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
			float a = 0.0F;
			if (limbSwingAmount > 0.1F)
				a = MathHelper.cos(ageInTicks * 1.3F * WING_SPEED) * PI * 0.15F * limbSwingAmount;

			Shape24.rotateAngleX = a;
			Shape25.rotateAngleX = -0.17F + a;
			Shape26.rotateAngleX = a;
			Shape13.rotateAngleX = -a;
			Shape15.rotateAngleX = -0.17F - a;
			Shape17.rotateAngleX = -a;

			float b = MathHelper.cos(ageInTicks * 0.7F * WING_SPEED) * PI * 0.25F;
			Shape21.rotateAngleZ = b;
			Shape16.rotateAngleZ = -b;
		}

		@Override
		public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
			Shape27.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			Shape28.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			Shape29.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			Shape30.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			Shape31.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			Shape32.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			Shape33.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			Shape34.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			Shape35.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			Shape36.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			Shape37.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			Shape38.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			Shape39.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			Shape40.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			Shape41.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			Shape42.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			Shape43.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			Shape44.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			Shape45.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			Shape46.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			Shape47.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			Shape48.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			Shape49.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			Shape50.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			Shape51.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			Shape1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			Shape2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			Shape3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			Shape4.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			Shape5.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			Shape6.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			Shape7.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			Shape8.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			Shape9.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			Shape10.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			Shape11.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			Shape12.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			Shape13.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			Shape14.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			Shape15.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			Shape16.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			Shape17.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			Shape18.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			Shape19.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			Shape20.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			Shape21.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			Shape22.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			Shape23.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			Shape24.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			Shape25.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			Shape26.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			Shape52.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		}

		public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
			modelRenderer.rotateAngleX = x;
			modelRenderer.rotateAngleY = y;
			modelRenderer.rotateAngleZ = z;
		}
	}
}
