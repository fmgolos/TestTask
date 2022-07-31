package fdr.home.task.controllers.message

data class MessageRequest(val name: String, val message: String)

data class MessageHistoryRequest(val name: String, val amountOfHistoryMessage: Int)