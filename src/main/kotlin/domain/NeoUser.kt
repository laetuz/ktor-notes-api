package id.neotica.domain

import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class NeoUser(
    val id: String? = UUID.randomUUID().toString(),
    val username: String,
    val email: String,
    val password: String,
    val createdAt: Long? = System.currentTimeMillis()
)