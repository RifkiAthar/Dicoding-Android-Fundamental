package com.rifkiathar.githubuser.di.data

import com.rifkiathar.githubuser.api.NetworkServices
import com.rifkiathar.githubuser.data.AppDatabase
import com.rifkiathar.githubuser.data.AppRepository
import com.rifkiathar.githubuser.data.LocalDataSource
import com.rifkiathar.githubuser.data.RemoteDataSource
import com.rifkiathar.githubuser.di.BaseModule
import org.koin.core.module.Module
import org.koin.dsl.module

object DataModule : BaseModule {
    override val modules: List<Module>
        get() = listOf(dataModule)

    private val dataModule = module {
        single { provideAppRepository(get(), get()) }
        single { provideLocalDataSource(get()) }
        single { provideRemoteDataSource(get()) }
    }

    private fun provideAppRepository(
        localDataSource: LocalDataSource,
        remoteDataSource: RemoteDataSource
    ): AppRepository = AppRepository(localDataSource, remoteDataSource)

    private fun provideLocalDataSource(appDatabase: AppDatabase) = LocalDataSource(appDatabase)

    private fun provideRemoteDataSource(networkServices: NetworkServices) =
        RemoteDataSource(networkServices)
}