package com.rsicarelli.homehunt.presentation.filter.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rsicarelli.homehunt.R
import com.rsicarelli.homehunt.ui.theme.*

@Composable
fun SeeResultsButton(
    onClick: () -> Unit,
    previewResultCount: Int?,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(Size_Regular))
        val hasResults = previewResultCount != null
        Button(
            modifier = Modifier
                .height(40.dp),
            shape = MaterialTheme.shapes.large,
            enabled = hasResults,
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(
                disabledContentColor = Color.White
            )
        )
        {
            val resources = LocalContext.current.resources

            val text = previewResultCount?.let { count ->
                if (count > 0) {
                    resources.getQuantityString(
                        R.plurals.see_results_plurals, count, count
                    )
                } else {
                    stringResource(id = R.string.no_results)
                }
            } ?: stringResource(id = R.string.calculating_results)

            Text(
                text = text,
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
        SeeResultsButton(onClick = { }, previewResultCount = null)
    }
}

@Composable
@Preview
private fun SeeResultsButtonPreview() {
    HomeHuntTheme(isPreview = true) {
        SeeResultsButton(onClick = { }, previewResultCount = 50)
    }
}

@Composable
@Preview
private fun SeeResultsButtonEmptyPreview() {
    HomeHuntTheme(isPreview = true) {
        SeeResultsButton(onClick = { }, previewResultCount = 0)
    }
}