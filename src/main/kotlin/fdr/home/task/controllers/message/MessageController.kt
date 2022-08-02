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
    fun sendMessage(@RequestBody message: MessageText) {
        val authenticatedUser = SecurityContextHolder.getContext().authentication as AuthenticatedUser
        messageStorage.save(authenticatedUser.name, message.text)
    }

    @GetMapping("/history")
    fun getHistory(limit: Int): List<Message> {
        val authenticatedUser = SecurityContextHolder.getContext().authentication as AuthenticatedUser
        return messageStorage.getHistory(authenticatedUser.name, limit)
    }
}

data class MessageText(val text: String)