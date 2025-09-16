package com.hcs.findmedev.data.repository

import com.hcs.findmedev.data.mapper.UserMapper
import com.hcs.findmedev.data.remote.GithubApiService
import com.hcs.findmedev.domain.model.GithubUser
import com.hcs.findmedev.domain.repository.GithubRepository
import javax.inject.Inject

class GithubRepositoryImpl @Inject constructor(
    private val api: GithubApiService
) : GithubRepository {
    override suspend fun searchUsers(query: String): List<GithubUser> {
        val response = api.searchUsers(query)

        return response.items.map { UserMapper().run { it.toDomain() } }
    }
}