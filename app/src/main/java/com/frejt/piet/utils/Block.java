package com.frejt.piet.utils;

import com.frejt.piet.entity.Board;
import com.frejt.piet.entity.Codel;
import com.frejt.piet.utils.color.PietColor;

/**
 * †The basic unit of Piet code is the colour block. 
 * 
 * A colour block is a contiguous block of any number of codels
 * of one colour, bounded by blocks of other colours of by the 
 * edge of the program graphic.
 * 
 * Blocks of colour adjacent only diagonally are not considered contiguous.
 * 
 * A colour block may be any shape and may have "holes" of other colours inside
 * it, which are not considered part of the block.
 */
public class Block {
    
    private Integer size;
    private PietColor color;

    private Codel rightTop;
	private Codel rightBottom;
	private Codel bottomRight;
	private Codel bottomLeft;
	private Codel leftBottom;
	private Codel leftTop;
	private Codel topLeft;
	private Codel topRight;

    public Block(Board board, Codel coordinate) {
        this.color = board.getColor(coordinate);
        this.rightTop = coordinate;
		this.rightBottom = coordinate;
		this.bottomRight = coordinate;
		this.bottomLeft = coordinate;
		this.leftBottom = coordinate;
		this.leftTop = coordinate;
		this.topLeft = coordinate;
		this.topRight = coordinate;
    }

    public Integer getSize() {
		return this.size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public PietColor getColor() {
		return this.color;
	}

	public void setColor(PietColor color) {
		this.color = color;
	}

	public String getColorName() {
		return this.color.getName();
	}

	public String getHexValue() {
		return this.color.getRBG();
	}

	public Codel getRightTop() {
		return this.rightTop;
	}

	public void setRightTop(Codel coordinate) {
		this.rightTop = coordinate;
	}

	public Codel getRightBottom() {
		return this.rightBottom;
	}

	public void setRightBottom(Codel coordinate) {
		this.rightBottom = coordinate;
	}

	public Codel getBottomRight() {
		return this.bottomRight;
	}

	public void setBottomRight(Codel coordinate) {
		this.bottomRight = coordinate;
	}

	public Codel getBottomLeft() {
		return this.bottomLeft;
	}

	public void setBottomLeft(Codel coordinate) {
		this.bottomLeft = coordinate;
	}

	public Codel getLeftBottom() {
		return this.leftBottom;
	}

	public void setLeftBottom(Codel coordinate) {
		this.leftBottom = coordinate;
	}

	public Codel getLeftTop() {
		return this.leftTop;
	}

	public void setLeftTop(Codel coordinate) {
		this.leftTop = coordinate;
	}

	public Codel getTopLeft() {
		return this.topLeft;
	}

	public void setTopLeft(Codel coordinate) {
		this.topLeft = coordinate;
	}

	public Codel getTopRight() {
		return this.topRight;
	}

	public void setTopRight(Codel coordinate) {
		this.topRight = coordinate;
	}

    public String getCoords() {
		return "[x: " + topRight.getX() + ", y: " + topRight.getY() + "]";
    }

    /**
     * For each {@link DP} direction, determines whether the given {@link Codel}
     * is in the furthest direction for each {@link CC}
     * 
     * @param coordinate the Codel being checked for the current Block
     */
    public void setCorner(Codel coordinate) {

        // potential for new right column value
        if (coordinate.getY() >= getRightTop().getY() || getRightTop().getY() == -1) {
            // if further right
            if (coordinate.getY() > getRightTop().getY() || getRightTop().getY() == -1) {
                setRightTop(coordinate);
            }
            // if further up
            if (coordinate.getX() < getRightTop().getX() || getRightTop().getX() == -1) {
                setRightTop(coordinate);
            }
            // if further right
            if (coordinate.getY() > getRightBottom().getY() || getRightBottom().getY() == -1) {
                setRightBottom(coordinate);
            }
            // if further down
            if (coordinate.getX() > getRightBottom().getX() || getRightBottom().getX() == -1) {
                setRightBottom(coordinate);
            }
        }

        // potential for new bottom row value
        if (coordinate.getX() >= getBottomRight().getX() || getBottomRight().getX() == -1) {
            // if further down
            if (coordinate.getX() > getBottomRight().getX() || getBottomRight().getX() == -1) {
                setBottomRight(coordinate);
            }
            // if further right
            if (coordinate.getY() > getBottomRight().getY() || getBottomRight().getY() == -1) {
                setBottomRight(coordinate);
            }
            // if further down
            if (coordinate.getX() > getBottomLeft().getX() || getBottomLeft().getX() == -1) {
                setBottomLeft(coordinate);
            }
            // if further left
            if (coordinate.getY() < getBottomLeft().getY() || getBottomLeft().getY() == -1) {
                setBottomLeft(coordinate);
            }
        }

        // potential for new left column value
        if (coordinate.getY() <= getLeftBottom().getY() || getLeftBottom().getY() == -1) {
            // if further left
            if (coordinate.getY() < getLeftBottom().getY() || getLeftBottom().getY() == -1) {
                setLeftBottom(coordinate);
            }
            // if further down
            if (coordinate.getX() > getLeftBottom().getX() || getLeftBottom().getX() == -1) {
                setLeftBottom(coordinate);
            }
            // if further left
            if (coordinate.getY() < getLeftTop().getX() || getLeftTop().getX() == -1) {
                setLeftTop(coordinate);
            }
            // if further up
            if (coordinate.getX() < getLeftTop().getX() || getLeftTop().getX() == -1) {
                setLeftTop(coordinate);
            }
        }

        // potential for upper-most row
        if (coordinate.getX() <= getTopLeft().getX() || getTopLeft().getX() == -1) {
            // if further up
            if (coordinate.getX() < getTopLeft().getX() || getTopLeft().getX() == -1) {
                setTopLeft(coordinate);
            }
            // if further left
            if (coordinate.getY() < getTopLeft().getY() || getTopLeft().getY() == -1) {
                setTopLeft(coordinate);
            }
            // if further up
            if (coordinate.getX() < getTopRight().getX() || getTopLeft().getY() == -1) {
                setTopRight(coordinate);
            }
            // if further right
            if (coordinate.getY() > getTopRight().getY() || getTopRight().getY() == -1) {
                setTopRight(coordinate);
            }
        }
    }

	// public PietColor codelIntoString(String val) {
    //     for(PietColor c : PietColor.values()) {
    //         if(c.getRBG().equals(val)) {
    //             return c;
    //         }
    //     }

    //     return Color.WHITE;
    // }

}
