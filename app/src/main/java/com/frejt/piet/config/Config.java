package com.frejt.piet.config;

import java.nio.file.Path;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Config {

    @JsonProperty
    private HostTypes host;

    @JsonProperty
    private String azureDBAccountURL;

    @JsonProperty
    private String azureDBKeyPath;

    @JsonProperty
    private String azureDBName;

    @JsonProperty
    private String azureDBContainer;

    @JsonProperty
    private Path azureServiceBusConnectionStringPath;

    @JsonProperty
    private String azureServiceBusMessagesQueue;

    @JsonProperty
    private String azureStorageAccountURL;

    @JsonProperty
    private String azureStorageConnectionStringPath;

    @JsonProperty
    private String azureStorageFileContainer;

    public HostTypes getHost() {
        return host;
    }

    public String getAzureDBAccountURL() {
        return azureDBAccountURL;
    }

    public String getAzureDBKeyPath() {
        return azureDBKeyPath;
    }

    public String getAzureDBName() {
        return azureDBName;
    }

    public String getAzureDBContainer() {
        return azureDBContainer;
    }

    public Path getAzureServiceBusConnectionStringPath() {
        return azureServiceBusConnectionStringPath;
    }

    public String getAzureServiceBusMessagesQueue() {
        return azureServiceBusMessagesQueue;
    }

    public String getAzureStorageAccountURL() {
        return azureStorageAccountURL;
    }

    public String getAzureStorageConnectionStringPath() {
        return azureStorageConnectionStringPath;
    }

    public String getAzureStorageFileContainer() {
        return azureStorageFileContainer;
    }
    
}
