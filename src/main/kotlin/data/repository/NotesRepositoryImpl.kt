package id.neotica.data.repository

import id.neotica.data.RoomDaoProvider
import id.neotica.data.Database
import id.neotica.data.USE_ROOM
import id.neotica.data.dao.note.NoteEntity as ExposedNoteEntity
import id.neotica.data.dao.note.NoteEntityRoom
import id.neotica.data.repository.mapper.toDomain
import id.neotica.data.repository.mapper.toNote as toExposedNote
import id.neotica.domain.Note
import id.neotica.domain.repository.NotesRepository
import id.neotica.extension.toUUID
import io.ktor.server.plugins.NotFoundException
import java.util.UUID

class NotesRepositoryImpl(private val database: Database) : NotesRepository {
    override suspend fun getAllNotes(userId: String): List<Note> = database.dbQuery {
        if (USE_ROOM) {
            // 直接透過 DaoProvider 存取 DAO
            RoomDaoProvider.noteDao.getAllNotesByUser(userId).map { it.toDomain() }
        } else {
            ExposedNoteEntity.all().map(::toExposedNote).filter { it.userId == userId }
        }
    }

    override suspend fun getAllPublicNotes(): List<Note> = database.dbQuery {
        if (USE_ROOM) {
            RoomDaoProvider.noteDao.getAllPublicNotes().map { it.toDomain() }
        } else {
            ExposedNoteEntity.all().filter { it.userId == null }.map(::toExposedNote)
        }
    }

    suspend fun getNoteById(noteId: String, userId: String): Note = database.dbQuery {
        if (USE_ROOM) {
            RoomDaoProvider.noteDao.getNoteById(noteId, userId)?.toDomain()
                ?: throw NotFoundException("Note not found for the given user.")
        } else {
            val note = ExposedNoteEntity.findById(noteId.toUUID())
            if (note != null && note.userId == userId.toUUID()) {
                toExposedNote(note)
            } else throw NotFoundException()
        }
    }

    suspend fun getPublicNoteById(noteId: String): Note = database.dbQuery {
        if (USE_ROOM) {
            RoomDaoProvider.noteDao.getPublicNoteById(noteId)?.toDomain()
                ?: throw NotFoundException("Public note not found.")
        } else {
            val note = ExposedNoteEntity.findById(noteId.toUUID())
            if (note?.userId == null && note != null) {
                toExposedNote(note)
            } else throw NotFoundException()
        }
    }

    override suspend fun postNote(userId: String, note: Note): Note = database.dbQuery {
        if (USE_ROOM) {
            val newNote = NoteEntityRoom(
                id = UUID.randomUUID().toString(),
                userId = userId,
                title = note.title,
                content = note.content,
                isPinned = false,
                isArchived = false,
                createdAt = note.createdAt ?: System.currentTimeMillis(),
                updatedAt = System.currentTimeMillis()
            )
            RoomDaoProvider.noteDao.insertOrUpdateNote(newNote)
            newNote.toDomain()
        } else {
            val newNote = ExposedNoteEntity.new {
                this.userId = userId.toUUID()
                title = note.title
                content = note.content
                createdAt = note.createdAt ?: System.currentTimeMillis()
                updatedAt = System.currentTimeMillis()
            }
            toExposedNote(newNote)
        }
    }

    override suspend fun postPublicNote(note: Note): Note = database.dbQuery {
        if (USE_ROOM) {
            val newNote = NoteEntityRoom(
                id = UUID.randomUUID().toString(),
                userId = null,
                title = note.title,
                content = note.content,
                isPinned = false,
                isArchived = false,
                createdAt = note.createdAt ?: System.currentTimeMillis(),
                updatedAt = System.currentTimeMillis()
            )
            RoomDaoProvider.noteDao.insertOrUpdateNote(newNote)
            newNote.toDomain()
        } else {
            val newNote = ExposedNoteEntity.new {
                title = note.title
                content = note.content
                createdAt = note.createdAt ?: System.currentTimeMillis()
                updatedAt = System.currentTimeMillis()
            }
            toExposedNote(newNote)
        }
    }

    override suspend fun updateNote(id: String, note: Note): Note = database.dbQuery {
        val now = System.currentTimeMillis()
        if (USE_ROOM) {
            RoomDaoProvider.noteDao.updateNote(id, note.title, note.content, now)
            note.copy(updatedAt = now)
        } else {
            val updateNote = ExposedNoteEntity.findById(id.toUUID()) ?: throw NotFoundException()
            updateNote.apply {
                this.title = note.title
                this.content = note.content
                this.updatedAt = now
            }
            toExposedNote(updateNote)
        }
    }

    override suspend fun deleteNote(id: String): String = database.dbQuery {
        if (USE_ROOM) {
            val deletedRows = RoomDaoProvider.noteDao.deleteNoteById(id)
            if (deletedRows > 0) {
                "Note with id $id deleted."
            } else {
                throw NotFoundException("Note not found or could not be deleted.")
            }
        } else {
            val note = ExposedNoteEntity.findById(id.toUUID()) ?: throw NotFoundException()
            val title = note.title
            note.delete()
            "${title} deleted."
        }
    }
}