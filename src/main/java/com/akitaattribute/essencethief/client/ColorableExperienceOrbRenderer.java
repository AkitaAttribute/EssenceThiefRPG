package com.akitaattribute.essencethief.client;

import com.akitaattribute.essencethief.EssenceThiefMod;
import com.akitaattribute.essencethief.api.Essence;
import com.akitaattribute.essencethief.api.EssenceColor;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/** Vanilla-compatible XP-orb renderer which honors Essence's synced RGB marker when one exists. */
public final class ColorableExperienceOrbRenderer extends EntityRenderer<ExperienceOrb> {
    private static final ResourceLocation TEXTURE = ResourceLocation.withDefaultNamespace("textures/entity/experience_orb.png");
    private static final RenderType RENDER_TYPE = RenderType.itemEntityTranslucentCull(TEXTURE);

    public ColorableExperienceOrbRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.shadowRadius = 0.15F;
        this.shadowStrength = 0.75F;
    }

    @Override
    public void render(ExperienceOrb orb, float yaw, float partialTick, PoseStack poseStack, MultiBufferSource buffers, int packedLight) {
        poseStack.pushPose();
        int icon = orb.getIcon();
        float minU = (icon % 4 * 16) / 64.0F;
        float maxU = (icon % 4 * 16 + 16) / 64.0F;
        float minV = (icon / 4 * 16) / 64.0F;
        float maxV = (icon / 4 * 16 + 16) / 64.0F;
        EssenceColor color = Essence.readColor(orb).orElseGet(() -> vanillaColor(orb, partialTick));

        poseStack.translate(0.0F, 0.1F, 0.0F);
        poseStack.mulPose(this.entityRenderDispatcher.cameraOrientation());
        poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
        poseStack.scale(0.3F, 0.3F, 0.3F);
        VertexConsumer consumer = buffers.getBuffer(RENDER_TYPE);
        PoseStack.Pose pose = poseStack.last();
        vertex(consumer, pose, -0.5F, -0.25F, color, minU, maxV);
        vertex(consumer, pose, 0.5F, -0.25F, color, maxU, maxV);
        vertex(consumer, pose, 0.5F, 0.75F, color, maxU, minV);
        vertex(consumer, pose, -0.5F, 0.75F, color, minU, minV);
        poseStack.popPose();
        super.render(orb, yaw, partialTick, poseStack, buffers, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(ExperienceOrb orb) {
        return TEXTURE;
    }

    private static EssenceColor vanillaColor(ExperienceOrb orb, float partialTick) {
        float phase = (orb.tickCount + partialTick) / 2.0F;
        int red = (int) ((Mth.sin(phase) + 1.0F) * 127.5F);
        int blue = (int) ((Mth.sin(phase + 4.1887903F) + 1.0F) * 127.5F);
        return new EssenceColor(red, 255, blue);
    }

    private static void vertex(VertexConsumer consumer, PoseStack.Pose pose, float x, float y, EssenceColor color, float u, float v) {
        consumer.addVertex(pose, x, y, 0.0F)
            .setColor(color.red(), color.green(), color.blue(), 128)
            .setUv(u, v)
            .setOverlay(OverlayTexture.NO_OVERLAY)
            .setLight(15728880)
            .setNormal(pose, 0.0F, 1.0F, 0.0F);
    }

    @Mod.EventBusSubscriber(modid = EssenceThiefMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static final class Registration {
        private Registration() {
        }

        @SubscribeEvent
        public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
            event.registerEntityRenderer(EntityType.EXPERIENCE_ORB, ColorableExperienceOrbRenderer::new);
        }
    }
}
