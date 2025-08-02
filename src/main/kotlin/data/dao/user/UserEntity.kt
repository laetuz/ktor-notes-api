package id.neotica.data.dao.user

import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.UUIDEntity
import org.jetbrains.exposed.v1.dao.UUIDEntityClass
import java.util.UUID

class UserEntity(id: EntityID<UUID>): UUIDEntity(id) {
    companion object: UUIDEntityClass<UserEntity>(UserTable)

    var username: String by UserTable.username
    var email: String by UserTable.email
    var password: String by UserTable.password
    var createdAt: Long by UserTable.createdAt
}