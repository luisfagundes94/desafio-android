package com.luisfagundes.data.di

import com.luisfagundes.data.repository.UserRepositoryImpl
import com.luisfagundes.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    internal abstract fun bindUserRepository(repository: UserRepositoryImpl): UserRepository
}
