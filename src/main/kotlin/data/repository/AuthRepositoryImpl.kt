package id.neotica.data.repository

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import id.neotica.BCrypt
import id.neotica.data.RoomDaoProvider
import id.neotica.data.Database
import id.neotica.data.USE_ROOM
import id.neotica.data.dao.user.UserEntity as ExposedUserEntity
import id.neotica.data.dao.user.UserEntityRoom
import id.neotica.data.dao.user.UserTable as ExposedUserTable
import id.neotica.data.repository.mapper.toDomain
import id.neotica.data.repository.mapper.toUser as toExposedUser
import id.neotica.domain.NeoUser
import id.neotica.domain.TokenData
import id.neotica.domain.repository.AuthRepository
import id.neotica.extension.toUUID
import id.neotica.utils.baseUrl
import io.ktor.server.plugins.*
import java.util.*
import javax.security.auth.login.CredentialException
import kotlin.time.Duration.Companion.days

class AuthRepositoryImpl(private val database: Database) : AuthRepository {
    override suspend fun register(user: NeoUser): NeoUser = database.dbQuery {
        if (USE_ROOM) {
            val newUser = UserEntityRoom(
                id = UUID.randomUUID().toString(),
                username = user.username,
                passwordHash = BCrypt.hashpw(user.password, BCrypt.gensalt(12)),
                createdAt = user.createdAt ?: System.currentTimeMillis(),
                email = user.email
            )
            RoomDaoProvider.userDao.insertUser(newUser)
            newUser.toDomain()
        } else {
            val newAccount = ExposedUserEntity.new {
                username = user.username
                password = BCrypt.hashpw(user.password, BCrypt.gensalt(12))
                createdAt = user.createdAt ?: System.currentTimeMillis()
                email = user.email
            }
            toExposedUser(newAccount)
        }
    }

    override suspend fun login(username: String, password: String): TokenData = database.dbQuery {
        val user: NeoUser
        val passwordHash: String

        if (USE_ROOM) {
            val userEntity = RoomDaoProvider.userDao.getUserByUsername(username)
                ?: throw NotFoundException("User not found")
            user = userEntity.toDomain()
            passwordHash = userEntity.passwordHash
        } else {
            val userEntity =
                ExposedUserEntity.find { ExposedUserTable.username eq username }.singleOrNull()
                    ?: throw NotFoundException("User not found")
            user = toExposedUser(userEntity)
            passwordHash = userEntity.password
        }

        val passwordsMatch = BCrypt.checkpw(password, passwordHash)
        if (!passwordsMatch) throw CredentialException("Invalid credentials")

        val token = JWT.create()
            .withIssuer("$baseUrl/")
            .withClaim("id", user.id)
            .withExpiresAt(Date(System.currentTimeMillis() + 7.days.inWholeMilliseconds))
            .sign(Algorithm.HMAC256("lol"))

        val refreshToken = JWT.create()
            .withIssuer("$baseUrl/")
            .withClaim("refreshId", user.id)
            .withExpiresAt(Date(System.currentTimeMillis() + 30.days.inWholeMilliseconds))
            .sign(Algorithm.HMAC256("lol"))

        TokenData(
            token = token,
            refreshToken = refreshToken,
            expirationTime = System.currentTimeMillis() + 7.days.inWholeMilliseconds
        )
    }

    override suspend fun refreshLogin(id: String): TokenData = database.dbQuery {
        val userId: String
        if (USE_ROOM) {
            userId = RoomDaoProvider.userDao.getUserById(id)?.id
                ?: throw NotFoundException("User not found")
        } else {
            userId = ExposedUserEntity.findById(id.toUUID())?.id?.toString()
                ?: throw NotFoundException("User not found")
        }

        val token = JWT.create()
            .withIssuer("$baseUrl/")
            .withClaim("id", userId)
            .withExpiresAt(Date(System.currentTimeMillis() + 7.days.inWholeMilliseconds))
            .sign(Algorithm.HMAC256("lol"))

        val refreshToken = JWT.create()
            .withIssuer("$baseUrl/")
            .withClaim("refreshId", userId)
            .withExpiresAt(Date(System.currentTimeMillis() + 30.days.inWholeMilliseconds))
            .sign(Algorithm.HMAC256("lol"))

        TokenData(
            token = token,
            refreshToken = refreshToken,
            expirationTime = System.currentTimeMillis() + 7.days.inWholeMilliseconds
        )
    }

    override suspend fun deleteUser(id: String): String = database.dbQuery {
        if (USE_ROOM) {
            val user =
                RoomDaoProvider.userDao.getUserById(id) ?: throw NotFoundException("User not found")
            RoomDaoProvider.userDao.deleteUser(user)
            "User ${user.username} deleted."
        } else {
            val user =
                ExposedUserEntity.findById(id.toUUID()) ?: throw NotFoundException("User not found")
            val username = user.username
            user.delete()
            "User $username deleted."
        }
    }

    override suspend fun checkUser(username: String): NeoUser = database.dbQuery {
        if (USE_ROOM) {
            RoomDaoProvider.userDao.getUserByUsername(username)?.toDomain()
                ?: throw NotFoundException("User not found")
        } else {
            ExposedUserEntity.find { ExposedUserTable.username eq username }.singleOrNull()
                ?.let { toExposedUser(it) }
                ?: throw NotFoundException("User not found")
        }
    }

    override suspend fun getAllUser(): List<NeoUser> = database.dbQuery {
        if (USE_ROOM) {
            RoomDaoProvider.userDao.getAllUsers().map { it.toDomain() }
        } else {
            ExposedUserEntity.all().map(::toExposedUser)
        }
    }
}