package com.trulinc.traqr.domain

import com.trulinc.traqr.util.Faction
import com.trulinc.traqr.util.ZoneChangeType
import groovy.transform.ToString

@ToString(includes = "zoneName, zoneId, controlState, legionCount, facelessCount, swarmCount", includeNames = true, ignoreNulls = true, includePackage = false)
class Zone {

    String id

    long zoneId
    String zoneName
    long regionId
    String regionName
    String regionCode
    Faction controlState
    long countryId
    String countryName
    String countryCode
    Map location
    Date dateCapturedUTC
    Date lastUpdateDateUtc

    Date lastUpdatedDateTime
    Date lastChangeDateTime
    Integer updateIndex
    ZoneChangeType lastZoneChangeType

    long legionCount
    long swarmCount
    long facelessCount

    static constraints = {

    }
}
