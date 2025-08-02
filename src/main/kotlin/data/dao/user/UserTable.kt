package id.neotica.data.dao.user

import org.jetbrains.exposed.v1.core.dao.id.UUIDTable

object UserTable: UUIDTable("User") {
    val username = varchar("username", 255).uniqueIndex()
    val email = varchar("email", 255).uniqueIndex()
    val password = varchar("password_hash", 255)
    val createdAt = long("created_at")
}