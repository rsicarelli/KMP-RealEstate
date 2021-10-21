package com.rsicarelli.homehunt.presentation.propertyDetail.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.rsicarelli.homehunt.ui.theme.Size_Regular
import com.rsicarelli.homehunt.ui.theme.Size_Small

@Composable
fun PropertyContentDescription(
    @StringRes titleRes: Int,
    content: String?,
) {
    content.takeIf { it != null && it.isNotEmpty() }?.let { text ->
        Column(
            Modifier
                .fillMaxWidth()
                .padding(Size_Regular)
        ) {
            Text(
                text = stringResource(id = titleRes),
                style = MaterialTheme.typography.h6
            )
            Spacer(modifier = Modifier.height(Size_Small))
            ExpandableText(text)
        }
    }
}