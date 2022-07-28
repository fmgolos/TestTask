package fdr.home.task.database.user.credentials

import fdr.home.task.database.FlywayConfig
import fdr.home.task.database.PostgresContainerWrapper
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.springframework.jdbc.core.JdbcTemplate

internal class PostgresUserCredentialsStorageTest {

    private val dbStorage = PostgresUserCredentialsStorage(getJdbcTemplate())

    @Test
    fun save() {

    }

    @Test
    fun isExist() {
    }

    private fun getJdbcTemplate(): JdbcTemplate {
        val containerWrapper = PostgresContainerWrapper()
        return FlywayConfig(
            jdbcUrl = containerWrapper.container.jdbcUrl,
            username = containerWrapper.container.username,
            password = containerWrapper.container.password,
            databaseName = containerWrapper.container.databaseName
        ).getJdbcTemplate()
    }
}