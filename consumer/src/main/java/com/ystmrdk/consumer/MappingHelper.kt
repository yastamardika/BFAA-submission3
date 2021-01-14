package com.ystmrdk.consumer

import android.database.Cursor

object UserMappingHelper {
    fun mapCursorToArrayList(usersCursor: Cursor): ArrayList<User> {
        val list = ArrayList<User>()
        while (usersCursor.moveToNext()) {
            val id =
                usersCursor.getInt(usersCursor.getColumnIndexOrThrow("id"))
            val username =
                usersCursor.getString(usersCursor.getColumnIndexOrThrow("username"))
            val name =
                usersCursor.getString(usersCursor.getColumnIndexOrThrow("name"))
            val location =
                usersCursor.getString(usersCursor.getColumnIndexOrThrow("location"))
            val company =
                usersCursor.getString(usersCursor.getColumnIndexOrThrow("company"))
            val avatar =
                usersCursor.getString(usersCursor.getColumnIndexOrThrow("avatar"))
            list.add(User(id, username, name, avatar, company, location))
        }
        return list
    }

}