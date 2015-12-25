package com.trulinc.traqr.util

public enum Faction {

    Uncontrolled(0),
    Legion(1),
    Swarm(2),
    Faceless(3)

    private int qonqrValue

    public Faction(int qonqrValue) {
        this.qonqrValue = qonqrValue
    }

    public int getQonqrValue() {
        return qonqrValue
    }

    public static Faction fromQonqrValue(int qonqrValue) {
        for(faction in values()) {
            if(faction.qonqrValue == qonqrValue) {
                return faction
            }
        }

        return null
    }

    public String toString() {
        this.name()
    }
}