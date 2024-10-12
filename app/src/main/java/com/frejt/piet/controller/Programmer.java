package com.frejt.piet.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Keeps a running list of the currently running {@link Program}s
 * being executed.
 * 
 * Each Program is tied to a distinct {@link UUID} that can be
 * used to track progress.
 * 
 * TODO: develop a routine that removes UUIDs that are either
 * going on too long or have stoped but for some reason still
 * remain in memory.
 */
public class Programmer {

    private static Map<UUID, Program> activePrograms = new HashMap<>();

    public static Program getProgram(UUID uuid) {
        return activePrograms.get(uuid);
    }

    public static Program newProgram(UUID uuid) {
        activePrograms.put(uuid, new Program());
        return activePrograms.get(uuid);
    }

    public static void removeProgram(UUID uuid) {
        activePrograms.remove(uuid);
    }
    
}
