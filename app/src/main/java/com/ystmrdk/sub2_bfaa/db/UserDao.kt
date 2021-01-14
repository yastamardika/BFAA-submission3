package com.ystmrdk.sub2_bfaa.db

import android.database.Cursor
import androidx.room.*
import com.ystmrdk.sub2_bfaa.model.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Query("SELECT * FROM users")
    fun getUsers(): Flow<List<User>>

    @Query("SELECT * FROM users")
    fun getUsersCursor(): Cursor

    @Query("SELECT COUNT(id) FROM users WHERE id=:id")
    fun getCount(id: Int): Int

    @Insert
    fun insertUser(user: User)

    @Delete
    fun deleteUser(user: User)
}