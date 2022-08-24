package com.rifkiathar.githubuser.di.room

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

object RoomMigration {
    val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL(
                "CREATE TABLE IF NOT EXISTS userFavorite (id INTEGER NOT NULL PRIMARY KEY, name TEXT, url TEXT, login TEXT)"
            )
        }
    }
}