package com.example.yatrimitrasharedmobilityapp.data.repository

import com.example.yatrimitrasharedmobilityapp.data.local.UserDao
import com.example.yatrimitrasharedmobilityapp.data.model.UserEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val userDao: UserDao
) {
    fun getUser(): Flow<UserEntity?> = userDao.getUser()

    suspend fun saveUser(user: UserEntity) {
        userDao.insertUser(user)
    }

    suspend fun logout() {
        userDao.clearUser()
    }
}
