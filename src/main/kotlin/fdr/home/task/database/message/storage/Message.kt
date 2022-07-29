package fdr.home.task.message.storage

import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet

data class Message(val id: Int, val name: String, val text: String)

data class MessageRequest(val name: String, val text: String)

data class MessageHistoryRequest(val name: String, val amountOfHistoryMessage: Int)

class MessageMapper : RowMapper<Message> {
    override fun mapRow(rs: ResultSet, rowNum: Int): Message {
        return Message(
            id = rs.getInt(1),
            name = rs.getString(2),
            text = rs.getString(3)
        )
    }
}

