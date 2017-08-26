package org.leanpoker.player;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * ...
 */
public class PlayerState {

    public String name;
    public int stack;
    public String status;
    public int bet;
    public Cards[] hole_cards;
    public String version;
    public int id;


    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
