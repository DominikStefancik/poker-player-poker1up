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
    
    int big_blind;

    public int betRequest() {
        big_blind = 2 * small_blind;
        System.out.println("======================================================");
        //System.out.println("gameState: " + this);


        final Cards[] allCards = ArrayUtils.addAll(we().hole_cards, community_cards);
        final CardRater cardRater = new CardRater(allCards);
        final int ratingOfCards = cardRater.rate();
        int bet;

        if (community_cards.length == 0) {
            bet = call();


            System.out.println("nothing on the table - bet: " + bet + " ratingOfCards: " + ratingOfCards + " mr: " + small_blind);
            if (bet > 4 * big_blind && ratingOfCards < 10) {
                bet = foldOrCheck();
            }
            if (bet > 10 * big_blind && ratingOfCards < 20) {
                bet = foldOrCheck();
            }
            if (bet > 20 * big_blind && ratingOfCards < 40) {
                bet = foldOrCheck();
            }
            if (isAllIn(bet) && ratingOfCards < 50) {
                bet = foldOrCheck();
            }
        } else {

            if (ratingOfCards == VERY_GOOD) {
                bet = raise(20);
            } else {
                bet = getComplicatedBet(cardRater, ratingOfCards);
            }
        }

        bet = Math.max(0, bet);

        System.out.println("ratingOfCards: " + ratingOfCards);
        System.out.println("bet: " + bet);
        return bet;
    }

    private int getComplicatedBet(CardRater weWantToFold, int ratingOfCards) {
        int bet = foldOrCheck();

        if (call() > 0 && weWantToFold(weWantToFold, ratingOfCards)) {
            return foldOrCheck();
        }

        if (ratingOfCards > 0) {
            if (bet_index > 2) {
                bet = foldOrCheck();
            } else {
                bet = call();
            }
        }

        if (ratingOfCards > 60) { // trippels
            bet = raise(3);
        } else if (ratingOfCards > 40) { // two pairs
            bet = raise(2);
        } else if (ratingOfCards > 20) { // one pairs
            bet = raise(1);
        }

        if (isAllIn(bet)) {
            if (ratingOfCards < 9) {
                bet = foldOrCheck();
            }
        }

        if (bet == call() && bet > 20) {
            if (ratingOfCards < 25) {
                bet = foldOrCheck();
            }
        }
        return bet;
    }

    private boolean weWantToFold(CardRater cardRater, int ratingOfCards) {
        int chipsForCalling = call();
        int ourChips = we().stack;
        int cardsOnTable = cardRater.cardsOnTable;

        if (ratingOfCards < 20) {
            return chipsForCalling >= 20 * big_blind;
        }
        if (ratingOfCards < 30) {
            return chipsForCalling >= 30 * big_blind;
        }
        if (ratingOfCards < 40) {
            return chipsForCalling >= 50 * big_blind;
        }
        if (ratingOfCards < 60) {
            return chipsForCalling >= 70 * big_blind;
        }

        return false;
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
        // current_buy_in - players[in_action][bet] + big_blind
        return current_buy_in - we().bet + (big_blind * factor);
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
        sb.append(", big_blind=").append(big_blind);
        sb.append(", dealer=").append(dealer);
        sb.append(", current_buy_in=").append(current_buy_in);
        sb.append(", pot=").append(pot);
        sb.append('}');
        return sb.toString();
    }
}
