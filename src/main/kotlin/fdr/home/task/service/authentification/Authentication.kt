package fdr.home.task.service.authentification

import fdr.home.task.database.user.storage.PostgresUserStorage
import io.jsonwebtoken.Jwts
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.*

class Authentication(private val userStorage: PostgresUserStorage) {

    fun login(login: String, password: String): String {
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
    private fun validate(){

    }
}

