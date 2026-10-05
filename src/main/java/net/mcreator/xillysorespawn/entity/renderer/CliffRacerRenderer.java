package net.mcreator.xillysorespawn.entity.renderer;

import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.MobRenderer;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.mcreator.xillysorespawn.entity.CliffRacerEntity;

@OnlyIn(Dist.CLIENT)
public class CliffRacerRenderer {
    private static final String TEXTURE = "xillys_orespawn:textures/entities/cliffracertexture.png";
    public static class ModelRegisterHandler {
        @SubscribeEvent @OnlyIn(Dist.CLIENT)
        public void registerModels(ModelRegistryEvent event) {
            RenderingRegistry.registerEntityRenderingHandler(CliffRacerEntity.entity, manager ->
                new MobRenderer(manager, new ModelCliffRacer(1.0F), 0.5F) {
                    @Override public ResourceLocation getEntityTexture(Entity entity) { return new ResourceLocation(TEXTURE); }
                });
        }
    }

public static class ModelCliffRacer extends EntityModel<Entity> {
   private float wingspeed = 1.0F;

   private final ModelRenderer Body;

   private final ModelRenderer Fins;
   private final ModelRenderer LWing;
   private final ModelRenderer RWing;
   private final ModelRenderer Tail;
   private final ModelRenderer TailEnd;
   private final ModelRenderer Head;
   private final ModelRenderer Beak;

   public ModelCliffRacer(float f1) {
     this.wingspeed = f1;

     this.textureWidth = 64;
     this.textureHeight = 64;

     this.Body = new ModelRenderer(this, 0, 52);
     this.Body.addBox(0.0F, 0.0F, 0.0F, 3, 1, 10);
     this.Body.setRotationPoint(-1.0F, 15.0F, -4.0F);

     this.Body.mirror = true;
     setRotation(this.Body, 0.0F, 0.0F, 0.0F);
     this.Fins = new ModelRenderer(this, 0, 40);
     this.Fins.addBox(0.0F, -4.0F, 0.0F, 1, 6, 3);
     this.Fins.setRotationPoint(0.0F, 15.0F, -1.0F);

     this.Fins.mirror = true;
     setRotation(this.Fins, 0.0F, 0.0F, 0.0F);
     this.LWing = new ModelRenderer(this, 0, 31);
     this.LWing.addBox(0.0F, 0.0F, 0.0F, 7, 1, 6);
     this.LWing.setRotationPoint(2.0F, 15.0F, -2.0F);

     this.LWing.mirror = true;
     setRotation(this.LWing, 0.0F, 0.0F, 0.0F);
     this.RWing = new ModelRenderer(this, 39, 0);
     this.RWing.addBox(-7.0F, 0.0F, 0.0F, 7, 1, 6);
     this.RWing.setRotationPoint(-1.0F, 15.0F, -2.0F);

     this.RWing.mirror = true;
     setRotation(this.RWing, 0.0F, 0.0F, 0.0F);
     this.Tail = new ModelRenderer(this, 0, 16);
     this.Tail.addBox(0.0F, 0.0F, 0.0F, 1, 1, 9);
     this.Tail.setRotationPoint(0.0F, 15.0F, 6.0F);

     this.Tail.mirror = true;
     setRotation(this.Tail, 0.0F, 0.0F, 0.0F);
     this.TailEnd = new ModelRenderer(this, 0, 10);
     this.TailEnd.addBox(0.0F, -1.0F, 9.0F, 2, 2, 2);
     this.TailEnd.setRotationPoint(-0.5F, 15.0F, 6.0F);

     this.TailEnd.mirror = true;
     setRotation(this.TailEnd, 0.0F, 0.0F, 0.0F);
     this.Head = new ModelRenderer(this, 28, 21);
     this.Head.addBox(0.0F, 0.0F, 0.0F, 2, 2, 2);
     this.Head.setRotationPoint(-0.5F, 14.0F, -6.0F);

     this.Head.mirror = true;
     setRotation(this.Head, 0.0F, 0.0F, 0.0F);
     this.Beak = new ModelRenderer(this, 0, 0);
     this.Beak.addBox(0.0F, 0.0F, 0.0F, 1, 1, 2);
     this.Beak.setRotationPoint(0.0F, 14.5F, -8.0F);

     this.Beak.mirror = true;
     setRotation(this.Beak, 0.0F, 0.0F, 0.0F);
   }

   public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay,
        float red, float green, float blue, float alpha) {
        Body.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Fins.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        LWing.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        RWing.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Tail.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        TailEnd.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Head.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Beak.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }

   private void setRotation(ModelRenderer model, float x, float y, float z) {
     model.rotateAngleX = x;
     model.rotateAngleY = y;
     model.rotateAngleZ = z;
   }

   public void setRotationAngles(Entity entity, float limbSwing, float limbSwingAmount,
        float ageInTicks, float netHeadYaw, float headPitch) {
float newangle = 0.0F;

     newangle = MathHelper.cos(ageInTicks * 1.3F * this.wingspeed) * 3.1415927F * 0.25F;
     this.LWing.rotateAngleZ = newangle;
     this.RWing.rotateAngleZ = -newangle;
    }
 }
}

