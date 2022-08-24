package com.rifkiathar.githubuser.model.response

import com.google.gson.annotations.SerializedName

data class DetailResponse(
    @SerializedName("login")
    val login: String? = "",
    @SerializedName("id")
    val id: Int? = 0,
    @SerializedName("avatar_url")
    val avatar: String? = "",
    @SerializedName("name")
    val name: String? = "",
    @SerializedName("company")
    val company: String? = "",
    @SerializedName("followers")
    val followers: Int? = 0,
    @SerializedName("following")
    val following: Int? = 0
)