package com.rsicarelli.homehunt.presentation.components

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.rsicarelli.homehunt.R
import com.rsicarelli.homehunt.ui.theme.HomeHuntTheme

@Composable
fun FavouritableIconButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    isFavourited: Boolean
) {
    IconButton(onClick = onClick) {
        if (isFavourited) {
            Icon(
                modifier = modifier,
                painter = painterResource(id = R.drawable.ic_round_favorite),
                contentDescription = stringResource(
                    id = R.string.favourite
                )
            )
        } else {
            Icon(
                modifier = modifier,
                painter = painterResource(id = R.drawable.ic_round_favorite_border),
                contentDescription = stringResource(
                    id = R.string.unfavourite
                )
            )
        }
    }
}

@Composable
@Preview
private fun FavouritableIconButtonFavouritedPreview() {
    HomeHuntTheme(isPreview = true) {
        FavouritableIconButton(onClick = { }, isFavourited = true)
    }
}

@Composable
@Preview
private fun FavouritableIconButtonUnFavouritedPreview() {
    HomeHuntTheme(isPreview = true) {
        FavouritableIconButton(onClick = { }, isFavourited = false)
    }
}