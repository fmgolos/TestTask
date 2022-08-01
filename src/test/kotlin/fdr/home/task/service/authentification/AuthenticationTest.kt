package fdr.home.task.service.authentification

import fdr.home.task.controllers.user.UserCredentialsRequest
import fdr.home.task.database.PostgresContainerWrapper
import fdr.home.task.database.user.storage.PostgresUserStorage
import junit.framework.TestCase.assertTrue
import org.junit.jupiter.api.Test

class AuthenticationTest {
    @Test
    fun `successfully login can send token`() {
        val template = PostgresContainerWrapper.getJdbcTemplate()
        val userStorage = PostgresUserStorage(template)

        val login = "testLogin"
        val password = "testPassword"
        val user = UserCredentialsRequest(login, password)
        userStorage.createNewUser(user)

        val token = Authentication(userStorage).login(login, password)
        assertTrue(TokenService().isToken(token))
    }


}