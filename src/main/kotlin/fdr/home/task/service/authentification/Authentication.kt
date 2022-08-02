package fdr.home.task.service.authentification

import fdr.home.task.database.user.storage.PostgresUserStorage
import fdr.home.task.web.exceptions.UnAuthorizedException
import mu.KLogging
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.*

class Authentication(private val userStorage: PostgresUserStorage, private val tokenService: TokenService) {

    fun login(login: String, password: String): String {
        if (userStorage.canBeAuthorized(login, password)) {
            logger.info { "Successfully authenticated" }
            return tokenService.create(login, Date.from(Instant.now().plus(24, ChronoUnit.HOURS)))
        } else {
            logger.info { "Wrong login or password" }
            throw UnAuthorizedException()
        }
    }

    companion object : KLogging()
}