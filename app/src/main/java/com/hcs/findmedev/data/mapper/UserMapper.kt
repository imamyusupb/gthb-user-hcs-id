package com.hcs.findmedev.data.mapper

import com.hcs.findmedev.data.remote.GithubUserDto
import com.hcs.findmedev.data.remote.UserDetailResponse
import com.hcs.findmedev.domain.model.GithubUser
import com.hcs.findmedev.domain.model.GithubUserDetail

fun GithubUserDto.toDomain(): GithubUser {
    return GithubUser(
        username = login, avatarUrl = avatarUrl, url = url
    )
}

fun UserDetailResponse.toDomain(): GithubUserDetail {
    return GithubUserDetail(
        username = login,
        name = name,
        avatarUrl = avatarUrl,
        bio = bio,
        company = company,
        location = location,
        blog = blog,
        repositoryCount = publicRepos,
        gistCount = publicGists,
        followingCount = following,
        followerCount = followers
    )
}

