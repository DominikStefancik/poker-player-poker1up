package org.leanpoker.player;

import com.google.gson.JsonElement;

public class Player {

    static final String VERSION = "Default Java folding player";

    public static int betRequest(GameState gameState) {
        final int bet = raise(gameState);

        System.out.println("gameState: " + gameState);
        System.out.println("bet: " + bet);
        return bet;
    }

    private static int call(GameState gameState) {
        // current_buy_in - players[in_action][bet]
        return gameState.current_buy_in - gameState.players[gameState.in_action].bet;
    }

    private static int raise(GameState gameState, int factor) {
        // current_buy_in - players[in_action][bet] + gameState.minimum_raise
        return gameState.current_buy_in - gameState.players[gameState.in_action].bet + (gameState.minimum_raise * factor);
    }
    private static int raise(GameState gameState) {
        return raise(gameState, 1);
    }

    public static void showdown(JsonElement game) {
    }
}
