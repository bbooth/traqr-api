package com.trulinc.traqr.util;

public class FactionZoneCheckFactory {
    public static FactionZoneCheckStrategy instance(Faction controlState) {
        FactionZoneCheckStrategy retVal

        switch (controlState) {
            case Faction.Uncontrolled:
                retVal = new UndeterminedZoneCheckStrategy()
                break;

            case Faction.Legion:
                retVal = new LegionZoneCheckStrategy()
                break;

            case Faction.Swarm:
                retVal = new SwarmZoneCheckStrategy()
                break;

            case Faction.Faceless:
                retVal = new FacelessZoneCheckStrategy()
                break;

        }

        return retVal
    }
}
