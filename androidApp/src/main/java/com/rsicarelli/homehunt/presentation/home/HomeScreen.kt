package com.rsicarelli.homehunt.presentation.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.insets.statusBarsPadding
import com.rsicarelli.homehunt.R
import com.rsicarelli.homehunt.presentation.favourites.FavouritesScreen
import com.rsicarelli.homehunt.presentation.home.components.HomeTopBar
import com.rsicarelli.homehunt.presentation.home.components.NavigationOptions
import com.rsicarelli.homehunt.presentation.map.MapScreen
import com.rsicarelli.homehunt.presentation.discover.DiscoverScreen
import com.rsicarelli.homehunt.presentation.filter.FilterScreen
import com.rsicarelli.homehunt.ui.navigation.Screen
import com.rsicarelli.homehunt.ui.state.AppState
import com.rsicarelli.homehunt.ui.theme.Green_500
import com.rsicarelli.homehunt.ui.theme.HomeHuntTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(appState: AppState) {
    HomeContent(
        onNavigateToProperty = appState::navigate,
    )
}

@OptIn(
    ExperimentalMaterialApi::class,
    ExperimentalAnimationApi::class
)
@Composable
private fun HomeContent(
    onNavigateToProperty: (propertyId: String) -> Unit
) {
    val backdropState = rememberBackdropScaffoldState(initialValue = BackdropValue.Concealed)
    val selectedScreen = remember { mutableStateOf<Screen>(Screen.Discover) }
    var filterVisible by remember { mutableStateOf(false) }
    val coroutinesScope = rememberCoroutineScope()
    val contentVisibility = remember { mutableStateOf(true) }
    var isMenuShown by remember { mutableStateOf(false) }
    var filterApplied by remember { mutableStateOf(false) }

    isMenuShown = backdropState.isRevealed

    BackdropScaffold(
        modifier = Modifier.statusBarsPadding(),
        scaffoldState = backdropState,
        backLayerBackgroundColor = Green_500,
        gesturesEnabled = selectedScreen.value != Screen.Map,
        appBar = {
            HomeTopBar(
                titleRes = if (filterVisible) R.string.filter else selectedScreen.value.titleRes,
                currentDestination = selectedScreen.value,
                onFilterClick = {
                    coroutinesScope.launch { //TODO: simplify
                        if (filterVisible) {
                            isMenuShown = false
                            backdropState.conceal()
                            filterVisible = false
                        } else {
                            isMenuShown = true
                            filterVisible = true
                            backdropState.reveal()
                        }
                    }
                }, onNavigationClick = {
                    coroutinesScope.launch { //TODO: simplify
                        if (backdropState.isRevealed) {
                            isMenuShown = false
                            backdropState.conceal()
                            filterVisible = false
                        } else {
                            isMenuShown = !isMenuShown
                            filterVisible = false
                            backdropState.reveal()
                        }
                    }
                }, isMenuShown = isMenuShown
            )
        },
        backLayerContent = {
            if (filterVisible) {
                FilterScreen(
                    onFilterApplied = {
                        filterApplied = true //force recomposition
                        filterApplied = false
                    })
            } else {
                NavigationOptions(selectedScreen.value) {
                    coroutinesScope.launch {
                        contentVisibility.value = false
                        isMenuShown = false
                        backdropState.conceal()
                        selectedScreen.value = it
                        contentVisibility.value = true
                    }
                }
            }

        },
        frontLayerContent = {
            AnimatedVisibility(
                visible = contentVisibility.value,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                when (selectedScreen.value) {
                    Screen.Discover -> DiscoverScreen(onNavigateToProperty, filterApplied)
                    Screen.Map -> MapScreen(onNavigateToProperty)
                    Screen.Favourites -> FavouritesScreen(onNavigateToProperty)
                }
            }
        }
    )
}

@Composable
@Preview
private fun HomeScreenPreview() {
    HomeHuntTheme(isPreview = true) {
        HomeContent(
            onNavigateToProperty = {},
        )
    }
}


