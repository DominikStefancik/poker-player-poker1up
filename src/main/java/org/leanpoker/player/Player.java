package org.leanpoker.player;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import java.util.Map;

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

    public static void showdown(JsonElement game) {
    }
}
