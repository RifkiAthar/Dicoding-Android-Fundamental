package com.rifkiathar.githubuser.data

import com.rifkiathar.githubuser.api.NetworkServices
import com.rifkiathar.githubuser.base.ResourceState
import com.rifkiathar.githubuser.base.ResponseWrapper
import com.rifkiathar.githubuser.model.response.BaseResponse
import com.rifkiathar.githubuser.base.BaseDataSource
import retrofit2.Response
import java.net.UnknownHostException


class RemoteDataSource(private val networkServices: NetworkServices) : BaseDataSource()  {
    private suspend fun <T> getResult(request: suspend () -> Response<T>): ResourceState<ResponseWrapper<T>> {
        return try {
            val response = request()
            val body = response.body()
            if (response.isSuccessful.not() || body == null) {
                return errorState(response.code(), response.message())
            }

            return ResourceState.Success(
                ResponseWrapper(
                    body,
                    null
                )
            )
        } catch (e: Exception) {
            errorState(msg = if (e is UnknownHostException) NO_INTERNET else e.localizedMessage.orEmpty())
        }
    }

    suspend fun searchUser(user: String) = suspendDataResult {
        getResult {
            networkServices.getSearchUser(user)
        }
    }

    suspend fun getDetail(user: String) = suspendDataResult {
        getResult {
            networkServices.getDetail(user)
        }
    }

    suspend fun getUserFollowing(user: String) = suspendDataResult {
        getResult {
            networkServices.getUserFollowing(user)
        }
    }

    suspend fun getUserFollowers(user: String) = suspendDataResult {
        getResult {
            networkServices.getUserFollowers(user)
        }
    }

    companion object {
        private const val NO_INTERNET = "Tidak ada koneksi internet"
    }
}