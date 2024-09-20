package com.frejt.piet.utils.color;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.frejt.piet.exception.PietColorNotFoundException;

class PietHueTest {

    /**
     * Asserts that, if a known color is requested, the respective {@link PietHue} 
     * will be returned,
     */
    @Test
    void getPietHue_ValidHue_ReturnsHueEnum() throws PietColorNotFoundException {

        PietHue expected = PietHue.RED;

        String red = "RED";
        PietHue actual = PietHue.getPietHue(red);

        assertEquals(expected, actual);

    }

    /**
     * Asserts that, When an unsupported color is requested, a 
     * {@link PietColorNotFoundException} will be thrown.
     */
    @Test
    void getPietHue_InvalidHue_ThrowsException() {
        
        String xed = "xed";

        assertThrows(PietColorNotFoundException.class, () -> PietHue.getPietHue(xed));

    }
    
}
