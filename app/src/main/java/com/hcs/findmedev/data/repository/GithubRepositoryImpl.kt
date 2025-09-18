package com.hcs.findmedev.data.repository

import com.hcs.findmedev.data.mapper.toDomain
import com.hcs.findmedev.data.remote.GithubApiService
import com.hcs.findmedev.domain.model.GithubUser
import com.hcs.findmedev.domain.model.GithubUserDetail
import com.hcs.findmedev.domain.repository.GithubRepository
import javax.inject.Inject

class GithubRepositoryImpl @Inject constructor(
    private val api: GithubApiService
) : GithubRepository {
    override suspend fun searchUsers(query: String): List<GithubUser> {
        val response = api.searchUsers(query)

        return response.items.map { it.toDomain() }
    }

    override suspend fun getUserDetail(username: String): GithubUserDetail {
        return api.getUserDetail(username).toDomain()
    }

    override suspend fun getFollowers(username: String, perPage: Int, page: Int): List<GithubUser> {
        return api.getFollowers(username).map { it.toDomain() }
    }

    override suspend fun getFollowing(username: String, perPage: Int, page: Int): List<GithubUser> {
        return api.getFollowing(username).map { it.toDomain() }
    }
}