package com.frejt.piet.command;

import java.util.Stack;

import org.apache.logging.log4j.LogManager;

import com.frejt.piet.controller.Program;
import com.frejt.piet.exception.PietCommandNotFoundException;
import com.frejt.piet.utils.Block;
import com.frejt.piet.utils.BlockSet;
import com.frejt.piet.utils.color.PietHue;
import com.frejt.piet.utils.color.PietLight;

import org.apache.logging.log4j.Logger;

public class CommandRunner {
    
    /**
     * Piet uses a stack for storage of all data values. 
     * 
     * Data values exist only as integers, though they may be read in 
     * or printed as Unicode character values with appropriate commands.
     * 
     * The stack is notionally infinitely deep, but implementations may 
     * elect to provide a finite maximum stack size. 
     * 
     * If a finite stack overflows, it should be treated as a runtime 
     * error, and handling this will be implementation dependent.
     * 
     * TODO? Make this its own class
     */
    private Stack<Integer> stack;

    /**
     * The command that will be ran
     */
    private Command command;

    private Block older;

    private Block newer;

    private static Program program = Program.getInstance();

    private static final Logger log = LogManager.getLogger(CommandRunner.class);

    public CommandRunner(Stack<Integer> stack, BlockSet blocks) {
        this.stack = stack;
        this.older = blocks.getFirst();
        this.newer = blocks.getLast();
    }

    public Stack<Integer> run() throws PietCommandNotFoundException {

        this.command = findCommand();

        return runCommand();

    }

    /**
     * Using the difference between the two latest Blocks, finds the Command that
     * should be ran next.
     * 
     * @return the next Command to be ran.
     */
    public Command findCommand() throws PietCommandNotFoundException {

        PietHue olderHue = older.getColor().getHue();
        PietLight olderLight = older.getColor().getLight();

        PietHue newerHue = newer.getColor().getHue();
        PietLight newerLight = newer.getColor().getLight();

        Integer hueChange = newerHue.getValue() - olderHue.getValue();
        Integer lightChange = newerLight.getValue() - olderLight.getValue();

        while (hueChange < 0) {
            hueChange += 6;
        }
        while (lightChange < 0) {
            lightChange += 3;
        }

        Command command = Command.getCommand(hueChange, lightChange);
        return command;

    }

    /**
     * Performs this runner's {@link #command} and adds it to the list of commands.
     * 
     * @return the program's stack after the command has been ran
     */
    public Stack<Integer> runCommand() {

        program.addToCommandList(command);
        stack = command.calculate(stack, older, newer);
        log.debug("{} \t {}. {} \t\t {}", String.format("%14s", newer.getCoords()),
                String.format("%3s", program.getCommandList().size()), String.format("%10s", command.toString()),
                stack.toString());

        return stack;

    }

}
