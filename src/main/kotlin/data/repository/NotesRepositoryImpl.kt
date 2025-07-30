package id.neotica.data.repository

import id.neotica.data.Database
import id.neotica.data.dao.note.NoteEntity
import id.neotica.data.repository.mapper.toNote
import id.neotica.domain.Note
import id.neotica.extension.toUUID
import io.ktor.server.plugins.NotFoundException

class NotesRepositoryImpl(private val database: Database) {
    suspend fun getAllNotes() = database.dbQuery {
        val notes = NoteEntity.all()

        notes.map(::toNote)
    }

    suspend fun postNote(note: Note) = database.dbQuery {
        val newNote = NoteEntity.new {
            title = note.title
            content = note.content
            createdAt = note.createdAt?: System.currentTimeMillis()
            updatedAt = System.currentTimeMillis()
        }

        toNote(newNote)
    }

    suspend fun deleteNote(id: String) = database.dbQuery {
        val note = NoteEntity.findById(id.toUUID())?: throw NotFoundException()

        note.delete()
    }
}