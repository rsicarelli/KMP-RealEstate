package com.rsicarelli.homehunt.presentation.filter

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
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
import com.rsicarelli.homehunt.ui.state.AppState
import com.rsicarelli.homehunt_kmm.core.model.UiEvent
import com.rsicarelli.homehunt.presentation.components.BackButton
import com.rsicarelli.homehunt.presentation.components.rememberOnLifecycle
import com.rsicarelli.homehunt.presentation.filter.components.*
import com.rsicarelli.homehunt.ui.theme.HomeHuntTheme
import com.rsicarelli.homehunt.ui.theme.Size_Large
import com.rsicarelli.homehunt.ui.theme.Size_Small

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FilterScreen(
    onFilterApplied: () -> Unit
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
        onSaveFilter = {
            viewModel.onSaveFilter()
            onFilterApplied()
        },
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
    LazyColumn(
        Modifier
            .fillMaxWidth()
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
        item {
            SeeResultsButton(
                previewResultCount = state.previewResultCount,
                onClick = actions.onSaveFilter
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF21AF6C, group = "test")
@Composable
private fun FilterContentPreview() {
    HomeHuntTheme() {
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
            )
        )
    }
}