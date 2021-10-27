package com.rsicarelli.homehunt.ui.navigation

import androidx.annotation.StringRes
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.rsicarelli.homehunt.R
import com.rsicarelli.homehunt.ui.navigation.NavArguments.PROPERTY_DETAIL

sealed class Screen(
    val route: String,
    @StringRes val titleRes: Int,
    val arguments: List<NamedNavArgument> = emptyList()
) {
    object Splash : Screen(
        route = "splash",
        arguments = emptyList(),
        titleRes = R.string.loading_app
    )

    object Login : Screen(
        route = "login",
        arguments = emptyList(),
        titleRes = R.string.do_login
    )

    object Home : Screen(
        route = "home",
        titleRes = R.string.home
    )

    object Favourites : Screen(
        route = "favourites",
        titleRes = R.string.favourites
    )

    object Filter : Screen(
        route = "filter",
        titleRes = R.string.filter
    )

    object Map : Screen(
        route = "map",
        titleRes = R.string.map
    )

    object Discover : Screen(
        route = "discover",
        titleRes = R.string.discover
    )

    object PropertyDetail :
        Screen(
            "property_details",
            arguments = listOf(navArgument(PROPERTY_DETAIL) {
                type = NavType.StringType
            }),
            titleRes = R.string.property_details
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