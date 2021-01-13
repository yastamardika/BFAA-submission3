package com.ystmrdk.sub2_bfaa.viewmodel

import androidx.lifecycle.MutableLiveData
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.ystmrdk.sub2_bfaa.model.User
import com.ystmrdk.sub2_bfaa.util.Constant
import cz.msebera.android.httpclient.Header
import org.json.JSONArray
import org.json.JSONObject


class FollowingViewModel {
    private val list = ArrayList<User>()
    var usersData = MutableLiveData<ArrayList<User>>()
    var isLoading = MutableLiveData<Boolean>()
    var err = MutableLiveData<String>()

    fun fetchFollowing(q: String) {
        isLoading.value = true
        val data = AsyncHttpClient()
        data.addHeader("User-Agent", "request")
        data.addHeader("Authorization", Constant.TOKEN)
        data.get("https://api.github.com/users/$q/following", object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray
            ) {
                list.clear()
                isLoading.value = false
                val res = String(responseBody)
                try {
                    val resArray = JSONArray(res)
                    for (i in 0 until resArray.length()) {
                        val resObject = resArray.getJSONObject(i)
                        val name: String = resObject.getString("login")
                        fetchDetail(name)
                    }
                } catch (e: Exception) {
                    err.value = e.message
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable
            ) {
                isLoading.value = false
                err.value = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error.message}"
                }
            }
        })
    }

    private fun fetchDetail(query: String) {
        isLoading.value = true
        val data = AsyncHttpClient()
        data.addHeader("User-Agent", "request")
        data.addHeader("Authorization", Constant.TOKEN)

        data.get("https://api.github.com/users/$query", object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray
            ) {
                isLoading.value = false
                val response = String(responseBody)
                try {
                    val resObject = JSONObject(response)
                    val username: String = resObject.getString("login").toString()
                    val name: String = resObject.getString("name").toString()
                    val avatar: String = resObject.getString("avatar_url").toString()
                    val company: String = resObject.getString("company").toString()
                    val location: String = resObject.getString("location").toString()
                    val repository: String = resObject.getString("public_repos").toString()
                    val follower: String = resObject.getString("followers").toString()
                    val following: String = resObject.getString("following").toString()
                    list.add(
                        User(
                            username,
                            if (name == "null") "-" else name,
                            avatar,
                            if (company == "null") "-" else company,
                            if (location == "null") "-" else location,
                            repository,
                            follower,
                            following
                        )
                    )
                    usersData.value = list
                } catch (e: Exception) {
                    err.value = e.message
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                isLoading.value = false
                err.value = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error?.message}"
                }
            }

        })
    }

}