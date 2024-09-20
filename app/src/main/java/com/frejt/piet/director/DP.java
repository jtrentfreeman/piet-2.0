package com.frejt.piet.director;

/**
 * The Interpreter maintains a Direction Pointer (DP), initially pointing to the
 * right.
 * The DP may point either right, left, down, or up.
 * 
 * @see Director
 */
public enum DP {
    DOWN(1, 0),
    LEFT(0, -1),
    RIGHT(0, 1),
    UP(-1, 0);

    private Integer x;
    private Integer y;

    DP(Integer x, Integer y) {
        this.x = x;
        this.y = y;
    }

    public Integer getX() {
        return this.x;
    }

    public Integer getY() {
        return this.y;
    }
}
