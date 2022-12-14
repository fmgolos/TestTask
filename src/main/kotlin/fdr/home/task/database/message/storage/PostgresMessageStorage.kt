package fdr.home.task.database.message.storage


import mu.KLogging
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.jdbc.support.KeyHolder
import java.sql.Statement


class PostgresMessageStorage(private val jdbcTemplate: JdbcTemplate) {
    fun save(name: String, message: String): Int {
        val keyHolder: KeyHolder = GeneratedKeyHolder()
        val sql = "insert into message_storage (login,text) values (?,?)"
        jdbcTemplate.update({ connection ->
            val ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
            ps.setString(1, name)
            ps.setString(2, message)
            ps
        }, keyHolder)
        logger.info { "Message was successfully saved to database" }
        return keyHolder.keyList.first().getValue("id").toString().toInt()
    }

    fun getHistory(name: String, limit: Int): List<Message> {
        val sql = "select * from message_storage where login = ? order by id desc  limit ? "
        return jdbcTemplate.query(
            sql, MessageMapper(), name, limit
        )
    }

    internal fun getById(id: Int): Message {
        val sql = "select * from message_storage where id = ?"
        return jdbcTemplate.query(sql, MessageMapper(), id).first()
    }

    private companion object : KLogging()
}