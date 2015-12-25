package com.trulinc.traqr.service.groupme

import com.trulinc.traqr.domain.Quadrant
import com.trulinc.traqr.domain.ZoneLog
import com.trulinc.traqr.service.google.GoogleService
import com.trulinc.traqr.util.SurveillanceResult
import com.trulinc.traqr.util.ZoneChangeType
import org.joda.time.DateTime
import org.springframework.context.MessageSource

public class GroupMeQonqrService {

    GoogleService googleService
    GroupMeService groupMeService
    MessageSource messageSource

    public boolean sendNotification(Quadrant surveillanceZone, SurveillanceResult surveillanceResult) {
        boolean notificationSent = false

        String message = null

        ZoneChangeType zoneChangeType = surveillanceResult?.zoneChangeType
        switch (zoneChangeType) {
            case ZoneChangeType.UnderAttackPlasma:
                message = messageSource.getMessage("groupme.zone.bot.decrease", [surveillanceResult.getZoneName(), surveillanceResult?.getLegionCount(), surveillanceResult?.getFacelessCount(), surveillanceResult?.getSwarmCount(), googleService.shortenMapUrl(surveillanceResult.latitude, surveillanceResult.longitude)].toArray(), null)
                break

            case ZoneChangeType.UnderAttack:
            case ZoneChangeType.StackingNeedsClear:
            case ZoneChangeType.NeedsClear:
                message = messageSource.getMessage("groupme.zone.not.clear", [surveillanceResult.getZoneName(), surveillanceResult?.getLegionCount(), surveillanceResult?.getFacelessCount(), surveillanceResult?.getSwarmCount(), googleService.shortenMapUrl(surveillanceResult.latitude, surveillanceResult.longitude)].toArray(), null)
                break

            case ZoneChangeType.Stacking:
            case ZoneChangeType.Clear:
                break

            case ZoneChangeType.ZoneGained:
                message = messageSource.getMessage("groupme.zone.gained", [surveillanceResult.getZoneName(), surveillanceResult?.getLegionCount(), surveillanceResult?.getFacelessCount(), surveillanceResult?.getSwarmCount(), googleService.shortenMapUrl(surveillanceResult.latitude, surveillanceResult.longitude)].toArray(), null)
                break

            case ZoneChangeType.ZoneLost:
                message = messageSource.getMessage("groupme.zone.lost", [surveillanceResult.getZoneName(), surveillanceResult?.getLegionCount(), surveillanceResult?.getFacelessCount(), surveillanceResult?.getSwarmCount(), googleService.shortenMapUrl(surveillanceResult.latitude, surveillanceResult.longitude)].toArray(), null)
                break

            default:
                break
        }

        if (needsNotify(surveillanceResult) && message) {
            log.debug message

            groupMeService.postMessage(surveillanceZone.getGroupMeBotToken(), message, surveillanceResult.latitude, surveillanceResult.longitude, surveillanceResult.getZoneName())
            notificationSent = true
        }

        return notificationSent
    }

    private static boolean needsNotify(SurveillanceResult surveillanceResult) {
        DateTime anHourAgo = new DateTime(surveillanceResult?.surveillanceDate).minusHours(1)

        long count = ZoneLog.countByZoneIdAndNotificationSentAndLogDateGreaterThanEquals(surveillanceResult.zoneId, true, anHourAgo.toDate())

        return count == 0
    }

    public void allClear(Quadrant surveillanceZone) {
        String message = messageSource.getMessage("groupme.zone.clear", null, null)
        log.debug message
        groupMeService.postMessage(surveillanceZone.getGroupMeBotToken(), message)
    }

    public void needsClearing(Quadrant surveillanceZone, SurveillanceResult surveillanceResult) {
        String message = messageSource.getMessage("groupme.zone.not.clear", [surveillanceResult.getZoneName(), surveillanceResult.getLegionCount(), surveillanceResult.getFacelessCount(), surveillanceResult.getSwarmCount(), googleService.shortenMapUrl(surveillanceResult.latitude, surveillanceResult.longitude)].toArray(), null)
        log.debug message
        groupMeService.postMessage(surveillanceZone.getGroupMeBotToken(), message, surveillanceResult.latitude, surveillanceResult.longitude, surveillanceResult.getZoneName())
    }

    public void communicationsError(Quadrant surveillanceZone, Exception e) {
        String message = messageSource.getMessage("groupme.qonqr.communication.error", [e.message].toArray(), null)
        log.error message, e
        groupMeService.postMessage(surveillanceZone.getGroupMeBotToken(), message);
    }
}
