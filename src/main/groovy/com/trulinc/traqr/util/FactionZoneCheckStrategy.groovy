package com.trulinc.traqr.util

import com.trulinc.traqr.domain.ZoneLog
import com.trulinc.traqr.qonqr.QonqrZoneResponse

public abstract class FactionZoneCheckStrategy {

    public abstract long getFriendlyBotCount(ZoneLog zoneLog)

    public abstract long getFriendlyBotCount(QonqrZoneResponse qonqrZoneResponse)

    public abstract long getEnemyBotCount(ZoneLog zoneLog)

    public abstract long getEnemyBotCount(QonqrZoneResponse qonqrZoneResponse)

    public abstract Faction getSurveyingFaction()

    public ZoneChangeType determineZoneChange(ZoneLog previousZoneLog, QonqrZoneResponse currentResponse) {
        ZoneChangeType zoneChangeType = ZoneChangeType.NoChange

        if (previousZoneLog) {
            if (surveyingFaction != previousZoneLog?.controlState && surveyingFaction == currentResponse?.controlState) {
                zoneChangeType = ZoneChangeType.ZoneGained
            } else if (surveyingFaction == previousZoneLog?.controlState && surveyingFaction != currentResponse?.controlState) {
                zoneChangeType = ZoneChangeType.ZoneLost
            } else if (surveyingFaction != currentResponse?.controlState) {
                //Attacking
                if (getFriendlyBotCount(previousZoneLog) < getFriendlyBotCount(currentResponse)) {
                    zoneChangeType = ZoneChangeType.AttackingBotIncrease
                } else if (getFriendlyBotCount(previousZoneLog) > getFriendlyBotCount(currentResponse)) {
                    zoneChangeType = ZoneChangeType.AttackingBotDecrease
                } else if (getFriendlyBotCount(currentResponse) > 0 && getFriendlyBotCount(previousZoneLog) == getFriendlyBotCount(currentResponse)) {
                    zoneChangeType = ZoneChangeType.AttackingNoChange
                } else if (getFriendlyBotCount(currentResponse) == 0 && getEnemyBotCount(currentResponse) > getEnemyBotCount(previousZoneLog)) {
                    zoneChangeType = ZoneChangeType.EnemyStacking
                }
            } else if (surveyingFaction == currentResponse?.controlState) {
                //Defending
                if (getFriendlyBotCount(previousZoneLog) == getFriendlyBotCount(currentResponse) && getEnemyBotCount(currentResponse) > 0) {
                    zoneChangeType = ZoneChangeType.NeedsClear
                } else if (getFriendlyBotCount(previousZoneLog) < getFriendlyBotCount(currentResponse) && getEnemyBotCount(currentResponse) > 0) {
                    zoneChangeType = ZoneChangeType.StackingNeedsClear
                } else if (getFriendlyBotCount(previousZoneLog) < getFriendlyBotCount(currentResponse) && getEnemyBotCount(currentResponse) == 0) {
                    zoneChangeType = ZoneChangeType.Stacking
                } else if (getFriendlyBotCount(previousZoneLog) > getFriendlyBotCount(currentResponse) && getEnemyBotCount(currentResponse) > 0) {
                    zoneChangeType = ZoneChangeType.UnderAttack
                } else if (getFriendlyBotCount(previousZoneLog) > getFriendlyBotCount(currentResponse) && getEnemyBotCount(currentResponse) == 0) {
                    zoneChangeType = ZoneChangeType.UnderAttackPlasma
                }
            }
        } else {
            if (surveyingFaction == currentResponse?.controlState) {
                //Defending
                if (getEnemyBotCount(currentResponse) > 0) {
                    zoneChangeType = ZoneChangeType.NeedsClear
                } else {
                    zoneChangeType = ZoneChangeType.Clear
                }
            } else if(surveyingFaction != currentResponse?.controlState) {
                //Attacking
                if (getFriendlyBotCount(currentResponse) > 0) {
                    zoneChangeType = ZoneChangeType.AttackingNoChange
                } else {
                    zoneChangeType = ZoneChangeType.EnemyStacking
                }
            }
        }

        return zoneChangeType
    }
}