package com.akitaattribute.essencethief;

import com.akitaattribute.essencethief.compat.ForgeEventBusCompatibility;
import com.akitaattribute.essencethief.trail.EntityTrailManager;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.fml.common.Mod;

@Mod(EssenceThiefMod.MOD_ID)
public final class EssenceThiefMod {
    public static final String MOD_ID = "essencethief";

    public EssenceThiefMod() {
        ForgeEventBusCompatibility.addListener(TickEvent.LevelTickEvent.class, EntityTrailManager::onLevelTick);
    }
}
