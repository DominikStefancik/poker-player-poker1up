package org.leanpoker.player;

/**
 * ...
 */
public class Cards {
    public String rank; // 2-10 and J,Q,K,A
    public Suit suit;

    public Rank getRank() {
        return Rank.fromString(rank);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Cards{");
        sb.append("rank='").append(rank).append('\'');
        sb.append(", suit=").append(suit);
        sb.append('}');
        return sb.toString();
    }
}
