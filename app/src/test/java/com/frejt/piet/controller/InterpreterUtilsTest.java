package com.frejt.piet.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;


class InterpreterUtilsTest {

    /**
     * If the user passes in a string in the arguments, that string will be
     * translated into a Path and validated that it exists
     */
    @Test 
    void getRunFile_StringAsParam_ReturnsStringAsPath() {

        Path expectedPath = Paths.get("TEST_ARGS_PATH.ppm");

        String[] args = new String[] {"TEST_ARGS_PATH.ppm"};

        Path actualPath = InterpreterUtils.getRunFile(args);

        assertEquals(expectedPath, actualPath);
    }

    /**
     * If the user does not pass in any string in the arguments, the user will
     * be prompted to give the string as System.in
     */
    @Test 
    void getRunFile_NoParam_ReturnSystemInAsPath() {

        String PATH_STRING = "TEST_INPUT_PATH.ppm";
        Path expectedPath = Paths.get(PATH_STRING);

        InputStream in = new ByteArrayInputStream(PATH_STRING.getBytes());
        System.setIn(in);


        Path actualPath = InterpreterUtils.getRunFile(new String[0]);
        assertEquals(expectedPath, actualPath);

    }
    
}
