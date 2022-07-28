package fdr.home.task.database

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