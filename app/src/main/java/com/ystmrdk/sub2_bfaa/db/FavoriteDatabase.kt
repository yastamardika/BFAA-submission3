package com.ystmrdk.sub2_bfaa.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ystmrdk.sub2_bfaa.model.User

@Database(entities = [User::class], exportSchema = false, version = 1)
abstract class FavoriteDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {
        var INSTANCE: FavoriteDatabase? = null

        fun getDatabase(context: Context): FavoriteDatabase? {
            if (INSTANCE == null) {
                synchronized(FavoriteDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        FavoriteDatabase::class.java,
                        "FavoriteDb"
                    ).build()
                }
            }
            return INSTANCE
        }

        fun destroyDatabase() {
            INSTANCE = null
        }
    }
}