package com.hcs.findmedev.domain.repository

import com.hcs.findmedev.domain.model.GithubUser

interface GithubRepository {
    suspend fun searchUsers(query: String): List<GithubUser>
}