package id.neotica.data.repository

import id.neotica.data.Database
import id.neotica.data.dao.note.NoteEntity
import id.neotica.data.dao.user.UserEntity
import id.neotica.data.repository.mapper.toNote
import id.neotica.domain.Note
import org.jetbrains.exposed.sql.not
import java.util.UUID


class NotesRepositoryImpl(private val database: Database) {
    suspend fun getAllNotes() = database.dbQuery {
        val notes = NoteEntity.all()

        notes.map(::toNote)
//        "lol"
    }

    suspend fun postNote(note: Note) = database.dbQuery {
//        val note = n
        val newNote = NoteEntity.new {
//            userId = UserEntity.findById()
            title = note.title
            content = note.content
            createdAt = note.createdAt?: System.currentTimeMillis()
            updatedAt = System.currentTimeMillis()
        }

        toNote(newNote)
    }
}