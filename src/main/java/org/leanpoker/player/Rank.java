package org.leanpoker.player;

/**
 * ...
 */
enum Rank {
    Number(5),
    Jack(17),
    Queen(18),
    King(19),
    As(20),

    ;

    private final int rank;

    Rank(int i) {
        this.rank = i;
    }

    public int getFactor() {
        return rank;
    }

    public static Rank fromString(String s) {
        switch (s) {
            case "A":
                return As;
            case "K":
                return King;
            case "Q":
                return Queen;
            case "J":
                return Jack;
            default:
                return Number;
        }
    }
}
