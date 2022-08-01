package fdr.home.task.controllers.message

import fdr.home.task.database.message.storage.Message
import fdr.home.task.database.message.storage.PostgresMessageStorage
import fdr.home.task.service.authentification.AuthenticatedUser
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/message")
class MessageController(private val messageStorage: PostgresMessageStorage) {
    @PostMapping("/send")
    fun sendMessage(@RequestBody message: String) {
        val authenticatedUser = SecurityContextHolder.getContext().authentication as AuthenticatedUser
        val messageRequest = MessageRequest(authenticatedUser.name, message)
        messageStorage.save(authenticatedUser.name, message)
    }

    @GetMapping("/history")
    fun getHistory(limitOfMessage: Int): List<Message> {
        limitOfMessage
        val authenticatedUser = SecurityContextHolder.getContext().authentication as AuthenticatedUser

        val history = messageStorage.getHistory(authenticatedUser.name, limitOfMessage)
        history.forEach { println(it) }
        return history
    }
}