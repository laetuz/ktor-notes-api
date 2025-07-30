package id.neotica.data.dao.note

import id.neotica.data.dao.user.UserEntity
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.UUID

class NoteEntity(id: EntityID<UUID>): UUIDEntity(id) {
    companion object: UUIDEntityClass<NoteEntity>(NoteTable)

//    var userId: UserEntity by UserEntity referencedOn NoteTable.userId
    var title: String? by NoteTable.title
    var content: String? by NoteTable.content
    var isPinned: Boolean by NoteTable.isPinned
    var isArchived: Boolean by NoteTable.isArchived
    var createdAt: Long by NoteTable.createdAt
    var updatedAt: Long by NoteTable.updatedAt
}