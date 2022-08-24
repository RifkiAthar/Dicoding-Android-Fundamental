package com.rifkiathar.githubuser.di.common

import com.rifkiathar.githubuser.di.BaseModule
import com.rifkiathar.githubuser.utils.DiffCallback
import org.koin.core.module.Module
import org.koin.dsl.module

object CommonModule : BaseModule {
    override val modules: List<Module>
        get() = listOf(commonModule)

    private val commonModule = module {
        single { provideDiffCallback() }
    }

    private fun provideDiffCallback() = DiffCallback()
}