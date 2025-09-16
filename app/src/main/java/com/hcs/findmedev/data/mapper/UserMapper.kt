package com.hcs.findmedev.data.mapper

import com.hcs.findmedev.data.remote.GithubUserDto
import com.hcs.findmedev.domain.model.GithubUser

class UserMapper {
    fun GithubUserDto.toDomain(): GithubUser {
        return GithubUser(
            username = login,
            avatarUrl = avatarUrl
        )
    }
}