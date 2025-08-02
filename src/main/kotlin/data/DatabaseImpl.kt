package id.neotica.data

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import id.neotica.data.dao.note.NoteTable
import id.neotica.data.dao.user.UserTable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.v1.jdbc.Database
import org.jetbrains.exposed.v1.jdbc.SchemaUtils
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import kotlin.coroutines.CoroutineContext

class DatabaseImpl(
    private val ioContext: CoroutineContext = Dispatchers.IO
): id.neotica.data.Database {
    init {
        val dataSource = hikariDataSource()

        Database.connect(dataSource)
        transaction {
            simulateExistingDatabase()
        }
    }

    override suspend fun <T> dbQuery(block: () -> T): T = withContext(ioContext) { transaction { block() } }

    private fun hikariDataSource(): HikariDataSource = HikariDataSource(
        HikariConfig().apply {
            driverClassName = "org.postgresql.Driver"
            jdbcUrl = System.getenv("NOTES_PG_URL") ?: "jdbc:postgresql://127.0.0.1:5432/notes"
            username = System.getenv("NOTES_PG_USER") ?: "notes"
            password = System.getenv("NOTES_PG_PASSWORD") ?: "notes"
            maximumPoolSize = 3
            isAutoCommit = true
            transactionIsolation = "TRANSACTION_REPEATABLE_READ"
            validate()
        }
    )
}

fun simulateExistingDatabase() {
    SchemaUtils.create(NoteTable, UserTable)
}