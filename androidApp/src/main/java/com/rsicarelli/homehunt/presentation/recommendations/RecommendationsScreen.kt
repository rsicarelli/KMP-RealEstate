package com.rsicarelli.homehunt.presentation.recommendations

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import com.google.accompanist.pager.*
import com.rsicarelli.homehunt.presentation.components.CircularIndeterminateProgressBar
import com.rsicarelli.homehunt.presentation.components.EmptyContent
import com.rsicarelli.homehunt.presentation.components.rememberOnLifecycle
import com.rsicarelli.homehunt.presentation.recommendations.components.PropertyPager
import com.rsicarelli.homehunt.ui.navigation.Screen
import com.rsicarelli.homehunt.ui.state.AppState
import com.rsicarelli.homehunt.ui.theme.*
import com.rsicarelli.homehunt_kmm.core.model.ProgressBarState
import utils.Fixtures

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RecommendationsScreen(
    appState: AppState,
    viewModel: RecommendationsViewModel = hiltViewModel()
) {

    val stateFlowLifecycleAware = viewModel.rememberOnLifecycle {
        viewModel.init().flowWithLifecycle(
            lifecycle = it.lifecycle,
            minActiveState = Lifecycle.State.RESUMED
        )
    }

    val state by stateFlowLifecycleAware.collectAsState(initial = RecommendationsState())

    val actions = RecommendationsActions(
        onNavigate = appState::navigate,
        onDownVote = viewModel::onDownVote,
        onUpVote = viewModel::onUpVote,
        onPropertyViewed = viewModel::onPropertyViewed,
    )

    RecommendationsContent(
        state = state,
        actions = actions
    )
}

@OptIn(ExperimentalPagerApi::class, ExperimentalAnimationApi::class)
@Composable
private fun RecommendationsContent(state: RecommendationsState, actions: RecommendationsActions) {

    when {
        state.properties.isNotEmpty() -> PropertyPager(
            state.properties,
            onNavigate = { actions.onNavigate("${Screen.PropertyDetail.route}/${it}") },
            onUpVote = { actions.onDownVote(it) },
            onDownVote = { actions.onUpVote(it) },
        )
        state.progressBarState != ProgressBarState.Loading -> EmptyContent()
        else -> CircularIndeterminateProgressBar(progressBarState = state.progressBarState)
    }

}

@Preview
@Composable
private fun RecommendationPreview() {
    HomeHuntTheme(isPreview = true) {
        RecommendationsContent(
            state = RecommendationsState(properties = Fixtures.aListOfProperty),
            actions = RecommendationsActions(
                onDownVote = {},
                onUpVote = {},
                onNavigate = {},
                onPropertyViewed = {}
            )
        )
    }
}