package com.rifkiathar.githubuser.model.response

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("login")
    val login: String = "",
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("avatar_url")
    val avatarUrl: String = "",
    @SerializedName("html_url")
    val url: String = "",
    @SerializedName("score")
    val score: Double? = 0.0
)