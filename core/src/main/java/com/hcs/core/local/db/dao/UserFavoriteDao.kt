package com.hcs.core.local.db.dao

import android.database.Cursor
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hcs.core.local.db.entity.UserFavoriteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserFavoriteDao {

    @Query("SELECT * FROM user_favorite_table")
    fun fetchAllUsers(): Flow<List<UserFavoriteEntity>>

    @Query("SELECT * FROM user_favorite_table WHERE username = :userName")
    fun getFavByUsername(userName: String): Flow<List<UserFavoriteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUserToFavoriteDB(userEntity: UserFavoriteEntity)

    @Delete
    suspend fun deleteUserFromFavoriteDB(userEntity: UserFavoriteEntity)

    @Query("SELECT * FROM user_favorite_table")
    fun cursorGetAllUserFavorite(): Cursor

}