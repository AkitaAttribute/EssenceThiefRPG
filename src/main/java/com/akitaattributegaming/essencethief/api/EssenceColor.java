package com.akitaattributegaming.essencethief.api;

/** An immutable RGB color accepted as either channels or a conventional 0xRRGGBB value. */
public record EssenceColor(int red, int green, int blue) {
    public static final EssenceColor DEFAULT = new EssenceColor(128, 255, 32);

    public EssenceColor {
        checkChannel("red", red);
        checkChannel("green", green);
        checkChannel("blue", blue);
    }

    public static EssenceColor fromRgb(int rgb) {
        return new EssenceColor((rgb >>> 16) & 0xff, (rgb >>> 8) & 0xff, rgb & 0xff);
    }

    public int rgb() {
        return (red << 16) | (green << 8) | blue;
    }

    public float redFloat() {
        return red / 255.0F;
    }

    public float greenFloat() {
        return green / 255.0F;
    }

    public float blueFloat() {
        return blue / 255.0F;
    }

    private static void checkChannel(String name, int value) {
        if (value < 0 || value > 255) {
            throw new IllegalArgumentException(name + " must be between 0 and 255, got " + value);
        }
    }
}
