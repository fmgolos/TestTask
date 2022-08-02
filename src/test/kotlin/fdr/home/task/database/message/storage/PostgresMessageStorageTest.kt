package fdr.home.task.database.message.storage

import fdr.home.task.controllers.message.MessageRequest
import fdr.home.task.controllers.user.PojoLoginPassword
import fdr.home.task.database.PostgresContainerWrapper
import fdr.home.task.database.user.storage.PostgresUserStorage
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class PostgresMessageStorageTest {
    private val template = PostgresContainerWrapper.getJdbcTemplate()
    private val dbMessageStorage = PostgresMessageStorage(template)
    private val userStorage = PostgresUserStorage(template)

    @Test
    fun save() {
        val login = "TestName"
        val password = "Password"
        val text = "Test text"
        userStorage.createNewUser(PojoLoginPassword(login, password))
        val messageId = dbMessageStorage.save(login, text)
        val actualMessage = Message(messageId, login, text)
        val expected = dbMessageStorage.getById(messageId)
        assertThat(actualMessage).isEqualTo(expected)
    }

    @Test
    fun `get history if request amount less that count of message in db`() {
        val login = "TestName"
        val password = "Password"
        val text = "Test text"
        userStorage.createNewUser(PojoLoginPassword(login, password))
        val messageRequestList = (0..20).map { MessageRequest(login, "$text $it") }
        messageRequestList.forEach { dbMessageStorage.save(it.name, it.message) }
        val actual = (11..20).map { messageRequestList[it] }
        val expected = dbMessageStorage.getHistory(login, 10).map { MessageRequest(it.name, it.message) }
        expected.forEach { println(it) }
        assertThat(actual).containsAll(expected)
    }

    @Test
    fun `get history if request amount greater than count of message in db`() {
        val login = "TestName"
        val password = "Password"
        val text = "Test text"
        userStorage.createNewUser(PojoLoginPassword(login, password))
        val actual = (0..4).map { MessageRequest(login, "$text $it") }
        actual.forEach { dbMessageStorage.save(it.name, it.message) }
        val expected = dbMessageStorage.getHistory(login, 10).map { MessageRequest(it.name, it.message) }
        assertThat(actual).containsAll(expected)
    }
}