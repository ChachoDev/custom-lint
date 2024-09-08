package com.example.customlintexample.data.di

import com.example.customlintexample.data.repositories.impl.UseRepositoryImpl
import com.example.customlintexample.domain.repositories.UserRepository
import com.example.customlintexample.domain.usecases.GetUserUseCase
import com.example.customlintexample.domain.usecases.impl.GetUserUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MainModule {
    @Provides
    @Singleton
    fun provideUseRepository(): UserRepository =
        UseRepositoryImpl()

    @Provides
    @Singleton
    fun provideUseUseCase(userRepositoy: UserRepository): GetUserUseCase =
        GetUserUseCaseImpl(userRepositoy)
}