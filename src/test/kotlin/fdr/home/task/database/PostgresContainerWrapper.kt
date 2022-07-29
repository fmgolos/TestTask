package fdr.home.task.database

import fdr.home.task.config.FlywayConfig
import org.springframework.jdbc.core.JdbcTemplate
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.utility.DockerImageName

class PostgresContainerWrapper {
    val container = PostgreSQLContainer<Nothing>(
        DockerImageName.parse("postgres:13-alpine")
    ).apply {
        withDatabaseName("meter_data")
        withUsername("user")
        withPassword("pass")
    }

    init {
        container.start()
    }
    companion object{
         fun getJdbcTemplate(): JdbcTemplate {
            val containerWrapper = PostgresContainerWrapper()
            return FlywayConfig(
                jdbcUrl = containerWrapper.container.jdbcUrl,
                username = containerWrapper.container.username,
                password = containerWrapper.container.password,
                databaseName = containerWrapper.container.databaseName
            ).getJdbcTemplate()
        }
    }
}