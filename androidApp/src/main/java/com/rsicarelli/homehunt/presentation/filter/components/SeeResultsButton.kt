package com.rsicarelli.homehunt.presentation.filter.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rsicarelli.homehunt.R
import com.rsicarelli.homehunt.ui.theme.HomeHuntTheme
import com.rsicarelli.homehunt.ui.theme.Size_Regular
import com.rsicarelli.homehunt.ui.theme.Surface

@Composable
fun SeeResultsButton(
    onClick: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(Size_Regular))
        Button(
            modifier = Modifier
                .height(40.dp),
            shape = MaterialTheme.shapes.large,
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(
                disabledContentColor = Color.White
            )
        )
        {
            Text(
                text = stringResource(id = R.string.apply),
                style = MaterialTheme.typography.button.copy(fontSize = 16.sp, color = Surface)
            )
        }
        Spacer(modifier = Modifier.height(Size_Regular))
    }
}

@Composable
@Preview
private fun SeeResultsButtonCalculatingPreview() {
    HomeHuntTheme(isPreview = true) {
        SeeResultsButton(onClick = { })
    }
}

@Composable
@Preview
private fun SeeResultsButtonPreview() {
    HomeHuntTheme(isPreview = true) {
        SeeResultsButton(onClick = { })
    }
}

@Composable
@Preview
private fun SeeResultsButtonEmptyPreview() {
    HomeHuntTheme(isPreview = true) {
        SeeResultsButton(onClick = { })
    }
}