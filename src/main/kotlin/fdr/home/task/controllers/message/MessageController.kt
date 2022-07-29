package fdr.home.task.controllers.message

import fdr.home.task.database.message.storage.PostgresMessageStorage
import org.springframework.web.bind.annotation.RestController

@RestController
class MessageController(private val messageStorage: PostgresMessageStorage) {

}