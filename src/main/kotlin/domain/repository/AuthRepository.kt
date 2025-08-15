package id.neotica.domain.repository

import id.neotica.domain.NeoUser
import id.neotica.domain.TokenData

interface AuthRepository {
    suspend fun register(user: NeoUser): NeoUser
    suspend fun login(username: String, password: String): TokenData
    suspend fun refreshLogin(id: String): TokenData
    suspend fun deleteUser(id: String): String
    suspend fun checkUser(username: String): NeoUser
    suspend fun getAllUser(): List<NeoUser>
}