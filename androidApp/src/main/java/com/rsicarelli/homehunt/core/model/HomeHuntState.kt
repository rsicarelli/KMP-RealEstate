package com.rsicarelli.homehunt.core.model

import android.content.Context
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.rsicarelli.homehunt.core.util.asString
import com.rsicarelli.homehunt.ui.navigation.bottomBarDestinations
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class HomeHuntState(
    private val coroutineScope: CoroutineScope,
    val scaffoldState: ScaffoldState,
    val navController: NavHostController,
    val context: Context,
) {

    val shouldShowBottomBar: Boolean
        @Composable get() {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            return navBackStackEntry?.destination?.route in bottomBarDestinations
        }

    fun showMessageToUser(uiText: UiText) {
        coroutineScope.launch {
            scaffoldState.snackbarHostState.showSnackbar(
                message = uiText.asString(context),
                duration = SnackbarDuration.Short
            )
        }
    }

    fun navigateSingleTop(route: String) {
        navController.navigate(route) {
            launchSingleTop = true
        }
    }

    fun navigate(uiEvent: UiEvent.Navigate) {
        navController.navigate(uiEvent.route)
    }

    fun navigate(route: String) {
        navController.navigate(route)
    }

    fun navigateUp() {
        navController.navigateUp()
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun rememberHomeHuntState(
    navController: NavHostController = rememberAnimatedNavController(),
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    context: Context = LocalContext.current,
): HomeHuntState = remember(navController, scaffoldState, coroutineScope, context) {
    HomeHuntState(coroutineScope, scaffoldState, navController, context)
}