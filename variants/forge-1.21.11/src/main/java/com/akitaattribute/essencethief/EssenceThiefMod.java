package com.akitaattribute.essencethief;

import com.akitaattribute.essencethief.client.ColorableExperienceOrbRenderer;
import com.akitaattribute.essencethief.client.particle.EssenceParticleProviders;
import com.akitaattribute.essencethief.trail.EntityTrailManager;
import com.akitaattribute.essencethief.registry.ModParticles;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.EntityLeaveLevelEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.eventbus.api.bus.BusGroup;

@Mod(EssenceThiefMod.MOD_ID)
public final class EssenceThiefMod {
    public static final String MOD_ID = "essencethief";

    public EssenceThiefMod() {
        ModParticles.register(BusGroup.DEFAULT);
        TickEvent.LevelTickEvent.Post.BUS.addListener(EntityTrailManager::onLevelTick);
        EntityJoinLevelEvent.BUS.addListener(EntityTrailManager::onEntityJoin);
        EntityLeaveLevelEvent.BUS.addListener(EntityTrailManager::onEntityLeave);
        if (FMLEnvironment.dist == Dist.CLIENT) {
            ColorableExperienceOrbRenderer.register();
            EssenceParticleProviders.register();
        }
    }
}
