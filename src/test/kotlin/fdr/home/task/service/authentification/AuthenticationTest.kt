package fdr.home.task.service.authentification

import fdr.home.task.database.PostgresContainerWrapper
import fdr.home.task.database.message.storage.PostgresMessageStorage
import fdr.home.task.database.user.storage.PostgresUserStorage
import io.jsonwebtoken.Jwts
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.jupiter.api.Test
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.*

class AuthenticationTest {
    private val template = PostgresContainerWrapper.getJdbcTemplate()
    private val userStorage = PostgresUserStorage(template)

    @Test
    fun `is token`() {
        val token = Jwts.builder()
            .claim("name", "TestName")
            .setExpiration(Date.from(Instant.now().plus(24, ChronoUnit.HOURS)))
            .compact()

        assertTrue(Authentication(userStorage).isToken(token))
    }

    @Test
    fun `is not token`() {
        val token = Jwts.builder()
            .claim("name", "TestName")
            .setExpiration(Date.from(Instant.now().plus(24, ChronoUnit.HOURS)))
            .compact().plus("RandomSymbols")

        assertFalse(Authentication(userStorage).isToken(token))
    }

    @Test
    fun `token is expired`() {
        val tokenIsExpired = Jwts.builder()
            .claim("name", "TestName")
            .setExpiration(Date.from(Instant.now().minus(24, ChronoUnit.HOURS)))
            .compact()
        val tokenIsNotExpired = Jwts.builder()
            .claim("name", "TestName")
            .setExpiration(Date.from(Instant.now().plus(24, ChronoUnit.HOURS)))
            .compact()

        assertTrue(Authentication(userStorage).isNotExpired(tokenIsNotExpired))
        assertFalse(Authentication(userStorage).isNotExpired(tokenIsExpired))
    }

    @Test
    fun `token is valid`() {
        val validToken = Jwts.builder()
            .claim("name", "TestName")
            .setExpiration(Date.from(Instant.now().plus(24, ChronoUnit.HOURS)))
            .compact()
        val invalidToken = Jwts.builder()
            .claim("name", "TestName")
            .setExpiration(Date.from(Instant.now().plus(24, ChronoUnit.HOURS)))
            .compact().plus("SomeSymbols")
        assertTrue(Authentication(userStorage).isValid(validToken))
        assertFalse(Authentication(userStorage).isValid(invalidToken))

    }

}