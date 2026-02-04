package com.karthik.pro.engr.github.api.playground.presentation.repos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.karthik.pro.engr.github.api.domain.error.DomainError
import com.karthik.pro.engr.github.api.domain.model.Repo
import com.karthik.pro.engr.github.api.domain.result.Result
import com.karthik.pro.engr.github.api.domain.usecase.GetUserReposUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GithubReposListViewModel @Inject constructor(private val useCase: GetUserReposUseCase) :
    ViewModel() {

    private var _uiState = MutableStateFlow<UiState<List<Repo>>>(UiState.Idle)
    val uiState: StateFlow<UiState<List<Repo>>> = _uiState

    fun loadRepos(username: String) {
        viewModelScope.launch {
            useCase(username).collect { result ->
                _uiState.value = when (result) {
                    is Result.Failure -> UiState.Error(mapError(result.error))
                    is Result.Success -> UiState.Success(result.data)
                }
            }
        }

    }

    private fun mapError(error: DomainError): String {
        return when (error) {
            DomainError.Network -> "No internet connection"
            DomainError.Unauthorized -> "Unauthorized access"
            DomainError.NotFound -> "User not found"
            DomainError.RateLimited -> "Rate limit exceeded"
            DomainError.Unknown -> "Something went wrong"
        }
    }

}