package com.github.unimaru6.statekeeper.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StatekeeperClient implements ClientModInitializer {

    // 外部からアクセスあり
    private static boolean SprintState = false;

    private boolean hasJoinedServer = false;

    public static final String MOD_ID = "togglesprintfixer";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitializeClient() {

        // 設定ファイルを作成
        ConfigHandler.makeConfigFile();
        // 設定ファイルを読み込み
        ConfigHandler.loadConfig();

        // 設定ファイルからセーブしたスプリント状態を取得
        boolean savedSprintState = ConfigHandler.getSprintState();

        // サーバーに接続した時のイベントを設定
        ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> {
            if (!hasJoinedServer) {
                if (savedSprintState) {
                    client.options.sprintKey.setPressed(true);
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
        ConfigHandler.saveConfig(SprintState);
    }

    public static void setSprintState(boolean isSprintState) {
        StatekeeperClient.SprintState = isSprintState;
    }
}
