package com.trulinc.traqr.util

import grails.plugins.rest.client.RestBuilder
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.web.client.RestTemplate

class QonqrRestBuilder extends RestBuilder {

    public QonqrRestBuilder(Proxy proxy) {
        super(connectionTimeout: 2000, readTimeout: 10000, proxy: proxy)
    }

    @Override
    protected void registerMessageConverters(RestTemplate restTemplate) {
        super.registerMessageConverters(restTemplate)
        restTemplate.messageConverters.removeAll { it instanceof MappingJackson2HttpMessageConverter }
    }
}
