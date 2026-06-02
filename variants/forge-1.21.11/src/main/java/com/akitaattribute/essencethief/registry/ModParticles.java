package com.akitaattribute.essencethief.registry;

import net.minecraftforge.eventbus.api.bus.BusGroup;

/**
 * Compatibility no-op for the 1.21.x variant.
 *
 * Custom particle registration was removed from 1.21.x because the variant now
 * uses vanilla ColorParticleOption/ENTITY_EFFECT for velocity-based trails.
 */
public final class ModParticles {
    private ModParticles() {
    }

    public static void register(BusGroup modBusGroup) {
    }
}
