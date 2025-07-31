package id.neotica.data.repository.mapper

import id.neotica.data.dao.note.NoteEntity
import id.neotica.data.dao.user.UserEntity
import id.neotica.domain.NeoUser
import id.neotica.domain.Note

fun toNote(entity: NoteEntity) = Note(
    id = entity.id.toString(),
    title = entity.title,
    content = entity.content,
    createdAt = entity.createdAt,
    updatedAt = entity.updatedAt
)

fun toUser(entity: UserEntity) = NeoUser(
    id = entity.id.toString(),
    username = entity.username,
    email = entity.email,
    password = entity.password,
    createdAt = entity.createdAt
)