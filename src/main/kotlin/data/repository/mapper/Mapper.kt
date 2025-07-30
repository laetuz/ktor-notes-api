package id.neotica.data.repository.mapper

import id.neotica.data.dao.note.NoteEntity
import id.neotica.domain.Note

fun toNote(entity: NoteEntity) = Note(
    id = entity.id.toString(),
    title = entity.title,
    content = entity.content,
    createdAt = entity.createdAt,
    updatedAt = entity.updatedAt
)