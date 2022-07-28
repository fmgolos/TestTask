package fdr.home.task.database.user.credentials

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.jdbc.support.KeyHolder
import java.sql.Statement

class PostgresUserCredentialsStorage(private val jdbcTemplate: JdbcTemplate) {
    fun createNewUser(entry: UserCredentialsRequest): Int {
        val keyHolder: KeyHolder = GeneratedKeyHolder()
        val sql = """insert into credentials (login,password) values (?,?)
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
        val sql = "select * from credentials where login = ? and password = ?"
        val response = jdbcTemplate.query(sql, UserCredentialsMapper(), login, password)
        return response.size > 0
    }

    internal fun getById(id: Int): UserCredentials {
        val sql = " select * from credentials where id = ?"
        return jdbcTemplate.query(sql, UserCredentialsMapper(), id).first()
    }
}
