package com.akitaattribute.essencethief.particle;

import com.akitaattribute.essencethief.api.EssenceColor;

/**
 * Compatibility placeholder for the 1.21.x variant.
 *
 * Active 1.21.x trail rendering uses vanilla ColorParticleOption directly.
 */
public record EssenceRisingParticleOptions(float red, float green, float blue, float scale) {
    public static EssenceRisingParticleOptions fromColor(EssenceColor color) {
        return new EssenceRisingParticleOptions(color.redFloat(), color.greenFloat(), color.blueFloat(), 1.0F);
    }
}
