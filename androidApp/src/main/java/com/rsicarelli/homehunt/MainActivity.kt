package com.rsicarelli.homehunt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.snap
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.systemBarsPadding
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.rsicarelli.homehunt.presentation.favourites.FavouritesScreen
import com.rsicarelli.homehunt.presentation.filter.FilterScreen
import com.rsicarelli.homehunt.presentation.home.HomeScreen
import com.rsicarelli.homehunt.presentation.login.LoginScreen
import com.rsicarelli.homehunt.presentation.map.MapScreen
import com.rsicarelli.homehunt.presentation.propertyDetail.PropertyDetailScreen
import com.rsicarelli.homehunt.presentation.splash.SplashScreen
import com.rsicarelli.homehunt.ui.components.AppScaffold
import com.rsicarelli.homehunt.ui.navigation.NavArguments
import com.rsicarelli.homehunt.ui.navigation.Screen
import com.rsicarelli.homehunt.ui.theme.HomeHuntTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            HomeHuntTheme {
                ProvideWindowInsets {
                    MainContent()
                }
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun MainContent() {
    Surface(
        modifier = Modifier.systemBarsPadding(top = false)
    ) {
        val density = LocalDensity.current
        AppScaffold { scaffoldDelegate ->
            AnimatedNavHost(
                navController = scaffoldDelegate.navController,
                startDestination = Screen.Splash.route,
            ) {
                composable(route = Screen.Splash.route) {
                    SplashScreen(scaffoldDelegate)
                }

                composable(route = Screen.Login.route,
                    enterTransition = { _, _ -> fadeIn() },
                    exitTransition = { _, _ -> fadeOut() },
                    popEnterTransition = { _, _ -> fadeIn() },
                    popExitTransition = { _, _ -> fadeOut() }
                ) {
                    LoginScreen(scaffoldDelegate)
                }

                composable(
                    route = Screen.Home.route,
                    enterTransition = { _, _ -> fadeIn() },
                    exitTransition = { _, _ -> fadeOut() },
                    popEnterTransition = { _, _ -> fadeIn() },
                    popExitTransition = { _, _ -> fadeOut() }
                ) {
                    HomeScreen(scaffoldDelegate)
                }

                composable(
                    route = Screen.PropertyDetail.route + "/{${NavArguments.PROPERTY_DETAIL}}",
                    arguments = Screen.PropertyDetail.arguments,
                    enterTransition = { _, _ -> fadeIn() },
                    exitTransition = { _, _ -> fadeOut() },
                    popEnterTransition = { _, _ -> fadeIn() },
                    popExitTransition = { _, _ -> fadeOut() }
                ) {
                    PropertyDetailScreen(scaffoldDelegate)
                }

                composable(route = Screen.Filter.route,
                    enterTransition = { _, _ -> fadeIn() },
                    exitTransition = { _, _ -> fadeOut() },
                    popEnterTransition = { _, _ -> fadeIn() },
                    popExitTransition = { _, _ -> fadeOut() }
                ) {
                    FilterScreen(scaffoldDelegate)
                }
                composable(route = Screen.Favourites.route,
                    enterTransition = { _, _ -> fadeIn() },
                    exitTransition = { _, _ -> fadeOut() },
                    popEnterTransition = { _, _ -> fadeIn() },
                    popExitTransition = { _, _ -> fadeOut() }
                ) {
                    FavouritesScreen(scaffoldDelegate)
                }
                composable(route = Screen.Map.route,
                    enterTransition = { _, _ -> fadeIn() },
                    exitTransition = { _, _ -> fadeOut() },
                    popEnterTransition = { _, _ -> fadeIn() },
                    popExitTransition = { _, _ -> fadeOut() }
                ) {
                    MapScreen(scaffoldDelegate)
                }
            }
        }
    }
}

