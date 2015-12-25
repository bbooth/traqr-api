package com.trulinc.traqr.service.google

import com.trulinc.traqr.util.QonqrRestBuilder

class GoogleService {

    def grailsApplication

    public String shortenMapUrl(double latitude, double longitude) {

        def rest = new QonqrRestBuilder()
        def response = rest.post("${grailsApplication.config.google.api.url}/urlshortener/v1/url?key=${grailsApplication.config.google.api.key}") {
            json {
                longUrl = "http://maps.google.com/maps?q=${latitude},${longitude}"
            }
        }

        return response.json.id;
    }
}
