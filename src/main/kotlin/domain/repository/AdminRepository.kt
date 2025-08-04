package id.neotica.domain.repository

import id.neotica.domain.NeoUser

interface AdminRepository {
    suspend fun checkUser(username: String): NeoUser
    suspend fun getAllUser(): List<NeoUser>
}