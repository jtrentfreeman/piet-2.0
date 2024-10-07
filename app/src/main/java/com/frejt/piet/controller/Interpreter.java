package com.frejt.piet.controller;

import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.frejt.azure.eventhubs.ServiceBus;
import com.frejt.azure.storage.BlobStorage;

/**
 * This is an interpreter for the programming language Piet.
 * This program is entirely my own, written to the specification 
 * of David Morgan-Mar.
 * 
 * Interspersed across my program are notes taken directly from
 * the specification, marked with the '†' symbol (as '*' would make things confusing).
 * 
 * †Piet is a programming language in which programs look like abstract paintings.
 * 
 * The language is named after Piet Mondrian, who pioneered the field of 
 * geometric abstract art. 
 * 
 * [David Morgan-Mar] would have liked to call the language Mondrian, but someone beat me to it 
 * with a rather mundane-looking scripting language. 
 * 
 * Oh well, we can't all be esoteric language writers I suppose.
 * 
 * @see https://www.dangermouse.net/esoteric/piet.html 
 */
public class Interpreter {

    private static final Logger log = LogManager.getLogger(Interpreter.class);
    
    /**
     * Entry point to the program.
     * 
     * @param args: args[0] - String holding the image's file path
     */
    public static void main(String[] args) {

        try {

            // TODO: this trash crashes the first time we try to
            // download a blob from Azure
            // it's real annoying
            // luckily it works perfectly fine every other time
            // so we're just going to instantiate a fake first download
            // so the rest of the program works just fine
            BlobStorage storage = new BlobStorage();
            storage.downloadBlob("ppm/hi.ppm");

            ServiceBus bus = new ServiceBus();

            while(true) {
                log.info("prompt for receivemessages");
                bus.receiveMessages();

                log.info("we have " + bus.getFutures().size() + " programs");

                TimeUnit.SECONDS.sleep(5);
            }

        } catch(Exception e) {
            log.error("Ran into an unfixable error: ", e);
        }

        System.exit(0);
    }
}

/**
 * Thank you, Larry Tesler.
 * 
 * Realized that ^ may sound like I was copy+pasting from outside sources, I
 * wasn't.
 * 
 * Some of the code (converting numbers -> colors) was repetitive, where I
 * duplicated a lot of code.
 * 
 * Larry Tesler was the creator of the copy & paste commands, according to
 * Wikipedia.
 * 
 * All of this code was written by myself (Trent Freeman), and no code was taken
 * from any outside sources.
 * 
 * 
 * Also shout of to Sean Szumlanski, a (former) CS professor at UCF.
 * 
 * He initially introduced me to Piet and got me interested in writing this
 * program, which has taken up a large chunk of time (which I've mostly
 * enjoyed).
 */