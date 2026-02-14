package com.karthik.pro.engr.github.api.playground.di

import com.karthik.pro.engr.github.api.core.di.IsBeta
import com.karthik.pro.engr.github.api.core.di.IsDebug
import com.karthik.pro.engr.github.api.playground.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppConfigModule {

    @Provides
    @Singleton
    @IsDebug
    fun provideIsDebug(): Boolean = BuildConfig.DEBUG

    @Provides
    @Singleton
    @IsBeta
    fun provideIsBeta(): Boolean = BuildConfig.BUILD_TYPE == "beta"
}