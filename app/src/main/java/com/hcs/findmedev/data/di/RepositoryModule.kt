package com.hcs.findmedev.data.di

import android.content.Context
import com.hcs.findmedev.data.remote.GithubApiService
import com.hcs.findmedev.data.repository.GithubRepositoryImpl
import com.hcs.findmedev.domain.repository.GithubRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindGithubRepository(
        impl: GithubRepositoryImpl
    ): GithubRepository
}
