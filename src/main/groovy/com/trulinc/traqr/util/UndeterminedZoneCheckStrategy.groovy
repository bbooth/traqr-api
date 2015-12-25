package com.trulinc.traqr.util

import com.trulinc.traqr.domain.ZoneLog
import com.trulinc.traqr.qonqr.QonqrZoneResponse


class UndeterminedZoneCheckStrategy extends FactionZoneCheckStrategy {

    @Override
    Faction getSurveyingFaction() {
        Faction.Uncontrolled
    }

    @Override
    long getFriendlyBotCount(ZoneLog zoneLog) {
        return 0
    }

    @Override
    long getFriendlyBotCount(QonqrZoneResponse qonqrZoneResponse) {
        return 0
    }

    @Override
    long getEnemyBotCount(ZoneLog zoneLog) {
        return 0
    }

    @Override
    long getEnemyBotCount(QonqrZoneResponse qonqrZoneResponse) {
        return 0
    }
}
