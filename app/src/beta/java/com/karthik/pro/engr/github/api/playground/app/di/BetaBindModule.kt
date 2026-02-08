package com.karthik.pro.engr.github.api.playground.app.di

import com.karthik.pro.engr.github.api.playground.app.feedback.AppFeedbackController
import com.karthik.pro.engr.github.api.playground.app.feedback.BetaAppFeedbackActions
import com.karthik.pro.engr.github.api.playground.app.feedback.BetaAppFeedbackController
import com.karthik.pro.engr.github.api.playground.app.feedback.BetaFeedback
import com.karthik.pro.engr.github.api.playground.app.feedback.FeedbackActions
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
abstract class BetaBindModule {
    @Binds
    @ActivityScoped
    abstract fun bindBetaFeedbackActions(impl: BetaAppFeedbackActions): FeedbackActions

    @Binds
    @ActivityScoped
    abstract fun bindBetaFeedbackController(impl: BetaAppFeedbackController): AppFeedbackController
}