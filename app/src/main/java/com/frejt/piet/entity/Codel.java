package com.frejt.piet.entity;

public class Codel {
    
    private Integer x;
    private Integer y;

    public Codel(Integer x, Integer y) {
        this.x = x;
        this.y = y;
    }

    public Codel(Codel other) {
        this.x = other.x;
        this.y = other.y;
    }

    public Integer getX() {
        return this.x;
    }

    public Integer getY() {
        return this.y;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Codel other = (Codel) obj;
        if (x == null) {
            if (other.x != null)
                return false;
        } else if (!x.equals(other.x))
            return false;
        if (y == null) {
            if (other.y != null)
                return false;
        } else if (!y.equals(other.y))
            return false;
        return true;
    }

    

}
