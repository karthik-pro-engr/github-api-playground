package com.karthik.pro.engr.github.api.playground.di

import com.karthik.pro.engr.github.api.playground.BuildConfig
import com.karthik.pro.engr.github.api.playground.app.feedback.AppFeedbackController
import com.karthik.pro.engr.github.api.playground.app.feedback.BetaFeedback
import com.karthik.pro.engr.github.api.playground.app.feedback.DefaultFeedback
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
object AppFeedbackProviderModule {

    @Provides
    @ActivityScoped
    fun provideAppFeedbackController(
        @DefaultFeedback defaultController: AppFeedbackController,
        @BetaFeedback betaController: AppFeedbackController?
    ): AppFeedbackController {
        // If beta controller is available and the build flag is true, return it.
        // Note: betaController will be null if not bound; make sure BetaBindModule is only installed when appropriate.
        return if (BuildConfig.ENABLE_APP_DISTRIBUTION && betaController != null) {
            betaController
        } else {
            defaultController
        }
    }
}
