package com.rifkiathar.githubuser.data.dao

import androidx.room.*
import com.rifkiathar.githubuser.model.entity.UserEntity
import com.rifkiathar.githubuser.model.response.DetailResponse

@Dao
interface UserDao {

    @Query("SELECT * FROM user_favorite")
    suspend fun getAllUsers(): List<UserEntity>

    @Query("SELECT EXISTS(SELECT * FROM user_favorite where login = :user)")
    suspend fun isFavoriteUser(user: String): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(user: UserEntity)

    @Query("DELETE FROM user_favorite WHERE id = :id")
    suspend fun deleteFavorite(id: Int)

    @Update
    fun updateFavoriteUser(user: UserEntity)
}