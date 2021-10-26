package com.rsicarelli.homehunt.presentation.home.components

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.rsicarelli.homehunt.R
import com.rsicarelli.homehunt.ui.navigation.Screen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeTopBar(
    coroutinesScope: CoroutineScope = rememberCoroutineScope(),
    backdropState: BackdropScaffoldState,
    currentDestination: Screen,
    onFilterClick: () -> Unit,
) {
    TopAppBar(
        backgroundColor = Color.Transparent,
        elevation = 0.dp,
        title = { Text(stringResource(id = currentDestination.titleRes)) },
        navigationIcon = {
            IconButton(onClick = {
                coroutinesScope.launch {
                    if (backdropState.isConcealed) {
                        backdropState.reveal()
                    } else {
                        backdropState.conceal()
                    }
                }
            }) {
                if (backdropState.isRevealed) {
                    Icon(
                        imageVector = Icons.Rounded.Close,
                        contentDescription = stringResource(id = R.string.close_menu)
                    )
                } else {
                    Icon(
                        imageVector = Icons.Rounded.Menu,
                        contentDescription = stringResource(id = R.string.open_menu)
                    )
                }
            }
        },
        actions = {
            IconButton(onClick = onFilterClick) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_round_filter),
                    contentDescription = stringResource(id = R.string.filter)
                )
            }
        })
}