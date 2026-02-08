package com.karthik.pro.engr.github.api.playground.app.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DefaultFeedback

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class BetaFeedback
