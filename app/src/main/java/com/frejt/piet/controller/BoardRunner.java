package com.frejt.piet.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.frejt.piet.command.CommandRunner;
import com.frejt.piet.director.CC;
import com.frejt.piet.director.DP;
import com.frejt.piet.director.Director;
import com.frejt.piet.entity.Board;
import com.frejt.piet.entity.Codel;
import com.frejt.piet.exception.PietCommandNotFoundException;
import com.frejt.piet.exception.PietExecutionException;
import com.frejt.piet.utils.Block;
import com.frejt.piet.utils.BlockSet;
import com.frejt.piet.utils.color.PietColor;

public class BoardRunner {

    private static final Logger log = LogManager.getLogger(BoardRunner.class);

    private static Program program = Program.getInstance();
    private static Director director = program.getDirector();
    
    /**
     * Given a {@link Board}, move through the board in order and perform the
     * commands.
     *
     * @param board the Board, holding a grid of {@link Codel}s to be traversed
     */
    public static void runBoard(Board board) throws PietExecutionException {

        BlockSet blocks = prepareForRun(board);

        // the program will end on it's own (hypothetically)
        while(!program.getEnd()) {

            blocks.rotateBlocks();

            // get the coords of the next Codel
            Codel nextCodel = getNextCodel(board, blocks.getFirst(), 0);

            // initiate the newest Codel
            blocks.addBlock(new Block(board, nextCodel));
            int size = findSizeCodel(board, blocks.getLast(), nextCodel);
            board.setVisitedAll(false);

            blocks.getLast().setSize(size);

            // White codels are a special case
            if (blocks.getLast().getColor().equals(PietColor.WHITE)) {
                Codel nextNonWhite = getNextCodelWhite(board, nextCodel, blocks.getFirst().getRightTop(), 0);
                blocks.set(0, new Block(board, nextNonWhite));
                blocks.getFirst().setSize(findSizeCodel(board, blocks.getFirst(), nextNonWhite));
                blocks.remove(1);

                board.setVisitedAll(false);
            } else {

                try {
                    CommandRunner runner = new CommandRunner(program.getStack(), blocks.getFirst(), blocks.getLast());
                    runner.run();
                } catch (PietCommandNotFoundException e) {
                    log.error("Ran into an error during execution: " + e.getMessage());
                    program.end();
                }

                // shift the new Codel to the old one's spot
                blocks.set(0, blocks.getLast());
            }
        }

        program.end();

    }

    /**
     * Prepares to run the board by finding the initial block in the program.
     * 
     * @param board the board being ran during execution
     * @return a list holding the first {@link Block} of the program.
     */
    public static BlockSet prepareForRun(Board board) {

        BlockSet blockSet;

        // The Piet language interpreter begins executing a program in the colour block
        // which includes the upper left codel of the program.
        Codel first = new Codel(0, 0);

        Block initBlock = new Block(board, first);
        initBlock.setSize(findSizeCodel(board, initBlock, first));

        board.setVisitedAll(false);

        blockSet = new BlockSet(initBlock);

        return blockSet;

    }

    /**
     * Finds the size of a Block of Codels.
     * 
     * @param board the Board being used during the traversal of the program
     * @param block the Block the size is being found for
     * @param coord the Codel in the block to stat execution from
     * @return the size of the Block
     */
    public static Integer findSizeCodel(Board board, Block block, Codel coord) {
        // codel is uninitiated, so I need to set its corners
        if (block.getRightTop().getX() == -1) {
            log.debug("Setting init corners");
            // setCorners(block, coord);
            block.setCorner(coord);
        }

        // Include the color current coordinate
        Integer count = 1;
        board.setVisited(coord, true);

        // 4 directions the program will search in for more of the same color
        Codel newCoordinate;

        for (int i = 0; i < 4; i++) {
            int x = DP.values()[i].getX();
            int y = DP.values()[i].getY();

            newCoordinate = new Codel(coord.getX() + x, coord.getY() + y);

            // Not valid if not in bounds or previously visited
            if(!board.isInBounds(newCoordinate) || board.getVisited(newCoordinate)) {
                continue;
            }

            if (board.getColor(newCoordinate) == block.getColor()) {
                block.setCorner(newCoordinate);
                count += findSizeCodel(board, block, newCoordinate);
            }
        }

        return count;
    }
    
    /**
     * Based on the Director's DP and CC values, finds the next codel from the
     * passed in Block's corner Codels.
     * 
     * @param block the current block
     * @return the next Codel to be part of the execution
     */
    public static Codel getNextCodel(Block block) throws PietExecutionException {

        DP dp = director.getDP();
        CC cc = director.getCC();

        Codel currEdge;


        switch (dp) {

            case RIGHT:
                switch (cc) {
                    case LEFT:
                        currEdge = new Codel(block.getRightTop());
                        break;
                    case RIGHT:
                        currEdge = new Codel(block.getRightBottom());
                        break;
                    default:
                        throw new PietExecutionException("CC was found in an invalid state: " + cc);
                }
                break;

            case DOWN:
                switch (cc) {
                    case LEFT:
                        currEdge = new Codel(block.getBottomRight());
                        break;
                    case RIGHT:
                        currEdge = new Codel(block.getBottomLeft());
                        break;
                    default:
                        throw new PietExecutionException("CC was found in an invalid state: " + cc);
                }
                break;

            case LEFT:
                switch (cc) {
                    case LEFT:
                        currEdge = new Codel(block.getLeftBottom());
                        break;
                    case RIGHT:
                        currEdge = new Codel(block.getLeftBottom());
                        break;
                    default:
                        throw new PietExecutionException("CC was found in an invalid state: " + cc);
                }
                break;

            case UP:
                switch (cc) {
                    case LEFT:
                        currEdge = new Codel(block.getTopLeft());
                        break;
                    case RIGHT:
                        currEdge = new Codel(block.getTopRight());
                        break;
                    default:
                        throw new PietExecutionException("CC was found in an invalid state: " + cc);
                }
                break;

            default:
                throw new PietExecutionException("DP was found in an invalid state: " + dp);

        }

        Codel next = new Codel(currEdge.getX() + dp.getX(), currEdge.getY() + dp.getY());

        return next;
    }

    // we're getting the newest codel, from the old one, c
    public static Codel getNextCodel(Board board, Block block, int attempt) throws PietExecutionException {

        // We are not able to find another valid Codel in any of the current
        // blocks Codels.
        if (attempt > 8) {
            program.end();
        }

        Codel next = new Codel(getNextCodel(block));
    
        // we hit an edge, rotate and try to get another block
        if(!board.isInBounds(next) || board.getColor(next).equals(PietColor.BLACK)) {
            director.rotateByAttempt(attempt);

            return getNextCodel(board, block, attempt + 1);
        }

        return next;
    }

    /**
     * White colour blocks are "free" zones through which the interpreter passes
     * unhindered.
     * 
     * If it moves from a colour block into a white area, the interpreter "slides"
     * through the white codels in the direction of the DP until it reaches a
     * non-white colour block.
     * 
     * If the interpreter slides into a black block or an edge, if is considered
     * restricted, otherwise it moves into the colour block so encountered.
     * Sliding across white blocks into a new colour does not cause a command to be
     * executed.
     * 
     * In this way, white blocks can be used to change the current colour without
     * executing a command, which is very useful for coding loops.
     * 
     * Sliding across white blocks takes the interpreter in a straight line until it
     * hits a coloured pixel or edge.
     * 
     * It does not use the procedure described above for determining where the
     * interpreter emerges from non-white coloured blocks.
     * 
     * @param board         the board being traversed
     * @param coordinate    the Codel to start traversal from
     * @param previousColor the most recent non-white/non-block Codel to have been
     *                      ran
     * @param attempt       how many attempts have been made to escape the current
     *                      white space
     * @return the next non-white Codel to start the program at
     */
    public static Codel getNextCodelWhite(Board board, Codel coordinate, Codel previousColor, int attempt) {

        // We are not able to find another valid Codel in any of the current
        // blocks Codels.
        if (attempt > 8) {
            program.end();
        }

        DP dp = director.getDP();

        int nextRow = coordinate.getX();
        int nextCol = coordinate.getY();
        Codel next = new Codel(nextRow, nextCol);
        Codel potentialNext = new Codel(nextRow + dp.getX(), nextCol + dp.getY());

        // The interpreter "slides" across the white block in a straight line
        while(board.getColor(next).equals(PietColor.WHITE)) {
            potentialNext = new Codel(nextRow + dp.getX(), nextCol + dp.getY());

            // If it hits a restriction, the CC is toggled. Since this results in no
            // difference in where the interpreter is trying to go, the DP is immediately
            // stepped clockwise.
            if(!board.isInBounds(potentialNext) || board.getColor(potentialNext).equals(PietColor.BLACK)) {
                director.rotateCC(1);
                director.rotateDP(1);
                return getNextCodelWhite(board, next, previousColor, attempt + 1);
            }

            nextRow += dp.getX();
            nextCol += dp.getY();
            next = potentialNext;

        }

        return next;
    }

}
