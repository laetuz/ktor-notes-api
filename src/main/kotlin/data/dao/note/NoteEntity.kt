package id.neotica.data.dao.note

import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.UUIDEntity
import org.jetbrains.exposed.v1.dao.UUIDEntityClass
import java.util.UUID

class NoteEntity(id: EntityID<UUID>): UUIDEntity(id) {
    companion object: UUIDEntityClass<NoteEntity>(NoteTable)

    var userId: UUID? by NoteTable.userId
    var title: String? by NoteTable.title
    var content: String? by NoteTable.content
    var isPinned: Boolean by NoteTable.isPinned
    var isArchived: Boolean by NoteTable.isArchived
    var createdAt: Long by NoteTable.createdAt
    var updatedAt: Long by NoteTable.updatedAt
}