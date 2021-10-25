package com.rsicarelli.homehunt.presentation.filter.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.rsicarelli.homehunt.R
import com.rsicarelli.homehunt.ui.theme.HomeHuntTheme
import com.rsicarelli.homehunt.ui.theme.Size_2X_Large
import com.rsicarelli.homehunt.ui.theme.Size_Regular
import com.rsicarelli.homehunt.ui.theme.Green_500

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
                .height(Size_2X_Large),
            shape = MaterialTheme.shapes.large,
            enabled = hasResults,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Green_500,
                contentColor = MaterialTheme.colors.background
            ),
            onClick = onClick
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
                style = MaterialTheme.typography.button.copy(fontSize = 16.sp)
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