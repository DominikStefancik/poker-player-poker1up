package org.leanpoker.player;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * ...
 */
public class GameState {

    public PlayerState[] players;
    public Cards[] community_cards;

    public String tournament_id;
    public String game_id;
    public int round;
    public int bet_index;
    public int small_blind;
    public int in_action;
    public int orbits;
    public int minimum_raise;
    public int dealer;
    public int current_buy_in;
    public int pot;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
