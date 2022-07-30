package fdr.home.task.database.message.storage

import fdr.home.task.database.PostgresContainerWrapper
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class PostgresMessageStorageTest {
    private val dbMessageStorage = PostgresMessageStorage(PostgresContainerWrapper.getJdbcTemplate())

    @Test
    fun save() {
        val messageRequest = MessageRequest("testName", "Test text")
        val messageId = dbMessageStorage.save(messageRequest)
        val actualMessage = Message(messageId, messageRequest.name, messageRequest.message)
        val expected = dbMessageStorage.getById(messageId)
        assertThat(actualMessage).isEqualTo(expected)
    }

    @Test
    fun `get history if request amount less that count of message in db`() {
        val name = "TestName"
        val messageRequestList = (0..20).map { it -> MessageRequest(name, "TestText $it") }
        messageRequestList.forEach { dbMessageStorage.save(it) }
        val messageHistoryRequest = MessageHistoryRequest(name, 10)
        val actual = (11..20).map { messageRequestList[it] }
        val expected = dbMessageStorage.getHistory(messageHistoryRequest).map { MessageRequest(it.name, it.message) }
        assertThat(actual).containsAll(expected)
    }

    @Test
    fun `get history if request amount greater than count of message in db`() {
        val name = "TestName"
        val actual = (0..4).map { it -> MessageRequest(name, "TestText $it") }
        actual.forEach { dbMessageStorage.save(it) }
        val messageHistoryRequest = MessageHistoryRequest(name, 10)
        val expected = dbMessageStorage.getHistory(messageHistoryRequest).map { MessageRequest(it.name, it.message) }
        assertThat(actual).containsAll(expected)
    }
}