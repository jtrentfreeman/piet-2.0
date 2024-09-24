package com.frejt.piet.utils.color;

import com.frejt.piet.exception.PietColorNotFoundException;

public enum PietColor {

    LIGHT_RED(PietLight.LIGHT, PietHue.RED, 0xFF, 0xC0, 0xC0),
    RED(PietLight.NORMAL, PietHue.RED, 0xFF, 0x00, 0x00),
    DARK_RED(PietLight.DARK, PietHue.RED, 0xC0, 0x00, 0x00),

    LIGHT_YELLOW(PietLight.LIGHT, PietHue.YELLOW, 0xFF, 0xFF, 0xC0),
    YELLOW(PietLight.NORMAL, PietHue.YELLOW,  0xFF, 0xFF, 0x00),
    DARK_YELLOW(PietLight.DARK, PietHue.YELLOW,  0xC0, 0xC0, 0x00),

    LIGHT_GREEN(PietLight.LIGHT, PietHue.GREEN, 0xC0, 0xFF, 0xC0),
    GREEN(PietLight.NORMAL, PietHue.GREEN, 0x00, 0xFF, 0x00),
    DARK_GREEN(PietLight.DARK, PietHue.GREEN, 0x00, 0xC0, 0x00),

    LIGHT_CYAN(PietLight.LIGHT, PietHue.CYAN, 0xC0, 0xFF, 0xFF),
    CYAN(PietLight.NORMAL, PietHue.CYAN, 0x00, 0xFF, 0xFF),
    DARK_CYAN(PietLight.DARK, PietHue.CYAN, 0x00, 0xC0, 0xC0),

    LIGHT_BLUE(PietLight.LIGHT, PietHue.BLUE, 0xC0, 0xC0, 0xFF),
    BLUE(PietLight.NORMAL, PietHue.BLUE, 0x00, 0x00, 0xFF),
    DARK_BLUE(PietLight.DARK, PietHue.BLUE, 0x00, 0x00, 0xC0),

    LIGHT_MAGENTA(PietLight.LIGHT, PietHue.MAGENTA, 0xFF, 0xC0, 0xFF),
    MAGENTA(PietLight.NORMAL, PietHue.MAGENTA, 0xFF, 0x00, 0xFF),
    DARK_MAGENTA(PietLight.DARK, PietHue.MAGENTA, 0xC0, 0x00, 0xC0),
    
    BLACK(PietLight.NORMAL, PietHue.BLACK, 0x00, 0x00, 0x00),
    WHITE(PietLight.NORMAL, PietHue.WHITE, 0xFF, 0xFF, 0xFF);

    private String name;
    private Integer red;
    private Integer blue;
    private Integer green;
    private String rgb;

    private PietHue hue;
    private PietLight light;

    PietColor(PietLight light, PietHue hue, Integer red, Integer green, Integer blue) {
        this.light = light;
        this.hue = hue;
        
        this.red = red;
        this.green = green;
        this.blue = blue;

        this.name = this.light.toString() + " " + this.hue.toString();
        this.rgb = getColorFromRGB(red, green, blue);

    }

    public String getName() {
        return this.name;
    }

    public String getRBG() {
        return this.rgb;
    }

    public Integer getRed() {
        return this.red;
    }

    public Integer getBlue() {
        return this.blue;
    }

    public Integer getGreen() {
        return this.green;
    }

    public PietHue getHue() {
        return this.hue;
    }

    public PietLight getLight() {
        return this.light;
    }

    /**
     * Given a red, green, and blue value (0-255), returns the {@link Color}
     * matching those values
     * 
     * @param red   the amount of red in the color
     * @param green the amount of green in the color
     * @param blue  the amount of blue in the color
     * @return the Color represented by the provided RGB values
     * @throws ColorNotFoundException if the RGB values could not be matched to a Color
     */
    public static PietColor getColorFromValues(Integer red, Integer green, Integer blue) throws PietColorNotFoundException {
        String rgb = PietColor.getColorFromRGB(red, green, blue);

        for (PietColor c : PietColor.values()) {
            if (c.rgb.equals(rgb)) {
                return c;
            }
        }

        throw new PietColorNotFoundException("Value " + rgb + " at is not a defined color!");
    }

    /**
     * Converts RGB (0-255) values into a hex string representing it.
     * 
     * @param red   the amount of red in the color
     * @param green the amount of green in the color
     * @param blue  the amount of blue in the color
     * @return a String representing the
     */
    public static String getColorFromRGB(Integer red, Integer green, Integer blue) {
        return String.format("#%02x%02x%02x", red, green, blue);
    }
}
