package com.ystmrdk.sub2_bfaa.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.ystmrdk.sub2_bfaa.db.DatabaseContract.AUTHORITY
import com.ystmrdk.sub2_bfaa.db.FavoriteDatabase
import com.ystmrdk.sub2_bfaa.db.UserDao
import com.ystmrdk.sub2_bfaa.utils.Constants

class FavoriteProvider : ContentProvider() {

    companion object {
        private const val USERS = 1
        private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)
        private lateinit var userDao: UserDao

        init {
            uriMatcher.addURI(
                AUTHORITY, Constants.TABLE_NAME,
                USERS
            )
        }
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        return null
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        return when (uriMatcher.match(uri)) {
            USERS -> userDao.getUsersCursor()
            else -> null
        }
    }

    override fun onCreate(): Boolean {
        val favDb = context?.let { FavoriteDatabase.getDatabase(it) }
        favDb?.let {
            userDao = it.userDao()
        }
        return true
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        return 0
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        return 0
    }

    override fun getType(uri: Uri): String? {
        return null
    }


}