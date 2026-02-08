package com.karthik.pro.engr.github.api.playground.di

import com.karthik.pro.engr.github.api.playground.app.di.DefaultFeedback
import com.karthik.pro.engr.github.api.playground.app.feedback.AppFeedbackController
import com.karthik.pro.engr.github.api.playground.app.feedback.FeedbackActions
import com.karthik.pro.engr.github.api.playground.app.feedback.NoOpAppFeedbackActions
import com.karthik.pro.engr.github.api.playground.app.feedback.NoOpAppFeedbackController
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class FeedbackModule {

    @Binds
    @Singleton
    @DefaultFeedback
    abstract fun bindFeedbackActions(impl: NoOpAppFeedbackActions): FeedbackActions

    @Binds
    @Singleton
    @DefaultFeedback
    abstract fun bindFeedbackController(impl: NoOpAppFeedbackController): AppFeedbackController
}