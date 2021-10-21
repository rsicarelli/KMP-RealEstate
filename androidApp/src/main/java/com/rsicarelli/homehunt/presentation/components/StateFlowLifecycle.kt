package com.rsicarelli.homehunt.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow

@Composable
fun <T> ViewModel.rememberOnLifecycle(
    action: (LifecycleOwner) -> Flow<T>
): Flow<T> {
    val lifecycleOwner = LocalLifecycleOwner.current
    val stateFlowLifecycleAware: Flow<T> = remember(this, lifecycleOwner) {
        action(lifecycleOwner)
    }
    return stateFlowLifecycleAware
}