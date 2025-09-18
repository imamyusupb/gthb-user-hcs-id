package com.hcs.findmedev.domain.repository

import com.hcs.findmedev.domain.model.GithubUser
import com.hcs.findmedev.domain.model.GithubUserDetail

interface GithubRepository {
    suspend fun searchUsers(query: String): List<GithubUser>
    suspend fun getUserDetail(username: String): GithubUserDetail
}

