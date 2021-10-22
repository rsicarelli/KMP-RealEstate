package com.rsicarelli.homehunt.presentation.login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rsicarelli.homehunt.R
import com.rsicarelli.homehunt.presentation.components.CircularIndeterminateProgressBar
import com.rsicarelli.homehunt.presentation.login.components.Welcome
import com.rsicarelli.homehunt.ui.state.HomeHuntState
import com.rsicarelli.homehunt.ui.theme.*
import com.rsicarelli.homehunt_kmm.core.model.ProgressBarState
import com.rsicarelli.homehunt_kmm.core.model.UiEvent
import com.rsicarelli.homehunt_kmm.core.model.isLoading
import com.rsicarelli.homehunt_kmm.domain.model.User

@Composable
fun LoginScreen(
    homeHuntState: HomeHuntState
) {
    val viewModel: LoginViewModel = hiltViewModel()

    val state by viewModel.state.collectAsState(LoginState())

    val loginActions = LoginActions(
        onDoLogin = viewModel::onDoLogin,
        onSignUp = viewModel::onSignUp,
        onError = viewModel::onError,
        onShowMessageToUser = homeHuntState::showMessageToUser,
        onNavigateSingleTop = homeHuntState::navigateSingleTop,
        onPasswordChanged = viewModel::onPasswordChanged,
        onUserNameChanged = viewModel::onUserNameChanged
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
        is UiEvent.MessageToUser -> actions.onShowMessageToUser(stringResource(id = state.uiEvent.textId))
        is UiEvent.Navigate -> actions.onNavigateSingleTop(state.uiEvent.route)
    }

    Column(
        Modifier
            .fillMaxSize()
            .padding(
                top = Size_3X_Large,
                end = Size_Large,
                bottom = Size_Large,
                start = Size_Large
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Welcome()

        Spacer(modifier = Modifier.height(Size_2X_Large))

        UserNameTextField(
            username = state.userName,
            onUserNameChanged = actions.onUserNameChanged,
            hasError = state.invalidUsername,
            enabled = !state.progressBarState.isLoading()
        )

        Spacer(modifier = Modifier.height(Size_Regular))

        PasswordTextField(
            password = state.password,
            hasError = state.invalidPassword,
            onPasswordChanged = actions.onPasswordChanged,
            enabled = !state.progressBarState.isLoading()
        )

        Box(modifier = Modifier.height(200.dp)) {
            if (!state.progressBarState.isLoading()) {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(
                            top = Size_X_Large,
                            start = Size_Large,
                            end = Size_Large
                        ),
                    verticalArrangement = Arrangement.Center
                ) {
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = rally_green_500,
                            contentColor = MaterialTheme.colors.background
                        ),
                        onClick = actions.onDoLogin
                    ) {
                        Text(
                            text = stringResource(id = R.string.sign_in),
                            style = MaterialTheme.typography.button
                        )
                    }

                    Spacer(modifier = Modifier.height(Size_Regular))

                    TextButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        onClick = actions.onSignUp
                    ) {
                        Text(
                            text = stringResource(id = R.string.sign_up),
                            style = MaterialTheme.typography.button
                        )
                    }
                }
            } else {
                CircularIndeterminateProgressBar(state.progressBarState)
            }
        }
    }
}

@Composable
private fun UserNameTextField(
    hasError: Boolean,
    username: String,
    onUserNameChanged: (String) -> Unit,
    enabled: Boolean
) {
    Column {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            value = username,
            onValueChange = onUserNameChanged,
            trailingIcon = {
                if (hasError) {
                    Icon(
                        painterResource(id = R.drawable.ic_round_error_24),
                        stringResource(id = R.string.error),
                        tint = MaterialTheme.colors.error
                    )
                }
            },
            isError = hasError,
            label = { Text(stringResource(id = R.string.enter_username)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )
        if (hasError) {
            Text(
                text = stringResource(
                    id = R.string.error_username,
                    User.MIN_USERNAME_LENGTH,
                    User.MAX_USERNAME_LENGTH
                ),
                color = MaterialTheme.colors.error,
                style = MaterialTheme.typography.caption,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
    }
}

@Composable
private fun PasswordTextField(
    hasError: Boolean,
    password: String,
    onPasswordChanged: (String) -> Unit,
    enabled: Boolean
) {
    Column {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = password,
            enabled = enabled,
            trailingIcon = {
                if (hasError)
                    Icon(
                        painterResource(id = R.drawable.ic_round_error_24),
                        stringResource(id = R.string.error),
                        tint = MaterialTheme.colors.error
                    )
            },
            onValueChange = onPasswordChanged,
            isError = hasError,
            label = { Text(stringResource(id = R.string.enter_password)) },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )
        if (hasError) {
            Text(
                text = stringResource(
                    id = R.string.error_password,
                    User.MIN_PASSWORD_LENGTH,
                    User.MAX_PASSWORD_LENGTH
                ),
                color = MaterialTheme.colors.error,
                style = MaterialTheme.typography.caption,
                modifier = Modifier.padding(start = 16.dp)
            )
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
                onSignUp = {},
                onError = {},
                onShowMessageToUser = {},
                onNavigateSingleTop = {},
                onPasswordChanged = {},
                onUserNameChanged = {}
            )
        )
    }
}

@Composable
@Preview
private fun LoginScreenErrorPreview() {
    HomeHuntTheme(isPreview = true) {
        LoginContent(
            state = LoginState(invalidUsername = true, invalidPassword = true),
            actions = LoginActions(
                onDoLogin = {},
                onSignUp = {},
                onError = {},
                onShowMessageToUser = {},
                onNavigateSingleTop = {},
                onPasswordChanged = {},
                onUserNameChanged = {}
            )
        )
    }
}

@Composable
@Preview
private fun LoginScreenContentPreview() {
    HomeHuntTheme(isPreview = true) {
        LoginContent(
            state = LoginState(userName = "homehunt", password = "123456"),
            actions = LoginActions(
                onDoLogin = {},
                onSignUp = {},
                onError = {},
                onShowMessageToUser = {},
                onNavigateSingleTop = {},
                onPasswordChanged = {},
                onUserNameChanged = {}
            )
        )
    }
}

@Composable
@Preview
private fun LoginScreenLoadingPreview() {
    HomeHuntTheme(isPreview = true) {
        LoginContent(
            state = LoginState(
                userName = "homehunt",
                password = "123456",
                progressBarState = ProgressBarState.Loading
            ),
            actions = LoginActions(
                onDoLogin = {},
                onSignUp = {},
                onError = {},
                onShowMessageToUser = {},
                onNavigateSingleTop = {},
                onPasswordChanged = {},
                onUserNameChanged = {}
            )
        )
    }
}

