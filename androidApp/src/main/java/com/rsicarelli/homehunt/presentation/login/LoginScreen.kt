package com.rsicarelli.homehunt.presentation.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.rsicarelli.homehunt.core.model.HomeHuntState
import com.rsicarelli.homehunt.core.model.UiEvent
import com.rsicarelli.homehunt.core.model.isLoading
import com.rsicarelli.homehunt.presentation.components.CircularIndeterminateProgressBar
import com.rsicarelli.homehunt.presentation.login.components.GoogleSignInOption
import com.rsicarelli.homehunt.presentation.login.components.Welcome
import com.rsicarelli.homehunt.ui.theme.HomeHuntTheme
import com.rsicarelli.homehunt.ui.theme.Size_2X_Large

@Composable
fun LoginScreen(
    homeHuntState: HomeHuntState
) {
    val viewModel: LoginViewModel = hiltViewModel()

    val state by viewModel.state.collectAsState(LoginState())

    val loginActions = LoginActions(
        onDoLogin = viewModel::onDoLogin,
        onError = viewModel::onError,
        onShowMessageToUser = homeHuntState::showMessageToUser,
        onNavigateSingleTop = homeHuntState::navigateSingleTop

    )
    LoginContent(
        state = state,
        actions = loginActions
    )
}

@Composable
private fun LoginContent(
    state: LoginState,
    actions: LoginActions,
) {
    when (state.uiEvent) {
        is UiEvent.MessageToUser -> actions.onShowMessageToUser(state.uiEvent.uiText)
        is UiEvent.Navigate -> actions.onNavigateSingleTop(state.uiEvent.route)
    }

    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Welcome()

        Spacer(modifier = Modifier.height(Size_2X_Large))

        if (!state.progressBarState.isLoading()) {
            GoogleSignInOption(
                onDoLogin = actions.onDoLogin,
                onError = actions.onError
            )
        } else {
            CircularIndeterminateProgressBar(state.progressBarState)
        }
    }
}

@Composable
@Preview
private fun LoginScreenPreview() {
    HomeHuntTheme(isPreview = true) {
        LoginContent(
            state = LoginState(),
            actions = LoginActions(
                onDoLogin = {},
                onError = {},
                onShowMessageToUser = {},
                onNavigateSingleTop = {}
            )
        )
    }
}
