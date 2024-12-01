// package com.bdcga.pbdpp.security

package com.bdcga.pbdpp.security

import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class JwtAuthenticationManager(
    private val jwtTokenProvider: JwtTokenProvider,
    private val userDetailsService: CustomUserDetailsService
) : ReactiveAuthenticationManager {

    override fun authenticate(authentication: Authentication): Mono<Authentication> {
        val authToken = authentication.credentials as String

        if (!jwtTokenProvider.validateToken(authToken)) {
            return Mono.empty()
        }

        val userId = jwtTokenProvider.getUserIdFromJWT(authToken)
        return userDetailsService.loadUserById(userId).map { userDetails ->
            UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
        }
    }
}