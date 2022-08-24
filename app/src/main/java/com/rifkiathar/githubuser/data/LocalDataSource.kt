package com.rifkiathar.githubuser.data

import com.rifkiathar.githubuser.base.BaseDataSource
import com.rifkiathar.githubuser.base.ResourceState
import com.rifkiathar.githubuser.base.ResponseWrapper
import com.rifkiathar.githubuser.model.entity.UserEntity
import com.rifkiathar.githubuser.model.response.DetailResponse

class LocalDataSource(private val appDatabase: AppDatabase) : BaseDataSource(){

    private suspend fun <T> getResult(request: suspend () -> T): ResourceState<ResponseWrapper<T>> {
        return try {
            val res = request.invoke()
            return ResourceState.Success(ResponseWrapper(data = res, errorData = null))
        } catch (e: Exception) {
            errorState(msg = e.toString())
        }
    }

    suspend fun getAllUser() = getResult {
        appDatabase.userDao().getAllUsers()
    }

    suspend fun isFavoriteUser(user: String) = getResult {
        appDatabase.userDao().isFavoriteUser(user)
    }
    suspend fun addFavorite(user: UserEntity) = getResult {
        appDatabase.userDao().addFavorite(user)
    }
    suspend fun deleteFavorite(id: Int) = getResult {
        appDatabase.userDao().deleteFavorite(id)
    }

    suspend fun updateFavoriteUser(user: UserEntity) = getResult {
        appDatabase.userDao().updateFavoriteUser(user)
    }

}