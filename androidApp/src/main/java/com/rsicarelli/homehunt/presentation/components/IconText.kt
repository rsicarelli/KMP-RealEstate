package com.rsicarelli.homehunt.presentation.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.rsicarelli.homehunt.R
import com.rsicarelli.homehunt.ui.theme.HomeHuntTheme
import com.rsicarelli.homehunt.ui.theme.Size_X_Small

@Composable
fun IconText(
    modifier: Modifier = Modifier,
    text: String,
    textStyle: TextStyle = MaterialTheme.typography.body1,
    paddingIconStart: Dp = Size_X_Small,
    @DrawableRes leadingIcon: Int,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(painter = painterResource(id = leadingIcon), contentDescription = text)
        Text(
            modifier = modifier.padding(start = paddingIconStart),
            text = text,
            style = textStyle
        )
    }
}

@Composable
@Preview
private fun IconTextPreview() {
    HomeHuntTheme(isPreview = true) {
        IconText(
            text = "Some text",
            leadingIcon = R.drawable.ic_round_shower
        )
    }
}