package com.karthik.pro.engr.github.api.playground.app.feedback

import android.content.Context
import android.util.Log
import com.karthik.pro.engr.feedback.api.ui.viewmodel.FeedbackEvent
import com.karthik.pro.engr.feedback.api.ui.viewmodel.FeedbackUiEffect
import com.karthik.pro.engr.feedback.api.ui.viewmodel.FeedbackUiState
import com.karthik.pro.engr.github.api.playground.app.di.DefaultFeedback
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton


interface FeedbackActions {
    fun launchFeedbackEmail(context: Context, email: String)
}

interface AppFeedbackController {
    val uiState: StateFlow<FeedbackUiState>
    val uiEffect: SharedFlow<FeedbackUiEffect>

    val feedbackActions: FeedbackActions

    fun onEvent(event: FeedbackEvent)


}

@Singleton
class NoOpAppFeedbackActions @Inject constructor() : FeedbackActions {
    override fun launchFeedbackEmail(context: Context, email: String) {
        // no-op for non-beta builds (or show a debug toast if you want)
    }

}


@Singleton
class NoOpAppFeedbackController @Inject constructor(@DefaultFeedback actions: FeedbackActions) :
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