package com.ystmrdk.sub2_bfaa.repository

import com.ystmrdk.sub2_bfaa.db.UserDao
import com.ystmrdk.sub2_bfaa.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class FavoriteRepository(private val userDao: UserDao) {

    val favorites: Flow<List<User>> = userDao.getUsers()

    suspend fun insert(user: User) = withContext(Dispatchers.IO){
        userDao.insertUser(user)
    }

    suspend fun delete(user: User) = withContext(Dispatchers.IO){
        userDao.deleteUser(user)
    }

    suspend fun getCount(id: Int) = withContext(Dispatchers.IO){
        userDao.getCount(id)
    }

}