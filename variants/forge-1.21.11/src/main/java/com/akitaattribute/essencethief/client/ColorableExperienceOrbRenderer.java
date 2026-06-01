package com.akitaattribute.essencethief.client;

import com.akitaattribute.essencethief.api.Essence;
import com.akitaattribute.essencethief.api.EssenceColor;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.ExperienceOrbRenderState;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraftforge.client.event.EntityRenderersEvent;

/** Minecraft 1.21.11 render-state implementation for colored Essence XP orbs. */
public final class ColorableExperienceOrbRenderer extends EntityRenderer<ExperienceOrb, ColorableExperienceOrbRenderer.State> {
    private static final Identifier TEXTURE = Identifier.withDefaultNamespace("textures/entity/experience_orb.png");
    private static final RenderType RENDER_TYPE = RenderTypes.itemEntityTranslucentCull(TEXTURE);

    public ColorableExperienceOrbRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.shadowRadius = 0.15F;
        this.shadowStrength = 0.75F;
    }

    public static void register() {
        EntityRenderersEvent.RegisterRenderers.BUS.addListener(event ->
            event.registerEntityRenderer(EntityType.EXPERIENCE_ORB, ColorableExperienceOrbRenderer::new));
    }

    @Override
    public void submit(State state, PoseStack poseStack, SubmitNodeCollector collector, CameraRenderState camera) {
        poseStack.pushPose();
        int icon = state.icon;
        float minU = (icon % 4 * 16) / 64.0F;
        float maxU = (icon % 4 * 16 + 16) / 64.0F;
        float minV = (icon / 4 * 16) / 64.0F;
        float maxV = (icon / 4 * 16 + 16) / 64.0F;
        EssenceColor color = state.essenceColor != null ? state.essenceColor : vanillaColor(state.ageInTicks);
        poseStack.translate(0.0F, 0.1F, 0.0F);
        poseStack.mulPose(camera.orientation);
        poseStack.scale(0.3F, 0.3F, 0.3F);
        collector.submitCustomGeometry(poseStack, RENDER_TYPE, (pose, consumer) -> {
            vertex(consumer, pose, -0.5F, -0.25F, color, minU, maxV, state.lightCoords);
            vertex(consumer, pose, 0.5F, -0.25F, color, maxU, maxV, state.lightCoords);
            vertex(consumer, pose, 0.5F, 0.75F, color, maxU, minV, state.lightCoords);
            vertex(consumer, pose, -0.5F, 0.75F, color, minU, minV, state.lightCoords);
        });
        poseStack.popPose();
        super.submit(state, poseStack, collector, camera);
    }

    @Override
    public State createRenderState() {
        return new State();
    }

    @Override
    public void extractRenderState(ExperienceOrb orb, State state, float partialTick) {
        super.extractRenderState(orb, state, partialTick);
        state.icon = orb.getIcon();
        state.essenceColor = Essence.readColor(orb).orElse(null);
    }

    private static EssenceColor vanillaColor(float ageInTicks) {
        float phase = ageInTicks / 2.0F;
        int red = (int) ((Mth.sin(phase) + 1.0F) * 127.5F);
        int blue = (int) ((Mth.sin(phase + 4.1887903F) + 1.0F) * 25.5F);
        return new EssenceColor(red, 255, blue);
    }

    private static void vertex(VertexConsumer consumer, PoseStack.Pose pose, float x, float y, EssenceColor color, float u, float v, int light) {
        consumer.addVertex(pose, x, y, 0.0F)
            .setColor(color.red(), color.green(), color.blue(), 128)
            .setUv(u, v)
            .setOverlay(OverlayTexture.NO_OVERLAY)
            .setLight(light)
            .setNormal(pose, 0.0F, 1.0F, 0.0F);
    }

    public static final class State extends ExperienceOrbRenderState {
        private EssenceColor essenceColor;
    }
}
