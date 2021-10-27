package com.rsicarelli.homehunt.presentation.filter.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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

    val color = MaterialTheme.colors.contentColorFor(Green_500)

    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        RoundedButton(
            onClick = onDecrease,
            enabled = isDecreaseEnabled,
            painter = painterResource(id = R.drawable.ic_round_remove_24),
            contentDescription = contentDescription,
        )

        Spacer(modifier = Modifier.width(Size_Regular))

        Text(
            modifier = Modifier.width(Size_Regular),
            text = value.toString(),
            style = MaterialTheme.typography.body2.copy(color = Color.White)
        )
        Spacer(modifier = Modifier.width(Size_Small))
        RoundedButton(
            onClick = onIncrease,
            enabled = isIncreaseEnabled,
            imageVector = Icons.Rounded.Add,
            contentDescription = contentDescription,
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
        if (enabled) Color.White else Color.White.copy(alpha = 0.3f)

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
@Preview(showBackground = true, backgroundColor = 0xFF21AF6C, group = "new")
private fun CounterPreview() {
    HomeHuntTheme() {
        Counter(
            value = 2,
            onIncrease = { },
            onDecrease = { },
            contentDescription = ""
        )
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFF21AF6C, group = "new")
private fun RoundButtonEnabledPreview() {
    HomeHuntTheme() {
        RoundedButton(
            onClick = { },
            enabled = true,
            imageVector = Icons.Rounded.Add,
            contentDescription = ""
        )
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFF21AF6C, group = "new")
private fun RoundButtonDisabledPreview() {
    HomeHuntTheme() {
        RoundedButton(
            onClick = { },
            enabled = false,
            painter = painterResource(id = R.drawable.ic_round_remove_24),
            contentDescription = ""
        )
    }
}