package fdr.home.task.controllers.message

import fdr.home.task.database.message.storage.Message
import fdr.home.task.database.message.storage.PostgresMessageStorage
import fdr.home.task.service.authentification.MyUser
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/message")
class MessageController(private val messageStorage: PostgresMessageStorage) {
    @PostMapping("/send")
    fun sendMessage(@RequestBody message: String) {
        val myUser = SecurityContextHolder.getContext().authentication as MyUser
        val messageRequest = MessageRequest(myUser.name, message)
        messageStorage.save(messageRequest)
    }

    @GetMapping("/history")
    fun getHistory(@RequestBody limitOfMessage: Int): List<Message> {
        val myUser = SecurityContextHolder.getContext().authentication as MyUser
        val messageHistoryRequest = MessageHistoryRequest(myUser.name, limitOfMessage)
        return messageStorage.getHistory(messageHistoryRequest)
    }
}