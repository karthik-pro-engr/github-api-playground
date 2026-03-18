package com.karthik.pro.engr.github.api.playground.app.di

import android.content.Context
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModelProvider
import com.karthik.pro.engr.feedback.impl.FeedbackViewModelFactory
import com.karthik.pro.engr.feedback.impl.FirebaseFeedbackSender
import com.karthik.pro.engr.feedback.impl.ui.viewmodel.FeedbackViewModel
import com.karthik.pro.engr.github.api.playground.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
object BetaFeedbackModule {
    @Provides
    @ActivityScoped
    fun provideFeedbackViewModel(@ActivityContext context: Context): FeedbackViewModel {
        // Only provide Beta controller at activity scope when beta is enabled
        if (!BuildConfig.ENABLE_APP_DISTRIBUTION) {
            throw IllegalStateException(
                "BetaFeedbackModule.provideBetaAppFeedbackController called while ENABLE_APP_DISTRIBUTION=false"
            )
        }

        // cast the activity context to ComponentActivity
        val activity = context as ComponentActivity

        val firebaseFeedbackSender = FirebaseFeedbackSender()
        val feedbackViewModelFactory = FeedbackViewModelFactory(firebaseFeedbackSender)
        val vm = ViewModelProvider(activity, feedbackViewModelFactory)[FeedbackViewModel::class.java]
        return vm
    }
}
