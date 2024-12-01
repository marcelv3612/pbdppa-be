// package com.bdcga.pbdpp.security

package com.bdcga.pbdpp.security

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.security.Key
import java.util.*

@Component
class JwtTokenProvider(
    @Value("\${app.jwtSecret}") private val jwtSecret: String,
    @Value("\${app.jwtExpirationMs}") private val jwtExpirationMs: Long
) {
    private val key: Key = Keys.hmacShaKeyFor(jwtSecret.toByteArray())

    fun generateToken(userPrincipal: UserPrincipal): String {
        val now = Date()
        val expiryDate = Date(now.time + jwtExpirationMs)

        return Jwts.builder()
            .setSubject(userPrincipal.id)
            .setIssuedAt(now)
            .setExpiration(expiryDate)
            .signWith(key, SignatureAlgorithm.HS256)
            .compact()
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