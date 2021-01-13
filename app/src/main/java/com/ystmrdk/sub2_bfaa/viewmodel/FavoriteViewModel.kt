package com.ystmrdk.sub2_bfaa.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.ystmrdk.sub2_bfaa.db.FavoriteDatabase
import com.ystmrdk.sub2_bfaa.model.User
import com.ystmrdk.sub2_bfaa.repository.FavoriteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn

class FavoriteViewModel : ViewModel() {

    private var favDb: FavoriteDatabase? = null
    private lateinit var repository: FavoriteRepository

    val favorites: LiveData<List<User>>
    get() = repository.favorites.flowOn(Dispatchers.Main)
        .asLiveData(context = viewModelScope.coroutineContext)

    fun init(context: Context) {
        favDb = FavoriteDatabase.getDatabase(context)
        favDb?.let {
            repository = FavoriteRepository(it.userDao())
        }
    }
}