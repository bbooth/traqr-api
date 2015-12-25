package com.trulinc.traqr.domain

import com.trulinc.traqr.util.Faction
import com.trulinc.traqr.util.ZoneChangeType
import groovy.transform.ToString

@ToString(includes = "logDate, controlState, legionCount, facelessCount, swarmCount", includeNames = true, ignoreNulls = true, includePackage = false)
class ZoneLog {

//    static mapWith = "mongo"
    static mapping = {
        compoundIndex zone:1, logDate: -1
    }

    String id
    long zoneId

    Date logDate
    ZoneChangeType zoneChangeType
    Faction surveyingFaction
    boolean notificationSent = false
    Integer updateIndex

    Faction controlState

    long legionCount
    long swarmCount
    long facelessCount

    static constraints = {

    }
}
