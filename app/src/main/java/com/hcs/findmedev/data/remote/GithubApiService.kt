package com.hcs.findmedev.data.remote

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

    @GET("users/{username}/followers")
    suspend fun getFollowers(
        @Path("username") username: String, @Query("per_page") perPage: Int = 30, @Query("page") page: Int = 1
    ): List<GithubUserDto>

    @GET("users/{username}/following")
    suspend fun getFollowing(
        @Path("username") username: String, @Query("per_page") perPage: Int = 30, @Query("page") page: Int = 1
    ): List<GithubUserDto>
}