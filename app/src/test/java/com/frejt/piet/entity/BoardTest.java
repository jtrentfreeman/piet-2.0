package com.frejt.piet.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class BoardTest {

    /**
     * Asserts that, when part of a board has been "visited", it can be reset using the 
     * {@link Board#setVisitedAll(Boolean)} function
     */
    @Test
    void setVisitedAll_SetToFalse_NoVisitedColors() {

        Board board = new Board(4, 4);

        Codel topLeftCorner = new Codel(0, 0);
        Codel topRightCorner = new Codel(0, board.getSizeCol()-1);
        Codel bottomLeftCorner = new Codel(board.getSizeRow()-1, 0);
        Codel bottomRightCorner = new Codel(board.getSizeRow()-1, board.getSizeCol()-1);

        board.setVisited(topLeftCorner, true);
        board.setVisited(topRightCorner, true);
        board.setVisited(bottomLeftCorner, true);
        board.setVisited(bottomRightCorner, true);

        board.setVisitedAll(false);

        for(int i = 0; i < board.getSizeRow(); i++) {
            for(int j = 0; j < board.getSizeCol(); j++) {
                Codel currCodel = new Codel(i, j);
                assertEquals(false, board.getVisited(currCodel));
            }
        }

    }

    /**
     * Asserts that, when a {@link Codel} has the coordinates within the bounds of a 
     * {@link Board}, the Board detects that as a valid Codel
     */
    @Test
    void isInBounds_IsInBounds_ReturnsTrue() {

        Board board = new Board(4, 6);

        Codel codel_0 = new Codel(0, 0);
        Codel codel_1 = new Codel(1, 2);
        Codel codel_2 = new Codel(3, 5);

        assertEquals(true, board.isInBounds(codel_0));
        assertEquals(true, board.isInBounds(codel_1));
        assertEquals(true, board.isInBounds(codel_2));

    }

    /**
     * Asserts that, when a {@link Codel} has coordinates not within the bounds of a
     * {@link Board}, the Board detect that as an invalid Codel
     */
    @Test
    void isInBounds_NotInBounds_ReturnsFalse() {

        Board board = new Board(4, 6);

        Codel codel_0 = new Codel(-1, 0);
        Codel codel_1 = new Codel(4, 6);
        Codel codel_2 = new Codel(3, -2);

        assertEquals(false, board.isInBounds(codel_0));
        assertEquals(false, board.isInBounds(codel_1));
        assertEquals(false, board.isInBounds(codel_2));

    }
    
}
