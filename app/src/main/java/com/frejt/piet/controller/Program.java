package com.frejt.piet.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.frejt.piet.command.Command;
import com.frejt.piet.director.Director;

/**
 * A class used to keep track of various global variables needed to perform
 * execution.
 */
public class Program {

    private static final Logger log = LogManager.getLogger(Program.class);

    /**
     * Helps keep track of the commands that have taken place
     */
    private List<Command> commandList;

    /**
     * The Director the program uses to keep track of Board traversal
     */
    private Director director;

    /**
     * Whether the program has finished running
     */
    private Boolean end;

    /**
     * The end result of the program
     */
    private String output;

    /**
     * The stdout printed by the program;
     */
    private StringBuilder outputBuilder;

    /**
     * †Piet uses a stack for storage of all data values. 
     * 
     * Data values exist only as integers, though they may be 
     * read in or printed as Unicode character values with 
     * appropriate commands.
     * 
     * The stack is nottionally infinitely deep, but implementations may elect to
     * provide a finite maximum stack size.
     * 
     * If a finite stack overflows, it should be treated as a runtime error, and
     * handling this will be implementation dependent.
     */
    private Stack<Integer> stack;

    public Program() {
        commandList = new ArrayList<>();
        director = new Director();
        end = false;
        outputBuilder = new StringBuilder();
        stack = new Stack<>();
    }

    public List<Command> getCommandList() {
        return commandList;
    }

    public void setCommandList(List<Command> commandList) {
        this.commandList = commandList;
    }

    public void addToCommandList(Command command) {
        this.commandList.add(command);
    }

    public Director getDirector() {
        return director;
    }

    public Boolean getEnd() {
        return end;
    }

    public void setEnd(Boolean end) {
        this.end = end;
    }

    public String getOutput() {
        return output;
    }

    public StringBuilder getOutputBuilder() {
        return outputBuilder;
    }

    public Stack<Integer> getStack() {
        return stack;
    }

    public void end() {
        setEnd(true);
        log.debug("Done");
        this.output = outputBuilder.toString();
        // log.info(output);
        // System.exit(0);
        
    }


}
