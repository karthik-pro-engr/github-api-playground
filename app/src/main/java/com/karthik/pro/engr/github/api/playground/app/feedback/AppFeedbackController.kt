package com.karthik.pro.engr.github.api.playground.app.feedback

import com.karthik.pro.engr.feedback.api.ui.viewmodel.FeedbackEvent
import com.karthik.pro.engr.feedback.api.ui.viewmodel.FeedbackUiEffect
import com.karthik.pro.engr.feedback.api.ui.viewmodel.FeedbackUiState
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface AppFeedbackController {
    val uiState: StateFlow<FeedbackUiState>
    val uiEffect: SharedFlow<FeedbackUiEffect>

    val feedbackActions: FeedbackActions

    fun onEvent(event: FeedbackEvent)


}