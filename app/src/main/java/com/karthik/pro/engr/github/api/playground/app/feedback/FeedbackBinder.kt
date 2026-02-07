package com.karthik.pro.engr.github.api.playground.app.feedback

import androidx.activity.ComponentActivity


object FeedbackBinder {
    var provideAppFeedbackController: (ComponentActivity) -> AppFeedbackController =
        { _ ->
            NoOpAppFeedbackController()
        }
}