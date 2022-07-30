package fdr.home.task.database.foreign.key

import fdr.home.task.database.PostgresContainerWrapper
import fdr.home.task.database.message.storage.MessageHistoryRequest
import fdr.home.task.database.message.storage.MessageRequest
import fdr.home.task.database.message.storage.PostgresMessageStorage
import fdr.home.task.database.user.storage.PostgresUserStorage
import fdr.home.task.database.user.storage.UserCredentialsRequest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class ForeignKeyTest {
    private val messageStorage = PostgresMessageStorage(PostgresContainerWrapper.getJdbcTemplate())
    private val userStorage = PostgresUserStorage(PostgresContainerWrapper.getJdbcTemplate())

    @Test
    fun `when user wad deleted all message deleted too`() {
        val name = "user"
        val user = UserCredentialsRequest(name, "password")
        val message = MessageRequest(name, "message text")

        val userId = userStorage.createNewUser(user)
        messageStorage.save(message)

        userStorage.delete(userId)
        val history = messageStorage.getHistory(MessageHistoryRequest(name, 10))
        assertThat(history).hasSize(0)
    }
}

