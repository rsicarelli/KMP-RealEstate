package com.rsicarelli.homehunt.presentation.filter


data class FilterActions(
    val onPriceRangeChanged: (ClosedFloatingPointRange<Float>) -> Unit,
    val onSurfaceRangeChanged: (ClosedFloatingPointRange<Float>) -> Unit,
    val onDormsSelectionChanged: (Int) -> Unit,
    val onBathSelectionChanged: (Int) -> Unit,
    val onVisibilitySelectionChanged: (Boolean) -> Unit,
    val onLongTermRentalSelectionChanged: (Boolean) -> Unit,
    val onAvailabilitySelectionChanged: (Boolean) -> Unit,
    val onSaveFilter: () -> Unit,
    val onNavigateUp: () -> Unit,
    val onNavigateSingleTop: (String) -> Unit
)
