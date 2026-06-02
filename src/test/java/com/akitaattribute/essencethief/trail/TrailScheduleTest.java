package com.akitaattribute.essencethief.trail;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TrailScheduleTest {
    @Test
    void staggersStreamsByHalfASecond() {
        assertEquals(0.0D, TrailSchedule.progress(0, 0));
        assertEquals(0.0D, TrailSchedule.progress(10, 1));
        assertEquals(0.0D, TrailSchedule.progress(20, 2));
    }

    @Test
    void insertsHalfSecondGapAfterRise() {
        assertEquals(29.0D / 30.0D, TrailSchedule.progress(29, 0));
        assertEquals(-1.0D, TrailSchedule.progress(30, 0));
        assertEquals(-1.0D, TrailSchedule.progress(39, 0));
        assertEquals(0.0D, TrailSchedule.progress(40, 0));
    }

    @Test
    void rejectsUnknownStream() {
        assertThrows(IllegalArgumentException.class, () -> TrailSchedule.progress(0, 3));
    }
    @Test
    void shouldSpawnStaggeredParticles() {
        assertTrue(TrailSchedule.shouldSpawn(0, 0));
        assertFalse(TrailSchedule.shouldSpawn(0, 1));
        assertTrue(TrailSchedule.shouldSpawn(10, 1));
        assertTrue(TrailSchedule.shouldSpawn(20, 2));
        assertTrue(TrailSchedule.shouldSpawn(TrailSchedule.CYCLE_TICKS, 0));
    }

    @Test
    void keepsStreamsActiveWhileRising() {
        assertTrue(TrailSchedule.isRising(0, 0));
        assertTrue(TrailSchedule.isRising(29, 0));
        assertFalse(TrailSchedule.isRising(30, 0));
        assertFalse(TrailSchedule.isRising(39, 0));
        assertTrue(TrailSchedule.isRising(40, 0));
    }

    @Test
    void calculatesVelocityForTwoBlockRise() {
        assertEquals(2.0D / 30.0D, TrailSchedule.upwardVelocityPerTick());
        assertEquals(0.45D / 30.0D, TrailSchedule.horizontalVelocityPerTick(0.45D));
    }

}
