package com.example.yatrimitrasharedmobilityapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.yatrimitrasharedmobilityapp.data.model.UserEntity

@Database(entities = [UserEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}
