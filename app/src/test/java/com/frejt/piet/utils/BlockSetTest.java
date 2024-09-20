package com.frejt.piet.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.frejt.piet.entity.Board;
import com.frejt.piet.entity.Codel;

public class BlockSetTest {
    
    /**
     * Asserts that, when a {@link BlockSet} already has two {@link Block}s in it,
     * and a new Block is added, then the first Block in the set is removed, and
     * the new Block is added at the end of the set
     */
    @Test
    void addBlock_HasTwoBlocks_RotatesAndAddsNewBlock() {

        Board board = new Board(2, 2);

        Block blockA = new Block(board, new Codel(0, 0));
        Block blockB = new Block(board, new Codel(0, 1));
        Block blockC = new Block(board, new Codel(1, 1));

        BlockSet actual = new BlockSet(blockA, blockB);
        actual.addBlock(blockC);

        BlockSet expected = new BlockSet(blockB, blockC);

        assertEquals(expected, actual);

    }

    /**
     * Asserts that, when a {@link BlockSet} only has one {@link Block} in it,
     * and a new Block is added, then the new Block is added at the end of the set
     */
    @Test
    void addBlock_HasOneBlock_AddsNewBlock() {

        Board board = new Board(2, 2);

        Block blockA = new Block(board, new Codel(0, 0));
        Block blockB = new Block(board, new Codel(0, 1));

        BlockSet actual = new BlockSet(blockA);
        actual.remove(1);
        actual.addBlock(blockB);

        BlockSet expected = new BlockSet(blockA, blockB);

        assertEquals(expected, actual);

    }

}
