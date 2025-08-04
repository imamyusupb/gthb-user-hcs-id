package com.hcs.gitmeshow.di

import com.hcs.core.usecase.UserUseCase
import com.hcs.core.usecase.UserUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    abstract fun provideUserUseCase(userUseCaseImpl: UserUseCaseImpl): UserUseCase

}