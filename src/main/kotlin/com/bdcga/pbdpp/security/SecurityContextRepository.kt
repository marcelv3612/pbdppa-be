// package com.bdcga.pbdpp.security

package com.bdcga.pbdpp.security

import org.slf4j.LoggerFactory
import org.springframework.http.HttpHeaders
import org.springframework.http.server.reactive.ServerHttpRequest
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

    private val logger = LoggerFactory.getLogger(SecurityContextRepository::class.java)

    override fun save(exchange: ServerWebExchange, context: SecurityContext): Mono<Void> {
        // Not required as JWT is stateless
        throw UnsupportedOperationException("Not supported yet.")
    }

    override fun load(exchange: ServerWebExchange): Mono<SecurityContext> {
        val request: ServerHttpRequest = exchange.request
        val authHeader = request.headers.getFirst(HttpHeaders.AUTHORIZATION)

        return if (authHeader != null && authHeader.startsWith("Bearer ")) {
            val authToken = authHeader.substring(7)
            logger.debug("Received JWT Token: $authToken")
            val auth = UsernamePasswordAuthenticationToken(authToken, authToken)
            authenticationManager.authenticate(auth).map { authentication ->
                SecurityContextImpl(authentication)
            }
        } else {
            logger.warn("No JWT Token found in request headers")
            Mono.empty()
        }
    }
}