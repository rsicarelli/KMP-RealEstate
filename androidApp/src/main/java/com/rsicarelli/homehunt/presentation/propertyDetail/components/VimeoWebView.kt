package com.rsicarelli.homehunt.presentation.propertyDetail.components

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.viewinterop.AndroidView
import com.google.accompanist.insets.systemBarsPadding

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebContent(pageUrl: String) {
    BoxWithConstraints(
        Modifier
            .fillMaxSize()
            .systemBarsPadding(bottom = false),
        contentAlignment = Alignment.Center
    ) {
        val maxWidthPx = with(LocalDensity.current) { maxWidth.toPx() }
        val maxHeightPx = with(LocalDensity.current) { maxHeight.toPx() }
        val configuration = LocalConfiguration.current

        val height = if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            maxHeightPx.toInt()
        } else {
            800
        }

        AndroidView(factory = {
            WebView(it).apply {
                layoutParams = ViewGroup.LayoutParams(
                    maxWidthPx.toInt(),
                    height
                )
                setBackgroundColor(0)
                webViewClient = object : WebViewClient() {
                    override fun shouldOverrideUrlLoading(
                        webView: WebView,
                        request: WebResourceRequest
                    ): Boolean {
                        webView.loadUrl(request.url.toString())
                        return true
                    }
                }
                settings.javaScriptEnabled = true
            }
        }, modifier = Modifier.systemBarsPadding()) {
            it.loadData(pageUrl, "text/html", "utf-8")
        }
    }

}