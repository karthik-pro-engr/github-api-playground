package com.karthik.pro.engr.github.api.playground.app.feedback

import android.content.Context

interface FeedbackActions {
    fun launchFeedbackEmail(context: Context, email: String)
}