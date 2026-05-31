package com.akitaattribute.essencethief;

import com.akitaattribute.essencethief.trail.EntityTrailManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;

@Mod(EssenceThiefMod.MOD_ID)
public final class EssenceThiefMod {
    public static final String MOD_ID = "essencethief";

    public EssenceThiefMod() {
        MinecraftForge.EVENT_BUS.addListener(EntityTrailManager::onLevelTick);
    }
}
