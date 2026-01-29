package id.neotica.data.dao.note

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index // <--- 確保 import 這個
import androidx.room.PrimaryKey
import id.neotica.data.dao.user.UserEntityRoom

@Entity(
    tableName = "notes",
    foreignKeys = [
        ForeignKey(
            entity = UserEntityRoom::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["userId"])]
)
data class NoteEntityRoom(
    @PrimaryKey
    val id: String,
    val userId: String?,
    val title: String?,
    val content: String?,
    val isPinned: Boolean,
    val isArchived: Boolean,
    val createdAt: Long,
    val updatedAt: Long
)
