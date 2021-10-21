package com.rsicarelli.homehunt.presentation.filter.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.rsicarelli.homehunt.R
import com.rsicarelli.homehunt.ui.theme.*

@Composable
fun Counter(
    value: Int,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit,
    contentDescription: String,
) {
    val isDecreaseEnabled = value != 0
    val isIncreaseEnabled = value != 5

    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        RoundedButton(
            onDecrease,
            isDecreaseEnabled,
            painter = painterResource(id = R.drawable.ic_round_remove_24),
            contentDescription = contentDescription
        )
        Spacer(modifier = Modifier.width(Size_Regular))
        Text(
            modifier = Modifier.width(Size_Regular),
            text = value.toString(),
            style = MaterialTheme.typography.body2
        )
        Spacer(modifier = Modifier.width(Size_Small))
        RoundedButton(
            onIncrease,
            isIncreaseEnabled,
            Icons.Rounded.Add,
            contentDescription = contentDescription
        )
    }
}

@Composable
fun RoundedButton(
    onClick: () -> Unit,
    enabled: Boolean,
    imageVector: ImageVector? = null,
    painter: Painter? = null,
    contentDescription: String,
) {
    val color =
        if (enabled) MaterialTheme.colors.primary else MaterialTheme.colors.primary.copy(alpha = 0.3f)

    IconButton(
        onClick = onClick,
        enabled = enabled,
        modifier = Modifier
            .then(Modifier.size(Size_X_Large))
            .border(
                BorderSizeSmallest,
                color = color,
                shape = CircleShape
            )
    ) {

        imageVector?.let {
            Icon(
                it,
                modifier = Modifier.size(IconSizeSmall),
                contentDescription = contentDescription,
                tint = color
            )
        }

        painter?.let {
            Icon(
                it,
                modifier = Modifier.size(IconSizeSmall),
                contentDescription = contentDescription,
                tint = color
            )
        }
    }
}

@Composable
@Preview
private fun CounterPreview() {
    HomeHuntTheme(isPreview = true) {
        Counter(
            value = 2,
            onIncrease = { },
            onDecrease = { },
            contentDescription = ""
        )
    }
}

@Composable
@Preview
private fun RoundButtonEnabledPreview() {
    HomeHuntTheme(isPreview = true) {
        RoundedButton(
            onClick = { },
            enabled = true,
            imageVector = Icons.Rounded.Add,
            contentDescription = ""
        )
    }
}

@Composable
@Preview
private fun RoundButtonDisabledPreview() {
    HomeHuntTheme(isPreview = true) {
        RoundedButton(
            onClick = { },
            enabled = false,
            painter = painterResource(id = R.drawable.ic_round_remove_24),
            contentDescription = ""
        )
    }
}