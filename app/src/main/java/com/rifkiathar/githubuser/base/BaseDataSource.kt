package com.rifkiathar.githubuser.base

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.Response
import javax.net.ssl.HttpsURLConnection

abstract class BaseDataSource {
    fun <T> errorState(
        errorCode: Int? = HttpsURLConnection.HTTP_INTERNAL_ERROR,
        msg: String
    ): ResourceState<ResponseWrapper<T>> {
        return ResourceState.Error(ResponseWrapper(null, ErrorResponse(msg)))
    }

    suspend fun <T> suspendDataResult(request: suspend () -> ResourceState<T>): ResourceState<T> {
        return withContext(Dispatchers.IO) {
            request.invoke()
        }
    }

    suspend fun suspendDataDownload(request: suspend () -> Response<ResponseBody>): Response<ResponseBody> {
        return withContext(Dispatchers.IO) {
            request.invoke()
        }
    }
}