package com.hcs.core.di

import com.hcs.core.UserRepositoryImpl
import com.hcs.core.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun provideUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository

}