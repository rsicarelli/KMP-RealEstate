package com.rsicarelli.homehunt.presentation.favourites

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import com.rsicarelli.homehunt.R
import com.rsicarelli.homehunt.ui.state.HomeHuntState
import com.rsicarelli.homehunt.presentation.components.EmptyContent
import com.rsicarelli.homehunt.presentation.components.rememberOnLifecycle
import com.rsicarelli.homehunt.presentation.home.components.PropertyList
import com.rsicarelli.homehunt.ui.theme.HomeHuntTheme
import utils.Fixtures

@Composable
fun FavouritesScreen(
    homeHuntState: HomeHuntState
) {
    val viewModel: FavouritesViewModel = hiltViewModel()

    val stateFlowLifecycleAware = viewModel.rememberOnLifecycle {
        viewModel.init().flowWithLifecycle(
            lifecycle = it.lifecycle,
            minActiveState = Lifecycle.State.STARTED
        )
    }

    val state by stateFlowLifecycleAware.collectAsState(FavouritesState())

    val favouritesActions = FavouriteActions(
        onToggleFavourite = viewModel::onToggleFavourite,
        onNavigate = homeHuntState::navigate
    )

    FavouritesContent(
        state = state,
        actions = favouritesActions,
    )
}

@Composable
private fun FavouritesContent(
    actions: FavouriteActions,
    state: FavouritesState,
) {
    if (state.properties.isNotEmpty()) {
        PropertyList(
            properties = state.properties,
            headerPrefixRes = R.string.favourites,
            onNavigate = actions.onNavigate,
            onToggleFavourite = actions.onToggleFavourite,
        )
    } else if (state.isEmpty) {
        EmptyContent()
    }
}

@Composable
@Preview
private fun FavouriteScreenPreview() {
    HomeHuntTheme(isPreview = true) {
        FavouritesContent(
            actions = FavouriteActions({ _, _ -> }, {}),
            state = FavouritesState(
                properties = Fixtures.aListOfProperty.map {
                    it.copy(
                        isUpVoted = true,
                        isActive = false
                    )
                },
            )
        )
    }
}

