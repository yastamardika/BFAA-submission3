package com.ystmrdk.sub2_bfaa.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.ystmrdk.sub2_bfaa.model.User
import cz.msebera.android.httpclient.Header
import org.json.JSONArray
import org.json.JSONObject

class MainViewModel : ViewModel() {

    private val list = ArrayList<User>()
    var usersData = MutableLiveData<ArrayList<User>>()
    var isLoading = MutableLiveData<Boolean>()
    var err = MutableLiveData<String>()

    fun fetchUser() {
        isLoading.value = true
        val data = AsyncHttpClient()
        data.addHeader("User-Agent", "request")
        data.addHeader("Authorization", "token dacdb8a9be02852a14deaf5f2e27558627481d6d")

        data.get("https://api.github.com/users", object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?
            ) {
                list.clear()
                isLoading.value = false
                val response = String(responseBody!!)
                Log.d("TAG DEBUG", "fetchUser(): $response")
                try {
                    val resArray = JSONArray(response)
                    for (i in 0 until resArray.length()) {
                        val resObj = resArray.getJSONObject(i)
                        val username: String = resObj.getString("login")
                        fetchDetail(username)
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

    private fun fetchDetail(query: String) {
        isLoading.value = true
        val data = AsyncHttpClient()
        data.addHeader("User-Agent", "request")
        data.addHeader("Authorization", "token dacdb8a9be02852a14deaf5f2e27558627481d6d")

        data.get("https://api.github.com/users/$query", object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?
            ) {
                isLoading.value = false
                val response = String(responseBody!!)
//                Log.d(MainActivity.TAG, response)
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

    fun fetchSearch(query: String) {
        isLoading.value = true
        val data = AsyncHttpClient()
        data.addHeader("User-Agent", "request")
        data.addHeader("Authorization", "token dacdb8a9be02852a14deaf5f2e27558627481d6d")

        data.get(
            "https://api.github.com/search/users?q=$query",
            object : AsyncHttpResponseHandler() {
                override fun onSuccess(
                    statusCode: Int,
                    headers: Array<out Header>?,
                    responseBody: ByteArray?
                ) {
                    list.clear()
                    isLoading.value = false
                    val response = String(responseBody!!)
//                    Log.d("TAG DEBUG", "fetchSearch(): $response")
                    try {
                        val resArray = JSONObject(response)
                        val item = resArray.getJSONArray("items")
                        for (i in 0 until item.length()) {
                            val resObj = item.getJSONObject(i)
                            val username: String = resObj.getString("login").toString()
                            Log.d("TAG DEBUG", "fetchSearch() username: $i $username")
                            fetchDetail(username)
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