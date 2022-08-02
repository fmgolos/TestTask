package fdr.home.task.controllers.message

import fdr.home.task.database.message.storage.Message
import fdr.home.task.database.message.storage.PostgresMessageStorage
import fdr.home.task.service.authentification.AuthenticatedUser
import feign.Param
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/message")
class MessageController(private val messageStorage: PostgresMessageStorage) {
    @PostMapping("/send")
    fun sendMessage(@RequestBody messageText: MessageText) {
        val authenticatedUser = SecurityContextHolder.getContext().authentication as AuthenticatedUser
        messageStorage.save(authenticatedUser.name, messageText.text)
    }

    @GetMapping("/history?limit={limit}")
    fun getHistory(@Param("limit") limit: Int): List<Message> {
        val authenticatedUser = SecurityContextHolder.getContext().authentication as AuthenticatedUser
        val history = messageStorage.getHistory(authenticatedUser.name, limit)
        history.forEach { println(it) }
        return history
    }
}

data class MessageText(val text: String)