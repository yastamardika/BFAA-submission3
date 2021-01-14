package com.ystmrdk.consumerapp.model

import android.os.Parcelable

@Parcelize
//@Entity(tableName = Constants.TABLE_NAME)
data class User(
//    @PrimaryKey
    var id: Int,
    var username: String? = "",
    var name: String? = "",
    var avatar: String? = "",
    var company: String? = "",
    var location: String? = "",
    var repository: String? = "",
    var follower: String? = "",
    var following: String? = ""
): Parcelable
