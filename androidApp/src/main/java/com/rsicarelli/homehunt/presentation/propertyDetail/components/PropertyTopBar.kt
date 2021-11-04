package com.rsicarelli.homehunt.presentation.propertyDetail.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Share
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.statusBarsPadding
import com.rsicarelli.homehunt.R
import com.rsicarelli.homehunt.presentation.components.BackButton
import com.rsicarelli.homehunt.presentation.components.FavouritableIconButton
import com.rsicarelli.homehunt.ui.theme.HomeHuntTheme
import com.rsicarelli.homehunt.ui.theme.IconSizeLarge
import com.rsicarelli.homehunt.ui.theme.Size_Small

@Composable
fun PropertyTopBar(
    onFavouriteClick: () -> Unit,
    onShareClick: () -> Unit,
    onBackClicked: () -> Unit,
    isFavourited: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(Size_Small),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        BackButton(
            modifier = Modifier.size(IconSizeLarge),
            onBackClick = onBackClicked
        )

        Row {
            IconButton(onClick = onShareClick) {
                Icon(
                    modifier = Modifier.size(32.dp),
                    imageVector = Icons.Rounded.Share,
                    contentDescription = stringResource(id = R.string.share)
                )
            }

            FavouritableIconButton(
                onClick = onFavouriteClick,
                isFavourited = isFavourited
            )
        }
    }
}

@Composable
@Preview
fun PropertyTopBarPreview() {
    HomeHuntTheme {
        PropertyTopBar(
            onFavouriteClick = {},
            isFavourited = false,
            onBackClicked = {},
            onShareClick = {})
    }
}