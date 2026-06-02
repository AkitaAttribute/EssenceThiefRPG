package com.akitaattribute.essencethief.particle;

import com.mojang.serialization.Codec;
import net.minecraft.core.particles.ParticleType;

public final class EssenceRisingParticleType extends ParticleType<EssenceRisingParticleOptions> {
    public EssenceRisingParticleType(boolean overrideLimiter) {
        super(overrideLimiter, EssenceRisingParticleOptions.DESERIALIZER);
    }

    @Override
    public Codec<EssenceRisingParticleOptions> codec() {
        return EssenceRisingParticleOptions.CODEC;
    }
}
