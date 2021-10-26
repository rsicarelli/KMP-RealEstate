package com.rsicarelli.homehunt.presentation.home

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
import com.google.accompanist.pager.ExperimentalPagerApi
import com.rsicarelli.homehunt.presentation.favourites.FavouritesScreen
import com.rsicarelli.homehunt.presentation.home.components.HomeTopBar
import com.rsicarelli.homehunt.presentation.home.components.NavigationOptions
import com.rsicarelli.homehunt.presentation.map.MapScreen
import com.rsicarelli.homehunt.presentation.recommendations.RecommendationsScreen
import com.rsicarelli.homehunt.ui.navigation.Screen
import com.rsicarelli.homehunt.ui.state.AppState
import com.rsicarelli.homehunt.ui.theme.Green_500
import com.rsicarelli.homehunt.ui.theme.HomeHuntTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(appState: AppState) {
    HomeContent(
        favouritesScreen = { FavouritesScreen(appState = appState) },
        mapScreen = { MapScreen(appState = appState) },
        recommendationsScreen = { RecommendationsScreen(appState = appState) }
    )
}

@OptIn(ExperimentalPagerApi::class, ExperimentalMaterialApi::class)
@Composable
private fun HomeContent(
    recommendationsScreen: @Composable () -> Unit,
    favouritesScreen: @Composable () -> Unit,
    mapScreen: @Composable () -> Unit
) {

    val backdropState = rememberBackdropScaffoldState(initialValue = BackdropValue.Concealed)
    val selectedScreen = remember { mutableStateOf<Screen>(Screen.Home) }
    val coroutinesScope = rememberCoroutineScope()

    BackdropScaffold(
        modifier = Modifier.statusBarsPadding(),
        scaffoldState = backdropState,
        backLayerBackgroundColor = Green_500,
        appBar = {
            HomeTopBar(
                coroutinesScope = coroutinesScope,
                backdropState = backdropState,
                currentDestination = selectedScreen.value,
                onFilterClick = {
                    //TODO
                }
            )
        },
        backLayerContent = {
            NavigationOptions(selectedScreen.value) {
                selectedScreen.value = it
                coroutinesScope.launch {
                    backdropState.conceal()
                }
            }
        },
        frontLayerContent = {
            when (selectedScreen.value) {
                Screen.Home -> recommendationsScreen()
                Screen.Map -> mapScreen()
                Screen.Favourites -> favouritesScreen()
            }
        }
    )
}

@Composable
@Preview()
private fun HomeScreenPreview() {
    HomeHuntTheme(isPreview = true) {
        HomeContent(
            favouritesScreen = {},
            mapScreen = {},
            recommendationsScreen = {}
        )
    }
}


