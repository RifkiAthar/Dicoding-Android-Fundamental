package com.rifkiathar.githubuser.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.rifkiathar.githubuser.base.BaseViewModel
import com.rifkiathar.githubuser.base.ResourceState
import com.rifkiathar.githubuser.data.AppRepository
import com.rifkiathar.githubuser.di.dataStore.SettingPreferences
import com.rifkiathar.githubuser.model.entity.UserEntity
import kotlinx.coroutines.launch

class FavoriteViewModel(private val appRepository: AppRepository) : BaseViewModel() {

    private val favoriteResponse = MutableLiveData<List<UserEntity>>()

    fun observeUserFavorite() : LiveData<List<UserEntity>> = favoriteResponse

    fun getFavoriteData() {
        viewModelScope.launch {
            when(val result = appRepository.getAllUser()) {
                is ResourceState.Success -> {
                    result.result.data.let {
                        favoriteResponse.postValue(it)
                    }
                }
                is ResourceState.Error -> {
                    errorResponse.postValue(result.error.errorData)
                }
                else -> {}
            }
        }
    }
}