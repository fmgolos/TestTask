package fdr.home.task.database.user.storage

import fdr.home.task.controllers.user.UserCredentialsRequest
import fdr.home.task.database.PostgresContainerWrapper
import fdr.home.task.database.message.storage.PostgresMessageStorage
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class PostgresUserStorageTest {
    private val template = PostgresContainerWrapper.getJdbcTemplate()
    private val messageStorage = PostgresMessageStorage(template)
    private val userStorage = PostgresUserStorage(template)

    @Test
    fun `create new user`() {
        val login = "testLogin"
        val password = "testPassword"
        val user = UserCredentialsRequest(login, password)
        val id = userStorage.createNewUser(user)
        val actual = UserCredentials(id, login, password)
        val expected = userStorage.getById(id)
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `user is exist`() {
        val user = UserCredentialsRequest("testLogin", "testPassword")
        userStorage.createNewUser(user)
        val exist = userStorage.userIsExist("testLogin")
        val notExist = userStorage.userIsExist("falseLogin")
        assertTrue(exist)
        assertFalse(notExist)
    }

    @Test
    fun `delete user`() {
        val user = UserCredentialsRequest("testLogin", "testPassword")
        val userId = userStorage.createNewUser(user)
        userStorage.delete(userId)
        assertFalse(userStorage.userIsExist(user.login))
        assertThat(userStorage.getById(userId)).isEqualTo(null)
    }

    @org.junit.Test
    fun `when user wad deleted all message deleted too`() {
        val name = "user"
        val user = UserCredentialsRequest(name, "password")
        val message = "message text"
        val userId = userStorage.createNewUser(user)
        messageStorage.save(name, message)
        userStorage.delete(userId)
        val history = messageStorage.getHistory(name, 10)
        assertThat(history).hasSize(0)
    }

}