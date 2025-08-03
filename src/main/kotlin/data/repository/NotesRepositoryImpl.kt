package id.neotica.data.repository

import id.neotica.data.Database
import id.neotica.data.dao.note.NoteEntity
import id.neotica.data.repository.mapper.toNote
import id.neotica.domain.Note
import id.neotica.domain.repository.NotesRepository
import id.neotica.extension.toUUID
import io.ktor.server.plugins.NotFoundException

class NotesRepositoryImpl(private val database: Database): NotesRepository {
    override suspend fun getAllNotes(userId: String) = database.dbQuery {
        val notes = NoteEntity.all()

        notes.map(::toNote).filter { it.userId == userId }
    }

    override suspend fun getAllPublicNotes(): List<Note> = database.dbQuery {
        val notes = NoteEntity.all().filter { it.userId == null }

        notes.map(::toNote)
    }

    override suspend fun postNote(userId: String, note: Note) = database.dbQuery {
        val newNote = NoteEntity.new {
            println("✨ id2: $userId")
            println("✨ id3: ${userId.toUUID()}")
            this.userId = userId.toUUID()
            title = note.title
            content = note.content
            createdAt = note.createdAt?: System.currentTimeMillis()
            updatedAt = System.currentTimeMillis()
        }

        toNote(newNote)
    }

    override suspend fun postPublicNote(note: Note): Note = database.dbQuery {
        val newNote = NoteEntity.new {
            title = note.title
            content = note.content
            createdAt = note.createdAt?: System.currentTimeMillis()
            updatedAt = System.currentTimeMillis()
        }

        toNote(newNote)
    }

    override suspend fun updateNote(id: String, note: Note) = database.dbQuery {
        val updateNote = NoteEntity.findById(id.toUUID())?: throw NotFoundException()

        updateNote.apply {
            this.userId = note.userId?.toUUID()
            this.title = note.title
            this.content = note.content
            this.updatedAt = System.currentTimeMillis()
        }

        toNote(updateNote)
    }

    override suspend fun deleteNote(id: String) = database.dbQuery {
        val note = NoteEntity.findById(id.toUUID())?: throw NotFoundException()

        note.delete()
        "${note.title} deleted."
    }
}