package com.karthik.pro.engr.github.api.playground.app.feedback

import android.content.Context
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NoOpAppFeedbackActions @Inject constructor() : FeedbackActions {
    override fun launchFeedbackEmail(context: Context, email: String) {
        // no-op for non-beta builds (or show a debug toast if you want)
    }

}
