package com.rifkiathar.githubuser.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.rifkiathar.githubuser.base.BaseViewModel
import com.rifkiathar.githubuser.base.ResourceState
import com.rifkiathar.githubuser.data.AppRepository
import com.rifkiathar.githubuser.di.dataStore.SettingPreferences
import com.rifkiathar.githubuser.model.entity.UserEntity
import com.rifkiathar.githubuser.model.response.DetailResponse
import com.rifkiathar.githubuser.model.response.UserResponse
import com.rifkiathar.githubuser.utils.SingleLiveEvent
import kotlinx.coroutines.launch

class DetailViewModel(private val appRepository: AppRepository) : BaseViewModel() {

    private val detailResponse = MutableLiveData<DetailResponse>()
    private val followingResponse = MutableLiveData<List<UserResponse>>()
    private val followersResponse = MutableLiveData<List<UserResponse>>()
    private val isFavorite = MutableLiveData<Boolean>()
    private val favoriteMessage = MutableLiveData<SingleLiveEvent<Boolean>>()

    fun observeDetailResponse(): LiveData<DetailResponse> = detailResponse
    fun observeFollowing(): LiveData<List<UserResponse>> = followingResponse
    fun observeFollowers(): LiveData<List<UserResponse>> = followersResponse
    fun observeIsFavorite(): LiveData<Boolean> = isFavorite
    fun observeFavoriteMessage(): LiveData<SingleLiveEvent<Boolean>> = favoriteMessage

    fun getDetail(user: String) {
        isLoading.postValue(true)
        viewModelScope.launch {
            when (val result = appRepository.getDetail(user)) {
                is ResourceState.Success -> {
                    result.result.data.let { data ->
                        detailResponse.postValue(data)
                    }
                    isFavorite(user)
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

    private suspend fun isFavorite(user: String) {
        viewModelScope.launch {
            when(val result = appRepository.isFavoriteUser(user)) {
                is ResourceState.Success -> {
                    isFavorite.postValue(result.result.data ?: false)
                }
                is ResourceState.Error -> errorResponse.postValue(result.error.errorData)
                else -> {}
            }
        }
    }

    fun getUserFollowing(user: String) {
        viewModelScope.launch {
            when (val result = appRepository.getUserFollowing(user)) {
                is ResourceState.Success -> {
                    result.result.data.let { data ->
                        followingResponse.postValue(data)
                    }
                }
                is ResourceState.Error -> {
                    errorResponse.postValue(result.error.errorData)
                }
                else -> {}
            }
        }
    }

    fun getUserFollowers(user: String) {
        viewModelScope.launch {
            when (val result = appRepository.getUserFollowers(user)) {
                is ResourceState.Success -> {
                    result.result.data.let { data ->
                        followersResponse.postValue(data)
                    }
                }
                is ResourceState.Error -> {
                    errorResponse.postValue(result.error.errorData)
                }
                else -> {}
            }
        }
    }

    fun favorite(user: DetailResponse, isDelete: Boolean) {
        viewModelScope.launch {
            if (isDelete) {
                appRepository.deleteFavorite(user.id ?: 0)
                favoriteMessage.postValue(SingleLiveEvent(true))
                isFavorite.postValue(false)
            } else {
                val userFavorite = UserEntity(
                    id = user.id,
                    name = user.name,
                    url = user.avatar,
                    login = user.login
                )
                appRepository.addFavorite(userFavorite)
                favoriteMessage.postValue(SingleLiveEvent(false))
                isFavorite.postValue(true)
            }
        }
    }
}