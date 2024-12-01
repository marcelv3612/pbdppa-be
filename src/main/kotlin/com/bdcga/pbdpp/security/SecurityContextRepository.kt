// package com.bdcga.pbdpp.security

package com.bdcga.pbdpp.security

import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextImpl
import org.springframework.security.web.server.context.ServerSecurityContextRepository
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Component
class SecurityContextRepository(
    private val authenticationManager: JwtAuthenticationManager
) : ServerSecurityContextRepository {

    override fun save(exchange: ServerWebExchange, context: SecurityContext): Mono<Void> {
        // Not required as JWT is stateless
        return Mono.empty()
    }

    override fun load(exchange: ServerWebExchange): Mono<SecurityContext> {
        val authHeader = exchange.request.headers.getFirst(HttpHeaders.AUTHORIZATION)
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            val authToken = authHeader.substring(7)
            val auth = UsernamePasswordAuthenticationToken(authToken, authToken)
            return authenticationManager.authenticate(auth).map { authentication ->
                SecurityContextImpl(authentication)
            }
        }
        return Mono.empty()
    }
}