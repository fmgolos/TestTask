package fdr.home.task.service.authentification

import fdr.home.task.database.user.storage.PostgresUserStorage
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.jdbc.core.JdbcTemplate
import java.lang.Exception
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

   internal fun isToken(token: String): Boolean {
        return try {
            Jwts.parserBuilder().build().parse(token)
            true
        } catch (e: Exception) {
            false
        }
    }
}

//fun main() {
//    val key = Keys.secretKeyFor(SignatureAlgorithm.HS256)
//    val token = Jwts.builder()
//        .claim("Name", "HYU")
//        .signWith(key)
//        .setExpiration(Date.from(Instant.now().plus(24, ChronoUnit.HOURS)))
//        .compact()
//    println(token)
//
//    val result = Jwts.parserBuilder()
//        .setSigningKey(key)
//        .build()
//        .parse(token)
//
//    println(result)
//    println(key)
//}