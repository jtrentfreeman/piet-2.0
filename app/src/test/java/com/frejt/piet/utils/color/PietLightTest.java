package com.frejt.piet.utils.color;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import com.frejt.piet.exception.PietColorNotFoundException;

class PietLightTest {

    /**
     * Asserts that, when a valid {@link PietLight} is requested, the correct enum is returned
     */
    @Test
    void getPietLight_ValidLight_ReturnsLightEnum() throws PietColorNotFoundException {

        PietLight expected = PietLight.LIGHT;
        
        String light = "LIGHT";
        PietLight actual = PietLight.getPietLight(light);

        assertEquals(expected, actual);

    }

    /**
     * Asserts that, when an invalid {@link PietLight} is requested, a {@link PietColorNotFoundException} is thrown
     */
    @Test
    void getPietLight_InvalidLight_ThrowsException() {

        String xight = "XIGHT";

        assertThrows(PietColorNotFoundException.class, () -> PietLight.getPietLight(xight));

    }

    /**
     * Asserts that, when the {@link PietLight#NORMAL} is converted to a string, an empty string is returned
     */
    @Test
    void toString_NormalLight_NoNormalInString() {

        PietLight normal = PietLight.NORMAL;

        assertEquals(StringUtils.EMPTY, normal.toString());

    }

    /**
     * Asserts that, when a non-{@link PietLight#NORMAL} enum is converted to a string, it's name is returned
     */
    @Test
    void toString_DarkLight_IncludesDarkInString() {

        PietLight dark = PietLight.DARK;
        assertEquals("DARK", dark.toString());

        PietLight light = PietLight.LIGHT;
        assertEquals("LIGHT", light.toString());

    }
    
}
