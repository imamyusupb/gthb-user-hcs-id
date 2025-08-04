package com.hcs.core.di

import android.app.Application
import androidx.room.Room
import com.hcs.core.local.db.AppDatabase
import com.hcs.core.local.db.dao.UserFavoriteDao
import com.hcs.core.remote.Network
import com.hcs.core.remote.NetworkService
import com.hcs.core.utils.DataConfig
import com.hcs.core.utils.const.databaseName
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoreModule {

    @Provides
    @Singleton
    fun provideNetworkService(): NetworkService {
        return Network.retrofitClient().create(NetworkService::class.java)
    }

    @Singleton
    @Provides
    fun provideAppDatabase(app: Application): AppDatabase {
        val passPhare: ByteArray = SQLiteDatabase.getBytes(DataConfig.PASSPHRASE.toCharArray())
        val factory = SupportFactory(passPhare)

        return Room.databaseBuilder(
            app,
            AppDatabase::class.java,
            databaseName
        ).fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .build()
    }

    @Singleton
    @Provides
    fun provideUserFavoriteDao(appDatabase: AppDatabase): UserFavoriteDao {
        return appDatabase.userFavDao()
    }

}