package fdr.home.task.config

import fdr.home.task.database.user.storage.PostgresUserStorage
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.JdbcTemplate

@Configuration
class Configuration {
    @Bean
    fun getPostgresUserStorage(jdbcTemplate: JdbcTemplate): PostgresUserStorage {
        return PostgresUserStorage(jdbcTemplate)
    }
}