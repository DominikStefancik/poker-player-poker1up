package org.leanpoker.player;

/**
 * ...
 */
enum Rank {
    Number,
    Jack,
    Queen,
    King,
    As,

    ;

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
