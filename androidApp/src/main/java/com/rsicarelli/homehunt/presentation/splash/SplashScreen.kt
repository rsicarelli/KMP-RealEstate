package com.rsicarelli.homehunt.presentation.splash

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.rsicarelli.homehunt.R
import com.rsicarelli.homehunt.core.model.HomeHuntState
import com.rsicarelli.homehunt.core.model.UiEvent

@Composable
fun SplashScreen(
    homeHuntState: HomeHuntState,
    viewModel: SplashViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState(SplashState())

    val splashActions = SplashActions(
        onAnimationEnded = viewModel::onAnimationEnded,
        onNavigateSingleTop = homeHuntState::navigateSingleTop
    )

    SplashContent(
        state = state,
        actions = splashActions
    )
}

@Composable
private fun SplashContent(
    state: SplashState,
    actions: SplashActions
) {
    if (state.uiEvent is UiEvent.Navigate) actions.onNavigateSingleTop(state.uiEvent.route)

    val scale = remember { Animatable(0f) }
    val overshootInterpolator = remember { OvershootInterpolator(2f) }

    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 0.5f,
            animationSpec = tween(
                durationMillis = 500,
                easing = { overshootInterpolator.getInterpolation(it) }
            )
        )
        actions.onAnimationEnded()
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = stringResource(id = R.string.loading_app),
            modifier = Modifier.scale(scale.value)
        )
    }
}