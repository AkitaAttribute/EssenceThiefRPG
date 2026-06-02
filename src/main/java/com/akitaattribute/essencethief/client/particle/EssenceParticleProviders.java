package com.akitaattribute.essencethief.client.particle;

import com.akitaattribute.essencethief.EssenceThiefMod;
import com.akitaattribute.essencethief.registry.ModParticles;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = EssenceThiefMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class EssenceParticleProviders {
    private EssenceParticleProviders() {
    }

    @SubscribeEvent
    public static void register(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(ModParticles.ESSENCE_RISING.get(), EssenceRisingParticle.Provider::new);
    }
}
