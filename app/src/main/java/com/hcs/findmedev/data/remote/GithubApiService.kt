package com.hcs.findmedev.data.remote

import com.hcs.findmedev.domain.model.GithubUserDetail
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApiService {
    @GET("search/users")
    suspend fun searchUsers(
        @Query("q") query: String
    ): SearchUserResponse

    @GET("users/{username}")
    suspend fun getUserDetail(@Path("username") username: String): UserDetailResponse
}