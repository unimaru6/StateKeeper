package com.github.unimaru6.statekeeper.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.entity.EntityRenderDispatcher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StatekeeperClient implements ClientModInitializer {

    // 外部からアクセスあり
    private static boolean SprintState = false;
    private static boolean RenderHitboxesState = false;

    private boolean hasJoinedServer = false;
    private EntityRenderDispatcher entityRenderDispatcher;

    public static final String MOD_ID = "togglesprintfixer";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitializeClient() {

        ConfigHandler.initializeConfig();

        // 設定ファイルからセーブした状態を取得
        boolean savedSprintState = ConfigHandler.getSprintState();
        boolean savedRenderHitboxesState = ConfigHandler.getRenderHitboxesState();

        // サーバーに接続した時のイベントを設定
        ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> {
            if (!hasJoinedServer) {
                if (savedSprintState) {
                    client.options.sprintKey.setPressed(true);
                }
                if (savedRenderHitboxesState) {
                    entityRenderDispatcher = MinecraftClient.getInstance().getEntityRenderDispatcher();
                    entityRenderDispatcher.setRenderHitboxes(true);
                }
                hasJoinedServer = true;
            }
        });

        // クライアント終了時のイベントを設定
        ClientLifecycleEvents.CLIENT_STOPPING.register((client) -> onClientStopping());

    }

    /**
     * クライアント終了時の処理
     */
    private void onClientStopping() {
        // 設定ファイルにスプリント状態を保存
        ConfigHandler.saveConfig(SprintState, RenderHitboxesState);
    }

    public static void setSprintState(boolean isSprintState) {
        StatekeeperClient.SprintState = isSprintState;
    }

    public static void setRenderHitboxesState(boolean isRenderHitboxesState) {
        StatekeeperClient.RenderHitboxesState = isRenderHitboxesState;
    }
}
