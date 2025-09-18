package com.hcs.findmedev.domain.model

data class GithubUserDetail(
    val username: String,
    val name: String?,
    val avatarUrl: String?,
    val bio: String?,
    val company: String?,
    val location: String?,
    val blog: String?,
    val repositoryCount: Int,
    val gistCount: Int,
    val followingCount: Int,
    val followerCount: Int
)