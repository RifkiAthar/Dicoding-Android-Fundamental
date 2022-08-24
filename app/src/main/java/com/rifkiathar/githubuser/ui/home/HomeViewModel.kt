package com.rifkiathar.githubuser.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.rifkiathar.githubuser.base.BaseViewModel
import com.rifkiathar.githubuser.base.ResourceState
import com.rifkiathar.githubuser.data.AppRepository
import com.rifkiathar.githubuser.di.dataStore.SettingPreferences
import com.rifkiathar.githubuser.model.response.SearchResponse
import com.rifkiathar.githubuser.model.response.UserResponse
import com.rifkiathar.githubuser.utils.SingleLiveEvent
import kotlinx.coroutines.launch
import timber.log.Timber

class HomeViewModel(private val appRepository: AppRepository) : BaseViewModel() {

    private val userSearchResponse = MutableLiveData<List<UserResponse>?>()
    private val dataCount = MutableLiveData<Int?>()

    fun observeSearchResponse(): LiveData<List<UserResponse>?> = userSearchResponse
    fun observeDataCount(): LiveData<Int?> = dataCount

    fun searchUser(user: String) {
        Timber.d("DATA $user")
        isLoading.postValue(true)
        viewModelScope.launch {
            when(val result = appRepository.searchUser(user)) {
                is ResourceState.Success -> {
                     result.result.data?.let { data ->
                         userSearchResponse.postValue(data.items)
                         dataCount.postValue(data.totalCount)
                    }
                    isLoading.postValue(false)
                }
                is ResourceState.Error -> {
                    errorResponse.postValue(result.error.errorData)
                    isLoading.postValue(false)
                }
                else -> isLoading.postValue(false)
            }
        }
    }
}