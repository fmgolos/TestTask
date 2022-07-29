package fdr.home.task.config

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import mu.KLogging
import org.flywaydb.core.Flyway
import org.springframework.jdbc.core.JdbcTemplate


class FlywayConfig(
    jdbcUrl: String,
    username: String,
    password: String,
    databaseName: String
) {
    private val hikariConfig = HikariConfig().also {
        it.jdbcUrl = jdbcUrl
        it.username = username
        it.password = password
        it.driverClassName = "org.postgresql.Driver"
        it.schema = databaseName
    }
    private val dataSource = HikariDataSource(hikariConfig)
    private val jdbcTemplate: JdbcTemplate = JdbcTemplate(dataSource)
    private val flywayConfiguration = Flyway.configure()
        .locations("classpath:data/migration")
        .defaultSchema(databaseName)
        .dataSource(dataSource)

    init {
        flywayConfiguration.load().migrate()
        logger.info { "Successfully migrate " }
    }

    fun getJdbcTemplate(): JdbcTemplate {
        return jdbcTemplate
    }
    private companion object : KLogging()
}
