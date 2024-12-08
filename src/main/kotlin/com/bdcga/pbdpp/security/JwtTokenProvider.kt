// package com.bdcga.pbdpp.security

package com.bdcga.pbdpp.security

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.security.Key
import java.util.*

@Component
class JwtTokenProvider(
    @Value("\${app.jwtSecret}") private val jwtSecret: String,
    @Value("\${app.jwtExpirationMs}") private val jwtExpirationMs: Long
) {
    private val logger = LoggerFactory.getLogger(JwtTokenProvider::class.java)

    private val key: Key = Keys.hmacShaKeyFor(jwtSecret.toByteArray())

    fun generateToken(userPrincipal: UserPrincipal): String {
        val now = Date()
        val expiryDate = Date(now.time + jwtExpirationMs)

        val token = Jwts.builder()
            .setSubject(userPrincipal.username)
            .claim("userId", userPrincipal.id)
            .claim("email", userPrincipal.email)
            .setIssuedAt(now)
            .setExpiration(expiryDate)
            .signWith(SignatureAlgorithm.HS256, jwtSecret)
            .compact()

        logger.debug("Generated JWT Token: $token")
        return token
    }

    fun getUserIdFromJWT(token: String): String {
        val claims = Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .body

        return claims.subject
    }

    fun validateToken(authToken: String): Boolean {
        return try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(authToken)
            true
        } catch (ex: Exception) {
            println("Invalid JWT token: ${ex.message}")
            false
        }
    }
}