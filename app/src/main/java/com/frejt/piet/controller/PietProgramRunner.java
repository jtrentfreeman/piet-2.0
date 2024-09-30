package com.frejt.piet.controller;

import java.nio.file.Path;
import java.util.UUID;
import java.util.concurrent.Callable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.frejt.piet.entity.Board;
import com.frejt.piet.exception.PietExecutionException;
import com.frejt.piet.utils.reader.PietFileReader;

/**
 * A concurrency-supported Piet program runner.
 * 
 * Takes in the path to a Piet program and assigns a random UUID to it.
 * This UUID is used to track execution for the rest of the program until
 * termination.
 * 
 * TODO: I'm honestly not sure what makes it easier to track a UUID
 * over an actual {@link Program} object instead.
 * I wish I thought of that before I updated just about everything.
 * I guess it's lighter on memory... kind of
 * Anyway, this isn't technically an issue but very possibly a
 * code smell. Maybe look into this at some point.
 */
public class PietProgramRunner implements Callable<String> {

    private static final Logger log = LogManager.getLogger(PietProgramRunner.class);

    private Path program;

    private UUID uuid;

    public PietProgramRunner(Path path) {
        this.program = path;
        uuid = UUID.randomUUID();
    }
    
    @Override
    public String call() throws PietExecutionException {

        log.debug("Running file " + program.toString());

        String output;

        try {
            PietFileReader fileReader = new PietFileReader(program);

            Board board = fileReader.convertFileToBoard();

            BoardRunner runner = new BoardRunner(board, uuid);
            output = runner.runBoard();

        } catch(PietExecutionException e) {
            log.error("Failed to run Piet program:" + e.getMessage());
            output = "";
        }

        Programmer.removeProgram(uuid);

        return output;
    }



}
