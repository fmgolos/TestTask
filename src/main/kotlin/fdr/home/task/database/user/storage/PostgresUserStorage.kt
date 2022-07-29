package fdr.home.task.database.user.storage

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.jdbc.support.KeyHolder
import java.sql.Statement

class PostgresUserStorage(private val jdbcTemplate: JdbcTemplate) {
    fun createNewUser(entry: UserCredentialsRequest): Int {
        val keyHolder: KeyHolder = GeneratedKeyHolder()
        val sql = """insert into users (login,password) values (?,?)
            on conflict (login) do update set
            login = excluded.login,
            password = excluded.password
        """
        jdbcTemplate.update({ connection ->
            val ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
            ps.setString(1, entry.login)
            ps.setString(2, entry.password)
            ps
        }, keyHolder)
        return keyHolder.keyList.first().getValue("id").toString().toInt()
    }

    fun isExist(login: String, password: String): Boolean {
        val sql = "select * from users where login = ? and password = ?"
        val response = jdbcTemplate.query(sql, UserCredentialsMapper(), login, password)
        return response.size > 0
    }

    fun delete(id: Int) {
        val sql = "delete from users where id = ?"
        jdbcTemplate.update(sql, id)
    }

    internal fun getById(id: Int): UserCredentials? {
        val sql = " select * from users where id = ?"
        return jdbcTemplate.query(sql, UserCredentialsMapper(), id).firstOrNull()
    }
}
