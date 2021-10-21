package com.rsicarelli.homehunt.presentation.propertyDetail.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.rsicarelli.homehunt.R
import com.rsicarelli.homehunt.ui.theme.DividerSize
import com.rsicarelli.homehunt.ui.theme.Size_Small
import com.rsicarelli.homehunt.ui.theme.Size_Regular

@Composable
fun PropertyFooter(reference: String, propertyUrl: String, pdfUrl: String?) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(Size_Regular)
    ) {
        Divider(thickness = DividerSize)

        Spacer(modifier = Modifier.height(Size_Small))

        SelectionContainer {
            Text(reference, style = MaterialTheme.typography.overline.copy(fontSize = 14.sp))
        }

        Spacer(modifier = Modifier.height(Size_Small))

        CustomClickableText(
            propertyUrl = propertyUrl,
            placeholderRes = R.string.see_on_web
        )

        pdfUrl?.let {
            CustomClickableText(
                propertyUrl = it,
                placeholderRes = R.string.see_pdf
            )
        }
    }
}