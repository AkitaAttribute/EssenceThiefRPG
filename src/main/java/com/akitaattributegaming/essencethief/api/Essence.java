package com.akitaattributegaming.essencethief.api;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.level.Level;

import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

/** A composition-based wrapper around Minecraft's vanilla XP orb. */
public final class Essence extends WrappedEntity<ExperienceOrb> {
    private static final String COLOR_MARKER = "essencethief:color:";

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

    /** Colors the orb renderer and its optional trail. The marker uses vanilla synced entity data. */
    public Essence color(EssenceColor color) {
        Objects.requireNonNull(color, "color");
        entity().setCustomName(Component.literal(COLOR_MARKER + String.format(Locale.ROOT, "%06x", color.rgb())));
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
        Component name = orb.getCustomName();
        if (name == null) {
            return Optional.empty();
        }
        String value = name.getString();
        if (!value.startsWith(COLOR_MARKER) || value.length() != COLOR_MARKER.length() + 6) {
            return Optional.empty();
        }
        try {
            return Optional.of(EssenceColor.fromRgb(Integer.parseInt(value.substring(COLOR_MARKER.length()), 16)));
        } catch (NumberFormatException ignored) {
            return Optional.empty();
        }
    }
}
