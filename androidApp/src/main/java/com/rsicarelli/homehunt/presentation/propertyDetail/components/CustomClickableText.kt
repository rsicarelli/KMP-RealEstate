package com.rsicarelli.homehunt.presentation.propertyDetail.components

import android.content.Intent
import android.net.Uri
import androidx.annotation.StringRes
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.rsicarelli.homehunt.R
import com.rsicarelli.homehunt.ui.theme.HomeHuntTheme
import com.rsicarelli.homehunt.ui.theme.rally_blue_100

@Composable
fun CustomClickableText(
    propertyUrl: String,
    @StringRes placeholderRes: Int = R.string.see_on_web,
) {
    val annotatedText = buildAnnotatedString {
        pushStringAnnotation(
            tag = "URL",
            annotation = propertyUrl
        )
        withStyle(
            style = MaterialTheme.typography.overline.copy(
                fontSize = 14.sp,
                color = rally_blue_100
            ).toSpanStyle()
        ) {
            append(stringResource(id = placeholderRes))
        }
        pop()
    }

    val context = LocalContext.current
    ClickableText(
        text = annotatedText,
        onClick = { offset ->
            annotatedText.getStringAnnotations(tag = "URL", start = offset, end = offset)
                .firstOrNull()?.let { annotation ->
                    val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse(annotation.item))
                    context.startActivity(webIntent)
                }
        }
    )
}

@Composable
@Preview
private fun CustomClickableTextPreview() {
    HomeHuntTheme(isPreview = true) {
        CustomClickableText(propertyUrl = "")
    }
}