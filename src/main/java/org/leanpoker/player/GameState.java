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

        final int ourCards = rate(ArrayUtils.addAll(we().hole_cards, community_cards));
        int bet = fold();

        if (ourCards > 0) {
            if (bet_index > 2) {
                bet = fold();
            }
            else {
                bet = call();
            }
        }

        if ( community_cards.length == 0) {
            bet = call();
        }

        if (ourCards > 10) {
            bet = raise();
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

    private int rate(Cards[] allCards) {

        int result = 0;

        int[] ranks = new int[Rank.values().length];
        for (int i = 0 ; i < ranks.length; i++) {
            ranks[i] = 0;
        }

        int[] suits = new int[Suit.values().length];
        for (int i = 0 ; i < suits.length; i++) {
            suits[i] = 0;
        }

        for (final Cards card : allCards) {
            final int r = card.getRank().ordinal();
            final int s = card.suit.ordinal();
            ranks[r]++;
            suits[s]++;
        }

        List<Rank> pairs = new ArrayList<>();
        List<Rank> trippels = new ArrayList<>();
        List<Rank> four = new ArrayList<>();

        for (int count : suits) {
            if (count >= 5) {
                return 50;
            }
        }

        

        for (int i = 0 ; i < ranks.length; i++) {
            final int count = ranks[i];
            final Rank r = Rank.values()[i];
            if (count == 4) {
                four.add(r);
            }
            else if (count == 3) {
                trippels.add(r);
            }
            else if (count == 2) {
                pairs.add(r);
            }
        }

        if (!four.isEmpty()) {
            result = 100;
        }
        if (!four.isEmpty()) {
            if (!pairs.isEmpty()) {
                return 200;
            }
            return 50;
        }
        if (pairs.size() == 2) {
            return 25;
        }
        if (pairs.size() == 1) {
            return pairs.get(0).getFactor();
        }


        return result;
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
