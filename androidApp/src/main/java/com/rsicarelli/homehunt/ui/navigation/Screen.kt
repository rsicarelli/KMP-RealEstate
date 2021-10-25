package com.rsicarelli.homehunt.ui.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.rsicarelli.homehunt.ui.navigation.NavArguments.PROPERTY_DETAIL

sealed class Screen(val route: String, val arguments: List<NamedNavArgument> = emptyList()) {
    object Splash : Screen("splash", arguments = emptyList())
    object Login : Screen("login", arguments = emptyList())

    object Home : Screen(route = "home")
    object Favourites : Screen(route = "favourites")
    object Filter : Screen("filter")
    object Map : Screen(route = "map")

    object PropertyDetail :
        Screen(
            "property_details",
            arguments = listOf(navArgument(PROPERTY_DETAIL) {
                type = NavType.StringType
            })
        )
}

object NavArguments {
    const val PROPERTY_DETAIL = "referenceId"
}

val bottomBarDestinations = listOf(
    Screen.Home.route,
    Screen.Favourites.route,
    Screen.Map.route
)