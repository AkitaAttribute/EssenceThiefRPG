package com.akitaattributegaming.essencethief.api;

import com.akitaattributegaming.essencethief.trail.EntityTrailManager;
import net.minecraft.world.entity.Entity;

import java.util.Objects;

/** Adds reusable essence behavior without replacing the underlying vanilla or modded entity. */
public class WrappedEntity<E extends Entity> {
    private final E entity;

    public WrappedEntity(E entity) {
        this.entity = Objects.requireNonNull(entity, "entity");
    }

    public final E entity() {
        return entity;
    }

    /** Starts or replaces the rising three-stream trail attached to this entity. */
    public WrappedEntity<E> createRisingTrail(EssenceColor color) {
        EntityTrailManager.track(entity, Objects.requireNonNull(color, "color"));
        return this;
    }

    public WrappedEntity<E> createRisingTrail(int rgb) {
        return createRisingTrail(EssenceColor.fromRgb(rgb));
    }

    public WrappedEntity<E> removeRisingTrail() {
        EntityTrailManager.untrack(entity);
        return this;
    }
}
