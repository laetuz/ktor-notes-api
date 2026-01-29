package id.neotica.data

interface Database {
    suspend fun <T> dbQuery(block: suspend () -> T): T
}