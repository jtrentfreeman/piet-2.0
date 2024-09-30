package com.frejt.piet.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import java.util.Stack;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.frejt.piet.controller.Program;
import com.frejt.piet.controller.Programmer;
import com.frejt.piet.director.CC;
import com.frejt.piet.director.DP;
import com.frejt.piet.director.Director;
import com.frejt.piet.utils.Block;
import com.frejt.piet.utils.BlockSet;

import org.mockito.Mock;
import org.mockito.internal.util.reflection.Whitebox;

public class CommandControllerTest {

    @Mock
    private static Stack<Integer> stack;

    @Mock
    private static CommandRunner runner;

    @Mock
    private static Block older;

    @Mock
    private static Block newer;

    @Mock
    private static Program program;

    private UUID uuid;

    @BeforeEach
    void init() {

        stack = new Stack<>();

        older = mock(Block.class);
        newer = mock(Block.class);

        BlockSet blocks = new BlockSet(older, newer);

        uuid = UUID.randomUUID();
        Programmer.newProgram(uuid);

        program = mock(Program.class);
        when(program.getStack()).thenReturn(stack);

        runner = new CommandRunner(uuid, stack, blocks);

    }

    /**
     * Asserts that, when the runner has the {@link Command#NOP} command, the stack
     * remains unchanged after the execution of the command.
     */
    @Test
    void runCommand_Nop_DoesNothing() {

        Stack<Integer> stack = new Stack<>();

        Whitebox.setInternalState(runner, "command", Command.NOP);

        @SuppressWarnings("unchecked")
        Stack<Integer> expected = (Stack<Integer>) stack.clone();

        runner.runCommand();
        Stack<Integer> actual = Programmer.getProgram(uuid).getStack();

        assertEquals(expected, actual);

    }

    /**
     * Asserts that, when the runner has the {@link Command#PUSH} command, the stack
     * has the size of the older block pushed to it.
     */
    @Test
    void runCommand_Push_PushesOlderSize() {

        when(older.getSize()).thenReturn(5);

        Whitebox.setInternalState(runner, "command", Command.PUSH);

        Stack<Integer> expected = new Stack<>();
        expected.add(5);

        runner.runCommand();
        Stack<Integer> actual = Programmer.getProgram(uuid).getStack();

        assertEquals(expected, actual);

    }

    /**
     * Asserts that, when the runner has the {@link Command#POP} command, the stack
     * has a single value popped from it.
     */
    @Test
    void runCommand_Pop_PopsTopValue() {

        Stack<Integer> stack = Programmer.getProgram(uuid).getStack();
        stack.add(1);
        stack.add(2);

        Whitebox.setInternalState(runner, "command", Command.POP);
        Whitebox.setInternalState(runner, "uuid", uuid);

        when(program.getStack()).thenReturn(stack);

        Stack<Integer> expected = new Stack<>();
        expected.add(1);

        runner.runCommand();
        Stack<Integer> actual = Programmer.getProgram(uuid).getStack();

        assertEquals(expected, actual);

    }

    /**
     * Asserts that, when the runner has the {@link Command#POP} command and the 
     * stack is empty, the command is ignored and no action takes place
     */
    @Test
    void runCommand_Pop_EmptyStack_DoesNothing() {

        Whitebox.setInternalState(runner, "command", Command.POP);

        Stack<Integer> expected = new Stack<>();

        runner.runCommand();
        Stack<Integer> actual = Programmer.getProgram(uuid).getStack();

        assertEquals(expected, actual);

    }

    /**
     * Asserts that, when two Blocks, which have colors that are different by one hue
     * and one light step, but the stack does not have enough values to perform the 
     * {@link Command#ADD} command, the command is ignored and no action takes place.
     */
    @Test
    void runCommand_Add_StackHasOnlyOneValue_DoesNothing() {

        Stack<Integer> stack = Programmer.getProgram(uuid).getStack();
        stack.add(1);

        Whitebox.setInternalState(runner, "command", Command.ADD);

        Stack<Integer> expected = new Stack<>();
        expected.add(1);

        runner.runCommand();
        Stack<Integer> actual = Programmer.getProgram(uuid).getStack();

        assertEquals(expected, actual);

    }

    /**
     * Asserts that, when the runner has the {@link Command#ADD} command, the stack
     * has its top two values popped off, calculates [BOTTOM + TOP], and pushes the
     * result back onto the stack.
     */
    @Test
    void runCommand_Add_AddsTwoValues() {

        Stack<Integer> stack = Programmer.getProgram(uuid).getStack();
        stack.add(1);
        stack.add(2);

        Whitebox.setInternalState(runner, "command", Command.ADD);

        Stack<Integer> expected = new Stack<>();
        expected.add(3);

        runner.runCommand();
        Stack<Integer> actual = Programmer.getProgram(uuid).getStack();

        assertEquals(expected, actual);

    }

    /**
     * Asserts that, when the runner has the {@link Command#SUB} command, and
     * the stack doesn't have enough values to perform the operation, the
     * operation is ignored and nothing happens.
     */
    @Test
    void runCommand_Subtract_StackOnlyHasOneValue_DoesNothing() {

        Stack<Integer> stack = Programmer.getProgram(uuid).getStack();
        stack.add(1);

        Whitebox.setInternalState(runner, "command", Command.SUB);

        Stack<Integer> expected = new Stack<>();
        expected.add(1);

        runner.runCommand();
        Stack<Integer> actual = Programmer.getProgram(uuid).getStack();

        assertEquals(expected, actual);

    }

    /**
     * Asserts that, when the runner has the {@link Command#SUB} command, the stack
     * has its top two values popped off, calculates [BOTTOM - TOP], and pushes the
     * result back onto the stack.
     */
    @Test
    void runCommand_Subtract_SubtractsValue() {

        Stack<Integer> stack = Programmer.getProgram(uuid).getStack();
        stack.add(1);
        stack.add(2);

        Whitebox.setInternalState(runner, "command", Command.SUB);

        Stack<Integer> expected = new Stack<>();
        expected.add(-1);

        runner.runCommand();
        Stack<Integer> actual = Programmer.getProgram(uuid).getStack();

        assertEquals(expected, actual);

    }

    /**
     * Asserts that, when the runner has the {@link Command#MULT} command, and
     * the stack doesn't have enough values to perform the operation, the
     * operation is ignored and nothing happens.
     */
    @Test
    void runCommand_Multiply_StackOnlyHasOneValue_DoesNothing() {

        Stack<Integer> stack = Programmer.getProgram(uuid).getStack();
        stack.add(1);

        Whitebox.setInternalState(runner, "command", Command.MULT);

        Stack<Integer> expected = new Stack<>();
        expected.add(1);

        runner.runCommand();
        Stack<Integer> actual = Programmer.getProgram(uuid).getStack();

        assertEquals(expected, actual);


    }

    /**
     * Asserts that, when the runner has the {@link Command#MULT} command, the stack
     * has its top two values popped off, calculates [BOTTOM * TOP], and pushes the
     * result back onto the stack.
     */
    @Test
    void runCommand_Multiply_MultipliesValue() {

        Stack<Integer> stack = Programmer.getProgram(uuid).getStack();
        stack.add(2);
        stack.add(2);

        Whitebox.setInternalState(runner, "command", Command.MULT);

        Stack<Integer> expected = new Stack<>();
        expected.add(4);

        runner.runCommand();
        Stack<Integer> actual = Programmer.getProgram(uuid).getStack();

        assertEquals(expected, actual);

    }

    /**
     * Asserts that, when the runner has the {@link Command#MULT} command, and
     * the stack doesn't have enough values to perform the operation, the
     * operation is ignored and nothing happens.
     */
    @Test
    void runCommand_Divide_StackOnlyHasOneValue_DoesNothing() {
        
        Stack<Integer> stack = Programmer.getProgram(uuid).getStack();
        stack.add(1);

        Whitebox.setInternalState(runner, "command", Command.DIV);

        Stack<Integer> expected = new Stack<>();
        expected.add(1);

        runner.runCommand();
        Stack<Integer> actual = Programmer.getProgram(uuid).getStack();

        assertEquals(expected, actual);

    }

    /**
     * Asserts that, when the runner has the {@link Command#DIV} command, the stack
     * has its top two values popped off, calculates [BOTTOM / TOP], and pushes the
     * result back onto the stack.
     */
    @Test
    void runCommand_Divide_DividesValues() {

        Stack<Integer> stack = Programmer.getProgram(uuid).getStack();
        stack.add(4);
        stack.add(2);

        Whitebox.setInternalState(runner, "command", Command.DIV);

        Stack<Integer> expected = new Stack<>();
        expected.add(2);

        runner.runCommand();
        Stack<Integer> actual = Programmer.getProgram(uuid).getStack();

        assertEquals(expected, actual);

    }

    /**
     * Asserts that, when the runner has the {@link Command#MOD} command, and
     * the stack doesn't have enough values to perform the operation, the
     * operation is ignored and nothing happens.
     */
    @Test
    void runCommand_Modulus_StackOnlyHasOneValue_DoesNothing() {

        Stack<Integer> stack = Programmer.getProgram(uuid).getStack();
        stack.add(1);

        Whitebox.setInternalState(runner, "command", Command.MOD);

        Stack<Integer> expected = new Stack<>();
        expected.add(1);

        runner.runCommand();
        Stack<Integer> actual = Programmer.getProgram(uuid).getStack();

        assertEquals(expected, actual);

    }

    /**
     * Asserts that, when the runner has the {@link Command#MOD} command, the stack
     * has its top two values popped off, calculates [BOTTOM % TOP], and pushes the
     * result back onto the stack.
     */
    @Test
    void runCommand_Modulus_ModsValues() {

        Stack<Integer> stack = Programmer.getProgram(uuid).getStack();
        stack.add(7);
        stack.add(3);

        Whitebox.setInternalState(runner, "command", Command.MOD);

        Stack<Integer> expected = new Stack<>();
        expected.add(1);

        runner.runCommand();
        Stack<Integer> actual = Programmer.getProgram(uuid).getStack();

        assertEquals(expected, actual);

    }

    /**
     * Asserts that, when the runner has the {@link Command#NOT} command, and
     * the stack doesn't have enough values to perform the operation, the
     * operation is ignored and nothing happens.
     */
    @Test
    void runCommand_Not_StackIsEmpty_DoesNothing() {

        Whitebox.setInternalState(runner, "command", Command.NOT);

        Stack<Integer> expected = new Stack<>();

        runner.runCommand();
        Stack<Integer> actual = Programmer.getProgram(uuid).getStack();

        assertEquals(expected, actual);

    }

    /**
     * Asserts that, when the runner has the {@link Command#NOT} command, and the
     * stack has a top value of 0, replaces the top value with 1.
     */
    @Test
    void runCommand_Not_ZeroValue_ReplacesWithOne() {

        Stack<Integer> stack = Programmer.getProgram(uuid).getStack();
        stack.add(0);

        Whitebox.setInternalState(runner, "command", Command.NOT);

        Stack<Integer> expected = new Stack<>();
        expected.add(1);

        runner.runCommand();
        Stack<Integer> actual = Programmer.getProgram(uuid).getStack();

        assertEquals(expected, actual);

    }

    /**
     * Asserts that, when the runner has the {@link Command#NOT} command, and the
     * stack has a top value of 1, replaces the top value with 0.
     */
    @Test
    void runCommand_Not_NonZeroValue_ReplacesWithOne() {

        Stack<Integer> stack = Programmer.getProgram(uuid).getStack();
        stack.add(5);

        Whitebox.setInternalState(runner, "command", Command.NOT);

        Stack<Integer> expected = new Stack<>();
        expected.add(0);

        runner.runCommand();
        Stack<Integer> actual = Programmer.getProgram(uuid).getStack();

        assertEquals(expected, actual);

    }

    /**
     * Asserts that, when the runner has the {@link Command#GREATER} command, and 
     * the stack doesn't have enough values to perform the calculation, then
     * the operation is ignored and nothing happens
     */
    @Test
    void runCommand_Greater_StackOnlyHasOneValue_DoesNothing() {

        Stack<Integer> stack = Programmer.getProgram(uuid).getStack();
        stack.add(5);

        Whitebox.setInternalState(runner, "command", Command.GREATER);

        Stack<Integer> expected = new Stack<>();
        expected.add(5);

        runner.runCommand();
        Stack<Integer> actual = Programmer.getProgram(uuid).getStack();

        assertEquals(expected, actual);
        
    }

    /**
     * Asserts that, when the runner has the {@link Command#GREATER} command, and the
     * stack has the top two values popped off, if the bottom value is greater than the
     * top value, then a 1 is pushed onto the stack, and a 0 is pushed otherwise.
     */
    @Test
    void runCommand_Greater_OlderIsGreater_OneIsPushed() {

        Stack<Integer> stack = Programmer.getProgram(uuid).getStack();
        stack.add(10);
        stack.add(5);

        Whitebox.setInternalState(runner, "command", Command.GREATER);

        Stack<Integer> expected = new Stack<>();
        expected.add(1);

        runner.runCommand();
        Stack<Integer> actual = Programmer.getProgram(uuid).getStack();

        assertEquals(expected, actual);

    }

    /**
     * Asserts that, when the runner has the {@link Command#GREATER} command, and the
     * stack has the top two values popped off, if the top value is greater than the
     * bottom value, then a 1 is pushed onto the stack, and a 0 is pushed otherwise.
     */
    @Test
    void runCommand_Greater_NewerIsGreater_OneIsPushed() {

        Stack<Integer> stack = Programmer.getProgram(uuid).getStack();
        stack.add(5);
        stack.add(10);

        Whitebox.setInternalState(runner, "command", Command.GREATER);

        Stack<Integer> expected = new Stack<>();
        expected.add(0);

        runner.runCommand();
        Stack<Integer> actual = Programmer.getProgram(uuid).getStack();

        assertEquals(expected, actual);

    }

    /**
     * Asserts that, when the runner has the {@link Command#POINTER} command,
     * and the stack has the top value popped off, if the value is positive, the
     * DP is rotated that many times clockwise.
     */
    @Test
    void runCommand_Pointer_PositiveInteger_RotatesClockwise() {

        Program program = Programmer.getProgram(uuid);

        Director director = program.getDirector();

        Stack<Integer> stack = program.getStack();
        stack.add(3);

        Whitebox.setInternalState(runner, "command", Command.POINTER);

        Stack<Integer> expected = new Stack<>();

        runner.runCommand();
        Stack<Integer> actual = Programmer.getProgram(uuid).getStack();

        assertEquals(expected, actual);
        assertEquals(director.getDP(), DP.UP);

    }

    /**
     * Asserts that, when the runner has the {@link Command#POINTER} command,
     * and the stack has the top value popped off, if the value is negative, the
     * DP is rotated that many times anticlockwise.
     */
    @Test
    void runCommand_Pointer_NegativeInteger_RotatesAnticlockwise() {

        Program program = Programmer.getProgram(uuid);

        Director director = program.getDirector();

        Stack<Integer> stack = program.getStack();
        stack.add(-3);

        Whitebox.setInternalState(runner, "command", Command.POINTER);

        Stack<Integer> expected = new Stack<>();

        runner.runCommand();

        Stack<Integer> actual = program.getStack();

        assertEquals(expected, actual);
        assertEquals(director.getDP(), DP.DOWN);

    }

    /**
     * Asserts that, when the runner has the {@link Command#SWITCH} command,
     * and the stack has the top value popped off, if the value is positive, the
     * DP is rotated that many times clockwise.
     */
    @Test
    void runCommand_Switch_PositiveInteger_TogglesThatManyTimes() {

        Program program = Programmer.getProgram(uuid);

        Director director = program.getDirector();

        Stack<Integer> stack = program.getStack();
        stack.add(1);

        Whitebox.setInternalState(runner, "command", Command.SWITCH);

        Stack<Integer> expected = new Stack<>();

        runner.runCommand();
        Stack<Integer> actual = Programmer.getProgram(uuid).getStack();

        assertEquals(expected, actual);
        assertEquals(director.getCC(), CC.RIGHT);

    }

    /**
     * Asserts that, when the runner has the {@link Command#SWITCH} command,
     * and the stack has the top value popped off, if the value is negative, the
     * CC is toggled the absolute value of that many times.
     */
    @Test
    void runCommand_Switch_NegativeInteger_RotatesAnticlockwise() {

        Program program = Programmer.getProgram(uuid);

        Director director = program.getDirector();

        Stack<Integer> stack = new Stack<>();
        stack.add(-2);

        Whitebox.setInternalState(runner, "command", Command.SWITCH);

        Stack<Integer> expected = new Stack<>();

        runner.runCommand();
        Stack<Integer> actual = Programmer.getProgram(uuid).getStack();

        assertEquals(expected, actual);
        assertEquals(director.getCC(), CC.LEFT);

    }

    /**
     * Asserts that, when the runner has the {@link Command#DUP} command, and
     * the stack doesn't have enough values to perform the operation, the
     * operation is ignored and nothing happens.
     */
    @Test
    void runCommand_Duplicate_StackIsEmpty_DoesNothing() {

        Whitebox.setInternalState(runner, "command", Command.DUP);

        Stack<Integer> expected = new Stack<>();

        runner.runCommand();
        Stack<Integer> actual = Programmer.getProgram(uuid).getStack();

        assertEquals(expected, actual);

    }

    /**
     * Asserts that, when the runner has the {@link Command#DUP} command, and the
     * stack has a value, the value is duplicated so that the value at the top of the
     * stack now is at the top twice.
     */
    @Test
    void runCommand_duplicate_ValueDuplicated() {

        Stack<Integer> stack = Programmer.getProgram(uuid).getStack();
        stack.add(5);

        Whitebox.setInternalState(runner, "command", Command.DUP);

        Stack<Integer> expected = new Stack<>();
        expected.add(5);
        expected.add(5);

        runner.runCommand();
        Stack<Integer> actual = Programmer.getProgram(uuid).getStack();

        assertEquals(expected, actual);

    }

    /**
     * Asserts that, when the runner has the {@link Command#ROLL} command, and
     * the stack doesn't have enough values to perform the operation, the
     * operation is ignored and nothing happens.
     */
    @Test
    void runCommand_Roll_StackOnlyHasOneValue_DoesNothing() {

        Stack<Integer> stack = Programmer.getProgram(uuid).getStack();
        stack.push(1);

        Whitebox.setInternalState(runner, "command", Command.ROLL);

        Stack<Integer> expected = new Stack<>();
        expected.push(1);

        runner.runCommand();
        Stack<Integer> actual = Programmer.getProgram(uuid).getStack();

        assertEquals(expected, actual);

    }

    /**
     * Asserts that, when the runner has the {@link Command#ROLL} command,
     * it pops the top two values off the stack and rolls the remaining entries
     * to a depth equal to the second value popped, by a number of rolls equal
     * to the first value popped.
     */
    @Test
    void runCommand_Roll_ChangesOrder() {

        Stack<Integer> stack = Programmer.getProgram(uuid).getStack();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(3);
        stack.push(1);

        Whitebox.setInternalState(runner, "command", Command.ROLL);

        Stack<Integer> expected = new Stack<>();
        expected.push(3);
        expected.push(1);
        expected.push(2);

        runner.runCommand();
        Stack<Integer> actual = Programmer.getProgram(uuid).getStack();

        assertEquals(expected, actual);


    }

    /**
     * Asserts that, when the runner has the {@link Command#IN_NUM} command,
     * the user is prompted to enter a number via STDIN, which is then added
     * to the stack.
     */
    @Test
    void runCommand_InNum_InputAddedToStackAsInteger() {

        String SIXTY_FIVE = "65";
        InputStream in = new ByteArrayInputStream(SIXTY_FIVE.getBytes());
        System.setIn(in);
        
        Whitebox.setInternalState(runner, "command", Command.IN_NUM);

        Stack<Integer> expected = new Stack<>();
        expected.add(65);

        runner.runCommand();
        Stack<Integer> actual = Programmer.getProgram(uuid).getStack();

        assertEquals(expected, actual);

    }

    /**
     * Asserts that, when the runner has the {@link Command#IN_CHAR} command,
     * the user is prompted to enter a character via STDIN, which is then
     * converted to a number and added to the stack.
     */
    @Test
    void runCommand_InChar_InputAddedToStackAsCharacter() {

        String A = "A";
        InputStream in = new ByteArrayInputStream(A.getBytes());
        System.setIn(in);

        Whitebox.setInternalState(runner, "command", Command.IN_CHAR);

        Stack<Integer> expected = new Stack<>();
        expected.add(65);

        runner.runCommand();
        Stack<Integer> actual = Programmer.getProgram(uuid).getStack();

        assertEquals(expected, actual);

    }

    /**
     * Asserts that, when a positive dividend is "modded" by a lesser divisor, the
     * result is correctly calculated so that the result would be equal to 
     * ((p + Nq) mod q).
     */
    @Test
    void correctMod_PositiveDividendGreater_CorrectlyModded() {

        Integer dividend = 5;
        Integer divisor = 3;

        Integer expected = 2;

        Integer actual = CommandController.correctMod(dividend, divisor);

        assertEquals(expected, actual);

    }

    /**
     * Asserts that, when a positive dividend is "modded" by a greater divisor, the
     * result is correctly calculated so that the result would be equal to 
     * ((p + Nq) mod q).
     */
    @Test
    void correctMod_PositiveDividendLesser_CorrectlyModded() {

        Integer dividend = 2;
        Integer divisor = 3;

        Integer expected = 2;

        Integer actual = CommandController.correctMod(dividend, divisor);

        assertEquals(expected, actual);

    }

    /**
     * Asserts that, when a negative dividend is "modded" by a greater divisor, the
     * result is correctly calculated so that the result would be equal to 
     * ((p + Nq) mod q).
     */
    @Test
    void correctMod_NegativeDividend_CorrectlyModded() {

        Integer dividend = -1;
        Integer divisor = 3;

        Integer expected = 2;

        Integer actual = CommandController.correctMod(dividend, divisor);

        assertEquals(expected, actual);

    }
    
}