package com.trulinc.traqr.qonqr

import com.trulinc.traqr.domain.ZoneLog

public interface QonqrService {

    public List<ZoneLog> getAllZonesInLatLongBox(Formatter.DateTime surveillanceDateTime, double topLatitude, double leftLongitude, double bottomLatitude, double rightLongitude)

}
