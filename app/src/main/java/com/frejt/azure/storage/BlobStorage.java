package com.frejt.azure.storage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.UUID;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.frejt.piet.config.ConfigManager;

public class BlobStorage {

    private static final Logger log = LogManager.getLogger(BlobStorage.class);

    private static final String PIET_STORAGE_ACCOUNT_ENDPOINT = ConfigManager.getInstance().getConfig().getAzureStorageAccountURL();

    private static final String PIET_CONTAINER = ConfigManager.getInstance().getConfig().getAzureStorageFileContainer();
    
    private static final String CONNECTION_STRING_PATH = ConfigManager.getInstance().getConfig().getAzureStorageConnectionStringPath();

    private BlobServiceClient client;

    public BlobStorage() throws IOException {
        buildBlobClient(); 
    }

    private void buildBlobClient() throws IOException {
        String connectionString = Files.readString(Paths.get(CONNECTION_STRING_PATH));

        this.client = new BlobServiceClientBuilder()
            .connectionString(connectionString)
            .endpoint(PIET_STORAGE_ACCOUNT_ENDPOINT)
            .buildClient();
    }

    public Path downloadBlob(String filePath) throws IOException {

        BlobContainerClient containerClient = client.getBlobContainerClient(PIET_CONTAINER);
        BlobClient blobClient = containerClient.getBlobClient(filePath);

        String tempDirectoryPath = System.getProperty("java.io.tmpdir");
        Path downloadDir = Paths.get(tempDirectoryPath, UUID.randomUUID().toString(), filePath);
        Path downloadFilePath = Paths.get(downloadDir.toString());

        Files.createDirectories(downloadFilePath.getParent());

        log.info("Downloading requested file to " + downloadFilePath);

        // we need to keep the extension but generate a new name? maybe
        // or the random uuid at each download will keep them unique
        // maybe it's fine idk
        blobClient.downloadToFile(downloadFilePath.toString(), true);

        return downloadFilePath;
    
    }
    
}
