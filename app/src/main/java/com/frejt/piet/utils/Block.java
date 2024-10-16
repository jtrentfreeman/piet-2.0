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
     * TODO: I saw a cool implementation that kept a list of Codels in a list
     * When a corner was needed, the list was sorted by X and by Y, then the Codel
     * in the wanted corner could easily be found.
     * 
     * TODO: I'm not confident any of this works. Test
     * 
     * TODO: break up into smaller functions
     *  setCorner()
     *      setRightCorner
     *      setBottomCorner
     *      setLeftCorner
     *      setTopCorner
     * 
     * @param coordinate the Codel being checked for the current Block
     */
    public void setCorner(Codel coordinate) {

        int rightTopX = getRightTop().getX();
        int rightTopY = getRightTop().getY();
        int rightBottomX = getRightBottom().getX();
        int rightBottomY = getRightBottom().getY();

        // potential for new right column value
        // TODO: redundant checks, if the coordinate is greater
        // than the existing codel, we'll never check if the
        // existing codel is -1
        // if further right
        if (rightTopY == -1 || coordinate.getY() > rightTopY) {
            setRightTop(coordinate);
        }
        // if further up
        if (rightTopX == -1 || coordinate.getX() < rightTopX) {
            setRightTop(coordinate);
        }
        // if further right
        if (rightBottomY == -1 || coordinate.getY() > rightBottomY) {
            setRightBottom(coordinate);
        }
        // if further down
        if (rightBottomX == -1 || coordinate.getX() > rightBottomX) {
            setRightBottom(coordinate);
        }

        int bottomRightX = getBottomRight().getX();
        int bottomRightY = getBottomRight().getY();
        int bottomLeftX = getBottomLeft().getX();
        int bottomLeftY = getBottomLeft().getY();

        // potential for new bottom row value
        // if further down
        if (bottomRightX == -1 || coordinate.getX() > bottomRightX) {
            setBottomRight(coordinate);
        }
        // if further right
        if (bottomRightY == -1 || coordinate.getY() > bottomRightY) {
            setBottomRight(coordinate);
        }
        // if further down
        if (bottomLeftX == -1 || coordinate.getX() > bottomLeftX) {
            setBottomLeft(coordinate);
        }
        // if further left
        if (bottomLeftY == -1 || coordinate.getY() < bottomLeftY) {
            setBottomLeft(coordinate);
        }

        int leftBottomX = getLeftBottom().getX();
        int leftBottomY = getLeftBottom().getY();
        int leftTopX = getLeftTop().getX();
        int leftTopY = getLeftTop().getY();

        // potential for new left column value
        // if further left
        if (leftBottomY == -1 || coordinate.getY() < leftBottomY) {
            setLeftBottom(coordinate);
        }
        // if further down
        if (leftBottomX == -1 || coordinate.getX() > leftBottomX) {
            setLeftBottom(coordinate);
        }
        // if further left
        if (leftTopY == -1 || coordinate.getY() < leftTopY) {
            setLeftTop(coordinate);
        }
        // if further up
        if (leftTopX == -1 || coordinate.getX() < leftTopX) {
            setLeftTop(coordinate);
        }

        int topLeftX = getTopLeft().getX();
        int topLeftY = getTopLeft().getY();
        int topRightX = getTopRight().getX();
        int topRightY = getTopRight().getY();

        // potential for upper-most row
        if (topLeftX == -1 || coordinate.getX() <= topLeftX) {
            // if further up
            if (topLeftX == -1 || coordinate.getX() < topLeftX) {
                setTopLeft(coordinate);
            }
            // if further left
            if (topLeftY == -1 || coordinate.getY() < topLeftY) {
                setTopLeft(coordinate);
            }
            // if further up
            if (topRightX == -1 || coordinate.getX() < topRightX) {
                setTopRight(coordinate);
            }
            // if further right
            if (topRightY == -1 || coordinate.getY() > topRightY) {
                setTopRight(coordinate);
            }
        }
    }

}
