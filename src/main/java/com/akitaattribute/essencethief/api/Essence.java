package com.akitaattribute.essencethief.api;

import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.level.Level;

import java.util.Objects;
import java.util.Optional;

/** A composition-based wrapper around Minecraft's vanilla XP orb. */
public final class Essence extends WrappedEntity<ExperienceOrb> {
    public Essence(ExperienceOrb orb) {
        super(orb);
    }

    public static Essence create(Level level, double x, double y, double z, int value) {
        Objects.requireNonNull(level, "level");
        ExperienceOrb orb = new ExperienceOrb(level, x, y, z, value);
        level.addFreshEntity(orb);
        return new Essence(orb);
    }

    /** Colors the orb renderer and its optional trail using a conventional 0xRRGGBB value. */
    public Essence color(int rgb) {
        return color(EssenceColor.fromRgb(rgb));
    }

    /** Colors the orb renderer and its optional trail without replacing the orb's custom display name. */
    public Essence color(EssenceColor color) {
        Objects.requireNonNull(color, "color");
        EssenceColorData.apply(entity(), color);
        EntityTrailManagerSafe.syncTrailColor(entity(), color);
        return this;
    }

    public Optional<EssenceColor> color() {
        return readColor(entity());
    }

    @Override
    public Essence createRisingTrail(EssenceColor color) {
        super.createRisingTrail(color);
        return this;
    }

    @Override
    public Essence createRisingTrail(int rgb) {
        super.createRisingTrail(rgb);
        return this;
    }

    @Override
    public Essence removeRisingTrail() {
        super.removeRisingTrail();
        return this;
    }

    public static Optional<EssenceColor> readColor(ExperienceOrb orb) {
        return EssenceColorData.read(orb);
    }

    private static final class EntityTrailManagerSafe {
        private static void syncTrailColor(ExperienceOrb orb, EssenceColor color) {
            if (!orb.level().isClientSide()) {
                com.akitaattribute.essencethief.trail.EntityTrailManager.updateColorIfTracked(orb, color);
            }
        }
    }
}
