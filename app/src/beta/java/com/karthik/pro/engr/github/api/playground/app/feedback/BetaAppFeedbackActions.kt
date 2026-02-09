package com.karthik.pro.engr.github.api.playground.app.feedback

import android.content.Context
import com.karthik.pro.engr.feedback.impl.ui.viewmodel.FeedbackViewModel
import javax.inject.Inject

class BetaAppFeedbackActions @Inject constructor(val vm: FeedbackViewModel) : FeedbackActions {
    override fun launchFeedbackEmail(context: Context, email: String) {
        vm.launchFeedbackEmail(context, email)
    }
}
