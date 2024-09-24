package com.frejt.piet.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.internal.util.reflection.Whitebox;

import com.frejt.piet.director.Director;
import com.frejt.piet.exception.PietCommandNotFoundException;
import com.frejt.piet.utils.Block;
import com.frejt.piet.utils.BlockSet;
import com.frejt.piet.utils.color.PietColor;

/**
 * {@link CommandRunner}
 */
public class CommandRunnerTest {

    @Mock
    private static Director director;

    @Mock
    private static Stack<Integer> stack;

    @Mock
    private static List<Command> commandList;

    @Mock
    private static CommandRunner runner;

    @Mock
    private static Block older;

    @Mock
    private static Block newer;

    @BeforeAll
    static void init() {

        director = mock(Director.class);

        stack = new Stack<>();
        commandList = new ArrayList<>();

        older = mock(Block.class);
        newer = mock(Block.class);

        BlockSet blocks = new BlockSet(older, newer);

        runner = new CommandRunner(stack, blocks);

    }

    @AfterEach
    void clean() {

        Stack<Integer> stack = new Stack<>();
        Whitebox.setInternalState(runner, "stack", stack);

    }

    
    /**
     * Asserts that, when given two Blocks which have no difference in color,
     * that the {@link findCommand} function returns {@link Command#NOP}
     */
    @Test
    void findCommand_ZeroHueStep_ZeroLightStep_ReturnsNop() throws PietCommandNotFoundException {

        when(older.getColor()).thenReturn(PietColor.LIGHT_RED);
        when(newer.getColor()).thenReturn(PietColor.LIGHT_RED);

        Command expected = Command.NOP;

        Command actual = runner.findCommand();

        assertEquals(expected, actual);

    }

    /**
     * Asserts that, when two Blocks, which have colors that are different by one hue,
     * that the {@link findCommand} function returns {@link Command#PUSH}.
     */
    @Test
    void findCommand_ZeroHueStep_OneLightStep_ReturnsPush() throws PietCommandNotFoundException {

        when(older.getColor()).thenReturn(PietColor.LIGHT_RED);
        when(newer.getColor()).thenReturn(PietColor.RED);

        Command expected = Command.PUSH;

        Command actual = runner.findCommand();

        assertEquals(expected, actual);

    }

    /**
     * Asserts that, when two Blocks, which have colors that are different by two
     * hues, that the {@link findCommand} function returns {@link Command#POP}.
     */
    @Test
    void findCommand_ZeroHueStep_TwoLightStep_ReturnsPop() throws PietCommandNotFoundException {

        when(older.getColor()).thenReturn(PietColor.LIGHT_RED);
        when(newer.getColor()).thenReturn(PietColor.DARK_RED);

        Command expected = Command.POP;

        Command actual = runner.findCommand();

        assertEquals(expected, actual);

    }

    /**
     * Asserts that, when two Blocks, which have colors that are different by one hue
     * and one light step, that the {@link findCommand} function returns {@link Command#ADD}.
     */
    @Test
    void findCommand_OneHueStep_ZeroLightStep_ReturnsAdd() throws PietCommandNotFoundException {

        when(older.getColor()).thenReturn(PietColor.LIGHT_RED);
        when(newer.getColor()).thenReturn(PietColor.LIGHT_YELLOW);

        Command expected = Command.ADD;

        Command actual = runner.findCommand();

        assertEquals(expected, actual);

    }

    /**
     * Asserts that, when two Blocks, which have colors that are different by one hue
     * and one light step, that the {@link findCommand} function returns {@link Command#SUB}.
     */
    @Test
    void findCommand_OneHueStep_OneLightStep_ReturnsSubtract() throws PietCommandNotFoundException {

        when(older.getColor()).thenReturn(PietColor.LIGHT_RED);
        when(newer.getColor()).thenReturn(PietColor.YELLOW);

        Command expected = Command.SUB;

        Command actual = runner.findCommand();

        assertEquals(expected, actual);

    }

    /**
     * Asserts that, when two Blocks, which have colors that are different by two hues
     * and one light step, that the {@link findCommand} function returns {@link Command#MULT}.
     */
    @Test
    void findCommand_OneHueStep_TwoLightStep_ReturnsMultiply() throws PietCommandNotFoundException {

        when(older.getColor()).thenReturn(PietColor.LIGHT_RED);
        when(newer.getColor()).thenReturn(PietColor.DARK_YELLOW);

        Command expected = Command.MULT;

        Command actual = runner.findCommand();

        assertEquals(expected, actual);

    }

    /**
     * Asserts that, when two Blocks, which have colors that are different by two hues
     * and one light step, that the {@link findCommand} function returns {@link Command#DIV}.
     */
    @Test
    void findCommand_TwoHueStep_ZeroLightStep_ReturnsDivide() throws PietCommandNotFoundException {

        when(older.getColor()).thenReturn(PietColor.LIGHT_RED);
        when(newer.getColor()).thenReturn(PietColor.LIGHT_GREEN);

        Command expected = Command.DIV;

        Command actual = runner.findCommand();

        assertEquals(expected, actual);

    }

    /**
     * Asserts that, when two Blocks, which have colors that are different by two hues
     * and one light step, that the {@link findCommand} function returns {@link Command#MOD}.
     */
    @Test
    void findCommand_TwoHueStep_OneLightStep_ReturnsMod() throws PietCommandNotFoundException {

        when(older.getColor()).thenReturn(PietColor.LIGHT_RED);
        when(newer.getColor()).thenReturn(PietColor.GREEN);

        Command expected = Command.MOD;

        Command actual = runner.findCommand();

        assertEquals(expected, actual);

    }

    /**
     * Asserts that, when two Blocks, which have colors that are different by two hues
     * and one light step, that the {@link findCommand} function returns {@link Command#NOT}.
     */
    @Test
    void findCommand_TwoHueStep_TwoLightStep_ReturnsNot() throws PietCommandNotFoundException {

        when(older.getColor()).thenReturn(PietColor.LIGHT_RED);
        when(newer.getColor()).thenReturn(PietColor.DARK_GREEN);

        Command expected = Command.NOT;

        Command actual = runner.findCommand();

        assertEquals(expected, actual);

    }

    /**
     * Asserts that, when two Blocks, which have colors that are different by two hues
     * and one light step, that the {@link findCommand} function returns {@link Command#GREATER}.
     */
    @Test
    void findCommand_ThreeHueStep_ZeroLightStep_ReturnsGreater() throws PietCommandNotFoundException {

        when(older.getColor()).thenReturn(PietColor.LIGHT_RED);
        when(newer.getColor()).thenReturn(PietColor.LIGHT_CYAN);

        Command expected = Command.GREATER;

        Command actual = runner.findCommand();

        assertEquals(expected, actual);

    }   

    /**
     * Asserts that, when two Blocks, which have colors that are different by two hues
     * and one light step, that the {@link findCommand} function returns {@link Command#POINTER}.
     */
    @Test
    void findCommand_ThreeHueStep_OneLightStep_ReturnsPointer() throws PietCommandNotFoundException {

        when(older.getColor()).thenReturn(PietColor.LIGHT_RED);
        when(newer.getColor()).thenReturn(PietColor.CYAN);

        Command expected = Command.POINTER;

        Command actual = runner.findCommand();

        assertEquals(expected, actual);

    }

    /**
     * Asserts that, when two Blocks, which have colors that are different by two hues
     * and one light step, that the {@link findCommand} function returns {@link Command#DUP}.
     */
    @Test
    void findCommand_FourHueStep_ZeroLightStep_ReturnsDuplicate() throws PietCommandNotFoundException {

        when(older.getColor()).thenReturn(PietColor.LIGHT_RED);
        when(newer.getColor()).thenReturn(PietColor.LIGHT_BLUE);

        Command expected = Command.DUP;

        Command actual = runner.findCommand();

        assertEquals(expected, actual);

    }

    /**
     * Asserts that, when two Blocks, which have colors that are different by two hues
     * and one light step, that the {@link findCommand} function returns {@link Command#ROLL}.
     */
    @Test
    void findCommand_FourHueStep_OneLightStep_ReturnsRoll() throws PietCommandNotFoundException {

        when(older.getColor()).thenReturn(PietColor.LIGHT_RED);
        when(newer.getColor()).thenReturn(PietColor.BLUE);

        Command expected = Command.ROLL;

        Command actual = runner.findCommand();

        assertEquals(expected, actual);

    }

    /**
     * Asserts that, when two Blocks, which have colors that are different by two hues
     * and one light step, that the {@link findCommand} function returns {@link Command#IN_NUM}.
     */
    @Test
    void findCommand_FourHueStep_TwoLightStep_ReturnsInNumber() throws PietCommandNotFoundException {

        when(older.getColor()).thenReturn(PietColor.LIGHT_RED);
        when(newer.getColor()).thenReturn(PietColor.DARK_BLUE);

        Command expected = Command.IN_NUM;

        Command actual = runner.findCommand();

        assertEquals(expected, actual);

    }

    /**
     * Asserts that, when two Blocks, which have colors that are different by two hues
     * and one light step, that the {@link findCommand} function returns {@link Command#IN_CHAR}.
     */
    @Test
    void findCommand_FiveHueStep_ZeroLightStep_ReturnsInChar() throws PietCommandNotFoundException {

        when(older.getColor()).thenReturn(PietColor.LIGHT_RED);
        when(newer.getColor()).thenReturn(PietColor.LIGHT_MAGENTA);

        Command expected = Command.IN_CHAR;

        Command actual = runner.findCommand();

        assertEquals(expected, actual);

    }

    /**
     * Asserts that, when two Blocks, which have colors that are different by two hues
     * and one light step, that the {@link findCommand} function returns {@link Command#OUT_CHAR}.
     */
    @Test
    void findCommand_FiveHueStep_TwoLightStep_ReturnsOutChar() throws PietCommandNotFoundException {

        when(older.getColor()).thenReturn(PietColor.LIGHT_RED);
        when(newer.getColor()).thenReturn(PietColor.DARK_MAGENTA);

        Command expected = Command.OUT_CHAR;

        Command actual = runner.findCommand();

        assertEquals(expected, actual);

    }

    /**
     * Asserts that, when two Blocks, which have colors that are different by negative five
     * hues and zero light steps, that the {@link findCommand} function returns 
     * {@link Command#ADD}
     */
    @Test
    void findCommand_NegativeFiveHueStep_ZeroLightStep_ReturnsAdd() throws PietCommandNotFoundException {

        when(older.getColor()).thenReturn(PietColor.DARK_MAGENTA);
        when(newer.getColor()).thenReturn(PietColor.DARK_RED);

        Command expected = Command.ADD;

        Command actual = runner.findCommand();

        assertEquals(expected, actual);
    }

    /**
     * Asserts that, when two Blocks, which have colors that are different by zero hues
     * and negative two light steps, that the {@link findCommand} returns '
     * {@link Command#PUSH}
     */
    @Test
    void findCommand_ZeroHueStep_NegativeTwoLightStep_ReturnsPop() throws PietCommandNotFoundException {

        when(older.getColor()).thenReturn(PietColor.DARK_RED);
        when(newer.getColor()).thenReturn(PietColor.LIGHT_RED);

        Command expected = Command.PUSH;

        Command actual = runner.findCommand();

        assertEquals(expected, actual);

    }

}
