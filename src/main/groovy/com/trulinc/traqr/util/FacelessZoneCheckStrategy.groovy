package com.trulinc.traqr.util

import com.trulinc.traqr.domain.ZoneLog
import com.trulinc.traqr.qonqr.QonqrZoneResponse

public class FacelessZoneCheckStrategy extends FactionZoneCheckStrategy {

    @Override
    Faction getSurveyingFaction() {
        Faction.Faceless
    }

    @Override
    long getFriendlyBotCount(ZoneLog zoneLog) {
        return zoneLog?.facelessCount
    }

    @Override
    long getFriendlyBotCount(QonqrZoneResponse qonqrZoneResponse) {
        return qonqrZoneResponse?.facelessCount
    }

    @Override
    long getEnemyBotCount(ZoneLog zoneLog) {
        return zoneLog?.legionCount + zoneLog?.swarmCount
    }

    @Override
    long getEnemyBotCount(QonqrZoneResponse qonqrZoneResponse) {
        return qonqrZoneResponse?.legionCount + qonqrZoneResponse?.swarmCount
    }
}
