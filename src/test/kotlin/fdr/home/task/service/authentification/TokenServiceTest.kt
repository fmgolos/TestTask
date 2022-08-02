package fdr.home.task.service.authentification

import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.jupiter.api.Test
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.*

class TokenServiceTest {
    private val name = "TestName"
    private val token = TokenService().create(name, Date.from(Instant.now().plus(24, ChronoUnit.HOURS)))

    @Test
    fun `is token`() {
        val token =
            TokenService().create(name, Date.from(Instant.now().plus(24, ChronoUnit.HOURS))).replace("Bearer_", "")
        val fakeToken = token.plus("SomeText").replace("Bearer_", "")
        assertTrue(TokenService().isToken(token))
        assertFalse(TokenService().isToken(fakeToken))
    }

    @Test
    fun `token is expired`() {
        val notExpiredToken =
            TokenService().create(name, Date.from(Instant.now().plus(24, ChronoUnit.HOURS))).replace("Bearer_", "")
        val expiredToken =
            TokenService().create(name, Date.from(Instant.now().minus(24, ChronoUnit.HOURS))).replace("Bearer_", "")
        assertTrue(TokenService().isNotExpired(notExpiredToken))
        assertFalse(TokenService().isNotExpired(expiredToken))
    }
}