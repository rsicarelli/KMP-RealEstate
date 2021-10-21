package com.rsicarelli.homehunt.presentation.login.components

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.google.firebase.auth.AuthCredential
import com.rsicarelli.homehunt.R
import com.rsicarelli.homehunt.core.util.extractAuthCredentials
import com.rsicarelli.homehunt.core.util.getGoogleSignInOptions
import com.rsicarelli.homehunt.ui.theme.BorderSizeSmallest
import com.rsicarelli.homehunt.ui.theme.HomeHuntTheme
import com.rsicarelli.homehunt.ui.theme.Size_2X_Large
import com.rsicarelli.homehunt.ui.theme.Size_Large

@Composable
fun GoogleSignInOption(
    onDoLogin: (AuthCredential) -> Unit,
    onError: (Exception) -> Unit,
) {
    val context = LocalContext.current

    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
            activityResult.extractAuthCredentials(
                onSuccess = onDoLogin,
                onError = onError
            )
        }

    Box(
        modifier = Modifier
            .padding(Size_Large)
    ) {
        OutlinedButton(
            border = ButtonDefaults.outlinedBorder.copy(width = BorderSizeSmallest),
            modifier = Modifier
                .fillMaxWidth()
                .height(Size_2X_Large),
            onClick = {
                launcher.launch(context.getGoogleSignInOptions().signInIntent)
            },
            content = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    content = {
                        Icon(
                            tint = Color.Unspecified,
                            painter = painterResource(id = R.drawable.googleg_standard_color_18),
                            contentDescription = null,
                        )
                        Text(
                            style = MaterialTheme.typography.button,
                            color = MaterialTheme.colors.onSurface,
                            text = stringResource(id = R.string.google)
                        )
                        Icon(
                            tint = Color.Transparent,
                            imageVector = Icons.Default.MailOutline,
                            contentDescription = null,
                        )
                    }
                )
            }
        )
    }
}

@Composable
@Preview
private fun GoogleSignInOptionPreview() {
    HomeHuntTheme(isPreview = true) {
        GoogleSignInOption({ }, {})
    }
}