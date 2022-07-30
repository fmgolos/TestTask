//package fdr.home.task.service.message
//
//import fdr.home.task.database.PostgresContainerWrapper
//import fdr.home.task.database.message.storage.Message
//import fdr.home.task.database.message.storage.MessageWebRequest
//import fdr.home.task.database.message.storage.PostgresMessageStorage
//import fdr.home.task.database.user.storage.PostgresUserStorage
//import fdr.home.task.database.user.storage.UserCredentialsRequest
//import org.assertj.core.api.Assertions.assertThat
//import org.junit.jupiter.api.Test
//
//class MessageServiceTest {
//    private val messageStorage = PostgresMessageStorage(PostgresContainerWrapper.getJdbcTemplate())
//    private val userStorage = PostgresUserStorage(PostgresContainerWrapper.getJdbcTemplate())
//
//    @Test
//    fun `message can be sanded`() {
//        val name = "User"
//        val messageText = "test message"
//        val userId = userStorage.createNewUser(UserCredentialsRequest(name, "Password"))
//        val messageService = MessageService(userStorage, messageStorage)
//        val messageId = messageService.sendMessage(MessageWebRequest(name, messageText))
//        val expected = messageStorage.getById(messageId)
//        val actual = Message(messageId, userId, messageText)
//        assertThat(expected).isEqualTo(actual)
//    }
//}