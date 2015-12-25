package com.trulinc.traqr.service.groupme

import com.traqr.util.QonqrRestBuilder
import grails.transaction.Transactional

@Transactional
public class GroupMeService {

    def grailsApplication

    public void postMessage(String groupMeBotToken, String message) {
        postMessage(groupMeBotToken, message, null, null, null);
    }

    public void postMessage(String groupMeBotToken, String message, Double latitude, Double longitude, String locationName) {
        if (grailsApplication.config.groupme.enabled) {
            log.info "Posted message '${message}' in GroupMe(${groupMeBotToken})"

            def rest = new QonqrRestBuilder(null)
            def response = rest.post("${grailsApplication.config.groupme.api.url}/bots/post") {
                json {
                    bot_id = groupMeBotToken
                    text = message
//                    if (latitude && longitude) {
//                        attachments = [[type: "location", lng: longitude, lat: latitude, name: locationName]]
//                    }
                }
            }
        }
    }
}
