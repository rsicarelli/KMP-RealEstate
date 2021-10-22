package com.rsicarelli.homehunt.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rsicarelli.homehunt_kmm.core.model.UiEvent
import com.rsicarelli.homehunt.domain.usecase.IsLoggedInUseCase
import com.rsicarelli.homehunt.domain.usecase.IsLoggedInUseCase.Outcome.LoggedIn
import com.rsicarelli.homehunt.domain.usecase.IsLoggedInUseCase.Outcome.LoggedOut
import com.rsicarelli.homehunt.ui.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val isLoggedIn: IsLoggedInUseCase
) : ViewModel() {

    private val _state: MutableStateFlow<SplashState> = MutableStateFlow(SplashState())
    val state: StateFlow<SplashState> = _state

    fun onAnimationEnded() {
        viewModelScope.launch {
            isLoggedIn(Unit).collect { outcome ->
                when (outcome) {
                    LoggedIn -> navigateToHome()
                    LoggedOut -> navigateToLogin()
                }
            }
        }
    }

    private fun navigateToHome() {
        _state.value = state.value.copy(uiEvent = UiEvent.Navigate(Screen.Home.route))
    }

    private fun navigateToLogin() {
        _state.value = state.value.copy(uiEvent = UiEvent.Navigate(Screen.Login.route))
    }
}