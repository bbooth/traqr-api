package com.trulinc.traqr.domain

import com.trulinc.traqr.util.Faction

class Quadrant {

//    static mapWith = "mongo"
    static transients = ['box']

    static constraints = {
        lastUpdatedDateTime(nullable: true)
    }

    String id
    String groupMeBotToken
    String groupMeGroupId
    Double topLatitude
    Double bottomLatitude
    Double leftLongitude
    Double rightLongitude
    String zoneName
    Faction faction
    Date lastUpdatedDateTime

    def getBox() {
        return [[topLatitude, leftLongitude], [bottomLatitude, rightLongitude]]
    }
}
