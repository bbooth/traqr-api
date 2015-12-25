package com.trulinc.traqr.service.security

import grails.plugin.springsecurity.SpringSecurityUtils
import grails.plugin.springsecurity.userdetails.GormUserDetailsService
import grails.transaction.Transactional
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority

@Transactional
class TraqrUserDetailsService extends GormUserDetailsService {

    @Override
    protected Collection<GrantedAuthority> loadAuthorities(user, String username, boolean loadRoles) {
        if (!loadRoles) {
            return ['ROLE_USER']
        }

        def conf = SpringSecurityUtils.securityConfig

        String authoritiesPropertyName = conf.userLookup.authoritiesPropertyName

        Collection<?> userAuthorities = user."$authoritiesPropertyName"
        def authorities = userAuthorities.collect { new SimpleGrantedAuthority(it) }
        authorities ?: ['ROLE_USER']
    }
}
