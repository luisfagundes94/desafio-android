package com.picpay.desafio.android.core.data.di

import com.picpay.desafio.android.core.data.repository.UserRepositoryImpl
import com.picpay.desafio.android.core.domain.repository.UserRepository
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
