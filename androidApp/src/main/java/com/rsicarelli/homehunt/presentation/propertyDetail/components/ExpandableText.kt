package com.rsicarelli.homehunt.presentation.propertyDetail.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import com.rsicarelli.homehunt.R
import com.rsicarelli.homehunt.ui.theme.rally_blue
import com.rsicarelli.homehunt.ui.theme.Blue_200
import com.rsicarelli.homehunt.ui.theme.HomeHuntTheme

// adapted from https://stackoverflow.com/a/68894858/1945754
@Composable
fun ExpandableText(
    text: String,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = MaterialTheme.typography.body1,
    minimizedMaxLines: Int = 3,
) {
    var cutText by remember(text) { mutableStateOf<String?>(null) }
    var expanded by remember { mutableStateOf(false) }
    val textLayoutResultState = remember { mutableStateOf<TextLayoutResult?>(null) }
    val seeMoreSizeState = remember { mutableStateOf<IntSize?>(null) }
    val seeMoreOffsetState = remember { mutableStateOf<Offset?>(null) }

    // getting raw values for smart cast
    val textLayoutResult = textLayoutResultState.value
    val seeMoreSize = seeMoreSizeState.value
    val seeMoreOffset = seeMoreOffsetState.value

    LaunchedEffect(text, expanded, textLayoutResult, seeMoreSize) {
        val lastLineIndex = minimizedMaxLines - 1
        if (!expanded && textLayoutResult != null && seeMoreSize != null
            && lastLineIndex + 1 == textLayoutResult.lineCount
            && textLayoutResult.isLineEllipsized(lastLineIndex)
        ) {
            var lastCharIndex = textLayoutResult.getLineEnd(lastLineIndex, visibleEnd = true) + 1
            var charRect: Rect
            do {
                lastCharIndex -= 1
                charRect = textLayoutResult.getCursorRect(lastCharIndex)
            } while (
                charRect.left > textLayoutResult.size.width - seeMoreSize.width
            )
            seeMoreOffsetState.value = Offset(charRect.left, charRect.bottom - seeMoreSize.height)
            cutText = text.substring(startIndex = 0, endIndex = lastCharIndex)
        }
    }

    Column(
        horizontalAlignment = Alignment.End
    ) {
        Box(modifier) {
            Text(
                text = cutText ?: text,
                maxLines = if (expanded) Int.MAX_VALUE else minimizedMaxLines,
                overflow = TextOverflow.Ellipsis,
                onTextLayout = { textLayoutResultState.value = it },
                style = textStyle
            )

            if (!expanded) {
                val density = LocalDensity.current
                Text(
                    stringResource(id = R.string.see_more),
                    onTextLayout = { seeMoreSizeState.value = it.size },
                    style = textStyle.copy(rally_blue),
                    modifier = Modifier
                        .then(
                            if (seeMoreOffset != null)
                                Modifier.offset(
                                    x = with(density) { seeMoreOffset.x.toDp() },
                                    y = with(density) { seeMoreOffset.y.toDp() },
                                )
                            else
                                Modifier
                        )
                        .clickable {
                            expanded = true
                            cutText = null
                        }
                        .alpha(if (seeMoreOffset != null) 1f else 0f)
                )
            }
        }

        if (expanded) {
            Text(
                stringResource(id = R.string.see_less),
                modifier = Modifier
                    .clickable {
                        expanded = false
                        cutText = null
                    },
                style = textStyle.copy(Blue_200),
            )
        }
    }
}

@Composable
@Preview
fun ExpandableTextPreview() {
    HomeHuntTheme(isPreview = true) {
        ExpandableText(text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris convallis, nulla ut condimentum posuere, arcu risus lobortis purus, et consectetur tortor dolor sit amet nibh. Phasellus id venenatis nisl, sit amet eleifend lectus. Proin id viverra est. Curabitur a porta.")
    }
}