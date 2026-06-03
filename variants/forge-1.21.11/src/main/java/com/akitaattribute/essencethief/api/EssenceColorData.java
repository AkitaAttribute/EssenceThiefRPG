package com.akitaattribute.essencethief.api;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ExperienceOrb;

import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

/** Stores Essence color without replacing vanilla display names. */
public final class EssenceColorData {
    public static final String COLOR_TAG_PREFIX = "essencethief.color.";
    public static final String COLOR_NBT_KEY = "EssenceThiefColor";

    private EssenceColorData() {
    }

    public static void apply(ExperienceOrb orb, EssenceColor color) {
        Objects.requireNonNull(orb, "orb");
        Objects.requireNonNull(color, "color");
        stripExistingEssenceTags(orb);
        orb.addTag(tagFor(color));
        orb.getPersistentData().putInt(COLOR_NBT_KEY, color.rgb());
    }

    public static Optional<EssenceColor> read(ExperienceOrb orb) {
        return read((Entity) orb);
    }

    public static Optional<EssenceColor> read(Entity entity) {
        Objects.requireNonNull(entity, "entity");
        Optional<EssenceColor> tagColor = readFromTags(entity);
        if (tagColor.isPresent()) {
            return tagColor;
        }
        CompoundTag data = entity.getPersistentData();
        if (data.contains(COLOR_NBT_KEY)) {
            return data.getInt(COLOR_NBT_KEY).map(EssenceColor::fromRgb);
        }
        return Optional.empty();
    }

    public static String tagFor(EssenceColor color) {
        Objects.requireNonNull(color, "color");
        return COLOR_TAG_PREFIX + String.format(Locale.ROOT, "%06x", color.rgb());
    }

    private static Optional<EssenceColor> readFromTags(Entity entity) {
        for (String tag : entity.getTags()) {
            if (!tag.startsWith(COLOR_TAG_PREFIX) || tag.length() != COLOR_TAG_PREFIX.length() + 6) {
                continue;
            }
            try {
                return Optional.of(EssenceColor.fromRgb(Integer.parseInt(tag.substring(COLOR_TAG_PREFIX.length()), 16)));
            } catch (NumberFormatException ignored) {
                return Optional.empty();
            }
        }
        return Optional.empty();
    }

    private static void stripExistingEssenceTags(Entity entity) {
        for (String tag : entity.getTags().toArray(String[]::new)) {
            if (tag.startsWith(COLOR_TAG_PREFIX)) {
                entity.removeTag(tag);
            }
        }
    }
}
