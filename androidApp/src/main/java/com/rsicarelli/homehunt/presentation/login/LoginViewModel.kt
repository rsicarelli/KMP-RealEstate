package com.rsicarelli.homehunt.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.AuthCredential
import com.rsicarelli.homehunt.core.model.ProgressBarState
import com.rsicarelli.homehunt.core.model.UiEvent.MessageToUser
import com.rsicarelli.homehunt.core.model.UiEvent.Navigate
import com.rsicarelli.homehunt.core.model.UiText
import com.rsicarelli.homehunt.domain.usecase.SignInUseCase
import com.rsicarelli.homehunt.ui.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import com.rsicarelli.homehunt.domain.usecase.SignInUseCase.Request as SignInRequest

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val signIn: SignInUseCase
) : ViewModel() {

    private val _state: MutableStateFlow<LoginState> = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state

    fun onDoLogin(authCredential: AuthCredential) {
        viewModelScope.launch {
            signIn(SignInRequest(authCredential))
                .onStart { toggleLoading(ProgressBarState.Loading) }
                .onCompletion { toggleLoading(ProgressBarState.Idle) }
                .catch { onError(it) }
                .collectLatest { outcome ->
                    when (outcome) {
                        SignInUseCase.Outcome.Error -> onError()
                        SignInUseCase.Outcome.Success -> navigate(Navigate(Screen.Home.route))
                    }
                }
        }
    }

    private fun toggleLoading(progressBarState: ProgressBarState) {
        _state.value = state.value.copy(progressBarState = progressBarState)
    }

    private fun navigate(navigate: Navigate) {
        _state.value = state.value.copy(uiEvent = Navigate(navigate.route))
    }

    fun onError(exception: Throwable? = null) {
        Timber.e(exception)
        _state.value = state.value.copy(uiEvent = MessageToUser(UiText.unknownError()))
    }
}
