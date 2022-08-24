package com.rifkiathar.githubuser.di.room

import androidx.room.Room
import com.rifkiathar.githubuser.data.AppDatabase
import com.rifkiathar.githubuser.di.BaseModule
import com.rifkiathar.githubuser.di.room.RoomMigration.MIGRATION_1_2
import com.rifkiathar.githubuser.utils.AppConstant.DB_NAME
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

object RoomModule : BaseModule {
    override val modules: List<Module>
        get() = listOf(roomModule)

    private val roomModule = module {
        single {
            Room.databaseBuilder(androidContext(), AppDatabase::class.java, DB_NAME)
                .addMigrations(
                    MIGRATION_1_2
                )
                .fallbackToDestructiveMigration()
                .build()
        }
    }

}