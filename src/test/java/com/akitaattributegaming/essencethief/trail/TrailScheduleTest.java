package com.akitaattributegaming.essencethief.trail;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
}
