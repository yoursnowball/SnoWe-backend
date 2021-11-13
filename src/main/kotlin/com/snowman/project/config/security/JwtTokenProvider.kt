package com.snowman.project.config.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.DecodingException
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.util.*
import javax.crypto.SecretKey
import javax.servlet.http.HttpServletRequest

@Component
class JwtTokenProvider(
        @Value("\${app.jwt.secret}") private val jwtSecret: String,
) {
    companion object {
        const val TOKEN_VALID_TIME = 60480000000
        const val BEARER = "Bearer "
    }

    val key: SecretKey = Keys.hmacShaKeyFor(jwtSecret.toByteArray())

    fun generateToken(id: Long, userName: String): String {
        val now = Date()
        val expiredIn = now.time + TOKEN_VALID_TIME

        return Jwts.builder()
                .claim("id", id)
                .claim("userName", userName)
                .setIssuedAt(now)
                .setExpiration(Date(expiredIn))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact()
    }

    private fun getClaimsFromToken(token: String): Claims? {
        return Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .body
    }

    fun resolveToken(req: HttpServletRequest): Claims? {
        val headerValue: String? = req.getHeader("Authorization")
        val token = if (headerValue.isNullOrBlank()) {
            null
        } else if (headerValue.contains(BEARER)) {
            headerValue.replace(BEARER, "")
        } else {
            throw DecodingException("Bearer 가 존재하지 않습니다.")
        }

        return token?.let { getClaimsFromToken(it) }
    }

    fun getAuthentication(claims: Claims): Authentication {
        return UsernamePasswordAuthenticationToken(
                AuthInfo(
                        claims["id"].toString().toLong(),
                        claims["userName"] as String
                ), "", null)
    }
}