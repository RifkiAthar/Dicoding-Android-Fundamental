package com.rifkiathar.githubuser.data

import com.rifkiathar.githubuser.base.ResourceState
import com.rifkiathar.githubuser.base.ResponseWrapper
import com.rifkiathar.githubuser.model.entity.UserEntity
import com.rifkiathar.githubuser.model.response.DetailResponse
import com.rifkiathar.githubuser.model.response.SearchResponse
import com.rifkiathar.githubuser.model.response.UserResponse

class AppRepository(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) {
    suspend fun searchUser(user: String): StateWrapper<SearchResponse> {
        return remoteDataSource.searchUser(user)
    }
    suspend fun getDetail(user: String): StateWrapper<DetailResponse> {
        return remoteDataSource.getDetail(user)
    }
    suspend fun getUserFollowing(user: String): StateWrapper<List<UserResponse>> {
        return remoteDataSource.getUserFollowing(user)
    }
    suspend fun getUserFollowers(user: String): StateWrapper<List<UserResponse>> {
        return remoteDataSource.getUserFollowers(user)
    }
    suspend fun getAllUser(): StateWrapper<List<UserEntity>> {
        return localDataSource.getAllUser()
    }
    suspend fun addFavorite(user: UserEntity) {
        localDataSource.addFavorite(user)
    }
    suspend fun deleteFavorite(id: Int) {
        localDataSource.deleteFavorite(id)
    }

    suspend fun isFavoriteUser(user: String): StateWrapper<Boolean> {
        return localDataSource.isFavoriteUser(user)
    }

    suspend fun updateFavoriteUser(user: UserEntity) {
        localDataSource.updateFavoriteUser(user)
    }

}
typealias StateWrapper<T> = ResourceState<ResponseWrapper<T>>
