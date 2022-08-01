package fdr.home.task.service.authentification

import com.auth0.jwt.JWT
import io.jsonwebtoken.Jwts
import java.time.Instant
import java.util.*

class TokenService {
    fun create(login: String, expiredDate: Date): String {
        val token = Jwts.builder()
            .claim("name", login)
            .setExpiration(expiredDate)
            .compact()

        return "Bearer_$token"
    }

    internal fun isToken(token: String): Boolean {
        val tokenWithoutBearer = token.replace("Bearer_", "")
        return try {
            Jwts.parserBuilder().build().parse(tokenWithoutBearer)
            true
        } catch (e: Exception) {
            false
        }
    }

    internal fun isNotExpired(token: String): Boolean {
        val decode = JWT.decode(token)
        val expiresAt = decode.expiresAt.toInstant()
        return expiresAt > Instant.now()
    }

    internal fun parseNameFromToken(token: String): String {
        return JWT.decode(token).getClaim("name").asString()
    }
}