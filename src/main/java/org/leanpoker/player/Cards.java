package org.leanpoker.player;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * ...
 */
public class Cards {
    public String rank; // 2-10 and J,Q,K,A
    public Suit suit;


    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}