package org.leanpoker.player;

import com.google.gson.JsonElement;

public class Player {

    static final String VERSION = "Default Java folding player";

    public static int betRequest(GameState gameState) {
        final int bet = gameState.current_buy_in - gameState.players[gameState.in_action].bet + gameState.minimum_raise;
        System.out.println("bet: " + bet);
        return bet;
    }

    public static void showdown(JsonElement game) {
    }
}
