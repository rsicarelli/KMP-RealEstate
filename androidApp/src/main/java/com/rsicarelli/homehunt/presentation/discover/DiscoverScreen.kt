package com.rsicarelli.homehunt.presentation.discover

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import com.rsicarelli.homehunt.presentation.components.CircularIndeterminateProgressBar
import com.rsicarelli.homehunt.presentation.components.EmptyContent
import com.rsicarelli.homehunt.presentation.components.rememberOnLifecycle
import com.rsicarelli.homehunt.presentation.discover.components.PropertyPager
import com.rsicarelli.homehunt.ui.navigation.Screen
import com.rsicarelli.homehunt.ui.state.AppState
import com.rsicarelli.homehunt.ui.theme.HomeHuntTheme
import com.rsicarelli.homehunt_kmm.core.model.ProgressBarState
import utils.Fixtures

@Composable
fun DiscoverScreen(
    onNavigateToProperty: (propertyId: String) -> Unit,
    filterApplied: Boolean
) {
    val viewModel: DiscoverViewModel = hiltViewModel()

    val stateFlowLifecycleAware = viewModel.rememberOnLifecycle {
        viewModel.init().flowWithLifecycle(
            lifecycle = it.lifecycle,
            minActiveState = Lifecycle.State.RESUMED
        )
    }

    val state by stateFlowLifecycleAware.collectAsState(initial = DiscoverState())

    if (filterApplied) {
        viewModel.loadProperties()
    }

    val actions = DiscoverActions(
        onNavigate = onNavigateToProperty,
        onDownVote = viewModel::onDownVote,
        onUpVote = viewModel::onUpVote,
        onPropertyViewed = viewModel::onPropertyViewed,
    )

    DiscoverContent(
        state = state,
        actions = actions
    )
}

@Composable
private fun DiscoverContent(state: DiscoverState, actions: DiscoverActions) {
    when {
        state.properties.isNotEmpty() -> PropertyPager(
            state.properties,
            onNavigate = { actions.onNavigate("${Screen.PropertyDetail.route}/${it}") },
            onUpVote = { actions.onUpVote(it) },
            onDownVote = { actions.onDownVote(it) },
            itemRemoved = state.itemRemoved
        )
        state.progressBarState != ProgressBarState.Loading -> EmptyContent()
        else -> CircularIndeterminateProgressBar(progressBarState = state.progressBarState)
    }

}

@Preview
@Composable
private fun RecommendationPreview() {
    HomeHuntTheme(isPreview = true) {
        DiscoverContent(
            state = DiscoverState(properties = Fixtures.aListOfProperty),
            actions = DiscoverActions(
                onDownVote = {},
                onUpVote = {},
                onNavigate = {},
                onPropertyViewed = {}
            )
        )
    }
}