package fdr.home.task.service.authentification

import com.auth0.jwt.JWT
import fdr.home.task.database.user.storage.PostgresUserStorage
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.jdbc.core.JdbcTemplate
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.*

class Authentication() {

    fun login(login: String, password: String): String {
        val userStorage = PostgresUserStorage(JdbcTemplate())
        if (userStorage.isExist(login, password)) {
            return create(login)
        } else throw IllegalAccessException("access denied")
    }

    private fun create(login: String): String {
        return Jwts.builder()
            .claim("name", login)
            .setExpiration(Date.from(Instant.now().plus(24, ChronoUnit.HOURS)))
            .compact()
    }

    internal fun isValid(token: String): Boolean {
        return isToken(token) && isNotExpired(token)
    }

    internal fun isToken(token: String): Boolean {
        return try {
            Jwts.parserBuilder().build().parse(token)
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
}

fun main() {
    val key = Keys.secretKeyFor(SignatureAlgorithm.HS256)
    val token = Jwts.builder()
        .claim("Name", "HYU")
        .signWith(key)
        .setExpiration(Date.from(Instant.now().plus(24, ChronoUnit.HOURS)))
        .compact()
    println(token)

    val result = Jwts.parserBuilder()
        .setSigningKey(key)
        .build()
        .parse(token)


}