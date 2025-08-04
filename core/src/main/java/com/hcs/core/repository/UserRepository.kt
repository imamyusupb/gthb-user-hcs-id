package com.hcs.core.repository

import com.hcs.core.model.UserDetail
import com.hcs.core.model.UserFavorite
import com.hcs.core.model.UserFollower
import com.hcs.core.model.UserFollowing
import com.hcs.core.model.UserSearchItem
import com.hcs.core.utils.state.ResultState
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    /**
     * Remote
     */
    suspend fun getUserFromApi(username: String): Flow<ResultState<List<UserSearchItem>>>

    suspend fun getDetailUserFromApi(username: String): Flow<ResultState<UserDetail>>

    suspend fun getUserFollowers(username: String): Flow<ResultState<List<UserFollower>>>

    suspend fun getUserFollowing(username: String): Flow<ResultState<List<UserFollowing>>>

    /**
     * Local
     */
    fun fetchAllUserFavorite(): Flow<List<UserFavorite>>

    fun getFavoriteUserByUsername(username: String): Flow<List<UserFavorite>>

    suspend fun addUserToFavDB(userFavoriteEntity: UserFavorite)

    suspend fun deleteUserFromFavDB(userFavoriteEntity: UserFavorite)


}