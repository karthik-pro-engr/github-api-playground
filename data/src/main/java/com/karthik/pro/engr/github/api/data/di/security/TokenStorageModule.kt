package com.karthik.pro.engr.github.api.data.di.security

import com.karthik.pro.engr.github.api.data.security.SecureTokenStorage
import com.karthik.pro.engr.github.api.domain.security.TokenStorage
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class TokenStorageModule {

    @Binds
    @Singleton
    abstract fun bindSecureTokenStorage(impl: SecureTokenStorage): TokenStorage

}