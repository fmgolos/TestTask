package fdr.home.task.service.authentification

import io.jsonwebtoken.Jwts
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.jupiter.api.Test
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.*

class AuthenticationTest {
    @Test
    fun `is token`() {
        val token = Jwts.builder()
            .claim("name", "TestName")
            .setExpiration(Date.from(Instant.now().plus(24, ChronoUnit.HOURS)))
            .compact()

        assertTrue(Authentication().isToken(token))
    }

    @Test
    fun `is not token`(){
        val token = Jwts.builder()
            .claim("name", "TestName")
            .setExpiration(Date.from(Instant.now().plus(24, ChronoUnit.HOURS)))
            .compact().plus("RandomSymbols")

        assertFalse(Authentication().isToken(token))
    }
}