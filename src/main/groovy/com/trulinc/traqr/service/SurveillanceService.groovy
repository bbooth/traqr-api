package com.trulinc.traqr.service

import com.traqr.domain.Quadrant
import com.traqr.domain.Zone
import com.traqr.domain.ZoneLog
import com.traqr.qonqr.QonqrZoneResponse
import com.traqr.util.FactionZoneCheckFactory
import com.traqr.util.SurveillanceResult
import com.traqr.util.ZoneChangeType
import grails.transaction.Transactional
import org.joda.time.DateTime

@Transactional
public class SurveillanceService {

    def qonqrService
    def groupMeQonqrService

    public List<SurveillanceResult> conductSurveillance(Quadrant quadrant) {
        def retVal = []
        DateTime now = new DateTime()
        List<QonqrZoneResponse> qonqrZoneResponses = qonqrService.getAllZonesInLatLongBox(now, quadrant.getTopLatitude(), quadrant.getLeftLongitude(), quadrant.getBottomLatitude(), quadrant.getRightLongitude())
        log.info "Conducting surveillance on ${qonqrZoneResponses.size()} zones in ${quadrant.zoneName}"

        qonqrZoneResponses.eachWithIndex { QonqrZoneResponse qonqrZoneResponse, int i ->
            try {
                SurveillanceResult surveillanceResult = updateZone(quadrant, now, qonqrZoneResponse, i)

                retVal << surveillanceResult
            } catch (Exception e) {
                log.error e
            }
        }

        quadrant.lastUpdatedDateTime = now.toDate()
        if (!quadrant.save()) {
            log.error "Unable to save surveillance zone ${quadrant.errors}"
        }

        return retVal
    }

    public SurveillanceResult updateZone(Quadrant quadrant, DateTime surveillanceTimestamp, QonqrZoneResponse qonqrZoneResponse, Integer updateIndex) {
        SurveillanceResult retVal = null

        ZoneLog previousZoneLog = ZoneLog.findByZoneIdAndSurveyingFaction(qonqrZoneResponse.zoneId, quadrant.faction, [sort: 'logDate', order: 'desc'])

        ZoneLog zoneLog = new ZoneLog()
        zoneLog.zoneId = qonqrZoneResponse.zoneId
        zoneLog.logDate = surveillanceTimestamp.toDate()
        zoneLog.zoneChangeType = FactionZoneCheckFactory.instance(quadrant.faction).determineZoneChange(previousZoneLog, qonqrZoneResponse)
        zoneLog.surveyingFaction = quadrant.faction
        zoneLog.controlState = qonqrZoneResponse.controlState
        zoneLog.legionCount = qonqrZoneResponse.legionCount
        zoneLog.swarmCount = qonqrZoneResponse.swarmCount
        zoneLog.facelessCount = qonqrZoneResponse.facelessCount
        zoneLog.updateIndex = updateIndex

        Zone zone = Zone.findByZoneId(qonqrZoneResponse.zoneId)
        if (!zone) {
            zone = new Zone()
        }

        zone.zoneId = qonqrZoneResponse.zoneId
        zone.zoneName = qonqrZoneResponse.zoneName
        zone.regionId = qonqrZoneResponse.regionId
        zone.regionName = qonqrZoneResponse.regionName
        zone.regionCode = qonqrZoneResponse.regionCode
        zone.controlState = qonqrZoneResponse.controlState
        zone.countryId = qonqrZoneResponse.countryId
        zone.countryName = qonqrZoneResponse.countryName
        zone.countryCode = qonqrZoneResponse.countryCode
        zone.location = qonqrZoneResponse.location
        zone.dateCapturedUTC = qonqrZoneResponse.dateCapturedUTC
        zone.lastUpdatedDateTime = surveillanceTimestamp.toDate()
        zone.legionCount = qonqrZoneResponse.legionCount
        zone.facelessCount = qonqrZoneResponse.facelessCount
        zone.swarmCount = qonqrZoneResponse.swarmCount
        zone.lastUpdateDateUtc = qonqrZoneResponse.lastUpdateDateUtc
        zone.updateIndex = updateIndex

        if (!isDuplicateLog(previousZoneLog, zoneLog)) {
            zone.lastZoneChangeType = zoneLog.zoneChangeType
            zone.lastChangeDateTime = surveillanceTimestamp.toDate()

            if (!zoneLog.save()) {
                log.error "Unable to save zone log ${zoneLog.errors}"
            }
        } else {
            log.debug "${zoneLog} duplicate so discarding"
            zoneLog.discard()
        }

        if (zone.save()) {
            retVal = new SurveillanceResult(
                    zoneId: zone?.zoneId,
                    zoneName: zone?.zoneName,
                    latitude: zone?.location?.lat,
                    longitude: zone?.location?.long,
                    zoneChangeType: zoneLog.zoneChangeType,
                    surveillanceDate: zoneLog?.logDate,
                    legionCount: zoneLog.legionCount,
                    facelessCount: zoneLog.facelessCount,
                    swarmCount: zoneLog.swarmCount
            )

            if (groupMeQonqrService.sendNotification(quadrant, retVal)) {
                zoneLog.notificationSent = true
                zoneLog.save()
            }
        } else {
            log.error "Unable to save zone log ${zone.errors}"
        }

        if (zoneLog.zoneChangeType != ZoneChangeType.Clear && zoneLog.zoneChangeType != ZoneChangeType.NoChange && zoneLog.zoneChangeType != ZoneChangeType.AttackingNoChange) {
            log.info "${zone?.zoneName} - ${zoneLog.zoneChangeType}"
            log.debug "${zone} ${zoneLog}"
        }

        return retVal
    }

    private static boolean isDuplicateLog(ZoneLog previousZoneLog, ZoneLog zoneLog) {
        boolean retVal = true

        if (previousZoneLog) {
            retVal &= zoneLog.legionCount == previousZoneLog.legionCount
            retVal &= zoneLog.facelessCount == previousZoneLog.facelessCount
            retVal &= zoneLog.swarmCount == previousZoneLog.swarmCount
        } else {
            retVal = false
        }

        return retVal
    }

}
