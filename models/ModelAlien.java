// Ported from 1.7.10 ModelAlien -> Minecraft 1.16.5 (Forge, MCP mappings)

public static class ModelAlien extends EntityModel<Entity> {
	private final ModelRenderer torso;
	private final ModelRenderer stomach;
	private final ModelRenderer rThigh;
	private final ModelRenderer lThigh;
	private final ModelRenderer lShin;
	private final ModelRenderer rShin;
	private final ModelRenderer lShin1;
	private final ModelRenderer rShin1;
	private final ModelRenderer lFoot;
	private final ModelRenderer rFoot;
	private final ModelRenderer neck;
	private final ModelRenderer fan;
	private final ModelRenderer tail2;
	private final ModelRenderer tail3;
	private final ModelRenderer tail4;
	private final ModelRenderer tail5;
	private final ModelRenderer tail1;
	private final ModelRenderer fanl1;
	private final ModelRenderer fanr1;
	private final ModelRenderer fanl2;
	private final ModelRenderer fanr2;
	private final ModelRenderer fanl3;
	private final ModelRenderer fanr3;
	private final ModelRenderer fanl4;
	private final ModelRenderer fanr4;
	private final ModelRenderer fanl5;
	private final ModelRenderer fanr5;
	private final ModelRenderer fanl6;
	private final ModelRenderer fanr6;
	private final ModelRenderer fanl7;
	private final ModelRenderer fanr7;
	private final ModelRenderer spike4;
	private final ModelRenderer spike5;
	private final ModelRenderer spike3;
	private final ModelRenderer head;
	private final ModelRenderer head1;
	private final ModelRenderer jaw1;
	private final ModelRenderer head2;
	private final ModelRenderer jaw2;
	private final ModelRenderer fang1;
	private final ModelRenderer fang2;
	private final ModelRenderer fang3;
	private final ModelRenderer fang4;
	private final ModelRenderer spike2;
	private final ModelRenderer spike1;
	private final ModelRenderer arml1;
	private final ModelRenderer armr1;
	private final ModelRenderer arml2;
	private final ModelRenderer armr2;
	private final ModelRenderer clawr1;
	private final ModelRenderer clawr2;
	private final ModelRenderer clawr3;
	private final ModelRenderer clawl2;
	private final ModelRenderer clawl3;
	private final ModelRenderer clawl1;

	public ModelAlien() {
		textureWidth = 256;
		textureHeight = 128;

		torso = new ModelRenderer(this);
		torso.setRotationPoint(0.0F, -2.5F, -8.0F);
		setRotationAngle(torso, -0.1919862F, 0.0F, 0.0F);
		torso.setTextureOffset(0, 46).addBox(-4.5F, -2.0F, 0.0F, 9.0F, 8.0F, 10.0F, 0.0F, false);

		stomach = new ModelRenderer(this);
		stomach.setRotationPoint(0.0F, -2.5F, -8.0F);
		setRotationAngle(stomach, -0.5585054F, 0.0F, 0.0F);
		stomach.setTextureOffset(0, 27).addBox(-3.5F, -5.0F, 8.0F, 7.0F, 6.0F, 12.0F, 0.0F, false);

		rThigh = new ModelRenderer(this);
		rThigh.setRotationPoint(-4.5F, 7.0F, 8.0F);
		setRotationAngle(rThigh, -0.8028515F, 0.2443461F, 0.418879F);
		rThigh.setTextureOffset(59, 45).addBox(-1.5F, -4.0F, -2.5F, 4.0F, 14.0F, 5.0F, 0.0F, false);

		lThigh = new ModelRenderer(this);
		lThigh.setRotationPoint(4.5F, 7.0F, 8.0F);
		setRotationAngle(lThigh, -0.8028515F, -0.2443461F, -0.418879F);
		lThigh.setTextureOffset(40, 45).addBox(-2.5F, -4.0F, -2.5F, 4.0F, 14.0F, 5.0F, 0.0F, false);

		lShin = new ModelRenderer(this);
		lShin.setRotationPoint(4.5F, 7.0F, 8.0F);
		setRotationAngle(lShin, -0.4014257F, -0.2443461F, -0.418879F);
		lShin.setTextureOffset(79, 49).addBox(-2.0F, 8.0F, -5.5F, 3.0F, 3.0F, 12.0F, 0.0F, false);

		rShin = new ModelRenderer(this);
		rShin.setRotationPoint(-4.5F, 7.0F, 8.0F);
		setRotationAngle(rShin, -0.4014257F, 0.2443461F, 0.418879F);
		rShin.setTextureOffset(79, 33).addBox(-1.0F, 8.0F, -5.5F, 3.0F, 3.0F, 12.0F, 0.0F, false);

		lShin1 = new ModelRenderer(this);
		lShin1.setRotationPoint(4.5F, 7.0F, 8.0F);
		setRotationAngle(lShin1, -0.8028515F, -0.2443461F, -0.418879F);
		lShin1.setTextureOffset(113, 40).addBox(-1.5F, 5.5F, 9.0F, 2.0F, 9.0F, 2.0F, 0.0F, false);

		rShin1 = new ModelRenderer(this);
		rShin1.setRotationPoint(-4.5F, 7.0F, 8.0F);
		setRotationAngle(rShin1, -0.8028515F, 0.2443461F, 0.418879F);
		rShin1.setTextureOffset(113, 53).addBox(-0.5F, 5.5F, 9.0F, 2.0F, 9.0F, 2.0F, 0.0F, false);

		lFoot = new ModelRenderer(this);
		lFoot.setRotationPoint(4.5F, 7.0F, 8.0F);
		setRotationAngle(lFoot, 0.0F, -0.2443461F, 0.0F);
		lFoot.setTextureOffset(110, 24).addBox(5.0F, 15.0F, -8.0F, 2.0F, 2.0F, 6.0F, 0.0F, false);

		rFoot = new ModelRenderer(this);
		rFoot.setRotationPoint(-4.5F, 7.0F, 8.0F);
		setRotationAngle(rFoot, 0.0F, 0.2443461F, 0.0F);
		rFoot.setTextureOffset(95, 24).addBox(-7.0F, 15.0F, -8.0F, 2.0F, 2.0F, 6.0F, 0.0F, false);

		neck = new ModelRenderer(this);
		neck.setRotationPoint(0.0F, -2.5F, -8.0F);
		setRotationAngle(neck, -0.1919862F, 0.0F, 0.0F);
		neck.setTextureOffset(23, 86).addBox(-2.0F, -2.0F, -4.0F, 4.0F, 6.0F, 5.0F, 0.0F, false);

		fan = new ModelRenderer(this);
		fan.setRotationPoint(0.0F, -7.0F, -10.0F);
		fan.setTextureOffset(149, 10).addBox(-3.0F, -24.0F, 0.0F, 6.0F, 24.0F, 1.0F, 0.0F, false);

		tail2 = new ModelRenderer(this);
		tail2.setRotationPoint(0.0F, 9.5F, 20.5F);
		setRotationAngle(tail2, -0.3141593F, 0.0F, 0.0F);
		tail2.setTextureOffset(85, 66).addBox(-2.0F, -1.5F, 0.0F, 4.0F, 4.0F, 11.0F, 0.0F, false);

		tail3 = new ModelRenderer(this);
		tail3.setRotationPoint(0.0F, 13.5F, 30.5F);
		setRotationAngle(tail3, -0.2094395F, 0.0F, 0.0F);
		tail3.setTextureOffset(118, 66).addBox(-1.5F, -1.5F, 0.0F, 3.0F, 3.0F, 11.0F, 0.0F, false);

		tail4 = new ModelRenderer(this);
		tail4.setRotationPoint(0.0F, 15.5F, 40.5F);
		setRotationAngle(tail4, -0.1396263F, 0.0F, 0.0F);
		tail4.setTextureOffset(149, 66).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 11.0F, 0.0F, false);

		tail5 = new ModelRenderer(this);
		tail5.setRotationPoint(0.0F, 17.5F, 50.5F);
		setRotationAngle(tail5, -0.0523599F, 0.0F, 0.0F);
		tail5.setTextureOffset(178, 66).addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 11.0F, 0.0F, false);

		tail1 = new ModelRenderer(this);
		tail1.setRotationPoint(0.0F, 6.5F, 10.5F);
		setRotationAngle(tail1, -0.4014257F, 0.0F, 0.0F);
		tail1.setTextureOffset(50, 66).addBox(-2.0F, -2.5F, 0.0F, 4.0F, 4.0F, 11.0F, 0.0F, false);

		fanl1 = new ModelRenderer(this);
		fanl1.setRotationPoint(0.0F, -7.0F, -10.0F);
		setRotationAngle(fanl1, 0.0F, 0.0F, 0.2617994F);
		fanl1.setTextureOffset(130, 10).addBox(-3.0F, -24.0F, 0.0F, 6.0F, 24.0F, 1.0F, 0.0F, false);

		fanr1 = new ModelRenderer(this);
		fanr1.setRotationPoint(0.0F, -7.0F, -10.0F);
		setRotationAngle(fanr1, 0.0F, 0.0F, -0.2617994F);
		fanr1.setTextureOffset(130, 10).addBox(-3.0F, -24.0F, 0.0F, 6.0F, 24.0F, 1.0F, 0.0F, false);

		fanl2 = new ModelRenderer(this);
		fanl2.setRotationPoint(0.0F, -7.0F, -10.0F);
		setRotationAngle(fanl2, 0.0F, 0.0F, 0.5235988F);
		fanl2.setTextureOffset(130, 10).addBox(-3.0F, -24.0F, 0.0F, 6.0F, 24.0F, 1.0F, 0.0F, false);

		fanr2 = new ModelRenderer(this);
		fanr2.setRotationPoint(0.0F, -7.0F, -10.0F);
		setRotationAngle(fanr2, 0.0F, 0.0F, -0.5235988F);
		fanr2.setTextureOffset(130, 10).addBox(-3.0F, -24.0F, 0.0F, 6.0F, 24.0F, 1.0F, 0.0F, false);

		fanl3 = new ModelRenderer(this);
		fanl3.setRotationPoint(0.0F, -7.0F, -10.0F);
		setRotationAngle(fanl3, 0.0F, 0.0F, 0.7853982F);
		fanl3.setTextureOffset(130, 10).addBox(-3.0F, -24.0F, 0.0F, 6.0F, 24.0F, 1.0F, 0.0F, false);

		fanr3 = new ModelRenderer(this);
		fanr3.setRotationPoint(0.0F, -7.0F, -10.0F);
		setRotationAngle(fanr3, 0.0F, 0.0F, -0.7853982F);
		fanr3.setTextureOffset(130, 10).addBox(-3.0F, -24.0F, 0.0F, 6.0F, 24.0F, 1.0F, 0.0F, false);

		fanl4 = new ModelRenderer(this);
		fanl4.setRotationPoint(0.0F, -7.0F, -10.0F);
		setRotationAngle(fanl4, 0.0F, 0.0F, 1.047198F);
		fanl4.setTextureOffset(130, 10).addBox(-3.0F, -24.0F, 0.0F, 6.0F, 24.0F, 1.0F, 0.0F, false);

		fanr4 = new ModelRenderer(this);
		fanr4.setRotationPoint(0.0F, -7.0F, -10.0F);
		setRotationAngle(fanr4, 0.0F, 0.0F, -1.047198F);
		fanr4.setTextureOffset(130, 10).addBox(-3.0F, -24.0F, 0.0F, 6.0F, 24.0F, 1.0F, 0.0F, false);

		fanl5 = new ModelRenderer(this);
		fanl5.setRotationPoint(0.0F, -7.0F, -10.0F);
		setRotationAngle(fanl5, 0.0F, 0.0F, 1.308997F);
		fanl5.setTextureOffset(130, 10).addBox(-3.0F, -24.0F, 0.0F, 6.0F, 24.0F, 1.0F, 0.0F, false);

		fanr5 = new ModelRenderer(this);
		fanr5.setRotationPoint(0.0F, -7.0F, -10.0F);
		setRotationAngle(fanr5, 0.0F, 0.0F, -1.308997F);
		fanr5.setTextureOffset(130, 10).addBox(-3.0F, -24.0F, 0.0F, 6.0F, 24.0F, 1.0F, 0.0F, false);

		fanl6 = new ModelRenderer(this);
		fanl6.setRotationPoint(0.0F, -7.0F, -10.0F);
		setRotationAngle(fanl6, 0.0F, 0.0F, 1.570796F);
		fanl6.setTextureOffset(130, 10).addBox(-3.0F, -24.0F, 0.0F, 6.0F, 24.0F, 1.0F, 0.0F, false);

		fanr6 = new ModelRenderer(this);
		fanr6.setRotationPoint(0.0F, -7.0F, -10.0F);
		setRotationAngle(fanr6, 0.0F, 0.0F, -1.570796F);
		fanr6.setTextureOffset(130, 10).addBox(-3.0F, -24.0F, 0.0F, 6.0F, 24.0F, 1.0F, 0.0F, false);

		fanl7 = new ModelRenderer(this);
		fanl7.setRotationPoint(0.0F, -7.0F, -10.0F);
		setRotationAngle(fanl7, 0.0F, 0.0F, 1.832596F);
		fanl7.setTextureOffset(130, 10).addBox(-3.0F, -24.0F, 0.0F, 6.0F, 24.0F, 1.0F, 0.0F, false);

		fanr7 = new ModelRenderer(this);
		fanr7.setRotationPoint(0.0F, -7.0F, -10.0F);
		setRotationAngle(fanr7, 0.0F, 0.0F, -1.832596F);
		fanr7.setTextureOffset(130, 10).addBox(-3.0F, -24.0F, 0.0F, 6.0F, 24.0F, 1.0F, 0.0F, false);

		spike4 = new ModelRenderer(this);
		spike4.setRotationPoint(0.0F, 16.0F, 41.0F);
		setRotationAngle(spike4, -0.0523599F, 0.5235988F, 0.0F);
		spike4.setTextureOffset(178, 66).addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 11.0F, 0.0F, false);

		spike5 = new ModelRenderer(this);
		spike5.setRotationPoint(0.0F, 16.0F, 41.0F);
		setRotationAngle(spike5, -0.0523599F, -0.5759587F, 0.0F);
		spike5.setTextureOffset(178, 66).addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 11.0F, 0.0F, false);

		spike3 = new ModelRenderer(this);
		spike3.setRotationPoint(0.0F, 13.5F, 30.5F);
		setRotationAngle(spike3, 0.3141593F, 0.0F, 0.0F);
		spike3.setTextureOffset(178, 66).addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 11.0F, 0.0F, false);

		head = new ModelRenderer(this);
		head.setRotationPoint(0.0F, -3.0F, -11.0F);
		head.setTextureOffset(200, 0).addBox(-3.0F, -4.0F, -7.0F, 6.0F, 7.0F, 8.0F, 0.0F, false);

		head1 = new ModelRenderer(this);
		head1.setRotationPoint(0.0F, -3.0F, -11.0F);
		head1.setTextureOffset(200, 18).addBox(-2.5F, -2.0F, -15.0F, 5.0F, 2.0F, 8.0F, 0.0F, false);

		jaw1 = new ModelRenderer(this);
		jaw1.setRotationPoint(0.0F, -2.0F, -19.0F);
		jaw1.setTextureOffset(200, 43).addBox(-2.0F, -1.0F, -7.0F, 4.0F, 2.0F, 8.0F, 0.0F, false);

		head2 = new ModelRenderer(this);
		head2.setRotationPoint(0.0F, -3.0F, -11.0F);
		head2.setTextureOffset(200, 31).addBox(-2.0F, -2.0F, -22.0F, 4.0F, 2.0F, 7.0F, 0.0F, false);

		jaw2 = new ModelRenderer(this);
		jaw2.setRotationPoint(0.0F, -2.0F, -19.0F);
		jaw2.setTextureOffset(200, 56).addBox(-1.5F, -1.0F, -13.0F, 3.0F, 2.0F, 6.0F, 0.0F, false);

		fang1 = new ModelRenderer(this);
		fang1.setRotationPoint(0.0F, -3.0F, -11.0F);
		fang1.setTextureOffset(42, 0).addBox(1.0F, 0.0F, -20.0F, 1.0F, 5.0F, 1.0F, 0.0F, false);

		fang2 = new ModelRenderer(this);
		fang2.setRotationPoint(0.0F, -3.0F, -11.0F);
		fang2.setTextureOffset(50, 0).addBox(-2.0F, 0.0F, -20.0F, 1.0F, 5.0F, 1.0F, 0.0F, false);

		fang3 = new ModelRenderer(this);
		fang3.setRotationPoint(0.0F, -3.0F, -11.0F);
		fang3.setTextureOffset(60, 0).addBox(1.0F, 0.0F, -14.0F, 1.0F, 3.0F, 1.0F, 0.0F, false);

		fang4 = new ModelRenderer(this);
		fang4.setRotationPoint(0.0F, -3.0F, -11.0F);
		fang4.setTextureOffset(69, 0).addBox(-2.0F, 0.0F, -14.0F, 1.0F, 3.0F, 1.0F, 0.0F, false);

		spike2 = new ModelRenderer(this);
		spike2.setRotationPoint(0.0F, 9.5F, 20.5F);
		setRotationAngle(spike2, 0.3141593F, 0.0F, 0.0F);
		spike2.setTextureOffset(178, 66).addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 11.0F, 0.0F, false);

		spike1 = new ModelRenderer(this);
		spike1.setRotationPoint(0.0F, 6.5F, 10.5F);
		setRotationAngle(spike1, 0.3141593F, 0.0F, 0.0F);
		spike1.setTextureOffset(178, 66).addBox(-0.5F, -1.5F, 0.0F, 1.0F, 1.0F, 11.0F, 0.0F, false);

		arml1 = new ModelRenderer(this);
		arml1.setRotationPoint(2.0F, -1.0F, -6.0F);
		setRotationAngle(arml1, 0.0F, -0.5235988F, 0.1745329F);
		arml1.setTextureOffset(50, 98).addBox(0.0F, 0.0F, -2.0F, 11.0F, 3.0F, 4.0F, 0.0F, false);

		armr1 = new ModelRenderer(this);
		armr1.setRotationPoint(-3.0F, -1.0F, -6.0F);
		setRotationAngle(armr1, 0.0F, -2.617994F, -0.1745329F);
		armr1.setTextureOffset(49, 88).addBox(0.0F, 0.0F, -2.0F, 11.0F, 3.0F, 4.0F, 0.0F, false);

		arml2 = new ModelRenderer(this);
		arml2.setRotationPoint(11.0F, 2.0F, -1.0F);
		setRotationAngle(arml2, 0.0F, 0.8552113F, 0.0F);
		arml2.setTextureOffset(41, 107).addBox(0.0F, -1.0F, -1.0F, 15.0F, 3.0F, 3.0F, 0.0F, false);

		armr2 = new ModelRenderer(this);
		armr2.setRotationPoint(-11.0F, 2.0F, -1.0F);
		setRotationAngle(armr2, 0.0F, 2.268928F, 0.0F);
		armr2.setTextureOffset(42, 115).addBox(0.0F, -1.0F, -2.0F, 15.0F, 3.0F, 3.0F, 0.0F, false);

		clawr1 = new ModelRenderer(this);
		clawr1.setRotationPoint(-21.0F, 2.0F, -12.0F);
		setRotationAngle(clawr1, -0.1745329F, 0.4363323F, 0.0F);
		clawr1.setTextureOffset(100, 85).addBox(-0.5F, -1.0F, -6.0F, 1.0F, 1.0F, 6.0F, 0.0F, false);

		clawr2 = new ModelRenderer(this);
		clawr2.setRotationPoint(-21.0F, 2.0F, -12.0F);
		setRotationAngle(clawr2, 0.0F, 0.8726646F, 0.0F);
		clawr2.setTextureOffset(100, 94).addBox(0.0F, 0.0F, -10.0F, 1.0F, 1.0F, 10.0F, 0.0F, false);

		clawr3 = new ModelRenderer(this);
		clawr3.setRotationPoint(-21.0F, 2.0F, -12.0F);
		setRotationAngle(clawr3, 0.1745329F, 0.4363323F, 0.0F);
		clawr3.setTextureOffset(100, 107).addBox(0.0F, 1.0F, -6.0F, 1.0F, 1.0F, 6.0F, 0.0F, false);

		clawl2 = new ModelRenderer(this);
		clawl2.setRotationPoint(21.0F, 2.0F, -12.0F);
		setRotationAngle(clawl2, 0.0F, 2.268928F, 0.0F);
		clawl2.setTextureOffset(130, 94).addBox(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 10.0F, 0.0F, false);

		clawl3 = new ModelRenderer(this);
		clawl3.setRotationPoint(21.0F, 2.0F, -12.0F);
		setRotationAngle(clawl3, -0.1745329F, 2.70526F, 0.0F);
		clawl3.setTextureOffset(130, 109).addBox(0.0F, 1.0F, 0.0F, 1.0F, 1.0F, 6.0F, 0.0F, false);

		clawl1 = new ModelRenderer(this);
		clawl1.setRotationPoint(21.0F, 2.0F, -12.0F);
		setRotationAngle(clawl1, 0.1745329F, 2.70526F, 0.0F);
		clawl1.setTextureOffset(130, 83).addBox(0.0F, -1.0F, 0.0F, 1.0F, 1.0F, 6.0F, 0.0F, false);

	}

	@Override
	public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red,
			float green, float blue, float alpha) {
		torso.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		stomach.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		rThigh.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		lThigh.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		lShin.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		rShin.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		lShin1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		rShin1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		lFoot.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		rFoot.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		neck.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		fan.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		tail2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		tail3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		tail4.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		tail5.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		tail1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		fanl1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		fanr1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		fanl2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		fanr2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		fanl3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		fanr3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		fanl4.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		fanr4.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		fanl5.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		fanr5.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		fanl6.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		fanr6.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		fanl7.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		fanr7.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		spike4.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		spike5.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		spike3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		head.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		head1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		jaw1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		head2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		jaw2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		fang1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		fang2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		fang3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		fang4.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		spike2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		spike1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		arml1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		armr1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		arml2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		armr2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		clawr1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		clawr2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		clawr3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		clawl2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		clawl3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		clawl1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}

	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity e) {
	}
}
