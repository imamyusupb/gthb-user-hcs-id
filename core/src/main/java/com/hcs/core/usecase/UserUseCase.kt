package com.hcs.core.usecase

import com.hcs.core.model.UserDetail
import com.hcs.core.model.UserFavorite
import com.hcs.core.model.UserFollower
import com.hcs.core.model.UserFollowing
import com.hcs.core.model.UserSearchItem
import com.hcs.core.utils.state.ResultState
import kotlinx.coroutines.flow.Flow


interface UserUseCase {
    suspend fun getUserFromApi(username : String) : Flow<ResultState<List<UserSearchItem>>>
    suspend fun getUserDetailFromApi(username : String) : Flow<ResultState<UserDetail>>
    suspend fun getUserFollowers(username : String) : Flow<ResultState<List<UserFollower>>>
    suspend fun getUserFollowing(username : String) :  Flow<ResultState<List<UserFollowing>>>

    fun fetchAllUserFavorite() : Flow<List<UserFavorite>>
    suspend fun deleteUserFromDb(userFavorite: UserFavorite)
    suspend fun addUserToFavDB(userFavorite: UserFavorite)
    fun getFavUserByUsername(username: String) : Flow<List<UserFavorite>>
}