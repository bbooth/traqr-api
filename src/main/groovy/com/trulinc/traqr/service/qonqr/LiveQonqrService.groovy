package com.trulinc.traqr.service.qonqr

import com.traqr.domain.ZoneLog
import com.traqr.util.Faction
import com.traqr.util.QonqrRestBuilder
import grails.transaction.Transactional
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

@Transactional
public class LiveQonqrService implements QonqrService {

    def grailsApplication

    @Override
    public List<ZoneLog> getAllZonesInLatLongBox(DateTime surveillanceDateTime, double topLatitude, double leftLongitude, double bottomLatitude, double rightLongitude) {
        def qonqr = grailsApplication.config.qonqr

//        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress('us-east-1-static-hopper.quotaguard.com', 9293))
//        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress('velodrome.usefixie.com', 80))
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress('us-east-1-static-hopper.statica.io', 9293))

        Authenticator authenticator = new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
//                return (new PasswordAuthentication("quotaguard4421", "6188ed4afbe5".toCharArray()));
//                return (new PasswordAuthentication("fixie", "7QdXh3HlgVinVa4".toCharArray()));
                return (new PasswordAuthentication("statica975", "cf5c0e6a6081ba09".toCharArray()));
            }
        }

        Authenticator.setDefault(authenticator);

        def rest = new QonqrRestBuilder(proxy)
//        def rest = new QonqrRestBuilder(null)

        def response = rest.get("${qonqr.api.url}/pub/zones/BoundingBoxStatus/${topLatitude}/${leftLongitude}/${bottomLatitude}/${rightLongitude}") {
            header 'ApiKey', qonqr.api.key
            header 'ApiSecret', qonqr.api.secret
        }

        if (response.status == 200) {
            log.info "Returned ${response.json.Count} zones"

            def dateTimeFormat = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss'Z'").withZoneUTC()

            return response.json.Zones.collect {
                try {
                    QonqrZoneResponse qonqrZoneResponse = new QonqrZoneResponse()
                    qonqrZoneResponse.zoneId = it.ZoneId
                    qonqrZoneResponse.zoneName = it.ZoneName
                    qonqrZoneResponse.regionId = it.RegionId
                    qonqrZoneResponse.regionName = it.RegionName
                    qonqrZoneResponse.regionCode = it.RegionCode
                    qonqrZoneResponse.countryId = it.CountryId
                    qonqrZoneResponse.countryName = it.CountryName
                    qonqrZoneResponse.countryCode = it.CountryCode
                    qonqrZoneResponse.location = [lat: it.Latitude, long: it.Longitude]
                    qonqrZoneResponse.controlState = Faction.fromQonqrValue(it.ControlState)
                    qonqrZoneResponse.dateCapturedUTC = it.DateCapturedUTC ? DateTime.parse(it.DateCapturedUTC, dateTimeFormat).toDate() : surveillanceDateTime.toDate()
                    qonqrZoneResponse.legionCount = it.LegionCount
                    qonqrZoneResponse.swarmCount = it.SwarmCount
                    qonqrZoneResponse.facelessCount = it.FacelessCount
//                    qonqrZoneResponse.gridRef = it.GridRef
                    qonqrZoneResponse.lastUpdateDateUtc = it.LastUpdateDateUtc ? DateTime.parse(it.LastUpdateDateUtc, dateTimeFormat).toDate() : surveillanceDateTime.toDate()

                    return qonqrZoneResponse
                } catch (Exception e) {
                    log.error e
                    log.debug it
                    return null
                }
            }
        } else {
            throw new QonqrApiException("Unable to communicate with Qonqr API. Response: ${response.status}\n${response.text}")
        }
    }

}
