package com.frejt.piet.command;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;
import java.util.UUID;

import com.frejt.piet.controller.Program;
import com.frejt.piet.controller.Programmer;
import com.frejt.piet.utils.Block;

/**
 * List of functions describing what happens when the program encounters a
 * change in color Blocks.
 * 
 * Any operation which cannot be performs (such as popping values when not
 * enough are on the stack) are simply ignored, and processing continues with
 * the next command.
 * 
 * @see CommandInterface
 * @see CommandRunner
 * @see Command
 */
public class CommandController {

    /**
     * Does nothing and returns the stack.
     * 
     * @param stack the stack to modify
     * @param older the first block to be included in the calculation
     * @param newer the last block to be included in the calculation
     * @return the stack after the command has been ran
     */
    public static Stack<Integer> nop(UUID uuid, Block older, Block newer) {

        Program program = Programmer.getProgram(uuid);
        return program.getStack();

    }

    /**
     * †Pushes the value of the colour block just exited on to the stack.
     * 
     * Note that values of colour blocks are not automatically pushed on to the
     * stack - this push operation must be explicitly carried out
     * 
     * @param stack the stack to modify
     * @param older the first block to be included in the calculation
     * @param newer the last block to be included in the calculation
     * @return the stack after the command has been ran
     */
    public static Stack<Integer> push(UUID uuid, Block older, Block newer) {

        Program program = Programmer.getProgram(uuid);
        Stack<Integer> stack = program.getStack();
        
        Integer in = older.getSize();

        stack.push(in);
        return stack;
    
    }

    /**
     * †Pops the top value off the stack and discards it.
     * 
     * @param stack the stack to modify
     * @param older the first block to be included in the calculation
     * @param newer the last block to be included in the calculation
     * @return the stack after the command has been ran
     */
    public static Stack<Integer> pop(UUID uuid, Block older, Block newer) {

        Program program = Programmer.getProgram(uuid);
        Stack<Integer> stack = program.getStack();

        if(stack.size() == 0) {
            return stack;
        }

        stack.pop();
        return stack;
    }

    /**
     * †Pops the top two values off the stack, adds them, and pushes the result back
     * on the stack
     * 
     * @param stack the stack to modify
     * @param older the first block to be included in the calculation
     * @param newer the last block to be included in the calculation
     * @return the stack after the command has been ran
     */
    public static Stack<Integer> add(UUID uuid, Block older, Block newer) {

        Program program = Programmer.getProgram(uuid);
        Stack<Integer> stack = program.getStack();
       
        if(stack.size() < 2) {
            return stack;
        }

        Integer top = stack.pop();
        Integer bottom = stack.pop();
        stack.push(bottom + top);

        return stack;
    }

    /**
     * †Pops the top two values off the stack, calculates the second top value minus
     * the top value, and pushes the result back on the stack
     * 
     * @param stack the stack to modify
     * @param older the first block to be included in the calculation
     * @param newer the last block to be included in the calculation
     * @return the stack after the command has been ran
     */
    public static Stack<Integer> subtract(UUID uuid, Block older, Block newer) {

        Program program = Programmer.getProgram(uuid);
        Stack<Integer> stack = program.getStack();
        
        if(stack.size() < 2) {
            return stack;
        }

        Integer top = stack.pop();
        Integer bottom = stack.pop();
        stack.push(bottom - top);

        return stack;
    }

    /**
     * †Pops the top two values off the stack, multiplies them, and pushes the result
     * back on the stack.
     * 
     * @param stack the stack to modify
     * @param older the first block to be included in the calculation
     * @param newer the last block to be included in the calculation
     * @return the stack after the command has been ran
     */
    public static Stack<Integer> multiply(UUID uuid, Block older, Block newer) {

        Program program = Programmer.getProgram(uuid);
        Stack<Integer> stack = program.getStack();
        
        if(stack.size() < 2) {
            return stack;
        }

        Integer top = stack.pop();
        Integer bottom = stack.pop();

        stack.push(bottom * top);

        return stack;
    }

    /**
     * †Pops the top two values of fthe stack, calculates the integer division of the
     * second top value by the top value, and pushes the result back on the stack.
     * 
     * If a divide by zero occurs, it is handled as an implementation-dependent
     * error, though simply ignoring the command is recommended
     * 
     * @param stack the stack to modify
     * @param older the first block to be included in the calculation
     * @param newer the last block to be included in the calculation
     * @return the stack after the command has been ran
     */
    public static Stack<Integer> divide(UUID uuid, Block older, Block newer) {

        Program program = Programmer.getProgram(uuid);
        Stack<Integer> stack = program.getStack();
        
        if(stack.size() < 2 || stack.peek() == 0) {
            return stack;
        }

        Integer top = stack.pop();
        Integer bottom = stack.pop();

        stack.push(bottom / top);

        return stack;
    }

    /**
     * †Pops the top two values off the stack, calculates the second top value modulo
     * the top value, and pushes the result back on the stack.
     * 
     * The result has the same sign as the divisor (the top value).
     * 
     * If the top value is zero, there is a divide by zero error, which is handled
     * as an implementation-dependent error, though simply ignoring the commmand is
     * recommended.
     * 
     * @param stack the stack to modify
     * @param older the first block to be included in the calculation
     * @param newer the last block to be included in the calculation
     * @return the stack after the command has been ran
     */
    public static Stack<Integer> modulus(UUID uuid, Block older, Block newer) {

        Program program = Programmer.getProgram(uuid);
        Stack<Integer> stack = program.getStack();
        
        if(stack.size() < 2 || stack.peek() == 0) {
            return stack;
        }

        Integer top = stack.pop();
        Integer bottom = stack.pop();

        Integer mod = correctMod(bottom, top);
        stack.push(mod);

        return stack;
    }

    /**
     * †Replaces the top value of the stack with 0 if it is non-zero, and 1 if it is
     * 0.
     * 
     * @param stack the stack to modify
     * @param older the first block to be included in the calculation
     * @param newer the last block to be included in the calculation
     * @return the stack after the command has been ran
     */
    public static Stack<Integer> not(UUID uuid, Block older, Block newer) {

        Program program = Programmer.getProgram(uuid);
        Stack<Integer> stack = program.getStack();
        
        if(stack.size() == 0) {
            return stack;
        }

        Integer top = stack.pop();
        if (top == 0) {
            stack.push(1);
        } else {
            stack.push(0);
        }

        return stack;
    }

    /**
     * †Pops the top two values off the stack, and pushes 1 on to the stack if the
     * second top value is greater than the top value, and pushes 0 if it is not
     * greater.
     * 
     * @param stack the stack to modify
     * @param older the first block to be included in the calculation
     * @param newer the last block to be included in the calculation
     * @return the stack after the command has been ran
     */
    public static Stack<Integer> greater(UUID uuid, Block older, Block newer) {

        Program program = Programmer.getProgram(uuid);
        Stack<Integer> stack = program.getStack();
        
        if(stack.size() < 2) {
            return stack;
        }

        Integer top = stack.pop();
        Integer bottom = stack.pop();

        if (bottom > top) {
            stack.push(1);
        } else {
            stack.push(0);
        }

        return stack;
    }

    /**
     * †Pops the top value off the stack and rotates the DP clockwise that many steps
     * (anticlockwise if negative).
     * 
     * @param stack the stack to modify
     * @param older the first block to be included in the calculation
     * @param newer the last block to be included in the calculation
     * @return the stack after the command has been ran
     */
    public static Stack<Integer> dp(UUID uuid, Block older, Block newer) {

        Program program = Programmer.getProgram(uuid);
        Stack<Integer> stack = program.getStack();
        
        if(stack.size() == 0) {
            return stack;
        }

        Integer top = stack.pop();
        program.getDirector().rotateDP(top);

        return stack;
    }

    /**
     * †Pops the top value off the stack and toggles the CC that many times (the
     * absolute value of that many times if negative).
     * 
     * @param stack the stack to modify
     * @param older the first block to be included in the calculation
     * @param newer the last block to be included in the calculation
     * @return the stack after the command has been ran
     */
    public static Stack<Integer> cc(UUID uuid, Block older, Block newer) {

        Program program = Programmer.getProgram(uuid);
        Stack<Integer> stack = program.getStack();
        
        if(stack.size() == 0) {
            return stack;
        }

        Integer top = stack.pop();
        program.getDirector().rotateCC(top);

        return stack;
    }

    /**
     * †Pushes a copy of the top value on the stack on to the stack.
     * 
     * @param stack the stack to modify
     * @param older the first block to be included in the calculation
     * @param newer the last block to be included in the calculation
     * @return the stack after the command has been ran
     */
    public static Stack<Integer> duplicate(UUID uuid, Block older, Block newer) {

        Program program = Programmer.getProgram(uuid);
        Stack<Integer> stack = program.getStack();
        
        if(stack.size() == 0) {
            return stack;
        }

        Integer top = stack.peek();
        stack.push(top);

        return stack;
    }

    /**
     * †Pops the top two values off the stack and "rolls" the remaining stack entries
     * to a depth equal to the second value popped, by a number of rolls equal to
     * the first value popped.
     * 
     * A single roll to depth n is defined as burying the top value on the stack n
     * deep and bringing all values above it up by 1 place.
     * 
     * TODO: A negative number of rolls rolls in the opposite direction. A negative depth
     * is an error and the command is ignored.
     * 
     * If a roll is greater than an implementation-dependent maximum stack depth, it
     * is handled as an implementation-dependent error,
     * though simply ignoring the command is recommended.
     * 
     * @param stack the stack to modify
     * @param older the first block to be included in the calculation
     * @param newer the last block to be included in the calculation
     * @return the stack after the command has been ran
     */
    public static Stack<Integer> roll(UUID uuid, Block older, Block newer) {

        Program program = Programmer.getProgram(uuid);
        Stack<Integer> stack = program.getStack();
        
        if(stack.size() < 2) {
            return stack;
        }

        Integer top = stack.pop();
        Integer bottom = stack.pop();

        // TODO: Not sure if this works if I pop things and leave them popped
        if (top == 0) {
            return stack;
        }

        Boolean reverseFlag = false;
        Integer size = stack.size();

        if (top < 0) {
            reverseFlag = true;
            List<Integer> vals = new ArrayList<>();

            for (int i = 0; i < size; i++) {
                vals.add(stack.pop());
            }
            for (int i = 0; i < size; i++) {
                stack.push(vals.get(i));
            }

            top = -top;
        }

        while (top > 0) {
            Integer rollNum = stack.pop();
            List<Integer> vals = new ArrayList<>();
            for (int i = 0; i < bottom - 1; i++) {
                vals.add(stack.pop());
            }
            stack.push(rollNum);

            for (int i = bottom - 2; i >= 0; i--) {
                stack.push(vals.get(i));
            }
            top--;
        }

        if (reverseFlag) {
            List<Integer> vals = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                vals.add(stack.pop());
            }
            for (int i = 0; i < size; i++) {
                stack.push(vals.get(i));
            }

            reverseFlag = false;
        }

        return stack;
    }

    /**
     * †Reads a value from STDIN as either a number or character, depending on the
     * particular incarnation of this command and pushes it on to the stack.
     * 
     * If no input is waiting on STDIN, this is an error and the command is ignored.
     * 
     * TODO: If an integer read does not receive an integer value, this is an error 
     * and the command is ignored.
     * 
     * @param stack the stack to modify
     * @param older the first block to be included in the calculation
     * @param newer the last block to be included in the calculation
     * @return the stack after the command has been ran
     */
    public static Stack<Integer> inNum(UUID uuid, Block older, Block newer) {

        Program program = Programmer.getProgram(uuid);
        Stack<Integer> stack = program.getStack();
        
        try(Scanner s = new Scanner(System.in)) {
            Integer in = s.nextInt();
            stack.push(in);    
        }

        return stack;
    }

    /**
     * †Reads a value from STDIN as either a number or character, depending on the
     * particular incarnation of this command and pushes it on to the stack.
     * 
     * If no input is waiting on STDIN, this is an error and the command is ignored.
     * 
     * If an integer read does not receive an integer value, this is an error and
     * the command is ignored.
     * 
     * @param stack the stack to modify
     * @param older the first block to be included in the calculation
     * @param newer the last block to be included in the calculation
     * @return the stack after the command has been ran
     */
    public static Stack<Integer> inChar(UUID uuid, Block older, Block newer) {

        Program program = Programmer.getProgram(uuid);
        Stack<Integer> stack = program.getStack();
        
        try(Scanner s = new Scanner(System.in)) {
            char in = s.next().charAt(0);
            int i = in;

            stack.push(i);
        }

        return stack;
    }

    /**
     * †Pops the top value off the stack and prints it to STDOUT as either a number
     * or character, depending on the particular incarnation of this command.
     * 
     * @param stack the stack to modify
     * @param older the first block to be included in the calculation
     * @param newer the last block to be included in the calculation
     * @return the stack after the command has been ran
     */
    public static Stack<Integer> outNum(UUID uuid, Block older, Block newer) {

        Program program = Programmer.getProgram(uuid);
        Stack<Integer> stack = program.getStack();
        
        if(stack.size() == 0) {
            return stack;
        }

        Integer k = stack.pop();
        program.getOutputBuilder().append(k);

        return stack;
    }

    /**
     * †Pops the top value off the stack and prints it to STDOUT as either a number
     * or character, depending on the particular incarnation of this command.
     * 
     * @param stack the stack to modify
     * @param older the first block to be included in the calculation
     * @param newer the last block to be included in the calculation
     * @return the stack after the command has been ran
     */
    public static Stack<Integer> outChar(UUID uuid, Block older, Block newer) {
        
        Program program = Programmer.getProgram(uuid);
        Stack<Integer> stack = program.getStack();
        
        if(stack.size() == 0) {
            return stack;
        }

        int l = stack.pop();
        char m = (char) l;

        program.getOutputBuilder().append(m);

        return stack;
    }

    /**
     * †Note on the mod command: In the original specification of Piet the result of
     * a modulo operation with a negative dividend (the second top value popped off
     * the stack) was not explicitly defined.
     * 
     * I assumed that everyone would assume that the result of (p mod q) would
     * always be equal to ((p + Nq) mod q) for any integer N.
     * 
     * So:
     * - 5 mod 3 = 2
     * - 2 mod 3 = 2
     * - -1 mod 3 = 2
     * - -4 mod 3 = 2
     * 
     * The mod command is thus identical to floored division in Wikipedia's page on 
     * the modulus operation.
     * 
     * @param dividend the number being "divided" in order to get the remainder
     * @param divisor  the number that is dividing
     * @return the remainder after being modded by the appropriate standard
     */
    public static Integer correctMod(int dividend, int divisor) {
        while (dividend < 0)
            dividend += divisor;

        return dividend % divisor;
    }

}
