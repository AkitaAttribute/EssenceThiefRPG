package com.akitaattribute.essencethief;

import com.akitaattribute.essencethief.registry.ModParticles;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(EssenceThiefMod.MOD_ID)
public final class EssenceThiefMod {
    public static final String MOD_ID = "essencethief";

    public EssenceThiefMod() {
        ModParticles.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
