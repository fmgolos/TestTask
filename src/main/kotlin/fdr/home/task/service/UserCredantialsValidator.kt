package fdr.home.task.service

import fdr.home.task.database.user.credentials.PostgresUserCredentialsStorage
import io.jsonwebtoken.Jwts
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.*

class UserCredentialValidator(private val userStorage: PostgresUserCredentialsStorage) {

    fun validate(login: String, password: String): String {
        if (userStorage.isExist(login, password)) {
            return create(login)
        } else throw IllegalAccessException("access denied")
    }

    private fun create(login: String): String {
        return Jwts.builder()
            .claim("name", login)
            .setExpiration(Date.from(Instant.now().plus(24, ChronoUnit.HOURS)))
            .compact();
    }
}

