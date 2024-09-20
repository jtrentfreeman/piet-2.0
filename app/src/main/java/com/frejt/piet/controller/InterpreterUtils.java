package com.frejt.piet.controller;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

class InterpreterUtils {

    /**
     * Gets the file holding the Piet board that will be run.
     * If the user passed in an argument to the program, interprets the arguments
     * as the path to the file that should be run.
     * If no argument is provided, requests that the user enters a file to run.
     * 
     * @param args the arguments provided to the program
     * @return a string holding a path to an image file
     */
    public static Path getRunFile(String[] args) {

        String runFileStr;

        if(args.length > 0) {
            runFileStr = args[0];
        } else {
            System.err.println("Please enter the path to the file to be ran:");
            try(Scanner sc = new Scanner(System.in)) {
                runFileStr = sc.nextLine();
            }
        }

        return Paths.get(runFileStr);
    }
    
}
