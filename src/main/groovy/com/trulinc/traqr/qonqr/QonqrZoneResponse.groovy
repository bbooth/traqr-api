package com.trulinc.traqr.qonqr

import com.trulinc.traqr.util.Faction
import groovy.transform.ToString

@ToString(includes = "zoneName, zoneId, controlState, legionCount, facelessCount, swarmCount", includeNames = true, ignoreNulls = true, includePackage = false)
class QonqrZoneResponse {
    Long zoneId
    String zoneName
    Long regionId
    String regionName
    String regionCode
    Long countryId
    String countryName
    String countryCode
    Map location
    Faction controlState
    Date dateCapturedUTC
    Long legionCount
    Long swarmCount
    Long facelessCount
//    String gridRef
    Date lastUpdateDateUtc
}
