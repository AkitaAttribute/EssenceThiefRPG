package com.akitaattribute.essencethief;

import com.akitaattribute.essencethief.client.ColorableExperienceOrbRenderer;
import com.akitaattribute.essencethief.trail.EntityTrailManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLEnvironment;

@Mod(EssenceThiefMod.MOD_ID)
public final class EssenceThiefMod {
    public static final String MOD_ID = "essencethief";

    public EssenceThiefMod() {
        TickEvent.LevelTickEvent.Post.BUS.addListener(EntityTrailManager::onLevelTick);
        if (FMLEnvironment.dist == Dist.CLIENT) {
            ColorableExperienceOrbRenderer.register();
        }
    }
}
