package com.hcs.findmedev.data.remote

import com.squareup.moshi.Json

data class UserDetailResponse(
    val login: String,
    val name: String?,
    @Json(name = "avatar_url") val avatarUrl: String?,
    val bio: String?,
    val company: String?,
    val location: String?,
    val blog: String?,
    @Json(name = "public_repos") val publicRepos: Int,
    @Json(name = "public_gists") val publicGists: Int,
    val following: Int,
    val followers: Int
)