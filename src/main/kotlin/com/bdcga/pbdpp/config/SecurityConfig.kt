// package com.bdcga.pbdpp.security

package com.bdcga.pbdpp.config

import com.bdcga.pbdpp.security.JwtAuthenticationManager
import com.bdcga.pbdpp.security.SecurityContextRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.reactive.CorsConfigurationSource
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource

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
                it.pathMatchers("/api/*").permitAll()
                it.anyExchange().authenticated()
            }
            .authenticationManager(jwtAuthenticationManager)
            .securityContextRepository(securityContextRepository)
            .build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    // Note: While the CorsWebFilter should handle CORS globally, it’s important to ensure that your security configuration doesn’t override it.
    // Define the CORS configuration source
    fun corsConfigurationSource(): CorsConfigurationSource {
        val corsConfig = CorsConfiguration()
        corsConfig.allowedOrigins = listOf("http://localhost:3000")
        corsConfig.allowedMethods = listOf("GET", "POST", "PUT", "DELETE", "OPTIONS")
        corsConfig.allowedHeaders = listOf("*")
        corsConfig.allowCredentials = true

        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", corsConfig)
        return source
    }
}