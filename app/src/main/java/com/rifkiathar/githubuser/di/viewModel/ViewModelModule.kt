package com.rifkiathar.githubuser.di.viewModel

import com.rifkiathar.githubuser.di.BaseModule
import com.rifkiathar.githubuser.ui.detail.DetailViewModel
import com.rifkiathar.githubuser.ui.favorite.FavoriteViewModel
import com.rifkiathar.githubuser.ui.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

object ViewModelModule : BaseModule {
    override val modules: List<Module>
        get() = listOf(viewModels)

    private val viewModels = module {
        viewModel { HomeViewModel(get()) }
        viewModel { DetailViewModel(get()) }
        viewModel { FavoriteViewModel(get())}
    }
}