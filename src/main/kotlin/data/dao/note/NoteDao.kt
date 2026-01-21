package id.neotica.data.dao.note

import androidx.room.*

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateNote(note: NoteEntityRoom)

    @Query("SELECT * FROM notes WHERE userId = :userId ORDER BY createdAt DESC")
    suspend fun getAllNotesByUser(userId: String): List<NoteEntityRoom>

    @Query("SELECT * FROM notes WHERE userId IS NULL ORDER BY createdAt DESC")
    suspend fun getAllPublicNotes(): List<NoteEntityRoom>

    @Query("SELECT * FROM notes WHERE id = :noteId AND userId = :userId")
    suspend fun getNoteById(noteId: String, userId: String): NoteEntityRoom?

    @Query("SELECT * FROM notes WHERE id = :noteId AND userId IS NULL")
    suspend fun getPublicNoteById(noteId: String): NoteEntityRoom?

    @Query("UPDATE notes SET title = :title, content = :content, updatedAt = :updatedAt WHERE id = :id")
    suspend fun updateNote(id: String, title: String?, content: String?, updatedAt: Long)

    @Query("DELETE FROM notes WHERE id = :id")
    suspend fun deleteNoteById(id: String): Int // 返回刪除的行數
}