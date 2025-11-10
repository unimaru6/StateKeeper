package com.github.unimaru6.statekeeper.client;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class ConfigHandler {

    private static final Path configPath = Paths.get("config", "togglesprintfixerconfig.json");
    private static String configJson = "";

    public static void makeConfigFile() {
        if (!Files.exists(configPath)) {
            try {
                if (configPath.getParent() != null) {
                    Files.createDirectories(configPath.getParent());
                }
                Files.createFile(configPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void loadConfig() {
        if (Files.exists(configPath)) {
            try {
                configJson = Files.readString(configPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void saveConfig(boolean isSprintState, boolean isRenderHitboxesState) {
        if (Files.exists(configPath)) {
            try {
                Files.writeString(configPath, "{ \"sprintState\": " + isSprintState + ", \"renderHitboxesState\": "
                        + isRenderHitboxesState + " }");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean getSprintState() {
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(configJson, JsonObject.class);
        if (jsonObject != null) {
            if (jsonObject.has("sprintState")) {
                return jsonObject.get("sprintState").getAsBoolean();
            }
        }
        return false;
    }

    public static boolean getRenderHitboxesState() {
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(configJson, JsonObject.class);
        if (jsonObject != null) {
            if (jsonObject.has("renderHitboxesState")) {
                return jsonObject.get("renderHitboxesState").getAsBoolean();
            }
        }
        return false;
    }
}
