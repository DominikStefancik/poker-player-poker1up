package org.leanpoker.player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

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

        final Cards[] allCards = ArrayUtils.addAll(we().hole_cards, community_cards);
        final int ourCards = new CardRater(allCards).rate();
        int bet = fold();


        if (community_cards.length == 0) {
            bet = call();
            if (ourCards < 10 && we().bet > 10) {
                bet = fold();
            }
        } else {
            if (ourCards > 0) {
                if (bet_index > 2) {
                    bet = fold();
                } else {
                    bet = call();
                }
            }
        }

        if (ourCards > 10) {
            bet = raise(1);
        }

        if (isAllIn(bet)) {
            if (ourCards < 9) {
                bet = fold();
            }
        }

        bet = Math.max(0, bet);

        System.out.println("======================================================");
        System.out.println("gameState: " + this);
        System.out.println("ourCards: " + bet);
        System.out.println("bet: " + bet);
        System.out.println("======================================================");
        return bet;
    }

    private PlayerState we() {
        return players[in_action];
    }

    private boolean isAllIn(int bet) {
        return we().stack <= bet;
    }


    public int fold() {
        return 0;
    }

    public int call() {
        // current_buy_in - players[in_action][bet]
        return current_buy_in - we().bet;
    }

    public int raise(int factor) {
        // current_buy_in - players[in_action][bet] + minimum_raise
        return current_buy_in - we().bet + (minimum_raise * factor);
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
