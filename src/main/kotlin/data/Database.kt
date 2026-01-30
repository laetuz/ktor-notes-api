package id.neotica.data

interface Database {
    suspend fun <T> dbQuery(block: () -> T): T
}