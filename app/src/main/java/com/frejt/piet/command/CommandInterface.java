package com.frejt.piet.command;

import java.util.UUID;

import com.frejt.piet.utils.Block;

public interface CommandInterface {

    /**
     * Calculates the appropriate command given the two blocks, and adds it to the stack
     * 
     * @param stack - the stack to modify 
     * @param older - the first block to be included in the calculation
     * @param newer - the last block to be included in the calculation
     * @return the stack after the command has been ran
     */
    public void calculate(UUID uuid, Block older, Block newer);
    
}
