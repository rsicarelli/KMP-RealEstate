package com.rsicarelli.homehunt.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rsicarelli.homehunt.R
import com.rsicarelli.homehunt_kmm.core.model.ProgressBarState
import com.rsicarelli.homehunt_kmm.core.model.UiEvent.MessageToUser
import com.rsicarelli.homehunt_kmm.core.model.UiEvent.Navigate
import com.rsicarelli.homehunt_kmm.domain.usecase.SignInUseCase
import com.rsicarelli.homehunt.ui.navigation.Screen
import com.rsicarelli.homehunt_kmm.domain.usecase.SignUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import com.rsicarelli.homehunt_kmm.domain.usecase.SignInUseCase.Request as SignInRequest

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val signIn: SignInUseCase,
    private val signUp: SignUpUseCase
) : ViewModel() {

    private val _state: MutableStateFlow<LoginState> = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state

    fun onDoLogin() {
        withValidCredentials {
            viewModelScope.launch {
                signIn(SignInRequest(state.value.userName, state.value.password))
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
    }

    fun onSignUp() {
        withValidCredentials {
            viewModelScope.launch {
                signUp(SignUpUseCase.Request(state.value.userName, state.value.password))
                    .onStart { toggleLoading(ProgressBarState.Loading) }
                    .onCompletion { toggleLoading(ProgressBarState.Idle) }
                    .catch { onError(it) }
                    .collectLatest { outcome ->
                        when (outcome) {
                            SignUpUseCase.Outcome.Error -> onError()
                            SignUpUseCase.Outcome.Success -> navigate(Navigate(Screen.Home.route))
                        }
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

    fun onPasswordChanged(password: String) {
        _state.value = state.value.copy(password = password, invalidPassword = false)
    }

    fun onUserNameChanged(userName: String) {
        _state.value = state.value.copy(userName = userName, invalidUserName = false)
    }

    fun onError(exception: Throwable? = null) {
        Timber.e(exception)
        _state.value = state.value.copy(uiEvent = MessageToUser(R.string.error_unknown))
    }

    private inline fun withValidCredentials(action: () -> Unit) {
        val validUserName = state.value.userName.isNotEmpty() || state.value.userName.isNotBlank()
        val validPassword = state.value.password.isNotEmpty() || state.value.password.isNotBlank()

        if (validUserName && validPassword) {
            action()
        } else {
            _state.value =
                state.value.copy(invalidPassword = !validPassword, invalidUserName = !validUserName)
        }

    }
}
