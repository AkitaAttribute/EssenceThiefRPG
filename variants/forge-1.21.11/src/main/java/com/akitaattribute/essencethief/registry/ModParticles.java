package com.akitaattribute.essencethief.registry;

import com.akitaattribute.essencethief.EssenceThiefMod;
import com.akitaattribute.essencethief.particle.EssenceRisingParticleOptions;
import com.akitaattribute.essencethief.particle.EssenceRisingParticleType;
import net.minecraft.core.particles.ParticleType;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class ModParticles {
    private static final DeferredRegister<ParticleType<?>> PARTICLES =
        DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, EssenceThiefMod.MOD_ID);

    public static final RegistryObject<ParticleType<EssenceRisingParticleOptions>> ESSENCE_RISING =
        PARTICLES.register("essence_rising", () -> new EssenceRisingParticleType(false));

    private ModParticles() {
    }

    public static void register(BusGroup modBusGroup) {
        PARTICLES.register(modBusGroup);
    }
}
