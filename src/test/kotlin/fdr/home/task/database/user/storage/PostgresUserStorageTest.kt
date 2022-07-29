package fdr.home.task.database.user.storage

import fdr.home.task.database.PostgresContainerWrapper
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class PostgresUserStorageTest {

    private val userStorage = PostgresUserStorage(PostgresContainerWrapper.getJdbcTemplate())

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
        val exist = userStorage.isExist("testLogin", "testPassword")
        val notExist = userStorage.isExist("falseLogin", "falsePassword")
        assertTrue(exist)
        assertFalse(notExist)
    }

    @Test
    fun `delete user`() {
        val user = UserCredentialsRequest("testLogin", "testPassword")
        val userId = userStorage.createNewUser(user)
        userStorage.delete(userId)
        assertFalse(userStorage.isExist(user.login, user.password))
        assertThat(userStorage.getById(userId)).isEqualTo(null)
    }

}