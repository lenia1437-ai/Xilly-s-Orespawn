package net.mcreator.xillysorespawn.entity.renderer;

import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.MobRenderer;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.mcreator.xillysorespawn.entity.CaterKillerEntity;

@OnlyIn(Dist.CLIENT)
public class CaterKillerRenderer {
    private static final String TEXTURE = "xillys_orespawn:textures/entities/caterkillertexture.png";
    public static class ModelRegisterHandler {
        @SubscribeEvent @OnlyIn(Dist.CLIENT)
        public void registerModels(ModelRegistryEvent event) {
            RenderingRegistry.registerEntityRenderingHandler(CaterKillerEntity.entity, manager ->
                new MobRenderer(manager, new ModelCaterKiller(1.0F), 0.5F) {
                    @Override public ResourceLocation getEntityTexture(Entity entity) { return new ResourceLocation(TEXTURE); }
                    @Override protected void preRenderCallback(LivingEntity entity, MatrixStack stack, float partialTick) {
                        if (((CaterKillerEntity.CustomEntity) entity).getPlayNicely() != 0)
                            stack.scale(0.5F, 0.5F, 0.5F);
                    }
                });
        }
    }

public static class ModelCaterKiller extends EntityModel<Entity>
 {
   private float wingspeed = 1.0F;
   private float animationAge;
   private float animationLimbAmount;
   private int animationAttacking;

   private final ModelRenderer Head;

   private final ModelRenderer falsehead;

   private final ModelRenderer seg1;
   private final ModelRenderer ltusk1;
   private final ModelRenderer ltusk2;
   private final ModelRenderer rtusk1;
   private final ModelRenderer rtusk2;
   private final ModelRenderer ljaw;
   private final ModelRenderer rjaw;
   private final ModelRenderer seg1lspike;
   private final ModelRenderer seg1rspike;
   private final ModelRenderer seg1ltopspike;
   private final ModelRenderer seg1rtopspike;
   private final ModelRenderer seg1lleg;
   private final ModelRenderer seg1rleg;
   private final ModelRenderer seg2;
   private final ModelRenderer seg2lfoot;
   private final ModelRenderer seg2rfoot;
   private final ModelRenderer seg2ltopspike;
   private final ModelRenderer seg2rtopspike;
   private final ModelRenderer seg2lspike;
   private final ModelRenderer seg2rspike;
   private final ModelRenderer seg3;
   private final ModelRenderer seg3lfoot;
   private final ModelRenderer seg3rfoot;
   private final ModelRenderer seg3lspike;
   private final ModelRenderer seg3rspike;
   private final ModelRenderer seg3ltopspike;
   private final ModelRenderer seg3rtopspike;
   private final ModelRenderer seg3lbackspike;
   private final ModelRenderer seg3rbackspike;

   public ModelCaterKiller(float f1) {
     this.wingspeed = f1;

     this.textureWidth = 256;
     this.textureHeight = 512;

     this.Head = new ModelRenderer(this, 0, 50);
     this.Head.addBox(-8.0F, -8.0F, -8.0F, 16, 16, 8);
     this.Head.setRotationPoint(0.0F, -8.0F, -12.0F);

     this.Head.mirror = true;
     setRotation(this.Head, 0.0F, 0.0F, 0.0F);
     this.falsehead = new ModelRenderer(this, 0, 100);
     this.falsehead.addBox(-10.0F, -27.0F, -11.0F, 20, 20, 10);
     this.falsehead.setRotationPoint(0.0F, -8.0F, -12.0F);

     this.falsehead.mirror = true;
     setRotation(this.falsehead, -0.1570796F, 0.0F, 0.0F);
     this.seg1 = new ModelRenderer(this, 0, 200);
     this.seg1.addBox(-14.0F, -31.0F, 0.0F, 28, 32, 14);
     this.seg1.setRotationPoint(0.0F, -8.0F, -12.0F);

     this.seg1.mirror = true;
     setRotation(this.seg1, 0.0F, 0.0F, 0.0F);
     this.ltusk1 = new ModelRenderer(this, 0, 140);
     this.ltusk1.addBox(-1.0F, -1.0F, -1.0F, 33, 3, 3);
     this.ltusk1.setRotationPoint(9.0F, -25.0F, -19.0F);

     this.ltusk1.mirror = true;
     setRotation(this.ltusk1, 0.0F, 0.5585054F, 0.0F);
     this.ltusk2 = new ModelRenderer(this, 0, 160);
     this.ltusk2.addBox(0.0F, 0.0F, 0.0F, 20, 1, 1);
     this.ltusk2.setRotationPoint(36.0F, -25.0F, -36.0F);

     this.ltusk2.mirror = true;
     setRotation(this.ltusk2, 0.0F, 0.8028515F, 0.0F);
     this.rtusk1 = new ModelRenderer(this, 0, 150);
     this.rtusk1.addBox(-33.0F, 0.0F, 0.0F, 33, 3, 3);
     this.rtusk1.setRotationPoint(-8.0F, -25.0F, -17.0F);

     this.rtusk1.mirror = true;
     setRotation(this.rtusk1, 0.0F, -0.5585054F, 0.0F);
     this.rtusk2 = new ModelRenderer(this, 0, 170);
     this.rtusk2.addBox(-20.0F, 0.0F, 0.0F, 20, 1, 1);
     this.rtusk2.setRotationPoint(-36.0F, -24.0F, -34.0F);

     this.rtusk2.mirror = true;
     setRotation(this.rtusk2, 0.0F, -0.8028515F, 0.0F);
     this.ljaw = new ModelRenderer(this, 100, 50);
     this.ljaw.addBox(0.0F, 0.0F, 0.0F, 1, 7, 4);
     this.ljaw.setRotationPoint(4.0F, -1.0F, -18.0F);

     this.ljaw.mirror = true;
     setRotation(this.ljaw, 0.0F, 0.0F, 0.1396263F);
     this.rjaw = new ModelRenderer(this, 125, 50);
     this.rjaw.addBox(0.0F, 0.0F, 0.0F, 1, 7, 4);
     this.rjaw.setRotationPoint(-5.0F, -1.0F, -18.0F);

     this.rjaw.mirror = true;
     setRotation(this.rjaw, 0.0F, 0.0F, -0.1396263F);
     this.seg1lspike = new ModelRenderer(this, 0, 260);
     this.seg1lspike.addBox(-1.0F, -1.0F, -1.0F, 33, 2, 2);
     this.seg1lspike.setRotationPoint(14.0F, -32.0F, -6.0F);

     this.seg1lspike.mirror = true;
     setRotation(this.seg1lspike, 0.0F, 0.3316126F, -0.122173F);
     this.seg1rspike = new ModelRenderer(this, 0, 270);
     this.seg1rspike.addBox(-33.0F, -1.0F, -1.0F, 33, 2, 2);
     this.seg1rspike.setRotationPoint(-13.0F, -32.0F, -6.0F);

     this.seg1rspike.mirror = true;
     setRotation(this.seg1rspike, 0.0F, -0.3316126F, 0.122173F);
     this.seg1ltopspike = new ModelRenderer(this, 125, 260);
     this.seg1ltopspike.addBox(-2.0F, -8.0F, -2.0F, 4, 9, 4);
     this.seg1ltopspike.setRotationPoint(8.0F, -39.0F, -6.0F);

     this.seg1ltopspike.mirror = true;
     setRotation(this.seg1ltopspike, 0.0F, 0.0F, 0.1396263F);
     this.seg1rtopspike = new ModelRenderer(this, 150, 260);
     this.seg1rtopspike.addBox(-2.0F, -8.0F, -2.0F, 4, 9, 4);
     this.seg1rtopspike.setRotationPoint(-10.0F, -39.0F, -6.0F);

     this.seg1rtopspike.mirror = true;
     setRotation(this.seg1rtopspike, 0.0F, 0.0F, -0.1396263F);
     this.seg1lleg = new ModelRenderer(this, 125, 200);
     this.seg1lleg.addBox(-1.0F, 0.0F, -1.0F, 2, 16, 2);
     this.seg1lleg.setRotationPoint(8.0F, -8.0F, -5.0F);

     this.seg1lleg.mirror = true;
     setRotation(this.seg1lleg, 0.0F, 0.0F, 0.1570796F);
     this.seg1rleg = new ModelRenderer(this, 150, 200);
     this.seg1rleg.addBox(0.0F, 0.0F, 0.0F, 2, 16, 2);
     this.seg1rleg.setRotationPoint(-9.0F, -8.0F, -5.0F);

     this.seg1rleg.mirror = true;
     setRotation(this.seg1rleg, 0.0F, 0.0F, -0.1570796F);
     this.seg2 = new ModelRenderer(this, 0, 300);
     this.seg2.addBox(-20.0F, -17.0F, -9.0F, 40, 34, 18);
     this.seg2.setRotationPoint(0.0F, -2.0F, 32.0F);

     this.seg2.mirror = true;
     setRotation(this.seg2, 0.0F, 0.0F, 0.0F);
     this.seg2lfoot = new ModelRenderer(this, 125, 300);
     this.seg2lfoot.addBox(-5.0F, 0.0F, -5.0F, 10, 10, 10);
     this.seg2lfoot.setRotationPoint(13.0F, 14.0F, 32.0F);

     this.seg2lfoot.mirror = true;
     setRotation(this.seg2lfoot, 0.0F, 0.0F, 0.0F);
     this.seg2rfoot = new ModelRenderer(this, 175, 300);
     this.seg2rfoot.addBox(-5.0F, 0.0F, -5.0F, 10, 10, 10);
     this.seg2rfoot.setRotationPoint(-13.0F, 14.0F, 32.0F);

     this.seg2rfoot.mirror = true;
     setRotation(this.seg2rfoot, 0.0F, 0.0F, 0.0F);
     this.seg2ltopspike = new ModelRenderer(this, 100, 360);
     this.seg2ltopspike.addBox(-2.0F, -9.0F, -2.0F, 4, 9, 4);
     this.seg2ltopspike.setRotationPoint(14.0F, -18.0F, 32.0F);

     this.seg2ltopspike.mirror = true;
     setRotation(this.seg2ltopspike, 0.0F, 0.0F, 0.1396263F);
     this.seg2rtopspike = new ModelRenderer(this, 125, 360);
     this.seg2rtopspike.addBox(-2.0F, -9.0F, -2.0F, 4, 9, 4);
     this.seg2rtopspike.setRotationPoint(-14.0F, -18.0F, 32.0F);

     this.seg2rtopspike.mirror = true;
     setRotation(this.seg2rtopspike, 0.0F, 0.0F, -0.1396263F);
     this.seg2lspike = new ModelRenderer(this, 0, 360);
     this.seg2lspike.addBox(0.0F, -1.0F, -1.0F, 20, 2, 2);
     this.seg2lspike.setRotationPoint(18.0F, -9.0F, 32.0F);

     this.seg2lspike.mirror = true;
     setRotation(this.seg2lspike, 0.0F, 0.0F, -0.0698132F);
     this.seg2rspike = new ModelRenderer(this, 0, 370);
     this.seg2rspike.addBox(-20.0F, -1.0F, -1.0F, 20, 2, 2);
     this.seg2rspike.setRotationPoint(-18.0F, -9.0F, 32.0F);

     this.seg2rspike.mirror = true;
     setRotation(this.seg2rspike, 0.0F, 0.0F, 0.0698132F);
     this.seg3 = new ModelRenderer(this, 0, 400);
     this.seg3.addBox(-15.0F, -14.0F, -7.0F, 30, 28, 14);
     this.seg3.setRotationPoint(0.0F, 3.0F, 48.0F);

     this.seg3.mirror = true;
     setRotation(this.seg3, 0.0F, 0.0F, 0.0F);
     this.seg3lfoot = new ModelRenderer(this, 100, 400);
     this.seg3lfoot.addBox(-4.0F, 0.0F, -6.0F, 8, 8, 12);
     this.seg3lfoot.setRotationPoint(10.0F, 16.0F, 48.0F);

     this.seg3lfoot.mirror = true;
     setRotation(this.seg3lfoot, 0.0F, 0.0F, 0.0F);
     this.seg3rfoot = new ModelRenderer(this, 150, 400);
     this.seg3rfoot.addBox(-4.0F, 0.0F, -6.0F, 8, 8, 12);
     this.seg3rfoot.setRotationPoint(-10.0F, 16.0F, 48.0F);

     this.seg3rfoot.mirror = true;
     setRotation(this.seg3rfoot, 0.0F, 0.0F, 0.0F);
     this.seg3lspike = new ModelRenderer(this, 0, 450);
     this.seg3lspike.addBox(0.0F, -1.0F, -1.0F, 14, 2, 2);
     this.seg3lspike.setRotationPoint(14.0F, -4.0F, 48.0F);

     this.seg3lspike.mirror = true;
     setRotation(this.seg3lspike, 0.0F, 0.0F, -0.0698132F);
     this.seg3rspike = new ModelRenderer(this, 0, 460);
     this.seg3rspike.addBox(-14.0F, -1.0F, -1.0F, 14, 2, 2);
     this.seg3rspike.setRotationPoint(-14.0F, -4.0F, 48.0F);

     this.seg3rspike.mirror = true;
     setRotation(this.seg3rspike, 0.0F, 0.0F, 0.0698132F);
     this.seg3ltopspike = new ModelRenderer(this, 100, 450);
     this.seg3ltopspike.addBox(-2.0F, -13.0F, -2.0F, 3, 13, 3);
     this.seg3ltopspike.setRotationPoint(10.0F, -10.0F, 48.0F);

     this.seg3ltopspike.mirror = true;
     setRotation(this.seg3ltopspike, 0.0F, 0.0F, 0.1396263F);
     this.seg3rtopspike = new ModelRenderer(this, 120, 450);
     this.seg3rtopspike.addBox(-2.0F, -13.0F, -2.0F, 3, 13, 3);
     this.seg3rtopspike.setRotationPoint(-10.0F, -10.0F, 48.0F);

     this.seg3rtopspike.mirror = true;
     setRotation(this.seg3rtopspike, 0.0F, 0.0F, -0.1396263F);
     this.seg3lbackspike = new ModelRenderer(this, 50, 450);
     this.seg3lbackspike.addBox(-2.0F, -20.0F, -2.0F, 4, 20, 4);
     this.seg3lbackspike.setRotationPoint(13.0F, -8.0F, 54.0F);

     this.seg3lbackspike.mirror = true;
     setRotation(this.seg3lbackspike, -0.9773844F, 0.2792527F, 0.1396263F);
     this.seg3rbackspike = new ModelRenderer(this, 75, 450);
     this.seg3rbackspike.addBox(-2.0F, -20.0F, -2.0F, 4, 20, 4);
     this.seg3rbackspike.setRotationPoint(-13.0F, -8.0F, 54.0F);

     this.seg3rbackspike.mirror = true;
     setRotation(this.seg3rbackspike, -0.9773844F, -0.3490659F, 0.1396263F);
   }

   public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay,
        float red, float green, float blue, float alpha) {
        float ageInTicks = this.animationAge;
        float limbSwingAmount = this.animationLimbAmount;
        boolean attacking = this.animationAttacking != 0;
        float newangle;
        float headoff;

        if (attacking) {
            newangle = MathHelper.cos(ageInTicks * 1.7F * this.wingspeed) * 3.1415927F * 0.07F;
            headoff = MathHelper.cos(ageInTicks * 1.7F * this.wingspeed) * 8.0F;
        } else {
            newangle = MathHelper.cos(ageInTicks * 1.3F * this.wingspeed) * 3.1415927F * 0.025F;
            headoff = MathHelper.cos(ageInTicks * 0.3F * this.wingspeed) * 2.0F;
        }
        this.ljaw.rotateAngleZ = 0.139F + newangle;
        this.rjaw.rotateAngleZ = -0.139F - newangle;
        this.Head.rotationPointY = -8.0F + headoff;
        this.falsehead.rotationPointY = -8.0F + headoff;
        this.ltusk1.rotationPointY = -25.0F + headoff;
        this.ltusk2.rotationPointY = -25.0F + headoff;
        this.rtusk1.rotationPointY = -25.0F + headoff;
        this.rtusk2.rotationPointY = -25.0F + headoff;
        this.ljaw.rotationPointY = -1.0F + headoff;
        this.rjaw.rotationPointY = -1.0F + headoff;
        this.ltusk2.rotateAngleY = 0.802F + MathHelper.cos(ageInTicks * 2.11F * this.wingspeed)
            * 3.1415927F * 0.08F;
        this.rtusk2.rotateAngleY = -0.802F + MathHelper.cos(ageInTicks * 2.3F * this.wingspeed)
            * 3.1415927F * 0.08F;

        Head.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        falsehead.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        ltusk1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        ltusk2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rtusk1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rtusk2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        ljaw.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rjaw.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);

        // OreSpawn reuses this set of seven cubes three times. Rendering each
        // instance inside the loop is essential; rendering only the final state
        // is what made most of the caterpillar body invisible in the first port.
        for (int i = 0; i < 3; ++i) {
            this.seg1.rotationPointY = -8.0F + headoff / (i + 1) + 8.0F * i;
            this.seg1lspike.rotationPointY = -32.0F + headoff / (i + 1) + 8.0F * i;
            this.seg1rspike.rotationPointY = -32.0F + headoff / (i + 1) + 8.0F * i;
            this.seg1ltopspike.rotationPointY = -39.0F + headoff / (i + 1) + 8.0F * i;
            this.seg1rtopspike.rotationPointY = -39.0F + headoff / (i + 1) + 8.0F * i;
            this.seg1lleg.rotationPointY = -8.0F + headoff / (i + 1) + 8.0F * i;
            this.seg1rleg.rotationPointY = -8.0F + headoff / (i + 1) + 8.0F * i;
            this.seg1.rotationPointZ = -12.0F + 14.0F * i;
            this.seg1lspike.rotationPointZ = -6.0F + 14.0F * i;
            this.seg1rspike.rotationPointZ = -6.0F + 14.0F * i;
            this.seg1ltopspike.rotationPointZ = -6.0F + 14.0F * i;
            this.seg1rtopspike.rotationPointZ = -6.0F + 14.0F * i;
            this.seg1lleg.rotationPointZ = -5.0F + 14.0F * i;
            this.seg1rleg.rotationPointZ = -5.0F + 14.0F * i;
            newangle = MathHelper.cos((float)(ageInTicks * 0.91F * this.wingspeed
                + 0.39269908169872414D * i)) * 3.1415927F * 0.08F;
            this.seg1lspike.rotateAngleZ = newangle;
            this.seg1rspike.rotateAngleZ = -newangle;
            newangle = MathHelper.cos((float)(ageInTicks * (attacking ? 2.91F : 0.35F)
                * this.wingspeed + 0.39269908169872414D * i)) * 3.1415927F
                * (attacking ? 0.15F : 0.04F);
            this.seg1lleg.rotateAngleX = newangle;
            this.seg1rleg.rotateAngleX = -newangle;
            seg1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
            seg1lspike.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
            seg1rspike.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
            seg1ltopspike.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
            seg1rtopspike.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
            seg1lleg.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
            seg1rleg.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        }

        float phase = 0.0F;
        for (int i = 0; i < 6; ++i) {
            float zdist = MathHelper.cos(ageInTicks * 1.7F * this.wingspeed + phase)
                * 1.5F * limbSwingAmount;
            float z = 39.0F + (16.0F + zdist) * i;
            this.seg2.rotationPointZ = z;
            this.seg2lfoot.rotationPointZ = z;
            this.seg2rfoot.rotationPointZ = z;
            this.seg2ltopspike.rotationPointZ = z;
            this.seg2rtopspike.rotationPointZ = z;
            this.seg2lspike.rotationPointZ = z;
            this.seg2rspike.rotationPointZ = z;
            newangle = MathHelper.cos((float)(ageInTicks * 0.4F * this.wingspeed
                - 0.39269908169872414D * i)) * 3.1415927F * 0.07F;
            this.seg2lspike.rotateAngleZ = newangle;
            this.seg2rspike.rotateAngleZ = -newangle;
            seg2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
            seg2lfoot.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
            seg2rfoot.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
            seg2ltopspike.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
            seg2rtopspike.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
            seg2lspike.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
            seg2rspike.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
            phase += 0.7853982F;
        }

        // Reset the last segment before applying the legacy one-frame offsets.
        this.seg3.setRotationPoint(0.0F, 3.0F, 48.0F);
        this.seg3lfoot.setRotationPoint(10.0F, 16.0F, 48.0F);
        this.seg3rfoot.setRotationPoint(-10.0F, 16.0F, 48.0F);
        this.seg3lspike.setRotationPoint(14.0F, -4.0F, 48.0F);
        this.seg3rspike.setRotationPoint(-14.0F, -4.0F, 48.0F);
        this.seg3ltopspike.setRotationPoint(10.0F, -10.0F, 48.0F);
        this.seg3rtopspike.setRotationPoint(-10.0F, -10.0F, 48.0F);
        this.seg3lbackspike.setRotationPoint(13.0F, -8.0F, 54.0F);
        this.seg3rbackspike.setRotationPoint(-13.0F, -8.0F, 54.0F);
        this.seg3.rotationPointZ = 60.0F;
        int tailIndex = 6;
        newangle = MathHelper.cos((float)(ageInTicks * 0.4F * this.wingspeed
            - 0.39269908169872414D * tailIndex)) * 3.1415927F * 0.07F;
        this.seg3lspike.rotateAngleZ = newangle;
        this.seg3rspike.rotateAngleZ = -newangle;
        this.seg3lbackspike.rotateAngleX = -0.977F + MathHelper.cos(ageInTicks * 0.81F
            * this.wingspeed) * 3.1415927F * 0.04F;
        this.seg3rbackspike.rotateAngleX = -0.977F + MathHelper.cos(ageInTicks * 0.87F
            * this.wingspeed) * 3.1415927F * 0.04F;
        this.seg3lbackspike.rotateAngleY = 0.28F + MathHelper.cos(ageInTicks * 1.11F
            * this.wingspeed) * 3.1415927F * 0.04F;
        this.seg3rbackspike.rotateAngleY = -0.28F + MathHelper.cos(ageInTicks * 1.3F
            * this.wingspeed) * 3.1415927F * 0.04F;
        seg3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        seg3lfoot.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        seg3rfoot.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        seg3lspike.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        seg3rspike.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        seg3ltopspike.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        seg3rtopspike.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        seg3lbackspike.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        seg3rbackspike.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }

   private void setRotation(ModelRenderer model, float x, float y, float z) {
     model.rotateAngleX = x;
     model.rotateAngleY = y;
     model.rotateAngleZ = z;
   }

   public void setRotationAngles(Entity entity, float limbSwing, float limbSwingAmount,
        float ageInTicks, float netHeadYaw, float headPitch) {
CaterKillerEntity.CustomEntity e = (CaterKillerEntity.CustomEntity) entity;
     this.animationAge = ageInTicks;
     this.animationLimbAmount = limbSwingAmount;
     this.animationAttacking = e.getAttacking();
     this.seg3.setRotationPoint(0.0F, 3.0F, 48.0F);
     float newangle = 0.0F;
     float headoff = 0.0F;
     float zpi = 0.0F;
     float zdist = 0.0F;

     if (e.getAttacking() != 0) {
       newangle = MathHelper.cos(ageInTicks * 1.7F * this.wingspeed) * 3.1415927F * 0.07F;
     } else {
       newangle = MathHelper.cos(ageInTicks * 1.3F * this.wingspeed) * 3.1415927F * 0.025F;
     } 
     this.ljaw.rotateAngleZ = 0.139F + newangle;
     this.rjaw.rotateAngleZ = -0.139F - newangle;
 
     
     if (e.getAttacking() != 0) {
       headoff = MathHelper.cos(ageInTicks * 1.7F * this.wingspeed) * 8.0F;
     } else {
       headoff = MathHelper.cos(ageInTicks * 0.3F * this.wingspeed) * 2.0F;
     } 
     this.Head.rotationPointY = -8.0F + headoff;
     this.falsehead.rotationPointY = -8.0F + headoff;
     this.ltusk1.rotationPointY = -25.0F + headoff;
     this.ltusk2.rotationPointY = -25.0F + headoff;
     this.rtusk1.rotationPointY = -25.0F + headoff;
     this.rtusk2.rotationPointY = -25.0F + headoff;
     this.ljaw.rotationPointY = -1.0F + headoff;
     this.rjaw.rotationPointY = -1.0F + headoff;
 
 
 
 
 
     
     newangle = MathHelper.cos(ageInTicks * 2.11F * this.wingspeed) * 3.1415927F * 0.08F;
     this.ltusk2.rotateAngleY = 0.802F + newangle;
     newangle = MathHelper.cos(ageInTicks * 2.3F * this.wingspeed) * 3.1415927F * 0.08F;
     this.rtusk2.rotateAngleY = -0.802F + newangle;








     int i;
     for (i = 0; i < 3; i++) {
       this.seg1.rotationPointY = -8.0F + headoff / (i + 1) + (8 * i);
       this.seg1lspike.rotationPointY = -32.0F + headoff / (i + 1) + (8 * i);
       this.seg1rspike.rotationPointY = -32.0F + headoff / (i + 1) + (8 * i);
       this.seg1ltopspike.rotationPointY = -39.0F + headoff / (i + 1) + (8 * i);
       this.seg1rtopspike.rotationPointY = -39.0F + headoff / (i + 1) + (8 * i);
       this.seg1lleg.rotationPointY = -8.0F + headoff / (i + 1) + (8 * i);
       this.seg1rleg.rotationPointY = -8.0F + headoff / (i + 1) + (8 * i);
       
       this.seg1.rotationPointZ = (-12 + 14 * i);
       this.seg1lspike.rotationPointZ = (-6 + 14 * i);
       this.seg1rspike.rotationPointZ = (-6 + 14 * i);
       this.seg1ltopspike.rotationPointZ = (-6 + 14 * i);
       this.seg1rtopspike.rotationPointZ = (-6 + 14 * i);
       this.seg1lleg.rotationPointZ = (-5 + 14 * i);
       this.seg1rleg.rotationPointZ = (-5 + 14 * i);
       
       newangle = MathHelper.cos((float)((ageInTicks * 0.91F * this.wingspeed) + 0.39269908169872414D * i)) * 3.1415927F * 0.08F;
       this.seg1lspike.rotateAngleZ = newangle;
       this.seg1rspike.rotateAngleZ = -newangle;
       
       if (e.getAttacking() != 0) {
         newangle = MathHelper.cos((float)((ageInTicks * 2.91F * this.wingspeed) + 0.39269908169872414D * i)) * 3.1415927F * 0.15F;
       } else {
         newangle = MathHelper.cos((float)((ageInTicks * 0.35F * this.wingspeed) + 0.39269908169872414D * i)) * 3.1415927F * 0.04F;
       } 
       
       this.seg1lleg.rotateAngleX = newangle;
       this.seg1rleg.rotateAngleX = -newangle;







     } 
     
     for (i = 0; i < 6; i++) {
       
       zdist = MathHelper.cos(ageInTicks * 1.7F * this.wingspeed + zpi) * 1.5F * limbSwingAmount;
       
       this.seg2.rotationPointZ = 39.0F + (16.0F + zdist) * i;
       this.seg2lfoot.rotationPointZ = 39.0F + (16.0F + zdist) * i;
       this.seg2rfoot.rotationPointZ = 39.0F + (16.0F + zdist) * i;
       this.seg2ltopspike.rotationPointZ = 39.0F + (16.0F + zdist) * i;
       this.seg2rtopspike.rotationPointZ = 39.0F + (16.0F + zdist) * i;
       this.seg2lspike.rotationPointZ = 39.0F + (16.0F + zdist) * i;
       this.seg2rspike.rotationPointZ = 39.0F + (16.0F + zdist) * i;
       
       newangle = MathHelper.cos((float)((ageInTicks * 0.4F * this.wingspeed) - 0.39269908169872414D * i)) * 3.1415927F * 0.07F;
       this.seg2lspike.rotateAngleZ = newangle;
       this.seg2rspike.rotateAngleZ = -newangle;







       zpi += 0.7853982F;
     } 
     
     this.seg2rspike.rotationPointZ += 16.0F;
     this.seg3lfoot.rotationPointZ = this.seg3.rotationPointZ;
     this.seg3rfoot.rotationPointZ = this.seg3.rotationPointZ;
     this.seg3lspike.rotationPointZ = this.seg3.rotationPointZ;
     this.seg3rspike.rotationPointZ = this.seg3.rotationPointZ;
     this.seg3ltopspike.rotationPointZ = this.seg3.rotationPointZ;
     this.seg3rtopspike.rotationPointZ = this.seg3.rotationPointZ;
     this.seg3.rotationPointZ += 6.0F;
     this.seg3.rotationPointZ += 6.0F;
     
     i = 6;
     newangle = MathHelper.cos((float)((ageInTicks * 0.4F * this.wingspeed) - 0.39269908169872414D * i)) * 3.1415927F * 0.07F;
     this.seg3lspike.rotateAngleZ = newangle;
     this.seg3rspike.rotateAngleZ = -newangle;
     
     newangle = MathHelper.cos(ageInTicks * 0.81F * this.wingspeed) * 3.1415927F * 0.04F;
     this.seg3lbackspike.rotateAngleX = -0.977F + newangle;
     newangle = MathHelper.cos(ageInTicks * 0.87F * this.wingspeed) * 3.1415927F * 0.04F;
     this.seg3rbackspike.rotateAngleX = -0.977F + newangle;
     
     newangle = MathHelper.cos(ageInTicks * 1.11F * this.wingspeed) * 3.1415927F * 0.04F;
     this.seg3lbackspike.rotateAngleY = 0.28F + newangle;
     newangle = MathHelper.cos(ageInTicks * 1.3F * this.wingspeed) * 3.1415927F * 0.04F;
     this.seg3rbackspike.rotateAngleY = -0.28F + newangle;
    }
 }
}

