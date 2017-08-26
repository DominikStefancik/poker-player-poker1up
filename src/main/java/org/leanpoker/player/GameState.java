package org.leanpoker.player;

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
    public int orbits;
    public int dealer;
    public int current_buy_in;
    public int pot;

}
