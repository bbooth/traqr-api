package com.trulinc.traqr.util

import com.trulinc.traqr.domain.ZoneLog
import com.trulinc.traqr.qonqr.QonqrZoneResponse

public class SwarmZoneCheckStrategy extends FactionZoneCheckStrategy {

    @Override
    Faction getSurveyingFaction() {
        Faction.Swarm
    }

    @Override
    long getFriendlyBotCount(ZoneLog zoneLog) {
        return zoneLog?.swarmCount
    }

    @Override
    long getFriendlyBotCount(QonqrZoneResponse qonqrZoneResponse) {
        return qonqrZoneResponse?.swarmCount
    }

    @Override
    long getEnemyBotCount(ZoneLog zoneLog) {
        return zoneLog?.facelessCount + zoneLog?.legionCount
    }

    @Override
    long getEnemyBotCount(QonqrZoneResponse qonqrZoneResponse) {
        return qonqrZoneResponse?.facelessCount + qonqrZoneResponse?.legionCount
    }
}
