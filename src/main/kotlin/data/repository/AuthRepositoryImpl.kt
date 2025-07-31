package id.neotica.data.repository

import id.neotica.BCrypt
import id.neotica.data.Database
import id.neotica.data.dao.user.UserEntity
import id.neotica.data.dao.user.UserTable
import id.neotica.data.repository.mapper.toUser
import id.neotica.domain.NeoUser
import id.neotica.extension.toUUID
import io.ktor.server.plugins.NotFoundException

class AuthRepositoryImpl(private val database: Database) {
    suspend fun register(user: NeoUser) = database.dbQuery {
        val newAccount = UserEntity.new {
            username = user.username
            password = BCrypt.hashpw(user.password, BCrypt.gensalt(12))
            createdAt = user.createdAt?: System.currentTimeMillis()
            email = user.email
        }

        toUser(newAccount)
    }

    suspend fun login(username: String, password: String) = database.dbQuery {
        val userEntity = UserEntity.find { UserTable.username eq username }
            .singleOrNull()
            ?.let { toUser(it) }
            ?: throw NotFoundException("dd")

        val passwordsMatch = BCrypt.checkpw(password, userEntity.password)
        println("✨ $passwordsMatch")

        if (passwordsMatch) "Success" else "Nope"
    }

    suspend fun deleteUser(id: String) = database.dbQuery {
        val checkUsername = UserEntity.findById(id.toUUID())
            ?: throw NotFoundException("dd")

        checkUsername.delete()

        "User ${checkUsername.username} deleted."
    }

    suspend fun checkUser(username: String) = database.dbQuery {
        val checkUsername = UserEntity.find { UserTable.username eq username }
            .singleOrNull()
            ?.let { toUser(it) }
            ?: throw NotFoundException("dd")

        checkUsername
    }

    suspend fun getAllUser() = database.dbQuery {
        UserEntity.all().map(::toUser)
    }
}