package fdr.home.task.database.message.storage

import fdr.home.task.database.PostgresContainerWrapper
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class PostgresMessageStorageTest {
    private val dbMessageStorage = PostgresMessageStorage(PostgresContainerWrapper.getJdbcTemplate())

    @Test
    fun save() {
    }

    @Test
    fun getHistory() {
    }
}