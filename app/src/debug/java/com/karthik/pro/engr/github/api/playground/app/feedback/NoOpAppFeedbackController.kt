package com.karthik.pro.engr.github.api.playground.app.feedback

import android.util.Log
import com.karthik.pro.engr.feedback.api.ui.viewmodel.FeedbackEvent
import com.karthik.pro.engr.feedback.api.ui.viewmodel.FeedbackUiEffect
import com.karthik.pro.engr.feedback.api.ui.viewmodel.FeedbackUiState
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import javax.inject.Singleton



@Singleton
class NoOpAppFeedbackController @Inject constructor( actions: FeedbackActions) :
    AppFeedbackController {
    private val _uiState = MutableStateFlow(FeedbackUiState(""))
    override val uiState: MutableStateFlow<FeedbackUiState>
        get() = _uiState

    override val uiEffect: MutableSharedFlow<FeedbackUiEffect>
        get() = MutableSharedFlow(
            0,
            extraBufferCapacity = 1,
            onBufferOverflow = BufferOverflow.DROP_OLDEST
        )
    override val feedbackActions: FeedbackActions = actions


    override fun onEvent(event: FeedbackEvent) {
        Log.e("AppFeedback", "onEvent: called")
        /*no-op*/
    }
}