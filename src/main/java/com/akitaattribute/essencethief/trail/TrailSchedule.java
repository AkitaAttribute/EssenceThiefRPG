package com.akitaattribute.essencethief.trail;

/** Pure scheduling math for three staggered particle streams, each rising two blocks before a half-second gap. */
public final class TrailSchedule {
    public static final int STREAM_COUNT = 3;
    public static final int STAGGER_TICKS = 10;
    public static final int RISE_TICKS = 30;
    public static final int GAP_TICKS = 10;
    public static final int CYCLE_TICKS = RISE_TICKS + GAP_TICKS;
    public static final double RISE_BLOCKS = 2.0D;

    private TrailSchedule() {
    }

    public static boolean shouldSpawn(int gameTick, int stream) {
        validateStream(stream);
        return Math.floorMod(gameTick - stream * STAGGER_TICKS, CYCLE_TICKS) == 0;
    }

    public static boolean isRising(int gameTick, int stream) {
        return progress(gameTick, stream) >= 0.0D;
    }

    public static double progress(int gameTick, int stream) {
        validateStream(stream);
        int tick = Math.floorMod(gameTick - stream * STAGGER_TICKS, CYCLE_TICKS);
        return tick < RISE_TICKS ? tick / (double) RISE_TICKS : -1.0D;
    }

    public static double upwardVelocityPerTick() {
        return RISE_BLOCKS / RISE_TICKS;
    }

    public static double horizontalVelocityPerTick(double finalRadius) {
        return finalRadius / RISE_TICKS;
    }

    private static void validateStream(int stream) {
        if (stream < 0 || stream >= STREAM_COUNT) {
            throw new IllegalArgumentException("stream must be between 0 and " + (STREAM_COUNT - 1));
        }
    }
}
