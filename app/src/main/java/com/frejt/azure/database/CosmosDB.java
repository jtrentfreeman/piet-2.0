package com.frejt.azure.database;

import com.azure.cosmos.CosmosClient;
import com.azure.cosmos.CosmosClientBuilder;
import com.azure.cosmos.CosmosContainer;
import com.azure.cosmos.CosmosDatabase;
import com.azure.cosmos.models.CosmosItemRequestOptions;
import com.azure.cosmos.models.CosmosItemResponse;
import com.azure.cosmos.models.PartitionKey;
import com.frejt.piet.config.Config;
import com.frejt.piet.config.ConfigManager;
import com.frejt.piet.entity.PietOutput;

public class CosmosDB {

    private CosmosClient client;

    private CosmosDatabase database;

    private CosmosContainer container;

    public CosmosDB() {
        initClient();
    }

    private void initClient() {
        Config config = ConfigManager.getInstance().getConfig();

        client = new CosmosClientBuilder()
            .endpoint(config.getAzureDBAccountURL())
            .key(config.getAzureDBKeyPath())
            .buildClient();

        database = client.getDatabase(config.getAzureDBName());
        container = database.getContainer(config.getAzureDBContainer());
    }

    public void createPietOutputEntry(PietOutput output) {

        CosmosItemRequestOptions cosmosItemRequestOptions = new CosmosItemRequestOptions();
        CosmosItemResponse<PietOutput> item = container.createItem(output, new PartitionKey(output.getInputPath().toString()), cosmosItemRequestOptions);

        item.getActivityId();

    }


    
}
