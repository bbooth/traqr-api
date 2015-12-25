package com.trulinc.traqr.util

import com.trulinc.traqr.domain.ZoneLog
import com.trulinc.traqr.qonqr.QonqrZoneResponse

public class LegionZoneCheckStrategy extends FactionZoneCheckStrategy {

    @Override
    Faction getSurveyingFaction() {
        Faction.Legion
    }

    @Override
    long getFriendlyBotCount(ZoneLog zoneLog) {
        return zoneLog?.legionCount
    }

    @Override
    long getFriendlyBotCount(QonqrZoneResponse qonqrZoneResponse) {
        return qonqrZoneResponse?.legionCount
    }

    @Override
    long getEnemyBotCount(ZoneLog zoneLog) {
        return zoneLog?.facelessCount + zoneLog?.swarmCount
    }

    @Override
    long getEnemyBotCount(QonqrZoneResponse qonqrZoneResponse) {
        return qonqrZoneResponse?.facelessCount + qonqrZoneResponse?.swarmCount
    }
}
