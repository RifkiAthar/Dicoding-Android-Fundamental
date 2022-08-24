package com.rifkiathar.githubuser.di

import com.rifkiathar.githubuser.di.common.CommonModule
import com.rifkiathar.githubuser.di.data.DataModule
import com.rifkiathar.githubuser.di.network.NetworkModule
import com.rifkiathar.githubuser.di.room.RoomModule
import com.rifkiathar.githubuser.di.sharedPreferences.SharedPreferencesModule
import com.rifkiathar.githubuser.di.viewModel.ViewModelModule
import org.koin.core.module.Module

object CoreModule {
    val modules: List<Module>
        get() {
            return ArrayList<Module>().apply {
                addAll(NetworkModule.modules)
                addAll(SharedPreferencesModule.modules)
                addAll(DataModule.modules)
                addAll(ViewModelModule.modules)
                addAll(RoomModule.modules)
                addAll(CommonModule.modules)
            }
        }
}