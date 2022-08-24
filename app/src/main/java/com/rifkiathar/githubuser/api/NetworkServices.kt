package com.rifkiathar.githubuser.api

import com.rifkiathar.githubuser.model.response.BaseResponse
import com.rifkiathar.githubuser.model.response.DetailResponse
import com.rifkiathar.githubuser.model.response.SearchResponse
import com.rifkiathar.githubuser.model.response.UserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NetworkServices {
    @GET("search/users")
    suspend fun getSearchUser(
        @Query("q") keyword: String
    ) : SuccessCallback<SearchResponse>

    @GET("users/{username}")
    suspend fun getDetail(
        @Path("username") username: String
    ): SuccessCallback<DetailResponse>

    @GET("users/{username}/followers")
    suspend fun getUserFollowers(
        @Path("username") username: String
    ): SuccessCallback<List<UserResponse>>

    @GET("users/{username}/following")
    suspend fun getUserFollowing(
        @Path("username") username: String
    ): SuccessCallback<List<UserResponse>>
}
typealias SuccessCallback<T> = Response<T>