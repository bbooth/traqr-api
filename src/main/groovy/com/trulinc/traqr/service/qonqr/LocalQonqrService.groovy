package com.trulinc.traqr.service.qonqr

import com.traqr.domain.ZoneLog
import grails.transaction.Transactional
import org.joda.time.DateTime

@Transactional
public class LocalQonqrService implements QonqrService {

    @Override
    public List<ZoneLog> getAllZonesInLatLongBox(DateTime surveillanceDateTime, double topLatitude, double leftLongitude, double bottomLatitude, double rightLongitude) {
        return []
    }

}
