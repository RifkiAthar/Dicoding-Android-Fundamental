package com.rifkiathar.githubuser.di.network

import com.rifkiathar.githubuser.BuildConfig
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class HeaderInterceptor(): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val newRequest = addCommonHeaders(request)
        return chain.proceed(newRequest)
    }

    private fun addCommonHeaders(request: Request): Request {
        return request.newBuilder()
            .addHeader("Content-Type", "application/json")
            .addHeader("Authorization", BuildConfig.PAT)
            .build()
    }
}