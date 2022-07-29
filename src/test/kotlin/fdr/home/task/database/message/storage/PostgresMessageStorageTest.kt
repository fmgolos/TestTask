package fdr.home.task.database.message.storage

import fdr.home.task.database.PostgresContainerWrapper
import fdr.home.task.message.storage.Message
import fdr.home.task.message.storage.MessageHistoryRequest
import fdr.home.task.message.storage.MessageRequest
import fdr.home.task.message.storage.PostgresMessageStorage
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class PostgresMessageStorageTest {
    private val dbMessageStorage = PostgresMessageStorage(PostgresContainerWrapper.getJdbcTemplate())

    @Test
    fun save() {
        val messageRequest = MessageRequest("testName", "Test text")
        val messageId = dbMessageStorage.save(messageRequest)
        val actualMessage = Message(messageId, messageRequest.name, messageRequest.text)
        val expected = dbMessageStorage.getById(messageId)
        assertThat(actualMessage).isEqualTo(expected)
    }

    @Test
    fun `get history if request amount less that count of message in db`() {
        val name = "TestName"
        val messageRequestList = (0..20).map { it -> MessageRequest(name, "TestText $it") }
        messageRequestList.forEach { dbMessageStorage.save(it) }
        val messageHistoryRequest = MessageHistoryRequest(name, 10)
        val history = dbMessageStorage.getHistory(messageHistoryRequest)
        history.forEach { println(it) }
    }

    @Test
    fun `get history if request amount greater than count of message in db`() {
        val name = "TestName"
        val messageRequestList = (0..5).map { it -> MessageRequest(name, "TestText $it") }
        messageRequestList.forEach { dbMessageStorage.save(it) }
        val messageHistoryRequest = MessageHistoryRequest(name, 10)
        val history = dbMessageStorage.getHistory(messageHistoryRequest)
    }
}