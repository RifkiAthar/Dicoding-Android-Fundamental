package com.rifkiathar.githubuser.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rifkiathar.githubuser.data.dao.UserDao
import com.rifkiathar.githubuser.model.entity.UserEntity

@Database(
    entities = [
        UserEntity::class
               ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao() : UserDao
}