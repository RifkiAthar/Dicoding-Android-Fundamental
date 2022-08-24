package com.rifkiathar.githubuser.model.response

import com.google.gson.annotations.SerializedName

data class Meta(
    @SerializedName("code")
    val code: Int? = 0,
    @SerializedName("message")
    val message: String? = "",
    @SerializedName("page")
    val page: Int? = 0,
    @SerializedName("per_page")
    val perPage: Int? = 0,
    @SerializedName("data_count")
    val dataCount: Int? = 0
)