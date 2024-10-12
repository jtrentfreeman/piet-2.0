package com.frejt.piet.controller;

import java.nio.file.Path;
import java.util.UUID;
import java.util.concurrent.Callable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.frejt.azure.database.CosmosDB;
import com.frejt.piet.config.ConfigManager;
import com.frejt.piet.config.HostTypes;
import com.frejt.piet.entity.Board;
import com.frejt.piet.entity.PietOutput;
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
public class PietProgramRunner implements Callable<PietOutput> {

    private static final Logger log = LogManager.getLogger(PietProgramRunner.class);

    private Path programPath;

    private UUID uuid;

    public PietProgramRunner(Path path) {
        this.programPath = path;
        uuid = UUID.randomUUID();
    }
    
    @Override
    public PietOutput call() throws PietExecutionException {

        log.debug("Running file " + programPath.toString());

        PietOutput pietOutput = null;

        try {

            long startTime = System.currentTimeMillis();
        
            PietFileReader fileReader = new PietFileReader(programPath);
            Board board = fileReader.convertFileToBoard();

            BoardRunner runner = new BoardRunner(board, uuid);
            runner.runBoard();

            long endTime = System.currentTimeMillis();

            long runTimeMS = endTime - startTime;

            log.info("Program took " + runTimeMS + "ms to run");

            pietOutput = new PietOutput(uuid, programPath, Programmer.getProgram(uuid).getCommandList(), null, Programmer.getProgram(uuid).getOutput(), runTimeMS);

            if(ConfigManager.getInstance().getConfig().getHost().equals(HostTypes.CLOUD)) {
                CosmosDB cosmosDB = new CosmosDB();
                cosmosDB.createPietOutputEntry(pietOutput);
            } else {
                log.info(pietOutput);
            }

        } catch(PietExecutionException e) {
            log.error("Failed to run Piet program:" + e.getMessage());
        }

        Programmer.removeProgram(uuid);

        return pietOutput;
    }

}
