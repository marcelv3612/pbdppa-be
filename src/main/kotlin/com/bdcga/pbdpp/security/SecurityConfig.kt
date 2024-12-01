// package com.bdcga.pbdpp.security

package com.bdcga.pbdpp.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.server.SecurityWebFilterChain

@Configuration
@EnableWebFluxSecurity
@EnableMethodSecurity
class SecurityConfig(
    private val jwtAuthenticationManager: JwtAuthenticationManager,
    private val securityContextRepository: SecurityContextRepository
) {

    @Bean
    fun securityWebFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
        return http
            .csrf { it.disable() }
            .authorizeExchange {
                it.pathMatchers("/api/register", "/api/authenticate").permitAll()
                it.anyExchange().authenticated()
            }
            .authenticationManager(jwtAuthenticationManager)
            .securityContextRepository(securityContextRepository)
            .build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()
}