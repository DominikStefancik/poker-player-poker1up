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
    int numCards;

    CardRater(Cards[] allCards) {
        this.allCards = allCards;
        this.numCards = allCards.length;
    }

    public int rate() {

        collectCardData();

        if (numCards == 2) {
            return rankHand();
        }

        return rateHandAndTable();
    }

    private int rateHandAndTable() {
        int result = 0;

        for (int count : suits) {
            if (count >= 5) {
                result += 50;
            } else if (count == 4 && numCards < 7) {
                result += 25;
            }
        }

        if (!four.isEmpty()) {
            result += 100;
        }
        if (!trippels.isEmpty()) {
            if (!pairs.isEmpty()) {
                result += 200;
            }
            result += 50;
        }
        if (pairs.size() == 2) {
            result += 25;
        }
        if (pairs.size() == 1) {
            result += pairs.get(0).getFactor();
        }


        return result;
    }

    private void collectCardData() {
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
    }

    private int rankHand() {
        int result = 0;
        if (!pairs.isEmpty()) {
            result += 40;
        }

        if (maxSuitCount == 2) {
            result += 20;
        }


        for (int i = ranks.length - 1; i > 0; i--) {
            if (ranks[i] > 0) {
                result += i;
            }
        }

        return result;
    }
}
