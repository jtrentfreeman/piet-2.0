package com.frejt.piet.entity;

import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

import com.frejt.piet.command.Command;

/**
 * Data that gets written to a database on program completion
 * TODO:
 *  - metadata:
 *      - file type: ppm, png, etc
 *      - size: width, height
 *      - author
 *  - time to run, time started, time stopped
 *  - did succeed?
 *  - errors
 */
public class PietOutput {

    private UUID id;

    private Path inputPath;

    private String programName;

    private List<Command> commandList;

    private List<String> stdIn;

    private String stdOut;

    private long runTimeMS;

    public PietOutput(UUID uuid, Path programPath, List<Command> commandList, List<String> stdIn, String stdOut, long runTimeMS) {
        this.id = uuid;
        this.inputPath = programPath;
        this.programName = programPath.getFileName().toString();
        this.commandList = commandList;
        this.stdIn = stdIn;
        this.stdOut = stdOut;
        this.runTimeMS = runTimeMS;
    }

    public String getInputPath() {
        return inputPath.toString();
    }

    public String getProgramName() {
        return programName;
    }

    public List<Command> getCommandList() {
        return commandList;
    }

    public List<String> getStdIn() {
        return stdIn;
    }

    public String getStdOut() {
        return stdOut;
    }

    public UUID getId() {
        return id;
    }

    public long getRunTimeMS() {
        return runTimeMS;
    }

    @Override
    public String toString() {
        return "PietOutput [id=" + id + ", inputPath=" + inputPath + ", programName=" + programName + ", commandList="
                + commandList + ", stdIn=" + stdIn + ", stdOut=" + stdOut + ", runTimeMS=" + runTimeMS + "]";
    }

}
