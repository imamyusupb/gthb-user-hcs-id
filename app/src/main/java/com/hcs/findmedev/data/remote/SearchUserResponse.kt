package com.hcs.findmedev.data.remote

import com.squareup.moshi.Json

data class SearchUserResponse(
    @Json(name = "total_count") val totalCount: Int,
    @Json(name = "items") val items: List<GithubUserDto>
)

data class GithubUserDto(
    @Json(name = "login") val login: String,
    @Json(name = "avatar_url") val avatarUrl: String
)