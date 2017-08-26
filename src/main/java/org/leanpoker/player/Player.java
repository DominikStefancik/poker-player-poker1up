package org.leanpoker.player;

import com.google.gson.JsonElement;

public class Player {

    static final String VERSION = "Default Java folding player";

    public static int betRequest(GameState gameState) {
        return gameState.betRequest();
    }


    public static void showdown(JsonElement game) {
    }
}
