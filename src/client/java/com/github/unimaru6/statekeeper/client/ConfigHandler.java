package com.github.unimaru6.statekeeper.client;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class ConfigHandler {

    private static final Path configPath = Paths.get("config", "statekeeperconfig.json");
    private static String configJson = "";
    private static boolean sprintState = false;
    private static boolean renderHitboxesState = false;

    public static void initializeConfig() {
        makeConfigFile();
        loadConfig();
        loadSavedState();
    }

    private static void makeConfigFile() {
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

    private static void loadConfig() {
        if (Files.exists(configPath)) {
            try {
                configJson = Files.readString(configPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void loadSavedState() {
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(configJson, JsonObject.class);
        // null時false
        if (jsonObject != null) {
            if (jsonObject.has("sprintState")) {
                sprintState = jsonObject.get("sprintState").getAsBoolean();
            }
            if (jsonObject.has("renderHitboxesState")) {
                renderHitboxesState = jsonObject.get("renderHitboxesState").getAsBoolean();
            }
            // state追加時ここに処理を追加
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
        return sprintState;
    }

    public static boolean getRenderHitboxesState() {
        return renderHitboxesState;
    }

    // state追加時ここに処理を追加
}
