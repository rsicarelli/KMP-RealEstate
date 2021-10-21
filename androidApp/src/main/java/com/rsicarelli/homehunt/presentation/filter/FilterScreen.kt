package com.rsicarelli.homehunt.presentation.filter

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import com.google.accompanist.insets.statusBarsPadding
import com.rsicarelli.homehunt.core.model.HomeHuntState
import com.rsicarelli.homehunt.core.model.UiEvent
import com.rsicarelli.homehunt.presentation.components.BackButton
import com.rsicarelli.homehunt.presentation.components.rememberOnLifecycle
import com.rsicarelli.homehunt.presentation.filter.components.*
import com.rsicarelli.homehunt.ui.theme.HomeHuntTheme
import com.rsicarelli.homehunt.ui.theme.Size_Large

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FilterScreen(
    homeHuntState: HomeHuntState,
) {
    val viewModel: FilterViewModel = hiltViewModel()

    val stateFlowLifecycleAware = viewModel.rememberOnLifecycle {
        viewModel.init().flowWithLifecycle(it.lifecycle, Lifecycle.State.STARTED)
    }

    val state by stateFlowLifecycleAware.collectAsState(FilterState())

    val filterActions = FilterActions(
        onPriceRangeChanged = viewModel::onPriceRangeChanged,
        onSurfaceRangeChanged = viewModel::onSurfaceRangeChanged,
        onDormsSelectionChanged = viewModel::onDormsSelectionChanged,
        onBathSelectionChanged = viewModel::onBathSelectionChanged,
        onVisibilitySelectionChanged = viewModel::onVisibilitySelectionChanged,
        onLongTermRentalSelectionChanged = viewModel::onLongTermRentalSelectionChanged,
        onAvailabilitySelectionChanged = viewModel::onAvailabilitySelectionChanged,
        onSaveFilter = viewModel::onSaveFilter,
        onNavigateUp = homeHuntState::navigateUp,
        onNavigateSingleTop = homeHuntState::navigateSingleTop
    )

    FilterContent(
        state = state,
        actions = filterActions
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun FilterContent(
    state: FilterState,
    actions: FilterActions,
) {
    if (state.uiEvent is UiEvent.Navigate) actions.onNavigateUp()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
    ) {
        BackButton(
            modifier = Modifier,
            onBackClick = actions.onNavigateUp
        )

        LazyColumn(
            Modifier
                .fillMaxWidth()
                .weight(1.0f)
                .padding(start = Size_Large, end = Size_Large),
        ) {
            item {
                PriceRange(
                    range = state.priceRange,
                    onValueChange = actions.onPriceRangeChanged
                )
            }
            item {
                SurfaceRange(
                    range = state.surfaceRange,
                    onValueChange = actions.onSurfaceRangeChanged
                )
            }
            item {
                DormSelector(
                    dormCount = state.dormCount,
                    onValueChanged = actions.onDormsSelectionChanged
                )
            }
            item {
                BathSelector(
                    bathCount = state.bathCount,
                    onValueChanged = actions.onBathSelectionChanged
                )
            }
            item {
                VisibilitySelector(
                    isChecked = state.showSeen,
                    onChange = actions.onVisibilitySelectionChanged
                )
            }
            item {
                LongTermRentalSelector(
                    isChecked = state.longTermOnly,
                    onChange = actions.onLongTermRentalSelectionChanged
                )
            }
            item {
                AvailabilitySelector(
                    isChecked = state.availableOnly,
                    onChange = actions.onAvailabilitySelectionChanged
                )
            }
        }

        SeeResultsButton(
            previewResultCount = state.previewResultCount,
            onClick = actions.onSaveFilter
        )
    }
}

@Composable
@Preview
private fun FilterContentPreview() {
    HomeHuntTheme(isPreview = true) {
        FilterContent(
            state = FilterState(),
            actions = FilterActions(
                onPriceRangeChanged = {},
                onSurfaceRangeChanged = {},
                onDormsSelectionChanged = {},
                onBathSelectionChanged = {},
                onVisibilitySelectionChanged = {},
                onLongTermRentalSelectionChanged = {},
                onAvailabilitySelectionChanged = {},
                onSaveFilter = {},
                onNavigateUp = {},
                onNavigateSingleTop = {}
            )
        )
    }
}