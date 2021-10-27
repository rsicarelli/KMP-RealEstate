package com.rsicarelli.homehunt.presentation.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.material.BackdropScaffold
import androidx.compose.material.BackdropValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberBackdropScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.insets.statusBarsPadding
import com.rsicarelli.homehunt.presentation.favourites.FavouritesScreen
import com.rsicarelli.homehunt.presentation.home.components.HomeTopBar
import com.rsicarelli.homehunt.presentation.home.components.NavigationOptions
import com.rsicarelli.homehunt.presentation.map.MapScreen
import com.rsicarelli.homehunt.presentation.discover.DiscoverScreen
import com.rsicarelli.homehunt.ui.navigation.Screen
import com.rsicarelli.homehunt.ui.state.AppState
import com.rsicarelli.homehunt.ui.theme.Green_500
import com.rsicarelli.homehunt.ui.theme.HomeHuntTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(appState: AppState) {
    HomeContent(
        onFilterClick = {
            appState.navigate(Screen.Filter.route)
        },
        favouritesScreen = { FavouritesScreen(appState = appState) },
        mapScreen = { MapScreen(appState = appState) },
        discoverScreen = { DiscoverScreen(appState = appState) }
    )
}

@OptIn(
    ExperimentalMaterialApi::class,
    ExperimentalAnimationApi::class
)
@Composable
private fun HomeContent(
    onFilterClick: () -> Unit,
    discoverScreen: @Composable () -> Unit,
    favouritesScreen: @Composable () -> Unit,
    mapScreen: @Composable () -> Unit
) {

    val backdropState = rememberBackdropScaffoldState(initialValue = BackdropValue.Concealed)
    val selectedScreen = remember { mutableStateOf<Screen>(Screen.Discover) }
    val coroutinesScope = rememberCoroutineScope()
    val contentVisibility = remember { mutableStateOf(true) }

    BackdropScaffold(
        modifier = Modifier.statusBarsPadding(),
        scaffoldState = backdropState,
        backLayerBackgroundColor = Green_500,
        gesturesEnabled = selectedScreen.value != Screen.Map,
        appBar = {
            HomeTopBar(
                coroutinesScope = coroutinesScope,
                backdropState = backdropState,
                currentDestination = selectedScreen.value,
                onFilterClick = onFilterClick
            )
        },
        backLayerContent = {
            NavigationOptions(selectedScreen.value) {
                coroutinesScope.launch {
                    contentVisibility.value = false
                    backdropState.conceal()
                    selectedScreen.value = it
                    contentVisibility.value = true
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
                    Screen.Discover -> discoverScreen()
                    Screen.Map -> mapScreen()
                    Screen.Favourites -> favouritesScreen()
                }
            }
        }
    )
}

@Composable
@Preview()
private fun HomeScreenPreview() {
    HomeHuntTheme(isPreview = true) {
        HomeContent(
            onFilterClick = {},
            favouritesScreen = {},
            mapScreen = {},
            discoverScreen = {}
        )
    }
}


