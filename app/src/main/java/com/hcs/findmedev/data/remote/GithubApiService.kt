package com.hcs.findmedev.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface GithubApiService {
    @GET("search/users")
    suspend fun searchUsers(
        @Query("q") query: String
    ): SearchUserResponse
}