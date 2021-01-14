package com.ystmrdk.sub2_bfaa.provider

import android.content.*
import android.database.Cursor
import android.net.Uri
import android.util.Log
import androidx.annotation.Nullable
//import com.ystmrdk.sub2_bfaa.db.DatabaseContract.AUTHORITY
import com.ystmrdk.sub2_bfaa.db.FavoriteDatabase
import com.ystmrdk.sub2_bfaa.db.UserDao
import com.ystmrdk.sub2_bfaa.utils.Constants
import kotlin.jvm.Throws

//class FavoriteProvider : ContentProvider() {
//
//    companion object {
//        private const val USERS = 1
//        private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)
//        private lateinit var userDao: UserDao
//
//        init {
//            uriMatcher.addURI(
//                AUTHORITY, Constants.TABLE_NAME,
//                USERS
//            )
//        }
//    }
//
//    override fun insert(uri: Uri, values: ContentValues?): Uri? {
//        return null
//    }
//
//    override fun query(
//        uri: Uri,
//        projection: Array<out String>?,
//        selection: String?,
//        selectionArgs: Array<out String>?,
//        sortOrder: String?
//    ): Cursor? {
//        Log.d("CEK", "debug")
//        return when (uriMatcher.match(uri)) {
//            USERS -> userDao.getUsersCursor()
//            else -> null
//        }
//    }
//
//    override fun onCreate(): Boolean {
//        val favDb = context?.let { FavoriteDatabase.getDatabase(it) }
//        favDb?.let {
//            userDao = it.userDao()
//        }
//        return true
//    }
//
//    override fun update(
//        uri: Uri,
//        values: ContentValues?,
//        selection: String?,
//        selectionArgs: Array<out String>?
//    ): Int {
//        return 0
//    }
//
//    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
//        return 0
//    }
//
//    override fun getType(uri: Uri): String? {
//        return null
//    }
//
//}


class FavoriteProvider : ContentProvider() {
    companion object {
        const val AUTHORITY = "com.ystmrdk.sub2_bfaa"
        val URI_MENU = Uri.parse(
            "content://$AUTHORITY/users"
        )

        private const val USERS = 1
        private val MATCHER = UriMatcher(UriMatcher.NO_MATCH)

        init {
            MATCHER.addURI(
                AUTHORITY,
                "users",
                USERS
            )
        }
    }

    override fun onCreate(): Boolean {
        return true
    }

    // CompanyTMRecord -> Table, CompanyTMDB -> RoomDataBase
// and CompanyTMDao -> DAO
    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String?>?
    ): Int {
        return 0
    }

    override fun delete(
        uri: Uri, selection: String?,
        selectionArgs: Array<String?>?
    ): Int {
        return 0
    }


    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        return null
    }

    @Nullable
    override fun query(
        uri: Uri, @Nullable projection: Array<String>?, @Nullable selection: String?,
        @Nullable selectionArgs: Array<String>?, @Nullable sortOrder: String?
    ): Cursor? {

        val code = MATCHER.match(uri)
        return if (code == USERS) {
            val context = context ?: return null
            val userDao: UserDao = FavoriteDatabase.getDatabase(context)!!.userDao()
            val cursor: Cursor
            cursor = userDao.getUsersCursor()
            cursor.setNotificationUri(context.contentResolver, uri)
            cursor
        } else {
            throw IllegalArgumentException("Unknown URI: $uri")
        }
    }

    @Nullable
    override fun getType(uri: Uri): String? {
        return when (MATCHER.match(uri)) {
            USERS -> "vnd.android.cursor.dir/$AUTHORITY.users"
            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }
    }


    @Throws(OperationApplicationException::class)
    override fun applyBatch(
        operations: ArrayList<ContentProviderOperation?>
    ): Array<ContentProviderResult?> {
        val context = context ?: return arrayOfNulls(0)
        val database: FavoriteDatabase = FavoriteDatabase.getDatabase(context)!!
        database.beginTransaction()
        return try {
            val result = super.applyBatch(operations)
            database.setTransactionSuccessful()
            result
        } finally {
            database.endTransaction()
        }
    }


}