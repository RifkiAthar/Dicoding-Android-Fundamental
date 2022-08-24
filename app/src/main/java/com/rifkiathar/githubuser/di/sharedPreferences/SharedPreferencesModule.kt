package com.rifkiathar.githubuser.di.sharedPreferences

import android.content.Context
import com.rifkiathar.githubuser.R
import com.rifkiathar.githubuser.di.BaseModule
import com.rifkiathar.githubuser.di.sharedPreferences.utils.SharedPreferenceManager
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

object SharedPreferencesModule : BaseModule {
    override val modules: List<Module>
        get() = listOf(sharedPreferenceModule)

    private val sharedPreferenceModule = module {
        single { providesSharedPreferencesManager(androidContext()) }
    }

    private fun providesSharedPreferencesManager(context: Context): SharedPreferenceManager {
        val sharedPreferences = context.getSharedPreferences(
            context.resources.getString(R.string.SHARED_PREFERENCES_NAME),
            Context.MODE_PRIVATE
        )

        return SharedPreferenceManager(sharedPreferences)
    }
}