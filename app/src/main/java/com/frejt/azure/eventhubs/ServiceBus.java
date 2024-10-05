package com.frejt.azure.eventhubs;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import com.azure.messaging.servicebus.ServiceBusClientBuilder;
import com.azure.messaging.servicebus.ServiceBusErrorContext;
import com.azure.messaging.servicebus.ServiceBusException;
import com.azure.messaging.servicebus.ServiceBusFailureReason;
import com.azure.messaging.servicebus.ServiceBusProcessorClient;
import com.azure.messaging.servicebus.ServiceBusReceivedMessageContext;
import com.frejt.azure.storage.BlobStorage;
import com.frejt.piet.controller.PietProgramRunner;
import com.frejt.piet.exception.PietExecutionException;

/**
 * Receives messages determining which Piet programs to run.
 */
public class ServiceBus {

    private static final Logger log = LogManager.getLogger(ServiceBus.class);

    private static final String PIET_MESSAGES_QUEUE = "piet-messages";

    private ExecutorService service;

    private List<Future<String>> output;

    private ServiceBusProcessorClient client;

    public ServiceBus() throws IOException {
        service = Executors.newFixedThreadPool(5);
        output = new ArrayList<>();

        buildProcessorClient();
    }

    public List<Future<String>> getFutures() {
        return this.output;
    }

    private void buildProcessorClient() throws IOException {
        String connectionString = Files.readString(Paths.get("app\\src\\main\\resources\\servicebus_connection_string.txt"));
        
        this.client = new ServiceBusClientBuilder()
            .connectionString(connectionString)
            .processor()
            .queueName(PIET_MESSAGES_QUEUE)
            .processMessage((context) -> processMessage(context))
            .processError(context -> processError(context))
            .maxConcurrentCalls(5)
            .buildProcessorClient();
    }

    public void receiveMessages() throws InterruptedException, ExecutionException, PietExecutionException {

        log.info("Start processing new messages");
        this.client.start();

        TimeUnit.MILLISECONDS.sleep(500);
        log.info("Stop processing new messages");
        this.client.close();

        for(Future<String> future : output) {
            log.info(future.get());
        }

    }

    private void processMessage(ServiceBusReceivedMessageContext context) {

        String messageBody = context.getMessage().getBody().toString();
        log.info("Received new message: [" + messageBody + "]");

        try {
            BlobStorage storage = new BlobStorage();
            Path runFile = storage.downloadBlob(messageBody);

            log.info("Downloaded file, preparing to run: [" + runFile.toString() + "]");
            
            PietProgramRunner runner = new PietProgramRunner(runFile);
            output.add(service.submit(runner));
        } catch (IOException e) {
            log.error("Failed to process message: ", e.getMessage());
        }

    }

    private static void processError(ServiceBusErrorContext context) {
        System.out.printf("Error when receiving messages from namespace: '%s'. Entity: '%s'%n",
            context.getFullyQualifiedNamespace(), context.getEntityPath());

        if (!(context.getException() instanceof ServiceBusException)) {
            System.out.printf("Non-ServiceBusException occurred: %s%n", context.getException());
            return;
        }

        ServiceBusException exception = (ServiceBusException) context.getException();
        ServiceBusFailureReason reason = exception.getReason();

        if (reason == ServiceBusFailureReason.MESSAGING_ENTITY_DISABLED
            || reason == ServiceBusFailureReason.MESSAGING_ENTITY_NOT_FOUND
            || reason == ServiceBusFailureReason.UNAUTHORIZED) {
            System.out.printf("An unrecoverable error occurred. Stopping processing with reason %s: %s%n",
                reason, exception.getMessage());
        } else if (reason == ServiceBusFailureReason.MESSAGE_LOCK_LOST) {
            System.out.printf("Message lock lost for message: %s%n", context.getException());
        } else if (reason == ServiceBusFailureReason.SERVICE_BUSY) {
            try {
                // Choosing an arbitrary amount of time to wait until trying again.
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                System.err.println("Unable to sleep for period of time");
            }
        } else {
            System.out.printf("Error source %s, reason %s, message: %s%n", context.getErrorSource(),
                reason, context.getException());
        }
    }

    
}
