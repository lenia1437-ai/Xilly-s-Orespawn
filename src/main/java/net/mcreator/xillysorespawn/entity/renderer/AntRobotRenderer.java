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

import net.mcreator.xillysorespawn.entity.AntRobotEntity;

@OnlyIn(Dist.CLIENT)
public class AntRobotRenderer {
	// Если у тебя другой modid - поменяй здесь (текстура: assets/<modid>/textures/antrobot.png, размер 128x256)
	private static final String TEXTURE = "xillys_orespawn:textures/entities/antrobottexture.png";

	public static class ModelRegisterHandler {
		@SubscribeEvent
		@OnlyIn(Dist.CLIENT)
		public void registerModels(ModelRegistryEvent event) {
			RenderingRegistry.registerEntityRenderingHandler(AntRobotEntity.entity, renderManager -> {
				return new MobRenderer(renderManager, new ModelAntRobot(), 1.5f) {
					@Override
					public ResourceLocation getEntityTexture(Entity entity) {
						return new ResourceLocation(TEXTURE);
					}

					// В 1.7.10 начало координат модели (y=0) лежало на уровне "ног" сущности, а не на 1.5 блока выше.
					// Вся IK-логика ног (updateLegs) рассчитана именно на это, поэтому гасим стандартный сдвиг -1.501.
					// Если корпус окажется слишком высоко/низко - меняй это число.
					@Override
					protected void preRenderCallback(LivingEntity entity, MatrixStack matrixStack, float partialTickTime) {
						matrixStack.translate(0.0D, 1.501D, 0.0D);
					}
				};
			});
		}
	}

	// Модель AntRobot из 1.7.10 (ModelAntRobot), портирована на 1.16.5 вместе с анимацией
	public static class ModelAntRobot extends EntityModel<Entity> {
		private final ModelRenderer Leg1;
		private final ModelRenderer Leg2;
		private final ModelRenderer Leg3;
		private final ModelRenderer Foot1;
		private final ModelRenderer Foot2;
		private final ModelRenderer Foot3;
		private final ModelRenderer Foot4;
		private final ModelRenderer Foot5;
		private final ModelRenderer Foot6;
		private final ModelRenderer Foot7;
		private final ModelRenderer Body;
		private final ModelRenderer Abdomen;
		private final ModelRenderer Head;
		private final ModelRenderer Jet1;
		private final ModelRenderer Jet2;
		private final ModelRenderer Hip1;
		private final ModelRenderer Hip2;
		private final ModelRenderer LJaw1;
		private final ModelRenderer RJaw1;
		private final ModelRenderer LJaw2;
		private final ModelRenderer RJaw2;
		private final ModelRenderer LAntenna;
		private final ModelRenderer RAntenna;
		private final ModelRenderer Hip3;
		private final ModelRenderer Hip4;
		private final ModelRenderer Hip5;
		private final ModelRenderer Hip6;

		private AntRobotEntity.RenderSpiderRobotInfo info = null;
		private boolean attacking = false;

		public ModelAntRobot() {
			textureWidth = 128;
			textureHeight = 256;

			Leg1 = new ModelRenderer(this);
			Leg1.setRotationPoint(0.0F, 0.0F, 0.0F);
			setRotationAngle(Leg1, 0.7853982F, 0.0F, 0.0F);
			Leg1.setTextureOffset(19, 40).addBox(-1.5F, -1.5F, 0.0F, 3.0F, 3.0F, 50.0F, 0.0F, false);

			Leg2 = new ModelRenderer(this);
			Leg2.setRotationPoint(0.0F, -35.0F, 35.0F);
			Leg2.setTextureOffset(19, 41).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 50.0F, 0.0F, false);

			Leg3 = new ModelRenderer(this);
			Leg3.setRotationPoint(0.0F, -35.0F, 85.0F);
			setRotationAngle(Leg3, -0.7853982F, 0.0F, 0.0F);
			Leg3.setTextureOffset(20, 42).addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 50.0F, 0.0F, false);

			Foot1 = new ModelRenderer(this);
			Foot1.setRotationPoint(0.0F, -35.0F, 85.0F);
			setRotationAngle(Foot1, -0.7853982F, 0.0F, 0.0F);
			Foot1.setTextureOffset(28, 0).addBox(-2.5F, -0.5F, 50.0F, 5.0F, 1.0F, 2.0F, 0.0F, false);

			Foot2 = new ModelRenderer(this);
			Foot2.setRotationPoint(0.0F, -35.0F, 85.0F);
			setRotationAngle(Foot2, -0.7853982F, 0.0F, 0.0F);
			Foot2.setTextureOffset(30, 4).addBox(1.5F, -0.5F, 52.0F, 1.0F, 1.0F, 3.0F, 0.0F, false);

			Foot3 = new ModelRenderer(this);
			Foot3.setRotationPoint(0.0F, -35.0F, 85.0F);
			setRotationAngle(Foot3, -0.7853982F, 0.0F, 0.0F);
			Foot3.setTextureOffset(44, 0).addBox(-0.5F, -0.5F, 52.0F, 1.0F, 1.0F, 5.0F, 0.0F, false);

			Foot4 = new ModelRenderer(this);
			Foot4.setRotationPoint(0.0F, -35.0F, 85.0F);
			setRotationAngle(Foot4, -0.7853982F, 0.0F, 0.0F);
			Foot4.setTextureOffset(30, 9).addBox(-2.5F, -0.5F, 52.0F, 1.0F, 1.0F, 3.0F, 0.0F, false);

			Foot5 = new ModelRenderer(this);
			Foot5.setRotationPoint(0.0F, -35.0F, 85.0F);
			setRotationAngle(Foot5, -0.7853982F, 0.0F, 0.0F);
			Foot5.setTextureOffset(40, 8).addBox(-0.5F, -2.5F, 50.0F, 1.0F, 5.0F, 2.0F, 0.0F, false);

			Foot6 = new ModelRenderer(this);
			Foot6.setRotationPoint(0.0F, -35.0F, 85.0F);
			setRotationAngle(Foot6, -0.7853982F, 0.0F, 0.0F);
			Foot6.setTextureOffset(48, 9).addBox(-0.5F, -2.5F, 52.0F, 1.0F, 1.0F, 2.0F, 0.0F, false);

			Foot7 = new ModelRenderer(this);
			Foot7.setRotationPoint(0.0F, -35.0F, 85.0F);
			setRotationAngle(Foot7, -0.7853982F, 0.0F, 0.0F);
			Foot7.setTextureOffset(48, 14).addBox(-0.5F, 1.5F, 52.0F, 1.0F, 1.0F, 2.0F, 0.0F, false);

			Body = new ModelRenderer(this);
			Body.setRotationPoint(0.0F, 0.0F, 0.0F);
			Body.setTextureOffset(0, 151).addBox(-11.0F, 0.0F, -16.0F, 22.0F, 14.0F, 32.0F, 0.0F, false);

			Abdomen = new ModelRenderer(this);
			Abdomen.setRotationPoint(0.0F, 0.0F, 0.0F);
			Abdomen.setTextureOffset(0, 199).addBox(-15.0F, -10.0F, 16.0F, 30.0F, 22.0F, 34.0F, 0.0F, false);

			Head = new ModelRenderer(this);
			Head.setRotationPoint(0.0F, 0.0F, 0.0F);
			Head.setTextureOffset(0, 120).addBox(-7.0F, 4.0F, -34.0F, 14.0F, 11.0F, 18.0F, 0.0F, false);

			Jet1 = new ModelRenderer(this);
			Jet1.setRotationPoint(8.0F, -12.0F, 35.0F);
			Jet1.setTextureOffset(78, 0).addBox(0.0F, 0.0F, 0.0F, 6.0F, 6.0F, 18.0F, 0.0F, false);

			Jet2 = new ModelRenderer(this);
			Jet2.setRotationPoint(-14.0F, -12.0F, 35.0F);
			Jet2.setTextureOffset(78, 0).addBox(0.0F, 0.0F, 0.0F, 6.0F, 6.0F, 18.0F, 0.0F, false);

			Hip1 = new ModelRenderer(this);
			Hip1.setRotationPoint(11.0F, 9.0F, -3.0F);
			Hip1.setTextureOffset(0, 96).addBox(0.0F, 0.0F, 0.0F, 6.0F, 6.0F, 6.0F, 0.0F, false);

			Hip2 = new ModelRenderer(this);
			Hip2.setRotationPoint(-17.0F, 9.0F, -3.0F);
			Hip2.setTextureOffset(0, 96).addBox(0.0F, 0.0F, 0.0F, 6.0F, 6.0F, 6.0F, 0.0F, false);

			LJaw1 = new ModelRenderer(this);
			LJaw1.setRotationPoint(5.0F, 13.0F, -33.0F);
			setRotationAngle(LJaw1, 0.0F, 0.8901179F, 0.0F);
			LJaw1.setTextureOffset(0, 33).addBox(-2.0F, 0.0F, -2.0F, 17.0F, 1.0F, 4.0F, 0.0F, false);

			RJaw1 = new ModelRenderer(this);
			RJaw1.setRotationPoint(-5.0F, 13.0F, -33.0F);
			setRotationAngle(RJaw1, 0.0F, 2.216568F, 0.0F);
			RJaw1.setTextureOffset(0, 33).addBox(-2.0F, 0.0F, -2.0F, 17.0F, 1.0F, 4.0F, 0.0F, false);

			LJaw2 = new ModelRenderer(this);
			LJaw2.setRotationPoint(5.0F, 13.0F, -33.0F);
			setRotationAngle(LJaw2, 0.0F, 1.37881F, 0.0F);
			LJaw2.setTextureOffset(0, 27).addBox(12.0F, 0.0F, 5.0F, 17.0F, 1.0F, 3.0F, 0.0F, false);

			RJaw2 = new ModelRenderer(this);
			RJaw2.setRotationPoint(-5.0F, 13.0F, -33.0F);
			setRotationAngle(RJaw2, 0.0F, 1.745329F, 0.0F);
			RJaw2.setTextureOffset(0, 27).addBox(12.0F, 0.0F, -8.0F, 17.0F, 1.0F, 3.0F, 0.0F, false);

			LAntenna = new ModelRenderer(this);
			LAntenna.setRotationPoint(0.0F, 4.0F, -32.0F);
			setRotationAngle(LAntenna, 0.0F, 0.0F, 0.5410521F);
			LAntenna.setTextureOffset(70, 0).addBox(-0.5F, -12.0F, -0.5F, 1.0F, 12.0F, 1.0F, 0.0F, false);

			RAntenna = new ModelRenderer(this);
			RAntenna.setRotationPoint(0.0F, 4.0F, -32.0F);
			setRotationAngle(RAntenna, 0.0F, 0.0F, -0.5410521F);
			RAntenna.setTextureOffset(70, 0).addBox(-0.5F, -12.0F, -0.5F, 1.0F, 12.0F, 1.0F, 0.0F, false);

			Hip3 = new ModelRenderer(this);
			Hip3.setRotationPoint(-17.0F, 9.0F, 10.0F);
			Hip3.setTextureOffset(0, 96).addBox(0.0F, 0.0F, 0.0F, 6.0F, 6.0F, 6.0F, 0.0F, false);

			Hip4 = new ModelRenderer(this);
			Hip4.setRotationPoint(11.0F, 9.0F, 10.0F);
			Hip4.setTextureOffset(0, 96).addBox(0.0F, 0.0F, 0.0F, 6.0F, 6.0F, 6.0F, 0.0F, false);

			Hip5 = new ModelRenderer(this);
			Hip5.setRotationPoint(11.0F, 9.0F, -16.0F);
			Hip5.setTextureOffset(0, 96).addBox(0.0F, 0.0F, 0.0F, 6.0F, 6.0F, 6.0F, 0.0F, false);

			Hip6 = new ModelRenderer(this);
			Hip6.setRotationPoint(-17.0F, 9.0F, -16.0F);
			Hip6.setTextureOffset(0, 96).addBox(0.0F, 0.0F, 0.0F, 6.0F, 6.0F, 6.0F, 0.0F, false);
		}

		@Override
		public void setRotationAngles(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
			this.info = null;
			this.attacking = entity instanceof MobEntity && ((MobEntity) entity).isAggressive();
			if (entity instanceof AntRobotEntity.CustomEntity)
				this.info = ((AntRobotEntity.CustomEntity) entity).getRenderSpiderRobotInfo();
			if (this.info == null)
				return;
			AntRobotEntity.RenderSpiderRobotInfo r = this.info;

			// ---- челюсти и усики ----
			if (!attacking) {
				LJaw1.rotateAngleY = 0.89F;
				LJaw2.rotateAngleY = 1.378F;
				RJaw1.rotateAngleY = 2.216F;
				RJaw2.rotateAngleY = 1.745F;
				LAntenna.rotateAngleX = MathHelper.cos(r.gpcounter * 0.35F) * (float) Math.PI * 0.05F;
				LAntenna.rotateAngleZ = 0.54F + MathHelper.cos(r.gpcounter * 0.25F) * (float) Math.PI * 0.05F;
				RAntenna.rotateAngleX = MathHelper.cos(r.gpcounter * 0.3F) * (float) Math.PI * 0.05F;
				RAntenna.rotateAngleZ = -0.54F + MathHelper.cos(r.gpcounter * 0.45F) * (float) Math.PI * 0.05F;
			} else {
				float newangle = MathHelper.cos(r.gpcounter * 0.25F) * (float) Math.PI * 0.22F;
				LJaw1.rotateAngleY = newangle + 0.89F;
				LJaw2.rotateAngleY = newangle + 1.378F;
				RJaw1.rotateAngleY = -newangle + 2.216F;
				RJaw2.rotateAngleY = 1.745F - newangle;
				LAntenna.rotateAngleX = MathHelper.cos(r.gpcounter * 0.45F) * (float) Math.PI * 0.1F;
				LAntenna.rotateAngleZ = 0.54F + MathHelper.cos(r.gpcounter * 0.35F) * (float) Math.PI * 0.1F;
				RAntenna.rotateAngleX = MathHelper.cos(r.gpcounter * 0.4F) * (float) Math.PI * 0.1F;
				RAntenna.rotateAngleZ = -0.54F + MathHelper.cos(r.gpcounter * 0.55F) * (float) Math.PI * 0.1F;
			}
		}

		// Вектор одного сегмента ноги (длина 49) по его углам: поворот вокруг X, затем вокруг Y
		private static float segX(ModelRenderer s) { return (float) (Math.cos(s.rotateAngleX) * Math.sin(s.rotateAngleY)) * 49.0F; }
		private static float segY(ModelRenderer s) { return -(float) Math.sin(s.rotateAngleX) * 49.0F; }
		private static float segZ(ModelRenderer s) { return (float) (Math.cos(s.rotateAngleX) * Math.cos(s.rotateAngleY)) * 49.0F; }

		private ModelRenderer[] feet() { return new ModelRenderer[]{Foot1, Foot2, Foot3, Foot4, Foot5, Foot6, Foot7}; }

		@Override
		public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
			if (info != null) {
				AntRobotEntity.RenderSpiderRobotInfo r = info;
				ModelRenderer[] feet = feet();
				// 6 ног рисуются одним и тем же набором частей: сегмент 1 -> сегмент 2 -> сегмент 3 + лапка,
				// каждый следующий крепится к концу предыдущего, все лежат в одной вертикальной плоскости.
				for (int i = 0; i < 6; i++) {
					float yaw = r.ydisplayangle[i];
					Leg1.rotateAngleY = yaw;
					Leg2.rotateAngleY = yaw;
					Leg3.rotateAngleY = yaw;
					Leg1.rotateAngleX = (float) r.p1xangle[i] + r.uddisplayangle[i];
					Leg2.rotateAngleX = (float) r.p2xangle[i] + r.uddisplayangle[i];
					Leg3.rotateAngleX = (float) r.p3xangle[i] + r.uddisplayangle[i];

					float hx = -((float) Math.cos(r.ymid[i])) * r.legoff[i] * 16.0F;
					float hy = r.yoff[i] * -16.0F;
					float hz = (float) Math.sin(r.ymid[i]) * r.legoff[i] * 16.0F;
					Leg1.setRotationPoint(hx, hy, hz);
					Leg2.setRotationPoint(hx + segX(Leg1), hy + segY(Leg1), hz + segZ(Leg1));
					Leg3.setRotationPoint(Leg2.rotationPointX + segX(Leg2), Leg2.rotationPointY + segY(Leg2), Leg2.rotationPointZ + segZ(Leg2));
					for (ModelRenderer foot : feet) {
						foot.rotateAngleX = Leg3.rotateAngleX;
						foot.rotateAngleY = yaw;
						foot.setRotationPoint(Leg3.rotationPointX, Leg3.rotationPointY, Leg3.rotationPointZ);
					}
					Leg1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
					Leg2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
					Leg3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
					Foot1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
					Foot2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
					Foot3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
					Foot4.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
					Foot5.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
					Foot6.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
					Foot7.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
				}
			}
			Body.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			Abdomen.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			Head.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			Jet1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			Jet2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			Hip1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			Hip2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			LJaw1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			RJaw1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			LJaw2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			RJaw2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			LAntenna.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			RAntenna.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			Hip3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			Hip4.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			Hip5.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			Hip6.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		}

		public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
			modelRenderer.rotateAngleX = x;
			modelRenderer.rotateAngleY = y;
			modelRenderer.rotateAngleZ = z;
		}
	}
}
