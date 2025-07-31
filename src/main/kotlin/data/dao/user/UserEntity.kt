package id.neotica.data.dao.user

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.UUID

class UserEntity(id: EntityID<UUID>): UUIDEntity(id) {
    companion object: UUIDEntityClass<UserEntity>(UserTable)

    var username: String by UserTable.username
    var email: String by UserTable.email
    var password: String by UserTable.password
    var createdAt: Long by UserTable.createdAt
}