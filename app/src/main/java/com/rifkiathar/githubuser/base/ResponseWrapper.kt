package com.rifkiathar.githubuser.base

sealed class ResourceState<out T> {
    data class Success<out T>(val result: T) : ResourceState<T>()
    data class Error<T>(val error: T) : ResourceState<T>()
    object Loading : ResourceState<Nothing>()
}


data class ErrorResponse(
    val message: String? = "")

data class ResponseWrapper<out T>(
    val data: T?,
    val errorData: ErrorResponse?
)