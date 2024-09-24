package com.frejt.piet.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.frejt.piet.entity.Board;
import com.frejt.piet.entity.Codel;
import com.frejt.piet.utils.Block;
import com.frejt.piet.utils.color.PietColor;

public class BoardRunnerTest {

    /**
     * Asserts that, when a Board is filled with Codels, all of the same color,
     * then the size of the Codel is the same size of the board
     */
    @Test
    void findSizeCodel_BoardAllOneColor_ReturnsSizeOfBoard() {

        Board board = new Board(4, 4);

        for(int i = 0; i < board.getSizeRow(); i++) {
            for(int j = 0; j < board.getSizeCol(); j++) {
                Codel tmpCodel = new Codel(i, j);
                board.setColor(tmpCodel, PietColor.RED);
            }
        }

        BoardRunner runner = new BoardRunner(board);
        Integer actual = runner.findSizeCodel(new Block(board, new Codel(0, 0)), new Codel(0, 0));

        Integer expected = board.getSizeRow() * board.getSizeCol();
        
        assertEquals(expected, actual);

    }

    /**
     * Asserts that, when a Board is filled with Codels, but not all of the same color,
     * then only the size of the Codel in the top left corner of the Board (0, 0) 
     * is returned
     */
    @Test
    void findSizeCodel_BoardMixedColors_ReturnsSizeOfSingleCodel() {

        Board board = new Board(4, 4);

        /**
         * Creates a board that looks like the following, where the first row
         * and first column are filled with the LIGHT_RED color:
         * 
         * #ffc0c0 #ffc0c0 #ffc0c0 #ffc0c0 
         * #ffc0c0 #ff0000 #c00000 #ffffc0 
         * #ffc0c0 #c00000 #ffff00 #c0ffc0 
         * #ffc0c0 #ffffc0 #c0ffc0 #c0ffff 
         */
        for(int i = 0; i < board.getSizeRow(); i++) {
            for(int j = 0; j < board.getSizeCol(); j++) {
                Codel tmpCodel = new Codel(i, j);
                board.setColor(tmpCodel, PietColor.values()[i*j]);
            }
        }

        BoardRunner runner = new BoardRunner(board);
        Integer actual = runner.findSizeCodel(new Block(board, new Codel(0, 0)), new Codel(0, 0));

        Integer expected = board.getSizeRow() + board.getSizeCol()-1;

        assertEquals(expected, actual);

    }

    /**
     * Asserts that, when a Board is filled with Codels, all of a different color,
     * then only the size of the Codel in the top left corner of the Board (0, 0)
     * is returned
     */
    @Test
    void findSizeCodel_AllUniqueColors_ReturnsSizeOfSingleCodel() {

        Board board = new Board(4, 4);

        /**
         * Creates a board where each Codel is a different color
         */
        for(int i = 0; i < board.getSizeRow(); i++) {
            for(int j = 0; j < board.getSizeCol(); j++) {
                Codel tmpCodel = new Codel(i, j);
                board.setColor(tmpCodel, PietColor.values()[i*board.getSizeRow()+j]);
            }
        }

        BoardRunner runner = new BoardRunner(board);
        Integer actual = runner.findSizeCodel(new Block(board, new Codel(0, 0)), new Codel(0, 0));

        Integer expected = 1;

        assertEquals(expected, actual);

    }

    /**
     * Asserts that, when execution hits a {@link PietColor#WHITE} {@link Codel} 
     * on a "flat" board, the interpreter slides in the direction of the DP until
     * a non-white color block is hit.
     * 
     * Example board: 
     * _ _ _ R
     * 
     * _: White
     * R: Red
     */
    @Test
    void getNextCodelWhite_OnlyRightSideHasColor_ReturnsRightSide() {

        Board board = new Board(1, 4);

        for(int i = 0; i < board.getSizeRow(); i++) {
            for(int j = 0; j < board.getSizeCol(); j++) {
                Codel tempCodel = new Codel(i, j);
                board.setColor(tempCodel, PietColor.WHITE);
            }
        }

        Codel topLeftCodel = new Codel(0, 0);
        Codel topRightCodel = new Codel(0, board.getSizeCol()-1);
        board.setColor(topRightCodel, PietColor.RED);

        BoardRunner runner = new BoardRunner(board);
        Codel actual = runner.getNextCodelWhite(topLeftCodel, topLeftCodel, 0);

        Codel expected = new Codel(topRightCodel);

        assertEquals(expected, actual);

    }

    /**
     * Asserts that, when execution hits a {@link PietColor#WHITE} {@link Codel},
     * the interpreter slides in the direction of the DP until a non-white color 
     * block is hit. 
     * Then, if the interpreter hits a {@link PietColor#BLACK} Codel, or hits an 
     * edge, the DP and CC are toggled, and execution continues again.
     * 
     * Example board:
     * 
     * _ _ _ _
     * _ _ _ _
     * _ _ _ _
     * _ _ _ R
     * 
     * _: White
     * R: Red
     */
    @Test
    void getNextCodelWhite_OnlyBottomRightHasColor_ReturnsBottomRight() {

        Board board = new Board(4, 4);

        for(int i = 0; i < board.getSizeRow(); i++) {
            for(int j = 0; j < board.getSizeCol(); j++) {
                Codel tempCodel = new Codel(i, j);
                board.setColor(tempCodel, PietColor.WHITE);
            }
        }

        Codel topLeftCodel = new Codel(0, 0);
        Codel bottomRightCodel = new Codel(board.getSizeRow()-1, board.getSizeCol()-1);
        board.setColor(bottomRightCodel, PietColor.RED);

        BoardRunner runner = new BoardRunner(board);
        Codel actual = runner.getNextCodelWhite(topLeftCodel, topLeftCodel, 0);

        Codel expected = new Codel(bottomRightCodel);

        assertEquals(expected, actual);

    }

    /**
     * Asserts that, when execution hits a {@link PietColor#WHITE} {@link Codel},
     * the interpreter slides in the direction of the DP until a non-white color 
     * block is hit. 
     * Then, if the interpreter hits a {@link PietColor#BLACK} Codel, or hits an 
     * edge, the DP and CC are toggled, and execution continues again.
     * 
     * Example board:
     * 
     * _ _ _ _
     * _ _ _ _
     * _ _ _ _
     * R _ _ _
     * 
     * _: White
     * R: Red
     */
    @Test
    void getNextCodelWhite_OnlyBottomLeftHasColor_ReturnsBottomLeft() {

        Board board = new Board(4, 4);

        for(int i = 0; i < board.getSizeRow(); i++) {
            for(int j = 0; j < board.getSizeCol(); j++) {
                Codel tempCodel = new Codel(i, j);
                board.setColor(tempCodel, PietColor.WHITE);
            }
        }

        Codel topLeftCodel = new Codel(0, 0);
        Codel bottomLeftCodel = new Codel(board.getSizeRow()-1, 0);
        board.setColor(bottomLeftCodel, PietColor.RED);

        BoardRunner runner = new BoardRunner(board);
        Codel actual = runner.getNextCodelWhite(topLeftCodel, topLeftCodel, 0);

        Codel expected = new Codel(bottomLeftCodel);

        assertEquals(expected, actual);

    }

    /**
        Asserts that, when a White {@link}
    
        _ _ _ _ _ _ _
        _ _ B B B _ _
        B _ B _ B _ _
        _ _ _ _ B _ _
        _ _ B _ B _ _
        _ _ B B B _ _
        _ _ _ _ _ _ _ 
     
      
     */
    // @Test
    // void getNextCodelWhite_TrapDoor_ProgramEnds() {

    //     Board board = new Board(7, 7);

    //     for(int i = 0; i < board.getSizeRow(); i++) {
    //         for(int j = 0; j < board.getSizeCol(); j++) {
    //             Codel tempCodel = new Codel(i, j);
    //             board.setColor(tempCodel, PietColor.WHITE);
    //         }
    //     }

    //     // building a trap door
    //     int[][] trapdoors = new int[][] {
    //                 {1, 2}, {1, 3}, {1, 4},
    //         {2, 0}, {2, 2},         {2, 4},
    //                                 {3, 4},
    //                 {4, 2},         {4, 4},
    //                 {5, 2}, {5, 3}, {5, 4}
    //     };

    //     for(int i = 0; i < trapdoors.length; i++) {
    //         Codel trapdoor = new Codel(trapdoors[i][0], trapdoors[i][1]);
    //         board.setColor(trapdoor, PietColor.BLACK);
    //     }

    //     Codel topLeftCodel = new Codel(0, 0);
    //     Codel huh = BoardRunner.getNextCodelWhite(board, topLeftCodel, topLeftCodel, 0);

    //     System.out.println(huh);
    //     assertEquals(true, Program.getInstance().getEnd());
    // }
    
}