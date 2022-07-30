//package fdr.home.task.service.message
//
//
//import fdr.home.task.database.message.storage.MessageDBRequest
//import fdr.home.task.database.message.storage.MessageWebRequest
//import fdr.home.task.database.message.storage.PostgresMessageStorage
//import fdr.home.task.database.user.storage.PostgresUserStorage
//
//class MessageService(private val userStorage: PostgresUserStorage, private val messageStorage: PostgresMessageStorage) {
//    fun sendMessage(messageRequest: MessageWebRequest): Int {
//        val user = userStorage.getByName(messageRequest.name)
//        if (user == null) {
//            throw IllegalArgumentException("User does not exist")
//        } else {
//            val id = user.id
//            val messageId = messageStorage.save(MessageDBRequest(id, messageRequest.message))
//            return messageId
//        }
//    }
//
//    fun getHistory() {
//        TODO()
//    }
//
//}