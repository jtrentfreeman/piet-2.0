package com.frejt.piet.config;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

public class ConfigManager {

    private static final Logger log = LogManager.getLogger(ConfigManager.class);

    @JsonProperty
    private Config config;

    private static ConfigManager configManager = null;

    private ConfigManager() {
    }

    public static ConfigManager getInstance() {
        if(configManager == null) {
            configManager = readConfig();
        }
        return configManager;
    }

    public Config getConfig() {
        return config;
    }

    public static ConfigManager readConfig() {

        ConfigManager manager;
        try {
            Path configPath = Paths.get("app\\src\\main\\resources\\config.yaml");
            File configFile = configPath.toFile();

            ObjectMapper mapper = new YAMLMapper();
            manager = mapper.readValue(configFile, ConfigManager.class);
        } catch(IOException e) {
            log.error("You're gonna see some NPEs.... sorry", e);
            manager = null;
        }
        return manager;
    }
}
