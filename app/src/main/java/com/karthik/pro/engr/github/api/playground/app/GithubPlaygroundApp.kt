package com.karthik.pro.engr.github.api.playground.app

import android.app.Application
import android.util.Log
import com.google.firebase.FirebaseApp
import com.karthik.pro.engr.github.api.playground.BuildConfig
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class GithubPlaygroundApp : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.ENABLE_APP_DISTRIBUTION) {
            setupBetaFeatures()
        }

    }

    private fun setupBetaFeatures() {
        val app = FirebaseApp.initializeApp(this)
        Log.d("BetaApplication", "FirebaseApp.initializeApp returned: ${app?.name ?: "null"}")
        // Also list installed Firebase apps for debugging
        val apps = FirebaseApp.getApps(this)
        Log.d("BetaApplication", "FirebaseApp.getApps(): ${apps.map { it.name }}")
     /*   FeedbackBinder.provideAppFeedbackController = { activity: ComponentActivity ->
            val firebaseFeedbackSender = FirebaseFeedbackSender()
            val feedbackViewModelFactory = FeedbackViewModelFactory(firebaseFeedbackSender)
            val vm =
                ViewModelProvider(
                    activity,
                    feedbackViewModelFactory
                ).get(FeedbackViewModel::class.java)
            BetaAppFeedbackController(vm, BetaAppFeedbackActions(vm))
        }*/
    }
}