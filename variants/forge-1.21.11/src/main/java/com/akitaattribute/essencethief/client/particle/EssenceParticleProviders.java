package com.akitaattribute.essencethief.client.particle;

import com.akitaattribute.essencethief.registry.ModParticles;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;

public final class EssenceParticleProviders {
    private EssenceParticleProviders() {
    }

    public static void register() {
        RegisterParticleProvidersEvent.BUS.addListener(EssenceParticleProviders::register);
    }

    private static void register(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(ModParticles.ESSENCE_RISING.get(), EssenceRisingParticle.Provider::new);
    }
}
