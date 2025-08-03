package id.neotica.data.repository

import id.neotica.data.Database
import id.neotica.data.dao.user.UserEntity
import id.neotica.data.dao.user.UserTable
import id.neotica.data.repository.mapper.toUser
import id.neotica.extension.toUUID
import io.ktor.server.plugins.NotFoundException

class UserRepositoryImpl(private val database: Database) {
    suspend fun checkUser(id: String) = database.dbQuery {
        val checkUsername = UserEntity.find { UserTable.id eq id.toUUID() }
            .singleOrNull()
            ?.let { toUser(it) }
            ?: throw NotFoundException("dd")

        checkUsername.username
    }
}