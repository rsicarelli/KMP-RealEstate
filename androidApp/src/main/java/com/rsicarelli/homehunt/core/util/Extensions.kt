package com.rsicarelli.homehunt.core.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.rsicarelli.homehunt.BuildConfig
import com.rsicarelli.homehunt_kmm.domain.model.Location
import java.text.NumberFormat
import java.util.*

fun Double.toCurrency(): String? {
    val numberFormat = NumberFormat.getCurrencyInstance(Locale.ITALY)
    numberFormat.maximumFractionDigits = 0
    return numberFormat.format(this)
}

fun Context.getBitmapDescriptor(id: Int): BitmapDescriptor? {
    return ContextCompat.getDrawable(this, id)?.let {
        it.setBounds(0, 0, it.intrinsicWidth, it.intrinsicHeight)
        val bm: Bitmap =
            Bitmap.createBitmap(it.intrinsicWidth, it.intrinsicHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bm)
        it.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bm)
    }
}
