package com.frejt.piet.utils;

import java.util.ArrayList;
import java.util.List;

public class BlockSet {

    List<Block> blocks;

    public BlockSet(Block block) {
        blocks = new ArrayList<>();
        
        blocks.add(block);
        blocks.add(block);
    }

    public BlockSet(Block blockA, Block blockB) {
        blocks = new ArrayList<>();

        blocks.add(blockA);
        blocks.add(blockB);
    }

    public Block getFirst() {
        return blocks.get(0);
    }

    public Block getLast() {
        return blocks.get(1);
    }

    public void addBlock(Block newBlock) {

        if(blocks.size() > 1) {
            rotateBlocks();
        }

        blocks.add(newBlock);
    }

    public void rotateBlocks() {

        if(blocks.size() > 1) {
            blocks.set(0, blocks.get(1));
            blocks.remove(1);
        }

    }

    public int size() {
        return blocks.size();
    }

    public Block remove(int index) {
        return blocks.remove(index);
    }

    public void set(int index, Block block) {
        blocks.set(index, block);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        BlockSet other = (BlockSet) obj;
        if (blocks == null) {
            if (other.blocks != null)
                return false;
        } else if (!blocks.equals(other.blocks))
            return false;
        return true;
    }
    
}
