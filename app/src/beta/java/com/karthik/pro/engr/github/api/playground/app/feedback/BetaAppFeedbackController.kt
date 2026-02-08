package com.karthik.pro.engr.github.api.playground.app.feedback

import com.karthik.pro.engr.feedback.api.ui.viewmodel.FeedbackEvent
import com.karthik.pro.engr.feedback.api.ui.viewmodel.FeedbackUiEffect
import com.karthik.pro.engr.feedback.api.ui.viewmodel.FeedbackUiState
import com.karthik.pro.engr.feedback.impl.ui.viewmodel.FeedbackViewModel
import com.karthik.pro.engr.github.api.playground.app.di.BetaFeedback
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class BetaAppFeedbackController @Inject constructor(
    @param:BetaFeedback val vm: FeedbackViewModel,
    @param:BetaFeedback val actions: FeedbackActions
) : AppFeedbackController {

    override val uiState: StateFlow<FeedbackUiState> = vm.uiState
    override val uiEffect: SharedFlow<FeedbackUiEffect>
        get() = vm.uiEffect
    override val feedbackActions: FeedbackActions
        get() = actions

    override fun onEvent(event: FeedbackEvent) {
        vm.onEvent(event)
    }

}