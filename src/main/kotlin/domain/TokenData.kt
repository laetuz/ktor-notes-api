package id.neotica.domain

import kotlinx.serialization.Serializable

@Serializable
data class TokenData(
    val token: String? = null,
    val refreshToken: String? = null,
    val expirationTime: Long? = null
)
