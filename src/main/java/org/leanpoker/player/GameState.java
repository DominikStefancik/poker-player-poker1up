package org.leanpoker.player;

import java.util.Arrays;

import org.apache.commons.lang3.ArrayUtils;

import static org.leanpoker.player.CardRater.VERY_GOOD;

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
        System.out.println("======================================================");
        System.out.println("gameState: " + this);


        final Cards[] allCards = ArrayUtils.addAll(we().hole_cards, community_cards);
        final CardRater cardRater = new CardRater(allCards);
        final int ourCards = cardRater.rate();
        int bet;


        if (community_cards.length == 0) {
            bet = call();
            if (ourCards < 10 && we().bet > 10) {
                bet = foldOrCheck();
            }
        } else {

            if (ourCards == VERY_GOOD) {
                bet = raise(20);
            } else {
                bet = getComplicatedBet(cardRater, ourCards);
            }
        }

        bet = Math.max(0, bet);

        System.out.println("ourCards: " + ourCards);
        System.out.println("bet: " + bet);
        System.out.println("======================================================");
        return bet;
    }

    private int getComplicatedBet(CardRater cardRater, int ourCards) {
        int bet = foldOrCheck();

        if (ourCards > 0) {
            if (bet_index > 2) {
                bet = foldOrCheck();
            } else {
                bet = call();
            }
        }

        if (ourCards > 60) { // trippels
            bet = raise(3);
        } else if (ourCards > 40) { // two pairs
            bet = raise(2);
        } else if (ourCards > 20) { // one pairs
            bet = raise(1);
        }

        if (isAllIn(bet)) {
            if (ourCards < 9) {
                bet = foldOrCheck();
            }
        }

        if (bet == call() && bet > 20) {
            if (ourCards < 25) {
                bet = foldOrCheck();
            }
        }
        return bet;
    }

    private PlayerState we() {
        return players[in_action];
    }

    private boolean isAllIn(int bet) {
        return we().stack <= bet;
    }


    public int foldOrCheck() {
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
