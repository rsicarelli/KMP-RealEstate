package com.rsicarelli.homehunt.core.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.activity.result.ActivityResult
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.core.content.ContextCompat
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.GoogleAuthProvider
import com.rsicarelli.homehunt.R
import com.rsicarelli.homehunt.core.model.UiText
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import java.text.NumberFormat
import java.util.*
import kotlin.coroutines.cancellation.CancellationException
import kotlin.coroutines.resumeWithException

@OptIn(ExperimentalCoroutinesApi::class)
suspend fun <T> Task<T>.await(): T? {
    // fast path
    if (isComplete) {
        val e = exception
        return if (e == null) {
            if (isCanceled) {
                throw CancellationException(
                    "Task $this was cancelled normally."
                )
            } else {
                result
            }
        } else {
            throw e
        }
    }

    return suspendCancellableCoroutine { cont ->
        addOnCompleteListener {
            val e = exception
            if (e == null) {
                if (isCanceled) cont.cancel() else cont.resume(result) {}
            } else {
                cont.resumeWithException(e)
            }
        }
    }
}

fun ActivityResult.extractAuthCredentials(
    onSuccess: (AuthCredential) -> Unit,
    onError: (ApiException) -> Unit
) {
    try {
        val task = GoogleSignIn.getSignedInAccountFromIntent(this.data)
        val account = task.getResult(ApiException::class.java)!!
        val credential = GoogleAuthProvider.getCredential(account.idToken!!, null)
        onSuccess(credential)
    } catch (apiException: ApiException) {
        onError(apiException)
    }
}

@Composable
fun UiText.asString(): String {
    return when (this) {
        is UiText.DynamicString -> this.value
        is UiText.StringResource -> stringResource(id = this.id)
    }
}

fun UiText.asString(context: Context): String {
    return when (this) {
        is UiText.DynamicString -> this.value
        is UiText.StringResource -> context.getString(this.id)
    }
}

fun Context.getGoogleSignInOptions(): GoogleSignInClient {
    val token = this.resources.getString(R.string.default_web_client_id)

    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(token)
        .requestEmail()
        .build()

    return GoogleSignIn.getClient(this, gso)
}

fun Double.toCurrency(): String? {
    val numberFormat = NumberFormat.getCurrencyInstance(Locale.ITALY)
    numberFormat.maximumFractionDigits = 0;
    return numberFormat.format(this)
}

fun String.convertToInt() = this.replace(Regex("[^0-9]"), "").toInt()

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