package com.hcs.core.local.responses

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class SearchUserResponse(
    @Json(name = "items")
    val userItems: List<UserSearchResponseItem>?,

    @Json(name = "total_count")
    val totalCount: Int?
)