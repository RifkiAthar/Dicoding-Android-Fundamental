package com.rifkiathar.githubuser.data.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rifkiathar.githubuser.model.entity.UserEntity

@Database(entities = [UserEntity::class], version = 1, exportSchema = false)
abstract class UserDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
}