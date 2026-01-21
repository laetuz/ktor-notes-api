package id.neotica.data.repository

import id.neotica.data.RoomDaoProvider
import id.neotica.data.Database
import id.neotica.data.USE_ROOM
import id.neotica.data.dao.user.UserEntity as ExposedUserEntity
import id.neotica.data.dao.user.UserTable as ExposedUserTable
import id.neotica.domain.repository.UserRepository
import id.neotica.extension.toUUID
import io.ktor.server.plugins.NotFoundException

class UserRepositoryImpl(private val database: Database) : UserRepository {
    override suspend fun checkUsername(id: String): String = database.dbQuery {
        if (USE_ROOM) {
            RoomDaoProvider.userDao.getUserById(id)?.username
                ?: throw NotFoundException("User not found with Room")
        } else {
            ExposedUserEntity.find { ExposedUserTable.id eq id.toUUID() }.singleOrNull()?.username
                ?: throw NotFoundException("User not found with Exposed")
        }
    }
}