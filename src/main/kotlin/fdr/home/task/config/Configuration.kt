package fdr.home.task.config

import fdr.home.task.database.message.storage.PostgresMessageStorage
import fdr.home.task.database.user.storage.PostgresUserStorage
import fdr.home.task.service.authentification.Authentication
import fdr.home.task.service.authentification.TokenService
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springdoc.core.customizers.OpenApiCustomiser
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.JdbcTemplate

@Configuration
class Configuration {


    @Bean
    fun getPostgresUserStorage(jdbcTemplate: JdbcTemplate): PostgresUserStorage {
        return PostgresUserStorage(jdbcTemplate)
    }

    @Bean
    fun getPostgresMessageStorage(jdbcTemplate: JdbcTemplate): PostgresMessageStorage {
        return PostgresMessageStorage(jdbcTemplate)
    }

    @Bean
    fun getAuthentication(jdbcTemplate: JdbcTemplate): Authentication {
        return Authentication(getPostgresUserStorage(jdbcTemplate), TokenService())
    }

    @Bean
    fun openApiAuthorization(): OpenApiCustomiser {
        return OpenApiCustomiser {
            val securitySchemeName = "Token"
            val securityScheme = SecurityScheme()
                .name("Authorization")
                .scheme("bearer")
                .bearerFormat("JWT")
                .type(SecurityScheme.Type.APIKEY)
                .`in`(SecurityScheme.In.HEADER)
            it.components.addSecuritySchemes(securitySchemeName, securityScheme)
            it.addSecurityItem(SecurityRequirement().addList(securitySchemeName))
        }
    }

    @Bean
    fun getTokenService(): TokenService {
        return TokenService()
    }
}