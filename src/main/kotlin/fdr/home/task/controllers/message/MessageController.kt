package fdr.home.task.controllers.message

import fdr.home.task.database.message.storage.Message
import fdr.home.task.database.message.storage.MessageHistoryRequest
import fdr.home.task.database.message.storage.MessageRequest
import fdr.home.task.database.message.storage.PostgresMessageStorage
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class MessageController(private val messageStorage: PostgresMessageStorage) {
    @PostMapping("/send")
    fun sendMessage(@RequestBody messageRequest: MessageRequest) {
        messageStorage.save(messageRequest)
    }

    @GetMapping("/history")
    fun getHistory(@RequestBody messageHistoryRequest: MessageHistoryRequest): List<Message> {
        return messageStorage.getHistory(messageHistoryRequest)
    }
}