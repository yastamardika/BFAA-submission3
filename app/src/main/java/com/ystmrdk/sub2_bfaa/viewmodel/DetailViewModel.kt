package com.ystmrdk.sub2_bfaa.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ystmrdk.sub2_bfaa.db.FavoriteDatabase
import com.ystmrdk.sub2_bfaa.model.User
import com.ystmrdk.sub2_bfaa.repository.FavoriteRepository
import kotlinx.coroutines.launch

class DetailViewModel : ViewModel() {

    private var favDb: FavoriteDatabase? = null
    private var repository: FavoriteRepository? = null

    private var _userData: MutableLiveData<User> = MutableLiveData()
    var userData: LiveData<User> = _userData

    private var _count: MutableLiveData<Int> = MutableLiveData()
    var count: LiveData<Int> = _count

    fun init(context: Context) {
        favDb = FavoriteDatabase.getDatabase(context)
        favDb?.let {
            repository = FavoriteRepository(it.userDao())
        }
    }

    fun setUserData(user: User) {
        _userData.postValue(user)
    }

    fun addToFavorite() {
        viewModelScope.launch {
            favDb?.let {
                userData.value?.let {
                    repository?.insert(it)
                }
            }
        }
    }

    fun deleteFromFavorite(){
        viewModelScope.launch {
            favDb?.let {
                userData.value?.let {
                    repository?.delete(it)
                }
            }
        }
    }

    fun getCount(id: Int) {
        viewModelScope.launch {
            favDb?.let {
                repository?.getCount(id)?.let {
                    _count.postValue(it)
                }
            }
        }
    }
}