package org.leanpoker.player;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.math.NumberUtils;

/**
 * ...
 */
class CardRater {

    Cards[] allCards;
    int numCards;
    int remainingCards;

    int[] suits = new int[Suit.values().length];
    int[] ranks = new int[Rank.values().length];

    List<Rank> pairs = new ArrayList<>();
    List<Rank> trippels = new ArrayList<>();
    List<Rank> four = new ArrayList<>();

    int pairsWithHand = 0;
    int trippelsWithHand = 0;
    int fourWithHand = 0;

    int maxSuitCount = 0;

    static final int VERY_GOOD = 1000;

    CardRater(Cards[] allCards) {
        this.allCards = allCards;
        this.numCards = allCards.length;
        this.remainingCards = 7 - numCards;
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
                return VERY_GOOD;
            } else if (count == 4 && remainingCards > 0) {
                result += 25;
            }
        }

        if (!four.isEmpty()) {
            if (fourWithHand > 0) {
                return VERY_GOOD;
            }
        }
        if (!trippels.isEmpty()) {
            if (!pairs.isEmpty()) {
                return VERY_GOOD;
            }
            if (trippelsWithHand > 0) {
                result += 60;
                result += trippels.get(0).getFactor();
            }
        }

        if (pairs.size() == 3) {
            if (pairsWithHand > 0) {
                result += 20;
            }
            if (pairsWithHand > 1) {
                result += 20;
            }
            result += NumberUtils.max(pairs.get(0).getFactor(), pairs.get(1).getFactor(), pairs.get(2).getFactor());
        }
        if (pairs.size() == 2) {
            if (pairsWithHand > 0) {
                result += 20;
            }
            if (pairsWithHand > 1) {
                result += 20;
            }
            result += Math.max(pairs.get(0).getFactor(), pairs.get(1).getFactor());
        }
        if (pairs.size() == 1) {
            if (pairsWithHand > 0) {
                result += 20;
            }
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

        for (Rank r : pairs) {
            if (allCards[0].getRank() == r || allCards[1].getRank() == r) {
                pairsWithHand++;
            }
        }
        for (Rank r : trippels) {
            if (allCards[0].getRank() == r || allCards[1].getRank() == r) {
                trippelsWithHand++;
            }
        }
        for (Rank r : four) {
            if (allCards[0].getRank() == r || allCards[1].getRank() == r) {
                fourWithHand++;
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
