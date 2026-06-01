package com.akitaattribute.essencethief.api;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EssenceColorTest {
    @Test
    void convertsConventionalRgbValues() {
        EssenceColor color = EssenceColor.fromRgb(0x9B5CFF);

        assertEquals(155, color.red());
        assertEquals(92, color.green());
        assertEquals(255, color.blue());
        assertEquals(0x9B5CFF, color.rgb());
    }

    @Test
    void rejectsChannelsOutsideRgbRange() {
        assertThrows(IllegalArgumentException.class, () -> new EssenceColor(-1, 0, 0));
        assertThrows(IllegalArgumentException.class, () -> new EssenceColor(0, 256, 0));
    }
}
