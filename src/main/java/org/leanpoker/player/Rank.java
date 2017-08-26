package org.leanpoker.player;

/**
 * ...
 */
enum Rank {
    N02(1),
    N03(2),
    N04(3),
    N05(4),
    N06(5),
    N07(6),
    N08(7),
    N09(8),
    N10(9),
    Jack(10),
    Queen(11),
    King(12),
    As(13),

    // dummy
    N00(0),
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
            case "10":
                return N10;
            case "9":
                return N09;
            case "8":
                return N08;
            case "7":
                return N07;
            case "6":
                return N06;
            case "5":
                return N05;
            case "4":
                return N04;
            case "3":
                return N03;
            case "2":
                return N02;
            default:
                System.out.println("could not parse Rank '" + s + "'");
                return N00;
        }
    }
}
