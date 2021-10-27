package com.rsicarelli.homehunt.presentation.recommendations.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rsicarelli.homehunt.R
import com.rsicarelli.homehunt.ui.theme.*

@Composable
fun BottomBar(
    modifier: Modifier = Modifier,
    onDownVoted: () -> Unit,
    onUpVoted: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {

        RoundButton(
            onClick = onDownVoted,
            iconRes = R.drawable.ic_round_close,
            color = Color.White,
            contentDescriptionRes = R.string.down_vote
        )

        Spacer(modifier = Modifier.width(48.dp))

        RoundButton(
            onClick = onUpVoted,
            iconRes = R.drawable.ic_round_favorite,
            color = Color(0xFFEF5350),
            contentDescriptionRes = R.string.favourite
        )
    }
}

@Composable
private fun RoundButton(
    onClick: () -> Unit,
    @DrawableRes iconRes: Int,
    color: Color,
    @StringRes contentDescriptionRes: Int,
) {
    OutlinedButton(
        onClick = onClick,
        shape = CircleShape,
        modifier = Modifier.size(65.dp),
        elevation = ButtonDefaults.elevation(6.dp),
        border = null,
        colors = ButtonDefaults.outlinedButtonColors(backgroundColor = Light_Grey)
    ) {
        Icon(
            painter = painterResource(id = iconRes),
            modifier = Modifier.fillMaxSize(),
            tint = color,
            contentDescription = stringResource(id = contentDescriptionRes),
        )
    }
}

@Preview
@Composable
private fun BottomBarPreview() {
    HomeHuntTheme(isPreview = true) {
        BottomBar(
            modifier = Modifier.fillMaxWidth(),
            onDownVoted = {},
            onUpVoted = { }
        )
    }
}