package com.rifkiathar.githubuser.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "followers")
data class FollowersEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    var id: Int?,
    @ColumnInfo(name = "login")
    var login: String?,
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = " avatar_url")
    var url: String?,
    @ColumnInfo(name = "score")
    var score: String?
)