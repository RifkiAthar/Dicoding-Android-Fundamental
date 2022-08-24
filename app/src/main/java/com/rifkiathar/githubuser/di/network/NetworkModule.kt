package com.rifkiathar.githubuser.di.network

import android.content.Context
import com.rifkiathar.githubuser.R
import com.rifkiathar.githubuser.api.NetworkServices
import com.rifkiathar.githubuser.di.BaseModule
import com.rifkiathar.githubuser.di.sharedPreferences.utils.SharedPreferenceManager
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object NetworkModule : BaseModule {
    override val modules: List<Module>
        get() = listOf(retrofitModule, webServicesModule)

    private val retrofitModule = module{
        single { providesHeaderInterceptor() }
        single { provideOkHttpClient(get()) }
        single(named("SERVER")) {
            provideRetrofit(
                androidContext(),
                get()
            )
        }
    }

    private val webServicesModule = module {
        single { get<Retrofit>(named("SERVER")).create(NetworkServices::class.java) }
    }

    private fun providesHeaderInterceptor(): HeaderInterceptor =
        HeaderInterceptor()


    private fun provideOkHttpClient(headerInterceptor: HeaderInterceptor): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder()
            .addInterceptor(headerInterceptor)
            .addInterceptor(logging)
            .connectTimeout(120L, TimeUnit.SECONDS)
            .writeTimeout(120L, TimeUnit.SECONDS)
            .readTimeout(120L, TimeUnit.SECONDS)
            .build()
    }

    private fun provideRetrofit(
        context: Context,
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(context.resources.getString(R.string.URL))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}