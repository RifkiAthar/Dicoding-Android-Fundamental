package com.rifkiathar.githubuser.model.response

import com.google.gson.annotations.SerializedName

data class BaseResponse<T>(
    @SerializedName("meta")
    val meta: Meta,
    @SerializedName("data")
    val `data`: T?,
    @SerializedName("expired_in")
    val expiredIn: Int? = 0,
    @SerializedName("token_type")
    val tokenType: String? = "",
    @SerializedName("access_token")
    val token: String? = ""
)