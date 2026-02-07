package com.karthik.pro.engr.github.api.playground.app.feedback

import android.content.Context
import com.karthik.pro.engr.feedback.api.ui.viewmodel.FeedbackEvent
import com.karthik.pro.engr.feedback.api.ui.viewmodel.FeedbackUiEffect
import com.karthik.pro.engr.feedback.api.ui.viewmodel.FeedbackUiState
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow


interface FeedbackActions {
    fun launchFeedbackEmail(context: Context, email: String)
}

interface AppFeedbackController {
    val uiState: StateFlow<FeedbackUiState>
    val uiEffect: SharedFlow<FeedbackUiEffect>

    val feedbackActions: FeedbackActions

    fun onEvent(event: FeedbackEvent)


}

class NoOpAppFeedbackActions : FeedbackActions {
    override fun launchFeedbackEmail(context: Context, email: String) {
        // no-op for non-beta builds (or show a debug toast if you want)
    }

}


class NoOpAppFeedbackController : AppFeedbackController {
    private val _uiState = MutableStateFlow(FeedbackUiState(""))
    override val uiState: MutableStateFlow<FeedbackUiState>
        get() = _uiState

    override val uiEffect: MutableSharedFlow<FeedbackUiEffect>
        get() = MutableSharedFlow(
            0,
            extraBufferCapacity = 1,
            onBufferOverflow = BufferOverflow.DROP_OLDEST
        )
    override val feedbackActions: FeedbackActions
        get() = NoOpAppFeedbackActions()


    override fun onEvent(event: FeedbackEvent) {
        /*no-op*/
    }
}