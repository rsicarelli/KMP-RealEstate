package com.rsicarelli.homehunt.presentation.map

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import com.rsicarelli.homehunt.ui.state.HomeHuntState
import com.rsicarelli.homehunt.presentation.components.FilterFab
import com.rsicarelli.homehunt.presentation.components.rememberOnLifecycle
import com.rsicarelli.homehunt.presentation.home.components.PropertyListItem
import com.rsicarelli.homehunt.presentation.map.components.PropertiesMapView
import com.rsicarelli.homehunt.ui.navigation.Screen
import com.rsicarelli.homehunt.ui.theme.Size_Regular
import com.rsicarelli.homehunt.ui.theme.Size_Small


@Composable
fun MapScreen(
    homeHuntState: HomeHuntState,
    viewModel: MapViewModel = hiltViewModel(),
) {
    val stateFlowLifecycleAware = viewModel.rememberOnLifecycle {
        viewModel.init().flowWithLifecycle(
            lifecycle = it.lifecycle,
            minActiveState = Lifecycle.State.RESUMED
        )
    }

    val state by stateFlowLifecycleAware.collectAsState(initial = MapState())

    val actions = MapActions(
        onNavigate = homeHuntState::navigate,
        onToggleFavourite = viewModel::toggleFavourite,
        onPropertyViewed = viewModel::onPropertyViewed,
        onMarkerSelected = viewModel::onMarkerSelected,
        onMapClick = viewModel::onMapClicked,
        onClusterClicked = viewModel::onClusterClicked
    )

    MapContent(
        state = state,
        actions = actions
    )
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun MapContent(
    state: MapState,
    actions: MapActions,
) {

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.BottomEnd
    ) {

        if (state.properties.isNotEmpty()) {
            PropertiesMapView(
                onMarkerClick = actions.onMarkerSelected,
                onClusterClick = actions.onClusterClicked,
                locations = state.properties,
                onMapClick = actions.onMapClick
            )
        }

        SinglePropertyPreview(state, actions)

        MultiplePropertiesPreview(state, actions)

        FilterFab(false) {
            actions.onNavigate(Screen.Filter.route)
        }
    }
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalAnimationApi::class)
@Composable
fun MultiplePropertiesPreview(state: MapState, actions: MapActions) {
    AnimatedVisibility(
        visible = state.showClusteredPreview,
        enter = expandVertically(expandFrom = Alignment.Top),
        exit = shrinkVertically(shrinkTowards = Alignment.Top)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    end = Size_Small,
                    start = Size_Small,
                    bottom = Size_Small
                )
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .background(
                        Color.Black.copy(alpha = 0.2F),
                        MaterialTheme.shapes.medium
                    )
                    .padding(
                        end = Size_Small,
                        start = Size_Small,
                    )
            ) {
                items(state.propertySnippet) { property ->
                    PropertyListItem(
                        property = property,
                        onSelectProperty = { actions.onNavigate("${Screen.PropertyDetail.route}/${property._id}") },
                        onFavouriteClick = {
                            actions.onToggleFavourite(
                                property._id,
                                !property.isUpVoted
                            )
                        },
                        onViewedGallery = { },
                        gallerySize = 160.dp
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun SinglePropertyPreview(
    state: MapState,
    actions: MapActions,
) {
    AnimatedVisibility(
        visible = state.showSinglePreview,
        enter = expandVertically(expandFrom = Alignment.Top),
        exit = shrinkVertically(shrinkTowards = Alignment.Top)
    ) {
        state.properties.firstOrNull()?.let { property ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        end = Size_Regular,
                        start = Size_Regular,
                        bottom = Size_Regular
                    )
            ) {
                item {
                    PropertyListItem(
                        property = property,
                        onSelectProperty = { actions.onNavigate("${Screen.PropertyDetail.route}/${property._id}") },
                        onFavouriteClick = {
                            actions.onToggleFavourite(
                                property._id,
                                !property.isUpVoted
                            )
                        },
                        onViewedGallery = { },
                        gallerySize = 160.dp
                    )
                }
            }
        }
    }
}