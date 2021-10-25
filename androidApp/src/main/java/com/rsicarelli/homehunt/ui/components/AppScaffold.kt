package com.rsicarelli.homehunt.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.rsicarelli.homehunt.R
import com.rsicarelli.homehunt.ui.state.HomeHuntState
import com.rsicarelli.homehunt.ui.navigation.BottomNavItem
import com.rsicarelli.homehunt.ui.navigation.Screen
import com.rsicarelli.homehunt.ui.state.rememberHomeHuntState
import com.rsicarelli.homehunt.ui.theme.*

@Composable
fun AppScaffold(
    homeHuntState: HomeHuntState = rememberHomeHuntState(),
    content: @Composable (HomeHuntState) -> Unit
) {
    SystemBarEffect()

    Scaffold(
        bottomBar = {
            if (homeHuntState.shouldShowBottomBar) {
                HomeHuntBottomNavigation(homeHuntState.navController)
            }
        },
        scaffoldState = homeHuntState.scaffoldState,
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            content(homeHuntState)
        }
    }
}

@Composable
private fun SystemBarEffect() {
    val systemUiController = rememberSystemUiController()
    val useDarkIcons = MaterialTheme.colors.isLight
    val systemBarColor = MaterialTheme.colors.surface

    SideEffect {
        systemUiController.setStatusBarColor(
            color = Secondary.copy(alpha = 0.4f),
            darkIcons = useDarkIcons
        )
        systemUiController.setNavigationBarColor(
            color = systemBarColor,
            darkIcons = useDarkIcons,
        )
    }
}

@Composable
private fun HomeHuntBottomNavigation(navController: NavController) {
    val items: List<BottomNavItem> = listOf(
        BottomNavItem(
            route = Screen.Home.route,
            icon = Icons.Rounded.Home,
            contentDescription = stringResource(id = R.string.home)
        ),
        BottomNavItem(
            route = Screen.Favourites.route,
            icon = Icons.Rounded.Favorite,
            contentDescription = stringResource(id = R.string.favourites)
        ),
        BottomNavItem(
            route = Screen.Map.route,
            painter = painterResource(id = R.drawable.ic_round_map_24),
            contentDescription = stringResource(id = R.string.map)
        ),
    )

    BottomNavigation(
        backgroundColor = MaterialTheme.colors.surface,
        contentColor = Color.White
    ) {
        items.forEach { item ->
            val selected = item.route == navController.currentDestination?.route

            val lineLength = animateFloatAsState(
                targetValue = if (selected) 1f else 0f,
                animationSpec = tween(
                    durationMillis = 300
                )
            )

            BottomNavigationItem(
                icon = {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(Size_Small)
                            .drawBehind {
                                if (lineLength.value > 0f) {
                                    drawLine(
                                        color = if (selected) Color.White
                                        else Color.White.copy(0.4f),
                                        start = Offset(
                                            size.width / 2f - lineLength.value * Size_Regular.toPx(),
                                            size.height
                                        ),
                                        end = Offset(
                                            size.width / 2f + lineLength.value * Size_Regular.toPx(),
                                            size.height
                                        ),
                                        strokeWidth = Size_2X_Small.toPx(),
                                        cap = StrokeCap.Round
                                    )
                                }


                            }
                    ) {
                        if (item.icon != null) {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.contentDescription,
                                modifier = Modifier
                                    .align(Alignment.Center)
                            )
                        } else if (item.painter != null) {
                            Icon(
                                painter = item.painter,
                                contentDescription = item.contentDescription,
                                modifier = Modifier
                                    .align(Alignment.Center)
                            )
                        }
                    }
                },
                selected = selected,
                onClick = {
                    if (navController.currentDestination?.route != item.route) {
                        navController.navigate(item.route)
                    }
                }
            )
        }
    }
}

@Composable
@Preview
private fun AppScaffoldPreview() {
    HomeHuntTheme {
        AppScaffold(
            content = {

            })
    }
}