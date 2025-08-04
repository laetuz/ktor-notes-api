package id.neotica.domain.repository

interface UserRepository {
    suspend fun checkUsername(id: String): String
}