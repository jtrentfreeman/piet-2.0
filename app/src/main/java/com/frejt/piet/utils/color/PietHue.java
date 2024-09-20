package com.frejt.piet.utils.color;

import com.frejt.piet.exception.PietColorNotFoundException;

public enum PietHue {

    BLACK(-2),
    WHITE(-1),
    
    RED(0),
    YELLOW(1),
    GREEN(2),
    CYAN(3),
    BLUE(4),
    MAGENTA(5);

    private Integer value;

    PietHue(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return this.value;
    }

    /**
     * TODO: PietHueNotFoundException
     * 
     * @param pietHue
     * @return
     * @throws PietColorNotFoundException
     */
    public static PietHue getPietHue(String pietHue) throws PietColorNotFoundException {

        for(PietHue hue : PietHue.values()) {
            if(hue.toString().equals(pietHue)) {
                return hue;
            }
        }

        throw new PietColorNotFoundException("Hue " + pietHue + " is not a valid hue");
    }

}
