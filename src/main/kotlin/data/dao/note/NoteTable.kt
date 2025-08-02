package id.neotica.data.dao.note

import org.jetbrains.exposed.v1.core.dao.id.UUIDTable

object NoteTable: UUIDTable("Note") {
    val userId = uuid("user_id").nullable()
    val title = varchar("title", 255).nullable()
    val content = text("content").nullable()
    val isPinned = bool("is_pinned").default(false)
    val isArchived = bool("is_archived").default(false)
    val createdAt = long("created_at")
    val updatedAt = long("updated_at")
}