package com.rifkiathar.githubuser.model.response

import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("total_count")
    val totalCount: Int? = 0,
    @SerializedName("incomplete_results")
    val incompleteResult: Boolean? = false,
    @SerializedName("items")
    val items: List<UserResponse>? = null
)