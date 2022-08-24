package com.rifkiathar.githubuser

import android.app.Application
import com.rifkiathar.githubuser.di.CoreModule
import com.rifkiathar.githubuser.utils.AppConstant
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

open class GithubApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@GithubApp)
            modules(CoreModule.modules)
        }

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
            Timber.tag(AppConstant.TIMBER_TAG)
        }
    }
}