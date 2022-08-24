package com.rifkiathar.githubuser.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rifkiathar.githubuser.utils.SingleLiveEvent

abstract class BaseViewModel : ViewModel() {
    protected val errorResponse = MutableLiveData<ErrorResponse>()
    protected val errorSingleResponse = MutableLiveData<SingleLiveEvent<ErrorResponse?>>()
    protected val isLoading = MutableLiveData<Boolean>()
    protected val isFetching = MutableLiveData<Boolean>()
    protected val isEmptyData = MutableLiveData<Boolean>()

    fun observeError(): LiveData<ErrorResponse> = errorResponse
    fun observeSingleError(): LiveData<SingleLiveEvent<ErrorResponse?>> = errorSingleResponse
    fun observeLoading(): LiveData<Boolean> = isLoading
    fun observeFetching(): LiveData<Boolean> = isFetching
    fun observeEmptyData(): LiveData<Boolean> = isEmptyData
}