package org.leanpoker.player;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class Player {

    static final String VERSION = "Default Java folding player";

    public static int betRequest(JsonElement request) {
        final JsonObject o = request.getAsJsonObject();
        final JsonArray players = o.getAsJsonArray("players");
        final int in_action = o.getAsJsonPrimitive("in_action").getAsInt();
        final int current_buy_in = o.getAsJsonPrimitive("current_buy_in").getAsInt();
        final int bet = players.get(in_action).getAsJsonObject().getAsJsonPrimitive("bet").getAsInt();

        return current_buy_in - bet;
    }

    public static int betRequest(GameState gameState) {
        //current_buy_in - players[in_action][bet] + minimum_raise
        final int bet = gameState.current_buy_in - gameState.players[gameState.in_action].bet + gameState.minimum_raise;
        System.out.println("bet: " + bet);
        return bet;
    }

    public static void showdown(JsonElement game) {
    }
}
