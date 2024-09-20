package com.frejt.piet.utils.color;

import org.apache.commons.lang3.StringUtils;

import com.frejt.piet.exception.PietColorNotFoundException;

public enum PietLight {
    
    LIGHT(0),
    NORMAL(1),
    DARK(2);

    Integer value;

    private PietLight(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return this.value;
    }

    public static PietLight getPietLight(String pietLight) throws PietColorNotFoundException {

        for(PietLight light : PietLight.values()) {
            if(light.toString().equals(pietLight)) {
                return light;
            }
        }

        throw new PietColorNotFoundException("Light " + pietLight + " is not a valid light");

    }

    @Override
    public String toString() {

        if(this.value == 0) {
            return "LIGHT";
        } else if(this.value == 2) {
            return "DARK";
        } else {
            return StringUtils.EMPTY;
        }
    }

}
