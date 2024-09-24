package com.frejt.piet.entity;

/**
 * Piet code takes the form of graphics made up of the recognised colours. 
 * 
 * Individual pixels of colour are significant in the language, so it is common 
 * for programs to be enlarged for viewing so that the details are easily visible. 
 * 
 * In such enlarged programs, the term "codel" is used to mean a block of colour 
 * equivalent to a single pixel of code, to avoid confusion with the actual pixels 
 * of the enlarged graphic, of which many may make up one codel.
 */
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
