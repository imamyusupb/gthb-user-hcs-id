package com.hcs.findmedev.data.di

import com.hcs.findmedev.data.repository.GithubRepositoryImpl
import com.hcs.findmedev.domain.repository.GithubRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindGithubRepository(
        impl: GithubRepositoryImpl
    ): GithubRepository
}
