package id.neotica.domain.repository

import id.neotica.domain.Note

interface NotesRepository {
    suspend fun getAllNotes(userId: String): List<Note>
    suspend fun getAllPublicNotes(): List<Note>
    suspend fun postNote(userId: String, note: Note): Note
    suspend fun postPublicNote(note: Note): Note
    suspend fun updateNote(id: String, note: Note): Note
    suspend fun deleteNote(id: String): String
}