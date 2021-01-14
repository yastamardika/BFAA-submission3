package com.ystmrdk.consumer

import android.net.Uri

object DatabaseContract {
    const val AUTHORITY = "com.ystmrdk.sub2_bfaa"
    const val SCHEME = "content"
    const val TABLE_NAME = "users"

//    internal class UsersColumns : BaseColumns {
//        companion object {
            val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build()
//        }
//    }
}