package org.leanpoker.player;

import java.util.Arrays;

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

    public int betRequest() {

        final int ourCards = rate(players[in_action].hole_cards);
        int bet = fold();

        if (ourCards > 0) {
            bet = raise(ourCards);
        }

        System.out.println("======================================================");
        System.out.println("gameState: " + this);
        System.out.println("ourCards: " + bet);
        System.out.println("bet: " + bet);
        System.out.println("======================================================");
        return bet;
    }

    private int rate(Cards[] hole_cards) {
        Cards c1 = hole_cards[0];
        Cards c2 = hole_cards[1];

        int result = 0;

        if (c1.rank.equals(c2.rank)) {
            result += c1.getRank().getFactor();
        }

        return result;
    }

    public int fold() {
        return 0;
    }

    public int call() {
        // current_buy_in - players[in_action][bet]
        return current_buy_in - players[in_action].bet;
    }

    public int raise(int factor) {
        // current_buy_in - players[in_action][bet] + minimum_raise
        return current_buy_in - players[in_action].bet + (minimum_raise * factor);
    }
    public int raise() {
        return raise(1);
    }

    public int round() {
        final Cards[] cc = community_cards;
        if (cc.length == 0) {
            return 0;
        }
        return cc.length - 2;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("GameState{");
        sb.append("players=").append(Arrays.toString(players));
        sb.append(", community_cards=").append(Arrays.toString(community_cards));
        sb.append(", round=").append(round);
        sb.append(", bet_index=").append(bet_index);
        sb.append(", small_blind=").append(small_blind);
        sb.append(", in_action=").append(in_action);
        sb.append(", orbits=").append(orbits);
        sb.append(", minimum_raise=").append(minimum_raise);
        sb.append(", dealer=").append(dealer);
        sb.append(", current_buy_in=").append(current_buy_in);
        sb.append(", pot=").append(pot);
        sb.append('}');
        return sb.toString();
    }
}
