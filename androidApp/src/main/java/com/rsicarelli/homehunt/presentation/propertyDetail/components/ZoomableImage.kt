package com.rsicarelli.homehunt.presentation.propertyDetail.components

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView
import coil.annotation.ExperimentalCoilApi
import com.ortiz.touchview.TouchImageView

@OptIn(ExperimentalCoilApi::class)
@SuppressLint("ClickableViewAccessibility")
@Composable
fun ZoomableImage(
    drawable: Drawable,
    resetZoom: Boolean
) {
    AndroidView({
        TouchImageView(it).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )

            setOnTouchListener { view, event ->
                var result = true
                if (event.pointerCount >= 2
                    || view.canScrollHorizontally(1)
                    && canScrollHorizontally(-1)
                ) {
                    result = when (event.action) {
                        MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
                            parent.requestDisallowInterceptTouchEvent(true)
                            false
                        }
                        MotionEvent.ACTION_UP -> {
                            parent.requestDisallowInterceptTouchEvent(false)
                            true
                        }
                        else -> true
                    }
                }
                result
            }
        }
    }) { imageView ->
        imageView.setImageDrawable(drawable)
        if (resetZoom) {
            imageView.resetZoom()
        }
    }
}