package id.neotica.data.repository.mapper

import id.neotica.data.dao.note.NoteEntityRoom
import id.neotica.data.dao.user.UserEntityRoom
import id.neotica.domain.NeoUser
import id.neotica.domain.Note

fun NoteEntityRoom.toDomain(): Note {
    return Note(
        id = this.id,
        userId = this.userId,
        title = this.title,
        content = this.content,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt
    )
}

fun UserEntityRoom.toDomain(): NeoUser {
    return NeoUser(
        id = this.id,
        username = this.username,
        email = this.email,
        password = "",
        createdAt = this.createdAt
    )
}