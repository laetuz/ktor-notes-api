package id.neotica.data

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import id.neotica.data.dao.note.NoteDao
import id.neotica.data.dao.note.NoteEntityRoom
import id.neotica.data.dao.user.UserDao
import id.neotica.data.dao.user.UserEntityRoom
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import java.io.File

@Database(
    entities = [UserEntityRoom::class, NoteEntityRoom::class],
    version = 1,
    exportSchema = true
)
abstract class SqliteDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun noteDao(): NoteDao
}

