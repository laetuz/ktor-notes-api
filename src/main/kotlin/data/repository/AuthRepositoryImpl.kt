package id.neotica.data.repository

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import id.neotica.BCrypt
import id.neotica.data.Database
import id.neotica.data.dao.user.UserEntity
import id.neotica.data.dao.user.UserTable
import id.neotica.data.repository.mapper.toUser
import id.neotica.domain.NeoUser
import id.neotica.domain.TokenData
import id.neotica.domain.repository.AuthRepository
import id.neotica.extension.toUUID
import id.neotica.utils.baseUrl
import io.ktor.server.plugins.*
import java.util.*
import javax.security.auth.login.CredentialException
import kotlin.time.Duration.Companion.days

class AuthRepositoryImpl(private val database: Database): AuthRepository {
    override suspend fun register(user: NeoUser) = database.dbQuery {
        val newAccount = UserEntity.new {
            username = user.username
            password = BCrypt.hashpw(user.password, BCrypt.gensalt(12))
            createdAt = user.createdAt?: System.currentTimeMillis()
            email = user.email
        }

        toUser(newAccount)
    }

    override suspend fun login(username: String, password: String) = database.dbQuery {
        val userEntity = UserEntity.find { UserTable.username eq username }
            .singleOrNull()
            ?.let { toUser(it) }
            ?: throw NotFoundException("dd")

        val passwordsMatch = BCrypt.checkpw(password, userEntity.password)
        println("✨ password matched: $passwordsMatch")

        val token = JWT.create()
            .withIssuer("${baseUrl}/")
            .withClaim("id", userEntity.id)
            .withExpiresAt(
                Date(System.currentTimeMillis() + 7.days.inWholeMilliseconds))
            .sign(Algorithm.HMAC256("lol"))

        val refreshToken = JWT.create()
            .withIssuer("${baseUrl}/")
            .withClaim("refreshId", userEntity.id)
            .withExpiresAt(Date(System.currentTimeMillis() + 30.days.inWholeMilliseconds))
            .sign(Algorithm.HMAC256("lol"))

        if (passwordsMatch) {
            TokenData(
                token = token,
                refreshToken = refreshToken,
                expirationTime = System.currentTimeMillis() + 7.days.inWholeMilliseconds
            )
        } else {
            throw CredentialException()
        }
    }

    override suspend fun refreshLogin(id: String) = database.dbQuery {
        val userEntity = UserEntity.findById(id.toUUID())
            ?.let { toUser(it) }
            ?: throw NotFoundException("dd")

        val token = JWT.create()
            .withIssuer("${baseUrl}/")
            .withClaim("id", userEntity.id)
            .withExpiresAt(
                Date(System.currentTimeMillis() + 7.days.inWholeMilliseconds))
            .sign(Algorithm.HMAC256("lol"))

        val refreshToken = JWT.create()
            .withIssuer("${baseUrl}/")
            .withClaim("refreshId", userEntity.id)
            .withExpiresAt(Date(System.currentTimeMillis() + 30.days.inWholeMilliseconds))
            .sign(Algorithm.HMAC256("lol"))

        TokenData(
            token = token,
            refreshToken = refreshToken,
            expirationTime = System.currentTimeMillis() + 7.days.inWholeMilliseconds
        )
    }

    override suspend fun deleteUser(id: String) = database.dbQuery {
        val checkUsername = UserEntity.findById(id.toUUID())
            ?: throw NotFoundException("dd")

        checkUsername.delete()

        "User ${checkUsername.username} deleted."
    }

    override suspend fun checkUser(username: String) = database.dbQuery {
        val checkUsername = UserEntity.find { UserTable.username eq username }
            .singleOrNull()
            ?.let { toUser(it) }
            ?: throw NotFoundException("dd")

        checkUsername
    }

    override suspend fun getAllUser() = database.dbQuery {
        UserEntity.all().map(::toUser)
    }
}