package com.ystmrdk.sub2_bfaa.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ystmrdk.sub2_bfaa.utils.Constants
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = Constants.TABLE_NAME)
data class User(
    @PrimaryKey
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
