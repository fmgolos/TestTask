package fdr.home.task.database

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
}