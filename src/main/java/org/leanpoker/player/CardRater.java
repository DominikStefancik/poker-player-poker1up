package org.leanpoker.player;

import java.util.ArrayList;
import java.util.List;

/**
 * ...
 */
class CardRater {

    Cards[] allCards;
    List<Rank> pairs = new ArrayList<>();
    List<Rank> trippels = new ArrayList<>();
    List<Rank> four = new ArrayList<>();
    int[] suits = new int[Suit.values().length];
    int[] ranks = new int[Rank.values().length];
    int maxSuitCount = 0;

    CardRater(Cards[] allCards) {
        this.allCards = allCards;
    }

    public int rate() {

        int result = 0;

        for (int i = 0; i < ranks.length; i++) {
            ranks[i] = 0;
        }

        for (int i = 0; i < suits.length; i++) {
            suits[i] = 0;
        }

        for (final Cards card : allCards) {
            final int r = card.getRank().ordinal();
            final int s = card.suit.ordinal();
            ranks[r]++;
            suits[s]++;
        }


        for (int count : suits) {
            if (count > maxSuitCount) {
                maxSuitCount = count;
            }
        }

        for (int i = 0; i < ranks.length; i++) {
            final int count = ranks[i];
            final Rank r = Rank.values()[i];
            if (count == 4) {
                four.add(r);
            } else if (count == 3) {
                trippels.add(r);
            } else if (count == 2) {
                pairs.add(r);
            }
        }

        if (allCards.length == 2) {
            return rankHand();
        }

        for (int count : suits) {
            if (count >= 5) {
                return 50;
            }

            if (count == 4 && allCards.length < 7) {
                return 25;
            }
        }


        if (!four.isEmpty()) {
            result = 100;
        }
        if (!trippels.isEmpty()) {
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

    private int rankHand() {
        if (!pairs.isEmpty()) {
            return pairs.get(0).getFactor() + 20;
        }

        if (maxSuitCount == 2) {
            return 20;
        }


        for (int i = ranks.length-1; i > 0; i--) {
            if (ranks[i] > 0) {
                return i;
            }
        }

        return 0;
    }
}
