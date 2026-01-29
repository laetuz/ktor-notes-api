package id.neotica.data.dao.user

import androidx.room.*

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertUser(user: UserEntityRoom)

    @Query("SELECT * FROM users WHERE username = :username")
    suspend fun getUserByUsername(username: String): UserEntityRoom?

    @Query("SELECT * FROM users WHERE id = :id")
    suspend fun getUserById(id: String): UserEntityRoom?

    @Query("SELECT * FROM users")
    suspend fun getAllUsers(): List<UserEntityRoom>

    @Delete
    suspend fun deleteUser(user: UserEntityRoom)
}