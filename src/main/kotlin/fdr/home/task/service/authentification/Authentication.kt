package fdr.home.task.service.authentification

import com.auth0.jwt.JWT
import fdr.home.task.database.user.storage.PostgresUserStorage
import fdr.home.task.web.exceptions.UnAuthorizedException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.*

class Authentication(private val userStorage: PostgresUserStorage) {

    fun login(login: String, password: String): String {
        if (userStorage.canBeAuthorized(login, password)) {
            return TokenService().create(login, Date.from(Instant.now().plus(24, ChronoUnit.HOURS)))
        } else throw UnAuthorizedException()
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

    val decode = JWT.decode(token)
    println(decode.getClaim("Name").asString())

}