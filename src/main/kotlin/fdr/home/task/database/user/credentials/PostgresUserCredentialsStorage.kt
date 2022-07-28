package fdr.home.task.database.user.credentials

import org.springframework.jdbc.core.JdbcTemplate

class PostgresUserCredentialsStorage(private val jdbcTemplate: JdbcTemplate) {
    fun save(entry: UserCredentials) {
        val sql = """insert into credentials value (?,?)
            on conflict (login) do update set
        """
        jdbcTemplate.update(sql, entry.login, entry.password)
    }

    fun isExist(login: String, password: String): Boolean {
        val sql = "select * from credentials where login = ? and password = ?"
        val response = jdbcTemplate.query(sql, UserCredentialsMapper(), login, password)
        return response.size > 0
    }
}
